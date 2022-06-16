public class PulsarPulsar extends Weather
{
    Weather weather1;
    Weather weather2;
    public PulsarPulsar(Game game){
        super(game);
        name = "Pulsar(Pulsar)";
    }

    public void startOfGame(){
        randomizeWeathers();
        if(weather1.getClass().equals(weather2.getClass())){
            double rand = r.nextDouble();
            if(rand > 0.25)
                randomizeWeathers();
        }
        game.addEvent(weather1.name() + " is Expelled! " + weather2.name() + " is Expelled!");
        weather1.startOfGame();
        weather2.startOfGame();
    }
    
    public String randomFlavor(){
        int rand = (int)(r.nextDouble() * 3);
        switch(rand){
            case 0:
                return "Streams Merge. At the Delta. Birds Chirp. Pulsar(Pulsar) Flares.";
            case 1:
                return "Pulsar(Pulsar) Grows. Birds flee in terror. Pulsar(Pulsar) Wanes. Mollusks shrink away.";
            case 2: default:
                return "[TUMBLEWEED SOUNDS].";            
        }
    }
    
    public void randomizeWeathers(){
        do{
            weather1 = game.randomWeather();
        }while(!weatherValid(weather1));
        do{
            weather2 = game.randomWeather();
        }while(!weatherValid(weather2));
    }
    
    public boolean weatherValid(Weather w){
        if(w instanceof PulsarPulsar)
            return false;
        if(startEffectOnly(w) && endEffectOnly(w) && game.inning > 1 && game.inning < 9)
            return false;
        if(startEffectOnly(w) && game.inning > 1)
            return false;
        if(endEffectOnly(w) && game.inning < 9)
            return false;
        return true;
    }
    
    public boolean startEffectOnly(Weather w){
        return false;
    }
    
    public boolean endEffectOnly(Weather w){
        return false;
    }
    
    public void beforeFullInning(){
        weather1.beforeFullInning();
        weather2.beforeFullInning();
    }
    
    public void beforeHalfInning(){
        weather1.beforeHalfInning();
        weather2.beforeHalfInning();
    }

    public void beforePitch(){
        weather1.beforePitch();
        weather2.beforePitch();
    }

    public void afterBall(){
        weather1.afterBall();
        weather2.afterBall();
    }
    
    public void endOfGame(){
        weather1.endOfGame();
        weather2.endOfGame();
    }
}
