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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.common.util.time.SystemTime;
import de.metas.dunning.api.IDunnableDoc;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.api.impl.DunnableDoc;
import de.metas.dunning.invoice.api.IInvoiceSourceDAO;
import de.metas.dunning.model.I_C_Dunning_Candidate_Invoice_v1;
import de.metas.dunning.spi.impl.AbstractDunnableSource;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.util.collections.IteratorUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_InvoicePaySchedule;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Optional;

public class InvoiceSource extends AbstractDunnableSource
{
	@Override
	protected Iterator<IDunnableDoc> createRawSourceIterator(final IDunningContext context)
	{
		final Iterator<I_C_Dunning_Candidate_Invoice_v1> it = Services.get(IInvoiceSourceDAO.class).retrieveDunningCandidateInvoices(context);
		return IteratorUtils.map(it, candidate -> createDunnableDoc(context, candidate));
	}

	private IDunnableDoc createDunnableDoc(
			@NonNull final IDunningContext context,
			@NonNull final I_C_Dunning_Candidate_Invoice_v1 candidate)
	{
		final int invoiceId = candidate.getC_Invoice_ID();
		final int invoicePayScheduleId = candidate.getC_InvoicePaySchedule_ID();
		final int adClientId = candidate.getAD_Client_ID();
		final OrgId adOrgId = OrgId.ofRepoId(candidate.getAD_Org_ID());
		final int bpartnerId = candidate.getC_BPartner_ID();
		final int bpartnerLocationId = candidate.getC_BPartner_Location_ID();
		final int contactId = candidate.getAD_User_ID();
		final int currencyId = candidate.getC_Currency_ID();
		final BigDecimal grandTotal = candidate.getGrandTotal();
		final BigDecimal openAmt = candidate.getOpenAmt();
		final boolean isInDispute = candidate.isInDispute();
		final int sectionCodeId = candidate.getM_SectionCode_ID();

		final String documentNo; // FRESH-504
		final String poReference;

		final String tableName;
		final int recordId;
		if (invoicePayScheduleId > 0)
		{
			tableName = I_C_InvoicePaySchedule.Table_Name;
			recordId = invoicePayScheduleId;

			// The table C_InvoicePaySchedule does not have the column DocumentNo. In this case, the documentNo is null
			documentNo = null;
			poReference = null;
		}
		else
		// if (C_Invoice_ID > 0)
		{
			tableName = I_C_Invoice.Table_Name;
			recordId = invoiceId;

			final I_C_Invoice invoice = InterfaceWrapperHelper.create(
					context.getCtx(),
					invoiceId,
					I_C_Invoice.class,
					ITrx.TRXNAME_ThreadInherited);

			if (invoice == null)
			{
				// shall not happen
				// in case of no referenced record the documentNo is null.

				documentNo = null;
				poReference = null;
			}
			else
			{
				documentNo = invoice.getDocumentNo();
				poReference = invoice.getPOReference();
			}
		}

		final LocalDateAndOrgId systemTime = LocalDateAndOrgId.ofLocalDate(SystemTime.asLocalDate(orgDAO.getTimeZone(adOrgId)), adOrgId);
		final LocalDateAndOrgId dueDate = LocalDateAndOrgId.ofTimestamp(candidate.getDueDate(), adOrgId, orgDAO::getTimeZone);
		final LocalDateAndOrgId dunningDate = Optional.ofNullable(context.getDunningDate()).orElse(systemTime);

		final int daysDue = TimeUtil.getDaysBetween(dueDate, dunningDate);

		final LocalDateAndOrgId dunningGrace = LocalDateAndOrgId.ofNullableTimestamp(candidate.getDunningGrace(), adOrgId, orgDAO::getTimeZone);

		return new DunnableDoc(tableName,
							   recordId,
							   documentNo, // FRESH-504 DocumentNo is also needed
							   adClientId,
							   adOrgId.getRepoId(),
							   bpartnerId,
							   bpartnerLocationId,
							   contactId,
							   currencyId,
							   grandTotal,
							   openAmt,
							   dueDate,
							   dunningGrace,
							   daysDue,
							   sectionCodeId,
				isInDispute,
				poReference);
	}

	@Override
	public String getSourceTableName()
	{
		return I_C_Invoice.Table_Name;
	}
}
