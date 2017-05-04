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


import java.util.List;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.allocation.IAllocationDestination;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;

public abstract class AbstractCompositeAllocationDestination implements IAllocationDestination
{
	@Override
	public final IAllocationResult load(final IAllocationRequest request)
	{
		final IMutableAllocationResult result = AllocationUtils.createMutableAllocationResult(request.getQty());

		final List<IAllocationDestination> dests = getMatchingDestinations(request);
		if (dests == null || dests.isEmpty())
		{
			return result;
		}

		for (final IAllocationDestination dest : dests)
		{
			if (result.isCompleted())
			{
				return result;
			}
			final IAllocationRequest currentRequest = AllocationUtils.createQtyRequestForRemaining(request, result);
			final IAllocationResult currentResult = dest.load(currentRequest);
			AllocationUtils.mergeAllocationResult(result, currentResult);
		}

		return result;
	}

	protected abstract List<IAllocationDestination> getMatchingDestinations(final IAllocationRequest request);

	@Override
	public void loadComplete(final IHUContext huContext) // --NOPMD
	{
		// Do nothing on this level.
	}
}
