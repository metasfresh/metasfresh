package de.metas.order.costs.inout;

import com.google.common.collect.ImmutableSet;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyBL;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.money.Money;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderLineId;
import de.metas.order.costs.OrderCost;
import de.metas.order.costs.OrderCostAddReceiptRequest;
import de.metas.order.costs.OrderCostAddReceiptResult;
import de.metas.order.costs.OrderCostDetail;
import de.metas.order.costs.OrderCostRepository;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.uom.IUOMConversionBL;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_M_InOutLine;

import java.util.List;
import java.util.Objects;

public class InOutCostCreateCommand
{
	@NonNull final ICurrencyBL currencyBL;
	@NonNull private final IUOMConversionBL uomConversionBL;
	@NonNull private final IInOutBL inoutBL;
	@NonNull private final OrderCostRepository orderCostRepository;
	@NonNull private final InOutCostRepository inOutCostRepository;
	private final InOutId receiptId;

	@Builder
	private InOutCostCreateCommand(
			final @NonNull ICurrencyBL currencyBL,
			final @NonNull IUOMConversionBL uomConversionBL,
			final @NonNull IInOutBL inoutBL,
			final @NonNull OrderCostRepository orderCostRepository,
			final @NonNull InOutCostRepository inOutCostRepository,
			final @NonNull InOutId receiptId)

	{
		this.currencyBL = currencyBL;
		this.uomConversionBL = uomConversionBL;
		this.inoutBL = inoutBL;
		this.orderCostRepository = orderCostRepository;
		this.inOutCostRepository = inOutCostRepository;
		this.receiptId = receiptId;
	}

	public void execute()
	{
		final List<I_M_InOutLine> receiptLines = inoutBL.getLines(receiptId);

		final ImmutableSet<OrderLineId> purchaseOrderLineIds = receiptLines.stream()
				.map(receiptLine -> OrderLineId.ofRepoIdOrNull(receiptLine.getC_OrderLine_ID()))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		final List<OrderCost> orderCostsList = orderCostRepository.getByOrderLineIds(purchaseOrderLineIds);
		if (orderCostsList.isEmpty())
		{
			return;
		}

		for (final I_M_InOutLine receiptLine : receiptLines)
		{
			final OrderAndLineId purchaseOrderAndLineId = OrderAndLineId.ofRepoIdsOrNull(receiptLine.getC_Order_ID(), receiptLine.getC_OrderLine_ID());
			if (purchaseOrderAndLineId == null)
			{
				continue;
			}

			final StockQtyAndUOMQty receiptQty = inoutBL.getStockQtyAndCatchQty(receiptLine);

			for (final OrderCost orderCost : orderCostsList)
			{
				final OrderLineId purchaseOrderLineId = purchaseOrderAndLineId.getOrderLineId();
				final OrderCostDetail detail = orderCost.getDetailByOrderLineIdIfExists(purchaseOrderLineId).orElse(null);
				if (detail == null)
				{
					continue;
				}

				final Quantity receiptQtyConv = receiptQty.getQtyInUOM(detail.getUomId(), uomConversionBL);
				final CurrencyPrecision precision = currencyBL.getStdPrecision(orderCost.getCurrencyId());
				final Money receiptCostAmount = orderCost.computeCostAmountForQty(purchaseOrderLineId, receiptQtyConv, precision);

				final OrderCostAddReceiptResult addResult = orderCost.addMaterialReceipt(
						OrderCostAddReceiptRequest.builder()
								.orderLineId(purchaseOrderLineId)
								.qty(receiptQtyConv)
								.costAmount(receiptCostAmount)
								.build());

				inOutCostRepository.create(InOutCostCreateRequest.builder()
						.orgId(OrgId.ofRepoId(receiptLine.getAD_Org_ID()))
						.orderCostDetailId(addResult.getOrderCostDetailId())
						.orderAndLineId(purchaseOrderAndLineId)
						.receiptAndLineId(InOutAndLineId.ofRepoId(receiptLine.getM_InOut_ID(), receiptLine.getM_InOutLine_ID()))
						.bpartnerId(orderCost.getBpartnerId())
						.costTypeId(orderCost.getCostTypeId())
						.qty(addResult.getQty())
						.costAmount(addResult.getCostAmount())
						.build());
			}
		}

		orderCostRepository.saveAll(orderCostsList);
	}
}
