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


import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.CalloutEngine;
import org.compiere.model.GridField;
import org.compiere.model.GridTab;
import org.compiere.util.Env;

import de.metas.commission.interfaces.I_C_InvoiceLine;
import de.metas.commission.service.IInstanceTriggerBL;

public class InvoiceLine extends CalloutEngine
{
	private static final String TRX_NAME = ITrx.TRXNAME_None;


	/** When this Flag is set to false, override previous points with default (If available take 
	 * the points from order line, else use the price list) 
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
		if(!(Boolean)value)
		{
			final I_C_InvoiceLine il = InterfaceWrapperHelper.create(mTab, I_C_InvoiceLine.class);
			final IInstanceTriggerBL instanceTriggerBL = Services.get(IInstanceTriggerBL.class);
			
			retVal = instanceTriggerBL.setCommissionPoints(ctx, il, false, TRX_NAME);
			
			if(!retVal.equals(""))
			{
				return retVal;
			}
			
			retVal = instanceTriggerBL.setCommissionPointsSum(ctx, il, false, TRX_NAME);
		}
		return retVal;
	}
	
	/**
	 * If commissionPoints is changed update CommissionPointsSum
	 * @param ctx
	 * @param windowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String commissionPoints(
			final Properties ctx, final int windowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		if (isCalloutActive() || !Env.isSOTrx(ctx, windowNo))
		{
			return "";
		}

		final I_C_InvoiceLine il = InterfaceWrapperHelper.create(mTab, I_C_InvoiceLine.class);

		final IInstanceTriggerBL instanceTriggerBL = Services.get(IInstanceTriggerBL.class);
		return instanceTriggerBL.setCommissionPointsSum(ctx, il, false, TRX_NAME);
	}

	/**
	 * If commissionPointsSum is changed update CommissionPoints
	 * @param ctx
	 * @param windowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String commissionPointsSum(
			final Properties ctx, final int windowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		if (!Env.isSOTrx(ctx, windowNo))
		{
			return "";
		}

		final I_C_InvoiceLine il = InterfaceWrapperHelper.create(mTab, I_C_InvoiceLine.class);

		Services.get(IInstanceTriggerBL.class).updateCommissionPointsNet(il);

		return "";
	}

	/**
	 * If commissionPointsSum is changed update CommissionPoints
	 * @param ctx
	 * @param windowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String qtyEntered(
			final Properties ctx, final int windowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		if (!Env.isSOTrx(ctx, windowNo))
		{
			return "";
		}

		final I_C_InvoiceLine il = InterfaceWrapperHelper.create(mTab, I_C_InvoiceLine.class);

		return Services.get(IInstanceTriggerBL.class).setCommissionPointsSum(ctx, il, false, TRX_NAME);
	}
	
	/**
	 * If the Product is changed update CommissionPoints
	 * @param ctx
	 * @param windowNo
	 * @param mTab
	 * @param mField
	 * @param value
	 * @return
	 */
	public String product(
			final Properties ctx, final int windowNo,
			final GridTab mTab, final GridField mField, final Object value)
	{
		if (!Env.isSOTrx(ctx, windowNo))
		{
			return "";
		}

		final I_C_InvoiceLine il = InterfaceWrapperHelper.create(mTab, I_C_InvoiceLine.class);

		return Services.get(IInstanceTriggerBL.class).setCommissionPoints(ctx, il, false, TRX_NAME);
	}
}
