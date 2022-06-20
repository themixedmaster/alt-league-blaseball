public class Weather
{
    String name;
    NewGame game;
    public Weather(NewGame game){
        this.game = game;
<<<<<<< Updated upstream
=======
        r = new Random(game.dayNum * (long)Math.pow(game.teamA.favor,game.teamB.favor) + (long)Math.pow(game.teamB.favor,game.teamA.favor));
    }

    public String name(){
        return name;
    }
    
    public void startOfGame(){
        
    }
    
    public void beforeAddEvent(){
        
    }
    
    public void beforeFullInning(){
        
    }
    
    public void beforeHalfInning(){
        
    }
    
    public void batterDeclared(){
        
    }
    
    public void batterElsewhere(){
        
    }
    
    public void beforeBaseSteal(Player p){
        
    }
    
    public void beforePitch(){
        
    }
    
    public void afterPitch(){
        
    }
    
    public void afterBall(){
        
    }
    
    public void beforeWalk(){
        
    }
    
    public void afterWalk(){
        
    }
    
    public boolean alterWalk(boolean isWalk){
        return isWalk;
    }
    
    public boolean alterStrikeout(boolean isStrikeout){
        return isStrikeout;
    }
    
    public double alterEyesRoll(double eyesRoll){
        return eyesRoll;
    }
    
    public double alterFocusRoll(double focusRoll){
        return focusRoll;
    }
    
    public void defenderDeclared(){
        
    }
    
    public int alterBasesRun(int basesRun){
        return basesRun;
    }
    
    public double alterRunsScored(double runsScored){
        return runsScored;
    }
    
    public void afterHit(){
        
    }
    
    public void baseLeft(int baseNum){
        
    }
    
    public void onScore(Player p){
        
    }
    
    public void scoreOnWalk(Player p){
        
    }
    
    public void afterHomeRun(){
        
    }
    
    public void beforeEndOfPlay(){
        
    }
    
    public int alterWinsA(int winsA){
        return winsA;
    }
    
    public int alterWinsB(int winsB){
        return winsB;
    }
    
    public void endOfGame(){
        
>>>>>>> Stashed changes
    }
}
