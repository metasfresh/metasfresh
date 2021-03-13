/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.pricing.productprice;

import de.metas.organization.OrgId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.ProductPriceId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Getter
@EqualsAndHashCode
public class ProductPrice
{
	@NonNull
	OrgId orgId;

	@NonNull
	ProductId productId;

	@NonNull
	ProductPriceId productPriceId;

	@NonNull
	PriceListVersionId priceListVersionId;

	@NonNull
	BigDecimal priceLimit;

	@NonNull
	BigDecimal priceList;

	@NonNull
	BigDecimal priceStd;

	@Nullable
	TaxCategoryId taxCategoryId;

	@Nullable
	String internalName;

	@NonNull
	Boolean isActive;

	@Builder
	public ProductPrice(
			@NonNull final OrgId orgId,
			@NonNull final ProductId productId,
			@NonNull final ProductPriceId productPriceId,
			@NonNull final PriceListVersionId priceListVersionId,
			@NonNull final BigDecimal priceLimit,
			@NonNull final BigDecimal priceList,
			@NonNull final BigDecimal priceStd,
			@Nullable final TaxCategoryId taxCategoryId,
			@Nullable final String internalName,
			@NonNull final Boolean isActive)
	{
		this.orgId = orgId;
		this.productId = productId;
		this.productPriceId = productPriceId;
		this.priceListVersionId = priceListVersionId;
		this.priceLimit = priceLimit;
		this.priceList = priceList;
		this.priceStd = priceStd;
		this.taxCategoryId = taxCategoryId;
		this.internalName = internalName;
		this.isActive = isActive;
	}
}
