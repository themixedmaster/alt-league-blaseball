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
    //ArrayList<Player> players;
    //ArrayList<Team> teams;
    public static void loadLeagueFromJSON(int season){
        JSONParser parser = new JSONParser();
        
        try(FileReader reader = new FileReader("season" + season + ".json")){
            JSONObject json = (JSONObject)parser.parse(reader);
            JSONArray teams = (JSONArray)json.get("teams");
            JSONArray players = (JSONArray)json.get("players");
            
            
            
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ParseException e){
            e.printStackTrace();
        }
    }
}
