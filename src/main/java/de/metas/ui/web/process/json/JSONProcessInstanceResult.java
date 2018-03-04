package de.metas.ui.web.process.json;

import java.io.Serializable;
import java.util.Set;

import org.adempiere.util.Check;
import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.logging.LogManager;
import de.metas.ui.web.process.ProcessInstanceResult;
import de.metas.ui.web.process.ProcessInstanceResult.OpenIncludedViewAction;
import de.metas.ui.web.process.ProcessInstanceResult.OpenReportAction;
import de.metas.ui.web.process.ProcessInstanceResult.OpenSingleDocument;
import de.metas.ui.web.process.ProcessInstanceResult.OpenViewAction;
import de.metas.ui.web.process.ProcessInstanceResult.ResultAction;
import de.metas.ui.web.process.ProcessInstanceResult.SelectViewRowsAction;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewProfileId;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
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
	private final String pinstanceId;

	@JsonProperty("summary")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private final String summary;
	@JsonProperty("error")
	private final boolean error;

	@JsonProperty("action")
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private final JSONResultAction action;

	private JSONProcessInstanceResult(final ProcessInstanceResult result)
	{
		pinstanceId = result.getInstanceId().toJson();

		summary = result.getSummary();
		error = result.isError();

		action = toJSONResultAction(result.getAction());
	}

	/** Converts {@link ResultAction} to JSON */
	private static final JSONResultAction toJSONResultAction(final ResultAction resultAction)
	{
		if (resultAction == null)
		{
			return null;
		}
		else if (resultAction instanceof OpenReportAction)
		{
			final OpenReportAction openReportAction = (OpenReportAction)resultAction;
			return new JSONOpenReportAction(openReportAction.getFilename(), openReportAction.getContentType());
		}
		else if (resultAction instanceof OpenViewAction)
		{
			final OpenViewAction openViewAction = (OpenViewAction)resultAction;
			return new JSONOpenViewAction(openViewAction.getViewId());
		}
		else if (resultAction instanceof OpenIncludedViewAction)
		{
			final OpenIncludedViewAction openIncludedViewAction = (OpenIncludedViewAction)resultAction;
			return new JSONOpenIncludedViewAction(openIncludedViewAction.getViewId(), openIncludedViewAction.getProfileId());
		}
		else if (resultAction instanceof OpenSingleDocument)
		{
			final OpenSingleDocument openDocumentAction = (OpenSingleDocument)resultAction;
			final DocumentPath documentPath = openDocumentAction.getDocumentPath();
			return new JSONOpenSingleDocumentAction(documentPath.getWindowId(), documentPath.getDocumentId().toJson(), openDocumentAction.isModal());
		}
		else if (resultAction instanceof SelectViewRowsAction)
		{
			final SelectViewRowsAction selectViewRowsAction = (SelectViewRowsAction)resultAction;
			return new JSONSelectViewRowsAction(selectViewRowsAction.getViewId(), selectViewRowsAction.getRowIds());
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
	protected static abstract class JSONResultAction
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

		public JSONOpenReportAction(final String filename, final String contentType)
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
		private final WindowId windowId;
		private final String viewId;

		public JSONOpenViewAction(final ViewId viewId)
		{
			super("openView");
			this.windowId = viewId.getWindowId();
			this.viewId = viewId.getViewId();
		}
	}

	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	@lombok.Getter
	public static class JSONOpenIncludedViewAction extends JSONResultAction
	{
		private final WindowId windowId;
		private final String viewId;
		private final JSONViewDataType viewType = JSONViewDataType.includedView;
		@JsonInclude(JsonInclude.Include.NON_EMPTY)
		private final String profileId;

		public JSONOpenIncludedViewAction(final ViewId viewId, final ViewProfileId profileId)
		{
			super("openIncludedView");
			this.windowId = viewId.getWindowId();
			this.viewId = viewId.getViewId();
			this.profileId = profileId != null ? profileId.toJson() : null;
		}
	}

	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	@lombok.Getter
	public static class JSONOpenSingleDocumentAction extends JSONResultAction
	{
		private final WindowId windowId;
		private final String documentId;
		private final boolean modal;
		private final boolean advanced = false;

		public JSONOpenSingleDocumentAction(final WindowId windowId, final String documentId, final boolean modal)
		{
			super("openDocument");
			this.windowId = windowId;
			this.documentId = documentId;
			this.modal = modal;
		}
	}

	@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
	@lombok.Getter
	public static class JSONSelectViewRowsAction extends JSONResultAction
	{
		private final WindowId windowId;
		private final String viewId;
		private final Set<String> rowIds;

		public JSONSelectViewRowsAction(final ViewId viewId, final DocumentIdsSelection rowIds)
		{
			super("selectViewRows");

			this.windowId = viewId.getWindowId();
			this.viewId = viewId.getViewId();
			this.rowIds = rowIds.toJsonSet();
		}

	}
}
