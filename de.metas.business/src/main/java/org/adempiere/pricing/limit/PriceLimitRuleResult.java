package org.adempiere.pricing.limit;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.NonNull;
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

@Value
@Builder(builderMethodName = "_builder")
public class PriceLimitRuleResult
{
	public static PriceLimitRuleResult notApplicable(@NonNull final String reason)
	{
		return PriceLimitRuleResult._builder()
				.applicable(false)
				.notApplicableReason(reason)
				.build();
	}

	public static PriceLimitRuleResult priceLimit(@NonNull final BigDecimal priceLimit, final String priceLimitExplanation)
	{
		return PriceLimitRuleResult._builder()
				.applicable(true)
				.priceLimit(priceLimit)
				.priceLimitExplanation(priceLimitExplanation)
				.build();
	}

	private boolean applicable;
	private String notApplicableReason;

	private BigDecimal priceLimit;
	private String priceLimitExplanation;

	public boolean isBelowPriceLimit(final BigDecimal priceActual)
	{
		if (!applicable)
		{
			return false;
		}

		if(priceLimit.signum() == 0)
		{
			return false;
		}

		return priceActual.compareTo(priceLimit) < 0;
	}

}
