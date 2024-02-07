/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.ui.web.process;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.common.util.CoalesceUtil;
import de.metas.logging.LogManager;
import de.metas.monitoring.adapter.PerformanceMonitoringService;
import de.metas.monitoring.annotation.Monitor;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessClassInfo;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessMDC;
import de.metas.rest_api.utils.v2.JsonErrors;
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
import de.metas.ui.web.window.datatypes.json.JSONDocument;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentLayoutOptions;
import de.metas.ui.web.window.datatypes.json.JSONDocumentOptions;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesPage;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.datatypes.json.JsonProcessHealthResponse;
import de.metas.ui.web.window.exceptions.DocumentLayoutBuildException;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.NullDocumentChangesCollector;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.RepoIdAwares;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_AD_Process;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.core.io.Resource;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

/**
 * This is the rest controller used when processes are invoked from the <b>WebUI</b>.
 */
@Tag(name = "ProcessRestController")
@RestController
@RequestMapping(ProcessRestController.ENDPOINT)
public class ProcessRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/process";

	private static final Logger logger = LogManager.getLogger(ProcessRestController.class);
	private final ImmutableMap<ProcessHandlerType, IProcessInstancesRepository> pinstancesRepositoriesByHandlerType;
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
		return ProcessMDC.putProcessAndInstanceId(processId.toAdProcessIdOrNull(), pinstanceId);
	}

	private MDCCloseable putMDC(@NonNull final ProcessId processId)
	{
		return ProcessMDC.putAdProcessId(processId.toAdProcessIdOrNull());
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

	private IProcessInstancesRepository getRepository(@NonNull final ProcessId processId)
	{
		final ProcessHandlerType processHandlerType = processId.getProcessHandlerType();
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

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER)
	@GetMapping("/{processId}/layout")
	public ResponseEntity<JSONProcessLayout> getLayout(
			@PathVariable("processId") final String adProcessIdStr,
			final WebRequest request)
	{
		final ProcessId processId = ProcessId.fromJson(adProcessIdStr);

		try (final MDCCloseable ignored = putMDC(processId))
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

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER)
	@PostMapping("/{processId}")
	public JSONProcessInstance createInstanceFromRequest(
			@PathVariable("processId") final String processIdStr,
			@RequestBody final JSONCreateProcessInstanceRequest jsonRequest)
	{
		final ProcessId processId = ProcessId.fromJson(processIdStr);

		final DocumentId pinstanceId;
		try (final MDCCloseable ignored = putMDC(processId))
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
				if (viewId == null)
				{
					throw new AdempiereException("viewId is expected to be set");
				}
				final IView view = viewsRepo.getView(Check.assumeNotNull(viewId, "viewId shall not be null"));
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

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER)
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
		try (final IAutoCloseable ignored = putMDC(processId, pinstanceId))
		{
			userSession.assertLoggedIn();

			final IProcessInstancesRepository instancesRepository = getRepository(processId);

			return instancesRepository.forProcessInstanceReadonly(pinstanceId, processInstance -> JSONProcessInstance.of(processInstance, newJsonOptions()));
		}
	}

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER)
	@PatchMapping("/{processId}/{pinstanceId}")
	public List<JSONDocument> processParametersChangeEvents(
			@PathVariable("processId") final String processIdStr //
			, @PathVariable("pinstanceId") final String pinstanceIdStr //
			, @RequestBody final List<JSONDocumentChangedEvent> events //
	)
	{
		final ProcessId processId = ProcessId.fromJson(processIdStr);
		final DocumentId pinstanceId = DocumentId.of(pinstanceIdStr);

		try (final IAutoCloseable ignored = putMDC(processId, pinstanceId))
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

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER)
	@GetMapping(value = "/{processId}/{pinstanceId}/start")
	public JSONProcessInstanceResult startProcess(
			@PathVariable("processId") final String processIdStr,
			@PathVariable("pinstanceId") final String pinstanceIdStr)
	{
		final ProcessId processId = ProcessId.fromJson(processIdStr);
		final DocumentId pinstanceId = DocumentId.of(pinstanceIdStr);

		try (final IAutoCloseable ignored = putMDC(processId, pinstanceId))
		{
			userSession.assertLoggedIn();

			final IProcessInstancesRepository instancesRepository = getRepository(processId);

			return Execution.prepareNewExecution()
					.outOfTransaction()
					.execute(() -> instancesRepository.forProcessInstanceWritable(pinstanceId, NullDocumentChangesCollector.instance, processInstance -> {
						final ProcessInstanceResult result = processInstance.startProcess(ProcessExecutionContext.builder()
								.ctx(Env.getCtx())
								.adLanguage(userSession.getAD_Language())
								.viewsRepo(viewsRepo)
								.documentsCollection(documentsCollection)
								.build());
						return JSONProcessInstanceResult.of(result);
					}));
		}
	}

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER)
	@Operation(summary = "Retrieves and serves a report that was previously created by a reporting process.")
	@GetMapping("/{processId}/{pinstanceId}/print/{filename:.*}")
	public ResponseEntity<Resource> getReport(
			@PathVariable("processId") final String processIdStr,
			@PathVariable("pinstanceId") final String pinstanceIdStr,
			@PathVariable("filename") final String filename)
	{
		final ProcessId processId = ProcessId.fromJson(processIdStr);
		final DocumentId pinstanceId = DocumentId.of(pinstanceIdStr);

		try (final IAutoCloseable ignored = putMDC(processId, pinstanceId))
		{
			userSession.assertLoggedIn();

			final IProcessInstancesRepository instancesRepository = getRepository(processId);
			final ProcessInstanceResult executionResult = instancesRepository.forProcessInstanceReadonly(pinstanceId, IProcessInstanceController::getExecutionResult);

			final OpenReportAction action = executionResult.getAction(OpenReportAction.class);
			final String reportFilename = action.getFilename();
			final String reportContentType = action.getContentType();
			final Resource reportData = action.getReportData();

			final String reportFilenameEffective = CoalesceUtil.coalesce(filename, reportFilename, "");

			final HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.parseMediaType(reportContentType));
			headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + reportFilenameEffective + "\"");
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

			return new ResponseEntity<>(reportData, headers, HttpStatus.OK);
		}
	}

	@Monitor(type = PerformanceMonitoringService.Type.REST_CONTROLLER)
	@GetMapping("/{processId}/{pinstanceId}/field/{parameterName}/typeahead")
	public JSONLookupValuesPage getParameterTypeahead(
			@PathVariable("processId") final String processIdStr //
			, @PathVariable("pinstanceId") final String pinstanceIdStr //
			, @PathVariable("parameterName") final String parameterName //
			, @RequestParam(name = "query") final String query //
	)
	{
		final ProcessId processId = ProcessId.fromJson(processIdStr);
		final DocumentId pinstanceId = DocumentId.of(pinstanceIdStr);

		try (final IAutoCloseable ignored = putMDC(processId, pinstanceId))
		{
			userSession.assertLoggedIn();

			final IProcessInstancesRepository instancesRepository = getRepository(processId);

			return instancesRepository.forProcessInstanceReadonly(pinstanceId, processInstance -> processInstance.getParameterLookupValuesForQuery(parameterName, query))
					.transform(page -> JSONLookupValuesPage.of(page, userSession.getAD_Language()));
		}
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

		try (final IAutoCloseable ignored = putMDC(processId, pinstanceId))
		{
			userSession.assertLoggedIn();

			final IProcessInstancesRepository instancesRepository = getRepository(processId);

			return instancesRepository.forProcessInstanceReadonly(pinstanceId, processInstance -> processInstance.getParameterLookupValues(parameterName))
					.transform(list -> JSONLookupValuesList.ofLookupValuesList(list, userSession.getAD_Language()));
		}
	}

	public void cacheReset()
	{
		ProcessClassInfo.resetCache();
		WebuiProcessClassInfo.resetCache();

		getAllRepositories().forEach(IProcessInstancesRepository::cacheReset);
	}

	@GetMapping("/health")
	public JsonProcessHealthResponse healthCheck(
			@RequestParam(name = "adProcessIds", required = false) final String onlyAdProcesIdsCommaSeparated
	)
	{
		final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

		final ImmutableSet<AdProcessId> onlyAdProcessIds = RepoIdAwares.ofCommaSeparatedSet(onlyAdProcesIdsCommaSeparated, AdProcessId.class);
		final ImmutableSet<AdProcessId> allAdProcessIds = adProcessDAO.retrieveAllActiveAdProcesIds();
		final ImmutableSet<AdProcessId> adProcesIds = !onlyAdProcessIds.isEmpty() ? onlyAdProcessIds : allAdProcessIds;

		final ArrayList<JsonProcessHealthResponse.Entry> errors = new ArrayList<>();
		final int countTotal = adProcesIds.size();
		int countCurrent = 0;
		final Stopwatch stopwatch = Stopwatch.createStarted();
		for (final AdProcessId adProcessId : adProcesIds)
		{
			countCurrent++;

			final ProcessId processId = ProcessId.ofAD_Process_ID(adProcessId);
			try
			{
				if (!allAdProcessIds.contains(adProcessId))
				{
					throw new AdempiereException("Not an existing/active process");
				}

				final IProcessInstancesRepository repository = getRepository(processId);
				repository.cacheReset();

				final ProcessDescriptor processDescriptor = repository.getProcessDescriptor(processId);

				// Try loading & instantiating the process class if any
				if (processDescriptor.getProcessClassname() != null)
				{
					ProcessInfo.newProcessClassInstance(processDescriptor.getProcessClassname());
				}

				repository.cacheReset();

				logger.info("healthCheck [{}/{}] Process {} is OK", countCurrent, countTotal, processId);
			}
			catch (final Exception ex)
			{
				final String adLanguage = Env.getADLanguageOrBaseLanguage();
				final I_AD_Process adProcess = adProcessDAO.getById(adProcessId);
				final String processValue = adProcess != null ? adProcess.getValue() : "?";
				final String processName = adProcess != null ? adProcess.getName() : "?";
				final String processClassname = adProcess != null ? adProcess.getClassname() : "?";
				logger.info("healthCheck [{}/{}] Process {}_{} ({}) is NOK: {}", countCurrent, countTotal, processValue, processName, processId, ex.getLocalizedMessage());

				final Throwable cause = DocumentLayoutBuildException.extractCause(ex);
				errors.add(JsonProcessHealthResponse.Entry.builder()
						.processId(processId)
						.value(processValue)
						.name(processName)
						.classname(processClassname)
						.error(JsonErrors.ofThrowable(cause, adLanguage))
						.build());
			}
		}

		stopwatch.stop();

		return JsonProcessHealthResponse.builder()
				.countTotal(countTotal)
				.countErrors(errors.size())
				.took(stopwatch.toString())
				.errors(errors)
				.build();
	}
}
