package de.metas.frontend_testing.masterdata.product;

import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.uom.X12DE355;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@Jacksonized
public class JsonCreateProductRequest
{
	@Nullable X12DE355 uom;
	@Nullable List<UOMConversion> uomConversions;
	@Nullable List<Price> prices;

	//
	//
	//

	@Value
	@Builder
	@Jacksonized
	public static class UOMConversion
	{
		@NonNull X12DE355 from;
		@NonNull X12DE355 to;
		@NonNull BigDecimal multiplyRate;
		boolean isCatchUOMForProduct;
	}

	@Value
	@Builder
	@Jacksonized
	public static class Price
	{
		@NonNull BigDecimal price;
		@Nullable X12DE355 uom;
		@Nullable InvoicableQtyBasedOn invoicableQtyBasedOn;
	}
}
