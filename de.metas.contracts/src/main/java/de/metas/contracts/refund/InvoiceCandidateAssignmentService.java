package de.metas.contracts.refund;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.CollectionUtils;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.contracts.refund.AssignableInvoiceCandidate.SplitResult;
import de.metas.contracts.refund.InvoiceCandidateAssignmentService.UnassignResult.UnassignResultBuilder;
import de.metas.contracts.refund.InvoiceCandidateRepository.DeleteAssignmentsRequest;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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

@Service
public class InvoiceCandidateAssignmentService
{
	private final RefundContractRepository refundContractRepository;

	private final InvoiceCandidateRepository invoiceCandidateRepository;

	private final RefundInvoiceCandidateService refundInvoiceCandidateService;

	private RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;

	private AssignableInvoiceCandidateRepository assignableInvoiceCandidateRepository;

	private AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository;

	public InvoiceCandidateAssignmentService(
			@NonNull final RefundContractRepository refundConfigRepository,
			@NonNull final InvoiceCandidateRepository invoiceCandidateRepository,
			@NonNull final AssignableInvoiceCandidateRepository assignableInvoiceCandidateRepository,
			@NonNull final RefundInvoiceCandidateService refundInvoiceCandidateService,
			@NonNull final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository,
			@NonNull final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository)
	{
		this.refundContractRepository = refundConfigRepository;
		this.invoiceCandidateRepository = invoiceCandidateRepository;
		this.assignableInvoiceCandidateRepository = assignableInvoiceCandidateRepository;
		this.refundInvoiceCandidateService = refundInvoiceCandidateService;
		this.refundInvoiceCandidateRepository = refundInvoiceCandidateRepository;
		this.assignmentToRefundCandidateRepository = assignmentToRefundCandidateRepository;
	}

	public UpdateAssignmentResult updateAssignment(
			@NonNull final AssignableInvoiceCandidate assignableCandidate)
	{
		final RefundContractQuery refundContractQuery = RefundContractQuery.of(assignableCandidate);
		final RefundContract refundContract = refundContractRepository.getByQuery(refundContractQuery).orElse(null);

		if (refundContract == null)
		{
			if (!assignableCandidate.isAssigned())
			{
				return UpdateAssignmentResult.noUpdateDone(assignableCandidate); // nothing to do
			}

			// unassign (which also subtracts the assigned money)
			final UnassignResult unassignResult = unassignCandidate(assignableCandidate);
			return UpdateAssignmentResult.updateDone(unassignResult.getAssignableCandidate());
		}

		final List<RefundInvoiceCandidate> matchingRefundCandidates = //
				refundInvoiceCandidateService.retrieveOrCreateMatchingRefundCandidates(assignableCandidate, refundContract);
		final ImmutableMap<InvoiceCandidateId, RefundInvoiceCandidate> //
		id2matchingRefundCandidate = Maps.uniqueIndex(matchingRefundCandidates, RefundInvoiceCandidate::getId);

		final List<RefundInvoiceCandidate> refundInvoicesCandidateToAssign;

		final AssignableInvoiceCandidate reloadedInvoiceCandidate = assignableInvoiceCandidateRepository
				.getById(assignableCandidate.getId())
				.toBuilder()
				.assignmentsToRefundCandidates(assignmentToRefundCandidateRepository.getAssignmentsToRefundCandidate(assignableCandidate.getId()))
				.build();;
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
					// figure out if the assigned money changed
					final AssignmentToRefundCandidate newAssigment = refundInvoiceCandidateService
							.addAssignableMoney(
									matchingRefundInvoiceCandidate,
									assignableCandidate);

					// get the reloaded assignment (we know it exists because assignedRefundCandidateIdChanges is false)
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
				// unassign (which also subtracts the assigned money),
				// then collect the unassigned refund candidates for reassignment.
				final UnassignResult unassignResult = unassignCandidate(reloadedInvoiceCandidate);
				refundInvoicesCandidateToAssign = unassignResult
						.getUnassignedPairs()
						.stream()
						.map(UnassignedPairOfCandidates::getRefundInvoiceCandidate)
						.collect(ImmutableList.toImmutableList());
			}
			else
			{
				// the given invoiceCandidate was already up to date with the backend storage; nothing to do here
				return UpdateAssignmentResult.noUpdateDone(reloadedInvoiceCandidate);
			}
		}
		else
		{
			refundInvoicesCandidateToAssign = matchingRefundCandidates;
		}

		IPair<AssignableInvoiceCandidate, Quantity> //
		assignedCandidateWithRemainingQty = ImmutablePair.of(
				assignableCandidate.withoutRefundInvoiceCandidates(),
				assignableCandidate.getQuantity());

		final List<AssignmentToRefundCandidate> assignments = new ArrayList<>();

		for (final RefundInvoiceCandidate refundCandidateToAssign : refundInvoicesCandidateToAssign)
		{
			final UnassignedPairOfCandidates unassignedPair = UnassignedPairOfCandidates
					.builder()
					.assignableInvoiceCandidate(assignableCandidate.withoutRefundInvoiceCandidates())
					.refundInvoiceCandidate(refundCandidateToAssign)
					.build();

			assignedCandidateWithRemainingQty = assignCandidates(
					unassignedPair,
					assignedCandidateWithRemainingQty.getRight());

			// the result of the last method invocation has all the stuff we need.
			assignments.addAll(assignedCandidateWithRemainingQty.getLeft().getAssignmentsToRefundCandidates());
		}

		final AssignableInvoiceCandidate resultCandidate = assignableCandidate
				.toBuilder()
				.clearAssignmentsToRefundCandidates()
				.assignmentsToRefundCandidates(assignments)
				.build();
		return UpdateAssignmentResult.updateDone(resultCandidate);
	}

	@Value
	public static class UpdateAssignmentResult
	{
		public static final UpdateAssignmentResult updateDone(@NonNull final AssignableInvoiceCandidate candidate)
		{
			return new UpdateAssignmentResult(true, candidate);
		}

		public static final UpdateAssignmentResult noUpdateDone(@NonNull final AssignableInvoiceCandidate candidate)
		{
			return new UpdateAssignmentResult(false, candidate);
		}

		/** {@code false} means that no update was required since the data as loaded from the DB was already up to date. */
		boolean updateWasDone;

		/** The result, as loaded from the DB. */
		AssignableInvoiceCandidate assignableInvoiceCandidate;
	}

	/**
	 * @return the assignable candidate with its new additional assignment, and the quantity that is still left to be assigned.
	 */
	@VisibleForTesting
	IPair<AssignableInvoiceCandidate, Quantity> assignCandidates(
			@NonNull final UnassignedPairOfCandidates unAssignedPairOfCandidates,
			@NonNull final Quantity quantityToAssign)
	{
		final AssignableInvoiceCandidate assignableCandidate = unAssignedPairOfCandidates.getAssignableInvoiceCandidate();
		final RefundInvoiceCandidate refundCandidate = unAssignedPairOfCandidates.getRefundInvoiceCandidate();

		final Quantity assignableQuantity = refundCandidate.computeAssignableQuantity();

		final Quantity quantityToAssignEffective = quantityToAssign.min(assignableQuantity);

		final AssignableInvoiceCandidate candidateToAssign;
		final boolean partialAssignRequired = assignableCandidate.getQuantity().compareTo(quantityToAssignEffective) > 0;
		if (partialAssignRequired)
		{
			final SplitResult splitResult = assignableCandidate.splitQuantity(quantityToAssignEffective.getAsBigDecimal());
			candidateToAssign = splitResult.getNewCandidate();
		}
		else
		{
			candidateToAssign = assignableCandidate;
		}

		final boolean assignableQtyIsEnough = assignableQuantity.compareTo(quantityToAssign) >= 0;
		final Quantity remainingQty;
		if (assignableQtyIsEnough)
		{
			remainingQty = Quantity.zero(assignableCandidate.getQuantity().getUOM());
		}
		else
		{
			remainingQty = quantityToAssign.subtract(quantityToAssignEffective);
		}

		final AssignmentToRefundCandidate assignmentToRefundCandidate = refundInvoiceCandidateService
				.addAssignableMoney(
						refundCandidate,
						candidateToAssign);

		final RefundInvoiceCandidate //
		updatedRefundCandidate = assignmentToRefundCandidate.getRefundInvoiceCandidate();
		refundInvoiceCandidateRepository.save(updatedRefundCandidate);

		final UnassignedPairOfCandidates //
		updatedPair = unAssignedPairOfCandidates.withAssignmentToRefundCandidate(assignmentToRefundCandidate);

		return ImmutablePair.of(
				invoiceCandidateRepository.saveCandidateAssignment(updatedPair),
				remainingQty);
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
	public UnassignResult unassignCandidate(@NonNull final AssignableInvoiceCandidate assignableInvoiceCandidate)
	{
		final UnassignResult result = unassignSingleCandidate(assignableInvoiceCandidate);

		final List<UnassignedPairOfCandidates> unassignedPairs = result.getUnassignedPairs();

		final RefundMode refundMode = CollectionUtils.extractSingleElement(
				unassignedPairs,
				pair -> pair.getRefundInvoiceCandidate().getRefundConfig().getRefundMode());

		if (RefundMode.ALL_MAX_SCALE.equals(refundMode))
		{
			Check.errorIf(unassignedPairs.size() > 1, "If refundMode={}, then there can be only one refund candidate; unassignResult={}", refundMode, result);
			final UnassignedPairOfCandidates unassignedPair = CollectionUtils.singleElement(unassignedPairs);

			final RefundInvoiceCandidate refundCandidate = unassignedPair.getRefundInvoiceCandidate();
			final RefundConfig currentRefundConfig = refundCandidate.getRefundConfig();
			final RefundConfig targetRefundConfig = refundCandidate
					.getRefundContract()
					.getRefundConfig(refundCandidate.getAssignedQuantity().getAsBigDecimal());

			// if accumulated: check if the current quantity still matches the respective candidate's current refund-config's minQty;
			if (!currentRefundConfig.getId().equals(targetRefundConfig.getId()))
			{
				// if not, then update the candidates' refund-config and money; don't forget I_C_Invoice_Candidate_Assignment.C_Flatrate_RefundConfig_ID
				resetMoneyAmount(refundCandidate);
			}
			return result;
		}

		// refundMode == PER_SCALE
		final RefundContract refundContract = CollectionUtils.extractSingleElement(
				unassignedPairs,
				pair -> pair.getRefundInvoiceCandidate().getRefundContract());

		final List<RefundInvoiceCandidate> matchingRefundCandidates = refundInvoiceCandidateService.retrieveMatchingRefundCandidates(
				assignableInvoiceCandidate, refundContract);

		if (matchingRefundCandidates.size() > 1)
		{
			final UnassignResultBuilder resultBuilder = result.toBuilder();

//			final Comparator<UnassignedPairOfCandidates> comparingByMinQty = Comparator
//					.comparing(pair -> pair.getRefundInvoiceCandidate().getRefundConfig().getMinQty());
//			final ImmutableList<UnassignedPairOfCandidates> sortedByMinQtyDesc = matchingRefundCandidates
//					.stream()
//					.sorted(comparingByMinQty.reversed())
//					.collect(ImmutableList.toImmutableList());

			final Comparator<RefundInvoiceCandidate> comparingByMinQty = Comparator
					.comparing(r -> r.getRefundConfig().getMinQty());
			final ImmutableList<RefundInvoiceCandidate> sortedByMinQtyDesc = matchingRefundCandidates
					.stream()
					.sorted(comparingByMinQty.reversed())
					.collect(ImmutableList.toImmutableList());

			final RefundInvoiceCandidate highestRefundInvoiceCandidate = sortedByMinQtyDesc
					.get(sortedByMinQtyDesc.size() - 1);

			Quantity gap = Quantity.zero(assignableInvoiceCandidate.getQuantity().getUOM());

			boolean higherCandidateHasAssignedQty = highestRefundInvoiceCandidate.getAssignedQuantity().signum() > 0;

			for (int i = sortedByMinQtyDesc.size() - 2; i > 1; i--)
			{
				final RefundInvoiceCandidate refundInvoiceCandidate = sortedByMinQtyDesc.get(i);

				final Quantity assignableQty = refundInvoiceCandidate.computeAssignableQuantity();
				if (assignableQty.isInfinite() || assignableQty.signum() <= 0)
				{
					continue;
				}

				if (higherCandidateHasAssignedQty)
				{
					gap = gap.add(assignableQty);
				}

				higherCandidateHasAssignedQty = higherCandidateHasAssignedQty || refundInvoiceCandidate.getAssignedQuantity().signum() > 0;
			}
			// TODO: if per-scale: check if
			// * the "lowest" candidate now has an assignable quantity (i.e. a "gap");
			// * and there are "higher" candidates with an assigned quantity
			// if yes then
			// then get the quantity of that "lowest" candidate's gap
			// take a look at the "highest" candidate with an assigned quantity

			if (gap.signum() > 0)
			{
				final List<AssignableInvoiceCandidate> assignableCandidatesToReassign = getAssignableCandidates(refundContract, gap);
				for (final AssignableInvoiceCandidate assignableCandidateToReassign : assignableCandidatesToReassign)
				{
					final UpdateAssignmentResult updateAssignmentResult = updateAssignment(assignableCandidateToReassign);
					if (updateAssignmentResult.isUpdateWasDone())
					{
						resultBuilder.additionalChangedCandidate(updateAssignmentResult.getAssignableInvoiceCandidate());
					}
				}
			}
			return resultBuilder.build();
		}

		return result;
	}

	/**
	 * Just unassign the given candidate for its refund candidates and subtracts the formerly assigned quantity and money from those candidates.
	 * Does not do anything about changed refund config scales.
	 */
	private UnassignResult unassignSingleCandidate(
			@NonNull final AssignableInvoiceCandidate assignableInvoiceCandidate)
	{
		final List<AssignmentToRefundCandidate> assignmentsToRefundCandidates = Check
				.assumeNotEmpty(
						assignableInvoiceCandidate.getAssignmentsToRefundCandidates(),
						"The given assignableInvoiceCandidate to unassign needs to have refundInvoiceCandidates",
						assignableInvoiceCandidate);

		deleteAssignmentIfExists(assignableInvoiceCandidate);

		final AssignableInvoiceCandidate withoutRefundInvoiceCandidate = assignableInvoiceCandidate
				.withoutRefundInvoiceCandidates();

		final UnassignResultBuilder resultBuilder = UnassignResult
				.builder()
				.assignableCandidate(withoutRefundInvoiceCandidate);

		for (final AssignmentToRefundCandidate assignmentToRefundCandidate : assignmentsToRefundCandidates)
		{
			final RefundInvoiceCandidate withSubtractedMoneyAmount = assignmentToRefundCandidate
					.withSubtractedMoneyAndQuantity()
					.getRefundInvoiceCandidate();
			refundInvoiceCandidateRepository.save(withSubtractedMoneyAmount);

			final UnassignedPairOfCandidates unassignedPair = UnassignedPairOfCandidates
					.builder()
					.assignableInvoiceCandidate(withoutRefundInvoiceCandidate)
					.refundInvoiceCandidate(withSubtractedMoneyAmount)
					.build();
			resultBuilder.unassignedPair(unassignedPair);
		}
		final UnassignResult result = resultBuilder.build();
		return result;
	}

	@Value
	@Builder(toBuilder = true)
	public static class UnassignResult
	{
		/**
		 * The assignable candidate after the unassignment.
		 * Note that this candidate has no assignments anymore.
		 */
		AssignableInvoiceCandidate assignableCandidate;

		/**
		 * Each pair's {@link UnassignedPairOfCandidates#getAssignableInvoiceCandidate()} is this result's {@link #assignableCandidate}.
		 */
		@Singular
		List<UnassignedPairOfCandidates> unassignedPairs;

		/**
		 * Further candidates whose assignments also changed due to the unassignment.
		 * Note that for {@link RefundMode#ALL_MAX_SCALE}, this list is always empty.
		 */
		@Singular("additionalChangedCandidate")
		List<AssignableInvoiceCandidate> additionalChangedCandidates;
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
	 * Should be used only if e.g. something was manually changed in the DB.
	 */
	public RefundInvoiceCandidate resetMoneyAmount(
			@NonNull final RefundInvoiceCandidate refundInvoiceCandidate)
	{
		RefundInvoiceCandidate updatedCandidate = refundInvoiceCandidate
				.toBuilder()
				.money(refundInvoiceCandidate.getMoney().toZero())
				.build();

		final Iterator<AssignableInvoiceCandidate> //
		allAssignedCandidates = assignableInvoiceCandidateRepository.getAllAssigned(refundInvoiceCandidate);

		while (allAssignedCandidates.hasNext())
		{
			final AssignableInvoiceCandidate assigned = allAssignedCandidates.next();
			updatedCandidate = refundInvoiceCandidateService
					.addAssignableMoney(
							updatedCandidate,
							assigned)
					.getRefundInvoiceCandidate();
		}

		refundInvoiceCandidateRepository.save(updatedCandidate);
		return updatedCandidate;
	}

	public List<AssignableInvoiceCandidate> getAssignableCandidates(
			@NonNull final RefundContract contract,
			@NonNull final Quantity requiredQuantity)
	{

		Iterator<I_C_Invoice_Candidate_Assignment> iterator = Collections.emptyIterator();

		for (final RefundConfig config : contract.getRefundConfigs())
		{
			final Iterator<I_C_Invoice_Candidate_Assignment> assignments = iterateAssignments(contract.getId(), config.getId());
			iterator = Iterators.concat(iterator, assignments);
		}

		final Map<InvoiceCandidateId, AssignableInvoiceCandidate> invoiceCandidateId2assignable = new HashMap<>();

		Quantity foundQuantity = Quantity.zero(requiredQuantity.getUOM());
		while (iterator.hasNext())
		{
			final I_C_Invoice_Candidate_Assignment assignmentRecord = iterator.next();
			final AssignmentToRefundCandidate assignment = assignmentToRefundCandidateRepository.ofRecordOrNull(assignmentRecord);
			if (assignment == null)
			{
				continue;
			}

			final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(assignmentRecord.getC_Invoice_Candidate_Assigned_ID());

			// create or update the assignable candidate for the given id; could be cone with compute() or merge() i guess,
			// but this seems to be easier to read
			AssignableInvoiceCandidate existingCandidate = invoiceCandidateId2assignable.get(invoiceCandidateId);
			if (existingCandidate == null)
			{
				existingCandidate = assignableInvoiceCandidateRepository.getById(invoiceCandidateId)
						.withoutRefundInvoiceCandidates();
			}

			final AssignableInvoiceCandidate updatedAssignableInvoiceCandidate = existingCandidate
					.toBuilder()
					.assignmentToRefundCandidate(assignment)
					.build();

			invoiceCandidateId2assignable.put(invoiceCandidateId, updatedAssignableInvoiceCandidate);

			// see if we are there yet
			foundQuantity = foundQuantity.add(assignment.getQuantityAssigendToRefundCandidate());
			if (foundQuantity.compareTo(requiredQuantity) >= 0)
			{
				break;
			}
		}

		return ImmutableList.copyOf(invoiceCandidateId2assignable.values());
	}

	private Iterator<I_C_Invoice_Candidate_Assignment> iterateAssignments(
			@NonNull final FlatrateTermId contractId,
			@NonNull final RefundConfigId refundConfigId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		return queryBL.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Invoice_Candidate_Assignment.COLUMN_C_Flatrate_Term_ID, contractId)
				.addEqualsFilter(I_C_Invoice_Candidate_Assignment.COLUMN_C_Flatrate_RefundConfig_ID, refundConfigId)
				.create()
				.iterate(I_C_Invoice_Candidate_Assignment.class);
	}
}
