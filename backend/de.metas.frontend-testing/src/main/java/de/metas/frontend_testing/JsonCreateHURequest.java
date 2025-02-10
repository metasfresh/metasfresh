package de.metas.frontend_testing;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonCreateHURequest
{
	@NonNull String productCode;
	@NonNull String warehouseCode;
	@NonNull BigDecimal qty;
}
