package org.eevolution.api;

import org.eevolution.model.X_PP_Product_BOM;

import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
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

public enum BOMUse implements ReferenceListAwareEnum
{
	Master(X_PP_Product_BOM.BOMUSE_Master), //
	Engineering(X_PP_Product_BOM.BOMUSE_Engineering), //
	Manufacturing(X_PP_Product_BOM.BOMUSE_Manufacturing), //
	Planning(X_PP_Product_BOM.BOMUSE_Planning), //
	Quality(X_PP_Product_BOM.BOMUSE_Quality), //
	Trading(X_PP_Product_BOM.BOMUSE_Trading) //
	;

	@Getter
	private final String code;

	BOMUse(@NonNull final String code)
	{
		this.code = code;
	}

	public static BOMUse ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	private static final ValuesIndex<BOMUse> index = ReferenceListAwareEnums.index(values());
}
