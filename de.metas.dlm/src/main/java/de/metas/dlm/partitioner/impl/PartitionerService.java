package de.metas.dlm.partitioner.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.UpperCaseQueryFilterModifier;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.Mutable;
import org.compiere.Adempiere;
import org.compiere.model.IQuery;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.compiere.util.TrxRunnableAdapter;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.service.IColumnBL;
import de.metas.connection.IConnectionCustomizerService;
import de.metas.connection.ITemporaryConnectionCustomizer;
import de.metas.dlm.IDLMService;
import de.metas.dlm.Partition;
import de.metas.dlm.Partition.WorkQueue;
import de.metas.dlm.connection.DLMConnectionCustomizer;
import de.metas.dlm.exception.DLMReferenceException;
import de.metas.dlm.exception.TableNotAddedToDLMException;
import de.metas.dlm.migrator.IMigratorService;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_AD_Table;
import de.metas.dlm.model.I_DLM_Partition;
import de.metas.dlm.model.I_DLM_Partition_Record_V;
import de.metas.dlm.partitioner.IIterateResult;
import de.metas.dlm.partitioner.IIterateResultHandler;
import de.metas.dlm.partitioner.IPartitionerService;
import de.metas.dlm.partitioner.IRecordCrawlerService;
import de.metas.dlm.partitioner.PartitionRequestFactory;
import de.metas.dlm.partitioner.PartitionRequestFactory.CreatePartitionRequest;
import de.metas.dlm.partitioner.PartitionRequestFactory.CreatePartitionRequest.OnNotDLMTable;
import de.metas.dlm.partitioner.config.PartitionConfig;
import de.metas.dlm.partitioner.config.PartitionConfig.Builder;
import de.metas.dlm.partitioner.config.PartitionerConfigLine;
import de.metas.dlm.partitioner.config.PartitionerConfigReference;
import de.metas.dlm.partitioner.config.TableReferenceDescriptor;
import de.metas.logging.LogManager;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class PartitionerService implements IPartitionerService
{

	private final transient Logger logger = LogManager.getLogger(getClass());

	@Override
	public List<Partition> createPartition(final CreatePartitionRequest request)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final ITrxRunConfig trxRunnableConfig = trxManager.newTrxRunConfigBuilder()
				.setAutoCommit(true) // there will be a lot of selects, and we don't want them to accumulate locks.
				.build();

		final Mutable<List<Partition>> result = new Mutable<>();

		final IConnectionCustomizerService connectionCustomizerService = Services.get(IConnectionCustomizerService.class);

		try (final AutoCloseable temporaryCustomizer = connectionCustomizerService.registerTemporaryCustomizer(createConnectionCustomizer()))
		{
			trxManager.run(ITrx.TRXNAME_None,
					trxRunnableConfig,
					new TrxRunnableAdapter()
					{
						@Override
						public void run(final String localTrxName) throws Exception
						{
							final List<Partition> createdPartitions = createPartition0(request);
							result.setValue(createdPartitions);
						}
					});
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
		return result.getValue();
	}

	@VisibleForTesting
	/* package */ List<Partition> createPartition0(final CreatePartitionRequest request)
	{
		final PartitionConfig config = request.getConfig();

		// we need to get to the "first" line(s),
		// i.e. those DLM_PartitionLine_Configs that are not referenced via any DLM_PartitionReference_Config.DLM_Referencing_PartitionLine_Config_ID
		// i think we can also live with circles, i.e. if there is no "first", but for those "firsts" we should pick one and start with it
		// also, for the case of >1 "firsts", we need to be able to backtrack
		final List<PartitionerConfigLine> lines = config.getLines(); // DLM_PartitionLine_Config

		if (lines.isEmpty())
		{
			return Collections.singletonList(new Partition().withConfig(config)); // return one empty partition
		}

		// make sure the tables of which we might add records are all ready for DLM
		checkIfAllTablesAreDLM(lines, request.getOnNotDLMTable());

		final Map<Integer, Partition> id2Partition = new HashMap<>();
		final PlainContextAware ctxAware = PlainContextAware.newWithThreadInheritedTrx(Env.getCtx());

		if (request.getRecordToAttach() != null)
		{
			final ITableRecordReference tableRef = request.getRecordToAttach();
			final IDLMAware record = tableRef.getModel(ctxAware, IDLMAware.class);
			if (record != null)
			{
				final Partition partition = attachToPartitionAndCheck(
						request,
						mkIterateResult(
								Collections.singletonList(WorkQueue.of(ITableRecordReference.FromModelConverter.convert(record))).iterator(),
								null,
								ctxAware));
				id2Partition.put(partition.getDLM_Partition_ID(), partition);
			}
		}

		if (request.getPartitionToComplete() != null)
		{
			final I_DLM_Partition partitionDB = request.getPartitionToComplete();

			final Iterator<WorkQueue> queue = PartitionerTools.loadQueue(partitionDB.getDLM_Partition_ID(), ctxAware);

			final Partition partition = attachToPartitionAndCheck(
					request,
					mkIterateResult(
							queue,
							null,
							ctxAware));
			id2Partition.put(partition.getDLM_Partition_ID(), partition);
		}

		if (request.getRecordToAttach() == null && request.getPartitionToComplete() == null)
		{
			Loggables.get().addLog("The request does not explicitly tell us where to start; request={}", request);
			final Iterator<WorkQueue> incompletePartitionQueue = retrieveIncompletePartitionOrNull(ctxAware);
			if (incompletePartitionQueue != null)
			{
				Loggables.get().addLog("Working with an inclomplete partition");
				final Partition partition = attachToPartitionAndCheck(
						request,
						mkIterateResult(
								incompletePartitionQueue,
								null,
								ctxAware));
				id2Partition.put(partition.getDLM_Partition_ID(), partition);
			}
			else
			{
				Loggables.get().addLog("Iterating the config's lines and starting with on one record for each line");
				// iterate the lines and look for the first record out o
				for (final PartitionerConfigLine line : lines)
				{
					final IDLMAware record = retrieveUnpartitionedRecord(line.getTableName(), request.isOldestFirst());
					if (record == null)
					{
						continue;  // looks like we partitioned *every* record of the given table
					}
					final Partition partition = attachToPartitionAndCheck(request,
							mkIterateResult(
									Collections.singletonList(WorkQueue.of(ITableRecordReference.FromModelConverter.convert(record))).iterator(),
									null, // no exiting handlers
									ctxAware));
					id2Partition.put(partition.getDLM_Partition_ID(), partition);

					if (partition.isAborted())
					{
						Loggables.get().withLogger(logger, Level.WARN).addLog("Aborting while working on config line={}", line);
						break; // abort
					}
				}
			}
		}
		return new ArrayList<>(id2Partition.values());
	}

	private Iterator<WorkQueue> retrieveIncompletePartitionOrNull(final PlainContextAware ctxAware)
	{
		for (int i = 0; i < 50; i++)
		{
			final I_DLM_Partition incompletePartitionDB = Services.get(IQueryBL.class).createQueryBuilder(I_DLM_Partition.class, ctxAware)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_DLM_Partition.COLUMN_IsPartitionComplete, false)
					.orderBy().addColumn(I_DLM_Partition.COLUMN_DLM_Partition_ID).endOrderBy()
					.create()
					.first();

			if (incompletePartitionDB == null)
			{
				return null;
			}

			final Iterator<WorkQueue> queue = PartitionerTools.loadQueue(incompletePartitionDB.getDLM_Partition_ID(), ctxAware);
			if (queue.hasNext())
			{
				return queue;
			}
			else
			{
				logger.warn("{} is flagged as IsPartitionComplete='N', but has no WorkQueue records. Updating to IsPartitionComplete='Y'", incompletePartitionDB);
				incompletePartitionDB.setIsPartitionComplete(true);
				InterfaceWrapperHelper.save(incompletePartitionDB);
			}
		}
		return null;
	}

	/**
	 *
	 * @param request
	 * @param initialResult
	 * @return never {@code null}.
	 */
	private Partition attachToPartitionAndCheck(
			final CreatePartitionRequest request,
			final CreatePartitionIterateResult initialResult)
	{
		final PartitionConfig config = request.getConfig();

		final CreatePartitionIterateResult result = attachToPartition(initialResult, config);
		final Partition partition = result.getPartition();

		if (result.isHandlerSignaledToStop())
		{
			return partition.withAborted(true);
		}

		try
		{
			// now figure out if records are missing:
			// update each records' DLM_Level to 2 (2="test").
			final IMigratorService migratorService = Services.get(IMigratorService.class);

			Loggables.get().withLogger(logger, Level.INFO).addLog("Calling testMigratePartition with partition={}", partition);
			migratorService.testMigratePartition(partition);
		}
		catch (final DLMReferenceException e)
		{
			final TableReferenceDescriptor descriptor = e.getTableReferenceDescriptor();

			// if there is a DLMException, then depending on our config (LATER),
			// throw an exception (LATER),
			// skip the record (LATER)
			// or add another PartitionerConfigLine, get the additional line's records and retry.
			Loggables.get().withLogger(logger, Level.INFO).addLog("Caught {}; going to retry with an augmented config that also includes referencingTable={}", e.toString(), descriptor.getReferencingTableName());

			final PartitionConfig newConfig = augmentPartitionerConfig(config, Collections.singletonList(descriptor));
			storeOutOfTrx(newConfig); // store the new config so that even if we fail later on, the info is preserved

			// when adding another PartitionerConfigLine but the table is not DLM'ed yet, then DLM it on the fly.
			checkIfTableIsDLM(descriptor.getReferencingTableName(), request.getOnNotDLMTable());

			// call this method again, i.e. start over with our augmented config
			final CreatePartitionRequest newRequest = PartitionRequestFactory
					.builder(request)
					.setConfig(newConfig)
					.build();

			final String referencedTableName = descriptor.getReferencedTableName();

			// Check.errorUnless(partition.getRecordsWithTable(referencedTableName).isEmpty(),
			// "partition.getRecordsWithTable({}) should return an empty list because we stored & flushed this before we did the testmigration invokation that lead us into this catch-block; partition={}",
			// referencedTableName, partition);

			// retrieve all the records that might also be referenced from outside the partition via the new partitioner config augment.
			final Iterator<WorkQueue> recordReferencesForTable = loadForTable(partition, referencedTableName);
			final CreatePartitionIterateResult iterateResult = mkIterateResult(recordReferencesForTable,
					initialResult.getRegisteredHandlers(),
					PlainContextAware.newWithThreadInheritedTrx());

			iterateResult.clearAfterPartitionStored(partition); // to set "partition" as the result's partition
			return attachToPartitionAndCheck(newRequest, iterateResult);
		}

		final String msg = "Returning a newly identified partition={}.";
		logger.info(msg, partition);
		Loggables.get().addLog(msg, partition);

		return partition;
	}

	private CreatePartitionIterateResult mkIterateResult(final Iterator<WorkQueue> recordReferencesForTable,
			final List<IIterateResultHandler> existingHandlers,
			final IContextAware ctxAware)
	{
		final CreatePartitionIterateResult newResult = new CreatePartitionIterateResult(recordReferencesForTable, ctxAware);

		if (existingHandlers == null)
		{
			newResult.registerHandler(new SalesPurchaseWatchDog());
		}
		else
		{
			existingHandlers.forEach(h -> newResult.registerHandler(h));
		}

		return newResult;
	}

	private void checkIfAllTablesAreDLM(final List<PartitionerConfigLine> lines, final OnNotDLMTable onNotDLMTable)
	{
		final Set<String> checkedTableNames = new HashSet<>();
		for (final PartitionerConfigLine line : lines)
		{
			if (checkedTableNames.add(line.getTableName()))
			{
				checkIfTableIsDLM(line.getTableName(), onNotDLMTable);
			}

			for (final PartitionerConfigReference ref : line.getReferences())
			{
				if (checkedTableNames.add(ref.getReferencedTableName()))
				{
					checkIfTableIsDLM(ref.getReferencedTableName(), onNotDLMTable);
				}
			}
		}
	}

	private IDLMAware retrieveUnpartitionedRecord(
			final String tableName,
			final boolean oldestFirst)
	{
		Check.assumeNotNull(tableName, "Parameter 'tableName' is not null");

		final IColumnBL columnBL = Services.get(IColumnBL.class);

		// we will order by key column names, so that generally, older records are partitioned first. But note that a particular order is not a "must"
		// so, we don't need to have a single key column here, but later, we do need it, so let's fail early in case the given table does not yet have a single key column.
		final String keyColumnName = columnBL.getSingleKeyColumn(tableName);

		// AD_Table_ID
		// get the "record" from DLM_PartitionLine_Config.AD_Table_ID as the database record with the smallest ID
		// that does not yet have a DLM_Partition_ID
		final IDLMAware record = Services.get(IQueryBL.class)
				.createQueryBuilder(IDLMAware.class, tableName, PlainContextAware.newWithThreadInheritedTrx(Env.getCtx()))
				// .addOnlyActiveRecordsFilter() we want to partition both active and inactive records
				.addEqualsFilter(IDLMAware.COLUMNNAME_DLM_Partition_ID, null)
				.orderBy()
				.addColumn(keyColumnName, oldestFirst)
				.endOrderBy()
				.create()
				.first();
		logger.debug("Retrieved unpartitioned IDLMAware={} from table={}", record, tableName);
		return record;
	}

	private void storeOutOfTrx(final PartitionConfig newConfig)
	{
		Services.get(ITrxManager.class).run(new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				Services.get(IDLMService.class).storePartitionConfig(newConfig);
			}
		});
	}

	private void checkIfTableIsDLM(final String tableName, final OnNotDLMTable onNotDLMTable)
	{
		if (Adempiere.isUnitTestMode())
		{
			return;
		}

		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		// don't use IADTableDAO, because in this particular case, we don't want the table's trxName to be "NONE"
		final I_AD_Table referencingTable = queryBL.createQueryBuilder(I_AD_Table.class, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(org.compiere.model.I_AD_Table.COLUMNNAME_TableName, tableName, UpperCaseQueryFilterModifier.instance)
				.create()
				.firstOnly(I_AD_Table.class);
		Check.errorIf(referencingTable == null, "I_AD_Table record for tableName={} is null", tableName); // this can happen in unit test mode

		if (referencingTable.isDLM())
		{
			return; // nothing to do
		}

		if (onNotDLMTable == OnNotDLMTable.FAIL)
		{
			throw new TableNotAddedToDLMException(referencingTable);
		}

		//
		// then the the table, and if necessary, DLM it.
		//
		trxManager.run(new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				final IDLMService dlmService = Services.get(IDLMService.class);

				if (!referencingTable.isDLM())
				{
					final String msg = "ReferencingTable={} is not yet DLM'ed; doing it now";
					logger.info(msg, tableName);
					Loggables.get().addLog(msg, tableName);

					dlmService.addTableToDLM(referencingTable);
				}
			}
		});
	}

	@Override
	public final PartitionConfig augmentPartitionerConfig(final PartitionConfig config,
			final List<TableReferenceDescriptor> descriptors)
	{
		final Builder builder = PartitionConfig.builder(config);

		descriptors.forEach(descriptor -> {

			final String referencingTableName = descriptor.getReferencingTableName();
			final String referencingColumnName = descriptor.getReferencingColumnName();
			final String referencedTableName = descriptor.getReferencedTableName();

			if (!config.isMissing(descriptor))
			{
				return; // nothing to do
			}

			// also make sure that there is a line for the referenced table
			// otherwise, the view DLM_Partition_Record_V and therefore the method loadForTable() doesn't work.
			if (!config.getLine(referencedTableName).isPresent())
			{
				builder
						.setChanged(true)
						.line(referencedTableName)
						.endLine();
			}

			builder
					.setChanged(true)
					.line(referencingTableName)
					.ref().setReferencingColumnName(referencingColumnName).setReferencedTableName(referencedTableName)
					.endRef()
					.endLine();

			Loggables.get().withLogger(logger, Level.INFO)
					.addLog("Added descriptor={} to the config with name={}", descriptor, config.getName());
		});

		return builder.build();
	}

	@Override
	public ITemporaryConnectionCustomizer createConnectionCustomizer()
	{
		// needs to be "TEST, because if it was "Live", then the "testmgiration" code would not be able to select and "pull back" records from "TEST" to "LIVE" after a successfull migration.
		// then records would accululate in "TEST" and the DLM trigger functions would fail to throw DLMExceptions as they should
		final int dlmLevel = IMigratorService.DLM_Level_TEST;

		final int dlmCoalesceLevel = IMigratorService.DLM_Level_LIVE; // records that were not yet given a DLM-Level shall be assumed to be "operational"
		final DLMConnectionCustomizer connectionCustomizer = DLMConnectionCustomizer.withLevels(dlmLevel, dlmCoalesceLevel);

		return connectionCustomizer;
	}

	@VisibleForTesting
	/* package */ CreatePartitionIterateResult attachToPartition(
			final CreatePartitionIterateResult initialResult,
			final PartitionConfig config)
	{
		final PlainContextAware ctxAware = PlainContextAware.newWithThreadInheritedTrx(Env.getCtx());

		final IRecordCrawlerService recordCrawlerService = Services.get(IRecordCrawlerService.class);

		final IIterateResult result = recordCrawlerService.crawl(
				config,
				ctxAware,
				initialResult);

		return (CreatePartitionIterateResult)result;
	}

	/**
	 * This method is inteded for unit testing only!
	 * Please don't use it in production, trying to load all records at once might blow your memory.
	 *
	 * @param partition
	 * @return
	 */
	@VisibleForTesting
	/* package */ Partition loadWithAllRecords(final Partition partition)
	{
		if (partition.getDLM_Partition_ID() <= 0)
		{
			return partition; // nothing to load
		}

		final IDLMService dlmService = Services.get(IDLMService.class);
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final I_DLM_Partition partitionDB = InterfaceWrapperHelper.create(Env.getCtx(), partition.getDLM_Partition_ID(), I_DLM_Partition.class, ITrx.TRXNAME_ThreadInherited);
		final Partition loadedPartition = dlmService.loadPartition(partitionDB);

		final Map<String, Collection<ITableRecordReference>> allRecords = new HashMap<>();

		final Map<String, List<ITableRecordReference>> collect = queryBL.createQueryBuilder(I_DLM_Partition_Record_V.class, PlainContextAware.newWithThreadInheritedTrx())
				.addEqualsFilter(I_DLM_Partition_Record_V.COLUMN_DLM_Partition_ID, partitionDB.getDLM_Partition_ID())
				.create()
				.list()
				.stream()
				.map(r -> ITableRecordReference.FromReferencedModelConverter.convert(r))
				.collect(Collectors.groupingBy(r -> r.getTableName()));

		collect.entrySet().stream()
				.forEach(e -> allRecords.put(e.getKey(), e.getValue()));

		return loadedPartition.withRecords(allRecords);
	}

	Iterator<WorkQueue> loadForTable(final Partition partition, final String tableName)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final int tableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);

		final Iterator<I_DLM_Partition_Record_V> iterator = queryBL.createQueryBuilder(I_DLM_Partition_Record_V.class, PlainContextAware.newWithThreadInheritedTrx())
				.addEqualsFilter(I_DLM_Partition_Record_V.COLUMN_DLM_Partition_ID, partition.getDLM_Partition_ID())
				.addEqualsFilter(I_DLM_Partition_Record_V.COLUMN_AD_Table_ID, tableId)
				.create()
				.setOption(IQuery.OPTION_GuaranteedIteratorRequired, true)
				.setOption(IQuery.OPTION_IteratorBufferSize, 5000)
				.iterate(I_DLM_Partition_Record_V.class);

		return new Iterator<WorkQueue>()
		{
			@Override
			public boolean hasNext()
			{
				return iterator.hasNext();
			}

			@Override
			public WorkQueue next()
			{
				return WorkQueue.of(ITableRecordReference.FromReferencedModelConverter.convert(iterator.next()));
			}

			@Override
			public String toString()
			{
				return "PartitionerService.loadForTable() [partition=" + partition + "; tableName=" + tableName + ", iterator=" + iterator + "]";
			}
		};
	}
}
