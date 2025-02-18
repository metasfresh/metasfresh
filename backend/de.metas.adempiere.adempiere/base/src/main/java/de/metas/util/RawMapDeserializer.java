/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.NonNull;

import java.io.IOException;
import java.util.Map;

/**
 * Basic deserializer, which deserializes into a Map, to be later converted as needed
 * This is required because the parser already interprets unescaped (aka Raw) objects
 *
 * @see RawMapSerializer
 */
public class RawMapDeserializer extends JsonDeserializer<Map<String, Object>>
{
	@Override
	public Map<String, Object> deserialize(@NonNull final JsonParser parser, @NonNull final DeserializationContext ctx)
			throws IOException
	{
		// Deserialize into a map, which can be later converted into actual objects, if needed
		// For a basic implementation of delayed conversion, see de.metas.material.event.eventbus.MaterialEventConverter.toMaterialEvent
		return parser.readValueAs(new TypeReference<Map<String, Object>>() {});
	}

}
