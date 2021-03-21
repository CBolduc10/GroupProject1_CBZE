package business.entities.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import business.entities.Member;
import business.facade.Result;

/**
 * This Iterator implementation is tailor-made to supply a "read-only" version
 * of Member objects. The user should supply an iterator to Member as the
 * parameter to the constructor.
 * 
 * @author Brahma Dathan
 *
 */
public class SafeMemberIterator implements Iterator<Result> {
    private Iterator<Member> iterator;
    private Result result = new Result();

    /**
     * The user of SafeIterator must supply an Iterator to Book.
     * 
     * @param iterator Iterator<Book>
     */
    public SafeMemberIterator(Iterator<Member> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Result next() {
        if (iterator.hasNext()) {
            result.setMemberFields(iterator.next());
        } else {
            throw new NoSuchElementException("No such element");
        }
        return result;
    }

}