package de.metas.pos.returns;

import com.google.common.collect.ImmutableList;
import de.metas.common.util.time.SystemTime;
import de.metas.handlingunits.inout.returns.ReturnedGoodsWarehouseType;
import de.metas.handlingunits.inout.returns.ReturnsServiceFacade;
import de.metas.handlingunits.inout.returns.customer.CustomerReturnLineCandidate;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.InOutId;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandidateHandlerBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.inout.IInOutDAO;
import de.metas.order.InvoiceRule;
import de.metas.organization.OrgId;
import de.metas.pos.POSTerminal;
import de.metas.pos.POSTerminalId;
import de.metas.pos.POSTerminalService;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

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

	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IInvoiceCandidateHandlerBL invoiceCandidateHandlerBL = Services.get(IInvoiceCandidateHandlerBL.class);
	@NonNull private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	@NonNull private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	@NonNull private final POSTerminalService posTerminalService;
	@NonNull private final ReturnsServiceFacade returnsServiceFacade;
	@NonNull private final POSReturnRepository returnRepository;

	/**
	 * @throws AdempiereException ({@code de.metas.pos.Return.InvoiceCandidateError}) if pricing the credit leaves any
	 * invoice candidate in error; the whole return (goods receipt included) is rolled back
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
		request.getLines().forEach(this::assertPriceUomIsStockUom);

		final POSTerminal terminal = posTerminalService.getPOSTerminalById(request.getPosTerminalId());
		final OrgId orgId = terminal.getOrgId();
		final String returnExternalId = "POSReturn-" + request.getExternalId();

		final InOutId returnId = findExistingReturnId(returnExternalId)
				.orElseGet(() -> createReturnDocument(request, terminal, orgId, returnExternalId));

		final I_M_InOut returnRecord = returnRepository.getReturnById(returnId);

		// make sure every line has an invoice candidate — a no-op if they already exist (retry)
		invoiceCandidateHandlerBL.createMissingCandidatesFor(returnRecord);

		final ImmutableList.Builder<I_C_Invoice_Candidate> pricedCandidates = ImmutableList.builder();
		for (final POSReturnLine line : request.getLines())
		{
			final I_M_InOutLine returnLine = getReturnLineForProduct(returnId, line.getProductId());
			for (final I_C_Invoice_Candidate ic : invoiceCandDAO.retrieveInvoiceCandidatesForInOutLine(returnLine))
			{
				ic.setPriceEntered_Override(line.getPrice().toBigDecimal());
				ic.setInvoiceRule_Override(InvoiceRule.Immediate.getCode());

				// same recompute the framework triggers on save for these columns (InvoiceCandidate modelvalidator
				// updateNetAmtToInvoice) — done explicitly because the full IInvoiceCandBL#updateInvalid() pass
				// re-derives the candidate's M_InOutLine via an out-of-trx read (InvoiceCandInvalidUpdater
				// #populateC_InvoiceCandidate_InOutLine), which cannot see a line created earlier in this same,
				// still-open transaction — invalidateCand() below still tags it for a later, already-committed
				// async recompute, same as InvoiceCandWorkpackageProcessor does after generating invoices.
				invoiceCandBL.setPriceActual_Override(ic);
				invoiceCandBL.setNetAmtToInvoice(ic);

				returnRepository.save(ic);

				invoiceCandDAO.invalidateCand(ic);
				pricedCandidates.add(ic);
			}
		}

		final ImmutableList<I_C_Invoice_Candidate> invoiceCandidates = pricedCandidates.build();

		final ImmutableList.Builder<InvoiceCandidateId> invoiceCandidateIds = ImmutableList.builder();
		for (final I_C_Invoice_Candidate ic : invoiceCandidates)
		{
			InterfaceWrapperHelper.refresh(ic);
			if (ic.isError())
			{
				throw new AdempiereException(MSG_InvoiceCandidateError, ic.getErrorMsg())
						.setParameter("C_Invoice_Candidate_ID", ic.getC_Invoice_Candidate_ID());
			}
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
						.bPartnerLocationId(terminal.getWalkInCustomerShipToLocationId().getBpartnerLocationId())
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

	@NonNull
	private Optional<InOutId> findExistingReturnId(@NonNull final String returnExternalId)
	{
		return queryBL.createQueryBuilder(I_M_InOut.class)
				.addEqualsFilter(I_M_InOut.COLUMNNAME_ExternalId, returnExternalId)
				.create()
				.firstIdOnlyOptional(InOutId::ofRepoIdOrNull);
	}

	@NonNull
	private I_M_InOutLine getReturnLineForProduct(@NonNull final InOutId returnId, @NonNull final ProductId productId)
	{
		return queryBL.createQueryBuilder(I_M_InOutLine.class)
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_InOut_ID, returnId)
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_M_Product_ID, productId)
				.create()
				.firstOnlyNotNull(I_M_InOutLine.class);
	}

	private void assertQtyIsPositive(@NonNull final POSReturnLine line)
	{
		if (line.getQty().isZeroOrNegative())
		{
			throw new AdempiereException(MSG_QtyMustBePositive);
		}
	}

	/**
	 * Phase 1 assumes the line's price is entered per the product's stocking UOM (see the UOM-assumption risk in
	 * the increment's plan); a mismatch would silently misprice the credit, so it fails fast instead.
	 */
	private void assertPriceUomIsStockUom(@NonNull final POSReturnLine line)
	{
		final UomId stockUomId = productBL.getStockUOMId(line.getProductId());
		if (!stockUomId.equals(line.getPriceUomId()))
		{
			throw new AdempiereException("POS return line price UOM must match the product's stocking UOM")
					.setParameter("M_Product_ID", line.getProductId())
					.setParameter("stockUomId", stockUomId)
					.setParameter("priceUomId", line.getPriceUomId());
		}
	}
}
