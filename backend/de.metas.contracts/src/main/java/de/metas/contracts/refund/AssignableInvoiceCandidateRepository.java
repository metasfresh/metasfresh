package de.metas.contracts.refund;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.springframework.stereotype.Repository;

import com.google.common.annotations.VisibleForTesting;

import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
import lombok.NonNull;

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

@Repository
public class AssignableInvoiceCandidateRepository
{
	private AssignableInvoiceCandidateFactory assignableInvoiceCandidateFactory;

	public AssignableInvoiceCandidateRepository(
			@NonNull final AssignableInvoiceCandidateFactory assignableInvoiceCandidateFactory)
	{
		this.assignableInvoiceCandidateFactory = assignableInvoiceCandidateFactory;
	}

	public AssignableInvoiceCandidate getById(@NonNull final InvoiceCandidateId id)
	{
		final I_C_Invoice_Candidate invoiceCandidateRecord = load(id.getRepoId(), I_C_Invoice_Candidate.class);
		return assignableInvoiceCandidateFactory.ofRecord(invoiceCandidateRecord);
	}

	public AssignableInvoiceCandidate ofRecord(@NonNull final I_C_Invoice_Candidate invoiceCandidateRecord)
	{
		return assignableInvoiceCandidateFactory.ofRecord(invoiceCandidateRecord);
	}

	public Iterator<AssignableInvoiceCandidate> getAllAssigned(
			@NonNull final RefundInvoiceCandidate refundInvoiceCandidate)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(
						I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Term_ID,
						refundInvoiceCandidate.getId().getRepoId())
				.andCollect(
						I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Assigned_ID,
						I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(assignableInvoiceCandidateFactory::ofRecord)
				.iterator();
	}

	/**
	 * In production, assignable invoice candidates are created elsewhere and are only loaded if they are relevant for refund contracts.
	 * That's why this method is intended only for (unit-)testing.
	 */
	@VisibleForTesting
	public AssignableInvoiceCandidate saveNew(@NonNull final AssignableInvoiceCandidate assignableCandidate)
	{
		final I_C_Invoice_Candidate assignableCandidateRecord = newInstance(I_C_Invoice_Candidate.class);
		saveRecord(assignableCandidateRecord);

		return assignableCandidate
				.toBuilder()
				.id(InvoiceCandidateId.ofRepoId(assignableCandidateRecord.getC_Invoice_Candidate_ID()))
				.build();
	}
}
