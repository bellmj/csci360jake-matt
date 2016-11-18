package System;

import java.util.List;

/**
 * Created by matt on 11/15/16.
 */
public interface DataHandler <T>{
    void add(T t);
    List<T> getAll();
    T get(String id);
}
