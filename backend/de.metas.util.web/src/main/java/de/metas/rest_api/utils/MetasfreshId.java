/*
 * #%L
 * de.metas.util.web
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

package de.metas.rest_api.utils;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;

@Value
public class MetasfreshId
{
	int value;

	public static MetasfreshId ofOrNull(final int id)
	{
		if (id <= 0)
		{
			return null;
		}
		return of(id);
	}

	public static MetasfreshId ofOrNull(@Nullable final RepoIdAware id)
	{
		if (id == null)
		{
			return null;
		}
		return new MetasfreshId(id.getRepoId());
	}

	public static MetasfreshId of(final int id)
	{
		return new MetasfreshId(id);
	}

	public static MetasfreshId of(@NonNull final JsonMetasfreshId id)
	{
		return new MetasfreshId(id.getValue());
	}

	@Nullable
	public static MetasfreshId ofOrNull(@Nullable final JsonMetasfreshId id)
	{
		if (id == null)
		{
			return null;
		}

		return of(id);
	}

	public static MetasfreshId of(@NonNull final RepoIdAware id)
	{
		return new MetasfreshId(id.getRepoId());
	}

	public static MetasfreshId ofNullable(@Nullable final RepoIdAware id)
	{
		return id != null ? new MetasfreshId(id.getRepoId()) : null;
	}

	public static int toValue(@Nullable final MetasfreshId id)
	{
		if (id == null)
		{
			return -1;
		}
		return id.getValue();
	}

	@JsonCreator
	private MetasfreshId(final int value)
	{
		this.value = Check.assumeGreaterOrEqualToZero(value, "value"); // zero occurs when e.g. an AD_was created by the system-user
	}

	@JsonValue
	public int getValue()
	{
		return value;
	}

	public boolean isEqualTo(@Nullable final RepoIdAware otherId)
	{
		if (otherId == null)
		{
			return false;
		}
		return otherId.getRepoId() == value;
	}

	public static boolean equals(@Nullable final MetasfreshId id1, @Nullable final MetasfreshId id2)
	{
		return Objects.equals(id1, id2);
	}

	@NonNull
	public <T extends RepoIdAware> T mapToRepoId(@NonNull final Function<Integer, T> mappingFunction)
	{
		return mappingFunction.apply(value);
	}
}
