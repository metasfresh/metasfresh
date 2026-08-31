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

import de.metas.product.ProductId;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Locator;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public final class ShipmentScheduleSegmentBuilder
{
	private final Set<Integer> productIds = new HashSet<>();
	private final Set<Integer> bpartnerIds = new HashSet<>();
	private final Set<Integer> locatorIds = new HashSet<>();
	private final Set<Integer> warehouseIds = new HashSet<>();
	private final Set<ShipmentScheduleAttributeSegment> attributeSegments = new HashSet<>();

	public ShipmentScheduleSegmentBuilder()
	{
	}

	public ImmutableShipmentScheduleSegment build()
	{
		// Note: the warehouse-vs-locator mutual-exclusivity invariant (see warehouseId(...) Javadoc) is
		// enforced centrally in ImmutableShipmentScheduleSegment's constructor — the convergence point of
		// every construction path — so it holds for direct ImmutableShipmentScheduleSegment.builder() callers too.
		return ImmutableShipmentScheduleSegment.builder()
				.productIds(productIds)
				.locatorIds(locatorIds)
				.warehouseIds(warehouseIds)
				.bpartnerIds(bpartnerIds)
				.attributes(attributeSegments)
				.build();
	}

	public ShipmentScheduleSegmentBuilder productId(final int productId)
	{
		productIds.add(productId);
		return this;
	}

	public ShipmentScheduleSegmentBuilder productId(@NonNull final ProductId productId)
	{
		return productId(productId.getRepoId());
	}

	public ShipmentScheduleSegmentBuilder bpartnerId(final int bpartnerId)
	{
		bpartnerIds.add(bpartnerId);
		return this;
	}

	public ShipmentScheduleSegmentBuilder anyBPartnerId()
	{
		bpartnerIds.clear();
		bpartnerIds.add(0);
		return this;
	}

	public ShipmentScheduleSegmentBuilder locatorId(final int locatorId)
	{
		locatorIds.add(locatorId);
		return this;
	}

	public ShipmentScheduleSegmentBuilder locator(final I_M_Locator locator)
	{
		if (locator == null)
		{
			return this;
		}
		locatorIds.add(locator.getM_Locator_ID());
		return this;
	}

	/**
	 * Stores the warehouse identity (repo-id) on the segment. The recompute WHERE clause then matches by the
	 * schedule's effective warehouse column directly, instead of enumerating every locator of the warehouse.
	 * <p>
	 * NOTE: {@code warehouseId(...)} and {@link #locatorId(int)}/{@link #locator(I_M_Locator)} are meant to be
	 * MUTUALLY EXCLUSIVE on one builder. They populate independent fields that become two AND-ed branches in the
	 * WHERE clause ({@code (warehouse IN ...) AND EXISTS(locator ...)}) — i.e. an intersection, which would
	 * under-invalidate. Build a warehouse-scoped OR a locator-scoped segment, never both on the same builder.
	 */
	public ShipmentScheduleSegmentBuilder warehouseId(@NonNull final WarehouseId warehouseId)
	{
		warehouseIds.add(warehouseId.getRepoId());
		return this;
	}

	public ShipmentScheduleSegmentBuilder warehouseIdIfNotNull(final @Nullable WarehouseId warehouseId)
	{
		if (warehouseId == null)
		{
			return this;
		}
		return warehouseId(warehouseId);
	}

	public ShipmentScheduleSegmentBuilder attributeSetInstanceId(final int M_AttributeSetInstance_ID)
	{
		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(M_AttributeSetInstance_ID);
		return attributeSetInstanceId(asiId);
	}

	private ShipmentScheduleSegmentBuilder attributeSetInstanceId(@NonNull final AttributeSetInstanceId asiId)
	{
		final ShipmentScheduleAttributeSegment attributeSegment = ShipmentScheduleAttributeSegment.ofAttributeSetInstanceId(asiId);
		attributeSegments.add(attributeSegment);
		return this;
	}
}
