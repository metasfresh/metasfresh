package de.metas.handlingunits.inventory;

import java.util.Collection;
import java.util.Objects;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_UOM;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;

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
@ToString(doNotUseGetters = true)
@EqualsAndHashCode(doNotUseGetters = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class InventoryLineHU
{
	/** Null if not yet persisted or if this is an inventory line's single InventoryLineHU. */
	@Nullable
	@NonFinal
	InventoryLineHUId id;

	/** Null if this instance does not yet have a persisted HU */
	@Nullable
	HuId huId;

	//
	// Quantities
	private InventoryType inventoryType;
	//
	Quantity qtyInternalUse;
	//
	Quantity qtyBook;
	Quantity qtyCount;

	public static InventoryLineHU zeroPhysicalInventory(@NonNull final I_C_UOM uom)
	{
		final Quantity zero = Quantity.zero(uom);
		return builder()
				.huId(null)
				.qtyInternalUse(null)
				.qtyBook(zero)
				.qtyCount(zero)
				.build();
	}

	@Builder(toBuilder = true)
	private InventoryLineHU(
			@Nullable final InventoryLineHUId id,
			@Nullable final HuId huId,
			@Nullable final Quantity qtyInternalUse,
			@Nullable final Quantity qtyBook,
			@Nullable final Quantity qtyCount)
	{
		this.id = id;
		this.huId = huId;

		if (qtyInternalUse != null)
		{
			Check.assumeNull(qtyBook, "qtyBook shall be null when qtyInternalUse is set");
			Check.assumeNull(qtyCount, "qtyCount shall be null when qtyInternalUse is set");

			this.inventoryType = InventoryType.INTERNAL_USE;
			this.qtyInternalUse = qtyInternalUse;
			this.qtyBook = null;
			this.qtyCount = null;
		}
		else
		{
			Check.assumeNotNull(qtyBook, "qtyBook shall be set when qtyInternalUse is not set");
			Check.assumeNotNull(qtyCount, "qtyCount shall be set when qtyInternalUse is not set");
			Quantity.getCommonUomIdOfAll(qtyBook, qtyCount); // make sure QtyBook and QtyCount share the same UOM

			this.inventoryType = InventoryType.PHYSICAL;
			this.qtyInternalUse = null;
			this.qtyBook = qtyBook;
			this.qtyCount = qtyCount;
		}
	}

	final void setId(@NonNull final InventoryLineHUId id)
	{
		this.id = id;
	}

	private void assertInternalUseInventory()
	{
		if (!getInventoryType().isInternalUse())
		{
			throw new AdempiereException("Expected Internal Use Inventory: " + this);
		}
	}

	private void assertPhysicalInventory()
	{
		if (!getInventoryType().isPhysical())
		{
			throw new AdempiereException("Expected Physical Inventory: " + this);
		}
	}

	public Quantity getQtyInternalUse()
	{
		assertInternalUseInventory();
		return qtyInternalUse;
	}

	public Quantity getQtyCount()
	{
		assertPhysicalInventory();
		return qtyCount;
	}

	public Quantity getQtyBook()
	{
		assertPhysicalInventory();
		return qtyBook;
	}

	/**
	 * @param qtyCountToAdd needs to have the same UOM as this instance's current qtyCount.
	 */
	public InventoryLineHU withAddingQtyCount(@NonNull final Quantity qtyCountToAdd)
	{
		return withQtyCount(qtyCount.add(qtyCountToAdd));
	}

	public InventoryLineHU withZeroQtyCount()
	{
		return withQtyCount(qtyCount.toZero());
	}

	public InventoryLineHU withQtyCount(@NonNull final Quantity newQtyCount)
	{
		assertPhysicalInventory();
		return toBuilder().qtyCount(newQtyCount).build();
	}

	public static ImmutableSet<HuId> extractHuIds(@NonNull final Collection<InventoryLineHU> lineHUs)
	{
		return extractHuIds(lineHUs.stream());
	}

	static ImmutableSet<HuId> extractHuIds(@NonNull final Stream<InventoryLineHU> lineHUs)
	{
		return lineHUs
				.map(InventoryLineHU::getHuId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}
}
