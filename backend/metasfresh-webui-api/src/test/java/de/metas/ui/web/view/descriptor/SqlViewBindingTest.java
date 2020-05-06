package de.metas.ui.web.view.descriptor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverter;
import de.metas.ui.web.document.filter.sql.SqlDocumentFilterConverterDecorator;
import de.metas.ui.web.view.descriptor.SqlViewBinding.Builder;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class SqlViewBindingTest
{

	@Test
	public void createSqlViewBinding_Has_Null_Decorator_By_Default()
	{
		final SqlViewBinding sqlViewBinding = createMinimalBuilder().build();

		assertThat(sqlViewBinding).isNotNull();
		assertThat(sqlViewBinding.getFilterConverterDecoratorOrNull()).isNull();
	}

	@Test
	public void createSqlViewBinding_With_Custom_FilterConverterDecoratorProvider()
	{
		final CustomSqlDocumentFilterConverterDecoratorProvider customDecoratorProvider = new CustomSqlDocumentFilterConverterDecoratorProvider();

		final SqlViewBinding sqlViewBinding = createMinimalBuilder()
				.filterConverterDecorator(customDecoratorProvider)
				.build();

		assertThat(sqlViewBinding).isNotNull();
		assertThat(sqlViewBinding.getFilterConverterDecoratorOrNull()).isSameAs(customDecoratorProvider);
	}

	private Builder createMinimalBuilder()
	{
		final SqlViewRowFieldBinding field = SqlViewRowFieldBinding.builder()
				.fieldName("fieldName")
				.widgetType(DocumentFieldWidgetType.Amount)
				.sqlValueClass(String.class)
				.fieldLoader((rs, adLanguage) -> "dummyFieldValue")
				.keyColumn(true)
				.build();

		final Builder builder = SqlViewBinding.builder()
				.tableName("dummyTable")
				.field(field)
				.displayFieldNames("displayFieldName");

		return builder;
	}

	public static class CustomSqlDocumentFilterConverterDecoratorProvider implements SqlDocumentFilterConverterDecorator
	{
		@Override
		public WindowId getWindowId()
		{
			return WindowId.of(23);
		}

		@Override
		public SqlDocumentFilterConverter decorate(SqlDocumentFilterConverter converter)
		{
			throw new UnsupportedOperationException("The decorate method is not supposed to be called in this test");
		}

	}

}
