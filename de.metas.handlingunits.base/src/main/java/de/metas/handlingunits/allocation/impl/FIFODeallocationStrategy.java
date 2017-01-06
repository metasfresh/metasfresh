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


import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.model.I_M_HU_Item;

public class FIFODeallocationStrategy extends AbstractFIFOStrategy
{
	public FIFODeallocationStrategy()
	{
		super(true); // outTrx=true
	}

	/**
	 * Do nothing.
	 */
	@Override
	protected IAllocationResult allocateRemainingOnIncludedHUItem(final I_M_HU_Item item, final IAllocationRequest request)
	{
		return AllocationUtils.nullResult();
	}
}
