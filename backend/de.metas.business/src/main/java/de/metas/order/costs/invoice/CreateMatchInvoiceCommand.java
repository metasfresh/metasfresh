package de.metas.order.costs.invoice;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.matchinv.MatchInvCostPart;
import de.metas.invoice.matchinv.MatchInvType;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.costs.OrderCostService;
import de.metas.order.costs.inout.InOutCost;
import de.metas.quantity.StockQtyAndUOMQty;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_M_InOutLine;

public class CreateMatchInvoiceCommand
{
	private final OrderCostService orderCostService;
	private final MatchInvoiceService matchInvoiceService;
	private final IInvoiceBL invoiceBL;
	private final IInOutBL inoutBL;
	private final CreateMatchInvoiceRequest request;

	// state
	private I_C_InvoiceLine _invoiceLine; // lazy

	@Builder
	private CreateMatchInvoiceCommand(
			@NonNull final OrderCostService orderCostService,
			@NonNull final MatchInvoiceService matchInvoiceService,
			@NonNull final IInvoiceBL invoiceBL,
			@NonNull final IInOutBL inoutBL,
			@NonNull final CreateMatchInvoiceRequest request)
	{
		this.orderCostService = orderCostService;

		this.matchInvoiceService = matchInvoiceService;
		this.invoiceBL = invoiceBL;
		this.inoutBL = inoutBL;

		this.request = request;
	}

	public void execute()
	{
		final ImmutableList<InOutCost> inoutCosts = orderCostService.getInOutCostsByIds(request.getInoutCostIds());
		if (inoutCosts.isEmpty())
		{
			// shall not happen
			throw new AdempiereException("No inout costs found");
		}

		final ImmutableSet<InOutLineId> inoutLineIds = inoutCosts.stream().map(inoutCost -> inoutCost.getReceiptAndLineId().getInOutLineId()).collect(ImmutableSet.toImmutableSet());
		final ImmutableMap<InOutAndLineId, I_M_InOutLine> inoutLines = Maps.uniqueIndex(
				inoutBL.getLinesByIds(inoutLineIds),
				inoutLine -> InOutAndLineId.ofRepoId(inoutLine.getM_InOut_ID(), inoutLine.getM_InOutLine_ID())
		);

		final Money invoiceLineNetAmt = getInvoiceLineNetAmt();

		for (final InOutCost inoutCost : inoutCosts)
		{
			final InOutAndLineId receiptAndLineId = inoutCost.getReceiptAndLineId();
			final I_M_InOutLine receiptLine = inoutLines.get(receiptAndLineId);
			final StockQtyAndUOMQty receiptQty = inoutBL.getStockQtyAndQtyInUOM(receiptLine);
			final Money costAmountOpen = getCostAmountOpen(inoutCost);

			matchInvoiceService.newMatchInvBuilder(MatchInvType.Cost)
					.invoiceLine(getInvoiceLine())
					.inoutLine(receiptLine)
					.inoutCost(MatchInvCostPart.builder()
							.inoutCostId(inoutCost.getId())
							.costTypeId(inoutCost.getCostTypeId())
							.costAmount(costAmountOpen)
							.build())
					.qtyToMatchExact(receiptQty)
					.build();
		}
	}

	private Money getInvoiceLineNetAmt()
	{
		final I_C_InvoiceLine invoiceLine = getInvoiceLine();
		final I_C_Invoice invoice = invoiceBL.getById(request.getInvoiceLineId().getInvoiceId());
		return Money.of(invoiceLine.getLineNetAmt(), CurrencyId.ofRepoId(invoice.getC_Currency_ID()));
	}

	private I_C_InvoiceLine getInvoiceLine()
	{
		if (this._invoiceLine == null)
		{
			this._invoiceLine = invoiceBL.getLineById(request.getInvoiceLineId());
		}
		return this._invoiceLine;
	}

	private Money getCostAmountOpen(@NonNull final InOutCost inoutCost)
	{
		final Money totalAmt = inoutCost.getCostAmount();
		final Money matchedAmt = matchInvoiceService.getCostAmountMatched(inoutCost.getId()).orElse(null);
		if (matchedAmt == null || matchedAmt.isZero())
		{
			return totalAmt;
		}
		else
		{
			return totalAmt.subtract(matchedAmt);
		}
	}

}
