package de.metas.frontend_testing.masterdata.product;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonCreateProductResponse
{
	String productCode;
	String upc;
}
