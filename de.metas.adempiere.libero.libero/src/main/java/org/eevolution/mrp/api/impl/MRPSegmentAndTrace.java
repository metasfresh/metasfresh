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
import java.util.Collections;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.eevolution.mrp.api.IMRPSegmentBL;

import de.metas.material.planning.IMRPSegment;

/**
 * Group an {@link IMRPSegment} together if some previous segments which were evaluated, called here the "trace" (see {@link #getTrace()}).
 * 
 * @author tsa
 *
 */
/* package */class MRPSegmentAndTrace
{
	private final IMRPSegment _mrpSegment;
	private final List<IMRPSegment> _trace;

	public MRPSegmentAndTrace(final IMRPSegment mrpSegment)
	{
		super();

		Check.assumeNotNull(mrpSegment, "mrpSegment not null");
		this._mrpSegment = mrpSegment;
		this._trace = Collections.emptyList();
	}

	public MRPSegmentAndTrace(final IMRPSegment mrpSegment, final MRPSegmentAndTrace parent)
	{
		super();

		Check.assumeNotNull(mrpSegment, "mrpSegment not null");
		this._mrpSegment = mrpSegment;

		final List<IMRPSegment> trace = new ArrayList<>(parent.getTrace().size() + 1);
		trace.add(parent.getMRPSegment());
		trace.addAll(parent.getTrace());
		this._trace = Collections.unmodifiableList(trace);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public IMRPSegment getMRPSegment()
	{
		return _mrpSegment;
	}

	public List<IMRPSegment> getTrace()
	{
		return _trace;
	}

	public int getMRPSegmentCyclesCount()
	{
		final IMRPSegmentBL mrpSegmentBL = Services.get(IMRPSegmentBL.class);

		int cyclesCount = 0;

		final IMRPSegment mrpSegment = getMRPSegment();
		for (final IMRPSegment previousMRPSegment : getTrace())
		{
			if (mrpSegmentBL.isSegmentIncludes(previousMRPSegment, mrpSegment))
			{
				cyclesCount++;
			}
		}

		return cyclesCount;
	}
}
