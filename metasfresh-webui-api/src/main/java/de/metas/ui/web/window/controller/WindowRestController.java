package de.metas.ui.web.window.controller;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.security.IUserRolePermissions;
import org.compiere.process.ProcessExecutionResult;
import org.compiere.process.ProcessInfo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.adempiere.report.jasper.OutputType;
import de.metas.logging.LogManager;
import de.metas.process.ProcessCtl;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.login.LoginService;
import de.metas.ui.web.process.DocumentPreconditionsContext;
import de.metas.ui.web.process.descriptor.ProcessDescriptorsFactory;
import de.metas.ui.web.process.json.JSONDocumentActionsList;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayout;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutTab;
import de.metas.ui.web.window.datatypes.json.JSONDocumentReferencesList;
import de.metas.ui.web.window.datatypes.json.JSONDocumentViewResult;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.json.filters.JSONDocumentFilter;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.DocumentReference;
import de.metas.ui.web.window.model.DocumentReferencesService;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

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

@Api
@RestController
@RequestMapping(value = WindowRestController.ENDPOINT)
public class WindowRestController implements IWindowRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/window";
	private static final String PARAM_WindowId = WebConfig.PARAM_WindowId;
	private static final String PARAM_DocumentId = WebConfig.PARAM_DocumentId;
	private static final String PARAM_TabId = WebConfig.PARAM_TabId;
	private static final String PARAM_RowId = WebConfig.PARAM_RowId;
	private static final String PARAM_Field = "field";
	private static final String PARAM_FieldsList = "fields";
	private static final String PARAM_Advanced = "advanced";
	private static final String PARAM_Advanced_DefaultValue = "false";

	private static final String PARAM_ViewId = "viewId";
	private static final String PARAM_ViewDataType = "viewType";
	private static final String PARAM_FirstRow = "firstRow";
	private static final String PARAM_FirstRow_Description = "first row to fetch (starting from 0)";
	private static final String PARAM_PageLength = "pageLength";
	private static final String PARAM_OrderBy = "orderBy";
	private static final String PARAM_OrderBy_Description = "Command separated field names. Use +/- prefix for ascending/descending. e.g. +C_BPartner_ID,-DateOrdered";

	private static final Logger logger = LogManager.getLogger(WindowRestController.class);

	private static final ReasonSupplier REASON_Value_DirectSetFromCommitAPI = () -> "direct set from commit API";

	@Autowired
	private LoginService loginService;

	@Autowired
	@Lazy
	private UserSession userSession;

	@Autowired
	private DocumentCollection documentCollection;

	@Autowired
	@Deprecated
	private DocumentViewRestController documentViewController;

	@Autowired
	private ProcessDescriptorsFactory processDescriptorFactory;

	@Autowired
	private DocumentReferencesService documentReferencesService;

	private JSONOptions.Builder newJSONOptions()
	{
		return JSONOptions.builder()
				.setUserSession(userSession);
	}

	@Override
	@RequestMapping(value = "/layout", method = RequestMethod.GET)
	public JSONDocumentLayout layout(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_TabId, required = false) final String detailIdStr //
			, @RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced //
	)
	{
		loginService.assertLoggedIn();

		final DocumentLayoutDescriptor layout = documentCollection.getDocumentDescriptorFactory()
				.getDocumentDescriptor(adWindowId)
				.getLayout();

		final JSONOptions jsonOpts = newJSONOptions()
				.setShowAdvancedFields(advanced)
				.build();

		final DetailId detailId = DetailId.fromJson(detailIdStr);
		if (detailId == null)
		{
			return JSONDocumentLayout.ofHeaderLayout(layout, jsonOpts);
		}
		else
		{
			final DocumentLayoutDetailDescriptor detailLayout = layout.getDetail(detailId);
			return JSONDocumentLayout.ofDetailTab(detailLayout, jsonOpts);
		}
	}

	@Override
	@RequestMapping(value = "/data", method = RequestMethod.GET)
	public List<JSONDocument> data(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_DocumentId, required = true) final String idStr //
			, @RequestParam(name = PARAM_TabId, required = false) final String detailIdStr //
			, @RequestParam(name = PARAM_RowId, required = false) final String rowIdStr //
			, @RequestParam(name = PARAM_FieldsList, required = false) @ApiParam("comma separated field names") final String fieldsListStr //
			, @RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced //
	)
	{
		loginService.assertLoggedIn();

		final DocumentPath documentPath = DocumentPath.builder()
				.setAD_Window_ID(adWindowId)
				.setDocumentId(idStr)
				.setDetailId(detailIdStr)
				.setRowId(rowIdStr)
				.allowNullRowId()
				.build();

		//
		// Retrieve and return the documents
		final List<Document> documents = documentCollection.getDocuments(documentPath);
		return JSONDocument.ofDocumentsList(documents, newJSONOptions()
				.setShowAdvancedFields(advanced)
				.setDataFieldsList(fieldsListStr)
				.build());
	}

	@Override
	@RequestMapping(value = "/commit", method = RequestMethod.PATCH)
	public List<JSONDocument> commit(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_DocumentId, required = true) final String idStr //
			, @RequestParam(name = PARAM_TabId, required = false) final String detailIdStr //
			, @RequestParam(name = PARAM_RowId, required = false) final String rowIdStr //
			, @RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced //
			, @RequestBody final List<JSONDocumentChangedEvent> events)
	{
		loginService.assertLoggedIn();

		final DocumentPath documentPath = DocumentPath.builder()
				.setAD_Window_ID(adWindowId)
				.setDocumentId(idStr)
				.allowNewDocumentId()
				.setDetailId(detailIdStr)
				.setRowId(rowIdStr)
				.allowNewRowId()
				.build();

		final JSONOptions jsonOpts = newJSONOptions()
				.setShowAdvancedFields(advanced)
				.build();

		return Execution.callInNewExecution("window.commit", () -> commit0(documentPath, events, jsonOpts));
	}

	private List<JSONDocument> commit0(final DocumentPath documentPath, final List<JSONDocumentChangedEvent> events, final JSONOptions jsonOpts)
	{
		//
		// Fetch the document in writing mode
		final Document document = documentCollection.getOrCreateDocumentForWriting(documentPath);

		//
		// Apply changes
		for (final JSONDocumentChangedEvent event : events)
		{
			if (JSONDocumentChangedEvent.JSONOperation.replace == event.getOperation())
			{
				document.processValueChange(event.getPath(), event.getValue(), REASON_Value_DirectSetFromCommitAPI);
			}
			else
			{
				throw new IllegalArgumentException("Unknown operation: " + event);
			}
		}

		// Push back the changed document
		documentCollection.commit(document);

		//
		// Make sure all events were collected for the case when we just created the new document
		// FIXME: this is a workaround and in case we find out all events were collected, we just need to remove this.
		if (documentPath.isNewDocument())
		{
			logger.debug("Checking if we collected all events for the new document");
			final Set<String> collectedFieldNames = Execution.getCurrentDocumentChangesCollector().collectFrom(document, REASON_Value_DirectSetFromCommitAPI);
			if (!collectedFieldNames.isEmpty())
			{
				logger.warn("We would expect all events to be auto-magically collected but it seems that not all of them were collected!"
						+ "\n Collected field names were: {}" //
						, collectedFieldNames, new Exception("StackTrace"));
			}
		}

		//
		// Return the changes
		return JSONDocument.ofEvents(Execution.getCurrentDocumentChangesCollector(), jsonOpts);
	}

	@Override
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public List<JSONDocument> delete(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_DocumentId, required = true) final String idStr //
			, @RequestParam(name = PARAM_TabId, required = false) final String detailIdStr //
			, @RequestParam(name = PARAM_RowId, required = false) @ApiParam("comma separated rowIds") final String rowIdsListStr //
	)
	{
		loginService.assertLoggedIn();

		final DocumentPath documentPath = DocumentPath.builder()
				.setAD_Window_ID(adWindowId)
				.setDocumentId(idStr)
				.setDetailId(detailIdStr)
				.setRowIdsList(rowIdsListStr)
				.build();

		final JSONOptions jsonOpts = newJSONOptions()
				.setShowAdvancedFields(false)
				.build();

		return Execution.callInNewExecution("window.delete", () -> {
			documentCollection.delete(documentPath);
			return JSONDocument.ofEvents(Execution.getCurrentDocumentChangesCollector(), jsonOpts);
		});
	}

	@Override
	@RequestMapping(value = "/typeahead", method = RequestMethod.GET)
	public JSONLookupValuesList typeahead(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_DocumentId, required = true) final String idStr //
			, @RequestParam(name = PARAM_TabId, required = false) final String detailId //
			, @RequestParam(name = PARAM_RowId, required = false) final String rowIdStr //
			, @RequestParam(name = PARAM_Field, required = true) final String fieldName //
			, @RequestParam(name = "query", required = true) final String query //
	)
	{
		loginService.assertLoggedIn();

		final DocumentPath documentPath = DocumentPath.singleDocumentPath(adWindowId, idStr, detailId, rowIdStr);

		return documentCollection.getDocument(documentPath)
				.getFieldLookupValuesForQuery(fieldName, query)
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@Override
	@RequestMapping(value = "/dropdown", method = RequestMethod.GET)
	public JSONLookupValuesList dropdown(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_DocumentId, required = true, defaultValue = DocumentId.NEW_ID_STRING) final String idStr //
			, @RequestParam(name = PARAM_TabId, required = false) final String detailId //
			, @RequestParam(name = PARAM_RowId, required = false) final String rowIdStr //
			, @RequestParam(name = PARAM_Field, required = true) final String fieldName //
	)
	{
		loginService.assertLoggedIn();

		final DocumentPath documentPath = DocumentPath.singleDocumentPath(adWindowId, idStr, detailId, rowIdStr);

		return documentCollection.getDocument(documentPath)
				.getFieldLookupValues(fieldName)
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@Override
	@RequestMapping(value = "/viewLayout", method = RequestMethod.GET)
	@Deprecated
	public JSONDocumentLayoutTab viewLayout(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_ViewDataType, required = true) final JSONViewDataType viewDataType //

	)
	{
		return documentViewController.layout(adWindowId, viewDataType);
	}

	/**
	 * Create view
	 */
	@Override
	@RequestMapping(value = "/view", method = RequestMethod.PUT)
	@Deprecated
	public JSONDocumentViewResult createView(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_ViewDataType, required = true) final JSONViewDataType viewDataType //
			, @RequestParam(name = PARAM_FirstRow, required = false, defaultValue = "0") @ApiParam(PARAM_FirstRow_Description) final int firstRow //
			, @RequestParam(name = PARAM_PageLength, required = false, defaultValue = "0") final int pageLength //
			, @RequestBody final List<JSONDocumentFilter> jsonFilters //
	)
	{
		return documentViewController.createView(adWindowId, viewDataType, firstRow, pageLength, jsonFilters);
	}

	@Override
	@RequestMapping(value = "/view/{" + PARAM_ViewId + "}", method = RequestMethod.DELETE)
	@Deprecated
	public void deleteView(@PathVariable(PARAM_ViewId) final String viewId)
	{
		documentViewController.deleteView(viewId);
	}

	@Override
	@RequestMapping(value = "/view/{" + PARAM_ViewId + "}", method = RequestMethod.GET)
	@Deprecated
	public JSONDocumentViewResult browseView(
			@PathVariable(PARAM_ViewId) final String viewId//
			, @RequestParam(name = PARAM_FirstRow, required = true) @ApiParam(PARAM_FirstRow_Description) final int firstRow //
			, @RequestParam(name = PARAM_PageLength, required = true) final int pageLength //
			, @RequestParam(name = PARAM_OrderBy, required = false) @ApiParam(PARAM_OrderBy_Description) final String orderBysListStr //
	)
	{
		return documentViewController.browseView(viewId, firstRow, pageLength, orderBysListStr);
	}

	@Override
	@RequestMapping(value = "/documentActions", method = RequestMethod.GET)
	public JSONDocumentActionsList getDocumentActions(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_DocumentId, required = true) final String idStr //
			, @RequestParam(name = PARAM_TabId, required = false) final String detailId //
			, @RequestParam(name = PARAM_RowId, required = false) final String rowIdStr //
	)
	{
		loginService.assertLoggedIn();

		final DocumentPath documentPath = DocumentPath.singleDocumentPath(adWindowId, idStr, detailId, rowIdStr);

		final Document document = documentCollection.getDocument(documentPath);
		final String tableName = document.getEntityDescriptor().getTableName();

		final IUserRolePermissions permissions = userSession.getUserRolePermissions();
		final DocumentPreconditionsContext preconditionsContext = DocumentPreconditionsContext.of(document);

		return processDescriptorFactory.getDocumentRelatedProcesses(tableName)
				.stream()
				.filter(processDescriptor -> processDescriptor.isExecutionGranted(permissions))
				.filter(processDescriptor -> processDescriptor.isPreconditionsApplicable(preconditionsContext))
				.collect(JSONDocumentActionsList.collect(newJSONOptions().build()));
	}

	@Override
	@RequestMapping(value = "/documentReferences", method = RequestMethod.GET)
	public JSONDocumentReferencesList getDocumentReferences(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_DocumentId, required = true) final String idStr //
			, @RequestParam(name = PARAM_TabId, required = false) final String detailId //
			, @RequestParam(name = PARAM_RowId, required = false) final String rowIdStr //
	)
	{
		loginService.assertLoggedIn();

		final DocumentPath documentPath = DocumentPath.singleDocumentPath(adWindowId, idStr, detailId, rowIdStr);

		final Document document = documentCollection.getDocument(documentPath);
		final Collection<DocumentReference> documentReferences = documentReferencesService.getDocumentReferences(document).values();
		return JSONDocumentReferencesList.of(documentReferences, newJSONOptions().build());
	}

	@RequestMapping(value = "/documentPrint", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getDocumentPrint(
			@RequestParam(name = PARAM_WindowId, required = true) final int adWindowId //
			, @RequestParam(name = PARAM_DocumentId, required = true) final String idStr //
	)
	{
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(DocumentType.Window, adWindowId, idStr);

		final Document document = documentCollection.getDocument(documentPath);
		final DocumentEntityDescriptor entityDescriptor = document.getEntityDescriptor();

		final ProcessInfo pi = ProcessInfo.builder()
				.setCtx(userSession.getCtx())
				.setAD_Process_ID(entityDescriptor.getPrintProcessId())
				.setRecord(entityDescriptor.getTableName(), document.getDocumentIdAsInt())
				.setPrintPreview(true)
				.setJRDesiredOutputType(OutputType.PDF)
				.build();
		ProcessCtl.builder()
				.setProcessInfo(pi)
				.executeSync();

		final ProcessExecutionResult processExecutionResult = pi.getResult();
		final byte[] reportData = processExecutionResult.getReportData();
		// final String reportFilename = processExecutionResult.getReportFilename();
		final String reportContentType = processExecutionResult.getReportContentType();

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(reportContentType));
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline");
		// headers.setContentDispositionFormData(reportFilename, reportFilename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		final ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(reportData, headers, HttpStatus.OK);
		return response;
	}
}
