package de.metas.ui.web.window.controller;

import java.util.List;
import java.util.function.Function;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.report.jasper.OutputType;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessInfo;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.menu.MenuTree;
import de.metas.ui.web.menu.MenuTreeRepository;
import de.metas.ui.web.process.DocumentPreconditionsAsContext;
import de.metas.ui.web.process.ProcessRestController;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.process.json.JSONDocumentActionsList;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayout;
import de.metas.ui.web.window.datatypes.json.JSONDocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONDocumentReferencesGroupList;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.JSONZoomInto;
import de.metas.ui.web.window.descriptor.DetailId;
import de.metas.ui.web.window.descriptor.DocumentDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutDetailDescriptor;
import de.metas.ui.web.window.descriptor.factory.NewRecordDescriptorsProvider;
import de.metas.ui.web.window.exceptions.InvalidDocumentPathException;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.DocumentReference;
import de.metas.ui.web.window.model.DocumentReferencesService;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.IDocumentFieldView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Api
@RestController
@RequestMapping(value = WindowRestController.ENDPOINT)
public class WindowRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/window";
	private static final String PARAM_Advanced = "advanced";
	private static final String PARAM_Advanced_DefaultValue = "false";
	private static final String PARAM_FieldsList = "fields";

	private static final ReasonSupplier REASON_Value_DirectSetFromCommitAPI = () -> "direct set from commit API";

	@Autowired
	private UserSession userSession;

	@Autowired
	private DocumentCollection documentCollection;
	@Autowired
	private NewRecordDescriptorsProvider newRecordDescriptorsProvider;

	@Autowired
	private ProcessRestController processRestController;

	@Autowired
	private DocumentReferencesService documentReferencesService;

	@Autowired
	private MenuTreeRepository menuTreeRepository;

	private JSONOptions.Builder newJSONOptions()
	{
		return JSONOptions.builder(userSession)
				.setNewRecordDescriptorsProvider(newRecordDescriptorsProvider);
	}

	@GetMapping("/{windowId}/layout")
	public JSONDocumentLayout getLayout(
			@PathVariable("windowId") final String windowIdStr //
			, @RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced //
	)
	{
		userSession.assertLoggedIn();

		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentDescriptor descriptor = documentCollection.getDocumentDescriptorFactory().getDocumentDescriptor(windowId);
		DocumentPermissionsHelper.checkWindowAccess(descriptor.getEntityDescriptor(), userSession.getUserRolePermissions());

		final DocumentLayoutDescriptor layout = descriptor.getLayout();

		final JSONOptions jsonOpts = newJSONOptions()
				.setShowAdvancedFields(advanced)
				.build();

		return JSONDocumentLayout.ofHeaderLayout(layout, jsonOpts);
	}

	@GetMapping("/{windowId}/{tabId}/layout")
	public JSONDocumentLayout getLayout(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("tabId") final String tabIdStr //
			, @RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced //
	)
	{
		userSession.assertLoggedIn();

		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentDescriptor descriptor = documentCollection.getDocumentDescriptorFactory().getDocumentDescriptor(windowId);
		DocumentPermissionsHelper.checkWindowAccess(descriptor.getEntityDescriptor(), userSession.getUserRolePermissions());
		
		final DocumentLayoutDescriptor layout = descriptor.getLayout();

		final JSONOptions jsonOpts = newJSONOptions()
				.setShowAdvancedFields(advanced)
				.build();

		final DetailId detailId = DetailId.fromJson(tabIdStr);
		final DocumentLayoutDetailDescriptor detailLayout = layout.getDetail(detailId);
		return JSONDocumentLayout.ofDetailTab(detailLayout, jsonOpts);
	}

	@GetMapping("/{windowId}/{documentId}")
	public List<JSONDocument> getData(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentIdStr //
			, @RequestParam(name = PARAM_FieldsList, required = false) @ApiParam("comma separated field names") final String fieldsListStr //
			, @RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced //
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, documentIdStr);
		return getData(documentPath, fieldsListStr, advanced);
	}

	@GetMapping("/{windowId}/{documentId}/{tabId}")
	public List<JSONDocument> getData(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentIdStr //
			, @PathVariable("tabId") final String tabIdStr //
			, @RequestParam(name = PARAM_FieldsList, required = false) @ApiParam("comma separated field names") final String fieldsListStr //
			, @RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced //
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.includedDocumentPath(windowId, documentIdStr, tabIdStr);
		return getData(documentPath, fieldsListStr, advanced);
	}

	@GetMapping("/{windowId}/{documentId}/{tabId}/{rowId}")
	public List<JSONDocument> getData(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentIdStr //
			, @PathVariable("tabId") final String tabIdStr //
			, @PathVariable("rowId") final String rowIdStr //
			, @RequestParam(name = PARAM_FieldsList, required = false) @ApiParam("comma separated field names") final String fieldsListStr //
			, @RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced //
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.includedDocumentPath(windowId, documentIdStr, tabIdStr, rowIdStr);
		return getData(documentPath, fieldsListStr, advanced);
	}

	private List<JSONDocument> getData(final DocumentPath documentPath, final String fieldsListStr, final boolean advanced)
	{
		userSession.assertLoggedIn();

		final JSONOptions jsonOpts = newJSONOptions()
				.setShowAdvancedFields(advanced)
				.setDataFieldsList(fieldsListStr)
				.build();

		return documentCollection.forRootDocumentReadonly(documentPath, rootDocument -> {
			List<Document> documents;
			if (documentPath.isRootDocument())
			{
				documents = ImmutableList.of(rootDocument);
			}
			else if (documentPath.isAnyIncludedDocument())
			{
				documents = rootDocument.getIncludedDocuments(documentPath.getDetailId());
			}
			else if (documentPath.isSingleIncludedDocument())
			{
				documents = ImmutableList.of(rootDocument.getIncludedDocument(documentPath.getDetailId(), documentPath.getSingleRowId()));
			}
			else
			{
				throw new InvalidDocumentPathException(documentPath);
			}

			return JSONDocument.ofDocumentsList(documents, jsonOpts);
		});
	}

	@PatchMapping("/{windowId}/{documentId}")
	public List<JSONDocument> patchRootDocument(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentIdStr //
			, @RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced //
			, @RequestBody final List<JSONDocumentChangedEvent> events)
	{
		final DocumentPath documentPath = DocumentPath.builder()
				.setDocumentType(WindowId.fromJson(windowIdStr))
				.setDocumentId(documentIdStr)
				.allowNewDocumentId()
				.build();

		return patchDocument(documentPath, advanced, events);
	}

	@PatchMapping("/{windowId}/{documentId}/{tabId}/{rowId}")
	public List<JSONDocument> patchIncludedDocument(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentIdStr //
			, @PathVariable("tabId") final String detailIdStr //
			, @PathVariable("rowId") final String rowIdStr //
			, @RequestParam(name = PARAM_Advanced, required = false, defaultValue = PARAM_Advanced_DefaultValue) final boolean advanced //
			, @RequestBody final List<JSONDocumentChangedEvent> events)
	{
		final DocumentPath documentPath = DocumentPath.builder()
				.setDocumentType(WindowId.fromJson(windowIdStr))
				.setDocumentId(documentIdStr)
				.setDetailId(detailIdStr)
				.setRowId(rowIdStr)
				.allowNewRowId()
				.build();

		return patchDocument(documentPath, advanced, events);
	}

	private List<JSONDocument> patchDocument(final DocumentPath documentPath, final boolean advanced, final List<JSONDocumentChangedEvent> events)
	{
		userSession.assertLoggedIn();

		final JSONOptions jsonOpts = newJSONOptions()
				.setShowAdvancedFields(advanced)
				.build();

		return Execution.callInNewExecution("window.commit", () -> patchDocument0(documentPath, events, jsonOpts));
	}

	private List<JSONDocument> patchDocument0(final DocumentPath documentPath, final List<JSONDocumentChangedEvent> events, final JSONOptions jsonOpts)
	{
		documentCollection.forDocumentWritable(documentPath, document -> {
			document.processValueChanges(events, REASON_Value_DirectSetFromCommitAPI);
			Execution.getCurrentDocumentChangesCollector().setPrimaryChange(document.getDocumentPath());
			return null; // void
		});
		return JSONDocument.ofEvents(Execution.getCurrentDocumentChangesCollector(), jsonOpts);
	}

	@DeleteMapping("/{windowId}/{documentId}")
	public List<JSONDocument> deleteRootDocument(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentId //
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, documentId);
		return deleteDocuments(ImmutableList.of(documentPath));
	}

	@DeleteMapping("/{windowId}")
	public List<JSONDocument> deleteRootDocumentsList(
			@PathVariable("windowId") final String windowIdStr //
			, @RequestParam(name = "ids") @ApiParam("comma separated documentIds") final String idsListStr //
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final List<DocumentPath> documentPaths = DocumentPath.rootDocumentPathsList(windowId, idsListStr);
		if (documentPaths.isEmpty())
		{
			throw new IllegalArgumentException("No ids provided");
		}

		return deleteDocuments(documentPaths);
	}

	@DeleteMapping("/{windowId}/{documentId}/{tabId}/{rowId}")
	public List<JSONDocument> deleteIncludedDocument(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentId //
			, @PathVariable("tabId") final String tabId //
			, @PathVariable("rowId") final String rowId //
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.includedDocumentPath(windowId, documentId, tabId, rowId);
		return deleteDocuments(ImmutableList.of(documentPath));
	}

	@DeleteMapping("/{windowId}/{documentId}/{tabId}")
	public List<JSONDocument> deleteIncludedDocumentsList(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentId //
			, @PathVariable("tabId") final String tabId //
			, @RequestParam(name = "ids") @ApiParam("comma separated rowIds") final String rowIdsListStr //
	)
	{
		final DocumentPath documentPath = DocumentPath.builder()
				.setDocumentType(WindowId.fromJson(windowIdStr))
				.setDocumentId(documentId)
				.setDetailId(tabId)
				.setRowIdsList(rowIdsListStr)
				.build();
		if (documentPath.getRowIds().isEmpty())
		{
			throw new IllegalArgumentException("No rowId(s) specified");
		}
		return deleteDocuments(ImmutableList.of(documentPath));
	}

	private List<JSONDocument> deleteDocuments(final List<DocumentPath> documentPaths)
	{
		userSession.assertLoggedIn();

		final JSONOptions jsonOpts = newJSONOptions()
				.setShowAdvancedFields(false)
				.build();

		return Execution.callInNewExecution("window.delete", () -> {
			documentCollection.deleteAll(documentPaths);
			return JSONDocument.ofEvents(Execution.getCurrentDocumentChangesCollector(), jsonOpts);
		});
	}

	/**
	 * Typeahead for root document's field
	 */
	@GetMapping("/{windowId}/{documentId}/attribute/{fieldName}/typeahead")
	public JSONLookupValuesList getDocumentFieldTypeahead(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentId //
			, @PathVariable("fieldName") final String fieldName //
			, @RequestParam(name = "query", required = true) final String query //
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, documentId);
		return getDocumentFieldTypeahead(documentPath, fieldName, query);
	}

	/**
	 * Typeahead for included document's field
	 */
	@GetMapping(value = "/{windowId}/{documentId}/{tabId}/{rowId}/attribute/{fieldName}/typeahead")
	public JSONLookupValuesList getDocumentFieldTypeahead(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentId //
			, @PathVariable("tabId") final String tabId //
			, @PathVariable("rowId") final String rowId //
			, @PathVariable("fieldName") final String fieldName //
			, @RequestParam(name = "query", required = true) final String query //
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.includedDocumentPath(windowId, documentId, tabId, rowId);
		return getDocumentFieldTypeahead(documentPath, fieldName, query);
	}

	/** Typeahead: unified implementation */
	private JSONLookupValuesList getDocumentFieldTypeahead(final DocumentPath documentPath, final String fieldName, final String query)
	{
		userSession.assertLoggedIn();

		return documentCollection.forDocumentReadonly(documentPath, document -> document.getFieldLookupValuesForQuery(fieldName, query))
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@GetMapping("/{windowId}/{documentId}/attribute/{fieldName}/dropdown")
	public JSONLookupValuesList getDocumentFieldDropdown(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentId //
			, @PathVariable("fieldName") final String fieldName //
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, documentId);
		return getDocumentFieldDropdown(documentPath, fieldName);
	}

	@GetMapping("/{windowId}/{documentId}/{tabId}/{rowId}/attribute/{fieldName}/dropdown")
	public JSONLookupValuesList getDocumentFieldDropdown(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentId //
			, @PathVariable("tabId") final String tabId //
			, @PathVariable("rowId") final String rowId //
			, @PathVariable("fieldName") final String fieldName //
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.includedDocumentPath(windowId, documentId, tabId, rowId);
		return getDocumentFieldDropdown(documentPath, fieldName);
	}

	private JSONLookupValuesList getDocumentFieldDropdown(final DocumentPath documentPath, final String fieldName)
	{
		userSession.assertLoggedIn();

		return documentCollection.forDocumentReadonly(documentPath, document -> document.getFieldLookupValues(fieldName))
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@ApiOperation("field current value's window layout to zoom into")
	@GetMapping("/{windowId}/{documentId}/attribute/{fieldName}/zoomInto")
	public JSONZoomInto getDocumentFieldZoomInto(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentId //
			, @PathVariable("fieldName") final String fieldName //
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, documentId);
		return getDocumentFieldZoomInto(documentPath, fieldName);
	}

	@ApiOperation("field current value's window layout to zoom into")
	@GetMapping("/{windowId}/{documentId}/{tabId}/{rowId}/attribute/{fieldName}/zoomInto")
	public JSONZoomInto getDocumentFieldZoomInto(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentId //
			, @PathVariable("tabId") final String tabId //
			, @PathVariable("rowId") final String rowId //
			, @PathVariable("fieldName") final String fieldName //
	)
	{
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.includedDocumentPath(windowId, documentId, tabId, rowId);
		return getDocumentFieldZoomInto(documentPath, fieldName);
	}

	private JSONZoomInto getDocumentFieldZoomInto(final DocumentPath documentPath, final String fieldName)
	{
		userSession.assertLoggedIn();

		final ITableRecordReference zoomInfoTableRecordRef = documentCollection.forDocumentReadonly(documentPath, document -> {
			final IDocumentFieldView field = document.getFieldView(fieldName);
			return field.getValueAs(ITableRecordReference.class);
		});
		if (zoomInfoTableRecordRef == null)
		{
			throw new EntityNotFoundException("Cannot fetch ZoomInto infos from a null value")
					.setParameter("documentPath", documentPath)
					.setParameter("fieldName", fieldName);
		}

		final DocumentPath zoomIntoDocumentPath = documentCollection.getDocumentPath(zoomInfoTableRecordRef);
		final JSONDocumentLayout windowLayout = getLayout(zoomIntoDocumentPath.getWindowId().toJson(), false); // advanced=false
		return JSONZoomInto.builder()
				.documentPath(JSONDocumentPath.ofWindowDocumentPath(zoomIntoDocumentPath))
				.source(JSONDocumentPath.ofWindowDocumentPath(documentPath, fieldName))
				.windowLayout(windowLayout)
				.build();
	}

	@GetMapping("/{windowId}/{documentId}/actions")
	public JSONDocumentActionsList getDocumentActions(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentId //
	)
	{
		userSession.assertLoggedIn();

		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, documentId);
		return documentCollection.forDocumentReadonly(documentPath, document -> {
			final DocumentPreconditionsAsContext preconditionsContext = DocumentPreconditionsAsContext.of(document);

			return processRestController.streamDocumentRelatedProcesses(preconditionsContext)
					.filter(WebuiRelatedProcessDescriptor::isEnabledOrNotSilent)
					.collect(JSONDocumentActionsList.collect(newJSONOptions().build()));
		});
	}

	@GetMapping(value = "/{windowId}/{documentId}/references")
	public JSONDocumentReferencesGroupList getDocumentReferences(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentId //
	)
	{
		userSession.assertLoggedIn();

		// Get document references
		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, documentId);
		final List<DocumentReference> documentReferences = documentReferencesService.getDocumentReferences(documentPath);
		if (documentReferences.isEmpty())
		{
			return JSONDocumentReferencesGroupList.EMPTY;
		}

		// Organize document references in groups (by top level menu) and return them as JSON
		final JSONOptions jsonOpts = newJSONOptions().build();
		final MenuTree menuTree = menuTreeRepository.getMenuTree(userSession.getUserRolePermissionsKey(), jsonOpts.getAD_Language());
		final String othersMenuCaption = Services.get(IMsgBL.class).translatable("DocumentReferences.group.Others").translate(jsonOpts.getAD_Language());
		return JSONDocumentReferencesGroupList.of(documentReferences, menuTree, othersMenuCaption, jsonOpts);
	}

	@GetMapping("/{windowId}/{documentId}/print/{filename:.*}")
	public ResponseEntity<byte[]> getDocumentPrint(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentIdStr //
			, @PathVariable("filename") final String filename)
	{
		userSession.assertLoggedIn();

		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, documentIdStr);

		final Document document = documentCollection.forDocumentReadonly(documentPath, Function.identity());
		final int windowNo = document.getWindowNo();
		final DocumentEntityDescriptor entityDescriptor = document.getEntityDescriptor();

		final int printProcessId = entityDescriptor.getPrintProcessId();
		final TableRecordReference recordRef = documentCollection.getTableRecordReference(documentPath);

		final ProcessExecutionResult processExecutionResult = ProcessInfo.builder()
				.setCtx(userSession.getCtx())
				.setAD_Process_ID(printProcessId)
				.setWindowNo(windowNo) // important: required for ProcessInfo.findReportingLanguage
				.setRecord(recordRef)
				.setPrintPreview(true)
				.setJRDesiredOutputType(OutputType.PDF)
				//
				.buildAndPrepareExecution()
				.onErrorThrowException()
				.switchContextWhenRunning()
				.executeSync()
				.getResult();

		final byte[] reportData = processExecutionResult.getReportData();
		final String reportContentType = processExecutionResult.getReportContentType();

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(reportContentType));
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		final ResponseEntity<byte[]> response = new ResponseEntity<>(reportData, headers, HttpStatus.OK);
		return response;
	}

	/**
	 * @task https://github.com/metasfresh/metasfresh/issues/1090
	 */
	@GetMapping("/{windowId}/{documentId}/processNewRecord")
	public int processRecord(
			@PathVariable("windowId") final String windowIdStr //
			, @PathVariable("documentId") final String documentIdStr //
	)
	{
		userSession.assertLoggedIn();

		final WindowId windowId = WindowId.fromJson(windowIdStr);
		final DocumentPath documentPath = DocumentPath.rootDocumentPath(windowId, documentIdStr);

		return Execution.callInNewExecution("window.processTemplate", () -> documentCollection.forDocumentWritable(documentPath, document -> {
			document.saveIfValidAndHasChanges();
			if (document.hasChangesRecursivelly())
			{
				throw new AdempiereException("Not saved");
			}

			final int newRecordId = newRecordDescriptorsProvider.getNewRecordDescriptor(document.getEntityDescriptor())
					.getProcessor()
					.processNewRecordDocument(document);
			return newRecordId;
		}));
	}
}
