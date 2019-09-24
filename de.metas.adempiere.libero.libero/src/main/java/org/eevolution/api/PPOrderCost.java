package org.eevolution.api;

import java.math.BigDecimal;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.lang.Percent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

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

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PPOrderCost
{
	@NonFinal
	PPOrderCostId id;

	PPOrderCostTrxType trxType;

	CostSegmentAndElement costSegmentAndElement;

	CostPrice price;

	CostAmount accumulatedAmount;
	BigDecimal accumulatedQty;

	private Percent coProductCostDistributionPercent;

	@NonFinal
	private CostAmount postCalculationAmount;

	@Builder(toBuilder = true)
	private PPOrderCost(
			@Nullable final PPOrderCostId id,
			@NonNull final PPOrderCostTrxType trxType,
			@NonNull final CostSegmentAndElement costSegmentAndElement,
			@NonNull final CostPrice price,
			@Nullable final CostAmount accumulatedAmount,
			@Nullable BigDecimal accumulatedQty,
			@Nullable Percent coProductCostDistributionPercent,
			@Nullable CostAmount postCalculationAmount)
	{
		this.id = id;
		this.trxType = trxType;
		this.costSegmentAndElement = costSegmentAndElement;
		this.price = price;
		this.accumulatedAmount = accumulatedAmount != null ? accumulatedAmount : CostAmount.zero(price.getCurrenyId());
		this.accumulatedQty = accumulatedQty != null ? accumulatedQty : BigDecimal.ZERO;
		this.postCalculationAmount = postCalculationAmount != null ? postCalculationAmount : CostAmount.zero(price.getCurrenyId());

		if (trxType.isCoProduct())
		{
			this.coProductCostDistributionPercent = coProductCostDistributionPercent;
			if (coProductCostDistributionPercent == null || coProductCostDistributionPercent.signum() <= 0)
			{
				// TODO : FIXME see https://github.com/metasfresh/metasfresh/issues/4947
				// throw new AdempiereException("coProductCostDistributionPercent shall be positive but it was " + coProductCostDistributionPercent);
			}
		}
		else
		{
			this.coProductCostDistributionPercent = null;
		}
	}

	/** DON'T call it directly. It's called only by API */
	public void setId(@NonNull final PPOrderCostId id)
	{
		this.id = id;
	}

	public ProductId getProductId()
	{
		return getCostSegmentAndElement().getProductId();
	}

	public CostElementId getCostElementId()
	{
		return getCostSegmentAndElement().getCostElementId();
	}

	public boolean isInboundCost()
	{
		return !getTrxType().isOutboundCost();
	}

	public boolean isMainProduct()
	{
		return getTrxType() == PPOrderCostTrxType.MainProduct;
	}

	public boolean isCoProduct()
	{
		return getTrxType().isCoProduct();
	}

	public boolean isByProduct()
	{
		return getTrxType() == PPOrderCostTrxType.ByProduct;
	}

	public PPOrderCost addingAccumulatedAmountAndQty(@NonNull final CostAmount amt, @NonNull final Quantity qty)
	{
		if (amt.isZero() && qty.isZero())
		{
			return this;
		}

		final boolean amtIsPositiveOrZero = amt.signum() >= 0;
		final boolean qtyIsPositiveOrZero = qty.signum() >= 0;
		if (amtIsPositiveOrZero != qtyIsPositiveOrZero)
		{
			throw new AdempiereException("Amount and Quantity shall have the same sign: " + amt + ", " + qty);
		}

		return toBuilder()
				.accumulatedAmount(getAccumulatedAmount().add(amt))
				.accumulatedQty(getAccumulatedQty().add(qty.toBigDecimal()))
				.build();
	}

	public PPOrderCost subtractingAccumulatedAmountAndQty(final CostAmount amt, final Quantity qty)
	{
		return addingAccumulatedAmountAndQty(amt.negate(), qty.negate());
	}

	public PPOrderCost withPrice(@NonNull final CostPrice newPrice)
	{
		if (this.getPrice().equals(newPrice))
		{
			return this;
		}

		return toBuilder().price(newPrice).build();
	}

	/* package */void setPostCalculationAmount(@NonNull final CostAmount postCalculationAmount)
	{
		this.postCalculationAmount = postCalculationAmount;
	}

	/* package */void setPostCalculationAmountAsAccumulatedAmtr()
	{
		setPostCalculationAmount(getAccumulatedAmount());
	}

	/* package */void setPostCalculationAmountAsZero()
	{
		setPostCalculationAmount(getPostCalculationAmount().toZero());
	}

}
