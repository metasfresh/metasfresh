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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

import static de.metas.camel.externalsystems.shopware6.Shopware6Constants.JSON_NODE_ORDER_CUSTOMER;

@Value
@JsonDeserialize(builder = OrderCandidate.OrderCandidateBuilder.class)
public class OrderCandidate
{
	@NonNull
	@JsonProperty("jsonOrder")
	JsonOrder jsonOrder;

	@NonNull
	@JsonProperty("orderNode")
	JsonNode orderNode;

	@Nullable
	@JsonProperty("salesRepId")
	String salesRepId;

	@NonNull
	@JsonProperty("customer")
	Customer customer;

	@Builder
	public OrderCandidate(
			@JsonProperty("jsonOrder") @NonNull final JsonOrder jsonOrder,
			@JsonProperty("orderNode") @NonNull final JsonNode orderNode,
			@JsonProperty("salesRepId") @Nullable final String salesRepId)
	{
		this.jsonOrder = jsonOrder;
		this.orderNode = orderNode;
		this.salesRepId = salesRepId;

		this.customer = Customer.of(orderNode.get(JSON_NODE_ORDER_CUSTOMER), jsonOrder.getOrderCustomer());
	}

	@Nullable
	public JsonNode getCustomNode(@NonNull final String customPath)
	{
		final JsonNode customNode = orderNode.get(customPath);

		return (customNode == null || customNode.isMissingNode() || customNode.isNull()) ? null : customNode;
	}
}
