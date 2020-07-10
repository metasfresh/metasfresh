package de.metas.uom;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import de.metas.util.Check;
import de.metas.util.NumberUtils;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
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

@Builder
@Value
public class UOMConversionRate
{
	UomId fromUomId;
	UomId toUomId;

	@Getter(AccessLevel.NONE)
	BigDecimal fromToMultiplier;
	@Getter(AccessLevel.NONE)
	BigDecimal toFromMultiplier;

	boolean catchUOMForProduct;

	@Builder
	private UOMConversionRate(
			@NonNull final UomId fromUomId,
			@NonNull final UomId toUomId,
			@Nullable final BigDecimal fromToMultiplier,
			@Nullable final BigDecimal toFromMultiplier,
			final boolean catchUOMForProduct)
	{
		Check.assume(fromToMultiplier != null || toFromMultiplier != null, "at least fromToMultiplier={} or toFromMultiplier={} shall be not null", fromToMultiplier, toFromMultiplier);
		Check.assume(fromToMultiplier == null || fromToMultiplier.signum() != 0, "invalid fromToMultiplier: {}", fromToMultiplier);
		Check.assume(toFromMultiplier == null || toFromMultiplier.signum() != 0, "invalid toFromMultiplier: {}", toFromMultiplier);

		this.fromUomId = fromUomId;
		this.toUomId = toUomId;
		this.fromToMultiplier = fromToMultiplier;
		this.toFromMultiplier = toFromMultiplier;
		this.catchUOMForProduct = catchUOMForProduct;
	}

	public static UOMConversionRate one(@NonNull final UomId uomId)
	{
		return builder()
				.fromUomId(uomId)
				.toUomId(uomId)
				.fromToMultiplier(BigDecimal.ONE)
				.toFromMultiplier(BigDecimal.ONE)
				.build();
	}

	public UOMConversionRate invert()
	{
		if (fromUomId.equals(toUomId))
		{
			return this;
		}

		return UOMConversionRate.builder()
				.fromUomId(toUomId)
				.toUomId(fromUomId)
				.fromToMultiplier(toFromMultiplier)
				.toFromMultiplier(fromToMultiplier)
				.build();
	}

	public boolean isOne()
	{
		return (fromToMultiplier == null || fromToMultiplier.compareTo(BigDecimal.ONE) == 0)
				&& (toFromMultiplier == null || toFromMultiplier.compareTo(BigDecimal.ONE) == 0);
	}

	public BigDecimal getFromToMultiplier()
	{
		if (fromToMultiplier != null)
		{
			return fromToMultiplier;
		}
		else
		{
			return computeInvertedMultiplier(toFromMultiplier);
		}
	}

	public static BigDecimal computeInvertedMultiplier(@NonNull final BigDecimal multiplier)
	{
		if (multiplier.signum() == 0)
		{
			throw new AdempiereException("Multiplier shall not be ZERO");
		}
		return NumberUtils.stripTrailingDecimalZeros(BigDecimal.ONE.divide(multiplier, 12, RoundingMode.HALF_UP));
	}

	public BigDecimal convert(@NonNull final BigDecimal qty, @NonNull final UOMPrecision precision)
	{
		if (qty.signum() == 0)
		{
			return qty;
		}

		if (fromToMultiplier != null)
		{
			return precision.round(qty.multiply(fromToMultiplier));
		}
		else
		{
			return qty.divide(toFromMultiplier, precision.toInt(), precision.getRoundingMode());
		}
	}
}
