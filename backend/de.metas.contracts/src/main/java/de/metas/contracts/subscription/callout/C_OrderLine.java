package de.metas.contracts.subscription.callout;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.OrderLinePriceUpdateRequest.ResultUOM;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.compiere.model.I_C_Order;

import java.math.BigDecimal;

@Callout(I_C_OrderLine.class)
public class C_OrderLine
{
	private final transient IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
	private final transient ISubscriptionBL subscriptionBL = Services.get(ISubscriptionBL.class);

	@CalloutMethod(columnNames = { I_C_OrderLine.COLUMNNAME_C_Flatrate_Conditions_ID })
	public void onFlatrateConditions(final I_C_OrderLine ol, final ICalloutField field)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(ol.getM_Product_ID());
		final int bPartnerId = ol.getC_BPartner_ID();

		final I_C_Order order = ol.getC_Order();
		final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());

		if (productId == null || bPartnerId <= 0 || soTrx.isPurchase())
		{
			return;
		}

		final boolean updatePriceEnteredAndDiscountOnlyIfNotAlreadySet = false; // when the subscription changed, update all prices

		ol.setIsManualDiscount(false);
		final int subscriptionId = ol.getC_Flatrate_Conditions_ID();
		if (subscriptionId <= 0)
		{
			final BigDecimal qtyOrdered = orderLineBL.convertQtyEnteredToStockUOM(ol).toBigDecimal();
			ol.setQtyOrdered(qtyOrdered);
			final BigDecimal qtyEnteredInPriceUOM = orderLineBL.convertQtyEnteredToPriceUOM(ol).toBigDecimal();
			ol.setQtyEnteredInPriceUOM(qtyEnteredInPriceUOM);

			orderLineBL.updatePrices(OrderLinePriceUpdateRequest.builder()
											 .orderLine(ol)
											 .resultUOM(ResultUOM.PRICE_UOM)
											 .updatePriceEnteredAndDiscountOnlyIfNotAlreadySet(updatePriceEnteredAndDiscountOnlyIfNotAlreadySet)
											 .updateLineNetAmt(true)
											 .build());

			return;
		}

		subscriptionBL.updateQtysAndPrices(ol, soTrx, updatePriceEnteredAndDiscountOnlyIfNotAlreadySet);
	}

	@CalloutMethod(columnNames = { I_C_OrderLine.COLUMNNAME_QtyEntered })
	public void onQtyEntered(final I_C_OrderLine ol, final ICalloutField field)
	{

		final I_C_Order order = ol.getC_Order();
		final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());

		if (soTrx.isPurchase() || ol.getC_Flatrate_Conditions_ID() <= 0)
		{
			return; // leave this job to the adempiere standard callouts
		}

		final boolean updatePriceEnteredAndDiscountOnlyIfNotAlreadySet = true;
		subscriptionBL.updateQtysAndPrices(ol, soTrx, updatePriceEnteredAndDiscountOnlyIfNotAlreadySet);
	}

}
