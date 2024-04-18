package de.metas.invoice.matchinv.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.InvoiceAndLineId;
import de.metas.invoice.matchinv.MatchInv;
import de.metas.invoice.matchinv.MatchInvId;
import de.metas.invoice.matchinv.MatchInvQuery;
import de.metas.invoice.matchinv.MatchInvType;
import de.metas.invoice.matchinv.listeners.MatchInvListenersRegistry;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.Money;
import de.metas.order.OrderLineId;
import de.metas.order.costs.inout.InOutCostId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_M_InOutLine;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class MatchInvoiceService
{
	public static MatchInvoiceService get() {return SpringContextHolder.instance.getBean(MatchInvoiceService.class);}

	public static MatchInvoiceService newInstanceForUnitTesting()
	{
		Adempiere.assertUnitTestMode();
		return new MatchInvoiceService(new MatchInvoiceRepository(), new MatchInvListenersRegistry(Optional.empty()));
	}

	private final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
	private final IInOutBL inoutBL = Services.get(IInOutBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);
	private final MatchInvoiceRepository matchInvoiceRepository;
	private final MatchInvListenersRegistry listenersRegistry;

	public MatchInvoiceService(
			@NonNull final MatchInvoiceRepository matchInvoiceRepository,
			@NonNull @Lazy final MatchInvListenersRegistry listenersRegistry)
	{
		this.matchInvoiceRepository = matchInvoiceRepository;
		this.listenersRegistry = listenersRegistry;
	}

	public MatchInvBuilder newMatchInvBuilder(@NonNull final MatchInvType type)
	{
		return new MatchInvBuilder(this, invoiceBL, inoutBL, productBL, listenersRegistry)
				.type(type);
	}

	public MatchInv getById(final MatchInvId matchInvId) {return matchInvoiceRepository.getById(matchInvId);}

	public Set<MatchInvId> getIdsProcessedButNotPostedByInOutLineIds(final Set<InOutLineId> inoutLineIds) {return matchInvoiceRepository.getIdsProcessedButNotPostedByInOutLineIds(inoutLineIds);}

	public Set<MatchInvId> getIdsProcessedButNotPostedByInvoiceLineIds(final Set<InvoiceAndLineId> invoiceAndLineIds) {return matchInvoiceRepository.getIdsProcessedButNotPostedByInvoiceLineIds(invoiceAndLineIds);}

	/**
	 * @return material matched quantity; NOTE: the quantity is NOT credit memo adjusted, NOR IsSOTrx adjusted.
	 */
	public StockQtyAndUOMQty getMaterialQtyMatched(@NonNull final I_C_InvoiceLine invoiceLine)
	{
		final InvoiceAndLineId invoiceAndLineId = InvoiceAndLineId.ofRepoId(invoiceLine.getC_Invoice_ID(), invoiceLine.getC_InvoiceLine_ID());
		final StockQtyAndUOMQty zero = StockQtyAndUOMQtys.createZero(ProductId.ofRepoId(invoiceLine.getM_Product_ID()), UomId.ofRepoId(invoiceLine.getC_UOM_ID()));

		final List<MatchInv> matchInvs = matchInvoiceRepository.list(MatchInvQuery.builder()
				.type(MatchInvType.Material)
				.invoiceAndLineId(invoiceAndLineId)
				.build());
		return sumQty(matchInvs, zero);
	}

	/**
	 * @return material matched quantity using the UOMs of <code>initialQtys</code>
	 */
	public StockQtyAndUOMQty getMaterialQtyMatched(
			@NonNull final InOutLineId inoutLineId,
			@NonNull final StockQtyAndUOMQty initialQtys)
	{
		final List<MatchInv> matchInvs = matchInvoiceRepository.list(MatchInvQuery.builder()
				.type(MatchInvType.Material)
				.inoutLineId(inoutLineId)
				.build());
		return sumQty(matchInvs, initialQtys);
	}

	private static StockQtyAndUOMQty sumQty(
			@NonNull final List<MatchInv> matchInvs,
			@NonNull final StockQtyAndUOMQty initialQtys)
	{
		StockQtyAndUOMQty result = initialQtys;

		for (final MatchInv matchInv : matchInvs)
		{
			result = StockQtyAndUOMQtys.add(result, matchInv.getQty());
		}

		return result;
	}

	public void deleteByInOutId(@NonNull final InOutId inoutId)
	{
		final ImmutableList<MatchInv> matchInvs = matchInvoiceRepository.deleteAndReturn(MatchInvQuery.builder()
				.inoutId(inoutId)
				.build());

		if (!matchInvs.isEmpty())
		{
			listenersRegistry.fireAfterDeleted(matchInvs);
		}
	}

	public void deleteByInOutLineId(final InOutLineId inoutLineId)
	{
		final ImmutableList<MatchInv> matchInvs = matchInvoiceRepository.deleteAndReturn(MatchInvQuery.builder()
				.inoutLineId(inoutLineId)
				.build());

		if (!matchInvs.isEmpty())
		{
			listenersRegistry.fireAfterDeleted(matchInvs);
		}
	}

	public Optional<InvoiceAndLineId> suggestMaterialInvoiceLineId(@NonNull final InOutLineId inoutLineId, @NonNull AttributeSetInstanceId asiId)
	{
		return matchInvoiceRepository.first(
						MatchInvQuery.builder()
								.type(MatchInvType.Material)
								.inoutLineId(inoutLineId)
								.asiId(asiId)
								.build())
				.map(MatchInv::getInvoiceAndLineId);
	}

	public void createReversals(
			@NonNull final InvoiceAndLineId invoiceAndLineId,
			@NonNull final I_C_InvoiceLine reversalLine,
			@NonNull final Timestamp reversalDateInvoiced)
	{
		final List<MatchInv> matchInvs = matchInvoiceRepository.list(MatchInvQuery.builder()
				.invoiceAndLineId(invoiceAndLineId)
				.build());
		if (matchInvs.isEmpty())
		{
			return;
		}

		final ImmutableMap<InOutLineId, I_M_InOutLine> inoutLinesById = Maps.uniqueIndex(
				inoutBL.getLinesByIds(extractInOutLineIds(matchInvs)),
				inoutLine -> InOutLineId.ofRepoId(inoutLine.getM_InOutLine_ID()));

		for (final MatchInv matchInv : matchInvs)
		{
			final InOutLineId inoutLineId = matchInv.getInoutLineId().getInOutLineId();
			final I_M_InOutLine inoutLine = inoutLinesById.get(inoutLineId);

			final MatchInvType type = matchInv.getType();
			newMatchInvBuilder(type)
					.invoiceLine(reversalLine)
					.inoutLine(inoutLine)
					.inoutCost(type.isCost() ? matchInv.getCostPartNotNull() : null)
					.qtyToMatchExact(matchInv.getQty().negate())
					.dateTrx(reversalDateInvoiced)
					.build();
		}
	}

	private static ImmutableSet<InOutLineId> extractInOutLineIds(final List<MatchInv> matchInvs)
	{
		return matchInvs.stream()
				.map(matchInv -> matchInv.getInoutLineId().getInOutLineId())
				.collect(ImmutableSet.toImmutableSet());
	}

	public boolean hasMatchInvs(
			@NonNull final InvoiceAndLineId invoiceAndLineId,
			@NonNull final InOutLineId inoutLineId,
			@Nullable final InOutCostId inoutCostId)
	{
		return matchInvoiceRepository.anyMatch(
				MatchInvQuery.builder()
						.type(inoutCostId == null ? MatchInvType.Material : MatchInvType.Cost)
						.invoiceAndLineId(invoiceAndLineId)
						.inoutLineId(inoutLineId)
						.inoutCostId(inoutCostId)
						.build()
		);
	}

	public Optional<OrderLineId> getOrderLineId(final MatchInv matchInv)
	{
		final I_C_InvoiceLine invoiceLine = invoiceBL.getLineById(matchInv.getInvoiceAndLineId());
		OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(invoiceLine.getC_OrderLine_ID());
		if (orderLineId == null)
		{
			final InOutLineId inoutLineId = matchInv.getInoutLineId().getInOutLineId();
			final I_M_InOutLine ioLine = inoutBL.getLineByIdInTrx(inoutLineId);
			orderLineId = OrderLineId.ofRepoIdOrNull(ioLine.getC_OrderLine_ID());
		}
		return Optional.ofNullable(orderLineId);
	}

	public Optional<Money> getCostAmountMatched(@NonNull final InOutCostId inoutCostId)
	{
		final List<MatchInv> matchInvs = matchInvoiceRepository.list(
				MatchInvQuery.builder()
						.type(MatchInvType.Cost)
						.inoutCostId(inoutCostId)
						.build());

		return matchInvs.stream()
				.map(matchInv -> matchInv.getCostPartNotNull().getCostAmountInOut())
				.reduce(Money::add);
	}

	public Optional<Money> getCostAmountMatched(final InvoiceAndLineId invoiceAndLineId)
	{
		final List<MatchInv> matchInvs = matchInvoiceRepository.list(MatchInvQuery.builder()
				.type(MatchInvType.Cost)
				.invoiceAndLineId(invoiceAndLineId)
				.build());

		return matchInvs.stream()
				.map(matchInv -> matchInv.getCostPartNotNull().getCostAmountInvoiced())
				.reduce(Money::add);
	}
}
