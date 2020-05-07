package de.metas.storage.impl;

/*
 * #%L
 * de.metas.storage
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
import java.util.List;
import java.util.Set;

import org.adempiere.util.Services;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

import de.metas.storage.IStorageAttributeSegment;
import de.metas.storage.IStorageSegment;
import de.metas.storage.IStorageSegmentBuilder;

public class StorageSegmentBuilder implements IStorageSegmentBuilder
{
	private final Set<Integer> productIds = new HashSet<>();
	private final Set<Integer> bpartnerIds = new HashSet<>();
	private final Set<Integer> locatorIds = new HashSet<>();
	private final Set<IStorageAttributeSegment> attributeSegments = new HashSet<>();

	public StorageSegmentBuilder()
	{
		super();
	}

	@Override
	public IStorageSegment build()
	{
		return ImmutableStorageSegment.builder()
				.M_Product_IDs(productIds)
				.M_Locator_IDs(locatorIds)
				.C_BPartner_IDs(bpartnerIds)
				.attributeSegments(attributeSegments)
				.build();
	}

	@Override
	public IStorageSegmentBuilder addM_Product_ID(final int productId)
	{
		productIds.add(productId);
		return this;
	}

	@Override
	public IStorageSegmentBuilder addC_BPartner_ID(final int bpartnerId)
	{
		bpartnerIds.add(bpartnerId);
		return this;
	}

	@Override
	public IStorageSegmentBuilder addM_Locator_ID(final int locatorId)
	{
		locatorIds.add(locatorId);
		return this;
	}

	@Override
	public IStorageSegmentBuilder addM_Locator(final I_M_Locator locator)
	{
		if (locator == null)
		{
			return this;
		}
		locatorIds.add(locator.getM_Locator_ID());
		return this;
	}

	@Override
	public IStorageSegmentBuilder addM_Warehouse(final I_M_Warehouse warehouse)
	{
		if (warehouse == null)
		{
			return this;
		}
		final List<I_M_Locator> locators = Services.get(IWarehouseDAO.class).retrieveLocators(warehouse);
		for (final I_M_Locator locator : locators)
		{
			addM_Locator_ID(locator.getM_Locator_ID());
		}

		return this;
	}

	@Override
	public IStorageSegmentBuilder addM_AttributeSetInstance_ID(final int M_AttributeSetInstance_ID)
	{
		attributeSegments.add(new ImmutableStorageAttributeSegment(M_AttributeSetInstance_ID, 0));
		return this;
	}
}
