package de.metas.handlingunits.inventory;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableList;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.InventoryLineId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.reducers.Reducers;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.NonFinal;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.LocatorId;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

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
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class InventoryLine
{
	/** If not null then {@link InventoryRepository#save(InventoryLine)} will load and sync the respective {@code M_InventoryLine} record */
	@Nullable
	@NonFinal
	InventoryLineId id;

	@NonNull
	OrgId orgId;

	/** If not null then {@link InventoryRepository#save(InventoryLine)} will assume that there is an existing persisted ASI which is in sync with {@link #storageAttributesKey}. */
	@Nullable
	AttributeSetInstanceId asiId;

	@NonNull
	ProductId productId;

	@NonNull
	AttributesKey storageAttributesKey;

	@NonNull
	LocatorId locatorId;

	@Nullable
	@NonFinal
	@Setter
	HUAggregationType huAggregationType;

	@NonNull
	private InventoryType inventoryType;

	boolean counted;

	@Nullable Quantity qtyBookFixed;
	@Nullable Quantity qtyCountFixed;

	@NonNull
	ImmutableList<InventoryLineHU> inventoryLineHUs;

	@Builder(toBuilder = true)
	private InventoryLine(
			@Nullable final InventoryLineId id,
			@NonNull final OrgId orgId,
			@NonNull final ProductId productId,
			@Nullable final AttributeSetInstanceId asiId,
			@NonNull final AttributesKey storageAttributesKey,
			@NonNull final LocatorId locatorId,
			@Nullable final HUAggregationType huAggregationType,
			final boolean counted,
			@Nullable final Quantity qtyBookFixed,
			@Nullable final Quantity qtyCountFixed,
			@Singular("inventoryLineHU") @NonNull final ImmutableList<InventoryLineHU> inventoryLineHUs)
	{
		Check.assumeNotEmpty(inventoryLineHUs, "inventoryLineHUs is not empty");

		this.id = id;
		this.orgId = orgId;
		this.asiId = asiId;
		this.productId = productId;
		this.storageAttributesKey = storageAttributesKey;
		this.locatorId = locatorId;
		this.huAggregationType = huAggregationType;

		inventoryType = extractInventoryType(inventoryLineHUs, InventoryType.PHYSICAL);
		this.counted = counted;
		this.qtyBookFixed = qtyBookFixed;
		this.qtyCountFixed = qtyCountFixed;

		this.inventoryLineHUs = inventoryLineHUs;

		if (HUAggregationType.SINGLE_HU.equals(huAggregationType) && inventoryLineHUs.size() > 1)
		{
			throw new AdempiereException("Only one HU shall be assigned when huAggregatioType=" + huAggregationType);
		}
	}

	private static InventoryType extractInventoryType(
			@NonNull final List<InventoryLineHU> lineHUs,
			@NonNull final InventoryType defaultInventoryTypeWhenEmpty)
	{
		return lineHUs.stream()
				.map(InventoryLineHU::getInventoryType)
				.reduce(Reducers.singleValue(values -> new AdempiereException("Mixing Physical inventories with Internal Use inventories is not allowed: " + lineHUs)))
				.orElse(defaultInventoryTypeWhenEmpty);
	}

	void setId(@NonNull final InventoryLineId id)
	{
		this.id = id;
	}

	public boolean isSingleHUAggregation()
	{
		return HUAggregationType.SINGLE_HU.equals(huAggregationType);
	}

	public InventoryLineHU getSingleLineHU()
	{
		return CollectionUtils.singleElement(getInventoryLineHUs());
	}

	public Quantity getMovementQty()
	{
		if (getInventoryType().isInternalUse())
		{
			return getQtyInternalUse();
		}
		else
		{
			final Quantity qtyCount = getQtyCount();
			final Quantity qtyBook = getQtyBook();
			return qtyCount.subtract(qtyBook);
		}
	}

	public Quantity getQtyBookFixed()
	{
		if (qtyBookFixed == null)
		{
			return Quantity.zero(getQtyBook().getUOM());
		}

		return qtyBookFixed;
	}

	public Quantity getQtyCountFixed()
	{
		if (qtyCountFixed == null)
		{
			return Quantity.zero(getQtyCount().getUOM());
		}

		return qtyCountFixed;
	}

	public Quantity getQtyCountMinusBooked()
	{
		return getInventoryLineHUs()
				.stream()
				.map(InventoryLineHU::getQtyCountMinusBooked)
				.reduce(Quantity::add)
				.get();
	}

	public Quantity getQtyInternalUse()
	{
		return getInventoryLineHUs()
				.stream()
				.map(InventoryLineHU::getQtyInternalUse)
				.reduce(Quantity::add)
				.get();
	}

	public Quantity getQtyBook()
	{
		return getInventoryLineHUs()
				.stream()
				.map(InventoryLineHU::getQtyBook)
				.reduce(Quantity::add)
				.get();
	}

	public Quantity getQtyCount()
	{
		return getInventoryLineHUs()
				.stream()
				.map(InventoryLineHU::getQtyCount)
				.reduce(Quantity::add)
				.get();
	}

	public InventoryLine distributeQtyCountToHUs(
			@NonNull final Quantity qtyCountToDistribute,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		final Quantity qtyCountToDistributeConv = uomConverter.convertQuantityTo(qtyCountToDistribute, getProductId(), getQtyCount().getUomId());
		return distributeQtyCountToHUs(qtyCountToDistributeConv);
	}

	public InventoryLine distributeQtyCountToHUs(@NonNull final Quantity qtyCountToDistribute)
	{
		Quantity qtyCountLeftToDistribute = qtyCountToDistribute.subtract(getQtyCount());
		if (qtyCountLeftToDistribute.isZero())
		{
			return this;
		}

		final ArrayList<InventoryLineHU> newInventoryLineHUs = new ArrayList<>();
		for (final InventoryLineHU inventoryLineHU : inventoryLineHUs)
		{
			if (qtyCountLeftToDistribute.signum() > 0)
			{
				newInventoryLineHUs.add(inventoryLineHU.withAddingQtyCount(qtyCountLeftToDistribute));
				qtyCountLeftToDistribute = qtyCountLeftToDistribute.toZero();
			}
			else if (qtyCountLeftToDistribute.signum() < 0)
			{
				final boolean qtyToSubtractIsGreaterThanLineQty = qtyCountLeftToDistribute.negate()
						.compareTo(inventoryLineHU.getQtyCount()) > 0;

				if (qtyToSubtractIsGreaterThanLineQty)
				{
					qtyCountLeftToDistribute = qtyCountLeftToDistribute.add(inventoryLineHU.getQtyCount());

					newInventoryLineHUs.add(inventoryLineHU.withZeroQtyCount());
				}
				else
				{
					newInventoryLineHUs.add(inventoryLineHU.withAddingQtyCount(qtyCountLeftToDistribute));
					qtyCountLeftToDistribute = qtyCountLeftToDistribute.toZero();
				}
			}
			else
			{
				newInventoryLineHUs.add(inventoryLineHU); // just add it unchanged
			}
		}

		return withInventoryLineHUs(newInventoryLineHUs);
	}

	public InventoryLine withInventoryLineHUs(@NonNull final List<InventoryLineHU> inventoryLineHUs)
	{
		return toBuilder()
				.clearInventoryLineHUs()
				.inventoryLineHUs(inventoryLineHUs)
				.build();
	}
}
