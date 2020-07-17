package de.metas.ui.web.window.datatypes.json;

import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.ui.web.document.filter.DocumentFilterDescriptor;
import de.metas.ui.web.document.filter.json.JSONDocumentFilterDescriptor;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.view.json.JSONViewOrderBy;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import de.metas.common.util.CoalesceUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.NonNull;

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

@ApiModel(value = "tab", description = "Window included tab layout")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class JSONDocumentLayoutTab
{
	public static List<JSONDocumentLayoutTab> ofList(
			@NonNull final Collection<DocumentLayoutDetailDescriptor> details,
			@NonNull final JSONDocumentLayoutOptions jsonOpts)
	{
		final Collection<DocumentFilterDescriptor> filters = null;

		// note: this used to be implemented with stream, but that was too cumbersome to debug
		final ImmutableList.Builder<JSONDocumentLayoutTab> result = ImmutableList.builder();
		for (final DocumentLayoutDetailDescriptor detail : details)
		{
			final JSONDocumentLayoutTab jsonTab = new JSONDocumentLayoutTab(detail, filters, jsonOpts);
			if (isTabEmpty(jsonTab))
			{
				continue;
			}
			result.add(jsonTab);
		}
		return result.build();
	}

	private static boolean isTabEmpty(@NonNull final JSONDocumentLayoutTab tab)
	{
		final boolean singleRowDetailLayout = CoalesceUtil.coalesce(tab.singleRowDetailLayout, false);
		if (singleRowDetailLayout)
		{
			return tab.sections.isEmpty();
		}
		return tab.elements.isEmpty() && tab.subTabs.isEmpty();
	}

	@JsonProperty("windowId")
	private final WindowId windowId;

	@JsonProperty("tabId")
	private final DetailId tabId;

	@JsonProperty("internalName")
	@JsonInclude(Include.NON_EMPTY)
	private final String internalName;

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

	/**  */
	@ApiModelProperty( //
			allowEmptyValue = true, value = "Required to render the table columns for this tab.<br>"
					+ "Therefore filled, unless <code>singleRowDetailLayout</code> is <code>true</code>.")
	@JsonProperty("elements")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentLayoutElement> elements;

	@ApiModelProperty( //
			allowEmptyValue = true, value = "Subtabs of this tab; note: there are either subtabs or sections, but not both.")
	@JsonProperty("tabs")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentLayoutTab> subTabs;

	@ApiModelProperty( //
			allowEmptyValue = true, value = "\"detail\" layout of the rows in this tab; required if singleRowDetailView is <code>true</code> and in advanced edit mode")
	@JsonProperty("sections")
	@JsonInclude(Include.NON_EMPTY)
	private final List<JSONDocumentLayoutSection> sections;

	@JsonProperty("filters")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentFilterDescriptor> filters;

	@JsonProperty("defaultOrderBys")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONViewOrderBy> defaultOrderBys;

	@JsonProperty("supportQuickInput")
	private final boolean supportQuickInput;

	@JsonProperty("queryOnActivate")
	private final boolean queryOnActivate;

	@ApiModelProperty(allowEmptyValue = true, //
			value = "If set to true, then the frontend shall render the tab in \"detail\" view. It can assume that there is at most one record to be shown in the tab.<br>"
					+ "If empty, assume false.")
	@JsonProperty("singleRowDetailView")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final Boolean singleRowDetailLayout;

	private JSONDocumentLayoutTab(
			@NonNull final DocumentLayoutDetailDescriptor includedTabLayout,
			@Nullable final Collection<DocumentFilterDescriptor> filters,
			@NonNull final JSONDocumentLayoutOptions options)
	{
		final ViewLayout gridLayout = includedTabLayout.getGridLayout();

		supportQuickInput = includedTabLayout.isSupportQuickInput();
		queryOnActivate = includedTabLayout.isQueryOnActivate();

		windowId = includedTabLayout.getWindowId();

		tabId = includedTabLayout.getDetailId();

		internalName = includedTabLayout.getInternalName();

		final String adLanguage = options.getAdLanguage();
		if (options.isDebugShowColumnNamesForCaption()
				&& tabId != null
				&& gridLayout != null)
		{
			caption = new StringBuilder()
					.append("[")
					.append(tabId)
					.append(queryOnActivate ? "Q" : "")
					.append("] ")
					.append(gridLayout.getCaption(adLanguage))
					.toString();
		}
		else
		{
			caption = includedTabLayout.getCaption(adLanguage);
		}

		this.description = includedTabLayout.getDescription(adLanguage);

		if (includedTabLayout.isSingleRowDetailLayout())
		{
			this.sections = JSONDocumentLayoutSection.ofSectionsList(includedTabLayout.getSingleRowLayout().getSections(), options);
			this.subTabs = ImmutableList.of();

			this.emptyResultText = null;
			this.emptyResultHint = null;
			this.elements = ImmutableList.of();
			this.defaultOrderBys = ImmutableList.of();
		}
		else
		{
			this.sections = ImmutableList.of();
			this.subTabs = JSONDocumentLayoutTab.ofList(includedTabLayout.getSubTabLayouts(), options);

			if (gridLayout != null)
			{
				this.emptyResultText = gridLayout.getEmptyResultText(adLanguage);
				this.emptyResultHint = gridLayout.getEmptyResultHint(adLanguage);
				this.elements = JSONDocumentLayoutElement.ofList(gridLayout.getElements(), options);
				this.defaultOrderBys = JSONViewOrderBy.ofList(gridLayout.getDefaultOrderBys());
			}
			else
			{
				this.emptyResultText = null;
				this.emptyResultHint = null;
				this.elements = ImmutableList.of();
				this.defaultOrderBys = ImmutableList.of();
			}
		}

		// false=>null; because true is a very special case, let's not clutter our JSON with another property that's false almost all the time
		singleRowDetailLayout = includedTabLayout.isSingleRowDetailLayout() ? true : null;

		this.filters = JSONDocumentFilterDescriptor.ofCollection(filters, options);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("tabId", tabId)
				.add("caption", caption)
				.add("elements", elements.isEmpty() ? null : elements)
				.add("filters", filters.isEmpty() ? null : filters)
				.add("defaultOrderBys", defaultOrderBys.isEmpty() ? null : defaultOrderBys)
				.toString();
	}
}
