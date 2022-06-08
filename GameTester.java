import java.util.*;

public class GameTester
{
    public static void main(String[] args){
        Scanner console = new Scanner(System.in);
        System.out.print("Welcome to Alt(Alt) League Blaseball(alpha)! ");
        int selection = 0;
        do{
            System.out.println("Where would you like to go? ");
            System.out.println("0-Games\n1-Standings\n2-Election\n3-Feed(Beta)\n4-Exit");
            selection = console.nextInt();
            switch(selection){
                case 0:
                    League.resetLeague();
                    ArrayList<Game> games = League.getAllGames();
                    ArrayList<Game> liveGames = new ArrayList<Game>(); 
                    for(int x = 0; x < games.size(); x++){
                        games.get(x).simulateGame();
                        if(games.get(x).isLive()){
                            liveGames.add(games.get(x));
                        }
                    }
                    if(liveGames.size() > 0){
                        System.out.println("Which game would you like to watch?");
                    }else{
                        System.out.println("There are no games running at the moment.");
                        break;
                    }
                    for(int x = 0; x < liveGames.size(); x++){
                        System.out.println(x + "-" + liveGames.get(x).gameName());
                    }
                    selection = console.nextInt();
                    if(selection < liveGames.size() && selection > -1){
                        liveGames.get(selection).playLog();
                    }else{
                        System.out.println("No corresponding game found.");
                    }
                    System.out.println();
                    selection = 0;
                    break;
                case 1:
                    League.resetLeague();
                    selection = 0;
                    League.runGamesUpToDate();
                    if(League.getGameDay() > 114){
                        Team champion = League.getChampion();
                        System.out.println("Your season " + League.season + " champions are the " + champion.getTeamName() + "!");
                    }
                    if(League.getGameDay() > 99){
                        System.out.println("\n0-View regular season standings");
                        System.out.println("1-View postseason");
                        selection = console.nextInt();
                    }
                    switch(selection){
                        case 0:
                            League.printLeague();
                            System.out.println("\n0-View team pages");
                            System.out.println("1-Return to menu");
                            selection = console.nextInt();
                            switch(selection){
                                case 0:
                                    System.out.println("Select team:");
                                    Team[] teams = League.getTeams();
                                    for(int x = 0; x < teams.length; x++){
                                        System.out.println(x + "-" + teams[x].logo + " " + teams[x].getTeamName());
                                    }
                                    selection = console.nextInt();
                                    if(selection < 0 || selection >= teams.length){
                                        System.out.print("Team not found.");
                                    }else{
                                        Team selectedTeam = teams[selection];
                                        selectedTeam.printTeam();
                                        System.out.println("0-View player pages");
                                        System.out.println("1-Return to menu");
                                        selection = console.nextInt();
                                        switch(selection){
                                            case 0:
                                                int mode;
                                                System.out.println("Select mode:");
                                                System.out.println("0-Normal");
                                                System.out.println("1-Advanced Stats mode");
                                                mode = console.nextInt();
                                                System.out.println("Select player:");
                                                System.out.println("0-All players");
                                                Player[] players = selectedTeam.getActivePlayers();
                                                for(int x = 0; x < players.length; x++){
                                                    System.out.println((x + 1) + "-" + players[x].getName());
                                                }
                                                selection = console.nextInt();
                                                if(selection < 0 || selection > players.length){
                                                    System.out.println("Player not found.");
                                                }else{
                                                    if(selection == 0){
                                                        for(int x = 0; x < players.length; x++){
                                                            players[x].printPlayer(mode);
                                                        }
                                                    }else{
                                                        players[selection - 1].printPlayer(mode);
                                                    }
                                                }
                                                break;
                                            case 1:
                                                break;
                                        }
                                    }
                                    break;
                                case 1:
                                    break;
                                default:
                                    System.out.println("");
                                    break;
                            }
                            break;
                        case 1:
                            League.printPostseason();
                    }
                    selection = 0;
                    break;
                case 2:
                    System.out.println("Election results:\n");
                    System.out.println("Shadow decrees:");
                    System.out.println("  Advanced stats - MEIOSIS. STATS DEFINED.");
                    System.out.println("  Alt^3 - FAN TEAMS DISSOLVED. ALTERNATE ALTERNATE.");
                    System.out.println("\nPost Election:\n");
                    System.out.println("BIRD: [BIRD NOISES]");
                    System.out.println("BIRD: I brought back the normal teams.");
                    System.out.println("BIRD: The regular players are still sleeping though.");
                    System.out.println("BIRD: I'm hard at work over here.");
                    System.out.println("BIRD: Blay pall!");
                    System.out.println("BIRD: [BIRD NOISES]");
                    System.out.println();
                    break;
                case 3:
                    Feed.ViewFeed();//this will just be checking player statistics for now
                    break;
                case 4:
                    break;
                default:
                    System.out.println("That number doesn't do anything.");
                    break;
            }
        }while(selection != 4);
    }
}
