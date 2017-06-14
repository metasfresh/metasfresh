package de.metas.ui.web.view.json;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.json.JSONDocumentBase;
import de.metas.ui.web.window.datatypes.json.JSONDocumentField;

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
	public static List<JSONViewRow> ofViewRows(final List<? extends IViewRow> rows)
	{
		return rows.stream()
				.map(JSONViewRow::ofRow)
				.collect(Collectors.toList());
	}

	public static JSONViewRow ofRow(final IViewRow row)
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
			row.getFieldNameAndJsonValues()
					.entrySet()
					.stream()
					.map(e -> JSONDocumentField.ofNameAndValue(e.getKey(), e.getValue()))
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
						.map(JSONViewRow::ofRow)
						.collect(GuavaCollectors.toImmutableList());
			}
		}

		//
		// Attributes
		if (row.hasAttributes())
		{
			jsonRow.supportAttributes = true;
		}
		
		//
		// Included views
		if(row.hasIncludedView())
		{
			jsonRow.supportIncludedViews = true;
		}

		return jsonRow;
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

	@JsonProperty(value = "supportIncludedViews")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean supportIncludedViews;

	@JsonProperty("includedDocuments")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JSONViewRow> includedDocuments;

	private JSONViewRow(final DocumentId documentId)
	{
		super(documentId);
	}
}
