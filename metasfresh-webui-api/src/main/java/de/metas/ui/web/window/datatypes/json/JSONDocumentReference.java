package de.metas.ui.web.window.datatypes.json;

import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.datatypes.json.filters.JSONDocumentFilter;
import de.metas.ui.web.window.model.DocumentReference;
import de.metas.ui.web.window.model.filters.DocumentFilter;

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

public class JSONDocumentReference
{
	public static final JSONDocumentReference of(final DocumentReference documentReference, final JSONFilteringOptions jsonOpts)
	{
		try
		{
			return new JSONDocumentReference(documentReference, jsonOpts);
		}
		catch (Exception ex)
		{
			logger.warn("Failed convering {} to {}. Skipped", documentReference, JSONDocumentReference.class, ex);
			return null;
		}
	}

	private static final transient Logger logger = LogManager.getLogger(JSONDocumentReference.class);

	@JsonProperty("id")
	private final String id;
	@JsonProperty("caption")
	private final String caption;
	@JsonProperty("documentType")
	private final String documentType;
	@JsonProperty("documentsCount")
	private final int documentsCount;
	@JsonProperty("filter")
	private final JSONDocumentFilter filter;

	private JSONDocumentReference(final DocumentReference documentReference, final JSONFilteringOptions jsonOpts)
	{
		super();
		id = documentReference.getId();
		caption = documentReference.getCaption(jsonOpts.getAD_Language());
		documentType = String.valueOf(documentReference.getAD_Window_ID());
		documentsCount = documentReference.getDocumentsCount();

		final DocumentFilter filter = documentReference.getFilter();
		this.filter = JSONDocumentFilter.of(filter);
	}
	
	public String getId()
	{
		return id;
	}

	public String getCaption()
	{
		return caption;
	}

	public String getDocumentType()
	{
		return documentType;
	}

	public int getDocumentsCount()
	{
		return documentsCount;
	}

	public JSONDocumentFilter getFilter()
	{
		return filter;
	}
}
