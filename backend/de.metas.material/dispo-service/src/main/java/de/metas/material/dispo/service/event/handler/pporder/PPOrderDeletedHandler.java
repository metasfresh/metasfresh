package de.metas.material.dispo.service.event.handler.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.pporder.PPOrderDeletedEvent;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
public final class PPOrderDeletedHandler
		implements MaterialEventHandler<PPOrderDeletedEvent>
{
	private final CandidateChangeService candidateChangeService;
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	public PPOrderDeletedHandler(
			@NonNull final CandidateChangeService candidateChangeService,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		this.candidateChangeService = candidateChangeService;
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
	}

	@Override
	public Collection<Class<? extends PPOrderDeletedEvent>> getHandledEventType()
	{
		return ImmutableList.of(PPOrderDeletedEvent.class);
	}

	/**
	 * Deletes all (non-simulated) candidates of the event's PP_Order: the header supply and every BOM-line candidate.
	 * <p>
	 * Note: on void, the PP_Order's BOM lines are recreated with new IDs and a {@link de.metas.material.event.pporder.PPOrderChangedEvent} is posted.
	 * If that event is handled before this one, the line candidates already point to the new BOM-line IDs, i.e. this event's (pre-void) BOM-line IDs no longer match them.
	 * That's why we select the candidates by PP_Order_ID and not by BOM-line ID.
	 */
	@Override
	public void handleEvent(@NonNull final PPOrderDeletedEvent event)
	{
		final Map<Boolean, List<Candidate>> candidatesByIsHeader = candidateRepositoryRetrieval.retrieveCandidatesForPPOrderId(event.getPpOrder().getPpOrderId())
				.stream()
				.collect(Collectors.partitioningBy(PPOrderDeletedHandler::isHeaderCandidate));

		// BOM-line candidates first, then the header
		candidatesByIsHeader.get(false).forEach(candidateChangeService::onCandidateDelete);
		candidatesByIsHeader.get(true).forEach(candidateChangeService::onCandidateDelete);
	}

	private static boolean isHeaderCandidate(@NonNull final Candidate candidate)
	{
		return ProductionDetail.cast(candidate.getBusinessCaseDetail()).isFinishedGoods();
	}
}
