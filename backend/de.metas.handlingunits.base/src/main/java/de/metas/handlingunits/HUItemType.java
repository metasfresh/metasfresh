package de.metas.handlingunits;

import javax.annotation.Nullable;

import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

public enum HUItemType implements ReferenceListAwareEnum
{
	HandlingUnit(X_M_HU_Item.ITEMTYPE_HandlingUnit), //

	/** the actual good, like salad or machine parts */
	Material(X_M_HU_Item.ITEMTYPE_Material), //

	/** Packaging, like palette, IFCO or box */
	PackingMaterial(X_M_HU_Item.ITEMTYPE_PackingMaterial), //

	HUAggregate(X_M_HU_Item.ITEMTYPE_HUAggregate), //
	;

	private static final ValuesIndex<HUItemType> index = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	HUItemType(@NonNull final String code)
	{
		this.code = code;
	}

	public static HUItemType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public static HUItemType ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}
}
