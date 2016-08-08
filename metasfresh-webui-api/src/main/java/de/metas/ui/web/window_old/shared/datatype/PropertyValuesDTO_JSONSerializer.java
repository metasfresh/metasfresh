package de.metas.ui.web.window_old.shared.datatype;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import de.metas.ui.web.json.JsonHelper;
import de.metas.ui.web.window_old.PropertyName;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class PropertyValuesDTO_JSONSerializer extends JsonSerializer<PropertyValuesDTO>
{
	public static final String PROPERTY_PropertyName = "n";
	public static final String PROPERTY_ValueType = "vt";
	public static final String PROPERTY_Value = "v";

	@Override
	public void serialize(final PropertyValuesDTO propertyValues, final JsonGenerator gen, final SerializerProvider serializers) throws IOException, JsonProcessingException
	{
		gen.writeStartArray();

		for (final Map.Entry<PropertyName, Object> entry : propertyValues.entrySet())
		{
			gen.writeStartObject();

			final PropertyName propertyName = entry.getKey();
			gen.writeObjectField(PROPERTY_PropertyName, propertyName);

			final Object value = entry.getValue();
			if (NullValue.isNull(value))
			{
				gen.writeObjectField(PROPERTY_ValueType, JsonHelper.VALUETYPENAME_NULL);
			}
			else
			{
				final String type = JsonHelper.extractValueTypeName(value);
				gen.writeObjectField(PROPERTY_ValueType, type);
				gen.writeObjectField(PROPERTY_Value, value);
			}

			gen.writeEndObject();
		}

		gen.writeEndArray();
		// TODO Auto-generated method stub

	}

}
