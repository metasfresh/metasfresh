package de.metas.pricing.service;

import java.math.BigDecimal;

import de.metas.pricing.PriceListVersionId;
import de.metas.product.ProductId;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

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

@Value
@Builder
public class AddProductPriceRequest
{
	@NonNull
	PriceListVersionId priceListVersionId;

	@NonNull
	ProductId productId;
	@NonNull
	UomId uomId;

	@NonNull
	BigDecimal priceStd;
	@NonNull
	@Default
	BigDecimal priceList = BigDecimal.ZERO;
	@NonNull
	@Default
	BigDecimal priceLimit = BigDecimal.ZERO;

	@NonNull
	TaxCategoryId taxCategoryId;
}
