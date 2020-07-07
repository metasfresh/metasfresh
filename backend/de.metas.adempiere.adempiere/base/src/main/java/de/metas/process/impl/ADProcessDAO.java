package de.metas.process.impl;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CacheMgt;
import de.metas.cache.annotation.CacheCtx;
import de.metas.cache.annotation.CacheTrx;
import de.metas.i18n.ITranslatableString;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.process.ProcessType;
import de.metas.process.RelatedProcessDescriptor;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Para;
import org.compiere.model.I_AD_Process_Stats;
import org.compiere.model.I_AD_Table_Process;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;

public class ADProcessDAO implements IADProcessDAO
{
	private static final transient Logger logger = LogManager.getLogger(ADProcessDAO.class);

	private final RelatedProcessDescriptorMap staticRelatedProcessDescriptors = new RelatedProcessDescriptorMap();

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_AD_Process getById(@NonNull final AdProcessId processId)
	{
		return InterfaceWrapperHelper.loadOutOfTrx(processId, I_AD_Process.class);
	}

	@Override
	public AdProcessId retrieveProcessIdByClass(final Class<?> processClass)
	{
		final AdProcessId processId = retrieveProcessIdByClassIfUnique(processClass);
		Check.errorIf(processId == null, "Could not retrieve a single AD_Process_ID for processClass={}", processClass);
		return processId;
	}

	@Override
	public AdProcessId retrieveProcessIdByClassIfUnique(final Class<?> processClass)
	{
		final String processClassname = processClass.getName();
		return retrieveProcessIdByClassIfUnique(processClassname);
	}

	@Override
	@Cached(cacheName = I_AD_Process.Table_Name + "#by#Classname", expireMinutes = Cached.EXPIREMINUTES_Never)
	public AdProcessId retrieveProcessIdByClassIfUnique(final String processClassname)
	{
		final Set<AdProcessId> processIds = queryBL
				.createQueryBuilderOutOfTrx(I_AD_Process.class)
				.addEqualsFilter(I_AD_Process.COLUMN_Classname, processClassname)
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds(AdProcessId::ofRepoId);

		if (processIds.isEmpty())
		{
			return null;
		}
		else if (processIds.size() == 1)
		{
			return processIds.iterator().next();
		}
		else
		{
			// more then one AD_Process_IDs matched => return -1
			// we are logging a warning because it's not a common case.
			final AdProcessId adProcessId = null;
			logger.warn("retriveProcessIdByClassIfUnique: More then one AD_Process_ID found for {}: {} => considering {}", processClassname, processIds, adProcessId);
			return adProcessId;
		}
	}

	@Override
	public Optional<ITranslatableString> retrieveProcessNameByClassIfUnique(final Class<?> processClass)
	{
		final AdProcessId processId = retrieveProcessIdByClassIfUnique(processClass);
		if (processId == null)
		{
			return Optional.empty();
		}

		final I_AD_Process process = getById(processId);
		final ITranslatableString name = InterfaceWrapperHelper.getModelTranslationMap(process)
				.getColumnTrl(I_AD_Process.COLUMNNAME_Name, process.getName());
		return Optional.of(name);
	}

	@Override
	public AdProcessId retrieveProcessIdByValue(final String processValue)
	{
		return queryBL
				.createQueryBuilderOutOfTrx(I_AD_Process.class)
				.addEqualsFilter(I_AD_Process.COLUMN_Value, processValue)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstIdOnly(AdProcessId::ofRepoIdOrNull);
	}

	@Override
	public I_AD_Process retrieveProcessByValue(final Properties ctx, final String processValue)
	{
		return queryBL
				.createQueryBuilder(I_AD_Process.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Process.COLUMN_Value, processValue)
				.addOnlyActiveRecordsFilter()
				.create()
				.firstOnlyNotNull(I_AD_Process.class);
	}

	@Override
	public void registerTableProcess(final int adTableId, final AdWindowId adWindowId, final AdProcessId adProcessId)
	{
		final AdTabId adTabId = null;
		final RelatedProcessDescriptorKey key = RelatedProcessDescriptorKey.of(adTableId, adWindowId, adTabId);
		registerTableProcess(key, adProcessId);
	}

	private void registerTableProcess(final RelatedProcessDescriptorKey key, final AdProcessId adProcessId)
	{
		final RelatedProcessDescriptor newDescriptor = staticRelatedProcessDescriptors.register(key, adProcessId);

		if (newDescriptor == null)
		{
			// NOTE: not sure this shall be a WARN, but for sure is a rare case.
			logger.warn("Skip because process ID={} is already registered for {}", adProcessId, key);
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
	public void registerTableProcess(
			final String tableName,
			final AdWindowId adWindowId,
			@NonNull final AdProcessId adProcessId)
	{
		final int adTableId;
		if (Check.isEmpty(tableName))
		{
			adTableId = -1;
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
	@Cached(cacheName = I_AD_Table_Process.Table_Name + "#RelatedProcessDescriptors")
	public ImmutableList<RelatedProcessDescriptor> retrieveRelatedProcessDescriptors(
			final int adTableId,
			@Nullable final AdWindowId adWindowId,
			@Nullable final AdTabId adTabId)
	{
		final RelatedProcessDescriptorKey key = RelatedProcessDescriptorKey.of(adTableId, adWindowId, adTabId);

		final Map<AdProcessId, RelatedProcessDescriptor> result = new HashMap<>();

		//
		// Get the programmatically registered ones
		{
			final Map<AdProcessId, RelatedProcessDescriptor> relatedProcessesStatic = staticRelatedProcessDescriptors.getIndexedByProcessId(key);
			result.putAll(relatedProcessesStatic);
		}

		//
		// Fetch related processes from database and override the ones which we have registered here
		{
			final ImmutableMap<AdProcessId, RelatedProcessDescriptor> relatedProcessesFromAppDict = queryBL.createQueryBuilderOutOfTrx(I_AD_Table_Process.class)
					.addEqualsFilter(I_AD_Table_Process.COLUMN_AD_Table_ID, key.getAdTableId())
					.addInArrayFilter(I_AD_Table_Process.COLUMN_AD_Window_ID, null, key.getAdWindowId())
					.addInArrayFilter(I_AD_Table_Process.COLUMN_AD_Tab_ID, null, key.getAdTabId())
					.addOnlyActiveRecordsFilter()
					//
					.orderBy()
					.addColumn(I_AD_Table_Process.COLUMN_AD_Window_ID, Direction.Ascending, Nulls.Last) // important: keep the record without AD_Window_ID as last
					.addColumn(I_AD_Table_Process.COLUMN_AD_Tab_ID, Direction.Ascending, Nulls.Last) // important: keep the record without AD_Tab_ID as last
					.endOrderBy()
					//
					.create()
					.stream()
					.map(tableProcess -> toRelatedProcessDescriptor(tableProcess))
					.collect(GuavaCollectors.toImmutableMapByKeyKeepFirstDuplicate(RelatedProcessDescriptor::getProcessId));

			// Add all to result, overriding existing ones
			result.putAll(relatedProcessesFromAppDict);
		}

		return ImmutableList.copyOf(result.values());
	}

	private static final RelatedProcessDescriptor toRelatedProcessDescriptor(final I_AD_Table_Process tableProcess)
	{
		return RelatedProcessDescriptor.builder()
				.processId(AdProcessId.ofRepoId(tableProcess.getAD_Process_ID()))
				.tableId(tableProcess.getAD_Table_ID())
				.windowId(AdWindowId.ofRepoIdOrNull(tableProcess.getAD_Window_ID()))
				.tabId(AdTabId.ofRepoIdOrNull(tableProcess.getAD_Tab_ID()))
				//
				.displayPlaceIfTrue(tableProcess.isWEBUI_DocumentAction(), DisplayPlace.SingleDocumentActionsMenu)
				.displayPlaceIfTrue(tableProcess.isWEBUI_IncludedTabTopAction(), DisplayPlace.IncludedTabTopActionsMenu)
				.displayPlaceIfTrue(tableProcess.isWEBUI_ViewAction(), DisplayPlace.ViewActionsMenu)
				.displayPlaceIfTrue(tableProcess.isWEBUI_ViewQuickAction(), DisplayPlace.ViewQuickActions)
				.webuiDefaultQuickAction(tableProcess.isWEBUI_ViewQuickAction_Default())
				//
				.webuiShortcut(tableProcess.getWEBUI_Shortcut())
				//
				.build();
	}

	@Override
	public I_AD_Process retrieveProcessByForm(final Properties ctx, final int AD_Form_ID)
	{
		return queryBL
				.createQueryBuilder(I_AD_Process.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Process.COLUMNNAME_AD_Form_ID, AD_Form_ID)
				.create()
				.firstOnly(I_AD_Process.class);
	}

	@Override
	public int retrieveProcessParaLastSeqNo(final I_AD_Process process)
	{
		return retrieveProcessParaLastSeqNo(process.getAD_Process_ID());
	}

	private int retrieveProcessParaLastSeqNo(final int processId)
	{
		final Integer lastSeqNo = queryBL.createQueryBuilder(I_AD_Process_Para.class)
				.addEqualsFilter(I_AD_Process_Para.COLUMNNAME_AD_Process_ID, processId)
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
		final AdProcessId adProcessId = AdProcessId.ofRepoId(process.getAD_Process_ID());
		return retrieveProcessParameters(ctx, adProcessId, trxName).values();
	}

	@Override
	public I_AD_Process_Para retrieveProcessParameter(final AdProcessId adProcessId, final String parameterName)
	{
		return retrieveProcessParameters(Env.getCtx(), adProcessId, ITrx.TRXNAME_None).get(parameterName);
	}

	@Cached(cacheName = I_AD_Process_Para.Table_Name + "#by#" + I_AD_Process_Para.COLUMNNAME_AD_Process_ID)
	public Map<String, I_AD_Process_Para> retrieveProcessParameters(@CacheCtx final Properties ctx, final AdProcessId adProcessId, @CacheTrx final String trxName)
	{
		return queryBL.createQueryBuilder(I_AD_Process_Para.class, ctx, trxName)
				.addEqualsFilter(I_AD_Process_Para.COLUMNNAME_AD_Process_ID, adProcessId)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_Process_Para.COLUMNNAME_SeqNo)
				.create()
				.map(I_AD_Process_Para.class, I_AD_Process_Para::getColumnName);
	}

	@Override
	public void addProcessStatistics(final AdProcessId adProcessId, final ClientId adClientId, final long durationMillisToAdd)
	{
		try
		{
			I_AD_Process_Stats processStats = queryBL
					.createQueryBuilderOutOfTrx(I_AD_Process_Stats.class)
					.addEqualsFilter(I_AD_Process_Stats.COLUMN_AD_Client_ID, adClientId)
					.addEqualsFilter(I_AD_Process_Stats.COLUMN_AD_Process_ID, adProcessId)
					.create()
					.firstOnly(I_AD_Process_Stats.class);
			if (processStats == null)
			{
				processStats = newInstanceOutOfTrx(I_AD_Process_Stats.class);
				InterfaceWrapperHelper.setValue(processStats, I_AD_Process_Stats.COLUMNNAME_AD_Client_ID, adClientId.getRepoId());
				processStats.setAD_Org_ID(Env.CTXVALUE_AD_Org_ID_Any);
				processStats.setAD_Process_ID(adProcessId.getRepoId());
			}

			final int count = processStats.getStatistic_Count();
			final int durationMillis = processStats.getStatistic_Millis();

			processStats.setStatistic_Count(count + 1);
			processStats.setStatistic_Millis((int)(durationMillis + durationMillisToAdd));
			InterfaceWrapperHelper.save(processStats);
		}
		catch (final Exception ex)
		{
			logger.error("Failed updating process statistics for AD_Process_ID={}, AD_Client_ID={}, DurationMillisToAdd={}. Ignored.", adProcessId, adClientId, durationMillisToAdd, ex);
		}
	}

	@Override
	public void copyProcess(@NonNull final AdProcessId targetProcessId, @NonNull final AdProcessId sourceProcessId)
	{
		final I_AD_Process targetProcess = load(targetProcessId, I_AD_Process.class);
		final I_AD_Process sourceProcess = load(sourceProcessId, I_AD_Process.class);

		logger.debug("Copying from: {} to: {}", sourceProcess, targetProcess);

		InterfaceWrapperHelper.copy()
				.setSkipCalculatedColumns(true)
				.addTargetColumnNameToSkip(I_AD_Process.COLUMNNAME_Value)
				.addTargetColumnNameToSkip(I_AD_Process.COLUMNNAME_Name)
				.setFrom(sourceProcess)
				.setTo(targetProcess)
				.copy();
		InterfaceWrapperHelper.save(targetProcess);

		copyProcessParameters(targetProcess, sourceProcessId);
	}

	@Override
	public void copyProcessParameters(final AdProcessId targetProcessId, final AdProcessId sourceProcessId)
	{
		final I_AD_Process targetProcess = load(targetProcessId, I_AD_Process.class);
		copyProcessParameters(targetProcess, sourceProcessId);
	}

	private void copyProcessParameters(final I_AD_Process targetProcess, final AdProcessId sourceProcessId)
	{
		final AdProcessId targetProcessId = AdProcessId.ofRepoId(targetProcess.getAD_Process_ID());
		final Map<String, I_AD_Process_Para> existingTargetParams = retrieveProcessParameters(Env.getCtx(), targetProcessId, ITrx.TRXNAME_ThreadInherited);
		final Collection<I_AD_Process_Para> sourceParams = retrieveProcessParameters(Env.getCtx(), sourceProcessId, ITrx.TRXNAME_ThreadInherited).values();

		for (final I_AD_Process_Para sourcePara : sourceParams)
		{
			final I_AD_Process_Para existingTargetParam = existingTargetParams.get(sourcePara.getColumnName());
			copyProcessPara(targetProcess, existingTargetParam, sourcePara);
		}
	}

	public void copyProcessPara(final I_AD_Process targetProcess, final I_AD_Process_Para existingTargetPara, final I_AD_Process_Para sourcePara)
	{
		logger.debug("Copying parameter from {} to {}", sourcePara, targetProcess);

		final int targetProcessId = targetProcess.getAD_Process_ID();
		final String entityType = targetProcess.getEntityType();
		final String columnName = sourcePara.getColumnName();

		final I_AD_Process_Para targetPara = existingTargetPara != null ? existingTargetPara : newInstance(I_AD_Process_Para.class);
		final boolean wasNew = InterfaceWrapperHelper.isNew(targetPara);

		InterfaceWrapperHelper.copy()
				.setFrom(sourcePara)
				.setTo(targetPara)
				.copy();
		targetPara.setAD_Org_ID(sourcePara.getAD_Org_ID());
		targetPara.setAD_Process_ID(targetProcessId);
		targetPara.setEntityType(entityType);

		if (targetPara.getSeqNo() <= 0)
		{
			final int lastSeqNo = retrieveProcessParaLastSeqNo(targetProcessId);
			targetPara.setSeqNo(lastSeqNo + 10);
		}

		InterfaceWrapperHelper.save(targetPara);

		if (wasNew)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("@Created@ {}", columnName);
		}
		else
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("@Updated@ {}", columnName);
		}

		//
		copyProcessParaTrl(targetPara.getAD_Process_Para_ID(), sourcePara.getAD_Process_Para_ID());
	}

	private void copyProcessParaTrl(final int targetProcessParaId, final int sourceProcessParaId)
	{
		Check.assumeGreaterThanZero(targetProcessParaId, "targetProcessParaId");
		Check.assumeGreaterThanZero(sourceProcessParaId, "sourceProcessParaId");

		// Delete newly created translations and copy translations from source
		// NOTE: don't use parameterized SQL queries because this script will be logged as a migration script (task

		final String sqlDelete = "DELETE FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = " + targetProcessParaId;
		final int countDelete = DB.executeUpdateEx(sqlDelete, ITrx.TRXNAME_ThreadInherited);
		logger.debug("AD_Process_Para_Trl deleted: {}", countDelete);

		final String sqlInsert = "INSERT INTO AD_Process_Para_Trl (AD_Process_Para_ID, AD_Language, " +
				" AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, Updated, UpdatedBy, " +
				" Name, Description, Help, IsTranslated) " +
				" SELECT " + targetProcessParaId + ", AD_Language, AD_Client_ID, AD_Org_ID, IsActive, Created, CreatedBy, " +
				" Updated, UpdatedBy, Name, Description, Help, IsTranslated " +
				" FROM AD_Process_Para_Trl WHERE AD_Process_Para_ID = " + sourceProcessParaId;
		final int countInsert = DB.executeUpdateEx(sqlInsert, ITrx.TRXNAME_ThreadInherited);
		logger.debug("AD_Process_Para_Trl inserted: {}", countInsert);
	}

	@ToString
	private static final class RelatedProcessDescriptorMap
	{
		private final ConcurrentHashMap<RelatedProcessDescriptorKey, ConcurrentHashMap<AdProcessId, RelatedProcessDescriptor>> key2processId2descriptor = new ConcurrentHashMap<>();

		/**
		 * @return newly registered descriptor or null if it was not registered
		 */
		public RelatedProcessDescriptor register(@NonNull final RelatedProcessDescriptorKey key, final AdProcessId adProcessId)
		{
			// supplies a new descriptor if asked
			final ExtendedMemorizingSupplier<RelatedProcessDescriptor> newDescriptor = ExtendedMemorizingSupplier.of(() -> RelatedProcessDescriptor.builder()
					.processId(adProcessId)
					.tableId(key.getAdTableId())
					.windowId(key.getAdWindowId())
					.tabId(key.getAdTabId())
					.displayPlace(DisplayPlace.SingleDocumentActionsMenu)
					.build());

			// NOTE: never override
			final Map<AdProcessId, RelatedProcessDescriptor> processId2descriptor = key2processId2descriptor.computeIfAbsent(key, k -> new ConcurrentHashMap<>());
			processId2descriptor.computeIfAbsent(adProcessId, k -> newDescriptor.get());

			return newDescriptor.peek();
		}

		/**
		 * @return previous descriptor or null
		 */
		public RelatedProcessDescriptor register(@NonNull final RelatedProcessDescriptor descriptor)
		{
			// NOTE: always override
			final RelatedProcessDescriptorKey key = RelatedProcessDescriptorKey.of(descriptor.getTableId(), descriptor.getWindowId(), descriptor.getTabId());
			final ConcurrentHashMap<AdProcessId, RelatedProcessDescriptor> processId2descriptor = key2processId2descriptor.computeIfAbsent(key, k -> new ConcurrentHashMap<>());
			return processId2descriptor.put(descriptor.getProcessId(), descriptor);
		}

		private Stream<RelatedProcessDescriptor> streamDescriptorsForKey(final RelatedProcessDescriptorKey key)
		{
			final ConcurrentHashMap<AdProcessId, RelatedProcessDescriptor> descriptorsMap = key2processId2descriptor.get(key);
			if (descriptorsMap == null || descriptorsMap.isEmpty())
			{
				return Stream.empty();
			}
			return descriptorsMap.values().stream();
		}

		public Map<AdProcessId, RelatedProcessDescriptor> getIndexedByProcessId(final RelatedProcessDescriptorKey key)
		{
			return RelatedProcessDescriptorKey.mkKeysFromSpecificToGeneral(key)
					.stream()
					.sequential()
					.flatMap(this::streamDescriptorsForKey)
					// collects RelatedProcessDescriptor(s) indexed by AD_Process_ID
					// in case of duplicates, first descriptor will be kept,
					// i.e. the one which was more specifically registered (specific adTableId/adWindowId).
					.collect(GuavaCollectors.toImmutableMapByKeyKeepFirstDuplicate(desc -> desc.getProcessId()));
		}

	}

	@Value
	private static class RelatedProcessDescriptorKey
	{
		public static final ImmutableSet<RelatedProcessDescriptorKey> mkKeysFromSpecificToGeneral(final RelatedProcessDescriptorKey key)
		{
			final int adTableId = key.getAdTableId();
			final AdWindowId adWindowId = key.getAdWindowId();
			final AdTabId adTabId = key.getAdTabId();

			return ImmutableSet.of(
					of(adTableId, adWindowId, adTabId),
					of(adTableId, AD_Window_ID_Any, AD_Tab_ID_Any),
					of(AD_Table_ID_Any, adWindowId, adTabId),
					ANY);
		}

		public static final RelatedProcessDescriptorKey of(final int adTableId, @Nullable final AdWindowId adWindowId, @Nullable final AdTabId adTabId)
		{
			if (adTableId <= 0 && adWindowId == null && adTabId == null)
			{
				return ANY;
			}

			return new RelatedProcessDescriptorKey(adTableId, adWindowId, adTabId);
		}

		private static final int AD_Table_ID_Any = 0;
		private static final AdWindowId AD_Window_ID_Any = null;
		private static final AdTabId AD_Tab_ID_Any = null;

		public static final RelatedProcessDescriptorKey ANY = new RelatedProcessDescriptorKey(AD_Table_ID_Any, AD_Window_ID_Any, AD_Tab_ID_Any);

		int adTableId;
		AdWindowId adWindowId;
		AdTabId adTabId;

		private RelatedProcessDescriptorKey(final int adTableId, final AdWindowId adWindowId, final AdTabId adTabId)
		{
			this.adTableId = adTableId > 0 ? adTableId : AD_Table_ID_Any;
			this.adWindowId = adWindowId;
			this.adTabId = adTabId;
		}

	}

	@Override
	public ITranslatableString getProcessNameById(final AdProcessId id)
	{
		final I_AD_Process process = getById(id);
		return InterfaceWrapperHelper.getModelTranslationMap(process)
				.getColumnTrl(I_AD_Process.COLUMNNAME_Name, process.getName());
	}

	@NonNull
	public ImmutableList<I_AD_Process> getProcessesByType(@NonNull final Set<ProcessType> processTypeSet)
	{
		final Set<String> types = processTypeSet.stream().map(ProcessType::getCode).collect(Collectors.toSet());

		return queryBL.createQueryBuilder(I_AD_Process.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_AD_Process.COLUMNNAME_Type, types)
				.create()
				.list()
				.stream()
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public ImmutableList<I_AD_Process_Para> getProcessParamsByProcessIds(@NonNull final Set<Integer> processIDs)
	{
		return queryBL.createQueryBuilder(I_AD_Process_Para.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_AD_Process_Para.COLUMNNAME_AD_Process_ID, processIDs)
				.create()
				.list()
				.stream()
				.collect(ImmutableList.toImmutableList());
	}
}
