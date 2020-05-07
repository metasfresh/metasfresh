package de.metas.storage.spi.hu.impl;

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

import java.util.Collections;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.compiere.util.Env;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.storage.IStorageSegment;
import lombok.ToString;

@ToString(exclude = "huStorage")
public class StorageSegmentFromHUStorage implements IStorageSegment
{
	private final I_M_HU_Storage huStorage;

	private Set<Integer> productIds = Collections.emptySet();
	private Set<Integer> bpartnerIds = Collections.emptySet();
	private Set<Integer> locatorIds = Collections.emptySet();
	private boolean loaded = false;

	public StorageSegmentFromHUStorage(final I_M_HU_Storage huStorage)
	{
		super();
		Check.assumeNotNull(huStorage, "huStorage not null");
		this.huStorage = huStorage;
	}

	private final void loadIfNeeded()
	{
		if (loaded)
		{
			return;
		}

		load();

		loaded = true;
	}

	private final void load()
	{
		//
		// Load the HU
		// NOTE: instead of getting the HU by using huStorage.getM_HU() we are loading it directly because the huStorage's transaction is already closed,
		// and our ModelCacheService will log a WARNING about this.
		// see ModelCacheService (line ~194): "No transaction was found for " + trxName + ". Skip cache."
		final int huId = huStorage.getM_HU_ID();
		if (huId <= 0)
		{
			loaded = true;
			return;
		}
		final I_M_HU hu = InterfaceWrapperHelper.create(Env.getCtx(), huId, I_M_HU.class, ITrx.TRXNAME_None);
		if (hu == null)
		{
			return;
		}

		// Fire only for top-level HUs to minimize the number of events
		if (!Services.get(IHandlingUnitsBL.class).isTopLevel(hu))
		{
			return;
		}

		final StorageSegmentFromHU huSegment = new StorageSegmentFromHU(hu);

		// If this HU does not contain QtyOnHand storages, there is no point to go forward
		// because actually nothing changed from QOH perspective
		if (!huSegment.hasQtyOnHandChanges())
		{
			return;
		}

		productIds = ListUtils.asSet(huStorage.getM_Product_ID());
		bpartnerIds = huSegment.getC_BPartner_IDs();
		locatorIds = huSegment.getM_Locator_IDs();
	}

	@Override
	public Set<Integer> getM_Product_IDs()
	{
		loadIfNeeded();
		return productIds;
	}

	@Override
	public Set<Integer> getC_BPartner_IDs()
	{
		loadIfNeeded();
		return bpartnerIds;
	}

	@Override
	public Set<Integer> getM_Locator_IDs()
	{
		loadIfNeeded();
		return locatorIds;
	}

}
