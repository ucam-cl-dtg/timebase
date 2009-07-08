package uk.ac.cam.cl.dtg.android.time.data.quadtree;

public class QuadTree<E> {

	private QuadTreeNode<E> topNode;
	
	/**
	 * Creates a new QuadTree.
	 * 
	 * @param depth The depth of the tree
	 * @param treeBounds The area covered by the tree
	 */
	public QuadTree(int depth, QuadTreeBounds treeBounds) {
		topNode = new QuadTreeNode<E>(depth, treeBounds);
	}
	
}
