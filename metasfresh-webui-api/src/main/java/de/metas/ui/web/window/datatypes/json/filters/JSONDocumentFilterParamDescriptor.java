package de.metas.ui.web.window.datatypes.json.filters;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.datatypes.json.JSONLayoutWidgetType;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.descriptor.filters.DocumentFilterParamDescriptor;

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
/* package */final class JSONDocumentFilterParamDescriptor implements Serializable
{
	/* package */static List<JSONDocumentFilterParamDescriptor> ofCollection(final Collection<DocumentFilterParamDescriptor> params, final JSONOptions jsonOpts)
	{
		return params.stream()
				.map(filter -> of(filter, jsonOpts))
				.collect(GuavaCollectors.toImmutableList());
	}

	private static final JSONDocumentFilterParamDescriptor of(final DocumentFilterParamDescriptor param, final JSONOptions jsonOpts)
	{
		return new JSONDocumentFilterParamDescriptor(param, jsonOpts);
	}

	@JsonProperty("caption")
	private final String caption;

	@JsonProperty("parameterName")
	private final String parameterName;

	@JsonProperty("widgetType")
	private final JSONLayoutWidgetType widgetType;

	@JsonProperty("range")
	private final boolean rangeParameter;

	@JsonProperty("defaultValue")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Object defaultValue;
	@JsonProperty("defaultValueTo")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Object defaultValueTo;

	@JsonProperty("mandatory")
	private final boolean mandatory;

	private JSONDocumentFilterParamDescriptor(final DocumentFilterParamDescriptor param, final JSONOptions jsonOpts)
	{
		super();

		parameterName = param.getParameterName();

		if (jsonOpts.isDebugShowColumnNamesForCaption())
		{
			caption = parameterName;
		}
		else
		{
			final String adLanguage = jsonOpts.getAD_Language();
			caption = param.getDisplayName(adLanguage);
		}

		widgetType = JSONLayoutWidgetType.fromNullable(param.getWidgetType());
		rangeParameter = param.isRange();

		defaultValue = Values.valueToJsonObject(param.getDefaultValue());
		defaultValueTo = Values.valueToJsonObject(param.getDefaultValueTo());

		mandatory = param.isMandatory();
	}

	@JsonCreator
	private JSONDocumentFilterParamDescriptor(
			@JsonProperty("caption") final String caption //
			, @JsonProperty("parameterName") final String parameterName //
			, @JsonProperty("widgetType") final JSONLayoutWidgetType widgetType //
			, @JsonProperty("range") final boolean rangeParameter //
			, @JsonProperty("defaultValue") final Object defaultValue //
			, @JsonProperty("defaultValueTo") final Object defaultValueTo //
			, @JsonProperty("mandatory") final boolean mandatory //
	)
	{
		this.caption = caption;
		this.parameterName = parameterName;
		this.widgetType = widgetType;
		this.rangeParameter = rangeParameter;
		this.defaultValue = defaultValue;
		this.defaultValueTo = defaultValueTo;
		this.mandatory = mandatory;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("caption", caption)
				.add("parameterName", parameterName)
				.add("widgetType", widgetType)
				.add("rangeParameter", rangeParameter)
				.add("defaultValue", defaultValue)
				.add("defaultValueTo", defaultValueTo)
				.add("mandatory", mandatory)
				.toString();
	}

	public String getCaption()
	{
		return caption;
	}

	public String getParameterName()
	{
		return parameterName;
	}

	public JSONLayoutWidgetType getWidgetType()
	{
		return widgetType;
	}

	public boolean isRangeParameter()
	{
		return rangeParameter;
	}

	public Object getDefaultValue()
	{
		return defaultValue;
	}

	public Object getDefaultValueTo()
	{
		return defaultValueTo;
	}

	public boolean isMandatory()
	{
		return mandatory;
	}
}
