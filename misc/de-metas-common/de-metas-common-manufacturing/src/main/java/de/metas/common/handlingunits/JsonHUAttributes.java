/*
 * #%L
 * metasfresh
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

package de.metas.common.handlingunits;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Value
@Builder
@Jacksonized
public class JsonHUAttributes
{
	@NonNull List<JsonHUAttribute> list;

	public JsonHUAttributeCodeAndValues toJsonHUAttributeCodeAndValues()
	{
		final JsonHUAttributeCodeAndValues result = new JsonHUAttributeCodeAndValues();
		for (final JsonHUAttribute attribute : list)
		{
			result.putAttribute(attribute.getCode(), attribute.getValue());
		}
		return result;
	}

	public static JsonHUAttributes ofJsonHUAttributeCodeAndValues(@NonNull JsonHUAttributeCodeAndValues attributeCodeAndValues)
	{
		final ArrayList<JsonHUAttribute> list = new ArrayList<>();
		for (final Map.Entry<String, Object> attributeCodeAndValue : attributeCodeAndValues.getAttributes().entrySet())
		{
			list.add(JsonHUAttribute.builder()
					.code(attributeCodeAndValue.getKey())
					.caption(attributeCodeAndValue.getKey())
					.value(attributeCodeAndValue.getValue())
					.build());
		}

		return JsonHUAttributes.builder().list(list).build();
	}

}
