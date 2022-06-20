import java.util.HashMap;

//will be fully implemented next season
//elsewhere result is disabled becaue of infinite inning issues
public class Meownsoon extends Weather
{
    String nameA;
    String nameB;
    HashMap<Player,Integer> foodEaten;
    public Meownsoon(Game game){
        super(game);
        name = "Meownsoon";
        foodEaten = new HashMap<Player,Integer>();
        nameA = game.teamA.name;
        nameB = game.teamB.name;
    }
    
    public void eatFood(Player p){
        if(foodEaten.containsKey(p))
            foodEaten.put(p,foodEaten.get(p) + 1);
        else
            foodEaten.put(p,1);
        p.addStatistic("Food eaten");
    }
    
    public void beforeHalfInning(){
        if(!game.top && game.inning == 7){
            game.addEvent("It's the middle of the seventh! The cats stretch!");
        }
        int random = (int)(r.nextDouble() * 20);
        if((game.inning < 4 || (game.inning == 4 && game.top)) && random == 1)
            random = 2;
        int rand = (int)(r.nextDouble() * 2);
        Team team;
        switch(rand){
            case 0:
                team = game.teamA;
                break;
            case 1:default:
                team = game.teamB;
                break;
        }
        switch(random){
            case 0:
                if(!team.name.equals("Cats"))
                    game.addEvent("The Meownsoon Blows! The " + team.getTeamName() + " have become the " + team.location + " Cats!");
                else
                    game.addEvent("The Meownsoon Blows! The " + team.getTeamName() + " are still Cats!");
                team.name = "Cats";
                for(Player p : team.getActivePlayers()){
                    p.addMod("Cat");
                }
                break;
            case 1:
                String s = "The Meownsoon rains catnip onto the field! All of the cats become";
                random = (int)(r.nextDouble() * 2);
                switch(random){
                    case 0:
                        game.addEvent(s + " wilder.");
                        for(Player p : game.activePlayers()){
                            if(p.hasMod("Cat")){
                                p.boostBatting(1);
                                p.boostPitching(1);
                                p.boostBaserunning(1);
                                p.boostDefense(1);
                            }
                        }
                        break;
                    case 1:default:
                        game.addEvent(s + " milder.");
                        for(Player p : game.activePlayers()){
                            if(p.hasMod("Cat")){
                                p.boostBatting(-1);
                                p.boostPitching(-1);
                                p.boostBaserunning(-1);
                                p.boostDefense(-1);
                            }
                        }
                        break;
                }
                break;
            default:
                Player p = game.randomActivePlayer(team);
                if(!p.hasMod("Cat"))
                    game.addEvent("The Meownsoon Blows! " + p.name + " becomes a cat!");
                else
                    game.addEvent("The Meownsoon Blows! But " + p.name + " is already a cat!");
                p.addMod("Cat");
                break;
        }
    }
    
    public void beforePitch(){
        double rand = r.nextDouble();
        if(rand > 0.33)
            return;
        int random = (int)(r.nextDouble() * 4);
        Player p;
        Team t;
        int i = -1;
        switch(random){
            case 0://batter
                p = game.batter;
                t = game.battingTeam;
                if(!p.hasMod("Cat"))
                    return;
                random = (int)(r.nextDouble() * 5);
                switch(random){
                    case 0:
                        game.addEvent(p.name + " leaps into the stands and steals a fan's sunflower seeds! Their batting improves slightly.");
                        eatFood(p);
                        p.boostBatting(0.5);
                        break;
                    case 1:
                        game.addEvent(p.name + " leaps into the stands and steals a fan's hot dog! Their batting improves drastically.");
                        eatFood(p);
                        p.boostBatting(1.5);
                        break;
                }
                break;
            case 1://pitcher
                t = game.pitchingTeam;
                p = game.pitcher;
                if(!p.hasMod("Cat"))
                    return;
                random = (int)(r.nextDouble() * 4);
                switch(random){
                    case 0:
                        game.addEvent(p.name + " leaps into the stands and steals a fan's chips! Their pitching improves slightly.");
                        eatFood(p);
                        p.boostPitching(0.5);
                        break;
                    case 1:
                        game.addEvent(p.name + " leaps into the stands and steals a fan's sunflower seeds! Their pitching improves drastically.");
                        eatFood(p);
                        p.boostPitching(1.5);
                        break;
                }
                break;
            case 2://baserunner
                t = game.battingTeam;
                if(game.basesEmpty())
                    return;
                i = game.randomOccupiedBase();
                p = game.bases[i];
                if(!p.hasMod("Cat"))
                    return;
                random = (int)(r.nextDouble() * 4)+1;
                switch(random){
                    case 1:
                        game.addEvent(p.name + " leaps into the stands and steals a fan's slushie! They get a brain freeze, and are swept off base in a frenzy!");
                        eatFood(p);
                        game.bases[i] = null;
                        break;
                }
                break;
            case 3:default://defender
                t = game.pitchingTeam;
                p = game.randomDefender();
                if(!p.hasMod("Cat"))
                    return;
                random = (int)(r.nextDouble() * 3)+2;
                break;
        }
        switch(random){
            case 2:
                if(p == null)
                    break;
                game.addEvent(p.name + " leaps into the stands and steals a fan's peanuts! They taste the Infinite, and purr back!");
                eatFood(p);
                break;
            case 3:
                if(p == null)
                    break;
                game.addEvent("Rogue Umpire incinerates " + p.name + "! " + p.name + " loses a life! The Umpire is incinerated for being a dog person!");
                break;
            case 4:
                if(p == null)
                    break;
                game.addEvent(p.name + " leaps into the stands and steals a fan's breakfast! They take a catnap, and don't wake up for the rest of the game!");
                eatFood(p);
                if(i != -1)
                    game.bases[i] = null;
                if(Team.ablePlayers(t.lineup) > 1){
                    p.addMod("Elsewhere");
                }
                break;
        }
        if(p == null)
            return;
        try{
            if(foodEaten.get(p) >= 3){
                game.addEvent(p.name + " overate, resetting all stat boosts!");
                p.clearTemporaryStats();
            }
        }catch(NullPointerException e){
            return;
        }
    }
    
    public void endOfGame(){
        game.teamA.name = nameA;
        game.teamB.name = nameB;
        for(Player p : game.activePlayers()){
            p.removeMod("Cat");
            p.removeMod("Elsewhere");
        }
    }
}
