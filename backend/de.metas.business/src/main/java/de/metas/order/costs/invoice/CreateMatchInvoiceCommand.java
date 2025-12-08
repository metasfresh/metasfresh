package de.metas.order.costs.invoice;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.currency.CurrencyPrecision;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutLineId;
import de.metas.invoice.matchinv.MatchInvCostPart;
import de.metas.invoice.matchinv.MatchInvType;
import de.metas.invoice.matchinv.service.MatchInvoiceService;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.costs.OrderCostService;
import de.metas.order.costs.inout.InOutCost;
import de.metas.quantity.StockQtyAndUOMQty;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_InOutLine;

import java.util.ArrayList;

public class CreateMatchInvoiceCommand
{
	private final OrderCostService orderCostService;
	private final MatchInvoiceService matchInvoiceService;
	private final IInvoiceBL invoiceBL;
	private final IInOutBL inoutBL;
	private final MoneyService moneyService;
	private final CreateMatchInvoiceRequest request;

	// state
	private I_C_InvoiceLine _invoiceLine; // lazy

	@Builder
	private CreateMatchInvoiceCommand(
			@NonNull final OrderCostService orderCostService,
			@NonNull final MatchInvoiceService matchInvoiceService,
			@NonNull final IInvoiceBL invoiceBL,
			@NonNull final IInOutBL inoutBL,
			@NonNull final MoneyService moneyService,
			@NonNull final CreateMatchInvoiceRequest request)
	{
		this.orderCostService = orderCostService;
		this.matchInvoiceService = matchInvoiceService;
		this.invoiceBL = invoiceBL;
		this.inoutBL = inoutBL;
		this.moneyService = moneyService;

		this.request = request;
	}

	public CreateMatchInvoicePlan execute()
	{
		final CreateMatchInvoicePlan plan = createPlan();
		if (plan.isEmpty())
		{
			// shall not happen
			throw new AdempiereException("No inout costs found");
		}

		for (final CreateMatchInvoicePlanLine candidate : plan)
		{
			matchInvoiceService.newMatchInvBuilder(MatchInvType.Cost)
					.invoiceLine(getInvoiceLine())
					.inoutLine(candidate.getInoutLine())
					.inoutCost(candidate.getInoutCost())
					.qtyToMatchExact(candidate.getQty())
					.build();
		}

		return plan;
	}

	public CreateMatchInvoicePlan createPlan()
	{
		final ImmutableList<InOutCost> inoutCosts = orderCostService.getInOutCostsByIds(request.getInoutCostIds());
		if (inoutCosts.isEmpty())
		{
			throw new AdempiereException("No inout costs found");
		}

		final ImmutableSet<InOutLineId> inoutLineIds = inoutCosts.stream().map(InOutCost::getInOutLineId).collect(ImmutableSet.toImmutableSet());
		final ImmutableMap<InOutAndLineId, I_M_InOutLine> inoutLines = Maps.uniqueIndex(
				inoutBL.getLinesByIds(inoutLineIds),
				inoutLine -> InOutAndLineId.ofRepoId(inoutLine.getM_InOut_ID(), inoutLine.getM_InOutLine_ID())
		);

		//
		// Create initial plan
		final ArrayList<CreateMatchInvoicePlanLine> candidates = new ArrayList<>();
		for (final InOutCost inoutCost : inoutCosts)
		{
			final InOutAndLineId inoutAndLineId = inoutCost.getInoutAndLineId();
			final I_M_InOutLine inoutLine = inoutLines.get(inoutAndLineId);
			final StockQtyAndUOMQty qty = inoutBL.getStockQtyAndQtyInUOM(inoutLine);
			final Money costAmountInOut = getCostAmountOpen(inoutCost);

			candidates.add(
					CreateMatchInvoicePlanLine.builder()
							.inoutLine(inoutLine)
							.inoutCost(MatchInvCostPart.builder()
									.inoutCostId(inoutCost.getId())
									.costTypeId(inoutCost.getCostTypeId())
									.costElementId(inoutCost.getCostElementId())
									.costAmountInOut(costAmountInOut)
									.costAmountInvoiced(costAmountInOut) // will be updated below
									.build())
							.qty(qty)
							.build());
		}
		final CreateMatchInvoicePlan plan = CreateMatchInvoicePlan.ofList(candidates);

		//
		// Update CostAmountInvoiced
		final Money invoicedAmt = getInvoiceLineOpenAmt();
		final CurrencyPrecision precision = moneyService.getStdPrecision(invoicedAmt.getCurrencyId());
		plan.setCostAmountInvoiced(invoicedAmt, precision);

		return plan;
	}

	private Money getInvoiceLineOpenAmt()
	{
		return orderCostService.getInvoiceLineOpenAmt(getInvoiceLine());
	}

	private I_C_InvoiceLine getInvoiceLine()
	{
		if (this._invoiceLine == null)
		{
			this._invoiceLine = invoiceBL.getLineById(request.getInvoiceAndLineId());
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
