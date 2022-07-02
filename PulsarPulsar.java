import java.util.*;

public class PulsarPulsar extends Weather
{
    Weather weather1;
    Weather weather2;
    public PulsarPulsar(Game game){
        super(game);
        name = "Pulsar(Pulsar)";
        changeWeather();
    }

    public String randomFlavor(){
        int rand = (int)(game.r.nextDouble() * 3);
        switch(rand){
            case 0:
                return "Streams Merge. At the Delta. Birds Chirp. Pulsar(Pulsar) Flares.";
            case 1:
                return "Pulsar(Pulsar) Grows. Birds flee in terror.";
            case 2:
                return "Pulsar(Pulsar) Wanes. Mollusks shrink away.";
            case 3: default:
                return "[TUMBLEWEED SOUNDS].";
        }
    }
    
    public void changeWeather(){
        randomizeWeathers();
        if(weather1.getClass().equals(weather2.getClass())){
            double rand = game.r.nextDouble();
            if(rand > 0.25)
                randomizeWeathers();
        }
    }
    
    public void randomizeWeathers(){
        do{
            weather1 = game.randomWeather();
            weather1.r = new Random(r.nextLong());
        }while(!weatherValid(weather1));
        do{
            weather2 = game.randomWeather();
            weather2.r = new Random(r.nextLong());
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
    
    public void startOfGame(){
        weather1.startOfGame();
        weather2.startOfGame();
    }
    
    public void beforeAddEvent(){
        weather1.beforeAddEvent();
        weather2.beforeAddEvent();
    }
    
    public String alterEvent(String event){
        return weather2.alterEvent(weather1.alterEvent(event));
    }
    
    public void beforeFullInning(){
        if(game.inning > 1)
            changeWeather();
        int random = (int)(game.r.nextDouble() * 3);
        if(random == 0){
            game.addEvent(randomFlavor());
        }
        game.addEvent(weather1.name() + " is Expelled! " + weather2.name() + " is Expelled!");
        weather1.beforeFullInning();
        weather2.beforeFullInning();
    }
    
    public void beforeHalfInning(){
        weather1.beforeHalfInning();
        weather2.beforeHalfInning();
    }

    public void batterDeclared(){
        weather1.batterDeclared();
        weather2.batterDeclared();
    }
    
    public void batterElsewhere(){
        weather1.batterElsewhere();
        weather2.batterElsewhere();
    }
    
    public void beforeBaseSteal(Player p){
        weather1.beforeBaseSteal(p);
        weather2.beforeBaseSteal(p);
    }
    
    public void beforePitch(){
        weather1.beforePitch();
        weather2.beforePitch();
    }
    
    public void afterPitch(){
        weather1.afterPitch();
        weather2.afterPitch();
    }
    
    public void afterBall(){
        weather1.afterBall();
        weather2.afterBall();
    }
    
    public void beforeWalk(){
        weather1.beforeWalk();
        weather2.beforeWalk();
    }
    
    public void afterWalk(){
        weather1.afterWalk();
        weather2.afterWalk();
    }
    
    public boolean alterWalk(boolean isWalk){
        return weather2.alterWalk(weather1.alterWalk(isWalk));
    }
    
    public boolean alterStrikeout(boolean isStrikeout){
        return weather2.alterStrikeout(weather1.alterStrikeout(isStrikeout));
    }
    
    public double alterEyesRoll(double eyesRoll){
        return weather2.alterEyesRoll(weather1.alterEyesRoll(eyesRoll));
    }
    
    public double alterFocusRoll(double focusRoll){
        return weather2.alterFocusRoll(weather1.alterFocusRoll(focusRoll));
    }
    
    public void defenderDeclared(){
        weather1.defenderDeclared();
        weather2.defenderDeclared();
    }
    
    public int alterBasesRun(int basesRun){
        return weather2.alterBasesRun(weather1.alterBasesRun(basesRun));
    }
    
    public double alterRunsScored(double runsScored){
        return weather2.alterRunsScored(weather1.alterRunsScored(runsScored));
    }
    
    public void afterHit(){
        weather1.afterHit();
        weather2.afterHit();
    }
    
    public void baseLeft(int baseNum){
        weather1.baseLeft(baseNum);
        weather2.baseLeft(baseNum);
    }
    
    public void onScore(Player p){
        weather1.onScore(p);
        weather2.onScore(p);
    }
    
    public void scoreOnWalk(Player p){
        weather1.scoreOnWalk(p);
        weather2.scoreOnWalk(p);
    }
    
    public void afterHomeRun(){
        weather1.afterHomeRun();
        weather2.afterHomeRun();
    }
    
    public void beforeEndOfPlay(){
        weather1.beforeEndOfPlay();
        weather2.beforeEndOfPlay();
    }
    
    public int alterWinsA(int winsA){
        return weather2.alterWinsA(weather1.alterWinsA(winsA));
    }
    
    public int alterWinsB(int winsB){
        return weather2.alterWinsB(weather1.alterWinsB(winsB));
    }
    
    public void endOfGame(){
        weather1.endOfGame();
        weather2.endOfGame();
    }
}
