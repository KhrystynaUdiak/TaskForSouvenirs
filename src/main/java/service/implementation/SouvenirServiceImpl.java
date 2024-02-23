package service.implementation;

import data_storage.ProducerSouvenirJoiner;
import model.Souvenir;
import service.SouvenirService;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class SouvenirServiceImpl implements SouvenirService{
    private static SouvenirServiceImpl souvenirService;
    private final ProducerSouvenirJoiner producerSouvenirJoiner;

    private SouvenirServiceImpl(ProducerSouvenirJoiner producerSouvenirJoiner){
        this.producerSouvenirJoiner = producerSouvenirJoiner;
    }

    public static SouvenirServiceImpl getInstance(ProducerSouvenirJoiner producerSouvenirJoiner){
        if(souvenirService == null){
            souvenirService = new SouvenirServiceImpl(producerSouvenirJoiner);
        }
        return souvenirService;
    }

   @Override
  public void addSouvenir(Souvenir souvenir){
      Map<Long, Set<Souvenir>> producersSouvenir = producerSouvenirJoiner.getAll();
      if(producersSouvenir.isEmpty()){
          producersSouvenir.put(souvenir.getProducer().getId(), new HashSet<>());
      }
      if(!producersSouvenir.containsKey(souvenir.getProducer().getId())){
          producersSouvenir.put(souvenir.getProducer().getId(), new HashSet<>());
      }
      Set<Souvenir> souvenirs = producersSouvenir.get(souvenir.getProducer().getId());
       souvenirs.add(souvenir);
       producersSouvenir.put(souvenir.getProducer().getId(), souvenirs);
  }


    @Override
    public Souvenir readById(long id) {
        Map<Long, Set<Souvenir>> producersSouvenir = producerSouvenirJoiner.getAll();
        return producersSouvenir.values().stream()
                .flatMap(Set::stream)
                .filter(souvenir -> souvenir.getId() == id)
                .findAny()
                .orElse(null);
    }

    @Override
    public void update(Souvenir souvenir) {
        Map<Long, Set<Souvenir>> producersSouvenir = producerSouvenirJoiner.getAll().entrySet().stream()
                .map(entry -> {
                    Long producerId = entry.getKey();
                    Set<Souvenir> souvenirs = entry.getValue().stream()
                            .filter(s -> s.getId() == souvenir.getId())
                            .peek(souvenir1 -> {
                    souvenir1.setName(souvenir.getName());
                    souvenir1.setPrice(souvenir.getPrice());
                    souvenir1.setDate(souvenir.getDate());})
                            .collect(Collectors.toSet());
                    return new AbstractMap.SimpleEntry<>(producerId, souvenirs);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void delete(Souvenir souvenir) {
        Map<Long, Set<Souvenir>> producersSouvenir = producerSouvenirJoiner.getAll().entrySet().stream()
                .map(entry -> {
                    Long producerId = entry.getKey();
                    Set<Souvenir> souvenirs = entry.getValue();
                    souvenirs.removeIf(s -> s.getId() == souvenir.getId());
                    return new AbstractMap.SimpleEntry<>(producerId, souvenirs);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Set<Souvenir> getAll() {
        return producerSouvenirJoiner.getAll().values()
                .stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Souvenir> getByProducerId(long producerId) {
        return producerSouvenirJoiner.getAll().get(producerId);
    }

    @Override
    public Set<Souvenir> getByCountry(String country){
        return getAll().stream()
                .filter(s -> country.equalsIgnoreCase(s.getProducer().getCountry()))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Souvenir> getByYear(int year){
        return getAll().stream()
                .filter(s -> year == s.getDate())
                .collect(Collectors.toSet());
    }

}
