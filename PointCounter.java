package main.java.net.dv8tion;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PointCounter 
{
	HashMap<String, Integer> pointmap = new HashMap<String, Integer>();
	public PointCounter() 
	{
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("KiraNums.txt")));
		    String line;
		    String [] person;
		    while ((line = br.readLine()) != null) 
		    {
		        person = line.split(" ");
		        pointmap.put(person[0], Integer.parseInt(person[1]));
		    }
		    br.close();
		} catch (FileNotFoundException e) 
		{
			System.out.println("file not found");
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void addPoints(String user, int num)
	{
		if(pointmap.containsKey(user))
		{
			pointmap.put(user, pointmap.get(user)+num);
		}
		else
		{
			pointmap.put(user, num);
		}
	}
	
	public int getPoints(String user)
	{
		if(pointmap.containsKey(user))
		{
			return pointmap.get(user);
		}
		return 0;
	}
	
	//Mod Only
	public void subPoints(String user, int num)
	{
		pointmap.put(user, pointmap.get(user)-num);
	}
	
	public void writeToFile() throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter writer = new PrintWriter("KiraNums.txt", "UTF-8");
		Set set = pointmap.entrySet(); 
		Iterator iterator = set.iterator();
		while(iterator.hasNext()) 
		{
	         @SuppressWarnings("rawtypes")
			Map.Entry mentry = (Map.Entry)iterator.next();
	        writer.println(mentry.getKey() + " " + mentry.getValue());
	    }
		writer.close();
		
	}
	
	
}
