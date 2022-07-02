public class Brisk extends Weather
{
    boolean doubleWalk = false;
    public Brisk(Game game){
        super(game);
        name = "Brisk";
    }

    public void startOfGame(){
        game.addEvent("It's perfect weather for a walk.");
    }
    
    public void batterElsewhere(){
        game.addEvent(game.batter.name + " is still walking back home.");
    }

    public boolean alterWalk(boolean isWalk){
        return game.balls >= 2;
    }
    
    public void beforeBaseSteal(Player p){
        if(game.r.nextDouble() > 0.6)
            return;
        int rand = (int)(game.r.nextDouble() * 3);
        switch(rand){
            case 0:
                game.addEvent(p.name + " steals a base at a slow pace!");
                break;
            case 1:
                game.addEvent(p.name + " walks so quietly they steal a base before anybody notices!");
                break;
            case 2:
                game.addEvent("Is it really stealing a base if you just walk to it?");
                break;
        }
    }
    
    public void beforePitch(){
        if(game.r.nextDouble() > 0.1)
            return;
        int rand;
        if(game.weather instanceof PulsarPulsar)
            rand = (int)(game.r.nextDouble() * 18);
        else
            rand = (int)(game.r.nextDouble() * 11);
        switch(rand){
            case 0:
                game.addEvent("What a nice day!");
                break;
            case 1:
                game.addEvent("A refreshing breeze blows.");
                break;
            case 2:
                game.addEvent("Outfielders are strolling arm-in-arm.");
                break;
            case 3:
                game.addEvent("Fans are getting their steps in.");
                break;
            case 4:
                game.addEvent("Some fans amble away from the game for a walk of their own, but who could blame them?");
                break;
            case 5:
                game.addEvent("The pitcher takes a short walk around the mound and wishes it lasted longer.");
                break;
            case 6:
                game.addEvent("The air is especially fragrant today.");
                break;
            case 7:
                game.addEvent("Fluffy white clouds skitter across the sky.");
                break;
            case 8:
                game.addEvent("A gentle wind blows.");
                break;
            case 9:
                game.addEvent("Birds drift on the breeze.");
                break;
            case 10:
                game.addEvent("Even rogue umps are enjoying the weather!  As far as anyone can tell…");
                break;
            case 11:
                game.addEvent("The outfielders hope next inning has nice weather too.");
                break;
            case 12:
                game.addEvent("The gentle breezes are pulled in by the pulsar.");
                break;
            case 13:
                game.addEvent("The pulsar flashes through the fluffy white clouds.");
                break;
            case 14:
                game.addEvent("A bird sings a pulsing song.");
                break;
            case 15:
                game.addEvent("The pulsar flashes at a walking pace.");
                break;
            case 16:
                game.addEvent("The winds blow in rhythm.");
                break;
            case 17:
                game.addEvent("The sky is brighter than ever.");
                break;
        }
    }
    
    public void beforeWalk(){
        if(game.r.nextDouble() > 0.15)
            return;
        game.addEvent(game.batter.name + " feels energized by the crisp air and takes an extra base in their walk!");
        doubleWalk = true;
    }

    public void afterWalk(){
        Player stubbed = null;
        if(game.r.nextDouble() <= 0.1)
            stubbed = game.bases[0];
        if(doubleWalk){
            doubleWalk = false;
            boolean scored = false;
            Player moving = game.bases[0];
            if(moving == null)
                return;
            for(int x = 1; x < game.bases.length; x++){
                if(game.bases[x] != null){
                    Player temp = moving;
                    moving = game.bases[x];
                    game.bases[x] = temp;
                    if(x == game.bases.length - 1){
                        game.score(moving);
                        scored = true;
                    }
                }else{
                    game.bases[x] = moving;
                    break;
                }
            }
            if(scored){
                game.printScore();
            }
        }
        if(game.r.nextDouble() <= 0.1){
            Player p = game.bases[0];
            if(p != null){
                game.addEvent("A cold wind buffets " + p.name + "!  Their legs shake and move!  " + p.name + " walks until they’re just a spot on the horizon.");
                game.bases[0] = null;
                p.addMod("Elsewhere");
            }
        }
        if(stubbed == null)
            return;
        stubbed.addMod("Stubbed Toe");
        game.addEvent(stubbed.name + " stubs their toe and doesn’t feel like walking anymore.");
    }

    public void scoreOnWalk(Player p){
        if(game.r.nextDouble() > 0.6)
            return;
        int rand = (int)(game.r.nextDouble() * 4);
        switch(rand){
            case 0:
                game.addEvent(p.name + " speedwalks home!");
                break;
            case 1:
                game.addEvent(p.name + " runs home and enjoys the hike!");
                break;
            case 2:
                game.addEvent(p.name + " just strolls leisurely home but still makes it!");
                break;
            case 3:
                game.addEvent(p.name + " skips happily to home base!");
                break;
        }
    }
    
    public void afterHomeRun(){
        if(game.r.nextDouble() > 0.6)
            return;
        int rand = (int)(game.r.nextDouble() * 4);
        switch(rand){
            case 0:
                game.addEvent(game.batter.name + " smugly walks around the bases!");
                break;
            case 1:
                game.addEvent(game.batter.name + " draws out rounding the bases for too long and gets yelled at by the umps!");
                break;
            case 2:
                game.addEvent(game.batter.name + " walks their victory lap!");
                break;
            case 3:
                game.addEvent("It’s a home walk!");
                break;
        }
    }
    
    public void beforeEndOfPlay(){
        for(Player p : game.activePlayers()){
            if(p.hasMod("Elsewhere")){
                boolean teamA = false;
                boolean teamB = false;
                for(Player player : game.teamA.getActivePlayers()){
                    if(player.equals(p)){
                        teamA = true;
                        break;
                    }
                }
                for(Player player : game.teamB.getActivePlayers()){
                    if(player.equals(p)){
                        teamB = true;
                        break;
                    }
                }
                p.removeMod("Elsewhere");
                    game.addEvent(p.name + " has finished their long walk!");
                if(teamA || teamB)
                    game.addEvent(p.name + " scores!");
                if(teamA)
                    game.scoreA++;
                if(teamB)
                    game.scoreB++;
            }
        }
    }

    public void endOfGame(){
        for(Player p : game.activePlayers()){
            p.removeMod("Stubbed Toe");
        }
    }
}
