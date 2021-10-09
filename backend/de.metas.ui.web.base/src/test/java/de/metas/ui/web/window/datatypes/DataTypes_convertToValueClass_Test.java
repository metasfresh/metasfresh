package de.metas.ui.web.window.datatypes;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONNullValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.lookup.LookupValueByIdSupplier;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

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

public class DataTypes_convertToValueClass_Test
{
	@Nested
	class From_NULL
	{
		@Test
		void from_null()
		{
			final String fieldName = "MyInteger";
			final DocumentFieldWidgetType widgetType = DocumentFieldWidgetType.Integer;
			final Class<?> targetType = widgetType.getValueClass();
			final LookupValueByIdSupplier lookupDataSource = null;
			final Object valueConverted = DataTypes.convertToValueClass(fieldName, null, widgetType, targetType, lookupDataSource);
			assertThat(valueConverted).isNull();
		}

		@Test
		public void from_JSONNullValue()
		{
			final String fieldName = "MyInteger";
			final DocumentFieldWidgetType widgetType = DocumentFieldWidgetType.Integer;
			final Class<?> targetType = widgetType.getValueClass();
			final LookupValueByIdSupplier lookupDataSource = null;
			final Object valueConverted = DataTypes.convertToValueClass(fieldName, JSONNullValue.instance, widgetType, targetType, lookupDataSource);
			assertThat(valueConverted).isNull();
		}
	}

	@Nested
	class To_Integer
	{
		@Test
		void from_BigDecimalString()
		{
			final String fieldName = "MyInteger";
			final String value = "12345.00000000000";
			final DocumentFieldWidgetType widgetType = DocumentFieldWidgetType.Integer;
			final Class<?> targetType = widgetType.getValueClass();
			final LookupValueByIdSupplier lookupDataSource = null;

			final Object valueConverted = DataTypes.convertToValueClass(fieldName, value, widgetType, targetType, lookupDataSource);
			assertThat(valueConverted).isEqualTo(12345);
		}

		@Test
		void from_literaly_null_string()
		{
			final Object valueConverted = DataTypes.convertToValueClass(
					"C_InvoicePaySchedule_ID",
					"null",
					DocumentFieldWidgetType.Integer,
					Integer.class,
					null);
			assertThat(valueConverted).isNull();
		}

	}

	@Nested
	class To_LookupValuesList
	{
		/**
		 * Task https://github.com/metasfresh/metasfresh-webui-api/issues/1098
		 */
		@Test
		void from_EmptyString()
		{
			final String fieldName = "MyLabels";
			final String value = "";
			final DocumentFieldWidgetType widgetType = DocumentFieldWidgetType.Labels;
			final Class<?> targetType = widgetType.getValueClass();
			final LookupValueByIdSupplier lookupDataSource = null;

			final Object valueConverted = DataTypes.convertToValueClass(fieldName, value, widgetType, targetType, lookupDataSource);
			assertThat(valueConverted).isNull();
		}

		@Test
		void from_ListOfMaps()
		{
			final String fieldName = "MyMultiValueParam";
			final DocumentFieldWidgetType widgetType = DocumentFieldWidgetType.MultiValuesList;
			final Class<?> targetType = widgetType.getValueClass();
			final LookupValueByIdSupplier lookupDataSource = null;

			final List<Map<String, Object>> value = ImmutableList.of(
					ImmutableMap.of("key", "k1", "caption", "value1"),
					ImmutableMap.of("key", "k2", "caption", "value2"));

			final Object valueConverted = DataTypes.convertToValueClass(fieldName, value, widgetType, targetType, lookupDataSource);

			assertThat(valueConverted).isEqualTo(
					LookupValuesList.fromCollection(ImmutableList.of(
							StringLookupValue.of("k1", "value1"),
							StringLookupValue.of("k2", "value2"))));
		}

		@Test
		void from_OneElementMap()
		{
			final String fieldName = "MyMultiValueParam";
			final DocumentFieldWidgetType widgetType = DocumentFieldWidgetType.MultiValuesList;
			final Class<?> targetType = widgetType.getValueClass();
			final LookupValueByIdSupplier lookupDataSource = null;

			final ImmutableMap<String, Object> value = ImmutableMap.of("key", "k1", "caption", "value1");

			final Object valueConverted = DataTypes.convertToValueClass(fieldName, value, widgetType, targetType, lookupDataSource);

			assertThat(valueConverted).isEqualTo(
					LookupValuesList.fromCollection(ImmutableList.of(
							StringLookupValue.of("k1", "value1"))));
		}
	}
}
