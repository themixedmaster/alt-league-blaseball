public class Team
{
    String name;
    String location;
    String logo;
    String abbreviation;
    public Player[] lineup;
    public Player[] rotation;
    public int actualWins;
    public int wins;
    public int losses;
    public int favor;
    public int id;
    public Team(){
        this("team", "null", "❓", 0, "NULL", randomPlayerArray(9), randomPlayerArray(5));
    }
    
    public Team(String name, String location, String logo, int favor, String abbreviation){
        this(name, location, logo, favor, abbreviation, randomPlayerArray(9), randomPlayerArray(5));
    }
    
    public Team(String name, String location, String logo,int favor, String abbreviation, Player[] lineup, Player[] rotation){
        this.name = name;
        this.location = location;
        this.logo = logo;
        this.favor = favor;
        this.abbreviation = abbreviation;
        this.lineup = lineup;
        this.rotation = rotation;
    }
    
    public static Player[] randomPlayerArray(int length){
        Player[] players = new Player[length];
        for(int x = 0; x < length; x++){
            players[x] = new Player();
        }
        return players;
    }
    
    public String getTeamName(){
        if(location.equals(""))
            return name;
        return location + " " + name;
    }
    
    public String getName(){
        return name;
    }
    
    public boolean equals(Team team){
        return getTeamName().equals(team.getTeamName());
    }
    
    public void printTeam(){
        System.out.println(logo + " " + getTeamName());
        System.out.println("    Lineup:");
        for(int x = 0; x < lineup.length; x++){
            System.out.print("        ");
            lineup[x].printPlayer("batting");
            //lineup[x].printStats();
        }
        System.out.println("    Rotation:");
        for(int x = 0; x < rotation.length; x++){
            System.out.print("        ");
            rotation[x].printPlayer("pitching");
            //rotation[x].printStats();
        }
        System.out.println();
    }
<<<<<<< Updated upstream

=======
    
    public static int ablePlayers(Player[] players){
        int x = 0;
        for(Player p : players)
            if(!p.hasMod("Elsewhere"))
                x++;
        return x;
    }
    
>>>>>>> Stashed changes
    public Player[] getActivePlayers(){
        Player[] players = new Player[lineup.length + rotation.length];
        for(int x = 0; x < lineup.length;x++){
            players[x] = lineup[x];
        }
        for(int x = 0; x < rotation.length;x++){
            players[lineup.length + x] = rotation[x];
        }
        return players;
    }
    
    public String getWins(){
        String w = "" + wins;
        if(wins < 0)
            w = "(" + w + ")";
        return w;
    }
    
    public String getLosses(){
        String l = "" + losses;
        if(losses < 0)
            l = "(" + l + ")";
        return l;
    }
    
    public void win(){
        win(1);
    }
    
    public void win(int n){
        wins+=n;
    }
    
    public void addWin(){
        actualWins++;
    }
    
    public void lose(){
        losses++;
    }
}
