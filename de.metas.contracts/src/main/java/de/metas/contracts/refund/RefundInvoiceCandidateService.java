package de.metas.contracts.refund;

import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.adempiere.util.Check;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.InvoiceCandidateRepository.RefundInvoiceCandidateQuery;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
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
	public List<RefundInvoiceCandidate> retrieveOrCreateMatchingRefundCandidates(
			@NonNull final AssignableInvoiceCandidate assignableCandidate,
			@NonNull final RefundContract refundContract)
	{
		final List<RefundInvoiceCandidate> existingCandidates = retrieveMatchingRefundCandidates(assignableCandidate, refundContract);

		final BigDecimal qtyToAssign = assignableCandidate.getQuantity().getAsBigDecimal();

		final RefundMode refundMode = refundContract.extractRefundMode();
		if (refundMode.equals(RefundMode.ALL_MAX_SCALE))
		{
			if (!existingCandidates.isEmpty())
			{
				return ImmutableList.of(existingCandidates.get(0)); // there should be just one
			}
			final RefundConfig refundConfig = refundContract.getRefundConfig(qtyToAssign);
			final List<RefundInvoiceCandidate> newRefundCandidates = refundInvoiceCandidateFactory
					.createRefundInvoiceCandidates(
							assignableCandidate,
							refundContract,
							ImmutableList.of(refundConfig));
			return newRefundCandidates; // this is just one, because we passed just one refundConfig as parameter
		}

		final ImmutableMap<RefundConfig, RefundInvoiceCandidate> //
		refundConfig2existingCandidate = Maps.uniqueIndex(existingCandidates, RefundInvoiceCandidate::getRefundConfig);

		final Quantity assignedQuantity = existingCandidates.stream()
				.map(RefundInvoiceCandidate::getAssignedQuantity)
				.reduce(Quantity.zero(assignableCandidate.getQuantity().getUOM()), Quantity::add);

		final List<RefundConfig> relevantRefundConfigs = refundContract.getRefundConfigsToApplyForQuantity(assignedQuantity.getAsBigDecimal().add(qtyToAssign));

		final TreeSet<RefundInvoiceCandidate> result = new TreeSet<>(Comparator.comparing(c -> c.getRefundConfig().getMinQty()));

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

	public List<RefundInvoiceCandidate> retrieveMatchingRefundCandidates(
			@NonNull final AssignableInvoiceCandidate assignableCandidate,
			@NonNull final RefundContract refundContract)
	{
		final RefundInvoiceCandidateQuery refundCandidateQuery = RefundInvoiceCandidateQuery
				.builder()
				.refundContract(refundContract)
				.invoicableFrom(assignableCandidate.getInvoiceableFrom())
				.build();

		final List<RefundInvoiceCandidate> existingCandidates = invoiceCandidateRepository.getRefundInvoiceCandidates(refundCandidateQuery);
		return existingCandidates;
	}

	public AssignmentToRefundCandidate addAssignableMoney(
			@NonNull final RefundInvoiceCandidate candidateToUpdate,
			@NonNull final AssignableInvoiceCandidate candidateToAssign)
	{
		final RefundConfig refundConfig = candidateToUpdate	.getRefundConfig();

		final RefundMode refundMode = refundConfig.getRefundMode();
		final boolean quantityWithinCurrentScale = isQuantityWithinCurrentScale(candidateToUpdate, candidateToAssign.getQuantity());

		if (!quantityWithinCurrentScale)
		{
			Check.errorIf(
					RefundMode.PER_INDIVIDUAL_SCALE.equals(refundMode),
					"The given candidateToAssign has quantity={};"
							+ " the given candidateToUpdate has assignedQuantity={};"
							+ " together they exceed the quantity for candidateToUpdate's refund config;"
							+ " \ncandidateToAssign={}"
							+ " \ncandidateToUpdate={}",
					candidateToAssign.getQuantity(), candidateToUpdate.getAssignedQuantity(), candidateToAssign, candidateToUpdate);
		}
		final Percent percent = refundConfig.getPercent();

		final Money moneyAugend = moneyService.percentage(percent, candidateToAssign.getMoney());
		final Quantity assignedQtyAugent = candidateToAssign.getQuantity();

		final RefundInvoiceCandidate updatedRefundCandidate = candidateToUpdate.toBuilder()
				.money(candidateToUpdate.getMoney().add(moneyAugend))
				.assignedQuantity(candidateToUpdate.getAssignedQuantity().add(assignedQtyAugent))
				.build();

		return new AssignmentToRefundCandidate(
				candidateToAssign.getId(),
				updatedRefundCandidate,
				refundConfig.getId(),
				moneyAugend,
				assignedQtyAugent);
	}

	private boolean isQuantityWithinCurrentScale(
			@NonNull final RefundInvoiceCandidate candidateToUpdate,
			@NonNull final Quantity quantity)
	{
		final Quantity assignableQty = candidateToUpdate.computeAssignableQuantity();

		final boolean withinCurrentScale = assignableQty.compareTo(quantity) >= 0;
		return withinCurrentScale;
	}

	public boolean isRefundInvoiceCandidateRecord(@NonNull final I_C_Invoice_Candidate record)
	{
		if (record.getAD_Table_ID() != getTableId(I_C_Flatrate_Term.class))
		{
			return false;
		}

		final I_C_Flatrate_Term term = loadOutOfTrx(record.getRecord_ID(), I_C_Flatrate_Term.class);
		final boolean recordIsARefundCandidate = X_C_Flatrate_Term.TYPE_CONDITIONS_Refund.equals(term.getType_Conditions());

		return recordIsARefundCandidate;
	}
}
