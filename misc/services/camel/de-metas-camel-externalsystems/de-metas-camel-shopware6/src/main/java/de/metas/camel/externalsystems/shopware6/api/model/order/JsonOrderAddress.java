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

@Value
@Builder
@JsonDeserialize(builder = JsonOrderAddress.JsonOrderAddressBuilder.class)
public class JsonOrderAddress
{
	@NonNull
	@JsonProperty("id")
	String id;

	@NonNull
	@JsonProperty("countryId")
	String countryId;

	@JsonProperty("street")
	String street;

	@JsonProperty("zipcode")
	String zipcode;

	@JsonProperty("city")
	String city;

	@JsonProperty("phoneNumber")
	String phoneNumber;

	@JsonProperty("additionalAddressLine1")
	String additionalAddressLine1;

	@JsonProperty("additionalAddressLine2")
	String additionalAddressLine2;

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(withPrefix = "")
	static class JsonOrderAddressBuilder
	{
	}
}
