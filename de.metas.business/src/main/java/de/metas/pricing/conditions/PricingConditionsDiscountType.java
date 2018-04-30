package de.metas.pricing.conditions;

import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import org.adempiere.util.GuavaCollectors;
import org.compiere.model.X_M_DiscountSchema;

import lombok.Getter;
import lombok.NonNull;

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

public enum PricingConditionsDiscountType
{
	FLAT_PERCENT(X_M_DiscountSchema.DISCOUNTTYPE_FlatPercent), //
	FORMULA(X_M_DiscountSchema.DISCOUNTTYPE_Formula), //
	PRICE_LIST(X_M_DiscountSchema.DISCOUNTTYPE_Pricelist), //
	BREAKS(X_M_DiscountSchema.DISCOUNTTYPE_Breaks) //
	;

	@Getter
	private final String code;

	private PricingConditionsDiscountType(@NonNull final String code)
	{
		this.code = code;
		// TODO Auto-generated constructor stub
	}

	public static PricingConditionsDiscountType forCode(final String code)
	{
		final PricingConditionsDiscountType value = valuesByCode.get(code);
		if (value == null)
		{
			throw new NoSuchElementException("No " + PricingConditionsDiscountType.class + " for code '" + code + "'");
		}
		return value;
	}

	private static final Map<String, PricingConditionsDiscountType> valuesByCode = Stream.of(values())
			.collect(GuavaCollectors.toImmutableMapByKey(PricingConditionsDiscountType::getCode));
}
