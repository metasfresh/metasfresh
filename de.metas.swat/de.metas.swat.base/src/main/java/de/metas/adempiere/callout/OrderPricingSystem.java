package de.metas.adempiere.callout;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;

import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.service.IOrderBL;

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
		Services.get(IOrderBL.class).setPriceList(order);
		
		return NO_ERROR;
	}

	public String mPricingSystemId(final ICalloutField calloutField)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}
		
		final I_C_Order order = calloutField.getModel(I_C_Order.class);
		Services.get(IOrderBL.class).setPriceList(order);
		
		return NO_ERROR;
	}
}
