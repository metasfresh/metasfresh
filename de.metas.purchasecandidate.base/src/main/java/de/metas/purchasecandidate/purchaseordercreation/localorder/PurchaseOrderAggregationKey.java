package de.metas.purchasecandidate.purchaseordercreation.localorder;

import java.time.ZonedDateTime;
import java.util.Comparator;

import org.adempiere.warehouse.WarehouseId;

import de.metas.bpartner.BPartnerId;
import de.metas.organization.OrgId;
import de.metas.purchasecandidate.PurchaseCandidate;
import de.metas.purchasecandidate.purchaseordercreation.remotepurchaseitem.PurchaseOrderItem;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Value
@Builder
public final class PurchaseOrderAggregationKey implements Comparable<PurchaseOrderAggregationKey>
{
	private final OrgId orgId;
	private final WarehouseId warehouseId;
	private final BPartnerId vendorId;
	private final ZonedDateTime datePromised;

	private static final Comparator<PurchaseOrderAggregationKey> COMPARATOR = Comparator.comparing(PurchaseOrderAggregationKey::getOrgId)
			.thenComparing(PurchaseOrderAggregationKey::getWarehouseId)
			.thenComparing(PurchaseOrderAggregationKey::getVendorId)
			.thenComparing(PurchaseOrderAggregationKey::getDatePromised);

	public static PurchaseOrderAggregationKey fromPurchaseOrderItem(@NonNull final PurchaseOrderItem purchaseOrderItem)
	{
		return PurchaseOrderAggregationKey.builder()
				.orgId(purchaseOrderItem.getOrgId())
				.warehouseId(purchaseOrderItem.getWarehouseId())
				.vendorId(purchaseOrderItem.getVendorId())
				.datePromised(purchaseOrderItem.getDatePromised())
				.build();
	}

	public static PurchaseOrderAggregationKey fromPurchaseCandidate(@NonNull final PurchaseCandidate purchaseCandidate)
	{
		return PurchaseOrderAggregationKey.builder()
				.orgId(purchaseCandidate.getOrgId())
				.warehouseId(purchaseCandidate.getWarehouseId())
				.vendorId(purchaseCandidate.getVendorId())
				.datePromised(purchaseCandidate.getPurchaseDatePromised())
				.build();
	}

	public static PurchaseOrderAggregationKey cast(final Object obj)
	{
		return (PurchaseOrderAggregationKey)obj;
	}

	@Override
	public int compareTo(@NonNull final PurchaseOrderAggregationKey other)
	{
		return COMPARATOR.compare(this, other);
	}
}
