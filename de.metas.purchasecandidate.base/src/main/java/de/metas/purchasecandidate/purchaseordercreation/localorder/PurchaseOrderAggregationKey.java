package de.metas.purchasecandidate.purchaseordercreation.localorder;

import java.sql.Timestamp;

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
/* package */ final class PurchaseOrderAggregationKey
{
	public static PurchaseOrderAggregationKey formPurchaseOrderItem(
			@NonNull final PurchaseOrderItem purchaseOrderItem)
	{
		return PurchaseOrderAggregationKey.builder()
				.orgId(purchaseOrderItem.getOrgId())
				.warehouseId(purchaseOrderItem.getWarehouseId())
				.vendorBPartnerId(purchaseOrderItem.getVendorProductInfo().getVendorBPartnerId())
				.datePromisedMillis(purchaseOrderItem.getDatePromised().getTime())
				.build();
	}

	public static PurchaseOrderAggregationKey cast(final Object obj)
	{
		return (PurchaseOrderAggregationKey)obj;
	}

	private final int orgId;
	private final int warehouseId;
	private final int vendorBPartnerId;
	private final long datePromisedMillis;

	public Timestamp getDatePromised()
	{
		return new Timestamp(datePromisedMillis);
	}
}
