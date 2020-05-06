package de.metas.calendar;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class RecurrentNonBusinessDayTest
{
	@Test
	public void test_Christmas()
	{
		final RecurrentNonBusinessDay nonBusinessDay = RecurrentNonBusinessDay.builder()
				.frequency(RecurrentNonBusinessDayFrequency.YEARLY)
				.startDate(LocalDate.of(1970, Month.DECEMBER, 25))
				.name("Christmas")
				.build();

		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.DECEMBER, 24))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.DECEMBER, 25))).isTrue();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.DECEMBER, 26))).isFalse();
	}

	@Test
	public void test_Sunday()
	{
		final RecurrentNonBusinessDay nonBusinessDay = RecurrentNonBusinessDay.builder()
				.frequency(RecurrentNonBusinessDayFrequency.WEEKLY)
				.startDate(LocalDate.of(1970, Month.JANUARY, 4))
				.name("Sunday")
				.build();

		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JUNE, 30))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 1))).isTrue();

		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 2))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 3))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 4))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 5))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 6))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 7))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 8))).isTrue();

		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 9))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 10))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 11))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 12))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 13))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 14))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 15))).isTrue();

		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 16))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 17))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 18))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 19))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 20))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 21))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 22))).isTrue();

		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 23))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 24))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 25))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 26))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 27))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 28))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 29))).isTrue();

		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 30))).isFalse();
		assertThat(nonBusinessDay.isMatching(LocalDate.of(2018, Month.JULY, 31))).isFalse();
	}
}
