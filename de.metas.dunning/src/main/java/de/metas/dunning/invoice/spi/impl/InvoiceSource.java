package de.metas.dunning.invoice.spi.impl;

/*
 * #%L
 * de.metas.dunning
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
import java.util.Date;
import java.util.Iterator;

import org.adempiere.util.Services;
import org.adempiere.util.collections.ConvertIteratorWrapper;
import org.adempiere.util.collections.Converter;
import org.compiere.model.I_C_InvoicePaySchedule;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.dunning.api.IDunnableDoc;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.api.impl.DunnableDoc;
import de.metas.dunning.invoice.api.IInvoiceSourceDAO;
import de.metas.dunning.model.I_C_Dunning_Candidate_Invoice_v1;
import de.metas.dunning.spi.impl.AbstractDunnableSource;

public class InvoiceSource extends AbstractDunnableSource
{
	@Override
	protected Iterator<IDunnableDoc> createRawSourceIterator(final IDunningContext context)
	{
		final Iterator<I_C_Dunning_Candidate_Invoice_v1> it = Services.get(IInvoiceSourceDAO.class).retrieveDunningCandidateInvoices(context);

		return new ConvertIteratorWrapper<IDunnableDoc, I_C_Dunning_Candidate_Invoice_v1>(it, new Converter<IDunnableDoc, I_C_Dunning_Candidate_Invoice_v1>()
		{

			@Override
			public IDunnableDoc convert(I_C_Dunning_Candidate_Invoice_v1 value)
			{
				return createDunnableDoc(context, value);
			}
		});
	}

	private IDunnableDoc createDunnableDoc(final IDunningContext context, final I_C_Dunning_Candidate_Invoice_v1 candidate)
	{
		final int invoiceId = candidate.getC_Invoice_ID();
		final int invoicePayScheduleId = candidate.getC_InvoicePaySchedule_ID();
		final int adClientId = candidate.getAD_Client_ID();
		final int adOrgId = candidate.getAD_Org_ID();
		final int bpartnerId = candidate.getC_BPartner_ID();
		final int bpartnerLocationId = candidate.getC_BPartner_Location_ID();
		final int contactId = candidate.getAD_User_ID();
		final int currencyId = candidate.getC_Currency_ID();
		final BigDecimal grandTotal = candidate.getGrandTotal();
		final BigDecimal openAmt = candidate.getOpenAmt();
		final Date dateInvoiced = candidate.getDateInvoiced();
		final Date dueDate = candidate.getDueDate();
		final Date dunningGrace = candidate.getDunningGrace();
		final int paymentTermId = candidate.getC_PaymentTerm_ID();
		final boolean isInDispute = candidate.isInDispute();

		final String tableName;
		final int recordId;
		if (invoicePayScheduleId > 0)
		{
			tableName = I_C_InvoicePaySchedule.Table_Name;
			recordId = invoicePayScheduleId;
		}
		else
		// if (C_Invoice_ID > 0)
		{
			tableName = I_C_Invoice.Table_Name;
			recordId = invoiceId;
		}

		final int daysDue;
		if (invoicePayScheduleId > 0)
		{
			daysDue = TimeUtil.getDaysBetween(dueDate, context.getDunningDate());
		}
		else
		{
			daysDue = Services.get(IInvoiceSourceDAO.class).retrieveDueDays(paymentTermId, dateInvoiced, context.getDunningDate());
		}

		final IDunnableDoc dunnableDoc = new DunnableDoc(tableName, recordId
				, adClientId, adOrgId
				, bpartnerId, bpartnerLocationId, contactId
				, currencyId
				, grandTotal, openAmt
				, dueDate, dunningGrace
				, daysDue
				, isInDispute);

		return dunnableDoc;
	}

	@Override
	public String getSourceTableName()
	{
		return I_C_Invoice.Table_Name;
	}
}
