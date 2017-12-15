package main.java.net.dv8tion;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class MainBot extends ListenerAdapter 
{
	public static JDA epi; 
	boolean mute = false; 
	boolean logging = false; 
	PointCounter points = new PointCounter();
	RPLogger log = new RPLogger();
	
	
	public static void main (String [] args) 
			throws LoginException, IllegalArgumentException, InterruptedException, RateLimitedException
	{
			epi = new JDABuilder(AccountType.BOT).setToken(Ref.Token).buildBlocking();
			epi.getPresence().setGame(Game.of("BETA KITTY BOT"));
			epi.addEventListener(new MainBot());
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		String line = event.getMessage().getContent();
		String [] command;
		
		if(line.equalsIgnoreCase(("sudo make me a sandwich")))
		{
			event.getChannel().sendMessage("Make your own damn sandwich!").queue();
		}
		
		if(logging)
		{
			if((line.charAt(0) == '_' && line.charAt(line.length()-1) == '_') ||(line.charAt(0) == '*' && line.charAt(line.length()-1) == '*'))
			{
				line = line.substring(1,line.length()-1);
			}
			log.addLine(event.getAuthor().getName() + " " + line,event.getChannel().getName());
		}
		
		points.addPoints(event.getAuthor().getId(), 1);
		
		
		//checks for ! 
		if(line.length() < 1)
		{
			return;
		}
		if(!(line.charAt(0) == Ref.trigger) || mute)
		{
			if(mute)
			{
				if(event.getAuthor().getName().equals("RinTheSnowMew") && line.equalsIgnoreCase("!unmute"))
				{
					mute = false;
				}
			}
			return;
		}
		
		
		//documents commands
		System.out.println(event.getAuthor().getName() +"'s touching me in " +event.getChannel().getName() +": " + line);
		command = line.split(" ");
		
		//Ignores herself
		if(event.getAuthor().getName().equals("KittyBot"))
			return;
		
		
		//POST COUNTER STUFF 
		if(command[0].equalsIgnoreCase("!points"))
		{
			event.getChannel().sendMessage(event.getAuthor().getAsMention() + " you have " + Integer.toString(points.getPoints(event.getAuthor().getId())) + " beans!").queue();
		}
		
		if(command[0].equalsIgnoreCase("!writetofile"))
		{
			if(event.getAuthor().getName().equals("RinTheSnowMew"))
			{
				try {
					points.writeToFile();
					event.getChannel().sendMessage("Saved successfully").queue();
				} catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) 
				{
					e.printStackTrace();
				}
			}
		}
		
		//RP Log
		if(command[0].equalsIgnoreCase("!rpStart"))
		{
			if(event.getAuthor().getName().equals("RinTheSnowMew"))
			{
				logging = true; 
				log.switchChan(event.getChannel().getName());
				event.getChannel().sendMessage("Logging Started").queue();
			}
		}
		
		if(command[0].equalsIgnoreCase("!rpend") && logging)
		{
			if(event.getAuthor().getName().equals("RinTheSnowMew"))
			{
				logging = false;
				try 
				{
					if(event.getMessage().getContent().length() <= 7)
					{
						event.getChannel().sendFile(log.writeFile("RPLOG", event.getChannel().getName()),"RpLog", null).queue();
					}
					else
					{
						event.getChannel().sendFile(log.writeFile(event.getMessage().getContent().substring(7), event.getChannel().getName()), 
								event.getMessage().getContent().substring(7), null).queue();
					}
					
					event.getChannel().sendMessage("Test successful").queue();
				} catch (FileNotFoundException e) 
				{
					e.printStackTrace();
				} catch (UnsupportedEncodingException e) 
				{
					e.printStackTrace();
				}
//				event.getChannel().sendFile("test.txt", "");
			}
		}
		
		
		//RANDOM CALL & RESPONSE
		if(command[0].equalsIgnoreCase("!ping"))
		{
			event.getChannel().sendMessage("Boop!").queue();
			return;
		}
		
		if(command[0].equalsIgnoreCase("!boop"))
		{
			if(command.length == 1)
			{
				event.getChannel().sendMessage("*Boops " + event.getAuthor().getAsMention() +"*").queue();
			}
			else
			{
				event.getChannel().sendMessage("*Boops " + command[1] + "*").queue();
			}
			return;
		}
		
		
		//Method call and response 
		if(command[0].equalsIgnoreCase("!roll"))
		{
			event.getChannel().sendMessage(rollDice(command)).queue();
		}
		
		if(command[0].equalsIgnoreCase("!choose"))
		{
			event.getChannel().sendMessage(choose(line)).queue();
		}
		
		if(command[0].equalsIgnoreCase("!mute"))
		{
			if(event.getAuthor().getName().equals("RinTheSnowMew"))
			{
				mute = true;
				return;
			}
			event.getChannel().sendMessage("You can't tell me what to do!").queue();
			return;
		}
		
		if(command[0].equalsIgnoreCase("!listMembers"))
		{
			if(event.getAuthor().getName().equals("RinTheSnowMew"))
			{
//				String line2 = event.getAuthor().getId();
//				event.getChannel().sendMessage("<@"+line2+">").queue();
				String members = "";
				for(int i = 0; i < event.getChannel().getMembers().size(); i ++)
				{
					members += event.getChannel().getMembers().get(i).getUser().getId() + ", " + event.getChannel().getMembers().get(i).getUser().getName() + ";";
					if (members.length() > 1900)
					{
						event.getChannel().sendMessage(members).queue();
						members = "";
					}	
				}
				event.getChannel().sendMessage(members).queue();
				return;
			}
			event.getChannel().sendMessage("You can't tell me what to do!").queue();
			return;
		}
		
		if(command[0].equalsIgnoreCase("!listRoles"))
		{
			String roles = "";
			for(int i = 0; i < event.getChannel().getMembers().size(); i ++)
			{
				roles += event.getGuild().getRoles().get(i).getIdLong() + "\n";
				if (roles.length() > 1900)
				{
					event.getChannel().sendMessage(roles).queue();
					roles = "";
				}	
			}
			event.getChannel().sendMessage(roles).queue();
			return;
		}

		
	}
	
	/*
	 * (non-Javadoc)
	 * @see net.dv8tion.jda.core.hooks.ListenerAdapter#onPrivateMessageReceived(net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent)
	 * Sees private message and repeats it to them, documents message and author in console
	 */
	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event)
	{
		if(event.getAuthor().getName().equals("KittyBot"))
			return;
		if(event.getAuthor().getName().equals("RinTheSnowMew"))
		{
			
		}
		System.out.println(event.getAuthor().getName()+"'s touching me");		
	}
	
	/*
	 * Chooses a random answer out of a given list
	 */
	public String choose(String line)
	{
		line = line.substring(7);
		String [] choices = line.split(",");
		if(choices.length == 1)
		{
			return "I can't choose from *one* thing!";
		}
		
		return choices[(int) (Math.random()*choices.length)];
	}
	
	/*
	 * Takes the command for !roll and returns the string containing the rolls and the total
	 */
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
	    if (str == null) {
	        return false;
	    }
	    int length = str.length();
	    if (length == 0) {
	        return false;
	    }
	    int i = 0;
	    if (str.charAt(0) == '-') {
	        if (length == 1) {
	            return false;
	        }
	        i = 1;
	    }
	    for (; i < length; i++) {
	        char c = str.charAt(i);
	        if (c < '0' || c > '9') {
	            return false;
	        }
	    }
	    return true;
	}
}
