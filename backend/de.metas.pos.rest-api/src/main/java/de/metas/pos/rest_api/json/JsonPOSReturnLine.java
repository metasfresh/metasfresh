package de.metas.pos.rest_api.json;

import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

/**
 * One product+qty line of a {@link JsonPOSReturnRequest}. Carries NO price and NO UOM: the returned quantity is
 * priced server-side at the till's current price for the product (AC4e), and its price UOM is derived from the
 * product's own pricing (catch-weight UOM when the product is priced by catch weight, else the product's own
 * UOM) — never trusted from the client.
 */
@Value
@Builder
@Jacksonized
public class JsonPOSReturnLine
{
	@NonNull ProductId productId;

	/** Returned quantity, in whichever UOM the product is priced in (see class Javadoc) — the client never states the UOM. */
	@NonNull BigDecimal qty;
}
