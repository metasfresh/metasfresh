package de.metas.ui.web.view.json;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutElement;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.filters.JSONDocumentFilterDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSideListDescriptor;
import de.metas.ui.web.window.descriptor.filters.DocumentFilterDescriptor;

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
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class JSONDocumentViewLayout implements Serializable
{
	public static JSONDocumentViewLayout ofGridLayout(
			final DocumentLayoutDetailDescriptor detail //
			, final String idFieldName // for debug
			, final Collection<DocumentFilterDescriptor> filters //
			, final JSONOptions jsonOpts //
	)
	{
		return new JSONDocumentViewLayout(detail, idFieldName, filters, jsonOpts);
	}

	public static final JSONDocumentViewLayout ofSideListLayout(
			final DocumentLayoutSideListDescriptor sideListLayout //
			, final Collection<DocumentFilterDescriptor> filters //
			, final JSONOptions jsonOpts //
	)
	{
		return new JSONDocumentViewLayout(sideListLayout, filters, jsonOpts);
	}
	
	/** i.e. AD_Window_ID */
	@JsonProperty("type")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String type;

	@JsonProperty("caption")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String caption;

	@JsonProperty("description")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String description;

	@JsonProperty("emptyResultText")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String emptyResultText;

	@JsonProperty("emptyResultHint")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final String emptyResultHint;

	@JsonProperty("elements")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentLayoutElement> elements;

	@JsonProperty("filters")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentFilterDescriptor> filters;

	private JSONDocumentViewLayout(
			final DocumentLayoutDetailDescriptor gridLayout //
			, final String idFieldName //
			, final Collection<DocumentFilterDescriptor> filters //
			, final JSONOptions jsonOpts //
	)
	{
		super();

		type = String.valueOf(gridLayout.getAD_Window_ID());

		final String adLanguage = jsonOpts.getAD_Language();
		caption = gridLayout.getCaption(adLanguage);
		description = gridLayout.getDescription(adLanguage);
		emptyResultText = gridLayout.getEmptyResultText(adLanguage);
		emptyResultHint = gridLayout.getEmptyResultHint(adLanguage);

		//
		// Elements
		List<JSONDocumentLayoutElement> elements = JSONDocumentLayoutElement.ofList(gridLayout.getElements(), jsonOpts);
		if(jsonOpts.isDebugShowColumnNamesForCaption() && idFieldName != null)
		{
			elements = ImmutableList.<JSONDocumentLayoutElement>builder()
					.add(JSONDocumentLayoutElement.debuggingField(idFieldName, DocumentFieldWidgetType.Text))
					.addAll(elements)
					.build();
		}
		this.elements = elements;

		this.filters = JSONDocumentFilterDescriptor.ofCollection(filters, jsonOpts);
	}

	private JSONDocumentViewLayout(
			final DocumentLayoutSideListDescriptor sideListLayout //
			, final Collection<DocumentFilterDescriptor> filters //
			, final JSONOptions jsonOpts //
	)
	{
		super();
		type = String.valueOf(sideListLayout.getAD_Window_ID());

		final String adLanguage = jsonOpts.getAD_Language();
		caption = null; // n/a
		description = null; // n/a
		emptyResultText = sideListLayout.getEmptyResultText(adLanguage);
		emptyResultHint = sideListLayout.getEmptyResultHint(adLanguage);

		elements = JSONDocumentLayoutElement.ofList(sideListLayout.getElements(), jsonOpts);

		this.filters = JSONDocumentFilterDescriptor.ofCollection(filters, jsonOpts);
	}

	@JsonCreator
	private JSONDocumentViewLayout(
			@JsonProperty("type") final String type //
			, @JsonProperty("caption") final String caption //
			, @JsonProperty("description") final String description //
			, @JsonProperty("emptyResultText") final String emptyResultText //
			, @JsonProperty("emptyResultHint") final String emptyResultHint //
			, @JsonProperty("elements") final List<JSONDocumentLayoutElement> elements //
			, @JsonProperty("filters") final List<JSONDocumentFilterDescriptor> filters //
	)
	{
		super();
		this.type = type;

		this.caption = caption;
		this.description = description;
		this.emptyResultText = emptyResultText;
		this.emptyResultHint = emptyResultHint;

		this.elements = elements == null ? ImmutableList.of() : ImmutableList.copyOf(elements);
		this.filters = filters == null ? ImmutableList.of() : ImmutableList.copyOf(filters);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("AD_Window_ID", type)
				.add("caption", caption)
				.add("elements", elements.isEmpty() ? null : elements)
				.add("filters", filters.isEmpty() ? null : filters)
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

	public String getEmptyResultText()
	{
		return emptyResultText;
	}

	public String getEmptyResultHint()
	{
		return emptyResultHint;
	}

	public List<JSONDocumentLayoutElement> getElements()
	{
		return elements;
	}

	public boolean hasElements()
	{
		return !elements.isEmpty();
	}

	public List<JSONDocumentFilterDescriptor> getFilters()
	{
		return filters;
	}
}
