package de.metas.pricing.rules.campaign_price;

import ch.qos.logback.classic.Level;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.currency.CurrencyPrecision;
import de.metas.logging.LogManager;
import de.metas.money.Money;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.rules.IPricingRule;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.NonNull;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.util.Optional;

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

	private final CampaignPriceService campaignPriceService = SpringContextHolder.instance.getBean(CampaignPriceService.class);
	private final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);

	@Override
	public boolean applies(@NonNull final IPricingContext pricingCtx, @NonNull final IPricingResult result)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		if (result.isCalculated())
		{
			loggable.addLog("Already calculated, but we might have a better price!");
		}

		final BPartnerId bpartnerId = pricingCtx.getBPartnerId();

		if (bpartnerId == null)
		{
			loggable.addLog("Not applying because there is no BPartner specified in context");
			return false;
		}

		if (!bpartnersRepo.isCampaignPriceAllowed(bpartnerId))
		{
			loggable.addLog("Not applying because the partner is not allowed to receive campaign prices");
			return false;
		}

		if (pricingCtx.getProductId() == null)
		{
			loggable.addLog("Not applying because there is no Product specified in context");
			return false;
		}
		if (pricingCtx.getCountryId() == null)
		{
			loggable.addLog("Not applying because there is no Country specified in context");
			return false;
		}
		if (pricingCtx.getCurrencyId() == null)
		{
			loggable.addLog("Not applying because there is no Currency specified in context");
			return false;
		}

		loggable.addLog("applying");
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
			Loggables.withLogger(logger, Level.DEBUG).addLog("Did not find a campaingn price; -> return");
			return;
		}

		updateResultIfCampaignPriceIsLower(result, campaignPrice);
	}

	private CampaignPriceQuery createCampaignPriceQuery(@NonNull final IPricingContext pricingCtx)
	{
		return CampaignPriceQuery.builder()
				.bpartnerId(pricingCtx.getBPartnerId())
				.bpGroupId(bpartnersRepo.getBPGroupIdByBPartnerId(pricingCtx.getBPartnerId()))
				.pricingSystemId(pricingCtx.getPricingSystemId())
				.productId(pricingCtx.getProductId())
				.countryId(pricingCtx.getCountryId())
				.currencyId(pricingCtx.getCurrencyId())
				.date(pricingCtx.getPriceDate())
				.build();
	}

	private static void updateResultIfCampaignPriceIsLower(@NonNull final IPricingResult result, @NonNull final CampaignPrice campaignPrice)
	{
		final BigDecimal campaignPriceBD = campaignPrice.getPriceStd().toBigDecimal();

		final boolean campainPriceIsLower;
		if (!result.isCalculated())
		{
			campainPriceIsLower = true;
		}
		else
		{
			final int precision = CurrencyPrecision.toInt(result.getPrecision(), result.getPriceStd().scale());

			final BigDecimal effectiveResultPrice = result.getDiscount()
					.subtractFromBase(result.getPriceStd(), precision);
			campainPriceIsLower = campaignPriceBD.compareTo(effectiveResultPrice) < 0;
		}

		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);
		if (!campainPriceIsLower)
		{
			loggable.addLog("CampaingnPrice={} is higher than the stdPrice={} and discount={} from the previous pricing rules; -> discard campaingnPrice",
					campaignPriceBD, result.getPriceStd(), result.getDiscount());
			return;
		}

		loggable.addLog("CampaingnPrice={} is lower than the stdPrice={} and discount={} from the previous pricing rules; -> update result to campaign price",
				campaignPriceBD, result.getPriceStd(), result.getDiscount());

		result.setPriceStd(campaignPriceBD);
		result.setCalculated(true);
		result.setDisallowDiscount(false); // avoid an exception if a preceding rule advised against changing the discount anymore
		result.setDiscount(Percent.ZERO);
		result.setDisallowDiscount(true); // this is the end price, don't apply any other discounts
		result.setCurrencyId(campaignPrice.getPriceStd().getCurrencyId());
		result.setPriceUomId(campaignPrice.getPriceUomId());
		result.setTaxCategoryId(campaignPrice.getTaxCategoryId());
		result.setPrecision(extractPrecisionFromPrice(campaignPrice.getPriceStd()));
		result.setInvoicableQtyBasedOn(campaignPrice.getInvoicableQtyBasedOn());
		result.setCampaignPrice(true);

		Optional.ofNullable(campaignPrice.getPriceList())
				.map(Money::toBigDecimal)
				.ifPresent(result::setPriceList);
	}

	private static CurrencyPrecision extractPrecisionFromPrice(final Money amt)
	{
		final int amtPrecision = NumberUtils.stripTrailingDecimalZeros(amt.toBigDecimal()).scale();
		return CurrencyPrecision.ofInt(Math.max(amtPrecision, 2));
	}
}
