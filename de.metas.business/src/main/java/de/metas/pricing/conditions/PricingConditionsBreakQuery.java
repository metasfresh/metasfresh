package de.metas.pricing.conditions;

import java.math.BigDecimal;

import org.adempiere.mm.attributes.api.ImmutableAttributeSet;

import de.metas.product.ProductAndCategoryAndManufacturerId;
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
public class PricingConditionsBreakQuery
{
	ProductAndCategoryAndManufacturerId product;
	ImmutableAttributeSet attributes;
	BigDecimal qty;
	BigDecimal price;
	BigDecimal amt;

	@Builder
	private PricingConditionsBreakQuery(
			@NonNull final ProductAndCategoryAndManufacturerId product,
			ImmutableAttributeSet attributes,
			@NonNull final BigDecimal qty,
			@NonNull final BigDecimal price)
	{
		this.product = product;
		this.attributes = attributes != null ? attributes : ImmutableAttributeSet.EMPTY;
		this.qty = qty;
		this.price = price;
		this.amt = qty.multiply(price);
	}
}
