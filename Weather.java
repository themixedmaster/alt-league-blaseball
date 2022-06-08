public class Weather
{
    String name = "Clear";
    Game game;
    public Weather(Game game){
        this.game = game;
    }

    public String name(){
        return name;
    }
}
