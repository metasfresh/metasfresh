package de.metas.ui.web.process;

import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.util.concurrent.UncheckedExecutionException;

import de.metas.printing.esb.base.util.Check;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.ProcessDefaultParametersUpdater;
import de.metas.process.ProcessInfo;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.process.descriptor.ProcessDescriptorsFactory;
import de.metas.ui.web.process.descriptor.ProcessParametersRepository;
import de.metas.ui.web.process.json.JSONCreateProcessInstanceRequest;
import de.metas.ui.web.view.IDocumentViewSelection;
import de.metas.ui.web.view.IDocumentViewsRepository;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.DocumentCollection;

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

@Component
public class ProcessInstancesRepository
{
	//
	// Services
	@Autowired
	private ProcessDescriptorsFactory processDescriptorFactory;
	@Autowired
	private DocumentCollection documentsCollection;
	@Autowired
	private IDocumentViewsRepository documentViewsRepo;


	private final LoadingCache<DocumentId, ProcessInstance> processInstances = CacheBuilder.newBuilder()
			.expireAfterAccess(10, TimeUnit.MINUTES)
			.removalListener(new RemovalListener<DocumentId, ProcessInstance>()
			{
				@Override
				public void onRemoval(final RemovalNotification<DocumentId, ProcessInstance> notification)
				{
					final ProcessInstance pinstance = notification.getValue();
					pinstance.destroy();
				}
			})
			.build(CacheLoader.from(adPInstanceId -> retrieveProcessInstance(adPInstanceId)));

	public void cacheReset()
	{
		processInstances.invalidateAll();
		processInstances.cleanUp();
	}

	public ProcessDescriptor getProcessDescriptor(final int adProcessId)
	{
		return processDescriptorFactory.getProcessDescriptor(adProcessId);
	}

	public ProcessInstance createNewProcessInstance(final int adProcessId, final JSONCreateProcessInstanceRequest request)
	{
		//
		// Save process info together with it's parameters and get the the newly created AD_PInstance_ID
		final ProcessInfo processInfo = createProcessInfo(adProcessId, request);
		Services.get(IADPInstanceDAO.class).saveProcessInfo(processInfo);
		final DocumentId adPInstanceId = DocumentId.of(processInfo.getAD_PInstance_ID());

		//
		// Build the parameters document
		final ProcessDescriptor processDescriptor = getProcessDescriptor(adProcessId);
		final DocumentEntityDescriptor parametersDescriptor = processDescriptor.getParametersDescriptor();
		final Document parametersDoc = ProcessParametersRepository.instance.createNewParametersDocument(parametersDescriptor, adPInstanceId);

		// Set parameters's default values
		ProcessDefaultParametersUpdater.newInstance()
				.addDefaultParametersProvider(processInfo)
				.onDefaultValue((parameter, value) -> parametersDoc.processValueChange(parameter.getColumnName(), value, () -> "default parameter value"))
				.updateDefaultValue(parametersDoc.getFieldViews(), DocumentFieldAsProcessDefaultParameter::of);

		//
		// Create (webui) process instance and add it to our internal cache.
		final ProcessInstance pinstance = new ProcessInstance(processDescriptor, adPInstanceId, parametersDoc);
		processInstances.put(adPInstanceId, pinstance.copy(CopyMode.CheckInReadonly));
		return pinstance;
	}
	
	private ProcessInfo createProcessInfo(final int adProcessId, final JSONCreateProcessInstanceRequest request)
	{
		// Validate request's AD_Process_ID
		// (we are not using it, but just for consistency)
		if (request.getAD_Process_ID() > 0 && request.getAD_Process_ID() != adProcessId)
		{
			throw new IllegalArgumentException("Request's AD_Process_ID is not valid. It shall be " + adProcessId + " or none but it was " + request.getAD_Process_ID());
		}

		Check.assume(adProcessId > 0, "adProcessId > 0");

		//
		// Extract process where clause from view, in case the process was called from a view.
		final String sqlWhereClause;
		final String viewId = Strings.emptyToNull(request.getViewId());
		int view_AD_Window_ID = -1;
		DocumentId view_DocumentId = null;
		if (!Check.isEmpty(viewId))
		{
			final IDocumentViewSelection view = documentViewsRepo.getView(viewId);
			final Set<DocumentId> viewDocumentIds = request.getViewDocumentIds();
			sqlWhereClause = view.getSqlWhereClause(viewDocumentIds);

			if (viewDocumentIds.size() == 1)
			{
				view_AD_Window_ID = view.getAD_Window_ID();
				view_DocumentId = viewDocumentIds.iterator().next();
			}
		}
		else
		{
			sqlWhereClause = null;
		}

		//
		// Extract the (single) referenced document
		final TableRecordReference documentRef;
		if (request.getAD_Window_ID() > 0 && !Check.isEmpty(request.getDocumentId()))
		{
			final DocumentPath documentPath = DocumentPath.rootDocumentPath(DocumentType.Window, request.getAD_Window_ID(), request.getDocumentId());
			documentRef = documentsCollection.getTableRecordReference(documentPath);
		}
		else if (view_AD_Window_ID > 0 && view_DocumentId != null)
		{
			final DocumentPath documentPath = DocumentPath.rootDocumentPath(DocumentType.Window, view_AD_Window_ID, view_DocumentId);
			documentRef = documentsCollection.getTableRecordReference(documentPath);
		}
		else
		{
			documentRef = null;
		}

		return ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setCreateTemporaryCtx()
				.setAD_Process_ID(adProcessId)
				.setRecord(documentRef)
				.setWhereClause(sqlWhereClause)
				//
				.setLoadParametersFromDB(true) // important: we need to load the existing parameters from database, besides the internal ones we are adding here
				.addParameter(ProcessInstance.PARAM_ViewId, viewId) // internal parameter
				//
				.build();
	}


	private ProcessInstance retrieveProcessInstance(final DocumentId adPInstanceId)
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

		//
		// Build the parameters document
		final int adProcessId = processInfo.getAD_Process_ID();
		final ProcessDescriptor processDescriptor = getProcessDescriptor(adProcessId);

		//
		// Build the parameters (as document)
		final DocumentEntityDescriptor parametersDescriptor = processDescriptor.getParametersDescriptor();
		final Document parametersDoc = parametersDescriptor
				.getDataBinding()
				.getDocumentsRepository()
				.retrieveDocumentById(parametersDescriptor, adPInstanceId);

		// TODO: handle the case when the process was already executed
		// In that case we need to load the result and provide it to ProcessInstance constructor

		return new ProcessInstance(processDescriptor, adPInstanceId, parametersDoc);
	}

	public <R> R forProcessInstanceReadonly(final int pinstanceIdAsInt, final Function<ProcessInstance, R> processor)
	{
		final DocumentId pinstanceId = DocumentId.of(pinstanceIdAsInt);
		
		try
		{
			try (final IAutoCloseable readLock = processInstances.get(pinstanceId).lockForReading())
			{
				final ProcessInstance processInstance = processInstances.get(pinstanceId);
				return processor.apply(processInstance);
			}
		}
		catch (final UncheckedExecutionException | ExecutionException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	public <R> R forProcessInstanceWritable(final int pinstanceIdAsInt, final Function<ProcessInstance, R> processor)
	{
		final DocumentId pinstanceId = DocumentId.of(pinstanceIdAsInt);
		
		try
		{
			try (final IAutoCloseable writeLock = processInstances.get(pinstanceId).lockForWriting())
			{
				final ProcessInstance processInstance = processInstances.get(pinstanceId).copy(CopyMode.CheckOutWritable);
				
				final R result = processor.apply(processInstance);
				
				// Actually put it back
				processInstance.saveIfValidAndHasChanges(false); // throwEx=false
				processInstances.put(pinstanceId, processInstance.copy(CopyMode.CheckInReadonly));
				
				return result;
			}
		}
		catch (final UncheckedExecutionException | ExecutionException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
}
