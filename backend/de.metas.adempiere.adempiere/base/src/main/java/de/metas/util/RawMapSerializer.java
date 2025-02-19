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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import lombok.NonNull;

import java.io.IOException;
import java.util.Map;

/**
 * Basic serializer which writes a raw value instead of conventional serialization of entries in a map. When using this, the convention is that:<br>
 * <ul>
 *     <li>If the key ends in {@value RAW_PROPERTY_SUFFIX}, then the raw value shall be used for serialization</li>
 *     <li>Otherwise the conventional serialization approach is used</li>
 * </ul>
 *
 * What's the point, you ask? {@link de.metas.event.Event} contains a properties map, in which we may want to store JSON (as in the case of material events).
 * This allows to have such events copy-paste friendly in the event log window.
 *
 */
public class RawMapSerializer extends JsonSerializer<Map<String, Object>>
{
	public static final String RAW_PROPERTY_SUFFIX = "_RAW";

	@Override
	public void serialize(@NonNull final Map<String, Object> properties, @NonNull final JsonGenerator gen, @NonNull final SerializerProvider serializers)
			throws IOException
	{
		gen.writeStartObject(); // Start the serialization

		for (final Map.Entry<String, Object> entry : properties.entrySet())
		{
			gen.writeFieldName(entry.getKey());

			if (entry.getValue() instanceof String && isRawProperty(entry.getKey()))
			{
				// Write raw JSON directly
				gen.writeRawValue((String)entry.getValue());
			}
			else
			{
				// Write as a regular object
				gen.writeObject(entry.getValue());
			}
		}

		gen.writeEndObject(); // End the serialization
	}

	private boolean isRawProperty(@NonNull final String key)
	{
		return key.endsWith(RAW_PROPERTY_SUFFIX);
	}
}
