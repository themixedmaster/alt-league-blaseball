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

    String pregameRitual;
    String coffeeStyle;
    String bloodType;
    int fate;
    String soulscream;
    public int id;
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
        
        this.batting = (density + numberOfEyes / 2 + focus / 4 + malleability / 8 + splash * 3 / 4 + aggression * 3 / 32) / 2.28125;
        this.pitching = (pinpointedness + fun / 2 + grit / 4 + dimensions / 8 + powder * 3 / 16) / 2.1875;
        this.baserunning = (hitPoints + effort / 2 + arrogance / 20 + dexterity / 40) / 1.575;
        this.defense = (mathematics + damage / 2 + carcinization / 4 + rejection / 20 + wisdom / 40) / 1.825;
        
        this.pregameRitual = pregameRitual;
        this.coffeeStyle = coffeeStyle;
        this.bloodType = bloodType;
        this.fate = fate;
        this.soulscream = soulscream;
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
