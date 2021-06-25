package lock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class hash {
	
	public static void main (String[]args) throws IOException, ClassNotFoundException
	{
		
		
		HashMap<String, Node> hmap = new HashMap<String, Node>();

	     Node r=new Node(1.3, 1, "d", "e");
	      hmap.put("shit", r);
	      System.out.println(hmap.get("shit").getName());
	      System.out.println("---------------------");
	      hash p=new hash();
	      p.saveobj(hmap);
	      
	      HashMap<String, Node> hmap2 = new HashMap<String, Node>();
	      hmap2=(HashMap<String, Node>) p.readobj();
	      System.out.println(hmap2.get("shit").getName());
		
	}
	public hash() 
	{
		
	}
	public void saveobj(Object obj) throws IOException
	{
		FileOutputStream f = new FileOutputStream(new File("myObjects"));
		ObjectOutputStream o = new ObjectOutputStream(f);

		// Write objects to file
		o.writeObject(obj);
	

		o.close();
		f.close();
	}
	public Object readobj () throws IOException, ClassNotFoundException
	{
		FileInputStream fi = new FileInputStream(new File("myObjects"));
		ObjectInputStream oi = new ObjectInputStream(fi);
		return oi.readObject();
	}

}
