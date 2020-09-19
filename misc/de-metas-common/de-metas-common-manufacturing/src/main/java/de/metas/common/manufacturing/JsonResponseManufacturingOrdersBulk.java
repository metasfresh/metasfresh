package de.metas.common.manufacturing;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2020 metas GmbH
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
@Builder
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@JsonDeserialize(builder = JsonResponseManufacturingOrdersBulk.JsonResponseManufacturingOrdersBulkBuilder.class)
public class JsonResponseManufacturingOrdersBulk
{
	@NonNull
	String transactionKey;

	@NonNull
	@Singular
	List<JsonResponseManufacturingOrder> items;

	boolean hasMoreItems;

	@JsonPOJOBuilder(withPrefix = "")
	public static class JsonResponseManufacturingOrdersBulkBuilder
	{
	}

	public static JsonResponseManufacturingOrdersBulk empty(@NonNull final String transactionKey)
	{
		return builder().transactionKey(transactionKey).hasMoreItems(false).build();
	}
}
