package service.implementation;
import data_storage.ProducerSouvenirJoiner;
import model.Producer;
import model.Souvenir;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


class SouvenirServiceImplTest {
    ProducerSouvenirJoiner producerSouvenirJoiner;
    SouvenirServiceImpl souvenirService;

    @BeforeEach
    public void setUp() {
        producerSouvenirJoiner = ProducerSouvenirJoiner.getInstance();
        souvenirService = SouvenirServiceImpl.getInstance(producerSouvenirJoiner);

    }

    @Test
    public void testAddSouvenir() {
        Map<Long, Set<Souvenir>> expected = new HashMap<>();
        Set<Souvenir> souvenirs = new HashSet<>();
        Producer producer = new Producer(1, "PKO", "Poland");
        Souvenir souvenir = Souvenir.builder()
                .id(1)
                .name("cup")
                .producer(producer)
                .date(2012)
                .price(39.45)
                .build();
        souvenirs.add(souvenir);
        expected.put(producer.getId(), souvenirs);

        souvenirService.addSouvenir(souvenir);
        Assertions.assertEquals(expected, producerSouvenirJoiner.getAll(), "method addSouvenir is broken");
        producerSouvenirJoiner.getAll().clear();
    }



    @Test
    public void testReadById() {
        Map<Long, Set<Souvenir>> map = new HashMap<>();
        Set<Souvenir> souvenirs = new HashSet<>();

        Producer producer = new Producer(1, "PKO", "Poland");
        Souvenir souvenir = Souvenir.builder()
                .id(1)
                .name("cup")
                .producer(producer)
                .date(2012)
                .price(39.45)
                .build();
        souvenirs.add(souvenir);

        Souvenir souvenir1 = Souvenir.builder()
                .id(2)
                .name("mirror")
                .producer(producer)
                .date(2012)
                .price(550.00)
                .build();
        souvenirs.add(souvenir1);

        map.put(producer.getId(), souvenirs);
        souvenirService.addSouvenir(souvenir);
        souvenirService.addSouvenir(souvenir1);

        Souvenir expected = souvenir;
        Souvenir actual = souvenirService.readById(1);

        Assertions.assertEquals(expected, actual, "method readById is broken");
        producerSouvenirJoiner.getAll().clear();
    }

    @Test
    public void testUpdate() {
        Map<Long, Set<Souvenir>> expected = new HashMap<>();
        Set<Souvenir> souvenirs = new HashSet<>();
        Producer producer = new Producer(2, "View", "Germany");
        Souvenir souvenir = Souvenir.builder()
                .id(2)
                .name("mirror")
                .producer(producer)
                .date(2012)
                .price(550.00)
                .build();
        souvenirs.add(souvenir);
        expected.put(producer.getId(), souvenirs);

        souvenirService.addSouvenir(souvenir);
        souvenir.setPrice(630.00);
        souvenirService.update(souvenir);

        Assertions.assertEquals(expected, producerSouvenirJoiner.getAll(), "method updateSouvenir is broken");
        producerSouvenirJoiner.getAll().clear();
    }

    @Test
    public void testDelete() {
        Map<Long, Set<Souvenir>> expected = new HashMap<>();
        Set<Souvenir> souvenirs = new HashSet<>();

        Producer producer = new Producer(1, "PKO", "Poland");
        Souvenir souvenir = Souvenir.builder()
                .id(1)
                .name("cup")
                .producer(producer)
                .date(2012)
                .price(39.45)
                .build();
        souvenirs.add(souvenir);

        Souvenir souvenir1 = Souvenir.builder()
                .id(2)
                .name("mirror")
                .producer(producer)
                .date(2012)
                .price(550.00)
                .build();
        souvenirs.add(souvenir1);

        souvenirService.addSouvenir(souvenir);
        souvenirService.addSouvenir(souvenir1);

        expected.put(producer.getId(), souvenirs);
        expected.get(producer.getId()).remove(souvenir1);

        souvenirService.delete(souvenir1);

        Assertions.assertEquals(expected, producerSouvenirJoiner.getAll(), "method deleteSouvenir is broken");
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

       Set<Souvenir> expected = Set.of(souvenir, souvenir1);

        souvenirService.addSouvenir(souvenir);
        souvenirService.addSouvenir(souvenir1);

        Assertions.assertEquals(expected, souvenirService.getAll(), "method getAllSouvenir is broken");
        producerSouvenirJoiner.getAll().clear();
    }

    @Test
    public void testGetByProducerId() {
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
                .producer(producer)
                .date(2012)
                .price(550.00)
                .build();

        Souvenir souvenir2 = Souvenir.builder()
                .id(3)
                .name("magnet")
                .producer(producer1)
                .date(2023)
                .price(20.00)
                .build();
        Set<Souvenir> expected = Set.of(souvenir, souvenir1);

        souvenirService.addSouvenir(souvenir);
        souvenirService.addSouvenir(souvenir1);
        souvenirService.addSouvenir(souvenir2);

        Set<Souvenir> actual = souvenirService.getByProducerId(1);
        Assertions.assertEquals(expected, actual, "method getByProducerId is broken");
        producerSouvenirJoiner.getAll().clear();
    }

    @Test
    void testGetByCountry() {
        Producer producer = new Producer(1, "PKO", "Ukraine");
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
                .producer(producer)
                .date(2012)
                .price(550.00)
                .build();

        Souvenir souvenir2 = Souvenir.builder()
                .id(3)
                .name("magnet")
                .producer(producer1)
                .date(2023)
                .price(20.00)
                .build();
        Set<Souvenir> expected = Set.of(souvenir, souvenir1);

        souvenirService.addSouvenir(souvenir);
        souvenirService.addSouvenir(souvenir1);
        souvenirService.addSouvenir(souvenir2);

        Set<Souvenir> actual = souvenirService.getByCountry("Ukraine");

        Assertions.assertEquals(expected, actual, "method getByCountry is broken");
        producerSouvenirJoiner.getAll().clear();
    }

    @Test
    void testGetByYear() {
        Producer producer = new Producer(1, "PKO", "Ukraine");
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
                .producer(producer)
                .date(2014)
                .price(550.00)
                .build();

        Set<Souvenir> expected = Set.of(souvenir);

        souvenirService.addSouvenir(souvenir);
        souvenirService.addSouvenir(souvenir1);

        Set<Souvenir> actual = souvenirService.getByYear(2012);
        Assertions.assertEquals(expected, actual, "method getByYear is broken");
        producerSouvenirJoiner.getAll().clear();
    }
}