package de.metas.inventory;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.lang.ReferenceListAwareEnums.ValuesIndex;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_C_DocType;

import java.util.Objects;

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
	InternalUseInventory(X_C_DocType.DOCSUBTYPE_InternalUseInventory),
	AggregatedHUInventory(X_C_DocType.DOCSUBTYPE_AggregatedHUInventory),
	SingleHUInventory(X_C_DocType.DOCSUBTYPE_SingleHUInventory),
	VirtualInventory(X_C_DocType.DOCSUBTYPE_VirtualInventory),
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

	public static InventoryDocSubType of(@NonNull final DocBaseAndSubType docBaseAndSubType)
	{
		if (!Objects.equals(docBaseAndSubType.getDocBaseType(), DocBaseType.MaterialPhysicalInventory))
		{
			throw new AdempiereException("Invalid inventory document type: " + docBaseAndSubType);
		}

		return InventoryDocSubType.ofCode(docBaseAndSubType.getDocSubType().getCode());
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

	public boolean isActualPhysicalInventory()
	{
		return SingleHUInventory == this || AggregatedHUInventory == this;
	}
}
