
/**
 * Write a description of class Player here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Player
{
    // instance variables - replace the example below with your own
    private int energy;
    private int moves;
    private Room location;

    /**
     * Constructor for objects of class Player
     */
    public Player(Room location)
    {
        // initialise instance variables
        resetEnergy();
        moves = 0;
        this.location = location;
    }
    
    /**
     * Add one to move counter.
     */
    public void addMove()
    {
        moves++;
    }
    
    /**
     * Get number of moves made.
     * 
     * @return  number of moves
     */
    public int getMoves()
    {
        return moves;
    }
    
    /**
     * Increases player energy by 1
     */
    public void incrementEnergy()
    {
        energy++;
    }
        
    
    public Room getLocation() {
        return location;
    }
    
    public void leave() {
        location.leave(this);
    }
    
    public void setLocation(Room newLocation) {
        location = newLocation;
        location.enter(this);
    }
    
    public void decreaseEnergy() {
        energy--;
    }
    
    public int getEnergyLevel() {
        return energy;
    }
    
    public boolean isAlive() {
        return energy > 0;
    }
    
    public void resetEnergy() {
        energy = 10;
    }

}
