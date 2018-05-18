package de.metas.contracts.refund;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Supplier;

import org.adempiere.util.Check;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.refund.InvoiceCandidateRepository.DeleteAssignmentsRequest;
import de.metas.contracts.refund.InvoiceCandidateRepository.RefundInvoiceCandidateQuery;
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

@Service
public class InvoiceCandidateAssignmentService
{
	private final RefundContractRepository refundContractRepository;

	private final InvoiceCandidateRepository invoiceCandidateRepository;

	public InvoiceCandidateAssignmentService(
			@NonNull final RefundContractRepository refundConfigRepository,
			@NonNull final InvoiceCandidateRepository invoiceCandidateRepository)
	{
		this.refundContractRepository = refundConfigRepository;
		this.invoiceCandidateRepository = invoiceCandidateRepository;
	}

	public AssignableInvoiceCandidate createOrFindRefundCandidateAndAssignIfFeasible(
			@NonNull final AssignableInvoiceCandidate invoiceCandidate)
	{
		final Optional<FlatrateTermId> flatrateTermId = refundContractRepository
				.getMatchingIdByInvoiceCandidate(invoiceCandidate);

		if (!flatrateTermId.isPresent())
		{
			deleteAssignmentIfExists(invoiceCandidate);
			return invoiceCandidate.toBuilder()
					.refundInvoiceCandidate(null).build();
		}

		final RefundInvoiceCandidateQuery query = RefundInvoiceCandidateQuery.builder()
				.refundContractId(flatrateTermId.get())
				.invoicableFrom(invoiceCandidate.getInvoiceableFrom())
				.build();

		final Supplier<? extends RefundInvoiceCandidate> refundCandidateSupplier = //
				() -> invoiceCandidateRepository.createRefundInvoiceCandidate(invoiceCandidate, flatrateTermId.get());
		final RefundInvoiceCandidate refundInvoiceCandidate = //
				invoiceCandidateRepository
						.getRefundInvoiceCandidate(query)
						.orElseGet(refundCandidateSupplier);

		final UnassignedPairOfCandidates request = UnassignedPairOfCandidates
				.builder()
				.assignableInvoiceCandidate(invoiceCandidate)
				.refundInvoiceCandidate(refundInvoiceCandidate)
				.build();

		return assignCandidates(request);
	}

	@VisibleForTesting
	AssignableInvoiceCandidate assignCandidates(
			@NonNull final UnassignedPairOfCandidates unAssignedPairOfCandidates)
	{
		final RefundInvoiceCandidate updateRefundCandidate = unAssignedPairOfCandidates.getRefundInvoiceCandidate()
				.withAddedMoneyAmount(unAssignedPairOfCandidates.getAssignableInvoiceCandidate());
		invoiceCandidateRepository.save(updateRefundCandidate);

		final UnassignedPairOfCandidates updatedPair = unAssignedPairOfCandidates
				.withRefundInvoiceCandidate(updateRefundCandidate);

		return invoiceCandidateRepository
				.saveCandidateAssignment(updatedPair);
	}

	public void removeAllAssignments(@NonNull final RefundInvoiceCandidate invoiceCandidate)
	{
		final DeleteAssignmentsRequest request = DeleteAssignmentsRequest
				.builder()
				.removeForAssignedCandidateId(invoiceCandidate.getId())
				.removeForRefundCandidateId(invoiceCandidate.getId())
				.onlyActive(false) // remove *all*, as the method name sais
				.build();
		invoiceCandidateRepository.deleteAssignments(request);
	}

	/**
	 * Note: assumes {@link AssignableInvoiceCandidate#isAssigned()} to be {@code true}.
	 */
	public UnassignedPairOfCandidates unassignCandidate(@NonNull final AssignableInvoiceCandidate assignableInvoiceCandidate)
	{
		final RefundInvoiceCandidate refundInvoiceCandidate = Check
				.assumeNotNull(
						assignableInvoiceCandidate.getRefundInvoiceCandidate(),
						"The given assignableInvoiceCandidate to unassign needs to have a non-null refundInvoiceCandidate",
						assignableInvoiceCandidate);

		deleteAssignmentIfExists(assignableInvoiceCandidate);

		final AssignableInvoiceCandidate withoutRefundInvoiceCandidate = assignableInvoiceCandidate
				.withoutRefundInvoiceCandidate();
		invoiceCandidateRepository.save(withoutRefundInvoiceCandidate);

		final RefundInvoiceCandidate withSubtractedMoneyAmount = refundInvoiceCandidate
				.withSubtractedMoneyAmount(assignableInvoiceCandidate);
		invoiceCandidateRepository.save(withSubtractedMoneyAmount);

		return UnassignedPairOfCandidates
				.builder()
				.assignableInvoiceCandidate(withoutRefundInvoiceCandidate)
				.refundInvoiceCandidate(withSubtractedMoneyAmount)
				.build();
	}

	private void deleteAssignmentIfExists(
			@NonNull final AssignableInvoiceCandidate invoiceCandidate)
	{
		final DeleteAssignmentsRequest request = DeleteAssignmentsRequest
				.builder()
				.removeForAssignedCandidateId(invoiceCandidate.getId())
				.build();
		invoiceCandidateRepository.deleteAssignments(request);
	}

	/**
	 * Resets/fixes the allocated amount by iterating all candidates that are associated to the given candidate.
	 * Should be used only if e.g. something was direct changed in the DB.
	 */
	public RefundInvoiceCandidate resetMoneyAmount(
			@NonNull final RefundInvoiceCandidate refundInvoiceCandidate)
	{

		RefundInvoiceCandidate updatedCandidate = refundInvoiceCandidate
				.toBuilder()
				.money(refundInvoiceCandidate.getMoney().toZero())
				.build();

		final Iterator<AssignableInvoiceCandidate> allAssigned = invoiceCandidateRepository.getAllAssigned(refundInvoiceCandidate);
		while (allAssigned.hasNext())
		{
			final AssignableInvoiceCandidate assigned = allAssigned.next();
			updatedCandidate = updatedCandidate.withAddedMoneyAmount(assigned);
		}

		invoiceCandidateRepository.save(updatedCandidate);
		return updatedCandidate;
	}
}
