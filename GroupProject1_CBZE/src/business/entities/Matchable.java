package business.entities;

/**
 * An interface utilizing generics that ensures the implementing classes match
 * correctly according to a chosen/unique identifier.
 * 
 * @author Zachary Boling-Green, Brian Le, Ethan Nunn and Colin Bolduc
 * @param <K>
 */
public interface Matchable<K> {
	public boolean matches(K other);
}
