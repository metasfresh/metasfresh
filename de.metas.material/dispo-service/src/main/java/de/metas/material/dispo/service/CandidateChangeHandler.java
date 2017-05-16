package de.metas.material.dispo.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.adempiere.util.Check;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.CandidatesSegment;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidatesSegment.DateOperator;
import de.metas.material.event.EventDescr;
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
@Lazy // .. because MaterialEventService needs to be lazy
public class CandidateChangeHandler
{
	private final CandidateRepository candidateRepository;

	private final CandidateFactory candidateFactory;

	private final MaterialEventService materialEventService;

	public CandidateChangeHandler(
			@NonNull final CandidateRepository candidateRepository,
			@NonNull final CandidateFactory candidateFactory,
			@NonNull final MaterialEventService materialEventService)
	{
		this.candidateRepository = candidateRepository;
		this.candidateFactory = candidateFactory;
		this.materialEventService = materialEventService;
	}

	public Candidate onCandidateNewOrChange(@NonNull final Candidate candidate)
	{
		switch (candidate.getType())
		{
			case DEMAND:
				return onDemandCandidateNewOrChange(candidate);

			case SUPPLY:
				return onSupplyCandidateNewOrChange(candidate);

			default:
				Check.errorIf(true, "Param 'candidate' has unexpected type={}; candidate={}", candidate.getType(), candidate);
				return null;
		}
	}

	/**
	 * Persists (updates or creates) the given demand candidate and also it's <b>child</b> stock candidate.
	 * 
	 * @param demandCandidate
	 * @return
	 */
	@VisibleForTesting
	Candidate onDemandCandidateNewOrChange(@NonNull final Candidate demandCandidate)
	{
		Preconditions.checkArgument(demandCandidate.getType() == Type.DEMAND, "Given parameter 'demandCandidate' has type=%s; demandCandidate=%s", demandCandidate.getType(), demandCandidate);

		final Candidate demandCandidateWithId = candidateRepository.addOrUpdate(demandCandidate);

		if (demandCandidateWithId.getQuantity().signum() == 0)
		{
			// this candidate didn't change anything
			return demandCandidateWithId;
		}

		// this is the seqno which the new stock candidate shall get according to the demand candidate
		final int expectedStockSeqNo = demandCandidateWithId.getSeqNo() + 1;

		final Candidate childStockWithDemand;

		final Optional<Candidate> possibleChildStockCandidate = candidateRepository.retrieveSingleChild(demandCandidateWithId.getId());
		if (possibleChildStockCandidate.isPresent())
		{
			// this supply candidate is not new and already has a stock candidate as its parent. be sure to update exactly *that* scandidate
			childStockWithDemand = updateStock(
					demandCandidateWithId, () ->
						{
							// don't check if we might create a new stock candidate, because we know we don't.
							// Instead we might run into trouble with CandidateRepository.retrieveExact() and multiple matching records.
							// So get the one that we know already exists and just update its quantity
							final Candidate childStockCandidate = possibleChildStockCandidate.get();
							return candidateRepository.updateQty(
									childStockCandidate
											.withQuantity(
													childStockCandidate.getQuantity().subtract(demandCandidateWithId.getQuantity())));
						});
		}

		else
		{
			childStockWithDemand = addOrUpdateStock(
					demandCandidate
							.withSeqNo(expectedStockSeqNo)
							.withQuantity(demandCandidateWithId.getQuantity().negate())
							.withParentId(demandCandidateWithId.getId()));
		}

		final Candidate demandCandidateToReturn;

		if (childStockWithDemand.getSeqNo() != expectedStockSeqNo)
		{
			// there was already a stock candidate which already had a seqNo.
			// keep it and in turn update the demandCandidate's seqNo accordingly
			demandCandidateToReturn = demandCandidate
					.withSeqNo(childStockWithDemand.getSeqNo() - 1);
			candidateRepository.addOrUpdate(demandCandidateToReturn);
		}
		else
		{
			demandCandidateToReturn = demandCandidateWithId;
		}

		if (childStockWithDemand.getQuantity().signum() < 0)
		{
			// there would be no more stock left, so
			// notify whoever is in charge that we have a demand to balance
			final int orderLineId = demandCandidate.getDemandDetail() == null ? 0 : demandCandidate.getDemandDetail().getOrderLineId();

			final MaterialDemandEvent materialDemandEvent = MaterialDemandEvent
					.builder()
					.eventDescr(new EventDescr(demandCandidate.getClientId(), demandCandidate.getOrgId()))
					.descr(MaterialDescriptor.builder()
							.productId(demandCandidate.getProductId())
							.date(demandCandidate.getDate())
							.qty(childStockWithDemand.getQuantity().negate())
							.warehouseId(demandCandidate.getWarehouseId())
							.build())
					.reference(demandCandidate.getReference())
					.orderLineId(orderLineId)
					.build();

			materialEventService.fireEvent(materialDemandEvent);
		}
		return demandCandidateToReturn;
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
	@VisibleForTesting
	Candidate onSupplyCandidateNewOrChange(@NonNull final Candidate supplyCandidate)
	{
		Preconditions.checkArgument(supplyCandidate.getType() == Type.SUPPLY, "Given parameter 'supplyCandidate' has type=%s; supplyCandidate=%s", supplyCandidate.getType(), supplyCandidate);

		// store the supply candidate and get both it's ID and qty-delta
		final Candidate supplyCandidateDeltaWithId = candidateRepository.addOrUpdate(supplyCandidate);

		if (supplyCandidateDeltaWithId.getQuantity().signum() == 0)
		{
			return supplyCandidateDeltaWithId; // nothing to do
		}

		final Candidate parentStockCandidateWithId;
		if (supplyCandidateDeltaWithId.getParentIdNotNull() > 0)
		{
			// this supply candidate is not new and already has a stock candidate as its parent. be sure to update exactly *that* scandidate
			parentStockCandidateWithId = updateStock(
					supplyCandidateDeltaWithId,
					() ->
						{
							// don't check if we might create a new stock candidate, because we know we don't. Get the one that already exists and just update its quantity
							final Candidate stockCandidate = candidateRepository.retrieve(supplyCandidateDeltaWithId.getParentId());
							return candidateRepository.updateQty(
									stockCandidate.withQuantity(
											stockCandidate.getQuantity().add(supplyCandidateDeltaWithId.getQuantity())));
						});
		}
		else
		{
			// update (or add) the stock with the delta
			parentStockCandidateWithId = addOrUpdateStock(supplyCandidateDeltaWithId
					// but don't provide the supply's SeqNo, because there might already be a stock record which we might override (even if the supply candidate is not yet linked to it);
					// plus, the supply's seqNo shall depend on the stock's anyways
					.withSeqNo(null));
		}

		// set the stock candidate as parent for the supply candidate
		// the return value would have qty=0, but in the repository we updated the parent-ID
		candidateRepository.addOrUpdate(
				supplyCandidate
						.withParentId(parentStockCandidateWithId.getId())
						.withSeqNo(parentStockCandidateWithId.getSeqNo() + 1));

		return supplyCandidateDeltaWithId
				.withParentId(parentStockCandidateWithId.getId())
				.withSeqNo(parentStockCandidateWithId.getSeqNo() + 1);

		// e.g.
		// supply-candidate with 23 (i.e. +23)
		// parent-stockCandidate used to have -44 (because of "earlier" candidate)
		// now has -21
	}

	/**
	 * Updates the qty for the stock candidate returned by the given {@code stockCandidateToUpdate} and it's later stock candidates
	 * 
	 * @param relatedCanidateWithDelta
	 * @param stockCandidateToUpdate
	 * @return
	 */
	private Candidate updateStock(
			@NonNull final Candidate relatedCanidateWithDelta,
			@NonNull final Supplier<Candidate> stockCandidateToUpdate)
	{
		final Optional<Candidate> previousCandidate = candidateRepository.retrieve(relatedCanidateWithDelta);

		final Candidate persistedStockCandidate = stockCandidateToUpdate.get();

		final BigDecimal delta;
		if (previousCandidate.isPresent())
		{
			// there was already a persisted candidate. This means that the addOrReplace already did the work of providing our delta between the old and the current status.
			delta = persistedStockCandidate.getQuantity();
		}
		else
		{
			// there was no persisted candidate, so we basically propagate the full qty (positive or negative) of the given candidate upwards
			delta = relatedCanidateWithDelta.getQuantity();
		}
		applyDeltaToLaterStockCandidates(
				relatedCanidateWithDelta.getProductId(),
				relatedCanidateWithDelta.getWarehouseId(),
				relatedCanidateWithDelta.getDate(),
				persistedStockCandidate.getGroupId(),
				delta);
		return persistedStockCandidate;
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
	public Candidate addOrUpdateStock(@NonNull final Candidate candidate)
	{
		return updateStock(
				candidate,
				() ->
					{
						final Candidate stockCandidateToPersist = candidateFactory.createStockCandidate(candidate);

						final boolean preserveExistingSeqNo = true; // there there is a stock record with a seqNo, then don't override it, because we will need to adapt to it in order to put our new data into the right sequence.
						final Candidate persistedStockCandidate = candidateRepository.addOrUpdate(stockCandidateToPersist, preserveExistingSeqNo);

						return persistedStockCandidate;
					});
	}

	public void onCandidateDelete(@NonNull final TableRecordReference recordReference)
	{
		candidateRepository.deleteForReference(recordReference);
	}

	/**
	 * Selects all stock candidates which have the same product and locator but a later timestamp than the one from the given {@code segment}.
	 * Iterate them and add the given {@code delta} to their quantity.
	 * <p>
	 * That's it for now :-). Don't alter those stock candidates' children or parents.
	 *
	 * @param productId the product ID to match against
	 * @param warehouseId the warehouse ID to match against
	 * @param date the date to match against (i.e. everything after this date shall be a match)
	 * @param groupId the groupId to set to every stock record that we matched
	 * @param delta the quantity (positive or negative) to add to every stock record that we matched
	 */
	@VisibleForTesting
	/* package */ void applyDeltaToLaterStockCandidates(
			@NonNull final Integer productId,
			@NonNull final Integer warehouseId,
			@NonNull final Date date,
			@NonNull final Integer groupId,
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
			candidateRepository.addOrUpdate(candidate
					.withQuantity(newQty)
					.withGroupId(groupId));
		}
	}
}
