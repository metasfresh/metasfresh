package de.metas.ui.web.window.datatypes.json;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Serialize a {@link Map} as an list/array of it's values.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class JsonMapAsValuesListSerializer extends JsonSerializer<Map<?, ?>>
{
	@Override
	public void serialize(final Map<?, ?> map, final JsonGenerator gen, final SerializerProvider serializers) throws IOException, JsonProcessingException
	{
		// NOTE: we assume we never get a null map
		gen.writeObject(map.values());
	}

	@Override
	public boolean isEmpty(final SerializerProvider provider, final Map<?, ?> value)
	{
		return value == null || value.isEmpty();
	}
}
