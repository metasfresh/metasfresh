package de.metas.contracts.refund;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.contracts.refund.InvoiceCandidateRepository.RefundInvoiceCandidateQuery;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.lang.Percent;
import de.metas.money.Money;
import de.metas.money.MoneyService;
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

@Service
public class RefundInvoiceCandidateService
{
	private final RefundInvoiceCandidateRepository invoiceCandidateRepository;
	private final MoneyService moneyService;
	private final RefundInvoiceCandidateFactory refundInvoiceCandidateFactory;

	public RefundInvoiceCandidateService(
			@NonNull final RefundInvoiceCandidateRepository invoiceCandidateRepository,
			@NonNull final RefundInvoiceCandidateFactory refundInvoiceCandidateFactory,
			@NonNull final MoneyService moneyService)
	{
		this.refundInvoiceCandidateFactory = refundInvoiceCandidateFactory;
		this.invoiceCandidateRepository = invoiceCandidateRepository;
		this.moneyService = moneyService;
	}

	/**
	 * Queries for existing {@link RefundInvoiceCandidate}
	 * that might not not yet be associated with the given {@code assignableInvoiceCandidate}.
	 * If there are none yet, it creates them as needed.
	 *
	 * Note: in case of {@link RefundMode#PER_INDIVIDUAL_SCALE}, there can be multiple refund contracts for an assignable candidate.
	 */
	public List<RefundInvoiceCandidate> retrieveOrCreateMatchingCandidates(
			@NonNull final AssignableInvoiceCandidate assignableCandidate,
			@NonNull final RefundContract refundContract)
	{
		final RefundInvoiceCandidateQuery refundCandidateQuery = RefundInvoiceCandidateQuery
				.builder()
				.refundContract(refundContract)
				.invoicableFrom(assignableCandidate.getInvoiceableFrom())
				.build();

		final List<RefundInvoiceCandidate> existingCandidates = invoiceCandidateRepository.getRefundInvoiceCandidates(refundCandidateQuery);
		final ImmutableMap<RefundConfig, RefundInvoiceCandidate> refundConfig2existingCandidate = Maps.uniqueIndex(existingCandidates, RefundInvoiceCandidate::getRefundConfig);

		// the new refund candidate has no assigned quantity (besides, in the nearest future, the qty of 'invoiceCandidate')
		final List<RefundConfig> relevantRefundConfigs = refundContract.getRelevantRefundConfigs(assignableCandidate.getQuantity().getAsBigDecimal());

		final TreeSet<RefundInvoiceCandidate> result = new TreeSet<RefundInvoiceCandidate>(Comparator.comparing(c -> c.getRefundConfig().getMinQty()));

		final ImmutableList.Builder<RefundConfig> refundConfigsThatNeedCandidates = ImmutableList.builder();
		for (final RefundConfig relevantRefundConfig : relevantRefundConfigs)
		{
			final RefundInvoiceCandidate existingRelevantRefundCandidate = refundConfig2existingCandidate.get(relevantRefundConfig);
			if (existingRelevantRefundCandidate != null)
			{
				result.add(existingRelevantRefundCandidate);
			}
			else
			{
				refundConfigsThatNeedCandidates.add(relevantRefundConfig);
			}
		}

		final List<RefundInvoiceCandidate> newRefundCandidates = refundInvoiceCandidateFactory.createRefundInvoiceCandidates(
				assignableCandidate,
				refundContract,
				refundConfigsThatNeedCandidates.build());
		result.addAll(newRefundCandidates);

		return ImmutableList.copyOf(result);
	}

	private RefundInvoiceCandidate findMatchingCandidate(
			@NonNull final List<RefundInvoiceCandidate> matchingCandidates,
			@NonNull final Quantity quantity)
	{
		if (matchingCandidates.isEmpty())
		{
			return null;
		}

		final RefundInvoiceCandidate existingRefundCandidate = matchingCandidates
				.stream()
				.max(Comparator.comparing(RefundInvoiceCandidate::getAssignedQuantity))
				.get();

		final Quantity qtyAssignedToRefundCandidate = existingRefundCandidate.getAssignedQuantity();
		final Quantity qtyToMatchAgainst = qtyAssignedToRefundCandidate.add(quantity);
		final RefundConfig refundConfig = existingRefundCandidate.getRefundContract().getRefundConfig(qtyToMatchAgainst.getAsBigDecimal());

		if (RefundMode.ALL_MAX_SCALE.equals(refundConfig.getRefundMode()))
		{
			return existingRefundCandidate;
		}

		if (RefundMode.PER_INDIVIDUAL_SCALE.equals(refundConfig.getRefundMode()))
		{
			final boolean existingCandidateIsInSameScale = qtyAssignedToRefundCandidate
					.getAsBigDecimal()
					.compareTo(refundConfig.getMinQty()) <= 0;
			if (existingCandidateIsInSameScale)
			{
				return existingRefundCandidate;
			}
		}
		return null; // nothing found
	}

	public AssignmentToRefundCandidate addAssignableMoney(
			@NonNull final RefundInvoiceCandidate candidateToUpdate,
			@NonNull final Money money)
	{
		final Percent percent = candidateToUpdate
				.getRefundConfig()
				.getPercent();

		final Money augend = moneyService.percentage(percent, money);

		final RefundInvoiceCandidate updatedRefundCandidate = candidateToUpdate.toBuilder()
				.money(candidateToUpdate.getMoney().add(augend))
				.build();

		return new AssignmentToRefundCandidate(updatedRefundCandidate, augend);
	}

}
