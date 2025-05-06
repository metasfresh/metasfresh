/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.material.dispo.service.event.handler.ppordercandidate;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.ProductionDetailsQuery;
import de.metas.material.dispo.commons.repository.query.SimulatedQueryQualifier;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.candidatechange.handler.CandidateHandler.OnNewOrChangeAdvise;
import de.metas.material.dispo.service.event.handler.pporder.PPOrderHandlerUtils;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.pporder.AbstractPPOrderCandidateEvent;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrderCandidate;
import de.metas.material.event.pporder.PPOrderCandidateAdvisedEvent;
import de.metas.material.event.pporder.PPOrderData;
import de.metas.material.event.pporder.PPOrderLineCandidate;
import de.metas.material.event.pporder.PPOrderLineData;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanningId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
abstract class PPOrderCandidateEventHandler
{
	private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	@NonNull protected final CandidateChangeService candidateChangeService;
	@NonNull protected final CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	@NonNull
	protected final Candidate createHeaderCandidate(
			@NonNull final AbstractPPOrderCandidateEvent event,
			@NonNull final CandidatesQuery preExistingSupplyQuery)
	{
		final SupplyRequiredDescriptor supplyRequiredDescriptor = event.getSupplyRequiredDescriptor();
		final PPOrderCandidate ppOrderCandidate = event.getPpOrderCandidate();

		final Candidate existingCandidateOrNull = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(preExistingSupplyQuery);

		final Candidate.CandidateBuilder builder = existingCandidateOrNull != null
				? existingCandidateOrNull.toBuilder()
				: Candidate.builderForClientAndOrgId(ppOrderCandidate.getPpOrderData().getClientAndOrgId());

		final ProductionDetail headerCandidateProductionDetail = createProductionDetailForPPOrderCandidate(event, existingCandidateOrNull);

		final MaterialDescriptor headerCandidateMaterialDescriptor = createMaterialDescriptorForPPOrderCandidate(ppOrderCandidate);
		final MaterialDescriptor actualHeaderCandidateMaterialDescriptor = isMaterialTrackingDeferred(ppOrderCandidate) ? headerCandidateMaterialDescriptor.withQuantity(BigDecimal.ZERO) : headerCandidateMaterialDescriptor;

		PPOrderHandlerUtils.computeDemandDetail(
				CandidateType.SUPPLY,
				supplyRequiredDescriptor,
				actualHeaderCandidateMaterialDescriptor).ifPresent(builder::additionalDemandDetail);

		final Candidate headerCandidate = builder
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.businessCaseDetail(headerCandidateProductionDetail)
				.materialDescriptor(actualHeaderCandidateMaterialDescriptor)
				// .groupId(null) // will be set after save
				.build();

		final boolean attemptUpdate = !preExistingSupplyQuery.isFalse();

		return candidateChangeService.onCandidateNewOrChange(headerCandidate, OnNewOrChangeAdvise.attemptUpdate(attemptUpdate))
				.toCandidateWithQtyDelta();
	}

	protected final void createLineCandidates(
			@NonNull final AbstractPPOrderCandidateEvent event,
			@Nullable final MaterialDispoGroupId groupId,
			@NonNull final Candidate headerCandidate)
	{
		final PPOrderCandidate ppOrderCandidate = event.getPpOrderCandidate();
		final List<PPOrderLineCandidate> ppOrderLineCandidates = ppOrderCandidate.getLines();
		final boolean simulated = headerCandidate.isSimulated();

		for (final PPOrderLineCandidate ppOrderLineCandidate : ppOrderLineCandidates)
		{
			final Candidate existingLineCandidate = retrieveExistingLineCandidateOrNull(ppOrderLineCandidate, groupId, simulated);

			final Candidate.CandidateBuilder candidateBuilder = existingLineCandidate != null
					? existingLineCandidate.toBuilder()
					: Candidate.builderForClientAndOrgId(ppOrderCandidate.getPpOrderData().getClientAndOrgId());

			final DemandDetail headerDemandDetail = headerCandidate.getDemandDetail();
			final ProductionDetail lineCandidateProductionDetail = createProductionDetailForPPOrderLineCandidate(ppOrderLineCandidate, ppOrderCandidate);

			final MaterialDescriptor materialDescriptor = createMaterialDescriptorForPPOrderLineCandidate(ppOrderLineCandidate, ppOrderCandidate);

			final MaterialDescriptor actualMaterialDescriptor = isMaterialTrackingDeferred(ppOrderCandidate) ? materialDescriptor.withQuantity(BigDecimal.ZERO) : materialDescriptor;

			candidateBuilder
					.type(CandidateType.DEMAND)
					.businessCase(CandidateBusinessCase.PRODUCTION)
					.seqNo(headerCandidate.getSeqNo() + 1) // not related to the ppOrderCandidate's SeqNo!
					.businessCaseDetail(lineCandidateProductionDetail)
					.materialDescriptor(actualMaterialDescriptor)
					.simulated(simulated);
			if (headerDemandDetail != null)
			{
				candidateBuilder.additionalDemandDetail(headerDemandDetail.withTraceId(event.getTraceId()));
			}
			if (groupId != null)
			{
				candidateBuilder.groupId(groupId);
			}

			candidateChangeService.onCandidateNewOrChange(candidateBuilder.build());
		}
	}

	private boolean isMaterialTrackingDeferred(final PPOrderCandidate ppOrderCandidate)
	{
		return isMaterialTrackingDeferredForThisEventType() && isAutoGeneratedPPOrder(ppOrderCandidate);
	}

	/**
	 * Used to specify if the current event type should generate a 0 Qty Candidate. This is useful in scenarios where the PP_Order was/will be created automatically for this candidate.
	 * Without this the {@link de.metas.material.event.supplyrequired.SupplyRequiredEvent} shall be duplicated.
	 */
	protected boolean isMaterialTrackingDeferredForThisEventType()
	{
		return false;
	}

	@Nullable
	protected final Candidate deleteHeaderCandidate(
			@NonNull final CandidatesQuery preExistingSupplyQuery)
	{
		final Candidate existingCandidateOrNull = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(preExistingSupplyQuery);

		if (existingCandidateOrNull != null)
		{
			candidateChangeService.onCandidateDelete(existingCandidateOrNull);
		}
		return existingCandidateOrNull;
	}

	protected final void deleteLineCandidates(
			@NonNull final AbstractPPOrderCandidateEvent event,
			@NonNull final Candidate headerCandidate)
	{
		final List<PPOrderLineCandidate> ppOrderLineCandidates = event.getPpOrderCandidate().getLines();
		final boolean simulated = headerCandidate.isSimulated();

		for (final PPOrderLineCandidate ppOrderLineCandidate : ppOrderLineCandidates)
		{
			final Candidate existingLineCandidate = retrieveExistingLineCandidateOrNull(ppOrderLineCandidate, null, simulated);
			if (existingLineCandidate != null)
			{
				candidateChangeService.onCandidateDelete(existingLineCandidate);
			}
		}
	}

	@NonNull
	private static ProductionDetail createProductionDetailForPPOrderCandidate(
			@NonNull final AbstractPPOrderCandidateEvent event,
			@Nullable final Candidate existingCandidateOrNull)
	{
		final ProductionDetail.ProductionDetailBuilder productionDetailBuilder = prepareProductionDetail(existingCandidateOrNull);

		final PPOrderCandidate ppOrderCandidate = event.getPpOrderCandidate();

		return productionDetailBuilder
				.advised(Flag.of(event instanceof PPOrderCandidateAdvisedEvent))
				.qty(ppOrderCandidate.getPpOrderData().getQtyOpen())
				.plantId(ppOrderCandidate.getPpOrderData().getPlantId())
				.workstationId(ppOrderCandidate.getPpOrderData().getWorkstationId())
				.pickDirectlyIfFeasible(Flag.FALSE)
				.productPlanningId(ppOrderCandidate.getPpOrderData().getProductPlanningId())
				.ppOrderRef(PPOrderRef.ofPPOrderCandidateIdOrNull(ppOrderCandidate.getPpOrderCandidateId()))
				.build();
	}

	@NonNull
	private static MaterialDescriptor createMaterialDescriptorForPPOrderCandidate(final PPOrderCandidate ppOrderCandidate)
	{
		return MaterialDescriptor.builder()
				.date(ppOrderCandidate.getPpOrderData().getDatePromised())
				.productDescriptor(ppOrderCandidate.getPpOrderData().getProductDescriptor())
				.quantity(ppOrderCandidate.getPpOrderData().getQtyOpen())
				.warehouseId(ppOrderCandidate.getPpOrderData().getWarehouseId())
				.build();
	}

	private boolean isAutoGeneratedPPOrder(@NonNull final PPOrderCandidate ppOrderCandidate)
	{
		final ProductPlanningId productPlanningId = ProductPlanningId.ofRepoIdOrNull(ppOrderCandidate.getPpOrderData().getProductPlanningId());
		return productPlanningId != null && productPlanningDAO.getById(productPlanningId).isCreatePlan();
	}

	@NonNull
	private static ProductionDetail.ProductionDetailBuilder prepareProductionDetail(@Nullable final Candidate existingCandidateOrNull)
	{
		return Optional.ofNullable(existingCandidateOrNull)
				.map(Candidate::getBusinessCaseDetail)
				.map(ProductionDetail::cast)
				.map(ProductionDetail::toBuilder)
				.orElse(ProductionDetail.builder());
	}

	@Nullable
	private Candidate retrieveExistingLineCandidateOrNull(
			@NonNull final PPOrderLineCandidate ppOrderLineCandidate,
			@Nullable final MaterialDispoGroupId groupId,
			final boolean simulated)
	{
		final SimulatedQueryQualifier simulatedQueryQualifier = simulated
				? SimulatedQueryQualifier.ONLY_SIMULATED
				: SimulatedQueryQualifier.EXCLUDE_SIMULATED;

		final ProductionDetailsQuery productionDetailsQuery = ProductionDetailsQuery.builder()
				.ppOrderCandidateLineId(ppOrderLineCandidate.getPpOrderLineCandidateId())
				.build();

		final CandidatesQuery lineCandidateQuery = CandidatesQuery.builder()
				.type(CandidateType.DEMAND)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.productionDetailsQuery(productionDetailsQuery)
				.simulatedQueryQualifier(simulatedQueryQualifier)
				.groupId(groupId)
				.build();

		return candidateRepositoryRetrieval.retrieveLatestMatchOrNull(lineCandidateQuery);
	}

	@NonNull
	private ProductionDetail createProductionDetailForPPOrderLineCandidate(
			@NonNull final PPOrderLineCandidate ppOrderLineCandidate,
			@NonNull final PPOrderCandidate ppOrderCandidate)
	{
		final PPOrderLineData ppOrderLineData = ppOrderLineCandidate.getPpOrderLineData();

		return ProductionDetail.builder()
				.advised(Flag.FALSE)
				.pickDirectlyIfFeasible(Flag.FALSE_DONT_UPDATE)
				.plantId(ppOrderCandidate.getPpOrderData().getPlantId())
				.workstationId(ppOrderCandidate.getPpOrderData().getWorkstationId())
				.qty(ppOrderLineData.getQtyOpenNegateIfReceipt())
				.productPlanningId(ppOrderCandidate.getPpOrderData().getProductPlanningId())
				.productBomLineId(ppOrderLineData.getProductBomLineId())
				.description(ppOrderLineData.getDescription())
				.ppOrderRef(PPOrderRef.ofPPOrderLineCandidateId(ppOrderCandidate.getPpOrderCandidateId(), ppOrderLineCandidate.getPpOrderLineCandidateId()))
				.build();
	}

	@NonNull
	private MaterialDescriptor createMaterialDescriptorForPPOrderLineCandidate(
			@NonNull final PPOrderLineCandidate ppOrderLineCandidate,
			@NonNull final PPOrderCandidate ppOrderCandidate)
	{
		final PPOrderData ppOrderData = ppOrderCandidate.getPpOrderData();

		return MaterialDescriptor.builder()
				.date(ppOrderLineCandidate.getPpOrderLineData().getIssueOrReceiveDate())
				.productDescriptor(ppOrderLineCandidate.getPpOrderLineData().getProductDescriptor())
				.quantity(ppOrderLineCandidate.getPpOrderLineData().getQtyOpenNegateIfReceipt())
				.warehouseId(ppOrderData.getWarehouseId())
				.build();
	}
}
