package de.metas.monitoring.api.impl;

/*
 * #%L
 * de.metas.monitoring
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.atomic.AtomicLong;

import de.metas.common.util.time.SystemTime;
import de.metas.monitoring.api.IMeter;

public class Meter implements IMeter
{
	private AtomicLong invokeCount = new AtomicLong(0);

	private BigDecimal invokeRate = BigDecimal.ZERO;

	private AtomicLong millisLastInvoke = new AtomicLong(0);

	private AtomicLong gauge = new AtomicLong(0);
	
	private BigDecimal intervalLastInvoke = null;

	@Override
	public void plusOne()
	{
		gauge.addAndGet(1);
		onInvoke();
	}

	@Override
	public void minusOne()	
	{
		gauge.addAndGet(-1);
		onInvoke();
	}

	private void onInvoke()
	{
		final long millisNow = SystemTime.millis();
		intervalLastInvoke = new BigDecimal(millisNow - millisLastInvoke.get());
		millisLastInvoke.set(millisNow);

		invokeCount.addAndGet(1);
		updateInvokeRate();
	}
	
	private void updateInvokeRate()
	{
		// getting local copies to ensure that the values we work with aren't changed by other threads 
		// while this method executes
		final BigDecimal intervalLastInvokeLocal = this.intervalLastInvoke;
		final long invokeCountLocal = invokeCount.get();
		
		if (invokeCountLocal < 2)
		{
			// need at least two 'plusOne()' invocations to get a rate
			invokeRate = BigDecimal.ZERO;
		}
		else if (intervalLastInvokeLocal.signum() == 0)
		{
			// omit division by zero
			invokeRate = new BigDecimal(Long.MAX_VALUE);
		}
		else
		{
			invokeRate = new BigDecimal("1000")
					.setScale(2, BigDecimal.ROUND_HALF_UP)
					.divide(intervalLastInvokeLocal, RoundingMode.HALF_UP)
					.abs(); // be tolerant against intervalLastChange < 0
		}
	}
	
	@Override
	public long getInvokeCount()
	{
		return invokeCount.get();
	}

	@Override
	public BigDecimal getInvokeRate()
	{
		return invokeRate;
	}
	
	@Override
	public long getGauge()
	{
		return gauge.get();
	}

}
