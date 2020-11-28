package de.metas.contracts.refund;

import static de.metas.util.collections.CollectionUtils.singleElement;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.RefundConfig.RefundBase;
import de.metas.contracts.refund.RefundConfig.RefundMode;
import de.metas.contracts.refund.RefundInvoiceCandidateRepository.RefundInvoiceCandidateQuery;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.lang.Percent;
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
	private final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository;
	private final MoneyService moneyService;
	private final RefundInvoiceCandidateFactory refundInvoiceCandidateFactory;
	private final AssignmentAggregateService assignmentAggregateService;

	public RefundInvoiceCandidateService(
			@NonNull final RefundInvoiceCandidateRepository refundInvoiceCandidateRepository,
			@NonNull final MoneyService moneyService)
	{
		this.refundInvoiceCandidateFactory = refundInvoiceCandidateRepository.getRefundInvoiceCandidateFactory();
		this.refundInvoiceCandidateRepository = refundInvoiceCandidateRepository;
		this.moneyService = moneyService;
		this.assignmentAggregateService = refundInvoiceCandidateFactory.getAssignmentAggregateService();
	}

	/**
	 * Queries for existing {@link RefundInvoiceCandidate}
	 * that might not not yet be associated with the given {@code assignableInvoiceCandidate}.
	 * If there are none yet, it creates them as needed.
	 *
	 * Notes:
	 * <li>in case of {@link RefundMode#APPLY_TO_EXCEEDING_QTY}, there can be multiple refund contracts for an assignable candidate. However, each of them has just one refund config.
	 * <li>in case of {@link RefundMode#APPLY_TO_ALL_QTIES}, there is just one refund candidate per assignable candidate, but it can have more than one assignment.
	 */
	public List<RefundInvoiceCandidate> retrieveOrCreateMatchingRefundCandidates(
			@NonNull final AssignableInvoiceCandidate assignableCandidate,
			@NonNull final RefundContract refundContract)
	{
		final List<RefundInvoiceCandidate> existingCandidates = retrieveMatchingRefundCandidates(assignableCandidate, refundContract);

		final BigDecimal qtyToAssign = assignableCandidate.getQuantity().toBigDecimal();

		final Quantity currentAssignedQuantity = existingCandidates.stream()
				.map(RefundInvoiceCandidate::getAssignedQuantity)
				.reduce(Quantity.zero(assignableCandidate.getQuantity().getUOM()), Quantity::add);

		final BigDecimal assignedTargetQuantity = currentAssignedQuantity.toBigDecimal().add(qtyToAssign);

		// relevantRefundConfigs contains at least one config with minQty=0
		final List<RefundConfig> relevantRefundConfigs = refundContract.getRefundConfigsToApplyForQuantity(assignedTargetQuantity);
		Check.assumeNotNull(relevantRefundConfigs, "relevantRefundConfigs may not by empty; assignedTargetQuantity={}; refundContract={}", assignedTargetQuantity, refundContract);

		final RefundMode refundMode = refundContract.extractRefundMode();
		if (refundMode.equals(RefundMode.APPLY_TO_ALL_QTIES))
		{
			if (!existingCandidates.isEmpty())
			{
				// with refundMode=ALL_MAX_SCALE there should be just one;
				final RefundInvoiceCandidate result = singleElement(existingCandidates)
						.toBuilder()
						.clearRefundConfigs()
						// ...but it might not yet have all relevant configs, so add them to the final result
						.refundConfigs(relevantRefundConfigs)
						.build();
				return ImmutableList.of(result);
			}

			// this list contains just one, because we passed a list of configs with refundMode=ALL_MAX_SCALE
			final List<RefundInvoiceCandidate> newRefundCandidates = refundInvoiceCandidateFactory
					.createRefundCandidates(
							assignableCandidate,
							refundContract,
							relevantRefundConfigs);
			return newRefundCandidates;
		}

		final ImmutableMap<RefundConfig, RefundInvoiceCandidate> //
		refundConfig2existingCandidate = Maps.uniqueIndex(
				existingCandidates,
				c -> singleElement(c.getRefundConfigs()));

		final TreeSet<RefundInvoiceCandidate> result = new TreeSet<>(Comparator.comparing(c -> singleElement(c.getRefundConfigs()).getMinQty()));

		final List<RefundConfig> refundConfigsThatNeedCandidates = new ArrayList<>();
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

		if (!refundConfigsThatNeedCandidates.isEmpty())
		{
			// this list contains one candidate per config, because we passed a list of configs with refundMode=PER_INDIVIDUAL_SCALE
			final List<RefundInvoiceCandidate> newRefundCandidates = refundInvoiceCandidateFactory.createRefundCandidates(
					assignableCandidate,
					refundContract,
					ImmutableList.copyOf(refundConfigsThatNeedCandidates));
			result.addAll(newRefundCandidates);
		}

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

		final List<RefundInvoiceCandidate> existingCandidates = refundInvoiceCandidateRepository.getRefundInvoiceCandidates(refundCandidateQuery);
		return existingCandidates;
	}

	public AssignmentToRefundCandidate addAssignableMoney(
			@NonNull final RefundInvoiceCandidate refundCandidate,
			@NonNull final RefundConfig refundConfig,
			@NonNull final AssignableInvoiceCandidate candidateToAssign)
	{

		Check.assume(refundCandidate.getRefundConfigs().contains(refundConfig),
				"The given refundConfig needs to be one of the given refundCandidate's configs; refundConfig={}; refundCandidate={}",
				refundConfig, refundCandidate);

		if (RefundMode.APPLY_TO_EXCEEDING_QTY.equals(refundConfig.getRefundMode()))
		{
			final boolean quantityWithinCurrentScale = isQuantityWithinCurrentScale(refundCandidate, refundConfig, candidateToAssign.getQuantity());
			Check.errorUnless(
					quantityWithinCurrentScale,
					"The given candidateToAssign has quantity={};"
							+ " the given candidateToUpdate has assignedQuantity={};"
							+ " together they exceed the quantity for candidateToUpdate's refund config;"
							+ " \ncandidateToAssign={}"
							+ " \ncandidateToUpdate={}",
					candidateToAssign.getQuantity(), refundCandidate.getAssignedQuantity(), candidateToAssign, refundCandidate);
		}

		final Quantity assignedQtyAugent = candidateToAssign.getQuantity();

		final Money moneyAugend;
		if (RefundBase.AMOUNT_PER_UNIT.equals(refundConfig.getRefundBase()))
		{
			final Money amount = refundConfig.getAmount();
			moneyAugend = amount.multiply(assignedQtyAugent.toBigDecimal());
		}
		else
		{
			final Percent percent = refundConfig.getPercent();
			moneyAugend = moneyService.percentage(percent, candidateToAssign.getMoney());
		}

		final RefundInvoiceCandidate updatedRefundCandidate = refundCandidate.toBuilder()
				.money(refundCandidate.getMoney().add(moneyAugend))
				.assignedQuantity(refundCandidate.getAssignedQuantity().add(assignedQtyAugent))
				.build();

		return new AssignmentToRefundCandidate(
				refundConfig.getId(),
				candidateToAssign.getId(),
				updatedRefundCandidate,
				candidateToAssign.getMoney(),
				moneyAugend,
				assignedQtyAugent,
				refundConfig.isIncludeAssignmentsWithThisConfigInSum());
	}

	private boolean isQuantityWithinCurrentScale(
			@NonNull final RefundInvoiceCandidate candidateToUpdate,
			@NonNull final RefundConfig refundConfig,
			@NonNull final Quantity quantity)
	{
		final Quantity assignableQty = candidateToUpdate.computeAssignableQuantity(refundConfig);

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

	public RefundInvoiceCandidate updateMoneyFromAssignments(@NonNull final RefundInvoiceCandidate refundCandidate)
	{
		final InvoiceCandidateId id = refundCandidate.getId();
		final BigDecimal newMoneyAmount = assignmentAggregateService.retrieveMoneyAmount(id);
		final Money newMoney = Money.of(
				newMoneyAmount,
				refundCandidate.getMoney().getCurrencyId());

		final RefundInvoiceCandidate candidateWithUpdatedMoney = refundCandidate
				.toBuilder()
				.money(newMoney)
				.build();

		return refundInvoiceCandidateRepository.save(candidateWithUpdatedMoney);
	}
}
