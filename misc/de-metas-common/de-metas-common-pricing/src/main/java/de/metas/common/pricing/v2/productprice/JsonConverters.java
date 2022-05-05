/*
 * #%L
 * de-metas-common-pricing
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

package de.metas.common.pricing.v2.productprice;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

@UtilityClass
public class JsonConverters
{
	public JsonResponsePrice toJsonResponsePrice(
			@NonNull final JsonProductPrice productPrice,
			@NonNull final String productValue,
			@NonNull final String currencyCode,
			@Nullable final String countryCode,
			final boolean soTrx)
	{
		return JsonResponsePrice.builder()
				.productId(productPrice.getProductId())
				.productCode(productValue)
				.price(productPrice.getPriceStd())
				.currencyCode(currencyCode)
				.taxCategoryId(productPrice.getTaxCategoryId())
				.isSOTrx(soTrx)
				.countryCode(countryCode)
				.build();
	}
}