import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.*;

public class JsonTester
{
    public static void main(String[] args){
        League.recapSeason3();
        createJson1();
        /*League.recapSeason1();
        createJson(2);
        League.recapSeason2();
        createJson(3);
        League.recapSeason3();
        createJson(4);*/
    }

    public static void createJson(int season){
        int pID = 0;
        int tID = 0;
        JSONObject json = new JSONObject();
        JSONArray players = new JSONArray();
        JSONArray teams = new JSONArray();
        for(Team t : League.getTeams()){
            JSONObject team = new JSONObject();
            team.put("name",t.getName());
            team.put("location",t.location);
            team.put("logo",t.logo);
            team.put("abbreviation",t.abbreviation);
            team.put("abbreviation",t.abbreviation);
            team.put("favor",t.favor);
            team.put("id",tID);
            ArrayList<Integer> lineupIDs = new ArrayList<Integer>();
            ArrayList<Integer> rotationIDs = new ArrayList<Integer>();
            for(Player p : t.lineup){
                lineupIDs.add(p.id);
            }
            for(Player p : t.rotation){
                rotationIDs.add(p.id);
            }
            team.put("lineup",lineupIDs);
            team.put("rotation",rotationIDs);
            tID++;
            teams.add(team);
        }

        json.put("players",players);
        json.put("teams",teams);

        try(FileWriter file = new FileWriter("season" + season + ".json")){
            file.write(json.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace(); //i have no idea what this means but the JSON guide did it so here it is
        }
    }

    public static void createJson1(){
        int pID = 0;
        int tID = 0;
        JSONObject json = new JSONObject();
        JSONArray players = new JSONArray();
        JSONArray teams = new JSONArray();
        JSONArray deceaed = new JSONArray();
        JSONArray birdNest = new JSONArray();
        for(Team t : League.getTeams()){
            JSONObject team = new JSONObject();
            team.put("name",t.getName());
            team.put("location",t.location);
            team.put("logo",t.logo);
            team.put("abbreviation",t.abbreviation);
            team.put("favor",t.favor);
            team.put("id",tID);
            t.id = tID;
            ArrayList<Integer> lineupIDs = new ArrayList<Integer>();
            ArrayList<Integer> rotationIDs = new ArrayList<Integer>();
            for(Player p : t.lineup){
                JSONObject player = new JSONObject();
                player.put("id",pID);
                p.id = pID;
                player.put("name",p.name);
                player.put("aggression",p.aggression.baseValue());
                player.put("arrogance",p.arrogance.baseValue());
                player.put("carcinization",p.carcinization.baseValue());
                player.put("damage",p.damage.baseValue());
                player.put("density",p.density.baseValue());
                player.put("dexterity",p.dexterity.baseValue());
                player.put("dimensions",p.dimensions.baseValue());
                player.put("effort",p.effort.baseValue());
                player.put("focus",p.focus.baseValue());
                player.put("fun",p.fun.baseValue());
                player.put("grit",p.grit.baseValue());
                player.put("hitPoints",p.hitPoints.baseValue());
                player.put("malleability",p.malleability.baseValue());
                player.put("mathematics",p.mathematics.baseValue());
                player.put("numberOfEyes",p.numberOfEyes.baseValue());
                player.put("pinpointedness",p.pinpointedness.baseValue());
                player.put("powder",p.powder.baseValue());
                player.put("rejection",p.rejection.baseValue());
                player.put("splash",p.splash.baseValue());
                player.put("wisdom",p.wisdom.baseValue());
                player.put("pregameRitual",p.pregameRitual);
                player.put("coffee",p.coffeeStyle);
                player.put("blood",p.bloodType);
                player.put("fate",p.fate);
                player.put("soul",p.soulscream);
                player.put("team",tID);

                players.add(player);
                lineupIDs.add(pID);

                pID++;
            }
            for(Player p : t.rotation){
                JSONObject player = new JSONObject();
                player.put("id",pID);
                p.id = pID;
                player.put("name",p.name);
                player.put("batting",p.batting);
                player.put("pitching",p.pitching);
                player.put("baserunning",p.baserunning);
                player.put("defense",p.defense);
                player.put("pregameRitual",p.pregameRitual);
                player.put("coffee",p.coffeeStyle);
                player.put("blood",p.bloodType);
                player.put("fate",p.fate);
                player.put("soul",p.soulscream);

                players.add(player);
                rotationIDs.add(pID);

                pID++;
            }
            team.put("lineup",lineupIDs);
            team.put("rotation",rotationIDs);
            tID++;
            teams.add(team);
        }
        JSONObject league = new JSONObject();
        JSONArray uDark = new JSONArray();
        JSONArray mDark = new JSONArray();
        JSONArray uLight = new JSONArray();
        JSONArray mLight = new JSONArray();
        for(int x = 0; x < 6; x++){
            uDark.add(x);
        }
        for(int x = 6; x < 12; x++){
            mDark.add(x);
        }
        for(int x = 12; x < 18; x++){
            uLight.add(x);
        }
        for(int x = 18; x < 24; x++){
            mLight.add(x);
        }
        league.put("ultraDark",uDark);
        league.put("moderateDark",mDark);
        league.put("ultraLight",uLight);
        league.put("moderateLight",mLight);

        json.put("players",players);
        json.put("teams",teams);
        json.put("league",league);

        try(FileWriter file = new FileWriter("season" + 1 + ".json")){
            file.write(json.toJSONString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace(); //i have no idea what this means but the JSON guide did it so here it is
        }
    }
}
