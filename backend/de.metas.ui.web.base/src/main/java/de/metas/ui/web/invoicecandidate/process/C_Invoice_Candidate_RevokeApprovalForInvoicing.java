/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.invoicecandidate.process;

import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.IQuery;

import java.util.Set;

public class C_Invoice_Candidate_RevokeApprovalForInvoicing extends C_Invoice_Candidate_ProcessHelper
{

	@Override
	protected boolean isApproveForInvoicing()
	{
		return false;
	}


	/**
	 * Implementation detail: during `checkPreconditionsApplicable` `getProcessInfo` throws exception because it is not configured for the Process, so we ignore it.
	 */
	@Override
	protected IQuery<I_C_Invoice_Candidate> retrieveQuery(final boolean includeProcessInfoFilters)
	{
		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Candidate.class);
		if (includeProcessInfoFilters)
		{
			queryBuilder.filter(getProcessInfo().getQueryFilterOrElseFalse());
		}

		queryBuilder.addOnlyActiveRecordsFilter() // not processed
				.addNotNull(I_C_Invoice_Candidate.COLUMNNAME_C_Currency_ID)
				.addNotEqualsFilter(I_C_Invoice_Candidate.COLUMN_ApprovalForInvoicing, false) // not already rejected
		;

		// Only selected rows
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (!selectedRowIds.isAll())
		{
			final Set<Integer> invoiceCandidateIds = selectedRowIds.toIntSet();
			if (invoiceCandidateIds.isEmpty())
			{
				// shall not happen
				throw new AdempiereException("@NoSelection@");
			}

			queryBuilder.addInArrayFilter(I_C_Invoice_Candidate.COLUMN_C_Invoice_Candidate_ID, invoiceCandidateIds);
		}

		return queryBuilder.create();
	}
}
