package de.metas.material.dispo.service.event.handler;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import de.metas.material.dispo.commons.CandidateService;
import de.metas.material.dispo.commons.CandidatesQuery;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateStatus;
import de.metas.material.dispo.commons.candidate.CandidateSubType;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.DemandDetail;
import de.metas.material.dispo.commons.candidate.DistributionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryCommands;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.event.EventUtil;
import de.metas.material.dispo.service.event.SupplyProposalEvaluator;
import de.metas.material.dispo.service.event.SupplyProposalEvaluator.SupplyProposal;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.ddorder.DistributionAdvisedEvent;
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
public class DistributionAdvisedHandler
{
	private final CandidateRepositoryRetrieval candidateRepository;
	private final CandidateRepositoryCommands candidateRepositoryCommands;
	private final SupplyProposalEvaluator supplyProposalEvaluator;
	private final CandidateChangeService candidateChangeHandler;
	private final CandidateService candidateService;

	public DistributionAdvisedHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepository,
			@NonNull final CandidateRepositoryCommands candidateRepositoryCommands,
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final SupplyProposalEvaluator supplyProposalEvaluator,
			@NonNull final CandidateService candidateService)
	{
		this.candidateService = candidateService;
		this.candidateChangeHandler = candidateChangeHandler;
		this.candidateRepository = candidateRepository;
		this.candidateRepositoryCommands = candidateRepositoryCommands;
		this.supplyProposalEvaluator = supplyProposalEvaluator;
	}

	public void handleDistributionAdvisedEvent(final DistributionAdvisedEvent distributionPlanEvent)
	{
		final DDOrder ddOrder = distributionPlanEvent.getDdOrder();
		final CandidateStatus candidateStatus;
		if (ddOrder.getDdOrderId() <= 0)
		{
			candidateStatus = CandidateStatus.doc_planned;
		}
		else
		{
			candidateStatus = EventUtil.getCandidateStatus(ddOrder.getDocStatus());
		}

		final Set<Integer> groupIdsWithRequestedPPOrders = new HashSet<>();

		for (final DDOrderLine ddOrderLine : ddOrder.getLines())
		{
			final Timestamp orderLineStartDate = TimeUtil.addDaysExact(ddOrder.getDatePromised(), ddOrderLine.getDurationDays() * -1);

			final SupplyProposal proposal = SupplyProposal.builder()
					.date(orderLineStartDate)
					.productDescriptor(ddOrderLine.getProductDescriptor())
					.destWarehouseId(distributionPlanEvent.getToWarehouseId())
					.sourceWarehouseId(distributionPlanEvent.getFromWarehouseId())
					.build();
			if (!supplyProposalEvaluator.evaluateSupply(proposal))
			{
				// 'supplyProposalEvaluator' told us to ignore the given supply candidate.
				// the reason for this could be that it found an already existing distribution plan pointing in the other direction.
				// so instead of playing an infinite game of ping-ping with the material-planning component, it ignored the given 'event'
				// and leave it to the user to come up with a great idea.
				return;
			}

			final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
					.complete(true)
					.date(ddOrder.getDatePromised())
					.productDescriptor(ddOrderLine.getProductDescriptor())
					.quantity(ddOrderLine.getQty())
					.warehouseId(distributionPlanEvent.getToWarehouseId())
					.build();

			final Candidate supplyCandidate = Candidate.builderForEventDescr(distributionPlanEvent.getEventDescriptor())
					.type(CandidateType.SUPPLY)
					.status(candidateStatus)
					.subType(CandidateSubType.DISTRIBUTION)
					.materialDescriptor(materialDescriptor)
					.demandDetail(DemandDetail.forOrderLineIdOrNull(ddOrderLine.getSalesOrderLineId()))
					.distributionDetail(createCandidateDetailFromDDOrderAndLine(ddOrder, ddOrderLine))
					.build();

			final Candidate supplyCandidateWithId = candidateChangeHandler.onCandidateNewOrChange(supplyCandidate);
			if (supplyCandidateWithId.getQuantity().signum() == 0)
			{
				// nothing was added as supply in the destination warehouse, so there is no demand to register either
				return;
			}

			// we expect the demand candidate to go with the supplyCandidates SeqNo + 1,
			// *but* it might also be the case that the demandCandidate attaches to an existing stock and in that case would need to get another SeqNo
			final int expectedSeqNoForDemandCandidate = supplyCandidateWithId.getSeqNo() + 1;

			final Integer groupId = supplyCandidateWithId.getGroupId();

			final Candidate demandCandidate = supplyCandidate
					.withType(CandidateType.DEMAND)
					.withGroupId(groupId)
					.withParentId(supplyCandidateWithId.getId())
					.withQuantity(supplyCandidateWithId.getQuantity()) // what was added as supply in the destination warehouse needs to be registered as demand in the source warehouse
					.withDate(orderLineStartDate)
					.withWarehouseId(distributionPlanEvent.getFromWarehouseId())
					.withSeqNo(expectedSeqNoForDemandCandidate);

			// this might cause 'candidateChangeHandler' to trigger another event
			final Candidate demandCandidateWithId = candidateChangeHandler.onCandidateNewOrChange(demandCandidate);

			if (expectedSeqNoForDemandCandidate != demandCandidateWithId.getSeqNo())
			{
				// update/override the SeqNo of both supplyCandidate and supplyCandidate's stock candidate.
				candidateRepositoryCommands
						.addOrUpdateOverwriteStoredSeqNo(supplyCandidateWithId
								.withSeqNo(demandCandidateWithId.getSeqNo() - 1));

				final Candidate parentOfSupplyCandidate = candidateRepository
						.retrieveLatestMatchOrNull(CandidatesQuery.fromId(supplyCandidateWithId.getParentId()));

				candidateRepositoryCommands
						.addOrUpdateOverwriteStoredSeqNo(
								parentOfSupplyCandidate
										.withSeqNo(demandCandidateWithId.getSeqNo() - 2));
			}

			if (ddOrder.isAdvisedToCreateDDrder() && groupIdsWithRequestedPPOrders.add(groupId))
			{
				candidateService.requestMaterialOrder(groupId);
			}
		}
	}

	private DistributionDetail createCandidateDetailFromDDOrderAndLine(
			@NonNull final DDOrder ddOrder,
			@NonNull final DDOrderLine ddOrderLine)
	{
		return DistributionDetail.builder()
				.ddOrderDocStatus(ddOrder.getDocStatus())
				.ddOrderId(ddOrder.getDdOrderId())
				.ddOrderLineId(ddOrderLine.getDdOrderLineId())
				.networkDistributionLineId(ddOrderLine.getNetworkDistributionLineId())
				.plantId(ddOrder.getPlantId())
				.productPlanningId(ddOrder.getProductPlanningId())
				.shipperId(ddOrder.getShipperId())
				.build();
	}
}
