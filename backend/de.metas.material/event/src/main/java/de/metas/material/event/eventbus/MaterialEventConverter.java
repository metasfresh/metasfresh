package de.metas.material.event.eventbus;

import de.metas.async.QueueWorkPackageId;
import de.metas.event.Event;
import de.metas.material.event.MaterialEvent;
import de.metas.util.JSONObjectMapper;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;

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
 *
 */
@Service
public class MaterialEventConverter
{
	private static final String PROPERTY_MATERIAL_EVENT = "MaterialEvent";

	private final JSONObjectMapper<MaterialEvent> jsonObjectMapper;

	public MaterialEventConverter()
	{
		jsonObjectMapper = JSONObjectMapper.forClass(MaterialEvent.class);
	}

	public MaterialEvent toMaterialEvent(@NonNull final Event metasfreshEvent)
	{
		final String materialEventStr = metasfreshEvent.getProperty(PROPERTY_MATERIAL_EVENT);

		return jsonObjectMapper.readValue(materialEventStr);
	}

	/**
	 * Note: the returned metasfresh event shall be logged.
	 */
	public Event fromMaterialEvent(@NonNull final MaterialEvent materialEvent, @Nullable final QueueWorkPackageId queueWorkPackageId)
	{
		final String eventStr = jsonObjectMapper.writeValueAsString(materialEvent);

		final Event.Builder builder = Event.builder()
				.putProperty(PROPERTY_MATERIAL_EVENT, eventStr)
				.shallBeLogged();
		if (queueWorkPackageId != null)
		{
			builder.setQueueWorkPackageId(queueWorkPackageId);
		}
		return builder
				.build();
	}
}
