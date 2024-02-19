import model.Producer;
import model.Souvenir;
import service.implementation.ProducerServiceImpl;
import service.implementation.ProductSouvenirJoiner;
import service.implementation.SouvenirServiceImpl;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Producer pko = new Producer(1, "PKO", "Poland");
        Producer view = new Producer(2, "View", "Germany");
        Producer traveller = new Producer(3, "CityTravel", "Ukraine");

        List<Producer> producers = new ArrayList<>();
        producers.add(pko);
        producers.add(view);
        producers.add(traveller);

       Souvenir cup = Souvenir.builder()
                .id(1)
                .name("cup")
                .producer(pko)
                .date(2012)
                .price(39.45)
                .build();

       Souvenir mirror = Souvenir.builder()
                .id(2)
                .name("mirror")
                .producer(view)
                .date(2012)
                .price(550.00)
                .build();

       Souvenir magnet = Souvenir.builder()
               .id(3)
               .name("magnet")
               .producer(traveller)
               .date(2023)
               .price(20.00)
               .build();

       Set<Souvenir> souvenirs = new HashSet<>();
       souvenirs.add(cup);
       souvenirs.add(magnet);
       souvenirs.add(mirror);

        SouvenirServiceImpl souvenirService = SouvenirServiceImpl.getInstance(souvenirs);

        souvenirService.update(Souvenir.builder()
                .id(1)
                .name("greenCup")
                .producer(pko)
                .date(2014)
                .price(39.45)
                .build());
        souvenirService.add(Souvenir.builder().build());

  //      System.out.println(souvenirService.getAll());
       // souvenirService.delete(1);
 //       System.out.println(souvenirService.readById(1));
 //       System.out.println(souvenirService.getByProducerId(3));
 //       System.out.println(souvenirService.getByCountry("Ukraine"));
 //       System.out.println(souvenirService.getByYear(2012));
        //       souvenirService.writeToFile();
        //      souvenirService.readFromFile();

        ProducerServiceImpl producerService = ProducerServiceImpl.getInstance(producers);
        producerService.add(new Producer(4, "CarGo", "Ukraine"));
        producerService.update(new Producer(4, "CarRent", "Ukraine"));
  //      producerService.delete(4);
 //       System.out.println(producerService.getAll());
 //       System.out.println(producerService.readById(5));
  //      producerService.writeToFile();
  //      producerService.readFromFile();

        ProductSouvenirJoiner  joiner = ProductSouvenirJoiner.getInstance(new HashMap<>());
        joiner.addSouvenir(pko, cup);
        joiner.addSouvenir(traveller, magnet);
        joiner.addSouvenir(view, mirror);
  //      System.out.println(joiner.getSouvenirsByProducer(pko));
  //      System.out.println(joiner.getAll());
   //     System.out.println(joiner.getCheapestProducers(35));
       // System.out.println(joiner.getProducersOfSouvenirsByYear(magnet, 2023));
        joiner.writeToFile();
        joiner.readFromFile();
    }
}
