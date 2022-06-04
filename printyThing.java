public class printyThing
{
    public static void main(String[] args){
        League.resetLeague();
        Game[][] games = League.getGames(1);
        for(int x = 0; x < games.length; x++){
            for(int y = 0; y < games[x].length; y++){
                //System.out.println((games[x][y].continueTime - games[x][y].startTime)/1000);
                System.out.println(Game.scoreToString(games[x][y].scoreB));
            }
        }
    }
}
