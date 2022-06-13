import java.util.*;
public class Weather
{
    String name = "Clear";
    Game game;
    Random r;
    public Weather(Game game){
        this.game = game;
        r = new Random(game.dayNum * (long)Math.pow(game.teamA.favor,game.teamB.favor) + (long)Math.pow(game.teamB.favor,game.teamA.favor));
    }

    public String name(){
        return name;
    }
    
    public void startOfGame(){
        
    }
    
    public void beforeHalfInning(){
        
    }
    
    public void beforePitch(){
        
    }
    
    public void endOfGame(){
        
    }
}
