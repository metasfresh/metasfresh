package de.metas.ui.web.view.json;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.util.GuavaCollectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.ui.web.view.IDocumentView;
import de.metas.ui.web.window.datatypes.DocumentPath;
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
public class JSONDocumentView extends JSONDocumentBase
{
	public static List<JSONDocumentView> ofDocumentViewList(final List<IDocumentView> documentViews)
	{
		return documentViews.stream()
				.map(JSONDocumentView::ofDocumentView)
				.collect(Collectors.toList());
	}

	public static JSONDocumentView ofDocumentView(final IDocumentView row)
	{
		//
		// Document view record
		final JSONDocumentView jsonRow = new JSONDocumentView(row.getDocumentPath());
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
			final List<JSONDocumentField> jsonFields = new ArrayList<>();

			// Add pseudo "ID" field first
			{
				final Object id = row.getDocumentId().toJson();
				jsonFields.add(0, JSONDocumentField.idField(id));
			}

			// Append the other fields
			row.getFieldNameAndJsonValues()
					.entrySet()
					.stream()
					.map(e -> JSONDocumentField.ofNameAndValue(e.getKey(), e.getValue()))
					.forEach(jsonFields::add);

			jsonRow.setFields(jsonFields);
		}

		//
		// Included documents if any
		{
			final List<? extends IDocumentView> includedDocuments = row.getIncludedDocuments();
			if (!includedDocuments.isEmpty())
			{
				jsonRow.includedDocuments = includedDocuments
						.stream()
						.map(JSONDocumentView::ofDocumentView)
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

	@JsonProperty(value = JSONDocumentViewLayout.PROPERTY_supportAttributes)
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean supportAttributes;

	@JsonProperty(value = "supportIncludedViews")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private Boolean supportIncludedViews;

	@JsonProperty("includedDocuments")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JSONDocumentView> includedDocuments;

	private JSONDocumentView(final DocumentPath documentPath)
	{
		super(documentPath);
	}
}
