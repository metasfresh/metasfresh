package de.metas.picking.service;

import org.adempiere.exceptions.AdempiereException;

import de.metas.picking.api.PickingSlotId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.swat.base
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

/**
 * It's a logical place used to organize {@link IPackingItem}s in {@link PackingItemsMap}.
 */
@ToString
@EqualsAndHashCode
public final class PackingSlot
{
	public static final PackingSlot UNPACKED = new PackingSlot(0);
	public static final PackingSlot DEFAULT_PACKED = new PackingSlot(1);

	public static PackingSlot ofPickingSlotId(@NonNull final PickingSlotId pickingSlotId)
	{
		return ofInt(pickingSlotId.getRepoId());
	}

	public static PackingSlot ofInt(final int value)
	{
		if (value == UNPACKED.value)
		{
			throw new AdempiereException("Reusing the value of UNPACKED is not allowed: " + value);
		}
		if (value == DEFAULT_PACKED.value)
		{
			throw new AdempiereException("Reusing the value of DEFAULT_PACKED is not allowed: " + value);
		}

		return new PackingSlot(value);
	}

	private int value;

	public PackingSlot(final int value)
	{
		this.value = value;
	}

	public boolean isUnpacked()
	{
		return value == UNPACKED.value;
	}
}
