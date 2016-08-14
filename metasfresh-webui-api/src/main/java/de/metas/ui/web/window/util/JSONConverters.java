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

import org.adempiere.util.GuavaCollectors;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentField;
import de.metas.ui.web.window.model.DocumentFieldChangedEvent;
import de.metas.ui.web.window.model.IDocumentFieldChangedEventCollector;

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
	private static final Logger logger = LogManager.getLogger(JSONConverters.class);

	private static final String DATE_PATTEN = "yyyy-MM-dd'T'HH:mm'Z'"; // Quoted "Z" to indicate UTC, no timezone offset // TODO fix the pattern

	private static final String FIELDNAME_ID = "ID";

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
			jsonFields.add(documentFieldToJsonObject(FIELDNAME_ID, id));
		}

		// All other fields
		fields.stream()
				.map(field -> documentFieldToJsonObject(field))
				.forEach(jsonFields::add);

		return jsonFields;
	}

	private static Map<String, Object> documentFieldToJsonObject(final DocumentField field)
	{
		final String name = field.getFieldName();
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

	public static List<Map<String, Object>> toJsonObject(final IDocumentFieldChangedEventCollector eventsCollector)
	{
		final List<DocumentFieldChangedEvent> events = eventsCollector.toEventsList();
		if (events.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<Map<String, Object>> jsonFields = new ArrayList<>(events.size() + 1);
		DocumentFieldChangedEvent eventForIdField = null;
		for (final DocumentFieldChangedEvent event : events)
		{
			final Map<String, Object> jsonField = toJsonObject(event);
			jsonFields.add(jsonField);

			if (event.isKey() && event.isValueSet())
			{
				if (eventForIdField == null)
				{
					eventForIdField = event;

					final Map<String, Object> jsonIdField = documentFieldToJsonObject(FIELDNAME_ID, event.getValueAsJsonObject());
					jsonFields.add(0, jsonIdField);
				}
				else
				{
					logger.warn("More then one ID changed event found: {}, {}", eventForIdField, event);
				}
			}
		}

		return jsonFields;
	}

	private static Map<String, Object> toJsonObject(final DocumentFieldChangedEvent event)
	{
		final Map<String, Object> map = new LinkedHashMap<>();

		final String name = event.getFieldName();
		map.put("field", name);

		if (event.isValueSet())
		{
			final Object valueJSON = event.getValueAsJsonObject();
			map.put("value", valueJSON);
			final String reason = event.getValueReason();
			if (reason != null)
			{
				map.put("valueReason", reason);
			}
		}

		final Boolean readonly = event.getReadonly();
		if (readonly != null)
		{
			map.put("readonly", readonly);
			final String reason = event.getReadonlyReason();
			if (reason != null)
			{
				map.put("readonlyReason", reason);
			}
		}

		final Boolean mandatory = event.getMandatory();
		if (mandatory != null)
		{
			map.put("mandatory", mandatory);
			final String reason = event.getMandatoryReason();
			if (reason != null)
			{
				map.put("mandatoryReason", reason);
			}
		}

		final Boolean displayed = event.getDisplayed();
		if (displayed != null)
		{
			map.put("displayed", displayed);
			final String reason = event.getDisplayedReason();
			if (reason != null)
			{
				map.put("displayedReason", reason);
			}
		}

		final Boolean lookupValuesStale = event.getLookupValuesStale();
		if (lookupValuesStale != null)
		{
			map.put("lookupValuesStale", lookupValuesStale);
			final String reason = event.getLookupValuesStaleReason();
			if (reason != null)
			{
				map.put("lookupValuesStaleReason", reason);
			}
		}

		return map;
	}

	private static final DateFormat getDateFormat()
	{
		// TODO: optimize dateToJsonObject; maybe use joda time or java8 API
		// TODO: user current session Locale
		return new SimpleDateFormat(DATE_PATTEN);
	}

	public static Object dateToJsonObject(final java.util.Date valueDate)
	{
		final DateFormat dateFormat = getDateFormat();
		final String valueStr = dateFormat.format(valueDate);
		return valueStr;
	}

	public static java.util.Date dateFromString(final String valueStr)
	{
		try
		{
			final DateFormat dateFormat = getDateFormat();
			return dateFormat.parse(valueStr);
		}
		catch (final ParseException ex1)
		{
			// second try
			// FIXME: this is not optimum. We shall unify how we store Dates (as String)
			logger.warn("Using Env.parseTimestamp to convert '{}' to Date", valueStr);
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
				.map(JSONConverters::lookupValueToJsonObject)
				.collect(GuavaCollectors.toImmutableList());
	}

	public static final Map<String, String> lookupValueToJsonObject(final LookupValue lookupValue)
	{
		final String key = lookupValue.getIdAsString();
		final String name = lookupValue.getDisplayName();
		return ImmutableMap.of(key, name);
	}

	public static final Object integerLookupValueFromJsonMap(final Map<String, String> map)
	{
		final Set<Map.Entry<String, String>> entrySet = map.entrySet();
		if (entrySet.size() != 1)
		{
			throw new IllegalArgumentException("Invalid JSON lookup value: map=" + map);
		}
		final Map.Entry<String, String> e = entrySet.iterator().next();

		String idStr = e.getKey();
		if (idStr == null)
		{
			return null;
		}
		idStr = idStr.trim();
		if (idStr.isEmpty())
		{
			return null;
		}

		final int id = Integer.parseInt(idStr);
		final String name = e.getValue();

		return IntegerLookupValue.of(id, name);
	}

	public static final Object stringLookupValueFromJsonMap(final Map<String, String> map)
	{
		final Set<Map.Entry<String, String>> entrySet = map.entrySet();
		if (entrySet.size() != 1)
		{
			throw new IllegalArgumentException("Invalid JSON lookup value: map=" + map);
		}
		final Map.Entry<String, String> e = entrySet.iterator().next();

		final String id = e.getKey();
		final String name = e.getValue();

		return StringLookupValue.of(id, name);
	}

	public static final Object valueToJsonObject(final Object value)
	{
		if (value == null)
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
