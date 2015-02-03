/**
 * @author Yang
 */
import java.lang.IllegalArgumentException;

public class PercolationStats{
	private int expCount = 0;
	private double data[] = null;

	public PercolationStats(int N,int T){
		if (N<=0 || T<=0) {
			throw new IllegalArgumentException();
		}

		expCount = N;
		data = new double[T];
		for(int i=0; i<T; i++){
			Percolation p = new Percolation(N);
			int randR = -1;
			int randC = -1;
			int openNum = 0;
			while(!p.percolates()){
				do{
					randR = StdRandom.uniform(1,N+1);
					randC = StdRandom.uniform(1,N+1);
				} while (p.isOpen(randR,randC));
				p.open(randR,randC);
				openNum++;
			}
			data[i] = (double) openNum/(N*N);
		}
	}

	public double mean() {
		return StdStats.mean(data);
	}

	public double stddev() {
		if (expCount == 1) {
			return Double.NaN;
		}
		return StdStats.stddev(data);
	}

	public double confidenceLo() {
		return mean()-((float)1.96*stddev()/Math.sqrt(expCount));
	}

	public double confidenceHi() {
		return mean()+((float)1.96*stddev()/Math.sqrt(expCount));
	}

	public static void main(String[] args) {
		int N = StdIn.readInt();
		int T = StdIn.readInt();
//		int N = 2;
//		int T = 100000;
		PercolationStats ps = new PercolationStats(N,T);
		System.out.println("mean = "+ps.mean());
		System.out.println("stddev = "+ps.stddev());
		System.out.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
	}
}