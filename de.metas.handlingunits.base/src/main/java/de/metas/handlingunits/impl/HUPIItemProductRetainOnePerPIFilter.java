package de.metas.handlingunits.impl;

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


import java.util.HashSet;
import java.util.Set;

import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;

import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;

/**
 * {@link I_M_HU_PI_Item_Product} filter which retains only one configuration for each {@link I_M_HU_PI}.
 *
 * If {@link #isAllowDifferentCapacities()} then configurations will be filtered also only when they have different capacities.
 *
 * @author tsa
 *
 */
public final class HUPIItemProductRetainOnePerPIFilter implements IQueryFilter<I_M_HU_PI_Item_Product>
{
	private final boolean allowDifferentCapacities;
	private final Set<ArrayKey> seenSet = new HashSet<>();

	public HUPIItemProductRetainOnePerPIFilter(final boolean allowDifferentCapacities)
	{
		super();
		this.allowDifferentCapacities = allowDifferentCapacities;
	}

	public boolean isAllowDifferentCapacities()
	{
		return allowDifferentCapacities;
	}

	@Override
	public boolean accept(final I_M_HU_PI_Item_Product itemProduct)
	{
		final Object capacityKey = createCapacityKey(itemProduct);

		final int piItemId = itemProduct.getM_HU_PI_Item_ID();
		if (!seenSet.add(Util.mkKey(I_M_HU_PI_Item.Table_Name, piItemId, capacityKey)))
		{
			// already added
			return false;
		}

		final I_M_HU_PI_Item piItem = itemProduct.getM_HU_PI_Item();
		if (!piItem.isActive())
		{
			return false;
		}
		final int piVersionId = piItem.getM_HU_PI_Version_ID();
		if (!seenSet.add(Util.mkKey(I_M_HU_PI_Version.Table_Name, piVersionId, capacityKey)))
		{
			// already added
			return false;
		}

		final I_M_HU_PI_Version piVersion = piItem.getM_HU_PI_Version();
		if (!piVersion.isActive())
		{
			return false;
		}
		if (!piVersion.isCurrent())
		{
			return false;
		}

		final int piId = piVersion.getM_HU_PI_ID();
		if (!seenSet.add(Util.mkKey(I_M_HU_PI.Table_Name, piId, capacityKey)))
		{
			// already added
			return false;
		}

		// if (!Services.get(IHandlingUnitsBL.class).isConcretePI(piId))
		// {
		// // skip Virtual PIs, No HU PIs
		// return false;
		// }

		return true;
	}

	private Object createCapacityKey(final I_M_HU_PI_Item_Product itemProduct)
	{
		if (!allowDifferentCapacities)
		{
			return null;
		}
		else if (itemProduct.isInfiniteCapacity())
		{
			return "INFINITE";
		}
		else
		{
			return itemProduct.getQty();
		}
	}
}
