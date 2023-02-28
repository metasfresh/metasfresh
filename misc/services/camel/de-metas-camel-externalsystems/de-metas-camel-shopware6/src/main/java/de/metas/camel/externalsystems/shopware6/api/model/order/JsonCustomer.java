/*
 * #%L
 * de-metas-camel-shopware6
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

package de.metas.camel.externalsystems.shopware6.api.model.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = JsonCustomer.JsonCustomerBuilder.class)
public class JsonCustomer extends JsonCustomerBasicInfo
{
	@NonNull
	@JsonProperty("id")
	String id;

	@Nullable
	@JsonProperty("defaultBillingAddressId")
	String defaultBillingAddressId;

	@Nullable
	@JsonProperty("defaultShippingAddressId")
	String defaultShippingAddressId;

	@Nullable
	@JsonProperty("groupId")
	String groupId;

	@Builder
	public JsonCustomer(
			@JsonProperty("id") final String id,
			@JsonProperty("defaultBillingAddressId") final String defaultBillingAddressId,
			@JsonProperty("defaultShippingAddressId") final String defaultShippingAddressId,
			@JsonProperty("groupId") final String groupId,
			@JsonProperty("firstName") final String firstName,
			@JsonProperty("lastName") final String lastName,
			@JsonProperty("company") final String company,
			@JsonProperty("customerNumber") final String customerNumber,
			@JsonProperty("email") final String email,
			@JsonProperty("createdAt") final ZonedDateTime createdAt,
			@JsonProperty("updatedAt") final ZonedDateTime updatedAt,
			@JsonProperty("vatIds") final List<String> vatIds)
	{
		super(firstName, lastName, company, customerNumber, email, createdAt, updatedAt, vatIds);

		this.id = id;
		this.defaultBillingAddressId = defaultBillingAddressId;
		this.defaultShippingAddressId = defaultShippingAddressId;
		this.groupId = groupId;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(withPrefix = "")
	static class JsonCustomerBuilder
	{
	}
}
