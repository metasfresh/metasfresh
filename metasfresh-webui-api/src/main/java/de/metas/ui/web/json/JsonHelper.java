package de.metas.ui.web.json;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.shared.command.LookupDataSourceQueryCommand;
import de.metas.ui.web.window.shared.datatype.ComposedValue;
import de.metas.ui.web.window.shared.datatype.GridRowId;
import de.metas.ui.web.window.shared.datatype.LazyPropertyValuesListDTO;
import de.metas.ui.web.window.shared.datatype.LookupValue;
import de.metas.ui.web.window.shared.datatype.LookupValueList;
import de.metas.ui.web.window.shared.datatype.NullValue;
import de.metas.ui.web.window.shared.datatype.PropertyValuesListDTO;

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

public class JsonHelper
{
	private static ObjectMapper sharedJsonObjectMapper = null;

	public static final ObjectMapper createObjectMapper()
	{
		if (sharedJsonObjectMapper == null)
		{
			final ObjectMapper jsonObjectMapper = new ObjectMapper();

			// NOTE: don't configure it. we want to keep it as basic as possible. All particular tunnings shall go to annotations.

			// jsonObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
			// jsonObjectMapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty
			// jsonObjectMapper.enableDefaultTyping();
			//
			// jsonObjectMapper.registerModule(new GuavaModule());

			sharedJsonObjectMapper = jsonObjectMapper;
		}

		return sharedJsonObjectMapper;
	}

	private static final ImmutableList<Class<?>> allowedValueTypes = ImmutableList.of( //
			NullValue.class //
			, GridRowId.class //
			, String.class //
			, Integer.class, BigDecimal.class //
			, java.util.Date.class, Timestamp.class // FIXME: i think we shall avoid Timestamp, but since Timestamp extends Date, i think it's fine for now
			, Boolean.class //
			, LookupDataSourceQueryCommand.class //
			, PropertyValuesListDTO.class //
			, LazyPropertyValuesListDTO.class // NOTE: this one is used only in model
			, LookupValue.class //
			, LookupValueList.class //
			, ComposedValue.class //
	);

	private static final ImmutableList<Class<?>> allowedBaseValueTypes = ImmutableList.of( //
			LookupDataSourceQueryCommand.class // FIXME: see de.metas.ui.web.vaadin.window.model.LookupPropertyValue.LookupDataSourceServiceDTOImpl
	);

	public static final void validateValueType(final Object propertyNameObj, final Object value)
	{
		// NOTE: the aim is to have primitive or well known values types because, later, we want to serialize to JSON

		//
		if (value == null)
		{
			return; // OK
		}

		//
		final Class<? extends Object> valueClass = value.getClass();
		if (allowedValueTypes.contains(valueClass))
		{
			return; // OK
		}

		//
		for (final Class<?> baseType : allowedBaseValueTypes)
		{
			if (baseType.isAssignableFrom(valueClass))
			{
				return; // OK
			}
		}

		//
		throw new IllegalArgumentException("Invalid " + propertyNameObj + "=" + value + " (" + valueClass + ")"
				+ "\n Allowed types are: " + allowedValueTypes);
	}

	public static final String VALUETYPENAME_NULL = "null";

	public static final String extractValueTypeName(final Object value)
	{
		if (value == null)
		{
			return VALUETYPENAME_NULL;
		}
		return value.getClass().getName();
	}

	public static final Class<?> getValueTypeForName(final String valueTypeName)
	{
		if (valueTypeName == null || VALUETYPENAME_NULL.equals(valueTypeName))
		{
			return null;
		}

		// TODO: optimize
		try
		{
			return Thread.currentThread().getContextClassLoader().loadClass(valueTypeName);
		}
		catch (ClassNotFoundException e)
		{
			throw new IllegalArgumentException("Unknown type class for " + valueTypeName, e);
		}
	}
}
