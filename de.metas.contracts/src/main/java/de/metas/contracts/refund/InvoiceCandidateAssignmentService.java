package de.metas.contracts.refund;

import java.util.Iterator;
import java.util.Optional;

import org.adempiere.util.Check;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;

import de.metas.contracts.refund.InvoiceCandidateRepository.DeleteAssignmentsRequest;
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

	private final RefundInvoiceCandidateService refundInvoiceCandidateService;

	public InvoiceCandidateAssignmentService(
			@NonNull final RefundContractRepository refundConfigRepository,
			@NonNull final InvoiceCandidateRepository invoiceCandidateRepository,
			@NonNull final RefundInvoiceCandidateService refundInvoiceCandidateService)
	{
		this.refundContractRepository = refundConfigRepository;
		this.invoiceCandidateRepository = invoiceCandidateRepository;
		this.refundInvoiceCandidateService = refundInvoiceCandidateService;
	}

	public AssignableInvoiceCandidate updateAssignment(
			@NonNull final AssignableInvoiceCandidate invoiceCandidate)
	{
		final RefundContractQuery refundContractQuery = RefundContractQuery.of(invoiceCandidate);
		final Optional<RefundContract> refundContractOptional = refundContractRepository.getByQuery(refundContractQuery);

		if (!refundContractOptional.isPresent() && invoiceCandidate.isAssigned())
		{
			// unassign (which also subtracts the assigned money)
			final UnassignedPairOfCandidates unassignResult = unassignCandidate(invoiceCandidate);
			return unassignResult.getAssignableInvoiceCandidate();
		}

		final RefundContract refundContract = Check.assumeNotEmpty(refundContractOptional,
				"Since this method was called, the given invoiceCandidate parameter needs to have a refund contract; invoiceCandidate={}",
				invoiceCandidate);

		final RefundInvoiceCandidate matchingRefundInvoiceCandidate = //
				refundInvoiceCandidateService.retrieveOrCreateMatchingCandidate(invoiceCandidate, refundContract);

		final RefundInvoiceCandidate refundInvoiceCandidateToAssign;

		final AssignableInvoiceCandidate reloadedInvoiceCandidate = invoiceCandidateRepository.getById(invoiceCandidate.getId());
		if (reloadedInvoiceCandidate.isAssigned())
		{
			// figure out if the assigned refund candidate changes
			final AssignmentToRefundCandidate reloadedAssigment = reloadedInvoiceCandidate.getAssignmentToRefundCandidate();

			final boolean assignedRefundCandidateIdChanges = !Objects.equal(
					reloadedAssigment.getRefundInvoiceCandidate().getId(),
					matchingRefundInvoiceCandidate.getId());

			// figure out if the assigned money changes
			final AssignmentToRefundCandidate newAssigment = refundInvoiceCandidateService
					.addPercentageOfMoney(
							matchingRefundInvoiceCandidate,
							invoiceCandidate.getMoney());

			final boolean assignedMoneyChanges = !Objects.equal(
					reloadedAssigment.getMoneyAssignedToRefundCandidate(),
					newAssigment.getMoneyAssignedToRefundCandidate());

			if (assignedRefundCandidateIdChanges || assignedMoneyChanges)
			{
				// the refund candidate matching the given invoiceCandidate parameter changed;
				// unassign (which also subtracts the assigned money);
				final UnassignedPairOfCandidates unassignResult = unassignCandidate(reloadedInvoiceCandidate);
				refundInvoiceCandidateToAssign = unassignResult.getRefundInvoiceCandidate();
			}
			else
			{
				// the given invoiceCandidate was already up to date with the backend storage; nothing to do here
				return reloadedInvoiceCandidate;
			}
		}
		else
		{
			refundInvoiceCandidateToAssign = matchingRefundInvoiceCandidate;
		}

		final UnassignedPairOfCandidates request = UnassignedPairOfCandidates
				.builder()
				.assignableInvoiceCandidate(invoiceCandidate.withoutRefundInvoiceCandidate())
				.refundInvoiceCandidate(refundInvoiceCandidateToAssign)
				.build();

		return assignCandidates(request);
	}

	@VisibleForTesting
	AssignableInvoiceCandidate assignCandidates(
			@NonNull final UnassignedPairOfCandidates unAssignedPairOfCandidates)
	{
		final AssignmentToRefundCandidate assignmentToRefundCandidate = refundInvoiceCandidateService
				.addPercentageOfMoney(
						unAssignedPairOfCandidates.getRefundInvoiceCandidate(),
						unAssignedPairOfCandidates.getAssignableInvoiceCandidate().getMoney());

		final RefundInvoiceCandidate updatedRefundCandidate = assignmentToRefundCandidate.getRefundInvoiceCandidate();
		invoiceCandidateRepository.save(updatedRefundCandidate);

		final UnassignedPairOfCandidates updatedPair = unAssignedPairOfCandidates
				.withAssignmentToRefundCandidate(assignmentToRefundCandidate);

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
		final AssignmentToRefundCandidate assignmentToRefundCandidate = Check
				.assumeNotNull(
						assignableInvoiceCandidate.getAssignmentToRefundCandidate(),
						"The given assignableInvoiceCandidate to unassign needs to have a non-null refundInvoiceCandidate",
						assignableInvoiceCandidate);

		deleteAssignmentIfExists(assignableInvoiceCandidate);

		final AssignableInvoiceCandidate withoutRefundInvoiceCandidate = assignableInvoiceCandidate
				.withoutRefundInvoiceCandidate();

		final RefundInvoiceCandidate withSubtractedMoneyAmount = assignmentToRefundCandidate
				.withSubtractedMoneyAmount()
				.getRefundInvoiceCandidate();
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
			updatedCandidate = refundInvoiceCandidateService
					.addPercentageOfMoney(
							updatedCandidate,
							assigned.getMoney())
					.getRefundInvoiceCandidate();
		}

		invoiceCandidateRepository.save(updatedCandidate);
		return updatedCandidate;
	}

}
