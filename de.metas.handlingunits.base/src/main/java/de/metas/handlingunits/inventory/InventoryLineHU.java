package de.metas.handlingunits.inventory;

import static de.metas.util.Check.assumeGreaterThanZero;

import javax.annotation.Nullable;

import de.metas.handlingunits.HuId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.handlingunits.base
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

@Value
@Builder(toBuilder = true)
public class InventoryLineHU
{
	/** Null if not yet persisted or if this is an inventory line's single InventoryLineHU. */
	@Nullable
	InventoryLineHUId id;

	@NonNull
	HuId huId;

	@NonNull
	Quantity bookQty;

	@NonNull
	Quantity countQty;

	@Value
	public static class InventoryLineHUId implements RepoIdAware
	{
		public static InventoryLineHUId ofRepoId(int repoId)
		{
			return new InventoryLineHUId(repoId);
		}

		int repoId;

		private InventoryLineHUId(int repoId)
		{
			this.repoId = assumeGreaterThanZero(repoId, "inventoryLineHUId");
		}
	}

	/**
	 * @param countQtyToAdd needs to have the same UOM as this instance's current countQty.
	 */
	public InventoryLineHU addCountQty(@NonNull final Quantity countQtyToAdd)
	{
		return this.toBuilder()
				.countQty(countQty.add(countQtyToAdd))
				.build();
	}

	public InventoryLineHU zeroCountQty()
	{
		return this.toBuilder()
				.countQty(countQty.toZero())
				.build();
	}
}
