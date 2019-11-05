package de.metas.ui.web.order.products_proposal.campaign_price;

import java.time.LocalDate;
import java.util.Optional;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.BPartnerId;
import de.metas.location.CountryId;
import de.metas.money.CurrencyId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.rules.campaign_price.CampaignPrice;
import de.metas.pricing.rules.campaign_price.CampaignPriceQuery;
import de.metas.pricing.rules.campaign_price.CampaignPriceService;
import de.metas.product.ProductId;
import de.metas.ui.web.order.products_proposal.model.ProductProposalCampaignPrice;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

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

@ToString(exclude = { "campaignPriceService" })
public class StandardCampaignPriceProvider implements CampaignPriceProvider
{
	private final CampaignPriceService campaignPriceService;

	private final BPartnerId bpartnerId;
	private final BPGroupId bpGroupId;
	private final PricingSystemId pricingSystemId;
	private final CountryId countryId;
	private final CurrencyId currencyId;
	private final LocalDate date;

	@Builder
	private StandardCampaignPriceProvider(
			@NonNull final CampaignPriceService campaignPriceService,
			//
			@NonNull final BPartnerId bpartnerId,
			@NonNull final BPGroupId bpGroupId,
			@NonNull final PricingSystemId pricingSystemId,
			@NonNull final CountryId countryId,
			@NonNull final CurrencyId currencyId,
			@NonNull final LocalDate date)
	{
		this.campaignPriceService = campaignPriceService;

		this.bpartnerId = bpartnerId;
		this.bpGroupId = bpGroupId;
		this.pricingSystemId = pricingSystemId;
		this.countryId = countryId;
		this.currencyId = currencyId;
		this.date = date;
	}

	@Override
	public Optional<ProductProposalCampaignPrice> getCampaignPrice(@NonNull final ProductId productId)
	{
		final CampaignPriceQuery query = createQuery(productId);

		return campaignPriceService.getCampaignPrice(query)
				.map(this::toProductProposalCampaignPrice);
	}

	private ProductProposalCampaignPrice toProductProposalCampaignPrice(final CampaignPrice campaignPrice)
	{
		return ProductProposalCampaignPrice.builder()
				.amount(campaignPriceService.getPriceStdAsAmount(campaignPrice))
				.applyOnlyIfLessThanStandardPrice(true)
				.build();
	}

	private CampaignPriceQuery createQuery(final ProductId productId)
	{
		return CampaignPriceQuery.builder()
				.bpartnerId(bpartnerId)
				.bpGroupId(bpGroupId)
				.pricingSystemId(pricingSystemId)
				.productId(productId)
				.countryId(countryId)
				.currencyId(currencyId)
				.date(date)
				.build();
	}
}
