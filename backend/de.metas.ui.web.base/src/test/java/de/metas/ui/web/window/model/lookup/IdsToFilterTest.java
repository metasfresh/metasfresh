/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ui.web.window.model.lookup;

import com.google.common.collect.ImmutableList;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class IdsToFilterTest
{
	@Nested
	class streamSingleValues
	{
		@Test
		void noValues()
		{
			Assertions.assertThat(IdsToFilter.NO_VALUE.streamSingleValues()).isEmpty();
		}

		@Test
		void singleValue()
		{
			final IdsToFilter idsToFilter = IdsToFilter.ofSingleValue(12345);
			Assertions.assertThat(idsToFilter.streamSingleValues()).containsExactly(idsToFilter);
		}

		@Test
		void multipleValues()
		{
			final IdsToFilter idsToFilter = IdsToFilter.ofMultipleValues(ImmutableList.of(1, 2, 3));
			Assertions.assertThat(idsToFilter.streamSingleValues()).containsExactly(
					IdsToFilter.ofSingleValue(1),
					IdsToFilter.ofSingleValue(2),
					IdsToFilter.ofSingleValue(3)
			);
		}
	}
}