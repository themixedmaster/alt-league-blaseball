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
<<<<<<< Updated upstream
    public double tempDefense = 0;

    public double aggression;
    public double arrogance;
    public double carcinization;
    public double damage;
    public double density;
    public double dexterity;
    public double dimensions;
    public double effort;
    public double focus;
    public double fun;
    public double grit;
    public double hitPoints;
    public double malleability;
    public double mathematics;
    public double numberOfEyes;
    public double pinpointedness;
    public double powder;
    public double rejection;
    public double splash;
    public double wisdom;
=======
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
    public Stat grit;          //pitching; makes batters more likely to strike, looking.
    public Stat hitPoints;     //baserunning; ability to avoid ground outs
    public Stat malleability;  //batting; decreases chance to strike, swinging
    public Stat mathematics;   //defense; increaes chance to catch a flyout
    public Stat numberOfEyes;  //batting; increases chance to predict the pitcher's throw.
    public Stat pinpointedness;//pitching; increases accuracy, lower chance to miss strike zone
    public Stat powder;        //pitching; increases chance for batter to hit a foul ball
    public Stat rejection;     //defense; makes players less likely to attempt base-steals
    public Stat splash;        //batting; decreases chance of hitting fouls when hitting the ball.
    public Stat wisdom;        //defense; decreases chance of successful base steals
>>>>>>> Stashed changes

    String pregameRitual;
    String coffeeStyle;
    String bloodType;
    int fate;
    String soulscream;
    public int id;
<<<<<<< Updated upstream
    public boolean fan = false; //for altalt fever weather
    public boolean cat = false; //for altalt meownsoon weather
    public boolean elsewhere = false; //for altalt moon weather
    public boolean superCharged = false; //for faraday field weather
=======
    HashMap<String,Double> statistics; //stuff like balls hit, number of pitches, etc

    ArrayList<String> mods;
    //public boolean fan = false; //for altalt fever weather
    //public boolean cat = false; //for altalt meownsoon weather
    //public boolean elsewhere = false; //for altalt moon weather
    //public boolean superCharged = false; //for faraday field weather
>>>>>>> Stashed changes
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
        
        this.batting = (density + numberOfEyes / 2 + focus / 4 + malleability / 8 + splash * 3 / 4 + aggression * 3 / 32) / 2.28125;
        this.pitching = (pinpointedness + fun / 2 + grit / 4 + dimensions / 8 + powder * 3 / 16) / 2.1875;
        this.baserunning = (hitPoints + effort / 2 + arrogance / 20 + dexterity / 40) / 1.575;
        this.defense = (mathematics + damage / 2 + carcinization / 4 + rejection / 20 + wisdom / 40) / 1.825;
        
        this.pregameRitual = pregameRitual;
        this.coffeeStyle = coffeeStyle;
        this.bloodType = bloodType;
        this.fate = fate;
        this.soulscream = soulscream;
<<<<<<< Updated upstream
=======
        statistics = new HashMap<String,Double>();
        mods = new ArrayList<String>();
        PlayerMaker.addFlavor(this);
        id = League.nextID();
    }

    public void recalculateBaseStats(){
        this.batting = (density.baseValue() + numberOfEyes.baseValue() + focus.baseValue() + malleability.baseValue() + splash.baseValue() + aggression.baseValue() ) / 6;
        this.pitching = (pinpointedness.baseValue() + fun.baseValue() + grit.baseValue() + dimensions.baseValue() + powder.baseValue()) / 5;
        this.baserunning = (hitPoints.baseValue() + effort.baseValue() + arrogance.baseValue() + dexterity.baseValue() ) / 4;
        this.defense = (mathematics.baseValue() + damage.baseValue() + carcinization.baseValue() + rejection.baseValue() + wisdom.baseValue() ) / 5;
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
    
    void multiplyStats(double multiple){
        aggression.multiplyBoost(multiple);
        density.multiplyBoost(multiple);
        focus.multiplyBoost(multiple);
        malleability.multiplyBoost(multiple);
        numberOfEyes.multiplyBoost(multiple);
        splash.multiplyBoost(multiple);
        dimensions.multiplyBoost(multiple);
        fun.multiplyBoost(multiple);
        grit.multiplyBoost(multiple);
        pinpointedness.multiplyBoost(multiple);
        powder.multiplyBoost(multiple);
        arrogance.multiplyBoost(multiple);
        dexterity.multiplyBoost(multiple);
        effort.multiplyBoost(multiple);
        hitPoints.multiplyBoost(multiple);
        carcinization.multiplyBoost(multiple);
        damage.multiplyBoost(multiple);
        mathematics.multiplyBoost(multiple);
        rejection.multiplyBoost(multiple);
        wisdom.multiplyBoost(multiple);
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
        //System.out.println(mod);
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
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
=======
        printMods();
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
=======
    public void printMods(){
        if(mods.size() > 0){
            System.out.println("Mods:");
        }
        for(String s : mods){
            System.out.println("  " + s + " - " + modDescription(s));
        }
    }
    
    public String modDescription(String s){
        if(s.equals("Cat"))
            return "Meow.";
        return "This player is " + s + ".";
    }
    
>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
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
=======
        printStat(aggression.baseValue());
        System.out.println();
        System.out.print("Arrogance       ");
        printStat(arrogance.baseValue());
        System.out.println();
        System.out.print("Carcinization   ");
        printStat(carcinization.baseValue());
        System.out.println();
        System.out.print("Damage          ");
        printStat(damage.baseValue());
        System.out.println();
        System.out.print("Density         ");
        printStat(density.baseValue());
        System.out.println();
        System.out.print("Dexterity       ");
        printStat(dexterity.baseValue());
        System.out.println();
        System.out.print("Dimensions      ");
        printStat(dimensions.baseValue());
        System.out.println();
        System.out.print("Effort          ");
        printStat(effort.baseValue());
        System.out.println();
        System.out.print("Focus           ");
        printStat(focus.baseValue());
        System.out.println();
        System.out.print("Fun             ");
        printStat(fun.baseValue());
        System.out.println();
        System.out.print("Grit            ");
        printStat(grit.baseValue());
        System.out.println();
        System.out.print("Hit Points      ");
        printStat(hitPoints.baseValue());
        System.out.println();
        System.out.print("Malleability    ");
        printStat(malleability.baseValue());
        System.out.println();
        System.out.print("Mathematics     ");
        printStat(mathematics.baseValue());
        System.out.println();
        System.out.print("Number of Eyes  ");
        printStat(numberOfEyes.baseValue());
        System.out.println();
        System.out.print("Pinpointedness  ");
        printStat(pinpointedness.baseValue());
        System.out.println();
        System.out.print("Powder          ");
        printStat(powder.baseValue());
        System.out.println();
        System.out.print("Rejection       ");
        printStat(rejection.baseValue());
        System.out.println();
        System.out.print("Splash          ");
        printStat(splash.baseValue());
        System.out.println();
        System.out.print("Wisdom          ");
        printStat(wisdom.baseValue());
>>>>>>> Stashed changes
        System.out.println();
    }
}
