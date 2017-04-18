package de.metas.material.dispo;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.material.dispo.Candidate.Type;
import de.metas.material.event.MaterialDemandEvent;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEventService;
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

	public void onDemandCandidateNewOrChange(final Candidate demandCandidate)
	{
		final Candidate demandCandidateWithId = candidateRepository.addOrReplace(demandCandidate);

		final Candidate stockWithDemand = updateStock(demandCandidate
				.withQuantity(demandCandidate.getQuantity().negate())
				.withParentId(demandCandidateWithId.getId()));

		if (stockWithDemand.getQuantity().signum() >= 0)
		{
			// there would still be stock left, so nothing to do
			return;
		}

		// notify whoever is in charge that we have a demand to balance
		final MaterialDemandEvent metarialDemandEvent = MaterialDemandEvent
				.builder()
				.descr(MaterialDescriptor.builder()
						.orgId(demandCandidate.getOrgId())
						.productId(demandCandidate.getProductId())
						.date(demandCandidate.getDate())
						.qty(stockWithDemand.getQuantity().negate())
						.warehouseId(demandCandidate.getWarehouseId())
						.build())
				.build();

		MaterialEventService.get().fireEvent(metarialDemandEvent);
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
		final Candidate parentStockCandidateWithId = updateStock(supplyCandidate);

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
	 * @param candidate a candidate with type being {@link Type#STOCK} and a quantity being a <b>delta</b>, i.e. a quantity that is add or removed at the candidate's date.
	 * 
	 * @return a new candidate that reflects what was persisted in the DB. The candidate will have an ID and its quantity will not be a delta, but the "absolute" projected quantity at the given time.
	 */
	public Candidate updateStock(@NonNull final Candidate candidate)
	{
		final Optional<Candidate> previousCandidate = candidateRepository.retrieve(candidate);

		final Candidate candidateToPersist = candidateFactory.createStockCandidate(candidate);

		final Candidate savedCandidate = candidateRepository.addOrReplace(candidateToPersist);

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
				.date(candidate.getDate())
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
