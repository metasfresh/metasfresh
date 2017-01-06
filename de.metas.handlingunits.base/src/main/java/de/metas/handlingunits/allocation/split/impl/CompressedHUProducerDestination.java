package de.metas.handlingunits.allocation.split.impl;

import org.adempiere.util.Check;

import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.allocation.impl.AbstractProducerDestination;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.IMutableAllocationResult;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class CompressedHUProducerDestination extends AbstractProducerDestination
{

	private final I_M_HU_PI huPI;
	private I_M_HU_Item parentItem;

	public CompressedHUProducerDestination(final I_M_HU_PI huPI)
	{
		Check.assumeNotNull(huPI, "Param 'huPI' is not null");
		this.huPI = huPI;
	}
	
	/**
	 * Returns <code>true</code>.
	 * 
	 */
	@Override
	public boolean isAllowCreateNewHU()
	{
		return true;
	}

	@Override
	protected I_M_HU_PI getM_HU_PI()
	{
		return huPI;
	}

	@Override
	protected IAllocationResult loadHU(I_M_HU hu, IAllocationRequest request)
	{
		// TODO consider checking this, but not using this virtual column
		// Check.errorUnless(hu.isChildHU(), "Given HU {} needs to be a child; request={}", hu, request); 
		
		final IMutableAllocationResult result = AllocationUtils.createMutableAllocationResult(request);

		return result;
	}

	public void setParentItem(final I_M_HU_Item parentItem)
	{
		this.parentItem = parentItem;
	}

	@Override
	protected I_M_HU_Item getParent_HU_Item()
	{
		return parentItem;
	}

}
