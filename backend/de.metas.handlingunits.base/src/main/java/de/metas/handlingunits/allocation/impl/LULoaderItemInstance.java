package de.metas.handlingunits.allocation.impl;

import de.metas.bpartner.BPartnerId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.util.Services;

/* package */class LULoaderItemInstance implements Comparable<LULoaderItemInstance>
{
	//
	// Services
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

	private final IHUContext huContext;
	private final I_M_HU_Item luItem;
	private final int luPIItemId;
	private final IHUItemStorage luItemStorage;
	private final BPartnerId requiredBPartnerId;
	private final int requiredTU_HU_PI_ID;

	public LULoaderItemInstance(final IHUContext huContext, final I_M_HU_Item luItem)
	{
		super();
		this.huContext = huContext;
		this.luItem = luItem;
		luItemStorage = huContext.getHUStorageFactory().getStorage(luItem);

		final I_M_HU_PI_Item luPIItem = Services.get(IHandlingUnitsBL.class).getPIItem(luItem);
		luPIItemId = luPIItem.getM_HU_PI_Item_ID();

		this.requiredBPartnerId = BPartnerId.ofRepoIdOrNull(luPIItem.getC_BPartner_ID());

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
	 * <p>
	 * NOTE: this method is extremelly important for determining the priority of an {@link LULoaderItemInstance}.
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

		if (!BPartnerId.equals(requiredBPartnerId, other.requiredBPartnerId))
		{
			// Items with Specific BPartners First
			// Items with no BPartners Last
			return -1 * (BPartnerId.toRepoIdOr(requiredBPartnerId, 0) - BPartnerId.toRepoIdOr(other.requiredBPartnerId, 0));
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
		if (requiredBPartnerId != null)
		{
			final BPartnerId tuBPartnerId = BPartnerId.ofRepoIdOrNull(tuHU.getC_BPartner_ID());
			if (!BPartnerId.equals(tuBPartnerId, requiredBPartnerId))
			{
				return false;
			}
		}

		//
		// Default: accept
		return true;
	}
}
