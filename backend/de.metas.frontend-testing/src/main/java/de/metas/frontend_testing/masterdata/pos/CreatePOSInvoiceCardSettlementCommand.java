package de.metas.frontend_testing.masterdata.pos;

import de.metas.bpartner.BPartnerId;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.invoice.InvoiceId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.pos.POSTerminalId;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlement;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementRepository;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementStatus;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;

/**
 * Creates a {@code C_POS_InvoiceCardSettlement} of an invoice through {@link POSInvoiceCardSettlementRepository} and
 * then sets the requested status, note, error message, booking attempts and abandoned flag directly: the real flow
 * reaches e.g. {@code CR} or {@code BE} only through a SumUp transaction and an asynchronous booking, which the tests
 * of the back-office window do not need to drive. Partner, amount and currency are taken from the invoice.
 */
@Builder
public class CreatePOSInvoiceCardSettlementCommand
{
	@NonNull private final POSInvoiceCardSettlementRepository repository;
	@NonNull private final MasterdataContext context;
	@NonNull private final JsonPOSInvoiceCardSettlementRequest request;
	@NonNull private final Identifier identifier;

	public JsonPOSInvoiceCardSettlementResponse execute()
	{
		final InvoiceId invoiceId = context.getId(request.getInvoice(), InvoiceId.class);
		final I_C_Invoice invoice = InterfaceWrapperHelper.load(invoiceId, I_C_Invoice.class);

		final POSInvoiceCardSettlement created = repository.create(
				MasterdataContext.ORG_ID,
				context.getId(request.getPosTerminal(), POSTerminalId.class),
				invoiceId,
				BPartnerId.ofRepoId(invoice.getC_BPartner_ID()),
				context.getId(request.getCashier(), UserId.class),
				Money.of(invoice.getGrandTotal(), CurrencyId.ofRepoId(invoice.getC_Currency_ID())));

		final POSInvoiceCardSettlement settlement = repository.save(created.toBuilder()
				.status(request.getStatus() != null ? request.getStatus() : POSInvoiceCardSettlementStatus.PENDING)
				.isAbandoned(Boolean.TRUE.equals(request.getIsAbandoned()))
				.note(request.getNote())
				.errorMsg(request.getErrorMsg())
				.bookingAttempts(request.getBookingAttempts() != null ? request.getBookingAttempts() : 0)
				.build());

		return JsonPOSInvoiceCardSettlementResponse.builder()
				.id(settlement.getId().getRepoId())
				.build();
	}
}
