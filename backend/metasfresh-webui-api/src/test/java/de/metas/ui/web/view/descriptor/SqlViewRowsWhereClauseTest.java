package de.metas.ui.web.view.descriptor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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

public class SqlViewRowsWhereClauseTest
{
	@Nested
	public class isNoRecords
	{
		@Test
		public void noRecords()
		{
			final SqlViewRowsWhereClause whereClause = SqlViewRowsWhereClause.noRecords();
			assertThat(whereClause.isNoRecords()).isTrue();
		}

		@Test
		public void noSqls()
		{
			final SqlViewRowsWhereClause whereClause = SqlViewRowsWhereClause.builder().build();

			assertThat(whereClause.isNoRecords()).isTrue();
		}

		@Test
		public void someEmptySqls()
		{
			final SqlViewRowsWhereClause whereClause = SqlViewRowsWhereClause.builder()
					.rowsPresentInTable(SqlAndParams.of("    "))
					.build();

			assertThat(whereClause.isNoRecords()).isTrue();
		}

		@Test
		public void rowsPresentInViewSelection_set()
		{
			final SqlViewRowsWhereClause whereClause = SqlViewRowsWhereClause.builder()
					.rowsPresentInViewSelection(SqlAndParams.of("test"))
					.build();

			assertThat(whereClause.isNoRecords()).isFalse();
		}

		@Test
		public void rowsPresentInTable_set()
		{
			final SqlViewRowsWhereClause whereClause = SqlViewRowsWhereClause.builder()
					.rowsPresentInTable(SqlAndParams.of("test"))
					.build();

			assertThat(whereClause.isNoRecords()).isFalse();
		}

		@Test
		public void rowsMatchingFilter_set()
		{
			final SqlViewRowsWhereClause whereClause = SqlViewRowsWhereClause.builder()
					.rowsMatchingFilter(SqlAndParams.of("test"))
					.build();

			assertThat(whereClause.isNoRecords()).isFalse();
		}
	}
}
