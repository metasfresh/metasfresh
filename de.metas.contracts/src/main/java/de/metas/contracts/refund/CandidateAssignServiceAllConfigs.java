package de.metas.contracts.refund;

import static org.adempiere.util.collections.CollectionUtils.singleElement;

import java.util.List;

import org.adempiere.util.Check;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.contracts.refund.CandidateAssignmentService.UpdateAssignmentResult;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.contracts.refund.refundConfigChange.RefundConfigChangeService;
import de.metas.quantity.Quantity;
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

public class CandidateAssignServiceAllConfigs
{
	private final RefundConfigChangeService refundConfigChangeService;
	private final RefundInvoiceCandidateService refundInvoiceCandidateService;
	private final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository;
	private final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;

	public CandidateAssignServiceAllConfigs(
			@NonNull final RefundConfigChangeService refundConfigChangeService,
			@NonNull final RefundInvoiceCandidateService refundInvoiceCandidateService,
			@NonNull final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository,
			@NonNull final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository)
	{
		this.refundConfigChangeService = refundConfigChangeService;
		this.refundInvoiceCandidateService = refundInvoiceCandidateService;
		this.assignmentToRefundCandidateRepository = assignmentToRefundCandidateRepository;
		this.refundInvoiceCandidateRepository = refundInvoiceCandidateRepository;
	}

	public UpdateAssignmentResult updateAssignment(
			@NonNull final AssignableInvoiceCandidate assignableCandidate,
			@NonNull final RefundInvoiceCandidate refundCandidateToAssign,
			@NonNull final RefundContract refundContract)
	{
		Check.errorUnless(RefundMode.ALL_MAX_SCALE.equals(refundContract.extractRefundMode()),
				"refundMode needs to be {}; refundContract={}", RefundMode.ALL_MAX_SCALE, refundContract);

		final AssignableInvoiceCandidate assignableCandidateWithoutRefundInvoiceCandidates = assignableCandidate.withoutRefundInvoiceCandidates();

		//
		// first part: only assign to the smallest refund config (with minQty=0).
		final RefundConfig refundConfig = RefundConfigs.smallestMinQty(refundCandidateToAssign.getRefundConfigs());
		Check.assume(refundConfig.getMinQty().signum() == 0, "The first refundConfig from sortedConfigs needs to have minQty=0");

		final AssignCandidatesRequest assignCandidatesRequest = AssignCandidatesRequest
				.builder()
				.assignableInvoiceCandidate(assignableCandidateWithoutRefundInvoiceCandidates)
				.refundInvoiceCandidate(refundCandidateToAssign)
				.refundConfig(refundConfig)
				.build();

		final List<AssignmentToRefundCandidate> createdAssignments = assignCandidate(
				assignCandidatesRequest,
				assignableCandidate.getQuantity())
						.getAssignmentsToRefundCandidates();

		// the assignableInvoiceCandidate of our assignCandidatesRequest had no assignments, so the result has exactly one assignment.
		final AssignmentToRefundCandidate createdAssignment = singleElement(createdAssignments);

		final RefundInvoiceCandidate refundCandidateAfterAssignment = createdAssignment.getRefundInvoiceCandidate();

		//
		// second part: now see if the biggest applicable refund config changed. if yes, add or removed assignments for the respective configs that now apply as well or don't apply anymore
		final List<RefundConfig> relevantConfigsBeforeAssignment = refundContract.getRefundConfigsToApplyForQuantity(refundCandidateToAssign.getAssignedQuantity().getAsBigDecimal());
		final List<RefundConfig> relevantConfigsAfterAssignment = refundContract.getRefundConfigsToApplyForQuantity(refundCandidateAfterAssignment.getAssignedQuantity().getAsBigDecimal());

		final RefundConfig oldRefundConfig = RefundConfigs.largestMinQty(relevantConfigsBeforeAssignment);
		final RefundConfig newRefundConfig = RefundConfigs.largestMinQty(relevantConfigsAfterAssignment);

		final boolean configHasChanged = !oldRefundConfig.equals(newRefundConfig);
		if (configHasChanged)
		{
			refundConfigChangeService.createOrDeleteAdditionalAssignments(refundCandidateAfterAssignment, oldRefundConfig, newRefundConfig);
		}

		final AssignableInvoiceCandidate resultCandidate = assignableCandidate
				.toBuilder()
				.clearAssignmentsToRefundCandidates() // need to reload the assignments
				.assignmentsToRefundCandidates(assignmentToRefundCandidateRepository.getAssignmentsToRefundCandidate(assignableCandidate))
				.build();
		return UpdateAssignmentResult.updateDone(resultCandidate, ImmutableList.of());
	}

	/**
	 * @return the assignable candidate with its new additional assignment, and the quantity that is still left to be assigned.
	 */
	@VisibleForTesting
	AssignableInvoiceCandidate assignCandidate(
			@NonNull final AssignCandidatesRequest assignCandidatesRequest,
			@NonNull final Quantity quantityToAssign)
	{
		final AssignableInvoiceCandidate assignableCandidate = assignCandidatesRequest.getAssignableInvoiceCandidate();
		final RefundInvoiceCandidate refundCandidate = assignCandidatesRequest.getRefundInvoiceCandidate();

		final RefundConfig refundConfig = assignCandidatesRequest.getRefundConfig();
		final AssignmentToRefundCandidate assignmentToRefundCandidate = refundInvoiceCandidateService
				.addAssignableMoney(
						refundCandidate,
						refundConfig,
						assignableCandidate);

		final RefundInvoiceCandidate //
		updatedRefundCandidate = assignmentToRefundCandidate.getRefundInvoiceCandidate();
		refundInvoiceCandidateRepository.save(updatedRefundCandidate);

		assignmentToRefundCandidateRepository.save(assignmentToRefundCandidate);

		return assignableCandidate
				.toBuilder()
				.assignmentToRefundCandidate(assignmentToRefundCandidate)
				.build();
	}
}
