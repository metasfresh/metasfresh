package de.metas.contracts.refund;

import static de.metas.util.collections.CollectionUtils.extractSingleElement;
import static de.metas.util.collections.CollectionUtils.singleElement;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;

import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.contracts.refund.AssignmentToRefundCandidateRepository.DeleteAssignmentsRequest;
import de.metas.contracts.refund.CandidateAssignmentService.UnassignResult.UnassignResultBuilder;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.refund.allqties.CandidateAssignServiceAllQties;
import de.metas.contracts.refund.allqties.refundconfigchange.RefundConfigChangeService;
import de.metas.contracts.refund.exceedingqty.CandidateAssignServiceExceedingQty;
import de.metas.contracts.refund.packaging.RefundPackagingFilter;
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
	private final RefundPackagingFilter refundPackagingFilter;

	public CandidateAssignmentService(
			@NonNull final RefundContractRepository refundContractRepository,
			@NonNull final RefundInvoiceCandidateService refundInvoiceCandidateService,
			@NonNull final AssignableInvoiceCandidateRepository assignableInvoiceCandidateRepository,
			@NonNull final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository,
			@NonNull final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository,
			@NonNull final RefundConfigChangeService refundConfigChangeService,
			@NonNull final RefundPackagingFilter refundPackagingFilter)
	{
		this.refundContractRepository = refundContractRepository;
		this.refundInvoiceCandidateService = refundInvoiceCandidateService;
		this.assignableInvoiceCandidateRepository = assignableInvoiceCandidateRepository;
		this.assignmentToRefundCandidateRepository = assignmentToRefundCandidateRepository;
		this.refundInvoiceCandidateRepository = refundInvoiceCandidateRepository;
		this.refundConfigChangeService = refundConfigChangeService;
		this.refundPackagingFilter = refundPackagingFilter;
	}

	/**
	 * Assigns the given candidate to <b>every</b> refund contract that matches it; each contract gets the candidate's full amount.
	 * A contract whose conditions are restricted to some packaging options only matches a candidate whose order line is delivered in one of them.
	 * The assignments to contracts that don't match anymore are removed.
	 */
	public UpdateAssignmentResult updateAssignment(
			@NonNull final AssignableInvoiceCandidate assignableCandidate)
	{
		return updateAssignment(assignableCandidate, retrieveMatchingContracts(assignableCandidate));
	}

	private UpdateAssignmentResult updateAssignment(
			@NonNull final AssignableInvoiceCandidate assignableCandidate,
			@NonNull final List<RefundContract> refundContracts)
	{
		if (refundContracts.isEmpty())
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

		unassignFromContractsThatDontMatchAnymore(assignableCandidate, refundContracts);

		UpdateAssignmentResult result = null;
		final ImmutableList.Builder<AssignableInvoiceCandidate> additionalChangedCandidates = ImmutableList.builder();
		for (final RefundContract refundContract : refundContracts)
		{
			final UpdateAssignmentResult contractResult = updateAssignment(assignableCandidate, refundContract);
			additionalChangedCandidates.addAll(contractResult.getAdditionalChangedCandidates());
			result = result == null || contractResult.isUpdateWasDone() || !result.isUpdateWasDone()
					? contractResult
					: result;
		}

		// the result of the last contract has only that contract's assignments; return the candidate with all of them
		final AssignableInvoiceCandidate candidateWithAllAssignments = assignableInvoiceCandidateRepository.getById(assignableCandidate.getId());
		return new UpdateAssignmentResult(
				result.isUpdateWasDone(),
				candidateWithAllAssignments,
				additionalChangedCandidates.build());
	}

	/**
	 * The discount line of a contract-created compensation group matches no refund contract: the refund base is the goods value before that discount.
	 */
	private ImmutableList<RefundContract> retrieveMatchingContracts(@NonNull final AssignableInvoiceCandidate assignableCandidate)
	{
		if (assignableCandidate.isContractCompensationLine())
		{
			return ImmutableList.of();
		}
		return refundContractRepository.getByQuery(RefundContractQuery.of(assignableCandidate))
				.stream()
				// the customer deducts that bonus at payment; it is booked at the payment allocation, not invoiced
				.filter(contract -> !contract.isDeductedAtPayment())
				.filter(contract -> refundPackagingFilter.isIncluded(contract.getConditionsId(), assignableCandidate.getHuPIItemProductId(), assignableCandidate.getBpartnerLocationId().getBpartnerId()))
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Assigns the given candidate if it matches a refund contract that it is not assigned to yet, e.g. because the contract was completed afterwards.
	 * The assignment only goes to the current open period of the contract or a later one: past periods get no refund retroactively.
	 * Does nothing if the candidate is assigned to all contracts that match it.
	 * The discount line of a contract-created compensation group is no refund base: its assignments are removed.
	 */
	public void assignToNewlyMatchingContracts(@NonNull final AssignableInvoiceCandidate assignableCandidate)
	{
		if (assignableCandidate.isContractCompensationLine())
		{
			// it became the discount line of a contract-created group (e.g. regrouped): no refund base, so a former assignment goes
			if (assignableCandidate.isAssigned())
			{
				unassignCandidate(assignableCandidate);
			}
			return;
		}

		final ImmutableSet<FlatrateTermId> assignedContractIds = assignableCandidate.getAssignmentsToRefundCandidates().stream()
				.map(assignment -> assignment.getRefundInvoiceCandidate().getRefundContract().getId())
				.collect(ImmutableSet.toImmutableSet());

		final LocalDate today = SystemTime.asLocalDate();
		final ImmutableList<RefundContract> contractsToAssignTo = retrieveMatchingContracts(assignableCandidate).stream()
				// the contracts that the candidate already has stay as they are; the new ones count only if their period for the candidate is not over
				.filter(contract -> assignedContractIds.contains(contract.getId())
						|| isInCurrentOrLaterPeriod(contract, assignableCandidate.getInvoiceableFrom(), today))
				.collect(ImmutableList.toImmutableList());
		if (contractsToAssignTo.stream().allMatch(contract -> assignedContractIds.contains(contract.getId())))
		{
			return; // nothing new
		}

		updateAssignment(assignableCandidate, contractsToAssignTo);
	}

	private static boolean isInCurrentOrLaterPeriod(@NonNull final RefundContract contract, @NonNull final LocalDate invoiceableFrom, @NonNull final LocalDate today)
	{
		if (invoiceableFrom.isBefore(contract.getStartDate()) || invoiceableFrom.isAfter(contract.getEndDate()))
		{
			return false;
		}
		final LocalDate endOfCurrentPeriod = contract.computeNextInvoiceDate(today).getDateToInvoice();
		return !contract.computeNextInvoiceDate(invoiceableFrom).getDateToInvoice().isBefore(endOfCurrentPeriod);
	}

	private void unassignFromContractsThatDontMatchAnymore(
			@NonNull final AssignableInvoiceCandidate assignableCandidate,
			@NonNull final List<RefundContract> matchingContracts)
	{
		final ImmutableSet<FlatrateTermId> matchingContractIds = matchingContracts.stream()
				.map(RefundContract::getId)
				.collect(ImmutableSet.toImmutableSet());

		final AssignableInvoiceCandidate reloadedCandidate = assignableInvoiceCandidateRepository.getById(assignableCandidate.getId());

		final ImmutableSet<FlatrateTermId> staleContractIds = reloadedCandidate.getAssignmentsToRefundCandidates().stream()
				.map(assignment -> assignment.getRefundInvoiceCandidate().getRefundContract().getId())
				.filter(contractId -> !matchingContractIds.contains(contractId))
				.collect(ImmutableSet.toImmutableSet());

		for (final FlatrateTermId staleContractId : staleContractIds)
		{
			unassignSingleCandidate(onlyAssignmentsToContract(reloadedCandidate, staleContractId), staleContractId);
		}
	}

	private static AssignableInvoiceCandidate onlyAssignmentsToContract(
			@NonNull final AssignableInvoiceCandidate candidate,
			@NonNull final FlatrateTermId contractId)
	{
		return candidate.toBuilder()
				.clearAssignmentsToRefundCandidates()
				.assignmentsToRefundCandidates(candidate.getAssignmentsToRefundCandidates().stream()
						.filter(assignment -> contractId.equals(assignment.getRefundInvoiceCandidate().getRefundContract().getId()))
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private UpdateAssignmentResult updateAssignment(
			@NonNull final AssignableInvoiceCandidate assignableCandidate,
			@NonNull final RefundContract refundContract)
	{
		// retrieve or create refund candidates to which assignableCandidate shall be assigned
		final List<RefundInvoiceCandidate> matchingRefundCandidates = //
				refundInvoiceCandidateService.retrieveOrCreateMatchingRefundCandidates(assignableCandidate, refundContract);

		if (refundContract.getAmountPerUnitConfigInOtherCurrency(assignableCandidate.getMoney().getCurrencyId()).isPresent())
		{
			// the amount per unit can't be added to the refund (and is not converted): the refund candidate is in error (see FlatrateTermRefund_Handler) and gets nothing assigned;
			// once the config's currency is corrected, the candidate is flagged again (RefundInvoiceCandidateInvalidator) and assigned
			return UpdateAssignmentResult.noUpdateDone(assignableCandidate);
		}

		// guards
		matchingRefundCandidates.forEach(c -> Check.assumeNotEmpty(c.getRefundConfigs(),
				"Every refundInvoiceCandidate returned by retrieveOrCreateMatchingRefundCandidates() needs to have at least one config; candidate that hasn't={}", c));

		final ImmutableMap<InvoiceCandidateId, RefundInvoiceCandidate> //
		refundCandidateId2matchingRefundCandidate = Maps.uniqueIndex(matchingRefundCandidates, RefundInvoiceCandidate::getId);

		final List<RefundInvoiceCandidate> refundCandidatesToAssign;

		// reload from backend to find out if the assignableCandidate is already assigned or not
		// only the assignments to the given contract count, the other contracts are handled on their own
		final AssignableInvoiceCandidate reloadedAssignableCandidate = onlyAssignmentsToContract(
				assignableInvoiceCandidateRepository.getById(assignableCandidate.getId()),
				refundContract.getId());
		if (reloadedAssignableCandidate.isAssigned())
		{
			// the refund candidate matching the given assignableCandidate might have changed;
			// unassign (which also subtracts the assigned money),
			// then collect the now unassigned refund candidates for reassignment.
			final UnassignResult unassignResult = unassignSingleCandidate(reloadedAssignableCandidate, refundContract.getId());
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
		// each refund contract is handled on its own: it has its own refund candidates, configs and quantities
		final ImmutableList<FlatrateTermId> contractIds = assignableInvoiceCandidate.getAssignmentsToRefundCandidates().stream()
				.map(assignment -> assignment.getRefundInvoiceCandidate().getRefundContract().getId())
				.distinct()
				.collect(ImmutableList.toImmutableList());

		if (contractIds.isEmpty())
		{
			return unassignCandidate(assignableInvoiceCandidate, null); // fails, because there is nothing to unassign
		}

		final UnassignResultBuilder resultBuilder = UnassignResult.builder()
				.assignableCandidate(assignableInvoiceCandidate.withoutRefundInvoiceCandidates());
		for (final FlatrateTermId contractId : contractIds)
		{
			final UnassignResult contractResult = unassignCandidate(onlyAssignmentsToContract(assignableInvoiceCandidate, contractId), contractId);
			resultBuilder.unassignedPairs(contractResult.getUnassignedPairs());
			resultBuilder.additionalChangedCandidates(contractResult.getAdditionalChangedCandidates());
		}
		return resultBuilder.build();
	}

	/**
	 * @param onlyContractId the refund contract whose assignments are removed; {@code null} to remove all of them.
	 */
	private UnassignResult unassignCandidate(
			@NonNull final AssignableInvoiceCandidate assignableInvoiceCandidate,
			@Nullable final FlatrateTermId onlyContractId)
	{
		final UnassignResult result = unassignSingleCandidate(assignableInvoiceCandidate, onlyContractId);

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
		return unassignSingleCandidate(assignableInvoiceCandidate, null);
	}

	/**
	 * @param onlyContractId if not {@code null}, then only the assignments to this contract are removed. The given candidate shall then only have assignments to this contract.
	 */
	private UnassignResult unassignSingleCandidate(
			@NonNull final AssignableInvoiceCandidate assignableInvoiceCandidate,
			@Nullable final FlatrateTermId onlyContractId)
	{
		final List<AssignmentToRefundCandidate> assignmentsToRefundCandidates = Check
				.assumeNotEmpty(
						assignableInvoiceCandidate.getAssignmentsToRefundCandidates(),
						"The given assignableInvoiceCandidate to unassign needs to have refundInvoiceCandidates",
						assignableInvoiceCandidate);

		deleteAssignmentIfExists(assignableInvoiceCandidate, onlyContractId);

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
			@NonNull final AssignableInvoiceCandidate invoiceCandidate,
			@Nullable final FlatrateTermId onlyContractId)
	{
		final DeleteAssignmentsRequest request = DeleteAssignmentsRequest
				.builder()
				.removeForAssignedCandidateId(invoiceCandidate.getId())
				.flatrateTermId(onlyContractId)
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
