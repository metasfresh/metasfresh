package de.metas.forex;

import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.sectionCode.SectionCodeId;
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
	@NonNull SectionCodeId contractSectionCodeId;
}
