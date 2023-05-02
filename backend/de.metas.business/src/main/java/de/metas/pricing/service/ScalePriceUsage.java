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
import com.google.common.collect.ImmutableMap;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_M_ProductPrice;

@AllArgsConstructor
public enum ScalePriceUsage implements ReferenceListAwareEnum
{
	DONT_USE_SCALE_PRICE(X_M_ProductPrice.USESCALEPRICE_DonTUseScalePrice),
	USE_SCALE_PRICE_STRICT(X_M_ProductPrice.USESCALEPRICE_UseScalePriceStrict),
	USE_SCALE_PRICE(X_M_ProductPrice.USESCALEPRICE_UseScalePriceFallbackToProductPrice);

	@Getter
	private final String code;

	@JsonCreator
	@NonNull
	public static ScalePriceUsage ofCode(@NonNull final String code)
	{
		final ScalePriceUsage scalePriceUsage = typesByCode.get(code);
		if (scalePriceUsage == null)
		{
			throw new AdempiereException("No " + ScalePriceUsage.class + " found for code: " + code);
		}

		return scalePriceUsage;
	}

	private static final ImmutableMap<String, ScalePriceUsage> typesByCode = ReferenceListAwareEnums.indexByCode(values());

	public boolean useScalePrice()
	{
		return this == USE_SCALE_PRICE_STRICT || this == USE_SCALE_PRICE;
	}

	public boolean allowFallbackToProductPrice()
	{
		return this == USE_SCALE_PRICE;
	}
}
