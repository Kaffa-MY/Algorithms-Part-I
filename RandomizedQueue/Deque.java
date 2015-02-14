import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
	private Node first, last;
	private int size = 0;

	private class Node {
		private Item item;
		private Node next;

		public Item getItem() {
			return item;
		}

		public void setItem(Item item) {
			this.item = item;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node next) {
			this.next = next;
		}

	}

	private class ListIterator implements Iterator<Item> {
		private Node cur = first;

		public boolean hasNext() {
			return cur != null;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (cur == null) {
				throw new NoSuchElementException();
			}
			
			Item item = cur.getItem();
			cur = cur.getNext();
			return item;
		}
	}

	public Deque() {
	}

	public boolean isEmpty() {
		return first == null;
	}

	public int size() {
		return size;
	}

	public void addFirst(Item item) {
		if (item == null)
			throw new NullPointerException();

		if (isEmpty()) {
			first = last = new Node();
			first.setItem(item);
			first.setNext(null);
		} else {
			Node oldfirst = first;
			first = new Node();
			first.setItem(item);
			first.setNext(oldfirst);
		}
		size++;
	}

	public void addLast(Item item) {
		if (item == null)
			throw new NullPointerException();

		if (isEmpty()) {
			last = first = new Node();
			last.setItem(item);
			last.setNext(null);
		} else {
			Node oldlast = last;
			last = new Node();
			last.setItem(item);
			last.setNext(null);
			oldlast.setNext(last);
		}
		size++;
	}

	public Item removeFirst() {
		if (isEmpty())
			throw new NoSuchElementException();

		Item item = first.getItem();
		if (first == last)
			first = last = null;
		else
			first = first.getNext();
		size--;
		return item;
	}

	public Item removeLast() {
		if (isEmpty())
			throw new NoSuchElementException();

		Item item = last.getItem();

		if (first == last) {
			first = last = null;
		} else {
			last = first;
			for (int i = 0; i < size - 2; i++)
				last = last.getNext();
			last.setNext(null);
		}

		size--;
		return item;
	}

	public Iterator<Item> iterator() {
		return new ListIterator();
	}
}