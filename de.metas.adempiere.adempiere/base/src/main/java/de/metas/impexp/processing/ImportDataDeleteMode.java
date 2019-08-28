package de.metas.impexp.processing;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableMap;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
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

public enum ImportDataDeleteMode implements ReferenceListAwareEnum
{
	ONLY_SELECTED("S"), //
	ONLY_IMPORTED("I"), //
	ALL("A") //
	;

	@Getter
	private String code;

	private ImportDataDeleteMode(@NonNull final String code)
	{
		this.code = code;
	}

	public static ImportDataDeleteMode ofCode(@NonNull final String code)
	{
		ImportDataDeleteMode type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + ImportDataDeleteMode.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, ImportDataDeleteMode> typesByCode = ReferenceListAwareEnums.indexByCode(values());
}
