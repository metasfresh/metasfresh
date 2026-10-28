package de.metas.pos.withdrawal;

import de.metas.costing.ChargeId;
import de.metas.money.Money;
import de.metas.pos.POSTerminalId;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class POSCashWithdrawalRequest
{
	@NonNull POSTerminalId posTerminalId;
	@NonNull UserId cashierId;
	@NonNull ChargeId chargeId;

	/**
	 * Gross amount taken out of the till, in the terminal's currency; must be greater than zero.
	 */
	@NonNull Money amount;
}
