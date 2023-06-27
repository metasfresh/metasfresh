package de.metas.inventory;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.model.X_C_DocType;

/*
 * #%L
 * de.metas.business
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

public enum InventoryDocSubType implements ReferenceListAwareEnum
{
	InternalUseInventory(X_C_DocType.DOCSUBTYPE_InternalUseInventory), //
	AggregatedHUInventory("IAH"), //
	SingleHUInventory("ISH"), //
	VirtualInventory(X_C_DocType.DOCSUBTYPE_VirtualInventory), //
	;

	@Getter
	private final String code;

	private static final ValuesIndex<InventoryDocSubType> index = ReferenceListAwareEnums.index(values());

	InventoryDocSubType(@NonNull final String code)
	{
		this.code = code;
	}

	public static InventoryDocSubType ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	public DocBaseType getDocBaseType()
	{
		return DocBaseType.MaterialPhysicalInventory;
	}

	public String toDocSubTypeString()
	{
		return getCode();
	}

	public DocBaseAndSubType toDocBaseAndSubType()
	{
		return DocBaseAndSubType.of(getDocBaseType(), toDocSubTypeString());
	}
}
