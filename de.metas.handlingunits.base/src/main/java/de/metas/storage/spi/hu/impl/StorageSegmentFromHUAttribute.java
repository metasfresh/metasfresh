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
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;

import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.storage.IStorageAttributeSegment;
import de.metas.storage.IStorageSegment;
import de.metas.storage.impl.ImmutableStorageAttributeSegment;
import lombok.ToString;

@ToString
public class StorageSegmentFromHUAttribute implements IStorageSegment
{
	private final int huId;

	private Set<Integer> productIds = Collections.emptySet();
	private Set<Integer> bpartnerIds = Collections.emptySet();
	private Set<Integer> locatorIds = Collections.emptySet();
	private boolean loaded = false;

	private final Set<IStorageAttributeSegment> attributeSegments;

	public StorageSegmentFromHUAttribute(final I_M_HU_Attribute huAttribute)
	{
		super();
		Check.assumeNotNull(huAttribute, "huAttribute not null");
		huId = huAttribute.getM_HU_ID();

		final IStorageAttributeSegment attributeSegment = new ImmutableStorageAttributeSegment(
				-1, // attributeSetInstanceId
				huAttribute.getM_Attribute_ID());
		attributeSegments = Collections.singleton(attributeSegment);
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
		if (huId <= 0)
		{
			loaded = true;
			return;
		}
		final I_M_HU hu = InterfaceWrapperHelper.load(huId, I_M_HU.class);
		if (hu == null)
		{
			return;
		}

		// Fire only VHUs because those attributes counts for us
		if (Services.get(IHandlingUnitsBL.class).isVirtual(hu))
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

		productIds = huSegment.getM_Product_IDs();
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

	@Override
	public Set<IStorageAttributeSegment> getAttributes()
	{
		loadIfNeeded();
		return attributeSegments;
	}

}
