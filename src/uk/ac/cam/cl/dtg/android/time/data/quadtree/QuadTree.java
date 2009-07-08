package uk.ac.cam.cl.dtg.android.time.data.quadtree;

import java.util.Set;

/**
 * Stores items indexed by 2D position.
 * 
 * When given an arbitrary rectangle will return a set of items that reside inside or
 * close to the rectangle, as well as automatically adjusting the number of results returned
 * to compensate for the scale of the rectangle.
 * @author dt316
 *
 * @param <E>
 */
public class QuadTree<E> {

	// The top level of the tree is represented by a single node
	private QuadTreeNode<E> topNode;
	
	// The proportion of a child's nodes which are visible one level up
	private float DetailDecayFactor = 0.5f;
	
	/**
	 * Creates a new QuadTree.
	 * 
	 * @param depth The depth of the tree
	 * @param treeBounds The area covered by the tree
	 */
	public QuadTree(int depth, QuadTreeBounds treeBounds) {
		topNode = new QuadTreeNode<E>(depth, treeBounds);
		topNode.setDetailDecayFactor(DetailDecayFactor);
	}
	
	 /**
     * Inserts an item into the QuadTree.
     * 
     * @param item The item to be inserted
     * @param x The X co-ordinate of the item to be inserted
     * @param y The Y co-ordinate of the item to be inserted
     */
    public void insertItem(E item, int x, int y) throws QuadTreeException
    {
    	topNode.insertItem(item, x, y);
    }

    /**
     * Gets the detail decay factor for this tree. The detail decay factor dictates what
     * proportion of child nodes are visible one level of detail up the tree.
     * 
     * For example, a factor of 0.5 means that a certain level in the tree contains
     * half as many nodes as the next level down.
     * 
     * @return Current detail decay factor
     */
	public float getDetailDecayFactor() {
		return DetailDecayFactor;
	}
	
	/**
  	 * Sets the detail decay factor for this tree. The detail decay factor dictates what
     * proportion of child nodes are visible one level of detail up the tree.
     * 
     * For example, a factor of 0.5 means that a certain level in the tree contains
     * half as many nodes as the next level down.
     * 
     * @param detailDecayFactor The new detail decay factor
     */
	
	public void setDetailDecayFactor(float detailDecayFactor) {
		DetailDecayFactor = detailDecayFactor;
		topNode.setDetailDecayFactor(detailDecayFactor);
	}
	
	/**
	 * Calculates which items are visible at which detail levels. Must be called after
	 * new items have been inserted into the tree.
	 * 
	 */
	public void calculateDetailLevels() {
		topNode.propagateItems();
	}
	

	/**
	 * Returns a set of items contained in the tree which are either either:
	 * <ul><li>within the rectangle specified</li>
	 * <li>near the rectangle specified</li></ul>
	 * 
	 * The number of items returned is scaled depending on the relative size of the
	 * rectangle passed - the smaller the rectangle, the more point that will be returned.
	 * 
	 * @param rect Rectangle bounding the area you wish to get points for
	 * @return
	 */
	public Set<E> getItems(QuadTreeBounds rect) {
		return topNode.getItems(rect);
	}
	
	public String toString() {
		return topNode.toString();
	}
}
