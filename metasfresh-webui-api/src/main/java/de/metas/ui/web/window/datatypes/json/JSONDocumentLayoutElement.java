package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutElementField.JSONFieldType;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutElementField.JSONLookupSource;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
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
	
	static JSONDocumentLayoutElement debuggingField(final String fieldName, DocumentFieldWidgetType widgetType)
	{
		return new JSONDocumentLayoutElement(fieldName, widgetType);
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

	/** Other properties */
	private final Map<String, Object> otherProperties = new LinkedHashMap<>();

	private JSONDocumentLayoutElement(final DocumentLayoutElementDescriptor element, final JSONOptions jsonOpts)
	{
		super();
		final String adLanguage = jsonOpts.getAD_Language();

		final String caption = element.getCaption(adLanguage);
		if (jsonOpts.isDebugShowColumnNamesForCaption())
		{
			this.caption = element.getCaptionAsFieldNames();
			putDebugProperty("caption-original", caption);
		}
		else
		{
			this.caption = caption;
		}

		description = element.getDescription(adLanguage);

		widgetType = JSONLayoutWidgetType.fromNullable(element.getWidgetType());
		precision = element.getPrecision().orElse(null);

		type = JSONLayoutType.fromNullable(element.getLayoutType());
		gridAlign = JSONLayoutAlign.fromNullable(element.getGridAlign());

		fields = JSONDocumentLayoutElementField.ofSet(element.getFields(), jsonOpts);
	}
	
	/** Debugging field constructor */
	private JSONDocumentLayoutElement(final String fieldName, final DocumentFieldWidgetType widgetType)
	{
		super();
		this.caption = fieldName;
		this.description = null;
		
		this.widgetType = JSONLayoutWidgetType.fromNullable(widgetType);
		precision = null;
		
		type = null;
		gridAlign = JSONLayoutAlign.right;
		
		fields = ImmutableSet.of(new JSONDocumentLayoutElementField(fieldName, (JSONFieldType)null, (JSONLookupSource)null, "no "+fieldName));
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
	
	@JsonAnyGetter
	public Map<String, Object> getOtherProperties()
	{
		return otherProperties;
	}

	@JsonAnySetter
	public void putOtherProperty(final String name, final Object jsonValue)
	{
		otherProperties.put(name, jsonValue);
	}
	
	public JSONDocumentLayoutElement putDebugProperty(final String name, final Object jsonValue)
	{
		otherProperties.put("debug-" + name, jsonValue);
		return this;
	}

}
