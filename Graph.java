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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Graph implements GraphADT {
  private Map<String, Set<String>> map;
  private Set<String> vertices;
  private int edges;

  /*
   * Default no-argument constructor
   */
  public Graph() {
    map = new HashMap<>(); // creates a new map to hold all the edges of a certain vertex
    vertices = new LinkedHashSet<>(); // creates a set to hold all vertices
    edges = 0; // reset edge number to 0
  }


  /**
   * Add new vertex to the graph.
   *
   * If vertex is null or already exists,
   * method ends without adding a vertex or 
   * throwing an exception.
   * 
   * Valid argument conditions:
   * 1. vertex is non-null
   * 2. vertex is not already in the graph 
   */
  @Override
  public void addVertex(String vertex) {
    try {
      if (vertex == null) // nullcheck
        throw new Exception();
    } catch (Exception e) {
      return;
    }
    Set<String> vertices = getAllVertices();
    for (String pointer : vertices) { // checks if vertex already exists in the graph
      try {
        if (pointer.equals(vertex))
          throw new Exception();
      } catch (Exception e) {
        return;
      }
    }

    map.put(vertex, new LinkedHashSet<>()); // puts the new vertex in the map and initializes a list
                                            // to store its adjacent vertices
    vertices.add(vertex); // adds vertex to vertex list

  }

  /**
   * Remove a vertex and all associated 
   * edges from the graph.
   * 
   * If vertex is null or does not exist,
   * method ends without removing a vertex, edges, 
   * or throwing an exception.
   * 
   * Valid argument conditions:
   * 1. vertex is non-null
   * 2. vertex is not already in the graph 
   */
  @Override
  public void removeVertex(String vertex) {
    try {
      if (vertex == null) // null check
        throw new Exception();
    } catch (Exception e) {
      return;
    }
    Set<String> vertices = getAllVertices(); // gets all existing vertices
    boolean contains = false; // default
    for (String pointer : vertices) { // checks if the vertex exists
      if (pointer.equals(vertex))
        contains = true;
    }
    try {
      if (!contains) // if it doesn't exist then throw exception
        throw new Exception();
    } catch (Exception e) {
      return;
    }

    int tempEdges = map.get(vertex).size(); // gets the number of adjacent vertices to that vertex
    for (int i = 0; i < tempEdges; i++)
      edges--; // decrements edges

    for (Set<String> entry : map.values()) { // goes through the map to delete the vertex and it's
                                             // existing adjacent vertex links
      if (entry.contains(vertex))
        entry.remove(vertex);
    }

    vertices.remove(vertex); // removes from the vertex list
    map.remove(vertex); // removes from map
    return;

  }

  /**
   * Add the edge from vertex1 to vertex2
   * to this graph.  (edge is directed and unweighted)
   * If either vertex does not exist,
   * add vertex, and add edge, no exception is thrown.
   * If the edge exists in the graph,
   * no edge is added and no exception is thrown.
   * 
   * Valid argument conditions:
   * 1. neither vertex is null
   * 2. both vertices are in the graph 
   * 3. the edge is not in the graph
   */
  @Override
  public void addEdge(String vertex1, String vertex2) {
    try {
      if (vertex1 == null || vertex2 == null) // null check
        throw new Exception();
    } catch (Exception e) {
      return;
    }

    boolean vertexOne = false;
    boolean vertexTwo = false;
    Set<String> vertices = getAllVertices(); // gets all vertices
    for (String pointer : vertices) { // checks if both the vertices exist
      if (pointer.equals(vertex1))
        vertexOne = true;

      if (pointer.equals(vertex2))
        vertexTwo = true;
    }
    if (vertexOne == false) // if false then add to graph
      addVertex(vertex1);

    if (vertexTwo == false)
      addVertex(vertex2);

    map.get(vertex1).add(vertex2); // add the link from vertex 1 to vertex 2
    edges++; // increment edge count
  }

  /**
   * Remove the edge from vertex1 to vertex2
   * from this graph.  (edge is directed and unweighted)
   * If either vertex does not exist,
   * or if an edge from vertex1 to vertex2 does not exist,
   * no edge is removed and no exception is thrown.
   * 
   * Valid argument conditions:
   * 1. neither vertex is null
   * 2. both vertices are in the graph 
   * 3. the edge from vertex1 to vertex2 is in the graph
   */
  @Override
  public void removeEdge(String vertex1, String vertex2) {
    try {
      if (vertex1 == null || vertex2 == null) // null check
        throw new Exception();
    } catch (Exception e) {
      return;
    }

    boolean vertexOne = false;
    boolean vertexTwo = false;
    Set<String> vertices = getAllVertices();
    for (String pointer : vertices) { // check if both of them exist
      if (pointer.equals(vertex1))
        vertexOne = true;

      if (pointer.equals(vertex2))
        vertexTwo = true;
    }
    try {
      if (!vertexOne || !vertexTwo) // if either one of them don't exist throw exception
        throw new Exception();
    } catch (Exception e) {
      return;
    }

    try {
      if (!map.get(vertex1).contains(vertex2)) // if there is no connection from vertex 1 to 2
        throw new Exception();
    } catch (Exception e) {
      return;
    }

    map.get(vertex1).remove(vertex2); // remove the connection from vertex 1 to 2
    edges--; // decrement edge count
  }

  /**
   * Returns a Set that contains all the vertices
   * 
   */
  @Override
  public Set<String> getAllVertices() {
    return vertices;
  }

  /**
   * Get all the neighbor (adjacent) vertices of a vertex
   *
   */
  @Override
  public List<String> getAdjacentVerticesOf(String vertex) {
    if (!map.containsKey(vertex)) // checks if the vertex is in the vertex list
      return null;
    List<String> adjVertex = new ArrayList<>();
    for (String pointer : map.get(vertex)) // adds all adjacent vertices of "vertex" and adds to new
                                           // list
      adjVertex.add(pointer);

    return adjVertex;
  }


  /**
   * Returns the number of edges in this graph.
   */
  @Override
  public int size() {
    return edges;
  }

  /**
   * Returns the number of vertices in this graph.
   */
  @Override
  public int order() {
    return vertices.size(); // gets size of the vertex list
  }

}
