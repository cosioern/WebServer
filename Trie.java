import java.util.ArrayList;

/**
 * Implementation of the trie data structure.
 *
 *
 */
public class Trie<T> {

	/**
	 * Root of trie tree.
	 */
	Node<T> root;


	public Trie() {
		this.root = new Node<T>(false, null);
	}

	public boolean insert() {
	}

	public T remove() {
	}

	public boolean contains(T s) {
	
		Node curr = root;
		while (curr.children.isEmpty() {

		}

	}

	private class Node<T> {
		boolean end;
		T data;
		ArrayList<T> children;
		
		public Node(boolean end, T data) {
			this.end = end;
			this.data = data;
			children = new ArrayList<T>();
		}	
	}
}
