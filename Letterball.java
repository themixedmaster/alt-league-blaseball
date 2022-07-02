import java.util.ArrayList;

public class Letterball extends Weather
{
    ArrayList<Character> potentialLetters;
    ArrayList<Character> removedLetters;
    public Letterball(Game game){
        super(game);
        name = "Letterball";
        potentialLetters = new ArrayList<Character>();
        for(int x = 32; x < 127; x++)
            potentialLetters.add((char)x);
        removedLetters = new ArrayList<Character>();
    }
    
    public String alterEvent(String event){
        String s = "";
        char[] chars = event.toCharArray();
        for(int x = 0; x < chars.length; x++){
            char c = chars[x];
            if(!removedLetters.contains(c))
                s = s + c;
        }
        return s;
    }
    
    public void afterHit(){
        if(potentialLetters.size() == 0)
            return;
        int rand = (int)(r.nextDouble() * potentialLetters.size());
        char c = potentialLetters.get(rand);
        game.addEvent("It strikes the commentators' '" + c + "' away!");
        removedLetters.add(c);
        potentialLetters.remove(rand);
        if(r.nextDouble() <= 1.0/3.0){
            Player p = game.randomActivePlayer();
            String s = p.name;
            int pos;
            String s2 = "";
            switch((int)(r.nextDouble() * 2)){
                case 0:
                    pos = (int)(r.nextDouble() * (s.length() + 1));
                    for(int x = 0; x < s.length(); x++){
                        if(pos == x)
                            s2 += potentialLetters.get((int)(r.nextDouble() * potentialLetters.size()));
                        s2 += s.charAt(x);
                    }
                    if(pos == s.length())
                            s2 += potentialLetters.get((int)(r.nextDouble() * potentialLetters.size()));
                    break;
                case 1:
                    pos = (int)(r.nextDouble() * s.length());
                    for(int x = 0; x < s.length(); x++){
                        if(pos != x)
                            s2 += s.charAt(x);
                    }
                    break;
            }
            game.addEvent(p.name + "'s name is struck! They are now " + s2 + "!");
            p.name = s2;
            p.addStatistic("Name altered");
        }
        
        if(r.nextDouble() <= 1.0/12.0){
            Team t;
            switch((int)(r.nextDouble() * 2)){
                case 0:
                    t = game.teamA;
                    break;
                case 1: default:
                    t = game.teamB;
                    break;
            }
            String s;
            boolean place;
            switch((int)(r.nextDouble() * 2)){
                case 0:
                    place = true;
                    s = t.location;
                    break;
                case 1: default:
                    place = false;
                    s = t.name;
                    break;
            }
            int pos;
            String s2 = "";
            switch((int)(r.nextDouble() * 2)){
                case 0:
                    pos = (int)(r.nextDouble() * (s.length() + 1));
                    for(int x = 0; x < s.length(); x++){
                        if(pos == x)
                            s2 += potentialLetters.get((int)(r.nextDouble() * potentialLetters.size()));
                        s2 += s.charAt(x);
                    }
                    if(pos == s.length())
                            s2 += potentialLetters.get((int)(r.nextDouble() * potentialLetters.size()));
                    break;
                case 1:
                    pos = (int)(r.nextDouble() * s.length());
                    for(int x = 0; x < s.length(); x++){
                        if(pos != x)
                            s2 += s.charAt(x);
                    }
                    break;
            }
            String oldName = t.getTeamName();
            if(place)
                t.location = s2;
            else
                t.name = s2;
            game.addEvent("The " + oldName + " are struck! They are now the " + t.getTeamName() + "!");
            }
    }
}
