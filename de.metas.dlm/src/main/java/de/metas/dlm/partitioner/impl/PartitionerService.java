package de.metas.dlm.partitioner.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.UpperCaseQueryFilterModifier;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Column;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import de.metas.adempiere.service.IColumnBL;
import de.metas.connection.ITemporaryConnectionCustomizer;
import de.metas.dlm.IDLMService;
import de.metas.dlm.Partition;
import de.metas.dlm.connection.DLMConnectionCustomizer;
import de.metas.dlm.exception.DLMReferenceException;
import de.metas.dlm.exception.TableNotAddedToDLMException;
import de.metas.dlm.migrator.IMigratorService;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_AD_Table;
import de.metas.dlm.model.I_DLM_Partition;
import de.metas.dlm.model.I_DLM_Partition_Config;
import de.metas.dlm.model.I_DLM_Partition_Config_Line;
import de.metas.dlm.model.I_DLM_Partition_Config_Reference;
import de.metas.dlm.partitioner.IPartitionerService;
import de.metas.dlm.partitioner.PartitionRequestFactory;
import de.metas.dlm.partitioner.PartitionRequestFactory.CreatePartitionRequest;
import de.metas.dlm.partitioner.PartitionRequestFactory.CreatePartitionRequest.OnNotDLMTable;
import de.metas.dlm.partitioner.config.PartitionerConfig;
import de.metas.dlm.partitioner.config.PartitionerConfig.Builder;
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

public class PartitionerService implements IPartitionerService
{

	private final transient Logger logger = LogManager.getLogger(getClass());

	@Override
	public Partition createPartition(final CreatePartitionRequest request)
	{
		final Map<ITableRecordReference, IDLMAware> records = new HashMap<>();

		final PartitionerConfig config = request.getConfig();

		// we need to get to the "first" line(s),
		// i.e. those DLM_PartitionLine_Configs that are not referenced via any DLM_PartitionReference_Config.DLM_Referencing_PartitionLine_Config_ID
		// i think we can also live with circles, i.e. if there is no "first", but for those "firsts" we should pick one and start with it
		// also, for the case of >1 "firsts", we need to be able to backtrack
		final List<PartitionerConfigLine> lines = config.getLines(); // DLM_PartitionLine_Config

		if (lines.isEmpty())
		{
			return new Partition(config, records.values()); // return empty partition
		}

		// make sure the tables of which we might add records are all ready for DLM
		checkIfAllTablesAreDLM(lines, request.getOnNotDLMTable());

		// iterate the lines and look for the first record out o
		for (final PartitionerConfigLine line : lines)
		{

			final IDLMAware record = retrieveUnpartitionedRecod(line.getTableName(), request.isOldestFirst());
			if (record == null)
			{
				continue;  // looks like we partitioned *every* record of the given table
			}

			records.put(ITableRecordReference.FromModelConverter.convert(record), record);
			recurse(line, record, records);
			break;
		}

		final Partition partition = new Partition(config, records.values());

		try
		{
			// now figure out if records are missing:
			// update each records' DLM_Level to 1 (1="test").
			final IMigratorService migratorService = Services.get(IMigratorService.class);
			migratorService.testMigratePartition(partition);
		}
		catch (final DLMReferenceException e)
		{
			final TableReferenceDescriptor descriptor = e.getTableReferenceDescriptor();

			// if there is a DLMException, then depending on our config (LATER),
			// throw an exception (LATER),
			// skip the record (LATER)
			// or add another PartitionerConfigLine, get the additional line's records and retry.
			final String msg = "Caught {}; going to retry with an augmented config that also includes referencingTable={}";
			final Object[] msgParameters = { e.toString(), descriptor.getReferencingTableName() };
			logger.info(msg, msgParameters);
			ILoggable.THREADLOCAL.getLoggable().addLog(msg, msgParameters);

			final PartitionerConfig newConfig = augmentPartitionerConfig(config, Collections.singletonList(descriptor));

			// when adding another PartitionerConfigLine but the table is not DLM'ed yet, then DLM it on the fly.
			checkIfTableIsDLM(descriptor.getReferencingTableName(), request.getOnNotDLMTable());

			// call this method again, i.e. start over with our augmented config
			final CreatePartitionRequest newRequest = PartitionRequestFactory
					.builder(request)
					.setConfig(newConfig)
					.build();
			storeOutOfTrx(newConfig); // store the new config so that even if we fail later on, the info is preserved

			return createPartition(newRequest);
		}

		final String msg = "Returning a newly identified partition with {} records.";
		logger.info(msg, partition.getRecords().size());
		ILoggable.THREADLOCAL.getLoggable().addLog(msg, partition.getRecords().size());

		return partition;
	}

	private void checkIfAllTablesAreDLM(final List<PartitionerConfigLine> lines, OnNotDLMTable onNotDLMTable)
	{
		final Set<String> checkedTableNames = new HashSet<>();
		for (PartitionerConfigLine line : lines)
		{
			if (checkedTableNames.add(line.getTableName()))
			{
				checkIfTableIsDLM(line.getTableName(), onNotDLMTable);
			}

			for (PartitionerConfigReference ref : line.getReferences())
			{
				if (checkedTableNames.add(ref.getReferencedTableName()))
				{
					checkIfTableIsDLM(ref.getReferencedTableName(), onNotDLMTable);
				}
			}
		}
	}

	private void storeOutOfTrx(final PartitionerConfig newConfig)
	{
		Services.get(ITrxManager.class).run(new TrxRunnable()
		{
			@Override
			public void run(String localTrxName) throws Exception
			{
				storePartitionConfig(newConfig);
			}
		});
	}

	private void checkIfTableIsDLM(final String tableName, OnNotDLMTable onNotDLMTable)
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
		// first commit our current trx. see the javadoc of addTableToDLM for details.
		//

		// we want to verify there is an inherited trx to commit
		// because otherwise, it would mean that we have no clue what's going on.
		final ITrx currentTrx = trxManager.get(ITrx.TRXNAME_ThreadInherited, OnTrxMissingPolicy.Fail);
		try
		{
			currentTrx.commit(true);
		}
		catch (final SQLException e)
		{
			throw DBException.wrapIfNeeded(e);
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
					ILoggable.THREADLOCAL.getLoggable().addLog(msg, tableName);

					dlmService.addTableToDLM(referencingTable);
				}
			}
		});
	}

	@Override
	public I_DLM_Partition storePartition(final Partition partition)
	{
		final PlainContextAware ctxAware = new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_ThreadInherited);

		//
		// create and save the new partition record
		final I_DLM_Partition partitionDB = InterfaceWrapperHelper.newInstance(I_DLM_Partition.class, ctxAware);
		final I_DLM_Partition_Config partitionConfigDB = storePartitionConfig(partition.getConfig());
		partitionDB.setDLM_Partition_Config(partitionConfigDB);
		InterfaceWrapperHelper.save(partitionDB);

		//
		// update the partitioned data
		final IDLMService dlmService = Services.get(IDLMService.class);
		dlmService.directUpdateDLMColumn(ctxAware, partition, IDLMAware.COLUMNNAME_DLM_Partition_ID, partitionDB.getDLM_Partition_ID());

		final String msg = "Stored the partition {} with {} records";
		final Object[] msgParameters = { partitionDB, partition.getRecords().size() };
		logger.info(msg, msgParameters);
		ILoggable.THREADLOCAL.getLoggable().addLog(msg, msgParameters);

		return partitionDB;
	}

	private IDLMAware retrieveUnpartitionedRecod(
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
				.createQueryBuilder(IDLMAware.class, tableName, new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_ThreadInherited))
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

	/**
	 *
	 * @param line the line's <code>tableName</code> matches the given <code>record</code>. The line's <code>references</code> describe which foreign records the given <code>record</code> might reference.
	 *            This method loads them and adds the to the given <code>records</code> set.
	 * @param record used to get and load further records which this given record references.
	 * @param records the set of records we will eventually return. Needed to detect circles by checking if a record was already added. The given <code>record</code> is already included.
	 *
	 * @return
	 */
	private void recurse(
			final PartitionerConfigLine line,
			final IDLMAware record,
			final Map<ITableRecordReference, IDLMAware> records)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(record);
		final String trxName = InterfaceWrapperHelper.getTrxName(record);

		final List<PartitionerConfigReference> references = line.getReferences();

		// GO FORWARD
		// for each ref, load the referenced record and recurse with that record
		for (final PartitionerConfigReference ref : references) // DLM_PartitionReference_Config
		{
			// the table name for the foreign record which has 'foreignKey' as its ID
			final String foreignTableName = ref.getReferencedTableName();

			// first check if this is all about a Record_ID/AD_Table_ID reference.
			// if that is the case, then we need to verify that the AD_Table_ID of 'record' actually points to the table named 'foreignTableName'
			final IColumnBL columnBL = Services.get(IColumnBL.class);
			if (columnBL.isRecordColumnName(ref.getReferencingColumnName()))
			{
				final String tableColumnName = columnBL.getTableColumnName(line.getTableName(), ref.getReferencingColumnName())
						.orElseThrow(Check.supplyEx("Table={} has no table column name for recordColumnName={}", line.getTableName(), ref.getReferencingColumnName()));

				final Integer tableId = InterfaceWrapperHelper.getValueOrNull(record, tableColumnName);
				if (tableId == null || tableId <= 0)
				{
					logger.debug("The column={} of IDLMAware={} does not reference any table; skipping", ref.getReferencingColumnName(), record, foreignTableName, tableColumnName);
					continue;
				}

				final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
				final String tableName = adTableDAO.retrieveTableName(tableId);
				if (!tableName.equalsIgnoreCase(foreignTableName))
				{
					logger.debug("The column={} of IDLMAware={} does not reference a {}-record, but a {}-record; skipping", ref.getReferencingColumnName(), record, foreignTableName, tableName);
					continue;
				}
			}

			// get the foreign key ID of
			// table DLM_PartitionLine_Config.AD_Table_ID,
			// column DLM_PartitionReference_Config.DLM_Referencing_Column_ID
			final Integer foreignKey = InterfaceWrapperHelper.getValueOrNull(record, ref.getReferencingColumnName());
			if (foreignKey == null || foreignKey <= 0)
			{
				logger.debug("The column={} of IDLMAware={} does not reference anything; skipping", ref.getReferencingColumnName(), record);
				continue;
			}

			// load the foreign record from the table DLM_PartitionReference_Config.DLM_Referenced_Table_ID
			final IDLMAware foreignRecord = InterfaceWrapperHelper.create(ctx, foreignTableName, foreignKey, IDLMAware.class, trxName);
			if (foreignRecord == null)
			{
				// this happens e.g. for our "minidump" where we left out the HUs
				logger.debug("The record from table={} which we attempted to load via {}.{}={} is NULL", foreignTableName, line.getTableName(), ref.getReferencingColumnName(), foreignKey);
				continue;
			}
			logger.debug("Loaded referenced IDLMAware={} from table={} via {}.{}={}", foreignRecord, foreignTableName, line.getTableName(), ref.getReferencingColumnName(), foreignKey);

			final ITableRecordReference foreignTableRecordReference = ITableRecordReference.FromModelConverter.convert(foreignRecord);

			final boolean recordWasNotYetAddedBefore = null == records.putIfAbsent(foreignTableRecordReference, foreignRecord);

			if (recordWasNotYetAddedBefore)
			{
				// get the foreign record's PartitionerConfigLine
				// note: it's
				final Optional<PartitionerConfigLine> referencedConfigLine = line.getParent().getLine(foreignTableName);
				if (referencedConfigLine.isPresent())
				{
					recurse(referencedConfigLine.get(), foreignRecord, records);
				}
				else
				{
					logger.debug("The table={} of the referenced IDLMAware={} does not have a PartitionerConfigLine", foreignTableName, foreignRecord);
				}

				// GO BACKWARDS, i.e. get records which reference the foreign record we just loaded
				backTrack(line.getParent(),
						InterfaceWrapperHelper.getContextAware(record),                         // 'record' is only needed for ctx
						records,
						foreignTableName,
						foreignKey);
			}
			else
			{
				logger.debug("IDLMAware={} was already added in a previous iteration. Returning", record); // avoid circles
			}
		}

		// GO BACKWARDS, i.e. get records which reference 'record'
		backTrack(line.getParent(),
				InterfaceWrapperHelper.getContextAware(record),                               // make it clear that record is only needed for ctx
				records,
				line.getTableName(),
				InterfaceWrapperHelper.getId(record));
	}

	/**
	 * For the given <code>tableName</code> and <code>key</code>, method loads all record that reference the respective record.
	 * <p>
	 * Note that there needs to be {@link PartitionerConfigLine}s with {@link PartitionerConfigReference}s for those referencing records (same as with forward references).
	 *
	 * @param config
	 * @param contextProvider
	 * @param records
	 * @param remainingLines
	 * @param tableName
	 * @param key
	 */
	private void backTrack(final PartitionerConfig config,
			final IContextAware contextProvider,
			final Map<ITableRecordReference, IDLMAware> records,
			final String tableName,
			final int key)
	{
		final List<PartitionerConfigReference> backwardReferences = config.getReferences(tableName);
		for (final PartitionerConfigReference backwardRef : backwardReferences)
		{
			final PartitionerConfigLine backwardLine = backwardRef.getParent();
			final String backwardTableName = backwardLine.getTableName();

			// load all records which reference foreignRecord
			final IQueryBuilder<IDLMAware> queryBuilder = Services.get(IQueryBL.class)
					.createQueryBuilder(IDLMAware.class, backwardTableName, contextProvider)
					.addEqualsFilter(backwardRef.getReferencingColumnName(), key);

			// if we have a case of AD_Table_ID/Record_ID,
			// then we need to make sure to only load records whose AD_Table_ID references the
			final IColumnBL columnBL = Services.get(IColumnBL.class);
			if (columnBL.isRecordColumnName(backwardRef.getReferencingColumnName()))
			{
				final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

				// note that referencedTableColumnName = AD_Table_ID, in most cases
				final String referencedTableColumnName = columnBL.getTableColumnName(backwardTableName, backwardRef.getReferencingColumnName())
						.orElseThrow(Check.supplyEx("Table={} has no table column name for recordColumnName={}", backwardTableName, backwardRef.getReferencingColumnName()));

				final int referencedTableID = adTableDAO.retrieveTableId(tableName);

				queryBuilder.addEqualsFilter(referencedTableColumnName, referencedTableID);
			}

			final List<IDLMAware> backwardRecords = queryBuilder
					.create()
					.list();
			for (final IDLMAware backwardRecord : backwardRecords)
			{
				InterfaceWrapperHelper.setTrxName(backwardRecord, contextProvider.getTrxName()); // we need this for MPinstance, because it expilicitly ignores the trx it is loaded with in its constructor.

				final ITableRecordReference backwardTableRecordReference = ITableRecordReference.FromModelConverter.convert(backwardRecord);

				final boolean backwardRecordWasNotYetAddedBefore = null == records.putIfAbsent(backwardTableRecordReference, backwardRecord);

				if (backwardRecordWasNotYetAddedBefore)
				{
					recurse(backwardLine, backwardRecord, records);
				}
			}
		}
	}

	@Override
	public I_DLM_Partition_Config storePartitionConfig(final PartitionerConfig config)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		final Properties ctx = Env.getCtx();

		I_DLM_Partition_Config configDB;

		if (config.getDLM_Partition_Config_ID() > 0)
		{
			// load existing DLM_Partition_Config record
			configDB = InterfaceWrapperHelper.create(ctx, config.getDLM_Partition_Config_ID(), I_DLM_Partition_Config.class, ITrx.TRXNAME_ThreadInherited);
		}
		else
		{
			// new DLM_Partition_Config record
			configDB = InterfaceWrapperHelper.newInstance(I_DLM_Partition_Config.class, new PlainContextAware(ctx, ITrx.TRXNAME_ThreadInherited));
		}
		configDB.setName(config.getName());

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
				configLineDB = InterfaceWrapperHelper.create(ctx, line.getDLM_Partition_Config_Line_ID(), I_DLM_Partition_Config_Line.class, ITrx.TRXNAME_ThreadInherited);
			}
			else
			{
				// new DLM_Partition_Config_Line record
				configLineDB = InterfaceWrapperHelper.newInstance(I_DLM_Partition_Config_Line.class, new PlainContextAware(ctx, ITrx.TRXNAME_ThreadInherited));
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
					configRefDB = InterfaceWrapperHelper.create(ctx, ref.getDLM_Partition_Config_Reference_ID(), I_DLM_Partition_Config_Reference.class, ITrx.TRXNAME_ThreadInherited);
				}
				else
				{
					// new DLM_Partition_Config_Reference record
					configRefDB = InterfaceWrapperHelper.newInstance(I_DLM_Partition_Config_Reference.class, new PlainContextAware(ctx, ITrx.TRXNAME_ThreadInherited));
				}
				configRefDB.setDLM_Partition_Config_Line_ID(line.getDLM_Partition_Config_Line_ID());

				final int referencedTableID = adTableDAO.retrieveTableId(ref.getReferencedTableName());
				configRefDB.setDLM_Referenced_Table_ID(referencedTableID);

				final I_AD_Column referencingColumn = adTableDAO.retrieveColumn(line.getTableName(), ref.getReferencingColumnName());
				configRefDB.setDLM_Referencing_Column(referencingColumn);

				InterfaceWrapperHelper.save(configRefDB);
				ref.setDLM_Partition_Config_Reference_ID(configRefDB.getDLM_Partition_Config_Reference_ID());
			}
		}
		final String msg = "Stored DLM_Partition_Config={}";
		logger.info(msg, configDB);
		ILoggable.THREADLOCAL.getLoggable().addLog(msg, configDB);

		return configDB;
	}

	@Override
	public PartitionerConfig loadPartitionConfig(final I_DLM_Partition_Config configDB)
	{

		final Builder configBuilder = PartitionerConfig.builder()
				.setName(configDB.getName())
				.setDLM_Partition_Config_ID(configDB.getDLM_Partition_Config_ID());

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final List<I_DLM_Partition_Config_Line> lines = queryBL.createQueryBuilder(I_DLM_Partition_Config_Line.class, new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_ThreadInherited))
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DLM_Partition_Config_Line.COLUMN_DLM_Partition_Config_ID, configDB.getDLM_Partition_Config_ID())
				.create()
				.list();

		for (final I_DLM_Partition_Config_Line line : lines)
		{
			final LineBuilder lineBuilder = configBuilder
					.line(line.getDLM_Referencing_Table().getTableName())
					.setDLM_Partition_Config_Line(line.getDLM_Partition_Config_Line_ID());

			final List<I_DLM_Partition_Config_Reference> refs = queryBL.createQueryBuilder(I_DLM_Partition_Config_Reference.class, new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_ThreadInherited))
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_DLM_Partition_Config_Reference.COLUMN_DLM_Partition_Config_Line_ID, line.getDLM_Partition_Config_Line_ID())
					.create()
					.list();

			for (final I_DLM_Partition_Config_Reference ref : refs)
			{
				final RefBuilder refBuilder = lineBuilder.ref()
						.setReferencedTableName(ref.getDLM_Referenced_Table().getTableName())
						.setReferencingColumnName(ref.getDLM_Referencing_Column().getColumnName())
						.setDLM_Partition_Config_Reference(ref.getDLM_Partition_Config_Reference_ID());

				refBuilder.endRef();
			}
			lineBuilder.endLine();
		}

		final PartitionerConfig config = configBuilder.build();
		return config;
	}

	@Override
	public final PartitionerConfig augmentPartitionerConfig(final PartitionerConfig config,
			final List<TableReferenceDescriptor> descriptors)
	{

		final Builder builder = PartitionerConfig.builder(config);

		descriptors.forEach(descriptor -> {

			final String referencingTableName = descriptor.getReferencingTableName();
			final String referencingColumnName = descriptor.getReferencingColumnName();
			final String referencedTableName = descriptor.getReferencedTableName();

			builder.line(referencingTableName)
					.ref().setReferencingColumnName(referencingColumnName).setReferencedTableName(referencedTableName)
					.endRef()
					.endLine()
					.build();

			final String msg = "Added descriptor={} to the config with name={}";
			final Object[] params = { descriptor, config.getName() };
			logger.info(msg, params);
			ILoggable.THREADLOCAL.getLoggable().addLog(msg, params);
		});

		return builder.build();
	}

	@Override
	public ITemporaryConnectionCustomizer createConnectionCustomizer()
	{
		final int dlmLevel = IMigratorService.DLM_Level_TEST; // don't set it to 0, because otherwise, records will vanish from the partitionerService's radar after it successfully invoked testMigratePartition
		final int dlmCoalesceLevel = IMigratorService.DLM_Level_LIVE; // records that were not yet given a DLM-Level shall be assumed to be "operational"
		final DLMConnectionCustomizer connectionCustomizer = new DLMConnectionCustomizer(dlmLevel, dlmCoalesceLevel);

		return connectionCustomizer;
	}
}
