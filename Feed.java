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
        viewSeason(League.season);
    }

    public static void viewSeason(int season){
        if(season < 1 || season > League.season){
            return;
        }
        System.out.println("Viewing season " + season);
        System.out.println("0-View games");
        System.out.println("1-View election results");
        System.out.println("2-View teams");
        League.resetLeague();
        switch(console.nextInt()){
            case 0:
                viewGames(season);
                break;
            case 1:
                break;
            case 2:
                break;
        }
    }

    public static void viewGames(int season){
        if(season < 1 || season > League.season){
            return;
        }
        Game[][] games = League.getGames(season);
        System.out.println("Which games do you want to view?");
        System.out.println("0-All games");
        System.out.println("1-Filter by game day");
        System.out.println("2-Filter by team");
        switch(console.nextInt()){
            case 0:
                viewFilteredGames(filterByLive(filterAllGames(games)));
                break;
            case 1:
                viewFilteredGames(filterByLive(filterByDay(games)));
                break;
            case 2:
                viewFilteredGames(filterByLive(filterByTeam(games)));
                break;
        }
    }
    
    public static ArrayList<Game> filterAllGames(Game[][] games){
        ArrayList<Game> filteredList = new ArrayList<Game>();
        for(int x = 0; x < games.length; x++){
            for(int y = 0; y < games[x].length; y++){
                filteredList.add(games[x][y]);
            }
        }
        return filteredList;
    }

    public static ArrayList<Game> filterByDay(Game[][] games){
        System.out.println("Which game day would you like to view?");
        int day = console.nextInt();
        ArrayList<Game> filteredList = new ArrayList<Game>();
        for(int x = 0; x < games.length; x++){
            for(int y = 0; y < games[x].length; y++){
                if(games[x][y].dayNum == day)
                    filteredList.add(games[x][y]);
            }
        }
        return filteredList;
    }

    public static ArrayList<Game> filterByTeam(Game[][] games){
        System.out.println("Which team would you like to view?");
        Team[] teams = League.getTeams();
        for(int x = 0; x < teams.length; x++){
            System.out.println(x + "-" + teams[x].logo + " " + teams[x].getTeamName());
        }
        Team team = teams[console.nextInt()];
        ArrayList<Game> filteredList = new ArrayList<Game>();
        for(int x = 0; x < games.length; x++){
            for(int y = 0; y < games[x].length; y++){
                if(games[x][y].teamA.equals(team) || games[x][y].teamB.equals(team))
                    filteredList.add(games[x][y]);
            }
        }
        return filteredList;
    }

    public static ArrayList<Game> filterByLive(ArrayList<Game> games){
        ArrayList<Game> filteredList = new ArrayList<Game>();
        for(Game game : games){
            if(game.isLive())
                filteredList.add(game);
        }
        return filteredList;
    }
    
    public static void viewFilteredGames(ArrayList<Game> games){
        System.out.println("Would you like to see logs or results?");
        System.out.println("0-View game results");
        System.out.println("1-View game logs");
        switch(console.nextInt()){
            case 0:
                for(int x = 0; x < games.size(); x++){
                    Game game = games.get(x);
                    System.out.print("Day " + game.dayNum + ", " + game.gameName());
                    if(game.isLive())
                        System.out.println(" (LIVE)");
                    else
                        System.out.println(" " + game.scoreToString(game.scoreA) + "-" + game.scoreToString(game.scoreB));
                }
                break;
            case 1:
                System.out.println("Please select a game to watch:");
                for(int x = 0; x < games.size(); x++){
                    Game game = games.get(x);
                    System.out.println(x + "-Day " + game.dayNum + ", " + game.gameName());
                }
                watchGame(games.get(console.nextInt()));
                break;
        }
    }
    
    public static void watchGame(Game game){
            System.out.println("How do you want to view the game?");
            System.out.println("0-Show log");
            System.out.println("1-Rewatch game");
            switch(console.nextInt()){
                case 0:
                    game.watch();
                    game.runGame();
                    break;
                case 1:
                    game.startTime = System.currentTimeMillis();
                    game.watch();
                    game.runGame();
                    break;
            }
    }
}
