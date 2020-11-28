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


import de.metas.aggregation.listeners.IAggregationListener;
import de.metas.aggregation.listeners.IAggregationListeners;
import de.metas.aggregation.model.I_C_Aggregation;

public class AggregationListeners implements IAggregationListeners
{
	private final CompositeAggregationListener listeners = new CompositeAggregationListener();

	@Override
	public void addListener(final IAggregationListener listener)
	{
		listeners.addListener(listener);
	}

	@Override
	public void fireAggregationCreated(final I_C_Aggregation aggregation)
	{
		listeners.onAggregationChanged(aggregation);
	}

	@Override
	public void fireAggregationChanged(final I_C_Aggregation aggregation)
	{
		listeners.onAggregationChanged(aggregation);
	}

	@Override
	public void fireAggregationDeleted(final I_C_Aggregation aggregation)
	{
		listeners.onAggregationDeleted(aggregation);
	}
}
