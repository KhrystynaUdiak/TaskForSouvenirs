package service.implementation;

import model.Souvenir;
import service.SouvenirService;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SouvenirServiceImpl implements SouvenirService{
    private static SouvenirServiceImpl souvenirService;
    private ProductSouvenirJoiner productSouvenirJoiner;
    private Set<Souvenir> souvenirs;

    private SouvenirServiceImpl(){
    }

    public static SouvenirServiceImpl getInstance(){
        if(souvenirService == null){
            souvenirService = new SouvenirServiceImpl();
        }
        return souvenirService;
    }

    @Override
    public Set<Souvenir> add(Souvenir souvenir) {
        if(souvenirs == null) {
            souvenirs = new HashSet<>();
        }
        this.souvenirs.add(souvenir);
        //  productSouvenirJoiner.addSouvenir(souvenir.getProducer(), souvenir);
        return souvenirs;
    }

    @Override
    public Souvenir readById(long id) {
        return souvenirs.stream()
                .filter(s -> id == s.getId())
                .findAny()
                .orElse(null);
    }

    @Override
    public Set<Souvenir> update(Souvenir souvenir) {
       return this.souvenirs.stream()
                .filter(s -> s.getId() == souvenir.getId())
                .peek(s -> {
                    s.setName(souvenir.getName());
                    s.setPrice(souvenir.getPrice());
                    s.setProducer(souvenir.getProducer());  //чи може помінятись виробник? виключити поле звідси чи зробити final в класі Souvenir
                    s.setDate(souvenir.getDate());          //або зробити видалення сувеніру виробника та присвоєння нового через дані ProductSouvenirJoiner
                })
               .collect(Collectors.toSet());
    }

    @Override
    public void delete(Souvenir souvenir) {
         this.souvenirs.remove(souvenir);
    }

    @Override
    public Set<Souvenir> getAll() {
        return this.souvenirs;
    }

    @Override
    public Set<Souvenir> getByProducerId(long producerId) {
        return souvenirs.stream().filter(s -> producerId == s.getProducer().getId()).collect(Collectors.toSet());
    }

    @Override
    public Set<Souvenir> getByCountry(String country){
        return this.souvenirs.stream()
                .filter(s -> country.equalsIgnoreCase(s.getProducer().getCountry()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Souvenir> getByYear(int year){
        return this.souvenirs.stream()
                .filter(s -> year == s.getDate())
                .collect(Collectors.toSet());
    }

    @Override
    public void writeToFile() {
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("souvenirs_catalog.txt"))){
            for(Souvenir souvenir : souvenirs) {
                output.writeObject(souvenir);
            }
        }catch (IOException e){}
    }

    @Override
    public void readFromFile() {
        Souvenir souvenir = null;
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("souvenirs_catalog.txt"))){
            while (in.available() >= 0) {
                souvenir = (Souvenir) in.readObject();
                System.out.println(souvenir);
            }
        } catch(IOException | ClassNotFoundException e){}
    }
}
