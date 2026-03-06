package de.metas.ui.web.view.json;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import de.metas.ui.web.document.filter.json.JSONDocumentFilterDescriptor;
import de.metas.ui.web.view.ViewCloseAction;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.descriptor.IncludedViewLayout;
import de.metas.ui.web.view.descriptor.ViewLayout;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutElement;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutOptions;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import lombok.Builder;
import lombok.Getter;
import lombok.Value;

import java.util.List;
import java.util.Set;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JSONViewLayout
{
	public static JSONViewLayout of(final ViewLayout gridLayout, final JSONDocumentLayoutOptions options)
	{
		return new JSONViewLayout(gridLayout, options);
	}

	/**
	 * i.e. AD_Window_ID
	 */
	@JsonProperty("type")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Deprecated WindowId type;
	@JsonProperty("windowId")
	@JsonInclude(JsonInclude.Include.NON_NULL) WindowId windowId;

	@JsonProperty("profileId")
	@JsonInclude(JsonInclude.Include.NON_NULL) ViewProfileId profileId;

	@Getter @JsonProperty("caption")
	@JsonInclude(JsonInclude.Include.NON_NULL) String caption;

	@Getter @JsonProperty("description")
	@JsonInclude(JsonInclude.Include.NON_NULL) String description;

	@JsonProperty("emptyResultText")
	@JsonInclude(JsonInclude.Include.NON_NULL) String emptyResultText;

	@JsonProperty("emptyResultHint")
	@JsonInclude(JsonInclude.Include.NON_NULL) String emptyResultHint;

	@JsonProperty("pageLength") int pageLength;

	@Getter @JsonProperty("elements")
	@JsonInclude(JsonInclude.Include.NON_EMPTY) List<JSONDocumentLayoutElement> elements;

	@Getter @JsonProperty("filters")
	@JsonInclude(JsonInclude.Include.NON_EMPTY) List<JSONDocumentFilterDescriptor> filters;

	public static final String PROPERTY_supportAttributes = "supportAttributes";
	@JsonProperty(value = PROPERTY_supportAttributes) boolean supportAttributes;
	//
	@JsonProperty("supportTree") boolean supportTree;
	@JsonProperty("collapsible")
	@JsonInclude(JsonInclude.Include.NON_NULL) Boolean collapsible;
	@JsonProperty("expandedDepth")
	@JsonInclude(JsonInclude.Include.NON_NULL) Integer expandedDepth;

	@JsonProperty("allowedCloseActions") Set<ViewCloseAction> allowedCloseActions;

	//
	@JsonProperty("includedView") JSONIncludedViewSupport includedView;
	//
	@Deprecated
	@JsonProperty("supportIncludedView") boolean supportIncludedView;
	@Deprecated
	@JsonProperty("supportIncludedViewOnSelect") Boolean supportIncludedViewOnSelect;

	@JsonProperty("supportNewRecord") boolean supportNewRecord;

	@JsonProperty("supportOpenRecord")
	@JsonInclude(JsonInclude.Include.NON_NULL) Boolean supportOpenRecord;

	@JsonProperty("supportGeoLocations")
	@JsonInclude(JsonInclude.Include.NON_NULL) Boolean supportGeoLocations;

	@JsonProperty("focusOnFieldName")
	@JsonInclude(JsonInclude.Include.NON_NULL) String focusOnFieldName;

	private JSONViewLayout(final ViewLayout layout, final JSONDocumentLayoutOptions options)
	{
		windowId = layout.getWindowId();
		type = windowId;

		profileId = layout.getProfileId();

		final String adLanguage = options.getAdLanguage();
		caption = layout.getCaption(adLanguage);
		description = layout.getDescription(adLanguage);
		emptyResultText = layout.getEmptyResultText(adLanguage);
		emptyResultHint = layout.getEmptyResultHint(adLanguage);
		pageLength = layout.getPageLength();

		//
		// Elements
		List<JSONDocumentLayoutElement> elements = JSONDocumentLayoutElement.ofList(layout.getElements(), options);
		final String idFieldName = layout.getIdFieldName();
		if (options.isDebugShowColumnNamesForCaption()
				&& idFieldName != null
				&& !JSONDocumentLayoutElement.hasField(elements, idFieldName))
		{
			elements = ImmutableList.<JSONDocumentLayoutElement>builder()
					.add(JSONDocumentLayoutElement.debuggingField(idFieldName, DocumentFieldWidgetType.Text))
					.addAll(elements)
					.build();
		}
		this.elements = elements;

		filters = JSONDocumentFilterDescriptor.ofCollection(layout.getFilters(), options);

		supportAttributes = layout.isAttributesSupport();

		allowedCloseActions = layout.getAllowedViewCloseActions();

		//
		// Included view
		includedView = JSONIncludedViewSupport.fromNullable(layout.getIncludedViewLayout());
		if (includedView != null)
		{
			supportIncludedView = true;
			supportIncludedViewOnSelect = includedView.isOpenOnSelect() ? Boolean.TRUE : null;
		}
		else
		{
			supportIncludedView = false;
			supportIncludedViewOnSelect = null;
		}

		//
		// Tree
		supportTree = layout.isTreeSupport();
		if (supportTree)
		{
			collapsible = layout.isTreeCollapsible();
			if (collapsible)
			{
				expandedDepth = layout.getTreeExpandedDepth();
			}
			else
			{
				expandedDepth = null;
			}
		}
		else
		{
			collapsible = null;
			expandedDepth = null;
		}

		supportNewRecord = layout.isAllowNew();
		supportOpenRecord = layout.isAllowOpeningRowDetails();

		supportGeoLocations = layout.isGeoLocationSupport();

		focusOnFieldName = layout.getFocusOnFieldName();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("AD_Window_ID", windowId)
				.add("caption", caption)
				.add("elements", elements.isEmpty() ? null : elements)
				.add("filters", filters.isEmpty() ? null : filters)
				.toString();
	}

	@Value
	@Builder
	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	public static class JSONIncludedViewSupport
	{
		public static JSONIncludedViewSupport fromNullable(final IncludedViewLayout includedViewLayout)
		{
			if (includedViewLayout == null)
			{
				return null;
			}

			return builder()
					.openOnSelect(includedViewLayout.isOpenOnSelect())
					.blurWhenOpen(includedViewLayout.isBlurWhenOpen())
					.closeOnDeselect(includedViewLayout.isCloseOnDeselect())
					.build();
		}

		boolean openOnSelect;
		boolean blurWhenOpen;
		boolean closeOnDeselect;
	}
}
