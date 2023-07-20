/*
 * #%L
 * de.metas.serviceprovider.base
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

package de.metas.serviceprovider.timebooking;

import de.metas.util.time.HmmUtils;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Duration;

@Value
public class Effort
{
	public final static Effort ZERO = new Effort(0);

	long seconds;

	@NonNull
	public static Effort ofNullable(@Nullable final String hmm)
	{
		final long seconds = hmm != null
				? HmmUtils.hmmToSeconds(hmm)
				: 0;

		return new Effort(seconds);
	}

	@NonNull
	public static Effort ofSeconds(final long seconds)
	{
		return new Effort(seconds);
	}

	@NonNull
	public static Effort ofDuration(@NonNull final Duration duration)
	{
		return ofSeconds(duration.getSeconds());
	}

	@NonNull
	public Effort addNullSafe(@Nullable final Effort effort)
	{
		final long secondsToAdd = effort != null
				? effort.getSeconds()
				: 0;

		final long secondsSum = getSeconds() + secondsToAdd;

		return new Effort(secondsSum);
	}

	@NonNull
	public String getHmm()
	{
		return HmmUtils.secondsToHmm(seconds);
	}

	@NonNull
	public Effort negate()
	{
		return new Effort(-seconds);
	}

	@NonNull
	public BigDecimal toHours()
	{
		final Duration duration = Duration.ofSeconds(seconds);

		return new BigDecimal(duration.toHours());
	}

	private Effort(final long seconds)
	{
		this.seconds = seconds;
	}
}
