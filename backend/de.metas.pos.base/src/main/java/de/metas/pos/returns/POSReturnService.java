package de.metas.pos.returns;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.inout.returns.ReturnedGoodsWarehouseType;
import de.metas.handlingunits.inout.returns.ReturnsServiceFacade;
import de.metas.handlingunits.inout.returns.customer.CustomerReturnLineCandidate;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceService;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.InvoiceRule;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentId;
import de.metas.payment.TenderType;
import de.metas.payment.api.IPaymentBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.pos.POSCashJournal;
import de.metas.pos.POSCashJournalId;
import de.metas.pos.POSCashJournalService;
import de.metas.pos.POSProduct;
import de.metas.pos.POSProductsService;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalId;
import de.metas.pos.POSTerminalService;
import de.metas.tax.api.Tax;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Receives goods handed back at the till (a customer return, no HUs, no prior sales order) and prices the
 * credit at the till's price for each returned product — phase 1 of the POS product-return flow.
 */
@Service
@RequiredArgsConstructor
public class POSReturnService
{
	private static final AdMessageKey MSG_NoLines = AdMessageKey.of("de.metas.pos.Return.NoLines");
	private static final AdMessageKey MSG_QtyMustBePositive = AdMessageKey.of("de.metas.pos.Return.QtyMustBePositive");
	private static final AdMessageKey MSG_InvoiceCandidateError = AdMessageKey.of("de.metas.pos.Return.InvoiceCandidateError");
	private static final AdMessageKey MSG_NoTaxFound = AdMessageKey.of("de.metas.pos.Return.NoTaxFound");
	private static final AdMessageKey MSG_PriceUomMismatch = AdMessageKey.of("de.metas.pos.Return.PriceUomMismatch");
	private static final AdMessageKey MSG_CurrencyMismatch = AdMessageKey.of("de.metas.pos.Return.CurrencyMismatch");
	private static final AdMessageKey MSG_NotACreditMemo = AdMessageKey.of("de.metas.pos.Return.NotACreditMemo");
	private static final AdMessageKey MSG_TillBusy = AdMessageKey.of("de.metas.pos.Return.TillBusy");

	/** How long {@link #createReturn} waits to acquire the terminal's cross-transaction lock before rejecting
	 * with {@link #MSG_TillBusy} — see {@code 5826360_POS_Return_TillBusyMessageAndLockTimeout.sql}. */
	private static final String SYSCONFIG_LockTimeoutMillis = "de.metas.pos.Return.LockTimeoutMillis";
	private static final int SYSCONFIG_LockTimeoutMillis_DEFAULT = 30_000;

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	@NonNull private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
	@NonNull private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	@NonNull private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	@NonNull private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	@NonNull private final POSTerminalService posTerminalService;
	@NonNull private final ReturnsServiceFacade returnsServiceFacade;
	@NonNull private final POSReturnRepository returnRepository;
	@NonNull private final InvoiceService invoiceService;
	@NonNull private final POSCashJournalService posCashJournalService;
	@NonNull private final POSProductsService posProductsService;

	/**
	 * Phase 1 (goods receipt + pricing the credit candidates), phase 2 (generating the credit memo) and phase 3
	 * (cash refund + journal) run as three SEPARATE top-level transactions, not one — {@link #ensureCreditMemo}
	 * waits synchronously for an async invoice-candidate workpackage that reads the candidates from a different
	 * DB connection, so phase 1's changes (the {@code PriceEntered_Override} etc.) must already be committed by
	 * the time phase 2 runs, exactly like the {@code updateInvalid()} visibility issue phase 1 itself works
	 * around (see the class Javadoc history).
	 * <p>
	 * A transaction-scoped row lock cannot span three separate transactions, so the whole method body runs inside
	 * {@link POSTerminalService#runWithCrossTransactionLock}: a Postgres advisory lock, held on its own
	 * dedicated connection for the ENTIRE call, that serializes two concurrent callers against the SAME terminal
	 * end to end — a second caller cannot start ANY phase while a first is still in flight in any of its three,
	 * closing the cross-phase race a phase-1-only lock would leave open. The {@code C_POS} row lock inside phase 1
	 * ({@link POSTerminalService#lockForUpdate}) still runs too, unchanged from before this method existed — it is
	 * now redundant for mutual exclusion (the outer lock already guarantees only one caller is ever inside phase 1
	 * at a time) but is kept as-is since it is exercised directly, on its own, by a dedicated test.
	 * <p>
	 * The lock acquisition is BOUNDED (polled {@code pg_try_advisory_lock}, not a blocking {@code pg_advisory_lock})
	 * — an unbounded wait would let one stuck caller (e.g. a slow/hung async workpackage inside phase 2) freeze
	 * every OTHER return attempt on that till indefinitely. The timeout comes from
	 * {@value #SYSCONFIG_LockTimeoutMillis} (default {@value #SYSCONFIG_LockTimeoutMillis_DEFAULT} ms); on
	 * exhaustion this method rejects with {@link #MSG_TillBusy} rather than hanging.
	 *
	 * @throws AdempiereException if the request itself is invalid ({@code NoLines}/{@code QtyMustBePositive}), if
	 * pricing the credit fails synchronously (UOM/currency mismatch, no tax found, or the candidate is already in
	 * error) — the whole return (goods receipt included) is rolled back — if invoicing does not produce exactly
	 * one credit memo, or if the terminal's cross-transaction lock could not be acquired within the configured
	 * timeout ({@link #MSG_TillBusy}). A failure surfacing only later, from the candidate's own async recompute, is
	 * NOT covered here and does not roll back an already-committed return.
	 */
	@NonNull
	public POSReturnResult createReturn(@NonNull final POSReturnRequest request)
	{
		final int lockTimeoutMillis = sysConfigBL.getIntValue(SYSCONFIG_LockTimeoutMillis, SYSCONFIG_LockTimeoutMillis_DEFAULT);

		return posTerminalService.runWithCrossTransactionLock(
				request.getPosTerminalId(),
				lockTimeoutMillis,
				() -> {
					final ReturnAndCandidates phase1 = trxManager.callInThreadInheritedTrx(() -> ensureReturnAndCandidates(request));

					final InvoiceId creditMemoId = ensureCreditMemo(phase1.getInvoiceCandidateIds());

					return trxManager.callInThreadInheritedTrx(() -> ensureSettlement(request, phase1, creditMemoId));
				},
				() -> new AdempiereException(MSG_TillBusy).setParameter("C_POS_ID", request.getPosTerminalId()));
	}

	/**
	 * Entry point for the {@code POST /api/v2/pos/returns} REST endpoint: the client sends product + qty only
	 * (never a price — AC4e requires the credited amount to be "the till's current price for the returned
	 * quantity", not whatever the client claims), so this resolves each line's price and price UOM from
	 * {@link POSProductsService} before delegating to {@link #createReturn}.
	 *
	 * @throws AdempiereException {@link #MSG_NoLines} if {@code requestedLines} is empty, or if a requested
	 * product has no till price (not on the terminal's price list) — plus everything {@link #createReturn} itself
	 * throws.
	 */
	@NonNull
	public POSReturnResult createReturnFromTillPrices(
			@NonNull final POSTerminalId posTerminalId,
			@NonNull final UUID externalId,
			@NonNull final UserId cashierId,
			@NonNull final List<POSReturnRequestedLine> requestedLines)
	{
		if (requestedLines.isEmpty())
		{
			throw new AdempiereException(MSG_NoLines);
		}

		final Instant evalDate = SystemTime.asInstant();
		final ImmutableSet<ProductId> productIds = requestedLines.stream()
				.map(POSReturnRequestedLine::getProductId)
				.collect(ImmutableSet.toImmutableSet());
		final Map<ProductId, POSProduct> productsById = indexById(posProductsService.getProductsByIds(posTerminalId, evalDate, productIds));

		final CurrencyId currencyId = posTerminalService.getPOSTerminalById(posTerminalId).getCurrencyId();

		final ImmutableList.Builder<POSReturnLine> lines = ImmutableList.builder();
		for (final POSReturnRequestedLine requested : requestedLines)
		{
			final POSProduct product = productsById.get(requested.getProductId());
			if (product == null)
			{
				throw new AdempiereException("No till price found for product").setParameter("M_Product_ID", requested.getProductId());
			}

			final I_C_UOM priceUomRecord = uomDAO.getById(product.getPriceUom().getUomId());
			lines.add(toReturnLine(requested, product, currencyId, priceUomRecord));
		}

		return createReturn(POSReturnRequest.builder()
				.posTerminalId(posTerminalId)
				.externalId(externalId)
				.cashierId(cashierId)
				.lines(lines.build())
				.build());
	}

	@NonNull
	private static Map<ProductId, POSProduct> indexById(@NonNull final List<POSProduct> products)
	{
		final ImmutableMap.Builder<ProductId, POSProduct> builder = ImmutableMap.builder();
		for (final POSProduct product : products)
		{
			builder.put(product.getId(), product);
		}
		return builder.build();
	}

	/**
	 * Turns a client's product+qty (no price, no UOM) into a fully priced {@link POSReturnLine}: priced per
	 * {@link POSProduct#getPriceUom()} — the catch-weight UOM (e.g. kg) when the product is priced by catch
	 * weight, else the product's own UOM (see that method's own Javadoc). Package-private and pure (no DB access
	 * of its own — {@code priceUomRecord} is passed in already resolved) so this mapping is unit-testable
	 * without a real price list.
	 */
	@NonNull
	static POSReturnLine toReturnLine(
			@NonNull final POSReturnRequestedLine requested,
			@NonNull final POSProduct product,
			@NonNull final CurrencyId currencyId,
			@NonNull final I_C_UOM priceUomRecord)
	{
		return POSReturnLine.builder()
				.productId(requested.getProductId())
				.qty(Quantity.of(requested.getQty(), priceUomRecord))
				.price(Money.of(product.getPrice().getAsBigDecimal(), currencyId))
				.priceUomId(product.getPriceUom().getUomId())
				.build();
	}

	/** Phase 1 result: the material document plus the invoice candidates priced for its credit. */
	@Value
	private static class ReturnAndCandidates
	{
		InOutId returnInOutId;
		List<InvoiceCandidateId> invoiceCandidateIds;
	}

	@NonNull
	private ReturnAndCandidates ensureReturnAndCandidates(@NonNull final POSReturnRequest request)
	{
		if (request.getLines().isEmpty())
		{
			throw new AdempiereException(MSG_NoLines);
		}
		request.getLines().forEach(this::assertQtyIsPositive);

		// serializes two concurrent requests against the same terminal (e.g. two in-flight retries carrying the
		// same idempotency key): the second blocks here until the first commits, by which point findReturnIdByExternalId
		// below finds its result instead of racing to create a second document
		posTerminalService.lockForUpdate(request.getPosTerminalId());

		final POSTerminal terminal = posTerminalService.getPOSTerminalById(request.getPosTerminalId());
		final OrgId orgId = terminal.getOrgId();
		final boolean tillPriceListIsTaxIncluded = terminal.isTaxIncluded();
		final String returnExternalId = "POSReturn-" + request.getExternalId();

		final InOutId returnId = returnRepository.findReturnIdByExternalId(returnExternalId)
				.orElseGet(() -> createReturnDocument(request, terminal, orgId, returnExternalId));

		final I_M_InOut returnRecord = inOutDAO.getById(returnId);

		// make sure every line has an invoice candidate — a no-op if they already exist (retry)
		invoiceCandidateHandlerBL.createMissingCandidatesFor(returnRecord);

		// pair request lines with their created return line BY POSITION (both ordered the same way the return was
		// built), so a product returned on two separate lines of the same request (e.g. two different batches) is
		// priced line-by-line rather than by a M_Product_ID lookup that can't tell the lines apart
		final List<I_M_InOutLine> returnLines = inOutDAO.retrieveLines(returnRecord);
		if (returnLines.size() != request.getLines().size())
		{
			throw new AdempiereException("The POS return document does not have exactly one line per request line")
					.setParameter("M_InOut_ID", returnId)
					.setParameter("returnLines", returnLines.size())
					.setParameter("requestLines", request.getLines().size());
		}

		// one query per line (not batched): the return's line count is small (single digits), and this reuses
		// invoiceCandDAO's own canonical, full-semantics lookup (direct match, C_OrderLine_ID, IC-IOL association)
		// rather than re-deriving a narrower query
		final ImmutableList.Builder<I_C_Invoice_Candidate> pricedCandidates = ImmutableList.builder();
		for (int i = 0; i < request.getLines().size(); i++)
		{
			final POSReturnLine line = request.getLines().get(i);
			final I_M_InOutLine returnLine = returnLines.get(i);

			for (final I_C_Invoice_Candidate ic : invoiceCandDAO.retrieveInvoiceCandidatesForInOutLine(returnLine))
			{
				// checked first: a candidate with no tax is left in a degraded state (e.g. no Price_UOM_ID yet),
				// so a UOM/currency check below would fail on that symptom instead of the real cause
				final Tax taxEffective = invoiceCandBL.getTaxEffective(ic);
				if (taxEffective.isTaxNotFound())
				{
					throw new AdempiereException(MSG_NoTaxFound).setParameter("C_Invoice_Candidate_ID", ic.getC_Invoice_Candidate_ID());
				}

				assertPriceUomMatchesCandidate(line, ic);
				assertCurrencyMatchesCandidate(line, ic);

				ic.setPriceEntered_Override(line.getPrice().toBigDecimal());
				ic.setDiscount_Override(BigDecimal.ZERO);
				ic.setIsTaxIncluded_Override(tillPriceListIsTaxIncluded ? "Y" : "N");
				ic.setInvoiceRule_Override(InvoiceRule.Immediate.getCode());

				// the C_Invoice_Candidate model interceptor recomputes PriceActual/NetAmtToInvoice on this save
				invoiceCandDAO.save(ic);

				// tags the candidate for a later, already-committed async recompute; the full IInvoiceCandBL#updateInvalid()
				// is never called synchronously here because it can't see a line created in this same open transaction
				invoiceCandDAO.invalidateCand(ic);

				if (ic.isError())
				{
					throw new AdempiereException(MSG_InvoiceCandidateError, ic.getErrorMsg())
							.setParameter("C_Invoice_Candidate_ID", ic.getC_Invoice_Candidate_ID());
				}

				pricedCandidates.add(ic);
			}
		}

		final ImmutableList.Builder<InvoiceCandidateId> invoiceCandidateIds = ImmutableList.builder();
		for (final I_C_Invoice_Candidate ic : pricedCandidates.build())
		{
			invoiceCandidateIds.add(InvoiceCandidateId.ofRepoId(ic.getC_Invoice_Candidate_ID()));
		}

		return new ReturnAndCandidates(returnId, invoiceCandidateIds.build());
	}

	@NonNull
	private InOutId createReturnDocument(
			@NonNull final POSReturnRequest request,
			@NonNull final POSTerminal terminal,
			@NonNull final OrgId orgId,
			@NonNull final String returnExternalId)
	{
		final LocalDate today = SystemTime.asLocalDate();
		final ZonedDateTime now = SystemTime.asZonedDateTime();

		final ImmutableList<CustomerReturnLineCandidate> candidates = request.getLines()
				.stream()
				.map(line -> CustomerReturnLineCandidate.builder()
						.orgId(orgId)
						.bPartnerLocationId(terminal.getWalkInCustomerShipToBPartnerLocationId())
						.orderId(null)
						.productId(line.getProductId())
						.returnedQty(line.getQty())
						.movementDate(today)
						.dateReceived(now)
						.externalId(returnExternalId)
						.returnedGoodsWarehouseType(ReturnedGoodsWarehouseType.QUALITY_ISSUE)
						.build())
				.collect(ImmutableList.toImmutableList());

		final List<InOutId> createdReturnIds = returnsServiceFacade.createCustomerReturnsFromCandidates(candidates);
		return CollectionUtils.singleElement(createdReturnIds);
	}

	/**
	 * Generates the return's credit memo from its priced invoice candidates if it doesn't exist yet, and asserts
	 * exactly one invoice was produced and that it is a credit memo. A retry finds the invoice already generated:
	 * {@code InvoiceCandBLCreateInvoices} sets {@code C_InvoiceLine.M_InOutLine_ID} from the candidate's own
	 * IC-IOL association when it creates the line, so the existing invoice is found via that back-reference
	 * rather than re-invoicing (which would be a no-op anyway — the candidates have nothing left to invoice —
	 * but would still cost another synchronous wait on the async workpackage).
	 */
	@NonNull
	private InvoiceId ensureCreditMemo(@NonNull final List<InvoiceCandidateId> invoiceCandidateIds)
	{
		// one query per candidate (not batched): the return's candidate count is small (single digits), the same
		// "cardinality is small" trade-off already accepted for the per-line query in ensureReturnAndCandidates above
		final ImmutableSet<InvoiceId> existingInvoiceIds = invoiceCandidateIds.stream()
				.flatMap(icId -> invoiceCandDAO.retrieveIlForIc(icId).stream())
				.map(I_C_InvoiceLine::getC_Invoice_ID)
				.map(InvoiceId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final InvoiceId creditMemoId = existingInvoiceIds.isEmpty()
				? CollectionUtils.singleElement(invoiceService.generateInvoicesFromInvoiceCandidateIds(ImmutableSet.copyOf(invoiceCandidateIds)))
				: CollectionUtils.singleElement(existingInvoiceIds);

		// a customer-return invoice candidate always carries a negative qty/amount (IInvoiceCandidateHandlerBL's
		// M_InOutLine_Handler#getQtyMultiplier flips the sign for a return movement type), and this return's own
		// invoice never mixes with an unrelated candidate (it is its own standalone document, C_Order_ID=null) —
		// InvoiceCandBLCreateInvoices itself asserts a generated invoice's header/line credit-memo status agree, so
		// a genuinely mixed aggregation would already fail loudly there, before this guard is ever reached. Could
		// not fully trace every doc-type-resolution path (e.g. a BPartner-level doc-type override) to prove this
		// guard is unreachable with 100% certainty, so — unlike this file's other internal-consistency guards
		// (e.g. "does not have exactly one line per request line" above) — it gets a proper localized AD_Message
		// rather than a raw exception, in case a cashier ever does see it.
		final I_C_Invoice creditMemo = invoiceDAO.getByIdInTrx(creditMemoId);
		if (!invoiceBL.isCreditMemo(creditMemo))
		{
			throw new AdempiereException(MSG_NotACreditMemo).setParameter("C_Invoice_ID", creditMemoId);
		}

		return creditMemoId;
	}

	/**
	 * Settles the credit memo with a completed outbound cash payment and records the refund on the till's cash
	 * journal — both in the SAME transaction, so a retry can never observe a completed payment with no matching
	 * journal line, or vice versa. {@code DefaultPaymentBuilder#createAndProcess()} both completes the payment
	 * AND allocates it to the credit memo ({@code MPayment#allocateIt()} runs unconditionally from
	 * {@code completeIt()} whenever {@code C_Invoice_ID} is set — no separate {@code IAllocationBL} call is
	 * needed, and {@code IAllocationBL#autoAllocateSpecificPayment} would in fact be a no-op here: it explicitly
	 * skips credit memos).
	 * <p>
	 * Idempotent, AND — since {@link #createReturn} now runs its whole body inside
	 * {@link POSTerminalService#runWithCrossTransactionLock} — no two callers for the SAME terminal ever execute
	 * this method (or any other phase of {@link #createReturn}) concurrently, so a retry (sequential OR a genuinely
	 * concurrent one, blocked by that lock until the first caller is fully done) can never observe a completed
	 * payment with no matching journal line, or vice versa: {@code MPayment#allocateIt()} synchronously sets the
	 * credit memo's {@code IsPaid=Y}, so a retry sees {@code creditMemo.isPaid()} true and reuses the existing
	 * settlement payment instead of creating a second one and a second journal line.
	 */
	@NonNull
	private POSReturnResult ensureSettlement(
			@NonNull final POSReturnRequest request,
			@NonNull final ReturnAndCandidates phase1,
			@NonNull final InvoiceId creditMemoId)
	{
		final POSTerminal terminal = posTerminalService.getPOSTerminalById(request.getPosTerminalId());
		final POSCashJournalId journalId = terminal.getCashJournalIdNotNull();

		final I_C_Invoice creditMemo = invoiceDAO.getByIdInTrx(creditMemoId);
		final Money refundAmount = Money.of(creditMemo.getGrandTotal(), CurrencyId.ofRepoId(creditMemo.getC_Currency_ID()));

		final I_C_Payment payment;
		final POSCashJournal journal;
		if (!creditMemo.isPaid())
		{
			payment = paymentBL.newBuilderOfInvoice(creditMemo)
					.orgBankAccountId(terminal.getCashbookId())
					.tenderType(TenderType.Cash)
					.payAmt(refundAmount.toBigDecimal())
					.dateTrx(SystemTime.asInstant())
					.createAndProcess();

			journal = posCashJournalService.changeJournalById(
					journalId,
					j -> j.addCashInOut(refundAmount.negate(), request.getCashierId(), "Rücknahme " + creditMemo.getDocumentNo()));
		}
		else
		{
			payment = paymentBL.getById(CollectionUtils.singleElement(returnRepository.findSettlementPaymentIds(creditMemoId)));
			journal = posCashJournalService.getById(journalId);
		}

		return POSReturnResult.builder()
				.returnInOutId(phase1.getReturnInOutId())
				.invoiceCandidateIds(phase1.getInvoiceCandidateIds())
				.creditMemoId(creditMemoId)
				.creditMemoDocumentNo(creditMemo.getDocumentNo())
				.refundAmount(refundAmount)
				.paymentId(PaymentId.ofRepoId(payment.getC_Payment_ID()))
				.journal(journal)
				.build();
	}

	private void assertQtyIsPositive(@NonNull final POSReturnLine line)
	{
		if (line.getQty().isZeroOrNegative())
		{
			throw new AdempiereException(MSG_QtyMustBePositive);
		}
	}

	/**
	 * Phase 1 assumes the line's price is entered per the invoice candidate's own price UOM; a mismatch would
	 * silently misprice the credit, so it fails fast instead.
	 */
	// package-private (not private): unit-tested directly in POSReturnServiceTest — the mismatch this guards
	// against can't be produced end to end from the cucumber step, which always derives a matching UOM/currency
	void assertPriceUomMatchesCandidate(@NonNull final POSReturnLine line, @NonNull final I_C_Invoice_Candidate ic)
	{
		final UomId candidatePriceUomId = UomId.ofRepoIdOrNull(ic.getPrice_UOM_ID());
		if (!line.getPriceUomId().equals(candidatePriceUomId))
		{
			throw new AdempiereException(MSG_PriceUomMismatch)
					.setParameter("C_Invoice_Candidate_ID", ic.getC_Invoice_Candidate_ID())
					.setParameter("M_Product_ID", line.getProductId())
					.setParameter("candidatePriceUomId", candidatePriceUomId)
					.setParameter("linePriceUomId", line.getPriceUomId());
		}
	}

	/**
	 * The till price is entered in the terminal's own currency; a mismatch against the invoice candidate's
	 * currency (derived from the walk-in customer's own price list) would silently misprice the credit, so it
	 * fails fast instead.
	 */
	// package-private (not private): unit-tested directly in POSReturnServiceTest, same reason as
	// assertPriceUomMatchesCandidate above
	void assertCurrencyMatchesCandidate(@NonNull final POSReturnLine line, @NonNull final I_C_Invoice_Candidate ic)
	{
		final CurrencyId candidateCurrencyId = CurrencyId.ofRepoId(ic.getC_Currency_ID());
		final CurrencyId lineCurrencyId = line.getPrice().getCurrencyId();
		if (!candidateCurrencyId.equals(lineCurrencyId))
		{
			throw new AdempiereException(MSG_CurrencyMismatch)
					.setParameter("C_Invoice_Candidate_ID", ic.getC_Invoice_Candidate_ID())
					.setParameter("candidateCurrencyId", candidateCurrencyId)
					.setParameter("lineCurrencyId", lineCurrencyId);
		}
	}
}
