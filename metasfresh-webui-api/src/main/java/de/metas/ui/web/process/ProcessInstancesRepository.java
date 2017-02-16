package de.metas.ui.web.process;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import de.metas.ui.web.exceptions.InvalidDocumentVersionException;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.process.descriptor.ProcessDescriptorsFactory;
import de.metas.ui.web.process.descriptor.ProcessParametersRepository;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.CopyMode;

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

	public void checkin(final ProcessInstance processInstance)
	{
		processInstance.saveIfValidAndHasChanges(false); // throwEx=false

		final int versionCurrentExpected = processInstance.getVersion();
		final int version = versionCurrentExpected + 1;
		final ProcessInstance processInstanceCopy = processInstance.copy(CopyMode.CheckInReadonly);
		processInstanceCopy.setVersion(version);
		final DocumentId adPInstanceId = processInstance.getAD_PInstance_ID();

		// NOTE: because Cache does not have a compute() method, we have to synchronize this block. 
		synchronized (processInstanceCopy)
		{
			// Get current process instance.
			final ProcessInstance processInstanceCurrent = processInstances.getIfPresent(adPInstanceId);
			
			// Make sure we are not overriding a different document version than the one that was checked out.
			if (processInstanceCurrent != null)
			{
				if (processInstanceCurrent.getVersion() != versionCurrentExpected)
				{
					throw new InvalidDocumentVersionException(versionCurrentExpected, processInstanceCurrent.getVersion());
				}
			}
			
			// Actually put the new document
			processInstances.put(adPInstanceId, processInstanceCopy);
			
			// Update version of the document that was given as parameter.
			processInstance.setVersion(version);
		}
	}

	public ProcessInstance createNewProcessInstance(final ProcessInfo processInfo)
	{
		//
		// Save process info together with it's parameters and get the the newly created AD_PInstance_ID
		Services.get(IADPInstanceDAO.class).saveProcessInfo(processInfo);
		final DocumentId adPInstanceId = DocumentId.of(processInfo.getAD_PInstance_ID());

		//
		// Build the parameters document
		final int adProcessId = processInfo.getAD_Process_ID();
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

	public ProcessInstance getProcessInstanceForReading(final int pinstanceIdAsInt)
	{
		try
		{
			return processInstances.get(DocumentId.of(pinstanceIdAsInt));
		}
		catch (final UncheckedExecutionException | ExecutionException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	public ProcessInstance getProcessInstanceForWriting(final int pinstanceIdAsInt)
	{
		final DocumentId documentId = DocumentId.of(pinstanceIdAsInt);
		try
		{
			return processInstances.get(documentId).copy(CopyMode.CheckOutWritable);
		}
		catch (final UncheckedExecutionException | ExecutionException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}
}
