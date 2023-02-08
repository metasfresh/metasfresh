package de.metas.order.costs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.currency.ICurrencyBL;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import java.util.Comparator;

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

		final OrderId orderId = request.getOrderId();
		final I_C_Order order = orderBL.getById(orderId);

		final OrderCost orderCost = OrderCost.builder()
				.orderId(orderId)
				.orgId(OrgId.ofRepoId(order.getAD_Org_ID()))
				.bpartnerId(request.getBpartnerId())
				.costTypeId(costType.getId())
				.calculationMethod(costType.getCalculationMethod())
				.calculationMethodParams(request.getCostCalculationMethodParams())
				.distributionMethod(costType.getDistributionMethod())
				.details(details)
				.build();

		orderCost.updateCostAmount(currencyBL::getStdPrecision);

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
				.sorted(Comparator.comparing(I_C_OrderLine::getLine))
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
