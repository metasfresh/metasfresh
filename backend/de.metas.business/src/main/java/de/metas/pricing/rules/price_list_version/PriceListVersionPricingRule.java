/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.pricing.rules.price_list_version;

import de.metas.common.util.time.SystemTime;
import de.metas.pricing.IEditablePricingContext;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.attributebased.impl.AttributePricing;
import de.metas.pricing.rules.AggregatedPricingRule;
import de.metas.pricing.rules.IPricingRule;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_PriceList_Version;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.function.Supplier;

/**
 * This pricing rule is a composite; see the constructor for the included sub-rules.
 */
public class PriceListVersionPricingRule implements IPricingRule
{
	private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);

	private final IPricingRule includedPricingRules;

	public PriceListVersionPricingRule()
	{
		this.includedPricingRules = AggregatedPricingRule.ofNullables(
				createPricingRule(PriceListVersionConfiguration.getHUPricingRuleFactory()),
				new AttributePricing(),
				new MainProductPriceRule()
		);
	}

	@Nullable
	private static IPricingRule createPricingRule(@Nullable final Supplier<IPricingRule> factory)
	{
		if (factory == null)
		{
			return null;
		}

		final IPricingRule pricingRule = factory.get();
		if (pricingRule == null)
		{
			// shall not happen
			throw new AdempiereException("Got no pricing rule instance from " + factory);
		}

		return pricingRule;
	}

	@Override
	public boolean applies(final IPricingContext pricingCtx, final IPricingResult result)
	{
		return includedPricingRules.applies(pricingCtx, result);
	}

	/**
	 * Iterates the ctx's {@code pricingCtx}'s priceList's PLVs from a PLV to its respective base-PLV 
	 * and applies the sub-pricing-rules until a price is found or all PLvs were tried.
	 */
	@Override
	public void calculate(final IPricingContext pricingCtx, final IPricingResult result)
	{
		final ZonedDateTime date = extractPriceDate(pricingCtx);

		final HashSet<PriceListVersionId> seenPriceListVersionIds = new HashSet<>();
		PriceListVersionId currentPriceListVersionId = getPriceListVersionIdEffective(pricingCtx, date);
		do
		{
			if (currentPriceListVersionId != null && !seenPriceListVersionIds.add(currentPriceListVersionId))
			{
				// loop detected, we already tried to compute using that price list version
				break;
			}

			final IEditablePricingContext pricingCtxEffective = pricingCtx.copy();
			pricingCtxEffective.setPriceListVersionId(currentPriceListVersionId);
			includedPricingRules.calculate(pricingCtxEffective, result);
			if (result.isCalculated())
			{
				return;
			}

			currentPriceListVersionId = getBasePriceListVersionId(currentPriceListVersionId, date);
		}
		while (currentPriceListVersionId != null);
	}

	@Nullable
	private PriceListVersionId getPriceListVersionIdEffective(final IPricingContext pricingCtx, @NonNull final ZonedDateTime date)
	{
		final I_M_PriceList_Version contextPLV = pricingCtx.getM_PriceList_Version();
		if (contextPLV != null)
		{
			return contextPLV.isActive() ? PriceListVersionId.ofRepoId(contextPLV.getM_PriceList_Version_ID()) : null;
		}

		final I_M_PriceList_Version plv = priceListDAO.retrievePriceListVersionOrNull(
				pricingCtx.getPriceListId(),
				date,
				null // processed
		);

		return plv != null && plv.isActive() ? PriceListVersionId.ofRepoId(plv.getM_PriceList_Version_ID()) : null;
	}

	@Nullable
	private PriceListVersionId getBasePriceListVersionId(@Nullable final PriceListVersionId priceListVersionId, @NonNull final ZonedDateTime date)
	{
		if (priceListVersionId == null)
		{
			return null;
		}

		return priceListDAO.getBasePriceListVersionIdForPricingCalculationOrNull(priceListVersionId, date);
	}

	@NonNull
	private static ZonedDateTime extractPriceDate(@NonNull final IPricingContext pricingCtx)
	{
		return pricingCtx.getPriceDate().atStartOfDay(SystemTime.zoneId());
	}

}
