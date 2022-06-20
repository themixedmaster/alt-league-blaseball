public class NamedWeather extends Weather
{
    int mode;
    public NamedWeather(Game game){
        super(game);
        name = "Weather";
        int rand = (int)(r.nextDouble() * 5);
        switch(rand){
            case 0:
                mode = 0;//both teams get 0 wins
                break;
            case 1:
                mode = 2;//both teams get 2 wins
                break;
            default:
                mode = 1;//both teams get 1 win
                break;
        }
    }

    public void beforeHalfInning(){
        int rand;
        switch(mode){
            case 0:
                rand = (int)(r.nextDouble() * 7);
                switch(rand){
                    case 0:
                        game.addEvent("Images taken of the World Meteor Shower by gifted photographer.");
                        break;
                    case 1:
                        game.addEvent("Reports of widespread worldwide destruction of cities. More at five.");
                        break;
                    case 2:
                        game.addEvent("Forests went up in flames. Solar Eclipse denial still persists.");
                        break;
                    case 3:
                        game.addEvent("Scientists discover curious ancient artifacts in space rocks.");
                        break;
                    case 4:
                        game.addEvent("Death toll rises to the thousands nationwide.");
                        break;
                    case 5:
                        game.addEvent("The Commisioner advises Fans to stay in bunkers and protect themselves.");
                        break;
                    case 6:
                        game.addEvent("Play continues despite widespread calamity.");
                        break;
                }
                break;
            case 1:
                rand = (int)(r.nextDouble() * 7);
                switch(rand){
                    case 0:
                        game.addEvent("Players on the outfield crawl around like snakes.");
                        break;
                    case 1:
                        game.addEvent("Suns are popping like big eggs - the Commisioner states in recent press conference.");
                        break;
                    case 2:
                        game.addEvent("Nothing is real. Look to the suns.");
                        break;
                    case 3:
                        game.addEvent("The fabric of reality melts like ice cream on pavement.");
                        break;
                    case 4:
                        game.addEvent("Summer heat may cause hallucinations, confusion, and an imperative to be hit by a ball.");
                        break;
                    case 5:
                        game.addEvent("A Rogue Umpire is accidentally incinerated by a Rogue Umpire! A Rougue Umpire is replaced by a Rogue Umpire.");
                        break;
                    case 6:
                        game.addEvent("Play continues despite widespread calamity.");
                        break;
                }
                break;
            case 2:
                rand = (int)(r.nextDouble() * 7);
                switch(rand){
                    case 0:
                        game.addEvent("Strange stone monuments continue to rise in the major cities of the world. Conspiracists still blame Moon aliens.");
                        break;
                    case 1:
                        game.addEvent("Total breakdown of communications reported worldswide according to flare signals and messenger pigeons.");
                        break;
                    case 2:
                        game.addEvent("Hospitals flooded with victims of strange radiation.");
                        break;
                    case 3:
                        game.addEvent("The Church of the Monoliths gather followers and worshippers en-massed, growing to status of a major religion.");
                        break;
                    case 4:
                        game.addEvent("New species of altered humans protested against racial discrimination and demand equal rights to all.");
                        break;
                    case 5:
                        game.addEvent("Monolithians declare total war, erasing cities and countries from existence.");
                        break;
                    case 6:
                        game.addEvent("We will kill God if we have to - said government spokesperson.");
                        break;
                }
                break;
        }
    }

    public int alterWinsA(int winsA){
        return mode;
    }

    public int alterWinsB(int winsB){
        return mode;
    }

    public void endOfGame(){
        double x = ((int)(game.scoreA))*0.01;
        double y = ((int)(game.scoreB))*0.01;
        switch(mode){
            case 0:
                game.addEvent("A destructive Meteor Shower strikes the stadium, ending play without recorded wins.");
                game.addEvent("The " + game.teamA.name + " are boosted by " + x + "% by exercising the not-wanting-to-be-hit muscle.");
                game.addEvent("The " + game.teamB.name + " are boosted by " + y + "% by exercising the running-away-and-screaming muscle.");
                break;
            case 1:
                game.addEvent("Suns shine overhead, confusing Scorekeepers and giving a Win to both Teams.");
                game.addEvent("The " + game.teamA.name + " are boosted by " + x + "% by the joy of shiny things.");
                game.addEvent("The " + game.teamB.name + " are boosted by " + y + "% by relaxing from playing and going sunbathing.");
                break;
            case 2:
                game.addEvent("Alien electromagnetic waves swept through the stadium, corrupting electronics and giving both Teams 2 Wins!");
                game.addEvent("The " + game.teamA.name + " are boosted by " + x + "% by numerical errors in the code.");
                game.addEvent("The " + game.teamB.name + " are boosted by " + y + "% by looking at ancient carvings on monoliths.");
                break;
        }
        for(Player p : game.teamA.getActivePlayers()){
            p.aggression.permanentMultiply(1 + x/100);
            p.arrogance.permanentMultiply(1 + x/100);
            p.carcinization.permanentMultiply(1 + x/100);
            p.damage.permanentMultiply(1 + x/100);
            p.density.permanentMultiply(1 + x/100);
            p.dexterity.permanentMultiply(1 + x/100);
            p.dimensions.permanentMultiply(1 + x/100);
            p.effort.permanentMultiply(1 + x/100);
            p.focus.permanentMultiply(1 + x/100);
            p.fun.permanentMultiply(1 + x/100);
            p.grit.permanentMultiply(1 + x/100);
            p.hitPoints.permanentMultiply(1 + x/100);
            p.malleability.permanentMultiply(1 + x/100);
            p.mathematics.permanentMultiply(1 + x/100);
            p.numberOfEyes.permanentMultiply(1 + x/100);
            p.pinpointedness.permanentMultiply(1 + x/100);
            p.powder.permanentMultiply(1 + x/100);
            p.rejection.permanentMultiply(1 + x/100);
            p.splash.permanentMultiply(1 + x/100);
            p.wisdom.permanentMultiply(1 + x/100);
        }
        for(Player p : game.teamB.getActivePlayers()){
            p.aggression.permanentMultiply(1 + y/100);
            p.arrogance.permanentMultiply(1 + y/100);
            p.carcinization.permanentMultiply(1 + y/100);
            p.damage.permanentMultiply(1 + y/100);
            p.density.permanentMultiply(1 + y/100);
            p.dexterity.permanentMultiply(1 + y/100);
            p.dimensions.permanentMultiply(1 + y/100);
            p.effort.permanentMultiply(1 + y/100);
            p.focus.permanentMultiply(1 + y/100);
            p.fun.permanentMultiply(1 + y/100);
            p.grit.permanentMultiply(1 + y/100);
            p.hitPoints.permanentMultiply(1 + y/100);
            p.malleability.permanentMultiply(1 + y/100);
            p.mathematics.permanentMultiply(1 + y/100);
            p.numberOfEyes.permanentMultiply(1 + y/100);
            p.pinpointedness.permanentMultiply(1 + y/100);
            p.powder.permanentMultiply(1 + y/100);
            p.rejection.permanentMultiply(1 + y/100);
            p.splash.permanentMultiply(1 + y/100);
            p.wisdom.permanentMultiply(1 + y/100);
        }
    }
}
