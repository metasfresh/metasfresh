package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.List;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import de.metas.ui.web.window.descriptor.DocumentQueryFilterDescriptor;

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
public class JSONDocumentQueryFilterDescriptor implements Serializable
{
	public static List<JSONDocumentQueryFilterDescriptor> ofList(final List<DocumentQueryFilterDescriptor> filters, final String adLanguage)
	{
		return filters.stream()
				.map(filter -> of(filter, adLanguage))
				.collect(GuavaCollectors.toImmutableList());
	}

	private static final JSONDocumentQueryFilterDescriptor of(final DocumentQueryFilterDescriptor filter, final String adLanguage)
	{
		return new JSONDocumentQueryFilterDescriptor(filter, adLanguage);
	}



	@JsonProperty("caption")
	private final String caption;

	@JsonProperty("field")
	private final String field;

	@JsonProperty("widgetType")
	private final JSONLayoutWidgetType widgetType;

	@JsonProperty("frequent")
	private final boolean frequentUsed;

	@JsonProperty("parameter-required")
	private boolean requiresParameters;

	@JsonProperty("range")
	private final boolean rangeParameter;

	private JSONDocumentQueryFilterDescriptor(final DocumentQueryFilterDescriptor filter, final String adLanguage)
	{
		super();
		caption = filter.getDisplayName(adLanguage);
		field = filter.getFieldName();
		widgetType = JSONLayoutWidgetType.fromNullable(filter.getWidgetType());
		frequentUsed = filter.isFrequentUsed();

		requiresParameters = filter.isRequiresParameters();
		rangeParameter = filter.isRangeParameter();
	}

	@JsonCreator
	private JSONDocumentQueryFilterDescriptor(
			@JsonProperty("caption") final String caption //
			, @JsonProperty("field") final String field //
			, @JsonProperty("widgetType") final JSONLayoutWidgetType widgetType //
			, @JsonProperty("frequent") final boolean frequentUsed //
			, @JsonProperty("parameter-required") boolean requiresParameters //
			, @JsonProperty("range") final boolean range //
	)
	{
		this.caption = caption;
		this.field = field;
		this.widgetType = widgetType;
		this.frequentUsed = frequentUsed;
		this.requiresParameters = requiresParameters;
		this.rangeParameter = range;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("caption", caption)
				.add("field", field)
				.add("widgetType", widgetType)
				.add("frequentUsed", frequentUsed)
				.add("requiresParameters", requiresParameters)
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

	public boolean isRequiresParameters()
	{
		return requiresParameters;
	}

	public boolean isRangeParameter()
	{
		return rangeParameter;
	}
}
