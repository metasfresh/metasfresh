package de.metas.material.event.eventbus;

import de.metas.event.Event;
import de.metas.material.event.MaterialEvent;
import de.metas.util.JSONObjectMapper;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import java.util.Map;

import static de.metas.util.RawMapSerializer.RAW_PROPERTY_SUFFIX;

/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Converts {@link Event}s to {@link MaterialEvent}s and vice versa.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Service
public class MaterialEventConverter
{
	private static final String PROPERTY_MATERIAL_EVENT = "MaterialEvent" + RAW_PROPERTY_SUFFIX;

	private final JSONObjectMapper<MaterialEvent> jsonObjectMapper;

	public MaterialEventConverter()
	{
		jsonObjectMapper = JSONObjectMapper.forClass(MaterialEvent.class);
	}

	public MaterialEvent toMaterialEvent(@NonNull final Event metasfreshEvent)
	{
		final Object materialEventObj = metasfreshEvent.getProperty(PROPERTY_MATERIAL_EVENT);

		if (materialEventObj instanceof Map)
		{
			@SuppressWarnings("unchecked") final Map<String, Object> valueMap = (Map<String, Object>)materialEventObj;

			// Check if the valueMap contains the "type" field for a MaterialEvent
			if (valueMap.containsKey("type"))
			{
				// Deserialize the map into a MaterialEvent subclass
				return jsonObjectMapper.convertValue(valueMap);
			}
		}

		return null;
	}

	/**
	 * Note: the returned metasfresh event shall be logged.
	 */
	public Event fromMaterialEvent(@NonNull final MaterialEvent materialEvent)
	{
		final String eventStr = jsonObjectMapper.writeValueAsString(materialEvent);

		return Event.builder()
				.putProperty(PROPERTY_MATERIAL_EVENT, eventStr)
				.setEventName(materialEvent.getEventName())
				.setSourceRecordReference(materialEvent.getSourceTableReference())
				.shallBeLogged()
				.build();
	}
}
