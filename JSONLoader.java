import java.io.FileWriter;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.*;

public class JSONLoader
{
    static ArrayList<Player> players;
    static ArrayList<Team> teams;
    public static void loadLeagueFromJSON(int season){
        JSONParser parser = new JSONParser();
        try(FileReader reader = new FileReader("season" + season + ".json")){
            JSONObject json = (JSONObject)parser.parse(reader);
            JSONArray teamArr = (JSONArray)json.get("teams");
            JSONArray playerArr = (JSONArray)json.get("players");
            JSONArray deceased = (JSONArray)json.get("deceased");
            JSONArray birdNest = (JSONArray)json.get("birdNest");
            JSONObject league = (JSONObject)json.get("league");
            
            players = new ArrayList<Player>();
            teams = new ArrayList<Team>();
            for(Object j : playerArr){
                JSONObject pObject = (JSONObject)j;
                Player p = new Player();
                p.id = ((Long)pObject.get("id")).intValue();
                if(League.nextID <= p.id)
                    League.nextID = p.id + 1;
                p.name = (String)pObject.get("name");
                p.aggression = new Stat((double)pObject.get("aggression"));
                p.arrogance = new Stat((double)pObject.get("arrogance"));
                p.carcinization = new Stat((double)pObject.get("carcinization"));
                p.damage = new Stat((double)pObject.get("damage"));
                p.density = new Stat((double)pObject.get("density"));
                p.dexterity = new Stat((double)pObject.get("dexterity"));
                p.dimensions = new Stat((double)pObject.get("dimensions"));
                p.effort = new Stat((double)pObject.get("effort"));
                p.focus = new Stat((double)pObject.get("focus"));
                p.fun = new Stat((double)pObject.get("fun"));
                p.grit = new Stat((double)pObject.get("grit"));
                p.hitPoints = new Stat((double)pObject.get("hitPoints"));
                p.malleability = new Stat((double)pObject.get("malleability"));
                p.mathematics = new Stat((double)pObject.get("mathematics"));
                p.numberOfEyes = new Stat((double)pObject.get("numberOfEyes"));
                p.pinpointedness = new Stat((double)pObject.get("pinpointedness"));
                p.powder = new Stat((double)pObject.get("powder"));
                p.rejection = new Stat((double)pObject.get("rejection"));
                p.splash = new Stat((double)pObject.get("splash"));
                p.wisdom = new Stat((double)pObject.get("wisdom"));
                p.pregameRitual = (String)pObject.get("pregameRitual");
                p.coffeeStyle = (String)pObject.get("coffee");
                p.bloodType = (String)pObject.get("blood");
                p.fate = ((Long)pObject.get("fate")).intValue();
                p.soulscream = (String)pObject.get("soul");
                p.teamID = ((Long)pObject.get("team")).intValue();
                players.add(p);
            }
            for(Object j : teamArr){
                JSONObject tObject = (JSONObject)j;
                Team t = new Team();
                t.id = ((Long)tObject.get("id")).intValue();
                if(League.nextTeamID <= t.id)
                    League.nextTeamID = t.id + 1;
                t.name = (String)tObject.get("name");
                t.location = (String)tObject.get("location");
                t.logo = (String)tObject.get("logo");
                t.abbreviation = (String)tObject.get("abbreviation");
                t.favor = ((Long)tObject.get("favor")).intValue();
                JSONArray lineup = (JSONArray)tObject.get("lineup");
                JSONArray rotation = (JSONArray)tObject.get("rotation");
                t.lineup = new Player[lineup.size()];
                t.rotation = new Player[rotation.size()];
                for(int x = 0; x < lineup.size(); x++){
                    int playerID = ((Long)lineup.get(x)).intValue();
                    t.lineup[x] = getPlayer(playerID);
                }
                for(int x = 0; x < rotation.size(); x++){
                    int playerID = ((Long)rotation.get(x)).intValue();
                    t.rotation[x] = getPlayer(playerID);
                }
                teams.add(t);
            }
            League.deceased = new ArrayList<Player>();
            for(int x = 0; x < deceased.size(); x++){
                int playerID = ((Long)deceased.get(x)).intValue();
                League.deceased.add(getPlayer(playerID));
            }
            League.birdNest = new ArrayList<Player>();
            for(int x = 0; x < birdNest.size(); x++){
                int playerID = ((Long)birdNest.get(x)).intValue();
                League.birdNest.add(getPlayer(playerID));
            }
            JSONArray uDark = (JSONArray)league.get("ultraDark");
            JSONArray mDark = (JSONArray)league.get("moderateDark");
            JSONArray uLight = (JSONArray)league.get("ultraLight");
            JSONArray mLight = (JSONArray)league.get("moderateLight");
            League.ultraDark = new Team[uDark.size()];
            League.moderateDark = new Team[mDark.size()];
            League.ultraLight = new Team[uLight.size()];
            League.moderateLight = new Team[mLight.size()];
            for(int x = 0; x < uDark.size(); x++){
                int teamID = ((Long)uDark.get(x)).intValue();
                League.ultraDark[x] = getTeam(teamID);
            }
            for(int x = 0; x < mDark.size(); x++){
                int teamID = ((Long)mDark.get(x)).intValue();
                League.moderateDark[x] = getTeam(teamID);
            }
            for(int x = 0; x < uLight.size(); x++){
                int teamID = ((Long)uLight.get(x)).intValue();
                League.ultraLight[x] = getTeam(teamID);
            }
            for(int x = 0; x < mLight.size(); x++){
                int teamID = ((Long)mLight.get(x)).intValue();
                League.moderateLight[x] = getTeam(teamID);
            }
            for(Player p : players)
                p.originalTeam = getTeam(p.teamID);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }
    }

    static Player getPlayer(int playerID){
        for(Player p : players)
            if(p.id == playerID)
                return p;
        return null;
    }

    static Team getTeam(int teamID){
        for(Team t : teams)
            if(t.id == teamID)
                return t;
        return null;
    }
}
