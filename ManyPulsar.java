import java.util.ArrayList;
import java.util.Random;

public class ManyPulsar extends Weather
{
    ArrayList<Weather> weathers;
    public ManyPulsar(Game game){
        super(game);
        name = "Pulsar(";
        int length = 5;//000 / 8 - 2; //uncomment for release
        for(int x = 0; x < length; x++)
            name += "Pulsar(";
        name += "Pulsar)";
        for(int x = 0; x < length; x++)
            name += ")";
        //System.out.println(name.length());
        addWeathers();
    }

    public void addWeathers(){
        weathers = new ArrayList<Weather>();
        weathers.add(new SolarEclipse(game));
        weathers.add(new Meownsoon(game));
        weathers.add(new PulsarPulsar(game));
        weathers.add(new SnailMail(game));
        weathers.add(new Crabs(game));
        weathers.add(new FloatingMirrors(game));
        weathers.add(new Rain(game));
        weathers.add(new Distortion(game));
        weathers.add(new Growth(game));
        weathers.add(new FeverPitch(game));
        weathers.add(new Multiball(game));
        weathers.add(new NormalizedWeather(game));
        weathers.add(new NamedWeather(game));
        weathers.add(new Brisk(game));
        weathers.add(new SupernovaEclipse(game));
        weathers.add(new Letterball(game));
    }
    
    public void addWeather(){
        Weather weather;
        do{
            weather = game.randomWeather();
            weather.r = new Random(r.nextLong());
        }while(weather instanceof ManyPulsar);
        weathers.add(weather);
        game.addEvent(weather.name + " is Duplicated!");
    }
    
    public void startOfGame(){
        for(int x = 0; x < weathers.size();  x++)
            game.addEvent(weathers.get(x).name() + " is Expelled!");
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).startOfGame();
    }
    
    public void beforeAddEvent(){
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).beforeAddEvent();
    }
    
    public String alterEvent(String event){
        for(int x = 0; x < weathers.size();  x++)
            event = weathers.get(x).alterEvent(event);
        return event;
    }
    
    public void beforeFullInning(){
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).beforeFullInning();
    }
    
    public void beforeHalfInning(){
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).beforeHalfInning();
    }

    public void batterDeclared(){
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).batterDeclared();
        addWeather();
    }
    
    public void batterElsewhere(){
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).batterElsewhere();
    }
    
    public void beforeBaseSteal(Player p){
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).beforeBaseSteal(p);
    }
    
    public void beforePitch(){
        //System.out.println(this);
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).beforePitch();
    }
    
    public void afterPitch(){
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).afterPitch();
    }
    
    public void afterBall(){
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).afterBall();
    }
    
    public void beforeWalk(){
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).beforeWalk();
    }
    
    public void afterWalk(){
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).afterWalk();
    }
    
    public boolean alterWalk(boolean isWalk){
        for(int x = 0; x < weathers.size();  x++)
            isWalk = weathers.get(x).alterWalk(isWalk);
        return isWalk;
    }
    
    public boolean alterStrikeout(boolean isStrikeout){
        for(int x = 0; x < weathers.size();  x++)
            isStrikeout = weathers.get(x).alterStrikeout(isStrikeout);
        return isStrikeout;
    }
    
    public double alterEyesRoll(double eyesRoll){
        for(int x = 0; x < weathers.size();  x++)
            eyesRoll = weathers.get(x).alterEyesRoll(eyesRoll);
        return eyesRoll;
    }
    
    public double alterFocusRoll(double focusRoll){
        for(int x = 0; x < weathers.size();  x++)
            focusRoll = weathers.get(x).alterFocusRoll(focusRoll);
        return focusRoll;
    }
    
    public void defenderDeclared(){
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).defenderDeclared();
    }
    
    public int alterBasesRun(int basesRun){
        for(int x = 0; x < weathers.size();  x++)
            basesRun = weathers.get(x).alterBasesRun(basesRun);
        return basesRun;
    }
    
    public double alterRunsScored(double runsScored){
        for(int x = 0; x < weathers.size();  x++)
            runsScored = weathers.get(x).alterRunsScored(runsScored);
        return runsScored;
    }
    
    public void afterHit(){
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).afterHit();
    }
    
    public void baseLeft(int baseNum){
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).baseLeft(baseNum);
    }
    
    public void onScore(Player p){
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).onScore(p);
    }
    
    public void scoreOnWalk(Player p){
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).scoreOnWalk(p);
    }
    
    public void afterHomeRun(){
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).afterHomeRun();
    }
    
    public void beforeEndOfPlay(){
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).beforeEndOfPlay();
    }
    
    public int alterWinsA(int winsA){
        for(int x = 0; x < weathers.size();  x++)
            winsA = weathers.get(x).alterWinsA(winsA);
        return winsA;
    }
    
    public int alterWinsB(int winsB){
        for(int x = 0; x < weathers.size();  x++)
            winsB = weathers.get(x).alterWinsB(winsB);
        return winsB;
    }
    
    public void endOfGame(){
        for(int x = 0; x < weathers.size();  x++)
            weathers.get(x).endOfGame();
    }
}
