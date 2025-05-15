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

import de.metas.edi.model.I_C_Doc_Outbound_Log;
import de.metas.edi.model.I_C_Invoice;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.IADTableDAO;

/**
 * For the selected doc-outbound-logs it gets the invoices that are completed and EDI-enabled, and invokes {@link C_Invoice_EDI_Export_JSON} for each of those invoices.
 */
public class C_Doc_Outbound_Log_Selection_Export_JSON extends C_Invoice_Selection_Export_JSON
{
	private final IADTableDAO tableDAO = Services.get(IADTableDAO.class);

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
