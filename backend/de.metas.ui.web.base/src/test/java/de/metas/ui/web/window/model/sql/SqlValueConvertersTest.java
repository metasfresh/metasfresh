/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.window.model.sql;

import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class SqlValueConvertersTest
{
	@Nested
	class convertToPOValue
	{
		@Nested
		class toInteger
		{
			@Test
			void emptyString()
			{
				Assertions.assertThat(
						SqlValueConverters.convertToPOValue("", "NotRelevant", DocumentFieldWidgetType.Lookup, Integer.class)
				).isNull();
			}

			/**
			 * needed for some User Query legacy BLs
			 */
			@Test
			void floatString()
			{
				Assertions.assertThat(
						SqlValueConverters.convertToPOValue("4030153.000000000000", "NotRelevant", DocumentFieldWidgetType.Integer, Integer.class)
				).isEqualTo(4030153);
			}
		}

		@Nested
		class toBigDecimal
		{
			@Test
			void emptyString()
			{
				Assertions.assertThat(
						SqlValueConverters.convertToPOValue("", "NotRelevant", DocumentFieldWidgetType.Amount, BigDecimal.class)
				).isNull();
			}
		}

	}
}


