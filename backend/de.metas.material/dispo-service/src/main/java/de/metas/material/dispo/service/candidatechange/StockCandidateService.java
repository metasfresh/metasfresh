package de.metas.material.dispo.service.candidatechange;

import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService.SaveResult;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.commons.repository.DateAndSeqNo.Operator;
import de.metas.material.dispo.commons.repository.atp.BPartnerClassifier;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery.CustomerIdOperator;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery.MaterialDescriptorQueryBuilder;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.util.Check;
import lombok.NonNull;
import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.save;

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
@Profile(Profiles.PROFILE_MaterialDispo)
public class StockCandidateService
{
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	private final CandidateRepositoryWriteService candidateRepositoryWriteService;

	public StockCandidateService(
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval,
			@NonNull final CandidateRepositoryWriteService candidateRepositoryWriteService)
	{
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
		this.candidateRepositoryWriteService = candidateRepositoryWriteService;
	}

	/**
	 * Creates and returns <b>but does not store</b> a new stock candidate
	 * whose quantity is the quantity of the given {@code candidate} plus the quantity from
	 * the next-younger (not same-age!) stock candidate that has the same product, storage attributes key and warehouse.
	 * <p>
	 * If there is no such next-younger stock candidate (i.e. if this is the very first stock candidate to be created for the given product and locator), then a quantity of zero is taken.
	 *
	 * @return a candidate with
	 * <ul>
	 * <li>type = {@link CandidateType#STOCK}</li>
	 * <li>qty = qty of the given {@code candidate} plus the next younger candidate's quantity
	 * <li>groupId of the next younger-candidate (or null if there is none)
	 * </ul>
	 */
	public SaveResult createStockCandidate(@NonNull final Candidate candidate)
	{
		final CandidatesQuery previousStockQuery = createStockQueryUntilDate(candidate);
		final Candidate previousStockOrNull = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(previousStockQuery);

		final BigDecimal newQty;
		final BigDecimal previousQty;
		if (previousStockOrNull == null)
		{
			newQty = candidate.getQuantity();
			previousQty = null;
		}
		else
		{
			previousQty = previousStockOrNull.getQuantity();
			newQty = previousQty.add(candidate.getQuantity());
		}

		final MaterialDescriptor materialDescriptor = candidate
				.getMaterialDescriptor()
				.withQuantity(newQty);

		final MaterialDispoGroupId groupId = previousStockOrNull != null
				? previousStockOrNull.getGroupId()
				: null;

		final Candidate.CandidateBuilder candidateBuilder = Candidate.builder()
				.type(CandidateType.STOCK)
				.clientAndOrgId(candidate.getClientAndOrgId())
				.parentId(candidate.getParentId())
				.seqNo(candidate.getSeqNo())
				.groupId(groupId);
		if (materialDescriptor.isReservedForCustomer())
		{
			candidateBuilder.materialDescriptor(materialDescriptor);
		}
		else
		{ // our stock candidate's quantity shall not be bound to a particular customer, so don't propagate the costomer-id to the stock record
			candidateBuilder.materialDescriptor(materialDescriptor.withCustomerId(null));
		}

		final Candidate stockCandidate = candidateBuilder
				.build();

		return SaveResult.builder()
				.candidate(stockCandidate)
				.previousQty(previousQty)
				.build();
	}

	/**
	 * Updates the qty and date of the given candidate which is identified only by its id.
	 * Differs from {@link #applyDeltaToMatchingLaterStockCandidates(SaveResult)} in that
	 * if there is no existing persisted record, none is created but an exception is thrown.
	 * Also, it just updates the underlying persisted record of the given {@code candidateToUpdate} and nothing else.
	 *
	 * @param candidateToUpdate the candidate to update. Needs to have {@link Candidate#getId()} > 0.
	 */
	public SaveResult updateQtyAndDate(@NonNull final Candidate candidateToUpdate)
	{
		Check.errorIf(candidateToUpdate.getId().isNull(),
				"Parameter 'candidateToUpdate' needs to have a not-null Id; candidateToUpdate=%s",
				candidateToUpdate);

		final I_MD_Candidate candidateRecord = load(candidateToUpdate.getId().getRepoId(), I_MD_Candidate.class);
		final BigDecimal previousQty = candidateRecord.getQty();
		final Instant previousDate = TimeUtil.asInstant(candidateRecord.getDateProjected());
		final int previousSeqNo = candidateRecord.getSeqNo();

		candidateRecord.setQty(candidateToUpdate.getQuantity());
		candidateRecord.setDateProjected(TimeUtil.asTimestamp(candidateToUpdate.getDate()));
		save(candidateRecord);

		return SaveResult.builder()
				.candidate(candidateToUpdate)
				.previousTime(DateAndSeqNo
						.builder()
						.date(previousDate)
						.seqNo(previousSeqNo)
						.build())
				.previousQty(previousQty)
				.build();
	}

	/**
	 * Selects all stock candidates which have the same product and locator but a later timestamp than the one from the given {@code materialDescriptor}.
	 * Iterate them and add the given {@code delta} to their quantity.
	 */
	public void applyDeltaToMatchingLaterStockCandidates(@NonNull final SaveResult stockWithDelta)
	{
		final CandidatesQuery query = createStockQueryBetweenDates(stockWithDelta);

		final BigDecimal deltaUntilRangeEnd;
		final BigDecimal deltaAfterRangeEnd;
		if (stockWithDelta.isDateMoved())
		{
			if (stockWithDelta.isDateMovedForwards())
			{
				deltaUntilRangeEnd = stockWithDelta.getCandidate().getQuantity().negate();
				deltaAfterRangeEnd = stockWithDelta.getQtyDelta();
			}
			else
			{
				deltaUntilRangeEnd = stockWithDelta.getCandidate().getQuantity();
				deltaAfterRangeEnd = stockWithDelta.getQtyDelta();
			}
		}
		else
		{
			deltaUntilRangeEnd = stockWithDelta.getQtyDelta();
			deltaAfterRangeEnd = null;
		}

		final List<Candidate> candidatesToUpdateWithinRange = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(query);
		for (final Candidate candidate : candidatesToUpdateWithinRange)
		{
			final BigDecimal newQty = candidate.getQuantity().add(deltaUntilRangeEnd);

			candidateRepositoryWriteService.updateCandidateById(candidate
					.withQuantity(newQty)
					.withGroupId(stockWithDelta.getCandidate().getGroupId()));
		}
		if (deltaAfterRangeEnd == null || deltaAfterRangeEnd.signum() == 0)
		{
			return; // we are done
		}

		final MaterialDescriptorQuery materialDescriptorQuery = query.getMaterialDescriptorQuery();
		final MaterialDescriptorQuery materialDescriptToQueryAfterRange = materialDescriptorQuery.toBuilder()
				.timeRangeStart(materialDescriptorQuery.getTimeRangeEnd())
				.timeRangeEnd(null)
				.build();
		final CandidatesQuery queryAfterRange = query.withMaterialDescriptorQuery(materialDescriptToQueryAfterRange);
		final List<Candidate> candidatesToUpdateAfterRange = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(queryAfterRange);
		for (final Candidate candidate : candidatesToUpdateAfterRange)
		{
			final BigDecimal newQty = candidate.getQuantity().add(deltaAfterRangeEnd);

			candidateRepositoryWriteService.updateCandidateById(candidate
					.withQuantity(newQty)
					.withGroupId(stockWithDelta.getCandidate().getGroupId()));
		}
	}

	private CandidatesQuery createStockQueryUntilDate(
			@NonNull final Candidate candidate)
	{
		final MaterialDescriptorQuery //
				materialDescriptorQuery = createMaterialDescriptorQueryBuilder(candidate.getMaterialDescriptor())
				.timeRangeEnd(DateAndSeqNo
						.builder()
						.date(candidate.getDate())
						.seqNo(candidate.getSeqNo())
						.operator(Operator.EXCLUSIVE)
						.build())
				.build();

		return CandidatesQuery.builder()
				.materialDescriptorQuery(materialDescriptorQuery)
				.type(CandidateType.STOCK)
				.matchExactStorageAttributesKey(true)
				.parentId(CandidateId.UNSPECIFIED)
				.build();
	}

	private CandidatesQuery createStockQueryBetweenDates(
			@NonNull final SaveResult saveResult)
	{
		final DateAndSeqNo rangeStart = DateAndSeqNo
				.ofCandidate(saveResult.getCandidate())
				.min(saveResult.getPreviousTime())
				.withOperator(Operator.EXCLUSIVE);

		final DateAndSeqNo rangeEnd;
		if (!saveResult.isDateMoved())
		{
			rangeEnd = null; // if the stock is not moving on the time axis, we look at all records, from rangeStart onwards
		}
		else
		{
			rangeEnd = DateAndSeqNo
					.ofCandidate(saveResult.getCandidate())
					.max(saveResult.getPreviousTime())

					// if we moved a record forward in time (i.e. increased its date), then this is the time where the record sits now;
					// we don't want to apply the delta to it, because it already has the value it shall have
					.withOperator(Operator.EXCLUSIVE);
		}

		final MaterialDescriptorQuery //
				materialDescriptorQuery = createMaterialDescriptorQueryBuilder(saveResult.getCandidate().getMaterialDescriptor())
				.timeRangeStart(rangeStart)
				.timeRangeEnd(rangeEnd)
				.build();

		return CandidatesQuery.builder()
				.materialDescriptorQuery(materialDescriptorQuery)
				.type(CandidateType.STOCK)
				.matchExactStorageAttributesKey(true)
				.parentId(CandidateId.UNSPECIFIED)
				.build();
	}

	private MaterialDescriptorQueryBuilder createMaterialDescriptorQueryBuilder(
			@NonNull final MaterialDescriptor materialDescriptor)
	{
		final MaterialDescriptorQueryBuilder builder = MaterialDescriptorQuery
				.builder()
				.customerIdOperator(CustomerIdOperator.GIVEN_ID_OR_NULL); // want the latest, only excluding records that have a *different* customerId

		if (materialDescriptor.getCustomerId() != null && materialDescriptor.isReservedForCustomer())
		{
			// do include the bpartner in the query, because e.g. an increase for a given bpartner does a raised ATP just for that partner, and not for everyone
			builder.customer(BPartnerClassifier.specific(materialDescriptor.getCustomerId()));
		}
		else
		{
			// ..on the other hand, if materialDescriptor has *no* bpartner or that partner is just for info, then the respective change in ATP is for everybody
			builder.customer(BPartnerClassifier.any());
		}

		return builder
				.productId(materialDescriptor.getProductId())
				.storageAttributesKey(materialDescriptor.getStorageAttributesKey())
				.warehouseId(materialDescriptor.getWarehouseId());
	}
}
