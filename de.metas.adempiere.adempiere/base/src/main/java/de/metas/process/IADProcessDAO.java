package de.metas.process;

import java.util.Collection;

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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.adempiere.exceptions.DBException;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.util.Env;

import de.metas.i18n.ITranslatableString;

public interface IADProcessDAO extends ISingletonService
{
	/**
	 * Retrieves {@link I_AD_Process}es which are assigned to given <code>tableName</code> and have <code>IsReport=true</code>
	 *
	 * @param ctx
	 * @param tableName
	 * @return assigned report-processes
	 */
	List<I_AD_Process> retrieveReportProcessesForTable(Properties ctx, String tableName);

	/**
	 * Retrieves {@link RelatedProcessDescriptor} indexed by AD_Process_ID.
	 *
	 * @param ctx
	 * @param adTableId
	 * @param adWindowId
	 * @return AD_Process_ID to {@link RelatedProcessDescriptor}
	 */
	Map<Integer, RelatedProcessDescriptor> retrieveRelatedProcessesForTableIndexedByProcessId(Properties ctx, int adTableId, int adWindowId);

	/**
	 * Retrieves the {@link I_AD_Process} which references the given <code>AD_Form_ID</code>. If there is no such process, the method returns <code>null</code>. If there are multiple such records,
	 * then the method throws a
	 *
	 * @param ctx
	 * @param AD_Form_ID
	 * @return
	 * @throws org.adempiere.exceptions.DBMoreThenOneRecordsFoundException if more than one process references the given AD_Form_ID
	 */
	I_AD_Process retrieveProcessByForm(Properties ctx, int AD_Form_ID);

	/**
	 * Registers a process for a certain table without the need of an <code>AD_Table_Process</code> record in the database.
	 *
	 * @param adTableId
	 * @param adWindowId (optional)
	 * @param adProcessId
	 */
	void registerTableProcess(int adTableId, int adWindowId, int adProcessId);

	default void registerTableProcess(final int adTableId, final int adProcessId)
	{
		final int adWindowId = 0;
		registerTableProcess(adTableId, adWindowId, adProcessId);
	}

	/**
	 * Registers a process for a certain table without the need of an <code>AD_Table_Process</code> record in the database.
	 *
	 * @param tableName
	 * @param adWindowId (optional)
	 * @param adProcessId
	 */
	void registerTableProcess(String tableName, int adWindowId, int adProcessId);

	default void registerTableProcess(final String tableName, final int adProcessId)
	{
		final int adWindowId = 0;
		registerTableProcess(tableName, adWindowId, adProcessId);
	}

	void registerTableProcess(RelatedProcessDescriptor descriptor);

	/**
	 * Similar to {@link #retriveProcessIdByClassIfUnique(Properties, Class)}, but assumes that there is a unique ID and throws an exception if that's not the case.
	 * This can be beneficial since the exception message contains the class for which no {@code AD_Process_ID} could be fetched.
	 * 
	 * @param processClass
	 * @return
	 */
	int retrieveProcessIdByClass(Class<?> processClass);


	/**
	 * Retrieves the ID of the <code>AD_Process</code> whose {@link I_AD_Process#COLUMN_Classname Classname} column matches the given class.
	 *
	 * @param ctx
	 * @param processClass
	 * @return the <code>AD_Process_ID</code> of the matching process, or <code>-1</code> if there is no matching process or more than one of them
	 */
	int retriveProcessIdByClassIfUnique(Properties ctx, Class<?> processClass);

	default int retriveProcessIdByClassIfUnique(final Class<?> processClass)
	{
		return retriveProcessIdByClassIfUnique(Env.getCtx(), processClass);
	}

	/**
	 * @see #retriveProcessIdByClassIfUnique(Properties, Class)
	 */
	int retriveProcessIdByClassIfUnique(Properties ctx, String processClassname);

	Optional<ITranslatableString> retrieveProcessNameByClassIfUnique(Class<?> processClass);

	/**
	 * Retrieves {@link I_AD_Process} by given ID.
	 *
	 * @param ctx
	 * @param adProcessId
	 * @return process; never returns null
	 */
	I_AD_Process retrieveProcessById(Properties ctx, int adProcessId);

	/**
	 * Retrieves the ID of the <code>AD_Process</code> whose {@link I_AD_Process#COLUMN_Value} is equal to the given <code>processValue</code>. Assumes that <code>AD_Process.Value</code> is unique.
	 *
	 * @param ctx
	 * @param processValue
	 * @return
	 */
	int retriveProcessIdByValue(Properties ctx, String processValue);

	/**
	 * Retrieves {@link I_AD_Process} by {@link I_AD_Process#COLUMN_Value}.
	 *
	 * @param ctx
	 * @param processValue
	 * @return process; never returns <code>null</code>.
	 * @throws DBException in case of any error or if the process was not found
	 */
	I_AD_Process retriveProcessByValue(Properties ctx, String processValue);

	int retrieveProcessParaLastSeqNo(I_AD_Process process);

	Collection<I_AD_Process_Para> retrieveProcessParameters(I_AD_Process process);

	I_AD_Process_Para retriveProcessParameter(Properties ctx, int adProcessId, String parameterName);

	/**
	 * Add process execution statistics.
	 *
	 * NOTE: this method will never throw an exception but in case of failure, the error will be just logged.
	 *
	 * @param ctx
	 * @param adProcessId
	 * @param adClientId
	 * @param durationMillisToAdd
	 */
	void addProcessStatistics(Properties ctx, int adProcessId, int adClientId, long durationMillisToAdd);

	/**
	 * Copy settings from another process
	 * overwrites existing data
	 * (including translations)
	 * and saves.
	 * Not overwritten: name, value, entitytype
	 *
	 * @param targetProcess
	 * @param sourceProcess
	 */
	void copyAD_Process(I_AD_Process targetProcess, I_AD_Process sourceProcess);
}
