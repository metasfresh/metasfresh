package de.metas.ui.web.document.filter.sql;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.sql.SqlEntityBinding;
import de.metas.ui.web.window.model.sql.SqlOptions;

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

public class SqlDocumentFilterConvertersTest
{
	private final static SqlDocumentFilterConverter customConverter = new SqlDocumentFilterConverter()
	{
		public boolean canConvert(final String filterId)
		{
			return true;
		};

		/**
		 * This method won't be called throughout our test
		 */
		@Override
		public String getSql(final SqlParamsCollector sqlParamsOut, final DocumentFilter filter, final SqlOptions sqlOpts, final SqlDocumentFilterConverterContext context)
		{
			throw new UnsupportedOperationException();
		}
	};

	@Test
	public void createEntityBindingEffectiveConverter_uses_decorator_of_entityBinding()
	{
		final SqlEntityBinding sqlEntityBinding = Mockito.mock(SqlEntityBinding.class);
		Mockito.doReturn(Optional.of(new CustomDocumentFilterConverterDecorator()))
				.when(sqlEntityBinding)
				.getFilterConverterDecorator();
		Mockito.doReturn(SqlDocumentFilterConverters.emptyList())
				.when(sqlEntityBinding)
				.getFilterConverters();

		final SqlDocumentFilterConverter result = SqlDocumentFilterConverters.createEntityBindingEffectiveConverter(sqlEntityBinding);
		assertThat(result).isNotNull();
		assertThat(result)
				.as("Our sqlEntityBinding shall return a filterConverterDecoratorProvider that in turn provides exactly the customConverter from this test")
				.isSameAs(customConverter);
	}

	private static class CustomDocumentFilterConverterDecorator implements SqlDocumentFilterConverterDecorator
	{
		/**
		 * @return {@link SqlDocumentFilterConvertersTest#converter} so we have something very particular to check for in our test.
		 */
		@Override
		public SqlDocumentFilterConverter decorate(final SqlDocumentFilterConverter converter)
		{
			return customConverter;
		}

		@Override
		public WindowId getWindowId()
		{
			throw new UnsupportedOperationException("getWindowId is not supposed to be called within this test");
		}
	}
}
