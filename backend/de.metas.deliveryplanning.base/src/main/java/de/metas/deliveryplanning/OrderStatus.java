/*
 * #%L
 * de.metas.swat.base
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

package de.metas.deliveryplanning;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_M_Delivery_Planning;

import javax.annotation.Nullable;

public enum OrderStatus implements ReferenceListAwareEnum
{
	Arranged(X_M_Delivery_Planning.ORDERSTATUS_Arranged),
	Canceled(X_M_Delivery_Planning.ORDERSTATUS_Canceled),
	Loaded(X_M_Delivery_Planning.ORDERSTATUS_Loaded);

	private static final ReferenceListAwareEnums.ValuesIndex<OrderStatus> typesByCode = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	OrderStatus(@NonNull final String code)
	{
		this.code = code;
	}

	@Nullable
	public static OrderStatus ofNullableCode(@Nullable final String code)
	{
		return ofNullableCode(code, null);
	}

	@Nullable
	public static OrderStatus ofNullableCode(@Nullable final String code, @Nullable final OrderStatus fallbackValue)
	{
		return code != null ? ofCode(code) : fallbackValue;
	}

	public static OrderStatus ofCode(@NonNull final String code)
	{
		return typesByCode.ofCode(code);
	}

	@Nullable
	public static String toCodeOrNull(@Nullable final OrderStatus type)
	{
		return type != null ? type.getCode() : null;
	}
}
