//I'm probably going to have to rewrite this from scratch, we'll see.
import java.util.*;

public class Feed
{
    public static Scanner console = new Scanner(System.in);
    public static void ViewFeed(){
        System.out.println("Welcome to the feed!");
        /*for(int x = 1; x <= League.season;x++){
        System.out.println(x + "-Season " + x);
        }
        System.out.println("3-Exit feed");
        viewSeason(console.nextInt());*/
        League.resetLeague();
        viewSeason(League.season);
    }

    public static void viewSeason(int season){
        League.resetLeague();
        League.runGamesUpToDate();
        //System.out.println("Viewing season " + season + " (past seasons will be viewable in a later update)");
        System.out.println("Where would you like to go?");
        System.out.println("0-View games");
        //System.out.println("1-View election results");
        //System.out.println("2-View teams");
        System.out.println("1-View player statistics");
        System.out.println("2-Exit feed");
        switch(console.nextInt()){
            case 0:
                viewGames(season);
                break;
            case 1:
                viewPlayerStatistics(season);
                break;
            case 2:
                return;
        }
        viewSeason(season);
    }

    static void viewPlayerStatistics(int season){
        System.out.println("Which players would you like to view?");
        System.out.println("0-All players");
        System.out.println("1-All players of 1 team");
        System.out.println("2-Single player");
        ArrayList<Player> players;
        switch(console.nextInt()){
            case 1:
                players = getPlayersOfTeam();
                break;
            case 2:
                players = getSinglePlayer();
                break;
            default:
                players = League.allPlayers();
                break;
        }
        if(players.size() == 0){
            System.out.println("No players found");
            return;
        }
        System.out.println("Which statistics would you like to view?");
        System.out.println("0-Regular player stats");
        System.out.println("1-Advanced player stats");
        System.out.println("2-Performance statistics");
        System.out.println("3-everything");
        switch(console.nextInt()){
            case 0:
                for(Player p : players){
                    p.clearStatistics();
                    p.addStatistic("Batting",p.batting);
                    p.addStatistic("Pitching",p.pitching);
                    p.addStatistic("Baserunning",p.baserunning);
                    p.addStatistic("Defense",p.defense);
                }
                break;
            case 1:
                for(Player p : players){
                    p.clearStatistics();
                    p.addStatistic("Agression",p.aggression.value());
                    p.addStatistic("Arrogance",p.arrogance.value());
                    p.addStatistic("Carcinization",p.carcinization.value());
                    p.addStatistic("Damage",p.damage.value());
                    p.addStatistic("Density",p.density.value());
                    p.addStatistic("Dexterity",p.dexterity.value());
                    p.addStatistic("Dimensions",p.dimensions.value());
                    p.addStatistic("Effort",p.effort.value());
                    p.addStatistic("Focus",p.focus.value());
                    p.addStatistic("Fun",p.fun.value());
                    p.addStatistic("Grit",p.grit.value());
                    p.addStatistic("Hit Points",p.hitPoints.value());
                    p.addStatistic("Malleability",p.malleability.value());
                    p.addStatistic("Mathematics",p.mathematics.value());
                    p.addStatistic("Number of Eyes",p.numberOfEyes.value());
                    p.addStatistic("Pinpointedness",p.pinpointedness.value());
                    p.addStatistic("Powder",p.powder.value());
                    p.addStatistic("Rejection",p.rejection.value());
                    p.addStatistic("Splash",p.splash.value());
                    p.addStatistic("Wisdom",p.wisdom.value());
                }
                break;
            case 3:
                for(Player p : players){
                    p.addStatistic("Batting",p.batting);
                    p.addStatistic("Pitching",p.pitching);
                    p.addStatistic("Baserunning",p.baserunning);
                    p.addStatistic("Defense",p.defense);
                    p.addStatistic("Agression",p.aggression.value());
                    p.addStatistic("Arrogance",p.arrogance.value());
                    p.addStatistic("Carcinization",p.carcinization.value());
                    p.addStatistic("Damage",p.damage.value());
                    p.addStatistic("Density",p.density.value());
                    p.addStatistic("Dexterity",p.dexterity.value());
                    p.addStatistic("Dimensions",p.dimensions.value());
                    p.addStatistic("Effort",p.effort.value());
                    p.addStatistic("Focus",p.focus.value());
                    p.addStatistic("Fun",p.fun.value());
                    p.addStatistic("Grit",p.grit.value());
                    p.addStatistic("Hit Points",p.hitPoints.value());
                    p.addStatistic("Malleability",p.malleability.value());
                    p.addStatistic("Mathematics",p.mathematics.value());
                    p.addStatistic("Number of Eyes",p.numberOfEyes.value());
                    p.addStatistic("Pinpointedness",p.pinpointedness.value());
                    p.addStatistic("Powder",p.powder.value());
                    p.addStatistic("Rejection",p.rejection.value());
                    p.addStatistic("Splash",p.splash.value());
                    p.addStatistic("Wisdom",p.wisdom.value());
                }
                break;
        }
        System.out.println("Choose your viewing method");
        System.out.println("0-List by player (easier for looking at one player specifically)");
        System.out.println("1-List by stat (easier for copy/pasting into spreadsheets)");
        System.out.println("2-Comma delimited table (even easier to copy/paste than previous option)");
        ArrayList<String> statsTracked = new ArrayList<String>();
        switch(console.nextInt()){
            case 1:
                for(Player p : players){
                    for(Map.Entry<String,Double> entry : p.statistics.entrySet()){
                        if(!statsTracked.contains(entry.getKey()))
                            statsTracked.add(entry.getKey());
                    }
                }
                System.out.println("Player Name");
                for(Player p : players)
                    System.out.println(p.name);
                System.out.println();
                for(String s : statsTracked){
                    System.out.println(s);
                    for(Player p : players){
                        double d = p.getStatistic(s);
                        if(d % 1 != 0)
                            System.out.println(d);
                        else{
                            System.out.println((int)d);
                        }
                    }
                    System.out.println();
                }
                break;
            case 2:
                statsTracked.add("Games played");
                statsTracked.add("Plate appearances");
                statsTracked.add("Runs scored");
                statsTracked.add("Hits");
                statsTracked.add("Singles hit");
                statsTracked.add("Doubles hit");
                statsTracked.add("Triples hit");
                statsTracked.add("Home runs hit");
                statsTracked.add("Walks");
                statsTracked.add("Times struck out");
                statsTracked.add("Foul balls hit");
                statsTracked.add("Flyouts hit");
                statsTracked.add("Ground outs hit");
                statsTracked.add("Base steal attempts");
                statsTracked.add("Bases stolen");
                statsTracked.add("Caught stealing");
                statsTracked.add("Second base stolen");
                statsTracked.add("Caught stealing second base");
                statsTracked.add("Third base stolen");
                statsTracked.add("Caught stealing third base");
                statsTracked.add("Home stolen");
                statsTracked.add("Caught stealing home");
                statsTracked.add("Times pitched to");
                statsTracked.add("Strikes");
                statsTracked.add("Swinging strikes");
                statsTracked.add("Looking strikes");
                statsTracked.add("Balls received");
                statsTracked.add("Flyouts caught");
                statsTracked.add("Ground outs fielded");
                statsTracked.add("Pitcher wins");
                statsTracked.add("Shutouts");
                statsTracked.add("Innings pitched");
                statsTracked.add("Strikeouts");
                statsTracked.add("Pitches thrown");
                statsTracked.add("Balls thrown");
                statsTracked.add("Foul balls pitched");
                statsTracked.add("Walks allowed");
                statsTracked.add("Singles allowed");
                statsTracked.add("Doubles allowed");
                statsTracked.add("Triples allowed");
                statsTracked.add("Home runs allowed");
                statsTracked.add("Runs allowed");
                for(Player p : players){
                    for(Map.Entry<String,Double> entry : p.statistics.entrySet()){
                        if(!statsTracked.contains(entry.getKey()))
                            statsTracked.add(entry.getKey());
                    }
                }
                System.out.print("Player Name");
                for(String s : statsTracked)
                    System.out.print("," + s);
                System.out.println();
                for(Player p : players){
                    System.out.print(p.name);
                    for(String s : statsTracked){
                        double d = p.getStatistic(s);
                        if(d % 1 != 0)
                            System.out.print("," + d);
                        else{
                            System.out.print("," + (int)d);
                        }
                    }
                    System.out.println();
                }
                break;
            case 0: default:
                for(Player p : players){
                    System.out.println(p.name);
                    p.printStatistics();
                }
                break;
        }
    }

    static ArrayList<Player> getPlayersOfTeam(){
        System.out.println("Select a team:");
        Team[] teams = League.getTeams();
        for(int x = 0; x < teams.length; x++){
            System.out.println(x + "-" + teams[x].logo + " " + teams[x].getTeamName());
        }
        Team team = teams[console.nextInt()];
        ArrayList<Player> players = new ArrayList<Player>();
        for(Player p : team.getActivePlayers()){
            players.add(p);
        }
        return players;
    }

    static ArrayList<Player> getSinglePlayer(){
        System.out.println("How would you like to select a player?");
        System.out.println("0-Select player from list");
        System.out.println("1-Type player name");
        ArrayList<Player> singlePlayer = new ArrayList<Player>();
        ArrayList<Player> players = League.allPlayers();
        switch(console.nextInt()){
            case 0:
                for(int x = 0; x < players.size(); x++){
                    System.out.println(x + "-" + players.get(x).name);
                }
                int i = console.nextInt();
                if(i < players.size() & i >= 0)
                    singlePlayer.add(players.get(i));
                break;
            case 1:
                System.out.print("Type player name: ");
                console = new Scanner(System.in);
                String name = console.nextLine().toLowerCase();
                for(Player p : players){
                    if(name.equals(p.name.toLowerCase()))
                        singlePlayer.add(p);
                }
                break;
        }
        return singlePlayer;
    }

    public static void viewGames(int season){
        ArrayList<Game> games = filterByHasStarted(League.getAllGames());
        filterGames(games);
    }

    static void filterGames(ArrayList<Game> games){
        System.out.println("Which games do you want to view?");
        System.out.println("0-All games");
        System.out.println("1-Filter by game day");
        System.out.println("2-Filter by team");
        System.out.println("3-Filter by special event");
        switch(console.nextInt()){
            case 0:
                viewFilteredGames(games);
                break;
            case 1:
                viewFilteredGames(filterByDay(games));
                break;
            case 2:
                viewFilteredGames(filterByTeam(games));
                break;
            case 3:
                viewFilteredGames(filterBySpecial(games));
                break;
        }
    }

    public static ArrayList<Game> filterByHasStarted(ArrayList<Game> games){
        ArrayList<Game> filteredList = new ArrayList<Game>();
        for(Game g : games){
            if(g.startTime <= System.currentTimeMillis())
                filteredList.add(g);
        }
        return filteredList;
    }

    public static ArrayList<Game> filterBySpecial(ArrayList<Game> games){
        ArrayList<Game> filteredList = new ArrayList<Game>();
        for(Game g : games){
            boolean special = false;
            for(Event e : g.events)
                if(e.isSpecial() && e.time <= System.currentTimeMillis())
                    special = true;
            if(special)
                filteredList.add(g);
        }
        return filteredList;
    }

    public static ArrayList<Game> filterByDay(ArrayList<Game> games){
        System.out.println("Which game day would you like to view?");
        int day = console.nextInt();
        ArrayList<Game> filteredList = new ArrayList<Game>();
        for(Game g : games){
            if(g.dayNum == day)
                filteredList.add(g);            
        }
        return filteredList;
    }

    public static ArrayList<Game> filterByTeam(ArrayList<Game> games){
        System.out.println("Which team would you like to view?");
        Team[] teams = League.getTeams();
        for(int x = 0; x < teams.length; x++){
            System.out.println(x + "-" + teams[x].logo + " " + teams[x].getTeamName());
        }
        Team team = teams[console.nextInt()];
        ArrayList<Game> filteredList = new ArrayList<Game>();
        for(Game g : games){
            if(g.teamA.equals(team) || g.teamB.equals(team))
                filteredList.add(g);
        }
        return filteredList;
    }

    public static void viewFilteredGames(ArrayList<Game> games){
        if(games.size() == 0 || games == null){
            System.out.println("No games found.");
            return;
        }
        System.out.println("Would you like to see logs or results?");
        System.out.println("0-View game results");
        System.out.println("1-View game logs");
        System.out.println("2-Add another filter");
        switch(console.nextInt()){
            case 0:
                for(int x = 0; x < games.size(); x++){
                    Game game = games.get(x);
                    System.out.print(game.gameName());
                    if(game.isLive())
                        System.out.println(" (LIVE)");
                    else
                        System.out.println(" (" + game.scoreToString(game.scoreA) + "-" + game.scoreToString(game.scoreB) + ")");
                }
                break;
            case 1:
                System.out.println("Please select a game to watch:");
                for(int x = 0; x < games.size(); x++){
                    Game game = games.get(x);
                    System.out.println(x + "-" + game.gameName());
                }
                watchGame(games.get(console.nextInt()));
                break;
            case 2:
                filterGames(games);
                break;
        }
    }

    public static void watchGame(Game game){
        System.out.println("How do you want to view the game?");
        System.out.println("0-Show log");
        System.out.println("1-Rewatch game");
        switch(console.nextInt()){
            case 1:
                game.setStartTime(System.currentTimeMillis());
            case 0:
                game.playLog();
                break;
        }
    }
}
