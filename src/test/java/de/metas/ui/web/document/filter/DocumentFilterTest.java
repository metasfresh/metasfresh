package de.metas.ui.web.document.filter;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.ui.web.document.filter.DocumentFilterParam.Operator;

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

public class DocumentFilterTest
{
	@Test
	public void sqlFilter()
	{
		final DocumentFilter filter = DocumentFilter.builder()
				.setFilterId("filter1")
				.setCaption("caption1")
				.setFacetFilter(true)
				.addParameter(DocumentFilterParam.ofSqlWhereClause(true, "SQL WHERE CLAUSE"))
				.build();

		assertThat(filter.getParameters())
				.containsExactly(DocumentFilterParam.ofSqlWhereClause(true, "SQL WHERE CLAUSE"));
	}

	@Nested
	public static class equalsTests
	{
		@Test
		public void testEquals()
		{
			final DocumentFilter filter1 = DocumentFilter.builder()
					.setFilterId("filter1")
					.setCaption("caption1")
					.setFacetFilter(true)
					.addParameter(DocumentFilterParam.builder()
							.setFieldName("param1")
							.setOperator(Operator.BETWEEN)
							.setValue("value1")
							.setValueTo("value2")
							.build())
					.build();

			final DocumentFilter filter2 = DocumentFilter.builder()
					.setFilterId("filter1")
					.setCaption("caption1")
					.setFacetFilter(true)
					.addParameter(DocumentFilterParam.builder()
							.setFieldName("param1")
							.setOperator(Operator.BETWEEN)
							.setValue("value1")
							.setValueTo("value2")
							.build())
					.build();

			assertThat(filter1)
					.isEqualTo(filter2);
		}

		@Test
		public void testNotEquals()
		{
			final DocumentFilter filter1 = DocumentFilter.builder()
					.setFilterId("filter1")
					.setCaption("caption1")
					.setFacetFilter(true)
					.addParameter(DocumentFilterParam.builder()
							.setFieldName("param1")
							.setOperator(Operator.BETWEEN)
							.setValue("value1")
							.setValueTo("value2")
							.build())
					.build();

			final DocumentFilter filter2 = DocumentFilter.builder()
					.setFilterId("filter2")
					.setCaption("caption1")
					.setFacetFilter(true)
					.addParameter(DocumentFilterParam.builder()
							.setFieldName("param1")
							.setOperator(Operator.BETWEEN)
							.setValue("value1")
							.setValueTo("value2")
							.build())
					.build();

			assertThat(filter1)
					.isNotEqualTo(filter2);
		}
	}
}
