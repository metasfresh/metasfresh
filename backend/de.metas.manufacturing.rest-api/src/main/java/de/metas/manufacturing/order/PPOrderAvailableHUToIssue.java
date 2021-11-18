package de.metas.manufacturing.order;

import de.metas.handlingunits.HuId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

@Value
@Builder
public class PPOrderAvailableHUToIssue
{
	@NonNull LocatorId locatorId;
	@NonNull HuId huId;
	@NonNull ProductId productId;
	@NonNull Quantity availableQty;
}
