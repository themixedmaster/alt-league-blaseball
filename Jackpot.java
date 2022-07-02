public class Jackpot extends Weather
{
    Weather weather;
    public Jackpot(Game game){
        super(game);
        weather = game.randomWeather();
        name = weather.name + "(+ Jackpot)";
    }
    
    public double alterRunsScored(double runsScored){
        if(game.top){
            game.winsB+=runsScored;
            game.addEvent("The " + game.teamB.name + " gain " + game.scoreToString(runsScored) + " wins.");
        }else{
            game.winsA+=runsScored;
            game.addEvent("The " + game.teamA.name + " gain " + game.scoreToString(runsScored) + " wins.");
        }
        return weather.alterRunsScored(runsScored);
    }
    
    public void startOfGame(){
        weather.startOfGame();
    }
    
    public void beforeAddEvent(){
        weather.beforeAddEvent();
    }
    
    public String alterEvent(String event){
        return weather.alterEvent(event);
    }
    
    public void beforeFullInning(){
        weather.beforeFullInning();
    }
    
    public void beforeHalfInning(){
        weather.beforeHalfInning();
    }

    public void batterDeclared(){
        weather.batterDeclared();
    }
    
    public void batterElsewhere(){
        weather.batterElsewhere();
    }
    
    public void beforeBaseSteal(Player p){
        weather.beforeBaseSteal(p);
    }
    
    public void beforePitch(){
        weather.beforePitch();
    }
    
    public void afterPitch(){
        weather.afterPitch();
    }
    
    public void afterBall(){
        weather.afterBall();
    }
    
    public void beforeWalk(){
        weather.beforeWalk();
    }
    
    public void afterWalk(){
        weather.afterWalk();
    }
    
    public boolean alterWalk(boolean isWalk){
        return weather.alterWalk(isWalk);
    }
    
    public boolean alterStrikeout(boolean isStrikeout){
        return weather.alterStrikeout(isStrikeout);
    }
    
    public double alterEyesRoll(double eyesRoll){
        return weather.alterEyesRoll(eyesRoll);
    }
    
    public double alterFocusRoll(double focusRoll){
        return weather.alterFocusRoll(focusRoll);
    }
    
    public void defenderDeclared(){
        weather.defenderDeclared();
    }
    
    public int alterBasesRun(int basesRun){
        return weather.alterBasesRun(basesRun);
    }
    
    public void afterHit(){
        weather.afterHit();
    }
    
    public void baseLeft(int baseNum){
        weather.baseLeft(baseNum);
    }
    
    public void onScore(Player p){
        weather.onScore(p);
    }
    
    public void scoreOnWalk(Player p){
        weather.scoreOnWalk(p);
    }
    
    public void afterHomeRun(){
        weather.afterHomeRun();
    }
    
    public void beforeEndOfPlay(){
        weather.beforeEndOfPlay();
    }
    
    public int alterWinsA(int winsA){
        return weather.alterWinsA(winsA);
    }
    
    public int alterWinsB(int winsB){
        return weather.alterWinsB(winsB);
    }
    
    public void endOfGame(){
        weather.endOfGame();
    }
}
