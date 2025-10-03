package de.metas.inventory.mobileui.rest_api.json;

import de.metas.i18n.ITranslatableString;
import de.metas.inventory.InventoryLineId;
import de.metas.inventory.mobileui.job.InventoryJobLine;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonInventoryJobLine
{
	@NonNull InventoryLineId id;
	@NonNull String caption;

	@NonNull ProductId productId;
	@NonNull String productNo;
	@NonNull ITranslatableString productName;

	@NonNull Integer locatorId;
	@NonNull String locatorName;

	@NonNull String uom;
	@NonNull BigDecimal qtyBooked;
	@NonNull BigDecimal qtyCount;

	static JsonInventoryJobLine of(final InventoryJobLine line)
	{
		return builder()
				.id(line.getId())
				.caption(line.getProductNo() + "_" + line.getProductName())
				.productId(line.getProductId())
				.productNo(line.getProductNo())
				.productName(line.getProductName())
				.locatorId(line.getLocatorId().getRepoId())
				.locatorName(line.getLocatorName())
				.uom(line.getUOMSymbol())
				.qtyBooked(line.getQtyBooked().toBigDecimal())
				.qtyCount(line.getQtyCount().toBigDecimal())
				.build();
	}
}
