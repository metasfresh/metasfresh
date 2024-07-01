package de.metas.costing;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import de.metas.money.CurrencyId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.time.DurationUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.TemporalUnit;
import java.util.function.UnaryOperator;

/*
 * #%L
 * de.metas.business
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

@Value
public class CostPrice
{
	public static CostPrice ownCostPrice(
			@NonNull final CostAmount ownCostPrice,
			@NonNull final UomId uomId)
	{
		final CostAmount zero = ownCostPrice.toZero();
		return CostPrice.builder()
				.ownCostPrice(ownCostPrice)
				.componentsCostPrice(zero)
				.uomId(uomId)
				.build();
	}

	public static CostPrice zero(
			@NonNull final CurrencyId currencyId,
			@NonNull final UomId uomId)
	{
		final CostAmount zero = CostAmount.zero(currencyId);
		return CostPrice.builder()
				.ownCostPrice(zero)
				.componentsCostPrice(zero)
				.uomId(uomId)
				.build();
	}

	@NonNull CurrencyId currencyId;
	@NonNull CostAmount ownCostPrice;
	@NonNull CostAmount componentsCostPrice;
	@NonNull UomId uomId;

	@Builder(toBuilder = true)
	private CostPrice(
			@NonNull final CostAmount ownCostPrice,
			@NonNull final CostAmount componentsCostPrice,
			@NonNull final UomId uomId)
	{
		Check.assumeEquals(ownCostPrice.getCurrencyId(), componentsCostPrice.getCurrencyId());

		currencyId = ownCostPrice.getCurrencyId();

		this.ownCostPrice = ownCostPrice;
		this.componentsCostPrice = componentsCostPrice;
		this.uomId = uomId;
	}

	@Override
	public String toString()
	{
		final MoreObjects.ToStringHelper builder = MoreObjects.toStringHelper(this);
		boolean isZero = true;
		if (!ownCostPrice.isZero())
		{
			builder.add("ownCostPrice", ownCostPrice);
			isZero = false;
		}
		if (!componentsCostPrice.isZero())
		{
			builder.add("componentsCostPrice", componentsCostPrice);
			isZero = false;
		}

		if (isZero)
		{
			builder.addValue("ZERO");
		}

		return builder.toString();
	}

	public CostAmount toCostAmount()
	{
		return getOwnCostPrice().add(getComponentsCostPrice());
	}

	@VisibleForTesting
	public BigDecimal toBigDecimal()
	{
		return toCostAmount().toBigDecimal();
	}

	public CostPrice addToOwnCostPrice(@NonNull final CostAmount ownCostPriceToAdd)
	{
		if (ownCostPriceToAdd.isZero())
		{
			return this;
		}

		return withOwnCostPrice(getOwnCostPrice().add(ownCostPriceToAdd));
	}

	public CostPrice withOwnCostPrice(final CostAmount ownCostPrice)
	{
		return toBuilder().ownCostPrice(ownCostPrice).build();
	}

	public CostPrice withZeroOwnCostPrice()
	{
		final CostAmount ownCostPrice = getOwnCostPrice();
		if (ownCostPrice.isZero())
		{
			return this;
		}

		return withOwnCostPrice(ownCostPrice.toZero());
	}

	public CostPrice withZeroComponentsCostPrice()
	{
		final CostAmount componentsCostPrice = getComponentsCostPrice();
		if (componentsCostPrice.isZero())
		{
			return this;
		}

		return withComponentsCostPrice(componentsCostPrice.toZero());
	}

	public CostPrice withComponentsCostPrice(final CostAmount componentsCostPrice)
	{
		return toBuilder().componentsCostPrice(componentsCostPrice).build();
	}

	public CostPrice add(final CostPrice costPrice)
	{
		if (!UomId.equals(this.getUomId(), costPrice.getUomId()))
		{
			throw new AdempiereException("UOM does not match: " + this + ", " + costPrice);
		}

		return builder()
				.ownCostPrice(getOwnCostPrice().add(costPrice.getOwnCostPrice()))
				.componentsCostPrice(getComponentsCostPrice().add(costPrice.getComponentsCostPrice()))
				.uomId(getUomId())
				.build();
	}

	public CostAmount multiply(@NonNull final Quantity quantity)
	{
		if (!UomId.equals(uomId, quantity.getUomId()))
		{
			throw new AdempiereException("UOM does not match: " + this + ", " + quantity);
		}

		return toCostAmount().multiply(quantity);
	}

	public CostAmount multiply(
			@NonNull final Duration duration,
			@NonNull final TemporalUnit durationUnit)
	{
		final BigDecimal durationBD = DurationUtils.toBigDecimal(duration, durationUnit);
		return toCostAmount().multiply(durationBD);
	}

	public CostPrice convertAmounts(
			@NonNull final UomId toUomId,
			@NonNull final UnaryOperator<CostAmount> converter)
	{
		if (UomId.equals(this.uomId, toUomId))
		{
			return this;
		}

		return toBuilder()
				.uomId(toUomId)
				.ownCostPrice(converter.apply(getOwnCostPrice()))
				.componentsCostPrice(converter.apply(getComponentsCostPrice()))
				.build();
	}
}
