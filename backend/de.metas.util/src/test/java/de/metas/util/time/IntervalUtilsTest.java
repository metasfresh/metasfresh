/*
 * #%L
 * de.metas.util
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

package de.metas.util.time;

import com.google.common.collect.ImmutableList;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.format.DateTimeFormatter.ISO_INSTANT;

public class IntervalUtilsTest
{
	private static final Logger logger = LoggerFactory.getLogger(IntervalUtilsTest.class);

	/**
	 * input:
	 *  to keep: [{from: "2020-01-12T10:15:30Z", to: "2020-06-28T15:11:12Z"}],
	 *  to remove: [
	 *  {from: "2020-01-01T15:11:12Z", to: "2020-02-15T18:11:12Z"},
	 *  {from: "2020-02-15T18:11:12Z", to: "2020-04-18T15:11:12Z"}
	 *  {from: "2020-05-02T19:11:12Z", to: "2020-07-15T20:11:12Z"}
	 *  {from: "2020-07-15T20:11:12Z", to: "2021-01-25T15:11:12Z"}
	 *  ],
	 * output:
	 *  expected: [2020-04-18T15:11:12Z, 2020-05-02T19:11:12Z]
	 */
	@Test
	public void intervalDiff()
	{
		final StringToInstantConvertor stringToInstantConvertor = StringToInstantConvertor.builder()
				.dateTimeFormatter(ISO_INSTANT)
				.build();
		//given
		final InstantInterval instantIntervalToKeep = InstantInterval.of(
				stringToInstantConvertor.getInstant("2020-01-12T10:15:30Z"),
				stringToInstantConvertor.getInstant("2020-06-28T15:11:12Z"));

		final ImmutableList<InstantInterval> intervalGaps = ImmutableList.of(
				InstantInterval.of(
						stringToInstantConvertor.getInstant("2020-01-01T15:11:12Z"),
						stringToInstantConvertor.getInstant("2020-02-15T18:11:12Z")),

				InstantInterval.of(
						stringToInstantConvertor.getInstant("2020-02-15T18:11:12Z"),
						stringToInstantConvertor.getInstant("2020-04-18T15:11:12Z")),

				InstantInterval.of(
						stringToInstantConvertor.getInstant("2020-05-02T19:11:12Z"),
						stringToInstantConvertor.getInstant("2020-07-15T20:11:12Z")),

				InstantInterval.of(
						stringToInstantConvertor.getInstant("2020-07-15T20:11:12Z"),
						stringToInstantConvertor.getInstant("2021-01-25T15:11:12Z"))
		);

		//when
		final List<InstantInterval> noGaps = IntervalUtils.intervalDiff(ImmutableList.of(instantIntervalToKeep), intervalGaps);

		//then
		Assert.assertEquals(noGaps.size(), 1);
		Assert.assertEquals(noGaps.get(0).getFrom(), stringToInstantConvertor.getInstant("2020-04-18T15:11:12Z"));
		Assert.assertEquals(noGaps.get(0).getTo(), stringToInstantConvertor.getInstant("2020-05-02T19:11:12Z"));
	}

	@Value
	@Builder
	private static class StringToInstantConvertor {

		@NonNull
		DateTimeFormatter dateTimeFormatter;

		public Instant getInstant(@NonNull final String value)
		{
			return dateTimeFormatter.parse(value, Instant::from);
		}
	}
}
