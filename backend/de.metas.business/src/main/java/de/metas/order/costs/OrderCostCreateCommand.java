package de.metas.order.costs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.lang.SOTrx;
import de.metas.money.MoneyService;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.quantity.QuantityUOMConverter;
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
	@NonNull private final MoneyService moneyService;
	@NonNull private final QuantityUOMConverter uomConverter;
	@NonNull private final OrderCostRepository orderCostRepository;
	@NonNull private final OrderCostTypeRepository costTypeRepository;
	@NonNull private final OrderCostCreateRequest request;

	@Builder
	private OrderCostCreateCommand(
			final @NonNull IOrderBL orderBL,
			final @NonNull MoneyService moneyService,
			final @NonNull QuantityUOMConverter uomConverter,
			final @NonNull OrderCostRepository orderCostRepository,
			final @NonNull OrderCostTypeRepository costTypeRepository,
			//
			final @NonNull OrderCostCreateRequest request)
	{
		this.orderBL = orderBL;
		this.moneyService = moneyService;
		this.uomConverter = uomConverter;
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
				.soTrx(SOTrx.ofBoolean(order.isSOTrx()))
				.orgId(OrgId.ofRepoId(order.getAD_Org_ID()))
				.bpartnerId(request.getBpartnerId())
				.costElementId(costType.getCostElementId())
				.costTypeId(costType.getId())
				.calculationMethod(costType.getCalculationMethod())
				.calculationMethodParams(request.getCostCalculationMethodParams())
				.distributionMethod(costType.getDistributionMethod())
				.details(details)
				.build();

		orderCost.updateCostAmount(moneyService::getStdPrecision, uomConverter);
		createOrderLineIfNeeded(order, orderCost);
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

		// Do not allow order lines created by other costs
		// Maybe in future we will support it, but now, that's the simplest way to avoid recursion.
		final ImmutableSet<OrderLineId> orderLineIds = orderAndLineIds.stream().map(OrderAndLineId::getOrderLineId).collect(ImmutableSet.toImmutableSet());
		if (orderCostRepository.hasCostsByCreatedOrderLineIds(orderLineIds))
		{
			throw new AdempiereException("Cannot use order lines which were created by other costs");
		}

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
				.orderLineInfo(OrderCostDetailOrderLinePart.ofOrderLine(orderLine))
				.build();
	}

	public void createOrderLineIfNeeded(final I_C_Order order, final OrderCost orderCost)
	{
		final OrderCostCreateRequest.OrderLine addOrderLineRequest = request.getAddOrderLine();
		if (addOrderLineRequest == null)
		{
			return;
		}

		final OrderAndLineId createdOrderAndLineId = CreateOrUpdateOrderLineFromOrderCostCommand.builder()
				.orderBL(orderBL)
				.moneyService(moneyService)
				.orderCost(orderCost)
				.productId(addOrderLineRequest.getProductId())
				.loadedOrder(order)
				.build()
				.execute();

		orderCost.setCreatedOrderLineId(createdOrderAndLineId.getOrderLineId());
	}
}
