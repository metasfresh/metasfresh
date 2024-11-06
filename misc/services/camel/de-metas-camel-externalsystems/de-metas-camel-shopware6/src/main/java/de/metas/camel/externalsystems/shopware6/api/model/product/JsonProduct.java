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

package de.metas.camel.externalsystems.shopware6.api.model.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import de.metas.camel.externalsystems.shopware6.api.model.JsonTax;
import de.metas.camel.externalsystems.shopware6.api.model.product.price.JsonPrice;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

@Value
@Builder
@AllArgsConstructor
@JsonDeserialize(builder = JsonProduct.JsonProductBuilder.class)
public class JsonProduct
{
	@NonNull
	@JsonProperty("id")
	String id;

	@Nullable
	@JsonProperty("parentId")
	String parentId;

	@Nullable
	@JsonProperty("name")
	String name;

	@NonNull
	@JsonProperty("productNumber")
	String productNumber;

	@Nullable
	@JsonProperty("ean")
	String ean;

	@Nullable
	@JsonProperty("unitId")
	String unitId;

	@Nullable
	@JsonProperty("tax")
	JsonTax jsonTax;

	@NonNull
	@JsonProperty("createdAt")
	ZonedDateTime createdAt;

	@Nullable
	@JsonProperty("updatedAt")
	ZonedDateTime updatedAt;

	@Nullable
	@JsonProperty("price")
	List<JsonPrice> prices;

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(withPrefix = "")
	static class JsonProductBuilder
	{
	}

	@JsonIgnore
	@NonNull
	public ZonedDateTime getUpdatedAtNonNull()
	{
		if (updatedAt != null)
		{
			return updatedAt;
		}

		return createdAt;
	}

}
