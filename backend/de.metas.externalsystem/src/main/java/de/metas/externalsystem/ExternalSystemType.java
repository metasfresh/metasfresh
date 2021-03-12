package de.metas.externalsystem;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.externalsystem.model.X_ExternalSystem_Config;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;

/*
 * #%L
 * de.metas.swat.base
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

public enum ExternalSystemType implements ReferenceListAwareEnum
{
	Alberta(X_ExternalSystem_Config.TYPE_Alberta, "Alberta");

	@Getter
	private final String code;

	@Getter
	private final String name;

	ExternalSystemType(@NonNull final String code, final String name)
	{
		this.code = code;
		this.name = name;
	}

	@Nullable
	public static ExternalSystemType ofNullableCode(final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static ExternalSystemType ofCode(@NonNull final String code)
	{
		final ExternalSystemType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + ExternalSystemType.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, ExternalSystemType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), ExternalSystemType::getCode);
}
