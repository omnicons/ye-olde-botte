package main.java.net.dv8tion;

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
//	IgnoreList ignored = new IgnoreList();
//	AuthList authed = new AuthList(); 
	Command command = new Command(); 
	
	
	public static void main (String [] args) 
			throws LoginException, IllegalArgumentException, InterruptedException, RateLimitedException
	{
			epi = new JDABuilder(AccountType.BOT).setToken(Ref.Token).buildBlocking();
			epi.getPresence().setGame(Game.listening("BETA KITTY BOT"));
			epi.addEventListener(new MainBot());
	}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		//Ignores herself
		if(event.getAuthor().getName().equals("KittyBot"))
			return;
		
		String message = command.comSent(event.getMessage(), event.getMember());
		if(!message.equals(""))
		{
			event.getChannel().sendMessage(message).queue();
		}
		
//		String line = event.getMessage().getContent();
//		String [] command;
//		String [] words;
//		if(line.equalsIgnoreCase(("sudo make me a sandwich")))
//		{
//			event.getChannel().sendMessage("Make your own damn sandwich!").queue();
//		}
//		
//		
//		if(logging)
//		{
//			if((line.charAt(0) == '_' && line.charAt(line.length()-1) == '_') ||(line.charAt(0) == '*' && line.charAt(line.length()-1) == '*'))
//			{
//				line = line.substring(1,line.length()-1);
//			}
//			log.addLine(event.getAuthor().getName() + " " + line,event.getChannel().getName());
//		}
//		if(event.getGuild().getName().equals("Meeples Peeples"))
//		{
//			points.addPoints(event.getAuthor().getId(),event.getGuild().getId(), 1);
//		}
//		
//		
//		
//		//checks for ! 
//		if(line.length() < 1)
//		{
//			return;
//		}
//		if(!(line.charAt(0) == Ref.trigger) || mute)
//		{
//			if(mute)
//			{
//				if(event.getAuthor().getName().equals("RinTheSnowMew") && line.equalsIgnoreCase("!unmute"))
//				{
//					mute = false;
//				}
//			}
//			return;
//		}
//		
//		
//		
//		//documents commands
//		System.out.println(event.getAuthor().getName() +"'s touching me in " +event.getChannel().getName() +": " + line);
//		command = line.split(" ");
//		
//		//Ignores herself
//		if(event.getAuthor().getName().equals("KittyBot"))
//			return;
//		
//		//Help
//		if(command[0].equalsIgnoreCase("!help"))
//		{
//			event.getChannel().sendMessage("Hi! My name is KittyBot! I can do lots of things! "
//					+ "\nIf you !boop I'll boop you right back!"
//					+ "\nIf you !roll I'll need a dice like this 1d4 or 10d7"
//					+ "\nTo see your current amount of beans just !points"
//					+ "\nAnd if you wanna bet 100 beans for a chance for more you can always !bet"
//					+ "\nIf you give me some stuff to !choose from just remember to put commas in between"
//					+ "\nAnd if you want me to keep track of your RP you can !rpstart just don't forget to !rpend").queue();
//		}
//		
//		//POST COUNTER STUFF 
//		if(command[0].equalsIgnoreCase("!points"))
//		{
//			event.getChannel().sendMessage(event.getAuthor().getAsMention() + " you have " 
//						+ Integer.toString(points.getPoints(event.getAuthor().getId(),event.getGuild().getId())) + " beans!").queue();
//		}
//		
//		if(command[0].equalsIgnoreCase("!writetofile"))
//		{
//			if(event.getAuthor().getName().equals("RinTheSnowMew"))
//			{
//				try {
//					points.writeToFile();
//					event.getChannel().sendMessage("Beans saved successfully").queue();
//					ignored.writeToFile();
//					event.getChannel().sendMessage("Ignore list saved successfully").queue();
//					
//				} catch (FileNotFoundException e) 
//				{
//					e.printStackTrace();
//				} catch (UnsupportedEncodingException e) 
//				{
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		if(command[0].equalsIgnoreCase("!bet"))
//		{
//			event.getChannel().sendMessage(points.betStart(event.getAuthor().getId(),event.getGuild().getId())).queue();
//		}
//		
//		
//		//RP Log
//		if(command[0].equalsIgnoreCase("!rpStart"))
//		{
//			if(logging)
//			{
//				event.getChannel().sendMessage("Logging already started in other channel. Please wait for current RP to finish.").queue();
//			}
//			else
//			{
//				logging = true; 
//				log.switchChan(event.getChannel().getName());
//				event.getChannel().sendMessage("Logging Started").queue();
//			}
//			
//		}
//		
//		if(command[0].equalsIgnoreCase("!rpend") && logging)
//		{
//			logging = false;
//			try 
//			{
//				if(event.getMessage().getContent().length() <= 7)
//				{
//					event.getChannel().sendFile(log.writeFile("RPLOG", event.getChannel().getName()),"RpLog.txt", null).queue();
//				}
//				else
//				{
//					event.getChannel().sendFile(log.writeFile(event.getMessage().getContent().substring(7), event.getChannel().getName()), 
//							event.getMessage().getContent().substring(7) + ".txt", null).queue();
//				}
//				
//				event.getChannel().sendMessage("Here's your file!").queue();
//			} catch (FileNotFoundException e) 
//			{
//				e.printStackTrace();
//			} catch (UnsupportedEncodingException e) 
//			{
//				e.printStackTrace();
//			}
//
//		}
//		
//		
//		//RANDOM CALL & RESPONSE
//		if(command[0].equalsIgnoreCase("!ping"))
//		{
//			event.getChannel().sendMessage("Boop!").queue();
//			return;
//		}
//		
//		if(command[0].equalsIgnoreCase("!boop"))
//		{
//			if(command.length == 1)
//			{
//				event.getChannel().sendMessage("*Boops " + event.getAuthor().getAsMention() +"*").queue();
//			}
//			else
//			{
//				event.getChannel().sendMessage("*Boops " + command[1] + "*").queue();
//			}
//			return;
//		}
//		
//		
//
//		
//		//Method call and response 
//		
//		if(command[0].equalsIgnoreCase("!roll"))
//		{
//			event.getChannel().sendMessage(rollDice(command)).queue();
//		}
//		
//		if(command[0].equalsIgnoreCase("!choose"))
//		{
//			event.getChannel().sendMessage(choose(line)).queue();
//		}
//		
//		if(command[0].equalsIgnoreCase("!mute"))
//		{
//			if(event.getAuthor().getName().equals("RinTheSnowMew"))
//			{
//				mute = true;
//				return;
//			}
//			event.getChannel().sendMessage("You can't tell me what to do!").queue();
//			return;
//		}
//		
//		if(command[0].equalsIgnoreCase("!listMembers"))
//		{
//			if(event.getAuthor().getName().equals("RinTheSnowMew"))
//			{
////				String line2 = event.getAuthor().getId();
////				event.getChannel().sendMessage("<@"+line2+">").queue();
//				String members = "";
//				for(int i = 0; i < event.getChannel().getMembers().size(); i ++)
//				{
//					members += event.getChannel().getMembers().get(i).getUser().getId() + ", " + event.getChannel().getMembers().get(i).getUser().getName() + ";";
//					if (members.length() > 1900)
//					{
//						event.getChannel().sendMessage(members).queue();
//						members = "";
//					}	
//				}
//				event.getChannel().sendMessage(members).queue();
//				return;
//			}
//			event.getChannel().sendMessage("You can't tell me what to do!").queue();
//			return;
//		}
//		
//		if(command[0].equalsIgnoreCase("!myRoles"))
//		{
//			event.getChannel().sendMessage(event.getMember().getRoles().toString()).queue();
//			return;
//		}
//		
//		
//		
//		
//		//Moderation commands 
//		if(command[0].equalsIgnoreCase("!subtract"))
//		{
//			words = event.getMessage().getContent().split(" ");
//			points.subPoints(event.getMessage().getMentionedUsers().get(0).getId(),event.getGuild().getId(), Integer.parseInt(words[2]));
//		}
//			//Ignore List
//		if(command[0].equalsIgnoreCase("!ignore"))
//		{
//			ignored.addName(event.getMessage().getMentionedUsers().get(0).getId(),event.getGuild().getId()); 
//		}
//		
//		if(command[0].equalsIgnoreCase("!ignorerole"))
//		{
//			ignored.addRole(event.getMessage().getMentionedRoles().get(0).getId(),event.getGuild().getId());
//		}
//				
//		if(command[0].equalsIgnoreCase("!unignore"))
//		{
//			ignored.removeName(event.getMessage().getMentionedUsers().get(0).getId(),event.getGuild().getId());
//		}
//		
//		if(command[0].equalsIgnoreCase("!unignorerole"))
//		{
//			ignored.removeRole(event.getMessage().getMentionedRoles().get(0).getId(),event.getGuild().getId());
//		}
	
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
	
}
