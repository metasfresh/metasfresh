package de.metas.util.time.generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import de.metas.util.time.generator.DaysOfWeekExploder;

/*
 * #%L
 * de.metas.util
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class DaysOfWeekExploderTest
{
	@Test
	public void test_StandardCases()
	{
		final DaysOfWeekExploder exploder = DaysOfWeekExploder.of(DayOfWeek.MONDAY, DayOfWeek.THURSDAY);

		//
		// Forward:
		assertThat(exploder.explodeForward(_2018_July(2)))
				.isEqualTo(ImmutableSet.of(_2018_July(2), _2018_July(5)));

		assertThat(exploder.explodeForward(_2018_July(3)))
				.isEqualTo(ImmutableSet.of(_2018_July(5)));

		//
		// Backward:
		assertThat(exploder.explodeBackward(_2018_July(8)))
				.isEqualTo(ImmutableSet.of(_2018_July(2), _2018_July(5)));

		assertThat(exploder.explodeBackward(_2018_July(4)))
				.isEqualTo(ImmutableSet.of(_2018_July(2), _2018_June(28)));
	}

	private static LocalDateTime _2018_June(final int day)
	{
		return LocalDateTime.of(2018, Month.JUNE, day, 0, 0);
	}

	private static LocalDateTime _2018_July(final int day)
	{
		return LocalDateTime.of(2018, Month.JULY, day, 0, 0);
	}
}
