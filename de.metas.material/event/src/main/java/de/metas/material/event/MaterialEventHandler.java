package de.metas.material.event;

import java.util.Collection;

/*
 * #%L
 * metasfresh-material-dispo-service
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
 * Implementors if this interface are registered to {@link MaterialEventHandlerRegistry}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <T> the sort of {@link MaterialEvent} which the handler shall handle in its {@link #handleEvent(MaterialEvent)} method.<br>
 *            May be an abstract type.
 */
public interface MaterialEventHandler<T extends MaterialEvent>
{
	/**
	 * @return The event (concrete) classes the respective implementation will be invoked on.
	 *         <p>
	 *         Note that:
	 *         <li>please don't return {@code null}
	 *         <li>the actual events' respective class will be {@code equal}ed against the returned class(es), so returning an abstract class here seems to be pointless.
	 *         <li>An empty result means "invoke on every event type"
	 */
	Collection<Class<? extends T>> getHandeledEventType();

	void handleEvent(T event);

	/**
	 * Implementors may validate the event before handling it. This allows for "dump" events that can be successfully posted even if the posting code is buggy etc.<br>
	 * Errors can be logged to the event log.
	 */
	default void validateEvent(T event)
	{
	}
}
