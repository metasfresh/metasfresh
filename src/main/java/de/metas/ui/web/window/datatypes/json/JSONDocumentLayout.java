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
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.document.filter.json.JSONDocumentFilterDescriptor;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@ApiModel("layout")
@SuppressWarnings("serial")
public final class JSONDocumentLayout implements Serializable
{
	public static final JSONDocumentLayout ofHeaderLayout(final DocumentLayoutDescriptor layout, final JSONOptions jsonOpts)
	{
		return new JSONDocumentLayout(layout, jsonOpts);
	}

	public static final JSONDocumentLayout ofDetailTab(final DocumentLayoutDetailDescriptor detailLayout, final JSONOptions jsonOpts)
	{
		return new JSONDocumentLayout(detailLayout, jsonOpts);
	}
	
	@JsonProperty("windowId")
	private final WindowId windowId;
	@JsonProperty("type")
	@Deprecated
	private final WindowId type;

	@JsonProperty("tabId")
	@JsonInclude(Include.NON_NULL)
	private final DetailId tabId;
	@Deprecated
	@JsonProperty("tabid")
	@JsonInclude(Include.NON_NULL)
	private final DetailId tabid;
	
	@JsonProperty("caption")
	@JsonInclude(Include.NON_EMPTY)
	private final String caption;

	// NOTE: we are no longer using documentNoElement (see https://github.com/metasfresh/metasfresh-webui-api/issues/291 ).
	// @JsonProperty("documentNoElement")
	// @JsonInclude(Include.NON_NULL)
	// private final JSONDocumentLayoutElement documentNoElement;

	@JsonProperty("documentSummaryElement")
	@JsonInclude(Include.NON_NULL)
	private final JSONDocumentLayoutElement documentSummaryElement;

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
	private final List<JSONDocumentFilterDescriptor> filters;

	@JsonProperty("emptyResultText")
	@JsonInclude(Include.NON_EMPTY)
	private final String emptyResultText;

	@JsonProperty("emptyResultHint")
	@JsonInclude(Include.NON_EMPTY)
	private final String emptyResultHint;

	/** Other properties */
	private final Map<String, Object> otherProperties = new LinkedHashMap<>();

	/**
	 * Header layout constructor
	 *
	 * @param layout
	 * @param jsonOpts
	 */
	private JSONDocumentLayout(final DocumentLayoutDescriptor layout, final JSONOptions jsonOpts)
	{
		this.windowId = layout.getWindowId();
		type = windowId;
		
		tabId = null;
		tabid = tabId;
		
		caption = layout.getCaption(jsonOpts.getAD_Language());
		
		documentSummaryElement = JSONDocumentLayoutElement.fromNullable(layout.getDocumentSummaryElement(), jsonOpts);
		docActionElement = JSONDocumentLayoutElement.fromNullable(layout.getDocActionElement(), jsonOpts);

		if (jsonOpts.isShowAdvancedFields())
		{
			final DocumentLayoutDetailDescriptor advancedViewLayout = layout.getAdvancedView();
			sections = JSONDocumentLayoutSection.ofAdvancedView(advancedViewLayout, jsonOpts);
		}
		else
		{
			sections = JSONDocumentLayoutSection.ofSectionsList(layout.getSections(), jsonOpts);
		}

		//
		// Included tabs
		if (jsonOpts.isShowAdvancedFields())
		{
			tabs = ImmutableList.of();
			putDebugProperty("tabs-info", "not showing tabs when showing advanced fields");
		}
		else
		{
			tabs = JSONDocumentLayoutTab.ofList(layout.getDetails(), jsonOpts);
		}

		filters = null;

		emptyResultText = null;
		emptyResultHint = null;

		if (WindowConstants.isProtocolDebugging())
		{
			putDebugProperties(layout.getDebugProperties());
			putDebugProperty(JSONOptions.DEBUG_ATTRNAME, jsonOpts.toString());
		}
	}

	/**
	 * From detail tab constructor.
	 *
	 * @param detailLayout
	 * @param jsonOpts
	 */
	private JSONDocumentLayout(final DocumentLayoutDetailDescriptor detailLayout, final JSONOptions jsonOpts)
	{
		final String adLanguage = jsonOpts.getAD_Language();

		windowId = detailLayout.getWindowId();
		type = windowId;

		tabId = detailLayout.getDetailId();
		tabid = tabId;

		caption = detailLayout.getCaption(jsonOpts.getAD_Language());

		documentSummaryElement = null;
		docActionElement = null;

		sections = JSONDocumentLayoutSection.ofDetailTab(detailLayout, jsonOpts);
		tabs = ImmutableList.of(); // no tabs for included tab

		filters = null;

		emptyResultText = detailLayout.getEmptyResultText(adLanguage);
		emptyResultHint = detailLayout.getEmptyResultHint(adLanguage);

		if (WindowConstants.isProtocolDebugging())
		{
			putDebugProperty(JSONOptions.DEBUG_ATTRNAME, jsonOpts.toString());
		}
	}

	@JsonCreator
	private JSONDocumentLayout(
			@JsonProperty("windowId") final WindowId windowId//
			, @JsonProperty("tabId") final DetailId tabId //
			, @JsonProperty("caption") final String caption //
			, @JsonProperty("documentSummaryElement") final JSONDocumentLayoutElement documentSummaryElement //
			, @JsonProperty("docActionElement") final JSONDocumentLayoutElement docActionElement//
			, @JsonProperty("sections") final List<JSONDocumentLayoutSection> sections //
			, @JsonProperty("tabs") final List<JSONDocumentLayoutTab> tabs //
			, @JsonProperty("filters") final List<JSONDocumentFilterDescriptor> filters //
			, @JsonProperty("emptyResultText") final String emptyResultText //
			, @JsonProperty("emptyResultHint") final String emptyResultHint //

			)
	{
		this.windowId = windowId;
		type = windowId;
		
		this.tabId = tabId;
		tabid = tabId;
		
		this.caption = caption;
		
		this.documentSummaryElement = documentSummaryElement;
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
				.add("windowId", windowId)
				.add("sections", sections.isEmpty() ? null : sections)
				.add("tabs", tabs.isEmpty() ? null : tabs)
				.add("filters", filters.isEmpty() ? null : filters)
				.toString();
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
