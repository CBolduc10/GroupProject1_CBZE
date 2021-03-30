package business.entities;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class ItemList<T extends Matchable<K>, K>
		implements Serializable {
	private List<T> entities = new LinkedList<T>();

	/**
	 * Checks whether a member with a given member id exists.
	 * 
	 * @param memberId the id of the member
	 * @return true iff member exists
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
	 * Inserts a member into the collection
	 * 
	 * @param member the member to be inserted
	 * @return true iff the member could be inserted. Currently always true
	 */
	public boolean insert(T entity) {
		entities.add(entity);
		return true;
	}

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
	 * Method to delete a member from the collection.
	 * 
	 * @param memberId member ID of member to be removed
	 * @return removed member
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
