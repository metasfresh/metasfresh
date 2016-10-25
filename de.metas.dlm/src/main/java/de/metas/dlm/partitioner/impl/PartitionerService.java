package de.metas.dlm.partitioner.impl;

import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

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
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Column;
import org.compiere.util.Env;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import de.metas.adempiere.service.IColumnBL;
import de.metas.dlm.IDLMService;
import de.metas.dlm.Partition;
import de.metas.dlm.exception.DLMException;
import de.metas.dlm.migrator.IMigratorService;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_AD_Table;
import de.metas.dlm.model.I_DLM_Partition;
import de.metas.dlm.model.I_DLM_Partition_Config;
import de.metas.dlm.model.I_DLM_Partition_Config_Line;
import de.metas.dlm.model.I_DLM_Partition_Config_Reference;
import de.metas.dlm.partitioner.IPartitionerService;
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
	public Partition createPartition(final PartitionerConfig config)
	{
		final Map<ITableRecordReference, IDLMAware> records = new HashMap<>();

		// we need to get to the "first" line(s),
		// i.e. those DLM_PartitionLine_Configs that are not referenced via any DLM_PartitionReference_Config.DLM_Referencing_PartitionLine_Config_ID
		// i think we can also live with circles, i.e. if there is no "first", but for those "firsts" we should pick one and start with it
		// also, for the case of >1 "firsts", we need to be able to backtrack
		final List<PartitionerConfigLine> lines = config.getLines(); // DLM_PartitionLine_Config

		if (lines.isEmpty())
		{
			return new Partition(config, records.values()); // return empty partition
		}

		lines.forEach(l -> addToDLMIfNeccesary(l.getTableName())); // make sure the tables are all ready for DLM

		// iterate the lines and look for the first record out o
		for (final PartitionerConfigLine line : lines)
		{
			addToDLMIfNeccesary(line.getTableName());
			final IDLMAware record = retrieveUnpartitionedRecod(line.getTableName());
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
		catch (final DLMException e)
		{
			final TableReferenceDescriptor descriptor = e.getTableReferenceDescriptor();

			// if there is a DLMException, then depending on our config (LATER),
			// throw an exception (LATER),
			// skip the record (LATER)
			// or add another PartitionerConfigLine, get the additional line's records and retry.
			logger.info("Caught {}; going to retry with an augmented config which also includes referencingTable={}", e.toString(), descriptor.getReferencingTableName());

			final PartitionerConfig newConfig = augmentPartitionerConfig(config, Collections.singletonList(descriptor));

			// when adding another PartitionerConfigLine but the table is not DLM'ed yet, then DLM it on the fly.
			addToDLMIfNeccesary(descriptor.getReferencingTableName());

			return createPartition(newConfig); // call this method again, i.e. start over with our augmented config
		}
		return partition;
	}

	private void addToDLMIfNeccesary(final String tableName)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		//
		// first commit our current trx. see the javadoc of addTableToDLM for details.
		//

		if (!Adempiere.isUnitTestMode())
		{
			// in unit test mode we don't care, but in normal mode, we want to verify there is an inherited trx to commit
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
		}
		//
		// then the the table, and if necessary, DLM it.
		//
		trxManager.run(new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				final IQueryBL queryBL = Services.get(IQueryBL.class);
				final IDLMService dlmService = Services.get(IDLMService.class);

				// don't use IADTableDAO, because in this particular case, we don't want the table's trxName to be "NONE"
				final I_AD_Table referencingTable = queryBL.createQueryBuilder(I_AD_Table.class, Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
						.addOnlyActiveRecordsFilter()
						.addEqualsFilter(org.compiere.model.I_AD_Table.COLUMNNAME_TableName, tableName, UpperCaseQueryFilterModifier.instance)
						.create()
						.firstOnly(I_AD_Table.class);
				Check.errorIf(referencingTable == null, "I_AD_Table record for tableName={} is null", tableName); // this can happen in unit test mode

				if (!referencingTable.isDLM())
				{
					logger.info("ReferencingTable={} is not yet DLM'ed; doing it now", tableName);
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

		return partitionDB;
	}

	private IDLMAware retrieveUnpartitionedRecod(final String tableName)
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
				.addColumn(keyColumnName)
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
					logger.debug("The column={} of IDLMAware={} does not reference a {}-record, but a {}-record; skipping", ref.getReferencingColumnName(), record, foreignTableName, tableColumnName);
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
						InterfaceWrapperHelper.getContextAware(record),  // 'record' is only needed for ctx
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
				InterfaceWrapperHelper.getContextAware(record),        // make it clear that record is only needed for ctx
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

		I_DLM_Partition_Config configDB;
		if (config.getDLM_Partition_Config_ID() > 0)
		{
			configDB = InterfaceWrapperHelper.create(Env.getCtx(), config.getDLM_Partition_Config_ID(), I_DLM_Partition_Config.class, ITrx.TRXNAME_ThreadInherited);
		}
		else
		{
			configDB = InterfaceWrapperHelper.newInstance(I_DLM_Partition_Config.class, new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_ThreadInherited));
			InterfaceWrapperHelper.save(configDB);
			config.setDLM_Partition_Config_ID(configDB.getDLM_Partition_Config_ID());
		}

		// we first need to persist only the lines,
		// so that we can be sure to later have all the IDs for DLM_Partition_Config_Reference.DLM_Partition_Config_Reference_ID
		for (final PartitionerConfigLine line : config.getLines())
		{
			I_DLM_Partition_Config_Line configLineDB;
			if (line.getDLM_Partition_Config_Line_ID() > 0)
			{
				configLineDB = InterfaceWrapperHelper.create(Env.getCtx(), line.getDLM_Partition_Config_Line_ID(), I_DLM_Partition_Config_Line.class, ITrx.TRXNAME_ThreadInherited);
			}
			else
			{
				configLineDB = InterfaceWrapperHelper.newInstance(I_DLM_Partition_Config_Line.class, new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_ThreadInherited));
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
					configRefDB = InterfaceWrapperHelper.create(Env.getCtx(), ref.getDLM_Partition_Config_Reference_ID(), I_DLM_Partition_Config_Reference.class, ITrx.TRXNAME_ThreadInherited);
				}
				else
				{
					configRefDB = InterfaceWrapperHelper.newInstance(I_DLM_Partition_Config_Reference.class, new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_ThreadInherited));
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
		return configDB;
	}

	@Override
	public PartitionerConfig loadPartitionConfig(final I_DLM_Partition_Config configDB)
	{

		final Builder configBuilder = PartitionerConfig.builder()
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
		});

		return builder.build();

	}
}
