package de.metas.pos.returns;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.inout.returns.ReturnedGoodsWarehouseType;
import de.metas.handlingunits.inout.returns.ReturnsServiceFacade;
import de.metas.handlingunits.inout.returns.customer.CustomerReturnLineCandidate;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.CurrencyId;
import de.metas.order.InvoiceRule;
import de.metas.organization.OrgId;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalService;
import de.metas.tax.api.Tax;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

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

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	@NonNull private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
	@NonNull private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	@NonNull private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	@NonNull private final POSTerminalService posTerminalService;
	@NonNull private final ReturnsServiceFacade returnsServiceFacade;
	@NonNull private final POSReturnRepository returnRepository;

	/**
	 * @throws AdempiereException if the request itself is invalid ({@code NoLines}/{@code QtyMustBePositive}), or if
	 * pricing the credit fails synchronously (UOM/currency mismatch, no tax found, or the candidate is already in
	 * error) — the whole return (goods receipt included) is rolled back. A failure surfacing only later, from the
	 * candidate's own async recompute, is NOT covered here and does not roll back an already-committed return.
	 */
	@NonNull
	public POSReturnResult createReturn(@NonNull final POSReturnRequest request)
	{
		return trxManager.callInThreadInheritedTrx(() -> ensureReturnAndCandidates(request));
	}

	@NonNull
	private POSReturnResult ensureReturnAndCandidates(@NonNull final POSReturnRequest request)
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
		// rather than re-deriving a narrower query — see task report for the deliberate trade-off
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

		return POSReturnResult.builder()
				.returnInOutId(returnId)
				.invoiceCandidateIds(invoiceCandidateIds.build())
				.build();
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
