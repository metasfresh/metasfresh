package de.metas.contracts.refund;

import java.util.List;

import org.springframework.stereotype.Service;

import de.metas.contracts.refund.InvoiceCandidateRepository.RefundInvoiceCandidateQuery;
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
	private final InvoiceCandidateRepository invoiceCandidateRepository;
	private final MoneyService moneyService;

	public RefundInvoiceCandidateService(
			@NonNull final InvoiceCandidateRepository invoiceCandidateRepository,
			@NonNull final MoneyService moneyService)
	{
		this.invoiceCandidateRepository = invoiceCandidateRepository;
		this.moneyService = moneyService;
	}

	public RefundInvoiceCandidate retrieveOrCreateMatchingCandidate(
			@NonNull final AssignableInvoiceCandidate assignableInvoiceCandidate,
			@NonNull final RefundContract refundContract)
	{
		final RefundInvoiceCandidateQuery refundCandidateQuery = RefundInvoiceCandidateQuery
				.builder()
				.refundContract(refundContract)
				.invoicableFrom(assignableInvoiceCandidate.getInvoiceableFrom())
				.build();

		final List<RefundInvoiceCandidate> matchingCandidates = invoiceCandidateRepository.getRefundInvoiceCandidates(refundCandidateQuery);

		assignableInvoiceCandidate.getQuantity();

		final RefundInvoiceCandidate matchingCandidate = findMatchingCandidate(matchingCandidates, assignableInvoiceCandidate.getQuantity());
		if (matchingCandidate != null)
		{
			return matchingCandidate;
		}

		return invoiceCandidateRepository.createRefundInvoiceCandidate(assignableInvoiceCandidate, refundContract);
	}

	private RefundInvoiceCandidate findMatchingCandidate(
			@NonNull final List<RefundInvoiceCandidate> matchingCandidates,
			@NonNull final Quantity quantity)
	{

		for (final RefundInvoiceCandidate candidate : matchingCandidates)
		{
			final Quantity qtyToMatchAgainst = candidate.getAssignedQuantity().add(quantity);
		}

		return null; // nothing found
	}

	public AssignmentToRefundCandidate addPercentageOfMoney(
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
