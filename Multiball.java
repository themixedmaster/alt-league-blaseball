public class Multiball extends Weather
{
    boolean endWithOut;
    public Multiball(Game game){
        super(game);
        name = "Multiball";
    }
    
    public void beforePitch(){
        if(r.nextDouble() > 0.04)
            return;
        endWithOut = false;
        game.addEvent("!!!MULTIBALL!!!");
        game.addEvent(game.pitcher.name + " pitches a multiball!");
        game.pitcher.addStatistic("Multiballs pitched");
        
        //game.weather.beforePitch();
        game.batter.addMod("Persistent");
        game.doPitch();
        
        game.addEvent("MEANWHILE");
        //game.weather.beforePitch();
        game.batter.addMod("Persistent");
        game.doPitch();
        
        game.addEvent("BUT ALSO");
        //game.weather.beforePitch();
        game.batter.addMod("Persistent");
    }
    
    public void afterPitch(){
        game.batter.removeMod("Persistent");
    }
    
    public boolean alterStrikeout(boolean isStrikeout){
        boolean returnBool = isStrikeout;// || endWithOut;
        return returnBool;
    }
}
