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
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PackageManagerTest {

  static PackageManager packageManager;

  @BeforeEach
  public void setUp() throws Exception {
    packageManager = new PackageManager(); // creates new package manager every time before a test
  }

  @AfterEach
  public void tearDown() throws Exception {
    packageManager = null; // sets it back to null after every test
  }

  /**
   * this tests if the package manager is able to construct the graph correctly
   */
  @Test
  void test00_checkIfAbleToParse() {
    try {
      packageManager.constructGraph("test.json");
    } catch (Exception e) {
      fail("shouldn't have thrown exception for parsible file");
    }
  }

  /**
   * this tests if the package manager is able to correctly identify packages and 
   * return them correctly
   */
  @Test
  void test01_getAllPackages() {
    try {
      packageManager.constructGraph("test.json");
      packageManager.constructGraph("test1.json"); // constructs graphs from two files
    } catch (Exception e) {
      fail("shouldn't have thrown exception for parsible file");
    }
    Set<String> check = new LinkedHashSet<>();
    check.add("A");
    check.add("B");
    check.add("C");
    check.add("D");
    check.add("E");
    check.add("N");
    check.add("M");
    check.add("W");
    check.add("O"); // creates a mock check list
    if (!check.equals(packageManager.getAllPackages())) { // compares package manager to "answer
                                                          // key"
      fail("package manager returned the wrong packages");
    }
  }

  /**
   * this tests if the installation order of the packages is right and in the correct order
   */
  @Test
  void test02_getInstallationOrder() {
    try {
      packageManager.constructGraph("test.json");
    } catch (Exception e) {
      fail("shouldn't have thrown exception for parsible file");
    }
    List<String> check = new ArrayList<>();
    check.add("C");
    check.add("D");
    check.add("B"); // creates a mock check list
    try {
      if (!check.equals(packageManager.getInstallationOrder("B"))) // compares package manager to
                                                                   // "answer key"
        fail("package manager returned the wrong installation order");
    } catch (Exception e) {
      fail("package manager threw unncessary exception");
    }
  }

  /**
   * this tests if package manager to install method returns the correct packages still to be 
   * installed
   */
  @Test
  void test03_toInstall() {
    try {
      packageManager.constructGraph("test.json"); // creates a graph
    } catch (Exception e) {
      fail("shouldn't have thrown exception for parsible file");
    }
    List<String> check = new ArrayList<>();
    check.add("D");
    check.add("B");
    check.add("A"); // creates a mock check list
    try {
      if (!check.equals(packageManager.toInstall("A", "C"))) { // compares package manager to
                                                               // "answer key"
        fail("package manager returned the wrong order to install in");
      }
    } catch (Exception e) {
      fail("unnecessary exception was thrown");
    }
  }

  /**
   * this tests if installation order of all packages is correct and in the right order
   */
  @Test
  void test04_getInstallationOrderForAll() {
    try {
      packageManager.constructGraph("test.json");
      packageManager.constructGraph("test1.json"); // creates graphs from two files
    } catch (IOException | ParseException e) {
      fail("shouldn't have thrown exception for parsible file");
    }

    List<String> check = new ArrayList<>();
    check.add("C");
    check.add("D");
    check.add("B");
    check.add("A");
    check.add("M");
    check.add("N");
    check.add("W");
    check.add("E");
    check.add("O"); // creates a mock check list
    try {
      if (!check.equals(packageManager.getInstallationOrderForAllPackages())) { // compares package
                                                                                // manager to check
                                                                                // list
        fail("package manager didn't return the correct order for all packages");
      }
    } catch (Exception e) {
      fail("threw unncessary exception");
    }
  }

  /**
   * this tests if package manager getPackageWithMaxDependencies returns the correct package
   */
  @Test
  void test05_getMaxDependencies() {
    try {
      packageManager.constructGraph("test.json"); // creates graph
    } catch (Exception e) {
      fail("shouldn't have thrown exception for parsible file");
    }

    try {
      if (!packageManager.getPackageWithMaxDependencies().equals("A")) // checks max dependency
                                                                       // method with correct answer
        fail("package manager didn't return correct package with max dependencies");
    } catch (Exception e) {
      fail("unncessary exception was thrown");
    }
  }


}
