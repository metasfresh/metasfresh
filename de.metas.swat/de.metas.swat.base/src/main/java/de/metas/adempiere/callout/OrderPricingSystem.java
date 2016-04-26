package de.metas.adempiere.callout;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.ad.trx.api.ITrx;
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
		return Services.get(IOrderBL.class).setPricingSystemId(order, false, ITrx.TRXNAME_None);
	}

	public String cBPartnerLocationId(final ICalloutField calloutField)
	{
		if (isCalloutActive())
		{
			return NO_ERROR;
		}
		
		final I_C_Order order = calloutField.getModel(I_C_Order.class);
		Services.get(IOrderBL.class).checkForPriceList(order, ITrx.TRXNAME_None);
		
		return NO_ERROR;
	}

	public String mPricingSystemId(final ICalloutField calloutField) {

		if (isCalloutActive())
		{
			return NO_ERROR;
		}
		final I_C_Order order = calloutField.getModel(I_C_Order.class);

		// TODO: figure it out if this is still needed:
//		if (value == null && oldValue != null)
//		{
//			// metas: ok for some customers, imho, for others rather not
//			mTab.setValue(mField, oldValue);
//		}
		
		return Services.get(IOrderBL.class).setPriceList(order, false, order.getM_PricingSystem_ID(), ITrx.TRXNAME_None);
	}
}
