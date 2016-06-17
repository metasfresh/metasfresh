package de.metas.invoicecandidate.process;

import java.util.Iterator;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.process.ProcessInfo;
import org.compiere.process.SvrProcess;

import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

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

public class C_Invoice_Candidate_Close extends SvrProcess
{

	@Override
	protected String doIt() throws Exception
	{
		final Iterator<I_C_Invoice_Candidate> selectedCandidates = retrieveSelectedCandidates();

		if (!selectedCandidates.hasNext())
		{
			throw new AdempiereException("@NoSelection@");
		}

		while (selectedCandidates.hasNext())
		{
			final I_C_Invoice_Candidate candidate = selectedCandidates.next();

			// close all the linked shipment schedules (the ones the candidate was based on)
			final Set<I_M_ShipmentSchedule> shipmentSchedules = Services.get(IShipmentSchedulePA.class).retrieveForInvoiceCandidate(candidate);

			for (final I_M_ShipmentSchedule schedule : shipmentSchedules)
			{
				Services.get(IShipmentScheduleBL.class).closeShipmentSchedule(schedule);
			}

			// close the candidate
			Services.get(IInvoiceCandBL.class).closeInvoiceCandidate(candidate);
		}

		return MSG_OK;
	}

	private Iterator<I_C_Invoice_Candidate> retrieveSelectedCandidates()
	{
		final ProcessInfo processInfo = getProcessInfo();

		final IQueryFilter<I_C_Invoice_Candidate> userSelectionFilter = getProcessInfo().getQueryFilter();

		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_C_Invoice_Candidate.class, processInfo);

		// Apply the user selection filter
		queryBuilder.filter(userSelectionFilter);

		queryBuilder.addOnlyActiveRecordsFilter()
				.addOnlyContextClient();

		return queryBuilder.create()
				.iterate(I_C_Invoice_Candidate.class);
	}

}
