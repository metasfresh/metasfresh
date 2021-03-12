package org.eevolution.api;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;
import org.eevolution.model.X_PP_Product_BOM;

import javax.annotation.Nullable;

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

public enum BOMType implements ReferenceListAwareEnum
{
	CurrentActive(X_PP_Product_BOM.BOMTYPE_CurrentActive),
	MakeToOrder(X_PP_Product_BOM.BOMTYPE_Make_To_Order),
	// Previous(X_PP_Product_BOM.BOMTYPE_Previous),
	PreviousSpare(X_PP_Product_BOM.BOMTYPE_PreviousSpare),
	// Future(X_PP_Product_BOM.BOMTYPE_Future),
	// Verwaltung(X_PP_Product_BOM.BOMTYPE_Verwaltung),
	// Repair(X_PP_Product_BOM.BOMTYPE_Repair),
	// ProductConfigure(X_PP_Product_BOM.BOMTYPE_ProductConfigure),
	// MakeToKit(X_PP_Product_BOM.BOMTYPE_Make_To_Kit)
	;

	@Getter
	private final String code;

	BOMType(@NonNull final String code)
	{
		this.code = code;
	}

	public static BOMType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static BOMType ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	private static final ValuesIndex<BOMType> index = ReferenceListAwareEnums.index(values());
}
