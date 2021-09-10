/*
 * #%L
 * de.metas.elasticsearch
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

package de.metas.fulltextsearch.config;

import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class ESFieldName
{
	public static final ESFieldName ID = new ESFieldName(normalizeName("_id"));

	public static ESFieldName ofString(@NonNull final String name)
	{
		final String nameNorm = normalizeName(name);
		if (nameNorm == null)
		{
			throw new AdempiereException("Invalid name `" + name + "`");
		}
		else if (ID.name.equals(name))
		{
			return ID;
		}
		else
		{
			return new ESFieldName(nameNorm);
		}
	}

	@NonNull
	private final String name;

	public ESFieldName(@NonNull final String nameNorm)
	{
		this.name = nameNorm;
	}

	@Nullable
	private static String normalizeName(final @NonNull String name)
	{
		return StringUtils.trimBlankToNull(name);
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	public String getAsString()
	{
		return name;
	}
}
