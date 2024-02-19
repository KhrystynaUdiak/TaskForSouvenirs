package service.implementation;

import model.Producer;
import model.Souvenir;
import service.ProducerService;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProducerServiceImpl implements ProducerService {
    private static ProducerServiceImpl producerService;

   private List<Producer> producers;

   private ProducerServiceImpl(List<Producer> producers){
       this.producers = producers;
   }

   public static ProducerServiceImpl getInstance(List<Producer> producers){
       if(producerService == null){
           producerService = new ProducerServiceImpl(producers);
       }
       return producerService;
   }

    @Override
    public List<Producer> add(Producer producer) {
        if(producers == null){
            producers = new ArrayList<>();
        }
        this.producers.add(producer);
        return producers;
    }


    @Override
    public Producer readById(long id) {
        return producers.stream()
                .filter(p -> id == p.getId())
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Producer> update(Producer producer) {
        return producers.stream()
                .filter(p -> p.getId() == producer.getId())
                .peek(s -> {
                    s.setName(producer.getName());
                    s.setCountry(producer.getCountry());
                })
                .collect(Collectors.toList());
    }

    @Override
    public boolean delete(long id) {
        return producers.removeIf(p -> id == p.getId());
    }

    @Override
    public List<Producer> getAll() {
        return producers;
    }

//    public Set<Souvenir> getProducersSouvenirs(Producer producer) { //забрано сет сувенірів з класу виробника, метод перенесено до ProductSouvenirJoiner
//        return producer.getSouvenirs();
//    }
//
//    public List<Producer> getCheapestProducers(double price){//перенесено до Joiner
//       return producers.stream().filter(producer -> producer.getSouvenirs().stream()
//               .allMatch(souvenir -> price > souvenir.getPrice())).collect(Collectors.toList());
//    }

    @Override
    public void writeToFile() {
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("producers_catalog.txt"))){
            for(Producer producer : producers) {
                output.writeObject(producer);
            }
        }catch (IOException e){}
    }

    @Override
    public void readFromFile() {
        Producer producer = null;
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("producers_catalog.txt"))){
            while (in.available() >= 0) {
                producer = (Producer) in.readObject();
                System.out.println(producer);
            }
        } catch(IOException | ClassNotFoundException e){}
    }

}
