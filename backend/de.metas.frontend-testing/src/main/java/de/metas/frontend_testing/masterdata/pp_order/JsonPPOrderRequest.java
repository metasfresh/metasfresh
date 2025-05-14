package de.metas.frontend_testing.masterdata.pp_order;

import de.metas.frontend_testing.masterdata.Identifier;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Value
@Builder
@Jacksonized
public class JsonPPOrderRequest
{
	@NonNull Identifier warehouse;
	@NonNull Identifier product;
	@NonNull BigDecimal qty;
	@NonNull ZonedDateTime datePromised;
}
