/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.event;

import lombok.NonNull;

public interface EventEnqueuer
{
	void enqueueDistributedEvent(Event event, Topic topic);

	void enqueueLocalEvent(Event event, Topic topic);

	/**
	 * Runs the given code and sends all events that it enqueues on the current thread as one batch, in the order in which they were enqueued.
	 * <p>
	 * Use it for events whose consumers rely on their order, e.g. all events of one commit.
	 * A nested call adds its events to the outer batch.
	 */
	void enqueueEventsInOneBatch(@NonNull Runnable enqueueEvents);
}
