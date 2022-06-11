public class Event
{
    String text;
    long time;
    public Event(String text, long time){
        this.text = text;
        this.time = time;
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
}
