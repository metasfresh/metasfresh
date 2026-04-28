/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.edi.process.export.json;

import de.metas.document.archive.DocOutboundLogId;
import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.edi.model.I_C_Invoice;
import de.metas.invoice.InvoiceId;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.util.lang.impl.TableRecordReference;

import javax.annotation.Nullable;

/**
 * For the selected doc-outbound-logs it gets the invoices that are completed and EDI-enabled, and invokes {@link C_Invoice_EDI_Export_JSON} for each of those invoices.
 */
public class C_Doc_Outbound_Log_Selection_Export_JSON extends C_Invoice_Selection_Export_JSON
{
	private final IDocOutboundDAO docOutboundDAO = Services.get(IDocOutboundDAO.class);
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	@Override
	@Nullable
	protected InvoiceId extractSingleSelectedInvoiceId(@NonNull final IProcessPreconditionsContext context)
	{
		final DocOutboundLogId docOutboundLogId = DocOutboundLogId.ofRepoIdOrNull(context.getSingleSelectedRecordId());
		if (docOutboundLogId == null)
		{
			return null;
		}

		final de.metas.document.archive.model.I_C_Doc_Outbound_Log outboundLogRecord = docOutboundDAO.retrieveLog(docOutboundLogId);
		final TableRecordReference reference = TableRecordReference.ofReferenced(outboundLogRecord);
		if (!reference.tableNameEqualsTo(I_C_Invoice.Table_Name))
		{
			return null;
		}

		return InvoiceId.ofRepoIdOrNull(reference.getRecord_ID());
	}

	@Override
	@NonNull
	protected IQueryBuilder<I_C_Invoice> createSelectedInvoicesQueryBuilder()
	{
		return queryBL
				.createQueryBuilder(I_C_Doc_Outbound_Log.class)
				.filter(getProcessInfo().getQueryFilterOrElseFalse())
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Doc_Outbound_Log.COLUMNNAME_AD_Table_ID, tableDAO.retrieveTableId(I_C_Invoice.Table_Name))
				.andCollect(I_C_Doc_Outbound_Log.COLUMNNAME_Record_ID, I_C_Invoice.class);
	}
}
