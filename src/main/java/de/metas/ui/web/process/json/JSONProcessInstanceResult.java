package de.metas.ui.web.process.json;

import java.io.Serializable;

import org.adempiere.util.Check;
import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.logging.LogManager;
import de.metas.ui.web.process.ProcessInstanceResult;
import de.metas.ui.web.process.ProcessInstanceResult.OpenReportAction;
import de.metas.ui.web.process.ProcessInstanceResult.OpenSingleDocument;
import de.metas.ui.web.process.ProcessInstanceResult.OpenViewAction;
import de.metas.ui.web.process.ProcessInstanceResult.ResultAction;
import de.metas.ui.web.window.datatypes.DocumentPath;
import lombok.Getter;

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
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class JSONProcessInstanceResult implements Serializable
{
	public static final JSONProcessInstanceResult of(final ProcessInstanceResult result)
	{
		return new JSONProcessInstanceResult(result);
	}
	
	private static final Logger logger = LogManager.getLogger(JSONProcessInstanceResult.class);

	@JsonProperty("pinstanceId")
	private final int pinstanceId;

	@JsonProperty("summary")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String summary;
	@JsonProperty("error")
	private final boolean error;
	
	@JsonProperty("action")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JSONResultAction action;

	//
	// Report
	@JsonProperty("reportFilename")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Deprecated
	private String reportFilename;
	@JsonProperty("reportContentType")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Deprecated
	private String reportContentType;

	//
	// Open view
	@JsonProperty("openViewWindowId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Deprecated
	private Integer openViewWindowId;
	//
	@JsonProperty("openViewId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Deprecated
	private String openViewId;

	//
	// Open single document
	@JsonProperty("openDocumentWindowId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Deprecated
	private Integer openDocumentWindowId;
	//
	@JsonProperty("openDocumentId")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Deprecated
	private String openDocumentId;

	private JSONProcessInstanceResult(final ProcessInstanceResult result)
	{
		pinstanceId = result.getAD_PInstance_ID();

		summary = result.getSummary();
		error = result.isError();
		
		action = toJSONResultAction(result.getAction());
		
		//
		// Update action related deprecated fields:
		if(action == null)
		{
			// nothing
		}
		else if (action instanceof JSONOpenReportAction)
		{
			JSONOpenReportAction openReportAction = (JSONOpenReportAction)action;
			this.reportFilename = openReportAction.getFilename();
			this.reportContentType = openReportAction.getContentType();
		}
		else if(action instanceof JSONOpenViewAction)
		{
			JSONOpenViewAction openViewAction = (JSONOpenViewAction)action;
			openViewWindowId = openViewAction.getWindowId() > 0 ? openViewAction.getWindowId() : 0;
			openViewId = openViewAction.getViewId();
		}
		else if(action instanceof JSONOpenSingleDocumentAction)
		{
			final JSONOpenSingleDocumentAction openSingleDocumentAction = (JSONOpenSingleDocumentAction)action;
			openDocumentWindowId = openSingleDocumentAction.getWindowId();
			openDocumentId = openSingleDocumentAction.getDocumentId();
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

	/** Converts {@link ResultAction} to JSON */
	private static final JSONResultAction toJSONResultAction(final ResultAction resultAction)
	{
		if(resultAction == null)
		{
			return null;
		}
		else if (resultAction instanceof OpenReportAction)
		{
			final OpenReportAction openReportAction = (OpenReportAction)resultAction;
			return new JSONOpenReportAction(openReportAction.getFilename(), openReportAction.getContentType());
		}
		else if(resultAction instanceof OpenViewAction)
		{
			OpenViewAction openViewAction = (OpenViewAction)resultAction;
			return new JSONOpenViewAction(openViewAction.getWindowId(), openViewAction.getViewId());
		}
		else if(resultAction instanceof OpenSingleDocument)
		{
			final OpenSingleDocument openDocumentAction = (OpenSingleDocument)resultAction;
			final DocumentPath documentPath = openDocumentAction.getDocumentPath();
			return new JSONOpenSingleDocumentAction(documentPath.getAD_Window_ID(), documentPath.getDocumentId().toJson());
		}
		else
		{
			logger.warn("Unknown result action: {}. Ignoring it.", resultAction);
			return null;
		}
	}

	//
	//
	//
	//
	//

	@Getter
	public static abstract class JSONResultAction
	{
		@JsonProperty("type")
		private final String type;

		protected JSONResultAction(final String type)
		{
			Check.assumeNotEmpty(type, "type is not empty");
			this.type = type;
		}
	}

	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	@lombok.Getter
	public static class JSONOpenReportAction extends JSONResultAction
	{
		private final String filename;
		private final String contentType;

		public JSONOpenReportAction(String filename, String contentType)
		{
			super("openReport");
			this.filename = filename;
			this.contentType = contentType;
		}
	}
	
	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	@lombok.Getter
	public static class JSONOpenViewAction extends JSONResultAction
	{
		private final int windowId;
		private final String viewId;

		public JSONOpenViewAction(int windowId, String viewId)
		{
			super("openView");
			this.windowId = windowId;
			this.viewId = viewId;
		}
	}

	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	@lombok.Getter
	public static class JSONOpenSingleDocumentAction extends JSONResultAction
	{
		private final int windowId;
		private final String documentId;

		public JSONOpenSingleDocumentAction(int windowId, String documentId)
		{
			super("openDocument");
			this.windowId = windowId;
			this.documentId = documentId;
		}
	}
}
