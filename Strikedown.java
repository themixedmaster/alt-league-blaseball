public class Strikedown extends Weather
{
    public Strikedown(Game game){
        super(game);
        name = "Strikedown";
    }

    public void beforeHalfInning(){
        double random = game.r.nextDouble();
        if(random <= 0.65)
            game.addEvent("The " + game.battingTeam.name + " go silent, their bodies moving in mechanical lockstep.",true);
        else{
            int rand = (int)(game.r.nextDouble() * 4);
            switch(rand){
                case 0:
                    game.addEvent("The " + game.battingTeam.name + " go silent.  The crown boos.");
                    break;
                case 1:
                    game.addEvent("The " + game.battingTeam.name + " go silent.  You can hear someone demanding their ticket be refunded.");
                    break;
                case 2:
                    game.addEvent("The " + game.battingTeam.name + " go silent.  Oh right, we're in Strikedown weather.");
                    break;
                case 3:
                    game.addEvent("The " + game.battingTeam.name + " go silent.  The fans voted this out in Season 2, and for good reason.");
                    break;
            }
        }
    }

    public void beforePitch(){
        if(game.scoreA > 255 || game.scoreB > 255){
            game.addEvent("Strike out. The game must end.");
            game.strikes+=255;
        }
        game.batter.boostBatting(2);
        game.batter.boostBaserunning(2);
    }
    
    public void baseLeft(int baseNum){
        game.bases[baseNum].clearTemporaryStats();
    }
    
    public void onScore(Player p){
        p.clearTemporaryStats();
    }
}
