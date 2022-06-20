public class Stat
{
    double baseValue;
    double boost;
    public Stat(double baseValue){
        this.baseValue = baseValue;
    }
    
    void setBaseValue(double baseValue){
        this.baseValue = baseValue;
    }
    
    void setTemporary(double value){
        boost = value - baseValue;
    }
    
    double value(){
        return baseValue + boost;
    }
    
    void permanentIncrease(double boost){
        baseValue += boost;
    }
    
    void permanentMultiply(double multiple){
        baseValue *= multiple;
    }
    
    void multiplyBoost(double multiple){
        double v = value() * multiple;
        boost = v - baseValue;
    }
    
    void addBoost(double boost){
        this.boost += boost;
    }
    
    double baseValue(){
        return baseValue;
    }
    
    void removeBoost(){
        boost = 0;
    }
}
