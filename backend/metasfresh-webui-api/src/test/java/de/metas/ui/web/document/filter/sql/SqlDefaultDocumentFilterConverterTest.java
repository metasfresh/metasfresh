package de.metas.ui.web.document.filter.sql;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.metas.ui.web.window.descriptor.sql.SqlEntityBinding;
import de.metas.ui.web.window.descriptor.sql.SqlSelectValue;
import de.metas.ui.web.window.model.sql.SqlOptions;

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

public class SqlDefaultDocumentFilterConverterTest
{
	@Nested
	public class replaceTableNameWithTableAliasIfNeeded
	{
		@Test
		public void usingTableAlias()
		{
			final SqlEntityBinding entityBinding = Mockito.mock(SqlEntityBinding.class);
			Mockito.doReturn("MasterTableName").when(entityBinding).getTableName();

			final SqlDefaultDocumentFilterConverter converter = SqlDefaultDocumentFilterConverter.newInstance(entityBinding);
			final SqlSelectValue columnSql = SqlSelectValue.builder()
					.columnNameAlias("columnAlias")
					.virtualColumnSql("SELECT compute(SomeColumn) FROM ChildTableName WHERE bla=MasterTableName.bla")
					.build();

			final SqlOptions sqlOpts = SqlOptions.usingTableAlias("master");

			assertThat(converter.replaceTableNameWithTableAliasIfNeeded(columnSql, sqlOpts).toSqlString())
					.isEqualTo("SELECT compute(SomeColumn) FROM ChildTableName WHERE bla=master.bla");
		}

		@Test
		public void usingFullTableName()
		{
			final SqlEntityBinding entityBinding = Mockito.mock(SqlEntityBinding.class);
			Mockito.doReturn("MasterTableName").when(entityBinding).getTableName();

			final SqlDefaultDocumentFilterConverter converter = SqlDefaultDocumentFilterConverter.newInstance(entityBinding);
			final SqlSelectValue columnSql = SqlSelectValue.builder()
					.columnNameAlias("columnAlias")
					.virtualColumnSql("SELECT compute(SomeColumn) FROM ChildTableName WHERE bla=MasterTableName.bla")
					.build();

			final SqlOptions sqlOpts = SqlOptions.usingTableName("should_be_MasterTableName_but_DoesNotMatter");

			assertThat(converter.replaceTableNameWithTableAliasIfNeeded(columnSql, sqlOpts).toSqlString())
					.isEqualTo("SELECT compute(SomeColumn) FROM ChildTableName WHERE bla=MasterTableName.bla");
		}
	}
}
