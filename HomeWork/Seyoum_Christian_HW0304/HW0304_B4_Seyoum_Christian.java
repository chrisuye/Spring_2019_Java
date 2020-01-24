//Christian, jessica and Nebiyu

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;



public class HW0304_B4_Seyoum_Christian<E> {
 
 public class Node  {
 
  double Cost; 
  public boolean found; 
  Node precursor;

  public Node() {

      Cost = 0;   
   precursor = null;
  }
 }

 private HashMap<String, HashMap<String, Double>> adjacencyMap;
 private HashMap<String, E> dataMap;
 private static final int INFINITY = Integer.MAX_VALUE;

 protected HashMap<String, Node> vMap; 


 public HW0304_B4_Seyoum_Christian() {
  adjacencyMap = new HashMap<String, HashMap<String, Double>>();
  dataMap = new HashMap<String, E>();
  vMap = new HashMap<String, Node>();
 
 }

 public void addEdge(String startVertexName, String endVertexName, double cost) {

  if (dataMap.containsKey(startVertexName) && dataMap.containsKey(endVertexName)) {
   adjacencyMap.get(startVertexName).put(endVertexName, cost);
   adjacencyMap.get(endVertexName).put(startVertexName, cost);

  }
  else {
   throw new IllegalArgumentException();
  }

 }


 public void addVertex(String vertexName, E data) {
  if (!dataMap.containsKey(vertexName)){
   dataMap.put(vertexName, data);
   adjacencyMap.put(vertexName, new HashMap<String, Double>());

   Node fresh = new Node();
   vMap.put(vertexName, fresh);
  }
  else {

   throw new IllegalArgumentException();

  }

 }

 
 public double doDijkstras(String startVertexName, String endVertexName,ArrayList<String> shortestPath){

  String name;  
  double newPath; 
  double cost;  
  double newCost;


  if (dataMap.containsKey(startVertexName) && dataMap.containsKey(endVertexName)) {
   for (Node node: vMap.values()) {
    node.Cost = INFINITY;
   }

   Node vert = vMap.get(startVertexName); 
   vert.Cost = 0;
   PriorityQueue<String> priorityQueue = new PriorityQueue<String>();
   priorityQueue.add(startVertexName);

   while(priorityQueue.isEmpty()==false) {
    name = priorityQueue.poll();
    Node currVert = vMap.get(name); 
    for(String adjVert: getAdjacentVertices(name).keySet()) { 
     Node adjacentVertex = vMap.get(adjVert);
     cost = getCost(name, adjVert);
     newCost = currVert.Cost + cost;

     if(newCost < adjacentVertex.Cost) {

      adjacentVertex.Cost = newCost;
      adjacentVertex.precursor = currVert;
      priorityQueue.add(adjVert);

     }
    }
   }

   Node endVert = vMap.get(endVertexName); 
   if (endVert.Cost == INFINITY) {
    shortestPath.add("None");
    return -1;
   }
   else {
    newPath = endVert.Cost;
    endVertexName = getNode(endVert);
    shortestPath.add(endVertexName);
    endVert = endVert.precursor;

    while(!endVertexName.equals(startVertexName)) {
     endVertexName = getNode(endVert);
     shortestPath.add(endVertexName);
     endVert = endVert.precursor;
    }
    Collections.reverse(shortestPath);
    return  newPath;
   }

  }

  else
  {
   throw new IllegalArgumentException();
  }
 }

 public Map<String,Double> getAdjacentVertices(String vertexName) {

  if (dataMap.containsKey(vertexName)) {

   return adjacencyMap.get(vertexName);
  }
  else {

   throw new IllegalArgumentException();
  }
 }

 public E getData(String vertex) {
  if (dataMap.containsKey(vertex)) {
   return dataMap.get(vertex);
  }
  else {

   throw new IllegalArgumentException();
  }
 }

 public Set<String> getVertices() {

  return dataMap.keySet();

 }

 public double getCost(String startVertexName, String endVertexName) {
  if (dataMap.containsKey(startVertexName) && dataMap.containsKey(endVertexName)) {
   double cost = adjacencyMap.get(startVertexName).get(endVertexName);
   return cost;
  }

  else {
   throw new IllegalArgumentException();
  }
 }
 public String getNode(Node vertex) {
  Set<String> set = vMap.keySet();
  String node = null;
  for (String name : set) { 

   if (vMap.get(name).equals(vertex)) {

    node = name;

   }
  }
  return node;

 }

 public String toString() {
  List<String> sorted = new ArrayList<String>();
  List<String> vertices = new ArrayList <>(dataMap.keySet());

  while(vertices.size() > 0) {
   int compare = 0;
   for(int i = 1; i<vertices.size(); i++) {
    if(vertices.get(i).compareTo(vertices.get(compare)) < 0) {
     compare = i;
    }
   }

   sorted.add(vertices.get(compare));
   vertices.remove(compare);
  }


  List<String> list = sorted; 
  String str = new String();

  str += "Vertices: " + list.toString() + "\n" + "Edges:\n";

  for (int i=0; i< list.size();i++) {

   str += "Vertex(" + list.get(i) + ")--->{";

   boolean bool = false; 
   HashMap<String, Double> adj = adjacencyMap.get(list.get(i));

   for(String vertex2: list) {
    Double cost = adj.get(vertex2);
    if (cost == null) {
     continue;
    }
    if(bool==false){
     bool = true;
    }

    else {
     str += ", "; 
    }

    str += vertex2 + "=" + cost;
   }
   str+=("}\n");
  }
  return str;
 }
 
 
 @SuppressWarnings("resource")
 public static HW0304_B4_Seyoum_Christian<Double> createTheGraph() {
  try{
   Scanner myScanner = new Scanner(System.in);
   System.out.print("File name: ");
   String fileName = myScanner.next();
   Scanner input = new Scanner(new File(fileName));
   HW0304_B4_Seyoum_Christian<Double> graph = new HW0304_B4_Seyoum_Christian<Double>();
   
   ArrayList<String> start = new ArrayList<>();
   ArrayList<String>  end = new ArrayList<>();
   ArrayList<String> weight = new ArrayList<>();

   while (input.hasNext()){    
    start.add(input.next());
    end.add(input.next());
    weight.add(input.next());
   }
   
   ArrayList<String> temp =new ArrayList<>();
   for (int x = 0; x < start.size(); x++) {
    temp.add(start.get(x));
    temp.add(end.get(x));

   }
   
   ArrayList<String> vertices = (ArrayList<String>) temp.stream().distinct().collect(Collectors.toList());
   
   for (int i = 0; i < vertices.size(); i++) {
    graph.addVertex(vertices.get(i), new Double(i + 1000.50));
   }
   
   
   for (int i = 0; i < start.size(); i++) {
    graph.addEdge(start.get(i), end.get(i), Double.parseDouble(weight.get(i)));
   }
   input.close();
   return graph;

  }

  catch (FileNotFoundException sMsg){
   System.out.println("FileNotFoundException");
  }
  catch (NoSuchElementException sMsg){
   System.out.print("NoSuchElementException");
  }
  return null;
 }
 
 private static String runDijkstras(HW0304_B4_Seyoum_Christian<Double> graph, String startVertex) {
  ArrayList<String> shortestPath = new ArrayList<String>();
  StringBuffer results = new StringBuffer();
  
  Set<String> vertices = graph.getVertices();
  TreeSet<String> sortedVertices = new TreeSet<String>(vertices);
  for (String endVertex : sortedVertices) {
   double shortestPathCost = graph.doDijkstras(startVertex, endVertex,
     shortestPath);
   results.append("Shortest path cost between " + startVertex + " & " + endVertex
     + ": " + shortestPathCost);
   results.append(", Path: " + shortestPath + "\n");
   shortestPath.clear();
  }
  
  return results.toString();
 }
 
 public static void main(String[] args) {
  Scanner myScanner = new Scanner(System.in);
  HW0304_B4_Seyoum_Christian<Double> graph = createTheGraph();
  System.out.println(graph);
  System.out.print("Enter the starting vertex: ");
  String startVertex = myScanner.next();
  String answer = runDijkstras(graph, startVertex);
  System.out.println(answer);
  myScanner.close();

 }
 

 
}