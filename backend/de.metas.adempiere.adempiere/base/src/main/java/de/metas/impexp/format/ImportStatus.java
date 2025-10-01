/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.impexp.format;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;

public enum ImportStatus implements ReferenceListAwareEnum
{
	NOT_IMPORTED("N"),
	IMPORTED("Y"),
	ERROR("E");

	private final String code;

	ImportStatus(@NonNull final String code)
	{
		this.code = code;
	}

	@Override
	public String getCode()
	{
		return code;
	}

	public static ImportStatus ofCode(@NonNull final String code)
	{
		final ImportStatus type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + ImportStatus.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, ImportStatus> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), ImportStatus::getCode);
}