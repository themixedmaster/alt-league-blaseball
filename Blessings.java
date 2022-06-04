import java.util.*;

public class Blessings
{

    //Season 1 blessings

    public static void maxBatting(Team team){
        Player[] lineup = team.lineup;
        Player player = lineup[(int)(League.r.nextDouble() * lineup.length)];
        player.batting = 5;
    }

    public static void maxPitching(Team team){
        //System.out.println("max pitching");
        Player[] rotation = team.rotation;
        Player player = rotation[(int)(League.r.nextDouble() * rotation.length)];
        //System.out.println(player.name + " was maxed from " + player.pitching);
        player.pitching = 5;
    }

    public static void maxBaserunning(Team team){
        Player[] lineup = team.lineup;
        Player player = lineup[(int)(League.r.nextDouble() * lineup.length)];
        player.baserunning = 5;
    }

    public static void maxDefense(Team team){
        Player[] lineup = team.lineup;
        Player player = lineup[(int)(League.r.nextDouble() * lineup.length)];
        player.defense = 5;
    }

    public static void lineupFocus(Team team){
        Player[] lineup = team.lineup;
        Player[] rotation = team.rotation;
        Player[] newLineup = new Player[lineup.length+1];
        Player[] newRotation = new Player[rotation.length-1];
        Player bestHitPitcher = null;
        int bestHitPitcherLocation = 0;
        for(int x = 0; x < rotation.length; x++){
            if(bestHitPitcher == null || rotation[x].batting > bestHitPitcher.batting){
                bestHitPitcher = rotation[x];
                bestHitPitcherLocation = x;
            }
        }
        int iteratePosition = 0;
        for(int x = 0; x < rotation.length; x++){
            if(x != bestHitPitcherLocation){
                newRotation[iteratePosition] = rotation[x];
                iteratePosition++;
            }
        }
        for(int x = 0; x < lineup.length; x++){
            newLineup[x] = lineup[x];
        }
        newLineup[lineup.length] = bestHitPitcher;
        team.lineup = newLineup;
        team.rotation = newRotation;
    }

    public static void rotationFocus(Team team){
        Player[] lineup = team.lineup;
        Player[] rotation = team.rotation;
        Player[] newLineup = new Player[lineup.length-1];
        Player[] newRotation = new Player[rotation.length+1];
        Player bestPitchHitter = null;
        int bestPitchHitterLocation = 0;
        for(int x = 0; x < lineup.length; x++){
            if(bestPitchHitter == null || lineup[x].pitching > bestPitchHitter.pitching){
                bestPitchHitter = lineup[x];
                bestPitchHitterLocation = x;
            }
        }
        int iteratePosition = 0;
        for(int x = 0; x < lineup.length; x++){
            if(x != bestPitchHitterLocation){
                newLineup[iteratePosition] = lineup[x];
                iteratePosition++;
            }
        }
        for(int x = 0; x < rotation.length; x++){
            newRotation[x] = rotation[x];
        }
        newRotation[rotation.length] = bestPitchHitter;
        team.lineup = newLineup;
        team.rotation = newRotation;
    }

    public static void smallBoost(Team team){
        Player[] players = team.getActivePlayers();
        for(Player player : players){
            player.batting *= 1.05;
            player.pitching *= 1.05;
            player.baserunning *= 1.05;
            player.defense *= 1.05;
        }
    }

    public static void largeBoost(Team team){
        Player[] players = team.getActivePlayers();
        for(Player player : players){
            player.batting *= 1.1;
            player.pitching *= 1.1;
            player.baserunning *= 1.1;
            player.defense *= 1.1;
        }
    }

    public static void freshStart(Team team){
        Player[] players = team.getActivePlayers();
        for(Player player : players){
            player.batting = Player.randomStat();
            player.pitching = Player.randomStat();
            player.baserunning = Player.randomStat();
            player.defense = Player.randomStat();
        }
    }

    public static void sabotage(Team team, Team champion){
        Player[] lineup = team.lineup;
        Player[] championLineup = champion.lineup;
        Player worst = null;
        int worstPos = 0;
        for(int x = 0; x < lineup.length; x++){
            if(worst == null || lineup[x].batting < worst.batting){
                worst = lineup[x];
                worstPos = x;
            }
        }
        Player best = null;
        int bestPos = 0;
        for(int x = 0; x < championLineup.length; x++){
            if(best == null || championLineup[x].batting > best.batting){
                best = championLineup[x];
                bestPos = x;
            }
        }
        lineup[worstPos] = best;
        championLineup[bestPos] = worst;
    }

    //Season 2 blessings

    public static void battingGoo(Team team){
        //System.out.println("batting goo");
        Player[] lineup = team.lineup;
        for(int x = 0; x < 3; x++){
            Player worst = null;
            for(int y = 0; y < lineup.length; y++){
                if(worst == null || lineup[y].batting < worst.batting){
                    worst = lineup[y];
                }
            }
            //System.out.print(worst.name + " rerolled from " + worst.batting + " to ");
            worst.batting = Player.randomStat();
            //System.out.println(worst.batting);
        }
    }

    public static void pitchingGoo(Team team){
        //System.out.println("pitching goo");
        Player[] rotation = team.rotation;
        for(int x = 0; x < 2; x++){
            Player worst = null;
            for(int y = 0; y < rotation.length; y++){
                if(worst == null || rotation[y].pitching < worst.pitching){
                    worst = rotation[y];
                }
            }
            //System.out.print(worst.name + " rerolled from " + worst.pitching + " to ");
            worst.pitching = Player.randomStat();
            //System.out.println(worst.pitching);
        }
    }

    public static void tradeOff(Team team){
        //System.out.println("trade off");
        Player[] lineup = team.lineup;
        Player[] rotation = team.rotation;
        Player worstBat = null;
        int worstBatPos = 0;
        for(int x = 0; x < lineup.length; x++){
            if(worstBat == null || lineup[x].batting < worstBat.batting){
                worstBat = lineup[x];
                worstBatPos = x;
            }
        }
        Player worstPitch = null;
        int worstPitchPos = 0;
        for(int x = 0; x < rotation.length; x++){
            if(worstPitch == null || rotation[x].pitching < worstPitch.pitching){
                worstPitch = rotation[x];
                worstPitchPos = x;
            }
        }
        //System.out.println("pitcher " + worstPitch.name + " swapped with batter " + worstBat.name);
        lineup[worstBatPos] = worstPitch;
        rotation[worstPitchPos] = worstBat;
    }

    public static void waitImBatting(Team team){
        //System.out.println("Wait I'm batting?");
        Player[] lineup = team.lineup;
        Player worst = null;
        for(int x = 0; x < lineup.length; x++){
            if(worst == null || lineup[x].batting < worst.batting){
                worst = lineup[x];
            }
        }
        double temp = worst.batting;
        worst.batting = worst.pitching;
        worst.pitching = temp;
        //System.out.println(worst.name + "swapped " + worst.pitching + " batting for " + worst.batting + " pitching");
    }

    public static void waitImPitching(Team team){
        //System.out.println("Wait I'm pitching?");
        Player[] rotation = team.rotation;
        Player worst = null;
        for(int x = 0; x < rotation.length; x++){
            if(worst == null || rotation[x].pitching < worst.pitching){
                worst = rotation[x];
            }
        }
        double temp = worst.batting;
        worst.batting = worst.pitching;
        worst.pitching = temp;
        //System.out.println(worst.name + "swapped " + worst.pitching + " batting for " + worst.batting + " pitching");
    }

    public static void defragmentation(Team team){
        Player[] lineup = team.lineup;
        for(int x = 0;x < lineup.length; x++){
            int bestBatter = x;
            for(int y = x + 1;y < lineup.length; y++){
                if(lineup[y].batting > lineup[bestBatter].batting){
                    bestBatter = y;
                }
            }
            Player temp = lineup[bestBatter];
            lineup[bestBatter] = lineup[x];
            lineup[x] = temp;
        }
    }

    public static void infamy(Team team, Team[] league){
        //System.out.println("Infamy");
        Player[] lineup = team.lineup;
        Player[] rotation = team.rotation;
        Player worst = null;
        double worstStars = Double.MAX_VALUE;
        boolean batter = true;
        for(int x = 0; x < lineup.length; x++){
            if(worst == null || lineup[x].batting < worst.batting){
                worst = lineup[x];
                worstStars = worst.batting;
                batter = true;
            }
        }
        for(int x = 0; x < rotation.length; x++){
            if(rotation[x].pitching < worstStars){
                worst = rotation[x];
                worstStars = worst.pitching;
                batter = false;
            }
        }
        for(Team otherTeam : league){
            if(team.equals(otherTeam)){
                continue;
            }
            if(batter){
                Player[] newLineup = new Player[otherTeam.lineup.length + 1];
                for(int x = 0; x < otherTeam.lineup.length; x++){
                    newLineup[x] = otherTeam.lineup[x];
                }
                newLineup[otherTeam.lineup.length] = worst;
                otherTeam.lineup = newLineup;
            }else{
                Player[] newRotation = new Player[otherTeam.rotation.length + 1];
                for(int x = 0; x < otherTeam.rotation.length; x++){
                    newRotation[x] = otherTeam.rotation[x];
                }
                newRotation[otherTeam.rotation.length] = worst;
                otherTeam.rotation = newRotation;
            }
        }
        //System.out.println(worst.name + " lives in infamy");
        if(worst.originalTeam == null){
            worst.originalTeam = team;
        }
    }

    public static void shoes(Team team){
        Player[] players = team.getActivePlayers();
        for(Player player : players){
            player.baserunning *= 1.1;
        }
    }

    public static void defenseReal(Team team){
        Player[] players = team.getActivePlayers();
        for(Player player : players){
            player.defense *= 1.1;
        }
    }

    public static void birdCall(Team team){
        //System.out.println("Bird Call");
        for(int y = 0; y < 3; y++){
            Player[] lineup = team.lineup;
            Player[] rotation = team.rotation;
            Player worst = null;
            boolean batter = true;
            int worstPos = 0;
            for(int x = 0; x < lineup.length; x++){
                if(worst == null || lineup[x].totalStars() < worst.totalStars()){
                    worst = lineup[x];
                    worstPos = x;
                    batter = true;
                }
            }
            for(int x = 0; x < rotation.length; x++){
                if(worst == null || rotation[x].totalStars() < worst.totalStars()){
                    worst = rotation[x];
                    worstPos = x;
                    batter = false;
                }
            }
            League.birdNest.add(worst);
            Player bird = new Player("Bird " + RandomNameGenerator.randomNameWithDistribution(RandomNameGenerator.random(1,5) + RandomNameGenerator.random(1,5)));
            PlayerMaker.addFlavor(bird);
            bird.pregameRitual = "[BIRD NOISES]";
            bird.fate = (int)(League.r.nextDouble() * 1000);
            bird.soulscream = "";
            if(batter)
                lineup[worstPos] = bird;
            else
                rotation[worstPos] = bird;
            //System.out.println(worst.name + " will be taken care of by the birds.");
            //System.out.println(bird.name + " will take their place.");
        }
    }

    public static void approachTheSecondSun(Team team){
        //System.out.println("approach the 2nd sun");
        Player[] players = team.getActivePlayers();
        for(Player player : players){
            if(player.batting < 2){
                //System.out.println(player.name + " batting (" + player.batting + " -> 2");
                player.batting = 2;
            }
            if(player.pitching < 2){
                //System.out.println(player.name + " pitching (" + player.pitching + " -> 2");
                player.pitching = 2;
            }
            if(player.baserunning < 2){
                //System.out.println(player.name + " baserunning (" + player.baserunning + " -> 2");
                player.baserunning = 2;
            }
            if(player.defense < 2){
                //System.out.println(player.name + " defense (" + player.defense + " -> 2");
                player.defense = 2;
            }
        }
    }

    public static void eventHorizon(Team team, Team champion){
        //System.out.println("Event horizon");
        Player[] championPlayers = champion.getActivePlayers();
        Player best = null;
        for(int x = 0; x < championPlayers.length; x++){
            if(best == null || championPlayers[x].totalStars() > best.totalStars()){
                best = championPlayers[x];
            }
        }
        int stars = 0;
        //System.out.print(best.name + " was compressed! (" + best.totalStars() + " -> ");
        while(best.batting >= 1){
            best.batting-=1;
            stars++;
        }
        while(best.pitching >= 1){
            best.pitching-=1;
            stars++;
        }
        while(best.baserunning >= 1){
            best.baserunning-=1;
            stars++;
        }
        while(best.defense >= 1){
            best.defense-=1;
            stars++;
        }
        //System.out.println(best.totalStars() + ")");
        Player[] players = team.getActivePlayers();
        while(stars > 0){
            Player player = players[(int)(League.r.nextDouble()*players.length)];
            switch((int)(League.r.nextDouble() * 4)){
                case 0:
                    //System.out.println(player.name + "'s batting was raised! (" + player.batting + " -> " + (player.batting+2) + ")");
                    player.batting+=2;
                    break;
                case 1:
                    //System.out.println(player.name + "'s pitching was raised! (" + player.pitching + " -> " + (player.pitching+2) + ")");
                    player.pitching+=2;
                    break;
                case 2:
                    //System.out.println(player.name + "'s baserunning was raised! (" + player.baserunning + " -> " + (player.baserunning+2) + ")");
                    player.baserunning+=2;
                    break;
                case 3:
                    //System.out.println(player.name + "'s defense was raised! (" + player.defense + " -> " + (player.defense+2) + ")");
                    player.defense+=2;
                    break;
            }
            stars--;
        }
    }

    public static void cumulativeBoost(Team team){
        //System.out.println("cumulative boost");
        Player[] players = team.getActivePlayers();
        double stars = 0.1;
        for(int x = 0; x < players.length; x++){
            //System.out.println(players[x].name + "'s stars were raised! (" + players[x].totalStars() + " -> " + (players[x].totalStars()+stars * 4) + ")");
            players[x].batting += stars;
            players[x].pitching += stars;
            players[x].baserunning += stars;
            players[x].defense += stars;
            stars+=0.1;
        }
    }

    public static void boostCumulative(Team team){
        //System.out.println("boost cumulative");
        Player[] players = team.getActivePlayers();
        double stars = 0.1;
        for(int x = players.length - 1; x >= 0; x--){
            //System.out.println(players[x].name + "'s stars were raised! (" + players[x].totalStars() + " -> " + (players[x].totalStars()+stars * 4) + ")");
            players[x].batting += stars;
            players[x].pitching += stars;
            players[x].baserunning += stars;
            players[x].defense += stars;
            stars+=0.1;
        }
    }

    public static void reverse(Team team){
        //System.out.print("reverse");
        for(Player p : team.getActivePlayers()){
            double temp = p.batting;
            p.batting = p.pitching;
            p.pitching = temp;
            //System.out.println(p.name + "swapped " + p.pitching + " batting for " + p.batting + " pitching");
        }
    }

    public static void instructionManual(Team team){
        //System.out.println("Instruction manual");
        Player[] lineup = team.lineup;
        for(int y = 0; y < 2; y++){
            Player worst = null;
            for(int x = 0; x < lineup.length; x++){
                if(worst == null || lineup[x].batting < worst.batting){
                    worst = lineup[x];
                }
            }
            double temp = worst.batting;
            worst.batting = worst.pitching;
            worst.pitching = temp;
            //System.out.println(worst.name + " swapped " + worst.pitching + " batting for " + worst.batting + " pitching"); 
        }
    }

    public static void exploratorySurgeries(Team team){
        //System.out.println("exploratory surgeries");
        Player[] rotation = team.rotation;
        for(int x = 0; x < 3; x++){
            Player worst = null;
            for(int y = 0; y < rotation.length; y++){
                if(worst == null || rotation[y].pitching < worst.pitching){
                    worst = rotation[y];
                }
            }
            //System.out.print(worst.name + " rerolled from " + worst.pitching + " to ");
            worst.pitching = Player.randomStat();
            //System.out.println(worst.pitching);
        }
    }

    public static void chordataAves(Team team){
        Player[] players = team.getActivePlayers();
        for(Player player : players){
            player.baserunning += 1.5;
            player.pitching -= 1;
        }
    }

    public static void mollusca(Team team){
        Player[] players = team.getActivePlayers();
        for(Player player : players){
            player.pitching += 1.5;
            player.baserunning -= 1;
        }
    }

    public static void openTheForbiddenBook(Team team){
        //System.out.println("forbidden book");
        team.location = "Hellmouth";
        Player best = null;
        Team bestTeam = null;
        boolean batter = true;
        int bestPos = 0;
        for(Team t : League.getTeams()){
            Player[] lineup = t.lineup;
            Player[] rotation = t.rotation;
            for(int x = 0; x < lineup.length; x++){
                if(best == null || lineup[x].totalStars() > best.totalStars() && lineup[x].originalTeam == null){
                    best = lineup[x];
                    bestPos = x;
                    bestTeam = t;
                    batter = true;
                }
            }
            for(int x = 0; x < rotation.length; x++){
                if(best == null || rotation[x].totalStars() > best.totalStars() && rotation[x].originalTeam == null){
                    best = rotation[x];
                    bestPos = x;
                    bestTeam = t;
                    batter = false;
                }
            }
        }
        //System.out.println("Rogue Umpire incinerated " + bestTeam.getName() + " player, " + best.name);
        League.deceased = new ArrayList<Player>();
        best.originalTeam = bestTeam;
        League.deceased.add(best);
        Player p = PlayerMaker.newRandomPlayer();
        if(batter)
            bestTeam.lineup[bestPos] = p;
        else
            bestTeam.rotation[bestPos] = p;
        //System.out.println("They're replaced by " + p.name);
    }

    public static void outfamy(){
        for(Team t : League.getTeams()){
            boolean batting = true;
            int position = 0;
            boolean found = false;
            Player[] lineup = t.lineup;
            Player[] rotation = t.rotation;
            for(int x = 0; x < lineup.length; x++){
                if(lineup[x].originalTeam != null && !lineup[x].originalTeam.equals(t)){
                    batting = true;
                    position = x;
                    found = true;
                }
            }
            for(int x = 0; x < rotation.length; x++){
                if(rotation[x].originalTeam != null && !rotation[x].originalTeam.equals(t)){
                    batting = false;
                    position = x;
                    found = true;
                }
            }
            if(found){
                if(batting){
                    Player[] newLineup = new Player[lineup.length - 1];
                    for(int x = 0; x < position; x++){
                        newLineup[x] = lineup[x];
                    }
                    for(int x = position; x < newLineup.length; x++){
                        newLineup[x] = lineup[x+1];
                    }
                    t.lineup = newLineup;
                }else{
                    Player[] newRotation = new Player[rotation.length - 1];
                    for(int x = 0; x < position; x++){
                        newRotation[x] = rotation[x];
                    }
                    for(int x = position; x < newRotation.length; x++){
                        newRotation[x] = rotation[x+1];
                    }
                    t.rotation = newRotation;
                }
            }
        }
    }
    
    public static void lucky7s(Team team){
        for(int x = 0; x < 7; x++){
            Player worst = null;
            for(Player p : team.getActivePlayers()){
                if(worst == null || p.totalStars() < worst.totalStars()){
                    worst = p;
                }
            }
            worst.batting = Player.randomStat();
            worst.pitching = Player.randomStat();
            worst.baserunning = Player.randomStat();
            worst.defense = Player.randomStat();
        }
    }
    
    public static void option5(Team team){
        team.win(5);
    }
}
