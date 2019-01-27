package de.metas.vertical.pharma.msv3.server.peer.protocol;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.msv3.server
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class MSV3StockAvailabilityUpdatedEvent
{
	/** Delete all items that create created/updated according to a previous event version */
	public static MSV3StockAvailabilityUpdatedEvent deleteAllOlderThan(@NonNull final MSV3EventVersion eventVersion)
	{
		return builder()
				.eventVersion(eventVersion)
				.deleteAllOtherItems(true)
				.build();
	}

	public static MSV3StockAvailabilityUpdatedEvent ofSingle(
			@NonNull final MSV3StockAvailability msv3StockAvailability,
			@NonNull final MSV3EventVersion eventVersion)
	{
		return builder()
				.eventVersion(eventVersion)
				.item(msv3StockAvailability)
				.deleteAllOtherItems(false)
				.build();
	}

	private final String id;

	private final List<MSV3StockAvailability> items;

	private final boolean deleteAllOtherItems;

	/**
	 * The items of events with lower versions are discarded if the msv3-server was already updated based on an event with a higher version.
	 * Multiple events may have the same version.
	 */
	private MSV3EventVersion eventVersion;

	@JsonCreator
	@Builder
	private MSV3StockAvailabilityUpdatedEvent(
			@JsonProperty("id") final String id,
			@JsonProperty("items") @Singular final List<MSV3StockAvailability> items,
			@JsonProperty("deleteAllOtherItems") final boolean deleteAllOtherItems,
			@JsonProperty("eventVersion") @NonNull final MSV3EventVersion eventVersion)
	{
		this.id = id != null ? id : UUID.randomUUID().toString();
		this.items = items != null ? ImmutableList.copyOf(items) : ImmutableList.of();
		this.deleteAllOtherItems = deleteAllOtherItems;

		this.eventVersion = eventVersion;
	}
}
