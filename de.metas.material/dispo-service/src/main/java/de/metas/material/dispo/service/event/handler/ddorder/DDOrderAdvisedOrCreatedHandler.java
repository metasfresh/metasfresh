package de.metas.material.dispo.service.event.handler.ddorder;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.compiere.util.TimeUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.material.dispo.commons.RequestMaterialOrderService;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateStatus;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.DemandDetail;
import de.metas.material.dispo.commons.candidate.DistributionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.event.EventUtil;
import de.metas.material.dispo.service.event.SupplyProposalEvaluator;
import de.metas.material.dispo.service.event.SupplyProposalEvaluator.SupplyProposal;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderAdvisedOrCreatedEvent;
import de.metas.material.event.ddorder.DDOrderLine;
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
@Profile(Profiles.PROFILE_MaterialDispo)
public class DDOrderAdvisedOrCreatedHandler
		implements MaterialEventHandler<DDOrderAdvisedOrCreatedEvent>
{
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	private final CandidateRepositoryWriteService candidateRepositoryWrite;
	private final SupplyProposalEvaluator supplyProposalEvaluator;
	private final CandidateChangeService candidateChangeHandler;
	private final RequestMaterialOrderService requestMaterialOrderService;

	public DDOrderAdvisedOrCreatedHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepository,
			@NonNull final CandidateRepositoryWriteService candidateRepositoryCommands,
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final SupplyProposalEvaluator supplyProposalEvaluator,
			@NonNull final RequestMaterialOrderService candidateService)
	{
		this.requestMaterialOrderService = candidateService;
		this.candidateChangeHandler = candidateChangeHandler;
		this.candidateRepositoryRetrieval = candidateRepository;
		this.candidateRepositoryWrite = candidateRepositoryCommands;
		this.supplyProposalEvaluator = supplyProposalEvaluator;
	}

	@Override
	public Collection<Class<? extends DDOrderAdvisedOrCreatedEvent>> getHandeledEventType()
	{
		return ImmutableList.of(DDOrderAdvisedOrCreatedEvent.class);
	}

	@Override
	public void handleEvent(final DDOrderAdvisedOrCreatedEvent event)
	{
		final DDOrder ddOrder = event.getDdOrder();

		final CandidateStatus candidateStatus = computeCandidateStatus(ddOrder);

		final Set<Integer> groupIdsWithRequestedPPOrders = new HashSet<>();

		for (final DDOrderLine ddOrderLine : ddOrder.getLines())
		{
			final Timestamp orderLineStartDate = TimeUtil.addDaysExact(ddOrder.getDatePromised(), ddOrderLine.getDurationDays() * -1);

			final SupplyProposal proposal = SupplyProposal.builder()
					.date(orderLineStartDate)
					.productDescriptor(ddOrderLine.getProductDescriptor())
					// ignoring bpartner for now..not sure if that's good or not..
					.destWarehouseId(event.getToWarehouseId())
					.sourceWarehouseId(event.getFromWarehouseId())
					.build();
			if (!supplyProposalEvaluator.isProposalAccepted(proposal))
			{
				return;
			}

			final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
					.date(ddOrder.getDatePromised())
					.productDescriptor(ddOrderLine.getProductDescriptor())
					.bPartnerId(ddOrderLine.getBPartnerId())
					.quantity(ddOrderLine.getQty())
					.warehouseId(event.getToWarehouseId())
					.build();

			final DemandDetail demandDetailOrNull = DemandDetail.createOrNull(event.getSupplyRequiredDescriptor());

			final Candidate supplyCandidate = Candidate.builderForEventDescr(event.getEventDescriptor())
					.type(CandidateType.SUPPLY)
					.businessCase(CandidateBusinessCase.DISTRIBUTION)
					.groupId(event.getGroupId())
					.status(candidateStatus)
					.materialDescriptor(materialDescriptor)
					.demandDetail(demandDetailOrNull)
					.distributionDetail(createCandidateDetailFromDDOrderAndLine(ddOrder, ddOrderLine))
					.build();

			final Candidate supplyCandidateWithId = candidateChangeHandler.onCandidateNewOrChange(supplyCandidate);
			if (supplyCandidateWithId.getQuantity().signum() == 0)
			{
				return; // nothing was added as supply in the destination warehouse, so there is no demand to register either
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
					.withWarehouseId(event.getFromWarehouseId())
					.withSeqNo(expectedSeqNoForDemandCandidate);

			// this might cause 'candidateChangeHandler' to trigger another event
			final Candidate demandCandidateWithId = candidateChangeHandler.onCandidateNewOrChange(demandCandidate);

			final int seqNoOfDemand = demandCandidateWithId.getSeqNo();
			if (expectedSeqNoForDemandCandidate != seqNoOfDemand)
			{
				// update/override the SeqNo of both supplyCandidate and supplyCandidate's stock candidate.
				candidateRepositoryWrite.updateCandidate(supplyCandidateWithId.withSeqNo(seqNoOfDemand - 1));

				final Candidate parentOfSupplyCandidate = candidateRepositoryRetrieval
						.retrieveLatestMatchOrNull(CandidatesQuery.fromId(supplyCandidateWithId.getParentId()));
				candidateRepositoryWrite.updateCandidate(parentOfSupplyCandidate.withSeqNo(seqNoOfDemand - 2));
			}

			if (event.isAdvisedToCreateDDrder())
			{
				final boolean ddOrderCreationNotYetRequested = groupIdsWithRequestedPPOrders.add(groupId);
				if (ddOrderCreationNotYetRequested)
				{
					requestMaterialOrderService.requestMaterialOrder(groupId);
				}
			}
		}
	}

	private CandidateStatus computeCandidateStatus(final DDOrder ddOrder)
	{
		final CandidateStatus candidateStatus;
		if (ddOrder.getDdOrderId() <= 0)
		{
			candidateStatus = CandidateStatus.doc_planned;
		}
		else
		{
			candidateStatus = EventUtil.getCandidateStatus(ddOrder.getDocStatus());
		}
		return candidateStatus;
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
