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
import java.util.Iterator;
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import de.metas.common.util.pair.IPair;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.IAllocationSource;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;

public class GenericListAllocationSourceDestination implements IAllocationSource, IAllocationDestination
{
	private final List<IAllocationSource> sources = new ArrayList<>();
	private final List<IAllocationDestination> destinations = new ArrayList<>();

	public GenericListAllocationSourceDestination()
	{
		super();
	}

	@Override
	public String toString()
	{
		return getClass().getSimpleName() + "["
				+ "\nSources:"
				+ "\n" + CollectionUtils.toString(sources, "\n")
				+ "\nDestinations:"
				+ "\n" + CollectionUtils.toString(destinations, "\n")
				+ "\n]";
	}

	public void addAllocationSource(final IAllocationSource source)
	{
		Check.assumeNotNull(source, "source not null");
		if (sources.contains(source))
		{
			throw new AdempiereException("Source " + source + " was already added");
		}

		sources.add(source);
	}

	public void addAllocationDestination(final IAllocationDestination destination)
	{
		Check.assumeNotNull(destination, "destination not null");
		if (destinations.contains(destination))
		{
			throw new AdempiereException("Destination " + destination + " was already added");
		}

		destinations.add(destination);
	}

	public <T extends IAllocationSource & IAllocationDestination> void addAllocationSourceOrDestination(final T sourceOrDestination)
	{
		addAllocationSource(sourceOrDestination);
		addAllocationDestination(sourceOrDestination);
	}

	@Override
	public IAllocationResult load(final IAllocationRequest request)
	{
		final IMutableAllocationResult resultFinal = AllocationUtils.createMutableAllocationResult(request.getQty());

		final Iterator<IAllocationDestination> destinationsIterator = destinations.iterator();
		while (destinationsIterator.hasNext())
		{
			if (resultFinal.isCompleted())
			{
				return resultFinal;
			}

			final IAllocationDestination destination = destinationsIterator.next();

			final IAllocationRequest requestActual = AllocationUtils.createQtyRequestForRemaining(request, resultFinal);
			final IAllocationResult result = destination.load(requestActual);

			//
			// Nothing was allocated in this turn
			if (result.isZeroAllocated())
			{
				// Shall we remove current destination?
				continue;
			}

			AllocationUtils.mergeAllocationResult(resultFinal, result);
		}

		return resultFinal;
	}

	@Override
	public IAllocationResult unload(final IAllocationRequest request)
	{
		//
		// If there are no sources, we have nothing to unload
		if (sources.isEmpty())
		{
			return AllocationUtils.nullResult();
		}

		//
		// Create the inial result: we are starting from how much we need to allocate
		final IMutableAllocationResult resultFinal = AllocationUtils.createMutableAllocationResult(request.getQty());

		//
		// Iterate sources and try to unload from each of them.
		// But don't use ForceQtyAllocation because we want to unload from those sources which have something to be unloaded,
		// with one exception: if we have only one source and we were requested to do force qty allocation,
		// because there is no point to later do the same thing again.
		final boolean forceQtyAllocationOnFirstTry = request.isForceQtyAllocation() && sources.size() == 1;
		final Iterator<IAllocationSource> sourceIterator = sources.iterator();
		while (sourceIterator.hasNext())
		{
			// If we allocated everything, stop here
			if (resultFinal.isCompleted())
			{
				return resultFinal;
			}

			// Get next allocation source
			final IAllocationSource source = sourceIterator.next();

			// Create the actual request
			final IAllocationRequest requestActual = AllocationUtils.deriveAsQtyRequestForRemaining(request, resultFinal)
					.setForceQtyAllocation(forceQtyAllocationOnFirstTry)
					.create();

			// Ask the source to unload
			final IAllocationResult result = source.unload(requestActual);

			// Merge current result to our final result
			AllocationUtils.mergeAllocationResult(resultFinal, result);
		}

		// If we allocated everything, stop here
		if (resultFinal.isCompleted())
		{
			return resultFinal;
		}

		//
		// We still haven't unloaded all quantity, but our request has force allocation
		// => force allocation on last source (that our source for remaining)
		if (request.isForceQtyAllocation() && !forceQtyAllocationOnFirstTry)
		{
			final IAllocationSource source = sources.get(sources.size() - 1);
			final IAllocationRequest requestActual = AllocationUtils.createQtyRequestForRemaining(request, resultFinal);
			// Ask the source to unload
			final IAllocationResult result = source.unload(requestActual);
			// Merge current result to our final result
			AllocationUtils.mergeAllocationResult(resultFinal, result);
		}

		return resultFinal;
	}

	@Override
	public List<IPair<IAllocationRequest, IAllocationResult>> unloadAll(final IHUContext huContext)
	{
		final List<IPair<IAllocationRequest, IAllocationResult>> result = new ArrayList<>();

		final Iterator<IAllocationSource> sourceIterator = sources.iterator();
		while (sourceIterator.hasNext())
		{
			final IAllocationSource source = sourceIterator.next();

			final List<IPair<IAllocationRequest, IAllocationResult>> sourceResult = source.unloadAll(huContext);
			result.addAll(sourceResult);
		}

		return result;
	}

	@Override
	public void loadComplete(final IHUContext huContext)
	{
		for (final IAllocationDestination destionation : destinations)
		{
			destionation.loadComplete(huContext);
		}
	}

	@Override
	public void unloadComplete(final IHUContext huContext)
	{
		for (final IAllocationSource source : sources)
		{
			source.unloadComplete(huContext);
		}
	}

}
