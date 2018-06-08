package de.metas.vertical.pharma.msv3.server.peer.protocol;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.msv3.server-peer
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class MSV3ProductExcludesUpdateEvent
{
	@JsonProperty("id")
	private final String id;

	@JsonProperty("items")
	private final List<MSV3ProductExclude> items;

	@JsonProperty("deleteAllOtherItems")
	private final boolean deleteAllOtherItems;

	@JsonCreator
	@Builder
	private MSV3ProductExcludesUpdateEvent(
			@JsonProperty("id") final String id,
			@JsonProperty("items") @Singular final List<MSV3ProductExclude> items,
			@JsonProperty("deleteAllOtherItems") final boolean deleteAllOtherItems)
	{
		this.id = id != null ? id : UUID.randomUUID().toString();
		this.items = items != null ? ImmutableList.copyOf(items) : ImmutableList.of();
		this.deleteAllOtherItems = deleteAllOtherItems;
	}
}
