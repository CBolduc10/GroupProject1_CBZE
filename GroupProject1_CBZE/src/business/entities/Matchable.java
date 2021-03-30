package business.entities;

public interface Matchable<K> {
	public boolean matches(K other);
}
