package de.metas.distribution.ddordercandidate.material_dispo;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import com.google.common.collect.Sets;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.lowlevel.DDOrderLowLevelDAO;
import de.metas.distribution.ddordercandidate.DDOrderCandidate;
import de.metas.distribution.ddordercandidate.DDOrderCandidateId;
import de.metas.distribution.ddordercandidate.DDOrderCandidateQuery;
import de.metas.distribution.ddordercandidate.DDOrderCandidateRepository;
import de.metas.i18n.AdMessageKey;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DistributionDetailsQuery;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.eevolution.api.PPOrderAndBOMLineId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.api.ProductBOMLineId;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Re-points the distribution supply (MD_Candidate distribution details, DD_Order_Candidates, DD_Orders) of the BOM lines of a PP_Order
 * to the BOM lines which are re-created for the same PP_Product_BOMLine, following the move pattern of {@link PPOrderCandidateListeners}.
 * <p>
 * Usage: {@link #collect(PPOrderId)} before the BOM lines are deleted, {@link #moveToRecreatedBOMLines(Snapshot)} after they were re-created, both in the same transaction.
 */
@Component
@RequiredArgsConstructor
public class PPOrderBOMLineDistributionSupplyMover
{
	public static final AdMessageKey DD_ORDER_CANDIDATE_ALREADY_PROCESSED_CHANGES_NOT_ALLOWED = AdMessageKey.of("org.eevolution.model.validator.DD_ORDER_CANDIDATE_ALREADY_PROCESSED_CHANGES_NOT_ALLOWED");

	private final IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
	@NonNull private final DDOrderCandidateRepository ddOrderCandidateRepository;
	@NonNull private final DDOrderLowLevelDAO ddOrderLowLevelDAO;
	@NonNull private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	@NonNull private final CandidateRepositoryWriteService candidateRepositoryWriteService;

	@Value
	public static class Snapshot
	{
		@NonNull PPOrderId ppOrderId;
		@NonNull ImmutableList<OldBOMLine> oldBOMLines;
	}

	@Value
	static class Move
	{
		@NonNull OldBOMLine oldBOMLine;
		@NonNull PPOrderBOMLineId newBOMLineId;
	}

	/**
	 * Only ids are kept: the records are loaded fresh when they are re-pointed, so that a change committed meanwhile is not reverted
	 * and a record deleted meanwhile is not re-created.
	 */
	@Value
	static class OldBOMLine
	{
		@NonNull PPOrderBOMLineId bomLineId;
		@Nullable ProductBOMLineId productBOMLineId;
		@NonNull ImmutableSet<CandidateId> mdCandidateIds;
		@NonNull ImmutableSet<DDOrderCandidateId> ddOrderCandidateIds;
		@NonNull ImmutableSet<DDOrderId> ddOrderIds;
	}

	/**
	 * To be called before the BOM lines are deleted. Loads the supply of all BOM lines of the PP_Order at once.
	 */
	public Snapshot collect(@NonNull final PPOrderId ppOrderId)
	{
		final List<I_PP_Order_BOMLine> bomLines = ppOrderBOMDAO.retrieveOrderBOMLines(ppOrderId);
		final ImmutableSet<PPOrderBOMLineId> bomLineIds = bomLines.stream()
				.map(bomLine -> PPOrderBOMLineId.ofRepoId(bomLine.getPP_Order_BOMLine_ID()))
				.collect(ImmutableSet.toImmutableSet());
		if (bomLineIds.isEmpty())
		{
			return new Snapshot(ppOrderId, ImmutableList.of());
		}

		final ImmutableSetMultimap<PPOrderBOMLineId, CandidateId> mdCandidateIdsByBOMLineId = retrieveMDCandidates(bomLineIds)
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(PPOrderBOMLineDistributionSupplyMover::getMDCandidateBOMLineIdOrNull, Candidate::getId));

		final ImmutableSetMultimap<PPOrderBOMLineId, DDOrderCandidateId> ddOrderCandidateIdsByBOMLineId = retrieveDDOrderCandidates(ppOrderId, bomLineIds)
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(PPOrderBOMLineDistributionSupplyMover::getDDOrderCandidateBOMLineIdOrNull, DDOrderCandidate::getIdNotNull));

		// only the DD_Orders of the old BOM lines; a DD_Order without or with another BOM line (e.g. shared across PP_Orders) stays untouched
		final ImmutableSetMultimap<PPOrderBOMLineId, DDOrderId> ddOrderIdsByBOMLineId = ddOrderLowLevelDAO.getIdsByForwardPPOrderBOMLineIds(bomLineIds);

		final ImmutableList.Builder<OldBOMLine> oldBOMLines = ImmutableList.builder();
		for (final I_PP_Order_BOMLine bomLine : bomLines)
		{
			final PPOrderBOMLineId bomLineId = PPOrderBOMLineId.ofRepoId(bomLine.getPP_Order_BOMLine_ID());
			oldBOMLines.add(new OldBOMLine(
					bomLineId,
					ProductBOMLineId.ofRepoIdOrNull(bomLine.getPP_Product_BOMLine_ID()),
					mdCandidateIdsByBOMLineId.get(bomLineId),
					ddOrderCandidateIdsByBOMLineId.get(bomLineId),
					ddOrderIdsByBOMLineId.get(bomLineId)));
		}
		return new Snapshot(ppOrderId, oldBOMLines.build());
	}

	private List<Candidate> retrieveMDCandidates(@NonNull final Collection<PPOrderBOMLineId> bomLineIds)
	{
		return candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(
						CandidatesQuery.builder()
								.businessCase(CandidateBusinessCase.DISTRIBUTION)
								.distributionDetailsQuery(DistributionDetailsQuery.builder().ppOrderBOMLineIds(bomLineIds).build())
								.build())
				.stream()
				.filter(candidate -> getMDCandidateBOMLineIdOrNull(candidate) != null)
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * DD_Order_Candidates without a BOM line (made from PP_Order_Candidates) or with a BOM line of another PP_Order do not belong to the old lines.
	 */
	private List<DDOrderCandidate> retrieveDDOrderCandidates(@NonNull final PPOrderId ppOrderId, @NonNull final Set<PPOrderBOMLineId> bomLineIds)
	{
		return ddOrderCandidateRepository.list(DDOrderCandidateQuery.builder().ppOrderId(ppOrderId).build())
				.stream()
				.filter(candidate -> bomLineIds.contains(getDDOrderCandidateBOMLineIdOrNull(candidate)))
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * To be called after the BOM lines were re-created.
	 * <p>
	 * Several lines may share one PP_Product_BOMLine (e.g. the same phantom exploded twice); the n-th old line of a PP_Product_BOMLine
	 * is paired with the n-th new line of it. An old line without a counterpart is treated like a removed component.
	 */
	public void moveToRecreatedBOMLines(@NonNull final Snapshot snapshot)
	{
		final PPOrderId ppOrderId = snapshot.getPpOrderId();

		final Map<ProductBOMLineId, Deque<PPOrderBOMLineId>> newBOMLineIdsByProductBOMLine = new HashMap<>();
		for (final I_PP_Order_BOMLine line : ppOrderBOMDAO.retrieveOrderBOMLines(ppOrderId))
		{
			final ProductBOMLineId productBOMLineId = ProductBOMLineId.ofRepoIdOrNull(line.getPP_Product_BOMLine_ID());
			if (productBOMLineId != null)
			{
				newBOMLineIdsByProductBOMLine
						.computeIfAbsent(productBOMLineId, productBOMLineIdKey -> new ArrayDeque<>())
						.add(PPOrderBOMLineId.ofRepoId(line.getPP_Order_BOMLine_ID()));
			}
		}

		final List<Move> moves = new ArrayList<>();
		final List<OldBOMLine> removedBOMLines = new ArrayList<>();
		for (final OldBOMLine oldBOMLine : snapshot.getOldBOMLines())
		{
			final Deque<PPOrderBOMLineId> newBOMLineIds = oldBOMLine.getProductBOMLineId() != null ? newBOMLineIdsByProductBOMLine.get(oldBOMLine.getProductBOMLineId()) : null;
			final PPOrderBOMLineId newBOMLineId = newBOMLineIds != null ? newBOMLineIds.pollFirst() : null;
			if (newBOMLineId != null)
			{
				moves.add(new Move(oldBOMLine, newBOMLineId));
			}
			else
			{
				removedBOMLines.add(oldBOMLine);
			}
		}

		moveMDCandidates(ppOrderId, moves);
		moveDDOrderCandidates(ppOrderId, moves);
		moves.forEach(move -> moveDDOrders(move.getOldBOMLine(), PPOrderAndBOMLineId.of(ppOrderId, move.getNewBOMLineId())));
		removedBOMLines.forEach(this::deleteDDOrderCandidates);
	}

	private static Map<PPOrderBOMLineId, PPOrderBOMLineId> toNewBOMLineIdByOldBOMLineId(@NonNull final Collection<Move> moves)
	{
		final Map<PPOrderBOMLineId, PPOrderBOMLineId> result = new HashMap<>();
		moves.forEach(move -> result.put(move.getOldBOMLine().getBomLineId(), move.getNewBOMLineId()));
		return result;
	}

	private void moveMDCandidates(@NonNull final PPOrderId ppOrderId, @NonNull final Collection<Move> moves)
	{
		final Map<PPOrderBOMLineId, PPOrderBOMLineId> newBOMLineIdByOldBOMLineId = toNewBOMLineIdByOldBOMLineId(moves);
		final Set<CandidateId> expectedIds = moves.stream()
				.flatMap(move -> move.getOldBOMLine().getMdCandidateIds().stream())
				.collect(ImmutableSet.toImmutableSet());
		if (expectedIds.isEmpty())
		{
			return;
		}

		// fresh load, right before the re-point
		final List<Candidate> candidates = retrieveMDCandidates(newBOMLineIdByOldBOMLineId.keySet());
		final Set<CandidateId> missingIds = Sets.difference(expectedIds, candidates.stream().map(Candidate::getId).collect(ImmutableSet.toImmutableSet()));
		if (!missingIds.isEmpty())
		{
			throw new AdempiereException("MD_Candidates " + missingIds + " of the old BOM lines do not exist anymore");
		}

		for (final Candidate candidate : candidates)
		{
			final PPOrderBOMLineId newBOMLineId = newBOMLineIdByOldBOMLineId.get(getMDCandidateBOMLineIdOrNull(candidate));
			if (newBOMLineId == null)
			{
				continue;
			}

			final DistributionDetail distributionDetail = candidate.getBusinessCaseDetail(DistributionDetail.class)
					.orElseThrow(() -> new AdempiereException("No DistributionDetail found for " + candidate));
			candidateRepositoryWriteService.updateCandidateById(candidate.withBusinessCaseDetail(distributionDetail.toBuilder()
					.forwardPPOrderRef(PPOrderRef.withPPOrderAndBOMLineId(distributionDetail.getForwardPPOrderRef(), PPOrderAndBOMLineId.of(ppOrderId, newBOMLineId)))
					.build()));
		}
	}

	private void moveDDOrderCandidates(@NonNull final PPOrderId ppOrderId, @NonNull final Collection<Move> moves)
	{
		final Map<PPOrderBOMLineId, PPOrderBOMLineId> newBOMLineIdByOldBOMLineId = toNewBOMLineIdByOldBOMLineId(moves);
		final Set<DDOrderCandidateId> expectedIds = moves.stream()
				.flatMap(move -> move.getOldBOMLine().getDdOrderCandidateIds().stream())
				.collect(ImmutableSet.toImmutableSet());
		if (expectedIds.isEmpty())
		{
			return;
		}

		final Set<DDOrderCandidateId> existingIds = ddOrderCandidateRepository.getByIds(expectedIds)
				.stream()
				.map(DDOrderCandidate::getIdNotNull)
				.collect(ImmutableSet.toImmutableSet());
		final Set<DDOrderCandidateId> missingIds = Sets.difference(expectedIds, existingIds);
		if (!missingIds.isEmpty())
		{
			throw new AdempiereException("DD_Order_Candidates " + missingIds + " of the old BOM lines do not exist anymore");
		}

		// loads the current records and changes only the PP_Order reference
		ddOrderCandidateRepository.updateByQuery(
				DDOrderCandidateQuery.builder().ppOrderId(ppOrderId).build(),
				candidate -> {
					final PPOrderBOMLineId newBOMLineId = newBOMLineIdByOldBOMLineId.get(getDDOrderCandidateBOMLineIdOrNull(candidate));
					return newBOMLineId == null
							? candidate
							: candidate.toBuilder()
							.forwardPPOrderRef(PPOrderRef.withPPOrderAndBOMLineId(candidate.getForwardPPOrderRef(), PPOrderAndBOMLineId.of(ppOrderId, newBOMLineId)))
							.build();
				});
	}

	private void moveDDOrders(@NonNull final OldBOMLine oldBOMLine, @NonNull final PPOrderAndBOMLineId newPPOrderAndBOMLineId)
	{
		final int updatedDDOrders = ddOrderLowLevelDAO.updateForwardPPOrderBOMLineByIds(oldBOMLine.getDdOrderIds(), newPPOrderAndBOMLineId.getLineId());
		if (updatedDDOrders != oldBOMLine.getDdOrderIds().size())
		{
			throw new AdempiereException("Not all DD_Orders " + oldBOMLine.getDdOrderIds() + " of the old BOM line " + oldBOMLine.getBomLineId() + " exist anymore; cannot move them to the BOM line " + newPPOrderAndBOMLineId.getLineId());
		}
	}

	/**
	 * The BOM line has no successor (component removed from the BOM): same as before, unprocessed DD_Order_Candidates are deleted, a processed one rejects the change.
	 */
	private void deleteDDOrderCandidates(@NonNull final OldBOMLine oldBOMLine)
	{
		for (final DDOrderCandidateId ddOrderCandidateId : oldBOMLine.getDdOrderCandidateIds())
		{
			try
			{
				ddOrderCandidateRepository.delete(DDOrderCandidateQuery.builder().ddOrderCandidateId(ddOrderCandidateId).build());
			}
			catch (final AdempiereException e)
			{
				throw new AdempiereException(DD_ORDER_CANDIDATE_ALREADY_PROCESSED_CHANGES_NOT_ALLOWED, e);
			}
		}
	}

	@Nullable
	private static PPOrderBOMLineId getMDCandidateBOMLineIdOrNull(@NonNull final Candidate candidate)
	{
		return candidate.getBusinessCaseDetail(DistributionDetail.class)
				.map(DistributionDetail::getForwardPPOrderRef)
				.map(PPOrderRef::getPpOrderBOMLineId)
				.orElse(null);
	}

	@Nullable
	private static PPOrderBOMLineId getDDOrderCandidateBOMLineIdOrNull(@NonNull final DDOrderCandidate candidate)
	{
		final PPOrderRef ppOrderRef = candidate.getForwardPPOrderRef();
		return ppOrderRef != null ? ppOrderRef.getPpOrderBOMLineId() : null;
	}
}
