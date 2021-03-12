/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.mforecast.impl;

import de.metas.document.dimension.Dimension;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;

@Value
public class ForecastLineId
{
	int repoId;

	@NonNull
	ForecastId forecastId;

	public static ForecastLineId ofRepoId(@NonNull final ForecastId forecasteId, final int forecastlineId)
	{
		return new ForecastLineId(forecasteId, forecastlineId);
	}

	public static ForecastLineId ofRepoId(final int forecastId, final int forecastLineId)
	{
		return new ForecastLineId(ForecastId.ofRepoId(forecastId), forecastLineId);
	}

	public static ForecastLineId ofRepoIdOrNull(
			@Nullable final Integer forecastId,
			@Nullable final Integer forecastLineId)
	{
		return forecastId != null && forecastId > 0 && forecastLineId != null && forecastLineId > 0
				? ofRepoId(forecastId, forecastLineId)
				: null;
	}

	public static ForecastLineId ofRepoIdOrNull(
			@Nullable final ForecastId forecastId,
			final int forecastLineId)
	{
		return forecastId != null && forecastLineId > 0 ? ofRepoId(forecastId, forecastLineId) : null;
	}

	private ForecastLineId(@NonNull final ForecastId forecastId, final int forecastLineId)
	{
		this.repoId = Check.assumeGreaterThanZero(forecastLineId, "forecastLineId");
		this.forecastId = forecastId;
	}

	public static boolean equals(final ForecastLineId id1, final ForecastLineId id2)
	{
		return Objects.equals(id1, id2);
	}

}
