package de.metas.rest_api.dataentry.impl.dto;

import java.util.Arrays;

/*
 * #%L
 * metasfresh-pharma
 * %%
 * Copyright (C) 2018 metas GmbH
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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.dataentry.FieldType;
import lombok.Getter;

public enum JsonFieldType
{
	SUB_TAB_ID(FieldType.SUB_TAB_ID),
	PARENT_LINK_ID(FieldType.PARENT_LINK_ID),
	CREATED_UPDATED_INFO(FieldType.CREATED_UPDATED_INFO),
	TEXT(FieldType.TEXT),
	LONG_TEXT(FieldType.LONG_TEXT),
	NUMBER(FieldType.NUMBER),
	DATE(FieldType.DATE),
	LIST(FieldType.LIST),
	YESNO(FieldType.YESNO);

	@Getter
	private final FieldType fieldType;

	JsonFieldType(final FieldType fieldType)
	{
		this.fieldType = fieldType;
	}

	public static JsonFieldType getBy(FieldType fieldType)
	{
		final JsonFieldType type = map.get(fieldType);
		if (type == null)
		{
			throw new IllegalArgumentException(fieldType + " does not exist.");
		}
		return type;
	}

	private static final ImmutableMap<FieldType, JsonFieldType> map = Maps.uniqueIndex(Arrays.asList(values()), JsonFieldType::getFieldType);

}
