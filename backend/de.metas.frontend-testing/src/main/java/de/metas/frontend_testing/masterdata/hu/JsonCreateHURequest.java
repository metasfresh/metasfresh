package de.metas.frontend_testing.masterdata.hu;

import de.metas.frontend_testing.masterdata.Identifier;
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
	@NonNull Identifier product;
	@NonNull Identifier warehouse;
	@NonNull BigDecimal qty;
}
