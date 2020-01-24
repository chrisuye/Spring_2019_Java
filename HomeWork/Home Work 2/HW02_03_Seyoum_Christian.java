import java.util.*;
import java.io.*;


public class HW02_03_Seyoum_Christian
{
  public static void main(String[] args)
  {
    int num = 0;
    
    String file;
    char choice,option;
    long start=0,end=0,time=0;
    double[] Listtemp;
    double[] List;
    
    
    
    Scanner cin = new Scanner(System.in);
    do
    {
      System.out.print("do you want to create a file? Y or N");
      option = cin.next().charAt(0);
      
      if(option == 'y' || option == 'Y')
      {
        System.out.print("How many random numbers do you want? ");
        num = cin.nextInt();
        System.out.print("File name: ");
        file = cin.next();
        
        create(num,file);
        
        List = read (file,num);
        Listtemp = List;
      }
      else
      {
        System.out.print("File name: ");
        file = cin.next();
        
        List = Read(file);
        num = List.length;
        
        Listtemp = List;
      }
      
      
      System.out.println("MERGE_SORT_A");
      start = System.currentTimeMillis();
      MERGE_SORT_A(Listtemp, num);
      end = System.currentTimeMillis();
      time = end - start;
      print(num,time);
      
      Listtemp = List;
      System.out.println("MERGE_SORT_B");
      start = System.currentTimeMillis();
      MERGE_SORT_B(List);
      end = System.currentTimeMillis();
      time = end - start;
      print(num,time);
      
      Listtemp = List;
      System.out.println("MERGE_SORT_C");
      start = System.currentTimeMillis();
      MERGE_SORT_C(List,num);
      end = System.currentTimeMillis();
      time = end - start;
      print(num,time);
      
      Listtemp = List;
      System.out.println("MERGE_SORT_D");        
      start = System.currentTimeMillis();
      MERGE_SORT_D(List);
      end = System.currentTimeMillis();
      time = end - start;
      print(num,time);
      
      System.out.print("Do you want to read more files: Y or N");
      choice = cin.next().charAt(0);
    }while(choice == 'y' || choice == 'Y');
    
    cin.close();
    
  }
  
  public static void create (int num,String file)
  {
    double Rand;
    
    
    try
    {
      PrintStream ofsOutput = new PrintStream(new File(file));
      Random rand = new Random(); 
      for(int i=0; i<num; i++)
      {
        Rand = rand.nextDouble();
        ofsOutput.println(Rand);
      }
      System.out.println("file created!");
      
    }
    
    catch (FileNotFoundException sMsg)
    {
    }
  }
  
  public static double[] read(String file, int n) 
  {
    try
    {
      double[] arr = new double[n];
      
      Scanner ifsInput = new Scanner(new File(file));
      
      while (ifsInput.hasNextDouble())
      {
        
        for (int i=0; i<n; i++)
        {
          arr[i] = ifsInput.nextDouble();
        }
        
      }
      System.out.println("File read");
      ifsInput.close();
      return arr;
    }
    
    catch (FileNotFoundException sMsg)
    {
      System.out.println("FileNotFoundException");
    }
    catch (NoSuchElementException sMsg)
    {
      System.out.print("NoSuchElementException");
    }
    return null;
  }
  public static double[] Read(String file) 
  {
    try
    {
      ArrayList<Double> array = new ArrayList<Double>();
      
      
      Scanner ifsInput = new Scanner(new File(file));
      
      while (ifsInput.hasNextDouble())
      {
        array.add(ifsInput.nextDouble());
        
      }
      double[] arr = new double[array.size()];
      
      for(int i = 0; i<array.size(); i++)
      {
        arr[i] = array.get(i);
      }
      System.out.println("File read");
      ifsInput.close();
      return arr;
    }
    
    catch (FileNotFoundException sMsg)
    {
      System.out.println("FileNotFoundException");
    }
    catch (NoSuchElementException sMsg)
    {
      System.out.print("NoSuchElementException");
    }
    return null;
  }
  public static void print(int n, long time)
  {
    
    System.out.println("For "+n+" numbers, the total time to sort was: "+ time +" ms");
  }
  
  public static void MERGE_SORT_A(double[] a, int n) {
    if (n < 2) {
      return;
    }
    int mid = n / 2;
    double[] l = new double[mid];
    double[] r = new double[n - mid];
    
    for (int i = 0; i < mid; i++) {
      l[i] = a[i];
    }
    for (int i = mid; i < n; i++) {
      r[i - mid] = a[i];
    }
    MERGE_SORT_A(l, mid);
    MERGE_SORT_A(r, n - mid);
    
    Merge_A_C(a, l, r, mid, n - mid);
    
  }
  
  public static void Merge_A_C( double[] a, double[] l, double[] r, int left, int right) {
    
    int i = 0, j = 0, k = 0;
    while (i < left && j < right) {
      if (l[i] <= r[j]) {
        a[k++] = l[i++];
      }
      else {
        a[k++] = r[j++];
      }
    }
    while (i < left) {
      a[k++] = l[i++];
    }
    while (j < right) {
      a[k++] = r[j++];
    }
  }
  
  
  
  
  
  
  
  public static void MERGE_SORT_B(double[] A)
  {
    int low = 0;
    int high = A.length - 1;
    
    
    double[] temp = Arrays.copyOf(A, A.length);
    
    
    for (int m = 1; m <= high - low; m = 2*m)
    {
      
      for (int i = low; i < high; i += 2*m)
      {
        int from = i;
        int mid = Math.min(i + m - 1, A.length - 1);
        int to = Integer.min(i + 2 * m - 1, high);
        
        Merge_B_D(A, temp, from, mid, to);
      }
    }
  }
  public static void Merge_B_D(double[] A, double[] temp, int from, int mid, int to)
  {
    int k = from, i = from, j = mid + 1;
    
    
    while (i <= mid && j <= to) {
      if (A[i] < A[j]) {
        temp[k++] = A[i++];
      } else {
        temp[k++] = A[j++];
      }
    }
    
    
    while (i <= mid) {
      temp[k++] = A[i++];
    }
    
    
    for (i = from; i <= to; i++) {
      A[i] = temp[i];
    }
  }
  
  public static void MERGE_SORT_C(double[] a, int n) {
    if (n <= 25) {
      INSERTION_SORT(a);
    }
    else{
      int mid = n / 2;
      double[] l = new double[mid];
      double[] r = new double[n - mid];
      
      for (int i = 0; i < mid; i++) {
        l[i] = a[i];
      }
      for (int i = mid; i < n; i++) {
        r[i - mid] = a[i];
      }
      MERGE_SORT_C(l, mid);
      MERGE_SORT_C(r, n - mid);
      
      Merge_A_C(a, l, r, mid, n - mid);
    }
    
  }
  
  public static void MERGE_SORT_D(double[] A)
  {
    int low = 0;
    int high = A.length - 1;
    
    
    double[] temp = Arrays.copyOf(A, A.length);
    
    
    for (int m = 1; m <= high - low; m = 2*m)
    {
      
      for (int i = low; i < high; i += 2*m)
      {
        int from = i;
        int mid = Math.min(i + m - 1, A.length - 1);
        int to = Integer.min(i + 2 * m - 1, high);
        
        if (high-low <= 25) {
          INSERTION_SORT(A);
        }
        else{
          Merge_B_D(A, temp, from, mid, to);
        }
      }
    }
  }
  public static void INSERTION_SORT(double arr[])
    
  {
    
    int n = arr.length;        
    
    
    
    
    for (int i = 1; i < n; ++i) {
      
      double key = arr[i]; 
      
      int j = i - 1;
      
      while (j >= 0 && arr[j] > key) {
        
        arr[j + 1] = arr[j];
        
        j = j - 1;
        
      }
      
      arr[j + 1] = key;
      
    }
  }
  
}