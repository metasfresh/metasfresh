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
	class OfRepoIdOrNull
	{
		@Test
		void returns_null_for_zero()
		{
			assertThat(DebtorId.ofRepoIdOrNull(0)).isNull();
		}

		@Test
		void returns_null_for_negative()
		{
			assertThat(DebtorId.ofRepoIdOrNull(-5)).isNull();
		}

		@Test
		void returns_value_for_positive()
		{
			final DebtorId id = DebtorId.ofRepoIdOrNull(10000);
			assertThat(id).isNotNull();
			assertThat(id.getRepoId()).isEqualTo(10000);
		}
	}

	@Test
	void ofRepoId_throws_for_zero()
	{
		assertThatThrownBy(() -> DebtorId.ofRepoId(0))
				.isInstanceOf(Exception.class);
	}
}
