import java.util.*;
public class League
{
    public static Team[] ultraDark;
    public static Team[] moderateDark;
    public static Team[] ultraLight;
    public static Team[] moderateLight;
    public static Game[][] seasonGames;
    public static Game[][] postseason1;
    public static Game[][] postseason2;
    public static Game[][] postseason3;
    static ArrayList<Game> allGames;
    public static ArrayList<Player> birdNest; /**the name of this array list is ILLEGAL KNOWLEDGE*/
    public static ArrayList<Player> deceased;
    public static long seasonStartTime = 1655737200 * (long)1000;// - 3600000 *114;
    //public static long seasonStartTime = 1651503600 * (long)1000;// + 3600000 * (long)17;// - 60000 * (long)12;// - (long)(86400 * 1000 * 5) - (long)(3600 * 1000 * 22);
    public static int season;
    public static int nextID;
    public static int nextTeamID;
    static Random r;
    
    public static ArrayList<Team> replacementTeams;
    public static void resetLeague(){
        //recapAltAlt4();
        nextID = 0;
        nextTeamID = 0;
        r = new Random(-5);
        JSONLoader.loadLeagueFromJSON(5);
        setReplacementTeams();
        
        season = -5;
        scheduleSeason(seasonStartTime);
    }

    public static int nextID(){
        int i = nextID;
        nextID++;
        return i;
    }
    
    public static int nextTeamID(){
        int i = nextTeamID;
        nextTeamID++;
        return i;
    }

    public static ArrayList<Game> getAllGames(){
        return allGames;
    }
    
    public static ArrayList<Game> allScheduledGames(){
        ArrayList scheduledGames = new ArrayList<Game>();
        for(Game[] games : seasonGames)
            for(Game game : games)
                scheduledGames.add(game);
        if(postseason1 == null)
            return scheduledGames;
        for(Game[] games : postseason1)
            for(Game game : games)
            scheduledGames.add(game);
        if(postseason2 == null)
            return scheduledGames;
        for(Game[] games : postseason2)
            for(Game game : games)
            scheduledGames.add(game);
        if(postseason3 == null)
            return scheduledGames;
        for(Game[] games : postseason3)
            for(Game game : games)
            scheduledGames.add(game);
        return scheduledGames;
    }

    public static void runGamesUpToDate(){
        allGames = new ArrayList<Game>();
        int day = getGameDay();
        long time = System.currentTimeMillis();
        for(Game[] games : seasonGames)
            for(Game game : games){
                game.simulateGame(time);
                allGames.add(game);
            }
        if(day >= 100){
            schedulePostseason1();
            for(Game[] games : postseason1)
                for(Game game : games){
                    game.simulateGame(time);
                    allGames.add(game);
                }
            if(day >= 105){
                schedulePostseason2();
                for(Game[] games : postseason2)
                    for(Game game : games){
                        game.simulateGame(time);
                        allGames.add(game);
                    }
            }
            if(day >= 110){
                schedulePostseason3();
                for(Game[] games : postseason3)
                    for(Game game : games){
                        game.simulateGame(time);
                        allGames.add(game);
                    }
            }
        }
    }

    public static void runAllGames(){
        allGames = new ArrayList<Game>();
        int day = getGameDay();
        for(Game[] games : seasonGames)
            for(Game game : games){
                game.simulateGame(Long.MAX_VALUE);
                allGames.add(game);
            }
        schedulePostseason1();
        for(Game[] games : postseason1)
            for(Game game : games){
                game.simulateGame(Long.MAX_VALUE);
                allGames.add(game);
            }
        schedulePostseason2();
        for(Game[] games : postseason2)
            for(Game game : games){
                game.simulateGame(Long.MAX_VALUE);
                allGames.add(game);
            }
        schedulePostseason3();
        for(Game[] games : postseason3)
            for(Game game : games){
                game.simulateGame(Long.MAX_VALUE);
                allGames.add(game);
            }
    }

    public static void sortLeague(){
        selectionSort(ultraDark);
        selectionSort(moderateDark);
        selectionSort(ultraLight);
        selectionSort(moderateLight);
    }

    public static void selectionSort(Team[] list){ //selectionSort should be good enough for this program
        for(int x = 0;x < list.length; x++){
            int bestTeam = x;
            for(int y = x + 1;y < list.length; y++){
                if(list[y].wins > list[bestTeam].wins || (list[y].wins == list[bestTeam].wins && list[y].favor < list[bestTeam].favor)){
                    bestTeam = y;
                }
            }
            Team temp = list[bestTeam];
            list[bestTeam] = list[x];
            list[x] = temp;
        }
    }

    public static void printLeague(){
        sortLeague();
        int l = longestTeamNameLength();
        System.out.println("Alt(Alt) League Blaseball");
        System.out.println("\nDark League");//dark league
        System.out.println("    Ultra Dark");//ultra dark
        for(int x = 0;x < ultraDark.length;x++){
            Team team = ultraDark[x];
            System.out.println("        " + team.logo + " " + addSpaces(team.getTeamName(),l) + " " + team.getWins() + " (" + team.actualWins + "-" + team.getLosses() + ")");
        }
        System.out.println("    Moderate Dark");//moderate dark
        for(int x = 0;x < moderateDark.length;x++){
            Team team = moderateDark[x];
            System.out.println("        " + team.logo + " " + addSpaces(team.getTeamName(),l) + " " + team.getWins() + " (" + team.actualWins + "-" + team.getLosses() + ")");
        }
        System.out.println("\nLight League");//light league
        System.out.println("    Ultra Light");//ultra light
        for(int x = 0;x < ultraLight.length;x++){
            Team team = ultraLight[x];
            System.out.println("        " + team.logo + " " + addSpaces(team.getTeamName(),l) + " " + team.getWins() + " (" + team.actualWins + "-" + team.getLosses() + ")");
        }
        System.out.println("    Moderate Light");//moderate light
        for(int x = 0;x < moderateLight.length;x++){
            Team team = moderateLight[x];
            System.out.println("        " + team.logo + " " + addSpaces(team.getTeamName(),l) + " " + team.getWins() + " (" + team.actualWins + "-" + team.getLosses() + ")");
        }
    }

    public static int longestTeamNameLength(){
        int length = 0;
        for(Team t : getTeams()){
            if(t.getTeamName().length() > length)
                length = t.getTeamName().length();
        }
        return length;
    }

    public static String addSpaces(String s, int length){
        while(s.length() < length){
            s = s + " ";
        }
        return s;
    }

    public static Team getChampion(){
        return getRoundWinner(League.postseason3, 0);
    }

    public static void printPostseason(){
        System.out.println("Season " + season + " Postseason\n");
        if(getGameDay() < 100){
            return;
        }
        System.out.println("Round 1");
        for(int x = 0; x < 4; x++){
            System.out.println("    " + postseason1[0][x].teamA.logo + " " + postseason1[0][x].teamA.getTeamName() + " vs. " + postseason1[0][x].teamB.logo + " " + postseason1[0][x].teamB.getTeamName() + " " + getRecord(postseason1, x));
        }

        if(getGameDay() < 105){
            return;
        }
        System.out.println("\nRound 2");
        for(int x = 0; x < 2; x++){
            System.out.println("    " + postseason2[0][x].teamA.logo + " " + postseason2[0][x].teamA.getTeamName() + " vs. " + postseason2[0][x].teamB.logo + " " + postseason2[0][x].teamB.getTeamName() + " " + getRecord(postseason2, x));
        }

        if(getGameDay() < 110){
            return;
        }
        System.out.println("\nThe Alt Series");
        for(int x = 0; x < 1; x++){
            System.out.println("    " + postseason3[0][x].teamA.logo + " " + postseason3[0][x].teamA.getTeamName() + " vs. " + postseason3[0][x].teamB.logo + " " + postseason3[0][x].teamB.getTeamName() + " " + getRecord(postseason3, x));
        }
    }

    public static String getRecord(Game[][] games, int gameSet){
        int recordA = 0;
        int recordB = 0;
        for(int x = 0; x < games.length; x++){
            Game g = games[x][gameSet];
            recordA += g.winsA;
            recordB += g.winsB;
        }
        String rA = "" + recordA;
        String rB = "" + recordB;
        if(recordA < 0)
            rA = "(" + rA + ")";
        if(recordB < 0)
            rB = "(" + rB + ")";
        return rA + "-" + rB;
    }

    public static Team getRoundWinner(Game[][] games, int gameSet){
        int recordA = 0;
        int recordB = 0;
        //System.out.println(games.length);
        for(int x = 0; x < games.length; x++){
            Game g = games[x][gameSet];
            recordA += g.winsA;
            recordB += g.winsB;
        }
        if(recordA > recordB){
            return games[0][gameSet].teamA;
        }
        if(recordA < recordB){
            return games[0][gameSet].teamB;
        }
        if(games[0][gameSet].teamA.favor < games[0][gameSet].teamB.favor){
            return games[0][gameSet].teamA;
        }
        return games[0][gameSet].teamB;
    }

    public static void scheduleSeason(){
        scheduleSeason(seasonStartTime);
    }

    /*public static void scheduleSeason(long startTime){//timeless league lol
    seasonGames = new Game[1][1];
    seasonGames[0][0] = new Game(ultraDark[0],ultraDark[1],1,seasonStartTime,season); 
    }/**/

    public static void scheduleSeason(long startTime){
        seasonGames = new Game[99][getTeams().length / 2];
        for(int x = 0; x < 99; x+=3){
            ArrayList<Team> darkTeams = new ArrayList<Team>();
            ArrayList<Team> lightTeams = new ArrayList<Team>();
            for(int y = 0; y < ultraDark.length; y++){
                darkTeams.add(ultraDark[y]);
            }
            for(int y = 0; y < moderateDark.length; y++){
                darkTeams.add(moderateDark[y]);
            }
            for(int y = 0; y < ultraLight.length; y++){
                lightTeams.add(ultraLight[y]);
            }
            for(int y = 0; y < moderateLight.length; y++){
                lightTeams.add(moderateLight[y]);
            }
            int y = 0;
            for(;darkTeams.size() > 1; y++){
                int r1 = (int)(r.nextDouble() * darkTeams.size());
                Team t1 = darkTeams.get(r1);
                darkTeams.remove(r1);
                int r2 = (int)(r.nextDouble() * darkTeams.size());
                Team t2 = darkTeams.get(r2);
                darkTeams.remove(r2);
                seasonGames[x][y] = new Game(t1,t2,x+1,startTime + (3600000 * x)); 
                seasonGames[x+1][y] = new Game(t1,t2,x+2,startTime + (3600000 * (x+1))); 
                seasonGames[x+2][y] = new Game(t1,t2,x+3,startTime + (3600000 * (x+2))); 
            }
            for(;lightTeams.size() > 1; y++){
                int r1 = (int)(r.nextDouble() * lightTeams.size());
                Team t1 = lightTeams.get(r1);
                lightTeams.remove(r1);
                int r2 = (int)(r.nextDouble() * lightTeams.size());
                Team t2 = lightTeams.get(r2);
                lightTeams.remove(r2);
                seasonGames[x][y] = new Game(t1,t2,x+1,startTime + (3600000 * x)); 
                seasonGames[x+1][y] = new Game(t1,t2,x+2,startTime + (3600000 * (x+1))); 
                seasonGames[x+2][y] = new Game(t1,t2,x+3,startTime + (3600000 * (x+2))); 
            }
        }
    }

    public static void schedulePostseason1(){
        Team[] darkTeams = new Team[ultraDark.length + moderateDark.length];
        Team[] lightTeams = new Team[ultraLight.length + moderateLight.length];
        for(int x = 0; x < ultraDark.length; x++){
            darkTeams[x] = ultraDark[x];
            darkTeams[x + ultraDark.length] = moderateDark[x];
        }
        for(int x = 0; x < ultraLight.length; x++){
            lightTeams[x] = ultraLight[x];
            lightTeams[x + ultraLight.length] = moderateLight[x];
        }
        selectionSort(darkTeams);
        selectionSort(lightTeams);
        postseason1 = new Game[5][4];
        for(int x = 0; x < 5; x++){
            //adjust +99 to start saturday the next day
            //System.out.println(seasonGames[0][0].season + "\n\n\n\n");
            postseason1[x][0] = new Game(darkTeams[0],darkTeams[3],x+100,seasonStartTime + ((long)3600000 * (102 + x)));
            postseason1[x][1] = new Game(darkTeams[1],darkTeams[2],x+100,seasonStartTime + ((long)3600000 * (102 + x)));
            postseason1[x][2] = new Game(lightTeams[0],lightTeams[3],x+100,seasonStartTime + ((long)3600000 * (102 + x)));
            postseason1[x][3] = new Game(lightTeams[1],lightTeams[2],x+100,seasonStartTime + ((long)3600000 * (102 + x)));
        }
    }

    public static void schedulePostseason2(){
        Team darkA = getRoundWinner(postseason1,0);
        Team darkB = getRoundWinner(postseason1,1);
        Team lightA = getRoundWinner(postseason1,2);
        Team lightB = getRoundWinner(postseason1,3);
        postseason2 = new Game[5][2];
        for(int x = 0; x < 5; x++){
            //adjust +104 to start saturday the next day
            postseason2[x][0] = new Game(darkA,darkB,x+105,seasonStartTime + ((long)3600000 * (121 + x)));
            postseason2[x][1] = new Game(lightA,lightB,x+105,seasonStartTime + ((long)3600000 * (121 + x)));
        }
    }

    public static void schedulePostseason3(){
        Team dark = getRoundWinner(postseason2,0);
        Team light = getRoundWinner(postseason2,1);
        postseason3 = new Game[5][1];
        for(int x = 0; x < 5; x++){
            //adjust +109 to start saturday the next day
            postseason3[x][0] = new Game(dark,light,x+110,seasonStartTime + ((long)3600000 * (126 + x)));
        }
    }

    public static Game[] getCurrentGames(){
        return getCurrentGames(getGameDay());
    }

    public static Game[] getCurrentGames(int day){
        if(day < 1 || day > 114){
            return null;
        }
        if(day > 99 && day < 105){
            return postseason1[day - 100];
        }
        if(day > 104 && day < 110){
            return postseason2[day - 105];
        }
        if(day > 109 && day < 115){
            return postseason3[day - 110];
        }
        return seasonGames[day - 1];
    }

    public static int getGameDay(){
        if(System.currentTimeMillis() < seasonStartTime)
            return 0;
        long time = seasonStartTime;
        int day = 0;
        if(System.currentTimeMillis() < seasonStartTime){
            return 0;
        }
        while(time < System.currentTimeMillis()){
            time+=3600000;
            day++;
        }
        if(day > 99){
            day = 99;
            time = seasonStartTime + (long)(102 * 3600) * (long)1000;
            while(time < System.currentTimeMillis()){
                time+=3600000;
                day++;
            }
        }
        if(day > 104){
            day = 104;
            time = seasonStartTime + (long)(121 * 3600) * (long)1000;
            while(time < System.currentTimeMillis()){
                time+=3600000;
                day++;
            }
        }
        if(day > 114){
            return 115;
        }
        return day;
    }

    public static Team[] getTeams(){
        Team[] teams = new Team[ultraDark.length + moderateDark.length + ultraLight.length + moderateLight.length];
        for(int x = 0; x < ultraDark.length; x++){
            teams[x] = ultraDark[x];
        }
        for(int x = 0; x < moderateDark.length; x++){
            teams[x + ultraDark.length] = moderateDark[x];
        }
        for(int x = 0; x < ultraLight.length; x++){
            teams[x + ultraDark.length + moderateDark.length] = ultraLight[x];
        }
        for(int x = 0; x < moderateLight.length; x++){
            teams[x + ultraDark.length + moderateDark.length + ultraLight.length] = moderateLight[x];
        }
        return teams;
    }

    public static ArrayList<Team> allTeams(){
        ArrayList<Team> teams = new ArrayList<Team>();
        for(Team t : ultraDark){
            teams.add(t);
        }
        for(Team t : moderateDark){
            teams.add(t);
        }
        for(Team t : ultraLight){
            teams.add(t);
        }
        for(Team t : moderateLight){
            teams.add(t);
        }
        return teams;
    }

    public static ArrayList<Player> allPlayers(){
        ArrayList<Player> players = new ArrayList<Player>();
        for(Team t : getTeams()){
            if(t == null)
                continue;
            for(Player p : t.getActivePlayers()){
                players.add(p);
            }
        }
        for(Player p : birdNest){
            players.add(p);
        }
        for(Player p : deceased){
            players.add(p);
        }
        return players;
    }

    public static void recapSeason0(){
        r = new Random(1);//IMPORTANT: Change to 1 before publishing!!
        ultraDark = new Team[6];
        moderateDark = new Team[6];
        ultraLight = new Team[6];
        moderateLight = new Team[6];
        ultraDark[0] = new Team("Bluetooth","Pacific","????",1,"BLT");
        ultraDark[1] = new Team("Rollers","Vegas","????",2,"VGS");
        ultraDark[2] = new Team("Minimalists","Minnesota","????",3,"MIN");
        ultraDark[3] = new Team("Chorale","Cosquin","????",4,"CHR");
        ultraDark[4] = new Team("Greetings","Green Bank","????",5,"GRN");
        ultraDark[5] = new Team("Bluescreens","California","????",6,"CLF");
        moderateDark[0] = new Team("Boas","Myanmar","????",7,"BOA");
        moderateDark[1] = new Team("Candy Floss","England","????",8,"ENG");
        moderateDark[2] = new Team("Pears","Portland","????",9,"PRT");
        moderateDark[3] = new Team("Champions","Calgary","????",10,"CLG");
        moderateDark[4] = new Team("Zoomers","Detroit","????",11,"DTR");
        moderateDark[5] = new Team("Fate","Columbus","???",12,"COL");
        ultraLight[0] = new Team("Lettuces","Romania","????",13,"RMN");
        ultraLight[1] = new Team("Metalheads","Gaborone","????",14,"GBR");
        ultraLight[2] = new Team("Labs","Labrador","????",15,"LAB");
        ultraLight[3] = new Team("Dams","Hoover","????",16,"DAM");
        ultraLight[4] = new Team("Pufferfish","Pacific","????",17,"PUF");
        ultraLight[5] = new Team("Limes","Key West","????",18,"KEY");
        moderateLight[0] = new Team("Toboggans","Trinidad","???",19,"TRI");
        moderateLight[1] = new Team("Rice-cakes","Reno","????",20,"RNO");
        moderateLight[2] = new Team("Cool Guys","Colorado","????",21,"COL");
        moderateLight[3] = new Team("Squares","Egyptian","????",22,"EGP");
        moderateLight[4] = new Team("Bluegrass","Kentucky","????",23,"KNT");
        moderateLight[5] = new Team("Crepes","French","????",24,"FRC");
    }

    public static void recapSeason3(){
        recapSeason2();
        birdNest = new ArrayList<Player>();
        r = new Random(123456); //it should've been 3 but i forgor; oh well.
        Blessings.birdCall(moderateLight[0]);
        Blessings.approachTheSecondSun(moderateDark[1]);
        Blessings.eventHorizon(moderateLight[4],moderateLight[1]);
        Blessings.cumulativeBoost(moderateLight[1]);
        Blessings.boostCumulative(ultraLight[1]);
        Blessings.reverse(moderateDark[5]);
        Blessings.instructionManual(ultraDark[4]);
        Blessings.defragmentation(moderateLight[5]);
        Blessings.exploratorySurgeries(ultraDark[0]);
        Blessings.chordataAves(moderateDark[3]);
        Blessings.mollusca(ultraLight[5]);
        Blessings.openTheForbiddenBook(ultraDark[0]);
        Blessings.outfamy();/**/
    }

    public static void recapSeason1(){
        r = new Random(1);//IMPORTANT: Change to 1 before publishing!!
        ultraDark = new Team[6];
        moderateDark = new Team[6];
        ultraLight = new Team[6];
        moderateLight = new Team[6];
        ultraDark[0] = new Team("Bluetooth","Pacific","????",1,"BLT");
        ultraDark[1] = new Team("Rollers","Vegas","????",2,"VGS");
        ultraDark[2] = new Team("Minimalists","Minnesota","????",3,"MIN");
        ultraDark[3] = new Team("Chorale","Cosquin","????",4,"CHR");
        ultraDark[4] = new Team("Greetings","Green Bank","????",5,"GRN");
        ultraDark[5] = new Team("Bluescreens","California","????",6,"CLF");
        moderateDark[0] = new Team("Boas","Myanmar","????",7,"BOA");
        moderateDark[1] = new Team("Candy Floss","England","????",8,"ENG");
        moderateDark[2] = new Team("Pears","Portland","????",9,"PRT");
        moderateDark[3] = new Team("Champions","Calgary","????",10,"CLG");
        moderateDark[4] = new Team("Zoomers","Detroit","????",11,"DTR");
        moderateDark[5] = new Team("Fate","Columbus","???",12,"COL");
        ultraLight[0] = new Team("Lettuces","Romania","????",13,"RMN");
        ultraLight[1] = new Team("Metalheads","Gaborone","????",14,"GBR");
        ultraLight[2] = new Team("Labs","Labrador","????",15,"LAB");
        ultraLight[3] = new Team("Dams","Hoover","????",16,"DAM");
        ultraLight[4] = new Team("Pufferfish","Pacific","????",17,"PUF");
        ultraLight[5] = new Team("Limes","Key West","????",18,"KEY");
        moderateLight[0] = new Team("Toboggans","Trinidad","???",19,"TRI");
        moderateLight[1] = new Team("Rice-cakes","Reno","????",20,"RNO");
        moderateLight[2] = new Team("Cool Guys","Colorado","????",21,"COL");
        moderateLight[3] = new Team("Squares","Egyptian","????",22,"EGP");
        moderateLight[4] = new Team("Bluegrass","Kentucky","????",23,"KNT");
        moderateLight[5] = new Team("Crepes","French","????",24,"FRC");
        PlayerMaker.season1ToSeason2(ultraDark);
        PlayerMaker.season1ToSeason2(moderateDark);
        PlayerMaker.season1ToSeason2(ultraLight);
        PlayerMaker.season1ToSeason2(moderateLight);
        Blessings.maxBatting(ultraDark[5]);
        Blessings.maxPitching(moderateDark[2]);
        Blessings.maxBaserunning(moderateDark[4]);
        Blessings.maxDefense(moderateDark[3]);
        Blessings.lineupFocus(ultraDark[3]);
        Blessings.rotationFocus(moderateLight[4]);
        Blessings.smallBoost(moderateLight[5]);
        Blessings.largeBoost(moderateLight[5]);
        Blessings.freshStart(ultraLight[0]);
        Blessings.sabotage(moderateDark[3],moderateLight[2]);
        Blessings.largeBoost(ultraLight[4]); //potluck

    }

    public static void recapSeason2(){
        recapSeason1();
        r = new Random(2);
        moderateDark[5].abbreviation = "CF8";
        moderateLight[2].abbreviation = "CCG";
        Blessings.battingGoo(ultraDark[1]);
        Blessings.pitchingGoo(moderateDark[1]);
        Blessings.tradeOff(moderateDark[3]);
        Blessings.maxPitching(moderateDark[3]);
        Blessings.waitImBatting(ultraDark[4]);
        Blessings.waitImPitching(ultraDark[5]);
        Blessings.defragmentation(ultraDark[5]);
        Blessings.largeBoost(moderateDark[5]);
        Blessings.infamy(ultraLight[4],getTeams());
        Blessings.shoes(ultraLight[2]);
        Blessings.defenseReal(moderateLight[5]);
        Blessings.waitImBatting(ultraDark[1]); //potluck
    }

    public static void recapVortex(){
        r = new Random(Integer.MIN_VALUE);
        ultraDark = new Team[2];
        moderateDark = new Team[0];
        ultraLight = new Team[0];
        moderateLight = new Team[0];
        ultraDark[0]= new Team("Siesta","Endless","????",1,"EDL");
        ultraDark[1]= new Team("Insomniacs","Eternal","???",2,"ETR");

        birdNest = new ArrayList<Player>();
        deceased = new ArrayList<Player>();
        for(Player p : allPlayers()){
            PlayerMaker.addFlavor(p);
        }
    }

    public static void recapAltAlt(){
        r = new Random(-1);//IMPORTANT: Change to -1 before publishing!!
        ultraDark = new Team[11];
        moderateDark = new Team[10];
        ultraLight = new Team[10];
        moderateLight = new Team[10];
        ultraDark[0] = new Team("Fish","Florda","????",1,"FLF");
        ultraDark[1] = new Team("Big Lizards","Norcal","????",2,"NOR");
        ultraDark[2] = new Team("Crosses","Nottingham","???",3,"NOT");
        ultraDark[3] = new Team("Scouts","SpringField","???",4,"SCT");
        ultraDark[4] = new Team("Council","High Avian","????",5,"HAC");
        ultraDark[5] = new Team("Ice Caps","Polar","????",6,"PIC");
        ultraDark[6] = new Team("Dreamers","Soul City","????",7,"SCD");
        ultraDark[7] = new Team("Skeletons","Sendai","????",8,"SEN");
        ultraDark[8] = new Team("Jokers","Sacramento","????",9,"SCR");
        moderateDark[0] = new Team("Fitted Sheets","Las Vegas","????",10,"VGS");
        moderateDark[1] = new Team("Shadows","Jakarta","???",11,"JKR");
        moderateDark[2] = new Team("Cups","Melbourne","???",12,"CUP");
        moderateDark[3] = new Team("Null Team","","???",13,"NUL");
        moderateDark[4] = new Team("Specters","Dublin","????",14,"DBN");
        moderateDark[5] = new Team("Consortiums","Unicode","???",15,"UNI");
        moderateDark[6] = new Team("Bulls","Pamplona","????",16,"PMP");
        moderateDark[7] = new Team("Pharaohs","T??rshavn","????",17,"TRS");
        moderateDark[8] = new Team("Connectors","Cardiff","4??????",18,"CRD");
        ultraLight[0] = new Team("Pierogies","Pittsburgh","????",19,"PTS");
        ultraLight[1] = new Team("Funks","Uptown","????",20,"UPT");
        ultraLight[2] = new Team("Eyes","London","????",21,"EYE");
        ultraLight[3] = new Team("Tulips","Springfield","????",22,"TLP");
        ultraLight[4] = new Team("Alternates","Ankara","????",23,"ALT");
        ultraLight[5] = new Team("Surfers","Detroit","????",24,"DTR");
        ultraLight[6] = new Team("Soli","Prague","????",25,"PRG");//pair with yugoslavia
        ultraLight[7] = new Team("Rovers","Mars","????",26,"MRS");
        moderateLight[0] = new Team("Crabs","Baltimore","????",27,"BAL");
        moderateLight[1] = new Team("Pawns","St Louis","???",28,"SLP");
        moderateLight[2] = new Team("Glolfers","Hilton Head Island","???",29,"HHI");
        ultraLight[8] = new Team("Wasted Potential","Yugoslavia","????",30,"YWP");//pair with prague
        moderateLight[3] = new Team("Perennials","Toronto","????",31,"TOR");
        moderateLight[4] = new Team("Moonmen","New Mexico","????",32,"NMM");
        moderateLight[5] = new Team("Clones","Colorado","????",33,"COL");
        moderateLight[6] = new Team("Charmers","Carolina","????",34,"CRL");
        moderateLight[7] = new Team("Pastries","Overpass","????",35,"OVR");
        moderateLight[9] = new Team("Extras","Fill-in","???",100,"FIL");
        ultraDark[9] = new Team("Sledgehammers","Pasadena","????",36,"PSD");
        moderateDark[9] = new Team("Muffins","Milan","????",37,"MLN");

        Team fish = ultraDark[0];
        Team bigLizards = ultraDark[1];
        Team crosses = ultraDark[2];
        Team scouts = ultraDark[3];
        Team council = ultraDark[4];
        Team iceCaps = ultraDark[5];
        Team dreamers = ultraDark[6];
        Team skeletons = ultraDark[7];
        Team jokers = ultraDark[8];
        Team fittedSheets = moderateDark[0];
        Team shadows = moderateDark[1];
        Team cups = moderateDark[2];
        Team nullTeam = moderateDark[3];
        Team specters = moderateDark[4];
        Team consortiums = moderateDark[5];
        Team bulls = moderateDark[6];
        Team pharaohs = moderateDark[7];
        Team connectors = moderateDark[8];
        Team pierogies = ultraLight[0];
        Team funks = ultraLight[1];
        Team eyes = ultraLight[2];
        Team tulips = ultraLight[3];
        Team alternates = ultraLight[4];
        Team surfers = ultraLight[5];
        Team soli = ultraLight[6];
        Team rovers = ultraLight[7];
        Team crabs = moderateLight[0];
        Team pawns = moderateLight[1];
        Team glolfers = moderateLight[2];
        Team wastedPotential = ultraLight[8];
        Team perennials = moderateLight[3];
        Team moonmen = moderateLight[4];
        Team clones = moderateLight[5];
        Team charmers = moderateLight[6];
        Team pastries = moderateLight[7];
        Team sledgehammers = ultraDark[9];

        birdNest = new ArrayList<Player>();
        deceased = new ArrayList<Player>();
        for(Player p : allPlayers()){
            PlayerMaker.addFlavor(p);
        }

        Team muffins = moderateDark[9];
        Team extras = moderateLight[9];
        for(Player p : allPlayers()){
            PlayerMaker.addFlavor(p);
        }

        ultraLight[9] = new Team("Kings","Maximus","????",38,"MAX");
        moderateLight[8] = new Team("Suits","New York New York","????",39,"????S");
        moderateLight[9] = new Team("Urns","Uruguay","???",40,"URN");
        ultraDark[10] = new Team("Stargazers","Loyola","????",41,"LOY");
        Team kings = ultraLight[9];
        Team suits = moderateLight[8];
        Team urns = moderateLight[9];
        Team stargazers = ultraDark[10];
        for(Player p : kings.getActivePlayers()){
            PlayerMaker.addFlavor(p);
        }
        for(Player p : suits.getActivePlayers()){
            PlayerMaker.addFlavor(p);
        }
        for(Player p : urns.getActivePlayers()){
            PlayerMaker.addFlavor(p);
        }
        for(Player p : stargazers.getActivePlayers()){
            PlayerMaker.addFlavor(p);
        }

        Blessings.freshStart(cups);
        Blessings.freshStart(dreamers);
        Blessings.freshStart(pharaohs);
        Blessings.freshStart(wastedPotential);
        Blessings.lucky7s(sledgehammers);
        Blessings.lucky7s(alternates);
        Blessings.lucky7s(bulls);
        Blessings.lucky7s(connectors);
        Blessings.lucky7s(specters);
        Blessings.lucky7s(shadows);
        Blessings.lucky7s(bigLizards);
        Blessings.lucky7s(crosses);
        Blessings.lucky7s(pastries);
        Blessings.lucky7s(iceCaps);
        Blessings.lucky7s(scouts);
        Blessings.lucky7s(skeletons);
        Blessings.lucky7s(perennials);
        Blessings.reverse(council);
        Blessings.reverse(tulips);
        Blessings.reverse(pawns);
        /*Blessings.option5(clones);
        Blessings.option5(surfers);
        Blessings.option5(extras);
        Blessings.option5(eyes);
        Blessings.option5(nullTeam);
        Blessings.option5(pierogies);
        Blessings.option5(jokers);*/
        ArrayList<Team> teams = allTeams();
        ultraDark = new Team[6];
        moderateDark = new Team[6];
        ultraLight = new Team[6];
        moderateLight = new Team[6];
        for(int x = 0; x < 6; x++){
            int random = (int)(r.nextDouble() * teams.size());
            ultraDark[x] = teams.get(random);
            teams.remove(random);
        }
        for(int x = 0; x < 6; x++){
            int random = (int)(r.nextDouble() * teams.size());
            moderateDark[x] = teams.get(random);
            teams.remove(random);
        }
        for(int x = 0; x < 6; x++){
            int random = (int)(r.nextDouble() * teams.size());
            ultraLight[x] = teams.get(random);
            teams.remove(random);
        }
        for(int x = 0; x < 6; x++){
            int random = (int)(r.nextDouble() * teams.size());
            moderateLight[x] = teams.get(random);
            teams.remove(random);
        }

    }

    public static void recapAltAlt4(){
        r = new Random(-4);
        ultraDark = new Team[6];
        moderateDark = new Team[6];
        ultraLight = new Team[6];
        moderateLight = new Team[6];
        ultraDark[0] = new Team("Bluetooth","Hellmouth","????",1,"BLT");
        ultraDark[1] = new Team("Rollers","Vegas","????",2,"VGS");
        ultraDark[2] = new Team("Minimalists","Minnesota","????",3,"MIN");
        ultraDark[3] = new Team("Chorale","Cosquin","????",4,"CHR");
        ultraDark[4] = new Team("Greetings","Green Bank","????",5,"GRN");
        ultraDark[5] = new Team("Bluescreens","California","????",6,"CLF");
        moderateDark[0] = new Team("Boas","Myanmar","????",7,"BOA");
        moderateDark[1] = new Team("Candy Floss","England","????",8,"ENG");
        moderateDark[2] = new Team("Pears","Portland","????",9,"PRT");
        moderateDark[3] = new Team("Champions","Calgary","????",10,"CLG");
        moderateDark[4] = new Team("Zoomers","Detroit","????",11,"DTR");
        moderateDark[5] = new Team("Fate","Columbus","???",12,"COL");
        ultraLight[0] = new Team("Lettuces","Romania","????",13,"RMN");
        ultraLight[1] = new Team("Metalheads","Gaborone","????",14,"GBR");
        ultraLight[2] = new Team("Labs","Labrador","????",15,"LAB");
        ultraLight[3] = new Team("Dams","Hoover","????",16,"DAM");
        ultraLight[4] = new Team("Pufferfish","Pacific","????",17,"PUF");
        ultraLight[5] = new Team("Limes","Key West","????",18,"KEY");
        moderateLight[0] = new Team("Toboggans","Trinidad","???",19,"TRI");
        moderateLight[1] = new Team("Rice-cakes","Reno","????",20,"RNO");
        moderateLight[2] = new Team("Cool Guys","Colorado","????",21,"COL");
        moderateLight[3] = new Team("Squares","Egyptian","????",22,"EGP");
        moderateLight[4] = new Team("Bluegrass","Kentucky","????",23,"KNT");
        moderateLight[5] = new Team("Crepes","French","????",24,"FRC");

        birdNest = new ArrayList<Player>();
        deceased = new ArrayList<Player>();

    }
    
    public static void setReplacementTeams(){
        replacementTeams = new ArrayList<Team>();
        replacementTeams.add(new Team("Fish","Florda","????",1,"FLF"));
        replacementTeams.add(new Team("Big Lizards","Norcal","????",2,"NOR"));
        replacementTeams.add(new Team("Crosses","Nottingham","???",3,"NOT"));
        replacementTeams.add(new Team("Scouts","SpringField","???",4,"SCT"));
        replacementTeams.add(new Team("Council","High Avian","????",5,"HAC"));
        replacementTeams.add(new Team("Ice Caps","Polar","????",6,"PIC"));
        replacementTeams.add(new Team("Dreamers","Soul City","????",7,"SCD"));
        replacementTeams.add(new Team("Skeletons","Sendai","????",8,"SEN"));
        replacementTeams.add(new Team("Jokers","Sacramento","????",9,"SCR"));
        replacementTeams.add(new Team("Fitted Sheets","Las Vegas","????",10,"VGS"));
        replacementTeams.add(new Team("Shadows","Jakarta","???",11,"JKR"));
        replacementTeams.add(new Team("Cups","Melbourne","???",12,"CUP"));
        replacementTeams.add(new Team("Null Team","","???",13,"NUL"));
        replacementTeams.add(new Team("Specters","Dublin","????",14,"DBN"));
        replacementTeams.add(new Team("Consortiums","Unicode","???",15,"UNI"));
        replacementTeams.add(new Team("Bulls","Pamplona","????",16,"PMP"));
        replacementTeams.add(new Team("Pharaohs","T??rshavn","????",17,"TRS"));
        replacementTeams.add(new Team("Connectors","Cardiff","4??????",18,"CRD"));
        replacementTeams.add(new Team("Pierogies","Pittsburgh","????",19,"PTS"));
        replacementTeams.add(new Team("Funks","Uptown","????",20,"UPT"));
        replacementTeams.add(new Team("Eyes","London","????",21,"EYE"));
        replacementTeams.add(new Team("Tulips","Springfield","????",22,"TLP"));
        replacementTeams.add(new Team("Alternates","Ankara","????",23,"ALT"));
        replacementTeams.add(new Team("Surfers","Detroit","????",24,"DTR"));
        replacementTeams.add(new Team("Soli","Prague","????",25,"PRG"));
        replacementTeams.add(new Team("Rovers","Mars","????",26,"MRS"));
        replacementTeams.add(new Team("Crabs","Baltimore","????",27,"BAL"));
        replacementTeams.add(new Team("Pawns","St Louis","???",28,"SLP"));
        replacementTeams.add(new Team("Glolfers","Hilton Head Island","???",29,"HHI"));
        replacementTeams.add(new Team("Wasted Potential","Yugoslavia","????",30,"YWP"));
        replacementTeams.add(new Team("Perennials","Toronto","????",31,"TOR"));
        replacementTeams.add(new Team("Moonmen","New Mexico","????",32,"NMM"));
        replacementTeams.add(new Team("Clones","Colorado","????",33,"COL"));
        replacementTeams.add(new Team("Charmers","Carolina","????",34,"CRL"));
        replacementTeams.add(new Team("Pastries","Overpass","????",35,"OVR"));
        replacementTeams.add(new Team("Sledgehammers","Pasadena","????",36,"PSD"));
        replacementTeams.add(new Team("Muffins","Milan","????",37,"MLN"));
        replacementTeams.add(new Team("Kings","Maximus","????",38,"MAX"));
        replacementTeams.add(new Team("Suits","New York New York","????",39,"????S"));
        replacementTeams.add(new Team("Urns","Uruguay","???",40,"URN"));
        replacementTeams.add(new Team("Stargazers","Loyola","????",41,"LOY"));
        replacementTeams.add(new Team("Extras","Fill-in","???",42,"FIL"));
        replacementTeams.add(new Team("Firefighters","Chicago","????",43,"CHI"));
        replacementTeams.add(new Team("Lift","Tokyo","??????????",44,"TKL"));
        replacementTeams.add(new Team("Tigers","Hades","????",45,"HAD"));
        replacementTeams.add(new Team("Jazz Hands","Breckenridge","????",46,"BRK"));
        replacementTeams.add(new Team("Wild Wings","Mexico City","????",47,"MXC"));
        replacementTeams.add(new Team("Georgias","Atlantis","????",48,"ATL"));
        replacementTeams.add(new Team("Flowers","Boston","????",49,"BOS"));
        replacementTeams.add(new Team("Tacos","LA Unlimited","????",50,"LAU"));
        replacementTeams.add(new Team("Sunbeams","Hellmouth","????",51,"HEL"));
        replacementTeams.add(new Team("Spies","Houston","????",52,"HST"));
        replacementTeams.add(new Team("Dale","Miami","????",53,"MIA"));
        replacementTeams.add(new Team("Worms","Ohio","????",54,"OHO"));
        replacementTeams.add(new Team("Pies","Philly","????",55,"PHL"));
        replacementTeams.add(new Team("Steaks","Dallas","????",56,"DLS"));
        replacementTeams.add(new Team("Lovers","San Francisco","????",57,"SFR"));
        replacementTeams.add(new Team("Millennials","New York","????",58,"NYM"));
        replacementTeams.add(new Team("Garages","Seattle","????",59,"SEA"));
        replacementTeams.add(new Team("Mechanics","Core","????",60,"COR"));
        replacementTeams.add(new Team("Magic","Yellowstone","???",61,"YLW"));
        replacementTeams.add(new Team("Shoe Thieves","Charleston","????",62,"CHR"));
        replacementTeams.add(new Team("Fridays","Hawai'i","????",63,"HWI"));
        replacementTeams.add(new Team("Breath Mints","Kansas City","????",64,"TBM"));
        replacementTeams.add(new Team("Moist Talkers","Canada","????",65,"CMT"));
        replacementTeams.add(new Team("Crabs","Baltimore","????",66,"BAL"));
        replacementTeams.add(new Team("Latte","Atl??tico","????",67,"ATL"));
        replacementTeams.add(new Team("Crew","Cold Brew","???",68,"CLD"));
        replacementTeams.add(new Team("United","Cream & Sugar","????",69,"C&S"));
        replacementTeams.add(new Team("Data Witches","Society","????",70,"SIBR"));
        replacementTeams.add(new Team("PoS","Royal","????",71,"RYL"));
        replacementTeams.add(new Team("Society","Milk Proxy","????",72,"MPS"));
        replacementTeams.add(new Team("FWXBC","","????",73,"FWX"));
        replacementTeams.add(new Team("Artists","Pandemonium","?????",74,"PAN"));
        replacementTeams.add(new Team("Game Band","Real","????",75,"RGB"));
        replacementTeams.add(new Team("City","Macchiato","????",76,"MAC"));
        replacementTeams.add(new Team("Calf","Club de","?????",77,"CDC"));
        replacementTeams.add(new Team("Electric Co.","Light & Sweet","????",78,"L&S"));
        replacementTeams.add(new Team("Water Works","Americano","????",79,"AWW"));
        replacementTeams.add(new Team("Xpresso","Inter","???",80,"INR"));
        replacementTeams.add(new Team("FC","Heavy","???",81,"HFC"));
        replacementTeams.add(new Team("Noir","BC","????",82,"BCN"));
        replacementTeams.add(new Team("Stars","The Hall","????",83,"THS"));
        replacementTeams.add(new Team("PODS","THE SHELLED ONE'S","????",84,"POD"));
        replacementTeams.add(new Team("Legends","Vault","????",85,"VLT"));
        replacementTeams.add(new Team("Stars","Rising","????",86,"RSN"));
        replacementTeams.add(new Team("Paws","Oxford","????",87,"OXF"));
        replacementTeams.add(new Team("Immortals","Alaskan","????",88,"ALK"));
        replacementTeams.add(new Team("Fireballs","Antarctic","???",89,"ANT"));
        replacementTeams.add(new Team("Crabs","Baltimore","????",90,"BAL"));
        replacementTeams.add(new Team("Bicycles","Beijing","????",91,"BEI"));
        replacementTeams.add(new Team("Bay Birds","Boulders","????",92,"BBB"));
        replacementTeams.add(new Team("Bison","Busan","????",93,"BSN"));
        replacementTeams.add(new Team("Artists","Canada","????",94,"ART"));
        replacementTeams.add(new Team("Drop Bears","Canberra","????",95,"CDB"));
        replacementTeams.add(new Team("Queens","Carolina","????",96,"CRL"));
        replacementTeams.add(new Team("Cows","Dallas","????",97,"COW"));
        replacementTeams.add(new Team("Dogs","Downward","????",98,"DWN"));
        replacementTeams.add(new Team("Rhinoceroses","Florence","????",99,"RNO"));
        replacementTeams.add(new Team("Hedgehogs","Green Hill","????",100,"GHH"));
        replacementTeams.add(new Team("Boar","Kola","????",101,"KOL"));
        replacementTeams.add(new Team("Llamas","La Paz","????",102,"LPL"));
        replacementTeams.add(new Team("Excavators","Laredo","????",103,"LRD"));
        replacementTeams.add(new Team("Lynx","Libson","???",104,"LIB"));
        replacementTeams.add(new Team("Frogs","London","????",105,"LND"));
        replacementTeams.add(new Team("Lobsters","Louisville","????",106,"LSV"));
        replacementTeams.add(new Team("Whales","Mallorca","????",107,"MLR"));
        replacementTeams.add(new Team("Squirrels","Maryland","????",108,"MLD"));
        replacementTeams.add(new Team("Truckers","Minneapolis","????",109,"MNN"));
        replacementTeams.add(new Team("Eggplants","New Hampshire","????",110,"NHE"));
        replacementTeams.add(new Team("Heartthrobs","Oklahoma","????",111,"OKL"));
        replacementTeams.add(new Team("Psychics","Oregon","????",112,"ORG"));
        replacementTeams.add(new Team("Trunks","Phoenix","????",113,"PHX"));
        replacementTeams.add(new Team("Otters","Portland","????",114,"OTT"));
        replacementTeams.add(new Team("Saltines","San Diego","????",155,"SDS"));
        replacementTeams.add(new Team("Parrots","S??o Paulo","????",116,"SPP"));
        replacementTeams.add(new Team("Dolphins","Wyoming","????",117,"WYO"));
    }
}
