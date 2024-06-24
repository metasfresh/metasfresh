/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.material.dispo.service.event.handler.ppordercandidate;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.ProductionDetailsQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.pporder.PPOrderCandidate;
import de.metas.material.event.pporder.PPOrderCandidateDeletedEvent;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public class PPOrderCandidateDeletedEventHandler extends PPOrderCandidateEventHandler implements MaterialEventHandler<PPOrderCandidateDeletedEvent>
{
	public PPOrderCandidateDeletedEventHandler(
			@NonNull final CandidateChangeService candidateChangeService,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		super(candidateChangeService, candidateRepositoryRetrieval);
	}

	@Override
	public Collection<Class<? extends PPOrderCandidateDeletedEvent>> getHandledEventType()
	{
		return ImmutableList.of(PPOrderCandidateDeletedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final PPOrderCandidateDeletedEvent event)
	{
		final CandidatesQuery preExistingHeaderSupplyQuery = createPreExistingHeaderCandidateQuery(event);

		final Candidate existingCandidateOrNull = candidateRepositoryRetrieval.retrieveLatestMatchOrNull(preExistingHeaderSupplyQuery);

		if (existingCandidateOrNull != null)
		{
			deleteLineCandidates(event, existingCandidateOrNull);
			candidateChangeService.onCandidateDelete(existingCandidateOrNull);
		}
	}

	@NonNull
	private CandidatesQuery createPreExistingHeaderCandidateQuery(@NonNull final PPOrderCandidateDeletedEvent ppOrderCandidateDeletedEvent)
	{
		final PPOrderCandidate ppOrderCandidate = ppOrderCandidateDeletedEvent.getPpOrderCandidate();

		return CandidatesQuery
				.builder()
				.productionDetailsQuery(ProductionDetailsQuery.builder()
						.ppOrderCandidateId(ppOrderCandidate.getPpOrderCandidateId())
						.build())
				.build();
	}

}