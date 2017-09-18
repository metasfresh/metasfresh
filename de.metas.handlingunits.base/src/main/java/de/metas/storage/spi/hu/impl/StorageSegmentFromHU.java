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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.text.annotation.ToStringBuilder;
import org.compiere.util.Env;

import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.storage.AbstractStorageSegment;

public class StorageSegmentFromHU extends AbstractStorageSegment
{
	// services
	private final transient IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);

	private final Set<Integer> bpartnerIds = new HashSet<>();
	@ToStringBuilder(skip = true)
	private final Set<Integer> bpartnersIdsRO = Collections.unmodifiableSet(bpartnerIds);
	//
	private final Set<Integer> locatorIds = new HashSet<>();
	@ToStringBuilder(skip = true)
	private final Set<Integer> locatorsIdsRO = Collections.unmodifiableSet(locatorIds);
	//
	private Set<Integer> productIdsRO = null; // lazy init
	//
	private final int huId;
	private final boolean hasQtyOnHandChanges;

	public StorageSegmentFromHU(final I_M_HU hu)
	{
		super();

		Check.assumeNotNull(hu, "hu not null");
		huId = hu.getM_HU_ID();
		final I_M_HU huOld = InterfaceWrapperHelper.createOld(hu, I_M_HU.class);

		boolean hasQtyOnHandChanges = false;
		final boolean isQtyOnHandOld = huStatusBL.isQtyOnHand(huOld.getHUStatus());
		final boolean isQtyOnHand = huStatusBL.isQtyOnHand(hu.getHUStatus());

		if (isQtyOnHandOld)
		{
			bpartnerIds.add(huOld.getC_BPartner_ID());
			locatorIds.add(huOld.getM_Locator_ID());
			hasQtyOnHandChanges = true;
		}

		if (isQtyOnHand)
		{
			bpartnerIds.add(hu.getC_BPartner_ID());
			locatorIds.add(hu.getM_Locator_ID());
			hasQtyOnHandChanges = true;
		}

		this.hasQtyOnHandChanges = hasQtyOnHandChanges;

		// If there are no QtyOnHand changes, set no products
		// => prevent triggering any change... because there is not
		if (!hasQtyOnHandChanges)
		{
			productIdsRO = Collections.emptySet();
		}
	}

	@Override
	public Set<Integer> getM_Product_IDs()
	{
		if (productIdsRO != null)
		{
			return productIdsRO;
		}

		//
		// Extract affected products
		final List<Integer> productIdsList = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Storage.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addEqualsFilter(I_M_HU_Storage.COLUMN_M_HU_ID, huId)
				.andCollect(I_M_HU_Storage.COLUMN_M_Product_ID)
				.create()
				.listIds();
		productIdsRO = Collections.unmodifiableSet(new HashSet<>(productIdsList));

		return productIdsRO;
	}

	@Override
	public Set<Integer> getC_BPartner_IDs()
	{
		return bpartnersIdsRO;
	}

	@Override
	public Set<Integer> getM_Locator_IDs()
	{
		return locatorsIdsRO;
	}

	public boolean hasQtyOnHandChanges()
	{
		return hasQtyOnHandChanges;
	}
}
