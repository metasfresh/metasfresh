package de.metas.forex;

import de.metas.currency.Amount;
import de.metas.money.Money;
import de.metas.order.OrderId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ForexContractAllocation
{
	@NonNull ForexContractAllocationId id;
	@NonNull ForexContractId contractId;
	@NonNull OrderId orderId;
	@NonNull Money amount;
}
