/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.workplace;

import com.google.common.collect.ImmutableSet;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.inout.PriorityRule;
import de.metas.order.OrderPickingType;
import de.metas.picking.api.PickingSlotId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.shipping.CarrierProductId;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

@Value
public class Workplace
{
	@NonNull WorkplaceId id;
	@NonNull String name;
	@NonNull WarehouseId warehouseId;
	@Nullable LocatorId pickFromLocatorId;
	@Nullable PickingSlotId pickingSlotId;
	@NonNull SeqNo seqNo;
	@Nullable PriorityRule priorityRule;
	@Nullable OrderPickingType orderPickingType;
	int maxPickingJobs;

	@NonNull ImmutableSet<ProductId> productIds;
	@NonNull ImmutableSet<ProductCategoryId> productCategoryIds;
	@NonNull ImmutableSet<CarrierProductId> carrierProductIds;
	@NonNull ImmutableSet<ExternalSystemId> externalSystemIds;

	@Builder
	private Workplace(
			@NonNull final WorkplaceId id,
			@NonNull final String name,
			@NonNull final WarehouseId warehouseId,
			@Nullable final LocatorId pickFromLocatorId,
			@Nullable final PickingSlotId pickingSlotId,
			@NonNull final SeqNo seqNo,
			@Nullable final PriorityRule priorityRule,
			@Nullable final OrderPickingType orderPickingType,
			final int maxPickingJobs,
			@Nullable final ImmutableSet<ProductId> productIds,
			@Nullable final ImmutableSet<ProductCategoryId> productCategoryIds,
			@Nullable final ImmutableSet<CarrierProductId> carrierProductIds,
			@Nullable final ImmutableSet<ExternalSystemId> externalSystemIds)
	{
		if (pickFromLocatorId != null)
		{
			pickFromLocatorId.assetWarehouseId(warehouseId);
		}

		this.id = id;
		this.name = name;
		this.warehouseId = warehouseId;
		this.pickFromLocatorId = pickFromLocatorId;
		this.pickingSlotId = pickingSlotId;
		this.seqNo = seqNo;
		this.priorityRule = priorityRule;
		this.orderPickingType = orderPickingType;
		this.maxPickingJobs = maxPickingJobs;

		this.productIds = productIds != null ? productIds : ImmutableSet.of();
		this.productCategoryIds = productCategoryIds != null ? productCategoryIds : ImmutableSet.of();
		this.carrierProductIds = carrierProductIds != null ? carrierProductIds : ImmutableSet.of();
		this.externalSystemIds = externalSystemIds != null ? externalSystemIds : ImmutableSet.of();
	}
}
