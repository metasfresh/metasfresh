package de.metas.util.lang;

import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;

import com.google.common.annotations.VisibleForTesting;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.util
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

@UtilityClass
public class UIDStringUtil
{
	private static final AtomicLong nextViewId = new AtomicLong(1);
	private static final char[] VIEW_DIGITS = {
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
			'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
			'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
			'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	};

	private static final Supplier<String> DEFAULT_RANDOM_UUID_SOURCE = () -> UUID.randomUUID().toString();

	private static Supplier<String> randomUUIDSource;

	public String createNext()
	{
		return encodeUsingDigits(nextViewId.getAndIncrement(), VIEW_DIGITS);
	}

	@VisibleForTesting
	String encodeUsingDigits(final long value, char[] digits)
	{
		if (value == 0)
		{
			return String.valueOf(digits[0]);
		}

		final int base = digits.length;
		final StringBuilder buf = new StringBuilder();
		long currentValue = value;
		while (currentValue > 0)
		{
			final int remainder = (int)(currentValue % base);
			currentValue = currentValue / base;
			buf.append(digits[remainder]);
		}

		return buf.reverse().toString();
	}

	public void setRandomUUIDSource(@NonNull final Supplier<String> newRandomUUIDSource)
	{
		randomUUIDSource = newRandomUUIDSource;
	}

	public void reset()
	{
		nextViewId.set(1);
		randomUUIDSource = null;
	}

	public String createRandomUUID()
	{
		return coalesceSuppliers(randomUUIDSource, DEFAULT_RANDOM_UUID_SOURCE);
	}
}
