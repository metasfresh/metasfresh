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

import de.metas.common.util.time.SystemTime;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MeterTests
{

	@Test
	public void testInitial()
	{
		final Meter meter = new Meter();
		setTime(0);
		meter.plusOne();

		assertThat(meter.getInvokeCount(), is(1L));
		assertThat("After only one count, rate shall still be zero", meter.getInvokeRate(), comparesEqualTo(BigDecimal.ZERO));
	}

	@Test
	public void testPlusAfterHalfSec()
	{
		final Meter meter = new Meter();
		setTime(0);
		meter.plusOne();

		setTime(500);
		meter.plusOne();
		
		assertThat(meter.getGauge(), is(2L));
		assertThat(meter.getInvokeCount(), is(2L));
		assertThat(meter.getInvokeRate(), comparesEqualTo(new BigDecimal("2")));
	}

	@Test
	public void testPlusAfter1Sec()
	{
		final Meter meter = new Meter();
		setTime(0);
		meter.plusOne();

		setTime(1000);
		meter.plusOne();
		
		assertThat(meter.getGauge(), is(2L));
		assertThat(meter.getInvokeCount(), is(2L));
		assertThat(meter.getInvokeRate(), comparesEqualTo(BigDecimal.ONE));
	}

	@Test
	public void testPlusAfter2Secs()
	{
		final Meter meter = new Meter();
		setTime(0);
		meter.plusOne();

		setTime(2000);
		meter.plusOne();
		
		assertThat(meter.getGauge(), is(2L));
		assertThat(meter.getInvokeCount(), is(2L));
		assertThat(meter.getInvokeRate(), comparesEqualTo(new BigDecimal("0.5")));
	}

	@Test
	public void testMinusAfter1Sec()
	{
		final Meter meter = new Meter();
		setTime(0);
		meter.minusOne();

		setTime(1000);
		meter.minusOne();
		
		assertThat(meter.getGauge(), is(-2L));
		assertThat(meter.getInvokeCount(), is(2L));
		assertThat(meter.getInvokeRate(), comparesEqualTo(BigDecimal.ONE));
	}

	@Test
	public void testMinusAfter2Secs()
	{
		final Meter meter = new Meter();
		setTime(0);
		meter.minusOne();

		setTime(2000);
		meter.minusOne();
		
		assertThat(meter.getGauge(), is(-2L));
		assertThat(meter.getInvokeCount(), is(2L));
		assertThat(meter.getInvokeRate(), comparesEqualTo(new BigDecimal("0.5")));
	}
	
	
	/**
	 * Verifies that <code>Meter</code> is tolerant aginst the time going backwards (which might be the case when {@link SystemTime} is manipulated for whatever reason).
	 */
	@Test
	public void timeGoingBackwards()
	{
		final Meter meter = new Meter();
		setTime(1000);
		meter.plusOne();
		
		setTime(500);
		meter.plusOne();
		
		assertThat(meter.getGauge(), is(2L));
		assertThat(meter.getInvokeCount(), is(2L));
		assertThat(meter.getInvokeRate(), comparesEqualTo(new BigDecimal("2")));
	}

	@Test
	public void timeStanding()
	{
		final Meter meter = new Meter();
		setTime(1000);
		meter.plusOne();
		
		setTime(1000);
		meter.plusOne();
		
		assertThat(meter.getGauge(), is(2L));
		assertThat(meter.getInvokeCount(), is(2L));
		assertThat(meter.getInvokeRate(), comparesEqualTo(new BigDecimal(Long.MAX_VALUE)));
	}

	
	private void setTime(final long millis)
	{
		de.metas.common.util.time.SystemTime.setTimeSource(() -> millis);
	}
}
