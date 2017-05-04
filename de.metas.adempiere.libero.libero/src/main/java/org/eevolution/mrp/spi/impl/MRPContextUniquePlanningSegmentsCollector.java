package org.eevolution.mrp.spi.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.adempiere.util.Services;
import org.eevolution.mrp.api.IMRPContext;
import org.eevolution.mrp.api.IMRPSegment;
import org.eevolution.mrp.api.IMRPSegmentBL;

/**
 * Helper used to collect those {@link IMRPContext}s for whom no other {@link IMRPContext} was added with same Planning Seqment (see {@link IMRPContext#isSamePlanningSegment(IMRPContext)}).
 * 
 * @author tsa
 *
 */
public class MRPContextUniquePlanningSegmentsCollector implements Iterable<IMRPContext>
{
	// services
	private final IMRPSegmentBL mrpSegmentBL = Services.get(IMRPSegmentBL.class);

	private final List<IMRPContext> mrpContexts = new ArrayList<IMRPContext>();
	private final List<IMRPContext> mrpContextsRO = Collections.unmodifiableList(mrpContexts);
	private boolean keepLastAdded = true;

	public MRPContextUniquePlanningSegmentsCollector()
	{
		super();
	}

	public boolean isKeepLastAdded()
	{
		return keepLastAdded;
	}

	/**
	 * Sets if we shall keep the last added {@link IMRPContext}.
	 * 
	 * i.e. in case there was already added an {@link IMRPContext} with same planning segment, replace it with the new one.
	 * 
	 * @param keepLastAdded
	 */
	public void setKeepLastAdded(boolean keepLastAdded)
	{
		this.keepLastAdded = keepLastAdded;
	}

	/**
	 * 
	 * @param mrpContextToAdd
	 * @return true if it was added. NOTE: if {@link #isKeepLastAdded()} and we already have an MRP context for planning segment, it will be replaced and this method will return true
	 */
	public boolean addMRPContext(final IMRPContext mrpContextToAdd)
	{
		if (mrpContextToAdd == null)
		{
			return false;
		}

		final IMRPSegment mrpSegmentToAdd = mrpSegmentBL.createMRPSegment(mrpContextToAdd);

		for (int i = 0; i < mrpContexts.size(); i++)
		{
			final IMRPContext mrpContextExisting = mrpContexts.get(i);
			final IMRPSegment mrpSegmentExisting = mrpSegmentBL.createMRPSegment(mrpContextExisting);

			if (!mrpSegmentExisting.equals(mrpSegmentToAdd))
			{
				continue;
			}

			if (keepLastAdded)
			{
				mrpContexts.set(i, mrpContextToAdd);
				// return true because we added the new MRPContext to list
				return true;
			}
			else
			{
				return false;
			}
		}

		mrpContexts.add(mrpContextToAdd);
		return true;
	}

	public List<IMRPContext> getMRPContexts()
	{
		return mrpContextsRO;
	}

	public List<IMRPContext> getMRPContextsAndClear()
	{
		final List<IMRPContext> mrpContextsToReturn = new ArrayList<>(mrpContexts);
		mrpContexts.clear();
		return mrpContextsToReturn;
	}

	@Override
	public Iterator<IMRPContext> iterator()
	{
		return mrpContextsRO.iterator();
	}

	public boolean isEmpty()
	{
		return mrpContexts.isEmpty();
	}
}
