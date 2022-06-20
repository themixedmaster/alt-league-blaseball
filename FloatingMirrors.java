import java.util.*;

public class FloatingMirrors extends Weather
{
    Player distractedPlayer;
    public FloatingMirrors(Game game){
        super(game);
        name = "Floating Mirrors";
    }

    public void startOfGame(){
        double rand = r.nextDouble();
        if(rand <= 0.024)
            game.weather = new Strikedown(game);
        else{
            int random = (int)(r.nextDouble() * 5);
            switch(random){
                case 0:
                    game.addEvent("!llap yalB");
                    game.addEvent("Nah, just kidding.");
                    break;
                case 1:
                    game.addEvent("Mirror, mirror, on the sky...");
                    break;
                case 2:
                    game.addEvent("What if YOU'RE the reflection?");
                    break;
                case 3:
                    game.addEvent("The sky slowly fills with mirrors.");
                    break;
                case 4:
                    game.addEvent("It's a good thing this game is just text, because rendering all these mirrors would be a pain...");
                    break;
            }
        }
    }

    public void beforePitch(){
        double rand = r.nextDouble();
        if(rand <= 1.0/60){
            Player p = randomTarget();
            game.addEvent(p.name + " is looking at themselves in the Mirror...");
            p.addStatistic("Mirror viewed");
            double wisdom = r.nextDouble() * 0.2;
            double arrogance = r.nextDouble() * 0.2;
            p.wisdom.permanentIncrease(wisdom);
            p.arrogance.permanentIncrease(arrogance);
            p.addStatistic("Wisdom boost from mirror",wisdom);
            p.addStatistic("Arrogance boost from mirror",arrogance);
            wisdom = (double)((int)(wisdom * 100))/100.0;
            arrogance = (double)((int)(arrogance * 100))/100.0;
            game.addEvent("Their Wisdom increased by " + wisdom + ", and their Arrogance increased by " + arrogance + ".");
        }
        rand = r.nextDouble();
        if(rand <= 0.0005){
            Player p = randomWeightedTarget();
            if(p == null)
                return;
            game.addEvent(p.name + " suddenly feels a sharp pain.");
            game.addEvent(p.name + " shatters!",true);
            p.addStatistic("Shattered");
            p.addMod("Shattered");
            p.name = "Fragments of " + p.name;
            p.aggression.permanentMultiply(r.nextDouble());
            p.arrogance.permanentMultiply(r.nextDouble());
            p.carcinization.permanentMultiply(r.nextDouble());
            p.damage.permanentMultiply(r.nextDouble());
            p.density.permanentMultiply(r.nextDouble());
            p.dexterity.permanentMultiply(r.nextDouble());
            p.dimensions.permanentMultiply(r.nextDouble());
            p.effort.permanentMultiply(r.nextDouble());
            p.focus.permanentMultiply(r.nextDouble());
            p.fun.permanentMultiply(r.nextDouble());
            p.grit.permanentMultiply(r.nextDouble());
            p.hitPoints.permanentMultiply(r.nextDouble());
            p.malleability.permanentMultiply(r.nextDouble());
            p.mathematics.permanentMultiply(r.nextDouble());
            p.numberOfEyes.permanentMultiply(r.nextDouble());
            p.pinpointedness.permanentMultiply(r.nextDouble());
            p.powder.permanentMultiply(r.nextDouble());
            p.rejection.permanentMultiply(r.nextDouble());
            p.splash.permanentMultiply(r.nextDouble());
            p.wisdom.permanentMultiply(r.nextDouble());
        }
    }

    public void defenderDeclared(){
        double rand = r.nextDouble();
        Player p = game.defender;
        distractedPlayer = p;
        double threshold = 0.15 - (0.05 * p.numberOfEyes.value());
        if(rand <= threshold){
            game.addEvent(p.name + " was distracted by the reflections...");
            p.addStatistic("Distracted by reflections");
            p.boostDefense(0 - (p.defense * 2 / 3));
        }
    }

    public void afterHit(){
        double rand = r.nextDouble();
        Player p = game.batter;
        double threshold = 0.25 - (0.025 * p.dexterity.value()) - (0.025 * p.focus.value()) - (0.025 * p.pinpointedness.value());
        if(rand <= threshold){
            game.addEvent(p.name + " shatters a mirror with a blaseball!  A penalty has been issued.");
            p.addStatistic("Mirrors shattered");
            if(game.top)
                game.scoreB-=0.5;
            else
                game.scoreA-=0.5;
            game.printScore();
        }
        if(distractedPlayer != null)
            distractedPlayer.clearTemporaryStats();
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
        return targets.get((int)(r.nextDouble() * targets.size()));
    }

    public Player randomWeightedTarget(){
        double total = 0;
        HashMap<Player,Double> targets = new HashMap<Player,Double>();
        for(Player p : game.teamA.lineup){
            if(!p.hasMod("Elsewhere") && !p.hasMod("Shattered")){
                total += p.batting;
                targets.put(p,total);
            }
        }
        for(Player p : game.teamB.lineup){
            if(!p.hasMod("Elsewhere") && !p.hasMod("Shattered")){
                total += p.batting;
                targets.put(p,total);
            }
        }
        if(!game.pitcher.hasMod("Shattered")){
            total += game.pitcher.batting;
            targets.put(game.pitcher,total);
        }
        if(!game.waitingPitcher.hasMod("Shattered")){
            total += game.waitingPitcher.batting;
            targets.put(game.waitingPitcher,total);
        }
        double rand = r.nextDouble() * total;
        for(Map.Entry<Player,Double> entry : targets.entrySet()){
            if(rand <= entry.getValue())
                return entry.getKey();
        }
        return null;
    }
}
