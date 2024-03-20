/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.ITranslatableString;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.ad.element.api.AdElementId;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.window.api.WindowCopyResult;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

public interface IADProcessDAO extends ISingletonService
{
	I_AD_Process getById(AdProcessId processId);

	@Deprecated
	default I_AD_Process getById(final int processId)
	{
		return getById(AdProcessId.ofRepoId(processId));
	}

	List<RelatedProcessDescriptor> retrieveRelatedProcessDescriptors(@Nullable AdTableId adTableId, @Nullable AdWindowId adWindowId, @Nullable AdTabId adTabId);

	/**
	 * Retrieves the {@link I_AD_Process} which references the given <code>AD_Form_ID</code>. If there is no such process, the method returns <code>null</code>. If there are multiple such records,
	 * then the method throws a
	 *
	 * @throws org.adempiere.exceptions.DBMoreThanOneRecordsFoundException if more than one process references the given AD_Form_ID
	 */
	I_AD_Process retrieveProcessByForm(Properties ctx, int AD_Form_ID);

	/**
	 * Registers a process for a certain table without the need of an <code>AD_Table_Process</code> record in the database.
	 */
	void registerTableProcess(@Nullable AdTableId adTableId, @Nullable AdWindowId adWindowId, AdProcessId adProcessId);

	default void registerTableProcess(final AdTableId adTableId, final AdProcessId adProcessId)
	{
		registerTableProcess(adTableId, null, adProcessId);
	}

	/**
	 * Registers a process for a certain table without the need of an <code>AD_Table_Process</code> record in the database.
	 */
	void registerTableProcess(String tableName, @Nullable AdWindowId adWindowId, AdProcessId adProcessId);

	default void registerTableProcess(final String tableName, final AdProcessId adProcessId)
	{
		registerTableProcess(tableName, null, adProcessId);
	}

	void registerTableProcess(RelatedProcessDescriptor descriptor);

	/**
	 * Similar to {@link #retrieveProcessIdByClassIfUnique(Class)}, but assumes that there is a unique ID and throws an exception if that's not the case.
	 * This can be beneficial since the exception message contains the class for which no {@code AD_Process_ID} could be fetched.
	 */
	AdProcessId retrieveProcessIdByClass(Class<?> processClass);

	/**
	 * Retrieves the ID of the <code>AD_Process</code> whose {@link I_AD_Process#COLUMN_Classname Classname} column matches the given class.
	 *
	 * @return the <code>AD_Process_ID</code> of the matching process, or <code>-1</code> if there is no matching process or more than one of them
	 */
	AdProcessId retrieveProcessIdByClassIfUnique(Class<?> processClass);

	AdProcessId retrieveProcessIdByClassIfUnique(String processClassname);

	Optional<ITranslatableString> retrieveProcessNameByClassIfUnique(Class<?> processClass);

	/**
	 * Retrieves the ID of the <code>AD_Process</code> whose {@link I_AD_Process#COLUMN_Value} is equal to the given <code>processValue</code>. Assumes that <code>AD_Process.Value</code> is unique.
	 */
	AdProcessId retrieveProcessIdByValue(String processValue);

	/**
	 * Retrieves {@link I_AD_Process} by {@link I_AD_Process#COLUMN_Value}.
	 *
	 * @return process; never returns <code>null</code>.
	 * @throws DBException in case of any error or if the process was not found
	 */
	I_AD_Process retrieveProcessByValue(Properties ctx, String processValue);

	int retrieveProcessParaLastSeqNo(I_AD_Process process);

	Collection<I_AD_Process_Para> retrieveProcessParameters(AdProcessId adProcessId);

	I_AD_Process_Para retrieveProcessParameter(AdProcessId adProcessId, String parameterName);

	/**
	 * Add process execution statistics.
	 * <p>
	 * NOTE: this method will never throw an exception but in case of failure, the error will be just logged.
	 */
	void addProcessStatistics(AdProcessId adProcessId, ClientId adClientId, long durationMillisToAdd);

	/**
	 * Copy settings from another process
	 * overwrites existing data
	 * (including translations)
	 * and saves.
	 * Not overwritten: name, value, EntityType
	 */
	void copyProcess(AdProcessId targetProcessId, AdProcessId sourceProcessId);

	void copyProcessParameters(AdProcessId targetProcessId, AdProcessId sourceProcessId);

	ITranslatableString getProcessNameById(final AdProcessId id);

	ImmutableList<I_AD_Process> getProcessesByType(Set<ProcessType> processTypeSet);

	ImmutableList<I_AD_Process_Para> getProcessParamsByProcessIds(Set<Integer> processIDs);

	void save(I_AD_Process process);

	void copyWindowRelatedProcesses(WindowCopyResult windowCopyResult);

	void updateColumnNameByAdElementId(
			@NonNull AdElementId adElementId,
			@Nullable String newColumnName);

	ProcessType retrieveProcessType(@NonNull AdProcessId processId);

	ImmutableSet<AdProcessId> retrieveAllActiveAdProcesIds();

	@NonNull
	List<I_AD_Process> retrieveProcessRecordsByValRule(@NonNull AdValRuleId valRuleId);
}
