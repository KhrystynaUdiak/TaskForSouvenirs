package data_storage;

import model.Producer;
import model.Souvenir;

import java.io.*;
import java.util.*;

public class ProducerSouvenirJoiner {
  private static ProducerSouvenirJoiner productSouvenirJoiner;

  private final Map<Long, Set<Souvenir>> producersSouvenir;
  private ProducerSouvenirJoiner(){
      this.producersSouvenir = new HashMap<>();
  }

  public static ProducerSouvenirJoiner getInstance(){
      if(productSouvenirJoiner == null){
          productSouvenirJoiner = new ProducerSouvenirJoiner();
      }
      return productSouvenirJoiner;
  }

     public Map<Long, Set<Souvenir>> getAll(){
      return this.producersSouvenir;
    }

    public void writeToFile() {
        try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("src/main/resources/catalog.txt"))){
                output.writeObject(producersSouvenir);
        }catch (IOException e){}
    }

    public void readFromFile() {
        Map<Producer, Set<Souvenir>> map = new HashMap<>();
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream("src/main/resources/catalog.txt"))){
                map = (Map<Producer, Set<Souvenir>>) in.readObject();
                System.out.println(map);
        } catch(IOException | ClassNotFoundException e){}
    }

}
