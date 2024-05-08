/*
 * #%L
 * de.metas.purchasecandidate.base
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

package de.metas.purchasecandidate.purchaseordercreation;

import de.metas.bpartner.BPartnerId;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Value
@Builder
public class PurchaseOrderPricingInfo
{
	@NonNull OrgId orgId;
	@Nullable ZoneId orgTimeZone;
	@NonNull BPartnerId bpartnerId;
	@NonNull ZonedDateTime datePromised;
	@NonNull ProductId productId;
	@Nullable CountryId countryId;
	@NonNull Quantity quantity;
}
