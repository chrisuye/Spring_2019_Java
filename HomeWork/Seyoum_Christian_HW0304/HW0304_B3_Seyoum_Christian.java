//Christian, Jessica and Nebiyu
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;



public class HW0304_B3_Seyoum_Christian<T extends Comparable<T>> implements Serializable{


 class SKNode<T extends Comparable<? super T>> implements Serializable{
  private SKNode<T> up = null;
  private SKNode<T> down = null;
  private SKNode<T> next = null;
  private SKNode<T> prev = null;
  private int printPosition = 0;
  private T data;

  SKNode(T data){ 
   this.data = data;
  }

  public void setUp(SKNode<T> up){
   this.up = up;
  }
  public void setDown(SKNode<T> down){
   this.down = down;
  }
  public void setNext(SKNode<T> next){
   this.next = next;
  }
  public void setPrev(SKNode<T> prev){
   this.prev = prev;
  }
  public void setPrintPosition(int printPosition){
   this.printPosition = printPosition;
  }

  public T getData(){
   return data;
  }        
  public SKNode<T> getUp(){
   return up;
  }
  public SKNode<T> getDown(){
   return down;
  }
  public SKNode<T> getNext(){
   return next;
  }
  public SKNode<T> getPrev(){
   return prev;
  }
  public int getPrintPosition(){
   return printPosition;
  }

  @Override
  // To avoid NullPointerException, we check if object is null, if it is, print "null" 
  public String toString(){ 
   return (data == null ? "null" : data.toString());
  }
 }
 public class SkipHeightGen{

  private Random rand;
  private int maxLevel;
  // Ideally the probability should be somewhere between
  // 1/2, 1/3, 1/e
  private double probability;
  // For the different implementations of the 
  // SkipList, the initialLevel can either be 0(quad-node pointers) 
  // or 1(array version)
  private int initLevel;

  public SkipHeightGen(int initLevel, int maxLevel){
   this(initLevel, maxLevel, 0.0);
  }

  public SkipHeightGen(int initLevel, int maxLevel, double probability){
   this.initLevel = initLevel;
   this.maxLevel = maxLevel;
   this.probability = probability;
   rand = new Random();
  }

  public int manLevel(){
   int row = initLevel;

   // Return a number between 1 and the maxLevel
   // with a certain probability
   while((rand.nextFloat() < probability) && (row  < maxLevel)) {
    row++;
   }
   return row;
  }

  // More even distribution
  public int simpleLevel(){
   int row = initLevel;

   // Return a certain random level
   while( rand.nextBoolean()) {
    row++;
   }
   if (row >= maxLevel) {
    row--;
   }
   return row;
  }

 }

 private SKNode<T> head;
 private SKNode<T> tail;

 private SkipHeightGen random; 
 private int height;
 private int nodeCount;

 HW0304_B3_Seyoum_Christian(){
  nodeCount = 0; 
  height = 0;
  random = new SkipHeightGen(0, 16);    // maxLevel = 16;

  SKNode<T> negInf = new SKNode<>(null);
  SKNode<T> posInf = new SKNode<>(null);

  negInf.setNext(posInf);
  posInf.setPrev(negInf);

  head = negInf;
  tail = posInf;
 }

 public boolean isEmpty(){ 
  return nodeCount == 0;
 }

 public int size(){ 
  return nodeCount;
 }

 public int getHeight() { 
  return height;
 }
 
 public boolean Search(T data){ 
  if (data.compareTo(find(data).getData()) == 0)
   return true;
  return false;
 }   

 public boolean Delete(T data){ 
  if (!Search(data)) {
   return false;
  }
  SKNode<T> curPos = find(data);
  SKNode<T> prevPos = curPos.getPrev();
  while(true){
   prevPos.setNext(curPos.getNext());
   curPos.getNext().setPrev(prevPos);
   curPos.setNext(null);
   curPos.setPrev(null);
   curPos.setUp(null);
   curPos = null;
   while(prevPos.getUp() == null)
    prevPos = prevPos.getPrev();
   prevPos = prevPos.getUp();
   if (prevPos == head && prevPos.getNext() == tail)
    break;
   curPos = prevPos.getNext();
   curPos.setDown(null);   
  }
  nodeCount--;
  if ((head.getDown().getNext() == tail.getDown()))
   removeEmptyLevel();
  return true;
 }

 public boolean insert(T data){
  if (data == null) throw new IllegalArgumentException("Data cannot be null");
  SKNode<T> curPos = find(data);
  System.out.println("Predecessor: " + curPos.toString());
  if (!curPos.toString().equals("null") && curPos.getData().compareTo(data) == 0) {
   return false;
  }
  SKNode<T> toInsert = new SKNode<T>(data);
  toInsert.setNext(curPos.getNext());
  toInsert.setPrev(curPos);
  curPos.getNext().setPrev(toInsert);
  curPos.setNext(toInsert);
  System.out.println("Inserted " + data + " on lowest level") ;
  int levels = random.simpleLevel(); 
  System.out.println("Constructing : " + levels + " levels");
  if (levels > 0) {
   buildTowers(toInsert, curPos, levels);
  }
  nodeCount++;
  return true;
 }
 private SKNode<T> find(T data){
  SKNode<T> curPos = head;
  // while(curPos != null) -- Possible alteration
  /**
        while(!curPos.toString().equals("null") 
            && curPos.getData().compareTo(data) != 0){
            if (curPos.getNext().getData().compareTo(data) > 0)
                curPos = curPos.getDown();
            else
                curPos = curPos.getNext();
            if (curPos == null) break;
        }*/
  while (true){
   // Check to see you have not reached the end of the row
   // and also check to see if you have not found a node either
   // less than or equal to what you are looking for.
   while(!(curPos.getNext().toString().equals("null")) && curPos.getNext().getData().compareTo(data) <= 0) {
    curPos = curPos.getNext();
   }
   // If node is found, move down a level below
   if (curPos.getDown() != null) {
    curPos = curPos.getDown();
   }
   else {
    // On the last level, stop
    break;  
   }
  }
  return curPos;
 }
 private void buildTowers(SKNode<T> curPos, SKNode<T> prevPos, int levels){
  int initHeight = height;
  int offset;
  if (levels >= height)
   while (height <= levels)
    buildEmptyLevel();

  if (levels < height) 
   initHeight = 0;
  if (levels > initHeight)    
   offset = levels - initHeight;
  else
   offset = 1;
  for (int counter = 0; counter < offset; counter++){
   while(prevPos.getUp() == null)
    prevPos = prevPos.getPrev();
   prevPos =  prevPos.getUp();

   System.out.println("Inserting " +  curPos.getData().toString() 
     + " for Round :" + counter);
   SKNode<T> towerNode = new SKNode<T> (curPos.getData());
   towerNode.setPrev(prevPos);
   towerNode.setNext(prevPos.getNext());
   prevPos.getNext().setPrev(towerNode);
   prevPos.setNext(towerNode);
   towerNode.setDown(curPos);
   curPos.setUp(towerNode);
   curPos = towerNode;
  }       
 }

 private void buildEmptyLevel() {
  SKNode<T> tempHead = new SKNode<T>(null);
  SKNode<T> tempTail = new SKNode<T>(null);
  tempHead.setNext(tempTail);
  tempTail.setPrev(tempHead);
  tempHead.setDown(head);
  tempTail.setDown(tail);
  head.setUp(tempHead);
  tail.setUp(tempTail);
  head = tempHead;
  tail = tempTail;
  //System.out.println("\nBuilt empty level!!");
  height++;
 }

 private void removeEmptyLevel(){
  SKNode<T> tempHead = head;
  SKNode<T> tempTail = tail;
  head = head.getDown();
  tail = tail.getDown();
  head.setUp(null);
  tail.setUp(null);
  tempHead.setDown(null);
  tempTail.setDown(null);
  tempHead.setNext(null);
  tempTail.setPrev(null);
  tempHead = null;
  tempTail = null;
 }


 /**
  * Display methods
  */

 @Override
 public String toString(){
  return printHorizontally().toString();
 }

 private StringBuilder printHorizontally(){
  StringBuilder sb = new StringBuilder();
  SKNode<T> curPos = head;
  while (curPos.getDown() != null)
   curPos = curPos.getDown();

  while ( curPos != null){
   SKNode<T> pos = curPos;
   sb.append(getColumn(pos));
   sb.append("\n");
   curPos = curPos.getNext();          
  }
  return sb;
 }

 private String getColumn(SKNode<T> pos){
  String row = " ";
  while(pos != null){
   row = row + pos.toString() + " ";
   pos = pos.getUp();
  }
  return row;
 }
 public void print(){
  SKNode<T> curPos = head;
  SKNode<T> temp = curPos;

  while (curPos.getNext() == null) {
   curPos = curPos.getDown();
  }
  curPos = curPos.getDown();

  while(curPos != null) {

   temp = curPos.getNext();
   while(temp.data != null) {
    System.out.print(temp.data + " ");
    temp = temp.next;
   }
   System.out.println();
   curPos = curPos.getDown();
  }
  return;
 }
 
 public static void main (String[] args){
  System.out.println("SkipList List\n");    
  Scanner myScanner = new Scanner(System.in); 
  HW0304_B3_Seyoum_Christian<Integer> mySkipList = new HW0304_B3_Seyoum_Christian<>(); 
  try{
   System.out.print("File name: ");
   String fileName = myScanner.next();
   Scanner input = new Scanner(new File(fileName));
   
   int key = 0;
   while (input.hasNext()){
    key = input.nextInt();
    mySkipList.insert(key);
   }
   System.out.println("File read");
   input.close();

  }

  catch (FileNotFoundException sMsg){
   System.out.println("FileNotFoundException");
  }
  catch (NoSuchElementException sMsg){
   System.out.print("NoSuchElementException");
  }  System.out.println(mySkipList);
  mySkipList.print();
  char ch;
  /*  Perform list operations  */
  do
  {
   System.out.println("\nSkipList List Operations\n");
   System.out.println("1. insert");
   System.out.println("2. delete");
   System.out.println("3. search");

   int choice = myScanner.nextInt();            
   switch (choice)
   {
   case 1 : 
    System.out.print("Enter integer element to insert: ");
    mySkipList.insert( myScanner.nextInt() ); 
    /*  Display List  */ 
    System.out.println(mySkipList);
    mySkipList.print();
    break;                          
   case 2 : 
    System.out.print("Enter integer element to delete: ");
    mySkipList.Delete(myScanner.nextInt());   
    /*  Display List  */ 
    System.out.println(mySkipList);
    mySkipList.print();
    break;            
   case 3 : 
    System.out.print("Enter integer element to search: ");
    boolean search = mySkipList.Search(myScanner.nextInt());
    if(search == true) {
     System.out.println("The number you entered DOES exist\n");
    }
    else {
     System.out.println("The number you entered DOES Not exist\n");
    }
    break;                         
   default : 
    System.out.println("Wrong Entry \n ");
    break;   
   }    
  
   System.out.println("\nDo you want to continue (Type y or n) \n");
   ch = myScanner.next().charAt(0);    

  } while (ch == 'Y'|| ch == 'y');       

  System.out.println("List after operation");
  System.out.println(mySkipList);
  mySkipList.print();
  myScanner.close();
 }
}