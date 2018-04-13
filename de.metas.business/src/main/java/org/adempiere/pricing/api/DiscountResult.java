/**
 *
 */
package org.adempiere.pricing.api;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Builder.Default;
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

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Builder
@Value
public class DiscountResult
{
	public static final DiscountResult ZERO = builder().discount(BigDecimal.ZERO).build();
	
	@Default
	@NonNull
	BigDecimal discount = BigDecimal.ZERO;
	@Default
	int C_PaymentTerm_ID = -1;
	BigDecimal priceListOverride;
	BigDecimal priceStdOverride;
	BigDecimal priceLimitOverride;

	@Default
	private final int discountSchemaBreakId = -1;
}
