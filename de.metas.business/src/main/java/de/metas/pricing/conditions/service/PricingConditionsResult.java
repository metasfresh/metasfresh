/**
 *
 */
package de.metas.pricing.conditions.service;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import de.metas.money.CurrencyId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakId;
import de.metas.pricing.conditions.PricingConditionsId;
import de.metas.util.lang.Percent;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
public class PricingConditionsResult
{
	public static final PricingConditionsResult ZERO = builder().build();

	Percent discount;
	PaymentTermId paymentTermId;

	CurrencyId currencyId;
	BigDecimal priceListOverride;
	BigDecimal priceStdOverride;
	BigDecimal priceLimitOverride;

	PricingConditionsId pricingConditionsId;
	PricingConditionsBreak pricingConditionsBreak;

	PricingSystemId basePricingSystemId;

	@Builder
	public PricingConditionsResult(
			@Nullable final Percent discount,
			final PaymentTermId paymentTermId,
			final CurrencyId currencyId,
			final BigDecimal priceListOverride,
			final BigDecimal priceStdOverride,
			final BigDecimal priceLimitOverride,
			final PricingConditionsId pricingConditionsId,
			final PricingConditionsBreak pricingConditionsBreak,
			final PricingSystemId basePricingSystemId)
	{
		this.discount = discount != null ? discount : Percent.ZERO;
		this.paymentTermId = paymentTermId;

		this.currencyId = currencyId;
		this.priceListOverride = priceListOverride;
		this.priceStdOverride = priceStdOverride;
		this.priceLimitOverride = priceLimitOverride;

		this.pricingConditionsBreak = pricingConditionsBreak;
		this.pricingConditionsId = extractPricingConditionsId(pricingConditionsId, pricingConditionsBreak);

		this.basePricingSystemId = basePricingSystemId;
	}

	private static PricingConditionsId extractPricingConditionsId(PricingConditionsId pricingConditionsId, PricingConditionsBreak pricingConditionsBreak)
	{
		if (pricingConditionsBreak == null)
		{
			return pricingConditionsId;
		}
		else if (pricingConditionsId == null)
		{
			return pricingConditionsBreak.getPricingConditionsIdOrNull();
		}
		else if (pricingConditionsBreak.getId() == null)
		{
			return pricingConditionsId;
		}
		else
		{
			PricingConditionsBreakId.assertMatching(pricingConditionsId, pricingConditionsBreak.getId());
			return pricingConditionsId;
		}
	}

}
