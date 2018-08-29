package de.metas.contracts.refund;

import static org.adempiere.util.collections.CollectionUtils.extractSingleElement;
import static org.adempiere.util.collections.CollectionUtils.singleElement;

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
import de.metas.contracts.refund.AssignCandidatesRequest.AssignCandidatesRequestBuilder;
import de.metas.contracts.refund.AssignableInvoiceCandidate.SplitResult;
import de.metas.contracts.refund.InvoiceCandidateAssignmentService.UnassignResult.UnassignResultBuilder;
import de.metas.contracts.refund.InvoiceCandidateRepository.DeleteAssignmentsRequest;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.contracts.refund.refundConfigChange.RefundConfigChangeService;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.quantity.Quantity;
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

	private RefundConfigChangeService refundConfigChangeService;

	public InvoiceCandidateAssignmentService(
			@NonNull final RefundContractRepository refundConfigRepository,
			@NonNull final InvoiceCandidateRepository invoiceCandidateRepository,
			@NonNull final AssignableInvoiceCandidateRepository assignableInvoiceCandidateRepository,
			@NonNull final RefundInvoiceCandidateService refundInvoiceCandidateService,
			@NonNull final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository,
			@NonNull final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository,
			@NonNull final RefundConfigChangeService refundConfigChangeService)
	{
		this.refundContractRepository = refundConfigRepository;
		this.invoiceCandidateRepository = invoiceCandidateRepository;
		this.assignableInvoiceCandidateRepository = assignableInvoiceCandidateRepository;
		this.refundInvoiceCandidateService = refundInvoiceCandidateService;
		this.refundInvoiceCandidateRepository = refundInvoiceCandidateRepository;
		this.assignmentToRefundCandidateRepository = assignmentToRefundCandidateRepository;
		this.refundConfigChangeService = refundConfigChangeService;
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
		// final RefundMode refundMode = refundContract.extractRefundMode();
		// if (RefundMode.PER_INDIVIDUAL_SCALE.equals(refundMode))
		// {
		return updateAssignmentForPerScaleConfig(assignableCandidate, refundContract);
		// }
		// return updateAssignmentForAccumulatedScaleConfig(assignableCandidate, refundContract);
	}

	private UpdateAssignmentResult updateAssignmentForPerScaleConfig(
			@NonNull final AssignableInvoiceCandidate assignableCandidate,
			@NonNull final RefundContract refundContract)
	{
		// retrieve or create refund candidates to which assignableCandidate shall be assigned
		final List<RefundInvoiceCandidate> matchingRefundCandidates = //
				refundInvoiceCandidateService.retrieveOrCreateMatchingRefundCandidates(assignableCandidate, refundContract);
		final ImmutableMap<InvoiceCandidateId, RefundInvoiceCandidate> //
		id2matchingRefundCandidate = Maps.uniqueIndex(matchingRefundCandidates, RefundInvoiceCandidate::getId);

		final List<RefundInvoiceCandidate> refundInvoicesCandidateToAssign;

		// reload from backend to find out if the assignableCandidate is already assigned or not
		final AssignableInvoiceCandidate reloadedAssignableCandidate = assignableInvoiceCandidateRepository
				.getById(assignableCandidate.getId())
				.toBuilder()
				.assignmentsToRefundCandidates(assignmentToRefundCandidateRepository.getAssignmentsToRefundCandidate(assignableCandidate))
				.build();
		if (reloadedAssignableCandidate.isAssigned())
		{
			// figure out if the assigned refund candidate(s) change
			final List<AssignmentToRefundCandidate> reloadedAssigments = reloadedAssignableCandidate.getAssignmentsToRefundCandidates();

			final ImmutableMap<InvoiceCandidateId, AssignmentToRefundCandidate> //
			id2reloadedAssigment = Maps.uniqueIndex(reloadedAssigments, a -> a.getRefundInvoiceCandidate().getId());

			final boolean assignedRefundCandidateIdsHaveChanged = !Objects.equal(id2reloadedAssigment.keySet(), id2matchingRefundCandidate.keySet());

			boolean assignedMoneyHasChanged = false;
			if (!assignedRefundCandidateIdsHaveChanged)
			{
				for (final RefundInvoiceCandidate matchingRefundInvoiceCandidate : id2matchingRefundCandidate.values())
				{
					// get the reloaded assignment (we know it exists because assignedRefundCandidateIdChanges is false)
					final AssignmentToRefundCandidate reloadedAssigment = id2reloadedAssigment.get(matchingRefundInvoiceCandidate.getId());

					assignedMoneyHasChanged = isMoneyHasChanged(matchingRefundInvoiceCandidate, assignableCandidate, reloadedAssigment);
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
				final UnassignResult unassignResult = unassignSingleCandidate(reloadedAssignableCandidate);
				refundInvoicesCandidateToAssign = unassignResult
						.getUnassignedPairs()
						.stream()
						.map(UnassignedPairOfCandidates::getRefundInvoiceCandidate)
						.filter(refundCand -> id2matchingRefundCandidate.containsKey(refundCand.getId()))
						.collect(ImmutableList.toImmutableList());
			}
			else
			{
				// the given invoiceCandidate was already up to date with the backend storage; nothing to do here
				return UpdateAssignmentResult.noUpdateDone(reloadedAssignableCandidate);
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
			final AssignCandidatesRequestBuilder assignCandidatesRequest = AssignCandidatesRequest
					.builder()
					.assignableInvoiceCandidate(assignableCandidate.withoutRefundInvoiceCandidates())
					.refundInvoiceCandidate(refundCandidateToAssign);

			for (final RefundConfig refundConfig : refundCandidateToAssign.getRefundConfigs())
			{
				assignCandidatesRequest.refundConfig(refundConfig);

				assignedCandidateWithRemainingQty = assignCandidates(
						assignCandidatesRequest.build(),
						assignedCandidateWithRemainingQty.getRight());

				final List<AssignmentToRefundCandidate> createAssignments = assignedCandidateWithRemainingQty
						.getLeft()
						.getAssignmentsToRefundCandidates();
				assignments.addAll(createAssignments);
			}
		}

		final AssignableInvoiceCandidate resultCandidate = assignableCandidate
				.toBuilder()
				.clearAssignmentsToRefundCandidates()
				.assignmentsToRefundCandidates(assignments)
				.build();
		return UpdateAssignmentResult.updateDone(resultCandidate);
	}

	// /** This method deals with assignableCandidate whose configs have {@link RefundMode#ALL_MAX_SCALE}. */
	// private UpdateAssignmentResult updateAssignmentForAccumulatedScaleConfig(
	// @NonNull final AssignableInvoiceCandidate assignableCandidate,
	// @NonNull final RefundContract refundContract)
	// {
	// // retrieve or create refund candidates to which assignableCandidate shall be assigned
	// final List<RefundInvoiceCandidate> matchingCandidates = refundInvoiceCandidateService.retrieveOrCreateMatchingRefundCandidates(assignableCandidate, refundContract);
	// final RefundInvoiceCandidate matchingRefundCandidate = singleElement(matchingCandidates);
	//
	// final RefundInvoiceCandidate refundCandidateToAssign;
	//
	// // reload from backend to find out if the assignableCandidate is already assigned or not
	// final AssignableInvoiceCandidate reloadedAssignableCandidate = assignableInvoiceCandidateRepository
	// .getById(assignableCandidate.getId())
	// .toBuilder()
	// .assignmentsToRefundCandidates(assignmentToRefundCandidateRepository.getAssignmentsToRefundCandidate(assignableCandidate))
	// .build();
	// if (reloadedAssignableCandidate.isAssigned())
	// {
	// final AssignmentToRefundCandidate reloadedAssigment = singleElement(reloadedAssignableCandidate.getAssignmentsToRefundCandidates());
	//
	// if (isMoneyHasChanged(matchingRefundCandidate, assignableCandidate, reloadedAssigment))
	// {
	// // the refund candidate matching the given invoiceCandidate parameter changed;
	// // unassign (which also subtracts the assigned money),
	// // then collect the unassigned refund candidates for reassignment.
	// final UnassignResult unassignResult = unassignSingleCandidate(reloadedAssignableCandidate);
	// final ImmutableList<RefundInvoiceCandidate> unassignedRefundCandidates = unassignResult
	// .getUnassignedPairs()
	// .stream()
	// .map(UnassignedPairOfCandidates::getRefundInvoiceCandidate)
	// .filter(refundCand -> refundCand.getId().equals(matchingRefundCandidate.getId()))
	// .collect(ImmutableList.toImmutableList());
	// if (unassignedRefundCandidates.size() == 1)
	// {
	// refundCandidateToAssign = singleElement(unassignedRefundCandidates);
	// }
	// else
	// {
	// refundCandidateToAssign = matchingRefundCandidate;
	// }
	// }
	// else
	// {
	// return UpdateAssignmentResult.noUpdateDone(reloadedAssignableCandidate);
	// }
	// }
	// else
	// {
	// refundCandidateToAssign = matchingRefundCandidate;
	// }
	//
	// final UnassignedPairOfCandidates unassignedPair = UnassignedPairOfCandidates
	// .builder()
	// .assignableInvoiceCandidate(assignableCandidate.withoutRefundInvoiceCandidates())
	// .refundInvoiceCandidate(refundCandidateToAssign)
	// .build();
	//
	// final IPair<AssignableInvoiceCandidate, Quantity> assignedCandidateWithRemainingQty = assignCandidates(
	// unassignedPair,
	// assignableCandidate.getQuantity());
	// Check.errorUnless(
	// assignedCandidateWithRemainingQty.getRight().isZero(),
	// "With RefundMode={}, the full quantity can be assigned; assignedCandidateWithRemainingQty={}",
	// RefundMode.ALL_MAX_SCALE, assignedCandidateWithRemainingQty);
	//
	// final AssignmentToRefundCandidate assignment = singleElement(assignedCandidateWithRemainingQty.getLeft().getAssignmentsToRefundCandidates());
	// final RefundInvoiceCandidate refundCandidateAfterAssignment = assignment.getRefundInvoiceCandidate();
	// final Quantity quantityAfterAssignment = refundCandidateAfterAssignment.getAssignedQuantity();
	// final RefundConfig refundConfigAfterAssignment = refundContract.getRefundConfig(quantityAfterAssignment.getAsBigDecimal());
	//
	// final List<AssignmentToRefundCandidate> resultAssignments;
	//
	// final boolean configHasChanged = !matchingRefundCandidate.getRefundConfig().getId().equals(refundConfigAfterAssignment.getId());
	// if (configHasChanged)
	// {
	// refundConfigChangeService.resetMoneyAmount(refundCandidateAfterAssignment, refundConfigAfterAssignment);
	// // reload the assignment(s) after the reset of refundCandidateAfterAssignment
	// resultAssignments = assignmentToRefundCandidateRepository.getAssignmentsToRefundCandidate(assignableCandidate);
	// }
	// else
	// {
	// resultAssignments = ImmutableList.of(assignment);
	// }
	//
	// final AssignableInvoiceCandidate resultCandidate = assignableCandidate
	// .toBuilder()
	// .clearAssignmentsToRefundCandidates()
	// .assignmentsToRefundCandidates(resultAssignments)
	// .build();
	// return UpdateAssignmentResult.updateDone(resultCandidate);
	// }

	private boolean isMoneyHasChanged(
			@NonNull final RefundInvoiceCandidate matchingRefundInvoiceCandidate,
			@NonNull final AssignableInvoiceCandidate assignableCandidate,
			@NonNull final AssignmentToRefundCandidate reloadedAssigment)
	{
		// final AssignmentToRefundCandidate newAssigment = refundInvoiceCandidateService
		// .addAssignableMoney(
		// matchingRefundInvoiceCandidate,
		// assignableCandidate);
		//
		// final boolean assignedMoneyHasChanged = !Objects.equal(
		// reloadedAssigment.getMoneyAssignedToRefundCandidate(),
		// newAssigment.getMoneyAssignedToRefundCandidate());
		//
		// return assignedMoneyHasChanged;
		return true; // TODO fix or cleanup
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
			@NonNull final AssignCandidatesRequest assignCandidatesRequest,
			@NonNull final Quantity quantityToAssign)
	{
		final AssignableInvoiceCandidate assignableCandidate = assignCandidatesRequest.getAssignableInvoiceCandidate();
		final RefundInvoiceCandidate refundCandidate = assignCandidatesRequest.getRefundInvoiceCandidate();

		final RefundConfig refundConfig = assignCandidatesRequest.getRefundConfig();

		final Quantity assignableQuantity = refundCandidate.computeAssignableQuantity(refundConfig);

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
						refundConfig,
						candidateToAssign);

		final RefundInvoiceCandidate //
		updatedRefundCandidate = assignmentToRefundCandidate.getRefundInvoiceCandidate();
		refundInvoiceCandidateRepository.save(updatedRefundCandidate);

		// final AssignCandidatesRequest //
		// updatedPair = assignCandidatesRequest.withAssignmentToRefundCandidate(assignmentToRefundCandidate);

		assignmentToRefundCandidateRepository.save(assignmentToRefundCandidate);

		return ImmutablePair.of(
				assignableCandidate
						.toBuilder()
						.assignmentToRefundCandidate(assignmentToRefundCandidate)
						.build(),
				// saveCandidateAssignment(assignCandidatesRequest, assignmentToRefundCandidate.getMoneyAssignedToRefundCandidate()),
				remainingQty);
	}

//	private AssignableInvoiceCandidate saveCandidateAssignment(
//			@NonNull final AssignCandidatesRequest assignCandidatesRequest,
//			@NonNull final Money moneyToAssign,
//			@NonNull final Quantity quantityToAssign)
//	{
//		final AssignableInvoiceCandidate //
//		assignableInvoiceCandidate = assignCandidatesRequest.getAssignableInvoiceCandidate();
//
//		final RefundInvoiceCandidate //
//		refundInvoiceCandidate = assignCandidatesRequest.getRefundInvoiceCandidate();
//
//		final AssignmentToRefundCandidate assignmentToRefundCandidate = //
//				new AssignmentToRefundCandidate(
//						assignableInvoiceCandidate.getId(),
//						refundInvoiceCandidate,
//						assignCandidatesRequest.getRefundConfig().getId(),
//						assignableInvoiceCandidate.getMoney(),
//						moneyToAssign, // assignCandidatesRequest.getMoneyToAssign(),
//						quantityToAssign // assignCandidatesRequest.getQuantityToAssign()
//				);
//
//		assignmentToRefundCandidateRepository.save(assignmentToRefundCandidate);
//
//		return assignableInvoiceCandidate
//				.toBuilder()
//				.assignmentToRefundCandidate(assignmentToRefundCandidate)
//				.build();
//	}

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

		final ImmutableList<RefundConfig> configs = unassignedPairs
				.stream()
				.flatMap(pair -> pair.getRefundInvoiceCandidate().getRefundConfigs().stream())
				.collect(ImmutableList.toImmutableList());

		final RefundMode refundMode = extractSingleElement(
				configs,
				RefundConfig::getRefundMode);

		if (RefundMode.ALL_MAX_SCALE.equals(refundMode))
		{
			// "If refundMode=ALL_MAX_SCALE, then there can be only one refund candidate
			final RefundInvoiceCandidate refundCandidate = CollectionUtils.extractSingleElement(unassignedPairs, UnassignedPairOfCandidates::getRefundInvoiceCandidate);

			final Quantity previouslyAssignedQty = refundCandidate
					.getAssignedQuantity()
					.add(assignableInvoiceCandidate.getQuantity());

			// note that in this refund mode the whole qty for *all* refundConfigs is assigned to this candidate.
			// therefore we can get the "biggest" refund config like this.
			final RefundConfig oldRefundConfig = refundCandidate
					.getRefundContract()
					.getRefundConfig(previouslyAssignedQty.getAsBigDecimal());

			final RefundConfig newRefundConfig = refundCandidate
					.getRefundContract()
					.getRefundConfig(refundCandidate.getAssignedQuantity().getAsBigDecimal());

			// if accumulated: check if the current quantity still matches the respective candidate's current refund-config's minQty;
			if (!oldRefundConfig.getId().equals(newRefundConfig.getId()))
			{
				// if not, then update the candidates' refund-config and money; don't forget I_C_Invoice_Candidate_Assignment.C_Flatrate_RefundConfig_ID
				refundConfigChangeService.resetMoneyAmount(refundCandidate, oldRefundConfig, newRefundConfig);
			}
			return result;
		}

		// refundMode == PER_SCALE
		final RefundContract refundContract = extractSingleElement(
				unassignedPairs,
				pair -> pair.getRefundInvoiceCandidate().getRefundContract());

		final List<RefundInvoiceCandidate> matchingRefundCandidates = refundInvoiceCandidateService.retrieveMatchingRefundCandidates(
				assignableInvoiceCandidate, refundContract)
				.stream()
				.filter(r -> !r.getAssignedQuantity().isZero())
				.collect(ImmutableList.toImmutableList());

		if (matchingRefundCandidates.size() > 1)
		{
			final UnassignResultBuilder resultBuilder = result.toBuilder();

			final Comparator<RefundInvoiceCandidate> // if refundMode == PER_SCALE, then each refundCandidate has just one config
			comparingByMinQty = Comparator.comparing(r -> singleElement(r.getRefundConfigs()).getMinQty());

			final ImmutableList<RefundInvoiceCandidate> sortedByMinQty = matchingRefundCandidates
					.stream()
					.sorted(comparingByMinQty)
					.collect(ImmutableList.toImmutableList());

			final RefundInvoiceCandidate highestRefundInvoiceCandidate = sortedByMinQty
					.get(sortedByMinQty.size() - 1);

			Quantity gap = Quantity.zero(assignableInvoiceCandidate.getQuantity().getUOM());

			boolean higherCandidateHasAssignedQty = highestRefundInvoiceCandidate.getAssignedQuantity().signum() > 0;

			for (int i = sortedByMinQty.size() - 2; i >= 0; i--)
			{
				final RefundInvoiceCandidate refundInvoiceCandidate = sortedByMinQty.get(i);

				// remember, if refundMode == PER_SCALE, then each refundCandidate has just one config
				final RefundConfig refundConfigs = singleElement(refundInvoiceCandidate.getRefundConfigs());

				final Quantity assignableQty = refundInvoiceCandidate.computeAssignableQuantity(refundConfigs);
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
					.withSubtractedAssignedMoneyAndQuantity()
					.getRefundInvoiceCandidate();
			refundInvoiceCandidateRepository.save(withSubtractedMoneyAmount);

			final UnassignedPairOfCandidates unassignedPair = UnassignedPairOfCandidates
					.builder()
					.assignableInvoiceCandidate(withoutRefundInvoiceCandidate)
					.unassignedQuantity(assignmentToRefundCandidate.getQuantityAssigendToRefundCandidate())
					.refundInvoiceCandidate(withSubtractedMoneyAmount)
					.build();
			resultBuilder.unassignedPair(unassignedPair);
		}
		final UnassignResult result = resultBuilder.build();
		return result;
	}

	@lombok.Value
	@lombok.Builder(toBuilder = true)
	public static class UnassignResult
	{
		/**
		 * The assignable candidate after the unassignment.
		 * Note that this candidate has no assignments anymore.
		 */
		@NonNull
		AssignableInvoiceCandidate assignableCandidate;

		/**
		 * Each pair's {@link AssignCandidatesRequest#getAssignableInvoiceCandidate()} is this result's {@link #assignableCandidate}.
		 */
		@Singular
		List<UnassignedPairOfCandidates> unassignedPairs;

		/**
		 * Further candidates whose assignments also changed due to the unassignment.
		 * Note that for {@link RefundMode#ALL_MAX_SCALE}, this list is always empty.
		 * TODO fix comment
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
