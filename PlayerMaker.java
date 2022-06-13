import java.util.*;
public class PlayerMaker
{
    static String[] pregameRituals = {"Trying their best","Eating","Sleeping","Carcinization","Squirming",
        "Looking for a way out","Gonging","Bees","Shapeshifting","Counting primes","Doing nothing","Listening",
        "Looking at the standings","Trying to pronounce their own name", "Talking to god", "Deicide", "Tampering","Singing",
        "Running the bases backwards","Checking their pulse","Eating a bird","Eating multiple birds","Saying hello",
        "Saying goodbye","Having problems","Forgetting","Connecting","Tying their shoes","Losing their glove","Brushing",
        "Staring","Blinking","Having an existential crisis", "Smiling", "Gambling", "Washing the blood off","Coffee","Hydration",
        "Winning","Prepping","Polishing","Sit-ups","Hello?","Slithering","Zooming","Starting a trend","Teleporting","Existing",
        "Snack break!","Colors","Thinking about their secret crush","Cloning","Multitasking","Particle Accelerator","Looking",
        "Pitching","Reading","Heavy metal","Tuning","Lucid dreaming","Begging","Making friends","Eating grass","Reassembling",
        "Breadmaking","Knitting","Spitting","Plenty of Sugar","Preparing for the worst","Hoping for the best","Painting",
        "*crunch*","Juggling","Gaming","Tax evasion","Unionizing","Feeding Carl","Melting","Blaseing","Waltzing",
        "Spinning in circles","Spinning in squares","Soup night","Hiding","Seeking","Research","Reading lore","Swearing",
        "Procrastinating","Looking at their stats","Explosion","Spooning","Screaming","Ultraviolet lights","Imaginary cow",
        "They know","Swimming","Cleaning their bat","Laps","Befriending the leeches","Pretending everything's fine","crab math",
        "Simulating","Chasing down dreams","Go fish","Hopscotch","Scotch","Marine biology","Rolling coins","Crumping",
        "Skipping stones","Fall Guys Among Us","Getting away from it all","Succulents","Pizza","Monobob","Extreme ironing",
        "Cheese rolling","Cheese grating","Smelling old books","Doing cartwheels","Basket weaving","Writing lore","Consuming",
        "Smiting mortals","Hugging","Calling your manager","Getting cozy","Studying Pataphysics","Discord","Discourse",
        "Making cheesecake","Necromancy","Counting beans","Crying","Vibrating","Reincarnation","Vibing","Troubleshooting",
        "Shaking their fist at a bird's nest","Yelling at clouds","Tooth collecting","Setting the clock an hour forwards",
        "Up to interpretation","Brass","Interpreting","Planking","Checking the mail","Ragtime","Spider climbing","Tickling",
        "Being the ball","Picking up lungs","Picking scabs","Antiquing","Petting","Soapstone","Resetting the Wi-Fi","Ritual?",
        "Inventing new swear words","Downvoting","Upvoting","Backreading","Reforestation","Defenestration","Combo breaking",
        "Trolling","Causing paradoxes","\"Do I really need one?\"","Online shopping","Stealing hearts","Returning hearts","Gacha",
        "Breaking infinities","Wlordle","Gaslighting","Gatekeeping","Girlbossing","Throwing punches","Catching punches",
        "Blanking","Fiber arts","Tables","Card games","Bonking","Blocking the sun","Swabbing the deck","Bridging",
        "Thinking of pregame rituals","Escaping","Keymashing","Locking all the doors","Neighing","Searching",
        "Keeping it together","Yes","No","Scratching their head","Whisking","Divining the stars","Sighs","Being sheriff",
        "Burning","Blubbering","Psychogeography","Unwarranted arrogance","Ingesting poison","Theft","Shadowboxing","Philosophy",
        "Butchering","Baking","Leaving breadcrumbs","Lava","Birdwatching","Oobleck","Salad spinning","Jellyfishing","Cereal",
        "Parallel parking","Coloring outside the lines","Gun bong","Contrarianism","Being cool","Self-inserting","Holding hands",
        "Counting rings","Yeeting","tYpInG lIkE tHiS","Digging for buried treasure","Wishing they had played better yesterday",
        "Hiding from teammates","Putting whoopie cushions under the bases","Horseback riding","Staring at their reflection",
        "photosynthesis","Tearing up the daily newspaper","Crosswords","Plucking feathers","Laughing uncontrollably",
        "cursing the gods","Leaving ominous notes","Running errands","Pressing the big red button","Calculating","Mooing",
        "Boiling water","Melting wood","Letting it out","Chaos","Stealing shoes","Magic","Smashing a typewriter","X-rays",
        "Microwaves","ctrl+z","rotating","Merging timelines","Forbidden knowledge","Breakdancing","AP Calculus","Power nap",
        "Building a table","Hardcore plumbing","Freezing","Punching the air","Advanced Origami","Advanced hopscotch",
        "Ignoring the voices","Ignoring the voices","Googling themself","The cha cha slide","Poetry","Staring into the sun",
        "Twitch streaming","Propaganda","Fighting fires","Drama","Drinking the juice","The void","Divorce","Gardening","Default",
        "Responding to emails","Denying accusations","Spam mail","Waiting for the game to start","Pondering shrimp",
        "Standing up straight","Perpetual motion","Backflips","Rocket science","peeling fruit","Admiring their collection",
        "Selling copper wire on craigslist","Threatening umps","Something evil","Showing up late","Violence","Remembering before",
        "Feeding the floor monster","Camouflage","Phone games from 2009","Dressing up","Piracy","Drinking battery acid",
        "Catching up","Checking the forecast","Feeding the birds","Catching up","Saving the world","Holding their breath",
        "Changing color","Evolving","Flickering","Mood swings","Math homework","Giving a monkey a shower","Mandelbrot","Surfing",
        "Painting the bases","Partying","Licking the moon","Tasting the infinite","Standup comedy","Food crimes","Overachieving",
        "Organ borrowing","Theatrics","Summoning a dragon","Crime","Breaking a mirror","Dodge rolling","OFFLINE","Leaking",
        "Making slime","Unknowing the known","Weasels","Vandalism","Skateboard trick","Reading the room","Picking flowers",
        "Side jobs"};
        
        
        
        
    static String[] coffeeStyles = {"Latte","Cold Brew","Cream & Sugar","Blood","Plenty of Sugar","Decaf","Flat White",
        "Macchiato","Milk Substitute","Light & Sweet","Americano","Espresso","Heavy Foam","Coffee?","Black","Anything","Tea"};
    static String[] bloodTypes = {"A","AA","AAA","AAAAAAAAAA","Acidic","Basic","Electric","Fire","Grass","H2O","Love","O","O No",
        "Psychic","Dirt","B","AB","Coffee","Blood?"};
    static char[] soulscreamChars = {'A','E','I','O','U','H','X'};    
         
    public static void season1ToSeason2(Team[] teams){
        //System.out.println(pregameRituals.length);
        for(Team t : teams){
            for(Player p : t.getActivePlayers()){
                String ritual = pregameRituals[(int)(League.r.nextDouble() * pregameRituals.length)];
                String coffee = coffeeStyles[(int)(League.r.nextDouble() * coffeeStyles.length)];
                String blood = bloodTypes[(int)(League.r.nextDouble() * bloodTypes.length)];
                int fate = (int)(League.r.nextDouble() * 100);
                p.setFlavor(ritual,coffee,blood,fate,generateSoulscream(p.getName()));
            }
        }
    }
    
    public static Player newRandomPlayer(){
        Player p = new Player();
        addFlavor(p);
        return p;
    }
    
    public static void addFlavor(Player p){
        if(p.soulscream != null)
            return;
        String ritual = pregameRituals[(int)(League.r.nextDouble() * pregameRituals.length)];
        String coffee = coffeeStyles[(int)(League.r.nextDouble() * coffeeStyles.length)];
        String blood = bloodTypes[(int)(League.r.nextDouble() * bloodTypes.length)];
        int fate = (int)(League.r.nextDouble() * 100);
        p.setFlavor(ritual,coffee,blood,fate,generateSoulscream(p.getName()));
    }
    
    public static String generateSoulscream(String name){
        int seed = 1;
        String scream = "";
        for(int x = 0; x < name.length();x++){
            seed = seed * name.charAt(x);
        }
        Random r = new Random(seed);
        double y = 1;
        for(int x = 0; x < 8; x++){
                scream = scream + soulscreamChars[(int)(r.nextDouble() * soulscreamChars.length)];
            }
        do{
            y = y * 0.9;
            for(int x = 0; x < 8; x++){
                scream = scream + soulscreamChars[(int)(r.nextDouble() * soulscreamChars.length)];
            }
        }while(r.nextDouble() < y);
        return scream;
    }
}
