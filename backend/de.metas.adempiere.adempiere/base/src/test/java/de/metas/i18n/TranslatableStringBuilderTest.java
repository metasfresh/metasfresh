package de.metas.i18n;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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

public class TranslatableStringBuilderTest
{
	@Test
	public void testStringsAreAggregated_1()
	{
		final ITranslatableString actual = TranslatableStringBuilder.newInstance()
				.append("a")
				.append("b")
				.append("c")
				.build();

		final ITranslatableString expected = TranslatableStrings.constant("abc");
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void testStringsAreAggregated_2()
	{
		final ITranslatableString actual = TranslatableStringBuilder.newInstance()
				.append("a")
				.append("b")
				.append("c")
				.append(66)
				.build();

		final ITranslatableString expected = TranslatableStrings.join("",
				TranslatableStrings.constant("abc"),
				TranslatableStrings.number(66));

		assertThat(actual).isEqualTo(expected);
	}

	@Nested
	public class isEmpty
	{
		@Test
		public void whenCreated()
		{
			final TranslatableStringBuilder builder = TranslatableStringBuilder.newInstance();
			assertThat(builder.isEmpty()).isTrue();
		}

		@Test
		public void whenSingleStringAppended()
		{
			final TranslatableStringBuilder builder = TranslatableStringBuilder.newInstance();
			builder.append("string constant");
			assertThat(builder.isEmpty()).isFalse();
		}

		@Test
		public void whenEmptyStringAppended()
		{
			final TranslatableStringBuilder builder = TranslatableStringBuilder.newInstance();
			builder.append("");
			assertThat(builder.isEmpty()).isTrue();
		}

	}
}
