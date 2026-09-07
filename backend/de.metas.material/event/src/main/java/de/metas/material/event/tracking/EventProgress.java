/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.material.event.tracking;

import lombok.NonNull;
import lombok.Value;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import static de.metas.material.event.tracking.EventStatus.PROCESSED;

@Value
public class EventProgress
{
	@NonNull
	Map<String, EventStatus> eventId2Status;
	@NonNull
	CompletableFuture<Void> completableFuture;

	public EventProgress()
	{
		this.eventId2Status = new ConcurrentHashMap<>();
		this.completableFuture = new CompletableFuture<>();
	}

	public void enqueue(@NonNull final String eventId)
	{
		eventId2Status.put(eventId, EventStatus.ENQUEUED);
	}

	public void markAsProcessed(@NonNull final String eventId)
	{
		eventId2Status.put(eventId, PROCESSED);
	}

	public boolean areAllEventsProcessed()
	{
		// allMatch(..) is vacuously true on an empty map, so without the isEmpty() check a progress with nothing
		// enqueued would report "all processed". That is reachable because the completion check does not run inline:
		// it runs from a deferred AFTER_COMMIT handler that re-looks-up the EventProgress by traceId at fire time
		// (MaterialEventObserver.notifyIfAllEventsProcessed) instead of closing over the instance it was registered
		// for. If the entry under that traceId is removed (awaitProcessing's finally, e.g. on timeout) and a new one
		// observed before the handler fires, the lookup finds a FRESH, still-empty progress -- and announcing
		// completion for it would release an awaiter whose work has not even been enqueued yet.
		return !eventId2Status.isEmpty()
				&& eventId2Status.values()
						.stream()
						.allMatch(PROCESSED::equals);
	}
}