import java.awt.Point;
import java.util.Random;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * Randomly generate a maze given a CSV file containing rooms.
 * 
 * 
 * @author Jordan Lees
 * @version 10/12/2016
 */
public class MazeGenerator
{
    private Grid grid;
    private HashMap<Point, Room> rooms;
    private ArrayList<Room> roomsFromFile;
    private Random rand;
    private InputReader inputReader;
    private Room startPoint;

    /**
     * Constructor for objects of class MazeGenerator
     * 
     * @param csv   a csv file used to generate rooms
     */
    public MazeGenerator(String csv)
    {
        rooms = new HashMap<Point, Room>();
        roomsFromFile = new ArrayList<Room>();
        rand = new Random();
        inputReader = new InputReader(csv);
        startPoint = null;
        
        getFromFile();
        
        int pickRoom = rand.nextInt(roomsFromFile.size());
        Room initialRoom = roomsFromFile.get(pickRoom);
        
        
        grid = new Grid(0, 0, initialRoom);   
        
        roomsFromFile.remove(pickRoom);
        
        generateMaze();
    }

    
    /**
     * Pick a random room for the player to start in.
     * 
     * @return  a random room in the maze
     */
    public Room pickStartPoint()
    {        
        while(true)
        {
            ArrayList<Point> keys = new ArrayList<Point>(rooms.keySet());
            int pick = rand.nextInt(keys.size());
        
            startPoint = rooms.get(keys.get(pick));
            
            if (!startPoint.getShortDescription().equals("X"))
            {
                return startPoint;
            }
        }                
    }
    
    /**
     * Add all rooms from the file to the grid.
     */    
    private void addRoomsToGrid()
    {
        int limit = roomsFromFile.size();
        
        for (int i = 0; i < limit; i++)
        {
            grid.addRoom(roomsFromFile.get(i));
        }
    }
    
    /**
     * Construct an ArrayList of rooms from a list in a csv file, terminating with "X"
     */
    private void getFromFile()
    {
        Room room = null;
        String description = null;
        
        String input[] = inputReader.getInput();
        
        while(!input[0].equals("X"))
        {
            description = input[0];
            room = new Room(description);
            
            roomsFromFile.add(room);
            
            input = inputReader.getInput();
        }
    }
    
    
/**
 * Generate a maze by adding all rooms to the grid, setting exits on all rooms, and
 * creating rooms HashMap.
 */    
    private void generateMaze()
    {
        addRoomsToGrid();
        
        grid.setExits();
        
        rooms = grid.getGrid();   
    }
}
