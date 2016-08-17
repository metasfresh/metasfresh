package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.base.MoreObjects;

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

	public static List<JSONDocumentLayoutElement> ofList(final List<DocumentLayoutElementDescriptor> elements)
	{
		return elements.stream()
				.map(element -> of(element))
				.collect(GuavaCollectors.toImmutableList());
	}

	public static JSONDocumentLayoutElement of(final DocumentLayoutElementDescriptor element)
	{
		return new JSONDocumentLayoutElement(element);
	}

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String caption;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String description;

	private final JSONLayoutWidgetType widgetType;

	/** Type: primary, secondary */
	private final JSONLayoutType type;
	
	@JsonInclude(Include.NON_EMPTY)
	private final Set<JSONDocumentLayoutElementField> fields;

	private JSONDocumentLayoutElement(final DocumentLayoutElementDescriptor element)
	{
		super();
		caption = element.getCaption();
		description = element.getDescription();
		widgetType = JSONLayoutWidgetType.fromNullable(element.getWidgetType());
		type = JSONLayoutType.fromNullable(element.getLayoutType());
		fields = JSONDocumentLayoutElementField.ofSet(element.getFields());
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
	
	public Set<JSONDocumentLayoutElementField> getFields()
	{
		return fields;
	}
}
