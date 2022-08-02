/*
 * #%L
 * de.metas.workflow.rest-api
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

package de.metas.workflow.rest_api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Objects;

@EqualsAndHashCode
public final class WFActivityId
{
	@JsonCreator
	public static WFActivityId ofString(@NonNull final String value)
	{
		return new WFActivityId(value);
	}

	public static <T extends RepoIdAware> WFActivityId ofId(@NonNull final T id)
	{
		return new WFActivityId(String.valueOf(id.getRepoId()));
	}

	private final String value;

	private WFActivityId(@NonNull final String value) {this.value = Check.assumeNotEmpty(value, "value");}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	@JsonValue
	public String getAsString()
	{
		return value;
	}

	public <T extends RepoIdAware> T getAsId(@NonNull final Class<T> type)
	{
		return RepoIdAwares.ofObject(value, type);
	}

	public static boolean equals(@Nullable final WFActivityId o1, @Nullable final WFActivityId o2)
	{
		return Objects.equals(o1, o2);
	}
}
