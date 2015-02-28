

package dominos.model;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;


public class Dice {
    
    private Entry<Integer, Integer> value;
    private boolean blocked;
    private boolean cycleMaker;
    private boolean inPath;
    private boolean deleted;
    public Dice(){}
    public Dice(int a, int b) {
            value = new SimpleEntry<>(a,b);
            blocked = false;
            cycleMaker = false;
            inPath = false;
            deleted = false;
    }
    public Dice(Entry<Integer,Integer> p) {
            value = p;
            blocked = false;
            cycleMaker = false;
            inPath = false;
            deleted = false;
    }

    public Entry<Integer, Integer> getValue() {
        return value;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public boolean isCycleMaker() {
        return cycleMaker;
    }

    public boolean isInPath() {
        return inPath;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setValue(Entry<Integer, Integer> value) {
        this.value = value;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void setCycleMaker(boolean cycleMaker) {
        this.cycleMaker = cycleMaker;
    }

    public void setInPath(boolean inPath) {
        this.inPath = inPath;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    
    
    
    
};
