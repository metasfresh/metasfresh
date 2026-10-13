package de.metas.order.grossprofit;

import de.metas.bpartner.BPartnerId;
import de.metas.money.grossprofit.CalculateProfitPriceActualRequest;
import de.metas.order.IOrderBL;
import de.metas.order.OrderLine;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_C_Order;

import static de.metas.common.util.CoalesceUtil.coalesceNotNull;

@UtilityClass
public class OrderLineProfitPriceActualRequests
{
	/**
	 * @param orderLine the order line as it currently is, which may be not yet saved; so also its packing instruction is the one that is currently set
	 * @param order     the line's order; its invoice partner is the request's partner, because the line's invoice candidate is invoiced to that partner and refund terms match the sales invoiced to their partner
	 */
	public static CalculateProfitPriceActualRequest of(@NonNull final OrderLine orderLine, @NonNull final I_C_Order order)
	{
		final BPartnerId invoicePartnerId = coalesceNotNull(Services.get(IOrderBL.class).getEffectiveBillPartnerId(order), orderLine.getBPartnerId());

		return CalculateProfitPriceActualRequest.builder()
				.bPartnerId(invoicePartnerId)
				.huPIItemProductId(orderLine.getHuPIItemProductId())
				.productId(orderLine.getProductId())
				.date(orderLine.getDatePromised().toLocalDate())
				.baseAmount(orderLine.getPriceActual().toMoney())
				.paymentTermId(orderLine.getPaymentTermId())
				.quantity(orderLine.getOrderedQty())
				.build();
	}
}
