import java.awt.Point;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
/**
 * Write a description of class Grid here.
 * 
 * @author Jordan Lees 
 * @version 08/12/2016
 */
public class Grid
{
    private Random rand;
    private ArrayList<Point> freePoints;
    private HashMap<Point, Room> roomsOnGrid;
    private Room endRoom;

    /**
     * Constructor for objects of class Grid
     * Takes the first room to be added to grid and adds it at given x, y. Sets an exit point at
     * random.
     * 
     * @param   x   x coordinate of first room
     * @param   y   y coordinate of first room
     * @param room  first room to be added to the grid    
     */
    public Grid(int x, int y, Room room)
    {
        rand = new Random();
        Point initialPoint =  new Point(x, y);
        freePoints = new ArrayList<Point>();
        roomsOnGrid = new HashMap<Point, Room>();
        roomsOnGrid.put(initialPoint, room);
        
        freeAdjacentPoints(initialPoint);
        endRoom = new Room("X");
        endRoom.setEndRoom();
        
        int direction = rand.nextInt(3);
        
        Point north = new Point((int)initialPoint.getX(), (int)initialPoint.getY() - 1);
        Point east = new Point((int)initialPoint.getX() + 1, (int)initialPoint.getY());
        Point south = new Point((int)initialPoint.getX(), (int)initialPoint.getY() - 1);
        Point west = new Point((int)initialPoint.getX() - 1, (int)initialPoint.getY());
        
        switch (direction) {
            case 0: roomsOnGrid.put(north, endRoom);
                    freePoints.remove(north);
                    break;
            case 1: roomsOnGrid.put(east, endRoom);
                    freePoints.remove(east);
                    break;
            case 2: roomsOnGrid.put(south, endRoom);
                    freePoints.remove(south);
                    break;
            case 3: roomsOnGrid.put(west, endRoom);
                    freePoints.remove(west);
                    break;
            default: roomsOnGrid.put(north, endRoom);
                    freePoints.remove(north);
                    break;
        }
    }
    
    /**
     * Add a given room to the grid at a random point.
     * 
     * @param room  room to be added to grid
     */
    public void addRoom(Room room)
    {
        int choose = rand.nextInt(freePoints.size());
        Point point = freePoints.get(choose);
        roomsOnGrid.put(point, room);
        freePoints.remove(point);
        freeAdjacentPoints(point);
    }
    
    /**
     * Add appropriate exits to each point on the grid, based on rooms adjacent.
     */    
    public void setExits()
    {
        for (Point point : roomsOnGrid.keySet())
        {
            Point north = new Point((int)point.getX(), (int)point.getY() - 1);
            Point east = new Point((int)point.getX() + 1, (int)point.getY());
            Point south = new Point((int)point.getX(), (int)point.getY() + 1);
            Point west = new Point((int)point.getX() - 1, (int)point.getY());
            
            if (roomsOnGrid.containsKey(north))
            {
                roomsOnGrid.get(point).setExit("north", roomsOnGrid.get(north));
            }
            
            if (roomsOnGrid.containsKey(east))
            {
                roomsOnGrid.get(point).setExit("east", roomsOnGrid.get(east));
            }
            
            if (roomsOnGrid.containsKey(south))
            {
                roomsOnGrid.get(point).setExit("south", roomsOnGrid.get(south));
            }
            
            if (roomsOnGrid.containsKey(west))
            {
                roomsOnGrid.get(point).setExit("west", roomsOnGrid.get(west));
            }
        }
    }
    
    /**
     * Gets the grid.
     * 
     * @return  a HashMap containing all points and corresponding rooms.
     */
    public HashMap<Point, Room> getGrid()
    {
        return roomsOnGrid;
    }
        
    
    /**
     * Used internally to add points to ArrayList to indicate that they are free and valid, so that
     * rooms can be added at these points.
     * 
     * @param point the given point whose adjacent points we are adding to the list.
     */
    private void freeAdjacentPoints(Point point)
    {
        Point north = new Point((int)point.getX(), (int)point.getY() - 1);
        Point east = new Point((int)point.getX() + 1, (int)point.getY());
        Point south = new Point((int)point.getX(), (int)point.getY() - 1);
        Point west = new Point((int)point.getX() - 1, (int)point.getY());
        
        if (!roomsOnGrid.containsKey(north) && !freePoints.contains(north))
        {
            freePoints.add(north);
        }
        
        if (!roomsOnGrid.containsKey(east) && !freePoints.contains(east))
        {
            freePoints.add(east);
        }
        
        if (!roomsOnGrid.containsKey(south) && !freePoints.contains(south))
        {
            freePoints.add(south);
        }
        
        if (!roomsOnGrid.containsKey(west) && !freePoints.contains(west))
        {
            freePoints.add(west);
        }
    }
}
