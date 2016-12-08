package de.metas.invoicecandidate.process;

import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;

import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.process.ProcessInfo;
import de.metas.process.JavaProcess;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class C_Invoice_Candidate_Close extends JavaProcess
{
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	@Override
	protected String doIt() throws Exception
	{
		final Iterator<I_C_Invoice_Candidate> selectedCandidates = retrieveSelectedCandidates();
		if (!selectedCandidates.hasNext())
		{
			throw new AdempiereException("@NoSelection@");
		}

		invoiceCandBL.closeInvoiceCandidates(selectedCandidates);

		return MSG_OK;
	}

	private Iterator<I_C_Invoice_Candidate> retrieveSelectedCandidates()
	{
		final ProcessInfo processInfo = getProcessInfo();
		final IQueryFilter<I_C_Invoice_Candidate> userSelectionFilter = processInfo.getQueryFilter();

		return queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class, processInfo)
				.filter(userSelectionFilter) // Apply the user selection filter
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClient()
				//
				.create()
				.iterate(I_C_Invoice_Candidate.class);
	}

}
