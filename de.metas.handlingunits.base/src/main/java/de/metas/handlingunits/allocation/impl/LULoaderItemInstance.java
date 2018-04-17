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


import org.adempiere.util.Services;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.storage.IHUItemStorage;

/* package */class LULoaderItemInstance implements Comparable<LULoaderItemInstance>
{
	//
	// Services
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

	private final IHUContext huContext;
	private final I_M_HU_Item luItem;
	private final int luPIItemId;
	private final IHUItemStorage luItemStorage;
	private final int requiredBPartnerId;
	private final int requiredTU_HU_PI_ID;

	public LULoaderItemInstance(final IHUContext huContext, final I_M_HU_Item luItem)
	{
		super();
		this.huContext = huContext;
		this.luItem = luItem;
		luItemStorage = huContext.getHUStorageFactory().getStorage(luItem);

		final I_M_HU_PI_Item luPIItem = Services.get(IHandlingUnitsBL.class).getPIItem(luItem);
		luPIItemId = luPIItem.getM_HU_PI_Item_ID();

		int requiredBPartnerId = luPIItem.getC_BPartner_ID();
		if (requiredBPartnerId <= 0)
		{
			requiredBPartnerId = -1;
		}
		this.requiredBPartnerId = requiredBPartnerId;

		int requiredTU_HU_PI_ID = luPIItem.getIncluded_HU_PI_ID();
		if (requiredTU_HU_PI_ID <= 0)
		{
			requiredTU_HU_PI_ID = -1;
		}
		this.requiredTU_HU_PI_ID = requiredTU_HU_PI_ID;
	}

	@Override
	public String toString()
	{
		return "LULoaderItemInstance ["
				+ "luPIItemId=" + luPIItemId
				+ ", requiredBPartnerId=" + requiredBPartnerId
				+ ", requiredTU_HU_PI_ID=" + requiredTU_HU_PI_ID
				+ "]";
	}

	/**
	 * Sorts as following:
	 * <ul>
	 * <li>items with specific BPartner will be first
	 * <li>items with specific TU_HU_PI_ID will be first
	 * <li>ordered by M_HU_PI_Item_ID ascending
	 * <li>nulls will be last
	 * </ul>
	 *
	 * NOTE: this method is extremelly important for determining the priority of an {@link LULoaderItemInstance}.
	 *
	 * @param other
	 */
	@Override
	public final int compareTo(final LULoaderItemInstance other)
	{
		if (this == other)
		{
			return 0;
		}

		if (other == null)
		{
			// Nulls last
			return +1;
		}

		if (requiredBPartnerId != other.requiredBPartnerId)
		{
			// Items with Specific BPartners First
			// Items with no BPartners Last
			return -1 * (requiredBPartnerId - other.requiredBPartnerId);
		}

		if (requiredTU_HU_PI_ID != other.requiredTU_HU_PI_ID)
		{
			// Items with Specific TU_HU_PI_ID First
			// Items with no TU_HU_PI_ID Last
			return -1 * (requiredTU_HU_PI_ID - other.requiredTU_HU_PI_ID);
		}

		// Order by PI Item ID ascending
		return luPIItemId - other.luPIItemId;
	}

	/**
	 * Add given Transport Unit (TU) to this LU
	 *
	 * @param tuHU Transport Unit HU
	 * @return true if given TU was added; false if this LU does not support given TU or the LU is already full.
	 */
	public boolean addTU(final I_M_HU tuHU)
	{
		if (!isMatchTU(tuHU))
		{
			return false;
		}

		if (!luItemStorage.requestNewHU())
		{
			return false;
		}

		huTrxBL.setParentHU(huContext, luItem, tuHU);
		return true;
	}

	/**
	 * @return {@code true} if the given {@code tuHU} fits to this instance's wrapped item. 
	 */
	public boolean isMatchTU(final I_M_HU tuHU)
	{
		//
		// Check if TU's M_HU_PI_ID is accepted
		if (requiredTU_HU_PI_ID > 0)
		{
			final int tuPIId = Services.get(IHandlingUnitsBL.class).getPIVersion(tuHU).getM_HU_PI_ID();
			if (tuPIId != requiredTU_HU_PI_ID)
			{
				return false;
			}
		}

		//
		// Check if TU's BPartner is accepted
		if (requiredBPartnerId > 0)
		{
			final int tuBPartnerId = tuHU.getC_BPartner_ID();
			if (tuBPartnerId != requiredBPartnerId)
			{
				return false;
			}
		}

		//
		// Default: accept
		return true;
	}
}
