package de.metas.workplace;

import com.google.common.collect.ImmutableSet;
import de.metas.externalsystem.ExternalSystemId;
import de.metas.order.OrderPickingType;
import de.metas.picking.api.PickingSlotId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.shipping.CarrierProductId;
import de.metas.util.lang.SeqNo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;

import javax.annotation.Nullable;

@Value
@Builder
public class WorkplaceCreateRequest
{
	@NonNull String name;
	@NonNull WarehouseId warehouseId;
	@Nullable LocatorId pickFromLocatorId;
	@Nullable PickingSlotId pickingSlotId;
	@Nullable SeqNo seqNo;
	@Nullable OrderPickingType orderPickingType;
	int maxPickingJobs;

	@NonNull @Singular ImmutableSet<ProductCategoryId> productCategoryIds;
	@NonNull @Singular ImmutableSet<ProductId> productIds;
	@NonNull @Singular ImmutableSet<ExternalSystemId> externalSystemIds;
	@NonNull @Singular ImmutableSet<CarrierProductId> carrierProductIds;
}
