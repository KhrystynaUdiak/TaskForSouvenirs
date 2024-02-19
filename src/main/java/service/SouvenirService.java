package service;

import model.Souvenir;

import java.util.List;
import java.util.Set;

public interface SouvenirService {
    Set<Souvenir> add(Souvenir souvenir);
    Souvenir readById(long id);
    Set<Souvenir> update(Souvenir souvenir);
    void delete(Souvenir souvenir);

    Set<Souvenir> getAll();
    Set<Souvenir> getByProducerId(long producerId);
    Set<Souvenir> getByYear(int year);
    Set<Souvenir> getByCountry(String country);

    void writeToFile();
    void readFromFile();
}
