import java.util.*;

public class SolarEclipse extends Weather
{
    public SolarEclipse(Game game){
        super(game);
        name = "Solar Eclipse";
    }

    public void beforePitch(){
        if(r.nextDouble() > 0.00003)
            return;
        int rand;
        if(game.basesEmpty())
            rand = (int)(r.nextDouble() * 3);
        else
            rand = (int)(r.nextDouble() * 4);
        Player p;
        Player replacement = new Player();
        replacement.addStatistic("Added as replacement");
        switch(rand){
            case 0://active pitcher
                p = game.pitcher;
                p.originalTeam = game.pitchingTeam;
                game.pitchingTeam.rotation[game.pitchingTeam.getPositionInRotation(p)] = replacement;
                game.pitcher = replacement;
                break;
            case 1://active batter
                p = game.batter;
                p.originalTeam = game.battingTeam;
                game.battingTeam.lineup[game.battingTeam.getPositionInLineup(p)] = replacement;
                game.batter = replacement;
                break;
            case 2://defender
                p = game.randomDefender();
                p.originalTeam = game.pitchingTeam;
                game.pitchingTeam.lineup[game.pitchingTeam.getPositionInLineup(p)] = replacement;
                break;
            case 3: default://player on base
                int i = game.randomOccupiedBase();
                p = game.bases[i];
                p.originalTeam = game.battingTeam;
                game.bases[i] = replacement;//optional: set to null
                game.battingTeam.lineup[game.battingTeam.getPositionInLineup(p)] = replacement;
                break;
        }
        League.deceased.add(p);
        p.addStatistic("Times incinerated");
        game.addEvent("Rogue umpire incinerates " + p.name + "! They're replaced by " + replacement.name + ".", true);
    }
}
