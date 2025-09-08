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

package de.metas.cache;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CCacheStatsOrderByTest
{
	@Nested
	class parse
	{
		@Test
		void blankString()
		{
			assertThat(CCacheStatsOrderBy.parse("   ")).isEmpty();
		}

		@Test
		void standardTest()
		{
			assertThat(CCacheStatsOrderBy.parse("  name,  -size , +hitRate ,   -missRate   "))
					.get()
					.isEqualTo(CCacheStatsOrderBy.builder()
							.ascending(CCacheStatsOrderBy.Field.name)
							.descending(CCacheStatsOrderBy.Field.size)
							.ascending(CCacheStatsOrderBy.Field.hitRate)
							.descending(CCacheStatsOrderBy.Field.missRate)
							.build());
		}

		@Test
		void unknownField()
		{
			assertThatThrownBy(() -> CCacheStatsOrderBy.parse("unknownField"))
					.hasMessageStartingWith("Invalid order by string");
		}

	}
}