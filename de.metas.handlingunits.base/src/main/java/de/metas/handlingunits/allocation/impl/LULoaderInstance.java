package de.metas.handlingunits.allocation.impl;

import java.util.Objects;

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

import java.util.SortedSet;
import java.util.TreeSet;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_Locator;

import de.metas.handlingunits.HUIteratorListenerAdapter;
import de.metas.handlingunits.IHUBuilder;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.handlingunits.model.X_M_HU_PI_Version;

/**
 * Loading Unit(LU) loader instance. It creates and wraps one loading unit {@link I_M_HU} to which it can add TU, if they match. Used by {@link LULoader}.
 *
 * Equally {@code true}: This class is responsible for adding Transport Units (TUs) to a particular LU handling unit (which will be created using {@link HULoader} in class constructor).
 *
 * @author tsa
 *
 */
/* package */class LULoaderInstance
{
	//
	// Services
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	private final IHUContext huContext;
	private final I_C_BPartner bpartner;
	private final int bpartnerLocationId;
	private final I_M_Locator locator;
	private final String huStatus;

	private final I_M_HU luHU;

	/**
	 * LU Item Instances.
	 *
	 * NOTE: we use {@link SortedSet} because we want items to be sorted by priority. For this we relly on {@link LULoaderItemInstance#compareTo(LULoaderItemInstance)}.
	 */
	private final SortedSet<LULoaderItemInstance> luItemInstances = new TreeSet<>();

	/**
	 * The builder used to create the LU wrapped by this instance. We need to keep it around even after the LU was created, because when {@link #addTU(I_M_HU)} is called,
	 * and only the "aggregate" HU item was created so far, we need to create a matching HU item on the fly.
	 */
	private final IHUBuilder huBuilder;

	public LULoaderInstance(final IHUContext huContext,
			final I_C_BPartner bpartner,
			final int bpartnerLocationId,
			final I_M_Locator locator,
			final String huStatus,
			final I_M_HU_PI_Version tuPIVersion)
	{
		this.huContext = huContext;
		this.bpartner = bpartner;
		this.bpartnerLocationId = bpartnerLocationId;
		this.locator = locator;
		this.huStatus = huStatus;

		assertUnitTypeOrNull(tuPIVersion, X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);

		//
		// Retrieve LU PI to be used
		final I_M_HU_PI tuPI = tuPIVersion.getM_HU_PI();
		final I_M_HU_PI_Item luPIItem = handlingUnitsDAO.retrieveDefaultParentPIItem(tuPI, X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit, bpartner);
		if (luPIItem == null)
		{
			throw new AdempiereException("No LU found for TU: " + tuPI.getName());
		}
		final I_M_HU_PI_Version luPIVersion = luPIItem.getM_HU_PI_Version();

		//
		// Create LU
		huBuilder = handlingUnitsDAO.createHUBuilder(huContext);
		huBuilder.setC_BPartner(bpartner);
		huBuilder.setC_BPartner_Location_ID(bpartnerLocationId);
		huBuilder.setM_Locator(locator);
		huBuilder.setHUStatus(huStatus);
		// set up a little custom anonymous listener so that if the huBuilder created a HU item (*not* an aggregate one), it shall be added to this LULoaderInstance's HU
		huBuilder.setListener(new HUIteratorListenerAdapter()
		{
			@Override
			public Result afterHUItem(final I_M_HU_Item luItem)
			{
				addLUItemIfPossible(luItem);
				return Result.CONTINUE;
			}
		});

		luHU = huBuilder.create(luPIVersion);

	}

	@Override
	public String toString()
	{
		return "LULoaderInstance ["
				+ "bpartner=" + bpartner
				+ ", bpartnerLocationId=" + bpartnerLocationId
				+ ", locator=" + locator
				+ ", huStatus=" + huStatus
				+ ", luItemInstances=" + luItemInstances
				+ ", luHU=" + luHU
				+ "]";
	}

	public I_M_HU getLU_HU()
	{
		return luHU;
	}

	private static final void assertUnitTypeOrNull(final I_M_HU_PI_Version piVersion, final String expectedUnitType)
	{
		final String unitType = piVersion.getHU_UnitType();
		if (Check.isEmpty(unitType))
		{
			return;
		}

		if (!expectedUnitType.equals(unitType))
		{
			throw new AdempiereException("Invalid @HU_UnitType@. Expected=" + expectedUnitType + ", Actual=" + unitType);
		}
	}

	/**
	 * Add given Transport Unit (TU) to this LU
	 *
	 * @param tuHU Transport Unit HU
	 * @return true if given TU was added; false if this LU does not support given TU or the LU is already full.
	 */
	public boolean addTU(final I_M_HU tuHU)
	{
		if (!acceptTU(tuHU))
		{
			return false;
		}

		// in case tuHU can't be added, we'll need to know if it's because there is no matching LULoaderItemInstance.
		boolean matchingItemInstanceExists = false;

		for (final LULoaderItemInstance luItemInstance : luItemInstances)
		{
			if (luItemInstance.isMatchTU(tuHU))
			{
				matchingItemInstanceExists = true;
			}

			if (luItemInstance.addTU(tuHU)) // is still might not be added because luItemInstance is already "full"
			{
				return true;
			}
		}

		if (!matchingItemInstanceExists)
		{
			// #gh 460: the HUBuilder doesn't add just any possible HU item anymore, so chances are big that luHU does not yet have a HU-item to attach tuHU to.
			// therefore we now need to identify the correct HU PI item and create that item on the fly.
			final I_M_HU_PI_Item piItemForTU = handlingUnitsDAO.retrieveParentPIItemForChildHUOrNull(luHU,
					Services.get(IHandlingUnitsBL.class).getPI(tuHU),
					huContext);

			if (piItemForTU != null)
			{
				final I_M_HU_Item newItem = handlingUnitsDAO.createHUItem(luHU, piItemForTU);
				final LULoaderItemInstance newItemInstace = addLUItemIfPossible(newItem);
				Check.errorIf(newItemInstace == null,
						"Unable to create and add a LULoaderItemInstance for newly created item={}; luItemInstances={}; huContext={}",
						newItem, luItemInstances, huContext);

				return newItemInstace.addTU(tuHU);
			}
		}

		// TU was not added
		return false;
	}

	/**
	 * Adds given LU Item to our index, <b>if</b> that item has {@link X_M_HU_Item#ITEMTYPE_HandlingUnit}.
	 *
	 * @param luItem
	 */
	private LULoaderItemInstance addLUItemIfPossible(final I_M_HU_Item luItem)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		//
		// Check if it's a handling unit item
		if (!Objects.equals(handlingUnitsBL.getItemType(luItem), X_M_HU_Item.ITEMTYPE_HandlingUnit))
		{
			// Note: we aren't even interested in items with type "HUAggregate" / "HA", because this here is about adding TUs that already exist to an LU.
			// In the other hand, "HUAggregate" items are all about *not* having to add an actual TU, but sortof cover it in a "bag", together with others.
			return null;
		}

		final LULoaderItemInstance luItemInstance = new LULoaderItemInstance(huContext, luItem);
		if (!luItemInstances.add(luItemInstance))
		{
			throw new AdempiereException("InternalError: Item " + luItemInstance + " was not added to instances list");
		}
		return luItemInstance;
	}

	/**
	 * Checks if given TU is compatible with our LU (same BParter, BP Location etc).
	 *
	 * @param tuHU
	 * @return true if it's compatible
	 */
	private boolean acceptTU(final I_M_HU tuHU)
	{
		//
		// Check same BPartner
		if (tuHU.getC_BPartner_ID() != getC_BPartner_ID())
		{
			return false;
		}

		//
		// Check same BPartner Location
		if (tuHU.getC_BPartner_Location_ID() != this.bpartnerLocationId)
		{
			return false;
		}

		//
		// Check same Locator
		if (tuHU.getM_Locator_ID() != getM_Locator_ID())
		{
			return false;
		}

		//
		// Check same HUStatus
		final String huStatus = tuHU.getHUStatus();
		if (!Objects.equals(huStatus, this.huStatus))
		{
			return false;
		}

		//
		// Default: accept
		return true;
	}

	private int getC_BPartner_ID()
	{
		if (bpartner == null)
		{
			return 0;
		}

		final int bpartnerId = bpartner.getC_BPartner_ID();
		return bpartnerId;
	}

	private int getM_Locator_ID()
	{
		if (locator == null)
		{
			return 0;
		}

		final int locatorId = locator.getM_Locator_ID();
		return locatorId;
	}
}
