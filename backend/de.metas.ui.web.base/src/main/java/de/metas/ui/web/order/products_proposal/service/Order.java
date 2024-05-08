package de.metas.ui.web.order.products_proposal.service;

import java.time.ZonedDateTime;
import java.util.Optional;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.currency.Currency;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.lang.SOTrx;
import de.metas.location.CountryId;
import de.metas.order.OrderId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Value
@Builder
public class Order
{
	@NonNull
	OrderId orderId;

	@NonNull
	SOTrx soTrx;

	@NonNull
	ZonedDateTime datePromised;

	@NonNull
	BPartnerId bpartnerId;

	String bpartnerName;

	@NonNull
	PricingSystemId pricingSystemId;

	@NonNull
	PriceListId priceListId;

	@NonNull
	PriceListVersionId priceListVersionId;

	@Nullable
	CountryId countryId;

	@NonNull
	Currency currency;
	

	@NonNull
	ImmutableList<OrderLine> lines;

	public Optional<OrderLine> getFirstMatchingOrderLine(
			@NonNull final ProductId productId,
			@Nullable final HUPIItemProductId packingMaterialId)
	{
		return getLines()
				.stream()
				.filter(line -> line.isMatching(productId, packingMaterialId))
				.findFirst();
	}

}
