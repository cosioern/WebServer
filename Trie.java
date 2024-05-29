import java.util.ArrayList;

/**
 * Implementation of the trie data structure.
 *
 */
public class Trie {

  /**
   * Root of trie tree.
   */
  Node root;

  /**
   * Constructor for trie tree. Root node always has a null parent, a null char for data, and is 
   * never an end-of-word.
   */
  public Trie() {
    this.root = new Node(false, '\0', null);
  }

  /**
   * Inserts a String s into the tree by following branches with matching chars, or if matching chars
   * are not found or word diverges from a branch, creates new branches to accomodate the word.
   * Each diverging char receives its own node before insertion. No node has duplicate children 
   * (nodes with matching chars).
   * 
   * @param s is the String to add to the trie tree
   * @return true if String s is correctly inserted, false otherwise
   */
  public boolean insert(String s) {
    
    // no duplicates
    if (contains(s))
      return false;

    // track Node, matches, and position in string
    int i = 0;
    Node curr = root;
    Node child = null;
    boolean match = false;
    
    while (i < s.length()) {
      
      // search for match
      for (Node n : curr.children) {
        if (s.charAt(i) == n.data) { // match found
          match = true;
          child = n;
          i++;
          break;
        }
      }
      
      // no match, add child
      if (!match) {
        child = new Node(false, s.charAt(i), curr);
        curr.children.add(child);
        i++;
      }
      
      // last char of String, set end (index is used to compare next char and thus overshoots by 1)
      if (i == s.length()) {
        child.end = true;
        return true;
      }
      
      // move down tree
      curr = child;
      child = null;
      match = false;
    }
     
    return false;
  }

  /**
   * Removes a word s from the trie tree by removing any nodes not shared by other words, or else by
   * setting the final node's end-of-word value to false.\
   * 
   * @param s is the String to remove
   * @return true if s is found and removed, false otherwise
   */
  public boolean remove(String s) {   
    if (!contains(s))
      throw new IllegalArgumentException("String \"" + s + "\" not found");
  
    Node child;
    Node curr = root;
    int i = 0;
    boolean match = false;
    
    // find tail branch of word
    while (i < s.length()) {
      
      for (Node n : curr.children) {
        if (n.data == s.charAt(i)) {
          match = true;
          curr = n;
          break;
        }
      }
      
      if (!match) return false;
      if (curr.end && s.charAt(s.length()-1)==curr.data) break;
    }
    
    // set tail as non end-of-word
    curr.end = false;
    
    // move up branch until hitting an end-of-word or root node or node with children
    while ( (curr.data != '\0') && !curr.end && curr.children.isEmpty()) {
      
      // remove node, move up tree
      child = curr;
      curr = curr.parent;
      curr.children.remove(child);
            
    }
    
    return true;
  }
  
  /**
   * Returns true if tree contains argument String. Each char in s must match a node in a sequence,
   * ending with a match in which the node is marked as the end of a word.
   * 
   * @param s is the String to search for
   * @return true if word is present in tree
   */
  public boolean contains(String s) {

    Node curr = root;
    int index = 0;
    boolean match = false;

    // before surpassing length of string
    while (index < s.length()) { // <=

      // no children, searach ends
      if (curr.children.isEmpty()) return false;
      
      // search each child for a match
      for (Node n : curr.children) {
        // match found among children, move down tree
        if (n.data == s.charAt(index)) {
          match = true;
          curr = n;
          break;
        }
      }
      if (!match) return false; // no match among children

      // found word matching string
      if (curr.end && index == s.length()) return true;

      // match but not end of string, continue search
      index++; // increment index to search for next node
      // node already moved down
      match = false;
    }
    
    return false;
  }
  
  /**
   * Future update should implement Red Black Tree to replace ArrayList
   */
  protected class Node {
    boolean end;
    char data;
    Node parent;
    ArrayList<Node> children; 

    public Node(boolean end, char data, Node parent) {
      
      //if (data == '\0') throw new IllegalArgumentException("Invalid data argument");
      
      this.end = end;
      this.data = data;
      this.parent = parent;
      children = new ArrayList<Node>();
    }
  }
}
