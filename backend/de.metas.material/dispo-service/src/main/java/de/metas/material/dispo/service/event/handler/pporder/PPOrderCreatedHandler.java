package de.metas.material.dispo.service.event.handler.pporder;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.cockpit.view.mainrecord.UpdateMainDataRequest;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateSaveResult;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DemandDetailsQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.handler.CandidateHandler.OnNewOrChangeAdvise;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderCreatedEvent;
import de.metas.material.event.pporder.PPOrderData;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.organization.IOrgDAO;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.Collection;
import java.util.Optional;

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
public final class PPOrderCreatedHandler
		implements MaterialEventHandler<PPOrderCreatedEvent>
{
	private static final Logger logger = LogManager.getLogger(PPOrderCreatedHandler.class);

	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final CandidateChangeService candidateChangeService;
	private final MainDataRequestHandler mainDataRequestHandler;
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	public PPOrderCreatedHandler(
			@NonNull final CandidateChangeService candidateChangeService,
			@NonNull final MainDataRequestHandler mainDataRequestHandler,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		this.candidateChangeService = candidateChangeService;
		this.mainDataRequestHandler = mainDataRequestHandler;
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
	}

	@Override
	public Collection<Class<? extends PPOrderCreatedEvent>> getHandledEventType()
	{
		return ImmutableList.of(PPOrderCreatedEvent.class);
	}

	@Override
	public void validateEvent(@NonNull final PPOrderCreatedEvent event)
	{
		event.validate();
	}

	@Override
	public void handleEvent(@NonNull final PPOrderCreatedEvent event)
	{
		final WarehouseId warehouseId = event.getPpOrder().getPpOrderData().getWarehouseId();
		if (warehouseBL.isIgnoreInMaterialDispo(warehouseId))
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog(
					"Ignoring {} for M_Warehouse_ID={} (warehouse is excluded from material-dispo: MRP_Exclude or IsDropShipWarehouse)",
					event.getClass().getSimpleName(),
					WarehouseId.toRepoId(warehouseId));
			return;
		}

		handlePPOrderCreatedEvent(event);
	}

	private void handlePPOrderCreatedEvent(@NonNull final PPOrderCreatedEvent ppOrderEvent)
	{
		final Candidate headerCandidate = createHeaderCandidate(ppOrderEvent)
				.getCandidate();

		updateMainData(ppOrderEvent);

		final DemandDetail headerDemandDetail = headerCandidate.getDemandDetail();
		final ProductionDetail headerProductionDetail = ProductionDetail.cast(headerCandidate.getBusinessCaseDetail());

		PPOrderLineCandidatesCreateCommand.builder()
				.candidateChangeService(candidateChangeService)
				.candidateRepositoryRetrieval(candidateRepositoryRetrieval)
				.ppOrder(ppOrderEvent.getPpOrder())
				.headerDemandDetail(headerDemandDetail)
				.groupId(headerCandidate.getGroupId())
				.headerCandidateSeqNo(headerCandidate.getSeqNo())
				.advised(headerProductionDetail.getAdvised())
				.pickDirectlyIfFeasible(Flag.FALSE_DONT_UPDATE) // only the ppOrder's header supply product can be picked directly because only there we might know the shipment schedule ID
				.create();
	}

	private void updateMainData(final @NonNull PPOrderCreatedEvent ppOrderEvent)
	{
		final ZoneId orgZoneId = orgDAO.getTimeZone(ppOrderEvent.getOrgId());

		final MainDataRecordIdentifier mainDataRecordIdentifier = MainDataRecordIdentifier.builder()
				.warehouseId(ppOrderEvent.getPpOrder().getPpOrderData().getWarehouseId())
				.productDescriptor(ppOrderEvent.getPpOrder().getPpOrderData().getProductDescriptor())
				.date(TimeUtil.getDay(ppOrderEvent.getPpOrder().getPpOrderData().getDatePromised(), orgZoneId))
				.build();

		final UpdateMainDataRequest updateMainDataRequest = UpdateMainDataRequest.builder()
				.identifier(mainDataRecordIdentifier)
				.qtySupplyPPOrder(ppOrderEvent.getPpOrder().getPpOrderData().getQtyOpen())
				.build();

		mainDataRequestHandler.handleDataUpdateRequest(updateMainDataRequest);
	}

	@NonNull
	private CandidateSaveResult createHeaderCandidate(@NonNull final PPOrderCreatedEvent ppOrderEvent)
	{
		final PPOrder ppOrder = ppOrderEvent.getPpOrder();

		final Candidate.CandidateBuilder builder = Candidate.builderForClientAndOrgId(ppOrder.getPpOrderData().getClientAndOrgId());

		retrieveDemandDetail(ppOrder.getPpOrderData())
				.ifPresent(builder::additionalDemandDetail);

		final Candidate headerCandidate = builder
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.businessCaseDetail(createProductionDetailForPPOrder(ppOrderEvent))
				.materialDescriptor(createMaterialDescriptorForPPOrder(ppOrder))
				// .groupId(null) // will be set after save
				.build();

		return candidateChangeService.onCandidateNewOrChange(headerCandidate, OnNewOrChangeAdvise.attemptUpdate(false));
	}

	@NonNull
	private MaterialDescriptor createMaterialDescriptorForPPOrder(@NonNull final PPOrder ppOrder)
	{
		return MaterialDescriptor.builder()
				.date(ppOrder.getPpOrderData().getDatePromised())
				.productDescriptor(ppOrder.getPpOrderData().getProductDescriptor())
				.quantity(ppOrder.getPpOrderData().getQtyOpen())
				.warehouseId(ppOrder.getPpOrderData().getWarehouseId())
				.build();
	}

	@NonNull
	private ProductionDetail createProductionDetailForPPOrder(
			@NonNull final PPOrderCreatedEvent ppOrderEvent)
	{
		final PPOrder ppOrder = ppOrderEvent.getPpOrder();

		return ProductionDetail.builder()
				.advised(Flag.FALSE_DONT_UPDATE)
				.pickDirectlyIfFeasible(Flag.of(ppOrderEvent.isDirectlyPickIfFeasible()))
				.qty(ppOrder.getPpOrderData().getQtyRequired())
				.plantId(ppOrder.getPpOrderData().getPlantId())
				.workstationId(ppOrder.getPpOrderData().getWorkstationId())
				.productPlanningId(ppOrder.getPpOrderData().getProductPlanningId())
				.ppOrderRef(PPOrderRef.ofPPOrderId(ppOrder.getPpOrderId()))
				.ppOrderDocStatus(ppOrder.getDocStatus())
				.build();
	}

	@NonNull
	private Optional<DemandDetail> retrieveDemandDetail(@NonNull final PPOrderData ppOrderData)
	{
		if (ppOrderData.getShipmentScheduleId() <= 0)
		{
			return Optional.empty();
		}

		final DemandDetailsQuery demandDetailsQuery = DemandDetailsQuery.
				ofShipmentScheduleId(ppOrderData.getShipmentScheduleId());

		final MaterialDescriptorQuery materialDescriptorQuery = MaterialDescriptorQuery.builder()
				.productId(ppOrderData.getProductDescriptor().getProductId())
				.warehouseId(ppOrderData.getWarehouseId())
				.build();

		final CandidatesQuery candidatesQuery = CandidatesQuery.builder()
				.type(CandidateType.DEMAND)
				.materialDescriptorQuery(materialDescriptorQuery)
				.demandDetailsQuery(demandDetailsQuery)
				.build();

		return candidateRepositoryRetrieval.retrieveLatestMatch(candidatesQuery)
				.map(Candidate::getDemandDetail);
	}
}
