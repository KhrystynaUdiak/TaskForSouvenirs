package service;

import model.Producer;
import model.Souvenir;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProducerService {
    void addProducer(Producer producer);
    Producer readById(long id);
    void update(Producer producer);
    void delete(Producer producer);
    List<Producer> getAll();
    List<Producer> getCheapestProducers(double price);
    List<Producer> getProducersOfSouvenirsByYear(String name, int year);

}
