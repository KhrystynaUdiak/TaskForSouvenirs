package service.implementation;

import data_storage.ProducerSouvenirJoiner;
import model.Producer;
import model.Souvenir;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ProducerServiceImplTest {
     ProducerSouvenirJoiner producerSouvenirJoiner;
     ProducerServiceImpl producerService;
     SouvenirServiceImpl souvenirService;

    @BeforeEach
    public void setUp() {
        producerSouvenirJoiner = ProducerSouvenirJoiner.getInstance();
        producerService = ProducerServiceImpl.getInstance(producerSouvenirJoiner);
        souvenirService = SouvenirServiceImpl.getInstance(producerSouvenirJoiner);
    }

    @Test
    public void testAddProducer() {
        Map<Long, Set<Souvenir>> expected = new HashMap<>();
        Producer producer = new Producer(1, "PKO", "Poland");
        expected.put(producer.getId(), new HashSet<>());
        producerService.addProducer(producer);
        Assertions.assertEquals(expected, producerSouvenirJoiner.getAll(), "method addProducer is broken");
        producerSouvenirJoiner.getAll().clear();
    }

    @Test
    public void testReadById() {
        Map<Long, Set<Souvenir>> map = new HashMap<>();

        Producer producer = new Producer(1, "PKO", "Poland");
        Producer producer1 = new Producer(2, "View", "Germany");
        Souvenir souvenir = Souvenir.builder()
                .id(1)
                .name("cup")
                .producer(producer)
                .date(2012)
                .price(39.45)
                .build();

        Souvenir souvenir1 = Souvenir.builder()
                .id(2)
                .name("mirror")
                .producer(producer1)
                .date(2012)
                .price(550.00)
                .build();

        map.put(producer.getId(), Set.of(souvenir));
        map.put(producer1.getId(), Set.of(souvenir1));
        souvenirService.addSouvenir(souvenir);
        souvenirService.addSouvenir(souvenir1);

        Producer expected = producer;
        Producer actual = producerService.readById(1);
        Assertions.assertEquals(expected, actual, "method readById is broken");
        producerSouvenirJoiner.getAll().clear();

    }

    @Test
    public void testUpdate() {
        Map<Long, Set<Souvenir>> expected = new HashMap<>();

        Producer producer = new Producer(1, "PKO", "Poland");
        Producer producer1 = new Producer(2, "View", "Germany");
        Souvenir souvenir = Souvenir.builder()
                .id(1)
                .name("cup")
                .producer(producer)
                .date(2012)
                .price(39.45)
                .build();

        Souvenir souvenir1 = Souvenir.builder()
                .id(2)
                .name("mirror")
                .producer(producer1)
                .date(2012)
                .price(550.00)
                .build();

        expected.put(producer.getId(), Set.of(souvenir));
        expected.put(producer1.getId(), Set.of(souvenir1));
        souvenirService.addSouvenir(souvenir);
        souvenirService.addSouvenir(souvenir1);
        producer.setCountry("Ukraine");
        producerService.update(producer);
        Assertions.assertEquals(expected, producerSouvenirJoiner.getAll(), "method update is broken");
        producerSouvenirJoiner.getAll().clear();
    }

    @Test
    public void testDelete() {
        Map<Long, Set<Souvenir>> expected = new HashMap<>();

        Producer producer = new Producer(1, "PKO", "Poland");
        Producer producer1 = new Producer(2, "View", "Germany");
        Souvenir souvenir = Souvenir.builder()
                .id(1)
                .name("cup")
                .producer(producer)
                .date(2012)
                .price(39.45)
                .build();

        Souvenir souvenir1 = Souvenir.builder()
                .id(2)
                .name("mirror")
                .producer(producer1)
                .date(2012)
                .price(550.00)
                .build();

        expected.put(producer.getId(), Set.of(souvenir));
        expected.put(producer1.getId(), Set.of(souvenir1));
        souvenirService.addSouvenir(souvenir);
        souvenirService.addSouvenir(souvenir1);
        expected.remove(producer1.getId());
        producerService.delete(producer1);
        Assertions.assertEquals(expected, producerSouvenirJoiner.getAll(), "method delete is broken");
        producerSouvenirJoiner.getAll().clear();
    }

    @Test
    public void testGetAll() {
        Producer producer = new Producer(1, "PKO", "Poland");
        Producer producer1 = new Producer(2, "View", "Germany");
        Souvenir souvenir = Souvenir.builder()
                .id(1)
                .name("cup")
                .producer(producer)
                .date(2012)
                .price(39.45)
                .build();

        Souvenir souvenir1 = Souvenir.builder()
                .id(2)
                .name("mirror")
                .producer(producer1)
                .date(2012)
                .price(550.00)
                .build();
        souvenirService.addSouvenir(souvenir);
        souvenirService.addSouvenir(souvenir1);
        List<Producer> expected = List.of(producer, producer1);
        Assertions.assertEquals(expected, producerService.getAll(), "method getAll is broken");
        producerSouvenirJoiner.getAll().clear();
    }

    @Test
    public void testGetCheapestProducers() {
        Producer producer = new Producer(1, "PKO", "Poland");
        Producer producer1 = new Producer(2, "View", "Germany");
        Souvenir souvenir = Souvenir.builder()
                .id(1)
                .name("cup")
                .producer(producer)
                .date(2012)
                .price(390.45)
                .build();

        Souvenir souvenir1 = Souvenir.builder()
                .id(2)
                .name("mirror")
                .producer(producer1)
                .date(2012)
                .price(550.00)
                .build();
        souvenirService.addSouvenir(souvenir);
        souvenirService.addSouvenir(souvenir1);

        List<Producer> expected = List.of(producer, producer1);
        List<Producer> actual = producerService.getCheapestProducers(600);
        Assertions.assertEquals(expected, actual, "method getCheapestProducers is broken");
        producerSouvenirJoiner.getAll().clear();
    }

    @Test
    public void testGetProducersOfSouvenirsByYear() {
        Producer producer = new Producer(1, "PKO", "Poland");
        Producer producer1 = new Producer(2, "View", "Germany");
        Souvenir souvenir = Souvenir.builder()
                .id(1)
                .name("cup")
                .producer(producer)
                .date(2012)
                .price(390.45)
                .build();

        Souvenir souvenir1 = Souvenir.builder()
                .id(2)
                .name("mirror")
                .producer(producer1)
                .date(2012)
                .price(550.00)
                .build();
        souvenirService.addSouvenir(souvenir);
        souvenirService.addSouvenir(souvenir1);

        List<Producer> expected = List.of( producer1);
        List<Producer> actual = producerService.getProducersOfSouvenirsByYear("mirror", 2012);
        Assertions.assertEquals(expected, actual, "method getCheapestProducers is broken");
        producerSouvenirJoiner.getAll().clear();
    }
}