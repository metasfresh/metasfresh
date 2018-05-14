package de.metas.contracts.refund;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.getValueOverrideOrValue;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.bpartner.BPartnerId;
import org.adempiere.util.Services;
import org.compiere.util.TimeUtil;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

public class InvoiceCandidateRepository
{
	public Optional<InvoiceCandidate> get(@NonNull final InvoiceCandidateQuery query)
	{
		final I_C_Invoice_Candidate recordOrNull = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_AD_Table_ID, getTableId(I_C_Flatrate_Term.class))
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Record_ID, query.getFlatrateTermId().getRepoId())
				.addEqualsFilter(I_C_Invoice_Candidate.COLUMN_Processed, false)
				.addCoalesceEqualsFilter(query.getInvoicableFrom(), I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice_Override, I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice)
				.orderBy()
				.addColumnAscending(I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice_Effective)
				.endOrderBy()
				.create()
				.first();

		return ofRecord(recordOrNull);
	}

	private Optional<InvoiceCandidate> ofRecord(@Nullable final I_C_Invoice_Candidate record)
	{
		if (record == null)
		{
			return Optional.empty();
		}

		final Timestamp invoicableFromDate = getValueOverrideOrValue(record, I_C_Invoice_Candidate.COLUMNNAME_DateToInvoice);

		final InvoiceCandidate invoiceCandidate = InvoiceCandidate.builder()
				.bpartnerId(BPartnerId.ofRepoId(record.getBill_BPartner_ID()))
				.invoiceableFrom(TimeUtil.asLocalDate(invoicableFromDate))
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.build();
		return Optional.of(invoiceCandidate);
	}

	public void associateCandidates(@Nullable final AssociationRequest request)
	{
		final I_C_Invoice_Candidate_Assignment assignmentRecord = loadOrCreateAssignmentRecord(request);
		assignmentRecord.setC_Invoice_Candidate_ID(request.getContractCandidate().getRepoId());
		assignmentRecord.setC_Flatrate_Term_ID(request.getFlatrateTermId().getRepoId());

		saveRecord(assignmentRecord);
	}

	private I_C_Invoice_Candidate_Assignment loadOrCreateAssignmentRecord(@NonNull final AssociationRequest request)
	{
		final int repoId = request.candidateToAssign.getRepoId();

		final I_C_Invoice_Candidate_Assignment existingAssignment = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Assigned_ID, repoId)
				.create()
				.firstOnly(I_C_Invoice_Candidate_Assignment.class);

		if (existingAssignment != null)
		{
			return existingAssignment;
		}
		final I_C_Invoice_Candidate_Assignment newAssignment = newInstance(I_C_Invoice_Candidate_Assignment.class);
		newAssignment.setC_Invoice_Candidate_Assigned_ID(repoId);
		return newAssignment;
	}

	@Value
	@Builder
	public static final class InvoiceCandidateQuery
	{
		FlatrateTermId flatrateTermId;
		LocalDate invoicableFrom;
	}

	@Value
	@Builder
	public static final class AssociationRequest
	{
		@NonNull
		InvoiceCandidateId contractCandidate;

		@NonNull
		InvoiceCandidateId candidateToAssign;

		@NonNull
		FlatrateTermId flatrateTermId;
	}
}
