package de.metas.contracts.refund.allqties.refundconfigchange;

import java.util.List;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.IQuery;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;

import de.metas.contracts.model.I_C_Invoice_Candidate_Assignment;
import de.metas.contracts.refund.AssignmentToRefundCandidate;
import de.metas.contracts.refund.AssignmentToRefundCandidateRepository;
import de.metas.contracts.refund.AssignmentToRefundCandidateRepository.DeleteAssignmentsRequest;
import de.metas.contracts.refund.AssignmentToRefundCandidateRepository.DeleteAssignmentsRequest.DeleteAssignmentsRequestBuilder;
import de.metas.contracts.refund.RefundConfig;
import de.metas.contracts.refund.RefundConfig.RefundBase;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.contracts.refund.RefundConfigs;
import de.metas.contracts.refund.RefundContract;
import de.metas.contracts.refund.RefundInvoiceCandidate;
import de.metas.contracts.refund.RefundInvoiceCandidateService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import de.metas.util.Check;
import de.metas.util.Services;
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
public class RefundConfigChangeService
{
	private final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository;
	private final MoneyService moneyService;
	private final RefundInvoiceCandidateService refundInvoiceCandidateService;

	public RefundConfigChangeService(
			@NonNull final AssignmentToRefundCandidateRepository assignmentToRefundCandidateRepository,
			@NonNull final MoneyService moneyService,
			@NonNull final RefundInvoiceCandidateService refundInvoiceCandidateService)
	{
		this.assignmentToRefundCandidateRepository = assignmentToRefundCandidateRepository;
		this.moneyService = moneyService;
		this.refundInvoiceCandidateService = refundInvoiceCandidateService;
	}

	public RefundInvoiceCandidate createOrDeleteAdditionalAssignments(
			@NonNull final RefundInvoiceCandidate refundInvoiceCandidate,
			@NonNull final RefundConfig oldRefundConfig,
			@NonNull final RefundConfig newRefundConfig)
	{
		if (oldRefundConfig.equals(newRefundConfig))
		{
			return refundInvoiceCandidate;
		}

		final RefundContract refundContract = refundInvoiceCandidate.getRefundContract();
		final List<RefundConfig> refundConfigs = refundContract.getRefundConfigs();

		Check.errorUnless(RefundMode.APPLY_TO_ALL_QTIES.equals(newRefundConfig.getRefundMode()),
				"Parameter 'newRefundConfig' needs to have refundMode={}", RefundMode.APPLY_TO_ALL_QTIES);

		Check.errorUnless(refundConfigs.contains(newRefundConfig),
				"Parameter 'newRefundConfig' needs to be one of the given refundInvoiceCandidate's contract's configs; newRefundConfig={}; refundInvoiceCandidate={}",
				newRefundConfig, refundInvoiceCandidate);

		// add new AssignmentToRefundCandidates, or remove existing ones
		// which reference newRefundConfig and track the additional money that comes with the new refund config.

		final boolean isHigherRefund = newRefundConfig.getMinQty().compareTo(oldRefundConfig.getMinQty()) > 0;

		final List<RefundConfig> refundConfigRange = getRefundConfigRange(refundContract, oldRefundConfig, newRefundConfig);
		Check.assumeNotEmpty(refundConfigRange,
				"There needs to be at least one refundConfig in the range defined by oldRefundConfig={} and newRefundConfig={}; refundInvoiceCandidate={}",
				oldRefundConfig, newRefundConfig, refundInvoiceCandidate);

		final RefundConfigChangeHandler handler = createForConfig(oldRefundConfig);
		if (isHigherRefund)
		{
			for (final RefundConfig currentRangeConfig : refundConfigRange)
			{
				handler.changeCurrentRefundConfig(currentRangeConfig);

				final RefundConfig formerRefundConfig = handler.getFormerRefundConfig();
				final Stream<AssignmentToRefundCandidate> assignmentsToExtend = streamAssignmentsToExtend(
						refundInvoiceCandidate,
						formerRefundConfig,
						currentRangeConfig);

				// note: the nice thing here is that we don't need to alter the existing candidate in any way!
				assignmentsToExtend
						.map(handler::createNewAssignment) // the new assignment only assigns *additional* stuff
						.forEach(assignmentToRefundCandidateRepository::save);
			}
		}
		else
		{
			final DeleteAssignmentsRequestBuilder builder = DeleteAssignmentsRequest
					.builder()
					.removeForRefundCandidateId(refundInvoiceCandidate.getId());
			for (final RefundConfig currentRangeConfig : refundConfigRange)
			{
				builder.refundConfigId(currentRangeConfig.getId());
			}

			assignmentToRefundCandidateRepository.deleteAssignments(builder.build());
		}
		final RefundInvoiceCandidate endResult = refundInvoiceCandidateService.updateMoneyFromAssignments(refundInvoiceCandidate);
		return endResult;
	}

	@VisibleForTesting
	List<RefundConfig> getRefundConfigRange(
			@NonNull final RefundContract contract,
			@NonNull final RefundConfig currentConfig,
			@NonNull final RefundConfig targetConfig)
	{
		Check.errorIf(currentConfig.getMinQty().equals(targetConfig.getMinQty()),
				"Params currentConfig and currentConfig={}; targetConfig={}",
				currentConfig, targetConfig);

		final boolean forward = currentConfig.getMinQty().compareTo(targetConfig.getMinQty()) < 0;

		// make sure we know which order of refund configs we operate on
		final ImmutableList<RefundConfig> configsByMinQty = RefundConfigs.sortByMinQtyAsc(contract.getRefundConfigs());

		final ImmutableList.Builder<RefundConfig> result = ImmutableList.builder();
		if (forward)
		{
			boolean collectItem = false;
			for (int i = 0; i < configsByMinQty.size(); i++)
			{
				final RefundConfig item = configsByMinQty.get(i);
				if (collectItem)
				{
					result.add(item);
				}
				if (item.getMinQty().equals(targetConfig.getMinQty()))
				{
					return result.build(); // we collected the last item (targetConfig) and are done
				}
				if (item.getMinQty().equals(currentConfig.getMinQty()))
				{
					collectItem = true; // don't collect currentConfig itself, but the next item(s)
				}
			}
		}

		// backward
		boolean collectItem = false;
		for (int i = configsByMinQty.size() - 1; i >= 0; i--)
		{
			final RefundConfig item = configsByMinQty.get(i);
			if (item.getMinQty().equals(currentConfig.getMinQty()))
			{
				collectItem = true; // do collect currentConfig
			}
			if (item.getMinQty().equals(targetConfig.getMinQty()))
			{
				return result.build(); // we collected the last item (one "above" targetConfig) and are done
			}
			if (collectItem)
			{
				result.add(item);
			}
		}
		return result.build();
	}

	private RefundConfigChangeHandler createForConfig(@NonNull final RefundConfig refundConfig)
	{
		final boolean isPercent = RefundBase.PERCENTAGE.equals(refundConfig.getRefundBase());
		if (isPercent)
		{
			return PercentRefundConfigChangeHandler.newInstance(moneyService, refundConfig);
		}
		else
		{
			return AmountRefundConfigChangeHandler.newInstance(refundConfig);
		}

	}

	private Stream<AssignmentToRefundCandidate> streamAssignmentsToExtend(
			@NonNull final RefundInvoiceCandidate refundInvoiceCandidate,
			@NonNull final RefundConfig refundConfig,
			@NonNull final RefundConfig additionalRefundConfig)
	{
		Check.assumeNotNull(refundInvoiceCandidate.getId(),
				"The given refundInvoiceCandidate needs to have a not-null Id; assignableInvoiceCandidate={}",
				refundInvoiceCandidate);

		final RefundInvoiceCandidate candidateWithAdditionalConfig = refundInvoiceCandidate
				.toBuilder()
				.refundConfig(additionalRefundConfig)
				.build();

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		// these are to be excluded in the actual query
		final IQuery<I_C_Invoice_Candidate> assignableCandidatesWithAdditionalConfig = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(
						I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Term_ID,
						refundInvoiceCandidate.getId().getRepoId())
				.addEqualsFilter(
						I_C_Invoice_Candidate_Assignment.COLUMN_C_Flatrate_RefundConfig_ID,
						additionalRefundConfig.getId().getRepoId())
				.andCollect(I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Assigned_ID,
						I_C_Invoice_Candidate.class)
				.addOnlyActiveRecordsFilter()
				.create();

		return queryBL
				.createQueryBuilder(I_C_Invoice_Candidate_Assignment.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(
						I_C_Invoice_Candidate_Assignment.COLUMN_C_Invoice_Candidate_Term_ID,
						refundInvoiceCandidate.getId().getRepoId())
				.addEqualsFilter(
						I_C_Invoice_Candidate_Assignment.COLUMN_C_Flatrate_RefundConfig_ID,
						refundConfig.getId().getRepoId())
				.addNotInSubQueryFilter(
						I_C_Invoice_Candidate_Assignment.COLUMNNAME_C_Invoice_Candidate_Assigned_ID,
						I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID,
						assignableCandidatesWithAdditionalConfig)
				.create()
				.iterateAndStream()
				.map(assignmentToRefundCandidateRepository::ofRecordOrNull)
				.map(a -> a.withRefundInvoiceCandidate(candidateWithAdditionalConfig));

	}
}
