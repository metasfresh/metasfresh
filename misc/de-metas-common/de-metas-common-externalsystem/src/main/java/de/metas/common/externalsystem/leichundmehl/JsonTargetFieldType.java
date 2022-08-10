/*
 * #%L
 * de-metas-common-externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.common.externalsystem.leichundmehl;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;

//dev-note to be kept in sync with AD_Reference_ID=541611
public enum JsonTargetFieldType
{
	TextArea("textArea"),
	EAN13("EAN13"),
	EAN128("EAN128"),
	NumberField("numberField"),
	Date("date"),
	UnitChar("unitChar"),
	Graphic("graphic");

	@Getter
	private final String code;

	JsonTargetFieldType(@NonNull final String code)
	{
		this.code = code;
	}

	@NonNull
	public static JsonTargetFieldType ofCode(@NonNull final String code)
	{
		final JsonTargetFieldType targetFieldType = typesByCode.get(code);

		if (targetFieldType == null)
		{
			throw new IllegalArgumentException("JsonTargetFieldType does not support code: " + code);
		}
		return targetFieldType;
	}

	private static final ImmutableMap<String, JsonTargetFieldType> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), JsonTargetFieldType::getCode);
}
