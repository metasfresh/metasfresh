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

import java.util.Collection;
import java.util.Objects;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import de.metas.common.rest_api.JsonExternalId;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonExternalIds
{
	public ExternalId toExternalIdOrNull(@Nullable final JsonExternalId jsonExternalId)
	{
		if (jsonExternalId == null)
		{
			return null;
		}
		return ExternalId.of(jsonExternalId.getValue());
	}

	public ExternalId toExternalId(@NonNull final JsonExternalId jsonExternalId)
	{
		return ExternalId.of(jsonExternalId.getValue());
	}

	public ImmutableList<ExternalId> toExternalIds(@NonNull final Collection<JsonExternalId> externalLineIds)
	{
		return externalLineIds
				.stream()
				.map(JsonExternalIds::toExternalId)
				.collect(ImmutableList.toImmutableList());
	}

	public JsonExternalId of(@NonNull final ExternalId externalId)
	{
		return JsonExternalId.of(externalId.getValue());
	}

	public JsonExternalId ofOrNull(@Nullable final ExternalId externalId)
	{
		if (externalId == null)
		{
			return null;
		}
		return JsonExternalId.of(externalId.getValue());
	}

	public boolean equals(@Nullable final JsonExternalId id1, @Nullable final JsonExternalId id2)
	{
		return Objects.equals(id1, id2);
	}

	public static boolean isEqualTo(
			@Nullable final JsonExternalId jsonExternalId,
			@Nullable final ExternalId externalId)
	{
		if (jsonExternalId == null && externalId == null)
		{
			return true;
		}
		if (jsonExternalId == null ^ externalId == null)
		{
			return false; // one is null, the other one isn't
		}
		return Objects.equals(jsonExternalId.getValue(), externalId.getValue());
	}
}
