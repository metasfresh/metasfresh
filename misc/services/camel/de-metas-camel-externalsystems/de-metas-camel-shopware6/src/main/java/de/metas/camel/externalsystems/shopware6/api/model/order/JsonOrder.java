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
import java.time.ZonedDateTime;

@Value
@Builder
@JsonDeserialize(builder = JsonOrder.JsonOrderBuilder.class)
public class JsonOrder
{
	@NonNull
	@JsonProperty("id")
	String id;

	@NonNull
	@JsonProperty("billingAddressId")
	String billingAddressId;

	@NonNull
	@JsonProperty("orderCustomer")
	JsonOrderCustomer orderCustomer;

	@NonNull
	@JsonProperty("currencyId")
	String currencyId;

	@Nullable
	@JsonProperty("orderNumber")
	String orderNumber;

	@Nullable
	@JsonProperty("orderDate")
	ZonedDateTime orderDate;

	@Nullable
	@JsonProperty("stateMachineState")
	JsonStateMachine stateMachine;

	@NonNull
	@JsonProperty("createdAt")
	ZonedDateTime createdAt;

	@Nullable
	@JsonProperty("updatedAt")
	ZonedDateTime updatedAt;

	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonPOJOBuilder(withPrefix = "")
	static class JsonOrderBuilder
	{
	}
}
