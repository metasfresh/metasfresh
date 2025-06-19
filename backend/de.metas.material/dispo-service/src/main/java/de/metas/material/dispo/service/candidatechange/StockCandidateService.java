package de.metas.material.dispo.service.candidatechange;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Functions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.Profiles;
import de.metas.material.commons.attributes.clasifiers.BPartnerClassifier;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.CandidateSaveResult;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.commons.repository.DateAndSeqNo.Operator;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery.CustomerIdOperator;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery.MaterialDescriptorQueryBuilder;
import de.metas.material.dispo.commons.repository.query.SimulatedQueryQualifier;
import de.metas.material.dispo.model.I_MD_Candidate;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.util.Check;
import lombok.NonNull;
import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
	public CandidateSaveResult createStockCandidate(@NonNull final Candidate candidate)
	{
		final Candidate previousStockOrNull = getPreviousStockForStockCreation(candidate);

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
				.groupId(groupId)
				.simulated(candidate.isSimulated());

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

		return CandidateSaveResult.builder()
				.candidate(stockCandidate)
				.previousQty(previousQty)
				.build();
	}

	/**
	 * Updates the qty and date of the given candidate which is identified only by its id.
	 * Differs from {@link #applyDeltaToMatchingLaterStockCandidates(CandidateSaveResult)} in that
	 * if there is no existing persisted record, none is created but an exception is thrown.
	 * Also, it just updates the underlying persisted record of the given {@code candidateToUpdate} and nothing else.
	 *
	 * @param candidateToUpdate the candidate to update. Needs to have {@link Candidate#getId()} > 0.
	 */
	@VisibleForTesting
	CandidateSaveResult updateQtyAndDate(@NonNull final Candidate candidateToUpdate)
	{
		Check.errorIf(candidateToUpdate.getId().isNull(),
				"Parameter 'candidateToUpdate' needs to have a not-null Id; candidateToUpdate=%s",
				candidateToUpdate);

		final I_MD_Candidate candidateRecord = load(candidateToUpdate.getId().getRepoId(), I_MD_Candidate.class);
		final BigDecimal previousQty = candidateRecord.getQty();
		final Instant previousDate = candidateRecord.getDateProjected().toInstant();
		final int previousSeqNo = candidateRecord.getSeqNo();

		candidateRecord.setQty(candidateToUpdate.getQuantity());
		candidateRecord.setDateProjected(TimeUtil.asTimestamp(candidateToUpdate.getDate()));
		save(candidateRecord);

		return CandidateSaveResult.builder()
				.candidate(candidateToUpdate)
				.previousTime(DateAndSeqNo.builder().date(previousDate).seqNo(previousSeqNo).build())
				.previousQty(previousQty)
				.build();
	}

	/**
	 * Selects all stock candidates which have the same product and locator but a later timestamp than the one from the given {@code materialDescriptor}.
	 * Iterate them and add the given {@code delta} to their quantity.
	 */
	public void applyDeltaToMatchingLaterStockCandidates(@NonNull final CandidateSaveResult saveResult)
	{
		final Candidate initialStockCandidate = saveResult.getCandidate();
		if (initialStockCandidate.isSimulated())
		{
			return;
		}
		Check.assume(initialStockCandidate.getType().isStock(), "Initial candidate's type shall be stock; initialCandidate={}", initialStockCandidate);

		final CandidatesQuery query = createStockQueryAfterDate(saveResult);
		final List<Candidate> stockCandidatesToUpdate = candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(query);
		if (stockCandidatesToUpdate.isEmpty())
		{
			return;
		}

		final ImmutableMap<CandidateId, Candidate> stockIdToMainCandidateMap = computeStockIdToMainCandidateMap(stockCandidatesToUpdate);

		Candidate previousStockCandidate = getPreviousStockOrNullForCandidate(stockCandidatesToUpdate.get(0));
		final MaterialDispoGroupId groupId = initialStockCandidate.getGroupId();
		for (final Candidate stockCandidate : stockCandidatesToUpdate)
		{
			final Candidate mainCandidate = stockIdToMainCandidateMap.get(stockCandidate.getId());
			final Candidate updatedStockCandidate = stockCandidate
					.withQuantity(candidateRepositoryWriteService.getCurrentAtpAndUpdateQtyDetails(mainCandidate, stockCandidate, previousStockCandidate))
					.withGroupId(groupId);
			candidateRepositoryWriteService.updateCandidateById(updatedStockCandidate);

			previousStockCandidate = updatedStockCandidate;
		}
	}

	private ImmutableMap<CandidateId, Candidate> computeStockIdToMainCandidateMap(final List<Candidate> stockCandidatesToUpdate)
	{
		final Set<CandidateId> parentIdSet = stockCandidatesToUpdate.stream()
				.map(Candidate::getParentId)
				.filter(candidateId -> !candidateId.isNull())
				.collect(Collectors.toSet());
		final Set<CandidateId> idSet = stockCandidatesToUpdate.stream()
				.map(Candidate::getId)
				.collect(Collectors.toSet());

		final ImmutableList<Candidate> mainCandidates = candidateRepositoryRetrieval.retrieveMainCandidatesForStockCandidates(idSet, parentIdSet);
		final Map<CandidateId, Candidate> stockCandidateIdToCandidateMap = stockCandidatesToUpdate.stream()
				.collect(Collectors.toMap(Candidate::getId, Functions.identity()));
		final Map<CandidateId, Candidate> suppplyStockToMainCandidateMap = mainCandidates.stream()
				.filter(mainCandidate -> !mainCandidate.getParentId().isNull() && !mainCandidate.getId().equals(mainCandidate.getParentId()))
				.collect(Collectors.toMap(Candidate::getParentId, Functions.identity()));

		final Map<CandidateId, Candidate> demandStockToMainCandidateMap = stockCandidatesToUpdate.stream()
				.filter(stockCandidate -> stockCandidate.getParentId().isNull() && stockCandidateIdToCandidateMap.containsKey(stockCandidate.getParentId()))
				.collect(Collectors.toMap(Candidate::getId, stockCandidate -> stockCandidateIdToCandidateMap.get(stockCandidate.getParentId())));
		return ImmutableMap.<CandidateId, Candidate>builder()
				.putAll(suppplyStockToMainCandidateMap)
				.putAll(demandStockToMainCandidateMap)
				.build();
	}

	@NonNull
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

		final CandidatesQuery.CandidatesQueryBuilder candidatesQueryBuilder = CandidatesQuery.builder()
				.materialDescriptorQuery(materialDescriptorQuery)
				.type(CandidateType.STOCK)
				.matchExactStorageAttributesKey(true)
				.parentId(CandidateId.UNSPECIFIED);

		final SimulatedQueryQualifier simulatedQueryQualifier = candidate.isSimulated()
				? SimulatedQueryQualifier.INCLUDE_SIMULATED
				: SimulatedQueryQualifier.EXCLUDE_SIMULATED;

		candidatesQueryBuilder.simulatedQueryQualifier(simulatedQueryQualifier);

		return candidatesQueryBuilder.build();
	}

	private CandidatesQuery createStockQueryAfterDate(
			@NonNull final CandidateSaveResult saveResult)
	{
		final DateAndSeqNo rangeStart;
		if (!saveResult.isDateMoved())
		{
			rangeStart = DateAndSeqNo
					.ofCandidate(saveResult.getCandidate())
					.min(saveResult.getPreviousTime())
					.withOperator(Operator.INCLUSIVE);
		}
		else
		{
			if (saveResult.isDateMovedForwards())
			{
				rangeStart = DateAndSeqNo
						.ofCandidate(saveResult.getCandidate())
						.min(saveResult.getPreviousTime())
						.withOperator(Operator.INCLUSIVE);

			}
			else
			{
				rangeStart = DateAndSeqNo
						.ofCandidate(saveResult.getCandidate())
						.min(DateAndSeqNo
								.builder()
								.date(saveResult.getCandidate().getDate())
								.seqNo(saveResult.getCandidate().getSeqNo())
								.build())
						.withOperator(Operator.INCLUSIVE);
			}
		}
		final MaterialDescriptorQuery //
				materialDescriptorQuery = createMaterialDescriptorQueryBuilder(saveResult.getCandidate().getMaterialDescriptor())
				.timeRangeStart(rangeStart)
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

	@Nullable
	private Candidate getPreviousStockForStockCreation(@NonNull final Candidate candidate)
	{
		final Candidate previousStockOrNull = getPreviousStockOrNullForCandidate(candidate);

		if (!candidate.isSimulated() || previousStockOrNull == null)
		{
			return previousStockOrNull;
		}

		//dev-note: we need to make sure it's not it's own Stock as we don't propagate changes to later stocks for simulated candidates
		final boolean isOwnStock = CandidateId.isRegularNonNull(candidate.getParentId()) && candidate.getParentId().equals(previousStockOrNull.getId())
				|| CandidateId.isRegularNonNull(previousStockOrNull.getParentId()) && previousStockOrNull.getParentId().equals(candidate.getId());

		if (!isOwnStock)
		{
			return previousStockOrNull;
		}

		return getPreviousStockOrNullForCandidate(previousStockOrNull);
	}

	@Nullable
	private Candidate getPreviousStockOrNullForCandidate(@NonNull final Candidate candidate)
	{
		final CandidatesQuery previousStockQuery = createStockQueryUntilDate(candidate);

		return candidateRepositoryRetrieval.retrieveLatestMatchOrNull(previousStockQuery);
	}
}
