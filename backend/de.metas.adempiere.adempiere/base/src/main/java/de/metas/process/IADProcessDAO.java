package de.metas.process;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.ITranslatableString;
import de.metas.util.ISingletonService;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;

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

	List<RelatedProcessDescriptor> retrieveRelatedProcessDescriptors(int adTableId, AdWindowId adWindowId, AdTabId adTabId);

	/**
	 * Retrieves the {@link I_AD_Process} which references the given <code>AD_Form_ID</code>. If there is no such process, the method returns <code>null</code>. If there are multiple such records,
	 * then the method throws a
	 *
	 * @param ctx
	 * @param AD_Form_ID
	 * @return
	 * @throws org.adempiere.exceptions.DBMoreThanOneRecordsFoundException if more than one process references the given AD_Form_ID
	 */
	I_AD_Process retrieveProcessByForm(Properties ctx, int AD_Form_ID);

	/**
	 * Registers a process for a certain table without the need of an <code>AD_Table_Process</code> record in the database.
	 *
	 * @param adTableId
	 * @param adWindowId (optional)
	 * @param adProcessId
	 */
	void registerTableProcess(int adTableId, AdWindowId adWindowId, AdProcessId adProcessId);

	default void registerTableProcess(final int adTableId, final AdProcessId adProcessId)
	{
		final AdWindowId adWindowId = null;
		registerTableProcess(adTableId, adWindowId, adProcessId);
	}

	/**
	 * Registers a process for a certain table without the need of an <code>AD_Table_Process</code> record in the database.
	 *
	 * @param tableName
	 * @param adWindowId (optional)
	 * @param adProcessId
	 */
	void registerTableProcess(String tableName, AdWindowId adWindowId, AdProcessId adProcessId);

	default void registerTableProcess(final String tableName, final AdProcessId adProcessId)
	{
		final AdWindowId adWindowId = null;
		registerTableProcess(tableName, adWindowId, adProcessId);
	}

	void registerTableProcess(RelatedProcessDescriptor descriptor);

	/**
	 * Similar to {@link #retrieveProcessIdByClassIfUnique(Class)}, but assumes that there is a unique ID and throws an exception if that's not the case.
	 * This can be beneficial since the exception message contains the class for which no {@code AD_Process_ID} could be fetched.
	 * 
	 * @param processClass
	 * @return
	 */
	AdProcessId retrieveProcessIdByClass(Class<?> processClass);

	/**
	 * Retrieves the ID of the <code>AD_Process</code> whose {@link I_AD_Process#COLUMN_Classname Classname} column matches the given class.
	 *
	 * @param processClass
	 * @return the <code>AD_Process_ID</code> of the matching process, or <code>-1</code> if there is no matching process or more than one of them
	 */
	AdProcessId retrieveProcessIdByClassIfUnique(Class<?> processClass);

	/**
	 * @see #retrieveProcessIdByClassIfUnique(Properties, Class)
	 */
	AdProcessId retrieveProcessIdByClassIfUnique(String processClassname);

	Optional<ITranslatableString> retrieveProcessNameByClassIfUnique(Class<?> processClass);

	/**
	 * Retrieves the ID of the <code>AD_Process</code> whose {@link I_AD_Process#COLUMN_Value} is equal to the given <code>processValue</code>. Assumes that <code>AD_Process.Value</code> is unique.
	 *
	 * @param ctx
	 * @param processValue
	 * @return
	 */
	AdProcessId retrieveProcessIdByValue(String processValue);

	/**
	 * Retrieves {@link I_AD_Process} by {@link I_AD_Process#COLUMN_Value}.
	 *
	 * @param ctx
	 * @param processValue
	 * @return process; never returns <code>null</code>.
	 * @throws DBException in case of any error or if the process was not found
	 */
	I_AD_Process retrieveProcessByValue(Properties ctx, String processValue);

	int retrieveProcessParaLastSeqNo(I_AD_Process process);

	Collection<I_AD_Process_Para> retrieveProcessParameters(I_AD_Process process);

	I_AD_Process_Para retrieveProcessParameter(AdProcessId adProcessId, String parameterName);

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
	void addProcessStatistics(AdProcessId adProcessId, ClientId adClientId, long durationMillisToAdd);

	/**
	 * Copy settings from another process
	 * overwrites existing data
	 * (including translations)
	 * and saves.
	 * Not overwritten: name, value, entitytype
	 *
	 * @param targetProcessId
	 * @param sourceProcessId
	 */
	void copyProcess(AdProcessId targetProcessId, AdProcessId sourceProcessId);

	void copyProcessParameters(AdProcessId targetProcessId, AdProcessId sourceProcessId);

	ITranslatableString getProcessNameById(final AdProcessId id);

	ImmutableList<I_AD_Process> getProcessesByType(Set<ProcessType> processTypeSet);

	ImmutableList<I_AD_Process_Para> getProcessParamsByProcessIds(Set<Integer> processIDs);
}
