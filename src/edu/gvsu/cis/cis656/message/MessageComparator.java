package edu.gvsu.cis.cis656.message;

import java.util.Comparator;

/**
 * Message comparator class. Use with PriorityQueue.
 */
public class MessageComparator implements Comparator<Message> {

    @Override
    public int compare(Message lhs, Message rhs) {
        // Write your code here
    	if (lhs.ts.happenedBefore(rhs.ts)) { return -1;}
    	else if (lhs.ts.happenedBefore(rhs.ts)) { return 1;}
    	else {return 0;}
    	
    }

}
