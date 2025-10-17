package de.metas.handlingunits.inventory;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.inventory.HUAggregationType;
import de.metas.inventory.InventoryLineId;
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
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static de.metas.common.util.CoalesceUtil.coalesceNotNull;

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
	@Nullable @NonFinal InventoryLineId id;
	@NonNull OrgId orgId;

	@NonNull AttributeSetInstanceId asiId;
	@NonNull ProductId productId;
	@NonNull LocatorId locatorId;
	@NonNull InventoryLinePackingInstructions packingInstructions;

	@Nullable @NonFinal @Setter HUAggregationType huAggregationType;
	@NonNull InventoryType inventoryType;

	boolean counted;
	@Nullable Quantity qtyBookFixed;
	@Nullable Quantity qtyCountFixed;

	@NonNull ImmutableList<InventoryLineHU> inventoryLineHUs;

	@Builder(toBuilder = true)
	private InventoryLine(
			@Nullable final InventoryLineId id,
			@NonNull final OrgId orgId,
			@NonNull final ProductId productId,
			@Nullable final AttributeSetInstanceId asiId,
			@NonNull final LocatorId locatorId,
			@Nullable final InventoryLinePackingInstructions packingInstructions,
			@Nullable final HUAggregationType huAggregationType,
			final boolean counted,
			@Nullable final Quantity qtyBookFixed,
			@Nullable final Quantity qtyCountFixed,
			@Singular("inventoryLineHU") @NonNull final ImmutableList<InventoryLineHU> inventoryLineHUs)
	{
		Check.assumeNotEmpty(inventoryLineHUs, "inventoryLineHUs is not empty");

		this.id = id;
		this.orgId = orgId;
		this.asiId = asiId != null ? asiId : AttributeSetInstanceId.NONE;
		this.productId = productId;
		this.locatorId = locatorId;
		this.packingInstructions = coalesceNotNull(packingInstructions, InventoryLinePackingInstructions.VHU);
		this.huAggregationType = huAggregationType;

		inventoryType = extractInventoryType(inventoryLineHUs).orElse(InventoryType.PHYSICAL);
		this.counted = counted;
		this.qtyBookFixed = qtyBookFixed;
		this.qtyCountFixed = qtyCountFixed;

		this.inventoryLineHUs = inventoryLineHUs;

		if (HUAggregationType.SINGLE_HU.equals(huAggregationType) && inventoryLineHUs.size() > 1)
		{
			throw new AdempiereException("Only one HU shall be assigned when huAggregatioType=" + huAggregationType);
		}
	}

	private static Optional<InventoryType> extractInventoryType(@NonNull final List<InventoryLineHU> lineHUs)
	{
		return lineHUs.stream()
				.map(InventoryLineHU::getInventoryType)
				.reduce(Reducers.singleValue(values -> new AdempiereException("Mixing Physical inventories with Internal Use inventories is not allowed: " + lineHUs)));
	}

	@NonNull
	public InventoryLineId getIdNonNull()
	{
		return Check.assumeNotNull(id, "line is saved: {}", this);
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

	public String getUOMSymbol()
	{
		return getUOM().getUOMSymbol();
	}

	public I_C_UOM getUOM()
	{
		final Quantity qtyBookFixed = getQtyBookFixed();
		final Quantity qtyCountFixed = getQtyCountFixed();
		Quantity.assertSameUOM(qtyBookFixed, qtyCountFixed);
		return qtyBookFixed.getUOM();
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

	public Quantity getQtyInternalUse()
	{
		return getInventoryLineHUs()
				.stream()
				.map(InventoryLineHU::getQtyInternalUse)
				.reduce(Quantity::add)
				.orElseThrow(() -> new AdempiereException("No HUs found for " + this));
	}

	public Quantity getQtyBook()
	{
		return getInventoryLineHUs()
				.stream()
				.map(InventoryLineHU::getQtyBook)
				.reduce(Quantity::add)
				.orElseThrow(() -> new AdempiereException("No HUs found for " + this));
	}

	public Quantity getQtyCount()
	{
		return computeQtyCountSum(inventoryLineHUs);
	}

	private Quantity computeQtyCountSum(final List<InventoryLineHU> inventoryLineHUs)
	{
		return inventoryLineHUs
				.stream()
				.map(InventoryLineHU::getQtyCount)
				.reduce(Quantity::add)
				.orElseThrow(() -> new AdempiereException("No HUs found for " + this));
	}

	public InventoryLine distributeQtyCountToHUs()
	{
		return distributeQtyCountToHUs(getQtyCountFixed());
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

	public InventoryLine withInventoryLineHUs(@NonNull final List<InventoryLineHU> newInventoryLineHUs)
	{
		return toBuilder()
				.huAggregationType(computeHUAggregationType(newInventoryLineHUs, this.huAggregationType))
				.clearInventoryLineHUs()
				.inventoryLineHUs(newInventoryLineHUs)
				.build();
	}

	public InventoryLine withCounting(@NonNull final InventoryLineCountRequest request)
	{
		final ArrayList<InventoryLineHU> newLineHUs = new ArrayList<>(inventoryLineHUs.size() + 1);
		boolean updated = false;

		boolean isSingleLineHUPlaceholder = inventoryLineHUs.size() == 1
				&& inventoryLineHUs.get(0).getId() == null
				&& inventoryLineHUs.get(0).getHuId() == null;

		if (!isSingleLineHUPlaceholder)
		{
			for (InventoryLineHU lineHU : inventoryLineHUs)
			{
				if (!updated && HuId.equals(lineHU.getHuId(), request.getHuId()))
				{
					newLineHUs.add(lineHU.updatingFrom(request));
					updated = true;
				}
				else
				{
					newLineHUs.add(lineHU);
				}
			}
		}

		if (!updated)
		{
			newLineHUs.add(InventoryLineHU.of(request));
		}

		return toBuilder()
				.huAggregationType(computeHUAggregationType(newLineHUs, this.huAggregationType))
				.qtyCountFixed(computeQtyCountSum(newLineHUs))
				.clearInventoryLineHUs()
				.inventoryLineHUs(newLineHUs)
				.build();
	}

	@Nullable
	private static HUAggregationType computeHUAggregationType(
			@NonNull final List<InventoryLineHU> newInventoryLineHUs,
			@Nullable final HUAggregationType prevHUAggregationType)
	{
		return newInventoryLineHUs.size() > 1 ? HUAggregationType.MULTI_HU : prevHUAggregationType;
	}

	public boolean isEligibleForCounting()
	{
		return !isCounted();
	}
}
