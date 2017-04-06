package de.metas.manufacturing.dispo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.metas.quantity.Quantity;

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
	private static final CandidateChangeHandler INSTANCE = new CandidateChangeHandler();

	private CandidateChangeHandler()
	{
	}

	public static CandidateChangeHandler get()
	{
		return INSTANCE;
	}

	@Autowired
	private CandidateRepository candidateRepository;

	public List<Candidate> onDemandCandidate(final Candidate demandCandidate)
	{
		// have some collector to collect all records we create and change
		final List<Candidate> newAndChangedCandidates = new ArrayList<>();

		// create a new stock candidate or retrieve an existing one and set its qty to the negated value of the demand candidate's qty.
		// the stock candidate shall be a *child* of the demandCandidate
		final Candidate stockCandidate = null;

		final BigDecimal delta = null; // computed from the stock candidate's qty before and after the change.

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
	 * Call this one if the system was notified about a new or changed or deleted supply candidate
	 *
	 * @param supplyCandidte
	 * @return
	 */
	public List<Candidate> onSupplyCandidate(final Candidate supplyCandidte)
	{
		// have some collector to collect all records we create and change
		final List<Candidate> newAndChangedCandidates = new ArrayList<>();

		// create a new stock candidate or retrieve an existing one and set its qty to the negated value of the demand candidate's qty.
		// the stock candidate shall be a *parent* of the supplyCandidate
		// when creating a new candidate, then compute its qty by getting the qty from that stockCandidate that has the same product and locator and is "before" it and add the supply candidate's qty
		final Candidate stockCandidate = null;
		newAndChangedCandidates.add(stockCandidate);

		// e.g.
		// supply-candidate with 23 (i.e. +23)
		// parent-stockCandidate used to have -44 (because of "earlier" candidate)
		// now has -21

		// notify the system about the stock candidate
		// this notification shall lead to all "later" stock candidates with the same product and locator being notified

		return newAndChangedCandidates;
	}

	public void projectedStockChange(final CandidatesSegment segment, final Quantity delta)
	{
		final List<Candidate> candidtesToUpdate = candidateRepository.retrieveStockFrom(segment);
		for (final Candidate candidate : candidtesToUpdate)
		{
			final Quantity newQty = candidate.getQuantity().add(delta);
			candidateRepository.add(candidate.withOtherQuantity(newQty));
		}

		// 1. select all stock candidates which have the same product and locator but a later timestamp than 'stockCandidate'
		// 2. add the "delta of 'stockCandidate' to their quantity
		// that's it for now :-). Don't alter those stock candidates' children or parents
	}
}
