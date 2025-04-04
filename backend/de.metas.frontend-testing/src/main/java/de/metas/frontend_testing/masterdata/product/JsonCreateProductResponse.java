package de.metas.frontend_testing.masterdata.product;

import de.metas.ean13.EAN13ProductCode;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

@Value
@Builder
@Jacksonized
public class JsonCreateProductResponse
{
	@NonNull ProductId id;
	@NonNull String productCode;
	@Nullable EAN13ProductCode ean13ProductCode;
}
