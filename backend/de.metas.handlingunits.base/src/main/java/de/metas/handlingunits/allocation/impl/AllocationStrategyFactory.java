package de.metas.handlingunits.allocation.impl;

import javax.annotation.Nullable;

import de.metas.handlingunits.allocation.IAllocationStrategy;
import de.metas.handlingunits.allocation.IAllocationStrategyFactory;
import de.metas.quantity.Capacity;
import lombok.NonNull;

public class AllocationStrategyFactory implements IAllocationStrategyFactory
{
	private final AllocationStrategySupportingServicesFacade services;

	public AllocationStrategyFactory()
	{
		services = AllocationStrategySupportingServicesFacade.newInstance();
	}

	@Override
	public IAllocationStrategy createAllocationStrategy(@NonNull final AllocationDirection direction)
	{
		return direction.isOutboundDeallocation()
				? new FIFODeallocationStrategy(services, this)
				: new FIFOAllocationStrategy(services, this);
	}

	@Override
	public IAllocationStrategy createUpperBoundAllocationStrategy(@Nullable final Capacity capacityOverride)
	{
		return new UpperBoundAllocationStrategy(capacityOverride, services, this);
	}
}
