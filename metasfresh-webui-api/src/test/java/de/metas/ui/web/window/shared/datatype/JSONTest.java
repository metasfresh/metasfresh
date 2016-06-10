package de.metas.ui.web.window.shared.datatype;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.json.JsonHelper;
import de.metas.ui.web.window.PropertyName;
import de.metas.ui.web.window.shared.ImageResource;
import de.metas.ui.web.window.shared.ImageResource.ResourceType;
import de.metas.ui.web.window.shared.JSONObjectValueHolder;
import de.metas.ui.web.window.shared.command.ViewCommandResult;
import de.metas.ui.web.window.shared.descriptor.PropertyDescriptorType;
import de.metas.ui.web.window.shared.descriptor.PropertyDescriptorValueType;
import de.metas.ui.web.window.shared.descriptor.PropertyLayoutInfo;
import de.metas.ui.web.window.shared.descriptor.ViewPropertyDescriptor;
import de.metas.ui.web.window.shared.event.AllPropertiesChangedModelEvent;
import de.metas.ui.web.window.shared.event.GridRowAddedModelEvent;
import de.metas.ui.web.window.shared.event.ModelEvent;
import de.metas.ui.web.window.shared.event.PropertyChangedModelEvent;
import junit.framework.Assert;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Tests JSON serialization/deserialization of various objects we use
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class JSONTest
{
	private ObjectMapper jsonObjectMapper;

	private final List<Object> valuesToTest = ImmutableList.<Object> of(
			NullValue.getInstance() //
			, "some string" //
			, (Integer)123 //
			, new BigDecimal("1234.56") //
			, new java.util.Date() //
			, new java.sql.Timestamp(System.currentTimeMillis()) //
			, Boolean.TRUE, Boolean.FALSE //
			, LookupValue.of(1234, "display value for 1234")//
			, LookupValue.of("StringKey", "display value for string key")//
			, LookupValueList.of(LookupValue.of(1234, "display value for 1234")) //
	);

	@Before
	public void init()
	{
		jsonObjectMapper = JsonHelper.createObjectMapper();
		jsonObjectMapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty
	}

	@Test
	public void test_LookupValue() throws Exception
	{
		testJSON(LookupValue.of(1234, "display value for 1234"), LookupValue.class);
	}

	@Test
	public void test_PropertyPath() throws Exception
	{
		testJSON(PropertyPath.of(PropertyName.of("TestPropertyName")), PropertyPath.class);
		testJSON(PropertyPath.of(PropertyName.of("gridProperty"), GridRowId.newRowId(), PropertyName.of("propertyName")), PropertyPath.class);
	}

	@Test
	public void test_PropertyPathValue() throws Exception
	{
		final PropertyPath propertyPath = PropertyPath.of(PropertyName.of("gridProperty"), GridRowId.newRowId(), PropertyName.of("propertyName"));

		//
		// test null:
		{
			final PropertyPathValue propertyPathValue = PropertyPathValue.of(propertyPath, null);
			testJSON(propertyPathValue, PropertyPathValue.class);
		}

		//
		// Test misc values
		for (final Object value : valuesToTest)
		{
			final PropertyPathValue propertyPathValue = PropertyPathValue.of(propertyPath, value);
			testJSON(propertyPathValue, PropertyPathValue.class);
		}
	}

	@Test
	public void test_PropertyPathValuesDTO() throws Exception
	{
		final PropertyPathValuesDTO.Builder valuesBuilder = PropertyPathValuesDTO.builder();

		int rowId = 1;
		for (final Object value : valuesToTest)
		{
			valuesBuilder.put(PropertyPath.of(PropertyName.of("gridProperty"), GridRowId.newRowId(), PropertyName.of("propertyName")), value);
			rowId++;
		}
		final PropertyPathValuesDTO values = valuesBuilder.build();

		testJSON(values, PropertyPathValuesDTO.class);
	}

	@Test
	public void test_PropertyValuesDTO() throws Exception
	{
		final PropertyValuesDTO values = createPropertyValuesDTO_WithAllTestValues();
		testJSON(values, PropertyValuesDTO.class);
	}

	private PropertyValuesDTO createPropertyValuesDTO_WithAllTestValues()
	{
		final PropertyValuesDTO.Builder valuesBuilder = PropertyValuesDTO.builder();

		int index = 1;
		for (final Object value : valuesToTest)
		{
			valuesBuilder.put(PropertyName.of("property-" + index), value);
			index++;
		}
		return valuesBuilder.build();
	}

	@Test
	public void test_ViewPropertyDescriptor() throws Exception
	{
		final ViewPropertyDescriptor descriptor = ViewPropertyDescriptor.builder()
				.setCaption("caption1")
				.setLayoutInfo(PropertyLayoutInfo.builder()
						.setDisplayed(true)
						.setNextColumn(true)
						.setRowsSpan(1)
						.build())
				.setPropertyName(PropertyName.of("propertyName1"))
				.setType(PropertyDescriptorType.Value)
				.setValueType(PropertyDescriptorValueType.Amount)
				//
				.addChildDescriptor(ViewPropertyDescriptor.builder()
						.setCaption("caption2")
						.setPropertyName(PropertyName.of("propertyName2"))
						.setType(PropertyDescriptorType.Value)
						.setValueType(PropertyDescriptorValueType.Amount)
						.build())
				//
				.build();

		testJSON(descriptor, ViewPropertyDescriptor.class);
	}

	@Test
	public void test_ImageResource() throws Exception
	{
		testJSON(new ImageResource("resource1", ResourceType.Image), ImageResource.class);
	}

	@Test
	public void test_ViewCommandResult() throws Exception
	{
		for (final Object value : valuesToTest)
		{
			testJSON(ViewCommandResult.of(value), ViewCommandResult.class);
		}
	}

	@Test
	public void test_JSONObjectValueHolder() throws Exception
	{
		testJSON(JSONObjectValueHolder.of(null), JSONObjectValueHolder.class);

		for (final Object value : valuesToTest)
		{
			testJSON(JSONObjectValueHolder.of(value), JSONObjectValueHolder.class);
		}
	}

	@Test
	public void test_PropertyChangedModelEvent() throws Exception
	{
		final PropertyPath propertyPath = PropertyPath.of(PropertyName.of("gridProperty"), GridRowId.newRowId(), PropertyName.of("propertyName"));

		for (final Object value : valuesToTest)
		{
			final PropertyChangedModelEvent event = PropertyChangedModelEvent.of(propertyPath, value, value);
			testJSON(event, ModelEvent.class);
		}
	}

	@Test
	public void test_AllPropertiesChangedModelEvent() throws Exception
	{
		testJSON(AllPropertiesChangedModelEvent.of(123), ModelEvent.class);
	}

	@Test
	public void test_GridRowAddedModelEvent() throws Exception
	{
		final PropertyName gridPropertyName = PropertyName.of("testGridProperty");
		final int rowId = 123;

		{
			final PropertyValuesDTO rowValues = PropertyValuesDTO.of();
			final GridRowAddedModelEvent event = GridRowAddedModelEvent.of(gridPropertyName, rowId, rowValues);
			testJSON(event, ModelEvent.class);
		}
		{
			final PropertyValuesDTO rowValues = createPropertyValuesDTO_WithAllTestValues();
			final GridRowAddedModelEvent event = GridRowAddedModelEvent.of(gridPropertyName, rowId, rowValues);
			testJSON(event, ModelEvent.class);
		}
	}

	private <T> void testJSON(final T value, final Class<T> type) throws Exception
	{
		final String jsonStr = jsonObjectMapper.writeValueAsString(value);
		System.out.println("\n\n-------------------------------------------------------------------------------------------");
		System.out.println("Value: " + value);
		System.out.println("Type: " + type);
		System.out.println("JSON: " + jsonStr);

		final T valueDeserialized = jsonObjectMapper.readValue(jsonStr, type);
		System.out.println("Value deserialized: " + valueDeserialized);
		System.out.println("-------------------------------------------------------------------------------------------");

		Assert.assertEquals(value, valueDeserialized);

	}

}
