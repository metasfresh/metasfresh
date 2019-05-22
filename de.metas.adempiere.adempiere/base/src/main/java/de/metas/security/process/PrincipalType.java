package de.metas.security.process;

import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public enum PrincipalType implements ReferenceListAwareEnum
{
	USER("U"), //
	USER_GROUP("G") //
	;

	@Getter
	private final String code;

	private PrincipalType(@NonNull final String code)
	{
		this.code = code;
	}

	public static PrincipalType ofCode(@NonNull final String code)
	{
		final PrincipalType type = byCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("@Unknown@ @PrincipalType@: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, PrincipalType> byCode = Maps.uniqueIndex(Arrays.asList(values()), PrincipalType::getCode);
}
