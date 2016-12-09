package system.db;

import system.election.CryptoException;

import java.io.IOException;
import java.util.List;

/**
 * Created by matt on 11/15/16.
 */
public interface DataHandler <T>{
    void add(T t);
    List<T> getAll();
    T get(String id) throws IOException;
}
