package de.metas.material.dispo.commons;

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.IdConstants;
import de.metas.common.util.time.SystemTime;
import de.metas.document.dimension.Dimension;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.CandidatesGroup;
import de.metas.material.dispo.commons.candidate.businesscase.DistributionDetail;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.ddordercandidate.DDOrderCandidateData;
import de.metas.material.event.ddordercandidate.DDOrderCandidateRequestedEvent;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.PPOrderData;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.event.pporder.PPOrderLineData;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.material.event.pporder.PPOrderRequestedEvent;
import de.metas.material.event.purchase.PurchaseCandidateRequestedEvent;
import de.metas.order.OrderLine;
import de.metas.order.OrderLineId;
import de.metas.order.OrderLineRepository;
import de.metas.product.IProductBL;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
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
@RequiredArgsConstructor
public class RequestMaterialOrderService
{
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final CandidateRepositoryRetrieval candidateRepository;
	@NonNull private final PostMaterialEventService materialEventService;
	@NonNull private final OrderLineRepository orderLineRepository;

	/**
	 * Creates and fires an event to request the creation of a particular material order (production, distribution or purchase).
	 *
	 * @param groupId of the candidates that are used to derive the material order to be requested.
	 */
	public void requestMaterialOrderForCandidates(
			@NonNull final MaterialDispoGroupId groupId,
			@Nullable final String traceId)
	{
		try
		{
			final CandidatesGroup group = candidateRepository.retrieveGroup(groupId);
			requestMaterialOrderForCandidates(group, traceId);
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("groupId", groupId);
		}
	}

	public void requestMaterialOrderForCandidates(@NonNull final CandidatesGroup group, @Nullable final String traceId)
	{
		if (group.isEmpty())
		{
			return;
		}

		try
		{
			final CandidateBusinessCase businessCase = group.getBusinessCase();
			switch (businessCase)
			{
				case PRODUCTION:
					createAndFirePPOrderRequestedEvent(group);
					break;
				case DISTRIBUTION:
					createAndFireDDOrderRequestedEvent(group, traceId);
					break;
				case PURCHASE:
					createAndFirePurchaseCandidateRequestedEvent(group, traceId);
					break;
				case FORECAST:
					createAndFireForecastRequestedEvent(group);
					break;
				default:
					break;
			}
		}
		catch (final RuntimeException e)
		{
			throw AdempiereException.wrapIfNeeded(e)
					.appendParametersToMessage()
					.setParameter("group", group);
		}
	}

	/**
	 * @param group a non-empty list of candidates that all have {@link CandidateBusinessCase#PRODUCTION},
	 *              all have the same {@link Candidate#getGroupId()}
	 *              and all have appropriate not-null {@link Candidate#getBusinessCaseDetail()}s that need to be {@link ProductionDetail} instances.
	 */
	private void createAndFirePPOrderRequestedEvent(@NonNull final CandidatesGroup group)
	{
		final PPOrderRequestedEvent ppOrderRequestEvent = createPPOrderRequestedEvent(group);
		materialEventService.postEventAsync(ppOrderRequestEvent);
	}

	@VisibleForTesting
	PPOrderRequestedEvent createPPOrderRequestedEvent(@NonNull final CandidatesGroup group)
	{
		group.assertNotEmpty();

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

		ppOrderDataBuilder.materialDispoGroupId(group.getEffectiveGroupId());

		return PPOrderRequestedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(group.getClientAndOrgId()))
				.dateOrdered(de.metas.common.util.time.SystemTime.asInstant())
				.ppOrder(ppOrderBuilder.ppOrderData(ppOrderDataBuilder.build()).build())
				.build();
	}

	private void createAndFireDDOrderRequestedEvent(@NonNull final CandidatesGroup group, @Nullable final String traceId)
	{
		final DDOrderCandidateRequestedEvent event = createDDOrderCandidateRequestedEvent(group, traceId);
		materialEventService.postEventAsync(event);
	}

	@VisibleForTesting
	DDOrderCandidateRequestedEvent createDDOrderCandidateRequestedEvent(@NonNull final CandidatesGroup group, @Nullable final String traceId)
	{
		return DDOrderCandidateRequestedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientOrgAndTraceId(group.getClientAndOrgId(), traceId))
				.dateOrdered(SystemTime.asInstant())
				.ddOrderCandidateData(toDDOrderCandidateData(group))
				.build();
	}

	private DDOrderCandidateData toDDOrderCandidateData(@NonNull final CandidatesGroup group)
	{
		final Candidate supplyCandidate = group.getSingleSupplyCandidate();
		final Candidate demandCandidate = group.getSingleDemandCandidate();

		final DistributionDetail supplyDistributionDetail = DistributionDetail.cast(supplyCandidate.getBusinessCaseDetail());

		return DDOrderCandidateData.builder()
				.clientAndOrgId(supplyCandidate.getClientAndOrgId())
				.productPlanningId(supplyDistributionDetail.getProductPlanningId())
				.distributionNetworkAndLineId(supplyDistributionDetail.getDistributionNetworkAndLineId())
				//
				.sourceWarehouseId(demandCandidate.getWarehouseId())
				.targetWarehouseId(supplyCandidate.getWarehouseId())
				.targetPlantId(supplyDistributionDetail.getPlantId())
				.shipperId(supplyDistributionDetail.getShipperId())
				//
				.customerId(BPartnerId.toRepoId(supplyCandidate.getCustomerId()))
				.salesOrderLineId(OrderLineId.toRepoId(demandCandidate.getSalesOrderLineId()))
				.forwardPPOrderRef(getPpOrderRef(supplyCandidate))
				//
				.productDescriptor(supplyCandidate.getMaterialDescriptor())
				.fromWarehouseMinMaxDescriptor(demandCandidate.getMinMaxDescriptor().toNullIfZero())
				.supplyDate(supplyCandidate.getDate())
				.demandDate(demandCandidate.getDate())
				//
				.qty(supplyCandidate.getQuantity())
				.uomId(productBL.getStockUOMId(supplyCandidate.getProductId()).getRepoId())
				//
				.simulated(supplyCandidate.isSimulated())
				.materialDispoGroupId(group.getEffectiveGroupId())
				.build();
	}

	private void createAndFirePurchaseCandidateRequestedEvent(@NonNull final CandidatesGroup group, @Nullable final String traceId)
	{
		final PurchaseCandidateRequestedEvent purchaseCandidateRequestedEvent = createPurchaseCandidateRequestedEvent(group, traceId);
		materialEventService.postEventAfterNextCommit(purchaseCandidateRequestedEvent);
	}

	private PurchaseCandidateRequestedEvent createPurchaseCandidateRequestedEvent(@NonNull final CandidatesGroup group, @Nullable final String traceId)
	{
		final Candidate singleCandidate = group.getSingleCandidate();

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

	private void createAndFireForecastRequestedEvent(@NonNull final CandidatesGroup group)
	{
		final PurchaseCandidateRequestedEvent purchaseCandidateRequestedEvent = createForecastRequestedEvent(group);
		materialEventService.postEventAfterNextCommit(purchaseCandidateRequestedEvent);
	}

	private PurchaseCandidateRequestedEvent createForecastRequestedEvent(@NonNull final CandidatesGroup group)
	{
		final Candidate singleCandidate = group.getSingleCandidate();

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

	private static PPOrderRef getPpOrderRef(final Candidate candidate)
	{
		final ProductionDetail productionDetail = candidate.getBusinessCaseDetail(ProductionDetail.class).orElse(null);
		if (productionDetail != null)
		{
			return productionDetail.getPpOrderRef();
		}

		final DistributionDetail distributionDetail = candidate.getBusinessCaseDetail(DistributionDetail.class).orElse(null);
		if (distributionDetail != null)
		{
			return distributionDetail.getForwardPPOrderRef();
		}

		return null;
	}

}
