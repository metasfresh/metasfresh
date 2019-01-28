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
import de.metas.util.GuavaCollectors;
import io.swagger.annotations.ApiModel;
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

/**
 * Window included tab layout (JSON)
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@ApiModel("tab")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class JSONDocumentLayoutTab
{
	public static List<JSONDocumentLayoutTab> ofList(
			@NonNull final Collection<DocumentLayoutDetailDescriptor> details,
			@NonNull final JSONOptions jsonOpts)
	{
		final Collection<DocumentFilterDescriptor> filters = null;
		return details.stream()
				.map(detail -> new JSONDocumentLayoutTab(detail, filters, jsonOpts))
				.filter(jsonDetail -> jsonDetail.hasElements() || !jsonDetail.subTabs.isEmpty())
				.collect(GuavaCollectors.toImmutableList());
	}

	@JsonProperty("windowId")
	private final WindowId windowId;
	@Deprecated
	@JsonProperty("type")
	private final WindowId type;

	@JsonProperty("tabId")
	private final DetailId tabId;
	@Deprecated
	@JsonProperty("tabid")
	private final DetailId tabid;

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

	@JsonProperty("elements")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentLayoutElement> elements;

	@JsonProperty("tabs")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final List<JSONDocumentLayoutTab> subTabs;

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

	private JSONDocumentLayoutTab(
			@NonNull final DocumentLayoutDetailDescriptor includedTabLayout,
			@Nullable final Collection<DocumentFilterDescriptor> filters,
			@NonNull final JSONOptions jsonOpts)
	{
		final ViewLayout gridLayout = includedTabLayout.getGridLayout();

		supportQuickInput = includedTabLayout.isSupportQuickInput();
		queryOnActivate = includedTabLayout.isQueryOnActivate();

		windowId = includedTabLayout.getWindowId();
		type = windowId;

		this.tabId = includedTabLayout.getDetailId();
		tabid = tabId;

		internalName = includedTabLayout.getInternalName();

		final String adLanguage = jsonOpts.getAD_Language();
		if (jsonOpts.isDebugShowColumnNamesForCaption() && tabId != null)
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

		if (gridLayout != null)
		{
			this.emptyResultText = gridLayout.getEmptyResultText(adLanguage);
			this.emptyResultHint = gridLayout.getEmptyResultHint(adLanguage);

			this.elements = JSONDocumentLayoutElement.ofList(gridLayout.getElements(), jsonOpts);
			this.defaultOrderBys = JSONViewOrderBy.ofList(gridLayout.getDefaultOrderBys());
		}
		else
		{
			this.emptyResultText = null;
			this.emptyResultHint = null;

			this.elements = ImmutableList.of();
			this.defaultOrderBys = ImmutableList.of();
		}

		this.filters = JSONDocumentFilterDescriptor.ofCollection(filters, jsonOpts);
		this.subTabs = JSONDocumentLayoutTab.ofList(includedTabLayout.getSubTabLayouts(), jsonOpts);
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

	private boolean hasElements()
	{
		return !elements.isEmpty();
	}
}
