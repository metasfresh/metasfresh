package de.metas.material.event.pporder;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.EqualsAndHashCode;

import javax.annotation.Nullable;

/*
 * #%L
 * metasfresh-material-event
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

@EqualsAndHashCode
public class MaterialDispoGroupId
{
	@JsonCreator
	public static MaterialDispoGroupId ofInt(final int value)
	{
		return new MaterialDispoGroupId(value);
	}

	public static MaterialDispoGroupId ofIntOrNull(@Nullable final Integer value)
	{
		return value != null && value > 0 ? ofInt(value) : null;
	}

	public static MaterialDispoGroupId ofIdOrNull(final RepoIdAware id)
	{
		return id != null ? ofIntOrNull(id.getRepoId()) : null;
	}

	private final int value;

	private MaterialDispoGroupId(final int value)
	{
		Check.assumeGreaterThanZero(value, "value");
		this.value = value;
	}

	@Override
	public String toString()
	{
		return String.valueOf(value);
	}

	@JsonValue
	public int toInt()
	{
		return value;
	}

	public static int toInt(@Nullable final MaterialDispoGroupId groupId) {return groupId != null ? groupId.toInt() : -1;}
}
