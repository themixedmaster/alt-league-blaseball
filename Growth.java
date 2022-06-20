public class Growth extends Weather
{
    public Growth(Game game){
        super(game);
        name = "Growth";
    }
    
    public void startOfGame(){
        double boost = ((game.dayNum-1)*5.0/99.0) / 100.0;
        if(boost < 0.05)
            game.addEvent("The Grass Multiplies, Expanding. The " + game.teamA.name + " and " + game.teamB.name + " experience " + game.dayNum + " Days of Growth");
        else{
            boost = 0.05;
            game.addEvent("The Grass Multiplies, Expanding. The " + game.teamA.name + " and " + game.teamB.name + " experience Maximum Growth");
        }
        for(Player p : game.activePlayers()){
            p.multiplyStats(1 + boost);
        }
    }
    
    public void beforePitch(){
        if(r.nextDouble() > 0.00025)
            return;
        Player p = new Player();
        int rand = (int)(r.nextDouble() * 2);
        Team t;
        switch(rand){
            case 0:
                t = game.teamA;
                break;
            case 1: default:
                t = game.teamB;
                break;
        }
        game.addEvent("WATER AND LIGHT");
        game.addEvent("AN EGG PLANT");
        game.addEvent("GROWING");
        game.addEvent(p.name.toUpperCase() + " ADVANCES ONTO THE " + t.getTeamName().toUpperCase(),true);
        p.addStatistic("Joined team through growth");
        rand = (int)(r.nextDouble() * 4);
        Player[] players;
        switch(rand){
            case 0: case 1: case 2:
                players = t.lineup;
                break;
            case 3: default: 
                players = t.rotation;
                break;
        }
        Player[] newPlayers = new Player[players.length + 1];
        for(int x = 0; x < players.length; x++){
            newPlayers[x] = players[x];
        }
        newPlayers[players.length] = p;
    }
}
