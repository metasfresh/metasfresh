/*
 * #%L
 * de-metas-camel-shopware6
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.camel.externalsystems.shopware6.api.model.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Value
@Builder
@JsonDeserialize(builder = JsonOrderLine.JsonOrderLineBuilder.class)
public class JsonOrderLine
{
	@NonNull
	@JsonProperty("id")
	String id;

	/**
	 * Note: it's {@code @Nullable} to avoid NPEs with faulty order lines, 
	 * but jsonOrderLines with null productId will be ignored, see {@link JsonOrderLines#filterForOrderLinesWithProductId()}.
	 */
	@JsonProperty("productId")
	@Nullable
	String productId;

	@NonNull
	@JsonProperty("quantity")
	BigDecimal quantity;

	@Nullable
	@JsonProperty("parentId")
	String parentId;

	@Nullable
	@JsonProperty("unitPrice")
	BigDecimal unitPrice;

	@Nullable
	@JsonProperty("description")
	String description;

	@Nullable
	@JsonProperty("position")
	Integer position;

	@Nullable
	@JsonProperty("createdAt")
	ZonedDateTime createdAt;

	@Nullable
	@JsonProperty("updatedAt")
	ZonedDateTime updatedAt;

	@Nullable
	@JsonProperty("payload")
	JsonOrderLinePayload payload;

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(withPrefix = "")
	static class JsonOrderLineBuilder
	{
	}
}
