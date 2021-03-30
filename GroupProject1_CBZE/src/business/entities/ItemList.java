package business.entities;

/**
 * 
 * @author Zachary Boling-Green, Brian Le, Ethan Nunn and Colin Bolduc
 */
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * An abstract class employing generics with regards to Matchable. Allows
 * sub-collections to extend its generic methods and respective class-bound
 * matching.
 * 
 * @param <T>
 * @param <K>
 */
public abstract class ItemList<T extends Matchable<K>, K>
		implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<T> entities = new LinkedList<T>();

	/**
	 * Checks whether an entity with a given identifier exists.
	 * 
	 * @param id the identifier of the entity
	 * @return true iff entity exists
	 * 
	 */
	public T search(K id) {
		for (Iterator<T> iterator = entities.iterator(); iterator.hasNext();) {
			T entity = iterator.next();
			if (entity.matches(id)) {
				return entity;
			}
		}
		return null;
	}

	/**
	 * Inserts an entity into the collection
	 * 
	 * @param entity the entity to be inserted
	 * @return true iff the entity could be inserted. Currently always true
	 */
	public boolean insert(T entity) {
		entities.add(entity);
		return true;
	}

	/**
	 * Returns an iterator for the collection.
	 * 
	 * @return collection iterator
	 */
	public Iterator<T> iterator() {
		return entities.iterator();
	}

	/**
	 * String form of the collection
	 * 
	 */
	@Override
	public String toString() {
		return entities.toString();
	}

	/**
	 * Method to delete a entity from the collection.
	 * 
	 * @param id entity identifier of entity to be removed
	 * @return removed entity
	 */
	public boolean remove(K id) {
		T entity = search(id);
		if (entity == null) {
			return false;
		} else {
			return entities.remove(entity);
		}
	}
}
