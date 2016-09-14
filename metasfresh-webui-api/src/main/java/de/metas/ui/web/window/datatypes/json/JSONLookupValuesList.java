package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.List;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.datatypes.LookupValue;
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

@ApiModel(value = "lookup-values-list", description = "[ { field : value} ]")
@SuppressWarnings("serial")
public class JSONLookupValuesList implements Serializable
{
	public static final JSONLookupValuesList ofLookupValuesList(final List<LookupValue> lookupValues)
	{
		if (lookupValues == null || lookupValues.isEmpty())
		{
			return EMPTY;
		}

		final ImmutableList<JSONLookupValue> values = lookupValues.stream()
				.map(JSONLookupValue::ofLookupValue)
				.collect(GuavaCollectors.toImmutableList());

		return new JSONLookupValuesList(values);
	}

	@JsonCreator
	private static final JSONLookupValuesList ofJSONLookupValuesList(@JsonProperty("values") final List<JSONLookupValue> jsonLookupValues)
	{
		if (jsonLookupValues == null || jsonLookupValues.isEmpty())
		{
			return EMPTY;
		}

		return new JSONLookupValuesList(ImmutableList.copyOf(jsonLookupValues));
	}

	private static final JSONLookupValuesList EMPTY = new JSONLookupValuesList(ImmutableList.of());

	@JsonProperty("values")
	private final List<JSONLookupValue> values;

	private JSONLookupValuesList(final ImmutableList<JSONLookupValue> values)
	{
		super();
		this.values = values;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("values", values)
				.toString();
	}

	public List<JSONLookupValue> getValues()
	{
		return values;
	}
}
