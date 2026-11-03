package de.metas.bpartner;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DebtorIdTest
{
	@Nested
	class OfNullableNo
	{
		@Test
		void returns_null_for_zero()
		{
			assertThat(DebtorId.ofNullableNo(0)).isNull();
		}

		@Test
		void returns_null_for_negative()
		{
			assertThat(DebtorId.ofNullableNo(-5)).isNull();
		}

		@Test
		void returns_null_for_null_input()
		{
			assertThat(DebtorId.ofNullableNo(null)).isNull();
		}

		@Test
		void returns_value_for_positive()
		{
			final DebtorId id = DebtorId.ofNullableNo(10000);
			assertThat(id).isNotNull();
			assertThat(id.toInt()).isEqualTo(10000);
		}
	}

	@Test
	void ofNo_returns_correct_value()
	{
		assertThat(DebtorId.ofNo(10000).toInt()).isEqualTo(10000);
	}

	@Test
	void ofNo_throws_for_zero()
	{
		assertThatThrownBy(() -> DebtorId.ofNo(0))
				.isInstanceOf(Exception.class);
	}

	@Test
	void toIntOrNull_returns_null_for_null()
	{
		assertThat(DebtorId.toIntOrNull(null)).isNull();
	}

	@Test
	void toIntOrNull_returns_value_for_non_null()
	{
		assertThat(DebtorId.toIntOrNull(DebtorId.ofNo(10000))).isEqualTo(10000);
	}
}
