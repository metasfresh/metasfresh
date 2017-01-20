package de.metas.process.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Access;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.I_AD_Process_Stats;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_AD_Table_Process;
import org.compiere.util.CacheMgt;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.SetMultimap;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.logging.LogManager;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;

public class ADProcessDAO implements IADProcessDAO
{
	private static final Logger logger = LogManager.getLogger(ADProcessDAO.class);

	/**
	 * Map: AD_Table_ID to list of AD_Process_ID
	 */
	private final SetMultimap<Integer, Integer> staticRegisteredProcesses = HashMultimap.create();

	@Override
	public int retriveProcessIdByClassIfUnique(final Properties ctx, final Class<?> processClass)
	{
		final String processClassname = processClass.getName();
		return retriveProcessIdByClassIfUnique(ctx, processClassname);
	}

	@Override
	public int retriveProcessIdByClassIfUnique(final Properties ctx, final String processClassname)
	{
		List<Integer> processIds = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Process.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Process.COLUMN_Classname, processClassname)
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds();

		if (processIds.isEmpty())
		{
			return -1;
		}
		else if (processIds.size() == 1)
		{
			return processIds.get(0);
		}
		else
		{
			// more then one AD_Process_IDs matched => return -1
			return -1;
		}
	}

	@Override
	public int retriveProcessIdByValue(final Properties ctx, final String processValue)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Process.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Process.COLUMN_Value, processValue)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnly();
	}

	@Override
	public I_AD_Process retrieveProcessById(final Properties ctx, final int adProcessId)
	{
		// NOTE: we assume IModelCacheService is activated
		final I_AD_Process process = InterfaceWrapperHelper.create(ctx, adProcessId, I_AD_Process.class, ITrx.TRXNAME_None);
		Check.assumeNotNull(process, "Process shall exist for AD_Process_ID={}", adProcessId);
		return process;

	}

	@Override
	public I_AD_Process retriveProcessByValue(final Properties ctx, final String processValue)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Process.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Process.COLUMN_Value, processValue)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyNotNull(I_AD_Process.class);
	}

	@Override
	public void registerTableProcess(final int adTableId, final int adProcessId)
	{
		Check.assume(adTableId > 0, "adTableId > 0");
		Check.assume(adProcessId > 0, "adProcessId > 0");
		final boolean added = staticRegisteredProcesses.put(adTableId, adProcessId);
		if (added)
		{
			logger.info("Registered table process: AD_Table_ID={}, AD_Process_ID={}", adTableId, adProcessId);
			CacheMgt.get().reset(I_AD_Table_Process.Table_Name);
		}
	}

	@Override
	public void registerTableProcess(final String tableName, final int adProcessId)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");

		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		Check.assume(adTableId > 0, "adTableId > 0 (TableName={})", tableName);

		registerTableProcess(adTableId, adProcessId);
	}

	/**
	 * Gets a list of AD_Process_IDs which were statically registered for given table ID.
	 * 
	 * @param adTableId
	 * @return set of AD_Process_IDs
	 */
	private Set<Integer> getStaticRegisteredProcessIds(final int adTableId)
	{
		final Set<Integer> processIds = staticRegisteredProcesses.get(adTableId);
		if (processIds == null || processIds.isEmpty())
		{
			return ImmutableSet.of();
		}

		return ImmutableSet.copyOf(processIds);
	}

	// @Cached
	@Override
	public final List<I_AD_Process> retrieveProcessesForTable(final Properties ctx, final int adTableId)
	{
		return retrieveProcessesForTableQueryBuilder(ctx, adTableId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_AD_Process.class);
	}

	@Override
	public final Set<Integer> retrieveProcessesIdsForTable(final Properties ctx, final int adTableId)
	{
		final Map<Integer, RelatedProcessDescriptor> relatedProcesses = retrieveRelatedProcessesForTableIndexedByProcessId(ctx, adTableId);
		return relatedProcesses.keySet();
	}

	@Override
	public final List<I_AD_Process> retrieveReportProcessesForTable(final Properties ctx, final String tableName)
	{
		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		return retrieveProcessesForTableQueryBuilder(ctx, adTableId)
				.addEqualsFilter(I_AD_Process.COLUMN_IsReport, true)
				.create()
				.list(I_AD_Process.class);
	}

	@Override
	@Cached(cacheName = I_AD_Table_Process.Table_Name + "#RelatedProcessDescriptors")
	public Map<Integer, RelatedProcessDescriptor> retrieveRelatedProcessesForTableIndexedByProcessId(@CacheCtx final Properties ctx, final int adTableId)
	{
		//
		// Fetch related processes from database
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final Map<Integer, RelatedProcessDescriptor> relatedProcessesMap = queryBL.createQueryBuilder(I_AD_Table_Process.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Table_Process.COLUMNNAME_AD_Table_ID, adTableId)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(tableProcess -> createRelatedProcessDescriptor(tableProcess))
				.collect(Collectors.toMap(RelatedProcessDescriptor::getAD_Process_ID, value -> value));

		//
		// Collect also those programmatically registered
		getStaticRegisteredProcessIds(adTableId)
				.stream()
				.filter(adProcessId -> !relatedProcessesMap.containsKey(adProcessId)) // only those which were not already added
				.map(adProcessId -> RelatedProcessDescriptor.ofAD_Process_ID(adProcessId))
				.forEach(relatedProcess -> relatedProcessesMap.put(relatedProcess.getAD_Process_ID(), relatedProcess));

		return ImmutableMap.copyOf(relatedProcessesMap);
	}

	private static final RelatedProcessDescriptor createRelatedProcessDescriptor(final I_AD_Table_Process tableProcess)
	{
		return RelatedProcessDescriptor.builder()
				.setAD_Process_ID(tableProcess.getAD_Process_ID())
				.setWebuiQuickAction(tableProcess.isWEBUI_QuickAction())
				.setWebuiDefaultQuickAction(tableProcess.isWEBUI_QuickAction_Default())
				.build();
	}

	private IQueryBuilder<I_AD_Process> retrieveProcessesForTableQueryBuilder(final Properties ctx, final int adTableId)
	{
		final String trxName = ITrx.TRXNAME_NoneNotNull;
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		
		
		final Map<Integer, RelatedProcessDescriptor> relatedProcesses = retrieveRelatedProcessesForTableIndexedByProcessId(ctx, adTableId);
		final Set<Integer> relatedProcessIds = relatedProcesses.keySet();

		return queryBL.createQueryBuilder(I_AD_Process.class, ctx, trxName)
				.addOnlyActiveRecordsFilter()
				.addInArrayOrAllFilter(I_AD_Process.COLUMN_AD_Process_ID, relatedProcessIds)
				//
				.orderBy()
				.addColumn(I_AD_Process.COLUMNNAME_Name)
				.endOrderBy();
	}

	@Override
	public I_AD_Process retrieveProcessByForm(final Properties ctx, final int AD_Form_ID)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Process.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Process.COLUMNNAME_AD_Form_ID, AD_Form_ID)
				.create()
				.firstOnly(I_AD_Process.class);
	}

	@Override
	public int retrieveProcessParaLastSeqNo(final I_AD_Process process)
	{
		final Integer lastSeqNo = Services.get(IQueryBL.class).createQueryBuilder(I_AD_Process_Para.class, process)
				.addEqualsFilter(I_AD_Process_Para.COLUMNNAME_AD_Process_ID, process.getAD_Process_ID())
				.addOnlyActiveRecordsFilter()
				.create()
				.aggregate(I_AD_Process_Para.COLUMNNAME_SeqNo, IQuery.AGGREGATE_MAX, Integer.class);
		return lastSeqNo == null ? 0 : lastSeqNo;
	}

	@Override
	public Collection<I_AD_Process_Para> retrieveProcessParameters(final I_AD_Process process)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(process);
		final String trxName = InterfaceWrapperHelper.getTrxName(process);
		final int adProcessId = process.getAD_Process_ID();
		return retrieveProcessParameters(ctx, adProcessId, trxName).values();
	}

	@Override
	public I_AD_Process_Para retriveProcessParameter(final Properties ctx, final int adProcessId, final String parameterName)
	{
		return retrieveProcessParameters(ctx, adProcessId, ITrx.TRXNAME_None).get(parameterName);
	}

	@Cached(cacheName = I_AD_Process_Para.Table_Name + "#by#" + I_AD_Process_Para.COLUMNNAME_AD_Process_ID)
	public Map<String, I_AD_Process_Para> retrieveProcessParameters(@CacheCtx final Properties ctx, final int adProcessId, @CacheTrx final String trxName)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_AD_Process_Para.class, ctx, trxName)
				.addEqualsFilter(I_AD_Process_Para.COLUMNNAME_AD_Process_ID, adProcessId)
				.addOnlyActiveRecordsFilter()
				.orderBy()
				.addColumn(I_AD_Process_Para.COLUMNNAME_SeqNo)
				.endOrderBy()
				.create()
				.map(I_AD_Process_Para.class, I_AD_Process_Para::getColumnName);
	}

	@Override
	public void addProcessStatistics(final Properties ctx, final int adProcessId, final int adClientId, final long durationMillisToAdd)
	{
		try
		{
			I_AD_Process_Stats processStats = Services.get(IQueryBL.class)
					.createQueryBuilder(I_AD_Process_Stats.class, ctx, ITrx.TRXNAME_None)
					.addEqualsFilter(I_AD_Process_Stats.COLUMN_AD_Client_ID, adClientId)
					.addEqualsFilter(I_AD_Process_Stats.COLUMN_AD_Process_ID, adProcessId)
					.create()
					.firstOnly(I_AD_Process_Stats.class);
			if (processStats == null)
			{
				processStats = InterfaceWrapperHelper.create(ctx, I_AD_Process_Stats.class, ITrx.TRXNAME_None);
				InterfaceWrapperHelper.setValue(processStats, I_AD_Process_Stats.COLUMNNAME_AD_Client_ID, adClientId);
				processStats.setAD_Org_ID(Env.CTXVALUE_AD_Org_ID_Any);
				processStats.setAD_Process_ID(adProcessId);
			}

			final int count = processStats.getStatistic_Count();
			final int durationMillis = processStats.getStatistic_Millis();

			processStats.setStatistic_Count(count + 1);
			processStats.setStatistic_Millis((int)(durationMillis + durationMillisToAdd));
			InterfaceWrapperHelper.save(processStats);
		}
		catch (Exception ex)
		{
			logger.error("Failed updating process statistics for AD_Process_ID={}, AD_Client_ID={}, DurationMillisToAdd={}. Ignored.", adProcessId, adClientId, durationMillisToAdd, ex);
		}
	}

	@Override
	public I_AD_Process_Access retrieveProcessAccessOrCreateDraft(final Properties ctx, final int adProcessId, final I_AD_Role role)
	{
		I_AD_Process_Access pa = retrieveProcessAccess(ctx, adProcessId, role.getAD_Role_ID());
		if (pa == null)
		{
			pa = createProcessAccessDraft(ctx, adProcessId, role);
		}
		return pa;
	}

	@Override
	public I_AD_Process_Access retrieveProcessAccess(final Properties ctx, final int adProcessId, final int adRoleId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Process_Access.class, ctx, ITrx.TRXNAME_ThreadInherited)
				.addEqualsFilter(I_AD_Process_Access.COLUMN_AD_Process_ID, adProcessId)
				.addEqualsFilter(I_AD_Process_Access.COLUMN_AD_Role_ID, adRoleId)
				.create()
				.firstOnly(I_AD_Process_Access.class);
	}

	@Override
	public I_AD_Process_Access createProcessAccessDraft(final Properties ctx, final int adProcessId, final I_AD_Role role)
	{
		final I_AD_Process_Access pa = InterfaceWrapperHelper.create(ctx, I_AD_Process_Access.class, ITrx.TRXNAME_ThreadInherited);
		InterfaceWrapperHelper.setValue(pa, I_AD_Process_Access.COLUMNNAME_AD_Client_ID, role.getAD_Client_ID());
		pa.setAD_Org_ID(role.getAD_Org_ID());

		pa.setAD_Process_ID(adProcessId);
		pa.setAD_Role(role);

		pa.setIsActive(true);
		pa.setIsReadWrite(true);

		return pa;
	}

	@Override
	public void copyAD_Process(final I_AD_Process targetProcess, final I_AD_Process sourceProcess)
	{
		logger.debug("Copying from: {} to: {}", sourceProcess, targetProcess);

		InterfaceWrapperHelper.copy()
				.setSkipCalculatedColumns(true)
				.addTargetColumnNameToSkip(I_AD_Process.COLUMNNAME_Value)
				.addTargetColumnNameToSkip(I_AD_Process.COLUMNNAME_Name)
				.setFrom(sourceProcess)
				.setTo(targetProcess)
				.copy();
		InterfaceWrapperHelper.save(targetProcess);

		// copy parameters
		// TODO? Perhaps should delete existing first?
		for (final I_AD_Process_Para sourcePara : retrieveProcessParameters(sourceProcess))
		{
			copyAD_Process_Para(targetProcess, sourcePara);
		}
	}

	/**
	 * Copy settings from another process parameter
	 * overwrites existing data
	 * (including translations)
	 * and saves
	 * 
	 * @param sourcePara
	 */
	private I_AD_Process_Para copyAD_Process_Para(final I_AD_Process targetProcess, final I_AD_Process_Para sourcePara)
	{
		logger.debug("Copying parameter from {} to {}", sourcePara, targetProcess);

		final Properties ctx = InterfaceWrapperHelper.getCtx(targetProcess);
		final String trxName = InterfaceWrapperHelper.getTrxName(targetProcess);

		final I_AD_Process_Para targetPara = InterfaceWrapperHelper.create(ctx, I_AD_Process_Para.class, trxName);
		targetPara.setAD_Org_ID(sourcePara.getAD_Org_ID());
		targetPara.setAD_Process(targetProcess);
		targetPara.setEntityType(targetProcess.getEntityType());

		targetPara.setAD_Element_ID(sourcePara.getAD_Element_ID());
		targetPara.setAD_Reference_ID(sourcePara.getAD_Reference_ID());
		targetPara.setAD_Reference_Value_ID(sourcePara.getAD_Reference_Value_ID());
		targetPara.setAD_Val_Rule_ID(sourcePara.getAD_Val_Rule_ID());
		targetPara.setColumnName(sourcePara.getColumnName());
		targetPara.setDefaultValue(sourcePara.getDefaultValue());
		targetPara.setDefaultValue2(sourcePara.getDefaultValue2());
		targetPara.setDescription(sourcePara.getDescription());
		targetPara.setDisplayLogic(sourcePara.getDisplayLogic());
		targetPara.setFieldLength(sourcePara.getFieldLength());
		targetPara.setHelp(sourcePara.getHelp());
		targetPara.setIsActive(sourcePara.isActive());
		targetPara.setIsCentrallyMaintained(sourcePara.isCentrallyMaintained());
		targetPara.setIsMandatory(sourcePara.isMandatory());
		targetPara.setIsRange(sourcePara.isRange());
		targetPara.setName(sourcePara.getName());
		targetPara.setReadOnlyLogic(sourcePara.getReadOnlyLogic());
		targetPara.setSeqNo(sourcePara.getSeqNo());
		targetPara.setValueMax(sourcePara.getValueMax());
		targetPara.setValueMin(sourcePara.getValueMin());
		targetPara.setVFormat(sourcePara.getVFormat());
		targetPara.setIsAutocomplete(sourcePara.isAutocomplete());
		InterfaceWrapperHelper.save(targetPara);

		//
		// Delete newly created translations and copy translations from source
		// NOTE: don't use parameterized SQL queries because this script will be logged as a migration script (task
		{
			final int adProcessParaId = targetPara.getAD_Process_Para_ID();
			final String sqlDelete = "DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = " + adProcessParaId;
			int count = DB.executeUpdateEx(sqlDelete, trxName);
			logger.debug("AD_Process_Para_Trl deleted: " + count);

			final String sqlInsert = "INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language, " +
					" AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, " +
					" Name, Description, Help, IsTranslated) " +
					" SELECT AD_Process_Para_ID, AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, " +
					" Updated, UpdatedBy, Name, Description, Help, IsTranslated " +
					" FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = " + adProcessParaId;
			count = DB.executeUpdateEx(sqlInsert, trxName);
			logger.debug("AD_Process_Para_Trl inserted: " + count);
		}

		return targetPara;
	}

}
