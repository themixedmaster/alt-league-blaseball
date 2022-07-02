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
    long interruptTime;
    ArrayList<Event> events;
    Weather weather;
    String weatherName;

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
    boolean baseStealOut = false;
    int activeTeamBat = 0;
    int inactiveTeamBat = 0;

    boolean teamAScored = false;
    boolean teamBScored = false;

    Player[] bases;

    String gameName;
    public Game(Team teamA, Team teamB, int dayNum, long startTime){
        this.teamA = teamA;
        this.teamB = teamB;
        this.dayNum = dayNum;
        this.startTime = startTime;
        currentTime = startTime;
        events = new ArrayList<Event>();
    }
    
    public void simulateGame(long time){
        interruptTime = time;
        r = new Random((long)(((double)dayNum + (double)teamA.favor + (double)teamB.favor) / (115.0 + 23.0 + 24.0) * Long.MAX_VALUE));
        for(int x = 0; x < 10;x++)
            r.nextDouble();
        weather = randomWeather();
        weatherName = weather.name;
        gameName = weatherName + ", " + teamA.getTeamName() + " vs. " + teamB.getTeamName() + ", Day " + dayNum;
        addEvent(teamA.getTeamName() + " vs. " + teamB.getTeamName(),0);
        //System.out.println(gameName);
        if(currentTime > interruptTime)
            return;

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

        weather.startOfGame();
        addEvent("Blay pall!",tick);
        if(currentTime > interruptTime)
        return;
        while(!endOfGame()){
        if(top)
        weather.beforeFullInning();
        weather.beforeHalfInning();
        String s;
        if(top)
        s = "Top";
        else
        s = "Bottom";
        addEvent(s + " of " + inning + ", " + battingTeam.getTeamName() + " batting. " + pitcher.name + " pitching.",tick);
        if(currentTime > interruptTime)
        return;
        pitcher.addStatistic("Innings pitched");
        clearBases();
        baseStealOut = false;
        while(!endOfInning()){
        if(!baseStealOut)
        setNextBatter();
        baseStealOut = false;
        int x = 0;
        while(!strikeout()){
        switch(x % 2){
    case 0:
        doSteals();
        break;
    case 1:
        weather.beforePitch();
        doPitch();
        weather.afterPitch();
        break;
        }
        x++;
        if(currentTime > interruptTime)
        return;
        //printBases();//debug feature
        }
        if(!out){
        pitcher.addStatistic("Strikeouts");
        batter.addStatistic("Times struck out");
        }
        out = false;
        outs++;
        addEvent("[Out " + outs + "]",0);
        if(currentTime > interruptTime)
        return;
        }
        outs = 0;
        if(top)
        top = false;
        else{
        top = true;
        addEvent("Inning " + inning + " is now an Outing.",tick);
        if(currentTime > interruptTime)
        return;
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
        activeTeamBat = inactiveTeamBat;
        inactiveTeamBat = temp3;
        if(endOfGame())
        weather.beforeEndOfPlay();
        }
        addEvent(teamA.getName() + " " + scoreToString(scoreA) + ", " + teamB.getName() + " " + scoreToString(scoreB),tick);
        if(currentTime > interruptTime)
        return;
        giveWins();
        clearGameEffects();
        if(!teamAScored)
        pitcherB.addStatistic("Shutouts");
        if(!teamBScored)
        pitcherA.addStatistic("Shutouts");
        weather.endOfGame();
        addEvent("\nGame over.",tick);
        if(currentTime > interruptTime)
        return;

        }

        void clearGameEffects(){
            for(Player p : activePlayers()){
                p.clearTemporaryStats();
            }
        }

        public Player[] activePlayers(){
            Player[] a = teamA.getActivePlayers();
            Player[] b = teamB.getActivePlayers();
            Player[] c = new Player[a.length + b.length];
            for(int x = 0; x < a.length; x++){
                c[x] = a[x];
            }
            for(int x = 0; x < b.length; x++){
                c[x+a.length] = b[x];
            }
            return c;
        }

        public Player randomActivePlayer(){
            Player[] players = activePlayers();
            int random = (int)(r.nextDouble() * players.length);
            return players[random];
        }

        public Player randomActivePlayer(Team team){
        Player[] players = team.getActivePlayers();
        int random = (int)(r.nextDouble() * players.length);
        return players[random];
    }

    //precondition: simulateGame() has been called already
    public String gameName(){
        return gameName;
    }

    //precondition: simulateGame() has been called already
    boolean isLive(){
        //System.out.println(dayNum + " " + startTime + " " + System.currentTimeMillis() + " " + currentTime);
        return currentTime >= System.currentTimeMillis() && startTime <= System.currentTimeMillis();
    }

    boolean isFinished(){
        return System.currentTimeMillis() >= currentTime && !isLive();
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
        winsA = weather.alterWinsA(winsA);
        winsB = weather.alterWinsA(winsB);
        if(gameEffectsRecord()){
            teamA.win(winsA);
            teamB.win(winsB);
        }
    }

    void stealAttempt(int baseNum){
        defender = randomDefender();
        double urge = r.nextDouble() * 10 + bases[baseNum].arrogance.value()/3 - defender.rejection.value()/3;
        if(urge < 9.95)
            return;
        steal(baseNum);
        doSteals();
    }

    void steal(int baseNum){
        bases[baseNum].addStatistic("Base steal attempts");
        if(defender == null)
            defender = randomDefender();
        double stealValue = r.nextDouble() * 10 + bases[baseNum].dexterity.value();
        double defenseValue = r.nextDouble() * 10 + defender.wisdom.value();
        if(stealValue > defenseValue){
            weather.beforeBaseSteal(bases[baseNum]);
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
                    score(bases[baseNum]);
                    printScore();
                    break;
            }
            if(baseNum < bases.length-1)
                bases[baseNum+1] = bases[baseNum];
            clearBase(baseNum);
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
            baseStealOut = true;
            clearBase(baseNum);
        }
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

    void doPitch(){
        double pitchValue = r.nextDouble() * 10 + pitcher.pinpointedness.value();
        double batValue = r.nextDouble() * 5 + batter.density.baseValue();//density can't be temp boosted
        pitcher.addStatistic("Pitches thrown");
        batter.addStatistic("Times pitched to");
        if(batValue > pitchValue){
            pitcher.addStatistic("Balls thrown");
            batter.addStatistic("Balls received");
            ball();
            weather.afterBall();
        }else{
            boolean struck = false;
            pitchValue = r.nextDouble() * 10 + pitcher.fun.value();
            batValue = r.nextDouble() * 10 + batter.numberOfEyes.value();
            batValue = weather.alterEyesRoll(batValue);
            if(batValue <= pitchValue){
                pitchValue = r.nextDouble() * 10 + pitcher.grit.value();
                batValue = r.nextDouble() * 10 + batter.focus.value();
                if(batValue <= pitchValue){
                    strikes++;
                    struck = true;
                    batter.addStatistic("Strikes");
                    batter.addStatistic("Looking strikes");
                    if(strikeout())
                        addEvent(batter.name + " strikes out looking.",tick);
                    else
                        addEvent("Strike, looking. " + bs(),tick);
                    return;
                }else{
                    pitchValue = r.nextDouble() * 10 + pitcher.dimensions.value();
                    batValue = r.nextDouble() * 10 + batter.malleability.value();
                    if(batValue <= pitchValue){
                        strikes++;
                        batter.addStatistic("Strikes");
                        batter.addStatistic("Swinging strikes");
                        if(strikeout())
                            addEvent(batter.name + " strikes out swinging.",tick);
                        else
                            addEvent("Strike, swinging. " + bs(),tick);
                        return;
                    }
                }
            }
            if(!strikeout() || batter.hasMod("Persistent")){
                batter.removeMod("Persistent");
                pitchValue = r.nextDouble() * 10 + pitcher.powder.value();
                batValue = r.nextDouble() * 10 + batter.splash.value();
                if(batValue <= pitchValue){
                    strikes++;
                    batter.addStatistic("Strikes");
                    while(overStrikeLimit()){
                        strikes--;
                        batter.addStatistic("Strikes",-1);
                    }
                    addEvent("Foul ball. " + bs(),tick);
                    batter.addStatistic("Foul balls hit");
                    pitcher.addStatistic("Foul balls pitched");
                }else{
                    defender = randomDefender();
                    weather.defenderDeclared();
                    batValue = r.nextDouble() * 10 + batter.aggression.value();
                    double defenseValue = r.nextDouble() * 10 + defender.mathematics.value();
                    if(batValue <= defenseValue){
                        out = true;
                        addEvent(batter.name + " hit a flyout to " + defender.name + ".",tick);
                        batter.addStatistic("Flyouts hit");
                        defender.addStatistic("Flyouts caught");
                    }else{
                        batValue = r.nextDouble() * 10 + batter.hitPoints.value();
                        defenseValue = r.nextDouble() * 10 + defender.damage.value();
                        if(batValue <= defenseValue){
                            out = true;
                            addEvent(batter.name + " hit a ground out to " + defender.name + ".",tick);
                            batter.addStatistic("Ground outs hit");
                            defender.addStatistic("Ground outs fielded");
                        }else{
                            batter.addStatistic("Hits");
                            int basesRun = 0;
                            double baseDifficulty = 0;
                            do{
                                batValue = r.nextDouble() * 10 + batter.effort.value();
                                defenseValue = r.nextDouble() * 10 + defender.carcinization.value() + baseDifficulty;
                                basesRun++;
                                baseDifficulty+=0.1;
                            }while(batValue > defenseValue);
                            basesRun = weather.alterBasesRun(basesRun);
                            switch(basesRun){
                                case 1:
                                    addEvent(batter.name + " hits a Single!",tick);
                                    batter.addStatistic("Singles hit");
                                    pitcher.addStatistic("Singles allowed");
                                    break;
                                case 2:
                                    addEvent(batter.name + " hits a Double!",tick);
                                    batter.addStatistic("Doubles hit");
                                    pitcher.addStatistic("Doubles allowed");
                                    break;
                                case 3:
                                    addEvent(batter.name + " hits a Triple!",tick);
                                    batter.addStatistic("Triples hit");
                                    pitcher.addStatistic("Triples allowed");
                                    break;
                                default:
                                    addEvent(batter.name + " hits a Home run!",tick);
                                    batter.addStatistic("Home runs hit");
                                    pitcher.addStatistic("Home runs allowed");
                                    break;
                            }
                            weather.afterHit();
                            if(basesRun >= 4)
                                weather.afterHomeRun();
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
                    clearBase(x);
                    scored = true;
                }else{
                    bases[x+basesRun] = bases[x];
                    clearBase(x);
                }
            }
        }
        if(basesRun > bases.length){
            score(batter);
            scored = true;
        }else{
            bases[basesRun-1] = batter;
        }

        if(scored){
            printScore();
        }
    }

    void clearBase(int baseNum){
        weather.baseLeft(baseNum);
        bases[baseNum] = null;
    }

    Player randomDefender(){
        Player[] players = pitchingTeam.lineup;
        return players[(int)(r.nextDouble() * players.length)];
    }

    int randomOccupiedBase(){
        if(basesEmpty())
            return -1;
        int pick;
        do{
            pick = (int)(r.nextDouble() * bases.length);
        }while(bases[pick] == null);
        return pick;
    }

    Player randomBaserunner(){
        if(basesEmpty())
            return null;
        Player pick = null;
        do{
            pick = bases[(int)(r.nextDouble() * bases.length)];
        }while(pick == null);
        return pick;
    }

    boolean basesEmpty(){
        for(int x = 0; x < bases.length; x++)
            if(bases[x] != null)
                return false;
        return true;
    }

    boolean strikeout(){
        return weather.alterStrikeout(overStrikeLimit() || out);
    }

    boolean overStrikeLimit(){
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
        if(bases != null){
            for(int x = 0; x < bases.length; x++)
                if(bases[x] != null)
                    clearBase(x);
        }else
            bases = new Player[3];
    }

    void ball(){
        balls++;
        if(weather.alterWalk(balls >= 4) && !batter.hasMod("Stubbed Toe")){
            addEvent(batter.walkMessage(),tick);
            batter.addStatistic("Walks");
            pitcher.addStatistic("Walks allowed");
            walk();
            setNextBatter();
        }else
            addEvent("Ball. " + balls + "-" + strikes,tick);
    }

    void walk(){
        weather.beforeWalk();
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
        weather.afterWalk();
    }

    void printBases(){
        String s = "";
        for(int x = 0; x < bases.length; x++){
            if(bases[x] == null){
                s = s  + "_";
            }else{
                s = s + "X";
            }
        }
        addEvent("[Bases:" + s + "]",0);
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
        double runsScored = weather.alterRunsScored(1);
        if(top){
            scoreB+=runsScored;
            teamBScored = true;
        }else{
            scoreA+=runsScored;
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
        if(endOfInning() || (batter != null && batter.hasMod("Persistent")))
            return;
        strikes = 0;
        balls = 0;
        do{
            if(noAvailableBatters(battingTeam))
                addBatter(battingTeam);
            activeTeamBat++;
            batter = battingTeam.lineup[(activeTeamBat-1) % battingTeam.lineup.length];
            if(batter.hasMod("Elsewhere"))
                weather.batterElsewhere();
        }while(batter.hasMod("Elsewhere"));
        addEvent(batter.batMessage(battingTeam),tick);
        batter.addStatistic("Plate appearances");
        weather.batterDeclared();
    }

    boolean noAvailableBatters(Team t){
        Player[] lineup = t.lineup;
        for(Player p : lineup)
            if(!p.hasMod("Elsewhere"))
                return false;
        return true;
    }
    
    void addBatter(Team t){
        Player[] lineup = t.lineup;
        Player [] newLineup = new Player[lineup.length + 1];
        for(int x = 0; x < lineup.length; x++)
            newLineup[x] = lineup[x];
        newLineup[lineup.length] = new Player("Pitching Machine");
        t.lineup = newLineup;
    }
    
    void addEvent(String s){
        addEvent(s,tick);
    }

    void addEvent(String s, boolean special){
        addEvent(s,tick,special);
    }

    void addEvent(String s, int tick){
        addEvent(s,tick,false);
    }

    void addEvent(String s, int tick, boolean special){
        currentTime+=tick;
        weather.beforeAddEvent();
        s = weather.alterEvent(s);
        events.add(new Event(s,currentTime,special));
        //if(currentTime > startTime + 3600000)//3600000
        //System.out.println(s);
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

    int numberOfWeathers(){
        return 18;//18
    }

    Weather randomWeather(){
        int rand = (int)(r.nextDouble() * numberOfWeathers());
        switch(rand){
            case 0:
                return new SolarEclipse(this);
            case 1:
                return new Meownsoon(this);
            case 2:
                return new PulsarPulsar(this);
            case 3:
                return new SnailMail(this);
            case 4:
                return new Crabs(this);
            case 5:
                return new FloatingMirrors(this);
            case 6:
                return new Rain(this);
            case 7:
                return new Distortion(this);
            case 8:
                return new Growth(this);
            case 9:
                return new FeverPitch(this);
            case 10:
                return new Multiball(this);
            case 11:
                return new NormalizedWeather(this);
            case 12:
                return new NamedWeather(this);
            case 13:
                return new Brisk(this);
            case 14:
                return new SupernovaEclipse(this);
            case 15:
                return new Letterball(this);
            case 16:
                return new ManyPulsar(this);
            case 17:
                return new Jackpot(this);
        }
        return new Weather(this);
    }

}
