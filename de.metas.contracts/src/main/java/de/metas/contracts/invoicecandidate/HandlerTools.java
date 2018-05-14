package de.metas.contracts.invoicecandidate;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class HandlerTools
{
	public static void invalidateCandidatesFor(final I_C_Flatrate_Term term)
	{
		final int tableId = InterfaceWrapperHelper.getTableId(I_C_Flatrate_Term.class);

		final IQuery<I_C_Invoice_Candidate> query = Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_AD_Table_ID, tableId)
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Record_ID, term.getC_Flatrate_Term_ID())
				.create();

		final IInvoiceCandDAO invoiceCandDB = Services.get(IInvoiceCandDAO.class);
		invoiceCandDB.invalidateCandsFor(query);
	}
}
