import java.util.*;
import java.util.HashMap;

public class Game //name will be changed to Game when finished and replace current Game class
{
    static int tick = 5000;
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
    double balls = 0;
    double strikes = 0;
    double outs = 0;
    int activeTeamBat = 0;
    int inactiveTeamBat = 0;

    Player[] bases;

    public Game(Team teamA, Team teamB, int dayNum, long startTime){
        this.teamA = teamA;
        this.teamB = teamB;
        this.dayNum = dayNum;
        this.startTime = startTime;
        events = new ArrayList<Event>();
    }
    //the code is entirely untested
    public void simulateGame(){
        r = new Random(dayNum *  + (long)Math.pow(teamA.favor,teamB.favor) + (long)Math.pow(teamB.favor,teamA.favor));
        addEvent(teamA.getTeamName() + " vs. " + teamB.getTeamName(),0);

        inning = 1;
        scoreA = 0;
        scoreB = 0;
        top = true;

        pitchingTeam = teamA;
        battingTeam = teamB;
        pitcher = pitcherA;
        waitingPitcher = pitcherB;
        clearBases();

        addEvent("Blay pall!",tick);

        while(!endOfGame()){
            String s;
            if(top)
                s = "Top";
            else
                s = "Bottom";
            addEvent(s + " of " + inning + ", " + battingTeam.getTeamName() + " batting. " + pitcher.name + " pitching.",tick);
            while(!endOfInning()){
                setNextBatter();
                while(!strikeout()){
                    doSteals();
                    doPitch();
                }
                strikes = 0;
                balls = 0;
                outs++;
            }
            outs++;
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
    }

    //precondition: simulateGame() has been called already
    boolean isLive(){
        //left off here, GameTester is broken
    }
    
    void giveWins(){
        Team winningTeam;
        Team losingTeam;
        if(scoreA > scoreB){
            winningTeam = teamA;
            losingTeam = teamB;
            teamA.addWin();
        }else{
            winningTeam = teamB;
            losingTeam = teamA;
            teamB.addWin();
        }
        teamA.win(winsA);
        teamB.win(winsB);
    }
    
    void stealAttempt(int baseNum){
        Player defender = randomDefender();
        double urge = r.nextDouble() * 10 + bases[baseNum].arrogance - defender.rejection;
        if(urge < 9.9)
            return;
        double stealValue = r.nextDouble() * 10 + bases[baseNum].dexterity;
        double defenseValue = r.nextDouble() * 10 + defender.wisdom;
        if(stealValue > defenseValue){
            switch(baseNum){
                case 0:
                    addEvent(bases[baseNum].name + " steals second base!",tick);
                    break;
                case 1:
                    addEvent(bases[baseNum].name + " steals third base!",tick);
                    break;
                default:
                    addEvent(bases[baseNum].name + " steals home!",tick);
                    break;
            }
            score(bases[baseNum]);
            if(baseNum < bases.length-1)
                bases[baseNum+1] = bases[baseNum];
            bases[baseNum] = null;
        }else{
            switch(baseNum){
                case 0:
                    addEvent(bases[baseNum].name + " gets caught stealing second base.",tick);
                    break;
                case 1:
                    addEvent(bases[baseNum].name + " gets caught stealing third base.",tick);
                    break;
                default:
                    addEvent(bases[baseNum].name + " gets caught stealing home.",tick);
                    break;
            }
            strikes+=100;
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
        if(batValue <= pitchValue)
            ball();
        else{
            pitchValue = r.nextDouble() * 10 + pitcher.fun;
            batValue = r.nextDouble() * 10 + batter.numberOfEyes;
            if(batValue <= pitchValue){
                pitchValue = r.nextDouble() * 10 + pitcher.grit;
                batValue = r.nextDouble() * 10 + batter.focus;
                if(batValue <= pitchValue){
                    strikes++;
                    if(strikeout())
                        addEvent(batter.name + " strikes out looking.",tick);
                    else
                        addEvent("Strike, looking. " + bs(),tick);
                }else{
                    pitchValue = r.nextDouble() * 10 + pitcher.dimensions;
                    batValue = r.nextDouble() * 10 + batter.malleability;
                    if(batValue <= pitchValue){
                        strikes++;
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
                    while(strikeout())
                        strikes--;
                    addEvent("Foul ball. " + bs(),tick);
                }else{
                    defender = randomDefender();
                    batValue = r.nextDouble() * 10 + batter.aggression;
                    double defenseValue = r.nextDouble() * 10 + defender.mathematics;
                    if(batValue <= defenseValue){
                        strikes+=100;
                        addEvent(batter.name + " hit a flyout to " + defender.name + ".",tick);
                    }else{
                        batValue = r.nextDouble() * 10 + batter.hitPoints;
                        defenseValue = r.nextDouble() * 10 + defender.damage;
                        if(batValue <= defenseValue){
                            strikes+=100;
                            addEvent(batter.name + " hit a ground out to " + defender.name + ".",tick);
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
                                    break;
                                case 2:
                                    addEvent(batter.name + " hits a Double!",tick);
                                    break;
                                case 3:
                                    addEvent(batter.name + " hits a Triple!",tick);
                                    break;
                                default:
                                    addEvent(batter.name + " hits a Home run!",tick);
                                    break;
                            }
                            advanceBaserunners(basesRun);
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
        return strikes >= 3;
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
            walk();
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
        if(top)
            scoreB++;
        else
            scoreA++;
        addEvent(p.name + " scores!",0);
    }

    boolean endOfGame(){
        return scoreA != scoreB && inning > 9 && top;
    }

    boolean endOfInning(){
        return outs >= 3;
    }

    void setNextBatter(){
        do{
            activeTeamBat++;
            batter = battingTeam.lineup[(activeTeamBat-1) % battingTeam.lineup.length];
        }while(batter.elsewhere);
        addEvent(batter.name + " batting for the " + battingTeam.getName(),tick);
    }

    void addEvent(String s, int tick){
        events.add(new Event(s,currentTime));
        currentTime+=tick;
    }

    void setRandomWeather(){
        weather = new Weather(this);
    }

}
