/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.08.08
 */

public class Game 
{
    private static final String ROOM_FILE = "rooms.csv";
    private MazeGenerator mazeGenerator;
    private Parser parser;
    private Room startRoom;
    //private Room currentRoom;
    private Player player;
    private int moves;
    boolean finished;
        
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        mazeGenerator = new MazeGenerator(ROOM_FILE);
        startRoom = mazeGenerator.pickStartPoint();
        createRooms();
        finished = false;
        
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        player = new Player(startRoom);
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();
        boolean wantToQuit = false;

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        while (!wantToQuit && !finished) {
            Command command = parser.getCommand();
            wantToQuit = processCommand(command);
            player.decreaseEnergy();
            System.out.println("Your energy level is " + player.getEnergyLevel());
            if (player.isAlive() == false) {
                finished = true;
            }
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(player.getLocation().getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getLocation().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
            return;
        }
        
        if (!nextRoom.getShortDescription().equals("X"))
        {
            player.setLocation(nextRoom);
            player.addMove();
            System.out.println(player.getLocation().getLongDescription());
        }
        else
        {
            endGame();
        }      
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
    
    /**
     * End the game loop, printing player statistics.
     */
    private void endGame()
    {
        String gameOver = null;
        StringBuffer sb = new StringBuffer();
        sb.append("\nCongratulations! You have escaped from the maze.\n");
        sb.append("You made " + player.getMoves() + " moves.\n");
        sb.append("You finished with " + player.getEnergyLevel() + " energy.\n");
        
        gameOver = sb.toString();
        System.out.println(gameOver);
        
        finished = true;

        
    }
}
