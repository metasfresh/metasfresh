package de.metas.dlm.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.process.AD_Table_CreatePK;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.proxy.Cached;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import de.metas.adempiere.service.IColumnBL;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.dlm.IDLMService;
import de.metas.dlm.Partition;
import de.metas.dlm.migrator.IMigratorService;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_AD_Table;
import de.metas.dlm.model.I_DLM_Partition;
import de.metas.dlm.model.I_DLM_Partition_Config;
import de.metas.dlm.model.I_DLM_Partition_Config_Line;
import de.metas.dlm.model.I_DLM_Partition_Config_Reference;
import de.metas.dlm.model.I_DLM_Partition_Record_V;
import de.metas.dlm.partitioner.config.PartitionConfig;
import de.metas.dlm.partitioner.config.PartitionConfig.Builder;
import de.metas.dlm.partitioner.config.PartitionerConfigLine;
import de.metas.dlm.partitioner.config.PartitionerConfigLine.LineBuilder;
import de.metas.dlm.partitioner.config.PartitionerConfigReference;
import de.metas.dlm.partitioner.config.PartitionerConfigReference.RefBuilder;
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

public abstract class AbstractDLMService implements IDLMService
{

	private final transient Logger logger = LogManager.getLogger(getClass());

	@Override
	public void addTableToDLM(final I_AD_Table table)
	{
		final IColumnBL columnBL = Services.get(IColumnBL.class);

		final String trxName = InterfaceWrapperHelper.getTrxName(table);
		final String tableName = table.getTableName();

		// check if their is a single key column which is in the form of tablenName + "_ID"
		final String singleKeyColumn = columnBL.getSingleKeyColumn(tableName);
		Check.errorIf(!InterfaceWrapperHelper.getKeyColumnName(tableName).equals(singleKeyColumn),
				"Table={} (AD_Table_ID={}) has singleKeyColumn={}; instead it needs to have singleKeyColumn={}; Consider running the process {} to fix it.",
				table.getTableName(), table.getAD_Table_ID(), singleKeyColumn, InterfaceWrapperHelper.getKeyColumnName(tableName), AD_Table_CreatePK.class.getName());

		executeDBFunction_add_table_to_dlm(tableName, trxName); // make sure that the DB call and the changes to table take place in the same trx.

		createOrUpdateDlmColumn(table, IDLMAware.COLUMNNAME_DLM_Level);
		createOrUpdateDlmColumn(table, IDLMAware.COLUMNNAME_DLM_Partition_ID);

		table.setIsDLM(true);
		InterfaceWrapperHelper.save(table);

		Loggables.get().addLog("Table {} is now added to DLM", table.getTableName());
	}

	private void createOrUpdateDlmColumn(final I_AD_Table table, final String columnName)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		// retrieve the column if it exists, even with IsActive='N'
		final IQueryBuilder<I_AD_Column> columnQueryBuilder = adTableDAO
				.retrieveColumnQueryBuilder(table.getTableName(), columnName, ITrx.TRXNAME_ThreadInherited);

		I_AD_Column column = columnQueryBuilder.create().firstOnly(I_AD_Column.class);
		if (column == null)
		{
			column = InterfaceWrapperHelper.newInstance(I_AD_Column.class, table);
		}

		final I_AD_Element element = adTableDAO.retrieveElement(columnName);
		column.setAD_Element(element);
		column.setAD_Table(table);
		column.setColumnName(element.getColumnName());
		column.setName(element.getName());
		column.setDescription(element.getDescription());
		column.setHelp(element.getHelp());

		column.setAD_Reference_ID(DisplayType.Integer);
		column.setDDL_NoForeignKey(true); // doesn't really matter for DLM_Level, but is important for DLM_Partition_ID
		column.setIsActive(true);

		InterfaceWrapperHelper.save(column);
	}

	@Override
	public void removeTableFromDLM(final I_AD_Table table)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(table);

		executeDBFunction_remove_table_from_dlm(table.getTableName(), trxName); // make sure that the DB call and the changes to table take place in the same trx.
		deactivateDlmColumn(table, IDLMAware.COLUMNNAME_DLM_Level);
		deactivateDlmColumn(table, IDLMAware.COLUMNNAME_DLM_Partition_ID);

		table.setIsDLM(false);
		InterfaceWrapperHelper.save(table);
	}

	@Override
	public int directUpdateDLMColumn(final IContextAware ctxAware,
			final int dlmPartitionId,
			final String columnName,
			final int targetValue)
	{

		// get all DLMed table names. they all might contain a record that belongs to the given partition
		final Stream<IQueryBuilder<IDLMAware>> queryBuilders = retrieveDLMTableNames(ctxAware, dlmPartitionId);

		final Mutable<Integer> updatedSum = new Mutable<>(0);

		queryBuilders.forEach(queryBuilder -> {

			final int updated = queryBuilder
					.addNotEqualsFilter(columnName, targetValue) // exclude records that already have the target value
					.create()
					.updateDirectly()
					.addSetColumnValue(columnName, targetValue)
					.execute();

			logger.debug("Table {}: updated {} record(s) to {}={} (but not yet committed!)", queryBuilder.getModelTableName(), updated, columnName, targetValue);
			updatedSum.setValue(updatedSum.getValue() + updated);
		});

		return updatedSum.getValue();
	}

	@Override
	public Stream<IQueryBuilder<IDLMAware>> retrieveDLMTableNames(final IContextAware ctxAware, final int dlmPartitionId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final Stream<IQueryBuilder<IDLMAware>> result = queryBL.createQueryBuilder(I_AD_Table.class, ctxAware)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Table.COLUMNNAME_IsDLM, true)
				.orderBy().addColumn(org.compiere.model.I_AD_Table.COLUMNNAME_AD_Table_ID).endOrderBy()
				.create()
				.list()
				.stream()
				.map(t -> queryBL
						.createQueryBuilder(IDLMAware.class, t.getTableName(), ctxAware)
						// .addOnlyActiveRecordsFilter() we usually want all records
						.addEqualsFilter(IDLMAware.COLUMNNAME_DLM_Partition_ID, dlmPartitionId));

		return result;
	}

	private void deactivateDlmColumn(final I_AD_Table table, final String columnName)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final I_AD_Column column = adTableDAO.retrieveColumnOrNull(table.getTableName(), columnName);
		if (column == null)
		{
			return; // nothing to do
		}

		column.setIsActive(false);
		InterfaceWrapperHelper.save(column);
	}

	abstract void executeDBFunction_add_table_to_dlm(String tableName, String trxName);

	abstract void executeDBFunction_remove_table_from_dlm(String tableName, String trxName);

	@Override
	public List<TableReferenceDescriptor> retrieveTableRecordReferences()
	{
		return retrieveTableRecordReferences(Env.getCtx(), ITrx.TRXNAME_None);
	}

	@Cached(cacheName = I_AD_Table.Table_Name + "#and#" + I_AD_Column.Table_Name + "#referencedTableId2TableRecordTableIDs")
	/* package */ List<TableReferenceDescriptor> retrieveTableRecordReferences(@CacheCtx final Properties ctx, @CacheTrx final String trxName)
	{
		//
		final List<TableReferenceDescriptor> result = new ArrayList<>();

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IColumnBL columnBL = Services.get(IColumnBL.class);
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		final PlainContextAware ctxAware = PlainContextAware.newWithTrxName(ctx, trxName);

		// get the list of all columns whose names that end with "Record_ID". They probably belong to a column-record table (but we will make sure).
		// we could have queried for columns ending with "Table_ID", but there might be more "*Table_ID" columns that don't have a "*Record_ID" column than the other way around.
		final List<I_AD_Column> recordIdColumns = queryBL.createQueryBuilder(I_AD_Column.class, ctxAware)
				.addOnlyActiveRecordsFilter()
				.addEndsWithQueryFilter(I_AD_Column.COLUMNNAME_ColumnName, ITableRecordReference.COLUMNNAME_Record_ID)
				.orderBy().addColumn(I_AD_Column.COLUMN_AD_Column_ID).endOrderBy()
				.create()
				.list(I_AD_Column.class);

		for (final I_AD_Column recordIdColumn : recordIdColumns)
		{
			final org.compiere.model.I_AD_Table table = recordIdColumn.getAD_Table();
			if (table.isView())
			{
				continue;
			}
			final Optional<String> tableColumnName = columnBL.getTableColumnName(table.getTableName(), recordIdColumn.getColumnName());
			if (!tableColumnName.isPresent())
			{
				continue;
			}

			// now we know for sure that the records "table" of table can reference other records via Table_ID/Record_ID
			queryBL
					.createQueryBuilder(Object.class, table.getTableName(), ctxAware)
					// .addOnlyActiveRecordsFilter() we also want inactive records, because being active doesn't count when it comes to references
					.addCompareFilter(tableColumnName.get(), Operator.GREATER, 0)
					.create()
					.listDistinct(tableColumnName.get(), Integer.class)
					.stream()
					.filter(referencedTableID -> referencedTableID > 0)
					.map(referencedTableID -> adTableDAO.retrieveTableName(referencedTableID))
					.forEach(referencedTableName -> result.add(
							TableReferenceDescriptor.of(table.getTableName(), recordIdColumn.getColumnName(), referencedTableName)));
		}

		return result;
	}

	@Override
	public void updatePartitionSize(final I_DLM_Partition partitionDB)
	{
		final String trxName = InterfaceWrapperHelper.getTrxName(partitionDB);
		executeDBFunction_update_partition_size(partitionDB.getDLM_Partition_ID(), trxName);
	}

	abstract void executeDBFunction_update_partition_size(int dlm_Partition_ID, String trxName);

	@Override
	public Partition loadPartition(final I_DLM_Partition partitionDB)
	{
		// load the records using our view
		final Map<String, Collection<ITableRecordReference>> records = Collections.emptyMap();

		// load the config
		final PartitionConfig config = loadPartitionConfig(partitionDB.getDLM_Partition_Config());

		final int targetDLMLevel = partitionDB.getTarget_DLM_Level();
		final int currentDLMLevel = partitionDB.getCurrent_DLM_Level();
		final Timestamp dateNextInspection = partitionDB.getDateNextInspection();
		final boolean partitionComplete = partitionDB.isPartitionComplete();
		return Partition.loadedPartition(
				config,
				records,
				partitionComplete,
				currentDLMLevel,
				targetDLMLevel,
				dateNextInspection,
				partitionDB.getDLM_Partition_ID());
	}

	@Override
	public Partition storePartition(final Partition partition, final boolean runInOwnTrx)
	{
		if (runInOwnTrx)
		{
			final Mutable<Partition> result = new Mutable<>();

			Services.get(ITrxManager.class).run(new TrxRunnable()
			{
				@Override
				public void run(final String localTrxName) throws Exception
				{
					result.setValue(storePartition0(partition));
				}
			});
			return result.getValue();
		}

		return storePartition0(partition);
	}

	private Partition storePartition0(final Partition partition)
	{
		final PlainContextAware ctxAware = PlainContextAware.newWithThreadInheritedTrx(Env.getCtx());

		//
		// create and save the new partition record
		final I_DLM_Partition partitionDB;
		if (partition.getDLM_Partition_ID() > 0)
		{
			partitionDB = InterfaceWrapperHelper.create(Env.getCtx(), partition.getDLM_Partition_ID(), I_DLM_Partition.class, ITrx.TRXNAME_ThreadInherited);
		}
		else
		{
			partitionDB = InterfaceWrapperHelper.newInstance(I_DLM_Partition.class, ctxAware);
		}

		if (partition.isConfigChanged() || partitionDB.getDLM_Partition_Config_ID() <= 0)
		{
			final PartitionConfig storedConfig = storePartitionConfig(partition.getConfig());
			partitionDB.setDLM_Partition_Config_ID(storedConfig.getDLM_Partition_Config_ID());
		}

		partitionDB.setCurrent_DLM_Level(partition.getCurrentDLMLevel());
		partitionDB.setTarget_DLM_Level(partition.getTargetDLMLevel());
		partitionDB.setDateNextInspection(partition.getNextInspectionDate());
		partitionDB.setIsPartitionComplete(partition.isComplete());

		InterfaceWrapperHelper.save(partitionDB);

		// use intermediatePartition from here onwards.
		Partition intermediatePartition = partition.withDLM_Partition_ID(partitionDB.getDLM_Partition_ID());

		if (intermediatePartition.isRecordsChanged())
		{
			// update the partitioned data. note that here, intermediatePartition needs to have a DLM_Partition_ID
			final boolean recordsAdded = storeRecordsPartitionID(ctxAware, intermediatePartition);
			if (recordsAdded)
			{
				intermediatePartition.getRecordsFlat().forEach(tableRefordRef -> {
					tableRefordRef.notifyModelStaled();
				});

				// the coordinator needs to reevaluate
				partitionDB.setTarget_DLM_Level(IMigratorService.DLM_Level_NOT_SET);
				partitionDB.setDateNextInspection(null);
				InterfaceWrapperHelper.save(partitionDB);

				intermediatePartition = intermediatePartition
						.withNextInspectionDate(null)
						.withTargetDLMLevel(IMigratorService.DLM_Level_NOT_SET);
			}

			Services.get(IDLMService.class).updatePartitionSize(partitionDB);
		}

		final String msg = "Stored the partition {} with {} records; configChanged={}; recordsChanged={}";
		final Object[] msgParameters = { partitionDB, intermediatePartition.getRecordsFlat().size(), intermediatePartition.isConfigChanged(), intermediatePartition.isRecordsChanged() };
		logger.info(msg, msgParameters);
		Loggables.get().addLog(msg, msgParameters);

		final Partition result = intermediatePartition.withJustStored(partitionDB.getDLM_Partition_ID());
		return result;
	}

	/**
	 * Effectively persists {@link Partition#getRecords()} by updating the respective database records.
	 *
	 * @param ctxAware
	 * @param partition
	 */
	private boolean storeRecordsPartitionID(final IContextAware ctxAware,
			final Partition partition)
	{
		Check.errorIf(partition.getDLM_Partition_ID() <= 0, "Partition={} has no DLM_Partition_ID", partition);

		final Map<String, Collection<ITableRecordReference>> table2Record = partition.getRecords();

		final IColumnBL columnBL = Services.get(IColumnBL.class);

		int updatedSum = 0;

		for (final Entry<String, Collection<ITableRecordReference>> tableWithRecords : table2Record.entrySet())
		{
			final String tableName = tableWithRecords.getKey();
			final String keyColumn = columnBL.getSingleKeyColumn(tableName);

			final Collection<ITableRecordReference> records = tableWithRecords.getValue();
			if (records.isEmpty())
			{
				continue;
			}

			// Reason for this maxBatchsize-value:
			// when i had too many IDs, i got this error:
			// "Tried to send an out-of-range integer as a 2-byte value: 43278"
			// 2-byte means 2^16 = 65536
			// 43278 is less than 2^16, so i guess the max range is not [0, 2^16] but rather [-2^15, 2^15] (give or take 1)
			final int maxBatchSize = 32700; // 2^15 minus a safety margin since there are additional params (at least for the DLM_Partition_ID)
			final List<Integer> batch = new ArrayList<>(maxBatchSize);
			final Mutable<Integer> updatedBatchesSum = new Mutable<>(0);

			records
					.stream()
					.map(r -> r.getRecord_ID())
					.forEach(id -> {
						batch.add(id);
						if (batch.size() >= maxBatchSize)
						{
							final int updated = updateBatch(ctxAware, partition, tableName, keyColumn, batch);
							updatedBatchesSum.setValue(updatedBatchesSum.getValue() + updated);
						}
					});
			if (!batch.isEmpty())
			{
				final int updated = updateBatch(ctxAware, partition, tableName, keyColumn, batch);
				updatedBatchesSum.setValue(updatedBatchesSum.getValue() + updated);
			}

			// make sure we didn't update more than we had on the screen..just to be sure
			Check.errorIf(updatedBatchesSum.getValue() > records.size(), "We attempted to update {} record(s) of table {} to {}={}, but instead we updated {} records",
					records.size(), tableName, IDLMAware.COLUMNNAME_DLM_Partition_ID, partition.getDLM_Partition_ID(), updatedBatchesSum);

			logger.debug("Table {}: updated {} record(s) to {}={} (but not yet committed!)",
					tableName, updatedBatchesSum.getValue(), IDLMAware.COLUMNNAME_DLM_Partition_ID, partition.getDLM_Partition_ID());

			updatedSum += updatedBatchesSum.getValue();
		}

		if (Adempiere.isUnitTestMode())
		{
			// in unit test mode we explicitly need to create I_DLM_Partition_Record records.
			// in "normal" mode, DLM_Partition_Record is a view.
			partition.getRecordsFlat().forEach(r -> {

				final boolean match = Services.get(IQueryBL.class).createQueryBuilder(I_DLM_Partition_Record_V.class, ctxAware)
						.addEqualsFilter(I_DLM_Partition_Record_V.COLUMNNAME_DLM_Partition_ID, partition.getDLM_Partition_ID())
						.addEqualsFilter(I_DLM_Partition_Record_V.COLUMNNAME_AD_Table_ID, r.getAD_Table_ID())
						.addEqualsFilter(I_DLM_Partition_Record_V.COLUMNNAME_Record_ID, r.getRecord_ID())
						.create()
						.match();
				if (!match)
				{
					final I_DLM_Partition_Record_V viewRecord = InterfaceWrapperHelper.newInstance(I_DLM_Partition_Record_V.class, ctxAware);
					viewRecord.setAD_Table_ID(r.getAD_Table_ID());
					viewRecord.setRecord_ID(r.getRecord_ID());
					viewRecord.setDLM_Partition_ID(partition.getDLM_Partition_ID());
					InterfaceWrapperHelper.save(viewRecord);
				}
			});
		}

		return updatedSum > 0;
	}

	private int updateBatch(final IContextAware ctxAware, final Partition partition, final String tableName, final String keyColumn, final List<Integer> batch)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final int updated = queryBL.createQueryBuilder(IDLMAware.class, tableName, ctxAware)
				.addInArrayFilter(keyColumn, batch.toArray())
				.addNotEqualsFilter(IDLMAware.COLUMNNAME_DLM_Partition_ID, partition.getDLM_Partition_ID()) // only records that are not yet in this partition
				.create()
				.updateDirectly()
				.addSetColumnValue(IDLMAware.COLUMNNAME_DLM_Partition_ID, partition.getDLM_Partition_ID())
				.execute();

		batch.clear();

		return updated;
	}

	@Override
	public PartitionConfig loadDefaultPartitionConfig()
	{
		final I_DLM_Partition_Config defaultConfigDB = Services.get(IQueryBL.class).createQueryBuilder(I_DLM_Partition_Config.class, PlainContextAware.newOutOfTrx(Env.getCtx()))
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DLM_Partition_Config.COLUMNNAME_IsDefault, true)
				.create()
				.firstOnly(I_DLM_Partition_Config.class); // note that we have a UC, so firstonly is OK

		return loadPartitionConfig(defaultConfigDB);
	}

	@Override
	public PartitionConfig loadPartitionConfig(final I_DLM_Partition_Config configDB)
	{
		if (configDB == null)
		{
			return new PartitionConfig.Builder().setName("empty config").build();
		}

		final PlainContextAware ctxAware = PlainContextAware.newWithThreadInheritedTrx(Env.getCtx());

		final Builder configBuilder = PartitionConfig.builder()
				.setName(configDB.getName())
				.setDLM_Partition_Config_ID(configDB.getDLM_Partition_Config_ID());

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final List<I_DLM_Partition_Config_Line> lines = queryBL.createQueryBuilder(I_DLM_Partition_Config_Line.class, ctxAware)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DLM_Partition_Config_Line.COLUMN_DLM_Partition_Config_ID, configDB.getDLM_Partition_Config_ID())
				.create()
				.list();

		for (final I_DLM_Partition_Config_Line line : lines)
		{
			final LineBuilder lineBuilder = configBuilder
					.line(line.getDLM_Referencing_Table().getTableName())
					.setDLM_Partition_Config_Line(line.getDLM_Partition_Config_Line_ID());

			final List<I_DLM_Partition_Config_Reference> refs = queryBL.createQueryBuilder(I_DLM_Partition_Config_Reference.class, ctxAware)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_DLM_Partition_Config_Reference.COLUMN_DLM_Partition_Config_Line_ID, line.getDLM_Partition_Config_Line_ID())
					.create()
					.list();

			for (final I_DLM_Partition_Config_Reference ref : refs)
			{
				final RefBuilder refBuilder = lineBuilder.ref()
						.setReferencedTableName(ref.getDLM_Referenced_Table().getTableName())
						.setReferencingColumnName(ref.getDLM_Referencing_Column().getColumnName())
						.setDLM_Partition_Config_Reference_ID(ref.getDLM_Partition_Config_Reference_ID())
						.setIsPartitionBoundary(
								InterfaceWrapperHelper.create(ref.getDLM_Referencing_Column(), de.metas.dlm.model.I_AD_Column.class)
										.isDLMPartitionBoundary());

				refBuilder.endRef();
			}
			lineBuilder.endLine();
		}

		final PartitionConfig config = configBuilder.build();
		return config;
	}

	@Override
	public PartitionConfig storePartitionConfig(final PartitionConfig config)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		I_DLM_Partition_Config configDB;

		final PlainContextAware ctxAware = PlainContextAware.newWithThreadInheritedTrx(Env.getCtx());

		if (config.getDLM_Partition_Config_ID() > 0)
		{
			// load existing DLM_Partition_Config record
			configDB = InterfaceWrapperHelper.create(ctxAware.getCtx(), config.getDLM_Partition_Config_ID(), I_DLM_Partition_Config.class, ctxAware.getTrxName());
		}
		else
		{
			// new DLM_Partition_Config record
			configDB = InterfaceWrapperHelper.newInstance(I_DLM_Partition_Config.class, ctxAware);
		}

		if (!Check.isEmpty(config.getName(), true))
		{
			configDB.setName(config.getName());
		}

		InterfaceWrapperHelper.save(configDB);
		config.setDLM_Partition_Config_ID(configDB.getDLM_Partition_Config_ID());

		// we first need to persist only the lines,
		// so that we can be sure to later have all the IDs for DLM_Partition_Config_Reference.DLM_Partition_Config_Reference_ID
		for (final PartitionerConfigLine line : config.getLines())
		{
			I_DLM_Partition_Config_Line configLineDB;
			if (line.getDLM_Partition_Config_Line_ID() > 0)
			{
				// load existing DLM_Partition_Config_Line record
				configLineDB = InterfaceWrapperHelper.create(ctxAware.getCtx(), line.getDLM_Partition_Config_Line_ID(), I_DLM_Partition_Config_Line.class, ctxAware.getTrxName());
			}
			else
			{
				// new DLM_Partition_Config_Line record
				configLineDB = InterfaceWrapperHelper.newInstance(I_DLM_Partition_Config_Line.class, ctxAware);
			}
			configLineDB.setDLM_Partition_Config(configDB);

			final int referencingTableID = adTableDAO.retrieveTableId(line.getTableName());
			configLineDB.setDLM_Referencing_Table_ID(referencingTableID);
			InterfaceWrapperHelper.save(configLineDB);
			line.setDLM_Partition_Config_Line_ID(configLineDB.getDLM_Partition_Config_Line_ID());
		}

		for (final PartitionerConfigLine line : config.getLines())
		{
			for (final PartitionerConfigReference ref : line.getReferences())
			{
				I_DLM_Partition_Config_Reference configRefDB;
				if (ref.getDLM_Partition_Config_Reference_ID() > 0)
				{
					// load existing DLM_Partition_Config_Reference record
					configRefDB = InterfaceWrapperHelper.create(ctxAware.getCtx(), ref.getDLM_Partition_Config_Reference_ID(), I_DLM_Partition_Config_Reference.class, ctxAware.getTrxName());
				}
				else
				{
					// new DLM_Partition_Config_Reference record
					configRefDB = InterfaceWrapperHelper.newInstance(I_DLM_Partition_Config_Reference.class, ctxAware);
				}
				configRefDB.setDLM_Partition_Config_Line_ID(line.getDLM_Partition_Config_Line_ID());

				final int referencedTableID = adTableDAO.retrieveTableId(ref.getReferencedTableName());
				configRefDB.setDLM_Referenced_Table_ID(referencedTableID);

				final I_AD_Column referencingColumn = InterfaceWrapperHelper.create(adTableDAO.retrieveColumn(line.getTableName(), ref.getReferencingColumnName()), I_AD_Column.class);
				configRefDB.setDLM_Referencing_Column(referencingColumn);

				InterfaceWrapperHelper.save(configRefDB);
				ref.setDLM_Partition_Config_Reference_ID(configRefDB.getDLM_Partition_Config_Reference_ID());
			}
		}
		final String msg = "Stored DLM_Partition_Config={}";
		logger.info(msg, configDB);
		Loggables.get().addLog(msg, configDB);

		return PartitionConfig.builder(config)
				.setChanged(false)
				.build();
	}

}
