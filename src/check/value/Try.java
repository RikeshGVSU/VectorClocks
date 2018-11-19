package check.value;

import org.junit.Assert;

import edu.gvsu.cis.cis656.clock.VectorClock;
import edu.gvsu.cis.cis656.clock.VectorClockComparator;
import edu.gvsu.cis.cis656.queue.PriorityQueue;

public class Try {

	public static void main(String[] args) {
		
		int[] refTimes = { 71, 70, 5 };
		VectorClock refClock = new VectorClock();
		for (int i = 0; i < refTimes.length; i++) {
			refClock.addProcess(i, refTimes[i]);
		}
		refClock.setClockFromString("{}");
		//Assert.assertEquals(refClock.toString(), "{}");
		System.out.println(refClock);

	}

}
