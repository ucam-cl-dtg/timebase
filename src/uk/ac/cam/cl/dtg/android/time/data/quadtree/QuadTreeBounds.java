package uk.ac.cam.cl.dtg.android.time.data.quadtree;

/**
 * Specifies an axis-aligned rectangular area for use with QuadTree and related classes in this package.
 * 
 * Note that the co-ordinate system is assumed to run with X increasing left-to-right and Y
 * increasing bottom-to-top.
 * 
 * @author dt316
 *
 */
public class QuadTreeBounds {

	private int top;
	private int left;
	private int bottom;
	private int right;
	
	/**
	 * Constructs a new QuadTreeBounds with the specified bottom-left and top-right co-ordinates.
     * Note that the co-ordinate system is assumed to run with X increasing left-to-right and Y
     * increasing bottom-to-top.
	 * @param bottom Y-value of bottom edge
	 * @param left X-value of left edge
	 * @param right X-value of right edge
	 * @param top Y-value of bottom edge
	 */
	public QuadTreeBounds(int bottom, int left, int right, int top) {
		super();
		this.bottom = bottom;
		this.left = left;
		this.right = right;
		this.top = top;
	}
	
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
	
	public int getWidth() {
		return this.right - this.left;
	}
	
	public int getHeight() {
		return this.top - this.bottom;
	}

	/**
	 * Set this QuadTreeBounds to a different rectangular area.
	 * @param bottom Y-value of new bottom edge
	 * @param left X-value of new left edge
	 * @param right X-value of new right edge
	 * @param top Y-value of new top edge
	 */
	public void setBounds(int bottom, int left, int right, int top) {
		this.bottom = bottom;
		this.left = left;
		this.right = right;
		this.top = top;
	}

	/**
	 * Returns true if the point (x,y) is contained within this QuadTreeBounds.
	 * 
	 * @param x X-value of point to check
	 * @param y Y-value of point to check
	 * @return
	 */
	public boolean contains(int x, int y) 	{
		return (x >= this.left) && (x <= this.right) && (y >= this.bottom) && (y <= this.top);
	}
	
	/**
	 * Returns true if this QuadTreeBounds wholly encloses the QuadTreeBounds object passed.
	 * @param rect Bounds to check for containment.
	 * @return
	 */
	public boolean contains(QuadTreeBounds rect) {
		
		// So long as none of these hold true, then this Bounds fully contains rect
		if(rect.left < left) return false;
		if(rect.right > right) return false;
		if(rect.bottom < bottom) return false;
		if(rect.top > top) return false;
		
		return true;
		
	}
	
	/**
	 * Returns true if the QuadTreeBounds passed overlaps with this QuadTreeBounds.
	 * (Strictly, if any corner of QuadTreeBounds object passed is contained within this QuadTreeBounds,
	 * or if any corner of this QuadTreeBounds is contained within the QuadTreeBounds object passed)
	 * @param rect QuadTreeBounds to check for overlap with.
	 * @return
	 */
	public boolean overlaps(QuadTreeBounds rect) {
		
		// For overlap to occur, at least 1 corner of rect is inside our bounds		
		if(contains(rect.left, rect.bottom)) return true; // bottom left
		if(contains(rect.left, rect.top)) return true; // top left
		if(contains(rect.right, rect.bottom)) return true; // bottom right
		if(contains(rect.right, rect.top)) return true; // top right
		
		// OR at least 1 of our corners is inside rect
		if(rect.contains(left, bottom)) return true; // bottom left
		if(rect.contains(left, top)) return true; // top left
		if(rect.contains(right, bottom)) return true; // bottom right
		if(rect.contains(right, top)) return true; // top right
		
		// Otherwise there is no overlap
		return false;
		
	}
	
	public QuadTreeBounds() {
		super();
	}
	
	public String toString() {
		return "[ (" + this.left+"/"+this.bottom+") -> ("+this.right+"/"+this.top+") ]";
	}
	

}
