public class Crabs extends Weather
{
    public Crabs(Game game){
        super(game);
        name = "Crabs";
    }
    
    public void beforeFullInning(){
        game.addEvent("Crabs fill the field");
    }
    
    public void afterBall(){
        for(int x = game.bases.length - 1; x >= 0; x--){
            if(game.bases[x] != null)
                game.steal(x);
        }
    }
}

