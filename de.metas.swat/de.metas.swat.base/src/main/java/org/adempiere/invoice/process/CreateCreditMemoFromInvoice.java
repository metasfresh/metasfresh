package org.adempiere.invoice.process;

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

import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceCreditContext;
import org.adempiere.invoice.service.impl.InvoiceCreditContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.apps.AEnv;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

public class CreateCreditMemoFromInvoice extends SvrProcess
{
	private static final String PARA_C_DocType_ID = "C_DocType_ID";
	private int C_DocType_ID = -1;

	private static final String PARA_CompleteIt = "CompleteIt";
	private boolean completeIt = false;

	private static final String PARA_IsReferenceOriginalOrder = "IsReferenceOriginalOrder";
	private boolean referenceOriginalOrder = false;

	private static final String PARA_IsReferenceInvoice = "IsReferenceInvoice";
	private boolean referenceInvoice = false;

	/**
	 * @task http://dewiki908/mediawiki/index.php/08927_Add_feature_Gutgeschriebener_Betrag_erneut_abrechenbar_%28101267285473%29
	 */
	private static final String PARA_IsCreditedInvoiceReinvoicable = "IsCreditedInvoiceReinvoicable";
	private boolean creditedInvoiceReinvoicable = false;

	private int AD_Table_ID = 0;
	private int Record_ID = 0;

	private I_C_Invoice creditMemo = null;

	@Override
	protected String doIt() throws Exception
	{
		final Properties ctx = getCtx();
		final I_C_Invoice invoice = InterfaceWrapperHelper.create(ctx, getRecord_ID(), de.metas.adempiere.model.I_C_Invoice.class, get_TrxName());

		final IInvoiceCreditContext creditCtx = new InvoiceCreditContext(C_DocType_ID,
				completeIt,
				referenceOriginalOrder,
				referenceInvoice,
				creditedInvoiceReinvoicable);

		creditMemo = Services.get(IInvoiceBL.class).creditInvoice(invoice, creditCtx);

		final String documentNo = creditMemo.getDocumentNo();

		final String msg = "@Created@: "
				+ creditMemo.getC_DocTypeTarget().getName() + ", @DocumentNo@ "
				+ documentNo;

		AD_Table_ID = InterfaceWrapperHelper.getModelTableId(creditMemo);
		Record_ID = InterfaceWrapperHelper.getId(creditMemo);

		if (creditMemo.isProcessed())
		{
			// we won't zoom to the new credit memo, but instead shown the message
			addLog(msg);
		}
		return msg;
	}

	@Override
	protected void prepare()
	{
		final ProcessInfoParameter[] para = getParametersAsArray();

		for (int i = 0; i < para.length; i++)
		{
			final String name = para[i].getParameterName();

			if (para[i].getParameter() == null)
			{
				// do nothing
			}
			else if (CreateCreditMemoFromInvoice.PARA_C_DocType_ID.equals(name))
			{
				final BigDecimal bdC_DocType_ID = para[i].getParameterAsBigDecimal();
				C_DocType_ID = bdC_DocType_ID.intValue();
			}
			else if (CreateCreditMemoFromInvoice.PARA_CompleteIt.equals(name))
			{
				completeIt = para[i].getParameterAsBoolean();
			}
			else if (CreateCreditMemoFromInvoice.PARA_IsReferenceOriginalOrder.equals(name))
			{
				referenceOriginalOrder = para[i].getParameterAsBoolean();
			}
			else if (CreateCreditMemoFromInvoice.PARA_IsReferenceInvoice.equals(name))
			{
				referenceInvoice = para[i].getParameterAsBoolean();
			}
			else if (CreateCreditMemoFromInvoice.PARA_IsCreditedInvoiceReinvoicable.equals(name))
			{
				creditedInvoiceReinvoicable = para[i].getParameterAsBoolean();
			}
			else
			{
				log.error("Unknown Parameter: " + name);
			}
		}
	}

	@Override
	protected void postProcess(boolean success)
	{
		if (creditMemo == null || creditMemo.isProcessed())
		{
			// not zooming to the credit memo, because there is nothing more to change
			return;
		}
		AEnv.zoom(AD_Table_ID, Record_ID);
	}
}
