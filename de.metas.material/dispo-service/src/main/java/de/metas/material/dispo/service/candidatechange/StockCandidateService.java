package de.metas.material.dispo.service.candidatechange;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.CandidatesSegment;
import de.metas.material.dispo.CandidatesSegment.DateOperator;
import de.metas.material.event.MaterialDescriptor;
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
public class StockCandidateService
{
	private final CandidateRepository candidateRepository;

	public StockCandidateService(@NonNull final CandidateRepository candidateRepository)
	{
		this.candidateRepository = candidateRepository;
	}

	/**
	 * Creates and returns <b>but does not store</b> a new stock candidate which takes its quantity from the next-younger (or same-age!) stock candidate that has the same product and warehouse.
	 * If there is no such next-younger stock candidate (i.e. if this is the very first stock candidate to be created for the given product and locator), then a quantity of zero is taken.
	 *
	 * @param candidate
	 * @return a candidate with
	 *         <ul>
	 *         <li>type = {@link Type#STOCK}</li>
	 *         <li>qty = qty of the given {@code candidate} plus the next younger candidate's quantity
	 *         <li>groupId of the next younger-candidate (or null if there is none)
	 *         </ul>
	 */
	public Candidate createStockCandidate(@NonNull final Candidate candidate)
	{
		final Optional<Candidate> stock = candidateRepository
				.retrieveLatestMatch(candidate.mkSegmentBuilder()
						.type(Type.STOCK)
						.dateOperator(DateOperator.until)
						.build());

		final BigDecimal formerQuantity = stock.isPresent()
				? stock.get().getQuantity()
				: BigDecimal.ZERO;

		final Integer groupId = stock.isPresent()
				? stock.get().getGroupId()
				: null;

		final MaterialDescriptor materialDescr = candidate
				.getMaterialDescr()
				.withQuantity(formerQuantity.add(candidate.getQuantity()));

		return Candidate.builder()
				.type(Type.STOCK)
				.orgId(candidate.getOrgId())
				.clientId(candidate.getClientId())
				.reference(candidate.getReference())
				.materialDescr(materialDescr)
				.parentId(candidate.getParentId())
				.seqNo(candidate.getSeqNo())
				.groupId(groupId)
				.build();
	}

	/**
	 * This method creates or updates a stock candidate according to the given candidate.
	 * It then updates the quantities of all "later" stock candidates that have the same product etc.<br>
	 * according to the delta between the candidate's former value (or zero if it was only just added) and it's new value.
	 *
	 * @param relatedCandidate a candidate that can have any type and a quantity which is a <b>delta</b>, i.e. a quantity that is added or removed at the candidate's date.
	 *
	 * @return a candidate with type {@link Type#STOCK} that reflects what was persisted in the DB.<br>
	 *         The candidate will have an ID and its quantity will not be a delta, but the "absolute" projected quantity at the given time.<br>
	 *         Note: this method does not establish a parent-child relationship between any two records
	 */
	public Candidate addOrUpdateStock(@NonNull final Candidate relatedCandidate)
	{
		final Supplier<Candidate> stockCandidateToUpdate = () -> {
			final Candidate stockCandidateToPersist = createStockCandidate(relatedCandidate);
			final Candidate persistedStockCandidate = candidateRepository.addOrUpdatePreserveExistingSeqNo(stockCandidateToPersist);
			return persistedStockCandidate;
		};

		return updateStock(
				relatedCandidate,
				stockCandidateToUpdate);
	}

	/**
	 * Updates the qty for the stock candidate returned by the given {@code stockCandidateToUpdate} and it's later stock candidates
	 * 
	 * @param relatedCanidateWithDelta
	 * @param stockCandidateToUpdate
	 * @return
	 */
	public Candidate updateStock(
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
				relatedCanidateWithDelta.getMaterialDescr().getProductId(),
				relatedCanidateWithDelta.getMaterialDescr().getWarehouseId(),
				relatedCanidateWithDelta.getMaterialDescr().getDate(),
				persistedStockCandidate.getGroupId(),
				delta);
		return persistedStockCandidate;
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

		final List<Candidate> candidatesToUpdate = candidateRepository.retrieveMatchesOrderByDateAndSeqNo(segment);
		for (final Candidate candidate : candidatesToUpdate)
		{
			final BigDecimal newQty = candidate.getQuantity().add(delta);
			candidateRepository.addOrUpdateOverwriteStoredSeqNo(candidate
					.withQuantity(newQty)
					.withGroupId(groupId));
		}
	}
}
