/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.material.dispo.service.event.handler.foreacast;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DemandDetailsQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.forecast.ForecastDeletedEvent;
import de.metas.material.event.forecast.ForecastLine;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
@RequiredArgsConstructor
public class ForecastDeletedHandler implements MaterialEventHandler<ForecastDeletedEvent>
{
	@NonNull
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;
	@NonNull
	private final CandidateChangeService candidateChangeHandler;

	@Override
	public Collection<Class<? extends ForecastDeletedEvent>> getHandledEventType()
	{
		return ImmutableList.of(ForecastDeletedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final ForecastDeletedEvent event)
	{
		deleteCandidates(event);
	}

	private void deleteCandidates(@NonNull final ForecastDeletedEvent event)
	{
		event.getForecast().getForecastLines().forEach(this::deleteCandidates);
	}

	private void deleteCandidates(@NonNull final ForecastLine forecastLine)
	{
		final CandidatesQuery query = CandidatesQuery
				.builder()
				.type(CandidateType.STOCK_UP)
				.businessCase(CandidateBusinessCase.FORECAST)
				.demandDetailsQuery(DemandDetailsQuery.ofForecastLineId(forecastLine.getForecastLineId()))
				.build();

		candidateRepositoryRetrieval.retrieveOrderedByDateAndSeqNo(query)
				.forEach(candidateChangeHandler::onCandidateDelete);
	}
}
