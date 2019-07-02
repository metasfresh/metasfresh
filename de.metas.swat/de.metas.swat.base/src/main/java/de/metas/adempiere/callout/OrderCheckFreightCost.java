package de.metas.adempiere.callout;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.model.I_M_FreightCostDetail;
import org.compiere.Adempiere;
import org.compiere.model.CalloutEngine;

import de.metas.adempiere.model.I_C_Order;
import de.metas.freighcost.FreightCostRule;
import de.metas.order.OrderFreightCostsService;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Versandkostenermittlung/_-berechnung_(2009_0027_G28)'>DV-Konzept (2009_0027_G28)</a>"
 */
public class OrderCheckFreightCost extends CalloutEngine
{
	/**
	 * Callout checks if there are {@link I_M_FreightCostDetail} records for the given BPartner, Location and Shipper.
	 */
	public String freightCostRule(final ICalloutField field)
	{
		final I_C_Order order = field.getModel(I_C_Order.class);
		if (isCalloutActiveOrNotSOTrx(order))
		{
			return NO_ERROR;
		}

		doCheckAndUpdate(order);
		return NO_ERROR;
	}

	public String deliveryVia(final ICalloutField field)
	{
		final I_C_Order order = field.getModel(I_C_Order.class);
		if (isCalloutActiveOrNotSOTrx(order))
		{
			return NO_ERROR;
		}

		// don't overwrite a fix price. Just return.
		final FreightCostRule freightCostRule = FreightCostRule.ofNullableCode(order.getFreightCostRule());
		if (freightCostRule.isFixPrice())
		{
			return NO_ERROR;
		}

		doCheckAndUpdate(order);
		return NO_ERROR;
	}

	private boolean isCalloutActiveOrNotSOTrx(final I_C_Order order)
	{
		if (isCalloutActive())
		{ // prevent recursive
			return true;
		}
		if (!order.isSOTrx())
		{ // nothing to do
			return true;
		}
		return false;
	}

	/**
	 * Callout checks if there are {@link I_M_FreightCostDetail} records for the given BPartner, Location and Shipper.
	 */
	public String mShipperId(final ICalloutField field)
	{
		final I_C_Order order = field.getModel(I_C_Order.class);
		if (isCalloutActiveOrNotSOTrx(order))
		{
			return NO_ERROR;
		}

		// don't overwrite a fix price. Just return.
		final FreightCostRule freightCostRule = FreightCostRule.ofNullableCode(order.getFreightCostRule());
		if (freightCostRule.isFixPrice())
		{
			return NO_ERROR;
		}

		doCheckAndUpdate(order);
		return NO_ERROR;
	}

	private void doCheckAndUpdate(final I_C_Order order)
	{
		// make sure that the freight amount is up to date
		final OrderFreightCostsService orderFreightCostService = Adempiere.getBean(OrderFreightCostsService.class);
		orderFreightCostService.updateFreightAmt(order);
	}
}
