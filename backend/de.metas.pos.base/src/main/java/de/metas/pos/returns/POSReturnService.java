package de.metas.pos.returns;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.inout.returns.ReturnedGoodsWarehouseType;
import de.metas.handlingunits.inout.returns.ReturnsServiceFacade;
import de.metas.handlingunits.inout.returns.customer.CustomerReturnLineCandidate;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceService;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.lang.SOTrx;
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
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
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
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Receives goods handed back at the till (a walk-in customer return, no prior sales order) and prices the
 * credit at the till's own current price for each returned product.
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
	private static final AdMessageKey MSG_NoTillPrice = AdMessageKey.of("de.metas.pos.Return.NoTillPrice");
	private static final AdMessageKey MSG_RetryContentMismatch = AdMessageKey.of("de.metas.pos.Return.RetryContentMismatch");
	private static final AdMessageKey MSG_JournalDescription = AdMessageKey.of("de.metas.pos.Return.JournalDescription");

	/** How long {@link #createReturn} waits to acquire the terminal's cross-transaction lock before rejecting
	 * with {@link #MSG_TillBusy}. */
	private static final String SYSCONFIG_LockTimeoutMillis = "de.metas.pos.Return.LockTimeoutMillis";
	private static final int SYSCONFIG_LockTimeoutMillis_DEFAULT = 30_000;

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	@NonNull private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
	@NonNull private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
	@NonNull private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	@NonNull private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final ITaxBL taxBL = Services.get(ITaxBL.class);
	@NonNull private final IMsgBL msgBL = Services.get(IMsgBL.class);

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
	 * around.
	 * <p>
	 * A transaction-scoped row lock cannot span three separate transactions, so the whole method body runs inside
	 * {@link POSTerminalService#runWithCrossTransactionLock}: a Postgres advisory lock, held on its own
	 * dedicated connection for the ENTIRE call, that serializes two concurrent callers against the SAME terminal
	 * end to end — a second caller cannot start ANY phase while a first is still in flight in any of its three,
	 * closing the cross-phase race a phase-1-only lock would leave open.
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
	 * Entry point for the {@code POST /api/v2/pos/returns} REST endpoint: the client sends product + qty only,
	 * never a price — the credited amount per line must be the till's own current price for the returned
	 * quantity, not whatever the client claims — so this resolves each line's price and price UOM from
	 * {@link POSProductsService} before delegating to {@link #createReturn}.
	 *
	 * @throws AdempiereException {@link #MSG_NoLines} if {@code requestedLines} is empty, {@link #MSG_NoTillPrice}
	 * if a requested product is not on the terminal's current price list — plus everything {@link #createReturn}
	 * itself throws.
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
				throw new AdempiereException(MSG_NoTillPrice).setParameter("M_Product_ID", requested.getProductId());
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
	 * weight, else the product's own UOM (see that method's own Javadoc). {@code priceUomRecord} is passed in
	 * already resolved, so the mapping does no DB access of its own.
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

		final POSTerminal terminal = posTerminalService.getPOSTerminalById(request.getPosTerminalId());
		final OrgId orgId = terminal.getOrgId();
		final boolean tillPriceListIsTaxIncluded = terminal.isTaxIncluded();
		final String returnExternalId = "POSReturn-" + request.getExternalId();

		final Optional<InOutId> existingReturnId = returnRepository.findReturnIdByExternalId(returnExternalId);
		final boolean isRetry = existingReturnId.isPresent();
		final InOutId returnId = existingReturnId
				.orElseGet(() -> createReturnDocument(request, terminal, orgId, returnExternalId));

		final I_M_InOut returnRecord = inOutDAO.getById(returnId);

		// pair request lines with their created return line BY POSITION (both ordered the same way the return was
		// built), so a product returned on two separate lines of the same request (e.g. two different batches) is
		// priced line-by-line rather than by a M_Product_ID lookup that can't tell the lines apart
		final List<I_M_InOutLine> returnLines = inOutDAO.retrieveLines(returnRecord);

		// a retry (same externalId) must be for the SAME cart: if the cashier edited the cart (changed a qty,
		// swapped/added/removed a product) and pressed pay-out again, that is a NEW operation — resolving back to the
		// already-recorded return would refund the OLD amount while the UI shows the edited total, so reject it here
		// instead of silently reusing the document. A genuine retry of the unchanged cart passes and stays idempotent.
		if (isRetry)
		{
			assertExistingReturnMatchesRequest(returnLines, request);
		}

		// make sure every line has an invoice candidate — a no-op if they already exist (retry)
		invoiceCandidateHandlerBL.createMissingCandidatesFor(returnRecord);

		if (returnLines.size() != request.getLines().size())
		{
			throw new AdempiereException("The POS return document does not have exactly one line per request line")
					.setParameter("M_InOut_ID", returnId)
					.setParameter("returnLines", returnLines.size())
					.setParameter("requestLines", request.getLines().size());
		}

		// resolved from the TILL's own price list (not the walk-in customer's sales price list, which is what
		// the candidate's own tax category was derived from and can lack a price row for a till-only-priced
		// product) — keyed once for the whole request, same set of products as the return's own lines
		final Map<ProductId, POSProduct> tillProductsById = indexById(posProductsService.getProductsByIds(
				request.getPosTerminalId(),
				SystemTime.asInstant(),
				request.getLines().stream().map(POSReturnLine::getProductId).collect(ImmutableSet.toImmutableSet())));

		final ImmutableList.Builder<I_C_Invoice_Candidate> pricedCandidates = ImmutableList.builder();
		for (int i = 0; i < request.getLines().size(); i++)
		{
			final POSReturnLine line = request.getLines().get(i);
			final I_M_InOutLine returnLine = returnLines.get(i);

			for (final I_C_Invoice_Candidate ic : invoiceCandBL.retrieveInvoiceCandidatesForInOutLine(returnLine))
			{
				// idempotent retry: a candidate already invoiced onto the credit memo must NOT be re-priced or
				// re-invalidated — rewriting its overrides (and flagging a fresh async recompute) on an
				// already-invoiced candidate is exactly the corruption the retry guard above shields the cashier
				// from. It carries the price the credit memo was built on, so reuse it verbatim.
				if (isRetry
						&& !invoiceCandBL.retrieveIlForIc(InvoiceCandidateId.ofRepoId(ic.getC_Invoice_Candidate_ID())).isEmpty())
				{
					pricedCandidates.add(ic);
					continue;
				}

				// checked first: a candidate with no tax is left in a degraded state (e.g. no Price_UOM_ID yet),
				// so a UOM/currency check below would fail on that symptom instead of the real cause. A
				// not-found tax means the product's price row on the walk-in customer's own sales pricing
				// system carries a tax category with no applicable C_Tax (e.g. it's priced there only for
				// catalog/inventory reasons, never actually sold to a walk-in customer at that rate) — resolve
				// the real tax the same way a normal sale/return of this product to this ship-to would, from
				// the till's own price list, and pin it via C_Tax_Override_ID instead of rejecting the return.
				final Tax taxEffective = invoiceCandBL.getTaxEffective(ic);
				if (taxEffective.isTaxNotFound())
				{
					final TaxId resolvedCreditTaxId = resolveCreditTaxId(ic, tillProductsById, terminal, orgId);
					// pins the tax the SAME way a normal sale/return of this product to this ship-to would
					// resolve it — from the till's own price list, not the walk-in customer's — so the
					// interceptor recompute triggered by this very save (and any later recompute) sees it via
					// InvoiceCandBL#getTaxEffective (override checked first)
					ic.setC_Tax_Override_ID(resolvedCreditTaxId.getRepoId());
				}

				assertPriceUomMatchesCandidate(line, ic);
				assertCurrencyMatchesCandidate(line, ic);

				ic.setPriceEntered_Override(line.getPrice().toBigDecimal());
				ic.setDiscount_Override(BigDecimal.ZERO);
				ic.setIsTaxIncluded_Override(tillPriceListIsTaxIncluded ? "Y" : "N");
				ic.setInvoiceRule_Override(InvoiceRule.Immediate.getCode());

				// the C_Invoice_Candidate model interceptor recomputes PriceActual/NetAmtToInvoice on this save
				invoiceCandBL.save(ic);

				// tags the candidate for a later, already-committed async recompute; the full IInvoiceCandBL#updateInvalid()
				// is never called synchronously here because it can't see a line created in this same open transaction
				invoiceCandBL.invalidateCand(ic);

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

	/**
	 * Resolves the tax for a credit candidate whose own {@code C_Tax_ID} came back not-found — the case for a
	 * product priced ONLY on the till's pricing system: the candidate's tax was derived from the walk-in
	 * customer's own sales pricing system (via the standard {@code M_InOutLine_Handler} invoice-candidate pricing),
	 * which has no {@code M_ProductPrice} row (hence no tax category) for such a product.
	 * <p>
	 * Resolves it the same way a normal sale/return of that product to that ship-to would: by the product's OWN
	 * tax category (read from the TILL's price list via {@code tillProductsById} — the price list this credit is
	 * actually priced against), the return's org, the terminal's walk-in-customer ship-to location, "now", and
	 * {@link SOTrx#SALES} (a customer return is priced as the sales-side leg of the transaction). Mirrors
	 * {@code M_InOutLine_Handler#calculatePriceAndTax}'s own {@code ITaxBL#getTaxNotNull} call, just against the
	 * till's price list's tax category instead of the walk-in customer's.
	 *
	 * @throws AdempiereException {@link #MSG_NoTaxFound} if even this explicit resolution comes back
	 * not-found — a genuine tax-configuration gap (e.g. no {@code C_Tax} row covers the product's tax category
	 * for this org/country/date), which the till cannot recover from on its own.
	 */
	@NonNull
	private TaxId resolveCreditTaxId(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final Map<ProductId, POSProduct> tillProductsById,
			@NonNull final POSTerminal terminal,
			@NonNull final OrgId orgId)
	{
		final ProductId productId = ProductId.ofRepoId(ic.getM_Product_ID());
		final POSProduct tillProduct = tillProductsById.get(productId);
		final TaxCategoryId taxCategoryId = tillProduct != null ? tillProduct.getTaxCategoryId() : null;

		final TaxId taxId = taxBL.getTaxNotNull(
				ic,
				taxCategoryId,
				productId.getRepoId(),
				SystemTime.asTimestamp(),
				orgId,
				null, // warehouseId: not needed here — getTaxNotNull only uses it as a country fallback, and the org's own country already covers that fallback chain
				terminal.getWalkInCustomerShipToLocationId(),
				SOTrx.SALES);

		if (taxId.isNoTaxId())
		{
			throw new AdempiereException(MSG_NoTaxFound).setParameter("C_Invoice_Candidate_ID", ic.getC_Invoice_Candidate_ID());
		}
		return taxId;
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
		final ImmutableSet<InvoiceId> existingInvoiceIds = invoiceCandidateIds.stream()
				.flatMap(icId -> invoiceCandBL.retrieveIlForIc(icId).stream())
				.map(I_C_InvoiceLine::getC_Invoice_ID)
				.map(InvoiceId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());

		final InvoiceId creditMemoId = existingInvoiceIds.isEmpty()
				? CollectionUtils.singleElement(invoiceService.generateInvoicesFromInvoiceCandidateIds(ImmutableSet.copyOf(invoiceCandidateIds)))
				: CollectionUtils.singleElement(existingInvoiceIds);

		// a customer-return candidate is sign-flipped to a negative qty/amount and invoiced as its own standalone
		// document (C_Order_ID=null), so the generated invoice must be a credit memo; guard it with a localized
		// message in case a doc-type misconfiguration ever yields otherwise
		final I_C_Invoice creditMemo = invoiceBL.getById(creditMemoId);
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
	 * this method (or any other phase of {@link #createReturn}) concurrently. {@code MPayment#allocateIt()}
	 * synchronously sets the credit memo's {@code IsPaid=Y}, so a retry sees {@code creditMemo.isPaid()} true and
	 * reuses the existing settlement payment instead of creating a second one and a second journal line.
	 */
	@NonNull
	private POSReturnResult ensureSettlement(
			@NonNull final POSReturnRequest request,
			@NonNull final ReturnAndCandidates phase1,
			@NonNull final InvoiceId creditMemoId)
	{
		final POSTerminal terminal = posTerminalService.getPOSTerminalById(request.getPosTerminalId());
		final POSCashJournalId journalId = terminal.getCashJournalIdNotNull();

		final I_C_Invoice creditMemo = invoiceBL.getById(creditMemoId);
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

			final String journalDescription = msgBL.getMsg(
					Env.getAD_Language(),
					MSG_JournalDescription,
					new Object[] { creditMemo.getDocumentNo() });
			journal = posCashJournalService.changeJournalById(
					journalId,
					j -> j.addCashInOut(refundAmount.negate(), request.getCashierId(), journalDescription));
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

	/**
	 * A retry (same {@code externalId}) must be for the SAME cart. When {@link POSReturnRepository#findReturnIdByExternalId}
	 * resolves an existing return, its lines must still match the request position-by-position — the product plus the
	 * entered quantity and its UOM (mirroring how {@code CustomerReturnInOutRecordFactory} persists each request line:
	 * {@code M_Product_ID}, {@code QtyEntered}, {@code C_UOM_ID}). An edited cart (a changed qty, a swapped/added/removed
	 * product) is a NEW operation: reusing the already-recorded return would refund its OLD amount while the UI shows the
	 * edited total, so it is rejected with {@link #MSG_RetryContentMismatch} rather than silently reused.
	 */
	void assertExistingReturnMatchesRequest(
			@NonNull final List<I_M_InOutLine> existingReturnLines,
			@NonNull final POSReturnRequest request)
	{
		final List<POSReturnLine> requestLines = request.getLines();
		if (existingReturnLines.size() != requestLines.size())
		{
			throw new AdempiereException(MSG_RetryContentMismatch)
					.setParameter("ExternalId", request.getExternalId())
					.setParameter("existingReturnLines", existingReturnLines.size())
					.setParameter("requestLines", requestLines.size());
		}

		for (int i = 0; i < requestLines.size(); i++)
		{
			final POSReturnLine requestLine = requestLines.get(i);
			final I_M_InOutLine existingLine = existingReturnLines.get(i);

			final boolean sameProduct = existingLine.getM_Product_ID() == requestLine.getProductId().getRepoId();
			final boolean sameUom = existingLine.getC_UOM_ID() == requestLine.getQty().getUomId().getRepoId();
			final boolean sameQty = existingLine.getQtyEntered().compareTo(requestLine.getQty().toBigDecimal()) == 0;

			if (!sameProduct || !sameUom || !sameQty)
			{
				throw new AdempiereException(MSG_RetryContentMismatch)
						.setParameter("ExternalId", request.getExternalId())
						.setParameter("position", i)
						.setParameter("M_InOutLine_ID", existingLine.getM_InOutLine_ID())
						.setParameter("existingProductId", existingLine.getM_Product_ID())
						.setParameter("requestProductId", requestLine.getProductId().getRepoId())
						.setParameter("existingQtyEntered", existingLine.getQtyEntered())
						.setParameter("requestQty", requestLine.getQty().toBigDecimal());
			}
		}
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
