package de.metas.impexp.format;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_AD_ImpFormat;

import java.util.Arrays;

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

public enum ImpFormatType implements ReferenceListAwareEnum
{
	FIXED_POSITION(X_AD_ImpFormat.FORMATTYPE_FixedPosition), //
	COMMA_SEPARATED(X_AD_ImpFormat.FORMATTYPE_CommaSeparated), //
	SEMICOLON_SEPARATED(X_AD_ImpFormat.FORMATTYPE_SemicolonSeparated), //
	TAB_SEPARATED(X_AD_ImpFormat.FORMATTYPE_TabSeparated), //
	XML(X_AD_ImpFormat.FORMATTYPE_XML) //
	;

	private final String code;

	private ImpFormatType(@NonNull final String code)
	{
		this.code = code;
	}

	@Override
	@JsonValue
	public String getCode()
	{
		return code;
	}

	public char getCellDelimiterChar()
	{
		switch (this)
		{
			case COMMA_SEPARATED:
				return ',';
			case SEMICOLON_SEPARATED:
				return ';';
			case TAB_SEPARATED:
				return '\t';
			default:
				throw new AdempiereException("Cannot find delimiter for " + this);
		}
	}

	@JsonCreator
	public static ImpFormatType ofCode(@NonNull final String code)
	{
		ImpFormatType type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + ImpFormatType.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, ImpFormatType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), ImpFormatType::getCode);
}
