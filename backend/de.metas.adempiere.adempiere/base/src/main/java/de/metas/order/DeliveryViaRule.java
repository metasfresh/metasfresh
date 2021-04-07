package de.metas.order;

import java.util.Arrays;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_Order;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.Getter;
import lombok.NonNull;

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

public enum DeliveryViaRule implements ReferenceListAwareEnum
{
	Pickup(X_C_Order.DELIVERYVIARULE_Pickup), //
	Delivery(X_C_Order.DELIVERYVIARULE_Delivery), //
	Shipper(X_C_Order.DELIVERYVIARULE_Shipper),
	NormalPost(X_C_Order.DELIVERYVIARULE_Normalpost),
	LuftPost(X_C_Order.DELIVERYVIARULE_Luftpost)//
	;

	@Getter
	private final String code;

	DeliveryViaRule(@NonNull final String code)
	{
		this.code = code;
	}

	public static DeliveryViaRule ofNullableCode(final String code)
	{
		final DeliveryViaRule defaultWhenNull = null;
		return ofNullableCodeOr(code, defaultWhenNull);
	}

	public static DeliveryViaRule ofNullableCodeOr(final String code, final DeliveryViaRule defaultWhenNull)
	{
		return code != null ? ofCode(code) : defaultWhenNull;
	}

	public static DeliveryViaRule ofCode(@NonNull final String code)
	{
		final DeliveryViaRule type = typesByCode.get(code);
		if (type == null)
		{
			throw new AdempiereException("No " + DeliveryViaRule.class + " found for code: " + code);
		}
		return type;
	}

	private static final ImmutableMap<String, DeliveryViaRule> typesByCode = Maps.uniqueIndex(Arrays.asList(values()), DeliveryViaRule::getCode);

	public boolean isShipper()
	{
		return this == Shipper;
	}

	public static String toCodeOrNull(final DeliveryViaRule type)
	{
		return type != null ? type.getCode() : null;
	}
}
