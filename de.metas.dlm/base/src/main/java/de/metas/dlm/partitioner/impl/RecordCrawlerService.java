package de.metas.dlm.partitioner.impl;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;

import de.metas.adempiere.service.IColumnBL;
import de.metas.dlm.IDLMService;
import de.metas.dlm.Partition;
import de.metas.dlm.migrator.IMigratorService;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_DLM_Partition;
import de.metas.dlm.model.I_DLM_Partition_Workqueue;
import de.metas.dlm.partitioner.IRecordCrawlerService;
import de.metas.dlm.partitioner.config.PartitionConfig;
import de.metas.dlm.partitioner.config.PartitionerConfigLine;
import de.metas.dlm.partitioner.config.PartitionerConfigReference;
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

public class RecordCrawlerService implements IRecordCrawlerService
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	@Override
	public IIterateResult crawl(
			final PartitionConfig config,
			final IContextAware ctxAware,
			final IIterateResult result)
	{

		final IColumnBL columnBL = Services.get(IColumnBL.class);
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		// store what we are setting out to do here.
		// E.g. if we are called from a DLMException, we want the situation - partition is not complete because testMigrate failed, and there are e.g. 20 orderlines to backtrack from - to be stored here.
		// otherwise, the partiton we are in truth working on just now would be flagged as "completed" in the DB until further notice
		storeIterateResult(config, result, ctxAware);

		while (!result.isQueueEmpty())
		{
			final ITableRecordReference currentReference = result.nextFromQueue();
			final IDLMAware currentRecord = currentReference.getModel(ctxAware, IDLMAware.class);
			if (currentRecord == null)
			{
				continue;
			}

			if (shallStoreResult(result))
			{
				storeIterateResult(config, result, ctxAware);
			}

			final String currentTableName = currentReference.getTableName();
			final int currentRecordId = InterfaceWrapperHelper.getId(currentRecord);

			// there might or migth not be a line for the current reference's table name. That would mean that we can only search "backward"
			final Optional<PartitionerConfigLine> currentLineOrNull = config.getLine(currentTableName);

			if (currentLineOrNull.isPresent())
			{
				final List<PartitionerConfigReference> forwardRefs = currentLineOrNull.get().getReferences();

				// look FORWARD
				//
				// look at all the records that are referenced by 'currentRecord' and add them to 'hull',
				// but only add them if they were not yet identified as parts of this partition (i.e. not yet added to 'records').
				for (final PartitionerConfigReference forwardRef : forwardRefs)
				{
					if (forwardRef.isPartitionBoundary())
					{
						continue; // don't follow it
					}

					// the table name for the foreign record which has 'foreignKey' as its ID
					final String forwardTableName = forwardRef.getReferencedTableName();
					final String forwardColumnName = forwardRef.getReferencingColumnName();

					// first check if this is all about a Record_ID/AD_Table_ID reference.
					// if that is the case, then we need to verify that the AD_Table_ID of 'record' actually points to the table named 'forwardTableName'
					if (columnBL.isRecordColumnName(forwardColumnName))
					{
						final String tableColumnName = columnBL.getTableColumnName(currentTableName, forwardColumnName)
								.orElseThrow(Check.supplyEx("Table={} has no table column name for recordColumnName={}", currentTableName, forwardColumnName));

						final Integer tableId = InterfaceWrapperHelper.getValueOrNull(currentRecord, tableColumnName);
						if (tableId == null || tableId <= 0)
						{
							logger.trace("{}[{}] forward: the column={} does not reference any table; skipping", currentTableName, currentRecordId, forwardColumnName, forwardTableName, tableColumnName);
							continue;
						}

						final String tableName = adTableDAO.retrieveTableName(tableId);
						if (!tableName.equals(forwardTableName))
						{
							logger.trace("{}[{}] forward: the column={} does not reference a {}-record, but a {}-record; skipping", currentTableName, currentRecordId, forwardColumnName, forwardTableName, tableName);
							continue;
						}
					}

					// get the foreign key ID of
					// table DLM_PartitionLine_Config.AD_Table_ID,
					// column DLM_PartitionReference_Config.DLM_Referencing_Column_ID
					final Integer forwardKey = InterfaceWrapperHelper.getValueOrNull(currentRecord, forwardColumnName);
					if (forwardKey == null || forwardKey <= 0)
					{
						logger.trace("{}[{}] forward: the column={} does not reference anything; skipping", currentTableName, currentRecordId, forwardColumnName);
						continue;
					}

					final TableRecordReference forwardReference = new TableRecordReference(forwardTableName, forwardKey);

					final boolean recordWasAlreadyAddedBefore = result.contains(forwardReference);
					if (recordWasAlreadyAddedBefore)
					{
						logger.trace("{}[{}] forward: ITableRecordReference={} was already added in a previous iteration. Returning", currentTableName, currentRecordId, forwardReference); // avoid circles and also avoid loading the whole PO again
					}
					else
					{
						// the foreign record was not yet added before. Load it now.
						final IDLMAware forwardRecord = forwardReference.getModel(ctxAware, IDLMAware.class);
						if (forwardRecord == null)
						{
							// this happens with our "minidump" where we left out the HUs
							logger.debug("{}[{}] forward: the record from table={} which we attempted to load via {}.{}={} is NULL",
									currentTableName, currentRecordId, forwardTableName, currentTableName, forwardColumnName, forwardKey);
							continue;
						}

						logger.debug("{}[{}] forward: loaded from table={} via {}.{}={}: referenced IDLMAware={}",
								currentTableName, currentRecordId, forwardTableName, currentTableName, forwardColumnName, forwardKey, forwardRecord);

						result.addReferencedRecord(currentReference, forwardReference, forwardRecord.getDLM_Partition_ID());
						if (forwardRecord.getDLM_Partition_ID() > 0)
						{
							// log why we do not search further using the new found foreign record
							logger.debug("{}[{}] forward: referenced IDLMAware={} already has DLM_Partition_ID={}",
									currentTableName, currentRecordId, forwardRecord, forwardRecord.getDLM_Partition_ID());
						}
					}
				}
			}

			// Look BACKWARD, i.e. get all config-references that point to 'currentTableName'.
			// Then, for each of them, load the records that reference 'currentRecord' via the respective config-reference.
			//
			final List<PartitionerConfigReference> backwardRefs = config.getReferences(currentTableName);
			for (final PartitionerConfigReference backwardRef : backwardRefs)
			{
				if (backwardRef.isPartitionBoundary())
				{
					continue;
				}

				final PartitionerConfigLine backwardLine = backwardRef.getParent();
				final String backwardTableName = backwardLine.getTableName();
				final String backwardColumnName = backwardRef.getReferencingColumnName();

				// load all records which reference foreignRecord
				// don't excluded records with DLM_Partition_ID>0 becase we might need to merge them into the partition we are currently building
				final IQueryBuilder<IDLMAware> queryBuilder = Services.get(IQueryBL.class)
						.createQueryBuilder(IDLMAware.class, backwardTableName, ctxAware)
						.addEqualsFilter(backwardRef.getReferencingColumnName(), currentRecordId);

				// if we have a case of AD_Table_ID/Record_ID,
				// then we need to make sure to only load records whose AD_Table_ID references currentRecord
				if (columnBL.isRecordColumnName(backwardColumnName))
				{
					// note that referencedTableColumnName = AD_Table_ID, in most cases
					final String referencedTableColumnName = columnBL.getTableColumnName(backwardTableName, backwardRef.getReferencingColumnName())
							.orElseThrow(Check.supplyEx("Table={} has no table column name for recordColumnName={}", backwardTableName, backwardColumnName));

					final int referencedTableID = adTableDAO.retrieveTableId(currentTableName);

					queryBuilder.addEqualsFilter(referencedTableColumnName, referencedTableID);
				}

				// avoid loading the records we already added before.
				// This doesn't work well. NOT IN is probably not very performant, see
				// http://stackoverflow.com/questions/7125291/postgresql-not-in-versus-except-performance-difference-edited-2
				// maybe it would become better when we do something along the lines of
				// https://www.datadoghq.com/blog/100x-faster-postgres-performance-by-changing-1-line/
				// at any rate it looks the postgresql jdbc driver "only" supports prepared statements with 65536 parameters,
				// Solutions:
				// 1. don't try to exclude anything and live with records beeing returned that we already saw earlier
				// 2. store the partition more regularly and add some sort of negative left-join to the query
				// {
				// final String columnName = columnBL.getSingleKeyColumn(backwardTableName);
				// final Collection<Integer> alreadyAddedBackwardIds = result.getIds(backwardTableName);
				//
				// queryBuilder.addNotInArrayFilter(columnName, alreadyAddedBackwardIds);
				// }

				// de.metas.dlm.partitioner.PartitionerTools.dumpHistogram(result)
				final List<IDLMAware> backwardRecords = queryBuilder
						.create()
						.list();

				for (final IDLMAware backwardRecord : backwardRecords)
				{
					InterfaceWrapperHelper.setTrxName(backwardRecord, ctxAware.getTrxName()); // we need this for MPinstance, because it explicitly ignores the trx it is loaded with in its constructor.

					final ITableRecordReference backwardTableRecordReference = ITableRecordReference.FromModelConverter.convert(backwardRecord);

					final boolean recordWasNotYetAddedBefore = result.addReferencingRecord(backwardTableRecordReference, currentReference, backwardRecord.getDLM_Partition_ID());

					// should not happen because we excluded alreadyAddedBackwardIds from the loading query
					// Check.errorUnless(recordWasNotYetAddedBefore, "{}[{}] backward: WAS ALREADY ADDED! - loaded referencing IDLMAware={} from table={} via {}.{}={}",
					// currentTableName, currentRecordId, backwardRecord, backwardTableName, backwardTableName, backwardColumnName, currentRecordId);
					if (recordWasNotYetAddedBefore)
					{
						// the foreign record was not yet added before. Add it now
						logger.debug("{}[{}] backward: loaded from table={} via {}.{}={}: referencing IDLMAware={}",
								currentTableName, currentRecordId, backwardTableName, backwardTableName, backwardColumnName, currentRecordId, backwardRecord);

						if (backwardRecord.getDLM_Partition_ID() > 0)
						{
							// log why we did not search further using the new found foreign record
							logger.debug("{}[{}] backward: referenced IDLMAware={} already has DLM_Partition_ID={}",
									currentTableName, currentRecordId, backwardRecord, backwardRecord.getDLM_Partition_ID());
						}
					}
					else
					{
						logger.trace("{}[{}] backward: ITableRecordReference={} was already added in a previous iteration. Returning", currentTableName, currentRecordId, backwardTableRecordReference);
					}
				}
			}
		}

		logger.info("Found {} records via config.name={}", result.size(), config.getName());
		storeIterateResult(config, result, ctxAware);
		return result;
	}

	private boolean shallStoreResult(final IIterateResult result)
	{
		// return true;
		final int maxSize = 100000;
		return result.size() > maxSize;
	}

	private void storeIterateResult(final PartitionConfig config,
			final IIterateResult result,
			final IContextAware ctxAware)
	{
		if (!(result instanceof IStorableIterateResult))
		{
			return; // nothing to store
		}
		Services.get(ITrxManager.class).run(new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				storeIterateResult0(config,
						(IStorableIterateResult)result,
						PlainContextAware.newWithTrxName(ctxAware.getCtx(), localTrxName));
			}
		});
	}

	/**
	 * Persists the given <code>result</code> to DB and invokes {@link CreatePartitionIterateResult#clearAfterPartitionStored(Partition)} to release memory.
	 * <p>
	 * This method is invoked in its own transaction via {@link ITrxManager#run(TrxRunnable)}.
	 *
	 * @param config not actually used in this method, but forwarded to the new {@link Partition} that <code>clearAfterPartitionStored</code> will be called with.
	 * @param result side-effect: the method will call {@link IterateResult#clearAfterPartitionStored(Partition))}, so the stored partition will be contained within the result
	 * @param ctxAware
	 * @return
	 */
	@VisibleForTesting
	/* package */ void storeIterateResult0(final PartitionConfig config,
			final IStorableIterateResult result,
			final IContextAware ctxAware)
	{
		if (result.getQueueRecordsToDelete().isEmpty() && result.getQueueRecordsToStore().isEmpty() && result.size() == 0)
		{
			// this can happen if a DLM_Partition_Record has IsCompletePartition=N but still doesn't have any workqueue records,
			// or if the respective referenced records were deleted meanwhile.
			// or if for a given work queue queue, there where no additional records found.
			logger.info("storeIterateResult: result={} is empty. Nothing to do", result);
			return;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final Partition resultPartition = result.getPartition() // the partiton returned by result might be empty, if this is the first time we are in this method
				.withConfig(config)
				.withComplete(result.isQueueEmpty())
				.withRecords(result.getTableName2Record());

		final Partition storedPartition;
		final Map<Integer, Set<ITableRecordReference>> dlmPartitionId2Record = result.getDlmPartitionId2Record();

		if (dlmPartitionId2Record.isEmpty())
		{
			logger.debug("storeIterateResult: Result={} has no records; config={}", result, config);
			storedPartition = Services.get(IDLMService.class).storePartition(
					resultPartition,
					false); // runInOwnTrx=false because this whole method is already running in its own transaction
		}
		else
		{
			// check the DLM_Partition_IDs we know about and see if there is one > 0, i.e. see if there is one already persisted parttion in the plan
			final Set<Integer> keySet = new HashSet<>(dlmPartitionId2Record.keySet());
			keySet.add(resultPartition.getDLM_Partition_ID());

			// commpares dlmPartitionIds by the size of there respective DLM_Partition records
			final Comparator<Integer> c = Comparator
					.comparing(dlmPartitionId -> InterfaceWrapperHelper
							.create(ctxAware.getCtx(), dlmPartitionId, I_DLM_Partition.class, ctxAware.getTrxName())
							.getPartitionSize());

			final Optional<Integer> firstKeyIfAny = keySet
					.stream()
					.filter(dlmPartitionId -> dlmPartitionId > 0) // we want the first partition ID that is already "persisted" in the DB
					.sorted(c.reversed()) // sort them by partition size so that the biggest existing partition can be left unchanged
					.findFirst();

			// in any case, store the partition, i.e. also persist the records that were found and added to result.getDlmPartitionId2Record()
			final int firstKey;
			if (firstKeyIfAny.isPresent())
			{
				// there is at least one stored partition
				firstKey = firstKeyIfAny.get();

				// update the existing parttion
				storedPartition = Services.get(IDLMService.class).storePartition(
						resultPartition
								.withDLM_Partition_ID(firstKey)
								.withTargetDLMLevel(IMigratorService.DLM_Level_NOT_SET)
								.withNextInspectionDate(null),
						false); // runInOwnTrx=false because this whole method is already running in its own transaction
			}
			else
			{
				// store a brand new partition
				storedPartition = Services.get(IDLMService.class).storePartition(
						resultPartition,
						false); // runInOwnTrx=false because this whole method is already running in its own transaction
				firstKey = storedPartition.getDLM_Partition_ID();
			}

			// now iterate the keyset and merge all other partitions with the one that has "firstKey" as it's ID
			final IDLMService dlmService = Services.get(IDLMService.class);

			keySet.stream()

					// no point loading all the records that already have the DLM_Partition_ID we want to update our records to
					.filter(dlmPartitionId -> dlmPartitionId != firstKey)

					// no point attempting to load the records for DLM_Partition_ID=0
					.filter(dlmPartitionId -> dlmPartitionId > 0)

					// for each partition ID, update the records that reference it to now reference "our" partittion
					.forEach(dlmPartitionId -> {
						dlmService.directUpdateDLMColumn(ctxAware, dlmPartitionId, IDLMAware.COLUMNNAME_DLM_Partition_ID, firstKey);

						// we know that the partitition with dlmPartitionId is now empty, so let's delete it
						queryBL.createQueryBuilder(I_DLM_Partition_Workqueue.class, ctxAware)
								.addEqualsFilter(I_DLM_Partition_Workqueue.COLUMN_DLM_Partition_ID, dlmPartitionId)
								.create()
								.updateDirectly()
								.addSetColumnValue(I_DLM_Partition_Workqueue.COLUMNNAME_DLM_Partition_ID, firstKey)
								.execute();

						final I_DLM_Partition emptyPartitionDB = InterfaceWrapperHelper.create(ctxAware.getCtx(), dlmPartitionId, I_DLM_Partition.class, ctxAware.getTrxName());
						InterfaceWrapperHelper.delete(emptyPartitionDB);
					});
		}

		// store and delete DLM_Partition_Workqueue records according to the records we processed and the records we newly added since the last time this method was called.
		{
			final Mutable<Integer> deletedSum = new Mutable<>(0);
			result.getQueueRecordsToDelete()
					.forEach(r -> {

						// It's not a must to delete them 1-by-1, but we can't just create one chuck with unknow size!
						// If we want to delete more than one at a time, we need to create chuncks of a fixed size that is less than 2^32.
						final int delete = queryBL.createQueryBuilder(I_DLM_Partition_Workqueue.class, ctxAware)
								.addEqualsFilter(I_DLM_Partition_Workqueue.COLUMN_DLM_Partition_Workqueue_ID, r.getDLM_Partition_Workqueue_ID())
								.create()
								.deleteDirectly();
						deletedSum.setValue(deletedSum.getValue() + delete);
					});
			logger.debug("storeIterateResult: Deleted {} DLM_Partition_Workqueue records", deletedSum.getValue());

			final Mutable<Integer> storedSum = new Mutable<>(0);
			result.getQueueRecordsToStore()
					.forEach(r -> {

						final ITableRecordReference tableRecordReference = r.getTableRecordReference();

						final I_DLM_Partition_Workqueue newQueueRecord = InterfaceWrapperHelper.newInstance(I_DLM_Partition_Workqueue.class);
						newQueueRecord.setDLM_Partition_ID(storedPartition.getDLM_Partition_ID());
						newQueueRecord.setAD_Table_ID(tableRecordReference.getAD_Table_ID());
						newQueueRecord.setRecord_ID(tableRecordReference.getRecord_ID());

						InterfaceWrapperHelper.save(newQueueRecord);

						r.setDLM_Partition_Workqueue_ID(newQueueRecord.getDLM_Partition_Workqueue_ID());

						storedSum.setValue(storedSum.getValue() + 1);
					});
			logger.debug("storeIterateResult: Stored {} DLM_Partition_Workqueue records", storedSum.getValue());
		}

		result.clearAfterPartitionStored(storedPartition);
	}

}
