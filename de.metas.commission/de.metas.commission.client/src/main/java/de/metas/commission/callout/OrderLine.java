package de.metas.commission.callout;

/*
 * #%L
 * de.metas.commission.client
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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigDAO;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Env;
import org.compiere.util.Trx;

import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.model.I_M_Product;
import de.metas.commission.interfaces.I_C_OrderLine;
import de.metas.commission.service.IInstanceTriggerBL;
import de.metas.commission.service.IOrderLineBL;

/**
 * 
 * @author ts
 * @see "<a href='http://dewiki908/mediawiki/index.php/Provisionsberechnung_%282009_0023_G106%29'>(2009 0023 G106)</a>"
 */
public class OrderLine extends CalloutEngine
{

	private static final String TRX_NAME = ITrx.TRXNAME_None;

	public String cSubscriptionId(
			final Properties ctx,
			final int WindowNo,
			final GridTab mTab,
			final GridField mField,
			final Object value)
	{
		return updateCommissionPoints(ctx, WindowNo, mTab) + updateCommissionPointsSum(ctx, WindowNo, mTab);
	}

	public String qtyEntered(
			final Properties ctx,
			final int WindowNo,
			final GridTab mTab,
			final GridField mField,
			final Object value)
	{
		return updateCommissionPoints(ctx, WindowNo, mTab) + updateCommissionPointsSum(ctx, WindowNo, mTab);
	}

	public String qtyOrdered(final Properties ctx,
			final int WindowNo,
			final GridTab mTab,
			final GridField mField,
			final Object value)
	{
		return updateCommissionPoints(ctx, WindowNo, mTab) + updateCommissionPointsSum(ctx, WindowNo, mTab);
	}

	public String mProductId(
			final Properties ctx,
			final int WindowNo,
			final GridTab mTab,
			final GridField mField,
			final Object value)
	{
		final boolean IsSOTrx = "Y".equals(Env.getContext(ctx, WindowNo, I_C_Order.COLUMNNAME_IsSOTrx));
		if (!IsSOTrx)
		{
			return "";
		}
		
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(mTab, I_C_OrderLine.class);
		
		final I_C_OrderLine orderLineOld;
		if (orderLine.getC_OrderLine_ID() > 0)
		{
			// old values are the latest values from database
			orderLineOld = InterfaceWrapperHelper.create(ctx, orderLine.getC_OrderLine_ID(), I_C_OrderLine.class, Trx.TRXNAME_None);
		}
		else
		{
			orderLineOld = null;
		}
		
		// US049: Set the commission points depending on the flag M_Product.IsDiverse
		if (orderLineOld != null && orderLine.getM_Product_ID() != orderLineOld.getM_Product_ID()) // When product was changed ...
		{
			final I_M_Product product = InterfaceWrapperHelper.create(orderLine.getM_Product(), I_M_Product.class);
			if (product != null)
			{
				if (product.isDiverse()) // ... to a diverse Product
				{
					orderLine.setIsManualCommissionPoints(true);
				}
				else
				// ... to a not diverse Product ...
				{
					final I_M_Product productOld = InterfaceWrapperHelper.create(orderLineOld.getM_Product(), I_M_Product.class);
					// If the product wasn't changed in line, the productOld is null
					if (productOld != null)
					{
						if (productOld.isDiverse()) // ... and previous Product was a diverse Product
						{
							orderLine.setIsManualCommissionPoints(false);
						}
					}
				}
			}
		}
		
		return updateCommissionPoints(ctx, WindowNo, mTab) + updateCommissionPointsSum(ctx, WindowNo, mTab);
	}

	public String mPriceListVersionId(
			final Properties ctx,
			final int WindowNo,
			final GridTab mTab,
			final GridField mField,
			final Object value)
	{
		return updateCommissionPoints(ctx, WindowNo, mTab) + updateCommissionPointsSum(ctx, WindowNo, mTab);
	}

	private String updateCommissionPoints(
			final Properties ctx,
			final int windowNo,
			final GridTab mTab)
	{
		if (isCalloutActive() || !Env.isSOTrx(ctx, windowNo))
		{
			return "";
		}

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(mTab, I_C_OrderLine.class);
		return Services.get(IInstanceTriggerBL.class).setCommissionPoints(ctx, ol, false, TRX_NAME);
	}

	private String updateCommissionPointsSum(
			final Properties ctx,
			final int windowNo,
			final GridTab mTab)
	{
		if (isCalloutActive() || !Env.isSOTrx(ctx, windowNo))
		{
			return "";
		}

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(mTab, I_C_OrderLine.class);
		return Services.get(IInstanceTriggerBL.class).setCommissionPointsSum(ctx, ol, false, TRX_NAME);
	}

	public String discount(
			final Properties ctx, final int windowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		if (isCalloutActive() || !Env.isSOTrx(ctx, windowNo))
		{
			return "";
		}

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(mTab, I_C_OrderLine.class);
		if (ol.getM_Product_ID() == 0)
		{
			return "";
		}
		ol.setIsManualDiscount(true);

		Services.get(IInstanceTriggerBL.class).updateCommissionPointsNet(ol);
		return "";
	}

	public String commissionPoints(
			final Properties ctx, final int windowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		if (isCalloutActive() || !Env.isSOTrx(ctx, windowNo))
		{
			return "";
		}

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(mTab, I_C_OrderLine.class);

		final IInstanceTriggerBL instanceTriggerBL = Services.get(IInstanceTriggerBL.class);
		return instanceTriggerBL.setCommissionPointsSum(ctx, ol, false, TRX_NAME);
	}

	public String commissionPointsSum(
			final Properties ctx, final int windowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		if (!Env.isSOTrx(ctx, windowNo))
		{
			return "";
		}

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(mTab, I_C_OrderLine.class);

		Services.get(IInstanceTriggerBL.class).updateCommissionPointsNet(ol);

		return "";
	}

	public String updateDiscounts(
			final Properties ctx, final int windowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		if (isCalloutActive() || !Env.isSOTrx(ctx, windowNo))
		{
			return "";
		}

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(mTab, I_C_OrderLine.class);
		if (ol.getM_Product_ID() <= 0)
		{
			return "";
		}

		// metas: cg: task 05085 start:
		final String updateDiscounts = Services.get(ISysConfigDAO.class).retrieveSysConfigValue(de.metas.commission.modelvalidator.OrderLine.SYSCONFIG_IS_UPDATE_DISCOUNTS, "N", ol.getAD_Client_ID(), ol.getAD_Org_ID());
		if ("Y".equals(updateDiscounts))
		{
			final BigDecimal oldVal = new BigDecimal(-1);

			final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
			orderLineBL.updateDiscounts(ctx, ol, oldVal, false, false, null);

			// 'updateDiscounts' changed the dispounts of other ols, too
			// therefore, we need to refresh the current tab.
			// in order not to loose the changes made to the current ol,
			// we also need to save the current tab
//			Integer row = 0;
//			row = mTab.getCurrentRow(); // tsa: buggy and is not needed: gridtab/gridtable should do this work
			mTab.dataSave(true);
//			mTab.setCurrentRow(row);  // tsa: buggy and is not needed: gridtab/gridtable should do this work
			mTab.dataRefreshAll();
		}
		// metas: cg: task 05085 end

		return "";
	}

	/** When this Flag is set to false, override previous points with default
	 * @param ctx
	 * @param windowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String isManualCommissionPoints(
			final Properties ctx, final int windowNo,
			final GridTab mTab, final GridField mField,	final Object value)
	{
		if (isCalloutActive() || !Env.isSOTrx(ctx, windowNo))
		{
			return "";
		}
		String retVal = "";
		if (!(Boolean)value)
		{
			final I_C_OrderLine ol = InterfaceWrapperHelper.create(mTab, I_C_OrderLine.class);
			final IInstanceTriggerBL instanceTriggerBL = Services.get(IInstanceTriggerBL.class);

			retVal = instanceTriggerBL.setCommissionPoints(ctx, ol, false, TRX_NAME);

			if (!retVal.equals(""))
			{
				return retVal;
			}

			retVal = instanceTriggerBL.setCommissionPointsSum(ctx, ol, false, TRX_NAME);
		}
		return retVal;
	}
}
