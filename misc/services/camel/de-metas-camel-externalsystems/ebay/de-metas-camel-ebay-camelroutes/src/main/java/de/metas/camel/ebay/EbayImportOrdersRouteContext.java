/*
 * #%L
 * de-metas-camel-ebay-camelroutes
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

package de.metas.camel.ebay;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import de.metas.camel.externalsystems.ebay.api.model.Order;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/**
 * Context to store all properties of a single eBay order and processed meta-data 
 * from various stages of the {@link GetEbayOrdersRouteBuilder} camel pipeline.
 * 
 * @author Werner Gaulke
 *
 */
@Data
@Builder
public class EbayImportOrdersRouteContext {
	
	
	@NonNull
	@Builder.Default
	@Setter(AccessLevel.NONE)
	private Set<String> importedExternalHeaderIds = new HashSet<>();
	
	@NonNull
	private final String orgCode;

	@Nullable
	private Order order;

	@Nullable
	private String bpLocationCustomJsonPath;

	@Nullable
	@Getter(AccessLevel.NONE)
	private String shippingBPLocationExternalId;

	@Nullable
	@Getter(AccessLevel.NONE)
	private String billingBPLocationExternalId;
	
	@Nullable
	private LocalDate dateRequired;

	
	@NonNull
	public Order getOrderNotNull()
	{
		if (order == null)
		{
			throw new RuntimeException("order cannot be null at this stage!");
		}

		return order;
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

	public void setOrder(@NonNull final Order order)
	{
		this.order = order;
		importedExternalHeaderIds.add(order.getOrderId());
	}
	
}
