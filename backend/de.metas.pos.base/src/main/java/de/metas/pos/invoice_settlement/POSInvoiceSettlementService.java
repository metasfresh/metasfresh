package de.metas.pos.invoice_settlement;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
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
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlement;
import de.metas.pos.invoice_settlement.card.POSInvoiceCardSettlementRepository;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.dao.ForUpdate;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
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
	private static final AdMessageKey MSG_TenderedTooLow = AdMessageKey.of("de.metas.pos.InvoiceSettlement.TenderedTooLow");
	private static final AdMessageKey MSG_NotEligibleForSettlement = AdMessageKey.of("de.metas.pos.InvoiceSettlement.NotEligibleForSettlement");
	private static final AdMessageKey MSG_AllocationIncomplete = AdMessageKey.of("de.metas.pos.InvoiceSettlement.AllocationIncomplete");
	private static final AdMessageKey MSG_JournalDescription = AdMessageKey.of("de.metas.pos.InvoiceSettlement.JournalDescription");
	private static final AdMessageKey MSG_CardPaymentStillOpen = AdMessageKey.of("de.metas.pos.InvoiceCardSettlement.CardPaymentStillOpen");

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
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
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);

	@NonNull private final POSTerminalService posTerminalService;
	@NonNull private final POSCashJournalService posCashJournalService;
	@NonNull private final POSInvoiceCardSettlementRepository cardSettlementRepository;

	/**
	 * @return the terminal org's sales invoices whose {@code DocumentNo} <b>ends with</b> {@code documentNo}
	 * (the till numeric keypad enters only the trailing numeric part of an alphanumeric-prefixed invoice number,
	 * so the search matches on the keyed suffix) and that are completed/closed, not yet fully paid, and not a
	 * credit memo — i.e. eligible for cash settlement at the till. A suffix match is not necessarily unique, so
	 * more than one candidate may come back; each is filtered independently, never assumed to be the only match.
	 */
	@NonNull
	public List<POSOpenInvoice> findOpenInvoices(@NonNull final POSTerminalId posTerminalId, @NonNull final String documentNo)
	{
		// The till keypad enters digits only, so the search term is a trailing numeric suffix. Trim it and reject
		// anything that is not a non-empty run of digits: this closes the SQL-wildcard hole (a REST caller sending
		// '%' would otherwise LIKE-match every invoice of the org) and the untrimmed-space miss (" 170245" would
		// otherwise never match). An invalid term finds nothing rather than dumping or erroring.
		final String searchSuffix = documentNo.trim();
		if (!isValidSearchSuffix(searchSuffix))
		{
			return ImmutableList.of();
		}

		final POSTerminal terminal = posTerminalService.getPOSTerminalById(posTerminalId);
		final OrgId orgId = terminal.getOrgId();
		final ZoneId zoneId = orgDAO.getTimeZone(orgId);

		final ImmutableList<I_C_Invoice> eligibleInvoices = invoiceDAO.getByDocumentNoEndingWith(searchSuffix, orgId, I_C_Invoice.class)
				.stream()
				.filter(this::isEligibleForCashSettlement)
				.collect(ImmutableList.toImmutableList());
		if (eligibleInvoices.isEmpty())
		{
			return ImmutableList.of();
		}

		final ImmutableMap<InvoiceId, POSInvoiceCardSettlement> blockingCardSettlements = cardSettlementRepository.findBlockingByInvoiceIds(
				eligibleInvoices.stream()
						.map(invoice -> InvoiceId.ofRepoId(invoice.getC_Invoice_ID()))
						.collect(ImmutableSet.toImmutableSet()));

		return eligibleInvoices.stream()
				.map(invoice -> toOpenInvoice(invoice, zoneId, blockingCardSettlements))
				.collect(ImmutableList.toImmutableList());
	}

	private static boolean isValidSearchSuffix(@NonNull final String searchSuffix)
	{
		if (searchSuffix.isEmpty())
		{
			return false;
		}
		for (int i = 0; i < searchSuffix.length(); i++)
		{
			if (!Character.isDigit(searchSuffix.charAt(i)))
			{
				return false;
			}
		}
		return true;
	}

	@VisibleForTesting
	boolean isEligibleForCashSettlement(@NonNull final I_C_Invoice invoice)
	{
		return isCompletedSalesInvoice(invoice)
				&& !invoice.isPaid();
	}

	/**
	 * The <i>structural</i> half of {@link #isEligibleForCashSettlement}: a completed/closed, non-credit-memo SALES
	 * invoice. Deliberately excludes {@code IsPaid} — the settle path uses this (not the full eligibility check) so
	 * that an already-paid but otherwise valid invoice is reported as {@code NoLongerOpen} (open amount zero, the
	 * legitimate "paid on another till in the meantime" case) rather than {@code NotEligibleForSettlement}, which is
	 * reserved for an invoice that could never be settled at the till at all (purchase / drafted / credit memo).
	 */
	@VisibleForTesting
	boolean isCompletedSalesInvoice(@NonNull final I_C_Invoice invoice)
	{
		return invoice.isSOTrx()
				&& DocStatus.ofCode(invoice.getDocStatus()).isCompletedOrClosed()
				&& !invoiceBL.isCreditMemo(invoice);
	}

	@NonNull
	private POSOpenInvoice toOpenInvoice(
			@NonNull final I_C_Invoice invoice,
			@NonNull final ZoneId zoneId,
			@NonNull final ImmutableMap<InvoiceId, POSInvoiceCardSettlement> blockingCardSettlements)
	{
		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(invoice.getC_BPartner_ID());
		final CurrencyId currencyId = CurrencyId.ofRepoId(invoice.getC_Currency_ID());

		return POSOpenInvoice.builder()
				.invoiceId(invoiceId)
				.documentNo(invoice.getDocumentNo())
				.bpartnerName(bpartnerDAO.getBPartnerNameById(bpartnerId))
				.dateInvoiced(TimeUtil.asLocalDate(invoice.getDateInvoiced(), zoneId))
				.grandTotal(Money.of(invoice.getGrandTotal(), currencyId))
				.openAmt(getOpenAmt(invoice))
				.blockingCardSettlement(blockingCardSettlements.get(invoiceId))
				.build();
	}

	/**
	 * Settles the open invoice in its own transaction. Every guard — eligibility, open-amount, currency, tender, org —
	 * fires BEFORE any payment is created, so a rejected settlement never leaves an orphaned payment or cash-journal
	 * line behind. Rationale for each lives at the guard it names.
	 * @throws AdempiereException {@code NoLongerOpen} — the open amount is already {@code <= 0} when re-read in-trx
	 * (e.g. settled meanwhile by a concurrent request); see {@link #getByIdInTrxWithRowLock}.
	 * @throws AdempiereException {@code NotEligibleForSettlement} — not a completed/closed, non-credit-memo SALES
	 * invoice; see {@link #isCompletedSalesInvoice}.
	 * @throws AdempiereException {@code AllocationIncomplete} — the invoice stayed open after auto-allocation; the
	 * throw rolls the whole transaction back (post-condition check in {@link #settleInCashInTrx}).
	 * @throws AdempiereException {@code CurrencyMismatch} — invoice currency differs from the terminal's; see
	 * {@link #assertInvoiceCurrencyMatchesTerminal}.
	 * @throws AdempiereException {@code WrongOrg} — the invoice belongs to another org; see
	 * {@link #assertInvoiceBelongsToTerminalOrg}.
	 * @throws AdempiereException {@code TenderedTooLow} — a non-{@code null}
	 * {@link POSInvoiceSettleRequest#getCashTenderedAmount()} is less than the open amount (a {@code null} tendered
	 * amount skips the check and settles the exact open amount).
	 */
	@NonNull
	public POSInvoiceSettleResult settleInCash(@NonNull final POSInvoiceSettleRequest request)
	{
		return trxManager.callInThreadInheritedTrx(() -> settleInCashInTrx(request));
	}

	/**
	 * Rejects settling an invoice that belongs to a different org than the terminal's. {@code request.getInvoiceId()}
	 * is client-supplied and, unlike {@link #findOpenInvoices} (which scopes its query to the terminal's org), the
	 * settle path has no other org gate — without this check a client-supplied invoiceId from another org would be
	 * settled using THIS terminal's cashbook/cashier, bypassing that scoping.
	 */
	@VisibleForTesting
	void assertInvoiceBelongsToTerminalOrg(@NonNull final I_C_Invoice invoice, @NonNull final POSTerminal terminal)
	{
		final OrgId invoiceOrgId = OrgId.ofRepoId(invoice.getAD_Org_ID());
		if (!invoiceOrgId.equals(terminal.getOrgId()))
		{
			throw new AdempiereException(MSG_WrongOrg)
					.setParameter("C_Invoice_ID", invoice.getC_Invoice_ID())
					.setParameter("invoiceOrgId", invoiceOrgId)
					.setParameter("terminalOrgId", terminal.getOrgId());
		}
	}

	/**
	 * Rejects settling an invoice whose currency differs from the terminal's — the cash drawer, cashbook and cash
	 * journal all operate in the terminal's currency. Checked before the payment is created: otherwise
	 * {@link Money#assertCurrencyId} inside {@link POSCashJournal#addCashInOut} would throw only after the payment was
	 * already completed, leaving a completed payment with no matching journal line.
	 */
	@VisibleForTesting
	void assertInvoiceCurrencyMatchesTerminal(
			@NonNull final I_C_Invoice invoice,
			@NonNull final CurrencyId invoiceCurrencyId,
			@NonNull final POSTerminal terminal)
	{
		if (!invoiceCurrencyId.equals(terminal.getCurrencyId()))
		{
			throw new AdempiereException(MSG_CurrencyMismatch)
					.setParameter("C_Invoice_ID", invoice.getC_Invoice_ID())
					.setParameter("invoiceCurrencyId", invoiceCurrencyId)
					.setParameter("terminalCurrencyId", terminal.getCurrencyId());
		}
	}

	@NonNull
	private POSInvoiceSettleResult settleInCashInTrx(@NonNull final POSInvoiceSettleRequest request)
	{
		final POSTerminal terminal = posTerminalService.getPOSTerminalById(request.getPosTerminalId());

		final SettleableInvoice settleableInvoice = lockAndAssertSettleable(terminal, request.getInvoiceId());
		final I_C_Invoice invoice = settleableInvoice.getInvoice();
		final Money open = settleableInvoice.getOpen();
		final CurrencyId invoiceCurrencyId = open.getCurrencyId();

		final BigDecimal cashTenderedAmount = request.getCashTenderedAmount();
		if (cashTenderedAmount != null)
		{
			final Money tendered = Money.of(cashTenderedAmount, invoiceCurrencyId);
			if (tendered.isLessThan(open))
			{
				throw new AdempiereException(MSG_TenderedTooLow)
						.setParameter("C_Invoice_ID", invoice.getC_Invoice_ID())
						.setParameter("open", open)
						.setParameter("cashTenderedAmount", tendered);
			}
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

		allocateFully(invoice, payment);

		final String description = getJournalDescription(MSG_JournalDescription, invoice);
		final POSCashJournal journal = posCashJournalService.changeJournalById(
				terminal.getCashJournalIdNotNull(),
				cashJournal -> cashJournal.addCashInOut(open, request.getCashierId(), description));

		return POSInvoiceSettleResult.builder()
				.paymentId(PaymentId.ofRepoId(payment.getC_Payment_ID()))
				.amount(open)
				.documentNo(invoice.getDocumentNo())
				.journal(journal)
				.build();
	}

	/**
	 * Allocates the payment to the invoice and throws {@code AllocationIncomplete} if the invoice is still open
	 * afterwards; must run in the transaction that books the payment, so the throw rolls the booking back.
	 */
	public void allocateFully(@NonNull final I_C_Invoice invoice, @NonNull final I_C_Payment payment)
	{
		allocateUpToOpenAmt(invoice, payment);

		// post-condition: autoAllocateSpecificPayment silently no-ops on a receipt↔purchase mismatch, a
		// non-financial invoice, or an already-paid one. If it left the invoice open, the payment + journal line
		// would be booked against an invoice that is still unpaid — throw so the whole transaction rolls back.
		final Money openAfterAllocation = getOpenAmt(invoice);
		if (openAfterAllocation.signum() != 0)
		{
			throw new AdempiereException(MSG_AllocationIncomplete)
					.setParameter("C_Invoice_ID", invoice.getC_Invoice_ID())
					.setParameter("openAfterAllocation", openAfterAllocation);
		}
	}

	/**
	 * Allocates as much of the payment to the invoice as the invoice is open for, i.e. the smaller of the payment's
	 * available amount and the invoice's open amount. Does nothing if the invoice cannot take the payment (already
	 * paid, a credit memo, another partner, ...).
	 */
	public void allocateUpToOpenAmt(@NonNull final I_C_Invoice invoice, @NonNull final I_C_Payment payment)
	{
		allocationBL.autoAllocateSpecificPayment(invoice, payment, true);
	}

	/**
	 * @return the part of the payment that is allocated to the invoice, in the given currency (that of both documents);
	 * zero if {@code paymentId} is {@code null}
	 */
	@NonNull
	public Money getAllocatedAmt(@NonNull final InvoiceId invoiceId, @Nullable final PaymentId paymentId, @NonNull final CurrencyId currencyId)
	{
		if (paymentId == null)
		{
			return Money.zero(currencyId);
		}
		return Money.of(allocationDAO.retrieveAllocatedAmtBetween(invoiceId, paymentId), currencyId);
	}

	/**
	 * @return {@code true} if the invoice is still a completed/closed sales invoice with an open amount &gt; 0; a
	 * reversed, voided or fully paid invoice is not
	 */
	public boolean isStillOpen(@NonNull final I_C_Invoice invoice)
	{
		return isCompletedSalesInvoice(invoice) && getOpenAmt(invoice).signum() > 0;
	}

	/**
	 * @return the invoice's open amount in the invoice currency (allocations and discounts considered)
	 */
	@NonNull
	public Money getOpenAmt(@NonNull final I_C_Invoice invoice)
	{
		return allocationDAO.retrieveOpenAmtInInvoiceCurrency(invoice, true);
	}

	/**
	 * @param messageKey the AD_Message of the line text, with the invoice's document number as its only parameter
	 *                   ({@code {0}}); the cash and the card settlement each have their own
	 * @return the description of the till's cash journal line that books a settlement of the invoice
	 */
	@NonNull
	public String getJournalDescription(@NonNull final AdMessageKey messageKey, @NonNull final I_C_Invoice invoice)
	{
		return msgBL.getMsg(
				Env.getADLanguageOrBaseLanguage(),
				messageKey,
				new Object[] { invoice.getDocumentNo() });
	}

	/**
	 * The guards shared by the cash and the card settlement start. Must be called inside a transaction: it
	 * re-reads the invoice under a row lock (see {@link #getByIdInTrxWithRowLock}) — the caller's earlier open amount
	 * may be stale, and the lock serializes concurrent settlements of the same invoice — and then, in this order:
	 * <ol>
	 * <li>refuses while a card settlement of the invoice is unfinished ({@link #assertNoBlockingCardSettlement}),</li>
	 * <li>the org ({@link #assertInvoiceBelongsToTerminalOrg}),</li>
	 * <li>eligibility ({@link #isCompletedSalesInvoice}: invoiceId is client-supplied and this path is not org-scoped
	 * by findOpenInvoices; IsPaid is excluded, so an already-paid invoice falls through to the open-amount check as
	 * {@code NoLongerOpen}),</li>
	 * <li>the open amount ({@code NoLongerOpen}) and</li>
	 * <li>the currency ({@link #assertInvoiceCurrencyMatchesTerminal}).</li>
	 * </ol>
	 */
	@NonNull
	public SettleableInvoice lockAndAssertSettleable(@NonNull final POSTerminal terminal, @NonNull final InvoiceId invoiceId)
	{
		// the row lock below is released at once outside a transaction, which would silently void the serialization
		trxManager.assertThreadInheritedTrxExists();

		final I_C_Invoice invoice = getByIdInTrxWithRowLock(invoiceId);

		assertNoBlockingCardSettlement(invoiceId);

		assertInvoiceBelongsToTerminalOrg(invoice, terminal);

		if (!isCompletedSalesInvoice(invoice))
		{
			throw new AdempiereException(MSG_NotEligibleForSettlement).setParameter("C_Invoice_ID", invoice.getC_Invoice_ID());
		}

		final Money open = getOpenAmt(invoice);
		if (open.signum() <= 0)
		{
			throw new AdempiereException(MSG_NoLongerOpen).setParameter("C_Invoice_ID", invoice.getC_Invoice_ID());
		}

		assertInvoiceCurrencyMatchesTerminal(invoice, open.getCurrencyId(), terminal);

		return new SettleableInvoice(invoice, open);
	}

	/**
	 * Refuses a new payment of the invoice while a card settlement of it is unfinished. Call it
	 * right after the invoice lock, in the same transaction.
	 */
	private void assertNoBlockingCardSettlement(@NonNull final InvoiceId invoiceId)
	{
		final POSInvoiceCardSettlement blocking = cardSettlementRepository.findBlockingByInvoiceId(invoiceId).orElse(null);
		if (blocking != null)
		{
			throw new AdempiereException(MSG_CardPaymentStillOpen)
					.setParameter("C_Invoice_ID", invoiceId)
					.setParameter("C_POS_InvoiceCardSettlement_ID", blocking.getId())
					.setParameter("status", blocking.getStatus());
		}
	}

	/**
	 * Takes the invoice row lock that every settlement path takes first (lock order: invoice before settlement)
	 * and returns the invoice as read under that lock. Must be called inside a transaction.
	 */
	@NonNull
	public I_C_Invoice lockInvoice(@NonNull final InvoiceId invoiceId)
	{
		trxManager.assertThreadInheritedTrxExists();
		return getByIdInTrxWithRowLock(invoiceId);
	}

	@Value
	public static class SettleableInvoice
	{
		@NonNull I_C_Invoice invoice;
		/** Open amount (&gt; 0), in the invoice's own currency. */
		@NonNull Money open;
	}

	/**
	 * Re-reads the invoice inside the current transaction with a {@code FOR NO KEY UPDATE} row lock, so two
	 * concurrent settlements of the same invoice serialize on this row (the settle path never changes the invoice's
	 * primary key, only {@code IsPaid}, so the weaker {@code FOR NO KEY UPDATE} is enough — see {@link ForUpdate}).
	 */
	@NonNull
	private I_C_Invoice getByIdInTrxWithRowLock(@NonNull final InvoiceId invoiceId)
	{
		return queryBL.createQueryBuilder(I_C_Invoice.class)
				.addEqualsFilter(I_C_Invoice.COLUMNNAME_C_Invoice_ID, invoiceId)
				.create()
				.setForUpdate(ForUpdate.FOR_NO_KEY_UPDATE)
				.firstOnlyNotNull(I_C_Invoice.class);
	}
}
