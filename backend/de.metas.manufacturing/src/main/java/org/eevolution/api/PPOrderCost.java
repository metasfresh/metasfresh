package org.eevolution.api;

import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.uom.UomId;
import de.metas.util.lang.Percent;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.adempiere.exceptions.AdempiereException;

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

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public class PPOrderCost
{
	@NonFinal
	@Nullable
	PPOrderCostId id;

	PPOrderCostTrxType trxType;

	CostSegmentAndElement costSegmentAndElement;

	CostPrice price;

	CostAmount accumulatedAmount;
	Quantity accumulatedQty;

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
			@NonNull final Quantity accumulatedQty,
			@Nullable final Percent coProductCostDistributionPercent,
			@Nullable final CostAmount postCalculationAmount)
	{
		if (!UomId.equals(price.getUomId(), accumulatedQty.getUomId()))
		{
			throw new AdempiereException("UOM not matching")
					.setParameter("price", price)
					.setParameter("accumulatedQty", accumulatedQty)
					.appendParametersToMessage();
		}

		this.id = id;
		this.trxType = trxType;
		this.costSegmentAndElement = costSegmentAndElement;
		this.price = price;
		this.accumulatedAmount = accumulatedAmount != null ? accumulatedAmount : CostAmount.zero(price.getCurrencyId());
		this.accumulatedQty = accumulatedQty;
		this.postCalculationAmount = postCalculationAmount != null ? postCalculationAmount : CostAmount.zero(price.getCurrencyId());

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

	/**
	 * DON'T call it directly. It's called only by API
	 */
	public void setId(@NonNull final PPOrderCostId id)
	{
		this.id = id;
	}

	public ProductId getProductId()
	{
		return getCostSegmentAndElement().getProductId();
	}

	public UomId getUomId()
	{
		return getPrice().getUomId();
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

	@NonNull
	public PPOrderCost addingAccumulatedAmountAndQty(
			@NonNull final CostAmount amt,
			@NonNull final Quantity qty,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		if (amt.isZero() && qty.isZero())
		{
			return this;
		}

		final boolean amtIsPositiveOrZero = amt.signum() >= 0;
		final boolean amtIsNotZero = amt.signum() != 0;
		final boolean qtyIsPositiveOrZero = qty.signum() >= 0;
		if (amtIsNotZero && amtIsPositiveOrZero != qtyIsPositiveOrZero )
		{
			throw new AdempiereException("Amount and Quantity shall have the same sign: " + amt + ", " + qty);
		}

		final Quantity accumulatedQty = getAccumulatedQty();
		final Quantity qtyConv = uomConverter.convertQuantityTo(qty, getProductId(), accumulatedQty.getUomId());

		return toBuilder()
				.accumulatedAmount(getAccumulatedAmount().add(amt))
				.accumulatedQty(accumulatedQty.add(qtyConv))
				.build();
	}

	public PPOrderCost subtractingAccumulatedAmountAndQty(
			@NonNull final CostAmount amt,
			@NonNull final Quantity qty,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		return addingAccumulatedAmountAndQty(amt.negate(), qty.negate(), uomConverter);
	}

	/* package */void setPostCalculationAmount(@NonNull final CostAmount postCalculationAmount)
	{
		this.postCalculationAmount = postCalculationAmount;
	}

	/* package */void setPostCalculationAmountAsAccumulatedAmt()
	{
		setPostCalculationAmount(getAccumulatedAmount());
	}

	/* package */void setPostCalculationAmountAsZero()
	{
		setPostCalculationAmount(getPostCalculationAmount().toZero());
	}

}

