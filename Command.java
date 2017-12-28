package main.java.net.dv8tion;

import java.util.ArrayList;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;

public class Command 
{
	ArrayList <String> mute = new ArrayList <String>(); 
	boolean logging = false; 
	PointCounter points = new PointCounter();
	RPLogger log = new RPLogger();
	IgnoreList ignored = new IgnoreList();
	AuthList authed = new AuthList();
	
	public Command()
	{
		
	}
	
	public String comSent(Message message, Member member)
	{
		
		String [] command = message.getContent().split(" ");
		
		if(!command[0].startsWith("!"))
		{
			return "";
		}
		
		//Start of Dev command
		if(command[0].equals("!write") && message.getAuthor().getName().equals("RinTheSnowMew"))
		{
			
		}
		
		//Start of Mod commands
		if(isAuth(member,message.getGuild().getId()) 
				|| message.getAuthor().getName().equals("RinTheSnowMew"))
		{
			//Mutes and unmutes bot
			if(command[0].equals("!mute"))
			{
				mute.add(message.getGuild().getId());
				return "Being quiet now.";
			}
			if(command[0].equals("!unmute"))
			{
				if(mute.contains(message.getGuild().getId()))
				{
					mute.remove(message.getGuild().getId());
				}
				return "I can talk again!";
			}
			
			//Adds points to user
			if(command[0].equalsIgnoreCase("!addpoints"))
			{
				points.addPoints(message.getMentionedUsers().get(0).getId(),
						message.getGuild().getId(), Integer.parseInt(command[2]));
				
			}
			
			//Removes points from user
			if(command[0].equalsIgnoreCase("!removepoints"))
			{
				points.subPoints(message.getMentionedUsers().get(0).getId(),
						message.getGuild().getId(), Integer.parseInt(command[2]));
				
			}
			
			//Ignore & un-ignore separate users
			if(command[0].equalsIgnoreCase("!ignore"))
			{
				ignored.addName(message.getMentionedUsers().get(0).getId(), message.getGuild().getId()); 
				return "Ignored " + message.getMentionedUsers().get(0).getName();
			}
			if(command[0].equalsIgnoreCase("!unignore"))
			{
				ignored.addName(message.getMentionedUsers().get(0).getId(), message.getGuild().getId()); 
				return "Unignored " + message.getMentionedUsers().get(0).getName();
			}
			
			//Ignore & un-ignore separate roles
			if(command[0].equalsIgnoreCase("!ignorerole"))
			{
				ignored.addRole(message.getMentionedRoles().get(0).getId(), message.getGuild().getId()); 
				return "Ignored " + message.getMentionedRoles().get(0).getName();
			}
			if(command[0].equalsIgnoreCase("!unignorerole"))
			{
				ignored.addRole(message.getMentionedRoles().get(0).getId(), message.getGuild().getId()); 
				return "Unignored " + message.getMentionedRoles().get(0).getName();
			}
		}
		
		//Start of non-mod commands
		System.out.println("Checking person");
		if(mute || isIgnore(member, message.getGuild().getId()))
		{
			System.out.println("stuff");
			return "";
		}
		
		if(command[0].equals("!boop"))
		{
			return "Success!";
		}
		
		//Method call and response 
		
		if(command[0].equalsIgnoreCase("!roll"))
		{
			return rollDice(command);
		}
		
		if(command[0].equalsIgnoreCase("!choose"))
		{
			return choose(message.getContent());
		}
		return ""; 
	}
	
	
	
	private boolean isAuth(Member person, String server)
	{
		if(authed.checkName(person.getUser().getId(),server) 
				|| authed.checkRole(person.getRoles(),server))
		{
			return true; 
		}
		return false; 
	}
	
	private boolean isIgnore(Member person, String server)
	{
		if(ignored.checkName(person.getUser().getId(),server) 
				|| ignored.checkRole(person.getRoles(),server))
		{
			return true; 
		}
		return false; 
	}
	
	public String choose(String line)
	{
		line = line.substring(7);
		String [] choices = line.split(",");
		if(choices.length == 1)
		{
			return "I can't choose from *one* thing!";
		}
		
		return "I chooooooose " + choices[(int) (Math.random()*choices.length)] + "!";
	}
	
	
	public String rollDice(String [] command)
	{
		int numofdice = 0;
		int dicesize = 0;
		String dicenum [] = command[1].split("d");
		if(!(isInteger(dicenum[0]) && isInteger(dicenum[1])))
		{
			
			return "Enter actual numbers please.";
		}
		numofdice = Integer.parseInt(dicenum[0]);
		dicesize = Integer.parseInt(dicenum[1]);
		int total = 0;
		int roll = 0;
		String nums = "";
		//checks for lower than 1 dice or dice size 
		if(numofdice < 1 || dicesize < 1 || numofdice > 100 || dicesize > 100)
		{
			return "*drowns in dice*";
		}
		for(int i = 0; numofdice > i; i ++)
		{
			roll = (int) (Math.random()*dicesize) + 1;
			nums += roll + " ";
			total += roll;
		}
		return "Rolls are: " + nums + "\n" + "Total is: " + total;
	}
	
	/*
	 * checks if String is Int if not returns false 
	 */
	public static boolean isInteger(String str) 
	{
	    if (str == "") 
	    {
	        return false;
	    }
	    int length = str.length();
	    if (length == 0) {
	        return false;
	    }
	    int i = 0;
	    if (str.charAt(0) == '-') 
	    {
	        if (length == 1) {
	            return false;
	        }
	        i = 1;
	    }
	    for (; i < length; i++) {
	        char c = str.charAt(i);
	        if (c < '0' || c > '9') 
	        {
	            return false;
	        }
	    }
	    return true;
	}
}
