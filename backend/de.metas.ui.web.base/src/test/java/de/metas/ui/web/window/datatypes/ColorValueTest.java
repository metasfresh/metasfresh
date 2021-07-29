/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.window.datatypes;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ColorValueTest
{
	@Nested
	class ofHexString
	{
		@Test
		void RED()
		{
			Assertions.assertThat(ColorValue.ofHexString(ColorValue.RED.getHexString())).isSameAs(ColorValue.RED);
		}

		@Test
		void GREEN()
		{
			Assertions.assertThat(ColorValue.ofHexString(ColorValue.GREEN.getHexString())).isSameAs(ColorValue.GREEN);
		}

		@Test
		void FFAABB()
		{
			Assertions.assertThat(ColorValue.ofHexString("#FFAABB")).isSameAs(ColorValue.ofHexString("#FFAABB"));
		}
	}
}