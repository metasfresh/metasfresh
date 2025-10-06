package de.metas.inventory.mobileui.job.qrcode;

import de.metas.handlingunits.HuId;
import de.metas.inventory.InventoryLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;

@Value
@Builder
public class ResolveHUResponse
{
	@NonNull InventoryLineId lineId;
	@Nullable HuId huId;
	@NonNull ProductId productId;
	@NonNull Quantity qtyBooked;

	boolean hasBestBeforeDateAttribute;
	@Nullable LocalDate bestBeforeDate;
	boolean hasLotNoAttribute;
	@Nullable String lotNo;
}
