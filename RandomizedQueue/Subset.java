public class Subset {
	public static void main(String[] args) {
		RandomizedQueue<String> queue = new RandomizedQueue<String>();
		int N = 0;
		int k = Integer.decode(args[0]);
		String str;

		if (k < 0)
			throw new IllegalArgumentException();
		while (!StdIn.isEmpty()) {
			str = StdIn.readString();
			queue.enqueue(str);
			N++;
		}

		if (k > N)
			throw new IllegalArgumentException();

		for (int i = 0; i < k; i++)
			System.out.println(queue.dequeue());
	}
}