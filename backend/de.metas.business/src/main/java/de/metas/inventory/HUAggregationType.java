package de.metas.inventory;

import java.util.Objects;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
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

/**
 * NOTE to developers: Please keep in sync with list reference "HUAggregationType" {@code AD_Reference_ID=540976}
 */

public enum HUAggregationType implements ReferenceListAwareEnum
{
	SINGLE_HU("S"), //
	MULTI_HU("M") //
	;

	@Getter
	private final String code;

	HUAggregationType(final String code)
	{
		this.code = code;
	}

	public static HUAggregationType ofCode(@NonNull final String code)
	{
		final HUAggregationType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + HUAggregationType.class + " found for code: " + code);
		}
		return type;
	}

	public static HUAggregationType ofNullableCode(@Nullable final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static String toCodeOrNull(@Nullable final HUAggregationType type)
	{
		return type != null ? type.getCode() : null;
	}

	private static final ImmutableMap<String, HUAggregationType> typesByCode = ReferenceListAwareEnums.indexByCode(values());

	public static boolean equals(@Nullable final HUAggregationType o1, @Nullable final HUAggregationType o2)
	{
		return Objects.equals(o1, o2);
	}

}
