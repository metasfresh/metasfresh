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

class SqlValueConvertersTest
{

	@Nested
	class UserQueryLegacy
	{
		@Test
		void poRecordIdValueIsFloat()
		{
			final String value = "4030153.000000000000";
			final String columnName = "C_Invoice_Candidate_ID";
			final DocumentFieldWidgetType documentFieldWidgetType = DocumentFieldWidgetType.Integer;
			final Class<?> targetClass = Integer.class;

			final Object result = SqlValueConverters.convertToPOValue(value, columnName, documentFieldWidgetType, targetClass);
			Assertions.assertThat(result).isEqualTo(4030153);
		}
	}
}
