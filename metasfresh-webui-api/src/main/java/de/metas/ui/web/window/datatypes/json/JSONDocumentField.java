package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.adempiere.util.comparator.FixedOrderComparator;
import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.DocumentFieldChangedEvent;
import de.metas.ui.web.window.model.IDocumentFieldChangedEventCollector;
import de.metas.ui.web.window.model.IDocumentFieldView;
import io.swagger.annotations.ApiModel;

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

@ApiModel("document-field")
@SuppressWarnings("serial")
public final class JSONDocumentField implements Serializable
{
	public static final JSONDocumentField ofName(final String fieldName)
	{
		return new JSONDocumentField(fieldName);
	}

	public static final JSONDocumentField ofId(final Object jsonId, final String reason)
	{
		return new JSONDocumentField(FIELD_VALUE_ID)
				.setValue(jsonId, reason);
	}

	public static final JSONDocumentField ofDocumentField(final IDocumentFieldView field)
	{
		final String name = field.getFieldName();
		final Object valueJSON = field.getValueAsJsonObject();
		final String reason = null; // N/A

		final JSONDocumentField jsonField = JSONDocumentField.ofName(name)
				.setValue(valueJSON, reason)
				.setReadonly(field.isReadonly(), reason)
				.setMandatory(field.isMandatory(), reason)
				.setDisplayed(field.isDisplayed(), reason);
		if (field.isLookupValuesStale())
		{
			jsonField.setLookupValuesStale(true, reason);
		}

		return jsonField;
	}

	public static JSONDocumentField ofDocumentFieldChangedEvent(final DocumentFieldChangedEvent event)
	{
		final JSONDocumentField jsonField = JSONDocumentField.ofName(event.getFieldName());

		if (event.isValueSet())
		{
			jsonField.setValue(event.getValueAsJsonObject(), event.getValueReason());
		}

		final Boolean readonly = event.getReadonly();
		if (readonly != null)
		{
			jsonField.setReadonly(readonly, event.getValueReason());
		}

		final Boolean mandatory = event.getMandatory();
		if (mandatory != null)
		{
			jsonField.setMandatory(mandatory, event.getMandatoryReason());
		}

		final Boolean displayed = event.getDisplayed();
		if (displayed != null)
		{
			jsonField.setDisplayed(displayed, event.getDisplayedReason());
		}

		final Boolean lookupValuesStale = event.getLookupValuesStale();
		if (lookupValuesStale != null)
		{
			jsonField.setLookupValuesStale(lookupValuesStale, event.getLookupValuesStaleReason());
		}

		return jsonField;
	}

	public static List<JSONDocumentField> ofDocumentFieldChangedEventCollector(final IDocumentFieldChangedEventCollector eventsCollector)
	{
		final List<DocumentFieldChangedEvent> events = eventsCollector.toEventsList();
		if (events.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<JSONDocumentField> jsonFields = new ArrayList<>(events.size() + 1);
		DocumentFieldChangedEvent eventForIdField = null;
		for (final DocumentFieldChangedEvent event : events)
		{
			final JSONDocumentField jsonField = ofDocumentFieldChangedEvent(event);
			jsonFields.add(jsonField);

			if (event.isKey() && event.isValueSet())
			{
				if (eventForIdField == null)
				{
					eventForIdField = event;

					final Object value = event.getValueAsJsonObject();
					final String id = value == null ? null : DocumentId.fromObject(value).toJson();
					final JSONDocumentField jsonIdField = JSONDocumentField.ofId(id, event.getValueReason());
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

	private static final transient Logger logger = LogManager.getLogger(JSONDocumentField.class);

	private static final String FIELD_field = "field";
	private static final String FIELD_VALUE_ID = "ID";

	private static final String FIELD_value = "value";
	private static final String FIELD_valueReason = "value-reason";
	private static final String FIELD_readonly = "readonly";
	private static final String FIELD_readonlyReason = "readonly-reason";
	private static final String FIELD_mandantory = "mandatory";
	private static final String FIELD_mandatoryReason = "mandatory-reason";
	private static final String FIELD_displayed = "displayed";
	private static final String FIELD_displayedReason = "displayed-reason";
	private static final String FIELD_lookupValuesStale = "lookupValuesStale";
	private static final String FIELD_lookupValuesStaleReason = "lookupValuesStale-reason";

	private final TreeMap<String, Object> map = new TreeMap<>(new FixedOrderComparator<>("*" //
			, FIELD_field //
			, FIELD_value//
			, FIELD_valueReason//
			, FIELD_readonly //
			, FIELD_readonlyReason //
			, FIELD_mandantory //
			, FIELD_mandatoryReason //
			, FIELD_displayed //
			, FIELD_displayedReason  //
			, FIELD_lookupValuesStale  //
			, FIELD_lookupValuesStaleReason  //
			, "*" //
	));

	private JSONDocumentField(final String fieldName)
	{
		super();
		map.put(FIELD_field, fieldName);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.addValue(map)
				.toString();
	}

	public JSONDocumentField setValue(final Object jsonValue, final String reason)
	{
		map.put(FIELD_value, jsonValue);
		if (reason != null)
		{
			map.put(FIELD_valueReason, reason);
		}
		return this;
	}

	public JSONDocumentField setReadonly(final boolean readonly, final String reason)
	{
		map.put(FIELD_readonly, readonly);
		if (reason != null)
		{
			map.put(FIELD_readonlyReason, reason);
		}
		return this;
	}

	public JSONDocumentField setMandatory(final boolean mandatory, final String reason)
	{
		map.put(FIELD_mandantory, mandatory);
		if (reason != null)
		{
			map.put(FIELD_mandatoryReason, reason);
		}

		return this;
	}

	public JSONDocumentField setDisplayed(final boolean displayed, final String reason)
	{
		map.put(FIELD_displayed, displayed);
		if (reason != null)
		{
			map.put(FIELD_displayedReason, reason);
		}

		return this;
	}

	public JSONDocumentField setLookupValuesStale(final boolean lookupValuesStale, final String reason)
	{
		map.put(FIELD_lookupValuesStale, lookupValuesStale);
		if (reason != null)
		{
			map.put(FIELD_lookupValuesStaleReason, reason);
		}

		return this;
	}

	@JsonAnyGetter
	public Map<String, Object> getMap()
	{
		return map;
	}

	public JSONDocumentField putDebugProperty(final String name, final Object jsonValue)
	{
		map.put("debug-" + name, jsonValue);
		return this;
	}
}
