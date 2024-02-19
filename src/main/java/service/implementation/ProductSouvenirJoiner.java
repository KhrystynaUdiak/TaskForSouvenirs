package service.implementation;

import model.Producer;
import model.Souvenir;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ProductSouvenirJoiner {
  private ProducerServiceImpl producerService;
  private SouvenirServiceImpl souvenirService;
  private static ProductSouvenirJoiner productSouvenirJoiner;

  private Map<Producer, Set<Souvenir>> producersSouvenir;
  private ProductSouvenirJoiner(Map<Producer, Set<Souvenir>> producersSouvenir){
      this.producersSouvenir = producersSouvenir;
  }

  public static ProductSouvenirJoiner getInstance(Map<Producer, Set<Souvenir>> producersSouvenir){
      if(productSouvenirJoiner == null){
          productSouvenirJoiner = new ProductSouvenirJoiner(producersSouvenir);
      }
      return productSouvenirJoiner;
  }

    public Map<Producer, Set<Souvenir>> addSouvenir(Producer producer, Souvenir souvenir){
        if(this.producersSouvenir.isEmpty()){
            this.producersSouvenir.put(producer, new HashSet<>());
        }
        if(!this.producersSouvenir.containsKey(producer)){
          this.producersSouvenir.put(producer, new HashSet<>());
      }
        Set<Souvenir> souvenirs = producersSouvenir.get(producer);
        souvenirs.add(souvenir);
        producersSouvenir.put(producer, souvenirs);
        return producersSouvenir;
    }

    public Set<Souvenir> getSouvenirsByProducer(Producer producer) { //дублюється? де краще залишити?
        return producersSouvenir.get(producer);
    }

    //інформація про сувеніри, виготовлені в заданій країні - залишено в SouvenirServiceImpl

    public List<Producer> getCheapestProducers(double price){//переврити роьоту методу
     return producersSouvenir.entrySet().stream()
              .filter(e -> e.getValue()
                      .stream()
                      .allMatch(souvenir -> price > souvenir.getPrice()))
             .map(Map.Entry::getKey)
             .collect(Collectors.toList());
//        return producers.stream().filter(producer -> producer.getSouvenirs().stream()
//                .allMatch(souvenir -> price > souvenir.getPrice())).collect(Collectors.toList());
    }
    public Map<Producer, Set<Souvenir>> getAll(){
      return this.producersSouvenir;
    }

    public Set<Souvenir> getAllSouvenir(){
      Set<Souvenir> souvenirs = new HashSet<>();
      for (Map.Entry<Producer, Set<Souvenir>> entry : producersSouvenir.entrySet()){
          souvenirs.addAll(entry.getValue());
      }
      return souvenirs;
    }

    public List<Producer> getProducersOfSouvenirsByYear(Souvenir souvenir, int year){ //перевірити роботу методу
      return producersSouvenir.entrySet().stream()
              .filter(e -> e.getValue()
                      .stream()
                      .filter(s -> s.equals(souvenir))
                      .anyMatch(s -> souvenir.getDate() == year))
              .map(Map.Entry::getKey)
              .collect(Collectors.toList());
    }

    public void deleteProducerWithSouvenirs(Producer producer){
      this.producersSouvenir.remove(producer);
    }


    public void writeToFile() {
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("producers_souvenirs.txt"))){
                output.writeObject(producersSouvenir);
        }catch (IOException e){}
    }

    public void readFromFile() {
        Map<Producer, Set<Souvenir>> map = new HashMap<>();
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("producers_souvenirs.txt"))){
                map = (Map<Producer, Set<Souvenir>>) in.readObject();
                System.out.println(map);
        } catch(IOException | ClassNotFoundException e){}
    }

}
