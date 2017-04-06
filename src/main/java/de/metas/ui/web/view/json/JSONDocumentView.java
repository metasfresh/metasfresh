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

	public static JSONDocumentView ofDocumentView(final IDocumentView documentView)
	{
		final JSONDocumentView jsonDocument = new JSONDocumentView(documentView.getDocumentPath());

		//
		// Fields
		{
			final List<JSONDocumentField> jsonFields = new ArrayList<>();

			// Add pseudo "ID" field first
			final String idFieldName = documentView.getIdFieldNameOrNull();
			if (idFieldName != null)
			{
				final Object id = documentView.getDocumentId().toJson();
				jsonFields.add(0, JSONDocumentField.idField(id));
			}

			// Append the other fields
			documentView.getFieldNameAndJsonValues()
					.entrySet()
					.stream()
					.map(e -> JSONDocumentField.ofNameAndValue(e.getKey(), e.getValue()))
					.forEach(jsonFields::add);

			jsonDocument.setFields(jsonFields);

			//
			// Document view record specific attributes
			if (documentView.isProcessed())
			{
				jsonDocument.processed = true;
			}
			if (documentView.hasAttributes())
			{
				jsonDocument.supportAttributes = true;
			}
			if (documentView.getType() != null)
			{
				// NOTE: mainly used by frontend to decide which Icon to show for this line
				jsonDocument.type = documentView.getType().getIconName();
			}
		}

		//
		// Included documents if any
		{
			final List<? extends IDocumentView> includedDocuments = documentView.getIncludedDocuments();
			if (!includedDocuments.isEmpty())
			{
				final List<JSONDocumentView> jsonIncludedDocuments = includedDocuments
						.stream()
						.map(JSONDocumentView::ofDocumentView)
						.collect(GuavaCollectors.toImmutableList());
				jsonDocument.setIncludedDocuments(jsonIncludedDocuments);
			}
		}

		return jsonDocument;
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
	
	@JsonProperty("includedDocuments")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<JSONDocumentView> includedDocuments;

	private JSONDocumentView(final DocumentPath documentPath)
	{
		super(documentPath);
	}

	private void setIncludedDocuments(final List<JSONDocumentView> includedDocuments)
	{
		this.includedDocuments = includedDocuments;
	}
}
