package de.metas.cache.model.jmx;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.util.Date;

import de.metas.cache.model.ITableCacheStatistics;
import de.metas.util.Check;

public class JMXTableCacheStatistics implements JMXTableCacheStatisticsMBean
{
	private static final BigDecimal ONEHUNDRED = BigDecimal.valueOf(100);

	private final ITableCacheStatistics statistics;

	public JMXTableCacheStatistics(final ITableCacheStatistics statistics)
	{
		super();

		Check.assumeNotNull(statistics, "statistics not null");
		this.statistics = statistics;
	}

	@Override
	public void reset()
	{
		statistics.reset();
	}

	@Override
	public String getTableName()
	{
		return statistics.getTableName();
	}

	@Override
	public Date getStartDate()
	{
		return statistics.getStartDate();
	}

	@Override
	public long getHitCount()
	{
		return statistics.getHitCount();
	}

	@Override
	public BigDecimal getHitPercent()
	{
		return calculateHitPercent(statistics.getHitCount(), statistics.getMissCount());
	}

	@Override
	public long getHitInTrxCount()
	{
		return statistics.getHitInTrxCount();
	}

	@Override
	public BigDecimal getHitInTrxPercent()
	{
		return calculateHitPercent(statistics.getHitInTrxCount(), statistics.getMissInTrxCount());
	}

	@Override
	public long getMissCount()
	{
		return statistics.getMissCount();
	}

	@Override
	public long getMissInTrxCount()
	{
		return statistics.getMissInTrxCount();
	}
	
	@Override
	public boolean isCacheEnabled()
	{
		return statistics.isCacheEnabled();
	}

	private final BigDecimal calculateHitPercent(final long hitCount, final long missCount)
	{
		final BigDecimal total = new BigDecimal(hitCount + missCount);
		if(total.signum() == 0)
		{
			return BigDecimal.ZERO;
		}
		
		final BigDecimal hitPercent = new BigDecimal(hitCount)
				.multiply(ONEHUNDRED)
				.divide(total, 0, RoundingMode.HALF_UP);

		return hitPercent;
	}
}
