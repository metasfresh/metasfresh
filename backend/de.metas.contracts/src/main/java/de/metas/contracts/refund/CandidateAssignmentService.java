package de.metas.contracts.refund;

import static de.metas.util.collections.CollectionUtils.extractSingleElement;
import static de.metas.util.collections.CollectionUtils.singleElement;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.contracts.refund.AssignmentToRefundCandidateRepository.DeleteAssignmentsRequest;
import de.metas.contracts.refund.CandidateAssignmentService.UnassignResult.UnassignResultBuilder;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.contracts.refund.allqties.CandidateAssignServiceAllQties;
import de.metas.contracts.refund.allqties.refundconfigchange.RefundConfigChangeService;
import de.metas.contracts.refund.exceedingqty.CandidateAssignServiceExceedingQty;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Singular;

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
public class CandidateAssignmentService
{
	private final RefundContractRepository refundContractRepository;
	private final RefundInvoiceCandidateService refundInvoiceCandidateService;
	private final AssignableInvoiceCandidateRepository assignableInvoiceCandidateRepository;
	private final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository;
	private final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;
	private final RefundConfigChangeService refundConfigChangeService;

	public CandidateAssignmentService(
			@NonNull final RefundContractRepository refundContractRepository,
			@NonNull final RefundInvoiceCandidateService refundInvoiceCandidateService,
			@NonNull final AssignableInvoiceCandidateRepository assignableInvoiceCandidateRepository,
			@NonNull final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository,
			@NonNull final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository,
			@NonNull final RefundConfigChangeService refundConfigChangeService)
	{
		this.refundContractRepository = refundContractRepository;
		this.refundInvoiceCandidateService = refundInvoiceCandidateService;
		this.assignableInvoiceCandidateRepository = assignableInvoiceCandidateRepository;
		this.assignmentToRefundCandidateRepository = assignmentToRefundCandidateRepository;
		this.refundInvoiceCandidateRepository = refundInvoiceCandidateRepository;
		this.refundConfigChangeService = refundConfigChangeService;
	}

	public UpdateAssignmentResult updateAssignment(
			@NonNull final AssignableInvoiceCandidate assignableCandidate)
	{
		final RefundContractQuery refundContractQuery = RefundContractQuery.of(assignableCandidate);
		final RefundContract refundContract = refundContractRepository
				.getByQuery(refundContractQuery)
				.orElse(null);

		if (refundContract == null)
		{
			if (!assignableCandidate.isAssigned())
			{
				return UpdateAssignmentResult.noUpdateDone(assignableCandidate); // nothing to do
			}

			// unassign (which also subtracts the assigned money)
			final UnassignResult unassignResult = unassignCandidate(assignableCandidate);
			return UpdateAssignmentResult.updateDone(
					unassignResult.getAssignableCandidate(),
					unassignResult.getAdditionalChangedCandidates());
		}

		// retrieve or create refund candidates to which assignableCandidate shall be assigned
		final List<RefundInvoiceCandidate> matchingRefundCandidates = //
				refundInvoiceCandidateService.retrieveOrCreateMatchingRefundCandidates(assignableCandidate, refundContract);

		// guards
		matchingRefundCandidates.forEach(c -> Check.assumeNotEmpty(c.getRefundConfigs(),
				"Every refundInvoiceCandidate returned by retrieveOrCreateMatchingRefundCandidates() needs to have at least one config; candidate that hasn't={}", c));

		final ImmutableMap<InvoiceCandidateId, RefundInvoiceCandidate> //
		refundCandidateId2matchingRefundCandidate = Maps.uniqueIndex(matchingRefundCandidates, RefundInvoiceCandidate::getId);

		final List<RefundInvoiceCandidate> refundCandidatesToAssign;

		// reload from backend to find out if the assignableCandidate is already assigned or not
		final AssignableInvoiceCandidate reloadedAssignableCandidate = assignableInvoiceCandidateRepository
				.getById(assignableCandidate.getId());
		if (reloadedAssignableCandidate.isAssigned())
		{
			// the refund candidate matching the given assignableCandidate might have changed;
			// unassign (which also subtracts the assigned money),
			// then collect the now unassigned refund candidates for reassignment.
			final UnassignResult unassignResult = unassignSingleCandidate(reloadedAssignableCandidate);
			refundCandidatesToAssign = unassignResult
					.getUnassignedPairs()
					.stream()
					.map(UnassignedPairOfCandidates::getRefundInvoiceCandidate)
					.filter(refundCand -> refundCandidateId2matchingRefundCandidate.containsKey(refundCand.getId()))
					.collect(ImmutableList.toImmutableList());
		}
		else
		{
			refundCandidatesToAssign = matchingRefundCandidates;
		}

		final RefundMode refundMode = refundContract.extractRefundMode();
		switch (refundMode)
		{
			case APPLY_TO_ALL_QTIES:

				Check.assume(matchingRefundCandidates.size() == 1,
						"If refundMode={}, then there needs to be exactly one refund candidate; refundCandidatesToAssign={}", refundMode, matchingRefundCandidates);

				final CandidateAssignServiceAllQties candidateAssignServiceAllQties = new CandidateAssignServiceAllQties(
						refundConfigChangeService,
						refundInvoiceCandidateService,
						assignmentToRefundCandidateRepository,
						refundInvoiceCandidateRepository);

				return candidateAssignServiceAllQties
						.updateAssignment(
								reloadedAssignableCandidate,
								singleElement(refundCandidatesToAssign),
								refundContract);

			case APPLY_TO_EXCEEDING_QTY:

				matchingRefundCandidates.forEach(c -> Check.assume(c.getRefundConfigs().size() == 1,
						"If refundMode={}, then every refundInvoiceCandidate returned by retrieveOrCreateMatchingRefundCandidates() needs to have exactly one config", refundMode, c));

				final CandidateAssignServiceExceedingQty candidateAssignServiceExceedingQty = new CandidateAssignServiceExceedingQty(
						refundInvoiceCandidateRepository,
						refundInvoiceCandidateService,
						assignmentToRefundCandidateRepository);

				return candidateAssignServiceExceedingQty.updateAssignment(
						reloadedAssignableCandidate,
						refundCandidatesToAssign,
						refundContract);

			default:
				throw new AdempiereException("Unexpected refundMode=" + refundMode)
						.appendParametersToMessage()
						.setParameter("assignableCandidate", assignableCandidate)
						.setParameter("refundContract", refundContract);
		}
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

		final RefundMode refundMode = RefundConfigs.extractRefundMode(configs);

		if (RefundMode.APPLY_TO_ALL_QTIES.equals(refundMode))
		{
			createOrDeleteAdditionalAssignments(unassignedPairs, assignableInvoiceCandidate);
			return result;
		}

		// refundMode == APPLY_TO_EXCEEDING_QTY
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

			final Comparator<RefundInvoiceCandidate> // if refundMode == APPLY_TO_EXCEEDING_QTY, then each refundCandidate has just one config
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

				// remember, if refundMode == APPLY_TO_EXCEEDING_QTY, then each refundCandidate has just one config
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

	private void createOrDeleteAdditionalAssignments(
			@NonNull final List<UnassignedPairOfCandidates> unassignedPairs,
			@NonNull final AssignableInvoiceCandidate assignableInvoiceCandidate)
	{
		// "If refundMode=ALL_MAX_SCALE, then there can be only one refund candidate
		final RefundInvoiceCandidate refundCandidate = extractSingleElement(unassignedPairs, UnassignedPairOfCandidates::getRefundInvoiceCandidate);

		// additional quantity that we had before assignableInvoiceCandidate was changed
		final Quantity quantityDelta = assignableInvoiceCandidate
				.getQuantityOld()
				.subtract(assignableInvoiceCandidate.getQuantity());

		final Quantity previouslyAssignedQty = refundCandidate
				.getAssignedQuantity()
				.add(quantityDelta);

		// note that in this refund mode the whole qty for *all* refundConfigs is assigned to this candidate.
		// therefore we can get the "biggest" refund config like this.
		final RefundConfig oldRefundConfig = refundCandidate
				.getRefundContract()
				.getRefundConfig(previouslyAssignedQty.toBigDecimal());

		final RefundConfig newRefundConfig = refundCandidate
				.getRefundContract()
				.getRefundConfig(refundCandidate.getAssignedQuantity().toBigDecimal());

		// check if the current quantity still matches the respective candidate's current refund-config's minQty;
		if (!oldRefundConfig.getId().equals(newRefundConfig.getId()))
		{
			refundConfigChangeService.createOrDeleteAdditionalAssignments(refundCandidate, oldRefundConfig, newRefundConfig);
		}
	}

	/**
	 * Just unassign the given candidate for its refund candidates and subtract the formerly assigned quantity and money from those candidates.
	 * Do not do anything about changed refund config scales.
	 */
	@VisibleForTesting
	UnassignResult unassignSingleCandidate(
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

		final Map<InvoiceCandidateId, RefundInvoiceCandidate> id2RefundInvoiceCandidate = new HashMap<>();
		final Map<InvoiceCandidateId, Quantity> id2UnassignedQuantity = new HashMap<>();
		for (final AssignmentToRefundCandidate assignmentToRefundCandidate : assignmentsToRefundCandidates)
		{
			final RefundInvoiceCandidate refundCandidate = assignmentToRefundCandidate.getRefundInvoiceCandidate();
			final InvoiceCandidateId refundCandidateId = refundCandidate.getId();
			if (id2RefundInvoiceCandidate.containsKey(refundCandidateId))
			{
				final RefundInvoiceCandidate refundCandidateFromMap = id2RefundInvoiceCandidate.get(refundCandidateId);
				id2RefundInvoiceCandidate.put(refundCandidateId, refundCandidateFromMap.subtractAssignment(assignmentToRefundCandidate));

				final Quantity unassignedQuantityFromMap = id2UnassignedQuantity.get(refundCandidateId);
				final Quantity quantityToUnassign = extractEffectiveQuantityToUnassign(assignmentToRefundCandidate);
				id2UnassignedQuantity.put(refundCandidateId, unassignedQuantityFromMap.add(quantityToUnassign));
			}
			else
			{
				id2RefundInvoiceCandidate.put(refundCandidateId, refundCandidate.subtractAssignment(assignmentToRefundCandidate));

				final Quantity quantityToUnassign = extractEffectiveQuantityToUnassign(assignmentToRefundCandidate);
				id2UnassignedQuantity.put(refundCandidateId, quantityToUnassign);
			}
		}

		for (final InvoiceCandidateId refundCandidateId : id2RefundInvoiceCandidate.keySet())
		{
			final RefundInvoiceCandidate refundInvoiceCandidate = id2RefundInvoiceCandidate.get(refundCandidateId);
			refundInvoiceCandidateRepository.save(refundInvoiceCandidate);

			final UnassignedPairOfCandidates unassignedPair = UnassignedPairOfCandidates
					.builder()
					.assignableInvoiceCandidate(withoutRefundInvoiceCandidate)
					.unassignedQuantity(id2UnassignedQuantity.get(refundCandidateId))
					.refundInvoiceCandidate(refundInvoiceCandidate)
					.build();
			resultBuilder.unassignedPair(unassignedPair);
		}
		final UnassignResult result = resultBuilder.build();
		return result;
	}

	private void deleteAssignmentIfExists(
			@NonNull final AssignableInvoiceCandidate invoiceCandidate)
	{
		final DeleteAssignmentsRequest request = DeleteAssignmentsRequest
				.builder()
				.removeForAssignedCandidateId(invoiceCandidate.getId())
				.build();
		assignmentToRefundCandidateRepository.deleteAssignments(request);
	}

	private Quantity extractEffectiveQuantityToUnassign(
			@NonNull final AssignmentToRefundCandidate assignmentToRefundCandidate)
	{
		final Quantity assignedQty;
		if (assignmentToRefundCandidate.isUseAssignedQtyInSum())
		{
			assignedQty = assignmentToRefundCandidate.getQuantityAssigendToRefundCandidate();
		}
		else
		{
			assignedQty = assignmentToRefundCandidate.getQuantityAssigendToRefundCandidate().toZero();
		}
		return assignedQty;
	}

	public void removeAllAssignments(@NonNull final RefundInvoiceCandidate invoiceCandidate)
	{
		final DeleteAssignmentsRequest request = DeleteAssignmentsRequest
				.builder()
				.removeForAssignedCandidateId(invoiceCandidate.getId())
				.removeForRefundCandidateId(invoiceCandidate.getId())
				.onlyActive(false) // remove *all*, as the method name sais
				.build();
		assignmentToRefundCandidateRepository.deleteAssignments(request);
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

	@lombok.Value
	public static class UpdateAssignmentResult
	{
		public static final UpdateAssignmentResult updateDone(
				@NonNull final AssignableInvoiceCandidate candidate,
				@NonNull final List<AssignableInvoiceCandidate> additionalChangedCandidates)
		{
			return new UpdateAssignmentResult(true, candidate, additionalChangedCandidates);
		}

		public static final UpdateAssignmentResult noUpdateDone(@NonNull final AssignableInvoiceCandidate candidate)
		{
			return new UpdateAssignmentResult(false, candidate, ImmutableList.of());
		}

		/** {@code false} means that no update was required since the data as loaded from the DB was already up to date. */
		boolean updateWasDone;

		/** The result, as loaded from the DB. */
		AssignableInvoiceCandidate assignableInvoiceCandidate;

		List<AssignableInvoiceCandidate> additionalChangedCandidates;
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
		 */
		@Singular("additionalChangedCandidate")
		List<AssignableInvoiceCandidate> additionalChangedCandidates;
	}
}
