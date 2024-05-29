import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BackendTests {

	/**
	 * Tests Users.java to ensure that user is correctly initialized.
	 *
	 */
	@Test
	public void testUserCreation() {
	  
	  Trie t = new Trie();
	  User bern = new User(null, "bern", null, null);
	  User ern  = new User(null, "ern", null, null);
	  User mern = new User(null, "mern", null, null);
	  // continue writing
	  
	}

	/**
	 * Tests that contains() accurately determines whether a String is contained by the tree.
	 * 
	 */
	@Test
	public void testTrieContains() {
	  
	  Trie t = new Trie();
	  t.insert("zoinks!");
	  t.insert("add");
	  t.insert("check");
	  t.insert("adderal");
	  t.insert("cherry");
	  t.insert("zeta");
	  t.insert("zebra");
	  
	  // test cases: absent, partial, and full words; non-letter chars
	  Assertions.assertEquals(true, null);
	  Assertions.assertEquals(false, t.contains("ad"));
	  Assertions.assertEquals(true, t.contains("add"));
	  Assertions.assertEquals(false, t.contains("adera"));
	  Assertions.assertEquals(true, t.contains("cherry"));
	  Assertions.assertEquals(true, t.contains("check"));
	  Assertions.assertEquals(false, t.contains("che"));
	  Assertions.assertEquals(false, t.contains("z"));
	  Assertions.assertEquals(true, t.contains("zoinks!"));
	  Assertions.assertEquals(false, t.contains("base"));
	  
	}
	
	/**
	 * Tests that string is correctly inserted into trie implementation.
	 */
	@Test
	public void testTrieInsertion() {
	  
	  Trie t = new Trie();
	  t.insert("ant");
	  t.insert("bat");
	  //t.insert("cat");
	  t.insert("and");
	  t.insert("are");
	  //t.insert("care");
	  
	  // word insertion with letters already present
	  Assertions.assertEquals(true, t.insert("an"));
	  Assertions.assertEquals(true, t.insert("car"));
	  
	  // ensure parent has been left unchanged
	  Assertions.assertEquals(false, t.root.end);
	  Assertions.assertEquals(null, t.root.parent);
	  Assertions.assertEquals('\0', t.root.data);
	  
	  // check that various nodes are present, have correct data, if end-of-word is set, parent node
	  Assertions.assertEquals(t.root.children.get(0).data, 'a');
	  Assertions.assertEquals(false, t.root.children.get(0).end);
      Assertions.assertEquals(t.root.children.get(0).parent, t.root);
	  
	  Assertions.assertEquals(t.root.children.get(0).children.get(0).data, 'n');
	  Assertions.assertEquals(true, t.root.children.get(0).children.get(0).end);
	  Assertions.assertEquals(t.root.children.get(0).children.get(0).parent, t.root.children.get(0));
	  
      Assertions.assertEquals(t.root.children.get(0).children.get(0).children.get(1).data, 'd');
      Assertions.assertEquals(true, t.root.children.get(0).children.get(0).children.get(1).end);

      Assertions.assertEquals(t.root.children.get(0).children.get(1).data, 'r');
      Assertions.assertEquals(false, t.root.children.get(0).children.get(1).end);
      Assertions.assertEquals(t.root.children.get(0).children.get(1).children.get(0).data, 'e');
      Assertions.assertEquals(t.root.children.get(0).children.get(1).children.get(0).parent, 
          t.root.children.get(0).children.get(1));

      Assertions.assertEquals(true, t.root.children.get(0).children.get(1).children.get(0).end);
      
      Assertions.assertEquals(t.root.children.get(1).data, 'b');
      Assertions.assertEquals(false, t.root.children.get(1).end);
      Assertions.assertEquals(t.root.children.get(1).parent, t.root);
      
      Assertions.assertEquals(t.root.children.contains(t.new Node(false, 'b', null)), false);

	}

	/**
	 * Tests that string is correctly removed from trie implementation.
	 */
	@Test
	public void testTrieRemoval() {
	  
	  Trie t = new Trie();
	  t.insert("ant");
	  t.insert("and");
	  t.insert("are");
	  t.insert("bat");
	  t.insert("car");
	  t.insert("care");
	  t.insert("cared");
	  t.insert("cat");
	  
	  // removing "bat" moves Node 'c' into index 1 of children ArrayList
	  Assertions.assertEquals(true, t.remove("bat"));
	  Assertions.assertEquals(t.root.children.get(1).data, 'c');
	  
	  // removing "ant" should leave Node 'n' with 1 child ('d')
	  Assertions.assertEquals(true, t.remove("ant"));
	  Assertions.assertEquals(t.root.children.get(0).children.get(0).data, 'n');
	  Assertions.assertEquals(t.root.children.get(0).children.get(0).children.size(), 1);

	  // removing "car" should leave Node 'r' within tree, but with end-of-word set to false
      Assertions.assertEquals(true, t.remove("car"));
      Assertions.assertEquals(t.root.children.get(1).children.get(0).children.get(0).data, 'r');
      Assertions.assertEquals(t.root.children.get(1).children.get(0).children.get(0).end, false);

	  // removing "are" should leave Node 'a' with one child ('n')
      Assertions.assertEquals(true, t.remove("are"));
      Assertions.assertEquals(t.root.children.get(0).children.size(), 1);
      
      // removing "cared" should see Node 'd' removed and Node 'e' remain as is
      Assertions.assertEquals(true, t.remove("cared"));
      Assertions.assertEquals(t.root.children.get(1).children.get(0).children.get(0).children.get(0).children.isEmpty(), true);
      Assertions.assertEquals(t.root.children.get(1).children.get(0).children.get(0).children.get(0).end, true);
      
      // words not held in trie tree
	  Assertions.assertEquals(false, t.remove("cow"));
	  Assertions.assertEquals(false, t.remove("ant"));
      Assertions.assertEquals(false, t.remove("cared"));

      // words that should remain within tree
      Assertions.assertEquals(true, t.contains("cat"));
      Assertions.assertEquals(true, t.contains("and"));
      Assertions.assertEquals(true, t.contains("care"));
      
	}

}
