package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
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

@ApiModel("element")
@SuppressWarnings("serial")
public final class JSONDocumentLayoutElement implements Serializable
{
	public static List<JSONDocumentLayoutElement> ofList(final List<DocumentLayoutElementDescriptor> elements, final JSONOptions jsonOpts)
	{
		return elements.stream()
				.filter(jsonOpts.documentLayoutElementFilter())
				.map(element -> new JSONDocumentLayoutElement(element, jsonOpts))
				.collect(GuavaCollectors.toImmutableList());
	}

	static JSONDocumentLayoutElement fromNullable(final DocumentLayoutElementDescriptor element, final JSONOptions jsonOpts)
	{
		if (element == null)
		{
			return null;
		}
		return new JSONDocumentLayoutElement(element, jsonOpts);
	}

	@JsonProperty("caption")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String caption;

	@JsonProperty("description")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String description;

	@JsonProperty("widgetType")
	private final JSONLayoutWidgetType widgetType;

	@JsonProperty("precision")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Integer precision;

	/** Type: primary, secondary */
	@JsonProperty("type")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JSONLayoutType type;

	@JsonProperty("gridAlign")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JSONLayoutAlign gridAlign;

	@JsonProperty("fields")
	@JsonInclude(Include.NON_EMPTY)
	private final Set<JSONDocumentLayoutElementField> fields;

	private JSONDocumentLayoutElement(final DocumentLayoutElementDescriptor element, final JSONOptions jsonOpts)
	{
		super();
		final String adLanguage = jsonOpts.getAD_Language();

		if (jsonOpts.isDebugShowColumnNamesForCaption())
		{
			caption = element.getCaptionAsFieldNames();
		}
		else
		{
			caption = element.getCaption(adLanguage);
		}

		description = element.getDescription(adLanguage);

		widgetType = JSONLayoutWidgetType.fromNullable(element.getWidgetType());
		precision = element.getPrecision().orElse(null);

		type = JSONLayoutType.fromNullable(element.getLayoutType());
		gridAlign = JSONLayoutAlign.fromNullable(element.getGridAlign());

		fields = JSONDocumentLayoutElementField.ofSet(element.getFields(), jsonOpts);
	}

	@JsonCreator
	public JSONDocumentLayoutElement(
			@JsonProperty("caption") final String caption //
			, @JsonProperty("description") final String description //
			, @JsonProperty("widgetType") final JSONLayoutWidgetType widgetType //
			, @JsonProperty("precision") final Integer precision //
			, @JsonProperty("type") final JSONLayoutType type //
			, @JsonProperty("fields") final Set<JSONDocumentLayoutElementField> fields //
			, @JsonProperty("gridAlign") final JSONLayoutAlign gridAlign //
	)
	{
		super();
		this.caption = caption;
		this.description = description;

		this.widgetType = widgetType;
		this.precision = precision;
		
		this.type = type;
		this.gridAlign = gridAlign;

		this.fields = fields == null ? ImmutableSet.of() : ImmutableSet.copyOf(fields);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("caption", caption)
				.add("description", description)
				.add("widgetType", widgetType)
				.add("type", type)
				.add("gridAlign", gridAlign)
				.add("fields", fields.isEmpty() ? null : fields)
				.toString();
	}

	public String getCaption()
	{
		return caption;
	}

	public String getDescription()
	{
		return description;
	}

	public JSONLayoutWidgetType getWidgetType()
	{
		return widgetType;
	}

	public JSONLayoutType getType()
	{
		return type;
	}

	public JSONLayoutAlign getGridAlign()
	{
		return gridAlign;
	}

	public Set<JSONDocumentLayoutElementField> getFields()
	{
		return fields;
	}
}
