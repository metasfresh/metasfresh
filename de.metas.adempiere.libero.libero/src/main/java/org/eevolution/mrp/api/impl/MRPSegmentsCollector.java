package org.eevolution.mrp.api.impl;

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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.eevolution.mrp.api.IMRPSegmentBL;

import de.metas.material.planning.IMRPSegment;
import de.metas.material.planning.pporder.LiberoException;

/* package */class MRPSegmentsCollector
{
	// services
	private final transient IMRPSegmentBL mrpSegmentBL = Services.get(IMRPSegmentBL.class);

	private final List<MRPSegmentAndTrace> mrpSegments = new ArrayList<>();

	public MRPSegmentsCollector()
	{
		super();
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "["
				+ "size=" + mrpSegments.size()
				+ "\n\t" + StringUtils.toString(mrpSegments, "\n\t")
				+ "]";
	}

	public MRPSegmentsCollector addSegment(final IMRPSegment mrpSegmentToAdd)
	{
		final MRPSegmentAndTrace mrpSegmentAndTraceToAdd = new MRPSegmentAndTrace(mrpSegmentToAdd);
		addSegment(mrpSegmentAndTraceToAdd);
		
		return this;
	}
	
	public MRPSegmentsCollector addSegment(final MRPSegmentAndTrace mrpSegmentAndTraceToAdd)
	{
		final IMRPSegment mrpSegmentToAdd = mrpSegmentAndTraceToAdd.getMRPSegment();
		assertFullyDefined(mrpSegmentToAdd);

		for (final Iterator<MRPSegmentAndTrace> it = mrpSegments.iterator(); it.hasNext();)
		{
			final MRPSegmentAndTrace mrpSegmentAndTrace = it.next();
			final IMRPSegment mrpSegment = mrpSegmentAndTrace.getMRPSegment();

			// Check if current segment is included in the segment we are going to add
			// => remove current segment
			if (mrpSegmentBL.isSegmentIncludes(mrpSegmentToAdd, mrpSegment))
			{
				it.remove();
				continue;
			}

			// Check if current segment includes the segment we are going to add
			// => stop here, there is no point to add a new segment
			if (mrpSegmentBL.isSegmentIncludes(mrpSegment, mrpSegmentToAdd))
			{
				return this;
			}
		}

		//
		// Finally, add our segment as the last segment in our queue
		mrpSegments.add(mrpSegmentAndTraceToAdd);
		
		return this;
	}

	public MRPSegmentsCollector addFullyDefinedSegments(final Properties ctx, final IMRPSegment notFullyQualifiedMRPSegmentToAdd)
	{
		final List<IMRPSegment> mrpSegmentsToAdd = mrpSegmentBL.createFullyDefinedMRPSegments(ctx, notFullyQualifiedMRPSegmentToAdd);
		addSegments(mrpSegmentsToAdd);
		
		return this;
	}

	public MRPSegmentsCollector addSegments(final Collection<IMRPSegment> mrpSegmentsToAdd)
	{
		if (mrpSegmentsToAdd == null || mrpSegmentsToAdd.isEmpty())
		{
			return this;
		}

		for (final IMRPSegment mrpSegmentToAdd : mrpSegmentsToAdd)
		{
			addSegment(mrpSegmentToAdd);
		}
		
		return this;
	}

	public MRPSegmentsCollector addSegmentAndTraces(final Collection<MRPSegmentAndTrace> mrpSegmentAndTracesToAdd)
	{
		if (mrpSegmentAndTracesToAdd == null || mrpSegmentAndTracesToAdd.isEmpty())
		{
			return this;
		}

		for (final MRPSegmentAndTrace mrpSegmentAndTraceToAdd : mrpSegmentAndTracesToAdd)
		{
			addSegment(mrpSegmentAndTraceToAdd);
		}
		
		return this;
	}

	public MRPSegmentsCollector addSegments(final MRPSegmentsCollector mrpSegmentsToAdd)
	{
		Check.assumeNotNull(mrpSegmentsToAdd, "mrpSegmentsToAdd not null");
		addSegmentAndTraces(mrpSegmentsToAdd.mrpSegments);
		
		return this;
	}

	public boolean removeSegmentsIncludedIn(final IMRPSegment parentMRPSegment)
	{
		assertFullyDefined(parentMRPSegment);

		boolean removed = false;
		for (final Iterator<MRPSegmentAndTrace> it = mrpSegments.iterator(); it.hasNext();)
		{
			final MRPSegmentAndTrace mrpSegmentAndTrace = it.next();
			final IMRPSegment mrpSegment = mrpSegmentAndTrace.getMRPSegment();
			if (!mrpSegmentBL.isSegmentIncludes(parentMRPSegment, mrpSegment))
			{
				continue;
			}

			it.remove();
			removed = true;
		}

		return removed;
	}

	public List<IMRPSegment> getMRPSegments()
	{
		final List<IMRPSegment> mrpSegmentsToReturn = new ArrayList<>(mrpSegments.size());
		for (final MRPSegmentAndTrace mrpSegmentAndTrace : mrpSegments)
		{
			final IMRPSegment mrpSegment = mrpSegmentAndTrace.getMRPSegment();
			mrpSegmentsToReturn.add(mrpSegment);
		}
		return mrpSegmentsToReturn;
	}

	public List<IMRPSegment> getAndClearMRPSegments()
	{
		final List<IMRPSegment> mrpSegmentsToReturn = getMRPSegments();
		mrpSegments.clear();

		return mrpSegmentsToReturn;
	}

	/**
	 * Gets and removed first enqueued segment. If there are no segments, this method will return <code>null</code>.
	 *
	 * @return first segment or <code>null</code>
	 */
	public MRPSegmentAndTrace removeFirst()
	{
		if (mrpSegments.isEmpty())
		{
			return null;
		}

		return mrpSegments.remove(0);
	}

	public boolean isEmpty()
	{
		return mrpSegments.isEmpty();
	}

	public boolean hasSegments()
	{
		return !mrpSegments.isEmpty();
	}

	public int size()
	{
		return mrpSegments.size();
	}

	private final void assertFullyDefined(final IMRPSegment mrpSegment)
	{
		Check.assume(mrpSegmentBL.isFullyDefined(mrpSegment), LiberoException.class, "MRP Segment is fully defined: {}", mrpSegment);
	}

	/**
	 * Checks if given segment exists or is included in segments that were already collected.
	 *
	 * @param childSegment
	 * @return true if given segment is included
	 */
	public boolean includes(final IMRPSegment childSegment)
	{
		if (childSegment == null)
		{
			return false;
		}

		if (mrpSegments.isEmpty())
		{
			return false;
		}

		for (final MRPSegmentAndTrace parentSegmentAndTrace : mrpSegments)
		{
			final IMRPSegment parentSegment = parentSegmentAndTrace.getMRPSegment();
			if (mrpSegmentBL.isSegmentIncludes(parentSegment, childSegment))
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Check if there is at least one segment which is included in given segment
	 *
	 * @param parentSegment
	 * @return
	 */
	public boolean hasSegmentsIncludedIn(final IMRPSegment parentSegment)
	{
		if (parentSegment == null)
		{
			return false;
		}
		if (mrpSegments.isEmpty())
		{
			return false;
		}

		for (final MRPSegmentAndTrace childSegmentAndTrace : mrpSegments)
		{
			final IMRPSegment childSegment = childSegmentAndTrace.getMRPSegment();
			if (mrpSegmentBL.isSegmentIncludes(parentSegment, childSegment))
			{
				return true;
			}
		}

		return false;
	}
}
