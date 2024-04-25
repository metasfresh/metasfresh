/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Optional;

@EqualsAndHashCode
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class QtyTU implements Comparable<QtyTU>
{
	public static QtyTU ofInt(final int intValue)
	{
		if (intValue >= 0 && intValue < cache.length)
		{
			return cache[intValue];
		}
		else
		{
			return new QtyTU(intValue);
		}
	}

	@NonNull
	public static QtyTU ofBigDecimal(@NonNull final BigDecimal bd)
	{
		return ofInt(bd.intValueExact());
	}

	public static Optional<QtyTU> optionalOfBigDecimal(@Nullable final BigDecimal bd)
	{
		return Optional.ofNullable(bd).map(QtyTU::ofBigDecimal);
	}

	@NonNull
	public static QtyTU ofString(@NonNull final String stringValue)
	{
		return ofInt(NumberUtils.asInt(stringValue));
	}

	@JsonCreator
	@Nullable
	public static QtyTU ofNullableString(@Nullable final String stringValue)
	{
		final String stringValueNorm = StringUtils.trimBlankToNull(stringValue);
		return stringValueNorm != null ? ofString(stringValueNorm) : null;
	}

	public static final QtyTU ZERO;
	public static final QtyTU ONE;
	public static final QtyTU TWO;
	public static final QtyTU THREE;
	public static final QtyTU FOUR;
	private static final QtyTU[] cache = new QtyTU[] {
			ZERO = new QtyTU(0),
			ONE = new QtyTU(1),
			TWO = new QtyTU(2),
			THREE = new QtyTU(3),
			FOUR = new QtyTU(4),
			new QtyTU(5),
			new QtyTU(6),
			new QtyTU(7),
			new QtyTU(8),
			new QtyTU(9),
			new QtyTU(10),
	};

	final int intValue;

	private QtyTU(final int intValue)
	{
		Check.assumeGreaterOrEqualToZero(intValue, "QtyTU");
		this.intValue = intValue;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return String.valueOf(intValue);
	}

	@JsonValue
	public int toInt()
	{
		return intValue;
	}

	public BigDecimal toBigDecimal()
	{
		return BigDecimal.valueOf(intValue);
	}

	@Override
	public int compareTo(@NonNull final QtyTU other)
	{
		return this.intValue - other.intValue;
	}

	public boolean isGreaterThan(@NonNull final QtyTU other) {return compareTo(other) > 0;}

	public boolean isZero() {return intValue == 0;}

	public boolean isPositive() {return intValue > 0;}

	public boolean isOne() {return intValue == 1;}

	public QtyTU add(@NonNull final QtyTU toAdd)
	{
		if (this.intValue == 0)
		{
			return toAdd;
		}
		else if (toAdd.intValue == 0)
		{
			return this;
		}
		else
		{
			return ofInt(this.intValue + toAdd.intValue);
		}
	}

	public QtyTU subtractOrZero(@NonNull final int toSubtract)
	{
		if (toSubtract <= 0)
		{
			return this;
		}
		else
		{
			return ofInt(Math.max(this.intValue - toSubtract, 0));
		}
	}

	public QtyTU subtractOrZero(@NonNull final QtyTU toSubtract)
	{
		if (toSubtract.intValue == 0)
		{
			return this;
		}
		else
		{
			return ofInt(Math.max(this.intValue - toSubtract.intValue, 0));
		}
	}
}
