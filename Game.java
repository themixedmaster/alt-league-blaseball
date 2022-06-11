import java.util.*;
import java.util.HashMap;

public class Game //name will be changed to Game when finished and replace current Game class
{
    static int tick = 100;
    Random r;

    Team teamA;
    Team teamB;
    Player pitcherA;
    Player pitcherB;
    int dayNum;
    long startTime;
    long currentTime;
    ArrayList<Event> events;
    Weather weather;

    int inning;
    boolean top;

    Team battingTeam;
    Team pitchingTeam;

    Player pitcher;
    Player waitingPitcher;
    Player batter;
    Player defender;

    double scoreA;
    double scoreB;
    int winsA = 0;
    int winsB = 0;
    int balls = 0;
    int strikes = 0;
    int outs = 0;
    boolean out = false;
    int activeTeamBat = 0;
    int inactiveTeamBat = 0;

    boolean teamAScored = false;
    boolean teamBScored = false;

    Player[] bases;

    public Game(Team teamA, Team teamB, int dayNum, long startTime){
        this.teamA = teamA;
        this.teamB = teamB;
        this.dayNum = dayNum;
        this.startTime = startTime;
        currentTime = startTime;
        events = new ArrayList<Event>();
    }
    //code is currently in testing phase
    public void simulateGame(){
        r = new Random(dayNum *  + (long)Math.pow(teamA.favor,teamB.favor) + (long)Math.pow(teamB.favor,teamA.favor));
        setRandomWeather();
        addEvent(teamA.getTeamName() + " vs. " + teamB.getTeamName(),0);

        inning = 1;
        scoreA = 0;
        scoreB = 0;
        top = true;

        pitcherA = teamA.rotation[(dayNum - 1 + teamA.rotation.length) % teamA.rotation.length];
        pitcherB = teamB.rotation[(dayNum - 1 + teamB.rotation.length) % teamB.rotation.length];

        pitchingTeam = teamA;
        battingTeam = teamB;
        pitcher = pitcherA;
        waitingPitcher = pitcherB;

        for(Player p : teamA.lineup){
            p.addStatistic("Games played");
        }
        for(Player p : teamB.lineup){
            p.addStatistic("Games played");
        }
        pitcherA.addStatistic("Games played");
        pitcherB.addStatistic("Games played");

        addEvent("Blay pall!",tick);
        beforeFirstInning();
        while(!endOfGame()){
            String s;
            if(top)
                s = "Top";
            else
                s = "Bottom";
            addEvent(s + " of " + inning + ", " + battingTeam.getTeamName() + " batting. " + pitcher.name + " pitching.",tick);
            pitcher.addStatistic("Innings pitched");
            while(!endOfInning()){
                clearBases();
                setNextBatter();
                while(!strikeout()){
                    doSteals();
                    doPitch();
                }
                if(!out)
                    pitcher.addStatistic("Strikeouts");
                strikes = 0;
                balls = 0;
                out = false;
                outs++;
                addEvent("[Out " + outs + "]",0);
            }
            outs = 0;
            if(top)
                top = false;
            else{
                top = true;
                addEvent("Inning " + inning + " is now an Outing.",tick);
                printScore();
                inning++;
            }
            //switches out batting team
            Team temp = pitchingTeam;
            pitchingTeam = battingTeam;
            battingTeam = temp;
            //switches out pitcher
            Player temp2 = pitcher;
            pitcher = waitingPitcher;
            waitingPitcher = temp2;
            //switches out teams' batting positions
            int temp3 = activeTeamBat;
            inactiveTeamBat = activeTeamBat;
            inactiveTeamBat = temp3;
        }
        addEvent(teamA.getName() + " " + scoreToString(scoreA) + ", " + teamB.getName() + " " + scoreToString(scoreB),tick);
        addEvent("\nGame over.",tick);
        giveWins();
        if(!teamAScored)
            pitcherB.addStatistic("Shutouts");
        if(!teamBScored)
            pitcherA.addStatistic("Shutouts");
    }

    void beforeFirstInning(){
        //nothing in here for now, maybe there will be later, idk
    }

    //precondition: simulateGame() has been called already
    public String gameName(){
        return weather.name() + ", " + teamA.getTeamName() + " vs. " + teamB.getTeamName() + ", Day " + dayNum;
    }

    //precondition: simulateGame() has been called already
    boolean isLive(){
        //System.out.println(dayNum + " " + startTime + " " + System.currentTimeMillis() + " " + currentTime);
        return currentTime >= System.currentTimeMillis() && startTime <= System.currentTimeMillis();
    }

    boolean gameEffectsRecord(){
        return dayNum < 100;
    }

    void giveWins(){
        Team winningTeam;
        Team losingTeam;
        if(scoreA > scoreB){
            winningTeam = teamA;
            losingTeam = teamB;
            winsA++;
            if(gameEffectsRecord()){
                teamA.addWin();
                teamB.lose();
                pitcherA.addStatistic("Pitcher wins");
            }
        }else{
            winningTeam = teamB;
            losingTeam = teamA;
            winsB++;
            if(gameEffectsRecord()){
                teamB.addWin();
                teamA.lose();
                pitcherB.addStatistic("Pitcher wins");
            }
        }
        if(gameEffectsRecord()){
            teamA.win(winsA);
            teamB.win(winsB);
        }
    }

    void stealAttempt(int baseNum){
        Player defender = randomDefender();
        double urge = r.nextDouble() * 10 + bases[baseNum].arrogance - defender.rejection;
        if(urge < 9.9)
            return;
        bases[baseNum].addStatistic("Base steal attempts");
        double stealValue = r.nextDouble() * 10 + bases[baseNum].dexterity;
        double defenseValue = r.nextDouble() * 10 + defender.wisdom;
        if(stealValue > defenseValue){
            bases[baseNum].addStatistic("Bases stolen");
            switch(baseNum){
                case 0:
                    addEvent(bases[baseNum].name + " steals second base!",tick);
                    bases[baseNum].addStatistic("Second base stolen");
                    break;
                case 1:
                    addEvent(bases[baseNum].name + " steals third base!",tick);
                    bases[baseNum].addStatistic("Third base stolen");
                    break;
                default:
                    addEvent(bases[baseNum].name + " steals home!",tick);
                    bases[baseNum].addStatistic("Home stolen");
                    break;
            }
            score(bases[baseNum]);
            if(baseNum < bases.length-1)
                bases[baseNum+1] = bases[baseNum];
            bases[baseNum] = null;
        }else{
            bases[baseNum].addStatistic("Caught stealing");
            switch(baseNum){
                case 0:
                    addEvent(bases[baseNum].name + " gets caught stealing second base.",tick);
                    bases[baseNum].addStatistic("Caught stealing second base");
                    break;
                case 1:
                    addEvent(bases[baseNum].name + " gets caught stealing third base.",tick);
                    bases[baseNum].addStatistic("Caught stealing third base");
                    break;
                default:
                    addEvent(bases[baseNum].name + " gets caught stealing home.",tick);
                    bases[baseNum].addStatistic("Caught stealing home");
                    break;
            }
            out = true;
            bases[baseNum] = null;
        }
        doSteals();
    }

    void doSteals(){
        for(int x = 0; x < bases.length; x++){
            if(bases[x] == null)
                continue;
            if((x == bases.length - 1 || bases[x+1] == null) && !strikeout()){
                stealAttempt(x);
            }
        }
    }

    boolean wantsToSteal(Player p){
        Player defender = randomDefender();
        double urge = r.nextDouble() * 10 + p.arrogance - defender.rejection;
        return urge >= 9.5;
    }

    void doPitch(){
        double pitchValue = r.nextDouble() * 10 + pitcher.pinpointedness;
        double batValue = r.nextDouble() * 10 + batter.density;
        pitcher.addStatistic("Pitches thrown");
        batter.addStatistic("Times pitched to");
        if(batValue <= pitchValue){
            pitcher.addStatistic("Balls thrown");
            pitcher.addStatistic("Balls received");
            ball();
        }else{
            pitchValue = r.nextDouble() * 10 + pitcher.fun;
            batValue = r.nextDouble() * 10 + batter.numberOfEyes;
            if(batValue <= pitchValue){
                pitchValue = r.nextDouble() * 10 + pitcher.grit;
                batValue = r.nextDouble() * 10 + batter.focus;
                if(batValue <= pitchValue){
                    strikes++;
                    batter.addStatistic("Strikes");
                    batter.addStatistic("Looking strikes");
                    if(strikeout())
                        addEvent(batter.name + " strikes out looking.",tick);
                    else
                        addEvent("Strike, looking. " + bs(),tick);
                }else{
                    pitchValue = r.nextDouble() * 10 + pitcher.dimensions;
                    batValue = r.nextDouble() * 10 + batter.malleability;
                    if(batValue <= pitchValue){
                        strikes++;
                        batter.addStatistic("Strikes");
                        batter.addStatistic("Swinging strikes");
                        if(strikeout())
                            addEvent(batter.name + " strikes out swinging.",tick);
                        else
                            addEvent("Strike, swinging. " + bs(),tick);
                    }
                }
            }
            if(!strikeout()){
                pitchValue = r.nextDouble() * 10 + pitcher.powder;
                batValue = r.nextDouble() * 10 + batter.splash;
                if(batValue <= pitchValue){
                    strikes++;
                    batter.addStatistic("Strikes");
                    while(strikeout()){
                        strikes--;
                        batter.addStatistic("Strikes",-1);
                    }
                    addEvent("Foul ball. " + bs(),tick);
                    batter.addStatistic("Foul balls hit");
                    pitcher.addStatistic("Foul balls pitched");
                }else{
                    batter.addStatistic("Hits");
                    pitcher.addStatistic("Hits allowed");
                    defender = randomDefender();
                    batValue = r.nextDouble() * 10 + batter.aggression;
                    double defenseValue = r.nextDouble() * 10 + defender.mathematics;
                    if(batValue <= defenseValue){
                        out = true;
                        addEvent(batter.name + " hit a flyout to " + defender.name + ".",tick);
                        batter.addStatistic("Flyouts hit");
                        defender.addStatistic("Flyouts caught");
                    }else{
                        batValue = r.nextDouble() * 10 + batter.hitPoints;
                        defenseValue = r.nextDouble() * 10 + defender.damage;
                        if(batValue <= defenseValue){
                            out = true;
                            addEvent(batter.name + " hit a ground out to " + defender.name + ".",tick);
                            batter.addStatistic("Ground outs hit");
                            defender.addStatistic("Ground outs fielded");
                        }else{
                            int basesRun = 0;
                            do{
                                batValue = r.nextDouble() * 10 + batter.effort;
                                defenseValue = r.nextDouble() * 10 + defender.carcinization;
                                basesRun++;
                            }while(batValue > defenseValue);
                            switch(basesRun){
                                case 1:
                                    addEvent(batter.name + " hits a Single!",tick);
                                    batter.addStatistic("Singles hit");
                                    pitcher.addStatistic("Singles allowed");
                                    break;
                                case 2:
                                    addEvent(batter.name + " hits a Double!",tick);
                                    batter.addStatistic("Doubles hit");
                                    pitcher.addStatistic("Singles allowed");
                                    break;
                                case 3:
                                    addEvent(batter.name + " hits a Triple!",tick);
                                    batter.addStatistic("Triples hit");
                                    pitcher.addStatistic("Singles allowed");
                                    break;
                                default:
                                    addEvent(batter.name + " hits a Home run!",tick);
                                    batter.addStatistic("Home runs hit");
                                    pitcher.addStatistic("Singles allowed");
                                    break;
                            }
                            advanceBaserunners(basesRun);
                            setNextBatter();
                        }
                    }
                }
            }
        }
    }

    void advanceBaserunners(int basesRun){
        boolean scored = false;
        for(int x = bases.length - 1; x >= 0; x--){
            if(bases[x] != null){
                if(x + basesRun >= bases.length){
                    score(bases[x]);
                    bases[x] = null;
                    scored = true;
                }else{
                    bases[x+basesRun] = bases[x];
                    bases[x] = null;
                }
            }
        }
        if(scored){
            printScore();
        }
    }

    Player randomDefender(){
        Player[] players = pitchingTeam.lineup;
        return players[(int)(r.nextDouble() * players.length)];
    }

    boolean strikeout(){
        return strikes >= 3 || out;
    }

    String bs(){ //balls strikes
        String b = "" + balls;
        String s = "" + strikes;
        if(balls < 0)
            b = "(-" + b + ")";
        if(strikes < 0)
            s = "(-" + s + ")";
        return b + "-" + s;
    }

    void clearBases(){
        bases = new Player[3];
    }

    void ball(){
        balls++;
        if(balls >= 4){
            addEvent(batter.name + " draws a walk.",tick);
            batter.addStatistic("Walks");
            pitcher.addStatistic("Walks allowed");
            walk();
            setNextBatter();
        }else
            addEvent("Ball. " + balls + "-" + strikes,tick);
    }

    void walk(){
        boolean scored = false;
        Player moving = batter;
        for(int x = 0; x < bases.length; x++){
            if(bases[x] != null){
                Player temp = moving;
                moving = bases[x];
                bases[x] = temp;
                if(x == bases.length - 1){
                    score(moving);
                    scored = true;
                }
            }else{
                bases[x] = moving;
                break;
            }
        }
        if(scored){
            printScore();
        }
    }

    void printScore(){
        addEvent("[Current score is " + teamA.abbreviation + " " + scoreToString(scoreA) + "-" + scoreToString(scoreB) + " " + teamB.abbreviation + "]",0);
    }

    public static String scoreToString(double score){
        String s;
        if(score % 1 == 0)
            s = "" + (int)score;
        else
            s = "" + (int)(score * 10)/10.0;
        if(score < 0)
            s = "(" + s + ")";
        return s;
    }

    void score(Player p){
        if(top){
            scoreB++;
            teamBScored = true;
        }else{
            scoreA++;
            teamAScored = true;
        }
        addEvent(p.name + " scores!",0);
        p.addStatistic("Runs scored");
        pitcher.addStatistic("Runs allowed");
    }

    boolean endOfGame(){
        return scoreA != scoreB && inning > 9 && top;
    }

    boolean endOfInning(){
        return outs >= 3;
    }

    //sets the next batter unless the inning is about to end
    void setNextBatter(){
        if(endOfInning())
            return;
        strikes = 0;
        balls = 0;
        do{
            activeTeamBat++;
            batter = battingTeam.lineup[(activeTeamBat-1) % battingTeam.lineup.length];
        }while(batter.elsewhere);
        addEvent(batter.name + " batting for the " + battingTeam.getName(),tick);
        batter.addStatistic("Plate appearences");
    }

    void addEvent(String s, int tick){
        events.add(new Event(s,currentTime));
        currentTime+=tick;
    }
    //precondition: at least 1 event in events
    public void setStartTime(long startTime){
        long shift = startTime - events.get(0).time;
        for(Event e : events){
            e.setTime(e.time() + shift);
        }
    }

    public void playLog(){
        for(int x = 0; x < events.size(); x++){
            while(System.currentTimeMillis() < events.get(x).time());
            System.out.println(events.get(x).text);
        }
    }

    void setRandomWeather(){
        weather = new Weather(this);
    }

}
