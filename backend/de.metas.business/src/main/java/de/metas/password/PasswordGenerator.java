/*
 * #%L
 * de.metas.business
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

package de.metas.password;

import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Borrowed from: https://mkyong.com/java/java-password-generator-example/
 */
@Service
public class PasswordGenerator
{
	private static final String CHAR_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
	private static final String CHAR_UPPERCASE = CHAR_LOWERCASE.toUpperCase();
	private static final String DIGIT = "0123456789";
	private static final String PUNCTUATION = "!@#&()–[{}]:;',?/*";

	final Random random = new Random();

	public String generatePassword(
			final int passwordLength,
			final boolean useLowercase,
			final boolean useUppercase,
			final boolean useDigit,
			final boolean usePunctuation
	)
	{
		if ((passwordLength < 1))
		{
			throw new AdempiereException("Password length must be > 0");
		}

		String FILL_CHARACTERS = "";
		final StringBuilder result = new StringBuilder(passwordLength);

		if (useLowercase)
		{
			// guaranteed 2 chars (lowercase)
			result.append(generateRandomString(CHAR_LOWERCASE, 2));

			FILL_CHARACTERS += CHAR_LOWERCASE;
		}

		if (useUppercase)
		{
			// guaranteed 2 chars (uppercase)
			result.append(generateRandomString(CHAR_UPPERCASE, 2));

			FILL_CHARACTERS += CHAR_UPPERCASE;
		}

		if (useDigit)
		{
			// guaranteed 2 digits
			result.append(generateRandomString(DIGIT, 2));

			FILL_CHARACTERS += DIGIT;
		}

		if (usePunctuation)
		{
			// guaranteed 2 punctuation
			result.append(generateRandomString(PUNCTUATION, 2));

			// don't fill with punctuation
			// FILL_CHARACTERS += PUNCTUATION;
		}

		{
			// remaining until length: random
			result.append(generateRandomString(FILL_CHARACTERS, passwordLength - result.length()));
		}

		return shuffleString(result.substring(0, passwordLength));
	}

	@NonNull
	private String generateRandomString(final String input, final int size)
	{
		if (size < 1)
		{
			return "";
		}

		final StringBuilder result = new StringBuilder(size);
		for (int i = 0; i < size; i++)
		{
			// produce a random order
			final int index = random.nextInt(input.length());
			result.append(input.charAt(index));
		}
		return result.toString();
	}

	public String shuffleString(final String input)
	{
		final List<String> result = Arrays.asList(input.split(""));
		Collections.shuffle(result);

		return String.join("", result);
	}
}
