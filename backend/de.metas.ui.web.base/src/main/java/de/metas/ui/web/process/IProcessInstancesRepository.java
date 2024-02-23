package de.metas.ui.web.process;

import java.util.function.Function;
import java.util.stream.Stream;

import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.IDocumentChangesCollector;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * Process descriptors and instances repository.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IProcessInstancesRepository
{
	/**
	 * Gets the handler type.
	 * The handler type shall be unique across all {@link IProcessInstancesRepository} implementations.
	 * 
	 * @return handler type
	 */
	ProcessHandlerType getProcessHandlerType();

	/**
	 * @return process descriptor; never returns null
	 */
	ProcessDescriptor getProcessDescriptor(ProcessId processId);

	/** @return related process descriptors which are available to be called for given <code>preconditionsContext</code> */
	Stream<WebuiRelatedProcessDescriptor> streamDocumentRelatedProcesses(WebuiPreconditionsContext preconditionsContext);

	/**
	 * Creates a new process instance for given request.
	 * 
	 * @param request
	 * @param changesCollector 
	 * @return newly created process instance; never returns null
	 */
	IProcessInstanceController createNewProcessInstance(CreateProcessInstanceRequest request);

	/**
	 * Fetching the process instance for given <code>pinstanceId</code> (readonly) and processes it using given processor.
	 * 
	 * @param pinstanceId
	 * @param processor
	 * @return <code>processor</code>'s return value
	 */
	<R> R forProcessInstanceReadonly(DocumentId pinstanceId, Function<IProcessInstanceController, R> processor);

	/**
	 * Fetching the process instance for given <code>pinstanceId</code> (read-write) and processes it using given processor.
	 * 
	 * @param pinstanceId
	 * @param processor
	 * @return <code>processor</code>'s return value
	 */
	<R> R forProcessInstanceWritable(DocumentId pinstanceId, IDocumentChangesCollector changesCollector, Function<IProcessInstanceController, R> processor);

	/**
	 * Resets internal caches.
	 */
	void cacheReset();
}
