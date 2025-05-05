package de.metas.ui.web.process.adprocess;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.ImmutableSet;
import de.metas.printing.esb.base.util.Check;
import de.metas.process.ADProcessService;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessDefaultParametersUpdater;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfo.ProcessInfoBuilder;
import de.metas.security.IUserRolePermissions;
import de.metas.ui.web.process.CreateProcessInstanceRequest;
import de.metas.ui.web.process.IProcessInstanceController;
import de.metas.ui.web.process.IProcessInstancesRepository;
import de.metas.ui.web.process.ProcessHandlerType;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.process.WebuiPreconditionsContext;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRowIdsSelection;
import de.metas.ui.web.view.descriptor.SqlViewRowsWhereClause;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.LookupDescriptorProviders;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.DocumentFieldLogicExpressionResultRevaluator;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentEvaluatee;
import de.metas.ui.web.window.model.NullDocumentChangesCollector;
import de.metas.ui.web.window.model.sql.SqlOptions;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Process;
import org.compiere.util.Env;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Stream;

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

/**
 * {@link IProcessInstancesRepository} implementation for metasfresh {@link I_AD_Process}s.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Component
public class ADProcessInstancesRepository implements IProcessInstancesRepository
{
	//
	// Services
	private final UserSession userSession;
	private final DocumentDescriptorFactory documentDescriptorFactory;
	private final IViewsRepository viewsRepo;
	private final DocumentCollection documentsCollection;
	private final ADProcessDescriptorsFactory processDescriptorFactory;

	private final Cache<DocumentId, ADProcessInstanceController> processInstances = CacheBuilder.newBuilder()
			.expireAfterAccess(10, TimeUnit.MINUTES)
			.build();

	public ADProcessInstancesRepository(
			@NonNull final UserSession userSession,
			@NonNull final DocumentDescriptorFactory documentDescriptorFactory,
			@NonNull final IViewsRepository viewsRepo,
			@NonNull final DocumentCollection documentsCollection,
			@NonNull final ADProcessService adProcessService,
			@NonNull final LookupDescriptorProviders lookupDescriptorProviders)
	{
		this.userSession = userSession;
		this.documentDescriptorFactory = documentDescriptorFactory;
		this.viewsRepo = viewsRepo;
		this.documentsCollection = documentsCollection;
		this.processDescriptorFactory = new ADProcessDescriptorsFactory(adProcessService, lookupDescriptorProviders);
	}

	@Override
	public ProcessHandlerType getProcessHandlerType()
	{
		return ProcessId.PROCESSHANDLERTYPE_AD_Process;
	}

	@Override
	public void cacheReset()
	{
		processInstances.invalidateAll();
		processInstances.cleanUp();
	}

	@Override
	public ProcessDescriptor getProcessDescriptor(final ProcessId processId)
	{
		return processDescriptorFactory.getProcessDescriptor(processId);
	}

	@Override
	public Stream<WebuiRelatedProcessDescriptor> streamDocumentRelatedProcesses(final WebuiPreconditionsContext preconditionsContext)
	{
		final IUserRolePermissions userRolePermissions = userSession.getUserRolePermissions();
		return processDescriptorFactory.streamDocumentRelatedProcesses(preconditionsContext, userRolePermissions);
	}

	@Override
	public IProcessInstanceController createNewProcessInstance(final CreateProcessInstanceRequest request)
	{
		if (documentsCollection.isValidDocumentPath(request.getSingleDocumentPath()))
		{
			// In case we have a single document path, we shall fetch it as use it as evaluation context.
			// This will make sure that the parameter's default values will be correctly computed
			return documentsCollection.forDocumentReadonly(request.getSingleDocumentPath(), document -> createNewProcessInstance0(request, document.asEvaluatee()));
		}
		else
		{
			return createNewProcessInstance0(request, null);
		}
	}

	private IProcessInstanceController createNewProcessInstance0(
			@NonNull final CreateProcessInstanceRequest request,
			@Nullable final IDocumentEvaluatee evalCtx)
	{
		//
		// Save process info together with it's parameters and get the the newly created AD_PInstance_ID
		final ProcessInfo processInfo = createProcessInfo(request);
		Services.get(IADPInstanceDAO.class).saveProcessInfo(processInfo);
		final DocumentId adPInstanceId = DocumentId.of(processInfo.getPinstanceId());

		final Object processClassInstance = processInfo.newProcessClassInstance();

		try (final IAutoCloseable ignored = JavaProcess.temporaryChangeCurrentInstance(processClassInstance))
		{
			//
			// Build the parameters document
			final ProcessDescriptor processDescriptor = getProcessDescriptor(request.getProcessId());
			final DocumentEntityDescriptor parametersDescriptor = processDescriptor.getParametersDescriptor();
			final Document parametersDoc = ADProcessParametersRepository.instance.createNewParametersDocument(parametersDescriptor, adPInstanceId, evalCtx);
			final int windowNo = parametersDoc.getWindowNo();

			// Set parameter's default values
			ProcessDefaultParametersUpdater.newInstance()
					.addDefaultParametersProvider(processClassInstance instanceof IProcessDefaultParametersProvider ? (IProcessDefaultParametersProvider)processClassInstance : null)
					.onDefaultValue((parameter, value) -> parametersDoc.processValueChange(
							parameter.getColumnName(),
							value,
							() -> "default parameter value",
							DocumentFieldLogicExpressionResultRevaluator.ALWAYS_RETURN_FALSE
					))
					.updateDefaultValue(parametersDoc.getFieldViews(), field -> DocumentFieldAsProcessDefaultParameter.of(windowNo, field));

			//
			// Create (webui) process instance and add it to our internal cache.
			final ADProcessInstanceController pinstance = ADProcessInstanceController.builder()
					.caption(processDescriptor.getCaption())
					.instanceId(adPInstanceId)
					.parameters(parametersDoc)
					.processClassInstance(processClassInstance)
					.startProcessDirectly(processDescriptor.isStartProcessDirectly())
					.contextSingleDocumentPath(request.getSingleDocumentPath())
					.viewId(request.getViewRowIdsSelection() != null ? request.getViewRowIdsSelection().getViewId() : null)
					.build();
			processInstances.put(adPInstanceId, pinstance.copyReadonly());
			return pinstance;
		}
	}

	private ProcessInfo createProcessInfo(@NonNull final CreateProcessInstanceRequest request)
	{
		final DocumentPath singleDocumentPath = request.getSingleDocumentPath();
		final String tableName;
		final int recordId;
		final String sqlWhereClause;
		final AdWindowId adWindowId;

		//
		// View
		if (request.getViewRowIdsSelection() != null)
		{
			final ViewRowIdsSelection viewRowIdsSelection = request.getViewRowIdsSelection();
			final ViewId viewId = viewRowIdsSelection.getViewId();
			final IView view = viewsRepo.getView(viewId);
			final DocumentIdsSelection viewDocumentIds = viewRowIdsSelection.getRowIds();

			adWindowId = viewId.getWindowId().toAdWindowIdOrNull();

			if (viewDocumentIds.isSingleDocumentId())
			{
				final DocumentId viewSingleDocumentId = viewDocumentIds.getSingleDocumentId();
				final TableRecordReference recordRef = view.getTableRecordReferenceOrNull(viewSingleDocumentId);
				if (recordRef != null)
				{
					tableName = recordRef.getTableName();
					recordId = recordRef.getRecord_ID();
				}
				else
				{
					tableName = view.getTableNameOrNull(viewSingleDocumentId);
					recordId = -1;
				}
			}
			else
			{
				tableName = view.getTableNameOrNull(null);
				recordId = -1;
			}

			final boolean emptyTableName = Check.isEmpty(tableName);
			if (viewDocumentIds.isEmpty() || emptyTableName)
			{
				// Note: in the case of material cockpit, there is no single tableName to be returned by view.getTableNameOrNull,
				// so we do have selected rows, but no table name, which is OK
				sqlWhereClause = null;
			}
			else
			{
				final SqlViewRowsWhereClause viewRowsWhereClause = view.getSqlWhereClause(viewDocumentIds, SqlOptions.usingTableName(tableName));
				sqlWhereClause = viewRowsWhereClause != null ? viewRowsWhereClause.toSqlString() : null;
			}
		}
		//
		// Single document call
		else if (singleDocumentPath != null)
		{
			final DocumentEntityDescriptor entityDescriptor = documentDescriptorFactory.getDocumentEntityDescriptor(singleDocumentPath);

			adWindowId = singleDocumentPath.getWindowId().toAdWindowIdOrNull();

			tableName = entityDescriptor.getTableNameOrNull();
			final DocumentId documentId;
			if (singleDocumentPath.isRootDocument())
			{
				documentId = singleDocumentPath.getDocumentId();
			}
			else
			{
				documentId = singleDocumentPath.getSingleRowId();
			}

			if (documentId.isInt())
			{
				recordId = documentId.toInt();
			}
			else
			{
				recordId = -1;
			}

			sqlWhereClause = entityDescriptor
					.getDataBinding(SqlDocumentEntityDataBindingDescriptor.class)
					.getSqlWhereClauseById(documentId);
		}
		//
		// From menu
		else
		{
			tableName = null;
			recordId = -1;
			sqlWhereClause = null;
			adWindowId = null;
		}

		//
		final Set<TableRecordReference> selectedIncludedRecords = extractSelectedIncludedRecords(request);

		final ProcessInfoBuilder processInfoBuilder = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setCreateTemporaryCtx()
				.setAD_Process_ID(request.getProcessIdAsInt())
				.setAdWindowId(adWindowId)
				.setRecord(tableName, recordId)
				.setSelectedIncludedRecords(selectedIncludedRecords)
				.setWhereClause(sqlWhereClause);

		//
		// View related internal parameters
		addViewInternalParameters(request, processInfoBuilder);

		return processInfoBuilder.build();
	}

	private ImmutableSet<TableRecordReference> extractSelectedIncludedRecords(final @NonNull CreateProcessInstanceRequest request)
	{
		return request.getSelectedIncludedDocumentPaths()
				.stream()
				.filter(documentPath -> documentPath.getSingleRowId().isInt()) // only Int rowIds are convertible to TableRecordReference
				.map(documentDescriptorFactory::getTableRecordReference)
				.collect(ImmutableSet.toImmutableSet());
	}

	@VisibleForTesting
	protected static void addViewInternalParameters(
			@NonNull final CreateProcessInstanceRequest request,
			@NonNull final ProcessInfoBuilder processInfoBuilder)
	{
		if (request.getViewRowIdsSelection() != null)
		{
			final ViewRowIdsSelection viewRowIdsSelection = request.getViewRowIdsSelection();
			processInfoBuilder
					.setLoadParametersFromDB(true) // important: we need to load the existing parameters from database, besides the internal ones we are adding here
					.addParameter(ViewBasedProcessTemplate.PARAM_ViewId, viewRowIdsSelection.getViewId().toJson())
					.addParameter(ViewBasedProcessTemplate.PARAM_ViewSelectedIds, viewRowIdsSelection.getRowIds().toCommaSeparatedString());
		}
		if (request.getParentViewRowIdsSelection() != null)
		{
			final ViewRowIdsSelection parentViewRowIdsSelection = request.getParentViewRowIdsSelection();
			processInfoBuilder
					.setLoadParametersFromDB(true) // important: we need to load the existing parameters from database, besides the internal ones we are adding here
					.addParameter(ViewBasedProcessTemplate.PARAM_ParentViewId, parentViewRowIdsSelection.getViewId().toJson())
					.addParameter(ViewBasedProcessTemplate.PARAM_ParentViewSelectedIds, parentViewRowIdsSelection.getRowIds().toCommaSeparatedString());

		}
		if (request.getChildViewRowIdsSelection() != null)
		{
			final ViewRowIdsSelection childViewRowIdsSelection = request.getChildViewRowIdsSelection();
			processInfoBuilder
					.setLoadParametersFromDB(true) // important: we need to load the existing parameters from database, besides the internal ones we are adding here
					.addParameter(ViewBasedProcessTemplate.PARAM_ChildViewId, childViewRowIdsSelection.getViewId().toJson())
					.addParameter(ViewBasedProcessTemplate.PARAM_ChildViewSelectedIds, childViewRowIdsSelection.getRowIds().toCommaSeparatedString());
		}
	}

	private ADProcessInstanceController retrieveProcessInstance(final DocumentId adPInstanceDocumentId)
	{
		Check.assumeNotNull(adPInstanceDocumentId, "Parameter adPInstanceDocumentId is not null");

		//
		// Load process info
		final PInstanceId pinstanceId = PInstanceId.ofRepoId(adPInstanceDocumentId.toInt());
		final ProcessInfo processInfo = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setCreateTemporaryCtx()
				.setPInstanceId(pinstanceId)
				.build();

		final Object processClassInstance = processInfo.newProcessClassInstanceOrNull();
		if (processClassInstance == null)
		{
			throw new AdempiereException("No processClassInstance found: " + processInfo);
		}

		try (final IAutoCloseable ignored = JavaProcess.temporaryChangeCurrentInstance(processClassInstance))
		{
			//
			// Build the parameters document
			final ProcessId processId = ProcessId.ofAD_Process_ID(processInfo.getAdProcessId());
			final ProcessDescriptor processDescriptor = getProcessDescriptor(processId);

			//
			// Build the parameters (as document)
			final DocumentEntityDescriptor parametersDescriptor = processDescriptor.getParametersDescriptor();
			final Document parametersDoc = parametersDescriptor
					.getDataBinding()
					.getDocumentsRepository()
					.retrieveDocumentById(parametersDescriptor, adPInstanceDocumentId, NullDocumentChangesCollector.instance);

			// TODO: handle the case when the process was already executed
			// In that case we need to load the result and provide it to ProcessInstance constructor

			//
			// View information
			final IRangeAwareParams processInfoParams = processInfo.getParameterAsIParams();
			final String viewIdStr = processInfoParams.getParameterAsString(ViewBasedProcessTemplate.PARAM_ViewId);
			final ViewId viewId = Strings.isNullOrEmpty(viewIdStr) ? null : ViewId.ofViewIdString(viewIdStr);

			//
			return ADProcessInstanceController.builder()
					.caption(processDescriptor.getCaption())
					.instanceId(adPInstanceDocumentId)
					.parameters(parametersDoc)
					.processClassInstance(processClassInstance)
					.startProcessDirectly(processDescriptor.isStartProcessDirectly())
					.viewId(viewId)
					.build();
		}
	}

	@Override
	public <R> R forProcessInstanceReadonly(final DocumentId pinstanceId, final Function<IProcessInstanceController, R> processor)
	{
		try (final IAutoCloseable ignored = getOrLoad(pinstanceId).lockForReading())
		{
			final ADProcessInstanceController processInstance = getOrLoad(pinstanceId)
					.copyReadonly()
					.bindContextSingleDocumentIfPossible(documentsCollection);

			try (final IAutoCloseable ignored1 = processInstance.activate())
			{
				return processor.apply(processInstance);
			}
		}
	}

	private ADProcessInstanceController getOrLoad(final DocumentId pinstanceId)
	{
		try
		{
			return processInstances.get(pinstanceId, () -> retrieveProcessInstance(pinstanceId));
		}
		catch (final ExecutionException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@Override
	public <R> R forProcessInstanceWritable(final DocumentId pinstanceId, final IDocumentChangesCollector changesCollector, final Function<IProcessInstanceController, R> processor)
	{
		try (final IAutoCloseable ignored = getOrLoad(pinstanceId).lockForWriting())
		{
			final ADProcessInstanceController processInstance = getOrLoad(pinstanceId)
					.copyReadWrite(changesCollector)
					.bindContextSingleDocumentIfPossible(documentsCollection);

			// Make sure the process was not already executed.
			// If it was executed we are not allowed to change it.
			processInstance.assertNotExecuted();

			try (final IAutoCloseable ignored1 = processInstance.activate())
			{
				// Call the given processor to apply changes to this process instance.
				final R result = processor.apply(processInstance);

				// Actually put it back
				processInstance.saveIfValidAndHasChanges(false); // throwEx=false
				processInstances.put(pinstanceId, processInstance.copyReadonly());

				return result;
			}
		}
	}


	public void addProcessParameters(final ProcessId processId, final DocumentEntityDescriptor.Builder parametersDescriptorBuilder)
	{
		processDescriptorFactory.addProcessParameters(processId, parametersDescriptorBuilder);
	}
}
