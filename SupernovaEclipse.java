public class SupernovaEclipse extends Weather
{
    public SupernovaEclipse(Game game){
        super(game);
        name = "Supernova Eclipse";
    }

    public void beforePitch(){
        if(game.r.nextDouble() > 0.0003)
            return;
        int rand = (int)(r.nextDouble() * 2);
        Team replacement = getReplacementTeam();
        Team t;
        switch((int)(r.nextDouble() * 2)){
            case 0:
                t = game.teamA;
                game.teamA = replacement;
                break;
            case 1: default:
                t = game.teamB;
                game.teamB = replacement;
                break;
        }
        if(game.pitchingTeam.equals(t))
            game.pitchingTeam = replacement;
        else
            game.battingTeam = replacement;
        replacement.actualWins = t.actualWins;
        replacement.wins = t.wins;
        replacement.losses = t.losses;
        replacement.favor = t.favor;

        for(int x = 0; x < League.ultraDark.length; x++)
            if(League.ultraDark[x].equals(t))
                League.ultraDark[x] = replacement;
        for(int x = 0; x < League.moderateDark.length; x++)
            if(League.moderateDark[x].equals(t))
                League.moderateDark[x] = replacement;
        for(int x = 0; x < League.ultraLight.length; x++)
            if(League.ultraLight[x].equals(t))
                League.ultraLight[x] = replacement;
        for(int x = 0; x < League.moderateLight.length; x++)
            if(League.moderateLight[x].equals(t))
                League.moderateLight[x] = replacement;
        for(Game g : League.allScheduledGames()){
            if(g.teamA.equals(t))
                g.teamA = replacement;
            if(g.teamB.equals(t))
                g.teamB = replacement;

        }
        for(Player p : replacement.getActivePlayers())
            p.addStatistic("Added as replacement");
        for(Player p : t.getActivePlayers()){
            League.deceased.add(p);
            p.addStatistic("Times incinerated");
        }
        game.addEvent("Rogue umpire incinerates the " + t.getTeamName() + "! They're replaced by the " + replacement.getTeamName() + ".", true);
    }

    public Team getReplacementTeam(){
        int i = League.replacementTeams.size();
        if(i == 0)
            return new Team("Crabs","Baltimore","🦀",0,"BAL");
        int rand = (int)(r.nextDouble() * i);
        Team t = League.replacementTeams.get(rand);
        League.replacementTeams.remove(rand);
        return t;
    }
}
