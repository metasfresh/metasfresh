package de.metas.calendar.plan_optimizer.solver;

import java.util.Comparator;

public class DelayStrengthComparator implements Comparator<Integer>
{
	@Override
	public int compare(Integer delay1, Integer delay2)
	{
		return delay1.compareTo(delay2);
	}
}
