package de.metas.material.dispo.service.event.handler.pporder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateStatus;
import de.metas.material.dispo.commons.candidate.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.event.EventUtil;
import de.metas.material.dispo.service.event.handler.MaterialEventHandler;
import de.metas.material.event.pporder.PPOrderChangedDocStatusEvent;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-dispo-service
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
public class PPOrderDocStatusChangedHandler implements MaterialEventHandler<PPOrderChangedDocStatusEvent>
{
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	private final CandidateChangeService candidateChangeService;

	public PPOrderDocStatusChangedHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval,
			@NonNull final CandidateChangeService candidateChangeService)
	{
		this.candidateChangeService = candidateChangeService;
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;

	}

	@Override
	public Class<PPOrderChangedDocStatusEvent> getHandeledEventType()
	{
		return PPOrderChangedDocStatusEvent.class;
	}

	@Override
	public void handleEvent(@NonNull final PPOrderChangedDocStatusEvent ppOrderChangedDocStatusEvent)
	{
		final List<Candidate> candidatesForPPOrderId = PPOrderUtil
				.retrieveCandidatesForPPOrderId(
						candidateRepositoryRetrieval,
						ppOrderChangedDocStatusEvent.getPpOrderId());

		final CandidateStatus newCandidateStatus = EventUtil.getCandidateStatus(ppOrderChangedDocStatusEvent.getNewDocStatus());

		final Function<ProductionDetail, BigDecimal> provider = createdQtyProvider(newCandidateStatus);

		final List<Candidate> updatedCandidatesToPersist = new ArrayList<>();

		for (final Candidate candidateForPPOrderId : candidatesForPPOrderId)
		{
			final BigDecimal newQuantity = provider.apply(candidateForPPOrderId.getProductionDetail());
			final Candidate updatedCandidateToPersist = candidateForPPOrderId.toBuilder()
					.materialDescriptor(candidateForPPOrderId.getMaterialDescriptor().withQuantity(newQuantity))
					.build();

			updatedCandidatesToPersist.add(updatedCandidateToPersist);
		}

		updatedCandidatesToPersist.forEach(candidate -> candidateChangeService.onCandidateNewOrChange(candidate));
	}

	private Function<ProductionDetail, BigDecimal> createdQtyProvider(final CandidateStatus candidateStatus)
	{
		final Function<ProductionDetail, BigDecimal> provider;

		if (CandidateStatus.doc_closed.equals(candidateStatus))
		{
			// update candidates , take the "actualQty" instead of max(actual, planned)
			provider = productDetail -> productDetail.getActualQty();
		}
		else
		{
			// update candidates , take the max(actual, pplanned)
			provider = productDetail -> productDetail.getActualQty().max(productDetail.getPlannedQty());
		}
		return provider;
	}
}
