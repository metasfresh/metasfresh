package de.metas.ui.web.process.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.ui.web.process.ProcessInstanceResult;
import de.metas.ui.web.window.datatypes.DocumentPath;

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

@SuppressWarnings("serial")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class JSONProcessInstanceResult implements Serializable
{
	public static final JSONProcessInstanceResult of(final ProcessInstanceResult result)
	{
		return new JSONProcessInstanceResult(result);
	}

	@JsonProperty("pinstanceId")
	private final int pinstanceId;

	@JsonProperty("summary")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String summary;
	@JsonProperty("error")
	private final boolean error;

	//
	// Report
	@JsonProperty("reportFilename")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String reportFilename;
	@JsonProperty("reportContentType")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String reportContentType;

	//
	// Open view (deprecated)
	@JsonProperty("viewWindowId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Deprecated
	private final Integer viewWindowId;
	//
	@JsonProperty("viewId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Deprecated
	private final String viewId;

	//
	// Open view
	@JsonProperty("openViewWindowId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Integer openViewWindowId;
	//
	@JsonProperty("openViewId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String openViewId;

	//
	// Open single document
	@JsonProperty("openDocumentWindowId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final Integer openDocumentWindowId;
	//
	@JsonProperty("openDocumentId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String openDocumentId;

	private JSONProcessInstanceResult(final ProcessInstanceResult result)
	{
		pinstanceId = result.getAD_PInstance_ID();

		summary = result.getSummary();
		error = result.isError();

		//
		// Report
		reportFilename = result.getReportFilename();
		reportContentType = result.getReportContentType();

		//
		// View
		openViewWindowId = result.getOpenViewWindowId() > 0 ? result.getOpenViewWindowId() : null;
		openViewId = result.getOpenViewId();
		//
		viewWindowId = openViewWindowId;
		viewId = openViewId;

		//
		// Single document
		{
			final DocumentPath singleDocumentPath = result.getOpenSingleDocumentPath();
			if (singleDocumentPath == null)
			{
				openDocumentWindowId = null;
				openDocumentId = null;
			}
			else
			{
				openDocumentWindowId = singleDocumentPath.getAD_Window_ID();
				openDocumentId = singleDocumentPath.getDocumentId().toJson();
			}
		}
	}

	public int getPinstanceId()
	{
		return pinstanceId;
	}

	public String getSummary()
	{
		return summary;
	}

	public boolean isError()
	{
		return error;
	}

	public String getReportFilename()
	{
		return reportFilename;
	}

	public String getReportContentType()
	{
		return reportContentType;
	}
}
