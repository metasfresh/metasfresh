package de.metas.material.dispo.service.event.handler;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.Candidate.Status;
import de.metas.material.dispo.Candidate.SubType;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.CandidateRepository;
import de.metas.material.dispo.CandidateService;
import de.metas.material.dispo.DemandCandidateDetail;
import de.metas.material.dispo.DistributionCandidateDetail;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.event.EventUtil;
import de.metas.material.dispo.service.event.SupplyProposalEvaluator;
import de.metas.material.dispo.service.event.SupplyProposalEvaluator.SupplyProposal;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.ddorder.DistributionPlanEvent;
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
	private final CandidateChangeService candidateChangeHandler;
	private final CandidateService candidateService;

	/**
	 * 
	 * @param candidateRepository
	 * @param candidateChangeHandler
	 * @param supplyProposalEvaluator
	 */
	public DistributionPlanEventHandler(
			@NonNull final CandidateRepository candidateRepository,
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final SupplyProposalEvaluator supplyProposalEvaluator,
			@NonNull final CandidateService candidateService)
	{
		this.candidateService = candidateService;
		this.candidateChangeHandler = candidateChangeHandler;
		this.candidateRepository = candidateRepository;
		this.supplyProposalEvaluator = supplyProposalEvaluator;
	}

	public void handleDistributionPlanEvent(final DistributionPlanEvent event)
	{
		final DDOrder ddOrder = event.getDdOrder();
		final Candidate.Status candidateStatus;
		if (ddOrder.getDdOrderId() <= 0)
		{
			candidateStatus = Status.doc_planned;
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
					.productId(ddOrderLine.getProductId())
					.destWarehouseId(event.getToWarehouseId())
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

			final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
					.date(ddOrder.getDatePromised())
					.productId(ddOrderLine.getProductId())
					.quantity(ddOrderLine.getQty())
					.warehouseId(event.getToWarehouseId())
					.build();

			final Candidate supplyCandidate = Candidate.builderForEventDescr(event.getEventDescr())
					.type(Type.SUPPLY)
					.status(candidateStatus)
					.subType(SubType.DISTRIBUTION)
					.materialDescr(materialDescriptor)
					.reference(event.getReference())
					.demandDetail(DemandCandidateDetail.forOrderLineId(ddOrderLine.getSalesOrderLineId()))
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
					.withType(Type.DEMAND)
					.withGroupId(groupId)
					.withParentId(supplyCandidateWithId.getId())
					.withQuantity(supplyCandidateWithId.getQuantity()) // what was added as supply in the destination warehouse needs to be registered as demand in the source warehouse
					.withDate(orderLineStartDate)
					.withWarehouseId(event.getFromWarehouseId())
					.withSeqNo(expectedSeqNoForDemandCandidate);

			// this might cause 'candidateChangeHandler' to trigger another event
			final Candidate demandCandidateWithId = candidateChangeHandler.onCandidateNewOrChange(demandCandidate);

			if (expectedSeqNoForDemandCandidate != demandCandidateWithId.getSeqNo())
			{
				// update/override the SeqNo of both supplyCandidate and supplyCandidate's stock candidate.
				candidateRepository.addOrUpdateOverwriteStoredSeqNo(supplyCandidateWithId
						.withSeqNo(demandCandidateWithId.getSeqNo() - 1));

				candidateRepository.addOrUpdateOverwriteStoredSeqNo(candidateRepository
						.retrieve(supplyCandidateWithId.getParentId())
						.withSeqNo(demandCandidateWithId.getSeqNo() - 2));
			}

			if (ddOrder.isCreateDDrder() && groupIdsWithRequestedPPOrders.add(groupId))
			{
				candidateService.requestMaterialOrder(groupId);
			}
		}
	}

	private DistributionCandidateDetail createCandidateDetailFromDDOrderAndLine(
			@NonNull final DDOrder ddOrder,
			@NonNull final DDOrderLine ddOrderLine)
	{
		return DistributionCandidateDetail.builder()
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
