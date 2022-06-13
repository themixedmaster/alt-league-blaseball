public class Stat
{
    double baseValue;
    double boost;
    public Stat(double baseValue){
        this.baseValue = baseValue;
    }
    
    double value(){
        return baseValue + boost;
    }
    
    void addBoost(double boost){
        this.boost+= boost;
    }
    
    double baseValue(){
        return baseValue;
    }
    
    void removeBoost(){
        boost = 0;
    }
}
