package uk.ac.cam.cl.dtg.android.time.data.quadtree;

/**
 * Represents an item held in the QuadTree
 * 
 * @author dt316
 *
 */
public class QuadTreeItem<E> implements Comparable<QuadTreeItem<E>> {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((element == null) ? 0 : element.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	
	//@Override
//	public boolean equals(Object obj) {
		
//		return false;
//	}
	/*
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuadTreeItem other = (QuadTreeItem) obj;
		if (element == null) {
			if (other.element != null)
				return false;
		} else if (!element.equals(other.element))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}*/

	private E element;
	private int x, y;
	
	public QuadTreeItem(E ele, int x, int y) {
		
		element = ele;
		
		this.x = x;
		this.y = y;
		
	}

	public E getElement() {
		return element;
	}

	public void setElement(E element) {
		this.element = element;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public String toString() {
		return element+" at ("+x+", "+y+")";
	}

	@Override
	public int compareTo(QuadTreeItem<E> arg0) {
		return (this.hashCode() > arg0.hashCode()) ? -1 : 0;
	}
	
	
}
