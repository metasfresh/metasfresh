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
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.math.BigDecimal;

@EqualsAndHashCode
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class QtyTU implements Comparable<QtyTU>
{
	@JsonCreator
	public static QtyTU ofInt(final int intValue)
	{
		if (intValue == 0)
		{
			return ZERO;
		}
		else if (intValue == 1)
		{
			return ONE;
		}
		else
		{
			return new QtyTU(intValue);
		}
	}

	public static QtyTU ofBigDecimal(@NonNull final BigDecimal bd)
	{
		return ofInt(bd.intValueExact());
	}

	public static final QtyTU ZERO = new QtyTU(0);
	public static final QtyTU ONE = new QtyTU(1);

	final int intValue;

	private QtyTU(final int intValue)
	{
		Check.assumeGreaterOrEqualToZero(intValue, "QtyTU");
		this.intValue = intValue;
	}

	@Override
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

	public boolean isGreaterThan(@NonNull final QtyTU other)
	{
		return compareTo(other) > 0;
	}
}
