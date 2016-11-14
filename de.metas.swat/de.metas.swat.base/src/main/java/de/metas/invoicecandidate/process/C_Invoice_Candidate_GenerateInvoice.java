/**
 *
 */
package de.metas.invoicecandidate.process;

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


import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_C_Invoice;
import org.compiere.process.ProcessInfoParameter;
import org.compiere.process.SvrProcess;

import de.metas.adempiere.util.ADHyperlinkBuilder;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL.IInvoiceGenerateResult;

/**
 * @author tsa
 *
 */
public class C_Invoice_Candidate_GenerateInvoice extends SvrProcess
{
	private boolean p_Selection = true;

	private boolean p_IgnoreInvoiceSchedule = false;

	public static final String CHECKBOX_IGNORE_INVOICE_SCHEDULE = "IgnoreInvoiceSchedule";

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
			{
				;
			}
			else if (name.equals("Selection"))
			{
				p_Selection = "Y".equals(para.getParameter());
			}
			else if (name.equals(CHECKBOX_IGNORE_INVOICE_SCHEDULE))
			{
				p_IgnoreInvoiceSchedule = "Y".equals(para.getParameter());
			}
		}
	}

	@Override
	protected String doIt() throws Exception
	{
		if (!p_Selection)
		{
			throw new IllegalStateException("Invoices can only be generated from selection");
		}

		final IInvoiceCandBL service = Services.get(IInvoiceCandBL.class);
		final IInvoiceGenerateResult result =
				service.generateInvoicesFromSelection(getCtx(), getAD_PInstance_ID(), p_IgnoreInvoiceSchedule, this, get_TrxName());

		final ADHyperlinkBuilder linkHelper = new ADHyperlinkBuilder();
		final StringBuffer summary = new StringBuffer("@Generated@");
		summary.append(" @C_Invoice_ID@ #").append(result.getInvoiceCount());
		if (result.getNotificationCount() > 0)
		{
			final String notificationsLink = linkHelper.createShowWindowHTML(
					"@AD_Note_ID@ #" + result.getNotificationCount(),
					I_AD_Note.Table_Name,
					result.getNotificationsWhereClause());
			summary.append(", ").append(notificationsLink);
		}

		// Output the actual invoices that were created.
		// Note that the list will be empty if storing the actual invoices has been switched off to avoid mass-data-memory problems.
		final IInvoiceBL invoiceBL = Services.get(IInvoiceBL.class);
		for (final I_C_Invoice invoice : result.getC_Invoices())
		{
			addLog(invoice.getC_Invoice_ID(), null, null, invoiceBL.getSummary(invoice));
		}

		return summary.toString();
	}
}
