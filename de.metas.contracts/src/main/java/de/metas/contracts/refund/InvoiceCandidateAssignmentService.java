package de.metas.contracts.refund;

import java.util.Iterator;
import java.util.List;

import org.adempiere.util.Check;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.contracts.refund.InvoiceCandidateRepository.DeleteAssignmentsRequest;
import de.metas.invoicecandidate.InvoiceCandidateId;
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
			@NonNull final AssignableInvoiceCandidate assignableCandidate)
	{
		final RefundContractQuery refundContractQuery = RefundContractQuery.of(assignableCandidate);
		final RefundContract refundContract = refundContractRepository.getByQuery(refundContractQuery).orElse(null);

		if (refundContract == null)
		{
			if (!assignableCandidate.isAssigned())
			{
				return assignableCandidate; // nothing to do
			}

			// unassign (which also subtracts the assigned money)
			final List<UnassignedPairOfCandidates> unassignResult = unassignCandidate(assignableCandidate);
			// all pairs have the same assignableInvoiceCandidate
			return unassignResult.get(0).getAssignableInvoiceCandidate();
		}

		final List<RefundInvoiceCandidate> matchingRefundCandidates = //
				refundInvoiceCandidateService.retrieveOrCreateMatchingCandidates(assignableCandidate, refundContract);
		final ImmutableMap<InvoiceCandidateId, RefundInvoiceCandidate> //
		id2matchingRefundCandidate = Maps.uniqueIndex(matchingRefundCandidates, RefundInvoiceCandidate::getId);

		final List<RefundInvoiceCandidate> refundInvoicesCandidateToAssign;

		final AssignableInvoiceCandidate reloadedInvoiceCandidate = invoiceCandidateRepository.getById(assignableCandidate.getId());
		if (reloadedInvoiceCandidate.isAssigned())
		{
			// figure out if the assigned refund candidate(s) change
			final List<AssignmentToRefundCandidate> reloadedAssigments = reloadedInvoiceCandidate.getAssignmentsToRefundCandidates();

			final ImmutableMap<InvoiceCandidateId, AssignmentToRefundCandidate> //
			id2reloadedAssigment = Maps.uniqueIndex(reloadedAssigments, a -> a.getRefundInvoiceCandidate().getId());

			final boolean assignedRefundCandidateIdsHaveChanged = !Objects.equal(id2reloadedAssigment.keySet(), id2matchingRefundCandidate.keySet());

			boolean assignedMoneyHasChanged = false;
			if (!assignedRefundCandidateIdsHaveChanged)
			{
				for (final RefundInvoiceCandidate matchingRefundInvoiceCandidate : id2matchingRefundCandidate.values())
				{
					// figure out if the assigned money changes
					final AssignmentToRefundCandidate newAssigment = refundInvoiceCandidateService
							.addAssignableMoney(
									matchingRefundInvoiceCandidate,
									assignableCandidate.getMoney());

					// get the reloaded assignment (it's there because assignedRefundCandidateIdChanges is false)
					final AssignmentToRefundCandidate reloadedAssigment = id2reloadedAssigment.get(matchingRefundInvoiceCandidate.getId());

					assignedMoneyHasChanged = !Objects.equal(
							reloadedAssigment.getMoneyAssignedToRefundCandidate(),
							newAssigment.getMoneyAssignedToRefundCandidate());
					if (assignedMoneyHasChanged)
					{
						break;
					}
				}
			}

			if (assignedRefundCandidateIdsHaveChanged || assignedMoneyHasChanged)
			{
				// the refund candidate matching the given invoiceCandidate parameter changed;
				// unassign (which also subtracts the assigned money);
				final List<UnassignedPairOfCandidates> unassignedPair = unassignCandidate(reloadedInvoiceCandidate);
				refundInvoicesCandidateToAssign = unassignedPair
						.stream()
						.map(UnassignedPairOfCandidates::getRefundInvoiceCandidate)
						.collect(ImmutableList.toImmutableList());
			}
			else
			{
				// the given invoiceCandidate was already up to date with the backend storage; nothing to do here
				return reloadedInvoiceCandidate;
			}
		}
		else
		{
			refundInvoicesCandidateToAssign = matchingRefundCandidates;
		}

		AssignableInvoiceCandidate result = assignableCandidate;
		for (final RefundInvoiceCandidate refundCandidateToAssign : refundInvoicesCandidateToAssign)
		{
			final UnassignedPairOfCandidates request = UnassignedPairOfCandidates
					.builder()
					.assignableInvoiceCandidate(assignableCandidate.withoutRefundInvoiceCandidate())
					.refundInvoiceCandidate(refundCandidateToAssign)
					.build();

			result = assignCandidates(request); // the result of the last method invocation has all the stuff we need.
		}

		return result;
	}

	@VisibleForTesting
	AssignableInvoiceCandidate assignCandidates(
			@NonNull final UnassignedPairOfCandidates unAssignedPairOfCandidates)
	{
		final AssignmentToRefundCandidate assignmentToRefundCandidate = refundInvoiceCandidateService
				.addAssignableMoney(
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
	public List<UnassignedPairOfCandidates> unassignCandidate(@NonNull final AssignableInvoiceCandidate assignableInvoiceCandidate)
	{
		final List<AssignmentToRefundCandidate> assignmentsToRefundCandidates = Check
				.assumeNotEmpty(
						assignableInvoiceCandidate.getAssignmentsToRefundCandidates(),
						"The given assignableInvoiceCandidate to unassign needs to have refundInvoiceCandidates",
						assignableInvoiceCandidate);

		deleteAssignmentIfExists(assignableInvoiceCandidate);

		final AssignableInvoiceCandidate withoutRefundInvoiceCandidate = assignableInvoiceCandidate
				.withoutRefundInvoiceCandidate();

		final ImmutableList.Builder<UnassignedPairOfCandidates> result = ImmutableList.builder();
		for (final AssignmentToRefundCandidate assignmentToRefundCandidate : assignmentsToRefundCandidates)
		{
			final RefundInvoiceCandidate withSubtractedMoneyAmount = assignmentToRefundCandidate
					.withSubtractedMoneyAmount()
					.getRefundInvoiceCandidate();
			invoiceCandidateRepository.save(withSubtractedMoneyAmount);

			final UnassignedPairOfCandidates unassignedPair = UnassignedPairOfCandidates
					.builder()
					.assignableInvoiceCandidate(withoutRefundInvoiceCandidate)
					.refundInvoiceCandidate(withSubtractedMoneyAmount)
					.build();
			result.add(unassignedPair);
		}
		return result.build();
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
					.addAssignableMoney(
							updatedCandidate,
							assigned.getMoney())
					.getRefundInvoiceCandidate();
		}

		invoiceCandidateRepository.save(updatedCandidate);
		return updatedCandidate;
	}

}
