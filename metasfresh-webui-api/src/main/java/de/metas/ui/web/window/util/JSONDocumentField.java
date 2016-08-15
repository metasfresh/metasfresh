package de.metas.ui.web.window.util;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import org.adempiere.util.comparator.FixedOrderComparator;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.google.common.base.MoreObjects;

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

	final TreeMap<String, Object> map = new TreeMap<>(new FixedOrderComparator<>("*" //
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
}
