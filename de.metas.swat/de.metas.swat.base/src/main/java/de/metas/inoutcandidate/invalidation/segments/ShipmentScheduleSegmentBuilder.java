package de.metas.inoutcandidate.invalidation.segments;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_M_Locator;

import de.metas.util.Services;
import lombok.NonNull;

public final class ShipmentScheduleSegmentBuilder
{
	private final Set<Integer> productIds = new HashSet<>();
	private final Set<Integer> bpartnerIds = new HashSet<>();
	private final Set<Integer> locatorIds = new HashSet<>();
	private final Set<ShipmentScheduleAttributeSegment> attributeSegments = new HashSet<>();

	public ShipmentScheduleSegmentBuilder()
	{
	}

	public ImmutableShipmentScheduleSegment build()
	{
		return ImmutableShipmentScheduleSegment.builder()
				.M_Product_IDs(productIds)
				.M_Locator_IDs(locatorIds)
				.C_BPartner_IDs(bpartnerIds)
				.attributeSegments(attributeSegments)
				.build();
	}

	public ShipmentScheduleSegmentBuilder addM_Product_ID(final int productId)
	{
		productIds.add(productId);
		return this;
	}

	public ShipmentScheduleSegmentBuilder addC_BPartner_ID(final int bpartnerId)
	{
		bpartnerIds.add(bpartnerId);
		return this;
	}

	public ShipmentScheduleSegmentBuilder addM_Locator_ID(final int locatorId)
	{
		locatorIds.add(locatorId);
		return this;
	}

	public ShipmentScheduleSegmentBuilder addM_Locator(final I_M_Locator locator)
	{
		if (locator == null)
		{
			return this;
		}
		locatorIds.add(locator.getM_Locator_ID());
		return this;
	}

	public ShipmentScheduleSegmentBuilder addWarehouseId(@NonNull final WarehouseId warehouseId)
	{
		final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

		final List<LocatorId> locatorIds = warehouseDAO.getLocatorIds(warehouseId);
		for (final LocatorId locatorId : locatorIds)
		{
			addM_Locator_ID(locatorId.getRepoId());
		}

		return this;
	}

	public ShipmentScheduleSegmentBuilder addWarehouseIdIfNotNull(WarehouseId warehouseId)
	{
		if (warehouseId == null)
		{
			return this;
		}
		return addWarehouseId(warehouseId);
	}

	public ShipmentScheduleSegmentBuilder addM_AttributeSetInstance_ID(final int M_AttributeSetInstance_ID)
	{
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(M_AttributeSetInstance_ID);
		final ShipmentScheduleAttributeSegment attributeSegment = ShipmentScheduleAttributeSegment.ofAttributeSetInstanceId(asiId);
		attributeSegments.add(attributeSegment);
		return this;
	}
}
