package de.metas.material.dispo.commons;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.IdConstants;
import de.metas.common.util.time.SystemTime;
import de.metas.document.dimension.Dimension;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrder.DDOrderBuilder;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.ddorder.DDOrderLine.DDOrderLineBuilder;
import de.metas.material.event.ddorder.DDOrderRequestedEvent;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderData;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.event.pporder.PPOrderLineData;
import de.metas.material.event.pporder.PPOrderRequestedEvent;
import de.metas.material.event.purchase.PurchaseCandidateRequestedEvent;
import de.metas.order.OrderLine;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLineRepository;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/*
 * #%L
 * metasfresh-manufacturing-dispo
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
public class RequestMaterialOrderService
{
	private final CandidateRepositoryRetrieval candidateRepository;
	private final PostMaterialEventService materialEventService;
	private final OrderLineRepository orderLineRepository;

	public RequestMaterialOrderService(
			@NonNull final CandidateRepositoryRetrieval candidateRepository,
			@NonNull final PostMaterialEventService materialEventService,
			@NonNull final OrderLineRepository orderLineRepository)
	{
		this.materialEventService = materialEventService;
		this.candidateRepository = candidateRepository;
		this.orderLineRepository = orderLineRepository;
	}

	/**
	 * Creates and fires an event to request the creation of a particular material order (production, distribution or purchase).
	 *
	 * @param groupId of the candidates that are used to derive the material order to be requested.
	 */
	public void requestMaterialOrderForCandidates(
			@NonNull final MaterialDispoGroupId groupId,
			@Nullable final String traceId)
	{
		List<Candidate> groupOfCandidates = null;
		try
		{
			groupOfCandidates = candidateRepository.retrieveGroup(groupId);
			if (groupOfCandidates.isEmpty())
			{
				return;
			}

			final CandidateBusinessCase businessCase = CollectionUtils.extractSingleElement(groupOfCandidates, Candidate::getBusinessCase);
			switch (businessCase)
			{
				case PRODUCTION:
					createAndFirePPOrderRequestedEvent(groupOfCandidates);
					break;
				case DISTRIBUTION:
					createAndFireDDOrderRequestedEvent(groupOfCandidates, traceId);
					break;
				case PURCHASE:
					createAndFirePurchaseCandidateRequestedEvent(groupOfCandidates, traceId);
					break;
				case FORECAST:
					createAndFireForecastRequestedEvent(groupOfCandidates);
					break;
				default:
					break;
			}
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException
					.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("groupId", groupId)
					.setParameter("groupOfCandidates", groupOfCandidates);
		}
	}

	/**
	 * @param group a non-empty list of candidates that all have {@link CandidateBusinessCase#PRODUCTION},
	 *              all have the same {@link Candidate#getGroupId()}
	 *              and all have appropriate not-null {@link Candidate#getBusinessCaseDetail()}s that need to be {@link ProductionDetail} instances.
	 */
	private void createAndFirePPOrderRequestedEvent(@NonNull final List<Candidate> group)
	{
		final PPOrderRequestedEvent ppOrderRequestEvent = createPPOrderRequestedEvent(group);
		materialEventService.postEventAsync(ppOrderRequestEvent);
	}

	@VisibleForTesting
	PPOrderRequestedEvent createPPOrderRequestedEvent(@NonNull final List<Candidate> group)
	{
		Preconditions.checkArgument(!group.isEmpty(), "Param 'group' may not be an empty list");

		final PPOrder.PPOrderBuilder ppOrderBuilder = PPOrder.builder();
		final PPOrderData.PPOrderDataBuilder ppOrderDataBuilder = PPOrderData.builder();

		for (final Candidate groupMember : group)
		{
			if (groupMember.getDemandDetail() != null)
			{
				final int orderLineId = groupMember.getDemandDetail().getOrderLineId();

				if (orderLineId > 0)
				{
					ppOrderDataBuilder.orderLineId(orderLineId);
					ppOrderDataBuilder.packingMaterialId(getPackingMaterialId(orderLineId));
				}

				if (groupMember.getDemandDetail().getShipmentScheduleId() > 0)
				{
					ppOrderDataBuilder.shipmentScheduleId(groupMember.getDemandDetail().getShipmentScheduleId());
				}
			}

			final ProductionDetail prodDetail = ProductionDetail.cast(groupMember.getBusinessCaseDetail());
			final MaterialDescriptor materialDescriptor = groupMember.getMaterialDescriptor();
			if (prodDetail.getProductBomLineId() <= 0)
			{
				// we talk about a ppOrder (header)
				ppOrderDataBuilder
						.clientAndOrgId(groupMember.getClientAndOrgId())
						.productPlanningId(prodDetail.getProductPlanningId())
						.datePromised(groupMember.getDate())
						.dateStartSchedule(groupMember.getDate())
						.plantId(prodDetail.getPlantId())
						.workstationId(prodDetail.getWorkstationId())
						.productDescriptor(materialDescriptor)
						.bpartnerId(materialDescriptor.getCustomerId())
						.qtyRequired(groupMember.getQuantity())
						.warehouseId(groupMember.getWarehouseId());
			}
			else
			{
				final boolean receipt = groupMember.getType() == CandidateType.SUPPLY;
				if (receipt)
				{
					ppOrderDataBuilder.datePromised(groupMember.getDate());
				}
				else
				{
					ppOrderDataBuilder.dateStartSchedule(groupMember.getDate());
				}

				ppOrderBuilder.line(
						PPOrderLine.builder()
								.ppOrderLineData(PPOrderLineData.builder()
														 .description(prodDetail.getDescription())
														 .productBomLineId(prodDetail.getProductBomLineId())
														 .issueOrReceiveDate(groupMember.getDate())
														 .productDescriptor(materialDescriptor)
														 .qtyRequired(groupMember.getQuantity())
														 .productBomLineId(prodDetail.getProductBomLineId())
														 .receipt(receipt)
														 .build())
								.build());
			}
		}

		final Candidate firstGroupMember = group.get(0);

		ppOrderDataBuilder.materialDispoGroupId(firstGroupMember.getEffectiveGroupId());

		return PPOrderRequestedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(firstGroupMember.getClientAndOrgId()))
				.dateOrdered(de.metas.common.util.time.SystemTime.asInstant())
				.ppOrder(ppOrderBuilder.ppOrderData(ppOrderDataBuilder.build()).build())
				.build();
	}

	private void createAndFireDDOrderRequestedEvent(@NonNull final List<Candidate> group, @Nullable final String traceId)
	{
		final DDOrderRequestedEvent ddOrderRequestEvent = createDDOrderRequestEvent(group, traceId);
		materialEventService.postEventAsync(ddOrderRequestEvent);
	}

	@VisibleForTesting
	DDOrderRequestedEvent createDDOrderRequestEvent(@NonNull final List<Candidate> group, @Nullable final String traceId)
	{
		Preconditions.checkArgument(!group.isEmpty(), "Param 'group' is an empty list");

		final DDOrderBuilder ddOrderBuilder = DDOrder.builder()
				.materialDispoGroupId(MaterialDispoGroupId.ofInt(10));

		final DDOrderLineBuilder ddOrderLineBuilder = DDOrderLine.builder();

		Instant startDate = null;
		Instant endDate = null;

		for (final Candidate groupMember : group)
		{
			ddOrderBuilder.orgId(groupMember.getOrgId())
					.simulated(groupMember.isSimulated());

			if (groupMember.getType() == CandidateType.SUPPLY)
			{
				endDate = groupMember.getDate();
				ddOrderBuilder.datePromised(groupMember.getDate());
			}
			else
			{
				startDate = groupMember.getDate();
			}

			final MaterialDescriptor materialDescriptor = groupMember.getMaterialDescriptor();
			ddOrderLineBuilder
					.productDescriptor(materialDescriptor)
					.bPartnerId(BPartnerId.toRepoId(materialDescriptor.getCustomerId()))
					.qty(groupMember.getQuantity());

			if (groupMember.getDemandDetail() != null && groupMember.getDemandDetail().getOrderLineId() > 0)
			{
				ddOrderLineBuilder.salesOrderLineId(groupMember.getDemandDetail().getOrderLineId());
			}

			final DistributionDetail distributionDetail = DistributionDetail.cast(groupMember.getBusinessCaseDetail());
			ddOrderBuilder
					.plantId(distributionDetail.getPlantId())
					.productPlanningId(distributionDetail.getProductPlanningId())
					.shipperId(distributionDetail.getShipperId());

			ddOrderLineBuilder
					.networkDistributionLineId(distributionDetail.getNetworkDistributionLineId());
		}

		final int durationDays = TimeUtil.getDaysBetween(startDate, endDate);

		final Candidate firstGroupMember = group.get(0);

		return DDOrderRequestedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientOrgAndTraceId(firstGroupMember.getClientAndOrgId(), traceId))
				.dateOrdered(SystemTime.asInstant())
				.ddOrder(ddOrderBuilder
								 .line(ddOrderLineBuilder
											   .durationDays(durationDays)
											   .build())
								 .build())
				.build();
	}

	private void createAndFirePurchaseCandidateRequestedEvent(@NonNull final List<Candidate> group, @Nullable final String traceId)
	{
		final PurchaseCandidateRequestedEvent purchaseCandidateRequestedEvent = createPurchaseCandidateRequestedEvent(group, traceId);
		materialEventService.postEventAfterNextCommit(purchaseCandidateRequestedEvent);
	}

	private PurchaseCandidateRequestedEvent createPurchaseCandidateRequestedEvent(@NonNull final List<Candidate> group, @Nullable final String traceId)
	{
		final Candidate singleCandidate = CollectionUtils.singleElement(group);

		final Dimension dimension = singleCandidate.getDimension();

		return PurchaseCandidateRequestedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientOrgAndTraceId(singleCandidate.getClientAndOrgId(), traceId))
				.purchaseMaterialDescriptor(singleCandidate.getMaterialDescriptor())
				.supplyCandidateRepoId(singleCandidate.getId().getRepoId())
				.salesOrderLineRepoId(singleCandidate.getAdditionalDemandDetail().getOrderLineId())
				.salesOrderRepoId(singleCandidate.getAdditionalDemandDetail().getOrderId())
				.forecastId(singleCandidate.getAdditionalDemandDetail().getForecastId())
				.forecastLineId(singleCandidate.getAdditionalDemandDetail().getForecastLineId())

				.projectId(ProjectId.toRepoId(dimension.getProjectId()))
				.campaignId(dimension.getCampaignId())
				.activityId(ActivityId.toRepoId(dimension.getActivityId()))
				.userElementId1(dimension.getUserElement1Id())
				.userElementId2(dimension.getUserElement2Id())
				.userElementString1(dimension.getUserElementString1())
				.userElementString2(dimension.getUserElementString2())
				.userElementString3(dimension.getUserElementString3())
				.userElementString4(dimension.getUserElementString4())
				.userElementString5(dimension.getUserElementString5())
				.userElementString6(dimension.getUserElementString6())
				.userElementString7(dimension.getUserElementString7())
				.simulated(singleCandidate.isSimulated())
				.build();
	}

	private void createAndFireForecastRequestedEvent(@NonNull final List<Candidate> group)
	{
		final PurchaseCandidateRequestedEvent purchaseCandidateRequestedEvent = createForecastRequestedEvent(group);
		materialEventService.postEventAfterNextCommit(purchaseCandidateRequestedEvent);
	}

	private PurchaseCandidateRequestedEvent createForecastRequestedEvent(@NonNull final List<Candidate> group)
	{
		final Candidate singleCandidate = CollectionUtils.singleElement(group);

		final Dimension dimension = singleCandidate.getDimension();

		return PurchaseCandidateRequestedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(singleCandidate.getClientAndOrgId()))
				.supplyCandidateRepoId(singleCandidate.getId().getRepoId())
				.purchaseMaterialDescriptor(singleCandidate.getMaterialDescriptor())
				.forecastId(singleCandidate.getDemandDetail().getForecastId())
				.forecastLineId(singleCandidate.getDemandDetail().getForecastLineId())

				.campaignId(dimension.getCampaignId())
				.activityId(dimension.getActivityId() == null ? -1 : dimension.getActivityId().getRepoId())
				.projectId(dimension.getProjectId() == null ? -1 : dimension.getProjectId().getRepoId())
				.userElementId1(dimension.getUserElement1Id())
				.userElementId2(dimension.getUserElement2Id())
				.userElementString1(dimension.getUserElementString1())
				.userElementString2(dimension.getUserElementString2())
				.userElementString3(dimension.getUserElementString3())
				.userElementString4(dimension.getUserElementString4())
				.userElementString5(dimension.getUserElementString5())
				.userElementString6(dimension.getUserElementString6())
				.userElementString7(dimension.getUserElementString7())

				.supplyCandidateRepoId(singleCandidate.getId().getRepoId())
				.build();
	}

	@Nullable
	private HUPIItemProductId getPackingMaterialId(final int demandOrderLineId)
	{
		final OrderLineId orderLineId = OrderLineId.ofRepoIdOrNull(IdConstants.toRepoId(demandOrderLineId));

		return Optional.ofNullable(orderLineId)
				.map(orderLineRepository::getById)
				.map(OrderLine::getHuPIItemProductId)
				.orElse(null);
	}
}
