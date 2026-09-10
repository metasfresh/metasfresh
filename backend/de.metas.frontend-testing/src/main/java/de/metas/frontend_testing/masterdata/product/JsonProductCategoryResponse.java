package de.metas.frontend_testing.masterdata.product;

import de.metas.product.ProductCategoryId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonProductCategoryResponse
{
	@NonNull ProductCategoryId id;
}
