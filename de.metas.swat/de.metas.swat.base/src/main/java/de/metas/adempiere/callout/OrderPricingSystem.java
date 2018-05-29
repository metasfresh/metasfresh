package de.metas.adempiere.callout;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.I_M_PriceList;

import de.metas.adempiere.model.I_C_Order;
import de.metas.order.IOrderBL;

public class OrderPricingSystem extends CalloutEngine
{
	public String cBPartnerId(final ICalloutField calloutField)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}

		final I_C_Order order = calloutField.getModel(I_C_Order.class);
		final boolean overridePricingSystem = true;
		Services.get(IOrderBL.class).setM_PricingSystem_ID(order, overridePricingSystem);

		return NO_ERROR;
	}

	public String cBPartnerLocationId(final ICalloutField calloutField)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}

		final I_C_Order order = calloutField.getModel(I_C_Order.class);
		final I_M_PriceList priceList = Services.get(IOrderBL.class).findPriceListOrNull(order);
		if (priceList == null)
		{
			order.setM_PricingSystem(null);
		}
		else
		{
			order.setM_PriceList(priceList);
		}


		return NO_ERROR;
	}

	public String mPricingSystemId(final ICalloutField calloutField)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}

		final I_C_Order order = calloutField.getModel(I_C_Order.class);
		final I_M_PriceList priceList = Services.get(IOrderBL.class).findPriceListOrNull(order);
		if (priceList == null)
		{
			order.setM_PricingSystem(null);
		}
		else
		{
			order.setM_PriceList(priceList);
		}

		return NO_ERROR;
	}
}
