/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.printing;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.printing.model.X_AD_PrinterHW;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import java.util.Arrays;

public enum OutputType implements ReferenceListAwareEnum
{
	Attach(X_AD_PrinterHW.OUTPUTTYPE_Attach), //
	Queue(X_AD_PrinterHW.OUTPUTTYPE_Queue), //
	Store(X_AD_PrinterHW.OUTPUTTYPE_Store);

	@Getter
	private final String code;

	private OutputType(@NonNull final String code)
	{
		this.code = code;
	}

	public static OutputType ofNullableCode(final String code)
	{
		return code != null ? ofCode(code) : null;
	}

	public static OutputType ofCode(@NonNull final String code)
	{
		OutputType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + OutputType.class + " found for code: " + code);
		}
		return type;
	}

	public static String toCodeOrNull(final OutputType type)
	{
		return type != null ? type.getCode() : null;
	}

	public boolean isStore()
	{
		return this == Store;
	}

	private static final ImmutableMap<String, OutputType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), OutputType::getCode);


}
