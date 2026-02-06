package repositories.interfaces;

import java.util.List;

public interface Repository<T> {
    void save(T entity);
    T findById(int id);
    List<T> findAll();
}
