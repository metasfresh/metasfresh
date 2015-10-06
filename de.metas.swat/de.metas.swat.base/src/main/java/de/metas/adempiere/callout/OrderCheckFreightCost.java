package de.metas.adempiere.callout;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.model.GridTabWrapper;
import org.adempiere.model.I_M_FreightCostDetail;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.X_C_Order;

import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.service.IOrderBL;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Versandkostenermittlung/_-berechnung_(2009_0027_G28)'>DV-Konzept (2009_0027_G28)</a>"
 */
public class OrderCheckFreightCost extends CalloutEngine
{
	final private static String TRX_NAME = null;

	/**
	 * Callout checks if there are {@link I_M_FreightCostDetail} records for the given BPartner, Location and Shipper.
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	public String freightCostRule(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value,
			final Object oldValue)
	{
		final I_C_Order order = GridTabWrapper.create(mTab, I_C_Order.class);
		if (isCalloutActiveOrNotSOTrx(order))
		{
			return "";
		}

		return doCheckAndUpdate(ctx, order, oldValue);
	}

	public String deliveryVia(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value,
			final Object oldValue)
	{
		final I_C_Order order = GridTabWrapper.create(mTab, I_C_Order.class);
		if (isCalloutActiveOrNotSOTrx(order))
		{
			return "";
		}

		final boolean fixPrice = X_C_Order.FREIGHTCOSTRULE_FixPrice.equals(order.getFreightCostRule());
		if (fixPrice)
		{// don't overwrite a fix price. Just return.
			return "";
		}

		return doCheckAndUpdate(ctx, order, oldValue);
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
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	public String mShipperId(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value,
			final Object oldValue)
	{
		final I_C_Order order = GridTabWrapper.create(mTab, I_C_Order.class);
		if (isCalloutActiveOrNotSOTrx(order))
		{
			return "";
		}

		final boolean fixPrice = X_C_Order.FREIGHTCOSTRULE_FixPrice.equals(order.getFreightCostRule());
		if (fixPrice)
		{// don't overwrite a fix price. Just return.
			return "";
		}

		return doCheckAndUpdate(ctx, order, oldValue);
	}

	private String doCheckAndUpdate(final Properties ctx, final I_C_Order order, final Object oldValue)
	{
		// make sure that the freight amount is up to date
		final IOrderBL orderBL = Services.get(IOrderBL.class);
		orderBL.updateFreightAmt(ctx, order, TRX_NAME);
		
		return "";
	}
}
