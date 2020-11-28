/*
 * #%L
 * de.metas.manufacturing
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

package de.metas.manufacturing.generatedcomponents;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class PasswordGeneratorTest
{
	private final PasswordGenerator generator = new PasswordGenerator();

	@ParameterizedTest
	@ValueSource(ints = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 })
	void passwordLength(final int length)
	{
		assertThat(generator.generatePassword(length, true, true, true, true)).hasSize(length);
	}

	@Test
	void noLowercase()
	{
		assertThat(generator.generatePassword(20, false, true, true, true).split(""))
				.noneMatch(s -> Character.isLowerCase(s.charAt(0)));
	}

	@Test
	void noUppercase()
	{
		assertThat(generator.generatePassword(20, true, false, true, true).split(""))
				.noneMatch(s -> Character.isUpperCase(s.charAt(0)));
	}

	@Test
	void noDigits()
	{
		assertThat(generator.generatePassword(20, true, true, false, true).split(""))
				.noneMatch(s -> Character.isDigit(s.charAt(0)));
	}

	@Test
	void noPunctuation()
	{
		assertThat(generator.generatePassword(20, true, true, true, false).split(""))
				.allMatch(s -> Character.isLetterOrDigit(s.charAt(0)));
	}

}
