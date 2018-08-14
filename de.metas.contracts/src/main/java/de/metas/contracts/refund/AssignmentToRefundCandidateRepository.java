package de.metas.contracts.refund;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.List;
import java.util.Optional;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.Money;
import de.metas.quantity.Quantity;
import lombok.Getter;
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

public class AssignmentToRefundCandidateRepository
{
	@Getter
	private final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;


	public AssignmentToRefundCandidateRepository(
			@NonNull final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository)
	{
		this.refundInvoiceCandidateRepository = refundInvoiceCandidateRepository;
	}

	public List<AssignmentToRefundCandidate> getAssignmentsToRefundCandidate(
			@NonNull final InvoiceCandidateId assignableInvoiceCandidateId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final List<I_C_Invoice_Candidate_Assignment> assignmentRecords = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(
						I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Assigned_ID,
						assignableInvoiceCandidateId.getRepoId())
				.create()
				.list(I_C_Invoice_Candidate_Assignment.class); // we might have multiple records with different C_Flatrate_RefundConfig_ID

		final ImmutableList.Builder<AssignmentToRefundCandidate> result = ImmutableList.builder();
		for (final I_C_Invoice_Candidate_Assignment assignmentRecord : assignmentRecords)
		{
			final AssignmentToRefundCandidate assignmentToRefundCandidate = ofRecordOrNull(assignmentRecord);
			if (assignmentToRefundCandidate != null)
			{
				result.add(assignmentToRefundCandidate);
			}
		}
		return result.build();
	}

	public AssignmentToRefundCandidate ofRecordOrNull(@NonNull final I_C_Invoice_Candidate_Assignment assignmentRecord)
	{
		final I_C_Invoice_Candidate refundRecord = load(
				assignmentRecord.getC_Invoice_Candidate_Term_ID(),
				I_C_Invoice_Candidate.class);

		final Optional<RefundInvoiceCandidate> refundCandidate = refundInvoiceCandidateRepository.ofNullableRefundRecord(refundRecord);
		if (!refundCandidate.isPresent())
		{
			return null;
		}

		final Money assignedMoney = Money.of(
				assignmentRecord.getAssignedAmount(),
				refundCandidate.get().getMoney().getCurrencyId());

		final Quantity assignedQuantity = Quantity.of(assignmentRecord.getAssignedQuantity(), refundRecord.getM_Product().getC_UOM());

		final AssignmentToRefundCandidate assignmentToRefundCandidate = new AssignmentToRefundCandidate(
				refundCandidate.get(),
				assignedMoney,
				assignedQuantity);
		return assignmentToRefundCandidate;
	}
}
