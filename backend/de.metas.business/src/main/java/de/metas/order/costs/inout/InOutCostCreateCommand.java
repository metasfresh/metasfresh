package de.metas.order.costs.inout;

import com.google.common.collect.ImmutableSet;
import de.metas.currency.CurrencyPrecision;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderLineId;
import de.metas.order.costs.OrderCost;
import de.metas.order.costs.OrderCostAddInOutRequest;
import de.metas.order.costs.OrderCostAddInOutResult;
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
	@NonNull final MoneyService moneyService;
	@NonNull private final IUOMConversionBL uomConversionBL;
	@NonNull private final IInOutBL inoutBL;
	@NonNull private final OrderCostRepository orderCostRepository;
	@NonNull private final InOutCostRepository inOutCostRepository;
	private final InOutId inoutId;

	@Builder
	private InOutCostCreateCommand(
			final @NonNull MoneyService moneyService,
			final @NonNull IUOMConversionBL uomConversionBL,
			final @NonNull IInOutBL inoutBL,
			final @NonNull OrderCostRepository orderCostRepository,
			final @NonNull InOutCostRepository inOutCostRepository,
			final @NonNull InOutId inoutId)

	{
		this.moneyService = moneyService;
		this.uomConversionBL = uomConversionBL;
		this.inoutBL = inoutBL;
		this.orderCostRepository = orderCostRepository;
		this.inOutCostRepository = inOutCostRepository;
		this.inoutId = inoutId;
	}

	public void execute()
	{
		final List<I_M_InOutLine> inoutLines = inoutBL.getLines(inoutId);

		final ImmutableSet<OrderLineId> orderLineIds = inoutLines.stream()
				.map(inoutLine -> OrderLineId.ofRepoIdOrNull(inoutLine.getC_OrderLine_ID()))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		final List<OrderCost> orderCostsList = orderCostRepository.getByOrderLineIds(orderLineIds);
		if (orderCostsList.isEmpty())
		{
			return;
		}

		for (final I_M_InOutLine inoutLine : inoutLines)
		{
			final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(inoutLine.getC_OrderLine_ID());
			if (orderLineId == null)
			{
				continue;
			}

			final StockQtyAndUOMQty inoutQty = inoutBL.getStockQtyAndCatchQty(inoutLine);

			for (final OrderCost orderCost : orderCostsList)
			{
				final OrderCostDetail detail = orderCost.getDetailByOrderLineIdIfExists(orderLineId).orElse(null);
				if (detail == null)
				{
					continue;
				}

				final Quantity inoutQtyConv = inoutQty.getQtyInUOM(detail.getUomId(), uomConversionBL);
				final CurrencyPrecision precision = moneyService.getStdPrecision(orderCost.getCurrencyId());
				final Money inoutCostAmount = orderCost.computeInOutCostAmountForQty(orderLineId, inoutQtyConv, precision);

				final OrderCostAddInOutResult addResult = orderCost.addInOutCost(
						OrderCostAddInOutRequest.builder()
								.orderLineId(orderLineId)
								.qty(inoutQtyConv)
								.costAmount(inoutCostAmount)
								.build());

				inOutCostRepository.create(InOutCostCreateRequest.builder()
						.orgId(OrgId.ofRepoId(inoutLine.getAD_Org_ID()))
						.orderCostDetailId(addResult.getOrderCostDetailId())
						.orderAndLineId(OrderAndLineId.of(orderCost.getOrderId(), orderLineId))
						.inoutAndLineId(InOutAndLineId.ofRepoId(inoutLine.getM_InOut_ID(), inoutLine.getM_InOutLine_ID()))
						.soTrx(orderCost.getSoTrx())
						.bpartnerId(orderCost.getBpartnerId())
						.costTypeId(orderCost.getCostTypeId())
						.costElementId(orderCost.getCostElementId())
						.qty(addResult.getQty())
						.costAmount(addResult.getCostAmount())
						.build());
			}
		}

		orderCostRepository.saveAll(orderCostsList);
	}
}
