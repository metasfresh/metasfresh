package de.metas.material.dispo.service.event.handler;

import org.springframework.stereotype.Service;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.Candidate.CandidateBuilder;
import de.metas.material.dispo.Candidate.SubType;
import de.metas.material.dispo.DemandCandidateDetail;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.dispo.service.event.EventUtil;
import de.metas.material.event.forecast.Forecast;
import de.metas.material.event.forecast.ForecastEvent;
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
public class ForecastEventHandler
{
	private final CandidateChangeService candidateChangeHandler;

	/**
	 * 
	 * @param candidateChangeHandler
	 * 
	 */
	public ForecastEventHandler(@NonNull final CandidateChangeService candidateChangeHandler)
	{
		this.candidateChangeHandler = candidateChangeHandler;
	}

	public void handleForecastEvent(@NonNull final ForecastEvent event)
	{
		final Forecast forecast = event.getForecast();

		final CandidateBuilder candidateBuilder = Candidate.builderForEventDescr(event.getEventDescr())
				.status(EventUtil.getCandidateStatus(forecast.getDocStatus()))
				.type(Candidate.Type.STOCK_UP)
				.subType(SubType.FORECAST);

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
		candidateBuilder.materialDescr(forecastLine.getMaterialDescriptor())
				.reference(forecastLine.getReference())
				.demandDetail(DemandCandidateDetail.forForecastLineId(forecastLine.getForecastLineId()));
	}
}
