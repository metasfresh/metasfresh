package de.metas.material.dispo.service.event.handler;

import java.util.Collection;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.DemandDetail;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.event.EventUtil;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.forecast.Forecast;
import de.metas.material.event.forecast.ForecastCreatedEvent;
import de.metas.material.event.forecast.ForecastLine;
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
public class ForecastCreatedHandler implements MaterialEventHandler<ForecastCreatedEvent>
{
	private final CandidateChangeService candidateChangeHandler;

	public ForecastCreatedHandler(@NonNull final CandidateChangeService candidateChangeHandler)
	{
		this.candidateChangeHandler = candidateChangeHandler;
	}

	@Override
	public Collection<Class<? extends ForecastCreatedEvent>> getHandeledEventType()
	{
		return ImmutableList.of(ForecastCreatedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final ForecastCreatedEvent event)
	{
		final Forecast forecast = event.getForecast();

		final CandidateBuilder candidateBuilder = Candidate.builderForEventDescr(event.getEventDescriptor())
				.status(EventUtil.getCandidateStatus(forecast.getDocStatus()))
				.type(CandidateType.STOCK_UP)
				.businessCase(CandidateBusinessCase.FORECAST);

		for (final ForecastLine forecastLine : forecast.getForecastLines())
		{
			complementBuilderFromForecastLine(candidateBuilder, forecastLine);

			final Candidate demandCandidate = candidateBuilder.build();
			candidateChangeHandler.onCandidateNewOrChange(demandCandidate);
		}
	}

	private void complementBuilderFromForecastLine(
			@NonNull final CandidateBuilder candidateBuilder,
			@NonNull final ForecastLine forecastLine)
	{
		candidateBuilder
				.materialDescriptor(forecastLine.getMaterialDescriptor())
				.demandDetail(DemandDetail.forForecastLineId(forecastLine.getForecastLineId()));
	}
}
