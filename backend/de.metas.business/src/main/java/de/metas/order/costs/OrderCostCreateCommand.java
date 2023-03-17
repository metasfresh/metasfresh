package de.metas.order.costs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.ICurrencyBL;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import java.math.BigDecimal;
import java.util.Comparator;

class OrderCostCreateCommand
{
	@NonNull private final IOrderBL orderBL;
	@NonNull private final ICurrencyBL currencyBL;
	@NonNull private final QuantityUOMConverter uomConverter;
	@NonNull private final OrderCostRepository orderCostRepository;
	@NonNull private final OrderCostTypeRepository costTypeRepository;
	@NonNull private final OrderCostCreateRequest request;

	@Builder
	private OrderCostCreateCommand(
			final @NonNull IOrderBL orderBL,
			final @NonNull ICurrencyBL currencyBL,
			final @NonNull QuantityUOMConverter uomConverter,
			final @NonNull OrderCostRepository orderCostRepository,
			final @NonNull OrderCostTypeRepository costTypeRepository,
			//
			final @NonNull OrderCostCreateRequest request)
	{
		this.orderBL = orderBL;
		this.currencyBL = currencyBL;
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

		orderCost.updateCostAmount(currencyBL::getStdPrecision, uomConverter);
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

	public void createOrderLineIfNeeded(final I_C_Order order, final OrderCost orderCost)
	{
		final OrderCostCreateRequest.OrderLine addOrderLineRequest = request.getAddOrderLine();
		if (addOrderLineRequest == null)
		{
			return;
		}

		final I_C_OrderLine orderLine = orderBL.createOrderLine(order);

		orderBL.setProductId(orderLine, addOrderLineRequest.getProductId(), true);

		orderLine.setQtyEntered(BigDecimal.ONE);
		orderLine.setQtyOrdered(BigDecimal.ONE);

		orderLine.setIsManualPrice(true);
		orderLine.setIsPriceEditable(false);

		final Money costAmountConv = convertToOrderCurrency(orderCost.getCostAmount(), order);
		orderLine.setPriceEntered(costAmountConv.toBigDecimal());
		orderLine.setPriceActual(costAmountConv.toBigDecimal());

		orderLine.setIsManualDiscount(true);
		orderLine.setDiscount(BigDecimal.ZERO);

		orderLine.setC_BPartner2_ID(BPartnerId.toRepoId(addOrderLineRequest.getBpartnerId2()));

		orderBL.save(orderLine);

		orderCost.setCreatedOrderLineId(OrderLineId.ofRepoId(orderLine.getC_OrderLine_ID()));
	}

	private Money convertToOrderCurrency(final Money amt, final I_C_Order order)
	{
		final CurrencyId orderCurrencyId = CurrencyId.ofRepoId(order.getC_Currency_ID());
		final CurrencyConversionContext conversionCtx = orderBL.getCurrencyConversionContext(order);
		return currencyBL.convert(
						conversionCtx,
						amt,
						orderCurrencyId)
				.getAmountAsMoney();
	}

}
