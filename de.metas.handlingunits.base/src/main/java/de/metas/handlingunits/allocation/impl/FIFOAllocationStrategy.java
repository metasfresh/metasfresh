package de.metas.handlingunits.allocation.impl;

import org.adempiere.util.Check;
import org.adempiere.util.Services;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.storage.IHUItemStorage;

public class FIFOAllocationStrategy extends AbstractFIFOStrategy
{
	public FIFOAllocationStrategy()
	{
		super(false); // outTrx=false
	}

	@Override
	protected IAllocationResult allocateRemainingOnIncludedHUItem(final I_M_HU_Item item, final IAllocationRequest request)
	{
		//
		// Create initial result
		final IMutableAllocationResult result = AllocationUtils.createMutableAllocationResult(request);

		//
		// Start creating and loading new HUs as much as we can
		while (!result.isCompleted())
		{
			final I_M_HU includedHU = createNewIncludedHU(item, request);
			if (includedHU == null)
			{
				// we cannot create more HUs
				break;
			}

			final IAllocationRequest includedRequest = AllocationUtils.createQtyRequestForRemaining(request, result);
			final IAllocationResult includedResult = allocateOnIncludedHU(includedHU, includedRequest);
			if (includedResult.isZeroAllocated())
			{
				destroyIncludedHU(includedHU);
				// if we cannot allocate on this HU, we won't be able to allocate on any of these
				break;
			}

			AllocationUtils.mergeAllocationResult(result, includedResult);
		}

		return result;
	}

	/**
	 * Creates a new included HU
	 *
	 * @param item
	 * @param request triggering request
	 * @return newly created HU or null if we cannot create HUs anymore
	 */
	private final I_M_HU createNewIncludedHU(final I_M_HU_Item item, final IAllocationRequest request)
	{
		final IHUItemStorage storage = getHUStorageFactory(request).getStorage(item);
		if (!storage.requestNewHU())
		{
			return null;
		}

		final I_M_HU_PI includedHUDef;
		if (X_M_HU_Item.ITEMTYPE_HUAggregate.equals(item.getItemType()))
		{
			// if we are to create an HU below an HUAggregate item, then we always create a VHU.
			final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
			includedHUDef = handlingUnitsDAO.retrieveVirtualPI(request.getHUContext().getCtx());
		}
		else
		{
			includedHUDef = handlingUnitsBL.getPIItem(item).getIncluded_HU_PI();
		}

		// we cannot create an instance which has no included handling unit definition
		Check.errorIf(includedHUDef == null, "Unable to get a M_HU_PI for the given request and item; request={}; item={}", request, item);

		final IHUBuilder huBuilder = AllocationUtils.createHUBuilder(request);
		huBuilder.setM_HU_Item_Parent(item);
		final I_M_HU includedHU = huBuilder.create(includedHUDef);

		return includedHU;
	}

	private final void destroyIncludedHU(final I_M_HU hu)
	{
		getHandlingUnitsDAO().delete(hu);
	}
}
