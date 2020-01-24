//Christian, Nebiyu and jessica
import java.util.*;
import java.io.*;
public class HW0304_B2_Seyoum_Christian
{ 
  
  public static void main(String[] args) 
  {
    HW0304_B2_Seyoum_Christian red = new HW0304_B2_Seyoum_Christian();   
    red.file();
  }
  
  private class Node 
  {
    
    int key, color = BLACK;
    Node left = nullNode, right = nullNode, parent = nullNode;
    
    Node(int key) 
    {
      this.key = key;
    }  
  }
  private final int RED = 0;
  private final int BLACK = 1;
  private final Node nullNode = new Node(0); 
  private Node root = nullNode;
  
  
  private void insert(Node nodeAux) 
  {
    Node temp = root;
    if (root == nullNode) 
    {
      root = nodeAux;
      nodeAux.color = BLACK;
      nodeAux.parent = nullNode;
    } 
    else 
    {
      nodeAux.color = RED;
      while (true) 
      {
        if (nodeAux.key < temp.key) 
        {
          if (temp.left == nullNode) 
          {
            temp.left = nodeAux;
            nodeAux.parent = temp;
            break;
          } 
          else 
          {
            temp = temp.left;
          }
        } 
        else if (nodeAux.key >= temp.key) 
        {
          if (temp.right == nullNode) 
          {
            temp.right = nodeAux;
            nodeAux.parent = temp;
            break;
          } 
          else 
          {
            temp = temp.right;
          }
        }
      }
      fixTree(nodeAux);
    }
  }
  
  
  private void fixTree(Node node) 
  {
    while (node.parent.color == RED) 
    {
      Node uncle = nullNode;
      if (node.parent == node.parent.parent.left) 
      {
        uncle = node.parent.parent.right;
        
        if (uncle != nullNode && uncle.color == RED) 
        {
          node.parent.color = BLACK;
          uncle.color = BLACK;
          node.parent.parent.color = RED;
          node = node.parent.parent;
          continue;
        } 
        if (node == node.parent.right) 
        {
          
          node = node.parent;
          rotateLeft(node);
        } 
        node.parent.color = BLACK;
        node.parent.parent.color = RED;
        
        rotateRight(node.parent.parent);
      } 
      else 
      {
        uncle = node.parent.parent.left;
        if (uncle != nullNode && uncle.color == RED)
        {
          node.parent.color = BLACK;
          uncle.color = BLACK;
          node.parent.parent.color = RED;
          node = node.parent.parent;
          continue;
        }
        if (node == node.parent.left) 
        {
          
          node = node.parent;
          rotateRight(node);
        }
        node.parent.color = BLACK;
        node.parent.parent.color = RED;
        
        rotateLeft(node.parent.parent);
      }
    }
    root.color = BLACK;
  }
  
  void rotateLeft(Node node)
  {
    if (node.parent != nullNode) 
    {
      if (node == node.parent.left) 
      {
        node.parent.left = node.right;
      } 
      else 
      {
        node.parent.right = node.right;
      }
      node.right.parent = node.parent;
      node.parent = node.right;
      if (node.right.left != nullNode) 
      {
        node.right.left.parent = node;
      }
      node.right = node.right.left;
      node.parent.left = node;
    } 
    else 
    {
      Node right = root.right;
      root.right = right.left;
      right.left.parent = root;
      root.parent = right;
      right.left = root;
      right.parent = nullNode;
      root = right;
    }
  }
  
  void rotateRight(Node node) 
  {
    if (node.parent != nullNode)
    {
      if (node == node.parent.left)
      {
        node.parent.left = node.left;
      } 
      else
      {
        node.parent.right = node.left;
      }
      
      node.left.parent = node.parent;
      node.parent = node.left;
      if (node.left.right != nullNode) 
      {
        node.left.right.parent = node;
      }
      node.left = node.left.right;
      node.parent.right = node;
    } 
    else 
    {
      Node left = root.left;
      root.left = root.left.right;
      left.right.parent = root;
      root.parent = left;
      left.right = root;
      left.parent = nullNode;
      root = left;
    }
  }
  
  
  public int height() 
  { 
    return height(root); 
  }
  private static int height(Node x) 
  {
    if (x == null) return -1;
    return 1 + Math.max(height(x.left), height(x.right));
  }
  
  static void printLevelOrder(Node node) 
  {  
    int height = height(node);   
    
    for (int i=1; i<=height; i++) 
    { 
      printLeveOrderAux(node, i);   
      System.out.println(); 
    } 
  } 
  
  static void printLeveOrderAux(Node node, int level) 
  {  
    if (node == null) 
    {
      return;
    }
    if (level == 1) 
    {
      if(node.parent == null) 
      {
        
      }
      else if (node.parent.key == 0)
      {
        System.out.print("("+node.key+", null)");
      }
      else 
      {
        if (node.color == 0) 
        {
          node.key = node.key *(-1); 
        } 
        if(node.key != 0) 
        {
          System.out.print("("+node.key+", " + node.parent.key+")"); 
        }
      }
    }
    else if (level > 1) 
    { 
      printLeveOrderAux(node.left, level-1); 
      printLeveOrderAux(node.right, level-1); 
    } 
  } 
  
  public void file() 
  {
    String fname;
    char check;
    Scanner cin = new Scanner(System.in); 
    int key;
    Node node;      
    System.out.print("Enter File name: ");
    fname = cin.nextLine();
    try
    {
      Scanner ifsInput = new Scanner(new File(fname));
      while (ifsInput.hasNextLine())
      {
        key = ifsInput.nextInt();
        node = new Node(key);
        insert(node);
        
      }
      printLevelOrder(root);
      
      System.out.print("Do you want to insert another number? y/n: ");
      
      check = cin.next().charAt(0);
      while(check == 'y' || check == 'Y')
      {
        System.out.print("Input number: ");
        key = cin.nextInt();
        node = new Node(key);
        insert(node);
        
        printLevelOrder(root);
        System.out.print("Do you want to insert another number? y/n: ");
        
        check = cin.next().charAt(0);
      }
      
      cin.close();
      ifsInput.close();
      
    }
    catch(FileNotFoundException sMsg)
    {
      System.out.println("File not found");
    }
    
    
  }
  
  
  
  
  
  
}