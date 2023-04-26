package de.metas.forex;

import de.metas.money.Money;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

@Value
public class ForexContractAllocateRequest
{
	@NonNull ForexContractId forexContractId;
	@NonNull OrgId orgId;
	@NonNull OrderId orderId;

	@NonNull Money orderGrandTotal;
	@NonNull Money amountToAllocate;

	@Builder
	private ForexContractAllocateRequest(
			@NonNull final ForexContractId forexContractId,
			@NonNull final OrgId orgId,
			@NonNull final OrderId orderId,
			@NonNull final Money orderGrandTotal,
			@NonNull final Money amountToAllocate)
	{
		Money.assertSameCurrency(orderGrandTotal, amountToAllocate);

		if (amountToAllocate.signum() <= 0)
		{
			throw new AdempiereException("Amount to allocate shall be greater than zero");
		}

		this.forexContractId = forexContractId;
		this.orgId = orgId;
		this.orderId = orderId;
		this.orderGrandTotal = orderGrandTotal;
		this.amountToAllocate = amountToAllocate;
	}
}
