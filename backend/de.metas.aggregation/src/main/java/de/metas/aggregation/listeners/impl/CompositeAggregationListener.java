package de.metas.aggregation.listeners.impl;

/*
 * #%L
 * de.metas.aggregation
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


import java.util.concurrent.CopyOnWriteArrayList;

import org.adempiere.util.lang.ObjectUtils;

import de.metas.aggregation.listeners.IAggregationListener;
import de.metas.aggregation.model.I_C_Aggregation;

public final class CompositeAggregationListener implements IAggregationListener
{
	private final CopyOnWriteArrayList<IAggregationListener> listeners = new CopyOnWriteArrayList<>();

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public void addListener(final IAggregationListener listener)
	{
		if (listener == null)
		{
			return;
		}
		listeners.addIfAbsent(listener);
	}

	@Override
	public void onAggregationCreated(final I_C_Aggregation aggregation)
	{
		for (final IAggregationListener listener : listeners)
		{
			listener.onAggregationCreated(aggregation);
		}
	}

	@Override
	public void onAggregationChanged(final I_C_Aggregation aggregation)
	{
		for (final IAggregationListener listener : listeners)
		{
			listener.onAggregationChanged(aggregation);
		}
	}

	@Override
	public void onAggregationDeleted(final I_C_Aggregation aggregation)
	{
		for (final IAggregationListener listener : listeners)
		{
			listener.onAggregationDeleted(aggregation);
		}
	}

}
