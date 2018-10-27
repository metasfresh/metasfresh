package de.metas.handlingunits.allocation.impl;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationStrategy;
import de.metas.handlingunits.allocation.IAllocationStrategyFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.util.Check;
import de.metas.util.Services;

public class AllocationStrategyFactory implements IAllocationStrategyFactory
{
	private final IHandlingUnitsDAO handlingUnitsDAO;

	public AllocationStrategyFactory()
	{
		this(Services.get(IHandlingUnitsDAO.class));
	}

	public AllocationStrategyFactory(final IHandlingUnitsDAO handlingUnitsDAO)
	{
		super();
		Check.assumeNotNull(handlingUnitsDAO, "handlingUnitsDAO not null");
		this.handlingUnitsDAO = handlingUnitsDAO;
	}

	@Override
	public IHandlingUnitsDAO getHandlingUnitsDAO()
	{
		return handlingUnitsDAO;
	}

	// TODO: check if there is any implementation that does not ignore the hu parameter.
	@Override
	public IAllocationStrategy getAllocationStrategy(final I_M_HU IGNORED)
	{
		final FIFOAllocationStrategy strategy = new FIFOAllocationStrategy();
		strategy.setAllocationStrategyFactory(this);
		return strategy;
	}

	@Override
	public IAllocationStrategy getDeallocationStrategy(final I_M_HU hu)
	{
		final FIFODeallocationStrategy strategy = new FIFODeallocationStrategy();
		strategy.setAllocationStrategyFactory(this);
		return strategy;
	}
}
