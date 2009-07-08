package uk.ac.cam.cl.dtg.android.time.data.quadtree;

public class QuadTreeBounds {

	private int top;
	private int left;
	private int bottom;
	private int right;
	
	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getBottom() {
		return bottom;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}
	

	public void setBounds(int bottom, int left, int right, int top) {
		this.bottom = bottom;
		this.left = left;
		this.right = right;
		this.top = top;
	}

	public QuadTreeBounds(int bottom, int left, int right, int top) {
		super();
		this.bottom = bottom;
		this.left = left;
		this.right = right;
		this.top = top;
	}

	
	public QuadTreeBounds() {
		super();
	}
	
	public String toString() {
		return "[ (" + this.left+"/"+this.bottom+") -> ("+this.right+"/"+this.top+") ]";
	}
	

}
