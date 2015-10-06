package de.schaeffer.pay.misc;

/*
 * #%L
 * de.metas.banking.base
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


import java.sql.Timestamp;
import java.util.Properties;

import org.adempiere.util.Services;
import org.compiere.apps.ADialog;
import org.compiere.model.MPayment;
import org.compiere.model.MSysConfig;
import org.compiere.model.MSystem;
import org.compiere.model.PaymentProcessor;

import de.metas.adempiere.form.IClientUI;
import de.schaeffer.pay.exception.TelecashUserException;
import de.schaeffer.pay.service.ITelecashBL;

public class TelecashProcessor extends PaymentProcessor
{
	public static final String SYSCONFIG_IsSandbox = "de.schaeffer.pay.misc.TelecashProcessor.IsSandbox";

	private boolean processedOk = false;

	@Override
	public boolean isProcessedOK()
	{
		return processedOk;
	}

	@Override
	public boolean processCC() throws IllegalArgumentException
	{
		final ITelecashBL teleCashBL = Services.get(ITelecashBL.class);

		try
		{
			if (isSandboxProcessing())
				processedOk = processSandbox();
			else
				processedOk = teleCashBL.performSale(p_mp);
			return processedOk;

		}
		catch (TelecashUserException e)
		{
			processedOk = false;
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	private boolean isSandboxProcessing()
	{
		final Properties ctx = p_mp.getCtx();
		final int AD_Client_ID = p_mp.getAD_Client_ID();
		final int AD_Org_ID = p_mp.getAD_Org_ID();
		final boolean isSandbox = MSysConfig.getBooleanValue(SYSCONFIG_IsSandbox, false, AD_Client_ID, AD_Org_ID);

		String systemStatus = MSystem.get(ctx).getSystemStatus();
		if (MSystem.SYSTEMSTATUS_Production.equals(systemStatus))
		{
			if (isSandbox)
			{
				throw new RuntimeException("Sandbox testing enabled (" + SYSCONFIG_IsSandbox + "=Y) but system is in production.\n"
						+ " If you really want to test it please change AD_System.SystemStatus.");
			}
			return false;
		}

		return isSandbox;
	}

	private boolean processSandbox()
	{
		final MPayment payment = p_mp;

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < payment.get_ColumnCount(); i++)
		{
			String name = payment.get_ColumnName(i);
			Object value = payment.get_Value(i);
			sb.append(name).append(": ").append(value).append("\n");
		}

		String documentNo = payment.getC_Invoice_ID() > 0 ? payment.getC_Invoice().getDocumentNo() : payment.getC_Order().getDocumentNo();
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String refNo = now + "_" + documentNo;

		String orig_TrxID = "test_" + payment.getC_Payment_ID();

		String message = "Do you confirm following online payment:\n"
				+ " Summary: " + payment.getSummary() + "\n"
				+ " Orig TrxID: " + orig_TrxID + "\n"
				+ " Reference#: " + refNo + "\n"
				+ "\n"
				+ " Details:\n"
				+ sb.toString();
		
		IClientUI clientUI = Services.get(IClientUI.class);
		final boolean confirmed;
		if (clientUI != null)
			confirmed = clientUI.ask(0, "Confirm", message);
		else
			confirmed = ADialog.ask(0, null, "Confirm", message);

		if (confirmed)
		{
			payment.setOrig_TrxID(orig_TrxID);
			payment.setR_RespMsg("OK");
			payment.saveEx();
			payment.setR_PnRef(refNo);
		}
		else
		{
			RuntimeException e = new RuntimeException("Sandbox: User did not confirm this online payment");
			payment.setR_RespMsg(e.getMessage());
			throw e;
		}

		return confirmed;
	}

}
