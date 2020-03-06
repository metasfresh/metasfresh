/*
 * #%L
 * de.metas.payment.esr
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

package de.metas.payment.esr.api.impl;

import com.google.common.collect.ImmutableSet;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static de.metas.payment.esr.api.impl.ESRBPBankAccountDAO.createMatchingESRAccountNumbers;
import static org.junit.jupiter.api.Assertions.*;

class ESRBPBankAccountDAOTest
{
	@Nested
	class MatchingESRAccountNumbersTest
	{
		@Test
		void esrHas9Digits()
		{
			final ImmutableSet<String> esrs = createMatchingESRAccountNumbers("01-1067-4");
			esrs.forEach(s -> assertEquals(9, s.length()));
		}

		@Test
		void fromEsrWithDash()
		{
			final ImmutableSet<String> esrs = createMatchingESRAccountNumbers("01-1067-4");
			assertEquals(2, esrs.size());

			Assertions.assertThat(esrs).containsAll(Arrays.asList("01-1067-4", "010010674"));
		}

		@Test
		void fromEsrWithoutDash()
		{
			final ImmutableSet<String> esrs = createMatchingESRAccountNumbers("010010674");
			assertEquals(2, esrs.size());

			Assertions.assertThat(esrs).containsAll(Arrays.asList("01-1067-4", "010010674"));
		}
	}
}
