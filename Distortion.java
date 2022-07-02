public class Distortion extends Weather
{
    public Distortion(Game game){
        super(game);
        name = "Distortion";
    }

    public void beforePitch(){
        if(game.r.nextDouble() > 0.00004 * game.numberOfWeathers())
            return;
        Player a = game.batter;
        Player b = game.pitcher;
        game.addEvent("Distortion washes over active play. " + a.name + " and " + b.name + " end up on the other's sides!",true);
        a.addStatistic("Swapped teams via distortion");
        b.addStatistic("Swapped teams via distortion");
        a.addStatistic("Swapped teams");
        b.addStatistic("Swapped teams");
        game.batter = b;
        game.pitcher = a;
        game.pitchingTeam.rotation[(game.dayNum - 1 + game.pitchingTeam.rotation.length) % game.pitchingTeam.rotation.length] = a;
        game.battingTeam.lineup[(game.activeTeamBat-1) % game.battingTeam.lineup.length] = b;
    }
}
