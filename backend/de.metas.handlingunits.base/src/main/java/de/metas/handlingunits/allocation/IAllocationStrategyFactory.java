package de.metas.handlingunits.allocation;

import de.metas.handlingunits.allocation.impl.AllocationDirection;
import de.metas.quantity.Capacity;
import de.metas.util.ISingletonService;

public interface IAllocationStrategyFactory extends ISingletonService
{
	IAllocationStrategy createAllocationStrategy(final AllocationDirection direction);

	IAllocationStrategy createUpperBoundAllocationStrategy(Capacity capacityOverride);
}
