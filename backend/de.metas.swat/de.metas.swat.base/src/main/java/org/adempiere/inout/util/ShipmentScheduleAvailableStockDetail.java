package org.adempiere.inout.util;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Multimaps;
import de.metas.material.event.commons.AttributesKey;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.WarehouseId;
import org.eevolution.api.QtyCalculationsBOM;
import org.eevolution.api.QtyCalculationsBOMLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;

/**
 * Stock detail with mutable qtyOnHand.
 */
@ToString
public class ShipmentScheduleAvailableStockDetail
{
	@Getter(AccessLevel.PACKAGE)
	private final ProductId productId;

	@Getter(AccessLevel.PACKAGE)
	private final WarehouseId warehouseId;

	@Getter(AccessLevel.PACKAGE)
	private final AttributesKey storageAttributesKey;

	private BigDecimal qtyOnHand;

	private final QtyCalculationsBOM pickingBOM;
	private final ImmutableListMultimap<ProductId, ShipmentScheduleAvailableStockDetail> componentStockDetails;

	@Builder
	private ShipmentScheduleAvailableStockDetail(
			@NonNull final ProductId productId,
			@NonNull final WarehouseId warehouseId,
			@NonNull final AttributesKey storageAttributesKey,
			@NonNull final BigDecimal qtyOnHand,
			//
			@Nullable final QtyCalculationsBOM pickingBOM,
			@Nullable @Singular final List<ShipmentScheduleAvailableStockDetail> componentStockDetails)
	{
		this.productId = productId;
		this.warehouseId = warehouseId;
		this.storageAttributesKey = storageAttributesKey;
		this.qtyOnHand = qtyOnHand;

		this.pickingBOM = pickingBOM;
		this.componentStockDetails = componentStockDetails != null && !componentStockDetails.isEmpty()
				? Multimaps.index(componentStockDetails, ShipmentScheduleAvailableStockDetail::getProductId)
				: ImmutableListMultimap.of();
	}

	public BigDecimal getQtyAvailable()
	{
		BigDecimal qtyOnHand = this.qtyOnHand;

		final Quantity qtyAvailableToPick = computeQtyAvailableToPick();
		if (qtyAvailableToPick != null && !qtyAvailableToPick.isZero())
		{
			qtyOnHand = qtyOnHand.add(qtyAvailableToPick.toBigDecimal());
		}

		return qtyOnHand;
	}

	void subtractQtyOnHand(@NonNull final BigDecimal qtyToRemove)
	{
		// Enough qty on hand
		if (this.qtyOnHand.compareTo(qtyToRemove) >= 0)
		{
			this.qtyOnHand = this.qtyOnHand.subtract(qtyToRemove);
		}
		// Not enough qty on hand
		else
		{
			BigDecimal qtyToRemoveRemaining = qtyToRemove;

			//
			// Remove as much we have on hand
			qtyToRemoveRemaining = qtyToRemoveRemaining.subtract(this.qtyOnHand);
			this.qtyOnHand = BigDecimal.ZERO;

			//
			// Pick available qty
			final BigDecimal qtyPicked = pick(qtyToRemoveRemaining);
			qtyToRemoveRemaining = qtyToRemoveRemaining.subtract(qtyPicked);

			//
			// If there is still remaining qty to pick, just subtract it from the qty on hand
			// => qty on hand gets negative
			if (qtyToRemoveRemaining.signum() != 0)
			{
				this.qtyOnHand = this.qtyOnHand.subtract(qtyToRemoveRemaining);
			}
		}
	}

	@Nullable
	private Quantity computeQtyAvailableToPick()
	{
		if (pickingBOM == null)
		{
			return null;
		}

		Quantity minQtyOfFinishedGoods = null;

		for (final QtyCalculationsBOMLine bomLine : pickingBOM.getLines())
		{
			final BigDecimal componentQtyOnHand = computeComponentQtyOnHand(bomLine.getProductId());
			final Quantity qtyOfFinishedGoods = bomLine.computeQtyOfFinishedGoodsForComponentQty(componentQtyOnHand);

			minQtyOfFinishedGoods = minQtyOfFinishedGoods != null
					? minQtyOfFinishedGoods.min(qtyOfFinishedGoods)
					: qtyOfFinishedGoods;
		}

		return minQtyOfFinishedGoods;
	}

	private BigDecimal computeComponentQtyOnHand(@NonNull final ProductId componentId)
	{
		return componentStockDetails.get(componentId)
				.stream()
				.map(ShipmentScheduleAvailableStockDetail::getQtyAvailable)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private BigDecimal pick(final BigDecimal qtyToPickTarget)
	{
		if (qtyToPickTarget.signum() <= 0)
		{
			return BigDecimal.ZERO;
		}

		if (pickingBOM == null)
		{
			return BigDecimal.ZERO;
		}

		final BigDecimal qtyAvailableToPick = computeQtyAvailableToPick().toBigDecimal();

		final BigDecimal qtyToPickEffective = qtyToPickTarget.min(qtyAvailableToPick);
		if (qtyToPickEffective.signum() <= 0)
		{
			return BigDecimal.ZERO;
		}

		for (final QtyCalculationsBOMLine bomLine : pickingBOM.getLines())
		{
			final Quantity componentQty = bomLine.computeQtyRequired(qtyToPickEffective);
			subtractComponentQtyOnHand(bomLine.getProductId(), componentQty);
		}

		return qtyToPickEffective;
	}

	private void subtractComponentQtyOnHand(@NonNull final ProductId componentId, @NonNull final Quantity qtyToRemove)
	{
		if (qtyToRemove.isZero())
		{
			return;
		}

		final ImmutableList<ShipmentScheduleAvailableStockDetail> stockDetails = componentStockDetails.get(componentId);
		if (stockDetails.isEmpty())
		{
			// shall not happen
			throw new AdempiereException("No component stock details defined for " + componentId);
		}

		BigDecimal qtyToRemoveRemaining = qtyToRemove.toBigDecimal();
		for (final ShipmentScheduleAvailableStockDetail componentStockDetail : stockDetails)
		{
			if (qtyToRemoveRemaining.signum() == 0)
			{
				break;
			}

			final BigDecimal componentQtyToRemoveEffective = qtyToRemoveRemaining.min(componentStockDetail.getQtyAvailable());
			componentStockDetail.subtractQtyOnHand(componentQtyToRemoveEffective);

			qtyToRemoveRemaining = qtyToRemoveRemaining.subtract(componentQtyToRemoveEffective);
		}

		if (qtyToRemoveRemaining.signum() != 0)
		{
			final ShipmentScheduleAvailableStockDetail lastStockDetail = stockDetails.get(stockDetails.size() - 1);
			lastStockDetail.subtractQtyOnHand(qtyToRemoveRemaining);

			qtyToRemoveRemaining = BigDecimal.ZERO;
		}
	}
}
