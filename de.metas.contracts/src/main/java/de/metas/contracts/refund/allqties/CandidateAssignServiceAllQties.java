package de.metas.contracts.refund.allqties;

import static de.metas.util.collections.CollectionUtils.singleElement;

import java.util.List;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.contracts.refund.AssignCandidatesRequest;
import de.metas.contracts.refund.AssignableInvoiceCandidate;
import de.metas.contracts.refund.AssignmentToRefundCandidate;
import de.metas.contracts.refund.AssignmentToRefundCandidateRepository;
import de.metas.contracts.refund.CandidateAssignmentService.UpdateAssignmentResult;
import de.metas.contracts.refund.RefundConfig;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.contracts.refund.RefundConfigs;
import de.metas.contracts.refund.RefundContract;
import de.metas.contracts.refund.RefundInvoiceCandidate;
import de.metas.contracts.refund.RefundInvoiceCandidateRepository;
import de.metas.contracts.refund.RefundInvoiceCandidateService;
import de.metas.contracts.refund.allqties.refundconfigchange.RefundConfigChangeService;
import de.metas.currency.CurrencyRepository;
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

public class CandidateAssignServiceAllQties
{
	private final RefundConfigChangeService refundConfigChangeService;
	private final RefundInvoiceCandidateService refundInvoiceCandidateService;

	@VisibleForTesting
	@Getter
	private final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository;

	@VisibleForTesting
	@Getter
	private final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;

	@VisibleForTesting
	public static CandidateAssignServiceAllQties createInstanceForUnitTesting()
	{
		final MoneyService moneyService = new MoneyService(new CurrencyRepository());

		final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository = RefundInvoiceCandidateRepository.createInstanceForUnitTesting();

		final RefundInvoiceCandidateService refundInvoiceCandidateService = new RefundInvoiceCandidateService(
				refundInvoiceCandidateRepository,
				moneyService);

		final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository = new AssignmentToRefundCandidateRepository(refundInvoiceCandidateRepository);

		final RefundConfigChangeService refundConfigChangeService = new RefundConfigChangeService(
				assignmentToRefundCandidateRepository,
				moneyService,
				refundInvoiceCandidateService);

		return new CandidateAssignServiceAllQties(
				refundConfigChangeService,
				refundInvoiceCandidateService,
				assignmentToRefundCandidateRepository,
				refundInvoiceCandidateRepository);
	}

	public CandidateAssignServiceAllQties(
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
			@NonNull final RefundInvoiceCandidate refundCandidateToAssignTo,
			@NonNull final RefundContract refundContract)
	{
		Check.errorUnless(RefundMode.APPLY_TO_ALL_QTIES.equals(refundContract.extractRefundMode()),
				"refundMode needs to be {}; refundContract={}", RefundMode.APPLY_TO_ALL_QTIES, refundContract);

		final AssignableInvoiceCandidate assignableCandidateWithoutRefundInvoiceCandidates = assignableCandidate.withoutRefundInvoiceCandidates();

		//
		// first part: only assign to the smallest refund config (with minQty=0).
		final RefundConfig refundConfig = RefundConfigs.smallestMinQty(refundCandidateToAssignTo.getRefundConfigs());
		Check.assume(refundConfig.getMinQty().signum() == 0, "The first refundConfig of refundCandidateToAssignTo needs to have minQty=0; actual minQty={}; refundConfig={}", refundConfig.getMinQty(), refundConfig);

		final AssignCandidatesRequest assignCandidatesRequest = AssignCandidatesRequest
				.builder()
				.assignableInvoiceCandidate(assignableCandidateWithoutRefundInvoiceCandidates)
				.refundInvoiceCandidate(refundCandidateToAssignTo)
				.refundConfig(refundConfig)
				.build();

		final List<AssignmentToRefundCandidate> createdAssignments = assignCandidate(
				assignCandidatesRequest,
				assignableCandidate.getQuantity())
						.getAssignmentsToRefundCandidates();

		// the assignableInvoiceCandidate of our assignCandidatesRequest had no assignments, so the result has exactly one assignment.
		final AssignmentToRefundCandidate createdAssignment = singleElement(createdAssignments);

		final RefundInvoiceCandidate refundCandidateWithAsignedMoneyAndQty = createdAssignment.getRefundInvoiceCandidate();

		//
		// second part: now see if the biggest applicable refund config changed.
		// if it did change, then add or remove assignments for the respective configs that now apply as well or don't apply anymore
		final List<RefundConfig> relevantConfigsAfterAssignment = refundContract.getRefundConfigsToApplyForQuantity(refundCandidateWithAsignedMoneyAndQty.getAssignedQuantity().toBigDecimal());

		final RefundConfig newRefundConfig = RefundConfigs.largestMinQty(relevantConfigsAfterAssignment);

		final boolean configHasChanged = !refundConfig.equals(newRefundConfig);
		if (configHasChanged)
		{
			refundConfigChangeService.createOrDeleteAdditionalAssignments(refundCandidateWithAsignedMoneyAndQty, refundConfig, newRefundConfig);
		}

		final AssignableInvoiceCandidate resultCandidate = assignableCandidate
				.toBuilder()
				.clearAssignmentsToRefundCandidates() // need to reload the assignments
				.assignmentsToRefundCandidates(assignmentToRefundCandidateRepository.getAssignmentsByAssignableCandidateId(assignableCandidate.getId()))
				.build();
		return UpdateAssignmentResult.updateDone(resultCandidate, ImmutableList.of()/* additionalChangedCandidates */);
	}

	/**
	 * @return the assignable candidate with its new additional assignment.
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
