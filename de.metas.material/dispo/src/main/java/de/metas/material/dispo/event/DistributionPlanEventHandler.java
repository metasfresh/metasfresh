package de.metas.material.dispo.event;

import org.springframework.stereotype.Service;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.Candidate.SubType;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidateChangeHandler;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.DemandCandidateDetail;
import de.metas.material.dispo.event.SupplyProposalEvaluator.SupplyProposal;
import de.metas.material.event.DistributionPlanEvent;
import de.metas.material.event.MaterialDescriptor;
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
@Service
public class DistributionPlanEventHandler
{
	private final CandidateRepository candidateRepository;
	private final SupplyProposalEvaluator supplyProposalEvaluator;
	private CandidateChangeHandler candidateChangeHandler;

	public DistributionPlanEventHandler(
			@NonNull final CandidateRepository candidateRepository,
			@NonNull final CandidateChangeHandler candidateChangeHandler,
			@NonNull final SupplyProposalEvaluator supplyProposalEvaluator)
	{
		this.candidateChangeHandler = candidateChangeHandler;
		this.candidateRepository = candidateRepository;
		this.supplyProposalEvaluator = supplyProposalEvaluator;
	}

	void handleDistributionPlanEvent(final DistributionPlanEvent event)
	{
		final MaterialDescriptor materialDescr = event.getMaterialDescr();

		final SupplyProposal proposal = SupplyProposal.builder()
				.date(event.getDistributionStart())
				.productId(materialDescr.getProductId())
				.destWarehouseId(materialDescr.getWarehouseId())
				.sourceWarehouseId(event.getFromWarehouseId())
				.build();
		if (!supplyProposalEvaluator.evaluateSupply(proposal))
		{
			// 'supplyProposalEvaluator' told us to ignore the given supply candidate.
			// the reason for this could be that it found an already existing distribution plan pointing in the other direction.
			// so instead of playing an infinite game of ping-ping with the material-planning component, it ignored the given 'event'
			// and leave it to the user to come up with a great idea.
			return;
		}

		final Candidate supplyCandidate = Candidate.builder()
				.type(Type.SUPPLY)
				.subType(SubType.DISTRIBUTION)
				.date(materialDescr.getDate())
				.orgId(materialDescr.getOrgId())
				.productId(materialDescr.getProductId())
				.quantity(materialDescr.getQty())
				.warehouseId(materialDescr.getWarehouseId())
				.reference(event.getReference())
				.demandDetail(DemandCandidateDetail.builder()
						.orderLineId(event.getOrderLineId())
						.build())
				.build();

		final Candidate supplyCandidateWithId = candidateChangeHandler.onSupplyCandidateNewOrChange(supplyCandidate);
		if (supplyCandidateWithId.getQuantity().signum() == 0)
		{
			// nothing was added as supply in the destination warehouse, so there is no demand to register either
			return;
		}

		// we expect the demand candidate to go with the supplyCandidates SeqNo + 1,
		// *but* it might also be the case that the demandCandidate attaches to an existing stock and in that case would need to get another SeqNo
		final int expectedSeqNoForDemandCandidate = supplyCandidateWithId.getSeqNo() + 1;

		final Candidate demandCandidate = supplyCandidate
				.withType(Type.DEMAND)
				.withSubType(SubType.DISTRIBUTION)
				.withGroupId(supplyCandidateWithId.getGroupId())
				.withParentId(supplyCandidateWithId.getId())
				.withQuantity(supplyCandidateWithId.getQuantity()) // what was added as supply in the destination warehouse needs to be registered as demand in the source warehouse
				.withDate(event.getDistributionStart())
				.withSeqNo(expectedSeqNoForDemandCandidate)
				.withWarehouseId(event.getFromWarehouseId());

		// this might cause 'candidateChangeHandler' to trigger another event
		final Candidate demandCandidateWithId = candidateChangeHandler.onDemandCandidateNewOrChange(demandCandidate);
		if (expectedSeqNoForDemandCandidate == demandCandidateWithId.getSeqNo())
		{
			return; // we are done
		}

		// update/override the SeqNo of both supplyCandidate and supplyCandidate's stock candidate.
		candidateRepository.addOrUpdate(supplyCandidateWithId
				.withSeqNo(demandCandidateWithId.getSeqNo() - 1),
				false);

		candidateRepository.addOrUpdate(candidateRepository
				.retrieve(supplyCandidateWithId.getParentId())
				.withSeqNo(demandCandidateWithId.getSeqNo() - 2),
				false);
	}
}
