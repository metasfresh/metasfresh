/*
 * #%L
 * de.metas.servicerepair.base
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

package de.metas.servicerepair.project.service.commands.createQuotationFromProjectCommand;

import de.metas.bpartner.BPartnerId;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Value
@Builder
public class ProjectQuotationPricingInfo
{
	@NonNull OrgId orgId;
	@NonNull ZoneId orgTimeZone;
	@NonNull BPartnerId shipBPartnerId;
	@NonNull ZonedDateTime datePromised;
	@NonNull PricingSystemId pricingSystemId;
	@NonNull PriceListId priceListId;
	@NonNull PriceListVersionId priceListVersionId;
	@NonNull CurrencyId currencyId;
	@NonNull CountryId countryId;
}
