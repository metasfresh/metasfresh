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

package de.metas.ui.web.window.model.sql;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SqlComposedKeyTest
{

	@Nested
	class getSqlWhereClauseById
	{
		@Test
		void nonNullKeys()
		{
			final SqlComposedKey composedKey = SqlComposedKey.builder()
					.keyColumnName("C_DocType_ID")
					.keyColumnName("AD_Language")
					.value("C_DocType_ID", 1000000)
					.value("AD_Language", "en_US")
					.build();

			assertThat(composedKey.getSqlWhereClauseById("tableAlias"))
					.isEqualTo("tableAlias.C_DocType_ID=1000000 AND tableAlias.AD_Language='en_US'");
		}

		@Test
		void partialNullValues()
		{
			final SqlComposedKey composedKey = SqlComposedKey.builder()
					.keyColumnName("C_Invoice_ID")
					.keyColumnName("C_InvoicePaySchedule_ID")
					.value("C_Invoice_ID", 1000000)
					.build();

			assertThat(composedKey.getSqlWhereClauseById("tableAlias"))
					.isEqualTo("tableAlias.C_Invoice_ID=1000000 AND tableAlias.C_InvoicePaySchedule_ID IS NULL");
		}
	}
}