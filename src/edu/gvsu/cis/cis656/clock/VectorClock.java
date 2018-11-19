package edu.gvsu.cis.cis656.clock;

import java.util.Collections;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

public class VectorClock implements Clock {

    // suggested data structure ...
     public Map<String,Integer> clock = new Hashtable<String,Integer>();


    @Override
    public void update(Clock other) {        
        VectorClock oth = (VectorClock) other;
    	for(String key: oth.clock.keySet()) {
    		if (!this.clock.containsKey(key) || oth.clock.get(key) > this.clock.get(key)) {
    			this.clock.put(key, oth.clock.get(key));
    		}
    	}
    }

    @Override
    public void setClock(Clock other) {
    	this.clock.clear();
    	VectorClock oth = (VectorClock) other;
    	this.clock.putAll(oth.clock);
    }

    @Override
    public void tick(Integer pid) {
    	clock.put(Integer.toString(pid), clock.get(Integer.toString(pid)) + 1);
    }

    @Override
    public boolean happenedBefore(Clock other) {
    	VectorClock oth = (VectorClock) other;
    	if(oth.clock.size() < this.clock.size()) {
    		return false;
    	}
    	for(String key: oth.clock.keySet()) {
    		if (this.clock.containsKey(key)) {
    			if (this.clock.get(key) > oth.clock.get(key)) {
        			return false;
        		}
    		}
    	}
        return true;
    }

    public String toString() {
    	int counter = 0;
        String clockString = "{";
//        for(String key: this.clock.keySet()){ 
//        	counter++;
//        	clockString =  clockString + "\""+key + "\":" +  Integer.toString(clock.get(key));
//        	if(this.clock.size() != counter) {
//        		clockString =  clockString + ",";
//        	}
//        	
//        }
        if (this.clock.size() > 0) {
        	String maxKey = Collections.max(this.clock.keySet());
        	for(int i = 0; i <= Integer.parseInt(maxKey); i++) {
            	if (this.clock.containsKey(Integer.toString(i))) {
            		counter++;
                	clockString =  clockString + "\""+ i + "\":" +  Integer.toString(clock.get(Integer.toString(i)));
                	if(this.clock.size() != counter) {
                		clockString =  clockString + ",";
                	}
            	}
            }
        }
        
        clockString =  clockString + "}";
        return clockString;  
        
        
        
    }

    @Override
    public void setClockFromString(String clock) {
    	Map<String,Integer> clockTemp = new Hashtable<String,Integer>(this.clock);
    	this.clock.clear();
    	clock = clock.replace("{", "");
    	clock = clock.replace("}", "");
    	clock = clock.replace(" ", "");
    	if (clock.length() != 0) {
    		String[] values = clock.split(",");
    		try {
    			for (String clockValues: values) {
            		String[] valueArr = clockValues.split(":");
            		this.clock.put(valueArr[0].replace("\"", ""), Integer.parseInt(valueArr[1]));
            	}
    		}catch (Exception e) {
    			this.clock.clear();
    			this.clock = clockTemp;
    		}
    	}
    }

    @Override
    public int getTime(int p) {
    	
    	return this.clock.get(Integer.toString(p));	
    }

    @Override
    public void addProcess(int p, int c) {
    	this.clock.put(Integer.toString(p), c);
    }
}
