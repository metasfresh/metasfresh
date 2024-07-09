package de.metas.ui.web.window.datatypes.json;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.ui.web.document.filter.json.JSONDocumentFilterDescriptor;
import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutSingleRow;
import de.metas.ui.web.window.descriptor.NotFoundMessages;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

@Schema(description = "layout")
public final class JSONDocumentLayout
{
	public static JSONDocumentLayout ofHeaderLayout(final DocumentLayoutDescriptor layout, final JSONDocumentLayoutOptions options)
	{
		return new JSONDocumentLayout(layout, options);
	}

	public static JSONDocumentLayout ofDetailTab(final DocumentLayoutDetailDescriptor detailLayout, final JSONDocumentLayoutOptions jsonOpts)
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

	@JsonProperty("internalName")
	@JsonInclude(Include.NON_EMPTY)
	private final String internalName;

	@JsonProperty("caption")
	@JsonInclude(Include.NON_EMPTY)
	private final String caption;

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

	@JsonProperty("notFoundMessage")
	@JsonInclude(Include.NON_EMPTY)
	private final String notFoundMessage;

	@JsonProperty("notFoundMessageDetail")
	@JsonInclude(Include.NON_EMPTY)
	private final String notFoundMessageDetail;

	/**
	 * Other properties
	 */
	private final Map<String, Object> otherProperties = new LinkedHashMap<>();

	/**
	 * Header layout constructor
	 */
	private JSONDocumentLayout(
			@NonNull final DocumentLayoutDescriptor layout,
			@NonNull final JSONDocumentLayoutOptions options)
	{
		this.windowId = layout.getWindowId();
		type = windowId;

		tabId = null;
		tabid = null;

		internalName = null;

		final String adLanguage = options.getAdLanguage();
		caption = layout.getCaption(adLanguage);

		documentSummaryElement = JSONDocumentLayoutElement.fromNullable(layout.getDocumentSummaryElement(), options);
		docActionElement = JSONDocumentLayoutElement.fromNullable(layout.getDocActionElement(), options);

		final DocumentLayoutSingleRow singleRowLayout = layout.getSingleRowLayout();
		sections = JSONDocumentLayoutSection.ofSectionsList(singleRowLayout.getSections(), options);
		setAdvSearchWindows(this.sections, this.windowId, null, options);
		//
		// Included tabs
		final ImmutableListMultimap<DetailId, JSONDocumentLayoutElement> elementsByTabIdToInline = sections.stream()
				.flatMap(JSONDocumentLayoutSection::streamInlineTabElements)
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						element -> element.getInlineTabId(),
						element -> element
				));

		final ImmutableSet<DetailId> tabIdsToInline = elementsByTabIdToInline.keySet();

		final ArrayList<JSONDocumentLayoutTab> jsonTabs = new ArrayList<>();
		for (final DocumentLayoutDetailDescriptor tab : layout.getDetails())
		{
			final JSONDocumentLayoutTab jsonTab = JSONDocumentLayoutTab.ofOrNull(tab, options);
			if (jsonTab == null)
			{
				continue;
			}
			else if (tabIdsToInline.contains(jsonTab.getTabId()))
			{
				final ImmutableList<JSONDocumentLayoutElement> elements = elementsByTabIdToInline.get(jsonTab.getTabId());
				elements.forEach(element -> element.setInlineTab(jsonTab));
				// NOTE: not adding the tab to the json tabs list because we don't want to render it as included tab
			}
			else
			{
				jsonTabs.add(jsonTab);
			}
		}

		tabs = ImmutableList.copyOf(jsonTabs);


		filters = null;

		emptyResultText = null;
		emptyResultHint = null;

		final NotFoundMessages notFoundMessages = singleRowLayout.getNotFoundMessages();
		this.notFoundMessage = notFoundMessages != null ? notFoundMessages.getMessage().translate(adLanguage) : null;
		this.notFoundMessageDetail = notFoundMessages != null ? notFoundMessages.getDetail().translate(adLanguage) : null;

		if (WindowConstants.isProtocolDebugging())
		{
			putDebugProperties(layout.getDebugProperties().toMap());
			putDebugProperty(JSONOptions.DEBUG_ATTRNAME, options.toString());
		}
	}

	/**
	 * From detail tab constructor.
	 */
	private JSONDocumentLayout(final DocumentLayoutDetailDescriptor detailLayout, final JSONDocumentLayoutOptions jsonOpts)
	{
		this.windowId = detailLayout.getWindowId();
		type = windowId;

		tabId = detailLayout.getDetailId();
		tabid = tabId;

		internalName = detailLayout.getInternalName();

		final DocumentLayoutSingleRow singleRowLayout = detailLayout.getSingleRowLayout();
		final String adLanguage = jsonOpts.getAdLanguage();
		caption = singleRowLayout.getCaption(adLanguage);

		documentSummaryElement = null;
		docActionElement = null;

		sections = JSONDocumentLayoutSection.ofSectionsList(singleRowLayout.getSections(), jsonOpts);
		tabs = ImmutableList.of(); // a tab(-group) has no included tabs

		filters = null;

		emptyResultText = null;
		emptyResultHint = null;

		final NotFoundMessages notFoundMessages = singleRowLayout.getNotFoundMessages();
		this.notFoundMessage = notFoundMessages != null ? notFoundMessages.getMessage().translate(adLanguage) : null;
		this.notFoundMessageDetail = notFoundMessages != null ? notFoundMessages.getDetail().translate(adLanguage) : null;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("windowId", windowId)
				.add("sections", sections.isEmpty() ? null : sections)
				.add("tabGroups", tabs.isEmpty() ? null : tabs)
				.add("filters", (filters == null || filters.isEmpty()) ? null : filters)
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

	private static void setAdvSearchWindows(
			@NonNull final List<JSONDocumentLayoutSection> sections,
			@NonNull final WindowId windowId,
			@Nullable final DetailId tabId,
			@NonNull final JSONDocumentLayoutOptions jsonOpts)
	{
		sections.stream()
				.flatMap(section -> section.getColumns().stream())
				.flatMap(column -> column.getElementGroups().stream())
				.flatMap(elementGroup -> elementGroup.getElementLines().stream())
				.flatMap(elementLine -> elementLine.getElements().stream())
				.flatMap(element -> element.getFields().stream())
				.forEach(field -> field.setAdvSearchWindow(windowId, tabId, jsonOpts));

	}
}
