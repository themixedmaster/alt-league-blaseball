public class SnailMail extends Weather
{
    public SnailMail(Game game){
        super(game);
        name = "Snail mail";
    }
    
    public void startOfGame(){
        game.addEvent("It's raining snails, the players move slowly to avoid stepping on them.");
    }
    
    public void beforeAddEvent(){
        game.currentTime += game.tick;
    }
}
