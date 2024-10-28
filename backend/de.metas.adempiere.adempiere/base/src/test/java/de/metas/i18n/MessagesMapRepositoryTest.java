/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.i18n;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class MessagesMapRepositoryTest
{
	@Nested
	class normalizeToJavaMessageFormat
	{
		@Test
		void empty()
		{
			assertThat(MessagesMapRepository.normalizeToJavaMessageFormat("")).isEmpty();
		}

		@Test
		void noPlaceholder()
		{
			assertThat(MessagesMapRepository.normalizeToJavaMessageFormat("some text without placeholders"))
					.isEqualTo("some text without placeholders");
		}

		@Test
		void placeholderAtStringStart()
		{
			assertThat(MessagesMapRepository.normalizeToJavaMessageFormat("{} bla")).isEqualTo("{0} bla");
		}

		@Test
		void placeholderAtStringEnd()
		{
			assertThat(MessagesMapRepository.normalizeToJavaMessageFormat("bla {}")).isEqualTo("bla {0}");
		}

		@Test
		void multiplePlaceholders()
		{
			assertThat(MessagesMapRepository.normalizeToJavaMessageFormat("{} then {} and then {}"))
					.isEqualTo("{0} then {1} and then {2}");
		}

		@Test
		void multiplePlaceholdersMixedWithNormalizedPlaceholders()
		{
			assertThat(MessagesMapRepository.normalizeToJavaMessageFormat("{} vs {2} then {} vs {1} and then {} vs {0}"))
					.isEqualTo("{0} vs {2} then {1} vs {1} and then {2} vs {0}");
		}

	}
}