package de.metas.pos.rest_api.json;

import de.metas.costing.ChargeId;
import de.metas.pos.POSTerminalId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;

@Value
@Builder
@Jacksonized
public class JsonCashWithdrawalRequest
{
	@NonNull POSTerminalId posTerminalId;
	@NonNull ChargeId chargeId;

	/**
	 * Gross amount to take out of the till, in the terminal's currency; must be greater than zero.
	 */
	@NonNull BigDecimal amount;
}
