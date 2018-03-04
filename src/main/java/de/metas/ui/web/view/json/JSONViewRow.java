package de.metas.ui.web.view.json;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.IViewRowOverrides;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRowOverridesHelper;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONDocumentBase;
import de.metas.ui.web.window.datatypes.json.JSONDocumentField;
import de.metas.ui.web.window.datatypes.json.JSONLayoutWidgetType;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * View document (row).
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class JSONViewRow extends JSONDocumentBase implements JSONViewRowBase
{
	public static List<JSONViewRow> ofViewRows(final List<? extends IViewRow> rows, final IViewRowOverrides rowOverrides, final String adLanguage)
	{
		return rows.stream()
				.map(row -> ofRow(row, rowOverrides, adLanguage))
				.collect(Collectors.toList());
	}

	public static JSONViewRow ofRow(final IViewRow row, final IViewRowOverrides rowOverrides, final String adLanguage)
	{
		//
		// Document view record
		final JSONViewRow jsonRow = new JSONViewRow(row.getId());
		if (row.isProcessed())
		{
			jsonRow.processed = true;
		}
		if (row.getType() != null)
		{
			// NOTE: mainly used by frontend to decide which Icon to show for this line
			jsonRow.type = row.getType().getIconName();
		}

		//
		// Fields
		{
			final Map<String, JSONDocumentField> jsonFields = new LinkedHashMap<>();

			// Add pseudo "ID" field first
			{
				final Object id = row.getId().toJson();
				final JSONDocumentField jsonIDField = JSONDocumentField.idField(id);
				jsonFields.put(jsonIDField.getField(), jsonIDField);
			}

			// Append the other fields
			row.getFieldNames()
					.stream()
					.map(createJSONDocumentField(row))
					.forEach(jsonField -> jsonFields.put(jsonField.getField(), jsonField));

			jsonRow.setFields(jsonFields);
		}

		//
		// Included documents if any
		{
			final List<? extends IViewRow> includedDocuments = row.getIncludedRows();
			if (!includedDocuments.isEmpty())
			{
				jsonRow.includedDocuments = includedDocuments
						.stream()
						.map(includedRow -> ofRow(includedRow, rowOverrides, adLanguage))
						.collect(GuavaCollectors.toImmutableList());
			}
		}

		//
		// Attributes
		jsonRow.supportAttributes = row.hasAttributes();

		//
		// Included views
		if (ViewRowOverridesHelper.extractSupportIncludedViews(row, rowOverrides))
		{
			jsonRow.supportIncludedViews = true;

			final ViewId includedViewId = ViewRowOverridesHelper.extractIncludedViewId(row, rowOverrides);
			if (includedViewId != null)
			{
				jsonRow.includedView = new JSONIncludedViewId(includedViewId.getWindowId(), includedViewId.getViewId());
			}
		}

		//
		// Single column row
		if (row.isSingleColumn())
		{
			jsonRow.colspan = true;
			jsonRow.caption = row.getSingleColumnCaption().translate(adLanguage);
		}

		return jsonRow;
	}

	private static final Function<String, JSONDocumentField> createJSONDocumentField(final IViewRow row)
	{
		final Map<String, Object> valuesByFieldName = row.getFieldNameAndJsonValues();
		final Map<String, DocumentFieldWidgetType> widgetTypesByFieldName = row.getWidgetTypesByFieldName();
		final Map<String, ViewEditorRenderMode> viewEditorRenderModeByFieldName = row.getViewEditorRenderModeByFieldName();

		return fieldName -> {
			final Object value = valuesByFieldName.get(fieldName);
			return JSONDocumentField.ofNameAndValue(fieldName, value)
					.setWidgetType(JSONLayoutWidgetType.fromNullable(widgetTypesByFieldName.get(fieldName)))
					.setViewEditorRenderMode(viewEditorRenderModeByFieldName.get(fieldName));
		};
	}

	/**
	 * Record type.
	 * 
	 * NOTE: mainly used by frontend to decide which Icon to show for this line
	 */
	@JsonProperty("type")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String type;

	@JsonProperty("processed")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean processed;

	@JsonProperty(value = JSONViewLayout.PROPERTY_supportAttributes)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean supportAttributes;

	@JsonProperty("supportIncludedViews")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean supportIncludedViews;
	@JsonProperty("includedView")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private JSONIncludedViewId includedView;

	@JsonProperty("includedDocuments")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JSONViewRow> includedDocuments;

	//
	// Single column row
	@JsonProperty("colspan")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean colspan;
	@JsonProperty("caption")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String caption;

	private JSONViewRow(final DocumentId documentId)
	{
		super(documentId);
	}

	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	@Value
	private static final class JSONIncludedViewId
	{
		private final WindowId windowId;
		private final String viewId;
	}
}
