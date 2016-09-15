package org.adempiere.ad.dao.jmx;

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

import java.util.Date;

import org.adempiere.ad.dao.IQueryStatisticsLogger;
import org.adempiere.util.Services;

public class JMXQueryStatisticsLogger implements JMXQueryStatisticsLoggerMBean
{
	@Override
	public void enable()
	{
		Services.get(IQueryStatisticsLogger.class).enable();
	}

	@Override
	public void enableWithSqlTracing()
	{
		Services.get(IQueryStatisticsLogger.class).enableWithSqlTracing();
	}

	@Override
	public void disable()
	{
		Services.get(IQueryStatisticsLogger.class).disable();
	}

	@Override
	public void reset()
	{
		Services.get(IQueryStatisticsLogger.class).reset();
	}

	@Override
	public void setFilterBy(final String filterBy)
	{
		Services.get(IQueryStatisticsLogger.class).setFilterBy(filterBy);
	}

	@Override
	public String getFilterBy()
	{
		return Services.get(IQueryStatisticsLogger.class).getFilterBy();
	}

	@Override
	public void clearFilterBy()
	{
		Services.get(IQueryStatisticsLogger.class).clearFilterBy();
	}

	@Override
	public Date getValidFrom()
	{
		return Services.get(IQueryStatisticsLogger.class).getValidFrom();
	}

	@Override
	public String[] getTopQueriesAsString()
	{
		return Services.get(IQueryStatisticsLogger.class).getTopQueriesAsString();
	}
}
