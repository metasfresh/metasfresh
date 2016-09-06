package de.metas.ui.web.window.datatypes.json;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSideListDescriptor;
import de.metas.ui.web.window.descriptor.DocumentQueryFilterDescriptor;
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

@ApiModel("layout")
@SuppressWarnings("serial")
public final class JSONDocumentLayout implements Serializable
{
	public static final JSONDocumentLayout of(final DocumentLayoutDescriptor layout, final JSONFilteringOptions jsonFilteringOpts)
	{
		return new JSONDocumentLayout(layout, jsonFilteringOpts);
	}

	public static final JSONDocumentLayout ofDetailTab(final int adWindowId, final DocumentLayoutDetailDescriptor detailLayout, final JSONFilteringOptions jsonFilteringOpts)
	{
		return new JSONDocumentLayout(adWindowId, detailLayout, jsonFilteringOpts);
	}

	public static final JSONDocumentLayout ofSideListLayout(final int adWindowId, final DocumentLayoutSideListDescriptor sideListLayout, final List<DocumentQueryFilterDescriptor> filters,
			final JSONFilteringOptions jsonFilteringOpts)
	{
		return new JSONDocumentLayout(adWindowId, sideListLayout, filters, jsonFilteringOpts);
	}

	/** i.e. AD_Window_ID */
	@JsonProperty("type")
	private final String type;

	@JsonProperty("tabid")
	@JsonInclude(Include.NON_NULL)
	private final String tabid;

	@JsonProperty("documentNoElement")
	@JsonInclude(Include.NON_NULL)
	private final JSONDocumentLayoutElement documentNoElement;

	@JsonProperty("docActionElement")
	@JsonInclude(Include.NON_NULL)
	private final JSONDocumentLayoutElement docActionElement;

	@JsonProperty("sections")
	@JsonInclude(Include.NON_EMPTY)
	private final List<JSONDocumentLayoutSection> sections;

	@JsonProperty("tabs")
	@JsonInclude(Include.NON_EMPTY)
	private final List<JSONDocumentLayoutTab> tabs;

	@JsonProperty("filters")
	@JsonInclude(Include.NON_EMPTY)
	private final List<JSONDocumentQueryFilterDescriptor> filters;

	@JsonProperty("emptyResultText")
	@JsonInclude(Include.NON_EMPTY)
	private final String emptyResultText;

	@JsonProperty("emptyResultHint")
	@JsonInclude(Include.NON_EMPTY)
	private final String emptyResultHint;

	/** Other properties */
	private final Map<String, Object> otherProperties = new LinkedHashMap<>();

	private JSONDocumentLayout(final DocumentLayoutDescriptor layout, final JSONFilteringOptions jsonFilteringOpts)
	{
		super();
		type = String.valueOf(layout.getAD_Window_ID());
		tabid = null;
		documentNoElement = JSONDocumentLayoutElement.fromNullable(layout.getDocumentNoElement(), jsonFilteringOpts);
		docActionElement = JSONDocumentLayoutElement.fromNullable(layout.getDocActionElement(), jsonFilteringOpts);
		sections = JSONDocumentLayoutSection.ofList(layout.getSections(), jsonFilteringOpts);

		if (jsonFilteringOpts.isShowAdvancedFields())
		{
			tabs = ImmutableList.of();
			putDebugProperty("tabs-info", "not showing tabs when showing advanced fields");
		}
		else
		{
			tabs = JSONDocumentLayoutTab.ofList(layout.getDetails(), jsonFilteringOpts);
		}

		filters = JSONDocumentQueryFilterDescriptor.ofList(layout.getFilters(), jsonFilteringOpts.getAD_Language());

		emptyResultText = null;
		emptyResultHint = null;

		if (WindowConstants.isProtocolDebugging())
		{
			putDebugProperties(layout.getDebugProperties());
			putDebugProperty("filtering-opts", jsonFilteringOpts.toString());
		}
	}

	/**
	 * From detail tab constructor.
	 *
	 * @param detailLayout
	 * @param jsonFilteringOpts
	 */
	private JSONDocumentLayout(final int adWindowId, final DocumentLayoutDetailDescriptor detailLayout, final JSONFilteringOptions jsonFilteringOpts)
	{
		super();

		final String adLanguage = jsonFilteringOpts.getAD_Language();

		type = String.valueOf(adWindowId);
		tabid = detailLayout.getDetailId();
		documentNoElement = null;
		docActionElement = null;
		sections = JSONDocumentLayoutSection.ofDetailTab(detailLayout, jsonFilteringOpts);
		tabs = ImmutableList.of();
		filters = JSONDocumentQueryFilterDescriptor.ofList(detailLayout.getFilters(), adLanguage);

		emptyResultText = detailLayout.getEmptyResultText(adLanguage);
		emptyResultHint = detailLayout.getEmptyResultHint(adLanguage);

		if (WindowConstants.isProtocolDebugging())
		{
			putDebugProperty("filtering-opts", jsonFilteringOpts.toString());
		}
	}

	/**
	 * From side-list layout constructor.
	 *
	 * @param adWindowId
	 * @param sideListLayout
	 * @param jsonFilteringOpts
	 */
	private JSONDocumentLayout(final int adWindowId, final DocumentLayoutSideListDescriptor sideListLayout, final List<DocumentQueryFilterDescriptor> filters,
			final JSONFilteringOptions jsonFilteringOpts)
	{
		super();
		
		final String adLanguage = jsonFilteringOpts.getAD_Language();
		
		type = String.valueOf(adWindowId);
		tabid = null;
		documentNoElement = null;
		docActionElement = null;
		sections = JSONDocumentLayoutSection.ofSideListLayout(sideListLayout, jsonFilteringOpts);
		tabs = ImmutableList.of();
		this.filters = JSONDocumentQueryFilterDescriptor.ofList(filters, adLanguage);

		emptyResultText = sideListLayout.getEmptyResultText(adLanguage);
		emptyResultHint = sideListLayout.getEmptyResultHint(adLanguage);

		if (WindowConstants.isProtocolDebugging())
		{
			putDebugProperty("filtering-opts", jsonFilteringOpts.toString());
		}
	}

	@JsonCreator
	private JSONDocumentLayout(
			@JsonProperty("type") final String type//
			, @JsonProperty("tabid") final String tabId //
			, @JsonProperty("documentNoElement") final JSONDocumentLayoutElement documentNoElement//
			, @JsonProperty("docActionElement") final JSONDocumentLayoutElement docActionElement//
			, @JsonProperty("sections") final List<JSONDocumentLayoutSection> sections //
			, @JsonProperty("tabs") final List<JSONDocumentLayoutTab> tabs //
			, @JsonProperty("filters") final List<JSONDocumentQueryFilterDescriptor> filters //
			, @JsonProperty("emptyResultText") final String emptyResultText //
			, @JsonProperty("emptyResultHint") final String emptyResultHint //

	)
	{
		super();
		this.type = type;
		tabid = Strings.emptyToNull(tabId);
		this.documentNoElement = documentNoElement;
		this.docActionElement = docActionElement;
		this.sections = sections == null ? ImmutableList.of() : ImmutableList.copyOf(sections);
		this.tabs = tabs == null ? ImmutableList.of() : ImmutableList.copyOf(tabs);
		this.filters = filters == null ? ImmutableList.of() : ImmutableList.copyOf(filters);

		this.emptyResultText = emptyResultText;
		this.emptyResultHint = emptyResultHint;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("type", type)
				.add("sections", sections.isEmpty() ? null : sections)
				.add("tabs", tabs.isEmpty() ? null : tabs)
				.add("filters", filters.isEmpty() ? null : filters)
				.toString();
	}

	public String getType()
	{
		return type;
	}

	public String getTabid()
	{
		return tabid;
	}

	public JSONDocumentLayoutElement getDocumentNoElement()
	{
		return documentNoElement;
	}

	public JSONDocumentLayoutElement getDocActionElement()
	{
		return docActionElement;
	}

	public List<JSONDocumentLayoutSection> getSections()
	{
		return sections;
	}

	public List<JSONDocumentLayoutTab> getTabs()
	{
		return tabs;
	}

	public List<JSONDocumentQueryFilterDescriptor> getFilters()
	{
		return filters;
	}

	public String getEmptyResultText()
	{
		return emptyResultText;
	}

	public String getEmptyResultHint()
	{
		return emptyResultHint;
	}

	@JsonAnyGetter
	public Map<String, Object> getOtherProperties()
	{
		return otherProperties;
	}

	@JsonAnySetter
	/* package */void putOtherProperty(final String name, final Object jsonValue)
	{
		otherProperties.put(name, jsonValue);
	}

	private JSONDocumentLayout putDebugProperty(final String name, final Object jsonValue)
	{
		otherProperties.put("debug-" + name, jsonValue);
		return this;
	}

	private void putDebugProperties(final Map<String, ?> debugProperties)
	{
		if (debugProperties == null || debugProperties.isEmpty())
		{
			return;
		}

		for (final Map.Entry<String, ?> e : debugProperties.entrySet())
		{
			putDebugProperty(e.getKey(), e.getValue());
		}
	}

}
