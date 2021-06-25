/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.kpi;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import lombok.NonNull;
import lombok.Value;

import java.time.Duration;
import java.time.Instant;

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class TimeRange
{
	public static TimeRange main(@NonNull final Instant from, @NonNull final Instant to)
	{
		return new TimeRange(true, from, to, Duration.ZERO);
	}

	public static TimeRange offset(@NonNull final TimeRange mainRange, @NonNull final Duration offset)
	{
		final boolean mainTimeRange = false;
		final Instant from = mainRange.getFrom().plus(offset);
		final Instant to = mainRange.getTo().plus(offset);
		return new TimeRange(mainTimeRange, from, to, offset);
	}

	@JsonProperty("fromMillis") long fromMillis;
	@JsonIgnore Instant from;

	@JsonProperty("toMillis") long toMillis;
	@JsonIgnore Instant to;

	@JsonIgnore boolean mainTimeRange;
	@JsonIgnore Duration offset;

	private TimeRange(
			final boolean mainTimeRange,
			@NonNull final Instant from,
			@NonNull final Instant to,
			@NonNull final Duration offset)
	{
		this.mainTimeRange = mainTimeRange;
		this.from = from;
		this.fromMillis = toMillis(from);
		this.to = to;
		this.toMillis = toMillis(to);
		this.offset = offset;
	}

	private static long toMillis(@NonNull final Instant instant)
	{
		return instant.isAfter(Instant.ofEpochMilli(0))
				? instant.toEpochMilli()
				: 0;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("from", from)
				.add("to", to)
				.add("main", mainTimeRange)
				.add("offset", offset)
				.toString();
	}

	public TimeRange offset(@NonNull final Duration offset)
	{
		final boolean mainTimeRange = false;
		final Instant from = this.from.plus(offset);
		final Instant to = this.to.plus(offset);
		return new TimeRange(mainTimeRange, from, to, offset);
	}
}
