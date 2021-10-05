package de.metas.material.dispo.service.event.handler.purchasecandidate;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.PurchaseDetailsQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.purchase.PurchaseCandidateEvent;
import de.metas.material.event.purchase.PurchaseCandidateUpdatedEvent;
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
public final class PurchaseCandidateUpdatedHandler
		extends PurchaseCandidateCreatedOrUpdatedHandler<PurchaseCandidateUpdatedEvent>
{
	/**
	 * @param candidateService needed in case we directly request a {@link PpOrderSuggestedEvent}'s proposed PP_Order to be created.
	 */
	public PurchaseCandidateUpdatedHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		super(candidateChangeHandler, candidateRepositoryRetrieval);
	}

	@Override
	public Collection<Class<? extends PurchaseCandidateUpdatedEvent>> getHandledEventType()
	{
		return ImmutableList.of(PurchaseCandidateUpdatedEvent.class);
	}

	@Override
	public void validateEvent(@NonNull final PurchaseCandidateUpdatedEvent event)
	{
		// nothing to do; the event was already validated on construction time
	}

	@Override
	public void handleEvent(@NonNull final PurchaseCandidateUpdatedEvent event)
	{
		handlePurchaseCandidateEvent(event);
	}

	@Override
	protected CandidatesQuery createCandidatesQuery(@NonNull final PurchaseCandidateEvent event)
	{
		final PurchaseDetailsQuery purchaseDetailsQuery = PurchaseDetailsQuery.builder()
				.purchaseCandidateRepoId(event.getPurchaseCandidateRepoId())
				.build();

		return CandidatesQuery.builder()
				.businessCase(CandidateBusinessCase.PURCHASE)
				.type(CandidateType.SUPPLY)
				.purchaseDetailsQuery(purchaseDetailsQuery)
				.build();
	}

	@Override
	protected CandidateBuilder updateBuilderFromEvent(
			@NonNull final CandidateBuilder candidateBuilder,
			@NonNull final PurchaseCandidateEvent event)
	{
		return candidateBuilder; // nothing to update
	}
}
