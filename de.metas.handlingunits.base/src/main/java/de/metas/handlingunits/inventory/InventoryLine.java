package de.metas.handlingunits.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.OrgId;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.HuId;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.InventoryLineId;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.reducers.Reducers;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;
import lombok.Singular;
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

	private InventoryType inventoryType;
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
			@Singular("inventoryLineHU") @NonNull final ImmutableList<InventoryLineHU> inventoryLineHUs)
	{

		this.id = id;
		this.orgId = orgId;
		this.asiId = asiId;
		this.productId = productId;
		this.storageAttributesKey = storageAttributesKey;
		this.locatorId = locatorId;
		this.huAggregationType = huAggregationType;

		inventoryType = extractInventoryType(inventoryLineHUs, InventoryType.PHYSICAL);
		this.inventoryLineHUs = inventoryLineHUs;
	}

	private static InventoryType extractInventoryType(
			@NonNull final List<InventoryLineHU> lineHUs,
			@NonNull final InventoryType defaultInventoryTypeWhenEmpty)
	{
		return lineHUs.stream()
				.map(InventoryLineHU::getInventoryType)
				.reduce(Reducers.distinct(values -> new AdempiereException("Mixing Physical inventories with Internal Use inventories is not allowed: " + lineHUs)))
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

	public HuId getHuId()
	{
		if (isSingleHUAggregation())
		{
			return getSingleLineHU().getHuId();
		}
		else
		{
			return null;
		}
	}

	public Set<HuId> getHUIds()
	{
		return getInventoryLineHUs()
				.stream()
				.map(InventoryLineHU::getHuId)
				.filter(Predicates.notNull())
				.collect(ImmutableSet.toImmutableSet());
	}

	public Quantity getMovementQty()
	{
		if (getInventoryType().isInternalUse())
		{
			return getQtyInternalUse().get();
		}
		else
		{
			final Quantity qtyCount = getQtyCount().get();
			final Quantity qtyBook = getQtyBook().get();
			return qtyCount.subtract(qtyBook);
		}
	}

	public Optional<Quantity> getQtyInternalUse()
	{
		return getInventoryLineHUs()
				.stream()
				.map(InventoryLineHU::getQtyInternalUse)
				.reduce(Quantity::add);
	}

	public Optional<Quantity> getQtyBook()
	{
		return getInventoryLineHUs()
				.stream()
				.map(InventoryLineHU::getQtyBook)
				.reduce(Quantity::add);
	}

	public Optional<Quantity> getQtyCount()
	{
		return getInventoryLineHUs()
				.stream()
				.map(InventoryLineHU::getQtyCount)
				.reduce(Quantity::add);
	}

	public InventoryLine distributeQtyCountToHUs(@NonNull final Quantity qtyCountToDistribute)
	{
		final Quantity currentQtyCount;
		final ImmutableList<InventoryLineHU> inventoryLineHUsToIterate;
		if (inventoryLineHUs.isEmpty())
		{
			final I_C_UOM uom = qtyCountToDistribute.getUOM();
			inventoryLineHUsToIterate = ImmutableList.of(InventoryLineHU.zeroPhysicalInventory(uom));
			currentQtyCount = qtyCountToDistribute.toZero();
		}
		else
		{
			inventoryLineHUsToIterate = inventoryLineHUs;
			currentQtyCount = getQtyCount().get();
		}

		final ArrayList<InventoryLineHU> newInventoryLineHUs = new ArrayList<>();

		Quantity qtyDiffLeftToDistribute = qtyCountToDistribute.subtract(currentQtyCount);
		for (final InventoryLineHU inventoryLineHU : inventoryLineHUsToIterate)
		{
			if (qtyDiffLeftToDistribute.signum() > 0)
			{
				newInventoryLineHUs.add(inventoryLineHU.withAddingQtyCount(qtyDiffLeftToDistribute));
				qtyDiffLeftToDistribute = qtyDiffLeftToDistribute.toZero();
			}
			else if (qtyDiffLeftToDistribute.signum() < 0)
			{
				final boolean qtyToSubtractIsGreaterThanLineQty = qtyDiffLeftToDistribute.negate()
						.compareTo(inventoryLineHU.getQtyCount()) > 0;

				if (qtyToSubtractIsGreaterThanLineQty)
				{
					qtyDiffLeftToDistribute = qtyDiffLeftToDistribute.add(inventoryLineHU.getQtyCount());

					newInventoryLineHUs.add(inventoryLineHU.withZeroQtyCount());
				}
				else
				{
					newInventoryLineHUs.add(inventoryLineHU.withAddingQtyCount(qtyDiffLeftToDistribute));
					qtyDiffLeftToDistribute = qtyDiffLeftToDistribute.toZero();
				}
			}
			else
			{
				newInventoryLineHUs.add(inventoryLineHU); // just add it unchanged
			}
		}

		return toBuilder()
				.clearInventoryLineHUs()
				.inventoryLineHUs(newInventoryLineHUs)
				.build();
	}
}
