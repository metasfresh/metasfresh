package de.metas.ui.web.process;

import java.util.List;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.process.ProcessInfo;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.login.LoginService;
import de.metas.ui.web.process.descriptor.ProcessLayout;
import de.metas.ui.web.process.json.JSONProcessInstance;
import de.metas.ui.web.process.json.JSONProcessInstanceResult;
import de.metas.ui.web.process.json.JSONProcessLayout;
import de.metas.ui.web.session.UserSession;
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
	private LoginService loginService;
	@Autowired
	private ProcessInstancesRepository instancesRepository;
	@Autowired
	private UserSession userSession;

	@Autowired
	private DocumentCollection documentsCollection;

	private static final ReasonSupplier REASON_Value_DirectSetFromCommitAPI = () -> "direct set from commit API";

	private JSONOptions newJsonOpts()
	{
		return JSONOptions.builder()
				.setUserSession(userSession)
				.build();
	}

	@RequestMapping(value = "/layout", method = RequestMethod.GET)
	public JSONProcessLayout layout(
			@RequestParam(name = "processId", required = true) final int adProcessId //
	)
	{
		loginService.assertLoggedIn();

		final ProcessLayout layout = instancesRepository.getProcessDescriptor(adProcessId).getLayout();
		return JSONProcessLayout.of(layout, newJsonOpts());
	}

	@RequestMapping(value = "/instance", method = RequestMethod.PUT)
	public JSONProcessInstance createInstance(
			@RequestParam(name = "processId", required = true) final int adProcessId //
			, @RequestParam(name = WebConfig.PARAM_WindowId, required = false, defaultValue = "0") final int adWindowId //
			, @RequestParam(name = WebConfig.PARAM_DocumentId, required = false) final String idStr //
	)
	{
		loginService.assertLoggedIn();

		final TableRecordReference documentRef;
		if(adWindowId > 0)
		{
			final DocumentPath documentPath = DocumentPath.rootDocumentPath(DocumentType.Window, adWindowId, idStr);
			documentRef = documentsCollection.getTableRecordReference(documentPath);
		}
		else
		{
			documentRef = null;
		}
		
		final ProcessInfo processInfo = ProcessInfo.builder()
				.setCtx(userSession.getCtx())
				.setCreateTemporaryCtx()
				.setAD_Process_ID(adProcessId)
				.setRecord(documentRef)
				.build();

		return Execution.callInNewExecution("pinstance.create", () -> {
			final ProcessInstance processInstance = instancesRepository.createNewProcessInstance(processInfo);
			return JSONProcessInstance.of(processInstance, newJsonOpts());
		});
	}

	@RequestMapping(value = "/instance/{pinstanceId}", method = RequestMethod.GET)
	public JSONProcessInstance getInstance(@PathVariable("pinstanceId") final int pinstanceId)
	{
		loginService.assertLoggedIn();

		final ProcessInstance processInstance = instancesRepository.getProcessInstanceForReading(pinstanceId);
		return JSONProcessInstance.of(processInstance, newJsonOpts());
	}

	@RequestMapping(value = "/instance/{pinstanceId}/parameters", method = RequestMethod.PATCH)
	public List<JSONDocument> commit(
			@PathVariable("pinstanceId") final int pinstanceId //
			, @RequestBody final List<JSONDocumentChangedEvent> events //
	)
	{
		loginService.assertLoggedIn();

		return Execution.callInNewExecution("pinstance.commit", () -> {
			final ProcessInstance processInstance = instancesRepository.getProcessInstanceForWriting(pinstanceId);

			//
			// Apply changes
			for (final JSONDocumentChangedEvent event : events)
			{
				if (JSONDocumentChangedEvent.JSONOperation.replace == event.getOperation())
				{
					processInstance.processParameterValueChange(event.getPath(), event.getValue(), REASON_Value_DirectSetFromCommitAPI);
				}
				else
				{
					throw new IllegalArgumentException("Unknown operation: " + event);
				}
			}

			// Push back the changed document
			instancesRepository.checkin(processInstance);

			//
			// Return the changes
			return JSONDocument.ofEvents(Execution.getCurrentDocumentChangesCollector(), newJsonOpts());
		});
	}

	@RequestMapping(value = "/instance/{pinstanceId}/start", method = RequestMethod.GET)
	public JSONProcessInstanceResult startProcess(@PathVariable("pinstanceId") final int pinstanceId)
	{
		loginService.assertLoggedIn();

		return Execution.callInNewExecution("pinstance.startProcess", () -> {
			final ProcessInstance processInstance = instancesRepository.getProcessInstanceForWriting(pinstanceId);
			final ProcessInstanceResult result = processInstance.startProcess();

			// Push back the changed document
			instancesRepository.checkin(processInstance);
			// TODO: remove it from instancesRepository

			return JSONProcessInstanceResult.of(result);
		});
	}

	@RequestMapping(value = "/instance/{pinstanceId}/parameters/{parameterName}/typeahead", method = RequestMethod.GET)
	public JSONLookupValuesList typeahead(
			@PathVariable("pinstanceId") final int pinstanceId //
			, @PathVariable("parameterName") final String parameterName //
			, @RequestParam(name = "query", required = true) final String query //
	)
	{
		loginService.assertLoggedIn();

		return instancesRepository.getProcessInstanceForReading(pinstanceId)
				.getParameterLookupValuesForQuery(parameterName, query)
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@RequestMapping(value = "/instance/{pinstanceId}/parameters/{parameterName}/dropdown", method = RequestMethod.GET)
	public JSONLookupValuesList dropdown(
			@PathVariable("pinstanceId") final int pinstanceId //
			, @PathVariable("parameterName") final String parameterName //
	)
	{
		loginService.assertLoggedIn();

		return instancesRepository.getProcessInstanceForReading(pinstanceId)
				.getParameterLookupValues(parameterName)
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}
}
