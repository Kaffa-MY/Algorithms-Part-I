import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
	// construct an empty randomized queue
	private Item[] queue;
	private int size = 0;

	@SuppressWarnings("unchecked")
	public RandomizedQueue() {
		queue = (Item[]) new Object[1];
		size = 0;
	}

	// is the queue empty?
	public boolean isEmpty() {
		return size == 0;
	}

	// resize the array
	@SuppressWarnings("unchecked")
	private void resize(int capacity) {
		Item[] copy = (Item[]) new Object[capacity];
		for (int i = 0; i < size; i++)
			copy[i] = queue[i];
		queue = copy;
	}

	// return the number of items on the queue
	public int size() {
		return size;
	}

	// add the item
	public void enqueue(Item item) {
		if (item == null)
			throw new NullPointerException();
		if (size == queue.length)
			resize(queue.length * 2);
		queue[size++] = item;
	}

	// remove and return a random item
	public Item dequeue() {
		if (size == 0)
			throw new NoSuchElementException();
		int position = StdRandom.uniform(size);
		Item item = queue[position];
		queue[position] = queue[--size];
		queue[size] = null;
		if (size > 0 && size == queue.length / 4)
			resize(queue.length / 2);
		return item;
	}

	// return (but do not remove) a random item
	public Item sample() {
		if (size == 0)
			throw new NoSuchElementException();
		int position = StdRandom.uniform(size);
		return queue[position];
	}

	// return an independent iterator over items in random order
	public Iterator<Item> iterator() {
		return new RandomizedQueueInterator();
	}

	// Iterator
	private class RandomizedQueueInterator implements Iterator<Item> {
		private int n = size;
		private Item[] shuffle;

		@SuppressWarnings("unchecked")
		public RandomizedQueueInterator() {
			if (n != 0) {
				shuffle = (Item[]) new Object[size];
				System.arraycopy(queue, 0, shuffle, 0, size);
				StdRandom.shuffle(shuffle, 0, size - 1);
			}
		}

		public boolean hasNext() {
			return n > 0;
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}

		public Item next() {
			if (n == 0)
				throw new NoSuchElementException();
			return shuffle[--n];
		}
	}
}