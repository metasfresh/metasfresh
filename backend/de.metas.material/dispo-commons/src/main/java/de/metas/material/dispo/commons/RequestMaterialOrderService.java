package de.metas.material.dispo.commons;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
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
import de.metas.material.event.pporder.PPOrder.PPOrderBuilder;
import de.metas.material.event.pporder.PPOrderLine;
import de.metas.material.event.pporder.PPOrderRequestedEvent;
import de.metas.material.event.purchase.PurchaseCandidateRequestedEvent;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

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

	public RequestMaterialOrderService(
			@NonNull final CandidateRepositoryRetrieval candidateRepository,
			@NonNull final PostMaterialEventService materialEventService)
	{
		this.materialEventService = materialEventService;
		this.candidateRepository = candidateRepository;
	}

	/**
	 * Creates and fires an event to request the creation of a particular material order (production, distribution or purchase).
	 *
	 * @param groupId of the candidates that are used to derive the material order to be requested.
	 */
	public void requestMaterialOrderForCandidates(@NonNull final MaterialDispoGroupId groupId)
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
					createAndFireDDOrderRequestedEvent(groupOfCandidates);
					break;
				case PURCHASE:
					createAndFirePurchaseCandidateRequestedEvent(groupOfCandidates);
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
	 *
	 * @param group a non-empty list of candidates that all have {@link CandidateBusinessCase#PRODUCTION},
	 *            all have the same {@link Candidate#getGroupId()}
	 *            and all have appropriate not-null {@link Candidate#getBusinessCaseDetail()}s that need to be {@link ProductionDetail} instances.
	 */
	private void createAndFirePPOrderRequestedEvent(@NonNull final List<Candidate> group)
	{
		final PPOrderRequestedEvent ppOrderRequestEvent = createPPOrderRequestedEvent(group);
		materialEventService.postEventNow(ppOrderRequestEvent);
	}

	@VisibleForTesting
	PPOrderRequestedEvent createPPOrderRequestedEvent(@NonNull final List<Candidate> group)
	{
		Preconditions.checkArgument(!group.isEmpty(), "Param 'group' may not be an empty list");

		final PPOrderBuilder ppOrderBuilder = PPOrder.builder();

		for (final Candidate groupMember : group)
		{
			if (groupMember.getDemandDetail() != null && groupMember.getDemandDetail().getOrderLineId() > 0)
			{
				ppOrderBuilder.orderLineId(groupMember.getDemandDetail().getOrderLineId());
			}

			final ProductionDetail prodDetail = ProductionDetail.cast(groupMember.getBusinessCaseDetail());
			final MaterialDescriptor materialDescriptor = groupMember.getMaterialDescriptor();
			if (prodDetail.getProductBomLineId() <= 0)
			{
				// we talk about a ppOrder (header)
				ppOrderBuilder
						.clientAndOrgId(groupMember.getClientAndOrgId())
						.productPlanningId(prodDetail.getProductPlanningId())
						.datePromised(groupMember.getDate())
						.plantId(prodDetail.getPlantId())
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
					ppOrderBuilder.datePromised(groupMember.getDate());
				}
				else
				{
					ppOrderBuilder.dateStartSchedule(groupMember.getDate());
				}

				ppOrderBuilder.line(
						PPOrderLine.builder()
								.description(prodDetail.getDescription())
								.productBomLineId(prodDetail.getProductBomLineId())
								.issueOrReceiveDate(groupMember.getDate())
								.productDescriptor(materialDescriptor)
								.qtyRequired(groupMember.getQuantity())
								.productBomLineId(prodDetail.getProductBomLineId())
								.receipt(receipt)
								.build());
			}
		}

		final Candidate firstGroupMember = group.get(0);

		ppOrderBuilder.materialDispoGroupId(firstGroupMember.getEffectiveGroupId());

		return PPOrderRequestedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(firstGroupMember.getClientAndOrgId()))
				.dateOrdered(de.metas.common.util.time.SystemTime.asInstant())
				.ppOrder(ppOrderBuilder.build())
				.build();
	}

	private void createAndFireDDOrderRequestedEvent(@NonNull final List<Candidate> group)
	{
		final DDOrderRequestedEvent ddOrderRequestEvent = createDDOrderRequestEvent(group);
		materialEventService.postEventNow(ddOrderRequestEvent);
	}

	@VisibleForTesting
	DDOrderRequestedEvent createDDOrderRequestEvent(@NonNull final List<Candidate> group)
	{
		Preconditions.checkArgument(!group.isEmpty(), "Param 'group' is an empty list");

		final DDOrderBuilder ddOrderBuilder = DDOrder.builder()
				.materialDispoGroupId(MaterialDispoGroupId.ofInt(10));

		final DDOrderLineBuilder ddOrderLineBuilder = DDOrderLine.builder();

		Instant startDate = null;
		Instant endDate = null;

		for (final Candidate groupMember : group)
		{
			ddOrderBuilder.orgId(groupMember.getOrgId());
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
				.eventDescriptor(EventDescriptor.ofClientAndOrg(firstGroupMember.getClientAndOrgId()))
				.dateOrdered(SystemTime.asInstant())
				.ddOrder(ddOrderBuilder
						.line(ddOrderLineBuilder
								.durationDays(durationDays)
								.build())
						.build())
				.build();
	}

	private void createAndFirePurchaseCandidateRequestedEvent(@NonNull final List<Candidate> group)
	{
		final PurchaseCandidateRequestedEvent purchaseCandidateRequestedEvent = createPurchaseCandidateRequestedEvent(group);
		materialEventService.postEventAfterNextCommit(purchaseCandidateRequestedEvent);
	}

	private PurchaseCandidateRequestedEvent createPurchaseCandidateRequestedEvent(@NonNull final List<Candidate> group)
	{
		final Candidate singleCandidate = CollectionUtils.singleElement(group);

		final PurchaseCandidateRequestedEvent purchaseCandidateRequestedEvent = PurchaseCandidateRequestedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientAndOrg(singleCandidate.getClientAndOrgId()))
				.purchaseMaterialDescriptor(singleCandidate.getMaterialDescriptor())
				.supplyCandidateRepoId(singleCandidate.getId().getRepoId())
				.salesOrderLineRepoId(singleCandidate.getAdditionalDemandDetail().getOrderLineId())
				.salesOrderRepoId(singleCandidate.getAdditionalDemandDetail().getOrderId())
				.build();

		return purchaseCandidateRequestedEvent;
	}
}
