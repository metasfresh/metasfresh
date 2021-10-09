package de.metas.material.dispo.service.event.handler.purchasecandidate;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail;
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail.PurchaseDetailBuilder;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.purchase.PurchaseCandidateEvent;
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

public abstract class PurchaseCandidateCreatedOrUpdatedHandler<T extends PurchaseCandidateEvent>
		implements MaterialEventHandler<T>
{

	private final CandidateChangeService candidateChangeHandler;
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	/**
	 *
	 * @param candidateChangeHandler
	 * @param candidateService needed in case we directly request a {@link PpOrderSuggestedEvent}'s proposed PP_Order to be created.
	 */
	public PurchaseCandidateCreatedOrUpdatedHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		this.candidateChangeHandler = candidateChangeHandler;
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
	}

	protected final void handlePurchaseCandidateEvent(@NonNull final PurchaseCandidateEvent event)
	{
		final MaterialDescriptor materialDescriptor = event.getPurchaseMaterialDescriptor();

		final CandidatesQuery query = createCandidatesQuery(event);

		final Candidate existingCandidteOrNull = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(query);

		final CandidateBuilder candidateBuilder;
		final PurchaseDetailBuilder purchaseDetailBuilder;
		if (existingCandidteOrNull != null)
		{
			candidateBuilder = existingCandidteOrNull.toBuilder();
			purchaseDetailBuilder = PurchaseDetail
					.cast(existingCandidteOrNull.getBusinessCaseDetail())
					.toBuilder();
		}
		else
		{
			candidateBuilder = createInitialBuilder()
					.clientAndOrgId(event.getEventDescriptor().getClientAndOrgId());
			purchaseDetailBuilder = PurchaseDetail.builder();
		}

		final PurchaseDetail purchaseDetail = purchaseDetailBuilder
				.qty(materialDescriptor.getQuantity())
				.vendorRepoId(event.getVendorId())
				.purchaseCandidateRepoId(event.getPurchaseCandidateRepoId())
				.advised(Flag.FALSE_DONT_UPDATE)
				.build();

		candidateBuilder
				.materialDescriptor(materialDescriptor)
				.minMaxDescriptor(event.getMinMaxDescriptor())
				.businessCaseDetail(purchaseDetail);

		final Candidate supplyCandidate = updateBuilderFromEvent(candidateBuilder, event).build();

		candidateChangeHandler.onCandidateNewOrChange(supplyCandidate);
	}

	protected abstract CandidateBuilder updateBuilderFromEvent(CandidateBuilder candidateBuilder, PurchaseCandidateEvent event);

	protected abstract CandidatesQuery createCandidatesQuery(@NonNull final PurchaseCandidateEvent event);

	protected final CandidateBuilder createInitialBuilder()
	{
		return Candidate.builder()
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PURCHASE)
		;
	}
}
