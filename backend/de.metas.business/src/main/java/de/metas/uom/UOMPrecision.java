package de.metas.uom;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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

@EqualsAndHashCode
@ToString
public class UOMPrecision
{
	@JsonCreator
	public static UOMPrecision ofInt(final int precision)
	{
		if (precision >= 0 && precision < cachedValues.length)
		{
			return cachedValues[precision];
		}
		else
		{
			return new UOMPrecision(precision);
		}
	}

	public static final UOMPrecision ZERO = new UOMPrecision(0);
	public static final UOMPrecision TWO = new UOMPrecision(2);
	public static final UOMPrecision TWELVE = new UOMPrecision(12);

	private static final UOMPrecision[] cachedValues = new UOMPrecision[] {
			ZERO,
			new UOMPrecision(1),
			TWO,
			new UOMPrecision(3),
			new UOMPrecision(4),
			new UOMPrecision(5),
			new UOMPrecision(6),
			new UOMPrecision(7),
			new UOMPrecision(8),
			new UOMPrecision(9),
			new UOMPrecision(10),
			new UOMPrecision(11),
			TWELVE,
	};

	private final int precision;

	private UOMPrecision(final int precision)
	{
		Check.assumeGreaterOrEqualToZero(precision, "precision");
		this.precision = precision;
	}

	@JsonValue
	public int toInt()
	{
		return precision;
	}

	/**
	 * @return always RoundingMode#UP. Example: we convert 300GR to piece; one piece is one kilo; we need one piece and not 0 piece as the result, so we need to round UP.
	 */
	public RoundingMode getRoundingMode()
	{
		return RoundingMode.UP;
	}

	public BigDecimal roundIfNeeded(@NonNull final BigDecimal qty)
	{
		if (qty.scale() > precision)
		{
			return qty.setScale(precision, getRoundingMode());
		}
		else
		{
			return qty;
		}
	}

	public BigDecimal round(@NonNull final BigDecimal qty)
	{
		return qty.setScale(precision, getRoundingMode());
	}

	public BigDecimal adjustToPrecisionWithoutRoundingIfPossible(@NonNull final BigDecimal qty)
	{
		// NOTE: it seems that ZERO is a special case of BigDecimal, so we are computing it right away
		if (qty == null || qty.signum() == 0)
		{
			return BigDecimal.ZERO.setScale(precision);
		}

		final BigDecimal qtyNoZero = qty.stripTrailingZeros();
		final int qtyScale = qtyNoZero.scale();
		if (qtyScale >= precision)
		{
			// Qty's actual scale is bigger than UOM precision, don't touch it
			return qtyNoZero;
		}
		else
		{
			// Qty's actual scale is less than UOM precision. Try to convert it to UOM precision
			// NOTE: we are using without scale because it shall be scaled without any problem
			return qtyNoZero.setScale(precision, RoundingMode.HALF_UP);
		}
	}
}
