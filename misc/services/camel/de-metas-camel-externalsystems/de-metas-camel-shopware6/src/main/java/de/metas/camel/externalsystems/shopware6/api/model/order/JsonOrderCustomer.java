/*
 * #%L
 * de-metas-camel-shopware6
 * %%
<<<<<<< HEAD
 * Copyright (C) 2021 metas GmbH
=======
 * Copyright (C) 2022 metas GmbH
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
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
<<<<<<< HEAD
=======
import com.fasterxml.jackson.annotation.JsonInclude;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
<<<<<<< HEAD
import lombok.NonNull;
import lombok.Value;

@Value
@Builder
@JsonDeserialize(builder = JsonOrderCustomer.JsonOrderCustomerBuilder.class)
public class JsonOrderCustomer
{
	@NonNull
	@JsonProperty("orderId")
	String orderId;

	@JsonProperty("customerId")
	String customerId;

	@NonNull
	@JsonProperty("firstName")
	String firstName;

	@NonNull
	@JsonProperty("lastName")
	String lastName;

	@JsonProperty("company")
	String company;

	@JsonProperty("customerNumber")
	String customerNumber;

	@JsonProperty("email")
	String email;
=======
import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.List;

@Value
@EqualsAndHashCode(callSuper = true)
@JsonDeserialize(builder = JsonOrderCustomer.JsonOrderCustomerBuilder.class)
public class JsonOrderCustomer extends JsonCustomerBasicInfo
{
	@Nullable
	@JsonProperty("customerId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	String customerId;

	@Builder
	JsonOrderCustomer(
			@JsonProperty("customerId") final String customerId,
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

		this.customerId = customerId;
	}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(withPrefix = "")
	static class JsonOrderCustomerBuilder
	{
	}
<<<<<<< HEAD
}

=======
}
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
