package de.metas.forex;

import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
public class ForexContractAllocateRequest
{
	@NonNull ForexContractId forexContractId;
	@NonNull OrgId orgId;
	@NonNull OrderId orderId;
	@NonNull Money amount;
}
