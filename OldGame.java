import java.util.*;
import java.util.HashMap;

public class OldGame
{
    final double PI = Math.PI;

    static int frameTime = 5000;
    Random r;

    public int season;
    int inning;
    boolean top;
    Team teamA;
    Team teamB;
    Player pitcherA;
    Player pitcherB;
    Player batter;
    Player[] bases;
    HashMap<Double,Player> radianBases;
    double scoreA;
    double scoreB;
    int balls;
    int strikes;
    int outs;
    long startTime;
    long continueTime;
    boolean watching = false;
    boolean continueRunning = true;
    boolean live = false;
    int dayNum;
    String weather = "";
    int winsA;
    int winsB;
    int bubbleScore;//for altalt bubble weather
    boolean madness = false;//for altalt madness weather
    boolean vortex = false;//for altalt time vortex weather
    long endTime; //vortex
    long switchTime; //vortex
    int battingTeamBattingPosition;
    int pitchingTeamBattingPosition;
    Team pitchingTeam;
    Team battingTeam;
    Player pitcher;
    Player waitingPitcher;
    double runValue;

    public OldGame(Team teamA, Team teamB, int dayNum, long startTime, int season){
        this.teamA = teamA;
        this.teamB = teamB;
        this.dayNum = dayNum;
        this.startTime = startTime;
        this.season = season;
        weather = "Time Vortex";//randomAltWeather();
    }

    public void runGame(){
        madness = false;
        r = new Random(dayNum + 4 + teamA.favor + teamB.favor);
        inning = 1;
        top = true;
        pitcherA = teamA.rotation[(dayNum - 1 + teamA.rotation.length) % teamA.rotation.length];
        pitcherB = teamB.rotation[(dayNum - 1 + teamB.rotation.length) % teamB.rotation.length];
        batter = teamB.lineup[0];
        clearBases();
        scoreA = 0;
        scoreB = 0;
        winsA = 0;
        winsB = 0;
        balls = 0;
        strikes = 0;
        outs = 0;
        continueTime = startTime;
        live = true;
        continueRunning = true;
        String nameA = teamA.name; //for meownsoon & error
        String nameB = teamB.name; //for meownsoon & error
        String locationA = teamA.location; //for error
        String locationB = teamB.location; //for error
        Player[] lineupA = copy(teamA.lineup); //for hot bench & duel
        Player[] lineupB = copy(teamB.lineup); //for hot bench & duel
        Player[] rotationA = copy(teamA.rotation); //for duel
        Player[] rotationB = copy(teamB.rotation); //for duel
        endTime = startTime + 1080000 * (long)1000;//1080000 * (long)1000; //vortex
        switchTime = startTime; //vortex
        if(startTime > System.currentTimeMillis() && watching){
            printlnC("The game will begin shortly.");
        }
        printlnInstantC(teamA.getTeamName() + " vs. " + teamB.getTeamName());
        if(!weather.equals(""))
            printlnC("Weather: " + weather);
        if(weather.equals("Reverse"))
            printlnC("The game reverses!\nPitching is batting, batting is pitching.");
        else if(weather.equals("rain")){
            printlnC("The players are slippery, baserunning is hindered!");
            for(Player p : activePlayers()){
                p.tempBaserunning-=2;
            }
        }else if(weather.equals("Moon"))
            printlnC("Night falls. A Moon shines on the field.");
        else if(weather.equals("Pulsar"))
            madness = true;
        else if(weather.equals("Hot Bench")){
            printlnC("The benches are hot! All pitchers join the lineup!");
            teamA.lineup = removePlayer(teamA.getActivePlayers(),pitcherA);
            teamB.lineup = removePlayer(teamB.getActivePlayers(),pitcherB);
        }else if(weather.equals("Duel"))
            startDuel();
        else if(weather.equals("Pixelization"))
            pixelize();
        else if(weather.equals("Brisk"))
            printlnC("It's perfect weather for a walk");
        else if(weather.equals("Space"))
            printlnC("Orbits align, pitchers experience less gravity");
        else if(weather.equals("Geometry"))
            printlnC("Geometry is applied. Players will now Round the bases");
        else if(weather.equals("Sleet"))
            printlnC("Sleet is falling, chilling the pitchers' hands.");
        else if(weather.equals("Earthquake")){
            printlnC("The ground rumbles...");
            printlnC("An Earthquake has begun!");
        }else if(weather.equals("Time Vortex")){
            vortex = true;
            dayNum = 1;
        }
        clearBases();
        printlnC("Blay pall!");
        pitchingTeam = teamA;
        battingTeam = teamB;
        pitcher = pitcherA;
        waitingPitcher = pitcherB;
        battingTeamBattingPosition = 0;
        pitchingTeamBattingPosition = 0;
        sortAlphabetically(teamA.getActivePlayers());
        
        while((inning < 10 || scoreA == scoreB|| !top) || (vortex && continueTime < endTime)){
            beforeHalfInning();
            if(top){
                printC("Top");
            }else{
                printC("Bottom");
            }
            printlnInstantC(" of " + inning + ", " + battingTeam.getTeamName() + " batting. " + pitcher.name + " pitching.");
            halfInningStart();
            while(outs < 3){
                beforeOut();
                nextBatter();
                afterBatterDeclared();
                while(strikes < 3){
                    double pitchValue;
                    double batValue;
                    if(weather.equals("Reverse")){
                        pitchValue = r.nextDouble() * 10 + pitcher.batting + pitcher.tempBatting;
                        batValue = r.nextDouble() * 10 + batter.pitching + batter.tempPitching;
                    }else if(weather.equals("Strikedown")){
                        pitchValue = r.nextDouble() * 10 + pitcher.pitching + pitcher.tempPitching;
                        batValue = r.nextDouble() * 10 + pitcher.batting + pitcher.tempBatting + 5;
                    }else{
                        pitchValue = r.nextDouble() * 10 + pitcher.pitching + pitcher.tempPitching;
                        batValue = r.nextDouble() * 10 + batter.batting + batter.tempBatting;
                    }
                    if(weather.equals("Space"))
                        pitchValue*=1.25;
                    double geometricValue = pitchValue / batValue + PI / 4; //for geometry weather
                    if((Math.abs(batValue - pitchValue) <= 1 && !weather.equals("Sleet")) || (weather.equals("Sleet") && (batValue - pitchValue) > -1.5 && (batValue - pitchValue) <= 1)){
                        balls++;
                        if(balls >= 4 || (weather.equals("Brisk") && balls >= 2)){
                            balls = 0;
                            printC(batter.name + " draws a walk.");
                            if(weather.equals("Geometry"))
                                printInstantC(" They Round " + geometricValue + " radians.");
                            if(batter.cat)
                                printInstantC(" Meow.");
                            printlnInstant("");
                            if(weather.equals("Geometry"))
                                geometricWalk(geometricValue);
                            else
                                walk();
                            balls = 0;
                            strikes = 0;
                            nextBatter();
                            afterBatterDeclared();
                        }else{
                            printlnC("Ball. " + balls + "-" + strikes);
                        }
                        afterPitch();
                    }else if(batValue - pitchValue <= -1){
                        strikes++;
                        if(strikes >= 3){
                            if(pitchValue - batValue > 2){
                                printlnC(batter.name + " strikes out looking.");
                            }else{
                                printlnC(batter.name + " strikes out swinging.");
                            }
                        }else{
                            if(pitchValue - batValue > 3){
                                printlnC("Foul ball. " + balls + "-" + strikes);
                                if(weather.equals("Superposition"))
                                    shuffleBases();
                            }else if(pitchValue - batValue > 1.5){
                                printlnC("Strike, looking. " + balls + "-" + strikes);
                            }else{
                                printlnC("Strike, swinging. " + balls + "-" + strikes);
                            }
                            afterPitch();
                        }
                    }else{
                        Player outfielder = randomPresentDefender();
                        double defenseValue = r.nextDouble() * 10 + outfielder.defense + outfielder.tempDefense;
                        double baserunningValue;
                        if(weather.equals("Strikedown"))
                            baserunningValue = r.nextDouble() * 10 + batter.baserunning + batter.tempBaserunning + 5;
                        else
                            baserunningValue = r.nextDouble() * 10 + batter.baserunning + batter.tempBaserunning;
                        int basesRun = (int)(baserunningValue - defenseValue);
                        if((basesRun > 0 && !weather.equals("Sleet"))||(weather.equals("Sleet") && basesRun > 0)){
                            if(weather.equals("Sleet")){
                                switch(basesRun){
                                    case 1:case 2:
                                        basesRun = 1;
                                        break;
                                    case 3:case 4:case 5:case 6:
                                        basesRun = 2;
                                        break;
                                    case 7:case 8:case 9:case 10:
                                        basesRun = 3;
                                        break;
                                    default:
                                        basesRun = 4;
                                        break;
                                }
                            }else if(batter.superCharged)
                                basesRun = (int)(basesRun / 1.4 + 1);
                            else
                                basesRun = (int)(basesRun / 3 + 1);
                            switch(basesRun){
                                case 1:
                                    printC(batter.name + " hits a Single!");
                                    break;
                                case 2:
                                    printC(batter.name + " hits a Double!");
                                    break;
                                case 3:
                                    printC(batter.name + " hits a Triple!");
                                    break;
                                default:
                                    if(batter.superCharged)
                                        printC(batter.name + " hits the ball with an electrical crack! " + batter.name + " is Discharged... ");
                                    printInstantC(batter.name + " hits a home run!");
                                    break;
                            }
                            double distanceRun = (geometricValue * (baserunningValue - defenseValue) / 3 + 1) * (PI/2) / 1.5;
                            if(weather.equals("Geometry")){
                                printlnInstantC(" The bases rotate by " + distanceRun + " radians.");
                                rotateBases(distanceRun);
                            }else{
                                printlnInstantC("");
                                advanceBaserunners(basesRun);
                            }
                            if(basesRun > 3)
                                batter.superCharged = false;
                            if(outs >= 3)
                                break;
                            balls = 0;
                            strikes = 0;
                            afterPitch();
                            nextBatter();
                            afterBatterDeclared();
                        }else{
                            if(batValue - pitchValue > 2){
                                printlnC(batter.name + " hit a flyout to " + outfielder.name + ".");
                            }else{
                                printlnC(batter.name + " hit a ground out to " + outfielder.name + ".");
                            }
                            break;
                        }
                    }
                }
                strikes = 0;
                balls = 0;
                outs++;
                if(outs < 4)
                    out();
                afterPitch();
            }
            outs = 0;
            clearBases();
            afterHalfInning();
            if(top){
                top = false;
            }else{
                top = true;
                printlnC("Inning " + inning + " is now an Outing.");
                afterInning();
                printScore();
                inning++;
            }
            Team temp = pitchingTeam;
            pitchingTeam = battingTeam;
            battingTeam = temp;
            Player temp2 = pitcher;
            pitcher = waitingPitcher;
            waitingPitcher = temp2;
            int temp3 = battingTeamBattingPosition;
            battingTeamBattingPosition = pitchingTeamBattingPosition;
            pitchingTeamBattingPosition = temp3;
        }
        if(weather.equals("Electrotherapy 2")){
            printlnC("The static dissipates...");
            printlnC("All stars revert to normal charge.");
        }else if(weather.equals("Moon")){
            printlnC("Day breaks. A Sun shines on the field.");
        }else if(weather.equals("Duel")){
            if(scoreA > scoreB)
                printlnC(teamA.lineup[0].name + " wins! Honour is satisfied!");
            else
                printlnC(teamB.lineup[0].name + " wins! Honour is satisfied!");
        }
        for(Player p : activePlayers()){
            if(weather.equals("Normal Weather") && (p.tempBatting > 0 || p.tempPitching > 0 || p.tempBaserunning > 0 || p.tempDefense > 0))
                println(p.name + " returned to feeling Abnormal.");
            else if(weather.equals("Moon") && p.elsewhere)
                println(p.name + " appears in the outfield!");
            p.tempBatting = 0;
            p.tempPitching = 0;
            p.tempBaserunning = 0;
            p.tempDefense = 0;
            p.cat = false;
            p.elsewhere = false;
            p.superCharged = false;
        }
        beforeWins();
        printlnC(teamA.getName() + " " + scoreToString(scoreA) + ", " + teamB.getName() + " " + scoreToString(scoreB));
        teamA.name = nameA;
        teamB.name = nameB;
        teamA.location = locationA;
        teamB.location = locationB;
        teamA.lineup = lineupA;
        teamB.lineup = lineupB;
        teamA.rotation = rotationA;
        teamB.rotation = rotationB;
        printlnC("");
        printlnC("Game over.");
        //println("debug note: day# = " + dayNum);
        if(continueRunning){
            if(dayNum < 100 || vortex){
                if(scoreA > scoreB){
                    if(!weather.equals("Friendship"))
                        winsA++;
                    teamA.addWin();
                    teamB.lose();
                }else{
                    if(!weather.equals("Friendship"))
                        winsB++;
                    teamB.addWin();
                    teamA.lose();
                }
                teamA.win(winsA);
                teamB.win(winsB);
            }else{
                if(scoreA > scoreB){
                    if(!weather.equals("Friendship"))
                        winsA++;
                }else{
                    if(!weather.equals("Friendship"))
                        winsB++;
                }
            }//else{
            //    System.out.println(gameName() + " " + startTime + " " + dayNum);
            //}
            //System.out.println(gameName() + " is no longer live.");
            live = false;
        }
        if(madness)
            weather = "Pulsar";
        if(vortex)
            weather = "Time Vortex";
    }

    public void out(){
        printlnInstant("[Out " + outs + "]");
        if(weather.equals("Error"))
            errorOut();
    }

    public void beforeWins(){
        if(weather.equals("Friendship"))
            friendship();
    }

    public void afterEnterBase(Player p,int base){
        if(weather.equals("Faraday Field"))
            faradayStrike(p,base);
    }

    public void nextBatter(){
        do{
            battingTeamBattingPosition++;
            if(weather.equals("Earthquake")){
                int random = (int)(r.nextDouble() * battingTeam.lineup.length);
                batter = battingTeam.lineup[random];
            }else
                batter = battingTeam.lineup[battingTeamBattingPosition % battingTeam.lineup.length];
            if(weather.equals("Moon") && batter.elsewhere)
                println(batter.name + " is nowhere to be seen...");
        }while(batter.elsewhere);
        printC(batter.name + " batting for the " + battingTeam.getName());
        if(batter.cat)
            printlnInstant(". Meow.");
        else
            printlnInstant("");
        //afterEnterBase(batter,-1);
    }

    public void removePlayerFromBases(Player p){
        for(int x = 0; x < bases.length; x++)
            if(p.equals(bases[x]))
                bases[x] = null;
    }

    public void clearBases(){
        if(bases == null){
            bases = new Player[3];
            return;
        }
        for(int x = 0; x < bases.length; x++){
            if(bases[x] != null && bases[x].fan){
                printlnC("Rogue Umpire incinerated " + bases[x].name + "!");
            }
        }
        bases = new Player[3];
        radianBases = new HashMap<Double,Player>();
    }

    public void halfInningStart(){
        if(vortex && top && inning > 1){
            printlnC("The game continues.");
            if(endTime - continueTime >= 3600 * (long)1000){
                printlnC(((endTime - continueTime) / (3600 * (long)1000)) + " hours remain.");
            }else if(endTime - continueTime >= 60 * (long)1000){
                printlnC(((endTime - continueTime) / (60 * (long)1000)) + " minutes remain.");
            }else{
                printlnC(((endTime - continueTime) / (1 * (long)1000)) + " seconds remain.");
            }
        }
        if(vortex && top && continueTime >= switchTime){
            switchTime += 3600/*3600*/ * (long)1000;
            madness = false;
            weather = randomTimelessWeather();
            printlnC("Temporal mass shifts.");
            printlnC("Pitchers rotate.");
            pitcherA = teamA.rotation[(dayNum - 1) % teamA.rotation.length];
            pitcherB = teamB.rotation[(dayNum - 1) % teamB.rotation.length];
            pitcher = pitcherA;
            waitingPitcher = pitcherB;
            printlnC(pitcher.name + " pitching.");
            printlnC("Day " + dayNum + ".");
            dayNum++;
            printlnC("The weather is now " + weather + ".");
            //System.out.println("The weather is now " + weather + ".");
            if(weather.equals("pulsar"))
                madness = true;
        }
        if(madness && top){
            weather = randomAltWeather();
            printlnC("The Pulsar Beams! " + weather + " is expelled!");
        }
        if(weather.equals("Strikedown"))
            printlnC("The " + battingTeam.name + " go silent, their bodies moving in mechanical lockstep.");
        else if(weather.equals("Error"))
            errorEvent();
        else if(weather.equals("Harsh Sunlight"))
            printlnC("The sunlight is harsh!");
    }

    public void beforeOut(){
        if(weather.equals("Meownsoon"))
            preOutCatEvent();
    }

    public void afterBatterDeclared(){
        if(weather.equals("Electrotherapy 2"))
            electroBoost(batter);
        else if(weather.equals("Normal Weather"))
            normalStatChange();
    }

    public void afterPitch(){
        if(weather.equals("Fever Pitch"))
            feverPitch();
        else if(weather.equals("Lightning Storm"))
            lightningStrike();
        else if(weather.equals("Electric Storm"))
            electricStrike();
        else if(weather.equals("Space"))
            spaceOut();
    }

    public void beforeHalfInning(){
        if(weather.equals("Meownsoon"))
            catEvent();
        else if(weather.equals("Moon"))
            moonEvent();
    }

    public void afterInning(){
        if(weather.equals("Electrotherapy 2"))
            printElectroMessage();
        else if(weather.equals("Bubble"))
            popBubble();
    }

    public void afterHalfInning(){
        if(weather.equals("Birds"))
            printBirdMessage();
        if(weather.equals("Fever Pitch"))
            printFeverMessage();
        if(weather.equals("Error"))
            errorHalfInning();
        if(weather.equals("Faraday Field"))
            printFaradayMessage();
    }

    public double getScoreA(){
        if(continueRunning){
            return scoreA;
        }
        return 0;
    }

    public double getScoreB(){
        if(continueRunning){
            return scoreB;
        }
        return 0;
    }

    public Team getWinner(){
        if(scoreA > scoreB){
            return teamA;
        }
        if(scoreA < scoreB){
            return teamB;
        }
        return null;
    }

    public void score(Player p){
        if(!p.fan && !weather.equals("Bubble"))
            printlnInstantC(p.name + " scores!");
        if(top){
            if(weather.equals("Bubble")){
                bubbleScore++;
                printlnInstant(p.name + " scores! Bubble at " + bubbleScore + "!");
            }else if(weather.equals("Sun .1"))
                scoreB = scoreB + (1 + inning/10.0);
            else if(weather.equals("Unsun"))
                scoreB = scoreB + (1 - inning/10.0);
            else if(p.fan){
                if(p.originalTeam.equals(teamB)){
                    printlnC(p.name + " scores in Favour! 1 run scored!");
                    scoreB++;
                }else{
                    printlnC(p.name + " scores against Favour! 1 unrun scored!");
                    scoreB--;
                }
            }else{
                scoreB++;
            }
            if(scoreB >= 10 && weather.equals("Sun 2")){
                printlnC("The " + teamB.name + " collect 10!" +
                    "\nSun 2 smiles.");
                scoreB-=10;
                winsB++;
            }
            if(scoreB >= 10 && weather.equals("Black Hole")){
                printlnC("The " + teamB.name + " collect 10!" +
                    "\nThe black hole swallows the Runs and a " + teamA.name + " win.");
                scoreB-=10;
                winsA--;
            }
        }else{
            if(weather.equals("Bubble")){
                bubbleScore--;
                printlnInstant(p.name + " scores! Bubble at " + bubbleScore + "!");
            }else if(weather.equals("Sun .1"))
                scoreA = scoreA + (1 + inning/10.0);
            else if(weather.equals("Unsun"))
                scoreA = scoreA + (1 - inning/10.0);
            else if(p.fan){
                if(p.originalTeam.equals(teamA)){
                    printlnC(p.name + " scores in Favour! 1 run scored!");
                    scoreA++;
                }else{
                    printlnC(p.name + " scores against Favour! 1 unrun scored!");
                    scoreA--;
                }
            }else{
                scoreA++;
            }
            if(scoreA >= 10 && weather.equals("Sun 2")){
                printlnC("The " + teamA.name + " collect 10!" +
                    "\nSun 2 smiles.");
                scoreA-=10;
                winsA++;
            }
            if(scoreA >= 10 && weather.equals("Black Hole")){
                printlnC("The " + teamA.name + " collect 10!" +
                    "\nThe black hole swallows the Runs and a " + teamB.name + " win.");
                scoreA-=10;
                winsB--;
            }
        }
    }

    void advanceBaserunners(int basesRun){
        boolean maximumBlaseball = maximumBlaseball();
        boolean scored = false;
        for(int x = bases.length - 1; x >= 0; x--){
            int baseRun = basesRun;
            if(weather.equals("Raining Cats and Dogs") && r.nextDouble() < 0.25 && bases[x] != null){
                int random = (int)(r.nextDouble() * 2);
                switch(random){
                    case 0:
                        println(bases[x].name + " tripped over a cat! Out.");
                        outs++;
                        out();
                        bases[x] = null;
                        break;
                    case 1:
                        if(canAdvanceExtra(x,basesRun)){
                            baseRun++;
                            println(bases[x].name + " was chased by a dog! " + bases[x].name + " proceeds to " + getBaseName(x+baseRun) + ".");
                        }
                        break;
                }
            }
            if(bases[x] != null){
                Player p = bases[x];
                for(int y = 0; y < baseRun; y++){
                    if(x+y < bases.length - 1)
                        afterEnterBase(p, x+y);
                }
                if(bases[x] == null)
                    return;
                if(x + baseRun >= bases.length){
                    score(bases[x]);
                    bases[x] = null;
                    scored = true;
                }else{
                    bases[x+baseRun] = bases[x];
                    bases[x] = null;
                }
                for(int y = 0; y < baseRun; y++){
                    if(x+y >= bases.length - 1)
                        afterEnterBase(p, x+y);
                }
            }
        }
        int baseRun = basesRun;
        if(weather.equals("Raining Cats and Dogs") && r.nextDouble() < 0.01){
            int random = (int)(r.nextDouble() * 2);
            switch(random){
                case 0:
                    println(batter.name + " tripped over a cat! Out.");
                    outs++;
                    out();
                    break;
                case 1:
                    if(canAdvanceExtra(-1,basesRun)){
                        baseRun++;
                        println(batter.name + " was chased by a dog! " + batter.name + " proceeds to " + getBaseName(-1+baseRun) + ".");
                    }
                    break;
            }
        }
        int oldOuts = outs;
        for(int y = 0; y < baseRun; y++){
            if(y < bases.length - 1)
                afterEnterBase(batter, y);
        }
        if(oldOuts > outs)
            return;
        if(baseRun > bases.length){
            score(batter);
            scored = true;
        }else{
            bases[baseRun - 1] = batter;
        }
        for(int y = 0; y < baseRun; y++){
            if(y >= bases.length - 1)
                afterEnterBase(batter, y);
        }
        if(weather.equals("Exponential") && maximumBlaseball){
            if(top)
                scoreB+=(int)Math.pow(basesRun,2)-basesRun;
            else
                scoreA+=(int)Math.pow(basesRun,2)-basesRun;
            println("The Exponential thunders. Numbers go up! " + (int)Math.pow(basesRun,2) + " runs scored.");
        }
        if(scored){
            printScore();
        }
    }

    boolean canAdvanceExtra(int base, int basesRun){
        if(base + basesRun + 1 >= bases.length)
            return true;
        if(bases[base + basesRun] == null)
            return true;
        return false;
    }

    String getBaseName(int base){
        switch(base){
            case 0:
                return "first base";
            case 1:
                return "second base";
            case 2:
                return "third base";
            case 3:default:
                return "home plate";
        }
    }

    boolean maximumBlaseball(){
        return basesLoaded() && balls == 3 && strikes == 2 && outs == 2;
    }

    boolean basesLoaded(){
        for(Player p : bases)
            if(p == null)
                return false;
        return true;
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
        println("[Current score is " + teamA.abbreviation + " " + scoreToString(scoreA) + "-" + scoreToString(scoreB) + " " + teamB.abbreviation + "]");
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

    public String cloud(String input){
        String s = "";
        for(int x = 0; x < input.length(); x++){
            s+="☁";
        }
        return s;
    }

    void printC(String message){
        if(weather.equals("Cloudy") && r.nextDouble() <= 0.3)
            print(cloud(message));
        else
            print(message);
    }

    void printlnC(String message){
        if(weather.equals("Cloudy") && r.nextDouble() <= 0.3)
            println(cloud(message));
        else
            println(message);
    }

    void printInstantC(String message){
        if(weather.equals("Cloudy") && r.nextDouble() <= 0.3)
            printInstant(cloud(message));
        else
            printInstant(message);
    }

    void printlnInstantC(String message){
        if(weather.equals("Cloudy") && r.nextDouble() <= 0.3)
            printlnInstant(cloud(message));
        else
            printlnInstant(message);
    }

    void print(String message){
        wait(frameTime);
        if(watching){
            System.out.print(message);
        }
    }

    void println(String message){
        wait(frameTime);
        if(watching){
            System.out.println(message);
        }
    }

    void printInstant(String message){
        if(watching){
            System.out.print(message);
        }
    }

    public void printlnInstant(String message){
        if(watching){
            System.out.println(message);
        }
    }

    void wait(int time){
        continueTime = continueTime + time;
        while(continueTime + time > System.currentTimeMillis()){
            if(!watching){
                continueRunning = false;
                return;
            }
        }
    }

    public String gameName(){
        if(season < 3 && season > -2)//change to 3 before release
            return teamA.getTeamName() + " vs. " + teamB.getTeamName();
        return weather + ", " + teamA.getTeamName() + " vs. " + teamB.getTeamName();
    }

    public boolean isLive(){
        return live;
    }

    public boolean hasStarted(){
        return startTime < System.currentTimeMillis();
    }

    void watch(){
        watching = true;
    }

    void leave(){
        watching = false;
    }

    String randomWeather(){
        r = new Random((dayNum * 12345 + 12345) * season * teamA.favor * teamB.favor);
        //System.out.println(dayNum * season * teamA.favor * teamB.favor);
        int rand = (int)(r.nextDouble() * 6);
        //System.out.println((dayNum * season * teamA.favor * teamB.favor) + " " + rand);
        switch(rand){
            case 0:
                return "Birds";
            case 1:
                return "Sun .1";
            case 2:
                return "Sun 2";
            case 3:
                return "Black Hole";
            case 4:
                return "Unsun";
            case 5:
                return "Reverse";
            default:
                return "";
        }
    }

    String horribleJoke(){
        if(!madness)
            r = new Random((dayNum * 12345 + 12345) * season * teamA.favor * teamB.favor);
        int rand = (int)(r.nextDouble() * 3);
        String w;
        switch(rand){
            case 0:
                w = "Strikedown";
                break;
            case 1:
                w = "Sun 2";
                break;
            case 2:
                if(!madness)
                    w = "Pulsar";//complete!
                else
                    w = horribleJoke();
                break;
            default:
                w = "";
        }
        if(weatherValid(w))
            return w;
        else
            return horribleJoke();
    }

    String randomTimelessWeather(){
        if(!madness && !vortex)
            r = new Random((dayNum * 12345 + 12345) * season * teamA.favor * teamB.favor);
        //System.out.println(dayNum * season * teamA.favor * teamB.favor);
        int rand = (int)(r.nextDouble() * 24);//24
        //rand = 25;
        //System.out.println((dayNum * season * teamA.favor * teamB.favor) + " " + rand);
        String w;
        switch(rand){
            case 0:
                w = "Birds";
                break;
            case 1:
                w = "Sun .1";
                break;
            case 2:
                w = "Sun 2";
                break;
            case 3:
                w = "Black Hole";
                break;
            case 4:
                w = "Unsun";
                break;
            case 5:
                w = "Reverse";
                break;
            case 6:
                w = "Fever Pitch";//complete!
                break;
            case 7:
                w = "Electrotherapy 2";//complete!
                break;
            case 8:
                w = "Bubble";//complete!
                break;
            case 9:
                w = "Harsh Sunlight";//complete!
                break;
            case 10:
                if(!madness)
                    w = "Pulsar";//complete!
                else
                    w = randomTimelessWeather();
                break;
            case 11:
                w = "Strikedown";//complete!
                break;
            case 12:
                w = "Exponential";//complete!
                break;
            case 13:
                w = "Raining Cats and Dogs";//complete!
                break;
            case 14:
                w = "Lightning Storm";//complete!
                break;
            case 15:
                w = "Hot Bench";//complete!
                break;
            case 16:
                w = "Brisk";//complete!
                break;
            case 17:
                w = "Superposition";//complete!
                break;
            case 18:
                w = "Space";//complete!
                break;
            case 19:
                w = "Electric Storm";//complete!
                break;
            case 20:
                w = "Geometry";//complete!
                break;
            case 21:
                w = "Error";//complete!
                break;
            case 22:
                w = "Faraday Field";//complete!
                break;
            case 23:
                w = "Earthquake";//complete!
                break;
            default:
                w = "";
                break;
        }
        if(weatherValid(w) || !madness)
            return w;
        else
            return randomTimelessWeather();
    }
    
    String randomAltWeather(){
        if(!madness)
            r = new Random((dayNum * 12345 + 12345) * season * teamA.favor * teamB.favor);
        //System.out.println(dayNum * season * teamA.favor * teamB.favor);
        int rand = (int)(r.nextDouble() * 24);//24
        //rand = 25;
        //System.out.println((dayNum * season * teamA.favor * teamB.favor) + " " + rand);
        String w;
        switch(rand){
            case 0:
                w = "Birds";
                break;
            case 1:
                w = "Sun .1";
                break;
            case 2:
                w = "Sun 2";
                break;
            case 3:
                w = "Black Hole";
                break;
            case 4:
                w = "Unsun";
                break;
            case 5:
                w = "Reverse";
                break;
            case 6:
                w = "Fever Pitch";//complete!
                break;
            case 7:
                w = "Electrotherapy 2";//complete!
                break;
            case 8:
                w = "Meownsoon";//complete!
                break;
            case 9:
                w = "Bubble";//complete!
                break;
            case 10:
                w = "Moon";//complete!
                break;
            case 11:
                if(!madness)
                    w = "Pulsar";//complete!
                else
                    w = randomAltWeather();
                break;
            case 12:
                w = "Raining Cats and Dogs";//complete!
                break;
            case 13:
                w = "Lightning Storm";//complete!
                break;
            case 14:
                w = "Hot Bench";//complete!
                break;
            case 15:
                w = "Duel";//complete!
                break;
            case 16:
                w = "Brisk";//complete!
                break;
            case 17:
                w = "Superposition";//complete!
                break;
            case 18:
                w = "Space";//complete!
                break;
            case 19:
                w = "Electric Storm";//complete!
                break;
            case 20:
                w = "Geometry";//complete!
                break;
            case 21:
                w = "Faraday Field";//complete!
                break;
            case 22:
                w = "Friendship";//complete!
                break;
            case 23:
                w = "Earthquake";//complete!
                break;
            default:
                w = "";
                break;
        }
        if(weatherValid(w) || !madness)
            return w;
        else
            return randomAltWeather();
    }

    boolean weatherValid(String w){
        if(w.equals("Hot Bench")){
            if(inning == 1){
                return true;
            }
            return false;
        }else if(w.equals("Duel")){
            if(inning == 1 || inning == 9){
                return true;
            }
            return false;
        }else if(w.equals("Friendship")){
            if(inning == 9){
                return true;
            }
            return false;
        }
        return true;
    }

    void printBirdMessage(){
        //Looking through all the bird messages here counts as spoilers, so keep it secret!
        int random = (int)(r.nextDouble() * 29);
        switch(random){
            case 0:
                println("Seeing a lot of birds today.");
                break;
            case 1:
                println("The birds continue to stare.");
                break;
            case 2:
                println("[BIRD NOISES]");
                break;
            case 3:
                println("Have you ever seen so many birds?");
                break;
            case 4:
                println("Several birds pluck a fan from the stands.");
                break;
            case 5:
                println("The Birds circle....but we don't have those snacks here.");
                break;
            case 6:
                println("The birds are mad at you. You specifically");
                break;
            case 7:
                println("These birds hate Blaseball!");
                break;
            case 8:
                println("The birds are hungry.");
                break;
            case 9:
                println("The birds stare into the sun.");
                break;
            case 10:
                println("The birds give you the shivers.");
                break;
            case 11:
                println("Birds.");
                break;
            case 12:
                println((int)(r.nextDouble() * 1000) + " birds.");
                break;
            case 13:
                println("Something isn't right about these birds.");
                break;
            case 14:
                println("A bird pecks the outfield for features.");
                break;
            case 15:
                println("A rogue umpire incinerated Bird " + (int)(r.nextDouble() * 1000) + "!");
                println("They're replaced by Bird " + (int)(r.nextDouble() * 1000) + ".");
                break;
            case 16:
                println("Is that a normal number of eyes for a bird?");
                break;
            case 17:
                println("Another bird lands in the rafters.");
                break;
            case 18:
                println("I hate these birds.");
                break;
            case 19:
                println("The birds' cries almost sound like music.");
                break;
            case 20:
                println("The birds form a blanket over the stadium, blocking out the sun.");
                break;
            case 21:
                println("The birds squak of death.");
                break;
            case 22:
                println("Too many birds.");
                break;
            case 23:
                //don't ruin the surprise for this one!
                Player player = randomActivePlayer();
                if(player.pregameRitual.equals("Eating a bird"))
                    println(player.name + " eats a bird!");
                else if(player.pregameRitual.equals("Eating multiple birds"))
                    println(player.name + " gobbles down an ungodly amounts of birds!");
                else if(player.pregameRitual.equals("Shaking their fist at a bird's nest"))
                    println(player.name + " REALLY hates birds!");
                else if(player.pregameRitual.equals("Birdwatching"))
                    println(player.name + " watches the birds.");
                else if(player.pregameRitual.equals("Feeding the birds"))
                    println("The birds feed off of " + player.name + ".");
                else
                    println(player.name + " is thinking about birds.");
                break;
            case 24:
                println("You spot a shiny bird! It looks back at you, annoyed.");
                break;
            case 25:
                println("The cacophony of bird calls makes you sick.");
                break;
            case 26:
                println("The birds stare at their favorite Keeper.");
                break;
            case 27:
                println("Birds Birds Birds Birds Birds Birds Birds Birds.");
                break;
            case 28:
                println("What if birds were real?");
                break;
        }
    }

    void printFaradayMessage(){
        int random = (int)(r.nextDouble() * 3);
        switch(random){
            case 0:
                println("The Bases spark.");
                break;
            case 1:
                println("It smells of ozone.");
                break;
            case 2:
                println("The crowd is electric!");
                break;
        }
    }
    
    void friendship(){
        int random = (int)(r.nextDouble() * 10);
        switch(random){
            case 0:
                println("The Gods are pleased with the beauty of Friendship. Everybody wins!");
                winsA = 2;
                winsB = 2;
                break;
            case 1:
                println("A Rogue Spaceship of Friends incinerates Scorekeeper " + (int)(r.nextDouble() * 1000) + ". Wins are unrecorded");
                winsA = 0;
                winsB = 0;
                break;
            default:
                println("The Scorekeepers applause the beauty of Friendship. Everybody wins!");
                winsA = 1;
                winsB = 1;
                break;
        }
    }

    Team randomTeam(){
        int random = (int)(r.nextDouble() * 3);
        switch(random){
            case 0:
                return teamA;
            case 1:default:
                return teamB;
        }
    }

    void printFeverMessage(){
        int random = (int)(r.nextDouble() * 29);
        switch(random){
            case 0:
                println("The Fans begin doing the Wave.");
                break;
            case 1:
                println("The Fans chant for the " + randomTeam().name + " in perfect unison.");
                break;
            case 2:
                println("FAN NOISES");
                break;
            case 3:
                println("Rogue Umpire incinerates Umpires Fan!");
                break;
            case 4:
                println("Tension fills the air as the Fans fall silent.");
                break;
            case 5:
                println("The Snackholders calculate tonight's income.");
                break;
            case 6:
                println("A staff member selling Hot Dogs passes by. They don't last long.");
                break;
            case 7:
                println("How many souls are there between the Fans?");
                break;
            case 8:
                println("The Fans stare at the pitcher's mound.");
                break;
            case 9:
                println("The Fans stare at the batting plate.");
                break;
            case 10:
                println("The Fans make eye contact with the Umpires.");
                break;
            case 11:
                println("The Fans compete with themselves over a ball found in the stands.");
                break;
            case 12:
                println("The Fans are loud!");
                break;
            case 13:
                println("A single Fan briefly considers going home.");
                break;
            case 14:
                println("The {FAVOUR OF THE CROWD} is palpable.");
                break;
            case 15:
                println("The Fans bicker over which team's pitcher is best.");
                break;
            case 16:
                println("The {FAVOUR OF THE CROWD} burns brightly. It's hard to see...");
                break;
            case 17:
                println("The Fans are mad at you. You specifically. You know who you are.");
                break;
            case 18:
                println("The Fans chant something beyond incomprehensible.");
                break;
        }
    }

    public String shortBaseName(int base){
        switch(base){
            case 0:
                return "First";
            case 1:
                return "Second";
            case 2:
                return "Third";
            case 3:default:
                return "Home";
        }
    }

    public void faradayStrike(Player p, int base){
        double rand = r.nextDouble();
        if(rand > 0.05)
            return;
        int random = (int)(r.nextDouble() * 2);
        switch(random){
            case 0://supercharge
                if(p.superCharged)
                    return;
                println(shortBaseName(base) + " Base Crackles, Supercharging " + p.name + "! ");
                p.superCharged = true;
                break;
            case 1://discharge
                if(p.superCharged)
                    print("Thunder booms! ");
                printlnInstant(shortBaseName(base) + " Base Discharges " + p.name + "! ");
                if(base > -1 && base < bases.length)
                    bases[base] = null;
                p.superCharged = false;
                outs++;
                out();
                break;
        }
    }

    public void geometricWalk(double distanceRun){
        boolean scored = false;
        //println(batter.name + " moved from 0.0 to " + distanceRun);
        if(distanceRun >= 2 * PI){
            score(batter);
            scored = true;
        }else
            radianBases.put(distanceRun,batter);
        if(scored)
            printScore();
    }

    public void rotateBases(double distanceRun){
        boolean scored = false;
        //println("distance is " + distanceRun);
        ArrayList<Map.Entry> positions = new ArrayList<Map.Entry>();
        for(Map.Entry entry : radianBases.entrySet())
            positions.add(entry);
        for(Map.Entry entry : positions){
            //println(((Player)entry.getValue()).name + " moved from " + (double)entry.getKey() + " to " + ((double)entry.getKey()+distanceRun));
            if((double)entry.getKey()+distanceRun >= 2 * PI){
                score((Player)entry.getValue());
                scored = true;
            }else
                radianBases.put((double)entry.getKey()+distanceRun,(Player)entry.getValue());
            radianBases.remove((double)entry.getKey());
        }
        //println(batter.name + " moved from 0.0 to " + distanceRun);
        if(distanceRun >= 2 * PI){
            score(batter);
            scored = true;
        }else
            radianBases.put(distanceRun,batter);
        if(scored)
            printScore();
    }

    public void errorOut(){
        double rand = r.nextDouble();
        if(rand <= 0.05){
            println("Weather error detected! Error is being corrected...");
        }
        rand = r.nextDouble();
        if(rand <= 0.09){
            println("Fanbase error detected! Error is being corrected..."); 
            println("TheMixedMaster has gained 1 new fan. ");
        }
    }

    public void errorHalfInning(){
        double rand = r.nextDouble();
        if(rand <= 0.1){
            println("Concessions error detected! Error is being corrected...");
            int random = (int)(r.nextDouble() * 5);
            switch(random){
                case 0:
                    println("SKITTLES now 75% off!");
                    break;
                case 1:
                    println("All peanuts are now loose!");
                    break;
                case 2:
                    println("Fans receive 1 free omelette!");
                    break;
                case 3:
                    println("All popcorn is now stale!");
                    break;
                case 4:
                    println("Concession stand has been closed!");
                    break;
            }
        }
    }

    public void errorEvent(){
        if(scoreWillFlip()){
            println("Error detected! Calculations inaccurate. The scoreboard has been corrected. Play resumes.");
            double temp = scoreA;
            scoreA = scoreB;
            scoreB = temp;
            printScore();
        }
        /*double rand = r.nextDouble();
        if(rand <= 0.05){
            println("Scoreboard error detected! Error is being corrected...");
            Team replace = null;
            do{
                replace = League.allTeams().get((int)(r.nextDouble() * League.allTeams().size()));
            }while(replace.equals(teamA) || replace.equals(teamB));
            Team randomTeam = randomTeam();
            randomTeam.name = replace.name;
            randomTeam.location = replace.location;
        }*/
    }

    public boolean scoreWillFlip(){
        double rand = r.nextDouble();
        if(inning == 9 && top && rand <= 0.19)
            return true;
        if(inning == 7 && top && rand <= 0.17)
            return true;
        if(rand <= 0.1)
            return true;
        return false;
    }

    public void shuffleBases(){
        ArrayList<Player> newBases = new ArrayList<Player>();
        for(int x = 0; x < bases.length; x++){
            newBases.add(bases[x]);
            //println("" + bases[x]);
        }
        ArrayList<Player> newNewBases = new ArrayList<Player>();
        while(newBases.size() > 0){
            int random = (int)(r.nextDouble() * newBases.size());
            newNewBases.add(newBases.get(random));
            newBases.remove(random);
        }
        for(int x = 0; x < bases.length; x++){
            bases[x] = newNewBases.get(x);
            //println("" + bases[x]);
        }
        println("The bases are shuffled!");
    }

    public void spaceOut(){
        double rand = r.nextDouble();
        if(rand > 0.05)
            return;
        println(pitcher.name + " has Spaced Out!");
        if(top){
            pitcher = nextAlphabetically(pitcher, teamA);
        }else{
            pitcher = nextAlphabetically(pitcher, teamB);
        }
        println(pitcher.name + " takes over pitching!");
    }

    public Player nextAlphabetically(Player p, Team team){
        Player[] players = sortAlphabetically(team.getActivePlayers());
        for(int x = 0; x < players.length; x++){
            if(p.equals(players[x])){
                Player p2;
                if(x + 1 == players.length){
                    p2 = players[0];
                }else{
                    p2 = players[x+1];
                }
                if(p2.elsewhere)
                    return nextAlphabetically(p2,team);
                else
                    return p2;
            }
        }
        return p;
    }

    public Player[] sortAlphabetically(Player[] players){            
        for(int x = 0; x < players.length; x++){
            for(int y = 0; y < players.length - x - 1; y++){
                if(AhigherThanB(players[y],players[y+1])){
                    Player temp = players[y];
                    players[y] = players[y+1];
                    players[y+1] = temp;
                }
            }
        }
        return players;
    }

    public boolean AhigherThanB(Player a, Player b){//alphabetically; z is higher than a
        String nameA = a.name.toLowerCase();
        String nameB = b.name.toLowerCase();
        for(int x = 0; x < nameA.length(); x++){
            if(nameB.length() <= x)
                return false;
            if(nameA.charAt(x) > nameB.charAt(x))
                return true;
            else if(nameA.charAt(x) < nameB.charAt(x)){
                return false;
            }
        }
        return true;
    }

    public void pixelize(){
        printlnC("The fine detail of players is lost");
        for(Player p : activePlayers()){
            p.tempBatting = roundToNearestOddNumber(p.batting) - p.batting;
            p.tempPitching = roundToNearestOddNumber(p.pitching) - p.pitching;
            p.tempBaserunning = roundToNearestOddNumber(p.baserunning) - p.baserunning;
            p.tempDefense = roundToNearestOddNumber(p.defense) - p.defense;
        }
    }

    public static int roundToNearestOddNumber(double num){
        return 2 * (int)Math.floor(num/2)+1;
    }

    public void lightningStrike(){
        double rand = r.nextDouble();
        if(rand > 0.33)
            return;
        int random = (int)(r.nextDouble() * 3);
        switch(random){
            case 0:
                if(strikes < 1)
                    println("The Lightning strikes, but nothing happens!");
                else{
                    println("The Lightning zaps a strike away!");
                    strikes--;
                }
                break;
            case 1:
                if(balls < 1)
                    println("The Lightning strikes, but nothing happens!");
                else{
                    println("The Lightning zaps a ball away!");
                    balls--;
                }
                break;
            case 2:
                random = (int)(r.nextDouble() * bases.length);
                switch(random){//continue here
                    case 0:
                        if(bases[0] == null)
                            println("The Lightning strikes, but nothing happens!");
                        else{
                            println("The Lightning Strikes First Base! " + bases[0].name + " flees to safety");
                            bases[0] = null;
                        }
                        break;
                    case 1:
                        if(bases[1] == null)
                            println("The Lightning strikes, but nothing happens!");
                        else{
                            println("The Lightning Strikes Second Base! " + bases[1].name + " flees to safety");
                            bases[1] = null;
                        }
                        break;
                    case 2:
                        if(bases[2] == null)
                            println("The Lightning strikes, but nothing happens!");
                        else{
                            println("The Lightning Strikes Third Base! " + bases[2].name + " flees to safety");
                            bases[2] = null;
                        }
                        break;
                }
                break;
        }
    }

    public void electricStrike(){
        double rand = r.nextDouble();
        if(rand > 0.1)
            return;
        int random = (int)(r.nextDouble() * 20);
        switch(random){
            case 0:case 1:case 2:case 3:case 4:case 5:case 6:case 7:case 8:
                random = (int)(r.nextDouble() * 2);
                switch(random){
                    case 0:
                        println("Lighting strike the mound! A ball is zapped away!");
                        break;
                    case 1:
                        println("Ball Lightning rolls through the outfield. A ball is zapped away.");
                        break;
                }
                balls--;
                break;
            case 9:case 10:case 11:case 12:case 13:case 14:case 15:case 16:case 17:
                random = (int)(r.nextDouble() * 2);
                switch(random){
                    case 0:
                        println("Lightning, Very Very Frightening! A strike is zapped away!");
                        break;
                    case 1:
                        println(batter.name + " is Thunder Struck! A strike is zapped away!");
                        break;
                }
                strikes--;
                break;
            default:
                println("Can't you hear, hear the thunder? A Run is zapped away");
                if(top)
                    scoreB--;
                else
                    scoreA--;
                printScore();
                break;
        }
    }

    public void startDuel(){
        Player playerA = randomActivePlayer(teamA);
        Player playerB = randomActivePlayer(teamB);
        Player[] arrA = new Player[1];
        arrA[0] = playerA;
        Player[] arrB = new Player[1];
        arrB[0] = playerB;
        teamA.lineup = arrA;
        teamA.rotation = arrA;
        teamB.lineup = arrB;
        teamB.rotation = arrB;
        println(playerA.name + " challenges " + playerB.name + " to a duel!");
    }

    public void moonEvent(){
        double rand = r.nextDouble();
        if(rand > 1.0/3.0 || playersPresent(pitchingTeam) < 2)
            return;
        Player p = randomPresentDefender();
        p.elsewhere = true;
        println("Moon shines upon " + p.name + "."); 
        println(p.name + " disappears from the field!");
    }

    public void popBubble(){
        if(bubbleScore < 0){
            scoreA+=1;
            println("The Bubble pops negative! The " + teamA.name + " score!");
        }else if(bubbleScore > 0){
            scoreB+=1;
            println("The Bubble pops positive! The " + teamB.name + " score!");
        }
        bubbleScore = 0;
    }

    public void normalStatChange(){
        double rand = r.nextDouble();
        if(rand > 0.25)
            return;
        int random = (int)(r.nextDouble() * 10);
        double statBoost = r.nextGaussian();
        String s;
        if(statBoost > 0)
            s = "boosted";
        else
            s = "impaired";
        switch(random){
            case 0://pitcher
                Player p;
                if(top)
                    p = pitcherA;
                else
                    p = pitcherB;
                p.tempPitching+=statBoost;
                println(batter.name + "'s pitching is " + s + " by " + ((double)(int)(Math.abs(statBoost) * 10)/10) + " stars. They are feeling Normal.");
                break;
            default://batter
                random = (int)(r.nextDouble() * 3);
                switch(random){
                    case 0://batting
                        batter.tempBatting+=statBoost;
                        println(batter.name + "'s batting is " + s + " by " + ((double)(int)(Math.abs(statBoost) * 10)/10) + " stars. They are feeling Normal.");
                        break;
                    case 1://baserunning
                        batter.tempBaserunning+=statBoost;
                        println(batter.name + "'s baserunning is " + s + " by " + ((double)(int)(Math.abs(statBoost) * 10)/10) + " stars. They are feeling Normal.");
                        break;
                    case 2://defense
                        batter.tempDefense+=statBoost;
                        println(batter.name + "'s defense is " + s + " by " + ((double)(int)(Math.abs(statBoost) * 10)/10) + " stars. They are feeling Normal.");
                        break;
                }
                break;
        }
    }

    public void preOutCatEvent(){
        double rand = r.nextDouble();
        if(rand > 0.33)
            return;
        int random = (int)(r.nextDouble() * 4);
        Player p;
        Team t;
        switch(random){
            case 0://batter
                p = batter;
                t = battingTeam;
                if(!p.cat)
                    return;
                random = (int)(r.nextDouble() * 5);
                switch(random){
                    case 0:
                        println(p.name + " leaps into the stands and steals a fan's sunflower seeds! Their pitching improves slightly.");
                        p.tempBatting+=0.5;
                        break;
                    case 1:
                        println(p.name + " leaps into the stands and steals a fan's hot dog! Their pitching improves drastically.");
                        p.tempBatting+=1.5;
                        break;
                }
                break;
            case 1://pitcher
                t = pitchingTeam;
                if(top)
                    p = pitcherA;
                else
                    p = pitcherB;
                if(!p.cat)
                    return;
                random = (int)(r.nextDouble() * 4);
                switch(random){
                    case 0:
                        println(p.name + " leaps into the stands and steals a fan's chips! Their pitching improves slightly.");
                        p.tempPitching+=0.5;
                        break;
                    case 1:
                        println(p.name + " leaps into the stands and steals a fan's sunflower seeds! Their pitching improves drastically.");
                        p.tempPitching+=1.5;
                        break;
                }
                break;
            case 2://baserunner
                t = battingTeam;
                p = randomCatOnBase();
                if(p == null)
                    return;
                random = (int)(r.nextDouble() * 4)+1;
                switch(random){
                    case 1:
                        println(p.name + " leaps into the stands and steals a fan's slushie! They get a brain freeze, and are swept off base in a frenzy!");
                        removePlayerFromBases(p);
                        break;
                }
                break;
            case 3:default://defender
                t = pitchingTeam;
                p = randomCatDefender();
                random = (int)(r.nextDouble() * 3)+2;
                break;
        }
        switch(random){
            case 2:
                if(p == null)
                    break;
                println(p.name + " leaps into the stands and steals a fan's peanuts! They taste the Infinite, and purr back!");
                break;
            case 3:
                if(p == null)
                    break;
                println("Rogue Umpire incinerates " + p.name + "! " + p.name + " loses a life! The Umpire is incinerated for being a dog person!");
                break;
            case 4:
                if(p == null)
                    break;
                println(p.name + " leaps into the stands and steals a fan's breakfast! They take a catnap, and don't wake up for the rest of the game!");
                removePlayerFromBases(p);
                if(t.lineup.length > 1)
                    p.elsewhere = true;
                    
                else{
                    println("VORTEX RUMBLES.");
                    println(p.name + " wakes up.");
                }
                break;
        }
    }

    public void catEvent(){
        if(!top && inning == 7){
            println("It's the middle of the seventh! The cats stretch!");
        }
        int random = (int)(r.nextDouble() * 20);
        if((inning < 4 || (inning == 4 && top)) && random == 1)
            random = 2;
        int rand = (int)(r.nextDouble() * 2);
        Team team;
        switch(rand){
            case 0:
                team = teamA;
                break;
            case 1:default:
                team = teamB;
                break;
        }
        switch(random){
            case 0:
                if(!team.name.equals("Cats"))
                    println("The Meownsoon Blows! The " + team.getTeamName() + " have become the " + team.location + " Cats!");
                else
                    println("The Meownsoon Blows! The " + team.getTeamName() + " are stil Cats!");
                team.name = "Cats";
                for(Player p : team.getActivePlayers()){
                    p.cat = true;
                }
                break;
            case 1:
                print("The Meownsoon rains catnip onto the field! All of the cats become");
                random = (int)(r.nextDouble() * 2);
                switch(random){
                    case 0:
                        printlnInstant(" wilder.");
                        for(Player p : activePlayers()){
                            if(p.cat){
                                p.tempBatting += p.batting + p.tempBatting;
                                p.tempPitching += p.pitching + p.tempPitching;
                                p.tempBaserunning += p.baserunning + p.tempBatting;
                                p.tempDefense += p.defense + p.tempDefense;
                            }
                        }
                        break;
                    case 1:default:
                        printlnInstant(" milder.");
                        for(Player p : activePlayers()){
                            if(p.cat){
                                p.tempBatting = (p.batting + p.tempBatting) / 2 - p.batting;
                                p.tempPitching = (p.pitching + p.tempPitching) / 2 - p.pitching;
                                p.tempBaserunning = (p.baserunning + p.tempBatting) / 2 - p.baserunning;
                                p.tempDefense = (p.defense + p.tempDefense) / 2 - p.defense;
                            }
                        }
                        break;
                }
                break;
            default:
                Player p = randomActivePlayer(team);
                if(!p.cat)
                    println("The Meownsoon Blows! " + p.name + " becomes a cat!");
                else
                    println("The Meownsoon Blows! But " + p.name + " is already a cat!");
                p.cat = true;
                break;
        }
    }

    public Player randomCatDefender(){
        Team team = pitchingTeam;
        Player[] a = team.lineup;
        ArrayList<Player> players = new ArrayList<Player>();
        for(Player p : a){
            if(p.cat && !p.elsewhere)
                players.add(p);
        }
        if(players.size() == 0)
            return null;
        int random = (int)(r.nextDouble() * players.size());
        return players.get(random);
    }

    public int playersPresent(Team t){
        int count = 0;
        for(Player p : t.lineup){
            if(!p.elsewhere)
                count++;
        }
        return count;
    }

    public boolean playersElsewhere(){
        for(Player p : activePlayers()){
            if(p.elsewhere)
                return true;
        }
        return false;
    }

    public Player randomPresentDefender(){
        Team team = pitchingTeam;
        Player[] a = team.lineup;
        ArrayList<Player> players = new ArrayList<Player>();
        for(Player p : a){
            if(!p.elsewhere)
                players.add(p);
        }
        if(players.size() == 0){
            //System.out.println("uh oh");
            return null;
        }
        int random = (int)(r.nextDouble() * players.size());
        return players.get(random);
    }

    public Player randomCatOnBase(){
        ArrayList<Player> players = new ArrayList<Player>();
        for(Player p : bases){
            if(p != null && p.cat)
                players.add(p);
        }
        if(players.size() == 0)
            return null;
        int random = (int)(r.nextDouble() * players.size());
        return players.get(random);
    }

    public void printElectroMessage(){
        int random = (int)(r.nextDouble() * 3);
        switch(random){
            case 0:
                println("The air crackles with Electricity");
                break;
            case 1:
                println("Everyones hair stands on end");
                break;
            case 2:
                println("Static builds...");
                break;
        }
    }

    public void electroBoost(Player p){
        boolean multiply = r.nextBoolean();
        double random = (r.nextDouble() * 1.99) + 0.01;
        //println("" + (p.batting + p.tempBatting));
        double display = (int)(random * 100)/100.0;
        double old = p.getBatting();
        double stars;
        if(multiply){
            print(p.name + "'s arms tense! They multiply their batting stars by " + display + "! ");
            stars = p.getBatting() * random;
            p.tempBatting = stars - p.batting;
        }else{
            print(p.name + "'s arms go limp! They divide their batting stars by " + display + "! ");
            stars = p.getBatting() / random;
            p.tempBatting = stars - p.batting;
        }
        printlnInstant("(" + p.roundStat(old) + " -> " + p.roundStat(p.getBatting()) + ")");
        //println("" + (p.batting + p.tempBatting));
    }

    public void feverPitch(){
        //println("fever pitch");
        double rand = r.nextDouble();
        if(rand > 0.02)
            return;
        int random = (int)(r.nextDouble() * 2);
        Team team;
        switch(random){
            case 0:
                team = teamA;
                break;
            case 1:default:
                team = teamB;
                break;
        }
        Player fan = new Player(team.name + " Fan");
        fan.originalTeam = team;
        fan.fan = true;
        int base = randomUnoccupiedBase();
        if(base == -1){
            println(fan.name + " has entered the field! But the bases are loaded...");
            println("Rogue umpire incinerated " + fan.name + "!");
            return;
        }
        bases[base] = fan;
        String s = fan.name + " has entered the field! They've stolen ";
        switch(base){
            case 0:
                s+="1st base!";
                break;
            case 1:
                s+="2nd base!";
                break;
            case 2:
                s+="3rd base!";
                break;
            case 3:
                s+="4th base!";
                break;
            default:
                s+= (base+1) + "th base!";
                break;
        }
        println(s);
    }

    public int randomUnoccupiedBase(){
        ArrayList<Integer> unoccupied = new ArrayList<Integer>();
        for(int x = 0; x < bases.length; x++){
            if(bases[x] == null)
                unoccupied.add(x);
        }
        if(unoccupied.size() == 0)
            return -1;
        return unoccupied.get((int)(r.nextDouble() * unoccupied.size()));
    }

    public Player randomActivePlayer(Team team){
        Player[] players = team.getActivePlayers();
        int random = (int)(r.nextDouble() * players.length);
        return players[random];
    }

    public Player randomActivePlayer(){
        Player[] players = activePlayers();
        int random = (int)(r.nextDouble() * players.length);
        return players[random];
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

    public Player[] removePlayer(Player[] lineup, Player p){
        ArrayList<Player> players = new ArrayList<Player>();
        for(Player player : lineup){
            if(!player.equals(p)){
                players.add(player);
            }
        }
        Player[] arr = new Player[players.size()];
        for(int x = 0; x < players.size(); x++){
            arr[x] = players.get(x);
        }
        return arr;
    }

    public Player[] copy(Player[] p){
        Player[] players = new Player[p.length];
        for(int x = 0; x < p.length; x++){
            players[x] = p[x];
        }
        return players;
    }
}
