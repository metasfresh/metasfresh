/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.report;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

@EqualsAndHashCode
public final class PrintCopies
{
	@JsonCreator
	public static final PrintCopies ofInt(final int copies)
	{
		return copies >= 0 && copies < CACHE.length
				? CACHE[copies]
				: new PrintCopies(copies);
	}

	public static final PrintCopies ZERO = new PrintCopies(0);
	public static final PrintCopies ONE = new PrintCopies(1);
	private static final PrintCopies[] CACHE = {
			ZERO,
			ONE,
			new PrintCopies(2),
			new PrintCopies(3),
			new PrintCopies(4),
			new PrintCopies(5)
	};

	private final int value;

	public PrintCopies(final int value)
	{
		if (value < 0)
		{
			throw new AdempiereException("Value shall be >= 0");
		}
		this.value = value;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return String.valueOf(value);
	}

	@JsonValue
	public int toInt()
	{
		return value;
	}

	public boolean isZero() { return value == 0; }

	public boolean isGreaterThanZero() { return value > 0; }

	public boolean isGreaterThanOne() { return value > 1; }

	public PrintCopies minimumOne()
	{
		return isZero() ? ONE : this;
	}

	public PrintCopies plus(@NonNull final PrintCopies other)
	{
		if (other.isZero())
		{
			return this;
		}
		else if (isZero())
		{
			return other;
		}
		else
		{
			return ofInt(this.value + other.value);
		}
	}
}
