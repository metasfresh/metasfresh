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

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.annotations.VisibleForTesting;
import de.metas.camel.externalsystems.shopware6.common.ExternalIdentifier;
import de.metas.camel.externalsystems.shopware6.common.ExternalIdentifierFormat;
import de.metas.common.util.Check;
import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Optional;

@Value
public class Customer
{
	public static Customer of(@NonNull final JsonNode customerNode, @NonNull final JsonCustomer customer)
	{
		return new Customer(customerNode, customer, null);
	}

	public static Customer of(@NonNull final JsonNode customerNode, @NonNull final JsonOrderCustomer orderCustomer)
	{
		return new Customer(customerNode, null, orderCustomer);
	}

	@NonNull
	JsonNode customerNode;

	@Nullable
	JsonCustomer customer;

	@Nullable
	JsonOrderCustomer orderCustomer;

	private Customer(
			@NonNull final JsonNode customerNode,
			@Nullable final JsonCustomer customer,
			@Nullable final JsonOrderCustomer orderCustomer)
	{
		if (customer == null && orderCustomer == null
				|| customer != null && orderCustomer != null)
		{
			throw new RuntimeException("customer && orderCustomer cannot be both null or not null at the same time!");
		}

		this.customerNode = customerNode;
		this.customer = customer;
		this.orderCustomer = orderCustomer;
	}

	@NonNull
	public String getShopwareId()
	{
		if (orderCustomer != null)
		{
			return orderCustomer.getCustomerId();
		}
		else if (customer != null)
		{
			return customer.getId();
		}

		throw new RuntimeException("customer && orderCustomer cannot be both null at this stage!");
	}

	@NonNull
	public JsonCustomerBasicInfo getCustomerBasicInfo()
	{
		return CoalesceUtil.coalesceNotNull(orderCustomer, customer);
	}

	@Nullable
	public String getDefaultShippingAddressId()
	{
		return Optional.ofNullable(customer)
				.map(JsonCustomer::getDefaultShippingAddressId)
				.orElse(null);
	}

	@Nullable
	public String getDefaultBillingAddressId()
	{
		return Optional.ofNullable(customer)
				.map(JsonCustomer::getDefaultBillingAddressId)
				.orElse(null);
	}

	@Nullable
	public String getGroupId()
	{
		return Optional.ofNullable(customer)
				.map(JsonCustomer::getGroupId)
				.orElse(null);
	}

	@NonNull
	public ExternalIdentifier getExternalIdentifier(@Nullable final String metasfreshIdJsonPath, @Nullable final String shopwareIdJsonPath)
	{
		final String id = getCustomField(metasfreshIdJsonPath);

		if (Check.isNotBlank(id))
		{
			return ExternalIdentifier.builder()
					.identifier(id)
					.rawValue(id)
					.build();
		}

		return getShopwareId(shopwareIdJsonPath);
	}

	@NonNull
	public ExternalIdentifier getShopwareId(@Nullable final String shopwareIdJsonPath)
	{
		final String id = getCustomField(shopwareIdJsonPath);

		if (Check.isNotBlank(id))
		{
			return ExternalIdentifier.builder()
					.identifier(ExternalIdentifierFormat.formatExternalId(id))
					.rawValue(id)
					.build();
		}

		final String customerId = getShopwareId();

		return ExternalIdentifier.builder()
				.identifier(ExternalIdentifierFormat.formatExternalId(customerId))
				.rawValue(customerId)
				.build();
	}

	@Nullable
	@VisibleForTesting
	public String getCustomField(@Nullable final String customPath)
	{
		return Optional.ofNullable(customPath)
				.filter(Check::isNotBlank)
				.map(customerNode::at)
				.map(JsonNode::asText)
				.filter(Check::isNotBlank)
				.orElse(null);
	}

	@NonNull
	public Instant getUpdatedAt()
	{
		final JsonCustomerBasicInfo basicInfo = getCustomerBasicInfo();

		return CoalesceUtil.coalesceNotNull(basicInfo.getUpdatedAt(), basicInfo.getCreatedAt())
				.toInstant();
	}
}
