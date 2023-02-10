package de.metas.invoice.matchinv.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.InvoiceLineId;
import de.metas.invoice.matchinv.MatchInvId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.order.OrderLineId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MatchInvoiceService
{
	public static MatchInvoiceService get() {return SpringContextHolder.instance.getBean(MatchInvoiceService.class);}

	public static MatchInvoiceService newInstanceForJUnitTesting() {return new MatchInvoiceService(new MatchInvoiceRepository());}

	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final MatchInvoiceRepository matchInvoiceRepository;

	public MatchInvoiceService(@NonNull final MatchInvoiceRepository matchInvoiceRepository)
	{
		this.matchInvoiceRepository = matchInvoiceRepository;
	}

	public MatchInvBuilder newMatchInvBuilder()
	{
		return new MatchInvBuilder(this, invoiceBL, productBL);
	}

	public I_M_MatchInv getById(final MatchInvId matchInvId) {return matchInvoiceRepository.getById(matchInvId);}

	public Set<MatchInvId> getIdsProcessedButNotPostedByInOutLineIds(final Set<InOutLineId> inoutLineIds) {return matchInvoiceRepository.getIdsProcessedButNotPostedByInOutLineIds(inoutLineIds);}

	public Set<MatchInvId> getIdsProcessedButNotPostedByInvoiceLineIds(final Set<InvoiceLineId> invoiceLineIds) {return matchInvoiceRepository.getIdsProcessedButNotPostedByInvoiceLineIds(invoiceLineIds);}

	/**
	 * Retrieves the quantity of given <code>invoiceLine</code> which was matched with {@link I_M_InOutLine}s.
	 * i.e. aggregates all (active) {@link I_M_MatchInv} records referencing the given <code>invoiceLine</code> and returns their <code>Qty</code> sum.
	 * NOTE: the quantity is NOT credit memo adjusted, NOR IsSOTrx adjusted.
	 */
	public StockQtyAndUOMQty getQtyMatched(@NonNull final I_C_InvoiceLine invoiceLine)
	{
		StockQtyAndUOMQty result = StockQtyAndUOMQtys.createZero(ProductId.ofRepoId(invoiceLine.getM_Product_ID()), UomId.ofRepoId(invoiceLine.getC_UOM_ID()));

		final InvoiceLineId invoiceLineId = InvoiceLineId.ofRepoId(invoiceLine.getC_Invoice_ID(), invoiceLine.getC_InvoiceLine_ID());
		final List<I_M_MatchInv> matchInvRecords = matchInvoiceRepository.getByInvoiceLineId(invoiceLineId);

		for (final I_M_MatchInv matchInvRecord : matchInvRecords)
		{
			final ProductId productId = ProductId.ofRepoId(matchInvRecord.getM_Product_ID());
			final StockQtyAndUOMQty matchInvQty = StockQtyAndUOMQtys.create(
					matchInvRecord.getQty(),
					productId,
					matchInvRecord.getQtyInUOM(),
					UomId.ofRepoIdOrNull(matchInvRecord.getC_UOM_ID()));

			result = StockQtyAndUOMQtys.add(result, matchInvQty);
		}

		return result;
	}

	/**
	 * Retrieves the quantity (in stock UOM) of given <code>inoutLine</code> which was matched with {@link I_C_InvoiceLine}s.
	 * i.e. aggregates all (active) {@link I_M_MatchInv} records referencing the given <code>inoutLine</code> and returns their <code>Qty</code> sum.
	 *
	 * @param initialQtys usually zero; the method will add its results to this parameter. The result will have the same UOMs.
	 */
	public StockQtyAndUOMQty getQtyMatched(final I_M_InOutLine inoutLine, final StockQtyAndUOMQty initialQtys)
	{
		final List<I_M_MatchInv> matchInvRecords = matchInvoiceRepository.getByInOutLineId(InOutLineId.ofRepoId(inoutLine.getM_InOutLine_ID()));

		final ProductId productId = ProductId.ofRepoId(inoutLine.getM_Product_ID());
		StockQtyAndUOMQty result = initialQtys;

		for (final I_M_MatchInv matchInvRecord : matchInvRecords)
		{
			final StockQtyAndUOMQty matchInvQty = StockQtyAndUOMQtys.create(
					matchInvRecord.getQty(),
					productId,
					matchInvRecord.getQtyInUOM(),
					UomId.ofRepoIdOrNull(matchInvRecord.getC_UOM_ID()));

			result = StockQtyAndUOMQtys.add(result, matchInvQty);
		}

		return result;
	}

	public void deleteByInOutId(final InOutId inoutId) {matchInvoiceRepository.deleteByInOutId(inoutId);}

	public void deleteByInOutLineId(final InOutLineId inoutLineId) {matchInvoiceRepository.deleteByInOutLineId(inoutLineId);}

	public Optional<InvoiceLineId> suggestInvoiceLineId(@NonNull final InOutLineId inoutLineId, @NonNull AttributeSetInstanceId asiId)
	{
		for (final I_M_MatchInv matchInv : matchInvoiceRepository.getByInOutLineId(inoutLineId))
		{
			final AttributeSetInstanceId matchInvASIId = AttributeSetInstanceId.ofRepoIdOrNone(matchInv.getM_AttributeSetInstance_ID());
			if (AttributeSetInstanceId.equals(matchInvASIId, asiId))
			{
				return Optional.of(InvoiceLineId.ofRepoId(matchInv.getC_Invoice_ID(), matchInv.getC_InvoiceLine_ID()));
			}
		}

		return Optional.empty();
	}

	public void createReversals(
			@NonNull final InvoiceLineId invoiceLineId,
			@NonNull final I_C_InvoiceLine reversalLine,
			@NonNull final Timestamp reversalDateInvoiced)
	{
		final List<I_M_MatchInv> matchInvs = matchInvoiceRepository.getByInvoiceLineId(invoiceLineId);
		if (matchInvs.isEmpty())
		{
			return;
		}

		final ImmutableMap<InOutLineId, I_M_InOutLine> inoutLinesById = Maps.uniqueIndex(
				inoutBL.getLinesByIds(extractInOutLineIds(matchInvs)),
				inoutLine -> InOutLineId.ofRepoId(inoutLine.getM_InOutLine_ID()));

		for (final I_M_MatchInv matchInv : matchInvs)
		{
			final InOutLineId inoutLineId = extractInOutLineId(matchInv);
			final I_M_InOutLine inoutLine = inoutLinesById.get(inoutLineId);

			final StockQtyAndUOMQty qtyToMatchExact = StockQtyAndUOMQtys.create(
					matchInv.getQty().negate(),
					ProductId.ofRepoId(inoutLine.getM_Product_ID()),
					matchInv.getQtyInUOM().negate(),
					UomId.ofRepoId(matchInv.getC_UOM_ID()));

			newMatchInvBuilder()
					.invoiceLine(reversalLine)
					.inoutLine(inoutLine)
					.dateTrx(reversalDateInvoiced)
					.qtyToMatchExact(qtyToMatchExact)
					.build();
		}
	}

	private static ImmutableSet<InOutLineId> extractInOutLineIds(final List<I_M_MatchInv> matchInvs)
	{
		return matchInvs.stream()
				.map(MatchInvoiceService::extractInOutLineId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	private static InOutLineId extractInOutLineId(final I_M_MatchInv matchInv)
	{
		return InOutLineId.ofRepoId(matchInv.getM_InOutLine_ID());
	}

	public boolean hasMatchInvs(@NonNull final InvoiceLineId invoiceLineId, @NonNull final InOutLineId inoutLineId)
	{
		return matchInvoiceRepository.hasMatchInvs(invoiceLineId, inoutLineId);
	}

	Instant computeNewerDateAcct(final I_M_MatchInv matchInv)
	{
		final Instant invoiceDateAcct = invoiceBL.getDateAcct(InvoiceId.ofRepoId(matchInv.getC_Invoice_ID()));
		final Instant inoutDateAcct = inoutBL.getDateAcct(InOutId.ofRepoId(matchInv.getM_InOut_ID()));

		return invoiceDateAcct.isAfter(inoutDateAcct) ? invoiceDateAcct : inoutDateAcct;
	}

	public Optional<OrderLineId> getOrderLineId(final I_M_MatchInv matchInv)
	{
		final InvoiceLineId invoiceLineId = InvoiceLineId.ofRepoId(matchInv.getC_Invoice_ID(), matchInv.getC_InvoiceLine_ID());
		final I_C_InvoiceLine invoiceLine = invoiceBL.getLineById(invoiceLineId);
		OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(invoiceLine.getC_OrderLine_ID());
		if (orderLineId == null)
		{
			final InOutLineId inoutLineId = InOutLineId.ofRepoId(matchInv.getM_InOutLine_ID());
			final I_M_InOutLine ioLine = inoutBL.getLineByIdInTrx(inoutLineId);
			orderLineId = OrderLineId.ofRepoIdOrNull(ioLine.getC_OrderLine_ID());
		}
		return Optional.ofNullable(orderLineId);
	}

}
