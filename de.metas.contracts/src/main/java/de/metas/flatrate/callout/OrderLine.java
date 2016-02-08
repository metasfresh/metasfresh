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
import java.sql.Timestamp;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.GridTabWrapper;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.CalloutOrder;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_M_PriceList;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.adempiere.service.IOrderLineBL;
import de.metas.flatrate.api.ISubscriptionBL;
import de.metas.flatrate.interfaces.I_C_OrderLine;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Flatrate_Matching;
import de.metas.product.IProductPA;

public class OrderLine extends CalloutEngine
{

	final private static String TRX_NAME = null;

	/**
	 * This callout "interposes" the callout
	 * {@link CalloutOrder#amt(java.util.Properties, int, GridTab, GridField, Object)}
	 * 
	 * and decides whether that callout should be executed. If no subscription is selected or this callout is invoked
	 * for another field than 'QtyEntered' that callout is executed, otherwise it is not.
	 */
	public String amt(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value,
			final Object oldValue)
	{

		if (isCalloutActive())
		{// prevent recursive
			return "";
		}

		if (isDoInvocation(mTab, mField))
		{
			return new CalloutOrder().amt(ctx, WindowNo, mTab, mField, value);
		}
		return "";
	}

	/**
	 * This callout "interposes" {@link CalloutOrder#qty(java.util.Properties, int, GridTab, GridField, Object)}
	 * 
	 * and decides whether that callout should be executed. If no subscription is selected or this is invoked for
	 * another field than 'QtyEntered' that callout is executed, otherwise not.
	 */
	public String qty(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value,
			final Object oldValue)
	{

		if (isCalloutActive())
		{// prevent recursive
			return "";
		}

		if (isDoInvocation(mTab, mField))
		{
			return new CalloutOrder().qty(ctx, WindowNo, mTab, mField, value);
		}
		return "";
	}

	private boolean isDoInvocation(final GridTab mTab, final GridField mField)
	{

		if (!I_C_OrderLine.COLUMNNAME_QtyEntered.equals(mField.getColumnName()))
		{
			// execute the callout
			return true;
		}

		final I_C_OrderLine ol = GridTabWrapper.create(mTab, I_C_OrderLine.class);
		final int subscriptionId = ol.getC_Flatrate_Conditions_ID();

		if (subscriptionId == 0)
		{
			// execute the callout
			return true;
		}

		// don't execute the callout
		return false;
	}

	public String cSubscriptionId(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value,
			final Object oldValue)
	{
		if (isCalloutActive())
		{
			return ""; // prevent recursive
		}

		final I_C_OrderLine ol = GridTabWrapper.create(mTab, I_C_OrderLine.class);

		final int productId = ol.getM_Product_ID();
		final int bPartnerId = ol.getC_BPartner_ID();

		final boolean isSOTrx = Env.isSOTrx(ctx, WindowNo);

		if (productId == 0 || bPartnerId == 0 || !isSOTrx)
		{
			return "";
		}

		// resetting PriceEntered so that it won't override the new price
		ol.setPriceEntered(BigDecimal.ZERO);

		final int subscriptionId = ol.getC_Flatrate_Conditions_ID();

		if (subscriptionId == 0)
		{
			final BigDecimal qty = ol.getQtyEntered();
			ol.setQtyOrdered(qty);

			final int priceListId = Env.getContextAsInt(ctx, WindowNo, I_C_Order.COLUMNNAME_M_PriceList_ID);
			Services.get(IOrderLineBL.class).setPricesIfNotIgnored(ctx, ol, priceListId, qty, BigDecimal.ONE, 
					false, // usePriceUOM = false 
					null);

			return "";
		}

		updatePrices(ctx, ol, isSOTrx);

		return "";
	}

	// metas
	public String subscriptionLocation(Properties ctx, int WindowNo,
			final GridTab mTab, GridField mField, Object value)
	{

		boolean IsSOTrx = Env.isSOTrx(ctx, WindowNo);
		final String isSubscription = Env.getContext(ctx, WindowNo,
				"IsSubscription");

		if (IsSOTrx && isSubscription.equals("N"))
		{
			mTab.setValue("C_Subscription_ID", null);
			final I_C_OrderLine ol = GridTabWrapper.create(mTab,
					I_C_OrderLine.class);

			final int priceListId = Env.getContextAsInt(ctx, WindowNo,
					I_C_Order.COLUMNNAME_M_PriceList_ID);

			final BigDecimal qty = ol.getQtyEntered();
			ol.setQtyOrdered(qty);

			Services.get(IOrderLineBL.class).setPricesIfNotIgnored(ctx, ol, priceListId,
					qty, BigDecimal.ONE, false, // usePriceUOM = false 
					null);
		}
		if (IsSOTrx && isSubscription.equals("Y"))
		{
			int C_BPartner_ID = Env.getContextAsInt(ctx, WindowNo,
					"C_BPartner_ID");

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
					mTab.setValue("C_BPartner_Location_ID", rs.getInt(1));
					log
							.fine("C_BPartner_Location_ID for Subscription changed -> "
									+ rs.getInt(1));
				}
			}
			catch (SQLException e)
			{
				log.log(Level.SEVERE, sql, e);
				return e.getLocalizedMessage();
			}
			finally
			{
				DB.close(rs, pstmt);
				rs = null;
				pstmt = null;
			}
		}
		return "";
	}

	public String qtyEntered(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value,
			final Object oldValue)
	{

		if (isCalloutActive())
		{// prevent recursive
			return "";
		}

		final I_C_OrderLine ol = GridTabWrapper.create(mTab, I_C_OrderLine.class);

		final boolean isSOTrx = Env.isSOTrx(ctx, WindowNo);

		if (!isSOTrx || ol.getC_Flatrate_Conditions_ID() <= 0)
		{
			return ""; // leave this job to the adempiere standard callouts
		}

		updatePrices(ctx, ol, isSOTrx);

		return "";
	}

	private void updatePrices(
			final Properties ctx,
			final I_C_OrderLine ol,
			final boolean isSOTrx)
	{
		final I_C_Flatrate_Conditions subscription = ol.getC_Flatrate_Conditions();

		final int pricingSysytemId = subscription.getM_PricingSystem_ID();

		final int bPartnerLocationId = ol.getC_BPartner_Location_ID();

		final Timestamp date = ol.getC_Order().getDateOrdered();

		final I_M_PriceList subscriptionPL =
				Services.get(IProductPA.class).retrievePriceListByPricingSyst(ctx, pricingSysytemId, bPartnerLocationId, isSOTrx, TRX_NAME);

		final ISubscriptionBL subscriptionBL = Services.get(ISubscriptionBL.class);

		final int numberOfRuns =
				subscriptionBL.computeNumberOfRuns(subscription.getC_Flatrate_Transition(), date);

		final I_C_Flatrate_Matching matching =
				subscriptionBL.retrieveMatching(ctx, ol.getC_Flatrate_Conditions_ID(), ol.getM_Product(), null);
		if (matching == null)
		{
			throw new AdempiereException("@C_Flatrate_Conditions_ID@ " + ol.getC_Flatrate_Conditions().getName() +
					", @M_Product_ID@ " + ol.getM_Product().getValue() +
					": @Conditions_Error_MatchingMissing@");
		}

		final BigDecimal qtyPerRun = matching.getQtyPerDelivery();

		// priceQty is the qty do be delivered during one complete subscription term
		final BigDecimal priceQty = qtyPerRun.multiply(new BigDecimal(numberOfRuns));

		// qtyEntered is the "number of subscriptions"
		final BigDecimal timesOrdered = ol.getQtyEntered();

		// qty ordered needs to be set because it will be used to compute the
		// line's NetLineAmount in MOrderLine.beforeSave()
		ol.setQtyOrdered(priceQty.multiply(timesOrdered));

		//
		// now compute the new prices
		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

		orderLineBL.setPricesIfNotIgnored(ctx, ol, subscriptionPL.getM_PriceList_ID(), priceQty, timesOrdered, 
				false, // usePriceUOM = false 
				null);
	}

}
