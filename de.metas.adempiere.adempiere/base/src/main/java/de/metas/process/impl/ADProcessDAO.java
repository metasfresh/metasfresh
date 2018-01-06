package de.metas.process.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.I_AD_Process_Stats;
import org.compiere.model.I_AD_Table_Process;
import org.compiere.util.CacheMgt;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.process.IADProcessDAO;
import de.metas.process.RelatedProcessDescriptor;
import lombok.ToString;

public class ADProcessDAO implements IADProcessDAO
{
	private static final transient Logger logger = LogManager.getLogger(ADProcessDAO.class);

	private final RelatedProcessDescriptorMap staticRelatedProcessDescriptors = new RelatedProcessDescriptorMap();

	@Override
	public int retrieveProcessIdByClass(final Class<?> processClass)
	{
		final int processId = retriveProcessIdByClassIfUnique(Env.getCtx(), processClass);
		Check.errorIf(processId <= 0, "Could not retrieve a singe AD_Process_ID for processClass={}", processClass);
		return processId;
	}

	@Override
	public int retriveProcessIdByClassIfUnique(final Properties ctx, final Class<?> processClass)
	{
		final String processClassname = processClass.getName();
		return retriveProcessIdByClassIfUnique(ctx, processClassname);
	}

	@Override
	@Cached(cacheName = I_AD_Process.Table_Name + "#by#Classname", expireMinutes = Cached.EXPIREMINUTES_Never)
	public int retriveProcessIdByClassIfUnique(@CacheCtx final Properties ctx, final String processClassname)
	{
		final List<Integer> processIds = Services.get(IQueryBL.class)
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
			// we are logging a warning because it's not a common case.
			final int adProcessId = -1;
			logger.warn("retriveProcessIdByClassIfUnique: More then one AD_Process_ID found for {}: {} => considering {}", processClassname, processIds, adProcessId);
			return adProcessId;
		}
	}

	@Override
	public Optional<ITranslatableString> retrieveProcessNameByClassIfUnique(final Class<?> processClass)
	{
		final Properties ctx = Env.getCtx();
		final int processId = retriveProcessIdByClassIfUnique(ctx, processClass);
		if (processId <= 0)
		{
			return Optional.empty();
		}

		final I_AD_Process process = retrieveProcessById(ctx, processId);
		final ITranslatableString name = InterfaceWrapperHelper.getModelTranslationMap(process)
				.getColumnTrl(I_AD_Process.COLUMNNAME_Name, process.getName());
		return Optional.of(name);
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
	public void registerTableProcess(final int adTableId, final int adWindowId, final int adProcessId)
	{
		final RelatedProcessDescriptor newDescriptor = staticRelatedProcessDescriptors.register(adTableId, adWindowId, adProcessId);

		if (newDescriptor == null)
		{
			// NOTE: not sure this shall be a WARN, but for sure is a rare case.
			logger.warn("Skip because already registered for adTableId={}, adWindowId={}, adProcessId={}", adTableId, adWindowId, adProcessId);
			return;
		}
		else
		{
			logger.info("Registered table process: {}", newDescriptor);

			//
			CacheMgt.get().reset(I_AD_Table_Process.Table_Name);
		}
	}

	@Override
	public void registerTableProcess(final String tableName, final int adWindowId, final int adProcessId)
	{
		final int adTableId;
		if (Check.isEmpty(tableName))
		{
			adTableId = RelatedProcessDescriptorMap.AD_Table_ID_Any;
		}
		else
		{
			adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
			Check.assume(adTableId > 0, "adTableId > 0 (TableName={})", tableName);
		}

		registerTableProcess(adTableId, adWindowId, adProcessId);
	}

	@Override
	public void registerTableProcess(final RelatedProcessDescriptor descriptor)
	{
		final RelatedProcessDescriptor previousDescriptor = staticRelatedProcessDescriptors.register(descriptor);

		if (previousDescriptor != null)
		{
			logger.info("Unregistered table process: {}", descriptor);
		}
		logger.info("Registered table process: {}", descriptor);

		//
		CacheMgt.get().reset(I_AD_Table_Process.Table_Name);
	}

	@Override
	public final List<I_AD_Process> retrieveReportProcessesForTable(final Properties ctx, final String tableName)
	{
		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		final int adWindowId = 0; // any
		final Map<Integer, RelatedProcessDescriptor> relatedProcesses = retrieveRelatedProcessesForTableIndexedByProcessId(ctx, adTableId, adWindowId);
		final Set<Integer> relatedProcessIds = relatedProcesses.keySet();

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_AD_Process.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_AD_Process.COLUMN_AD_Process_ID, relatedProcessIds)
				.addEqualsFilter(I_AD_Process.COLUMN_IsReport, true)
				//
				.orderBy()
				.addColumn(I_AD_Process.COLUMNNAME_Name)
				.endOrderBy()
				//
				.create()
				.list(I_AD_Process.class);
	}

	@Override
	@Cached(cacheName = I_AD_Table_Process.Table_Name + "#RelatedProcessDescriptors")
	public Map<Integer, RelatedProcessDescriptor> retrieveRelatedProcessesForTableIndexedByProcessId(@CacheCtx final Properties ctx, final int adTableId, final int adWindowId)
	{
		final Map<Integer, RelatedProcessDescriptor> result = new HashMap<>();

		//
		// Get the programmatically registered ones
		{
			final Map<Integer, RelatedProcessDescriptor> relatedProcessesStatic = staticRelatedProcessDescriptors.getIndexedByProcessId(adTableId, adWindowId);
			result.putAll(relatedProcessesStatic);
		}

		//
		// Fetch related processes from database and override the ones which we have registered here
		{
			final IQueryBL queryBL = Services.get(IQueryBL.class);
			final Map<Integer, RelatedProcessDescriptor> relatedProcessesFromAppDict = queryBL.createQueryBuilder(I_AD_Table_Process.class, ctx, ITrx.TRXNAME_None)
					.addEqualsFilter(I_AD_Table_Process.COLUMN_AD_Table_ID, adTableId)
					.addInArrayFilter(I_AD_Table_Process.COLUMN_AD_Window_ID, null, adWindowId)
					.addOnlyActiveRecordsFilter()
					//
					.orderBy()
					.addColumn(I_AD_Table_Process.COLUMN_AD_Window_ID, Direction.Ascending, Nulls.Last) // important: keep the record without AD_Window_ID as last
					.endOrderBy()
					//
					.create()
					.stream()
					.map(tableProcess -> createRelatedProcessDescriptor(tableProcess))
					.collect(GuavaCollectors.toImmutableMapByKeyKeepFirstDuplicate(RelatedProcessDescriptor::getProcessId));

			// Add all to result, overriding existing ones
			result.putAll(relatedProcessesFromAppDict);
		}

		return ImmutableMap.copyOf(result);
	}

	private static final RelatedProcessDescriptor createRelatedProcessDescriptor(final I_AD_Table_Process tableProcess)
	{
		return RelatedProcessDescriptor.builder()
				.processId(tableProcess.getAD_Process_ID())
				.tableId(tableProcess.getAD_Table_ID())
				.windowId(tableProcess.getAD_Window_ID())
				.webuiQuickAction(tableProcess.isWEBUI_QuickAction())
				.webuiDefaultQuickAction(tableProcess.isWEBUI_QuickAction_Default())
				.build();
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
				.aggregate(I_AD_Process_Para.COLUMNNAME_SeqNo, Aggregate.MAX, Integer.class);
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

	@ToString
	private static final class RelatedProcessDescriptorMap
	{
		private static final int AD_Table_ID_Any = 0;
		private static final int AD_Window_ID_Any = 0;
		private final Map<ArrayKey, Map<Integer, RelatedProcessDescriptor>> key2processId2descriptor = new ConcurrentHashMap<>();

		/**
		 * @return newly registered descriptor or null if it was not registered
		 */
		public RelatedProcessDescriptor register(final int adTableId, final int adWindowId, final int adProcessId)
		{
			// supplies a new descriptor if asked
			final ExtendedMemorizingSupplier<RelatedProcessDescriptor> newDescriptor = ExtendedMemorizingSupplier.of(() -> RelatedProcessDescriptor.builder()
					.processId(adProcessId)
					.tableId(adTableId)
					.windowId(adWindowId)
					.build());

			// NOTE: never override
			final ArrayKey key = mkKey(adTableId, adWindowId);
			final Map<Integer, RelatedProcessDescriptor> processId2descriptor = key2processId2descriptor.computeIfAbsent(key, k -> new ConcurrentHashMap<>());
			processId2descriptor.computeIfAbsent(adProcessId, k -> newDescriptor.get());

			return newDescriptor.peek();
		}

		/**
		 * @return previous descriptor or null
		 */
		public RelatedProcessDescriptor register(final RelatedProcessDescriptor descriptor)
		{
			Preconditions.checkNotNull(descriptor, "descriptor not null");

			// NOTE: always override
			final ArrayKey key = mkKey(descriptor.getTableId(), descriptor.getWindowId());
			final Map<Integer, RelatedProcessDescriptor> processId2descriptor = key2processId2descriptor.computeIfAbsent(key, k -> new ConcurrentHashMap<>());
			return processId2descriptor.put(descriptor.getProcessId(), descriptor);
		}

		private Stream<RelatedProcessDescriptor> streamDescriptorsForKey(final ArrayKey key)
		{
			final Map<Integer, RelatedProcessDescriptor> descriptorsMap = key2processId2descriptor.get(key);
			if (descriptorsMap == null || descriptorsMap.isEmpty())
			{
				return Stream.empty();
			}
			return descriptorsMap.values().stream();
		}

		private static final ArrayKey mkKey(final int adTableId, final int adWindowId)
		{
			return ArrayKey.of(adTableId > 0 ? adTableId : AD_Table_ID_Any, adWindowId > 0 ? adWindowId : AD_Window_ID_Any);
		}

		private static final ImmutableSet<ArrayKey> mkKeysFromSpecificToGeneral(final int adTableId, final int adWindowId)
		{
			return ImmutableSet.of(
					mkKey(adTableId, adWindowId) //
					, mkKey(adTableId, AD_Window_ID_Any) //
					, mkKey(AD_Table_ID_Any, adWindowId) //
			// , mkKey(AD_Table_ID_Any, AD_Window_ID_Any) // this case shall not be allowed
			);
		}

		public Map<Integer, RelatedProcessDescriptor> getIndexedByProcessId(final int adTableId, final int adWindowId)
		{
			return mkKeysFromSpecificToGeneral(adTableId, adWindowId)
					.stream().sequential()
					.flatMap(this::streamDescriptorsForKey)
					// collects RelatedProcessDescriptor(s) indexed by AD_Process_ID
					// in case of duplicates, first descriptor will be kept,
					// i.e. the one which was more specifically registered (specific adTableId/adWindowId).
					.collect(GuavaCollectors.toImmutableMapByKeyKeepFirstDuplicate(desc -> desc.getProcessId()));
		}

	}
}
