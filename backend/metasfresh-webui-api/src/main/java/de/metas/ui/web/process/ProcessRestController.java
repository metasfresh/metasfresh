package de.metas.ui.web.process;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.logging.LogManager;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessClassInfo;
import de.metas.process.ProcessMDC;
import de.metas.ui.web.cache.ETagResponseEntityBuilder;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.process.ProcessInstanceResult.OpenReportAction;
import de.metas.ui.web.process.adprocess.WebuiProcessClassInfo;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.process.json.JSONCreateProcessInstanceRequest;
import de.metas.ui.web.process.json.JSONProcessInstance;
import de.metas.ui.web.process.json.JSONProcessInstanceResult;
import de.metas.ui.web.process.json.JSONProcessLayout;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRowIdsSelection;
import de.metas.ui.web.window.controller.Execution;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutOptions;
import de.metas.ui.web.window.datatypes.json.JSONDocumentOptions;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.NullDocumentChangesCollector;
import de.metas.util.Check;
import de.metas.util.lang.CoalesceUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(ProcessRestController.ENDPOINT)
public class ProcessRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/process";

	private static final Logger logger = LogManager.getLogger(ProcessRestController.class);
	private final ImmutableMap<String, IProcessInstancesRepository> pinstancesRepositoriesByHandlerType;
	private final UserSession userSession;
	private final IViewsRepository viewsRepo;
	private final DocumentCollection documentsCollection;

	private static final ReasonSupplier REASON_Value_DirectSetFromCommitAPI = () -> "direct set from commit API";

	public ProcessRestController(
			@NonNull final List<IProcessInstancesRepository> pinstancesRepositories,
			@NonNull final UserSession userSession,
			@NonNull final IViewsRepository viewsRepo,
			@NonNull final DocumentCollection documentsCollection)
	{
		this.pinstancesRepositoriesByHandlerType = Maps.uniqueIndex(pinstancesRepositories, IProcessInstancesRepository::getProcessHandlerType);
		logger.info("Registered process instances repositories: {}", pinstancesRepositoriesByHandlerType);

		this.userSession = userSession;
		this.viewsRepo = viewsRepo;
		this.documentsCollection = documentsCollection;
	}

	private IAutoCloseable putMDC(
			@NonNull final ProcessId processId,
			@NonNull final DocumentId pinstanceDocId)
	{
		final PInstanceId pinstanceId = PInstanceId.ofRepoIdOrNull(pinstanceDocId.toIntOr(-1));
		return ProcessMDC.putProcessAndInstanceId(processId.toAdProcessId(), pinstanceId);
	}

	private MDCCloseable putMDC(@NonNull final ProcessId processId)
	{
		return ProcessMDC.putAdProcessId(processId.toAdProcessId());
	}

	private JSONOptions newJsonOptions()
	{
		return JSONOptions.of(userSession);
	}

	private JSONDocumentLayoutOptions newJsonLayoutOptions()
	{
		return JSONDocumentLayoutOptions.of(userSession);
	}

	private JSONDocumentOptions newJsonDocumentOptions()
	{
		return JSONDocumentOptions.of(userSession);
	}

	public Stream<WebuiRelatedProcessDescriptor> streamDocumentRelatedProcesses(final WebuiPreconditionsContext preconditionsContext)
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

	@GetMapping("/{processId}/layout")
	public ResponseEntity<JSONProcessLayout> getLayout(
			@PathVariable("processId") final String adProcessIdStr,
			final WebRequest request)
	{
		final ProcessId processId = ProcessId.fromJson(adProcessIdStr);

		try (final MDCCloseable processMDC = putMDC(processId))
		{
			userSession.assertLoggedIn();

			final IProcessInstancesRepository instancesRepository = getRepository(processId);
			final ProcessDescriptor descriptor = instancesRepository.getProcessDescriptor(processId);

			return ETagResponseEntityBuilder.ofETagAware(request, descriptor)
					.includeLanguageInETag()
					.cacheMaxAge(userSession.getHttpCacheMaxAge())
					.map(ProcessDescriptor::getLayout)
					.jsonLayoutOptions(this::newJsonLayoutOptions)
					.toLayoutJson(JSONProcessLayout::of);
		}
	}

	@PostMapping("/{processId}")
	public JSONProcessInstance createInstanceFromRequest(
			@PathVariable("processId") final String processIdStr,
			@RequestBody final JSONCreateProcessInstanceRequest jsonRequest)
	{
		final ProcessId processId = ProcessId.fromJson(processIdStr);

		final DocumentId pinstanceId;
		try (final MDCCloseable processMDC = putMDC(processId))
		{
			userSession.assertLoggedIn();

			final ViewRowIdsSelection viewRowIdsSelection = jsonRequest.getViewRowIdsSelection();
			final ViewId viewId = viewRowIdsSelection != null ? viewRowIdsSelection.getViewId() : null;
			final DocumentIdsSelection viewSelectedRowIds = viewRowIdsSelection != null ? viewRowIdsSelection.getRowIds() : DocumentIdsSelection.EMPTY;

			// Get the effective singleDocumentPath, i.e.
			// * if provided, use it
			// * if not provided and we have a single selected row in the view, ask the view's row to provide the effective document path
			DocumentPath singleDocumentPath = jsonRequest.getSingleDocumentPath();
			if (singleDocumentPath == null && viewSelectedRowIds.isSingleDocumentId())
			{
				final IView view = viewsRepo.getView(viewId);
				singleDocumentPath = view.getById(viewSelectedRowIds.getSingleDocumentId()).getDocumentPath();
			}

			final CreateProcessInstanceRequest request = CreateProcessInstanceRequest.builder()
					.processId(processId)
					.singleDocumentPath(singleDocumentPath)
					.selectedIncludedDocumentPaths(jsonRequest.getSelectedIncludedDocumentPaths())
					.viewRowIdsSelection(viewRowIdsSelection)
					.parentViewRowIdsSelection(jsonRequest.getParentViewRowIdsSelection())
					.childViewRowIdsSelection(jsonRequest.getChildViewRowIdsSelection())
					.build();
			// Validate request's AD_Process_ID
			// (we are not using it, but just for consistency)
			request.assertProcessIdEquals(jsonRequest.getProcessId());

			final IProcessInstancesRepository instancesRepository = getRepository(request.getProcessId());
			pinstanceId = Execution.callInNewExecution("pinstance.create", () -> {
				final IProcessInstanceController processInstance = instancesRepository.createNewProcessInstance(request);
				return processInstance.getInstanceId();
			});
		}

		return getInstance(processId, pinstanceId);
	}

	@GetMapping("/{processId}/{pinstanceId}")
	public JSONProcessInstance getInstance(
			@PathVariable("processId") final String processIdStr,
			@PathVariable("pinstanceId") final String pinstanceIdStr)
	{
		final ProcessId processId = ProcessId.fromJson(processIdStr);
		final DocumentId pinstanceId = DocumentId.of(pinstanceIdStr);

		return getInstance(processId, pinstanceId);
	}

	private JSONProcessInstance getInstance(final ProcessId processId, final DocumentId pinstanceId)
	{
		try (final IAutoCloseable mdcCloseable = putMDC(processId, pinstanceId))
		{
			userSession.assertLoggedIn();

			final IProcessInstancesRepository instancesRepository = getRepository(processId);

			return instancesRepository.forProcessInstanceReadonly(pinstanceId, processInstance -> JSONProcessInstance.of(processInstance, newJsonOptions()));
		}
	}

	@PatchMapping("/{processId}/{pinstanceId}")
	public List<JSONDocument> processParametersChangeEvents(
			@PathVariable("processId") final String processIdStr //
			, @PathVariable("pinstanceId") final String pinstanceIdStr //
			, @RequestBody final List<JSONDocumentChangedEvent> events //
	)
	{
		final ProcessId processId = ProcessId.fromJson(processIdStr);
		final DocumentId pinstanceId = DocumentId.of(pinstanceIdStr);

		try (final IAutoCloseable mdcCloseable = putMDC(processId, pinstanceId))
		{
			userSession.assertLoggedIn();
			Check.assumeNotEmpty(events, "events is not empty");

			final IProcessInstancesRepository instancesRepository = getRepository(processId);

			return Execution.callInNewExecution("", () -> {

				final IDocumentChangesCollector changesCollector = Execution.getCurrentDocumentChangesCollectorOrNull(); // get our collector to fill with the changes that we will record

				instancesRepository.forProcessInstanceWritable(pinstanceId, changesCollector, processInstance -> {

					processInstance.processParameterValueChanges(events, REASON_Value_DirectSetFromCommitAPI);
					return null; // void
				});
				return JSONDocument.ofEvents(changesCollector, newJsonDocumentOptions());
			});
		}
	}

	@GetMapping(value = "/{processId}/{pinstanceId}/start")
	public JSONProcessInstanceResult startProcess(
			@PathVariable("processId") final String processIdStr,
			@PathVariable("pinstanceId") final String pinstanceIdStr)
	{
		final ProcessId processId = ProcessId.fromJson(processIdStr);
		final DocumentId pinstanceId = DocumentId.of(pinstanceIdStr);

		try (final IAutoCloseable mdcCloseable = putMDC(processId, pinstanceId))
		{
			userSession.assertLoggedIn();

			final IProcessInstancesRepository instancesRepository = getRepository(processId);

			return Execution.prepareNewExecution()
					.outOfTransaction()
					.execute(() -> {
						return instancesRepository.forProcessInstanceWritable(pinstanceId, NullDocumentChangesCollector.instance, processInstance -> {
							final ProcessInstanceResult result = processInstance.startProcess(ProcessExecutionContext.builder()
									.ctx(Env.getCtx())
									.adLanguage(userSession.getAD_Language())
									.viewsRepo(viewsRepo)
									.documentsCollection(documentsCollection)
									.build());
							return JSONProcessInstanceResult.of(result);
						});
					});
		}
	}

	@ApiOperation("Retrieves and serves a report that was previously created by a reporting process.")
	@GetMapping("/{processId}/{pinstanceId}/print/{filename:.*}")
	public ResponseEntity<byte[]> getReport(
			@PathVariable("processId") final String processIdStr,
			@PathVariable("pinstanceId") final String pinstanceIdStr,
			@PathVariable("filename") final String filename)
	{
		final ProcessId processId = ProcessId.fromJson(processIdStr);
		final DocumentId pinstanceId = DocumentId.of(pinstanceIdStr);

		try (final IAutoCloseable mdcCloseable = putMDC(processId, pinstanceId))
		{
			userSession.assertLoggedIn();

			final IProcessInstancesRepository instancesRepository = getRepository(processId);
			final ProcessInstanceResult executionResult = instancesRepository.forProcessInstanceReadonly(pinstanceId, processInstance -> processInstance.getExecutionResult());

			final OpenReportAction action = executionResult.getAction(OpenReportAction.class);
			final String reportFilename = action.getFilename();
			final String reportContentType = action.getContentType();
			final byte[] reportData = action.getReportData();

			final String reportFilenameEffective = CoalesceUtil.coalesce(filename, reportFilename, "");

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType(reportContentType));
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + reportFilenameEffective + "\"");
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			final ResponseEntity<byte[]> response = new ResponseEntity<>(reportData, headers, HttpStatus.OK);
			return response;
		}
	}

	@GetMapping("/{processId}/{pinstanceId}/field/{parameterName}/typeahead")
	public JSONLookupValuesList getParameterTypeahead(
			@PathVariable("processId") final String processIdStr //
			, @PathVariable("pinstanceId") final String pinstanceIdStr //
			, @PathVariable("parameterName") final String parameterName //
			, @RequestParam(name = "query", required = true) final String query //
	)
	{
		final ProcessId processId = ProcessId.fromJson(processIdStr);
		final DocumentId pinstanceId = DocumentId.of(pinstanceIdStr);

		try (final IAutoCloseable mdcCloseable = putMDC(processId, pinstanceId))
		{
			userSession.assertLoggedIn();

			final IProcessInstancesRepository instancesRepository = getRepository(processId);

			return instancesRepository.forProcessInstanceReadonly(pinstanceId, processInstance -> processInstance.getParameterLookupValuesForQuery(parameterName, query))
					.transform(this::toJSONLookupValuesList);
		}
	}

	private JSONLookupValuesList toJSONLookupValuesList(final LookupValuesList lookupValuesList)
	{
		return JSONLookupValuesList.ofLookupValuesList(lookupValuesList, userSession.getAD_Language());
	}

	@GetMapping("/{processId}/{pinstanceId}/field/{parameterName}/dropdown")
	public JSONLookupValuesList getParameterDropdown(
			@PathVariable("processId") final String processIdStr //
			, @PathVariable("pinstanceId") final String pinstanceIdStr //
			, @PathVariable("parameterName") final String parameterName //
	)
	{
		final ProcessId processId = ProcessId.fromJson(processIdStr);
		final DocumentId pinstanceId = DocumentId.of(pinstanceIdStr);

		try (final IAutoCloseable mdcCloseable = putMDC(processId, pinstanceId))
		{
			userSession.assertLoggedIn();

			final IProcessInstancesRepository instancesRepository = getRepository(processId);

			return instancesRepository.forProcessInstanceReadonly(pinstanceId, processInstance -> processInstance.getParameterLookupValues(parameterName))
					.transform(this::toJSONLookupValuesList);
		}
	}

	public void cacheReset()
	{
		ProcessClassInfo.resetCache();
		WebuiProcessClassInfo.resetCache();

		getAllRepositories().forEach(IProcessInstancesRepository::cacheReset);
	}
}
