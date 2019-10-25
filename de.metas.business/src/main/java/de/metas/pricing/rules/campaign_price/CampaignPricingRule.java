package de.metas.pricing.rules.campaign_price;

import org.compiere.Adempiere;
import org.slf4j.Logger;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.CurrencyPrecision;
import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.rules.IPricingRule;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import lombok.NonNull;

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

public class CampaignPricingRule implements IPricingRule
{
	private static final Logger logger = LogManager.getLogger(CampaignPricingRule.class);

	private final CampaignPriceService campaignPriceService = Adempiere.getBean(CampaignPriceService.class);
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);

	@Override
	public boolean applies(@NonNull final IPricingContext pricingCtx, @NonNull final IPricingResult result)
	{

		if (result.isCalculated())
		{
			logger.debug("Not applying because already calculated");
			return false;
		}

		final BPartnerId bpartnerId = pricingCtx.getBPartnerId();

		if (bpartnerId == null)
		{
			logger.debug("Not applying because there is no BPartner specified in context");
			return false;
		}

		if (!bpartnersRepo.isActionPriceAllowed(bpartnerId))
		{
			logger.debug("Not applying because the partner is not allowed to receive campaign prices");
			return false;
		}

		if (pricingCtx.getProductId() == null)
		{
			logger.debug("Not applying because there is no Product specified in context");
			return false;
		}
		if (pricingCtx.getCountryId() == null)
		{
			logger.debug("Not applying because there is no Country specified in context");
			return false;
		}
		if (pricingCtx.getCurrencyId() == null)
		{
			logger.warn("Not applying because there is no Currency specified in context");
			return false;
		}

		return true;
	}

	@Override
	public void calculate(@NonNull final IPricingContext pricingCtx, @NonNull final IPricingResult result)
	{
		if (!applies(pricingCtx, result))
		{
			return;
		}

		final CampaignPriceQuery campaignPriceQuery = createCampaignPriceQuery(pricingCtx);
		final CampaignPrice campaignPrice = campaignPriceService.getCampaignPrice(campaignPriceQuery).orElse(null);
		if (campaignPrice == null)
		{
			return;
		}

		updateResult(result, campaignPrice);
	}

	private CampaignPriceQuery createCampaignPriceQuery(final IPricingContext pricingCtx)
	{
		return CampaignPriceQuery.builder()
				.bpartnerId(pricingCtx.getBPartnerId())
				.bpGroupId(bpartnersRepo.getBPGroupIdByBPartnerId(pricingCtx.getBPartnerId()))
				.productId(pricingCtx.getProductId())
				.countryId(pricingCtx.getCountryId())
				.currencyId(pricingCtx.getCurrencyId())
				.date(pricingCtx.getPriceDate())
				.build();
	}

	private static void updateResult(final IPricingResult result, final CampaignPrice campaignPrice)
	{
		result.setCalculated(true);
		result.setDisallowDiscount(true); // this is the end price, don't apply any other discounts
		result.setPriceStd(campaignPrice.getPriceStd().toBigDecimal());
		result.setCurrencyId(campaignPrice.getPriceStd().getCurrencyId());
		result.setTaxCategoryId(campaignPrice.getTaxCategoryId());
		result.setPrecision(extractPrecisionFromPrice(campaignPrice.getPriceStd()));
		result.setCampaignPrice(true);
	}

	private static CurrencyPrecision extractPrecisionFromPrice(final Money amt)
	{
		final int amtPrecision = NumberUtils.stripTrailingDecimalZeros(amt.toBigDecimal()).scale();
		return CurrencyPrecision.ofInt(Math.max(amtPrecision, 2));
	}
}
