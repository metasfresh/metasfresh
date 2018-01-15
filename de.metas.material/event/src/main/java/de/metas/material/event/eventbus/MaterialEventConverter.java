package de.metas.material.event.eventbus;

import org.springframework.stereotype.Service;

import de.metas.event.Event;
import de.metas.event.SimpleObjectSerializer;
import de.metas.event.log.EventLogUserService;
import de.metas.material.event.MaterialEvent;
import lombok.NonNull;

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

@Service
public class MaterialEventConverter
{
	public static final String PROPERTY_MATERIAL_EVENT = "MaterialEvent";

	private final EventLogUserService eventLogUserService;

	public MaterialEventConverter(@NonNull final EventLogUserService eventLogUserService)
	{
		this.eventLogUserService = eventLogUserService;
	}

	public MaterialEvent toMaterialEvent(@NonNull final Event metasfreshEvent)
	{
		final String lightWeigthEventStr = metasfreshEvent.getProperty(PROPERTY_MATERIAL_EVENT);

		final MaterialEvent lightWeightEvent = SimpleObjectSerializer.get()
				.deserialize(lightWeigthEventStr, MaterialEvent.class);
		return lightWeightEvent;
	}

	/**
	 * Note: the returned metasfresh event shall be logged.
	 *
	 * @param event
	 * @return
	 */
	public Event fromMaterialEvent(@NonNull final MaterialEvent event)
	{
		final String eventStr = SimpleObjectSerializer.get().serialize(event);

		final Event.Builder metasfreshEventBuilder = Event.builder()
				.putProperty(PROPERTY_MATERIAL_EVENT, eventStr);

		final Event metasfreshEvent = eventLogUserService
				.addEventLogAdvise(metasfreshEventBuilder, true)
				.build();

		return metasfreshEvent;
	}
}
