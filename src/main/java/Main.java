import model.Producer;
import model.Souvenir;
import service.implementation.ProducerServiceImpl;
import data_storage.ProducerSouvenirJoiner;
import service.implementation.SouvenirServiceImpl;

public class Main {
    public static void main(String[] args) {
        Producer pko = new Producer(1, "PKO", "Poland");
        Producer view = new Producer(2, "View", "Germany");
        Producer traveller = new Producer(3, "CityTravel", "Ukraine");
        Producer carGo = new Producer(4, "CarGo", "Ukraine");

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

        ProducerSouvenirJoiner joiner = ProducerSouvenirJoiner.getInstance();

        SouvenirServiceImpl souvenirService = SouvenirServiceImpl.getInstance(joiner);
        souvenirService.addSouvenir(Souvenir.builder().id(4).name("bag").producer(pko).date(2023).price(150.50).build());
        souvenirService.addSouvenir(cup);
        souvenirService.addSouvenir(magnet);
        souvenirService.addSouvenir(mirror);

        souvenirService.update(Souvenir.builder()
                .id(1)
                .name("greenCup")
                .producer(pko)
                .date(2014)
                .price(39.45)
                .build());


        System.out.println("id : " + souvenirService.readById(1));
        souvenirService.delete(cup);
        System.out.println(souvenirService.getAll());
        System.out.println(souvenirService.getByProducerId(1));
        System.out.println(souvenirService.getByCountry("Poland"));
        System.out.println(souvenirService.getByYear(2012));

        ProducerServiceImpl producerService = ProducerServiceImpl.getInstance(joiner);
        producerService.addProducer(carGo);
        souvenirService.addSouvenir(Souvenir.builder().id(5).name("toy_car").producer(carGo).price(178.00).build());
        producerService.update(new Producer(4, "CarRent", "Ukraine"));
        producerService.delete(carGo);
        System.out.println(producerService.getAll());
        System.out.println(producerService.readById(2));
        System.out.println(producerService.getCheapestProducers(153));
        System.out.println(producerService.getProducersOfSouvenirsByYear("magnet", 2023));

        System.out.println(joiner.getAll());
        joiner.writeToFile();
        joiner.readFromFile();
    }
}
