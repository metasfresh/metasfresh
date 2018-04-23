package org.adempiere.pricing.limit;

import java.math.BigDecimal;

import org.adempiere.pricing.api.IPricingContext;

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
public class PriceLimitRuleContext
{
	IPricingContext pricingContext;
	int paymentTermId;
	BigDecimal priceActual;
	BigDecimal priceLimit;

	@Builder
	private PriceLimitRuleContext(
			@NonNull final IPricingContext pricingContext,
			final int paymentTermId,
			@NonNull final BigDecimal priceActual,
			@NonNull final BigDecimal priceLimit)
	{
		this.pricingContext = pricingContext;
		this.paymentTermId = paymentTermId > 0 ? paymentTermId : -1;
		this.priceActual = priceActual;
		this.priceLimit = priceLimit;
	}

}
