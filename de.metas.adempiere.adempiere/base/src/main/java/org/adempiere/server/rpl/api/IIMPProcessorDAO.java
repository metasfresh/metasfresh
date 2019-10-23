package org.adempiere.server.rpl.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;
import java.util.Properties;

import org.compiere.model.AdempiereProcessorLog;
import org.compiere.model.I_IMP_Processor;
import org.compiere.model.I_IMP_ProcessorParameter;

import de.metas.util.ISingletonService;

public interface IIMPProcessorDAO extends ISingletonService
{

	List<AdempiereProcessorLog> retrieveAdempiereProcessorLogs(final org.compiere.model.I_IMP_Processor impProcessor);

	/**
	 * Delete attached logs.
	 * 
	 * NOTE: only logs older then {@link I_IMP_Processor#getKeepLogDays()} will be deleted.
	 * 
	 * @param impProcessor
	 * @return number of logs deleted
	 */
	int deleteLogs(I_IMP_Processor impProcessor);

	/**
	 * Delete attached logs
	 * 
	 * @param impProcessor
	 * @param deleteAll if false only those logs that are older then {@link I_IMP_Processor#getKeepLogDays()} will be deleted
	 * @return number of logs deleted
	 */
	int deleteLogs(I_IMP_Processor impProcessor, boolean deleteAll);

	List<I_IMP_ProcessorParameter> retrieveParameters(I_IMP_Processor impProcessor, String trxName);

	void deleteParameters(I_IMP_Processor impProcessor);

	I_IMP_ProcessorParameter retrieveParameter(I_IMP_Processor impProcessor, String parameterName);

	/**
	 * Retrieve ALL (from all clients) import processors which are active
	 * 
	 * @param ctx
	 * @return
	 */
	List<org.adempiere.server.rpl.interfaces.I_IMP_Processor> retrieveAllActive(Properties ctx);

}
