/**
 * @author Yang
 */

public class Percolation{

	private boolean grid[] = null;	//open->true, else false
	private int gridSize = 0;
	private WeightedQuickUnionUF quickUnion = null;

	public Percolation(int n) {
		if (n<=0) {
			throw new IllegalArgumentException();
		}
		
		this.gridSize = n;
		quickUnion = new WeightedQuickUnionUF(n*n+2);	//add two virtual grids, top and bottom
		grid = new boolean[n*n+2];
		for (int i=1; i<=n*n; i++) {
			grid[i] = false;
		}
		grid[0] = true;
		grid[n*n+1] = true;
	}

	public void open(int i,int j){
		if (isOpen(i,j)) {
			return;
		}

		int index = getIndex(i,j);
		grid[index] = true;

		//build connections to its adjacent grids in four directions
		//up
		if (i-1>=1 && isOpen(i-1,j)) {
			quickUnion.union(index,getIndex(i-1,j));
		}
		//down
		if (i+1<=this.gridSize && isOpen(i+1,j)) {
			quickUnion.union(index,getIndex(i+1,j));
		}
		//left
		if (j-1>=1 && isOpen(i,j-1)) {
			quickUnion.union(index,getIndex(i,j-1));
		}
		//right
		if (j+1<=this.gridSize && isOpen(i,j+1)) {
			quickUnion.union(index,getIndex(i,j+1));
		}
		//connect to first virtual node
		if (i==1) {
			quickUnion.union(0,index);
		}
		//connect to last virtual node
		if (i==this.gridSize) {
			quickUnion.union(this.gridSize*this.gridSize+1,index);
		}

	}

	public boolean isOpen(int i,int j){
		int index = getIndex(i,j);
		return grid[index];
	}

	public boolean isFull(int i,int j){
		int index = getIndex(i,j);
		return isOpen(i,j)&&quickUnion.connected(0,index);
	}

	public boolean percolates(){
		return quickUnion.connected(0,this.gridSize*this.gridSize+1);
	}

	public int getIndex(int i,int j){
		//check boundary
		if (i<1|| i>this.gridSize || j<1 ||j>this.gridSize) {
			throw new IllegalArgumentException();
		}
		return (i-1)*this.gridSize+j;
	}

	/**
	* Test client
	*/
//	public static void main(String[] args) {
//
//	}
}