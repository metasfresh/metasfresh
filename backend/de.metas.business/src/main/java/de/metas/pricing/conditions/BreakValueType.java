package de.metas.pricing.conditions;

import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_M_DiscountSchema;

import com.google.common.collect.ImmutableMap;

import de.metas.util.GuavaCollectors;
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

public enum BreakValueType
{
	QUANTITY(X_M_DiscountSchema.BREAKVALUETYPE_Quantity), //
	AMOUNT(X_M_DiscountSchema.BREAKVALUETYPE_Amount), //
	ATTRIBUTE(X_M_DiscountSchema.BREAKVALUETYPE_Attribute);

	@Getter
	private final String code;

	BreakValueType(final String code)
	{
		this.code = code;
	}

	public static BreakValueType forNullableCode(final String code)
	{
		return code != null ? forCode(code) : null;
	}

	public static BreakValueType forCode(@NonNull final String code)
	{
		final BreakValueType value = valuesByCode.get(code);
		if (value == null)
		{
			throw new AdempiereException("No " + BreakValueType.class + " found for '" + code + "'");
		}
		return value;
	}

	private static final ImmutableMap<String, BreakValueType> valuesByCode = Stream.of(values())
			.collect(GuavaCollectors.toImmutableMapByKey(BreakValueType::getCode));
}
