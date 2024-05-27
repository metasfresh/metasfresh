/*
 * #%L
 * de.metas.business
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

package de.metas.pricing.service;

import com.fasterxml.jackson.annotation.JsonCreator;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_M_ProductPrice;

import java.util.Objects;

@Getter
@AllArgsConstructor
public enum ScalePriceUsage implements ReferenceListAwareEnum
{
	DONT_USE_SCALE_PRICE(X_M_ProductPrice.USESCALEPRICE_DonTUseScalePrice),
	USE_SCALE_PRICE_STRICT(X_M_ProductPrice.USESCALEPRICE_UseScalePriceStrict),
	USE_SCALE_PRICE(X_M_ProductPrice.USESCALEPRICE_UseScalePriceFallbackToProductPrice);

	private static final ReferenceListAwareEnums.ValuesIndex<ScalePriceUsage> index = ReferenceListAwareEnums.index(values());

	private final String code;

	@JsonCreator
	@NonNull
	public static ScalePriceUsage ofCode(@NonNull final String code) {return index.ofCode(code);}

	public static boolean equals(ScalePriceUsage type1, ScalePriceUsage type2) {return Objects.equals(type1, type2);}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isUseScalePrice()
	{
		return this == USE_SCALE_PRICE_STRICT || this == USE_SCALE_PRICE;
	}

	public boolean isUseScalePriceStrict()
	{
		return this == USE_SCALE_PRICE_STRICT;
	}

	public boolean isDoNotUseScalePrice() {return this == DONT_USE_SCALE_PRICE;}

	public boolean isAllowFallbackToProductPrice()
	{
		return this == USE_SCALE_PRICE;
	}
}
