package de.metas.handlingunits.shipmentschedule.segments;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Storage;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ShipmentScheduleAttributeSegment;
import de.metas.util.Services;
import lombok.NonNull;

public class ShipmentScheduleSegmentFromHU implements IShipmentScheduleSegment
{
	// services
	private final transient IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);

	private final Set<Integer> bpartnerIds = new HashSet<>();
	private final Set<Integer> bpartnersIdsRO = Collections.unmodifiableSet(bpartnerIds);
	//
	private final Set<Integer> locatorIds = new HashSet<>();
	private final Set<Integer> locatorsIdsRO = Collections.unmodifiableSet(locatorIds);
	//
	private Set<Integer> productIdsRO = null; // lazy init
	//
	private final int huId;
	private final boolean hasQtyOnHandChanges;

	public ShipmentScheduleSegmentFromHU(@NonNull final I_M_HU hu)
	{
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
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("bpartnerIds", bpartnerIds)
				.add("locatorIds", locatorIds)
				.add("productIdsRO", productIdsRO)
				.add("huId", huId)
				.add("hasQtyOnHandChanges", hasQtyOnHandChanges)
				.toString();
	}

	@Override
	public Set<Integer> getProductIds()
	{
		if (productIdsRO != null)
		{
			return productIdsRO;
		}

		//
		// Extract affected products
		final List<Integer> productIdsList = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_HU_Storage.class, Env.getCtx(), ITrx.TRXNAME_None)
				.addEqualsFilter(I_M_HU_Storage.COLUMNNAME_M_HU_ID, huId)
				.andCollect(I_M_HU_Storage.COLUMNNAME_M_Product_ID, I_M_Product.class)
				.create()
				.listIds();
		productIdsRO = Collections.unmodifiableSet(new HashSet<>(productIdsList));

		return productIdsRO;
	}

	@Override
	public Set<Integer> getBpartnerIds()
	{
		return bpartnersIdsRO;
	}

	@Override
	public Set<Integer> getLocatorIds()
	{
		return locatorsIdsRO;
	}

	public boolean hasQtyOnHandChanges()
	{
		return hasQtyOnHandChanges;
	}

	@Override
	public Set<Integer> getBillBPartnerIds()
	{
		return ImmutableSet.of();
	}

	@Override
	public Set<ShipmentScheduleAttributeSegment> getAttributes()
	{
		return ImmutableSet.of();
	}
}
