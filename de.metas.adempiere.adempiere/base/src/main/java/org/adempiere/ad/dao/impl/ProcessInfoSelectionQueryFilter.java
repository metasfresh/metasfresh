package org.adempiere.ad.dao.impl;

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


import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.ISqlQueryFilter;
import org.adempiere.util.Check;
import org.compiere.Adempiere;

import de.metas.process.ProcessInfo;

/**
 * Filters user selection provided by {@link ProcessInfo}.
 * 
 * @author tsa
 * 
 * @param <T>
 */
public class ProcessInfoSelectionQueryFilter<T> implements IQueryFilter<T>, ISqlQueryFilter
{
	private final ProcessInfo processInfo;

	public ProcessInfoSelectionQueryFilter(final ProcessInfo pi)
	{
		Check.assumeNotNull(pi, "process info not null");
		this.processInfo = pi;
	}

	@Override
	public String getSql()
	{
		return processInfo.getWhereClause();
	}

	@Override
	public List<Object> getSqlParams(Properties ctx)
	{
		return Collections.emptyList();
	}

	@Override
	public boolean accept(T model)
	{
		if (Adempiere.isUnitTestMode())
		{
			// NOTE: in Unit Test mode we accept everything... else we would fail
			return true;
		}

		throw new UnsupportedOperationException();
	}
}
