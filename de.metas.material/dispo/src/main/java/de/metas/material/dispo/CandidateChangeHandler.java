package de.metas.material.dispo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidatesSegment.DateOperator;
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

		if (demandCandidateWithId.getQuantity().signum() == 0)
		{
			// this candidate didn't change anything
			return;
		}

		final Candidate stockWithDemand = updateStock(demandCandidate
				.withQuantity(demandCandidateWithId.getQuantity().negate())
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
				.reference(demandCandidate.getReference())
				.build();

		MaterialEventService.get().fireEvent(metarialDemandEvent);
	}

	/**
	 * Call this one if the system was notified about a new or changed supply candidate.
	 * <p>
	 * Creates a new stock candidate or retrieves and updates an existing one.<br>
	 * The stock candidate is made the <i>parent</i> of the supplyCandidate.<br>
	 * When creating a new candidate, then compute its qty by getting the qty from that stockCandidate that has the same product and locator and is "before" it and add the supply candidate's qty
	 *
	 * @param supplyCandidate
	 */
	public Candidate onSupplyCandidateNewOrChange(final Candidate supplyCandidate)
	{
		// store the supply candidate and get both it's ID and qty-delta
		final Candidate supplyCandidateDeltaWithId = candidateRepository.addOrReplace(supplyCandidate);

		if (supplyCandidateDeltaWithId.getQuantity().signum() == 0)
		{
			return supplyCandidateDeltaWithId; // nothing to do
		}

		// update the stock with the delta
		final Candidate parentStockCandidateWithId = updateStock(supplyCandidateDeltaWithId);

		// set the stock candidate as parent for the supply candidate
		// the return value would have qty=0, but in the repository we updated the parent-ID
		candidateRepository.addOrReplace(
				supplyCandidate.withParentId(parentStockCandidateWithId.getId()));

		return supplyCandidateDeltaWithId;

		// e.g.
		// supply-candidate with 23 (i.e. +23)
		// parent-stockCandidate used to have -44 (because of "earlier" candidate)
		// now has -21
	}

	public void onCandidateDelete(@NonNull final TableRecordReference recordReference)
	{
		candidateRepository.deleteForReference(recordReference);
	}

	/**
	 * This method creates or updates a stock candidate according to the given candidate.
	 * It then updates the quantities of all "later" stock candidates that have the same product etc.<br>
	 * according to the delta between the candidate's former value (or zero if it was only just added) and it's new value.
	 *
	 * @param candidate a candidate that can have any type and a quantity which is a <b>delta</b>, i.e. a quantity that is added or removed at the candidate's date.
	 *
	 * @return a candidate with type {@link Type#STOCK} that reflects what was persisted in the DB.<br>
	 *         The candidate will have an ID and its quantity will not be a delta, but the "absolute" projected quantity at the given time.<br>
	 *         Note: this method does not establish a parent-child relationship between any two records
	 */
	public Candidate updateStock(@NonNull final Candidate candidate)
	{
		final Optional<Candidate> previousCandidate = candidateRepository.retrieve(candidate);

		final Candidate candidateToPersist = candidateFactory.createStockCandidate(candidate);

		final Candidate savedCandidate = candidateRepository.addOrReplace(candidateToPersist.withGroupId(null));

		final BigDecimal delta;
		if (previousCandidate.isPresent())
		{
			// there was already a persisted candidate. This means that the addOrReplace already did the work of providing our delta between the old and the current status.
			delta = savedCandidate.getQuantity();
		}
		else
		{
			// there was no persisted candidate, so we basically propagate the full qty (positive or negative) of the given candidate upwards
			delta = candidate.getQuantity();
		}
		applyDeltaToLaterStockCandidates(
				candidate.getProductId(),
				candidate.getWarehouseId(),
				candidate.getDate(),
				delta);
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
	/* package */ void applyDeltaToLaterStockCandidates(
			@NonNull final Integer productId,
			@NonNull final Integer warehouseId,
			@NonNull final Date date,
			@NonNull final BigDecimal delta)
	{
		final CandidatesSegment segment = CandidatesSegment.builder()
				.type(Type.STOCK)
				.date(date)
				.dateOperator(DateOperator.after)
				.productId(productId)
				.warehouseId(warehouseId)
				.build();

		final List<Candidate> candidatesToUpdate = candidateRepository.retrieveMatches(segment);
		for (final Candidate candidate : candidatesToUpdate)
		{
			final BigDecimal newQty = candidate.getQuantity().add(delta);
			candidateRepository.addOrReplace(candidate.withQuantity(newQty));
		}
	}
}
