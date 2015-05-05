/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package filemanager;

import filecontroller.FileMangerLoader;
import filecontroller.FileMangerSaver;

import java.util.ArrayList;
import java.util.Scanner;
import memory.ContegeousMemory;
import memory.IndexedMemory;

/**
 *
 * @author Ahmed Abdelrheem
 */
public class Main {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		FileManager fm = null;
		String mangerName;
		int mangerSize;

		ArrayList<User>users = new ArrayList<User>();
		users.add(new User("admin", "admin"));
		User activeUser = users.get(0);
		activeUser.addDirectory("root", "11");
		
		String choice1; //for string input
		int choice2; //for integer input

		System.out.println("1-Contiguous");
		System.out.println("2-Indexing");

		choice2 = input.nextInt();

		if(choice2 == 2)
			fm = new FileManager("indexing", new IndexedMemory(1000));

		else
			fm = new FileManager("cont", new ContegeousMemory(1000));

		System.out.println("_______________________________________________");
		System.out.println("DisplayDiskStatus");
		System.out.println("DisplayDiskStructure");
		System.out.println("CreateFile <file path> <file size>");
		System.out.println("CreateFolder <directory path>");
		System.out.println("DeleteFile <file path> ");
		System.out.println("DeleteFolder <directory path> ");

		System.out.println("TellUser");
		System.out.println("CreateUser");
		System.out.println("DeleteUser"); 
		System.out.println("Grant");
		System.out.println("Login");
		System.out.println("Show_All");
		System.out.println("logout");
		

		System.out.println("exit");
		System.out.println("_______________________________________________");
		String command;

		command = "123";

		while (!command.equals("exite")) {
			command = input.nextLine();
			String cmd[] = command.split("\\s+");

			// updated commands 
			if (cmd[0].equals("DisplayDiskStatus")) {
				fm.displayDiskStatus();

			} else if (cmd[0].equals("DisplayDiskStructure")) {
				fm.displayDiskStructure();
			}

			else if (cmd[0].equals("CreateFile") && cmd.length == 3) {
				// if user not admin
				if(!activeUser.name.equals("admin"))
				{
					String access = activeUser.getAccess(cmd[1]);

					if(!hasCreateAccess(access))
					{
						System.out.println("You don't have access to create this file");
						continue;
					}
				}
				//// old part
				if (fm.createFile(cmd[1], Integer.parseInt(cmd[2])) == true) {
					System.out.println("Created Successfuly");
				} else {
					System.out.println("Failed");
				}
			} 

			else if (cmd[0].equals("CreateFolder") && cmd.length == 2) {
				// if user not admin
				if(!activeUser.name.equals("admin"))
				{
					String access = activeUser.getAccess(cmd[1]);

					if(!hasCreateAccess(access))
					{
						System.out.println("You don't have access to create this folder");
						continue;
					}

				}
				//// old part
				if (fm.createDirectory(cmd[1]) == true) {
					System.out.println("Created Successfuly");
				} else {
					System.out.println("Failed");
				}

			} 

			else if (cmd[0].equals("DeleteFile") && cmd.length == 2) {				
				// if user not admin
				if(!activeUser.name.equals("admin"))
				{
					String access = activeUser.getAccess(cmd[1]);

					if(!hasDeleteAccess(access))
					{
						System.out.println("You don't have access to delete this file");
						continue;
					}

				}

				if (fm.deleteFile(cmd[1])) {
					System.out.println("Deleted Successfuly");
				} else {
					System.out.println("Failed");
				}
			} 


			else if (cmd[0].equals("DeleteFolder") && cmd.length == 2) {
				// if user not admin
				if(!activeUser.name.equals("admin"))
				{
					String access = activeUser.getAccess(cmd[1]);

					if(!hasDeleteAccess(access))
					{
						System.out.println("You don't have access to delete this folder");
						continue;
					}

				}
				// old part
				if (fm.deleteDirectory(cmd[1]) == true) {
					System.out.println("Deleted Successfuly");
				} else {
					System.out.println("Failed");
				}
			} 

			else if (cmd[0].equals("exit")) {
				break;
			}
			////////////////////////////////////////////////////////////////////////
			///////////////////////////////////////////////////////////////////////
			// new commands
			else if(cmd[0].equalsIgnoreCase("TellUser")){
				System.out.println("Active user is: " + activeUser.name);    	
			}

			else if(cmd[0].equalsIgnoreCase("CreateUser") && cmd.length == 3){
				// if not admin continue this command will not be executed
				if(! activeUser.name.equals("admin"))
				{
					System.out.println("It is required to be admin");
					continue;
				}
				// user is admin 

				if(getUser(users, cmd[1]) == null)
				{
					users.add(new User(cmd[1], cmd[2]));
					System.out.println("User created successfully");
				}
				else
					System.out.println("username already exitsts");


			}
			//////////////
			else if(cmd[0].equalsIgnoreCase("DeleteUser") && cmd.length == 2){
				// if not admin continue this command will not be executed
				if(! activeUser.name.equals("admin"))
				{
					System.out.println("It is required to be admin");
					continue;
				}
				// user is admin 


				if(deleteUser(users, cmd[1]))
					System.out.println("User deleted successfully");
				else
					System.out.println("There is no such user with this name");


			}
			////////////
			else if(cmd[0].equalsIgnoreCase("Grant") && cmd.length == 4){
				// if not admin continue this command will not be executed
				if(! activeUser.name.equals("admin"))
				{
					System.out.println("It is required to be admin");
					continue;
				}
				// user is admin

				User user = getUser(users, cmd[1]);

				if(user == null)
				{
					System.out.println("not a valid user");
					continue;
				}
				// split directory path and search if the directory exists
				if(fm.getDirectory(cmd[2].split("/"), fm.root) == null)
				{
					System.out.println("Directory not found");
					continue;
				}

				if(!validAccess(cmd[3]))
				{
					System.out.println("This is not a valid access, valid access is 00, 01, 10, 11");
					continue;
				}
				user.addDirectory(cmd[2], cmd[3]);
				System.out.println("user granted");



			}
			////////////////
			else if(cmd[0].equalsIgnoreCase("Show_All")){
				// if not admin continue this command will not be executed
				if(! activeUser.name.equals("admin"))
				{
					System.out.println("It is required to be admin");
					continue;
				}
				// user is admin
				for (int i = 0; i < users.size(); i++) {

					System.out.println("username is: "+ users.get(i).name);
					System.out.println("capabilities");
					ArrayList<String>dirs = users.get(i).directories;
					ArrayList<String>access = users.get(i).access;

					for(int j=0;j<dirs.size();j++)
					{
						System.out.println("Directory: " + dirs.get(j) + " access: " + access.get(j));
					}

					System.out.println("____________________________________");
				}

			}
			/////////////////
			else if(cmd[0].equalsIgnoreCase("Login") && cmd.length == 3){

				User user = getUser(users, cmd[1]);
				if(user == null)
				{
					System.out.println("wrong username");
					continue;
				}

				if(!user.password.equals(cmd[2]))
				{
					System.out.println("wrong password");
					continue;
				}

				activeUser = user;
				System.out.println("You are logged in");

			}
			
			else if(cmd[0].equalsIgnoreCase("logout") ){			
				activeUser = getUser(users, "admin");
			}
								
			else {
				System.out.println("Enter valid command");

			}
			//FileMangerSaver.save(fm);
			System.out.println("_______________________________________________");
			
		}
	}
	///////////////////////////////////////////////////////
	public static User getUser(ArrayList<User>users, String username)
	{
		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).name.equals(username))
				return users.get(i);
		}

		return null;
	}

	/////////////////////////////////////
	public static boolean deleteUser(ArrayList<User>users, String username)
	{
		for (int i = 0; i < users.size(); i++) {
			if(users.get(i).name.equals(username))
			{
				users.remove(i);
				return true;
			}
		}

		return false;
	}
	///////////////
	public static boolean validAccess(String s)
	{
		if(s.length() !=2)
			return false;

		if( (s.charAt(0) != '1' && s.charAt(0) != '0' )|| (s.charAt(1) != '1' && s.charAt(1) != '0') )
			return false;

		return true;
	}

	public static boolean hasCreateAccess(String s)
	{
		if(s ==null)
			return false;

		if(s.charAt(0)!= '1')
			return false;

		return true;
	}

	public static boolean hasDeleteAccess(String s)
	{
		if(s ==null)
			return false;

		if(s.charAt(1)!= '1')
			return false;

		return true;
	}

}
