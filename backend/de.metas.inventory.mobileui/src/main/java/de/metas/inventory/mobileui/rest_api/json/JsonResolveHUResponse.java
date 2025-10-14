package de.metas.inventory.mobileui.rest_api.json;

import de.metas.handlingunits.HuId;
import de.metas.inventory.InventoryLineId;
import de.metas.product.ProductId;
import de.metas.scannable_code.ScannedCode;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonResolveHUResponse
{
	@NonNull ScannedCode scannedCode;
	@Nullable InventoryLineId lineId;
	@Nullable HuId huId;
	@Nullable String huDisplayName;
	@NonNull LocatorId locatorId;
	@NonNull String locatorName;
	@NonNull ProductId productId;
	@NonNull String productNo;
	@NonNull String productName;
	@NonNull String uom;
	@NonNull BigDecimal qtyBooked;
	@NonNull List<JsonResolveHUResponseAttribute> attributes;

}
