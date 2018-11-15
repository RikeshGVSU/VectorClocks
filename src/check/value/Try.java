package check.value;

import org.junit.Assert;

import edu.gvsu.cis.cis656.clock.VectorClock;
import edu.gvsu.cis.cis656.clock.VectorClockComparator;
import edu.gvsu.cis.cis656.queue.PriorityQueue;

public class Try {

	public static void main(String[] args) {
		
		
		VectorClock a = new VectorClock();
		a.addProcess(0, 1);
		a.addProcess(1, 0);

		VectorClock b = new VectorClock();
		b.addProcess(0, 2);
		b.addProcess(1, 0);

		VectorClock c = new VectorClock();
		c.addProcess(0, 0);
		c.addProcess(1, 1);

		VectorClock d = new VectorClock();
		d.addProcess(0, 0);
		d.addProcess(1, 2);

		PriorityQueue<VectorClock> priorityQueue = new PriorityQueue<VectorClock>(new VectorClockComparator());
		priorityQueue.add(d);
		priorityQueue.add(a);
		priorityQueue.add(b);
		priorityQueue.add(c);

		System.out.println(priorityQueue.toString());
       // Assert.assertEquals(priorityQueue.toString(), "[{\"0\":0,\"1\":1}, {\"0\":0,\"1\":2}, {\"0\":1,\"1\":0}, {\"0\":2,\"1\":0}]");

	}

}
