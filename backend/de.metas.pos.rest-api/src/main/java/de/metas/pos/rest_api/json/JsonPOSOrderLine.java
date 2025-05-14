package de.metas.pos.rest_api.json;

import de.metas.pos.POSOrderLine;
import de.metas.pos.remote.RemotePOSOrderLine;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonPOSOrderLine
{
	@NonNull String uuid;

	@NonNull ProductId productId;
	@NonNull String productName;
	@Nullable String scannedBarcode;
	@NonNull TaxCategoryId taxCategoryId;

	@Nullable String currencySymbol;
	@NonNull BigDecimal price;

	@NonNull BigDecimal qty;
	@NonNull UomId uomId;
	@NonNull String uomSymbol;

	@Nullable BigDecimal catchWeight;
	@Nullable UomId catchWeightUomId;
	@Nullable String catchWeightUomSymbol;

	@Nullable BigDecimal amount;

	int hashCode;

	public static JsonPOSOrderLine of(@NonNull final POSOrderLine line, @NonNull final String currencySymbol)
	{
		return builder()
				.uuid(line.getExternalId())
				.productId(line.getProductId())
				.productName(line.getProductName())
				.scannedBarcode(line.getScannedBarcode())
				.taxCategoryId(line.getTaxCategoryId())
				.currencySymbol(currencySymbol)
				.price(line.getPrice().toBigDecimal())
				.qty(line.getQty().toBigDecimal())
				.uomId(line.getQty().getUomId())
				.uomSymbol(line.getQty().getUOMSymbol())
				.catchWeight(line.getCatchWeight() != null ? line.getCatchWeight().toBigDecimal() : null)
				.catchWeightUomId(line.getCatchWeight() != null ? line.getCatchWeight().getUomId() : null)
				.catchWeightUomSymbol(line.getCatchWeight() != null ? line.getCatchWeight().getUOMSymbol() : null)
				.amount(line.getAmount().toBigDecimal())
				.hashCode(line.hashCode())
				.build();
	}

	RemotePOSOrderLine toRemotePOSOrderLine()
	{
		return RemotePOSOrderLine.builder()
				.uuid(uuid)
				.productId(productId)
				.productName(productName)
				.scannedBarcode(scannedBarcode)
				.taxCategoryId(taxCategoryId)
				.price(price)
				.qty(qty)
				.uomId(uomId)
				.catchWeight(catchWeight)
				.catchWeightUomId(catchWeightUomId)
				.build();
	}

}