/*
 * #%L
 * de.metas.banking.camt53
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.banking.camt53;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * Contains the currently supported CAMT53 versions.
 */
public enum Camt53Version
{
	V02("camt.053.001.02"),
	V04("camt.053.001.04");

	@Getter
	private final String code;

	Camt53Version(@NonNull final String code)
	{
		this.code = code;
	}

	@NonNull
	public static Camt53Version ofCode(@NonNull final String code)
	{
		final Camt53Version type = ofCodeOrNull(code);
		if (type == null)
		{
			throw new AdempiereException("No " + Camt53Version.class + " found for code: " + code);
		}
		return type;
	}

	@Nullable
	public static Camt53Version ofCodeOrNull(@NonNull final String code)
	{
		return versionsByCode.get(code);
	}

	private static final ImmutableMap<String, Camt53Version> versionsByCode = Maps.uniqueIndex(Arrays.asList(values()), Camt53Version::getCode);
}
