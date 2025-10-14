package de.metas.inventory.mobileui.rest_api.json;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inventory.InventoryLineHUId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonInventoryLineHU
{
	@NonNull InventoryLineHUId id;

	@NonNull ProductId productId;
	@NonNull String productNo;
	@NonNull String productName;

	@NonNull Integer locatorId;
	@NonNull String locatorName;

	@Nullable HuId huId;
	@Nullable String huDisplayName;

	@NonNull String uom;
	@NonNull BigDecimal qtyBooked;
	@NonNull BigDecimal qtyCount;
}
