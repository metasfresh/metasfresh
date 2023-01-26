package de.metas.contracts.refund.exceedingqty;

import static de.metas.util.collections.CollectionUtils.singleElement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import de.metas.common.util.pair.IPair;
import de.metas.common.util.pair.ImmutablePair;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.contracts.refund.AssignCandidatesRequest;
import de.metas.contracts.refund.AssignCandidatesRequest.AssignCandidatesRequestBuilder;
import de.metas.contracts.refund.AssignableInvoiceCandidate;
import de.metas.contracts.refund.AssignableInvoiceCandidate.SplitResult;
import de.metas.contracts.refund.AssignmentToRefundCandidate;
import de.metas.contracts.refund.AssignmentToRefundCandidateRepository;
import de.metas.contracts.refund.CandidateAssignmentService.UpdateAssignmentResult;
import de.metas.contracts.refund.RefundConfig;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.currency.CurrencyRepository;
import de.metas.contracts.refund.RefundContract;
import de.metas.contracts.refund.RefundInvoiceCandidate;
import de.metas.contracts.refund.RefundInvoiceCandidateRepository;
import de.metas.contracts.refund.RefundInvoiceCandidateService;
import de.metas.money.MoneyService;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
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

public class CandidateAssignServiceExceedingQty
{
	@VisibleForTesting
	@Getter
	private final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;

	private final RefundInvoiceCandidateService refundInvoiceCandidateService;

	@VisibleForTesting
	@Getter
	private final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository;

	@VisibleForTesting
	public static CandidateAssignServiceExceedingQty createInstanceForUnitTesting()
	{
		final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository = RefundInvoiceCandidateRepository.createInstanceForUnitTesting();

		final RefundInvoiceCandidateService refundInvoiceCandidateService = new RefundInvoiceCandidateService(
				refundInvoiceCandidateRepository,
				new MoneyService(new CurrencyRepository()));

		final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository = new AssignmentToRefundCandidateRepository(refundInvoiceCandidateRepository);

		return new CandidateAssignServiceExceedingQty(
				refundInvoiceCandidateRepository,
				refundInvoiceCandidateService,
				assignmentToRefundCandidateRepository);
	}

	public CandidateAssignServiceExceedingQty(
			@NonNull final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository,
			@NonNull final RefundInvoiceCandidateService refundInvoiceCandidateService,
			@NonNull final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository)
	{
		this.refundInvoiceCandidateRepository = refundInvoiceCandidateRepository;
		this.refundInvoiceCandidateService = refundInvoiceCandidateService;
		this.assignmentToRefundCandidateRepository = assignmentToRefundCandidateRepository;
	}

	public UpdateAssignmentResult updateAssignment(
			@NonNull final AssignableInvoiceCandidate assignableCandidate,
			@NonNull final List<RefundInvoiceCandidate> refundCandidatesToAssignTo,
			@NonNull final RefundContract refundContract)
	{
		Check.errorUnless(RefundMode.APPLY_TO_EXCEEDING_QTY.equals(refundContract.extractRefundMode()),
				"refundMode needs to be {}; refundContract={}", RefundMode.APPLY_TO_EXCEEDING_QTY, refundContract);

		final AssignableInvoiceCandidate assignableCandidateWithoutRefundInvoiceCandidates = assignableCandidate.withoutRefundInvoiceCandidates();

		IPair<AssignableInvoiceCandidate, Quantity> //
		assignedCandidateWithRemainingQty = ImmutablePair.of(
				assignableCandidateWithoutRefundInvoiceCandidates,
				assignableCandidate.getQuantity());

		final List<AssignmentToRefundCandidate> assignments = new ArrayList<>();

		for (final RefundInvoiceCandidate refundCandidateToAssignTo : orderCandidatesByConfigMinQty(refundCandidatesToAssignTo))
		{
			final RefundConfig refundConfig = singleElement(refundCandidateToAssignTo.getRefundConfigs());

			final AssignCandidatesRequestBuilder assignCandidatesRequest = AssignCandidatesRequest
					.builder()
					.assignableInvoiceCandidate(assignableCandidateWithoutRefundInvoiceCandidates)
					.refundInvoiceCandidate(refundCandidateToAssignTo)
					.refundConfig(refundConfig);

			assignedCandidateWithRemainingQty = assignCandidates(
					assignCandidatesRequest.build(),
					assignedCandidateWithRemainingQty.getRight());

			final List<AssignmentToRefundCandidate> createdAssignments = assignedCandidateWithRemainingQty.getLeft().getAssignmentsToRefundCandidates();

			// the assignableInvoiceCandidate of our assignCandidatesRequest had no assignments, so the result has exactly one assignment.
			final AssignmentToRefundCandidate createdAssignment = singleElement(createdAssignments);
			assignments.add(createdAssignment);
		}

		final AssignableInvoiceCandidate resultCandidate = assignableCandidate
				.toBuilder()
				.clearAssignmentsToRefundCandidates()
				.assignmentsToRefundCandidates(assignments)
				.build();
		return UpdateAssignmentResult.updateDone(resultCandidate, ImmutableList.of()/* additionalChangedCandidates */);
	}

	private ImmutableList<RefundInvoiceCandidate> orderCandidatesByConfigMinQty(@NonNull final List<RefundInvoiceCandidate> refundCandidatesToAssign)
	{
		return refundCandidatesToAssign
				.stream()
				.sorted(Comparator.comparing(c -> singleElement(c.getRefundConfigs()).getMinQty()))
				.collect(ImmutableList.toImmutableList());
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
			final SplitResult splitResult = assignableCandidate.splitQuantity(quantityToAssignEffective.toBigDecimal());
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

		refundInvoiceCandidateRepository.save(assignmentToRefundCandidate.getRefundInvoiceCandidate());

		assignmentToRefundCandidateRepository.save(assignmentToRefundCandidate);

		return ImmutablePair.of(
				assignableCandidate
						.toBuilder()
						.assignmentToRefundCandidate(assignmentToRefundCandidate)
						.build(),
				remainingQty);
	}
}
