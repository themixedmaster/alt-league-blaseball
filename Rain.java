public class Rain extends Weather
{
    public Rain(Game game){
        super(game);
        name = "Rain";
    }
    
    public void startOfGame(){
        game.addEvent("It started to rain!");
    }
    
    public void beforeHalfInning(){
        game.addEvent("It is raining!");
    }
}