package de.metas.material.dispo.service.event.handler.purchasecandidate;

import java.util.Collection;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateStatus;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.purchase.PurchaseCandidateAdvisedEvent;
import de.metas.material.event.purchase.PurchaseCandidateRequestedEvent;
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
public final class PurchaseCandidateAdvisedHandler
		implements MaterialEventHandler<PurchaseCandidateAdvisedEvent>
{

	private final CandidateChangeService candidateChangeHandler;
	private final PostMaterialEventService materialEventService;

	/**
	 *
	 * @param candidateChangeHandler
	 * @param candidateService needed in case we directly request a {@link PpOrderSuggestedEvent}'s proposed PP_Order to be created.
	 */
	public PurchaseCandidateAdvisedHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final PostMaterialEventService materialEventService)
	{
		this.candidateChangeHandler = candidateChangeHandler;
		this.materialEventService = materialEventService;
	}

	@Override
	public Collection<Class<? extends PurchaseCandidateAdvisedEvent>> getHandeledEventType()
	{
		return ImmutableList.of(PurchaseCandidateAdvisedEvent.class);
	}

	@Override
	public void validateEvent(@NonNull final PurchaseCandidateAdvisedEvent event)
	{
		// nothing to do; the event was already validated on construction time
	}

	/**
	 * Create a supply candidate
	 */
	@Override
	public void handleEvent(@NonNull final PurchaseCandidateAdvisedEvent event)
	{
		final SupplyRequiredDescriptor supplyRequiredDescriptor = event.getSupplyRequiredDescriptor();
		final DemandDetail demandDetail = DemandDetail.forSupplyRequiredDescriptorOrNull(supplyRequiredDescriptor);

		final MaterialDescriptor purchaseMaterialDescriptor = event.getPurchaseMaterialDescriptor();
		final PurchaseDetail purchaseDetail = PurchaseDetail.builder()
				.plannedQty(purchaseMaterialDescriptor.getQuantity())
				.vendorRepoId(purchaseMaterialDescriptor.getBPartnerId())
				.purchaseCandidateRepoId(-1)
				.advised(Flag.TRUE)
				.build();

		final Candidate supplyCandidate = Candidate.builder()
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PURCHASE)
				.status(CandidateStatus.doc_planned)
				.materialDescriptor(purchaseMaterialDescriptor)
				.businessCaseDetail(purchaseDetail)
				.additionalDemandDetail(demandDetail)
				.build();

		final Candidate createdCandidate = candidateChangeHandler.onCandidateNewOrChange(supplyCandidate);
		if (event.isDirectlyCreatePurchaseCandidate())
		{
			final PurchaseCandidateRequestedEvent purchaseCandidateRequestedEvent = PurchaseCandidateRequestedEvent.builder()
					.eventDescriptor(event.getEventDescriptor().copy())
					.purchaseMaterialDescriptor(purchaseMaterialDescriptor)
					.supplyCandidateRepoId(createdCandidate.getId())
					.salesOrderLineRepoId(demandDetail.getOrderLineId())
					.salesOrderRepoId(demandDetail.getOrderId())
					.build();
			materialEventService.postEventAfterNextCommit(purchaseCandidateRequestedEvent);
		}
	}
}
