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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * 
 * PackageManager is used to process json package dependency files and provide function that make
 * that information available to other users.
 * 
 * Each package that depends upon other packages has its own entry in the json file.
 * 
 * Package dependencies are important when building software, as you must install packages in an
 * order such that each package is installed after all of the packages that it depends on have been
 * installed.
 * 
 * For example: package A depends upon package B, then package B must be installed before package A.
 * 
 * This program will read package information and provide information about the packages that must
 * be installed before any given package can be installed. all of the packages in
 * 
 */

public class PackageManager {

  private Graph graph;

  /*
   * Package Manager default no-argument constructor.
   */
  public PackageManager() {
    graph = new Graph(); // initializes the graph

  }

  /**
   * Takes in a file path for a json file and builds the
   * package dependency graph from it. 
   * 
   * @param jsonFilepath the name of json data file with package dependency information
   * @throws FileNotFoundException if file path is incorrect
   * @throws IOException if the give file cannot be read
   * @throws ParseException if the given json cannot be parsed 
   */
  public void constructGraph(String jsonFilepath)
      throws FileNotFoundException, IOException, ParseException {

    Object obj = new JSONParser().parse(new FileReader(jsonFilepath)); // parse the json file into
                                                                       // an object
    JSONObject jo = (JSONObject) obj; // convert to JSONObject
    JSONArray packages = (JSONArray) jo.get("packages"); // get packages into an array

    for (Object temp : packages) { // loop through the available objects in packages
      Map array = (Map) temp; // map the object so it has a key/value list
      String vertexName = (String) array.get("name"); // convert to string
      JSONArray adjancentVertices = (JSONArray) array.get("dependencies"); // convert to jsonarray

      graph.addVertex(vertexName); // add the "name" to the graph as vertex

      for (Object edges : adjancentVertices) {
        graph.addEdge(vertexName, (String) edges); // add all the dependencies
      }
    }
  }

  /**
   * Helper method to get all packages in the graph.
   * 
   * @return Set<String> of all the packages
   */
  public Set<String> getAllPackages() {
    return graph.getAllVertices();
  }

  /**
   * Given a package name, returns a list of packages in a
   * valid installation order.  
   * 
   * Valid installation order means that each package is listed 
   * before any packages that depend upon that package.
   * 
   * @return List<String>, order in which the packages have to be installed
   * 
   * @throws CycleException if you encounter a cycle in the graph while finding
   * the installation order for a particular package. Tip: Cycles in some other
   * part of the graph that do not affect the installation order for the 
   * specified package, should not throw this exception.
   * 
   * @throws PackageNotFoundException if the package passed does not exist in the 
   * dependency graph.
   */
  public List<String> getInstallationOrder(String pkg)
      throws CycleException, PackageNotFoundException {

    ArrayList<String> ordered = new ArrayList<>();
    Map<String, Integer> check = new LinkedHashMap<>();
    if (!getAllPackages().contains(pkg)) { // check if "pkg" exists
      throw new PackageNotFoundException();
    }
    check.put(pkg, 1); // adds the package with "1" to indicate how many times it's visited
    helper(pkg, check, ordered);
    ordered.add(pkg); // adds the pkg element last to install

    return ordered;
  }

  /**
   * this method helps get installation order with logic
   */
  private void helper(String pkg, Map<String, Integer> check, ArrayList<String> ordered)
      throws CycleException {

    for (Integer pointer : check.values()) { // cycle checking to see how many times each package is
                                             // visited
      if (pointer >= 2)
        throw new CycleException();
    }

    for (String edge : graph.getAdjacentVerticesOf(pkg)) { // loop through the adjacent vertices
      if (!ordered.contains(edge)) {
        if (!check.containsKey(edge)) {
          check.put(edge, 1); // base case
        } else {
          check.put(edge, check.get(edge) + 1); // adds the adjacent vertex to map and adds one
          // count to times visited
        }
        helper(edge, check, ordered); // recursive to go "deeper into" packages
        ordered.add(edge);
      }
    }

  }

  /**
   * Given two packages - one to be installed and the other installed, 
   * return a List of the packages that need to be newly installed. 
   * 
   * For example, refer to shared_dependecies.json - toInstall("A","B") 
   * If package A needs to be installed and packageB is already installed, 
   * return the list ["A", "C"] since D will have been installed when 
   * B was previously installed.
   * 
   * @return List<String>, packages that need to be newly installed.
   * 
   * @throws CycleException if you encounter a cycle in the graph while finding
   * the dependencies of the given packages. If there is a cycle in some other
   * part of the graph that doesn't affect the parsing of these dependencies, 
   * cycle exception should not be thrown.
   * 
   * @throws PackageNotFoundException if any of the packages passed 
   * do not exist in the dependency graph.
   */
  public List<String> toInstall(String newPkg, String installedPkg)
      throws CycleException, PackageNotFoundException {
    List<String> already = getInstallationOrder(installedPkg);
    List<String> future = getInstallationOrder(newPkg);
    future.removeAll(already); // list subtraction
    return future;
  }

  /**
   * Return a valid global installation order of all the packages in the 
   * dependency graph.
   * 
   * assumes: no package has been installed and you are required to install 
   * all the packages
   * 
   * returns a valid installation order that will not violate any dependencies
   * 
   * @return List<String>, order in which all the packages have to be installed
   * @throws CycleException if you encounter a cycle in the graph
   */
  public List<String> getInstallationOrderForAllPackages() throws CycleException {
    List<String> listOfAllOrders = new ArrayList<>();
    Set<String> iterate = getAllPackages();
    try {
      for (String pointer : iterate) { // goes through each package

        listOfAllOrders.addAll(getInstallationOrder(pointer)); // adds all installation orders for
                                                               // all packages
      }
    } catch (PackageNotFoundException e) {
      return null;
    }
    List<String> installationOrder = new ArrayList<>();
    for (String pointer : listOfAllOrders) { // goes through list
      if (!installationOrder.contains(pointer)) // adds first instance of each package in list
        installationOrder.add(pointer);
    }
    return installationOrder;


  }

  /**
   * Find and return the name of the package with the maximum number of dependencies.
   * 
   * 
   * Example: if A depends on B and C, and B depends on C, and C depends on D.  
   * Then,  A has 3 dependencies - B,C and D.
   * 
   * @return String, name of the package with most dependencies.
   * @throws CycleException if you encounter a cycle in the graph
   */
  public String getPackageWithMaxDependencies() throws CycleException {
    int toCheck = 0;
    String maxPackage = null;
    Set<String> packages = getAllPackages();
    try {
      for (String iterate : packages) { // iterates through all packages
        List<String> order = getInstallationOrder(iterate); // gets order list for all elements
        if (order.size() > toCheck) {
          toCheck = order.size(); // checks which list has most elements
          maxPackage = iterate;
        }
      }

    } catch (Exception e) {
      return null; // smoothly handling exception
    }
    return maxPackage;
  }

  public static void main(String[] args) {
    System.out.println("PackageManager.main()");
  }

}
