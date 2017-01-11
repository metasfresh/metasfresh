package de.metas.ui.web.process;

import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
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

import de.metas.process.ProcessInfo;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.process.descriptor.ProcessLayout;
import de.metas.ui.web.process.json.JSONCreateProcessInstanceRequest;
import de.metas.ui.web.process.json.JSONProcessInstance;
import de.metas.ui.web.process.json.JSONProcessInstanceResult;
import de.metas.ui.web.process.json.JSONProcessLayout;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.IDocumentViewSelection;
import de.metas.ui.web.view.IDocumentViewsRepository;
import de.metas.ui.web.window.controller.Execution;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import io.swagger.annotations.Api;

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
@RequestMapping(value = ProcessRestController.ENDPOINT)
public class ProcessRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/process";

	@Autowired
	private UserSession userSession;

	@Autowired
	private ProcessInstancesRepository instancesRepository;

	@Autowired
	private DocumentCollection documentsCollection;

	@Autowired
	private IDocumentViewsRepository documentViewsRepo;

	private static final ReasonSupplier REASON_Value_DirectSetFromCommitAPI = () -> "direct set from commit API";

	private JSONOptions newJsonOpts()
	{
		return JSONOptions.builder()
				.setUserSession(userSession)
				.build();
	}

	@RequestMapping(value = "/{processId}/layout", method = RequestMethod.GET)
	public JSONProcessLayout getLayout(
			@PathVariable("processId") final int adProcessId //
	)
	{
		userSession.assertLoggedIn();

		final ProcessLayout layout = instancesRepository.getProcessDescriptor(adProcessId).getLayout();
		return JSONProcessLayout.of(layout, newJsonOpts());
	}

	@RequestMapping(value = "/{processId}", method = RequestMethod.POST)
	public JSONProcessInstance createInstanceFromRequest(
			@PathVariable("processId") final int adProcessId, @RequestBody final JSONCreateProcessInstanceRequest request //
	)
	{
		userSession.assertLoggedIn();

		// Validate request's AD_Process_ID
		// (we are not using it, but just for consistency)
		if (request.getAD_Process_ID() > 0 && request.getAD_Process_ID() != adProcessId)
		{
			throw new IllegalArgumentException("Request's AD_Process_ID is not valid. It shall be " + adProcessId + " or none but it was " + request.getAD_Process_ID());
		}

		Check.assume(adProcessId > 0, "adProcessId > 0");

		final TableRecordReference documentRef;
		if (request.getAD_Window_ID() > 0 && !Check.isEmpty(request.getDocumentId()))
		{
			final DocumentPath documentPath = DocumentPath.rootDocumentPath(DocumentType.Window, request.getAD_Window_ID(), request.getDocumentId());
			documentRef = documentsCollection.getTableRecordReference(documentPath);
		}
		else
		{
			documentRef = null;
		}

		final String whereClause;
		if (!Check.isEmpty(request.getViewId()))
		{
			final IDocumentViewSelection view = documentViewsRepo.getView(request.getViewId());
			whereClause = view.getSqlWhereClause(request.getViewDocumentIds());
		}
		else
		{
			whereClause = null;
		}

		final ProcessInfo processInfo = ProcessInfo.builder()
				.setCtx(userSession.getCtx())
				.setCreateTemporaryCtx()
				.setAD_Process_ID(adProcessId)
				.setRecord(documentRef)
				.setWhereClause(whereClause)
				.build();

		return Execution.callInNewExecution("pinstance.create", () -> {
			final ProcessInstance processInstance = instancesRepository.createNewProcessInstance(processInfo);
			return JSONProcessInstance.of(processInstance, newJsonOpts());
		});

	}

	@RequestMapping(value = "/{processId}/{pinstanceId}", method = RequestMethod.GET)
	public JSONProcessInstance getInstance(
			@PathVariable("processId") final int processId_NOTUSED //
			, @PathVariable("pinstanceId") final int pinstanceId //
	)
	{
		userSession.assertLoggedIn();

		final ProcessInstance processInstance = instancesRepository.getProcessInstanceForReading(pinstanceId);
		return JSONProcessInstance.of(processInstance, newJsonOpts());
	}

	@RequestMapping(value = "/instance/{pinstanceId}/parameters", method = RequestMethod.PATCH)
	public List<JSONDocument> processParametersChangeEvents_DEPRECATED(
			@PathVariable("pinstanceId") final int pinstanceId //
			, @RequestBody final List<JSONDocumentChangedEvent> events //
	)
	{
		return processParametersChangeEvents(-1, pinstanceId, events);
	}

	@RequestMapping(value = "/{processId}/{pinstanceId}", method = RequestMethod.PATCH)
	public List<JSONDocument> processParametersChangeEvents(
			@PathVariable("processId") final int processId_NOTUSED //
			, @PathVariable("pinstanceId") final int pinstanceId //
			, @RequestBody final List<JSONDocumentChangedEvent> events //
	)
	{
		userSession.assertLoggedIn();

		return Execution.callInNewExecution("pinstance.processParametersChangeEvents", () -> {
			final ProcessInstance processInstance = instancesRepository.getProcessInstanceForWriting(pinstanceId);

			//
			// Apply changes
			processInstance.processParameterValueChanges(events, REASON_Value_DirectSetFromCommitAPI);

			// Push back the changed document
			instancesRepository.checkin(processInstance);

			//
			// Return the changes
			return JSONDocument.ofEvents(Execution.getCurrentDocumentChangesCollector(), newJsonOpts());
		});
	}

	@RequestMapping(value = "/{processId}/{pinstanceId}/start", method = RequestMethod.GET)
	public JSONProcessInstanceResult startProcess(
			@PathVariable("processId") final int processId_NOTUSED //
			, @PathVariable("pinstanceId") final int pinstanceId //
	)
	{
		userSession.assertLoggedIn();

		return Execution.callInNewExecution("pinstance.startProcess", () -> {
			final ProcessInstance processInstance = instancesRepository.getProcessInstanceForWriting(pinstanceId);
			final ProcessInstanceResult result = processInstance.startProcess();

			// Push back the changed document
			instancesRepository.checkin(processInstance);

			return JSONProcessInstanceResult.of(result);
		});
	}

	@RequestMapping(value = "/{processId}/{pinstanceId}/print/{filename:.*}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getReport(
			@PathVariable("processId") final int processId_NOTUSED //
			, @PathVariable("pinstanceId") final int pinstanceId //
			, @PathVariable("filename") final String filename //
	)
	{
		final ProcessInstanceResult executionResult = instancesRepository.getProcessInstanceForReading(pinstanceId)
				.getExecutionResult();

		final String reportFilename = executionResult.getReportFilename();
		final String reportContentType = executionResult.getReportContentType();
		final byte[] reportData = executionResult.getReportData();

		final String reportFilenameEffective = Util.coalesce(filename, reportFilename, "");

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(reportContentType));
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + reportFilenameEffective + "\"");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		final ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(reportData, headers, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/{processId}/{pinstanceId}/attribute/{parameterName}/typeahead", method = RequestMethod.GET)
	public JSONLookupValuesList getParameterTypeahead(
			@PathVariable("processId") final int processId_NOTUSED //
			, @PathVariable("pinstanceId") final int pinstanceId //
			, @PathVariable("parameterName") final String parameterName //
			, @RequestParam(name = "query", required = true) final String query //
	)
	{
		userSession.assertLoggedIn();

		return instancesRepository.getProcessInstanceForReading(pinstanceId)
				.getParameterLookupValuesForQuery(parameterName, query)
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@RequestMapping(value = "/{processId}/{pinstanceId}/attribute/{parameterName}/dropdown", method = RequestMethod.GET)
	public JSONLookupValuesList getParameterDropdown(
			@PathVariable("processId") final int processId_NOTUSED //
			, @PathVariable("pinstanceId") final int pinstanceId //
			, @PathVariable("parameterName") final String parameterName //
	)
	{
		userSession.assertLoggedIn();

		return instancesRepository.getProcessInstanceForReading(pinstanceId)
				.getParameterLookupValues(parameterName)
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}
}
