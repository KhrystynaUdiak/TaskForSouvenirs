package service.implementation;

import data_storage.ProducerSouvenirJoiner;
import model.Producer;
import model.Souvenir;
import service.ProducerService;

import java.util.*;
import java.util.stream.Collectors;

public class ProducerServiceImpl implements ProducerService {
    private static ProducerServiceImpl producerService;
    private final ProducerSouvenirJoiner producerSouvenirJoiner;


   private ProducerServiceImpl(ProducerSouvenirJoiner productSouvenirJoiner){
       this.producerSouvenirJoiner = productSouvenirJoiner;
   }

   public static ProducerServiceImpl getInstance(ProducerSouvenirJoiner productSouvenirJoiner){
       if(producerService == null){
           producerService = new ProducerServiceImpl(productSouvenirJoiner);
       }
       return producerService;
   }

    @Override
    public void addProducer(Producer producer) {
        Map<Long, Set<Souvenir>> producersSouvenir = producerSouvenirJoiner.getAll();
        producersSouvenir.put(producer.getId(), new HashSet<>());
    }


    @Override
    public Producer readById(long id) {
        Map<Long, Set<Souvenir>> producersSouvenir = producerSouvenirJoiner.getAll();
        return producersSouvenir.values().stream()
                .flatMap(Set::stream)
                .filter(souvenir -> souvenir.getProducer().getId() == id)
                .findFirst()
                .map(Souvenir::getProducer)
                .orElse(null);
    }

    @Override
    public void update(Producer producer) {
        Map<Long, Set<Souvenir>> producersSouvenir = producerSouvenirJoiner.getAll();
        Set<Souvenir> souvenirs = producersSouvenir.values().stream()
                .flatMap(Set::stream)
                .filter(p -> p.getProducer().getId() == producer.getId())
                .peek(s -> s.setProducer(producer))
                .collect(Collectors.toSet());
        producersSouvenir.put(producer.getId(), souvenirs);
    }

    @Override
    public void delete(Producer producer) {
        Map<Long, Set<Souvenir>> producersSouvenir = producerSouvenirJoiner.getAll();
        producersSouvenir.remove(producer.getId());
    }

    @Override
    public List<Producer> getAll() {
        return producerSouvenirJoiner.getAll()
                .values().stream()
                .flatMap(Set::stream)
                .map(Souvenir::getProducer)
                .distinct()
                .collect(Collectors.toList());
    }
    @Override
    public List<Producer> getCheapestProducers(double price){
        Map<Long, Set<Souvenir>> producersSouvenir = producerSouvenirJoiner.getAll();
        return producersSouvenir.values().stream()
                .flatMap(Set::stream)
                .filter(s1 -> price > s1.getPrice())
                .map(Souvenir::getProducer)
                .distinct()
                .collect(Collectors.toList());

    }
    @Override
    public List<Producer> getProducersOfSouvenirsByYear(String name, int year){
        Map<Long, Set<Souvenir>> producersSouvenir = producerSouvenirJoiner.getAll();
        return producersSouvenir.values().stream()
                .flatMap(Set::stream)
                .filter(s -> s.getName() == name)
                .filter(s1 -> s1.getDate() == year)
                .map(Souvenir::getProducer)
                .collect(Collectors.toList());
    }
}
