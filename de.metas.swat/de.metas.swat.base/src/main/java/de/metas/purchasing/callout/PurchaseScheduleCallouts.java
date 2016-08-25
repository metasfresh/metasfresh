package de.metas.purchasing.callout;

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


import java.math.BigDecimal;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.model.MProductPricing;
import org.compiere.model.MUser;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import de.metas.purchasing.model.I_M_PurchaseSchedule;

public class PurchaseScheduleCallouts extends CalloutEngine
{

	final public static String PURCHASE_SCHEDULE_MISSING_VENDOR = "PurchaseSchedule.MissingVendor";
	final public static String PURCHASE_SCHEDULE_MISSING_VENDOR_PL = "PurchaseSchedule.MissingVendorPL";
	final public static String PURCHASE_SCHEDULE_USER_NOT_SALESREP_2P = "PurchaseSchedule.UserNotSalesRep";

	public final static String INCLUDE_IN_PURCHASE = PurchaseScheduleCallouts.class.getName() + ".includeInPurchase";

	public final static String BPARTNER_ID = PurchaseScheduleCallouts.class.getName() + ".cBPartnerId";

	/**
	 * Checks if a business partner has been set if the user tries to check the checkbox.
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	public String includeInPurchase(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value,
			final Object oldValue)
	{
		if (isCalloutActive())
		{// prevent recursive
			return "";
		}

		final I_M_PurchaseSchedule purchaseSchedule = InterfaceWrapperHelper.create(mTab, I_M_PurchaseSchedule.class);

		if (!purchaseSchedule.isIncludeInPurchase())
		{
			// nothing to check
			return "";
		}

		final int userId = Env.getAD_User_ID(Env.getCtx());
		if (!MUser.isSalesRep(userId))
		{
			purchaseSchedule.setIncludeInPurchase(false);
			final MUser user = MUser.get(ctx, userId);
			return Msg.getMsg(ctx, PURCHASE_SCHEDULE_USER_NOT_SALESREP_2P, new Object[] { user.getC_BPartner().getName(), user.getName() });
		}

		if (purchaseSchedule.getC_BPartner_ID() <= 0)
		{
			purchaseSchedule.setIncludeInPurchase(false);
			purchaseSchedule.setPricePO(null);
			return Msg.getMsg(ctx, PURCHASE_SCHEDULE_MISSING_VENDOR);
		}

		final int plId = purchaseSchedule.getC_BPartner().getPO_PriceList_ID();
		if (plId <= 0)
		{
			purchaseSchedule.setIncludeInPurchase(false);
			purchaseSchedule.setPricePO(null);
			return Msg.getMsg(Env.getCtx(), PURCHASE_SCHEDULE_MISSING_VENDOR_PL);
		}
		return "";
	}

	public String IsDropShip(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value,
			final Object oldValue)
	{
		I_M_PurchaseSchedule ps = InterfaceWrapperHelper.create(mTab, I_M_PurchaseSchedule.class);
		if (!ps.isDropShip())
		{
			ps.setDropShip_BPartner_ID(0);
			ps.setDropShip_Location_ID(0);
			ps.setDropShip_User_ID(0);
		}
		return "";
	}

	/**
	 * Updates PricePO and IncludeInPurchase (latter only if no bp is selected).
	 * 
	 * @param ctx
	 * @param WindowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @param oldValue
	 * @return
	 */
	public String cBPartnerId(final Properties ctx, final int WindowNo,
			final GridTab mTab, final GridField mField, final Object value,
			final Object oldValue)
	{

		if (isCalloutActive())
		{// prevent recursive
			return "";
		}

		final I_M_PurchaseSchedule purchaseSchedule = InterfaceWrapperHelper.create(mTab, I_M_PurchaseSchedule.class);

		if (purchaseSchedule.getC_BPartner_ID() <= 0)
		{
			purchaseSchedule.setIncludeInPurchase(false);
			purchaseSchedule.setPricePO(null);
			return "";
		}

		final int plId = purchaseSchedule.getC_BPartner().getPO_PriceList_ID();
		if (plId == 0)
		{
			purchaseSchedule.setIncludeInPurchase(false);
			purchaseSchedule.setPricePO(null);
			return Msg.getMsg(Env.getCtx(), PURCHASE_SCHEDULE_MISSING_VENDOR_PL);
		}

		if (purchaseSchedule.getM_Product_ID() > 0 && purchaseSchedule.getQty().signum() != 0 && plId != 0)
		{
			final MProductPricing pricing =
					new MProductPricing(purchaseSchedule.getM_Product_ID(), purchaseSchedule.getC_BPartner_ID(),
							purchaseSchedule.getQty(),
							false);

			pricing.setM_PriceList_ID(plId);

			final BigDecimal pricePO = pricing.getPriceStd();
			purchaseSchedule.setPricePO(pricePO);
		}

		return "";
	}
}
