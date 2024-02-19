package service;

import model.Producer;
import model.Souvenir;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProducerService {
    List<Producer> add(Producer producer);
    Producer readById(long id);
    List<Producer> update(Producer souvenir);
    boolean delete(long id);
    List<Producer> getAll();
    void writeToFile();
    void readFromFile();

}
