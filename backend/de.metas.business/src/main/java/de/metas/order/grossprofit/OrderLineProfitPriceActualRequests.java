package de.metas.order.grossprofit;

import de.metas.money.grossprofit.CalculateProfitPriceActualRequest;
import de.metas.order.OrderLine;
import de.metas.order.OrderShipmentBPartners;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderLineProfitPriceActualRequests
{
	/**
	 * @param orderLine the order line as it currently is, which may be not yet saved; so also its packing instruction is the one that is currently set
	 */
	public static CalculateProfitPriceActualRequest of(@NonNull final OrderLine orderLine)
	{
		return CalculateProfitPriceActualRequest.builder()
				.bPartnerId(orderLine.getBPartnerId())
				.huPIItemProductId(orderLine.getHuPIItemProductId())
				.shipmentBPartnerId(OrderShipmentBPartners.extractShipmentBPartnerId(orderLine.getOrderId()))
				.productId(orderLine.getProductId())
				.date(orderLine.getDatePromised().toLocalDate())
				.baseAmount(orderLine.getPriceActual().toMoney())
				.paymentTermId(orderLine.getPaymentTermId())
				.quantity(orderLine.getOrderedQty())
				.build();
	}
}
