package de.metas.process;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_PInstance;

import de.metas.util.ISingletonService;

import javax.annotation.Nullable;

public interface IADPInstanceDAO extends ISingletonService
{
	/**
	 * Saves {@link ProcessInfo} together with it's parameters.
	 *
	 * @param pi
	 * @see #saveProcessInfoOnly(ProcessInfo)
	 * @see #saveParameterToDB(PInstanceId, List)
	 */
	void saveProcessInfo(ProcessInfo pi);

	/**
	 * Saves {@link ProcessInfo} only, excluding depending records like process parameters.
	 *
	 * Also, in case the {@link ProcessInfo#getAdProcessId()} is missing, this method will create it and it will set it to {@link ProcessInfo}.
	 *
	 * @param pi
	 */
	void saveProcessInfoOnly(ProcessInfo pi);

	/**
	 * Saves process parameters.
	 *
	 * @param pinstanceId existing AD_PInstance_ID (mandatory)
	 * @param piParams
	 */
	void saveParameterToDB(PInstanceId pinstanceId, List<ProcessInfoParameter> piParams);

	/**
	 * @param pinstanceId AD_PInstance_ID
	 * @return process parameters for given AD_PInstance_ID
	 */
	List<ProcessInfoParameter> retrieveProcessInfoParameters(PInstanceId pinstanceId);

	/**
	 * Locks underlying AD_PInstance.
	 *
	 * @param pinstanceId
	 */
	void lock(PInstanceId pinstanceId);

	/**
	 * Unlocks underlying AD_PInstance, saves the result and logs.
	 *
	 * @param result
	 */
	void unlockAndSaveResult(ProcessExecutionResult result);

	void loadResultSummary(ProcessExecutionResult result);

	/** @return process info logs, ordered chronologically */
	List<ProcessInfoLog> retrieveProcessInfoLogs(PInstanceId pinstanceId);

	/**
	 * Creates a new selection ID (AD_PInstance_ID).
	 *
	 * IMPORTANT: <b>this method is NOT creating an {@link I_AD_PInstance} record.</b>
	 * If you want to create an {@link I_AD_PInstance}, please use {@link #createAD_PInstance(AdProcessId)}.
	 *
	 * @return new AD_PInstance_ID
	 */
	PInstanceId createSelectionId();

	/**
	 * Creates and saves a new AD_PInstance.
	 */
	I_AD_PInstance createAD_PInstance(AdProcessId adProcessId);

	/**
	 * @return process instance; never returns null
	 */
	I_AD_PInstance getById(PInstanceId pinstanceId);

	Set<TableRecordReference> retrieveSelectedIncludedRecords(PInstanceId pinstanceId);

	void saveSelectedIncludedRecords(PInstanceId pinstanceId, Set<TableRecordReference> recordRefs);

	PInstanceId createADPinstanceAndADPInstancePara(PInstanceRequest pinstanceRequest);

	Timestamp getLastRunDate(@NonNull AdProcessId adProcessId, @Nullable PInstanceId pinstanceToExclude);
}
