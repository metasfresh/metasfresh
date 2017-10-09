package de.metas.material.dispo.service.event;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.CandidatesSegment;
import de.metas.material.dispo.CandidatesSegment.DateOperator;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

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
	private final CandidateRepository candidateRepository;

	public SupplyProposalEvaluator(@NonNull final CandidateRepository candidateRepository)
	{
		this.candidateRepository = candidateRepository;
	}

	/**
	 * For the given {@code proposal}, look for existing demand records which match the proposal's <b>destination</b> and are linked (directly or indirectly, via parent-references) to any supply record matching the proposal's <b>source</b>.
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
	 *
	 * @param proposal
	 * @return
	 */
	public boolean evaluateSupply(@NonNull final SupplyProposal proposal)
	{
		final CandidatesSegment demandSegment = CandidatesSegment.builder()
				.type(Type.DEMAND)
				.date(proposal.getDate())
				.dateOperator(DateOperator.from)
				.productId(proposal.getProductId())
				.warehouseId(proposal.getDestWarehouseId())
				.build();

		final CandidatesSegment directReverseSegment = demandSegment
				.withParentProductId(proposal.getProductId())
				.withParentWarehouseId(proposal.getSourceWarehouseId());

		final List<Candidate> directReversals = candidateRepository.retrieveMatchesOrderByDateAndSeqNo(directReverseSegment);
		if (!directReversals.isEmpty())
		{
			return false;
		}

		final CandidatesSegment supplySegment = demandSegment
				.withType(Type.SUPPLY)
				.withDate(proposal.getDate())
				.withDateOperator(DateOperator.from)
				.withWarehouseId(proposal.getSourceWarehouseId());

		final List<Candidate> demands = candidateRepository.retrieveMatchesOrderByDateAndSeqNo(demandSegment);
		for (final Candidate demand : demands)
		{
			final Candidate indirectSupplyCandidate = searchRecursive(
					demand,
					supplySegment,
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
			@NonNull final CandidatesSegment searchTarget,
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

		if (currentCandidate.getParentIdNotNull() > 0)
		{
			final Candidate foundSearchTarget = searchRecursive(
					candidateRepository.retrieve(currentCandidate.getParentId()),
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
	@Data
	@Builder
	public static class SupplyProposal
	{
		@NonNull
		private final Integer productId;

		@NonNull
		private final Integer sourceWarehouseId;

		@NonNull
		private final Integer destWarehouseId;

		@NonNull
		private final Date date;
	}
}
