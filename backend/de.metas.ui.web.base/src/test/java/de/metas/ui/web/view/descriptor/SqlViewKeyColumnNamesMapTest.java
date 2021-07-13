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

package de.metas.ui.web.view.descriptor;

import com.google.common.collect.ImmutableList;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.sql.PlainSqlEntityFieldBinding;
import de.metas.ui.web.window.descriptor.sql.PlainSqlEntityFieldBinding.PlainSqlEntityFieldBindingBuilder;
import de.metas.ui.web.window.model.sql.SqlComposedKey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class SqlViewKeyColumnNamesMapTest
{
	private static PlainSqlEntityFieldBindingBuilder intLookupField(final String columnName)
	{
		return PlainSqlEntityFieldBinding.builder()
				.columnName(columnName)
				.widgetType(DocumentFieldWidgetType.Lookup)
				.sqlValueClass(Integer.class);
	}

	private static PlainSqlEntityFieldBindingBuilder stringLookupField(final String columnName)
	{
		return PlainSqlEntityFieldBinding.builder()
				.columnName(columnName)
				.widgetType(DocumentFieldWidgetType.Lookup)
				.sqlValueClass(String.class);
	}

	@Nested
	class case_SimpleIntKey
	{
		private SqlViewKeyColumnNamesMap map;

		@BeforeEach
		void beforeEach()
		{
			map = SqlViewKeyColumnNamesMap.ofKeyFields(ImmutableList.of(
					intLookupField("C_Invoice_ID").mandatory(true).build()
			));
		}

		@Test
		void extractComposedKey()
		{
			final SqlComposedKey composedKey = map.extractComposedKey(DocumentId.of(1000000));
			assertThat(composedKey)
					.isEqualTo(SqlComposedKey.builder()
							.keyColumnName("C_Invoice_ID")
							.value("C_Invoice_ID", 1000000)
							.build());
		}

		@Test
		void getSqlJoinCondition()
		{
			assertThat(map.getSqlJoinCondition("sourceTable", "selTable"))
					.isEqualTo("sourceTable.C_Invoice_ID=selTable.IntKey1");
		}

		@Test
		void getSqlValuesCommaSeparated()
		{
			final DocumentId documentId = DocumentId.of(1000000);
			assertThat(map.getSqlValuesCommaSeparated(documentId))
					.isEqualTo(SqlAndParams.of("?", 1000000));

		}

		@Test
		void getSqlValuesList()
		{
			final DocumentId documentId = DocumentId.of(1000000);
			assertThat(map.getSqlValuesList(documentId))
					.containsExactly(1000000);
		}

		@Test
		void prepareSqlFilterByRowIds()
		{
			final SqlAndParams sql = map.prepareSqlFilterByRowIds()
					.sqlColumnPrefix("prefix.")
					.mappingType(SqlViewKeyColumnNamesMap.MappingType.SOURCE_TABLE)
					.rowIds(DocumentIdsSelection.of(ImmutableList.of(
							DocumentId.of(1000000)
					)))
					.build();

			assertThat(sql)
					.isEqualTo(SqlAndParams.of("prefix.C_Invoice_ID=?", 1000000));
		}
	}

	@Nested
	class case_ComposedKey_Int_and_String
	{
		private SqlViewKeyColumnNamesMap map;

		@BeforeEach
		void beforeEach()
		{
			map = SqlViewKeyColumnNamesMap.ofKeyFields(ImmutableList.of(
					intLookupField("C_DocType_ID").mandatory(true).build(),
					stringLookupField("AD_Language").mandatory(true).build()
			));
		}

		@Test
		void extractComposedKey()
		{
			final DocumentId documentId = DocumentId.ofComposedKeyParts(Arrays.asList(1000000, "en_US"));
			assertThat(documentId.toJson()).isEqualTo("1000000$en_US");

			final SqlComposedKey composedKey = map.extractComposedKey(documentId);
			assertThat(composedKey)
					.isEqualTo(SqlComposedKey.builder()
							.keyColumnName("C_DocType_ID")
							.keyColumnName("AD_Language")
							.value("C_DocType_ID", 1000000)
							.value("AD_Language", "en_US")
							.build());
		}

		@Test
		void getSqlJoinCondition()
		{
			assertThat(map.getSqlJoinCondition("sourceTable", "selTable"))
					.isEqualTo("sourceTable.C_DocType_ID=selTable.IntKey1 AND sourceTable.AD_Language=selTable.StringKey1");
		}

		@Test
		void getSqlValuesCommaSeparated()
		{
			final DocumentId documentId = DocumentId.ofComposedKeyParts(Arrays.asList(1000000, "en_US"));
			assertThat(map.getSqlValuesCommaSeparated(documentId))
					.isEqualTo(SqlAndParams.of("?, ?", 1000000, "en_US"));

		}

		@Test
		void getSqlValuesList()
		{
			final DocumentId documentId = DocumentId.ofComposedKeyParts(Arrays.asList(1000000, "en_US"));
			assertThat(map.getSqlValuesList(documentId))
					.containsExactly(1000000, "en_US");
		}

		@Test
		void prepareSqlFilterByRowIds()
		{
			final SqlAndParams sql = map.prepareSqlFilterByRowIds()
					.sqlColumnPrefix("prefix.")
					.mappingType(SqlViewKeyColumnNamesMap.MappingType.SOURCE_TABLE)
					.rowIds(DocumentIdsSelection.of(ImmutableList.of(
							DocumentId.ofComposedKeyParts(Arrays.asList(1000000, "en_US"))
					)))
					.build();

			assertThat(sql)
					.isEqualTo(SqlAndParams.of(
							"prefix.C_DocType_ID=? AND prefix.AD_Language=?",
							1000000, "en_US"));
		}
	}

	@Nested
	class case_ComposedKey_WithSomeNullableValues
	{
		private SqlViewKeyColumnNamesMap map;

		@BeforeEach
		void beforeEach()
		{
			map = SqlViewKeyColumnNamesMap.ofKeyFields(ImmutableList.of(
					intLookupField("C_Invoice_ID").mandatory(true).build(),
					intLookupField("C_InvoicePaySchedule_ID").mandatory(false).build()
			));
		}

		@Test
		void extractComposedKey()
		{
			final DocumentId documentId = DocumentId.ofComposedKeyParts(Arrays.asList(1000000, null));
			assertThat(documentId.toJson()).isEqualTo("1000000$null");

			final SqlComposedKey composedKey = map.extractComposedKey(documentId);
			assertThat(composedKey)
					.isEqualTo(SqlComposedKey.builder()
							.keyColumnName("C_Invoice_ID")
							.keyColumnName("C_InvoicePaySchedule_ID")
							.value("C_Invoice_ID", 1000000)
							.build());
		}

		@Test
		void getSqlJoinCondition()
		{
			assertThat(map.getSqlJoinCondition("sourceTable", "selTable"))
					.isEqualTo("sourceTable.C_Invoice_ID=selTable.IntKey1 AND sourceTable.C_InvoicePaySchedule_ID IS NOT DISTINCT FROM selTable.IntKey2");
		}

		@Test
		void getSqlValuesCommaSeparated()
		{
			final DocumentId documentId = DocumentId.ofComposedKeyParts(Arrays.asList(1000000, null));
			assertThat(map.getSqlValuesCommaSeparated(documentId))
					.isEqualTo(SqlAndParams.of("?, ?", 1000000, null));

		}

		@Test
		void getSqlValuesList()
		{
			final DocumentId documentId = DocumentId.ofComposedKeyParts(Arrays.asList(1000000, null));
			assertThat(map.getSqlValuesList(documentId))
					.containsExactly(1000000, null);
		}

		@Test
		void prepareSqlFilterByRowIds()
		{
			final SqlAndParams sql = map.prepareSqlFilterByRowIds()
					.sqlColumnPrefix("prefix.")
					.mappingType(SqlViewKeyColumnNamesMap.MappingType.SOURCE_TABLE)
					.rowIds(DocumentIdsSelection.of(ImmutableList.of(
							DocumentId.ofComposedKeyParts(Arrays.asList(1000000, null))
					)))
					.build();

			assertThat(sql)
					.isEqualTo(SqlAndParams.of(
							"prefix.C_Invoice_ID=? AND prefix.C_InvoicePaySchedule_ID IS NULL",
							1000000));
		}
	}
}