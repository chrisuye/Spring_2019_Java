//Group:
//Christian Seyoum
//Nebiyu
import java.io.*;
import java.util.*;

public class Seyoum_Christian_Project
{
  public static void main(String[] args)
  {
    String estimate;
    String control;
    String bigo;
    String file;
    String temp;
    ArrayList<String> forloop = new ArrayList<String>();
    
    Scanner cin = new Scanner(System.in);
    
    System.out.print("Enter file name");
    
    file = cin.nextLine();
    try
    {
      Scanner ifsInput = new Scanner(new File(file));
      
      while (ifsInput.hasNextLine())
      {
        temp = ifsInput.nextLine();
        temp = temp.replace(" ","");
        forloop.add(temp);
        
      }
      estimate = forloop.remove(0);
      control = forloop.remove(0);
      forloop.remove(0); 
      
      if (checknest(forloop))
      {
        
        bigo = single(forloop,estimate,control,0);
        
        print(bigo, file);
      }
      else
      {
        dependency(forloop, control, estimate, file);
        
      }
      
      
      cin.close();
      ifsInput.close();
    }
    
    catch(FileNotFoundException sMsg)
    {
      System.out.print("File not found");
    }
    
  }
  //Check if the code has a nested for loop
  public static boolean checknest ( ArrayList<String> forloop)
  {
    //refer to this code
    int check = 0;
    for(int i = 0; i < forloop.size(); i++)
    {
      if ( forloop.get(i).length() > 2)
      {
        if( forloop.get(i).substring(0,3).equals("for"))
        {
          check++;
        }
      }
    }
    if(check > 1)
    {
      return false;
    }
    else
    {
      return true;
    }
  }
  //used to get the big O for both single and independent forloops
  public static String single(ArrayList<String> forloop, String estimate,String control, int n)
  {
    String contemp = "";
    String big = "";
    String left = "";
    int count = -1;
    
    for( int con = n; con < forloop.size(); con++)
    {
      if(forloop.get(con).length() > 2)
      {
        if( forloop.get(con).substring(0,3).equals("for"))
        {
          count++;
          
          if( n == count)
          {
            n = con;
            
            break;
          }
        }
      }
    }
    
    contemp = forloop.get(n);
    
    contemp = contemp.substring(contemp.indexOf(";")+1, contemp.length()-1);
    
    //work on contemp
    
    for(int y=0; y<contemp.length() - 1; y++)
    {
      if(contemp.substring(y,y+1).equals("<") || contemp.substring(y,y+1).equals(">"))
      {
        left = contemp.substring(0,y);
        y++;
        if(contemp.substring(y,y+1).equals("="))
        {
          y++;
        }
        big = contemp.substring(y,contemp.indexOf(";"));
        
        contemp = contemp.substring(contemp.indexOf(";")+1, contemp.length()-1);
        
        
        y = contemp.length();
      }
    }
    
    
    
    
    for(int c=0; c<left.length()-1; c++)
    {
      
      if(left.substring(c,c+1).equals("*"))
      {
        big = "("+big+")"+"^(1/2)";
        c=left.length();
      }
      else if(left.substring(c,c+1).equals("^"))
      {
        big = "("+big+")"+"^(1/"+left.substring(c+1,c+2)+")";
        c=left.length();
      }
    }
    
    for(int i=0; i<forloop.get(n).length();i++)
    {
      if(forloop.get(n).substring(i,i+1).equals("+") || forloop.get(n).substring(i,i+1).equals("-"))
      {
        return big;
      }
    }
    
    big = "log("+big+")";
    return big;
  }
  
  public static void dependency(ArrayList<String> forloop, String control, String estimate, String file)
  {
    String temp1 = "";
    String temp2 = "";
    String controltemp;
    int index;
    int check = 0;
    int flag=0;
    
    for(int i = 0; i < forloop.size(); i++)
    {
      if ( forloop.get(i).length() > 2)
      {
        if( forloop.get(i).substring(0,3).equals("for"))
        {
          check++;
        }
        
        if(check == 1)
        {
          index = forloop.get(i).indexOf("(");
          
          
          temp1 = forloop.get(i).substring(index+1,index+2);
          temp1 = temp1.replace(";","");
          
        }
        else if(check == 2)
        {
          index = forloop.get(i).indexOf("(");
          temp2 = forloop.get(i).substring(index+1,forloop.get(i).length());
          
          break;
        }
      }
    }
    
    
    
    
    
    if(temp1.equals(control.substring(0,1)))
    {
      controltemp = control.substring(0,1);
      
    }
    else
    {
      controltemp = control.substring(1,2);
      
    }
    
    for(int i =0; i<temp2.length(); i++)
    {
      if(temp2.substring(i,i+1).equals(controltemp))
      {
        
        flag++;
      }
    }
    
    if(flag == 0)
    {
      
      independent(forloop, file, control, estimate);
    }
    else
    {
      System.out.println("This program can not handle dependent forloop, as per the teachers instruction.");
    }
    
  }
  
  public static void independent(ArrayList<String> forloop, String file, String control, String estimate)
  {
    String bigo;
    
    bigo = single(forloop, estimate, control, 0) + "*" + single(forloop, estimate, control, 1);
    
    print(bigo,file);
  }
  
  public static void print(String bigo, String file)
  {
    file = file.replace(".txt","");
    String output = file+"_BigO.txt";
    try
    {
      PrintStream ofsOutput = new PrintStream(new File(output));
      ofsOutput.println("O("+bigo+")");   
      
      System.out.println("File name, "+output+", has been created.");
    }
    
    catch (FileNotFoundException sMsg)
    {
      System.out.print("File not Found");
    }
    
    
  }
}


