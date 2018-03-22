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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;

import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.Util;

import de.metas.handlingunits.allocation.IAllocationRequest;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.quantity.Capacity;
import de.metas.quantity.CapacityInterface;
import de.metas.quantity.Quantity;
import lombok.NonNull;

/**
 * This classe's {@link #getHUItemStorage(I_M_HU_Item, IAllocationRequest)} can return a storage with an
 * "artificial" uppper bound that is different from the capacity defined in {@link I_M_HU_PI_Item_Product}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class UpperBoundAllocationStrategy extends AbstractFIFOStrategy
{
	// services
	private final transient IDeveloperModeBL developerModeBL = Services.get(IDeveloperModeBL.class);

	private final Capacity _capacityOverride;

	/**
	 *
	 * @param capacityOverride optional capacity that can override the one from the packing instructions.
	 */
	public UpperBoundAllocationStrategy(final Capacity capacityOverride)
	{
		super(false); // outTrx=false

		_capacityOverride = isUseDefaultCapacity(capacityOverride) ? null : capacityOverride;
	}

	private static final boolean isUseDefaultCapacity(final CapacityInterface capacity)
	{
		if (capacity == null)
		{
			return true;
		}

		final BigDecimal qty;
		if (capacity.isInfiniteCapacity())
		{
			qty = Quantity.QTY_INFINITE;
		}
		else
		{
			qty = capacity.getCapacityQty();
		}

		return Util.same(Capacity.DEFAULT, qty);
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/**
	 * Invokes the {@link AbstractAllocationStrategy#getHUItemStorage(I_M_HU_Item, IAllocationRequest)},<br>
	 * but if an upper bound is set in <code>this</code> instance,<br>
	 * then it invokes {@link IHUItemStorage#setCustomCapacity(IHUCapacityDefinition)} accordingly.
	 */
	@Override
	protected IHUItemStorage getHUItemStorage(final I_M_HU_Item item, final IAllocationRequest request)
	{
		final IHUItemStorage storage = super.getHUItemStorage(item, request);

		// make sure that the capacity is forced by the user, not the system
		// If capacityOverride is null it means that we were asked to take the defaults
		final Capacity capacityOverride = getCapacityOverride();
		if (capacityOverride != null && !storage.isPureVirtual())
		{
			storage.setCustomCapacity(capacityOverride);
		}

		return storage;
	}

	private final Capacity getCapacityOverride()
	{
		return _capacityOverride;
	}

	@Override
	protected IAllocationResult allocateOnIncludedHUItem(
			@NonNull final I_M_HU_Item item,
			@NonNull final IAllocationRequest request)
	{
		// Prevent allocating on a included HU item
		final String itemType = handlingUnitsBL.getItemType(item);
		if (X_M_HU_PI_Item.ITEMTYPE_HandlingUnit.equals(itemType))
		{
			if (developerModeBL.isEnabled())
			{
				throw new AdempiereException("HUs which are used in " + this + " shall not have included HUs. They shall be pure TUs."
						+ "\n Item: " + item
						+ "\n PI: " + item.getM_HU().getM_HU_PI_Version().getM_HU_PI().getName()
						+ "\n Request: " + request);
			}
			return AllocationUtils.nullResult();
		}

		//
		// We are about to allocate to Virtual HUs linked to given "item" (which shall be of type Material).
		// In this case we rely on the standard logic.
		return super.allocateOnIncludedHUItem(item, request);
	}

	/**
	 * Does nothing, returns null result.
	 */
	@Override
	protected IAllocationResult allocateRemainingOnIncludedHUItem(final I_M_HU_Item item, final IAllocationRequest request)
	{
		return AllocationUtils.nullResult();
	}
}
