package de.metas.ui.web.dashboard;

import java.time.Duration;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class TimeRange
{
	public static TimeRange main(final long fromMillis, final long toMillis)
	{
		final boolean mainTimeRange = true;
		final int offsetMillis = 0;
		return new TimeRange(mainTimeRange, fromMillis, toMillis, offsetMillis);
	}

	public static TimeRange offset(final TimeRange mainRange, final Duration offset)
	{
		final boolean mainTimeRange = false;
		final long offsetMillis = offset.toMillis();
		final long fromMillis = mainRange.getFromMillis() + offsetMillis;
		final long toMillis = mainRange.getToMillis() + offsetMillis;
		return new TimeRange(mainTimeRange, fromMillis, toMillis, offsetMillis);
	}

	@JsonProperty("fromMillis")
	private final long fromMillis;

	@JsonProperty("toMillis")
	private final long toMillis;

	//
	@JsonIgnore
	private final boolean mainTimeRange;
	@JsonIgnore
	private final long offsetMillis;

	private TimeRange(final boolean mainTimeRange, final long fromMillis, final long toMillis, final long offsetMillis)
	{
		super();
		this.mainTimeRange = mainTimeRange;
		this.fromMillis = fromMillis;
		this.toMillis = toMillis;
		this.offsetMillis = offsetMillis;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("from", Instant.ofEpochMilli(fromMillis))
				.add("to", Instant.ofEpochMilli(toMillis))
				.add("main", mainTimeRange)
				.add("offset", Duration.ofMillis(offsetMillis))
				.toString();
	}

	public boolean isMainTimeRange()
	{
		return mainTimeRange;
	}

	public long getFromMillis()
	{
		return fromMillis;
	}

	public long getToMillis()
	{
		return toMillis;
	}

	public long getOffsetMillis()
	{
		return offsetMillis;
	}

	public long offsetDate(final long millis)
	{
		return millis + offsetMillis;
	}

	public long subtractOffset(final long millis)
	{
		return millis - offsetMillis;
	}
}
