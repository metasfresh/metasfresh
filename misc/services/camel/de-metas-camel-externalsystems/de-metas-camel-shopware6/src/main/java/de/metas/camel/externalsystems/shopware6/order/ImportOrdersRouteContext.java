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

package de.metas.camel.externalsystems.shopware6.order;

import de.metas.camel.externalsystems.common.DateAndImportStatus;
import de.metas.camel.externalsystems.shopware6.api.ShopwareClient;
import de.metas.camel.externalsystems.shopware6.api.model.customer.JsonCustomerGroup;
import de.metas.camel.externalsystems.shopware6.api.model.order.JsonShippingCost;
import de.metas.camel.externalsystems.shopware6.api.model.order.OrderCandidate;
import de.metas.camel.externalsystems.shopware6.currency.CurrencyInfoProvider;
import de.metas.camel.externalsystems.shopware6.order.processor.TaxProductIdProvider;
import de.metas.common.externalsystem.JsonExternalSystemRequest;
import de.metas.common.externalsystem.JsonExternalSystemShopware6ConfigMappings;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.annotation.Nullable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Data
@Builder
public class ImportOrdersRouteContext
{
	@NonNull
	@Setter(AccessLevel.NONE)
	private final String orgCode;

	@NonNull
	@Setter(AccessLevel.NONE)
	private ShopwareClient shopwareClient;

	@NonNull
	@Setter(AccessLevel.NONE)
	private CurrencyInfoProvider currencyInfoProvider;

	@NonNull
	@Setter(AccessLevel.NONE)
	private JsonExternalSystemRequest externalSystemRequest;

	@NonNull
	@Builder.Default
	@Setter(AccessLevel.NONE)
	private Set<String> importedExternalHeaderIds = new HashSet<>();

	@Nullable
	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	private DateAndImportStatus nextImportStartingTimestamp;

	@Nullable
	@Setter(AccessLevel.NONE)
	private JsonExternalSystemShopware6ConfigMappings shopware6ConfigMappings;

	@Nullable
	@Setter(AccessLevel.NONE)
	private TaxProductIdProvider taxProductIdProvider;

	@Nullable
	@Setter(AccessLevel.NONE)
	@Getter(AccessLevel.NONE)
	private OrderCompositeInfo order;

	@Nullable
	@Setter(AccessLevel.NONE)
	private String bpLocationCustomJsonPath;

	@Nullable
	@Getter(AccessLevel.NONE)
	private String shippingBPLocationExternalId;

	@Nullable
	@Getter(AccessLevel.NONE)
	private String billingBPLocationExternalId;

	@Nullable
	private LocalDate dateRequired;

	private boolean isMultipleShippingAddresses;

	@Nullable
	@Getter(AccessLevel.NONE)
	private JsonShippingCost shippingCost;

	@Nullable
	private String shippingMethodId;

	@Nullable
	private JsonCustomerGroup bPartnerCustomerGroup;

	@NonNull
	public OrderCandidate getOrderNotNull()
	{
		if (order == null)
		{
			throw new RuntimeException("order cannot be null at this stage!");
		}

		return order.getOrderAndCustomId();
	}

	@NonNull
	public String getBillingBPLocationExternalIdNotNull()
	{
		if (billingBPLocationExternalId == null)
		{
			throw new RuntimeException("billingBPLocationExternalId cannot be null at this stage!");
		}

		return billingBPLocationExternalId;
	}

	@NonNull
	public String getShippingBPLocationExternalIdNotNull()
	{
		if (shippingBPLocationExternalId == null)
		{
			throw new RuntimeException("shippingBPLocationExternalId cannot be null at this stage!");
		}

		return shippingBPLocationExternalId;
	}

	public void setOrderCompositeInfo(@NonNull final OrderCompositeInfo order)
	{
		this.order = order;
		importedExternalHeaderIds.add(order.getOrderAndCustomId().getJsonOrder().getId());
	}

	public void setNextImportStartingTimestamp(@NonNull final DateAndImportStatus dateAndImportStatus)
	{
		if (this.nextImportStartingTimestamp == null)
		{
			this.nextImportStartingTimestamp = dateAndImportStatus;
			return;
		}

		if (this.nextImportStartingTimestamp.isOkToImport())
		{
			if (dateAndImportStatus.isOkToImport()
					&& dateAndImportStatus.getTimestamp().isAfter(this.nextImportStartingTimestamp.getTimestamp()))
			{
				this.nextImportStartingTimestamp = dateAndImportStatus;
				return;
			}

			if (!dateAndImportStatus.isOkToImport())
			{
				this.nextImportStartingTimestamp = dateAndImportStatus;
				return;
			}
		}

		if (!this.nextImportStartingTimestamp.isOkToImport()
				&& !dateAndImportStatus.isOkToImport()
				&& dateAndImportStatus.getTimestamp().isBefore(this.nextImportStartingTimestamp.getTimestamp()))
		{
			this.nextImportStartingTimestamp = dateAndImportStatus;
		}
	}

	@NonNull
	public OrderCompositeInfo getCompositeOrderNotNull()
	{
		if (order == null)
		{
			throw new RuntimeException("OrderCompositeInfo cannot be null at this stage!");
		}

		return order;
	}

	@NonNull
	public Optional<Instant> getNextImportStartingTimestamp()
	{
		if (nextImportStartingTimestamp == null)
		{
			return Optional.empty();
		}

		return Optional.of(nextImportStartingTimestamp.getTimestamp());
	}

	@NonNull
	public JsonShippingCost getShippingCostNotNull()
	{
		if (shippingCost == null)
		{
			throw new RuntimeException("shippingCost cannot be null at this stage!");
		}

		return shippingCost;
	}
}
