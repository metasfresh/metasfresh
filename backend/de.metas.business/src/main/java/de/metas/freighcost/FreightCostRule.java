package de.metas.freighcost;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_Order;

import javax.annotation.Nullable;
import java.util.Arrays;

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

public enum FreightCostRule implements ReferenceListAwareEnum
{
	FreightIncluded(X_C_Order.FREIGHTCOSTRULE_FreightIncluded), //
	FixPrice(X_C_Order.FREIGHTCOSTRULE_FixPrice), //
	Calculated(X_C_Order.FREIGHTCOSTRULE_Calculated), //
	Line(X_C_Order.FREIGHTCOSTRULE_Line), //
	FlatShippingFee(X_C_Order.FREIGHTCOSTRULE_Versandkostenpauschale) //
	;

	@Getter
	private final String code;

	FreightCostRule(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static FreightCostRule ofNullableCode(@Nullable final String code)
	{
		final FreightCostRule defaultWhenNull = null;
		return ofNullableCodeOr(code, defaultWhenNull);
	}

	@Nullable
	public static FreightCostRule ofNullableCodeOr(@Nullable final String code, @Nullable final FreightCostRule defaultWhenNull)
	{
		return code != null ? ofCode(code) : defaultWhenNull;
	}

	public static FreightCostRule ofCode(@NonNull final String code)
	{
		final FreightCostRule type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + FreightCostRule.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, FreightCostRule> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), FreightCostRule::getCode);

	public boolean isFixPrice()
	{
		return this == FixPrice;
	}

	public boolean isNotFixPrice()
	{
		return !isFixPrice();
	}

	@Nullable
	public static String toCodeOrNull(final FreightCostRule type)
	{
		return type != null ? type.getCode() : null;
	}
}
