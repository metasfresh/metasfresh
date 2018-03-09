package de.metas.ui.web.process;

import java.util.Collection;
import java.util.List;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;

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
 * Process instance controller.
 * 
 * Implementations of this call are able to manage a process instance life-cycle:
 * <ul>
 * <li>parameters: providing current values, changing parameters, providing lookup values
 * <li>starting the process
 * </ul>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IProcessInstanceController
{
	DocumentId getInstanceId();

	ProcessInstanceResult startProcess(ProcessExecutionContext context);

	/**
	 * @return execution result or throws exception if the process was not already executed
	 */
	ProcessInstanceResult getExecutionResult();

	//
	// Process parameters
	//@formatter:off
	Collection<IProcessInstanceParameter> getParameters();
	LookupValuesList getParameterLookupValues(String parameterName);
	LookupValuesList getParameterLookupValuesForQuery(String parameterName, String query);
	void processParameterValueChanges(List<JSONDocumentChangedEvent> events, ReasonSupplier reason);
	//@formatter:on

}
