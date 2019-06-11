package de.metas.rest_api;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.Value;

/*
 * #%L
 * de.metas.business.rest-api
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class MetasfreshId
{
	long value;

	public static MetasfreshId ofOrNull(long id)
	{
		if (id <= 0)
		{
			return null;
		}
		return of(id);
	}

	public static MetasfreshId of(long id)
	{
		return new MetasfreshId(id);
	}

	@JsonCreator
	private MetasfreshId(long value)
	{
		this.value = Check.assumeGreaterThanZero(value, "value");
	}

	@JsonValue
	public long getValue()
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
}
