import java.util.*;
public class Player
{
    String name;
    public double batting;
    public double pitching;
    public double baserunning;
    public double defense;
                               //ILLEGAL KNOWLEDGE; for devs only
    public Stat aggression;    //batting; ability to avoid flyouts
    public Stat arrogance;     //baserunning; increases likelihood to attempt base-steal
    public Stat carcinization; //defense; ability to prevent additional bases being runs
    public Stat damage;        //defense; ability to ground out batters
    public Stat density;       //batting; higher density = smaller strike zone
    public Stat dexterity;     //baserunning; how good a player is at stealing bases
    public Stat dimensions;    //pitching; makes batters more likely to strike, swinging
    public Stat effort;        //baserunning; ability to run additional bases
    public Stat focus;         //batting; increaes likelihood to swing, avoid strike, looking.
    public Stat fun;           //pitching; decreases chance for batter to predict the pitch
    public Stat grit;          //pitching; makes batters more likely to strike, swinging.
    public Stat hitPoints;     //baserunning; ability to avoid ground outs
    public Stat malleability;  //batting; decreases chance to strike, swinging
    public Stat mathematics;   //defense; increaes chance to catch a flyout
    public Stat numberOfEyes;  //batting; increases chance to predict the pitcher's throw.
    public Stat pinpointedness;//pitching; increases accuracy, lower chance to miss strike zone
    public Stat powder;        //pitching; increases chance for batter to hit a foul ball
    public Stat rejection;     //defense; makes players less likely to attempt base-steals
    public Stat splash;        //batting; decreases chance of hitting fouls when hitting the ball.
    public Stat wisdom;        //defense; decreases chance of successful base steals

    String pregameRitual;
    String coffeeStyle;
    String bloodType;
    int fate;
    String soulscream;
    public int id;
    HashMap<String,Double> statistics; //stuff like balls hit, number of pitches, etc

    ArrayList<String> mods;
    //public boolean fan = false; //for altalt fever weather
    //public boolean cat = false; //for altalt meownsoon weather
    public boolean elsewhere = false; //for altalt moon weather
    //public boolean superCharged = false; //for faraday field weather
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
        this.batting = (density.value() + numberOfEyes.value() + focus.value() + malleability.value() + splash.value() + aggression.value() ) / 6;
        this.pitching = (pinpointedness.value() + fun.value() + grit.value() + dimensions.value() + powder.value()) / 5;
        this.baserunning = (hitPoints.value() + effort.value() + arrogance.value() + dexterity.value() ) / 4;
        this.defense = (mathematics.value() + damage.value() + carcinization.value() + rejection.value() + wisdom.value() ) / 5;

        this.pregameRitual = pregameRitual;
        this.coffeeStyle = coffeeStyle;
        this.bloodType = bloodType;
        this.fate = fate;
        this.soulscream = soulscream;
        statistics = new HashMap<String,Double>();
        mods = new ArrayList<String>();
        PlayerMaker.addFlavor(this);
        id = League.nextID();
    }

    public void recalculateBaseStats(){
        this.batting = (density.value() + numberOfEyes.value() + focus.value() + malleability.value() + splash.value() + aggression.value() ) / 6;
        this.pitching = (pinpointedness.value() + fun.value() + grit.value() + dimensions.value() + powder.value()) / 5;
        this.baserunning = (hitPoints.value() + effort.value() + arrogance.value() + dexterity.value() ) / 4;
        this.defense = (mathematics.value() + damage.value() + carcinization.value() + rejection.value() + wisdom.value() ) / 5;
    }
    
    public void clearTemporaryStats(){
        aggression.removeBoost();
        arrogance.removeBoost();
        carcinization.removeBoost();
        damage.removeBoost();
        density.removeBoost();
        dexterity.removeBoost();
        dimensions.removeBoost();
        effort.removeBoost();
        focus.removeBoost();
        fun.removeBoost();
        grit.removeBoost();
        hitPoints.removeBoost();
        malleability.removeBoost();
        mathematics.removeBoost();
        numberOfEyes.removeBoost();
        pinpointedness.removeBoost();
        powder.removeBoost();
        rejection.removeBoost();
        splash.removeBoost();
        wisdom.removeBoost();
        recalculateBaseStats();
    }
    
    void boostBatting(double boost){
        aggression.addBoost(boost);
        density.addBoost(boost);
        focus.addBoost(boost);
        malleability.addBoost(boost);
        numberOfEyes.addBoost(boost);
        splash.addBoost(boost);

        recalculateBaseStats();
    }
    
    void boostPitching(double boost){
        dimensions.addBoost(boost);
        fun.addBoost(boost);
        grit.addBoost(boost);
        pinpointedness.addBoost(boost);
        powder.addBoost(boost);
        recalculateBaseStats();
    }
    
    void boostBaserunning(double boost){
        arrogance.addBoost(boost);
        dexterity.addBoost(boost);
        effort.addBoost(boost);
        hitPoints.addBoost(boost);
        recalculateBaseStats();
    }
    
    void boostDefense(double boost){
        carcinization.addBoost(boost);
        damage.addBoost(boost);
        mathematics.addBoost(boost);
        rejection.addBoost(boost);
        wisdom.addBoost(boost);
        recalculateBaseStats();
    }
    
    public String batMessage(Team team){
        String s = name + " batting for the " + team.getName();
        if(hasMod("Cat"))
            s = s + ". Meow.";
        return s;
    }
    
    public String walkMessage(){
        String s = name + " draws a walk.";
        if(hasMod("Cat"))
            s = s + " Meow.";
        return s;
    }
    
    public boolean equals(Player p){
        return id == p.id;
    }
    
    public void clearStatistics(){
        statistics = new HashMap<String,Double>();
    }

    public void addStatistic(String statistic){
        addStatistic(statistic, 1.0);
    }

    public void addMod(String mod){
        if(!hasMod(mod))
            mods.add(mod);
    }
    
    public boolean hasMod(String mod){
        return mods.contains(mod);
    }
    
    public void removeMod(String mod){
        mods.remove(mod);
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

    public static Stat randomUnweightedStat(){
        return new Stat(League.r.nextDouble() * 5);
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
        printMods();
        System.out.println("Elsewhere: " + elsewhere);
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

    public void printMods(){
        if(mods.size() > 0){
            System.out.println("Mods:");
        }
        for(String s : mods){
            System.out.println("  " + s + " - " + modDescription(s));
        }
    }
    
    public String modDescription(String s){
        //insert other cases here
        return "This player is " + s + ".";
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
        printStat(aggression.value());
        System.out.println();
        System.out.print("Arrogance       ");
        printStat(arrogance.value());
        System.out.println();
        System.out.print("Carcinization   ");
        printStat(carcinization.value());
        System.out.println();
        System.out.print("Damage          ");
        printStat(damage.value());
        System.out.println();
        System.out.print("Density         ");
        printStat(density.value());
        System.out.println();
        System.out.print("Dexterity       ");
        printStat(dexterity.value());
        System.out.println();
        System.out.print("Dimensions      ");
        printStat(dimensions.value());
        System.out.println();
        System.out.print("Effort          ");
        printStat(effort.value());
        System.out.println();
        System.out.print("Focus           ");
        printStat(focus.value());
        System.out.println();
        System.out.print("Fun             ");
        printStat(fun.value());
        System.out.println();
        System.out.print("Grit            ");
        printStat(grit.value());
        System.out.println();
        System.out.print("Hit Points      ");
        printStat(hitPoints.value());
        System.out.println();
        System.out.print("Malleability    ");
        printStat(malleability.value());
        System.out.println();
        System.out.print("Mathematics     ");
        printStat(mathematics.value());
        System.out.println();
        System.out.print("Number of Eyes  ");
        printStat(numberOfEyes.value());
        System.out.println();
        System.out.print("Pinpointedness  ");
        printStat(pinpointedness.value());
        System.out.println();
        System.out.print("Powder          ");
        printStat(powder.value());
        System.out.println();
        System.out.print("Rejection       ");
        printStat(rejection.value());
        System.out.println();
        System.out.print("Splash          ");
        printStat(splash.value());
        System.out.println();
        System.out.print("Wisdom          ");
        printStat(wisdom.value());
        System.out.println();
    }
}
