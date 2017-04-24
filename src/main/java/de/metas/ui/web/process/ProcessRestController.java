package de.metas.ui.web.process;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.compiere.util.Util;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

import de.metas.logging.LogManager;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.process.ProcessInstanceResult.OpenReportAction;
import de.metas.ui.web.process.descriptor.ProcessLayout;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.process.json.JSONCreateProcessInstanceRequest;
import de.metas.ui.web.process.json.JSONProcessInstance;
import de.metas.ui.web.process.json.JSONProcessInstanceResult;
import de.metas.ui.web.process.json.JSONProcessLayout;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.controller.Execution;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import io.swagger.annotations.Api;
import lombok.NonNull;

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
@RequestMapping(value = ProcessRestController.ENDPOINT)
public class ProcessRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/process";

	private static final Logger logger = LogManager.getLogger(ProcessRestController.class);

	@Autowired
	private UserSession userSession;

	private final ConcurrentHashMap<String, IProcessInstancesRepository> pinstancesRepositoriesByHandlerType = new ConcurrentHashMap<>();

	private static final ReasonSupplier REASON_Value_DirectSetFromCommitAPI = () -> "direct set from commit API";

	public ProcessRestController(final ApplicationContext context)
	{
		//
		// Discover and register process instance repositories
		context.getBeansOfType(IProcessInstancesRepository.class)
				.values().stream()
				.forEach(processInstanceRepo -> {
					final String processHandlerType = processInstanceRepo.getProcessHandlerType();
					pinstancesRepositoriesByHandlerType.put(processHandlerType, processInstanceRepo);
					logger.info("Registered process instances repository for '{}': {}", processHandlerType, processInstanceRepo);
				});
	}

	private JSONOptions newJsonOpts()
	{
		return JSONOptions.of(userSession);
	}

	public Stream<WebuiRelatedProcessDescriptor> streamDocumentRelatedProcesses(final IProcessPreconditionsContext preconditionsContext)
	{
		return getAllRepositories()
				.stream()
				.flatMap(repo -> repo.streamDocumentRelatedProcesses(preconditionsContext));
	}

	private final IProcessInstancesRepository getRepository(@NonNull final ProcessId processId)
	{
		final String processHandlerType = processId.getProcessHandlerType();
		final IProcessInstancesRepository processInstanceRepo = pinstancesRepositoriesByHandlerType.get(processHandlerType);
		if (processInstanceRepo == null)
		{
			throw new EntityNotFoundException("No pinstances repository found for processHandlerType=" + processHandlerType);
		}
		return processInstanceRepo;
	}

	private Collection<IProcessInstancesRepository> getAllRepositories()
	{
		return pinstancesRepositoriesByHandlerType.values();
	}

	@RequestMapping(value = "/{processId}/layout", method = RequestMethod.GET)
	public JSONProcessLayout getLayout(
			@PathVariable("processId") final String adProcessIdStr //
			)
	{
		userSession.assertLoggedIn();

		final ProcessId processId = ProcessId.fromJson(adProcessIdStr);
		final IProcessInstancesRepository instancesRepository = getRepository(processId);
		final ProcessLayout layout = instancesRepository.getProcessDescriptor(processId).getLayout();
		return JSONProcessLayout.of(layout, newJsonOpts());
	}

	@RequestMapping(value = "/{processId}", method = RequestMethod.POST)
	public JSONProcessInstance createInstanceFromRequest(@PathVariable("processId") final String processIdStr, @RequestBody final JSONCreateProcessInstanceRequest request)
	{
		userSession.assertLoggedIn();

		final ProcessId processId = ProcessId.fromJson(processIdStr);
		final IProcessInstancesRepository instancesRepository = getRepository(processId);

		return Execution.callInNewExecution("pinstance.create", () -> {
			final IProcessInstanceController processInstance = instancesRepository.createNewProcessInstance(processId, request);
			return JSONProcessInstance.of(processInstance, newJsonOpts());
		});
	}

	@RequestMapping(value = "/{processId}/{pinstanceId}", method = RequestMethod.GET)
	public JSONProcessInstance getInstance(
			@PathVariable("processId") final String processIdStr //
			, @PathVariable("pinstanceId") final String pinstanceIdStr //
			)
	{
		userSession.assertLoggedIn();

		final ProcessId processId = ProcessId.fromJson(processIdStr);
		final DocumentId pinstanceId = DocumentId.of(pinstanceIdStr);
		
		final IProcessInstancesRepository instancesRepository = getRepository(processId);

		return instancesRepository.forProcessInstanceReadonly(pinstanceId, processInstance -> JSONProcessInstance.of(processInstance, newJsonOpts()));
	}

	@RequestMapping(value = "/{processId}/{pinstanceId}", method = RequestMethod.PATCH)
	public List<JSONDocument> processParametersChangeEvents(
			@PathVariable("processId") final String processIdStr //
			, @PathVariable("pinstanceId") final String pinstanceIdStr //
			, @RequestBody final List<JSONDocumentChangedEvent> events //
			)
	{
		userSession.assertLoggedIn();

		final ProcessId processId = ProcessId.fromJson(processIdStr);
		final DocumentId pinstanceId = DocumentId.of(pinstanceIdStr);
		
		final IProcessInstancesRepository instancesRepository = getRepository(processId);

		return Execution.callInNewExecution("", () -> {
			instancesRepository.forProcessInstanceWritable(pinstanceId, processInstance -> {
				processInstance.processParameterValueChanges(events, REASON_Value_DirectSetFromCommitAPI);
				return null; // void
			});
			return JSONDocument.ofEvents(Execution.getCurrentDocumentChangesCollector(), newJsonOpts());
		});
	}

	@RequestMapping(value = "/{processId}/{pinstanceId}/start", method = RequestMethod.GET)
	public JSONProcessInstanceResult startProcess(
			@PathVariable("processId") final String processIdStr //
			, @PathVariable("pinstanceId") final String pinstanceIdStr //
			)
	{
		userSession.assertLoggedIn();

		final ProcessId processId = ProcessId.fromJson(processIdStr);
		final DocumentId pinstanceId = DocumentId.of(pinstanceIdStr);
		
		final IProcessInstancesRepository instancesRepository = getRepository(processId);

		return Execution.callInNewExecution("", () -> {
			return instancesRepository.forProcessInstanceWritable(pinstanceId, processInstance -> {
				final ProcessInstanceResult result = processInstance.startProcess();
				return JSONProcessInstanceResult.of(result);
			});
		});
	}

	@RequestMapping(value = "/{processId}/{pinstanceId}/print/{filename:.*}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getReport(
			@PathVariable("processId") final String processIdStr //
			, @PathVariable("pinstanceId") final String pinstanceIdStr //
			, @PathVariable("filename") final String filename //
			)
	{
		userSession.assertLoggedIn();
		
		final ProcessId processId = ProcessId.fromJson(processIdStr);
		final DocumentId pinstanceId = DocumentId.of(pinstanceIdStr);
		
		final IProcessInstancesRepository instancesRepository = getRepository(processId);
		final ProcessInstanceResult executionResult = instancesRepository.forProcessInstanceReadonly(pinstanceId, processInstance -> processInstance.getExecutionResult());

		final OpenReportAction action = executionResult.getAction(OpenReportAction.class);
		final String reportFilename = action.getFilename();
		final String reportContentType = action.getContentType();
		final byte[] reportData = action.getReportData();

		final String reportFilenameEffective = Util.coalesce(filename, reportFilename, "");

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType(reportContentType));
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + reportFilenameEffective + "\"");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		final ResponseEntity<byte[]> response = new ResponseEntity<>(reportData, headers, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/{processId}/{pinstanceId}/attribute/{parameterName}/typeahead", method = RequestMethod.GET)
	public JSONLookupValuesList getParameterTypeahead(
			@PathVariable("processId") final String processIdStr //
			, @PathVariable("pinstanceId") final String pinstanceIdStr //
			, @PathVariable("parameterName") final String parameterName //
			, @RequestParam(name = "query", required = true) final String query //
			)
	{
		userSession.assertLoggedIn();

		final ProcessId processId = ProcessId.fromJson(processIdStr);
		final DocumentId pinstanceId = DocumentId.of(pinstanceIdStr);
		
		final IProcessInstancesRepository instancesRepository = getRepository(processId);

		return instancesRepository.forProcessInstanceReadonly(pinstanceId, processInstance -> processInstance.getParameterLookupValuesForQuery(parameterName, query))
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	@RequestMapping(value = "/{processId}/{pinstanceId}/attribute/{parameterName}/dropdown", method = RequestMethod.GET)
	public JSONLookupValuesList getParameterDropdown(
			@PathVariable("processId") final String processIdStr //
			, @PathVariable("pinstanceId") final String pinstanceIdStr //
			, @PathVariable("parameterName") final String parameterName //
			)
	{
		userSession.assertLoggedIn();

		final ProcessId processId = ProcessId.fromJson(processIdStr);
		final DocumentId pinstanceId = DocumentId.of(pinstanceIdStr);
		
		final IProcessInstancesRepository instancesRepository = getRepository(processId);

		return instancesRepository.forProcessInstanceReadonly(pinstanceId, processInstance -> processInstance.getParameterLookupValues(parameterName))
				.transform(JSONLookupValuesList::ofLookupValuesList);
	}

	public void cacheReset()
	{
		getAllRepositories().forEach(IProcessInstancesRepository::cacheReset);
	}
}
