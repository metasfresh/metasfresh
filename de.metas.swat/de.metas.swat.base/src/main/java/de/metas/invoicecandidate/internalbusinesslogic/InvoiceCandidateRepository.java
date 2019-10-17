package de.metas.invoicecandidate.internalbusinesslogic;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.springframework.stereotype.Repository;

import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import lombok.NonNull;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Repository
public class InvoiceCandidateRepository
{
	private final InvoiceCandidateRecordService invoiceCandidateRecordService;

	public InvoiceCandidateRepository(@NonNull final InvoiceCandidateRecordService invoiceCandidateRecordService)
	{
		this.invoiceCandidateRecordService = invoiceCandidateRecordService;

	}

	public InvoiceCandidate getById(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		final I_C_Invoice_Candidate invoiceCandidateRecord = load(invoiceCandidateId, I_C_Invoice_Candidate.class);
		return invoiceCandidateRecordService.ofRecord(invoiceCandidateRecord);
	}

	public InvoiceCandidateId save(@NonNull final InvoiceCandidate invoiceCandidate)
	{
		final I_C_Invoice_Candidate invoiceCandidateRecord = load(invoiceCandidate.getId(), I_C_Invoice_Candidate.class);

		invoiceCandidateRecordService.updateRecord(invoiceCandidate, invoiceCandidateRecord);

		saveRecord(invoiceCandidateRecord);
		return InvoiceCandidateId.ofRepoId(invoiceCandidateRecord.getC_Invoice_Candidate_ID());
	}



}
