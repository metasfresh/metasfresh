package org.adempiere.ad.service;

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

import org.adempiere.util.ISingletonService;
import org.compiere.process.ProcessExecutionResult;
import org.compiere.process.ProcessInfo;
import org.compiere.process.ProcessInfoLog;
import org.compiere.process.ProcessInfoParameter;

public interface IADPInstanceDAO extends ISingletonService
{
	/**
	 * Saves the parameters of the given ProcessInfo object to the Database. Parameters which are already saved in the database will be:
	 * <li>Ignored if the value has not changed
	 * <li>Overwritten if the
	 * Value has changed Parameters that are saved in the Database but not included in the ProcessInfo are not affected.
	 * 
	 * @param pi process info
	 * @task US1007
	 */
	void saveParameterToDB(ProcessInfo pi);

	/**
	 * 
	 * @param adPInstanceId
	 * @param piParams
	 * @see #saveParameterToDB(ProcessInfo)
	 */
	void saveParameterToDB(int adPInstanceId, List<ProcessInfoParameter> piParams);

	/**
	 * Retrieve process parameters for given AD_PInstance_ID
	 * 
	 * @param ctx
	 * @param adPInstanceId AD_PInstance_ID
	 * @return
	 */
	List<ProcessInfoParameter> retrieveProcessInfoParameters(Properties ctx, int adPInstanceId);

	List<ProcessInfoLog> retrieveProcessInfoLogs(int adPInstanceId);

	void saveProcessInfoLogs(int AD_PInstance_ID, List<ProcessInfoLog> logs);

	void retrieveResultSummary(ProcessExecutionResult result);
}
