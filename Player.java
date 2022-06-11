import java.util.*;
public class Player
{
    String name;
    public double batting;
    public double tempBatting = 0;
    public double pitching;
    public double tempPitching = 0;
    public double baserunning;
    public double tempBaserunning = 0;
    public double defense;
    public double tempDefense = 0;
    //ILLEGAL KNOWLEDGE; for devs only
    public double aggression;    //batting; ability to avoid flyouts
    public double arrogance;     //baserunning; increases likelihood to attempt base-steal
    public double carcinization; //defense; ability to prevent additional bases being runs
    public double damage;        //defense; ability to ground out batters
    public double density;       //baserunning; higher density = smaller strike zone
    public double dexterity;     //baserunning; how good a player is at stealing bases
    public double dimensions;    //pitching; makes batters more likely to strike, swinging
    public double effort;        //baserunning; ability to run additional bases
    public double focus;         //batting; increaes likelihood to swing, avoid strike, looking.
    public double fun;           //pitching; decreases chance for batter to predict the pitch
    public double grit;          //pitching; makes batters more likely to strike, swinging.
    public double hitPoints;     //baserunning; ability to avoid ground outs
    public double malleability;  //batting; decreases chance to strike, swinging
    public double mathematics;   //defense; increaes chance to catch a flyout
    public double numberOfEyes;  //batting; increases chance to predict the pitcher's throw.
    public double pinpointedness;//pitching; increases accuracy, lower chance to miss strike zone
    public double powder;        //pitching; increases chance for batter to hit a foul ball
    public double rejection;     //defense; makes players less likely to attempt base-steals
    public double splash;        //batting; decreases chance of hitting fouls when hitting the ball.
    public double wisdom;        //defense; decreases chance of successful base steals

    String pregameRitual;
    String coffeeStyle;
    String bloodType;
    int fate;
    String soulscream;
    public int id;
    HashMap<String,Double> statistics; //stuff like balls hit, number of pitches, etc

    public boolean fan = false; //for altalt fever weather
    public boolean cat = false; //for altalt meownsoon weather
    public boolean elsewhere = false; //for altalt moon weather
    public boolean superCharged = false; //for faraday field weather
    public Team originalTeam; //used when a player is taken away from their team out of the league
    public Player()
    {
        this(RandomNameGenerator.randomNameWithDistribution() + " " + RandomNameGenerator.randomNameWithDistribution(RandomNameGenerator.random(1,5) + RandomNameGenerator.random(1,5)));
    }

    public String getName(){
        return name;
    }

    public Player(String name){
        this.name = name;

        aggression = randomUnweightedStat();
        arrogance = randomUnweightedStat();
        carcinization = randomUnweightedStat();
        damage = randomUnweightedStat();
        density = randomUnweightedStat();
        dexterity = randomUnweightedStat();
        dimensions = randomUnweightedStat();
        effort = randomUnweightedStat();
        focus = randomUnweightedStat();
        fun = randomUnweightedStat();
        grit = randomUnweightedStat();
        hitPoints = randomUnweightedStat();
        malleability = randomUnweightedStat();
        mathematics = randomUnweightedStat();
        numberOfEyes = randomUnweightedStat();
        pinpointedness = randomUnweightedStat();
        powder = randomUnweightedStat();
        rejection = randomUnweightedStat();
        splash = randomUnweightedStat();
        wisdom = randomUnweightedStat();

        /*this.batting = (density + numberOfEyes / 2 + focus / 4 + malleability / 8 + splash * 3 / 4 + aggression * 3 / 32) / 2.28125;
        this.pitching = (pinpointedness + fun / 2 + grit / 4 + dimensions / 8 + powder * 3 / 16) / 2.1875;
        this.baserunning = (hitPoints + effort / 2 + arrogance / 20 + dexterity / 40) / 1.575;
        this.defense = (mathematics + damage / 2 + carcinization / 4 + rejection / 20 + wisdom / 40) / 1.825;/**/
        this.batting = (density + numberOfEyes + focus + malleability + splash + aggression ) / 6;
        this.pitching = (pinpointedness + fun + grit + dimensions + powder) / 5;
        this.baserunning = (hitPoints + effort + arrogance + dexterity ) / 4;
        this.defense = (mathematics + damage + carcinization + rejection + wisdom ) / 5;

        this.pregameRitual = pregameRitual;
        this.coffeeStyle = coffeeStyle;
        this.bloodType = bloodType;
        this.fate = fate;
        this.soulscream = soulscream;
        statistics = new HashMap<String,Double>();
    }

    public void clearStatistics(){
        statistics = new HashMap<String,Double>();
    }

    public void addStatistic(String statistic){
        addStatistic(statistic, 1.0);
    }

    public void addStatistic(String statistic, double amount){
        if(statistics.containsKey(statistic))
            statistics.put(statistic,statistics.get(statistic) + amount);
        else
            statistics.put(statistic,amount);
    }

    public void printStatistics(){
        String longestKey = "";
        for(Map.Entry<String,Double> entry : statistics.entrySet())
            if(entry.getKey().length() > longestKey.length())
                longestKey = entry.getKey();
        int i = longestKey.length();
        for(Map.Entry<String,Double> entry : statistics.entrySet()){
            String value;
            double d = getStatistic(entry.getKey());
            if(d % 1 != 0)
                value = "" + d;
            else{
                value = "" + (int)d;
            }
            System.out.println("  " + addSpaces(entry.getKey(),i) + ": " + value);
        }
        System.out.println();
    }

    public double getStatistic(String statistic){
        try{
            return statistics.get(statistic);
        }catch(NullPointerException e){
            return 0;
        }
    }

    public static String addSpaces(String s, int length){
        while(s.length() < length){
            s = " " + s;
        }
        return s;
    }

    public void setFlavor(String pregameRitual, String coffeeStyle, String bloodType, int fate, String soulscream){
        this.pregameRitual = pregameRitual;
        this.coffeeStyle = coffeeStyle;
        this.bloodType = bloodType;
        this.fate = fate;
        this.soulscream = soulscream;
    }

    public static double randomStat(){
        return League.r.nextDouble() * 2.5 + League.r.nextDouble() * 2.5;
    }

    public static double randomUnweightedStat(){
        return League.r.nextDouble() * 5;
    }

    public double getBatting(){
        return batting + tempBatting;
    }

    public double totalStars(){
        return batting + pitching + baserunning + defense;
    }

    public void printPlayer(String stat){
        System.out.print(name + " ");
        if(stat.equals("batting"))
            printStat(batting);
        else if(stat.equals("pitching"))
            printStat(pitching);
        System.out.println();
    }

    public static double roundStat(double stat){
        return (int)(stat * 10) / 10.0;
    }

    public void printStat(double stars){
        stars = roundStat(stars);
        for(int x = 1; x <= stars; x++){
            System.out.print("★");
        }
        if(stars % 1 >= 0.5){
            System.out.print("☆");
        }
        System.out.print(" (" + stars + ")");
    }

    public void printPlayer(int mode){
        System.out.println(name);
        printStats();
        System.out.println("Pregame Ritual  " + pregameRitual);
        System.out.println("Coffee Style    " + coffeeStyle);
        System.out.println("Blood Type      " + bloodType);
        System.out.println("Fate            " + fate);
        System.out.println("Soulscream      " + soulscream);
        if(mode == 1)
            printAdvancedStats();
        System.out.println();
    }

    public void printStats(){
        System.out.print("Batting         ");
        printStat(batting);
        System.out.println();
        System.out.print("Pitching        ");
        printStat(pitching);
        System.out.println();
        System.out.print("Baserunning     ");
        printStat(baserunning);
        System.out.println();
        System.out.print("Defense         ");
        printStat(defense);
        System.out.println();
    }

    public void printAdvancedStats(){
        System.out.print("Aggression      ");
        printStat(aggression);
        System.out.println();
        System.out.print("Arrogance       ");
        printStat(arrogance);
        System.out.println();
        System.out.print("Carcinization   ");
        printStat(carcinization);
        System.out.println();
        System.out.print("Damage          ");
        printStat(damage);
        System.out.println();
        System.out.print("Density         ");
        printStat(density);
        System.out.println();
        System.out.print("Dexterity       ");
        printStat(dexterity);
        System.out.println();
        System.out.print("Dimensions      ");
        printStat(dimensions);
        System.out.println();
        System.out.print("Effort          ");
        printStat(effort);
        System.out.println();
        System.out.print("Focus           ");
        printStat(focus);
        System.out.println();
        System.out.print("Fun             ");
        printStat(fun);
        System.out.println();
        System.out.print("Grit            ");
        printStat(grit);
        System.out.println();
        System.out.print("Hit Points      ");
        printStat(hitPoints);
        System.out.println();
        System.out.print("Malleability    ");
        printStat(malleability);
        System.out.println();
        System.out.print("Mathematics     ");
        printStat(mathematics);
        System.out.println();
        System.out.print("Number of Eyes  ");
        printStat(numberOfEyes);
        System.out.println();
        System.out.print("Pinpointedness  ");
        printStat(pinpointedness);
        System.out.println();
        System.out.print("Powder          ");
        printStat(powder);
        System.out.println();
        System.out.print("Rejection       ");
        printStat(rejection);
        System.out.println();
        System.out.print("Splash          ");
        printStat(splash);
        System.out.println();
        System.out.print("Wisdom          ");
        printStat(wisdom);
        System.out.println();
    }
}
