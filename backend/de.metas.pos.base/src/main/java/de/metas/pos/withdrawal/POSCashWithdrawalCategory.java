package de.metas.pos.withdrawal;

import de.metas.costing.ChargeId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * An expense category a cashier can take cash out of the till for; backed by a {@code C_Charge}.
 */
@Value
@Builder
public class POSCashWithdrawalCategory
{
	@NonNull ChargeId chargeId;
	@NonNull String name;
}
