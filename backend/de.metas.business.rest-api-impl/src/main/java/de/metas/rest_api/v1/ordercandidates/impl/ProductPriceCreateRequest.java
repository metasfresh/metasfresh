package de.metas.rest_api.v1.ordercandidates.impl;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

/*
 * #%L
 * de.metas.business.rest-api-impl
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
public class ProductPriceCreateRequest
{
	@NonNull
	BPartnerLocationId bpartnerAndLocationId;

	/** If null, the system will attempt to get the pricing system id from the bartner's mater data. */
	@Nullable
	final PricingSystemId pricingSystemId;

	@NonNull
	ZonedDateTime date;

	@NonNull
	ProductId productId;

	@NonNull
	UomId uomId;

	@NonNull
	BigDecimal priceStd;
}
