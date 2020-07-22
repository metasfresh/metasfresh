package de.metas.handlingunits.allocation.impl;

import de.metas.handlingunits.allocation.IAllocationResult;

/**
 * Implements common methods to be used in various {@link IAllocationResult} implementations.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
/* package */abstract class AbstractAllocationResult implements IAllocationResult
{

	@Override
	public final boolean isZeroAllocated()
	{
		return getQtyAllocated().signum() == 0;
	}
}
