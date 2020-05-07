package de.metas.material.event.forecast;

import static de.metas.material.event.MaterialEventUtils.checkIdGreaterThanZero;

import java.util.List;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-event
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

@Value
@Builder
public class Forecast
{
	int forecastId;

	String docStatus;

	@Singular
	List<ForecastLine> forecastLines;

	@JsonCreator
	public Forecast(
			@JsonProperty("forecastId") final int forecastId,
			@JsonProperty("docStatus") @NonNull final String docStatus,
			@JsonProperty("forecastLines") final List<ForecastLine> forecastLines)
	{
		this.forecastId = checkIdGreaterThanZero("forecastId", forecastId);
		this.docStatus = docStatus;
		this.forecastLines = forecastLines;
	}

	public void validate()
	{
		checkIdGreaterThanZero("forecastId", forecastId);
		try
		{
			forecastLines.forEach(ForecastLine::validate);
		}
		catch (final RuntimeException e)
		{
			AdempiereException.wrapIfNeeded(e).appendParametersToMessage()
					.setParameter("forecast", this);
		}
	}

}
