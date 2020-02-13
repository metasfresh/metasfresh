package de.metas.ui.web.window.datatypes;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.model.lookup.LookupValueByIdSupplier;

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
	@Test
	public void test_From_BigDecimalString_To_Integer()
	{
		final String fieldName = "MyInteger";
		final String value = "12345.00000000000";
		final DocumentFieldWidgetType widgetType = DocumentFieldWidgetType.Integer;
		final Class<?> targetType = widgetType.getValueClass();
		final LookupValueByIdSupplier lookupDataSource = null;

		final Object valueConverted = DataTypes.convertToValueClass(fieldName, value, widgetType, targetType, lookupDataSource);
		assertThat(valueConverted).isEqualTo(12345);
	}

	/**
	 * @task https://github.com/metasfresh/metasfresh-webui-api/issues/1098
	 */
	@Test
	public void test_From_EmptyString_To_LookupValuesList()
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
	public void test_From_ListOfMaps_to_LookupValuesList()
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
	public void test_From_OneMap_to_LookupValuesList()
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
