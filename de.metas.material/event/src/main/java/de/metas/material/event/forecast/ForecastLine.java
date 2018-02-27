package de.metas.material.event.forecast;

import static de.metas.material.event.MaterialEventUtils.checkIdGreaterThanZero;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.material.event.commons.MaterialDescriptor;
import lombok.Builder;
import lombok.NonNull;
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
public class ForecastLine
{

	private int forecastLineId;

	MaterialDescriptor materialDescriptor;

	@JsonCreator
	public ForecastLine(
			@JsonProperty("forecastLineId") final int forecastLineId,
			@JsonProperty("materialDescriptor") @NonNull final MaterialDescriptor materialDescriptor)
	{
		this.forecastLineId = forecastLineId;
		this.materialDescriptor = materialDescriptor;
	}

	public void validate()
	{
		checkIdGreaterThanZero("forecastLineId", forecastLineId);
	}
}
