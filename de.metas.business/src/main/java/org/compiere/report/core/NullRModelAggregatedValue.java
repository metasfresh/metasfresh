package org.compiere.report.core;

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


import java.util.List;

public final class NullRModelAggregatedValue implements IRModelAggregatedValue
{
	public static final transient NullRModelAggregatedValue instance = new NullRModelAggregatedValue();

	private NullRModelAggregatedValue()
	{
		super();
	};

	@Override
	public void reset()
	{
		// nothing
	}

	@Override
	public void add(final RModelCalculationContext calculationCtx, final List<Object> row, final Object columnValue)
	{
		// nothing
	}

	@Override
	public Object getAggregatedValue(RModelCalculationContext calculationCtx, List<Object> groupRow)
	{
		return null;
	}

}
