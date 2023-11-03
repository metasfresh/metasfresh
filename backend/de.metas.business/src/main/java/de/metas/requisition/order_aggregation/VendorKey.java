package de.metas.requisition.order_aggregation;

import de.metas.bpartner.BPartnerId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
class VendorKey
{
	@Nullable BPartnerId presetVendorId;
	@Nullable ProductId productId;
	int C_Charge_ID;
}
