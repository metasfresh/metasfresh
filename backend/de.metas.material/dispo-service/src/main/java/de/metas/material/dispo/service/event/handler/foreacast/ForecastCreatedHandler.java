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

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.forecast.Forecast;
import de.metas.material.event.forecast.ForecastCreatedEvent;
import de.metas.material.event.forecast.ForecastLine;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import de.metas.util.Services;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Profile(Profiles.PROFILE_MaterialDispo)
public class ForecastCreatedHandler implements MaterialEventHandler<ForecastCreatedEvent>
{
	private static final Logger logger = LogManager.getLogger(ForecastCreatedHandler.class);

	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final CandidateChangeService candidateChangeHandler;

	public ForecastCreatedHandler(@NonNull final CandidateChangeService candidateChangeHandler)
	{
		this.candidateChangeHandler = candidateChangeHandler;
	}

	@Override
	public Collection<Class<? extends ForecastCreatedEvent>> getHandledEventType()
	{
		return ImmutableList.of(ForecastCreatedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final ForecastCreatedEvent event)
	{
		final Forecast forecast = event.getForecast();

		final CandidateBuilder candidateBuilder = Candidate.builderForEventDescriptor(event.getEventDescriptor())
				//.status(EventUtil.getCandidateStatus(forecast.getDocStatus()))
				.type(CandidateType.STOCK_UP)
				.businessCase(CandidateBusinessCase.FORECAST);

		for (final ForecastLine forecastLine : forecast.getForecastLines())
		{
			final WarehouseId lineWarehouseId = forecastLine.getMaterialDescriptor().getWarehouseId();
			if (warehouseBL.isIgnoreInMaterialDispo(lineWarehouseId))
			{
				Loggables.withLogger(logger, Level.DEBUG).addLog(
						"Ignoring forecast line {} for M_Warehouse_ID={} (warehouse is excluded from material-dispo)",
						forecastLine.getForecastLineId(),
						WarehouseId.toRepoId(lineWarehouseId));
				continue;
			}

			complementBuilderFromForecastLine(candidateBuilder, forecast, forecastLine);

			final Candidate demandCandidate = candidateBuilder.build();
			candidateChangeHandler.onCandidateNewOrChange(demandCandidate);
		}
	}

	private void complementBuilderFromForecastLine(
			@NonNull final CandidateBuilder candidateBuilder,
			@NonNull final Forecast forecast,
			@NonNull final ForecastLine forecastLine)
	{
		candidateBuilder
				.materialDescriptor(forecastLine.getMaterialDescriptor())
				.businessCaseDetail(DemandDetail.forForecastLineId(
						forecastLine.getForecastLineId(),
						forecast.getForecastId(),
						forecastLine.getMaterialDescriptor().getQuantity()));
	}
}
