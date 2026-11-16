package de.metas.pos.payment_gateway;

import de.metas.currency.Amount;
import de.metas.organization.ClientAndOrgId;
import de.metas.pos.POSOrderAndPaymentId;
import de.metas.pos.POSTerminalPaymentProcessorConfig;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

/**
 * Exactly one of {@link #getPosOrderAndPaymentId()} / {@link #getPosInvoiceCardSettlementId()} is set.
 */
@Value
public class POSPaymentProcessRequest
{
	@NonNull POSTerminalPaymentProcessorConfig paymentProcessorConfig;
	@NonNull ClientAndOrgId clientAndOrgId;
	@Nullable POSOrderAndPaymentId posOrderAndPaymentId;
	@Nullable POSInvoiceCardSettlementId posInvoiceCardSettlementId;
	@Nullable String description;
	@NonNull Amount amount;

	@Builder
	private POSPaymentProcessRequest(
			@NonNull final POSTerminalPaymentProcessorConfig paymentProcessorConfig,
			@NonNull final ClientAndOrgId clientAndOrgId,
			@Nullable final POSOrderAndPaymentId posOrderAndPaymentId,
			@Nullable final POSInvoiceCardSettlementId posInvoiceCardSettlementId,
			@Nullable final String description,
			@NonNull final Amount amount)
	{
		Check.assume((posOrderAndPaymentId == null) != (posInvoiceCardSettlementId == null),
				"Exactly one of posOrderAndPaymentId and posInvoiceCardSettlementId must be set");

		this.paymentProcessorConfig = paymentProcessorConfig;
		this.clientAndOrgId = clientAndOrgId;
		this.posOrderAndPaymentId = posOrderAndPaymentId;
		this.posInvoiceCardSettlementId = posInvoiceCardSettlementId;
		this.description = description;
		this.amount = amount;
	}
}
