package de.metas.pricing.conditions;

import java.math.BigDecimal;
import java.util.Set;

import org.adempiere.util.Check;

import de.metas.product.ProductAndCategoryId;
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
@Builder
public class PricingConditionsBreakMatchCriteria
{
	@NonNull
	BigDecimal breakValue;
	int productId;
	int productCategoryId;
	int attributeValueId;

	public boolean breakValueMatches(final BigDecimal value)
	{
		return value.compareTo(breakValue) >= 0;

	}

	public boolean productMatchesAnyOf(@NonNull final Set<ProductAndCategoryId> productAndCategoryIds)
	{
		Check.assumeNotEmpty(productAndCategoryIds, "productAndCategoryIds is not empty");

		return productAndCategoryIds.stream().anyMatch(this::productMatches);
	}

	public boolean productMatches(@NonNull final ProductAndCategoryId productAndCategoryId)
	{
		final int breakProductId = this.productId;
		if (breakProductId > 0)
		{
			return breakProductId == productAndCategoryId.getProductId();
		}

		final int breakProductCategoryId = this.productCategoryId;
		if (breakProductCategoryId > 0)
		{
			return breakProductCategoryId == productAndCategoryId.getProductCategoryId();
		}

		return true;
	}

	public boolean attributeMatches(final int attributeValueId)
	{
		final int breakAttributeValueId = this.attributeValueId;
		return breakAttributeValueId <= 0 || breakAttributeValueId == attributeValueId;
	}

}
