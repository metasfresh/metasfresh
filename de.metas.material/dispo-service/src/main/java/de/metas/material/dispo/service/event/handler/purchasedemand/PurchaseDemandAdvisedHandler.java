package de.metas.material.dispo.service.event.handler.purchasedemand;

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
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.purchase.PurchaseDemandAdvisedEvent;
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
public final class PurchaseDemandAdvisedHandler
		implements MaterialEventHandler<PurchaseDemandAdvisedEvent>
{

	private final CandidateChangeService candidateChangeHandler;

	/**
	 *
	 * @param candidateChangeHandler
	 * @param candidateService needed in case we directly request a {@link PpOrderSuggestedEvent}'s proposed PP_Order to be created.
	 */
	public PurchaseDemandAdvisedHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		this.candidateChangeHandler = candidateChangeHandler;
	}

	@Override
	public Collection<Class<? extends PurchaseDemandAdvisedEvent>> getHandeledEventType()
	{
		return ImmutableList.of(PurchaseDemandAdvisedEvent.class);
	}

	@Override
	public void validateEvent(@NonNull final PurchaseDemandAdvisedEvent event)
	{
		// nothing to do; the instance was already validated on construction time
	}

	@Override
	public void handleEvent(@NonNull final PurchaseDemandAdvisedEvent event)
	{
		final SupplyRequiredDescriptor supplyRequiredDescriptor = event.getSupplyRequiredDescriptor();
		final DemandDetail demandDetail = DemandDetail.forSupplyRequiredDescriptorOrNull(supplyRequiredDescriptor);

		final PurchaseDetail purchaseDetail = new PurchaseDetail(supplyRequiredDescriptor.getMaterialDescriptor().getQuantity());

		final Candidate supplyCandidate = Candidate.builder()
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PURCHASE)
				.status(CandidateStatus.doc_planned)
				.materialDescriptor(supplyRequiredDescriptor.getMaterialDescriptor())
				.businessCaseDetail(purchaseDetail)
				.additionalDemandDetail(demandDetail)
				.build();

		candidateChangeHandler.onCandidateNewOrChange(supplyCandidate);
	}

}
