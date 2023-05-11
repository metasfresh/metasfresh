package de.metas.order.costs;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyConversionContext;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.order.IOrderBL;
import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;

class CreateOrUpdateOrderLineFromOrderCostCommand
{
	//
	// services
	@NonNull private final IOrderBL orderBL;
	@NonNull private final MoneyService moneyService;

	//
	// Params
	@NonNull private final OrderCost orderCost;
	@Nullable private final ProductId productId;

	//
	// State
	@Nullable private I_C_Order _order;

	@Builder
	private CreateOrUpdateOrderLineFromOrderCostCommand(
			final @NonNull IOrderBL orderBL,
			final @NonNull MoneyService moneyService,
			final @NonNull OrderCost orderCost,
			final @Nullable ProductId productId,
			final @Nullable I_C_Order loadedOrder)
	{
		this.orderBL = orderBL;
		this.moneyService = moneyService;

		this.orderCost = orderCost;
		this.productId = productId;

		if (loadedOrder != null && loadedOrder.getC_Order_ID() == orderCost.getOrderId().getRepoId())
		{
			this._order = loadedOrder;
		}
	}

	public OrderAndLineId execute()
	{
		final OrderAndLineId orderAndLineId = OrderAndLineId.ofNullable(orderCost.getOrderId(), orderCost.getCreatedOrderLineId());

		final I_C_OrderLine orderLine = orderAndLineId != null
				? orderBL.getLineById(orderAndLineId)
				: orderBL.createOrderLine(getOrder());

		if (productId != null)
		{
			orderBL.setProductId(orderLine, productId, true);
		}

		orderLine.setQtyEntered(BigDecimal.ONE);
		orderLine.setQtyOrdered(BigDecimal.ONE);

		orderLine.setIsManualPrice(true);
		orderLine.setIsPriceEditable(false);

		final Money costAmountConv = convertToOrderCurrency(orderCost.getCostAmount());
		orderLine.setPriceEntered(costAmountConv.toBigDecimal());
		orderLine.setPriceActual(costAmountConv.toBigDecimal());

		orderLine.setIsManualDiscount(true);
		orderLine.setDiscount(BigDecimal.ZERO);

		orderLine.setC_BPartner2_ID(BPartnerId.toRepoId(orderCost.getBpartnerId()));

		orderBL.save(orderLine);

		return OrderAndLineId.ofRepoIds(orderLine.getC_Order_ID(), orderLine.getC_OrderLine_ID());
	}

	private I_C_Order getOrder()
	{
		if (_order == null)
		{
			_order = orderBL.getById(orderCost.getOrderId());
		}
		return _order;
	}

	private Money convertToOrderCurrency(final Money amt)
	{
		final I_C_Order order = getOrder();
		final CurrencyId orderCurrencyId = CurrencyId.ofRepoId(order.getC_Currency_ID());
		final CurrencyConversionContext conversionCtx = orderBL.getCurrencyConversionContext(order);
		return moneyService.convertMoneyToCurrency(amt, orderCurrencyId, conversionCtx);
	}

}
