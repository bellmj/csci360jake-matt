package system.db;

import java.io.IOException;
import java.util.List;

/**
 * Interface for a class that handles some sort of data.
 *
 * @param <T>   the type of data being handled
 */
public interface DataHandler <T>{
    void add(T t);
    List<T> getAll();
    T get(String id) throws IOException;
}
