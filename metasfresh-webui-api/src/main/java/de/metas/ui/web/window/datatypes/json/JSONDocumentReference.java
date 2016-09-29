package de.metas.ui.web.window.datatypes.json;

import org.adempiere.model.ZoomInfoFactory.ZoomInfo;
import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.logging.LogManager;
import de.metas.ui.web.window.model.DocumentQueryFilter;

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
	public static final JSONDocumentReference of(final ZoomInfo zoomInfo, final JSONFilteringOptions jsonOpts)
	{
		try
		{
			return new JSONDocumentReference(zoomInfo, jsonOpts);
		}
		catch (Exception ex)
		{
			logger.warn("Failed convering {} to {}. Skipped", zoomInfo, JSONDocumentReference.class, ex);
			return null;
		}
	}

	private static final transient Logger logger = LogManager.getLogger(JSONDocumentReference.class);

	@JsonProperty("caption")
	private final String caption;
	@JsonProperty("documentType")
	private final String documentType;
	@JsonProperty("documentsCount")
	private final int documentsCount;
	@JsonProperty("filter")
	private final JSONDocumentQueryFilter filter;

	private JSONDocumentReference(final ZoomInfo zoomInfo, final JSONFilteringOptions jsonOpts)
	{
		super();
		caption = zoomInfo.getLabel();
		documentType = String.valueOf(zoomInfo.getAD_Window_ID());
		documentsCount = zoomInfo.getRecordCount();

		final DocumentQueryFilter filter = DocumentQueryFilter.of(zoomInfo.getQuery());
		this.filter = JSONDocumentQueryFilter.of(filter);
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

	public JSONDocumentQueryFilter getFilter()
	{
		return filter;
	}
}
