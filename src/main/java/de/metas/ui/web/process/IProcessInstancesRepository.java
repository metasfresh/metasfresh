package de.metas.ui.web.process;

import java.util.function.Function;
import java.util.stream.Stream;

import de.metas.process.IProcessPreconditionsContext;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.process.json.JSONCreateProcessInstanceRequest;
import de.metas.ui.web.window.datatypes.DocumentId;

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

public interface IProcessInstancesRepository
{
	String getProcessHandlerType();

	ProcessDescriptor getProcessDescriptor(ProcessId processId);

	Stream<WebuiRelatedProcessDescriptor> streamDocumentRelatedProcesses(IProcessPreconditionsContext preconditionsContext);

	IProcessInstanceController createNewProcessInstance(ProcessId processId, JSONCreateProcessInstanceRequest request);

	<R> R forProcessInstanceReadonly(DocumentId pinstanceId, Function<IProcessInstanceController, R> processor);

	<R> R forProcessInstanceWritable(DocumentId pinstanceId, Function<IProcessInstanceController, R> processor);
	
	void cacheReset();
}
