package org.eevolution.costing;

import com.google.common.collect.ImmutableList;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.lang.Percent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.eevolution.api.BOMComponentType;

import javax.annotation.Nullable;

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

/**
 * BOM line from costing point of view
 */
@Value
public class BOMLine
{
	BOMComponentType componentType;

	ProductId componentId;
	AttributeSetInstanceId asiId;

	@Getter(AccessLevel.PRIVATE)
	Quantity qty;

	Percent scrapPercent;

	@Getter(AccessLevel.PACKAGE)
	BOMCostPrice costPrice;
	Percent coProductCostDistributionPercent;

	@Builder
	private BOMLine(
			@NonNull final BOMComponentType componentType,
			@NonNull final ProductId componentId,
			@Nullable final AttributeSetInstanceId asiId,
			@NonNull final Quantity qty,
			@Nullable final Percent scrapPercent,
			@NonNull final BOMCostPrice costPrice,
			@Nullable final Percent coProductCostDistributionPercent)
	{
		if (!UomId.equals(qty.getUomId(), costPrice.getUomId()))
		{
			throw new AdempiereException("UOM not matching: " + qty + ", " + costPrice);
		}

		this.componentType = componentType;
		this.componentId = componentId;
		this.asiId = asiId != null ? asiId : AttributeSetInstanceId.NONE;
		this.qty = qty;
		this.scrapPercent = scrapPercent != null ? scrapPercent : Percent.ZERO;
		this.costPrice = costPrice;

		if (componentType.isCoProduct())
		{
			this.coProductCostDistributionPercent = coProductCostDistributionPercent;
			if (coProductCostDistributionPercent == null || coProductCostDistributionPercent.signum() <= 0)
			{
				//TODO : FIXME see https://github.com/metasfresh/metasfresh/issues/4947
				//				throw new AdempiereException("coProductCostDistributionPercent shall be positive for " + this);
			}
		}
		else
		{
			this.coProductCostDistributionPercent = null;
		}
	}

	public boolean isInboundBOMCosts()
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

	@Nullable
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
		return costPrice.multiply(qty);
	}

	void setComponentsCostPrice(
			@NonNull final CostAmount elementCostPrice,
			@NonNull final CostElementId costElementId)
	{
		getCostPrice().setComponentsCostPrice(elementCostPrice, costElementId);
	}

	void clearComponentsCostPrice(@NonNull final CostElementId costElementId)
	{
		getCostPrice().clearComponentsCostPrice(costElementId);
	}

	public ImmutableList<BOMCostElementPrice> getElementPrices()
	{
		return getCostPrice().getElementPrices();
	}

}
