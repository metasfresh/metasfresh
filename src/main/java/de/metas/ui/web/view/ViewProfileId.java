package de.metas.ui.web.view;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
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
public final class ViewProfileId
{
	public static final ViewProfileId NULL = null;

	public static boolean isNull(final ViewProfileId profileId)
	{
		return profileId == null || Objects.equals(profileId, NULL);
	}

	@JsonCreator
	public static final ViewProfileId fromJson(final String profileIdStr)
	{
		if (profileIdStr == null)
		{
			return NULL;
		}

		final String profileIdStrNorm = profileIdStr.trim();
		if (profileIdStrNorm.isEmpty())
		{
			return NULL;
		}

		return new ViewProfileId(profileIdStrNorm);
	}

	private final String id;

	private ViewProfileId(@NonNull final String id)
	{
		this.id = id;
	}

	@JsonValue
	public String toJson()
	{
		return id;
	}
}
