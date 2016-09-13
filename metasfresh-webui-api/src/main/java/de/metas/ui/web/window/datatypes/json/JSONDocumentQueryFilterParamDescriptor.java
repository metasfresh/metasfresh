package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.List;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.descriptor.DocumentQueryFilterParamDescriptor;

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
public final class JSONDocumentQueryFilterParamDescriptor implements Serializable
{
	public static List<JSONDocumentQueryFilterParamDescriptor> ofList(final List<DocumentQueryFilterParamDescriptor> params, final String adLanguage)
	{
		return params.stream()
				.map(filter -> of(filter, adLanguage))
				.collect(GuavaCollectors.toImmutableList());
	}

	private static final JSONDocumentQueryFilterParamDescriptor of(final DocumentQueryFilterParamDescriptor param, final String adLanguage)
	{
		return new JSONDocumentQueryFilterParamDescriptor(param, adLanguage);
	}

	@JsonProperty("caption")
	private final String caption;

	@JsonProperty("field")
	private final String field;

	@JsonProperty("widgetType")
	private final JSONLayoutWidgetType widgetType;

	@JsonProperty("range")
	private final boolean rangeParameter;

	private JSONDocumentQueryFilterParamDescriptor(final DocumentQueryFilterParamDescriptor param, final String adLanguage)
	{
		super();
		caption = param.getDisplayName(adLanguage);
		field = param.getFieldName();
		widgetType = JSONLayoutWidgetType.fromNullable(param.getWidgetType());
		rangeParameter = param.isRangeParameter();
	}

	@JsonCreator
	private JSONDocumentQueryFilterParamDescriptor(
			@JsonProperty("caption") final String caption //
			, @JsonProperty("field") final String field //
			, @JsonProperty("widgetType") final JSONLayoutWidgetType widgetType //
			, @JsonProperty("range") final boolean range //
	)
	{
		this.caption = caption;
		this.field = field;
		this.widgetType = widgetType;
		rangeParameter = range;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("caption", caption)
				.add("field", field)
				.add("widgetType", widgetType)
				.add("rangeParameter", rangeParameter)
				.toString();
	}

	public String getCaption()
	{
		return caption;
	}

	public String getField()
	{
		return field;
	}

	public JSONLayoutWidgetType getWidgetType()
	{
		return widgetType;
	}

	public boolean isRangeParameter()
	{
		return rangeParameter;
	}
}
