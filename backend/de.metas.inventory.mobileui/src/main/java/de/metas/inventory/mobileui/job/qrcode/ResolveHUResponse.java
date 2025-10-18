package de.metas.inventory.mobileui.job.qrcode;

import de.metas.handlingunits.HuId;
import de.metas.inventory.InventoryLineId;
import de.metas.inventory.mobileui.deps.products.Attributes;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;

@Value
@Builder
public class ResolveHUResponse
{
	@NonNull ScannedCode scannedCode;
	@NonNull InventoryLineId lineId;
	@NonNull LocatorId locatorId;
	@Nullable HuId huId;
	@NonNull ProductId productId;
	@NonNull Quantity qtyBooked;
	boolean isCounted;
	@Nullable Quantity qtyCount;
	@NonNull Attributes attributes;
}
