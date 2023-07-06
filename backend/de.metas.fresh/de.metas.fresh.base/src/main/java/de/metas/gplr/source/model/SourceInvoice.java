package de.metas.gplr.source.model;

import de.metas.currency.Amount;
import de.metas.invoice.InvoiceId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.paymentinstructions.PaymentInstructions;
import de.metas.payment.paymentterm.PaymentTerm;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Value
@Builder
public class SourceInvoice
{
	@NonNull InvoiceId id;
	@NonNull OrderId orderId;
	@NonNull OrgId orgId;
	@NonNull String documentNo;
	@NonNull String docTypeName;
	@NonNull SourceCurrencyInfo currencyInfo;
	@NonNull SourceUserInfo createdBy;
	@NonNull LocalDateAndOrgId created;
	@NonNull LocalDateAndOrgId dateInvoiced;
	@Nullable String sapProductHierarchy;
	@NonNull PaymentTerm paymentTerm;
	@Nullable PaymentInstructions paymentInstructions;
	@Nullable LocalDateAndOrgId dueDate;
	@Nullable String invoiceAdditionalText;
	@NonNull Amount linesNetAmtFC;
	@NonNull Amount taxAmtFC;

	@NonNull List<SourceInvoiceLine> lines;

	public Optional<SourceInvoiceLine> getLineByOrderLineId(@NonNull final OrderLineId orderLineId)
	{
		return lines.stream()
				.filter(line -> OrderLineId.equals(line.getOrderLineId(), orderLineId))
				.findFirst();
	}

}
