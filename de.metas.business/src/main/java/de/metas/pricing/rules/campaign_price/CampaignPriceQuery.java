package de.metas.pricing.rules.campaign_price;

import java.time.LocalDate;

import de.metas.location.CountryId;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.product.ProductId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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
public class CampaignPriceQuery
{
	@NonNull
	BPartnerId bpartnerId;
	@NonNull
	BPGroupId bpGroupId;

	@NonNull
	ProductId productId;

	@NonNull
	CountryId countryId;

	@NonNull
	CurrencyId currencyId;

	@NonNull
	LocalDate date;

	public boolean isMatching(@NonNull final CampaignPrice price)
	{
		if (!ProductId.equals(price.getProductId(), getProductId()))
		{
			return false;
		}

		if (price.getBpartnerId() != null && !BPartnerId.equals(price.getBpartnerId(), getBpartnerId()))
		{
			return false;
		}
		if (price.getBpGroupId() != null && !BPGroupId.equals(price.getBpGroupId(), getBpGroupId()))
		{
			return false;
		}

		if (!CountryId.equals(price.getCountryId(), getCountryId()))
		{
			return false;
		}

		if (!CurrencyId.equals(price.getCurrencyId(), getCurrencyId()))
		{
			return false;
		}

		if (!price.getValidRange().contains(getDate()))
		{
			return false;
		}

		return true;
	}
}
