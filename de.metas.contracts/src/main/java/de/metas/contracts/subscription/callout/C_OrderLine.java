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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.uom.api.IUOMConversionBL;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_PriceList;
import org.compiere.util.Env;

import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Matching;
import de.metas.contracts.subscription.ISubscriptionBL;
import de.metas.contracts.subscription.model.I_C_OrderLine;
import de.metas.lang.SOTrx;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.OrderLinePriceUpdateRequest.ResultUOM;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;

@Callout(I_C_OrderLine.class)
public class C_OrderLine
{
	@CalloutMethod(columnNames = { I_C_OrderLine.COLUMNNAME_C_Flatrate_Conditions_ID })
	public void onFlatrateConditions(final I_C_OrderLine ol, final ICalloutField field)
	{
		final int productId = ol.getM_Product_ID();
		final int bPartnerId = ol.getC_BPartner_ID();

		final I_C_Order order = ol.getC_Order();
		final SOTrx soTrx = SOTrx.ofBoolean(order.isSOTrx());

		if (productId <= 0 || bPartnerId <= 0 || soTrx.isPurchase())
		{
			return;
		}

		// resetting PriceEntered so that it won't override the new price
		ol.setPriceEntered(BigDecimal.ZERO);

		final int subscriptionId = ol.getC_Flatrate_Conditions_ID();
		if (subscriptionId <= 0)
		{
			final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
			final BigDecimal qtyEntered = ol.getQtyEntered();

			final BigDecimal qtyOrdered = uomConversionBL.convertToProductUOM(Env.getCtx(), ol.getM_Product(), ol.getC_UOM(), qtyEntered);
			ol.setQtyOrdered(qtyOrdered);

			Services.get(IOrderLineBL.class).updatePrices(OrderLinePriceUpdateRequest.builder()
					.orderLine(ol)
					.resultUOM(ResultUOM.PRICE_UOM)
					.updatePriceEnteredAndDiscountOnlyIfNotAlreadySet(true)
					.updateLineNetAmt(true)
					.build());

			return;
		}

		updatePrices(ol, soTrx);
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

		updatePrices(ol, soTrx);
	}

	private void updatePrices(final I_C_OrderLine ol, @NonNull final SOTrx soTrx)
	{
		final ISubscriptionBL subscriptionBL = Services.get(ISubscriptionBL.class);
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

		final I_C_Flatrate_Conditions flatrateConditions = ol.getC_Flatrate_Conditions();
		final I_C_Order order = ol.getC_Order();

		final PricingSystemId pricingSysytemId;

		if (flatrateConditions.getM_PricingSystem_ID() > 0)
		{
			pricingSysytemId = PricingSystemId.ofRepoId(flatrateConditions.getM_PricingSystem_ID());
		}
		else
		{
			pricingSysytemId = PricingSystemId.ofRepoIdOrNull(order.getM_PricingSystem_ID());
		}

		final I_C_BPartner_Location bpLocation = ol.getC_BPartner_Location();

		final Timestamp date = order.getDateOrdered();

		final I_M_PriceList subscriptionPL = priceListDAO.retrievePriceListByPricingSyst(pricingSysytemId, bpLocation, soTrx);

		final int numberOfRuns = subscriptionBL.computeNumberOfRuns(flatrateConditions.getC_Flatrate_Transition(), date);

		final Properties ctx = Env.getCtx();
		final I_C_Flatrate_Matching matching = subscriptionBL.retrieveMatching(
				ctx,
				ol.getC_Flatrate_Conditions_ID(),
				ol.getM_Product(),
				ITrx.TRXNAME_None);

		
		final Quantity qtyEntered = orderLineBL.getQtyEntered(ol);
		final Quantity qtyEnteredInProductUOM = uomConversionBL.convertToProductUOM(qtyEntered, ol.getM_Product_ID());
		
		final Quantity qtyPerRun;
		if (matching != null && matching.getQtyPerDelivery().signum() > 0)
		{
			final Quantity qtyPerDelivery = Quantity.of(matching.getQtyPerDelivery(), qtyEnteredInProductUOM.getUOM());
			qtyPerRun = qtyPerDelivery.min(qtyEnteredInProductUOM);
		}
		else
		{
			qtyPerRun = qtyEnteredInProductUOM;
		}

		// priceQty is the qty do be delivered during one complete subscription term
		final Quantity priceQty = qtyPerRun.multiply(numberOfRuns);

		// qty ordered needs to be set because it will be used to compute the
		// line's NetLineAmount in MOrderLine.beforeSave()
		ol.setQtyOrdered(priceQty.getAsBigDecimal());

		ol.setQtyEnteredInPriceUOM(priceQty.getAsBigDecimal());

		// now compute the new prices
		orderLineBL.updatePrices(OrderLinePriceUpdateRequest.builder()
				.orderLine(ol)
				.priceListIdOverride(PriceListId.ofRepoId(subscriptionPL.getM_PriceList_ID()))
				.qtyOverride(priceQty)
				.resultUOM(ResultUOM.PRICE_UOM)
				.updatePriceEnteredAndDiscountOnlyIfNotAlreadySet(true)
				.updateLineNetAmt(true)
				.build());
	}
}
