package de.metas.flatrate.callout;

/*
 * #%L
 * de.metas.contracts
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

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.adempiere.ad.callout.api.ICalloutField;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.CalloutOrder;
import org.compiere.model.I_C_Order;
import org.compiere.util.DB;

import de.metas.adempiere.service.IOrderLineBL;
import de.metas.contracts.subscription.model.I_C_OrderLine;

public class OrderLine extends CalloutEngine
{

	/**
	 * This callout "interposes" the callout {@link CalloutOrder#amt(ICalloutField)}
	 *
	 * and decides whether that callout should be executed. If no subscription is selected or this callout is invoked
	 * for another field than 'QtyEntered' that callout is executed, otherwise it is not.
	 */
	public String amt(ICalloutField calloutField)
	{

		if (isCalloutActive())
		{// prevent recursive
			return NO_ERROR;
		}

		if (isDoInvocation(calloutField))
		{
			return new CalloutOrder().amt(calloutField);
		}
		return NO_ERROR;
	}

	/**
	 * This callout "interposes" {@link CalloutOrder#qty(ICalloutField)}
	 *
	 * and decides whether that callout should be executed. If no subscription is selected or this is invoked for
	 * another field than 'QtyEntered' that callout is executed, otherwise not.
	 */
	public String qty(final ICalloutField calloutField)
	{

		if (isCalloutActive())
		{// prevent recursive
			return NO_ERROR;
		}

		if (isDoInvocation(calloutField))
		{
			return new CalloutOrder().qty(calloutField);
		}
		return NO_ERROR;
	}

	private boolean isDoInvocation(final ICalloutField calloutField)
	{

		if (!I_C_OrderLine.COLUMNNAME_QtyEntered.equals(calloutField.getColumnName()))
		{
			// execute the callout
			return true;
		}

		final I_C_OrderLine ol = calloutField.getModel(I_C_OrderLine.class);
		final int subscriptionId = ol.getC_Flatrate_Conditions_ID();

		if (subscriptionId <= 0)
		{
			// execute the callout
			return true;
		}

		// don't execute the callout
		return false;
	}



	// metas
	public String subscriptionLocation(final ICalloutField calloutField)
	{
		final I_C_OrderLine ol = calloutField.getModel(I_C_OrderLine.class);
		final I_C_Order order = ol.getC_Order();
		final boolean IsSOTrx = order.isSOTrx();
		final boolean isSubscription = ol.isSubscription();

		if (IsSOTrx && !isSubscription)
		{
			// FIXME: remove following line because there is no C_Order/C_OrderLine.C_Subscription_ID 
			//mTab.setValue("C_Subscription_ID", null);

			final int priceListId = order.getM_PriceList_ID();

			final BigDecimal qty = ol.getQtyEntered();
			ol.setQtyOrdered(qty);

			Services.get(IOrderLineBL.class).setPricesIfNotIgnored(calloutField.getCtx(), ol, priceListId,
					qty, BigDecimal.ONE, false, // usePriceUOM = false
					null);
		}
		if (IsSOTrx && isSubscription)
		{
			int C_BPartner_ID = order.getC_BPartner_ID();

			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql = "SELECT C_BPartner_Location_ID FROM C_BPartner_Location "
					+ " WHERE C_BPartner_ID = ? "
					+ " ORDER BY IsSubscriptionToDefault DESC";
			try
			{
				pstmt = DB.prepareStatement(sql, null);
				pstmt.setInt(1, C_BPartner_ID);
				rs = pstmt.executeQuery();
				if (rs.next())
				{
					final int bpartnerLocationId = rs.getInt(1);
					order.setC_BPartner_Location_ID(bpartnerLocationId);
					log.debug("C_BPartner_Location_ID for Subscription changed -> " + bpartnerLocationId);
				}
			}
			catch (SQLException e)
			{
				log.error(sql, e);
				return e.getLocalizedMessage();
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		}
		return NO_ERROR;
	}

}
