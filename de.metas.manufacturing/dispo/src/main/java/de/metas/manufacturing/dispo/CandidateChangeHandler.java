package de.metas.manufacturing.dispo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import lombok.NonNull;

/*
 * #%L
 * metasfresh-manufacturing-dispo
 * %%
 * Copyright (C) 2017 metas GmbH
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
public class CandidateChangeHandler
{
	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private CandidateFactory candidateFactory;

	public List<Candidate> onDemandCandidateNewOrChange(final Candidate demandCandidate)
	{
		// have some collector to collect all records we create and change
		final List<Candidate> newAndChangedCandidates = new ArrayList<>();

		// create a new stock candidate or retrieve an existing one and set its qty to the negated value of the demand candidate's qty.
		// the stock candidate shall be a *child* of the demandCandidate
		final Candidate stockCandidate = null;

		// final BigDecimal delta = null; // computed from the stock candidate's qty before and after the change.

		newAndChangedCandidates.add(stockCandidate);

		{
			// see if we can react "locally" to the new or changed stock candidate
			// take a look at the product-planning infos and determine if we can make a supply candidate
			final boolean canMakeSupplyCandidates = false;
			if (canMakeSupplyCandidates)
			{
				// get the children of 'stockCandidate' (if any).
				// create a candidate with type "supply" according to the product-planning infos if there is no candidate yet.
				// If there is already a candidate, then verify if it is still a fit (product) for 'stockCandidate'. if no, then recreate it according to the planning. if yes, then update it's qty according to 'delta'
				final Candidate supplyCandidate = null;
				newAndChangedCandidates.add(supplyCandidate);

				// the demand candidates for the supplyCandidate have the the same locator, but different products (PP_Order) or the same product but a different locator (DD_Order)
				// if they already exist, then update them according to the supply candidate's qty change
				final List<Candidate> demandCandidates = null;
				newAndChangedCandidates.addAll(demandCandidates);

				// the negative qty of stockCandidate could be balanced. We have e.g.
				// demand-candidate with 23 (i.e. -23)
				// balanced stock-candidate with 0 (i.e. this candidate is a neutral)
				// supply-candidate with 23 (i.e. + 23)

				// notify the system about our new demand candidate(s)
				// this notification shall lead to the onDemandCandidate() method being invoked once again, but probably asynchronously
			}
			else
			{
				// according to the product planning setup we can't create a supply candidate, so our stock candidate remains unbalanced. We have e.g.
				// demand-candidate with 23 (i.e. -23)
				// unbalanced stock-candidate with -23 (i.e. this candidate is *not* a neutral)

				// notify the system about the stock candidate
				// this notification shall lead to all "later" stock candidates with the same product and locator being notified
			}
		}

		return newAndChangedCandidates;
	}

	/**
	 * Call this one if the system was notified about a new or changed or deleted supply candidate.
	 * <p>
	 * Create a new stock candidate or retrieve an existing one and set its qty to the value of the supply candidate's qty.<br>
	 * The new stock candidate shall be the <i>parent</i> of the supplyCandidate.<br>
	 * When creating a new candidate, then compute its qty by getting the qty from that stockCandidate that has the same product and locator and is "before" it and add the supply candidate's qty
	 *
	 * @param supplyCandidate
	 * @return
	 */
	public void onSupplyCandidateNewOrChange(final Candidate supplyCandidate)
	{
		final Optional<Candidate> existingSupplyCandidate = candidateRepository.retrieve(supplyCandidate);

		final Candidate parentStockCandidate;
		if (existingSupplyCandidate.isPresent())
		{
			// if our supply candidate existed, then it also has a parent, because we never create them without a parent
			parentStockCandidate = candidateRepository
					.retrieve(
							existingSupplyCandidate.get().getParentId());
		}
		else
		{
			parentStockCandidate = candidateFactory
					.createStockCandidate(
							supplyCandidate.mkSegment())
					.withReferencedRecord(
							supplyCandidate.getReferencedRecord());
		}

		final BigDecimal newStockQty = parentStockCandidate.getQuantity().add(supplyCandidate.getQuantity());
		final Candidate parentStockCandidateWithId = onStockCandidateNewOrChanged(parentStockCandidate.withQuantity(newStockQty));

		candidateRepository.addOrReplace(supplyCandidate.withParentId(parentStockCandidateWithId.getId()));

		// e.g.
		// supply-candidate with 23 (i.e. +23)
		// parent-stockCandidate used to have -44 (because of "earlier" candidate)
		// now has -21

		// notify the system about the stock candidate
		// this notification shall lead to all "later" stock candidates with the same product and locator being notified
	}

	public void onCandidateDelete(@NonNull final TableRecordReference recordReference)
	{
		candidateRepository.deleteForReference(recordReference);
	}

	/**
	 * This method adds or replaces the given candidate.
	 * It then updates the quantities of all "later" stock candidates that have the same product etc.<br>
	 * according to the delta between the candidate's former value (or zero if it was only just added) and it's new value.
	 *
	 * @param candidate
	 * @param the given candidate or a new one. At any rate, the returned candidate will have an id!
	 */
	public Candidate onStockCandidateNewOrChanged(@NonNull final Candidate candidate)
	{

		final Optional<Candidate> previousCandidate = candidateRepository.retrieve(candidate);
		final Candidate savedCandidate = candidateRepository.addOrReplace(candidate);

		// if there was not pre-existing candidate, then subtract zero.
		final BigDecimal delta = candidate.getQuantity()
				.subtract(
						previousCandidate
								.orElse(
										candidate.withQuantity(BigDecimal.ZERO))
								.getQuantity());

		final CandidatesSegment segment = CandidatesSegment.builder()
				.productId(candidate.getProductId())
				.warehouseId(candidate.getWarehouseId())
				.locatorId(candidate.getLocatorId())
				.projectedDate(candidate.getDate())
				.build();

		applyDeltaToLaterStockCandidates(segment, delta);
		return savedCandidate;
	}

	/**
	 * Selects all stock candidates which have the same product and locator but a later timestamp than the one from the given {@code segment}.
	 * Iterate them and add the given {@code delta} to their quantity.
	 * <p>
	 * That's it for now :-). Don't alter those stock candidates' children or parents.
	 *
	 * @param segment
	 * @param delta
	 */
	@VisibleForTesting
	/* package */ void applyDeltaToLaterStockCandidates(final CandidatesSegment segment, final BigDecimal delta)
	{
		final List<Candidate> candidatesToUpdate = candidateRepository.retrieveStockAfter(segment);
		for (final Candidate candidate : candidatesToUpdate)
		{
			final BigDecimal newQty = candidate.getQuantity().add(delta);
			candidateRepository.addOrReplace(candidate.withQuantity(newQty));
		}
	}
}
