package de.metas.material.dispo.service.event.handler.purchasecandidate;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.dispo.commons.RequestMaterialOrderService;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.purchase.PurchaseCandidateAdvisedEvent;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
	private final RequestMaterialOrderService requestMaterialOrderService;
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	/**
	 * @param requestMaterialOrderService needed in case we directly request a {@link PurchaseCandidateAdvisedEvent}'s proposed purchase order to be created.
	 */
	public PurchaseCandidateAdvisedHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final RequestMaterialOrderService requestMaterialOrderService,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		this.candidateChangeHandler = candidateChangeHandler;
		this.requestMaterialOrderService = requestMaterialOrderService;
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
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

		final MaterialDescriptor materialDescriptor = supplyRequiredDescriptor.getMaterialDescriptor();

		final PurchaseDetail purchaseDetail = PurchaseDetail.builder()
				.qty(materialDescriptor.getQuantity())
				.vendorRepoId(event.getVendorId())
				.purchaseCandidateRepoId(-1)
				.productPlanningRepoId(event.getProductPlanningId())
				.advised(Flag.TRUE)
				.build();

		// see if there is an existing supply candidate to work with
		Candidate.CandidateBuilder candidateBuilder = null;
		if (supplyRequiredDescriptor != null && supplyRequiredDescriptor.getSupplyCandidateId() > 0)
		{
			final CandidatesQuery supplyCandidateQuery = CandidatesQuery.fromId(
					CandidateId.ofRepoId(supplyRequiredDescriptor.getSupplyCandidateId()));
			final Candidate existingCandidate = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(supplyCandidateQuery);
			if (existingCandidate == null)
			{
				candidateBuilder = existingCandidate.toBuilder();
			}
		}
		if (candidateBuilder == null)
		{
			candidateBuilder = Candidate.builder();
		}

		// put out data into the new or preexisting candidate
		final Candidate supplyCandidate = candidateBuilder
				.clientAndOrgId(event.getEventDescriptor().getClientAndOrgId())
				.id(CandidateId.ofRepoIdOrNull(supplyRequiredDescriptor.getSupplyCandidateId()))
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PURCHASE)
				.materialDescriptor(materialDescriptor)
				.businessCaseDetail(purchaseDetail)
				.additionalDemandDetail(demandDetail)
				.build();

		final Candidate createdCandidate = candidateChangeHandler.onCandidateNewOrChange(supplyCandidate);
		if (event.isDirectlyCreatePurchaseCandidate())
		{
			// the group contains just one item, i.e. the supplyCandidate, but for the same of generic-ness we use that same interface that's also used for production and distribution
			requestMaterialOrderService.requestMaterialOrderForCandidates(createdCandidate.getGroupId());
		}
	}
}
