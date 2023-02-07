package de.metas.order.costs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.ICurrencyBL;
import de.metas.money.Money;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

class OrderCostCreateCommand
{
	@NonNull private final IOrderBL orderBL;
	@NonNull private final ICurrencyBL currencyBL;
	@NonNull private final OrderCostRepository orderCostRepository;
	@NonNull private final OrderCostTypeRepository costTypeRepository;
	@NonNull private final OrderCostCreateRequest request;

	@Builder
	private OrderCostCreateCommand(
			final @NonNull IOrderBL orderBL,
			final @NonNull ICurrencyBL currencyBL,
			final @NonNull OrderCostRepository orderCostRepository,
			final @NonNull OrderCostTypeRepository costTypeRepository,
			//
			final @NonNull OrderCostCreateRequest request)
	{
		this.orderBL = orderBL;
		this.currencyBL = currencyBL;
		this.orderCostRepository = orderCostRepository;
		this.costTypeRepository = costTypeRepository;

		this.request = request;
	}

	public OrderCost execute()
	{
		final ImmutableList<OrderCostDetail> details = createOrderCostDetails();

		final OrderCostType costType = costTypeRepository.getById(request.getCostTypeId());
		final CostCalculationMethod calculationMethod = costType.getCalculationMethod();
		final Money costAmount;
		if (calculationMethod == CostCalculationMethod.FixedAmount)
		{
			final Money fixedAmount = request.getFixedAmount();
			if (fixedAmount == null || fixedAmount.signum() <= 0)
			{
				throw new AdempiereException("Fixed amount shall be provided");
			}

			costAmount = fixedAmount;
		}
		else if (calculationMethod == CostCalculationMethod.PercentageOfAmount)
		{
			final Percent percentageOfAmount = request.getPercentageOfAmount();
			if (percentageOfAmount == null || percentageOfAmount.signum() <= 0)
			{
				throw new AdempiereException("Percentage of Amount shall be provided");
			}

			final Money linesNetAmt = details.stream()
					.map(OrderCostDetail::getOrderLineNetAmt)
					.reduce(Money::add)
					.orElseThrow(() -> new AdempiereException("No lines"));// shall not happen

			final CurrencyPrecision precision = currencyBL.getStdPrecision(linesNetAmt.getCurrencyId());

			costAmount = linesNetAmt.multiply(percentageOfAmount, precision);
		}
		else
		{
			throw new AdempiereException("Unknown calculation method: " + costType.getCalculationMethod());
		}

		final OrderId orderId = request.getOrderId();
		final I_C_Order order = orderBL.getById(orderId);

		final OrderCost orderCost = OrderCost.builder()
				.orderId(orderId)
				.orgId(OrgId.ofRepoId(order.getAD_Org_ID()))
				.costTypeId(costType.getId())
				.calculationMethod(calculationMethod)
				.distributionMethod(costType.getDistributionMethod())
				.costAmount(costAmount)
				.details(details)
				.build();

		orderCostRepository.save(orderCost);

		return orderCost;
	}

	private ImmutableList<OrderCostDetail> createOrderCostDetails()
	{
		final ImmutableSet<OrderAndLineId> orderAndLineIds = request.getOrderAndLineIds();
		if (orderAndLineIds.isEmpty())
		{
			throw new AdempiereException("No order lines provided");
		}

		// Make sure all lines are from a single order
		CollectionUtils.extractSingleElement(orderAndLineIds, OrderAndLineId::getOrderId);

		return orderBL.getLinesByIds(orderAndLineIds)
				.values()
				.stream()
				.map(OrderCostCreateCommand::toOrderCostDetail)
				.collect(ImmutableList.toImmutableList());
	}

	private static OrderCostDetail toOrderCostDetail(final I_C_OrderLine orderLine)
	{
		return OrderCostDetail.builder()
				.orderLineId(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()))
				.productId(ProductId.ofRepoId(orderLine.getM_Product_ID()))
				.orderLineNetAmt(IOrderBL.extractLineNetAmt(orderLine))
				.qtyOrdered(IOrderBL.extractQtyEntered(orderLine))
				.build();
	}
}
