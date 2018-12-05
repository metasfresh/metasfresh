package org.eevolution.costing;

import java.math.BigDecimal;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.eevolution.api.BOMComponentType;

import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.lang.Percent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

/** BOM line from costing point of view */
@Builder
@Value
public final class BOMLine
{
	@NonNull
	@Getter(AccessLevel.PRIVATE)
	final BOMComponentType componentType;

	@NonNull
	final ProductId componentId;
	@NonNull
	@Default
	final AttributeSetInstanceId asiId = AttributeSetInstanceId.NONE;

	@NonNull
	final Quantity qty;

	@NonNull
	@Default
	final Percent scrapPercent = Percent.ZERO;

	@NonNull
	@Getter(AccessLevel.PACKAGE)
	final BOMCostPrice costPrice;

	public boolean isRelevantForBOMCosts()
	{
		return !isCoProduct() // Skip co-product
				&& !isVariant(); // Skip variants (06005)
	}

	public boolean isCoProduct()
	{
		return getComponentType().isCoProduct();
	}

	public boolean isByProduct()
	{
		return getComponentType().isByProduct();
	}

	public boolean isVariant()
	{
		return getComponentType().isVariant();
	}

	public Quantity getQtyIncludingScrap()
	{
		return getQty().add(getScrapPercent());
	}

	/**
	 * @return co-product cost allocation percent (i.e. -1/qty)
	 */
	public Percent getCoProductCostAllocationPerc()
	{
		Check.assume(isCoProduct(), "co-product line: {}", this);

		final Quantity qty = getQty().negate();
		if (qty.isZero())
		{
			return Percent.ZERO;
		}
		else
		{
			return Percent.of(BigDecimal.ONE, qty.getAsBigDecimal(), 4);
		}
	}

	public CostAmount getCostAmountOrNull(final CostElementId costElementId)
	{
		final BOMCostElementPrice costPriceHolder = getCostPrice().getCostElementPriceOrNull(costElementId);
		if (costPriceHolder == null)
		{
			return null;
		}

		final CostPrice costPrice;
		if (isByProduct())
		{
			costPrice = costPriceHolder.getCostPrice().withZeroComponentsCostPrice();
		}
		else
		{
			costPrice = costPriceHolder.getCostPrice();
		}

		final Quantity qty = getQtyIncludingScrap();
		final CostAmount componentCostAmount = costPrice.multiply(qty);
		return componentCostAmount;
	}

	void setComponentsCostPrice(@NonNull CostAmount elementCostPrice, @NonNull CostElementId costElementId)
	{
		getCostPrice().setComponentsCostPrice(elementCostPrice, costElementId);
	}

	void clearComponentsCostPrice(@NonNull final CostElementId costElementId)
	{
		getCostPrice().clearComponentsCostPrice(costElementId);
	}
}
