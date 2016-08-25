package de.metas.commission.modelvalidator;

/*
 * #%L
 * de.metas.commission.base
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

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Constants;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.MClient;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.Env;
import org.compiere.util.Msg;
import org.slf4j.Logger;

import de.metas.commission.interfaces.I_C_InvoiceLine;
import de.metas.commission.interfaces.I_C_OrderLine;
import de.metas.commission.model.IInstanceTrigger;
import de.metas.commission.service.IInstanceTriggerBL;
import de.metas.logging.LogManager;

public class InvoiceLine implements ModelValidator
{

	private static final Logger logger = LogManager.getLogger(CommissionValidator.class);

	private int ad_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public void initialize(final ModelValidationEngine engine, final MClient client)
	{
		// client = null for global validator
		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
			InvoiceLine.logger.info("Initializing validator: " + this + " for client " + client.toString());
		}
		else
		{
			InvoiceLine.logger.info("Initializing global validator: " + this);
		}
		engine.addModelChange(org.compiere.model.I_C_InvoiceLine.Table_Name, this);
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		// nothing to do
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type)
	{
		Check.assume(po instanceof MInvoiceLine, po + " is instanceof MInvoiceLine");

		if (type == ModelValidator.TYPE_BEFORE_NEW || type == ModelValidator.TYPE_BEFORE_CHANGE)
		{
			//
			// Make sure that the given il(if SOTrx) has CommissionPoints and CommissionPointsSum.
			// Usually these two are set from the il's order line (see MInvoiceLineAsp).
			// Only act if they are still NULL.
			//
			final I_C_InvoiceLine il = InterfaceWrapperHelper.create(po, I_C_InvoiceLine.class);
			final I_C_Invoice invoice = il.getC_Invoice();

			if (invoice.isSOTrx())
			{
				final String errMsg = syncCommissionPoints(po, il, invoice);

				if (errMsg != null)
				{
					return errMsg;
				}
			}

			// 02590: make sure that a commission invoice line always has the commission charge-ID (unless it
			// explicitly already has a different charge or product ID)
			if (Constants.DOCBASETYPE_AEInvoice.equals(invoice.getC_DocTypeTarget().getDocBaseType()))
			{
				if (il.getM_Product_ID() == 0 && il.getC_Charge_ID() == 0)
				{
					final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
					final int chargeId =
							sysConfigBL.getIntValue(CommissionValidator.SYSCONFIG_INVOICE_C_Charge_ID, 0, il.getAD_Client_ID(), il.getAD_Org_ID());
					Check.assume(chargeId != 0, "Missing AD_SysConfig record for " + CommissionValidator.SYSCONFIG_INVOICE_C_Charge_ID);

					il.setC_Charge_ID(chargeId);
				}
			}
		}
		return null;
	}

	/**
	 * Make sure that neither CommissionPoints nor CommissionPointsSum is null, Update CommissionPointsSum if CommissionPoints or qtyEntered were changed and check if il.CommissionPoints equal
	 * ol.ComissionPoints
	 * 
	 * @param po
	 * @param il
	 * @param invoice
	 * @return error message or null
	 */
	private String syncCommissionPoints(final PO po, final I_C_InvoiceLine il, final I_C_Invoice invoice)
	{
		final IInstanceTriggerBL instanceTriggerBL = Services.get(IInstanceTriggerBL.class);

		final Properties ctx = po.getCtx();
		final String trxName = po.get_TrxName();
		String errorMsg;

		// metas kh, US049: Calculate ComPointsSum after setting ComPoints
		if (po.is_ValueChanged(IInstanceTrigger.COLUMNNAME_CommissionPoints))
		{
			errorMsg = instanceTriggerBL.setCommissionPointsSum(ctx, il, true, trxName);

			if (errorMsg != null)
			{
				return errorMsg;
			}

			instanceTriggerBL.updateCommissionPointsNet(il);
		}
		else
		{
			// If the Value is Changed to null, po.isValueChanged returns false -> check if null
			if (po.get_Value(IInstanceTrigger.COLUMNNAME_CommissionPoints) == null)
			{
				errorMsg = instanceTriggerBL.setCommissionPoints(ctx, il, true, trxName);

				if (errorMsg != null)
				{
					return errorMsg;
				}

				errorMsg = instanceTriggerBL.setCommissionPointsSum(ctx, il, true, trxName);

				if (errorMsg != null)
				{
					return errorMsg;
				}

				instanceTriggerBL.updateCommissionPointsNet(il);
			}

			// Also check if the ComPointsSum is null. Is not needed if the ComPoints were changed,
			// because in that case the ComPointsSum has already been set above.
			if (po.get_Value(IInstanceTrigger.COLUMNNAME_CommissionPointsSum) == null)
			{
				errorMsg = instanceTriggerBL.setCommissionPointsSum(ctx, il, true, trxName);

				if (errorMsg != null)
				{
					return errorMsg;
				}

				instanceTriggerBL.updateCommissionPointsNet(il);
			}
		}

		if (po.is_ValueChanged(org.compiere.model.I_C_InvoiceLine.COLUMNNAME_QtyEntered))
		{
			errorMsg = instanceTriggerBL.setCommissionPointsSum(ctx, il, true, trxName);

			if (errorMsg != null)
			{
				return errorMsg;
			}

			instanceTriggerBL.updateCommissionPointsNet(il);
		}

		// introduced by US654, fixed in 02843
		if (il.getC_OrderLine_ID() > 0 && !il.isManualCommissionPoints())
		{
			final I_C_OrderLine ol = InterfaceWrapperHelper.create(il.getC_OrderLine(), I_C_OrderLine.class);
			final String message = Msg.getMsg(Env.getCtx(), "OrderInvoiceCommissionPointsNotMatch", new Object[] { il.toString() });
			Check.assume(ol.getCommissionPoints().compareTo(il.getCommissionPoints()) == 0, message);
		}

		return null;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		// nothing to do
		return null;
	}

}
