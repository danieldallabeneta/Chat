package Interface;

/**
 *
 * @author danie
 */
public interface InterfaceDao {
    
    void createTable();
    boolean insert(Object modelo);
    boolean update(Object modelo);
    boolean delete(Object modelo);
    Object get(int codigo);
    
}
