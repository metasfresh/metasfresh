package de.metas.material.dispo.service.event;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.MaterialDescriptorQuery.DateOperator;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.event.commons.ProductDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-dispo
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

/**
 * This class has the job to figure out if a particular supply "proposal" creates a kind of circle.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
public class SupplyProposalEvaluator
{
	/**
	 * Needed so the evaluator can check what's already there.
	 */
	private final CandidateRepositoryRetrieval candidateRepository;

	public SupplyProposalEvaluator(@NonNull final CandidateRepositoryRetrieval candidateRepository)
	{
		this.candidateRepository = candidateRepository;
	}

	/**
	 * For the given {@code proposal}, look for existing demand records which match the proposal's <b>destination</b>
	 * and are linked (directly or indirectly, via parent-references) to any supply record matching the proposal's <b>source</b>.
	 * <p>
	 * Background:
	 * <ul>
	 * <li>A {@link SupplyProposal} proposes to move stuff <b>from a source warehouse to a destination warehouse</b>.</li>
	 * <li>A demand record which is linked (directly or indirectly, via parent references) to a supply record means that the system already plans to move stuff from the demand's warehouse to the supply's warehouse (yes, it is this was around!)</li>
	 * <li>If the demand record is at the {@link SupplyProposal}'s source and the supply record is at the {@link SupplyProposal}'s destination, then this means that<br>
	 * we already plan to move things from the demand record's WH to the supply record's WH, but now the proposal is coming and wants to include into that existing plan a movement in the opposite direction.<br>
	 * That makes no sense and this method shall therefore return {@code false} on such a folly.
	 * </li>
	 * </ul>
	 */
	public boolean isProposalAccepted(@NonNull final SupplyProposal proposal)
	{
		final ProductDescriptor productDescriptor = proposal.getProductDescriptor();

		final CandidatesQuery demandQuery = CandidatesQuery.builder()
				.type(CandidateType.DEMAND)
				.materialDescriptorQuery(MaterialDescriptorQuery.builder()
						.date(proposal.getDate())
						.dateOperator(DateOperator.AT_OR_AFTER)
						.productId(productDescriptor.getProductId())
						.storageAttributesKey(productDescriptor.getStorageAttributesKey())
						.warehouseId(proposal.getDestWarehouseId()).build())
				.build();

		final MaterialDescriptorQuery sourceMaterialDescriptorQuery = MaterialDescriptorQuery.builder()
				.productId(productDescriptor.getProductId())
				.storageAttributesKey(productDescriptor.getStorageAttributesKey())
				.warehouseId(proposal.getSourceWarehouseId())
				.build();

		final CandidatesQuery directReverseForDemandQuery = demandQuery
				.withParentMaterialDescriptorQuery(sourceMaterialDescriptorQuery);

		final List<Candidate> directReversals = candidateRepository.retrieveOrderedByDateAndSeqNo(directReverseForDemandQuery);
		if (!directReversals.isEmpty())
		{
			return false;
		}

		final MaterialDescriptorQuery supplyMaterialDescriptorQuery = MaterialDescriptorQuery.builder()
				.productId(productDescriptor.getProductId())
				.storageAttributesKey(productDescriptor.getStorageAttributesKey())
				.warehouseId(proposal.getSourceWarehouseId())
				.date(proposal.getDate())
				.dateOperator(DateOperator.AT_OR_AFTER)
				.build();

		final CandidatesQuery supplyQuery = demandQuery
				.withType(CandidateType.SUPPLY)
				.withMaterialDescriptorQuery(supplyMaterialDescriptorQuery);

		final List<Candidate> demands = candidateRepository.retrieveOrderedByDateAndSeqNo(demandQuery);
		for (final Candidate demand : demands)
		{
			final Candidate indirectSupplyCandidate = searchRecursive(
					demand,
					supplyQuery,
					new HashSet<>());

			if (indirectSupplyCandidate != null)
			{
				return false;
			}
		}
		return true;
	}

	private Candidate searchRecursive(
			@NonNull final Candidate currentCandidate,
			@NonNull final CandidatesQuery searchTarget,
			@NonNull final Set<Candidate> alreadySeen)
	{
		if (!alreadySeen.add(currentCandidate))
		{
			return null;
		}

		if (searchTarget.matches(currentCandidate))
		{
			return currentCandidate;
		}

		if (currentCandidate.getParentId() > 0)
		{
			final Candidate foundSearchTarget = searchRecursive(
					candidateRepository.retrieveLatestMatchOrNull(CandidatesQuery.fromId(currentCandidate.getParentId())),
					searchTarget,
					alreadySeen);
			if (foundSearchTarget != null)
			{
				return foundSearchTarget;
			}
		}
		else /* the "else" is important to avoid a stack overflow error */
		if (currentCandidate.getEffectiveGroupId() > 0)
		{
			final List<Candidate> group = candidateRepository.retrieveGroup(currentCandidate.getEffectiveGroupId());
			for (final Candidate groupMember : group)
			{
				if (groupMember.getId() <= currentCandidate.getId())
				{
					continue; // avoid a stack overflow error
				}

				final Candidate foundSearchTarget = searchRecursive(
						groupMember,
						searchTarget,
						alreadySeen);

				if (foundSearchTarget != null)
				{
					return foundSearchTarget;
				}
			}
		}

		return null;

	}

	/**
	 * This class defines how the evaluator wants to receive it's proposals.
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	@Value
	@Builder
	public static class SupplyProposal
	{
		@NonNull
		ProductDescriptor productDescriptor;

		@NonNull
		Integer sourceWarehouseId;

		@NonNull
		Integer destWarehouseId;

		@NonNull
		Date date;
	}
}
