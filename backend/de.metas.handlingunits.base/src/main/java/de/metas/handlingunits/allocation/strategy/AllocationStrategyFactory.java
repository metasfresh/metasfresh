package de.metas.handlingunits.allocation.strategy;

import javax.annotation.Nullable;

import org.springframework.stereotype.Service;

import de.metas.handlingunits.allocation.IAllocationStrategy;
import de.metas.handlingunits.allocation.impl.AllocationDirection;
import de.metas.quantity.Capacity;
import lombok.NonNull;

@Service
public class AllocationStrategyFactory
{
	private final AllocationStrategySupportingServicesFacade services;

	public AllocationStrategyFactory(@NonNull final AllocationStrategySupportingServicesFacade services)
	{
		this.services = services;
	}

	public IAllocationStrategy createAllocationStrategy(@NonNull final AllocationDirection direction)
	{
		return new FIFOAllocationStrategy(direction, services);
	}

	public IAllocationStrategy createUpperBoundAllocationStrategy(@Nullable final Capacity capacityOverride)
	{
		return new UpperBoundAllocationStrategy(capacityOverride, services);
	}
}
