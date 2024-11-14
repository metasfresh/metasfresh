package de.metas.material.dispo.service.event;

import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.DateAndSeqNo;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DemandDetailsQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.util.Loggables;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.warehouse.WarehouseId;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.Instant;

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
@Profile(Profiles.PROFILE_MaterialDispo)
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

	public boolean isProposalAccepted(@NonNull final SupplyProposal proposal)
	{
		final DemandDetailsQuery demandDetailsQuery = DemandDetailsQuery.ofDemandDetailOrNull(proposal.getDemandDetail());
		
		final CandidatesQuery proposedDemandExistsQuery = CandidatesQuery
				.builder()
				.type(CandidateType.DEMAND)
				.demandDetailsQuery(demandDetailsQuery)
				.materialDescriptorQuery(MaterialDescriptorQuery.builder()
												 .atTime(DateAndSeqNo.atTimeNoSeqNo(proposal.getDate()))
												 .productId(proposal.getProductId())
												 .warehouseId(proposal.getSupplyWarehouseId())
												 .build())
				.build();
		final Candidate existingDemandCandidate = candidateRepository.retrieveLatestMatchOrNull(proposedDemandExistsQuery);
		if (existingDemandCandidate == null)
		{
			return true;
		}

		final CandidatesQuery proposedSupplyExistsQuery = CandidatesQuery
				.builder()
				.type(CandidateType.SUPPLY)
				.demandDetailsQuery(demandDetailsQuery)
				.materialDescriptorQuery(MaterialDescriptorQuery.builder()
												 .atTime(DateAndSeqNo.atTimeNoSeqNo(proposal.getDate()))
												 .productId(proposal.getProductId())
												 .warehouseId(proposal.getDemandWarehouseId())
												 .build())
				.build();
		final Candidate existingsupplyCandidate = candidateRepository.retrieveLatestMatchOrNull(proposedSupplyExistsQuery);
		if (existingsupplyCandidate == null)
		{
			return true;
		}

		Loggables.addLog(
				"The given proposal would repeat a step that is already planned; rejecting it; proposal={}; existing candidates: source={}; destination={}",
				proposal, existingDemandCandidate, existingsupplyCandidate);
		return false;
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
		/** The demand this proposal is actually about. Important to figure out if the proposal would close a loop. */
		@NonNull
		DemandDetail demandDetail;

		@NonNull
		WarehouseId supplyWarehouseId;

		/**
		 * The source warehouse; this instance proposes to take something for this warehouse i.e. create a new demand in this warehouse.
		 */
		@NonNull
		WarehouseId demandWarehouseId;

		int productId;

		@NonNull
		Instant date;
	}
}
