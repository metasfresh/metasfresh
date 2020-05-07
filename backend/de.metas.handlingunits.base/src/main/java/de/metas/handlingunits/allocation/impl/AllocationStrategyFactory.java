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


import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationStrategy;
import de.metas.handlingunits.allocation.IAllocationStrategyFactory;
import de.metas.handlingunits.model.I_M_HU;

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
