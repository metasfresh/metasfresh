package de.metas.pricing.rules.campaign_price;

import com.google.common.collect.Range;
import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.LocalDate;

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
public class CampaignPrice
{
	@NonNull
	ProductId productId;

	@Nullable
	BPartnerId bpartnerId;
	@Nullable
	BPGroupId bpGroupId;

	@Nullable
	PricingSystemId pricingSystemId;

	@NonNull
	CountryId countryId;

	@NonNull
	Range<LocalDate> validRange;

	@Nullable
	Money priceList;

	@NonNull
	Money priceStd;

	@NonNull
	UomId priceUomId;

	@NonNull
	TaxCategoryId taxCategoryId;

	@NonNull
	@Default
	InvoicableQtyBasedOn invoicableQtyBasedOn = InvoicableQtyBasedOn.NominalWeight;

	public LocalDate getValidFrom()
	{
		return getValidRange().lowerEndpoint();
	}

	public CurrencyId getCurrencyId()
	{
		return getPriceStd().getCurrencyId();
	}
}
