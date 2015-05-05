package filemanager;

import java.util.ArrayList;

public class User {
	public String name;
	public String password;
	
	public ArrayList<String>directories;
	public ArrayList<String>access;
	
	public User(String name, String password) {
		super();
		this.name = name;
		this.password = password;
		
		directories = new ArrayList<String>();
		access = new ArrayList<String>();
	}
	///////////////////////////
	
	public void addDirectory(String n, String a)
	{
		directories.add(n);
		access.add(a);
		
	}
	
	///////////////////////////////////////////////
	
	public String getAccess(String dir)
	{
		for (int i = 0; i < directories.size(); i++) {
			if(dir.contains(directories.get(i)))
				return access.get(i);
		}
		return null;
	}
	///////////////////////////////////////////////
	
}
