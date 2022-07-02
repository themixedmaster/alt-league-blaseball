import java.util.*;

public class NormalizedWeather extends Weather
{
    public NormalizedWeather(Game game){
        super(game);
        name = "Normalized Weather";
    }

    public void startOfGame(){
        ArrayList<Player> players = new ArrayList<Player>();
        for(Player p : game.teamA.lineup){
            if(!p.hasMod("Elsewhere"))
                players.add(p);
        }
        for(Player p : game.teamB.lineup){
            if(!p.hasMod("Elsewhere"))
                players.add(p);
        }
        if(!game.pitcher.hasMod("Elsewhere"))
            players.add(game.pitcher);
        if(!game.waitingPitcher.hasMod("Elsewhere"))
            players.add(game.waitingPitcher);

        double total = 0;
        for(Player p : players){
            total+=p.totalStars();
        }
        total = total / 4 / players.size();
        for(Player p : players){
            p.aggression.setTemporary(total);
            p.density.setTemporary(total);
            p.focus.setTemporary(total);
            p.malleability.setTemporary(total);
            p.numberOfEyes.setTemporary(total);
            p.splash.setTemporary(total);
            p.dimensions.setTemporary(total);
            p.fun.setTemporary(total);
            p.grit.setTemporary(total);
            p.pinpointedness.setTemporary(total);
            p.powder.setTemporary(total);
            p.arrogance.setTemporary(total);
            p.dexterity.setTemporary(total);
            p.effort.setTemporary(total);
            p.hitPoints.setTemporary(total);
            p.carcinization.setTemporary(total);
            p.damage.setTemporary(total);
            p.mathematics.setTemporary(total);
            p.rejection.setTemporary(total);
            p.wisdom.setTemporary(total);
            game.addEvent(p.name + " has been Normalized. They are feeling Normal.");
        }
    }

    public void endOfGame(){
        ArrayList<Player> players = new ArrayList<Player>();
        for(Player p : game.teamA.lineup){
            if(!p.hasMod("Elsewhere"))
                players.add(p);
        }
        for(Player p : game.teamB.lineup){
            if(!p.hasMod("Elsewhere"))
                players.add(p);
        }
        if(!game.pitcher.hasMod("Elsewhere"))
            players.add(game.pitcher);
        if(!game.waitingPitcher.hasMod("Elsewhere"))
            players.add(game.waitingPitcher);

        for(Player p : players){
            game.addEvent(p.name + " returns to feeling Abnormal.");
        }
    }
    
    public void beforePitch(){
        if(game.r.nextDouble() > 0.02)
            return;
        int rand = (int)(game.r.nextDouble() * 6);
        switch(rand){
            case 0:
                game.addEvent("An air-conditioned breeze drifts over the AstroTurf.");
                break;
            case 1:
                game.addEvent("The " + game.teamA.getTeamName() + " consider altering the humidity of the air conditioning, but decide against it.");
                break;
            case 2:
                rand = (int)(game.r.nextDouble() * 3);
                String weather;
                switch(rand){
                    case 0:
                        weather = "rain";
                        break;
                    case 1:
                        weather = "sun";
                        break;
                    case 2: default:
                        weather = "wind";
                        break;
                }
                game.addEvent("The " + weather + " beats down outside. The stadium is protected from the elements.");
                break;
            case 3:
                game.addEvent("The cheering of the crowd peaks without becoming too loud.");
                break;
            case 4:
                game.addEvent("The players' shoes step across the perfect level of the field.");
                break;
            case 5:
                game.addEvent("Everything is Normal. Play continues.");
                break;
        }
    }
    
}
