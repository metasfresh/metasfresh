package de.metas.pricing.conditions;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;

import de.metas.bpartner.BPartnerId;
import de.metas.product.ProductAndCategoryAndManufacturerId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.util.Check;
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
	ProductId productId;
	ProductCategoryId productCategoryId;
	BPartnerId productManufacturerId;
	AttributeValueId attributeValueId;

	public boolean breakValueMatches(final BigDecimal value)
	{
		return value.compareTo(breakValue) >= 0;

	}

	public boolean productMatchesAnyOf(@NonNull final Set<ProductAndCategoryAndManufacturerId> products)
	{
		Check.assumeNotEmpty(products, "products is not empty");

		return products.stream().anyMatch(this::productMatches);
	}

	public boolean productMatches(@NonNull final ProductAndCategoryAndManufacturerId product)
	{
		return productMatches(product.getProductId())
				&& productCategoryMatches(product.getProductCategoryId())
				&& productManufacturerMatches(product.getProductManufacturerId());
	}

	private boolean productMatches(final ProductId productId)
	{
		if (this.productId == null)
		{
			return true;
		}

		return Objects.equals(this.productId, productId);
	}

	private boolean productCategoryMatches(final ProductCategoryId productCategoryId)
	{
		if (this.productCategoryId == null)
		{
			return true;
		}

		return Objects.equals(this.productCategoryId, productCategoryId);
	}

	private boolean productManufacturerMatches(final BPartnerId productManufacturerId)
	{
		if (this.productManufacturerId == null)
		{
			return true;
		}

		if (productManufacturerId == null)
		{
			return false;
		}

		return Objects.equals(this.productManufacturerId, productManufacturerId);
	}

	public boolean attributeMatches(@NonNull final ImmutableAttributeSet attributes)
	{
		final AttributeValueId breakAttributeValueId = this.attributeValueId;
		if (breakAttributeValueId == null)
		{
			return true;
		}

		return attributes.hasAttributeValueId(breakAttributeValueId);
	}

}
