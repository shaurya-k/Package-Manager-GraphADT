//////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION /////////////////////
//
// Title: (P4 Graph)
// Course: (CS 400, Fall 2019)
//
// Author: (Shaurya Kethireddy)
// Email: (skethireddy@wisc.edu)
// Lecturer's Name: (Debra Deppeler)
// Description: Directed and unweighted graph implementation
/////////////////////////////// 80 COLUMNS WIDE ///////////////////////////////
import static org.junit.jupiter.api.Assertions.fail;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphTest {

  static Graph graph;

  @BeforeEach
  public void setUp() throws Exception {
    graph = new Graph(); // creates new graph every time before a test
  }

  @AfterEach
  public void tearDown() throws Exception {
    graph = null; // sets it back to null after every test
  }

  /**
   * this test makes sure that the graph is starting off with the correct variables
   */
  @Test
  void test00_checkInitialization() {
    if (graph.size() != 0 || graph.order() != 0) {
      fail("graph isn't initialized with correct variables");
    }
  }

  /**
   * this test adds vertices and checks if they're added to vertex list correctly
   */
  @Test
  void test01_addVertex() {
    graph.addVertex("a");
    graph.addVertex("b"); // adds two vertices

    Set<String> vertices = new LinkedHashSet<>();
    vertices.add("a");
    vertices.add("b"); // creates a check list to compare with

    if (!graph.getAllVertices().equals(vertices)) {
      fail("Graph didn't add the vertices correctly");
    }
  }

  /**
   * this test adds vertices and removes one of them and checks if the graph vertex list doesn't 
   * have it
   */
  @Test
  void test02_removeVertex() {
    graph.addVertex("a");
    graph.addVertex("b"); // adds two
    graph.removeVertex("a"); // remove

    Set<String> vertices = new LinkedHashSet<>();
    vertices.add("a");
    vertices.add("b");
    vertices.remove("a"); // creates a mock up vertex list

    if (!graph.getAllVertices().equals(vertices)) {
      fail("Graph didn't remove the vertices correctly");
    }
  }

  /**
   * this test makes sure that user is able to add edges between vertices which don't exist in graph
   */
  @Test
  void test03_addEdgeBetweenVerticesWhichDontExist() {
    graph.addEdge("a", "b");
    Set<String> vertices = new LinkedHashSet<>();
    vertices.add("a");
    vertices.add("b"); // creates mock up vertex list
    if (!graph.getAllVertices().equals(vertices)) { // compares vertices list
      fail("Graph didn't add the vertices correctly");
    }
    List<String> adjOfA = new ArrayList<>();
    adjOfA.add("b"); // creates mock up adjacent list
    if (!graph.getAdjacentVerticesOf("a").equals(adjOfA)) { // compares adjacent lists
      fail("Graph didn't correctly place adjacent vertices");
    }
  }

  /**
   * this test checks to see if the graph is returning the correct number of vertices
   */
  @Test
  void test04_checkOrder() {
    graph.addVertex("a");
    graph.addVertex("b");
    graph.addVertex("c");
    graph.addVertex("d");
    graph.addVertex("e");
    graph.addVertex("f"); // added 6 items
    if (graph.order() != 6) {
      fail("graph has counted incorrect number of vertices");
    }
  }

  /**
   * this test checks to see if the graph counts the correct number of edges
   */
  @Test
  void test05_checkSize() {
    graph.addVertex("a");
    graph.addVertex("b");
    graph.addVertex("c"); // adds three vertices
    graph.addEdge("a", "b");
    graph.addEdge("b", "c");
    graph.addEdge("c", "b"); // add three edges
    if (graph.size() != 3) {
      fail("graph has counted incorrect number of edges");
    }
  }

  /**
   * this test checks to see if graph is able to handle the remove edge function as well as 
   * return correct number of edges
   */
  @Test
  void test06_removeEdge_checkSize() {
    graph.addVertex("c");
    graph.addEdge("a", "b");
    graph.addEdge("b", "c"); // adds two edges
    graph.removeEdge("a", "b"); // removes one of the edges
    if (graph.size() != 1) {
      fail("graph has counted incorrect number of edges");
    }
  }

  /**
   * this test checks if exception is handled smoothly when trying to remove nonexisting edge
   */
  @Test
  void test07_tryToRemoveNonExistentEdge() {
    graph.addVertex("a");
    graph.addVertex("b");
    graph.addVertex("c");
    graph.addEdge("a", "b");
    graph.addEdge("b", "c"); // adds two edges
    try {
      graph.removeEdge("e", "f"); // remove non existent edge
    } catch (Exception e) {
      fail("Shouldn't have thrown exception");
    }

    Set<String> vertices = new LinkedHashSet<>();
    vertices.add("a");
    vertices.add("b");
    vertices.add("c"); // creates mock up vertex list

    if (!graph.getAllVertices().equals(vertices)) { // checks if vertices weren't added or removed
      fail("wrong vertices are inserted into the graph");
    }
    if (graph.size() != 2) {
      fail("wrong number of edges in the graph");
    }

  }

  /**
   * this test checks if graph is getting the correct adjacent vertices
   */
  @Test
  void test08_getAllAdjacentVertices() {
    graph.addEdge("a", "b");
    graph.addEdge("a", "c"); // add two edges

    List<String> check = new ArrayList<>();
    check.add("b");
    check.add("c"); // create mock up adjacent list

    if (!check.equals(graph.getAdjacentVerticesOf("a"))) { // checks if both are same
      fail("graph didn't record adjacent vertices correctly");
    }
  }


}
