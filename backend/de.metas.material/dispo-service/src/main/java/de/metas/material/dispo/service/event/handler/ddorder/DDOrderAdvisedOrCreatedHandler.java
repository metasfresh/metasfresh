package de.metas.material.dispo.service.event.handler.ddorder;

import de.metas.material.cockpit.view.ddorderdetail.DDOrderDetailRequestHandler;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.MaterialDescriptor.MaterialDescriptorBuilder;
import de.metas.material.event.ddorder.AbstractDDOrderEvent;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderCreatedEvent;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;

import java.time.Instant;
import java.time.ZoneId;

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

@RequiredArgsConstructor
public abstract class DDOrderAdvisedOrCreatedHandler<T extends AbstractDDOrderEvent>
		implements MaterialEventHandler<T>
{
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	@NonNull private final CandidateRepositoryWriteService candidateRepositoryWrite;
	@NonNull private final CandidateChangeService candidateChangeHandler;
	@NonNull private final DDOrderDetailRequestHandler ddOrderDetailRequestHandler;
	@NonNull private final MainDataRequestHandler mainDataRequestHandler;

	@NonNull
	public static WarehouseId computeWarehouseId(
			@NonNull final AbstractDDOrderEvent ddOrderEvent,
			@NonNull final CandidateType candidateType)
	{
		switch (candidateType)
		{
			case DEMAND:
				return ddOrderEvent.getFromWarehouseId();
			case SUPPLY:
				return ddOrderEvent.getToWarehouseId();
			default:
				throw new AdempiereException("Unexpected candidateType").appendParametersToMessage()
						.setParameter("candidateType", candidateType)
						.setParameter("abstractDDOrderEvent", ddOrderEvent);
		}
	}

	@NonNull
	public static Instant computeDate(
			@NonNull final AbstractDDOrderEvent ddOrderEvent,
			@NonNull final DDOrderLine ddOrderLine,
			@NonNull final CandidateType candidateType)
	{

		switch (candidateType)
		{
			case DEMAND:
				return ddOrderLine.getDemandDate();
			case SUPPLY:
				return ddOrderEvent.getDdOrder().getSupplyDate();
			default:
				throw new AdempiereException("Unexpected candidateType").appendParametersToMessage()
						.setParameter("candidateType", candidateType)
						.setParameter("abstractDDOrderEvent", ddOrderEvent);
		}
	}

	/**
	 *
	 */
	protected final void createAndProcessCandidates(@NonNull final AbstractDDOrderEvent ddOrderEvent)
	{
		for (final DDOrderLine ddOrderLine : ddOrderEvent.getDdOrder().getLines())
		{
			createAndProcessCandidatePair(ddOrderEvent, ddOrderLine);
		}
	}

	private void createAndProcessCandidatePair(
			final AbstractDDOrderEvent ddOrderEvent,
			final DDOrderLine ddOrderLine)
	{
		final DDOrder ddOrder = ddOrderEvent.getDdOrder();

		//
		// create the supply candidate
		final MaterialDescriptor supplyMaterialDescriptor = createSupplyMaterialDescriptor(ddOrderEvent, ddOrderLine);

		// these two will also be added to the demand candidate
		final DemandDetail demanddetail = //
				DemandDetail.forSupplyRequiredDescriptorOrNull(ddOrderEvent.getSupplyRequiredDescriptor());

		final DistributionDetail distributionDetail = createCandidateDetailFromDDOrderAndLine(ddOrder, ddOrderLine);

		// create or update the supply candidate
		final Candidate supplyCandidate = createSupplyCandidateBuilder(ddOrderEvent, ddOrderLine)
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.DISTRIBUTION)
				// .status(candidateStatus)
				.materialDescriptor(supplyMaterialDescriptor)
				.businessCaseDetail(distributionDetail)
				.additionalDemandDetail(demanddetail)
				.simulated(ddOrder.isSimulated())
				.build();

		final Candidate supplyCandidateWithId = candidateChangeHandler.onCandidateNewOrChange(supplyCandidate).toCandidateWithQtyDelta();

		// create  or update the demand candidate

		// we expect the demand candidate to go with the supplyCandidate's SeqNo + 1,
		final int expectedSeqNoForDemandCandidate = supplyCandidateWithId.getSeqNo() + 1;

		final MaterialDispoGroupId groupId = supplyCandidateWithId.getGroupId();

		final MaterialDescriptor demandMaterialDescriptor = createDemandMaterialDescriptor(ddOrderEvent, ddOrderLine);

		final Candidate demandCandidate = createDemandCandidateBuilder(ddOrderEvent, ddOrderLine)
				.type(CandidateType.DEMAND)
				.groupId(groupId)
				.parentId(supplyCandidateWithId.getId())
				.businessCase(CandidateBusinessCase.DISTRIBUTION)
				// .status(candidateStatus)
				.materialDescriptor(demandMaterialDescriptor)
				.minMaxDescriptor(ddOrderLine.getFromWarehouseMinMaxDescriptor())
				.businessCaseDetail(distributionDetail)
				.additionalDemandDetail(demanddetail != null ? demanddetail.withTraceId(ddOrderEvent.getTraceId()) : null)
				.seqNo(expectedSeqNoForDemandCandidate)
				.simulated(ddOrder.isSimulated())
				.build();

		// this might cause 'candidateChangeHandler' to trigger another event
		final Candidate demandCandidateWithId = candidateChangeHandler.onCandidateNewOrChange(demandCandidate).toCandidateWithQtyDelta();

		final int seqNoOfDemand = demandCandidateWithId.getSeqNo();
		if (expectedSeqNoForDemandCandidate != seqNoOfDemand)
		{
			// update/override the SeqNo of both supplyCandidate and supplyCandidate's stock candidate.
			candidateRepositoryWrite.updateCandidateById(supplyCandidateWithId.withSeqNo(seqNoOfDemand - 1));

			final Candidate parentOfSupplyCandidate = candidateRepositoryRetrieval
					.retrieveLatestMatchOrNull(CandidatesQuery.fromId(supplyCandidateWithId.getParentId()));
			candidateRepositoryWrite.updateCandidateById(parentOfSupplyCandidate.withSeqNo(seqNoOfDemand - 2));
		}

		if (ddOrderEvent instanceof DDOrderCreatedEvent)
		{
			handleMainDataUpdates((DDOrderCreatedEvent)ddOrderEvent, ddOrderLine);
		}

	}

	private CandidateBuilder createDemandCandidateBuilder(final AbstractDDOrderEvent ddOrderEvent, final DDOrderLine ddOrderLine)
	{
		final CandidatesQuery preExistingDemandQuery = createPreExistingCandidatesQuery(
				ddOrderEvent,
				ddOrderLine,
				CandidateType.DEMAND);
		final Candidate existingDemandCandidateOrNull = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(preExistingDemandQuery);

		return existingDemandCandidateOrNull != null
				? existingDemandCandidateOrNull.toBuilder()
				: Candidate.builderForEventDescriptor(ddOrderEvent.getEventDescriptor());
	}

	private CandidateBuilder createSupplyCandidateBuilder(final AbstractDDOrderEvent ddOrderEvent, final DDOrderLine ddOrderLine)
	{
		final CandidatesQuery preExistingSupplyQuery = createPreExistingCandidatesQuery(
				ddOrderEvent,
				ddOrderLine,
				CandidateType.SUPPLY);
		final Candidate existingSupplyCandidateOrNull = candidateRepositoryRetrieval
				.retrieveLatestMatchOrNull(preExistingSupplyQuery);

		return existingSupplyCandidateOrNull != null
				? existingSupplyCandidateOrNull.toBuilder()
				: Candidate.builderForEventDescriptor(ddOrderEvent.getEventDescriptor());
	}

	private MaterialDescriptor createDemandMaterialDescriptor(
			@NonNull final AbstractDDOrderEvent ddOrderEvent,
			@NonNull final DDOrderLine ddOrderLine)
	{
		final MaterialDescriptorBuilder materialDescriptorBuilder = initCommonDescriptorBuilder(ddOrderLine);

		return materialDescriptorBuilder
				.date(computeDate(ddOrderEvent, ddOrderLine, CandidateType.DEMAND))
				.warehouseId(computeWarehouseId(ddOrderEvent, CandidateType.DEMAND))
				.build();
	}

	private MaterialDescriptor createSupplyMaterialDescriptor(
			@NonNull final AbstractDDOrderEvent ddOrderEvent,
			@NonNull final DDOrderLine ddOrderLine)
	{
		final MaterialDescriptorBuilder materialDescriptorBuilder = initCommonDescriptorBuilder(ddOrderLine);

		return materialDescriptorBuilder
				.date(computeDate(ddOrderEvent, ddOrderLine, CandidateType.SUPPLY))
				.warehouseId(computeWarehouseId(ddOrderEvent, CandidateType.SUPPLY))
				.build();
	}

	private MaterialDescriptorBuilder initCommonDescriptorBuilder(final DDOrderLine ddOrderLine)
	{
		return MaterialDescriptor.builder()
				.productDescriptor(ddOrderLine.getProductDescriptor())
				// .customerId(ddOrderLine.getBPartnerId()) // the ddOrder line's bpartner is not the customer, but probably the shipper
				.quantity(ddOrderLine.getQty());
	}

	protected abstract CandidatesQuery createPreExistingCandidatesQuery(
			AbstractDDOrderEvent ddOrderEvent,
			DDOrderLine ddOrderLine,
			CandidateType candidateType);

	protected abstract Flag extractIsAdviseEvent(@NonNull final AbstractDDOrderEvent ddOrderEvent);

	private DistributionDetail createCandidateDetailFromDDOrderAndLine(
			@NonNull final DDOrder ddOrder,
			@NonNull final DDOrderLine ddOrderLine)
	{
		return DistributionDetail.builder()
				.ddOrderDocStatus(ddOrder.getDocStatus())
				.ddOrderId(ddOrder.getDdOrderId())
				.ddOrderLineId(ddOrderLine.getDdOrderLineId())
				.distributionNetworkAndLineId(ddOrderLine.getDistributionNetworkAndLineId())
				.qty(ddOrderLine.getQty())
				.plantId(ddOrder.getPlantId())
				.productPlanningId(ddOrder.getProductPlanningId())
				.shipperId(ddOrder.getShipperId())
				.build();
	}

	private void handleMainDataUpdates(@NonNull final DDOrderCreatedEvent ddOrderCreatedEvent, @NonNull final DDOrderLine ddOrderLine)
	{
		if (ddOrderCreatedEvent.getDdOrder().isSimulated())
		{
			return;
		}

		final OrgId orgId = ddOrderCreatedEvent.getOrgId();
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		final DDOrderMainDataHandler mainDataUpdater = DDOrderMainDataHandler.builder()
				.ddOrderDetailRequestHandler(ddOrderDetailRequestHandler)
				.mainDataRequestHandler(mainDataRequestHandler)
				.abstractDDOrderEvent(ddOrderCreatedEvent)
				.ddOrderLine(ddOrderLine)
				.orgZone(timeZone)
				.build();

		mainDataUpdater.handleUpdate();
	}
}
