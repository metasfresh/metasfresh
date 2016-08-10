package de.metas.ui.web.window.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentField;
import de.metas.ui.web.window.model.FieldChangedEvent;
import de.metas.ui.web.window.model.FieldChangedEventCollector;
import de.metas.ui.web.window_old.shared.datatype.LookupValue;
import de.metas.ui.web.window_old.shared.datatype.NullValue;

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

public final class JSONConverters
{
	private static final String DATE_PATTEN = "yyyy-MM-dd'T'HH:mm'Z'"; // Quoted "Z" to indicate UTC, no timezone offset // TODO fix the pattern
	
	/**
	 * @param documents
	 * @return array of {@link #documentToJsonObject(Document)}
	 */
	public static List<List<Map<String, Object>>> documentsToJsonObject(final Collection<Document> documents)
	{
		return documents.stream()
				.map(document -> documentToJsonObject(document))
				.collect(Collectors.toList());
	}

	/**
	 * 
	 * @param document
	 * @return [ { field:field1 }, {...} ]
	 */
	private static List<Map<String, Object>> documentToJsonObject(final Document document)
	{
		final Collection<DocumentField> fields = document.getFields();
		final List<Map<String, Object>> jsonFields = new ArrayList<>(fields.size() + 1);

		// ID field (special)
		{
			final int id = document.getDocumentId();
			jsonFields.add(documentFieldToJsonObject("ID", id));
		}

		// All other fields
		fields.stream()
				.map(field -> documentFieldToJsonObject(field))
				.forEach(jsonFields::add);

		return jsonFields;
	}

	private static Map<String, Object> documentFieldToJsonObject(final DocumentField field)
	{
		final String name = field.getName();
		final Object valueJSON = field.getValueAsJsonObject();

		final Map<String, Object> map = documentFieldToJsonObject(name, valueJSON);
		map.put("field", name);
		map.put("value", valueJSON);
		map.put("mandatory", field.isMandatory());
		map.put("readonly", field.isReadonly());
		map.put("displayed", field.isDisplayed());
		if (field.isLookupValuesStale())
		{
			map.put("lookupValuesStale", Boolean.TRUE);
		}

		return map;
	}

	private static Map<String, Object> documentFieldToJsonObject(final String fieldName, final Object valueJSON)
	{
		final Map<String, Object> map = new LinkedHashMap<>();

		map.put("field", fieldName);
		map.put("value", valueJSON);

		return map;
	}

	public static List<Map<String, Object>> toJsonObject(final FieldChangedEventCollector eventsCollector)
	{
		return eventsCollector.toEventsList()
				.stream()
				.map(event -> toJsonObject(event))
				.collect(Collectors.toList());
	}

	private static Map<String, Object> toJsonObject(final FieldChangedEvent event)
	{
		final Map<String, Object> map = new LinkedHashMap<>();

		final String name = event.getFieldName();
		map.put("field", name);

		if (event.isValueSet())
		{
			final Object valueJSON = event.getValueAsJsonObject();
			map.put("value", valueJSON);
		}

		final Boolean readonly = event.getReadonly();
		if (readonly != null)
		{
			map.put("readonly", readonly);
		}

		final Boolean mandatory = event.getMandatory();
		if (mandatory != null)
		{
			map.put("mandatory", mandatory);
		}

		final Boolean displayed = event.getDisplayed();
		if (displayed != null)
		{
			map.put("displayed", displayed);
		}

		final Boolean lookupValuesStale = event.getLookupValuesStale();
		if (lookupValuesStale != null)
		{
			map.put("lookupValuesStale", lookupValuesStale);
		}

		return map;
	}

	public static Object dateToJsonObject(final java.util.Date valueDate)
	{
		// TODO: optimize dateToJsonObject; maybe use joda time or java8 API
		final DateFormat dateFormat = new SimpleDateFormat(DATE_PATTEN);
		final String valueStr = dateFormat.format(valueDate);
		return valueStr;
	}

	public static java.util.Date dateFromString(final String valueStr)
	{
		final DateFormat dateFormat = new SimpleDateFormat(DATE_PATTEN);
		try
		{
			return dateFormat.parse(valueStr);
		}
		catch (final ParseException ex1)
		{
			// second try
			// FIXME: this is not optimum. We shall unify how we store Dates (as String)
			try
			{
				return Env.parseTimestamp(valueStr);
			}
			catch (final Exception ex2)
			{
				final IllegalArgumentException exFinal = new IllegalArgumentException("Failed converting '" + valueStr + "' to date", ex1);
				exFinal.addSuppressed(ex2);
				throw exFinal;
			}
		}
	}

	public static final List<Map<String, String>> lookupValuesToJsonObject(final List<LookupValue> lookupValues)
	{
		if (lookupValues == null || lookupValues.isEmpty())
		{
			return ImmutableList.of();
		}

		return lookupValues.stream()
				.map(lookupValue -> lookupValueToJsonObject(lookupValue))
				.collect(Collectors.toList());
	}

	public static final Map<String, String> lookupValueToJsonObject(final LookupValue lookupValue)
	{
		final Object keyObj = lookupValue.getId();
		final String key = keyObj == null ? null : keyObj.toString();

		final String name = lookupValue.getDisplayName();

		final Map<String, String> json = ImmutableMap.of(key, name);
		return json;
	}

	public static final Object lookupValueFromJsonMap(final Map<String, String> map)
	{
		final Set<Map.Entry<String, String>> entrySet = map.entrySet();
		if (entrySet.size() != 1)
		{
			throw new IllegalArgumentException("Invalid JSON lookup value: map=" + map);
		}

		final Map.Entry<String, String> e = entrySet.iterator().next();
		final String keyStr = e.getKey();
		// TODO: check if we need to convert the Key to integer. I think we shall replace the LookupValue with something else...
		final Object key = keyStr;

		final String name = e.getValue();

		return LookupValue.of(key, name);
	}

	public static final Object valueToJsonObject(final Object value)
	{
		if (NullValue.isNull(value))
		{
			return null;
		}
		else if (value instanceof java.util.Date)
		{
			final java.util.Date valueDate = (java.util.Date)value;
			return dateToJsonObject(valueDate);
		}
		else if (value instanceof LookupValue)
		{
			final LookupValue lookupValue = (LookupValue)value;
			return lookupValueToJsonObject(lookupValue);
		}
		else
		{
			return value;
		}
	}

	private JSONConverters()
	{
		super();
	}
}
