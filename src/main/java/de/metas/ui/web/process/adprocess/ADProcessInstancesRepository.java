package de.metas.ui.web.process.adprocess;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Process;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import de.metas.printing.esb.base.util.Check;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessDefaultParametersUpdater;
import de.metas.process.ProcessInfo;
import de.metas.ui.web.process.CreateProcessInstanceRequest;
import de.metas.ui.web.process.IProcessInstanceController;
import de.metas.ui.web.process.IProcessInstancesRepository;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.process.WebuiPreconditionsContext;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.factory.DocumentDescriptorFactory;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentEvaluatee;
import de.metas.ui.web.window.model.NullDocumentChangesCollector;
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
	@Autowired
	private UserSession userSession;
	@Autowired
	private DocumentDescriptorFactory documentDescriptorFactory;
	@Autowired
	private IViewsRepository viewsRepo;
	@Autowired
	private DocumentCollection documentsCollection;
	//
	private final ADProcessDescriptorsFactory processDescriptorFactory = new ADProcessDescriptorsFactory();

	private final Cache<DocumentId, ADProcessInstanceController> processInstances = CacheBuilder.newBuilder()
			.expireAfterAccess(10, TimeUnit.MINUTES)
			.build();

	@Override
	public String getProcessHandlerType()
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
	public IProcessInstanceController createNewProcessInstance(final CreateProcessInstanceRequest request, final IDocumentChangesCollector changesCollector)
	{
		if (documentsCollection.isValidDocumentPath(request.getSingleDocumentPath()))
		{
			// In case we have a single document path, we shall fetch it as use it as evaluation context.
			// This will make sure that the parameter's default values will be correctly computed
			return documentsCollection.forDocumentReadonly(request.getSingleDocumentPath(), changesCollector, document -> createNewProcessInstance0(request, document.asEvaluatee(), changesCollector));
		}
		else
		{
			final IDocumentEvaluatee shadowParentDocumentEvaluatee = null; // N/A
			return createNewProcessInstance0(request, shadowParentDocumentEvaluatee, changesCollector);
		}
	}

	/**
	 *
	 * @param request
	 * @param shadowParentDocumentEvaluatee optional shadowParentDocumentEvaluatee which will be
	 * @return
	 */
	private IProcessInstanceController createNewProcessInstance0(
			@NonNull final CreateProcessInstanceRequest request,
			@Nullable final IDocumentEvaluatee evalCtx,
			final IDocumentChangesCollector changesCollector)
	{
		//
		// Save process info together with it's parameters and get the the newly created AD_PInstance_ID
		final ProcessInfo processInfo = createProcessInfo(request);
		Services.get(IADPInstanceDAO.class).saveProcessInfo(processInfo);
		final DocumentId adPInstanceId = DocumentId.of(processInfo.getAD_PInstance_ID());

		final Object processClassInstance = processInfo.newProcessClassInstanceOrNull();
		try (final IAutoCloseable c = JavaProcess.temporaryChangeCurrentInstance(processClassInstance))
		{
			//
			// Build the parameters document
			final ProcessDescriptor processDescriptor = getProcessDescriptor(request.getProcessId());
			final DocumentEntityDescriptor parametersDescriptor = processDescriptor.getParametersDescriptor();
			final Document parametersDoc = ADProcessParametersRepository.instance.createNewParametersDocument(parametersDescriptor, adPInstanceId, evalCtx, changesCollector);
			final int windowNo = parametersDoc.getWindowNo();

			// Set parameters's default values
			ProcessDefaultParametersUpdater.newInstance()
					.addDefaultParametersProvider(processClassInstance instanceof IProcessDefaultParametersProvider ? (IProcessDefaultParametersProvider)processClassInstance : null)
					.onDefaultValue((parameter, value) -> parametersDoc.processValueChange(parameter.getColumnName(), value, () -> "default parameter value"))
					.updateDefaultValue(parametersDoc.getFieldViews(), field -> DocumentFieldAsProcessDefaultParameter.of(windowNo, field));

			//
			// Create (webui) process instance and add it to our internal cache.
			final ADProcessInstanceController pinstance = ADProcessInstanceController.builder()
					.setProcessDescriptor(processDescriptor)
					.setInstanceId(adPInstanceId)
					.setParameters(parametersDoc)
					.setViewsRepo(viewsRepo)
					.setView(request.getViewId(), request.getViewDocumentIds())
					.setProcessClassInstance(processClassInstance)
					.build();
			processInstances.put(adPInstanceId, pinstance.copy(CopyMode.CheckInReadonly, NullDocumentChangesCollector.instance));
			return pinstance;
		}
	}

	private ProcessInfo createProcessInfo(@NonNull final CreateProcessInstanceRequest request)
	{
		final String tableName;
		final int recordId;
		final String sqlWhereClause;

		//
		// View
		final ViewId viewId = request.getViewId();
		final String viewSelectedIdsAsStr;
		final DocumentPath singleDocumentPath = request.getSingleDocumentPath();
		if (viewId != null)
		{
			final IView view = viewsRepo.getView(viewId);
			final DocumentIdsSelection viewDocumentIds = request.getViewDocumentIds();
			viewSelectedIdsAsStr = viewDocumentIds.toCommaSeparatedString();

			if (viewDocumentIds.isSingleDocumentId())
			{
				final DocumentId viewSingleDocumentId = viewDocumentIds.getSingleDocumentId();
				final TableRecordReference recordRef = view.getTableRecordReferenceOrNull(viewSingleDocumentId);
				if(recordRef != null)
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

			sqlWhereClause = viewDocumentIds.isEmpty() ? null : view.getSqlWhereClause(viewDocumentIds);
		}
		//
		// Single document call
		else if (singleDocumentPath != null)
		{
			viewSelectedIdsAsStr = null;

			final DocumentEntityDescriptor entityDescriptor = documentDescriptorFactory.getDocumentEntityDescriptor(singleDocumentPath);
			tableName = entityDescriptor.getTableNameOrNull();
			if (singleDocumentPath.isRootDocument())
			{
				recordId = singleDocumentPath.getDocumentId().toInt();
			}
			else
			{
				recordId = singleDocumentPath.getSingleRowId().toInt();
			}
			sqlWhereClause = entityDescriptor
					.getDataBinding(SqlDocumentEntityDataBindingDescriptor.class)
					.getSqlWhereClauseById(recordId);
		}
		//
		// From menu
		else
		{
			viewSelectedIdsAsStr = null;
			tableName = null;
			recordId = -1;
			sqlWhereClause = null;
		}

		return ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setCreateTemporaryCtx()
				.setAD_Process_ID(request.getProcessIdAsInt())
				.setRecord(tableName, recordId)
				.setWhereClause(sqlWhereClause)
				//
				.setLoadParametersFromDB(true) // important: we need to load the existing parameters from database, besides the internal ones we are adding here
				.addParameter(ViewBasedProcessTemplate.PARAM_ViewWindowId, viewId == null ? null : viewId.getWindowId().toJson()) // internal parameter
				.addParameter(ViewBasedProcessTemplate.PARAM_ViewId, viewId == null ? null : viewId.getViewId()) // internal parameter
				.addParameter(ViewBasedProcessTemplate.PARAM_ViewSelectedIds, viewSelectedIdsAsStr) // internal parameter
				//
				.build();
	}

	private ADProcessInstanceController retrieveProcessInstance(final DocumentId adPInstanceId)
	{
		Check.assumeNotNull(adPInstanceId, "Parameter adPInstanceId is not null");
		Check.assume(adPInstanceId.toInt() > 0, "adPInstanceId > 0");

		//
		// Load process info
		final ProcessInfo processInfo = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setCreateTemporaryCtx()
				.setAD_PInstance_ID(adPInstanceId.toInt())
				.build();

		final Object processClassInstance = processInfo.newProcessClassInstanceOrNull();
		try (final IAutoCloseable c = JavaProcess.temporaryChangeCurrentInstance(processClassInstance))
		{
			//
			// Build the parameters document
			final ProcessId processId = ProcessId.ofAD_Process_ID(processInfo.getAD_Process_ID());
			final ProcessDescriptor processDescriptor = getProcessDescriptor(processId);

			//
			// Build the parameters (as document)
			final DocumentEntityDescriptor parametersDescriptor = processDescriptor.getParametersDescriptor();
			final Document parametersDoc = parametersDescriptor
					.getDataBinding()
					.getDocumentsRepository()
					.retrieveDocumentById(parametersDescriptor, adPInstanceId, NullDocumentChangesCollector.instance);

			// TODO: handle the case when the process was already executed
			// In that case we need to load the result and provide it to ProcessInstance constructor

			//
			// View informations
			final IRangeAwareParams processInfoParams = processInfo.getParameterAsIParams();
			final String viewWindowId = processInfoParams.getParameterAsString(ViewBasedProcessTemplate.PARAM_ViewWindowId);
			final String viewIdStr = processInfoParams.getParameterAsString(ViewBasedProcessTemplate.PARAM_ViewId);
			final ViewId viewId = Strings.isNullOrEmpty(viewIdStr) ? null : ViewId.of(viewWindowId, viewIdStr);
			//
			final String viewSelectedIdsStr = processInfoParams.getParameterAsString(ViewBasedProcessTemplate.PARAM_ViewSelectedIds);
			final DocumentIdsSelection viewSelectedIds = DocumentIdsSelection.ofCommaSeparatedString(viewSelectedIdsStr);

			//
			return ADProcessInstanceController.builder()
					.setProcessDescriptor(processDescriptor)
					.setInstanceId(adPInstanceId)
					.setParameters(parametersDoc)
					.setViewsRepo(viewsRepo)
					.setView(viewId, viewSelectedIds)
					.setProcessClassInstance(processClassInstance)
					.build();
		}
	}

	@Override
	public <R> R forProcessInstanceReadonly(final DocumentId pinstanceId, final Function<IProcessInstanceController, R> processor)
	{
		try (final IAutoCloseable readLock = getOrLoad(pinstanceId).lockForReading())
		{
			final ADProcessInstanceController processInstance = getOrLoad(pinstanceId);
			try (final IAutoCloseable c = processInstance.activate())
			{
				return processor.apply(processInstance);
			}
		}
	}

	private final ADProcessInstanceController getOrLoad(final DocumentId pinstanceId)
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
		try (final IAutoCloseable writeLock = getOrLoad(pinstanceId).lockForWriting())
		{
			final ADProcessInstanceController processInstance = getOrLoad(pinstanceId).copy(CopyMode.CheckOutWritable, changesCollector);

			// Make sure the process was not already executed.
			// If it was executed we are not allowed to change it.
			processInstance.assertNotExecuted();

			try (final IAutoCloseable c = processInstance.activate())
			{
				// Call the given processor to apply changes to this process instance.
				final R result = processor.apply(processInstance);

				// Actually put it back
				processInstance.saveIfValidAndHasChanges(false); // throwEx=false
				processInstances.put(pinstanceId, processInstance.copy(CopyMode.CheckInReadonly, NullDocumentChangesCollector.instance));

				return result;
			}
		}
	}
}
