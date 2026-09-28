package de.metas.pos.invoice_settlement;

import com.google.common.collect.ImmutableList;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.pos.POSCashJournal;
import de.metas.pos.POSCashJournalService;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalId;
import de.metas.pos.POSTerminalService;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.List;

/**
 * Settles an existing, still-open sales invoice in cash at the till: the customer holds an invoice (e.g. handed to
 * them on a prior visit) and pays it now, at the register. Booked as a completed inbound cash payment on the till's
 * own bank account, auto-allocated against the invoice, with a matching positive cash in/out line on the till's open
 * cash journal — all in one transaction.
 */
@Service
@RequiredArgsConstructor
public class POSInvoiceSettlementService
{
	private static final AdMessageKey MSG_CurrencyMismatch = AdMessageKey.of("de.metas.pos.InvoiceSettlement.CurrencyMismatch");
	private static final AdMessageKey MSG_NoLongerOpen = AdMessageKey.of("de.metas.pos.InvoiceSettlement.NoLongerOpen");
	private static final AdMessageKey MSG_WrongOrg = AdMessageKey.of("de.metas.pos.InvoiceSettlement.WrongOrg");

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	// both IInvoiceDAO and IInvoiceBL are needed: IInvoiceDAO.getByDocumentNo()/getByIdInTrx() have no BL equivalent,
	// while invoiceBL.isCreditMemo() is BL-only — same reasoning for the IAllocationDAO/IAllocationBL pair below
	// (IAllocationDAO.retrieveOpenAmtInInvoiceCurrency() has no BL equivalent; IAllocationBL.autoAllocateSpecificPayment() is BL-only)
	@NonNull private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IAllocationDAO allocationDAO = Services.get(IAllocationDAO.class);
	@NonNull private final IAllocationBL allocationBL = Services.get(IAllocationBL.class);
	@NonNull private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	@NonNull private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@NonNull private final POSTerminalService posTerminalService;
	@NonNull private final POSCashJournalService posCashJournalService;

	/**
	 * @return the terminal org's sales invoices matching {@code documentNo} that are completed/closed, not yet
	 * fully paid, and not a credit memo — i.e. eligible for cash settlement at the till. A document number is not
	 * necessarily unique across doc types, so more than one candidate may come back; each is filtered
	 * independently, never assumed to be the only match.
	 */
	@NonNull
	public List<POSOpenInvoice> findOpenInvoices(@NonNull final POSTerminalId posTerminalId, @NonNull final String documentNo)
	{
		final POSTerminal terminal = posTerminalService.getPOSTerminalById(posTerminalId);
		final OrgId orgId = terminal.getOrgId();
		final ZoneId zoneId = orgDAO.getTimeZone(orgId);

		final ImmutableList.Builder<POSOpenInvoice> result = ImmutableList.builder();
		for (final I_C_Invoice invoice : invoiceDAO.getByDocumentNo(documentNo, orgId, I_C_Invoice.class))
		{
			if (isEligibleForCashSettlement(invoice))
			{
				result.add(toOpenInvoice(invoice, zoneId));
			}
		}
		return result.build();
	}

	private boolean isEligibleForCashSettlement(@NonNull final I_C_Invoice invoice)
	{
		return invoice.isSOTrx()
				&& DocStatus.ofCode(invoice.getDocStatus()).isCompletedOrClosed()
				&& !invoice.isPaid()
				&& !invoiceBL.isCreditMemo(invoice);
	}

	@NonNull
	private POSOpenInvoice toOpenInvoice(@NonNull final I_C_Invoice invoice, @NonNull final ZoneId zoneId)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
		final CurrencyId currencyId = CurrencyId.ofRepoId(invoice.getC_Currency_ID());

		return POSOpenInvoice.builder()
				.invoiceId(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
				.documentNo(invoice.getDocumentNo())
				.bpartnerName(bpartnerDAO.getBPartnerNameById(bpartnerId))
				.dateInvoiced(TimeUtil.asLocalDate(invoice.getDateInvoiced(), zoneId))
				.grandTotal(Money.of(invoice.getGrandTotal(), currencyId))
				.openAmt(allocationDAO.retrieveOpenAmtInInvoiceCurrency(invoice, true))
				.build();
	}

	/**
	 * @throws AdempiereException ({@code de.metas.pos.InvoiceSettlement.NoLongerOpen}) if the invoice is no longer
	 * open (e.g. already settled by a concurrent request) by the time this transaction re-reads it. This is a
	 * best-effort optimistic re-check (a plain re-read, no row lock) — it narrows, but does not eliminate, the race
	 * between two concurrent settlement requests for the same invoice; it is not a hard concurrency guarantee.
	 * @throws AdempiereException ({@code de.metas.pos.InvoiceSettlement.CurrencyMismatch}) if the invoice's currency
	 * differs from the terminal's — checked BEFORE the payment is created: a foreign-currency invoice would put a
	 * wrongly-denominated amount into the till's own-currency cash journal, and {@link Money#assertCurrencyId} inside
	 * {@link POSCashJournal#addCashInOut} would throw only after the payment had already been completed in this same
	 * transaction, leaving a completed payment with no matching journal line.
	 * @throws AdempiereException ({@code de.metas.pos.InvoiceSettlement.WrongOrg}) if the invoice does not belong to
	 * the terminal's org — {@code request.getInvoiceId()} is client-supplied and, unlike {@link #findOpenInvoices},
	 * this method has no other org gate; without this check a client-supplied invoiceId from another org would be
	 * settled using THIS terminal's cashbook/cashier, bypassing the org scoping that {@link #findOpenInvoices} alone
	 * would otherwise enforce.
	 */
	@NonNull
	public POSInvoiceSettleResult settleInCash(@NonNull final POSInvoiceSettleRequest request)
	{
		return trxManager.callInThreadInheritedTrx(() -> settleInCashInTrx(request));
	}

	@NonNull
	private POSInvoiceSettleResult settleInCashInTrx(@NonNull final POSInvoiceSettleRequest request)
	{
		final POSTerminal terminal = posTerminalService.getPOSTerminalById(request.getPosTerminalId());

		// re-read inside this transaction: the amount looked up by the caller earlier (e.g. via findOpenInvoices,
		// in its own transaction) may be stale by the time the cashier confirms the settlement
		final I_C_Invoice invoice = invoiceDAO.getByIdInTrx(request.getInvoiceId());

		// request.getInvoiceId() is client-supplied and, unlike findOpenInvoices (which scopes to terminal.getOrgId()),
		// this method has no other org gate: without this check, a client-supplied invoiceId from another org would be
		// settled using THIS terminal's cashbook/cashier, bypassing the org scoping findOpenInvoices otherwise enforces.
		final OrgId invoiceOrgId = OrgId.ofRepoId(invoice.getAD_Org_ID());
		if (!invoiceOrgId.equals(terminal.getOrgId()))
		{
			throw new AdempiereException(MSG_WrongOrg)
					.setParameter("C_Invoice_ID", invoice.getC_Invoice_ID())
					.setParameter("invoiceOrgId", invoiceOrgId)
					.setParameter("terminalOrgId", terminal.getOrgId());
		}

		final Money open = allocationDAO.retrieveOpenAmtInInvoiceCurrency(invoice, true);
		if (open.signum() <= 0)
		{
			throw new AdempiereException(MSG_NoLongerOpen).setParameter("C_Invoice_ID", invoice.getC_Invoice_ID());
		}

		final CurrencyId invoiceCurrencyId = open.getCurrencyId();
		if (!invoiceCurrencyId.equals(terminal.getCurrencyId()))
		{
			throw new AdempiereException(MSG_CurrencyMismatch)
					.setParameter("C_Invoice_ID", invoice.getC_Invoice_ID())
					.setParameter("invoiceCurrencyId", invoiceCurrencyId)
					.setParameter("terminalCurrencyId", terminal.getCurrencyId());
		}

		final OrgId orgId = terminal.getOrgId();
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
		final Instant dateTrx = SystemTime.asInstant();

		final I_C_Payment payment = paymentBL.newInboundReceiptBuilder()
				.adOrgId(orgId)
				.orgBankAccountId(terminal.getCashbookId())
				.bpartnerId(bpartnerId)
				.payAmt(open.toBigDecimal())
				.currencyId(invoiceCurrencyId)
				.tenderType(TenderType.Cash)
				.dateTrx(dateTrx)
				.createAndProcess();

		allocationBL.autoAllocateSpecificPayment(invoice, payment, true);

		final String description = "Rechnung " + invoice.getDocumentNo();
		final POSCashJournal journal = posCashJournalService.changeJournalById(
				terminal.getCashJournalIdNotNull(),
				cashJournal -> cashJournal.addCashInOut(open, request.getCashierId(), description));

		return POSInvoiceSettleResult.builder()
				.paymentId(PaymentId.ofRepoId(payment.getC_Payment_ID()))
				.amount(open)
				.journal(journal)
				.build();
	}
}
