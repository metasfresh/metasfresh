package de.metas.handlingunits.allocation.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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
import java.util.List;

import org.adempiere.util.Check;

import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;

public class CompositeAllocationDestination extends AbstractCompositeAllocationDestination
{
	interface IAllocationDestinationMatcher
	{
		boolean matches(final IAllocationDestination destination, final IAllocationRequest request);
	}

	public static final IAllocationDestinationMatcher MATCHER_AcceptAll = new IAllocationDestinationMatcher()
	{
		@Override
		public boolean matches(final IAllocationDestination destination, final IAllocationRequest request)
		{
			return true;
		}
	};

	private final List<IAllocationDestination> destinations = new ArrayList<IAllocationDestination>();

	private IAllocationDestinationMatcher destinationMatcher = MATCHER_AcceptAll;

	public CompositeAllocationDestination()
	{
		super();

	}

	public void setMatcher(final IAllocationDestinationMatcher destinationMatcher)
	{
		Check.assumeNotNull(destinationMatcher, "destinationMatcher not null");
		this.destinationMatcher = destinationMatcher;
	}

	public void addDestination(final IAllocationDestination destination)
	{
		Check.assumeNotNull(destination, "destination not null");
		if (destinations.contains(destination))
		{
			return;
		}
		destinations.add(destination);
	}

	@Override
	protected List<IAllocationDestination> getMatchingDestinations(final IAllocationRequest request)
	{
		final List<IAllocationDestination> result = new ArrayList<IAllocationDestination>();
		for (final IAllocationDestination dest : destinations)
		{
			if (destinationMatcher.matches(dest, request))
			{
				result.add(dest);
			}
		}
		return result;
	}
}
