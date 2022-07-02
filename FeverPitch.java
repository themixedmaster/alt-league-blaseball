import java.util.*;

public class FeverPitch extends Weather
{
    Team favoredTeam;
    Team unfavoredTeam;
    Player targetedPlayer;
    public FeverPitch(Game game){
        super(game);
        name = "Fever Pitch";
        favoredTeam = game.teamA;
        unfavoredTeam = game.teamB;
    }
    
    public void beforeFullInning(){
        game.addEvent("The " + favoredTeam.name + " have the {FAVOUR OF THE CROWD}!");
    }
    
    public void beforeHalfInning(){
        if(game.r.nextDouble() > 0.25)
            return;
        if(favoredTeam.equals(game.pitchingTeam)){
            game.addEvent("The " + game.battingTeam.name + " steal the {FAVOUR OF THE CROWD}!");
            favoredTeam = game.battingTeam;
            unfavoredTeam = game.pitchingTeam;
        }
    }
    
    public void batterDeclared(){
        targetedPlayer = null;
        if(game.r.nextDouble() > 0.05)
            return;
        if(game.battingTeam.equals(favoredTeam)){
            game.addEvent("Cheers targeted at " + game.batter.name + " echo through the crowd!");
            targetedPlayer = game.batter;
        }else{
            game.addEvent("Jeers targeted at " + game.batter.name + " echo through the crowd!");
            targetedPlayer = game.batter;
        }
    }
    
    public void beforePitch(){
        if(game.r.nextDouble() > 0.00003)
            return;
        Player p = randomTarget();
        boolean favored = false;
        boolean unfavored = false;
        for(Player player : favoredTeam.getActivePlayers()){
            if(player.equals(p)){
                favored = true;
                break;
            }
        }
        for(Player player : unfavoredTeam.getActivePlayers()){
            if(player.equals(p)){
                unfavored = true;
                break;
            }
        }
        if(favored && unfavored){
            switch((int)(game.r.nextDouble() * 2)){
                case 0:
                    unfavored = false;
                    break;
                case 1:
                    favored = false;
                    break;
            }
        }
        
        String statName;
        Stat stat;
        switch((int)(game.r.nextDouble() * 2)){
            case 0:
                stat = p.aggression;
                statName = "Aggression";
                break;
            case 1:
                stat = p.arrogance;
                statName = "Arrogance";
                break;
            case 2:
                stat = p.carcinization;
                statName = "Carcinization";
                break;
            case 3:
                stat = p.damage;
                statName = "Damage";
                break;
            case 4:
                stat = p.density;
                statName = "Density";
                break;
            case 5:
                stat = p.dexterity;
                statName = "Dexterity";
                break;
            case 6:
                stat = p.dimensions;
                statName = "Dimensions";
                break;
            case 7:
                stat = p.effort;
                statName = "Effort";
                break;
            case 8:
                stat = p.focus;
                statName = "Focus";
                break;
            case 9:
                stat = p.fun;
                statName = "Fun";
                break;
            case 10:
                stat = p.grit;
                statName = "Grit";
                break;
            case 11:
                stat = p.hitPoints;
                statName = "Hit Points";
                break;
            case 12:
                stat = p.malleability;
                statName = "Malleability";
                break;
            case 13:
                stat = p.mathematics;
                statName = "Mathematics";
                break;
            case 14:
                stat = p.numberOfEyes;
                statName = "Number of Eyes";
                break;
            case 15:
                stat = p.pinpointedness;
                statName = "Pinpointedness";
                break;
            case 16:
                stat = p.powder;
                statName = "Powder";
                break;
            case 17:
                stat = p.rejection;
                statName = "Rejection";
                break;
            case 18:
                stat = p.splash;
                statName = "Splash";
                break;
            case 19: default:
                stat = p.wisdom;
                statName = "Wisdom";
                break;
        }
        
        if(favored){
            game.addEvent(p.name + " became the Centre of Attention! The {FAVOUR OF THE CROWD} blesses their " + statName + "!",true);
            stat.setBaseValue(5);
        }else{
            game.addEvent(p.name + " became the Centre of Attention! The {FAVOUR OF THE CROWD} curses their " + statName + "!",true);
            stat.setBaseValue(0);
        }
    }
    
    public double alterEyesRoll(double eyesRoll){
        if(targetedPlayer != null && game.batter.equals(targetedPlayer) && favoredTeam.equals(game.pitchingTeam))
            return 0;
        return eyesRoll;
    }
    
    public double alterFocusRoll(double focusRoll){
        if(targetedPlayer != null && game.batter.equals(targetedPlayer) && favoredTeam.equals(game.pitchingTeam))
            return 0;
        return focusRoll;
    }
    
    public double alterRunsScored(double runsScored){
        if(targetedPlayer != null && game.batter.equals(targetedPlayer) && favoredTeam.equals(game.battingTeam))
            return runsScored * 2;
        return runsScored;
    }
    
    public Player randomTarget(){
        ArrayList<Player> targets = new ArrayList<Player>();
        for(Player p : game.teamA.lineup){
            if(!p.hasMod("Elsewhere"))
                targets.add(p);
        }
        for(Player p : game.teamB.lineup){
            if(!p.hasMod("Elsewhere"))
                targets.add(p);
        }
        if(!game.pitcher.hasMod("Elsewhere"))
            targets.add(game.pitcher);
        if(!game.waitingPitcher.hasMod("Elsewhere"))
            targets.add(game.waitingPitcher);
        return targets.get((int)(game.r.nextDouble() * targets.size()));
    }
}
