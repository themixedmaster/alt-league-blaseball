public class Event
{
    String text;
    long time;
    boolean special;
    
    public Event(String text, long time, boolean special){
        this.text = text;
        this.time = time;
        this.special = special;
    }
    
    public Event(String text, long time){
        this(text,time,false);
    }
    
    public void setTime(long time){
        this.time = time;
    }
    
    public long time(){
        return time;
    }
    
    public String text(){
        return text;
    }
    
    public boolean isSpecial(){
        return special;
    }
}
