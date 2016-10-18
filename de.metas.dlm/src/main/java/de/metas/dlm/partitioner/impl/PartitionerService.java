package de.metas.dlm.partitioner.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.service.IColumnBL;
import de.metas.dlm.Partition;
import de.metas.dlm.exception.DLMException;
import de.metas.dlm.migrator.IMigratorService;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_DLM_Partion_Config;
import de.metas.dlm.model.I_DLM_Partion_Config_Line;
import de.metas.dlm.model.I_DLM_Partion_Config_Reference;
import de.metas.dlm.model.I_DLM_Partition;
import de.metas.dlm.partitioner.IPartitionerService;
import de.metas.dlm.partitioner.config.PartionerConfigReference;
import de.metas.dlm.partitioner.config.PartionerConfigReference.RefBuilder;
import de.metas.dlm.partitioner.config.PartitionerConfig;
import de.metas.dlm.partitioner.config.PartitionerConfig.Builder;
import de.metas.dlm.partitioner.config.PartitionerConfigLine;
import de.metas.dlm.partitioner.config.PartitionerConfigLine.LineBuilder;
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

	protected final transient Logger logger = LogManager.getLogger(getClass());

	@Override
	public Partition createPartition(final PartitionerConfig config)
	{
		final Set<IDLMAware> records = new HashSet<>();

		// we need to get to the "first" line(s),
		// i.e. those DLM_PartionLine_Configs that are not referenced via any DLM_PartionReference_Config.DLM_Referencing_PartionLine_Config_ID
		// i think we can also live with circles, i.e. if there is no "first", but for those "firsts" we should pick one and start with it
		// also, for the case of >1 "firsts", we need to be able to backtrack
		final List<PartitionerConfigLine> lines = config.getLines(); // DLM_PartionLine_Config

		if (lines.isEmpty())
		{
			return new Partition(config, records); // return empty partition
		}

		// iterate the lines
		for (final PartitionerConfigLine line : lines)
		{
			final IDLMAware record = retrieveUnpartitionedRecod(line.getTableName());
			if (record == null)
			{
				continue;  // looks like we partitioned *every* record of the given table
			}

			records.add(record);
			recurse(line, record, records);
		}

		final Partition partition = new Partition(config, records);

		try
		{
			// now figure out if records are missing:
			// update each records' DLM_Level to 1 (1="test").

			final IMigratorService migratorService = Services.get(IMigratorService.class);
			migratorService.testMigratePartition(partition);
		}
		catch (final DLMException e)
		{
			// when adding another PartitionerConfigLine but the table is not DLM'ed yet, then again depending on out config,
			// throw an exception or DLM it on the fly.

			// if there is a DLMException, then depending on our config (LATER),
			// throw an exception (LATER),
			// skip the record (LATER)
			// or add another PartitionerConfigLine, get the additional line's records and retry.
			logger.info("Caught {}; going to retry with an augmented config", e.toString());

			final PartitionerConfig newConfig = PartitionerConfig
					.builder(config)
					.newLine().setTableName(e.getReferencingTableName())
					.newRef().setReferencingColumnName(e.getReferencingColumnName()).setReferencedTableName(e.getReferencedTableName())
					.endRef()
					.endLine()
					.build();
			return createPartition(newConfig);
		}
		return partition;
	}

	@Override
	public I_DLM_Partition storePartition(final Partition partition)
	{
		final I_DLM_Partition partitionDB = InterfaceWrapperHelper.newInstance(I_DLM_Partition.class);

		final I_DLM_Partion_Config partitionConfigDB = storePartitionConfig(partition.getConfig());
		partitionDB.setDLM_Partion_Config(partitionConfigDB);

		InterfaceWrapperHelper.save(partitionDB);

		// TODO: consider using directUpdate for better performance
		for (final IDLMAware dlmAware : partition.getRecords())
		{
			dlmAware.setDLM_Partition_ID(partitionDB.getDLM_Partition_ID());
			InterfaceWrapperHelper.save(dlmAware);
		}

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
		// get the "record" from DLM_PartionLine_Config.AD_Table_ID as the database record with the smallest ID
		// that does not yet have a DLM_Partition_ID
		final IDLMAware record = Services.get(IQueryBL.class)
				.createQueryBuilder(IDLMAware.class, tableName, new PlainContextAware(Env.getCtx()))
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
	 * @param line the line's <code>tableName</code> matches the given <code>record</code>. The line's <code>references</code> descripe which foraeign records the give <code>record</code> might reference. This method loads them and adds the to the given <code>records</code> set.
	 * @param record used to get and load further records this parameter references.
	 * @param records the set of records we will eventually return. Needed to detect circles by checking if a record was already added. the given <code>record</code> is already included.
	 *
	 * @return
	 */
	private Set<IDLMAware> recurse(
			final PartitionerConfigLine line,
			final IDLMAware record,
			final Set<IDLMAware> records)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(record);
		final String trxName = InterfaceWrapperHelper.getTrxName(record);

		final List<PartionerConfigReference> references = line.getReferences();

		// GO FORWARD
		// for each ref, load the referenced record and recurse with that record
		for (final PartionerConfigReference ref : references) // DLM_PartionReference_Config
		{
			// get the foreign key ID of
			// table DLM_PartionLine_Config.AD_Table_ID,
			// column DLM_PartionReference_Config.DLM_Referencing_Column_ID
			final Integer foreignKey = InterfaceWrapperHelper.getValueOrNull(record, ref.getReferencingColumnName());
			if (foreignKey == null || foreignKey <= 0)
			{
				logger.debug("IDLMAware={} does not reference anything via column={}", record, ref.getReferencingColumnName());
				continue;
			}

			// the table name of the foreign record which has 'foreignKey' as its ID
			final String foreignTableName = ref.getReferencedTableName();

			// load the foreign record from the table DLM_PartionReference_Config.DLM_Referenced_Table_ID
			final IDLMAware foreignRecord = InterfaceWrapperHelper.create(ctx, foreignTableName, foreignKey, IDLMAware.class, trxName);
			logger.debug("Loaded referenced IDLMAware={} from table={} via {}.{}={}", foreignRecord, foreignTableName, line.getTableName(), ref.getReferencingColumnName(), foreignKey);

			final boolean recordWasNotYetAddedBefore = records.add(foreignRecord);

			if (recordWasNotYetAddedBefore)
			{
				// get the foreign record's PartitionerConfigLine (DLM_PartionReference_Config.DLM_Referencing_PartionLine_Config_ID)
				// note: it's OK to have a referencedConfigLine that has itself no references, but it's not OK to have no
				final PartitionerConfigLine referencedConfigLine = ref.getReferencedConfigLine();
				if (referencedConfigLine != null)
				{
					recurse(referencedConfigLine, foreignRecord, records);
				}
				else
				{
					logger.debug("Referenced IDLMAware={} does not reference any further DLM records", foreignRecord);
				}

				// GO BACKWARDS, i.e. get records which reference the foreign record we just loaded
				backTrack(line.getParent(),
						InterfaceWrapperHelper.getContextAware(record),             // make it clear that record is only needed for ctx
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
				InterfaceWrapperHelper.getContextAware(record),             // make it clear that record is only needed for ctx
				records,
				line.getTableName(),
				InterfaceWrapperHelper.getId(record));

		return records;
	}

	/**
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
			final Set<IDLMAware> records,
			final String tableName,
			final Integer key)
	{
		final List<PartionerConfigReference> backwardReferences = config.getReferences(tableName);
		for (final PartionerConfigReference backwardRef : backwardReferences)
		{
			final PartitionerConfigLine backwardLine = backwardRef.getParent();

			// load all records which reference foreignRecord
			final List<IDLMAware> backwardRecords = Services.get(IQueryBL.class)
					.createQueryBuilder(IDLMAware.class, backwardLine.getTableName(), contextProvider)
					.addEqualsFilter(backwardRef.getReferencingColumnName(), key)
					.create()
					.list();
			for (final IDLMAware backwardRecord : backwardRecords)
			{
				final boolean backwardRecordWasNotYetAddedBefore = records.add(backwardRecord);
				if (backwardRecordWasNotYetAddedBefore)
				{
					recurse(backwardLine, backwardRecord, records);
				}
			}
		}
	}

	@Override
	public I_DLM_Partion_Config storePartitionConfig(final PartitionerConfig config)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		I_DLM_Partion_Config configDB;
		if (config.getDLM_Partion_Config_ID() > 0)
		{
			configDB = InterfaceWrapperHelper.create(Env.getCtx(), config.getDLM_Partion_Config_ID(), I_DLM_Partion_Config.class, ITrx.TRXNAME_ThreadInherited);
		}
		else
		{
			configDB = InterfaceWrapperHelper.newInstance(I_DLM_Partion_Config.class, new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_ThreadInherited));
			InterfaceWrapperHelper.save(configDB);
			config.setDLM_Partion_Config_ID(configDB.getDLM_Partion_Config_ID());
		}

		// we first need to persist only the lines,
		// so that we can be sure to later have all the IDs for DLM_Partion_Config_Reference.DLM_Partion_Config_Reference_ID
		for (final PartitionerConfigLine line : config.getLines())
		{
			I_DLM_Partion_Config_Line configLineDB;
			if (line.getDLM_Partion_Config_Line_ID() > 0)
			{
				configLineDB = InterfaceWrapperHelper.create(Env.getCtx(), line.getDLM_Partion_Config_Line_ID(), I_DLM_Partion_Config_Line.class, ITrx.TRXNAME_ThreadInherited);
			}
			else
			{
				configLineDB = InterfaceWrapperHelper.newInstance(I_DLM_Partion_Config_Line.class, new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_ThreadInherited));
			}
			configLineDB.setDLM_Partion_Config(configDB);

			final int referencingTableID = adTableDAO.retrieveTableId(line.getTableName());
			configLineDB.setDLM_Referencing_Table_ID(referencingTableID);
			InterfaceWrapperHelper.save(configLineDB);
			line.setDLM_Partion_Config_Line_ID(configLineDB.getDLM_Partion_Config_Line_ID());
		}

		for (final PartitionerConfigLine line : config.getLines())
		{
			for (final PartionerConfigReference ref : line.getReferences())
			{
				I_DLM_Partion_Config_Reference configRefDB;
				if (ref.getDLM_Partion_Config_Reference_ID() > 0)
				{
					configRefDB = InterfaceWrapperHelper.create(Env.getCtx(), line.getDLM_Partion_Config_Line_ID(), I_DLM_Partion_Config_Reference.class, ITrx.TRXNAME_ThreadInherited);
				}
				else
				{
					configRefDB = InterfaceWrapperHelper.newInstance(I_DLM_Partion_Config_Reference.class, new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_ThreadInherited));
				}
				configRefDB.setDLM_Partion_Config_Line_ID(line.getDLM_Partion_Config_Line_ID());

				final int referencedTableID = adTableDAO.retrieveTableId(ref.getReferencedTableName());
				configRefDB.setDLM_Referenced_Table_ID(referencedTableID);

				final I_AD_Column referencingColumn = adTableDAO.retrieveColumn(ref.getReferencedTableName(), ref.getReferencingColumnName());
				configRefDB.setDLM_Referencing_Column(referencingColumn);

				final PartitionerConfigLine referencedConfigLine = ref.getReferencedConfigLine();
				if (referencedConfigLine == null)
				{
					configRefDB.setDLM_Partion_Config_Reference_ID(0);
				}
				else
				{
					configRefDB.setDLM_Partion_Config_Reference_ID(referencedConfigLine.getDLM_Partion_Config_Line_ID());
				}
				InterfaceWrapperHelper.save(configRefDB);
				ref.setDLM_Partion_Config_Reference_ID(configRefDB.getDLM_Partion_Config_Reference_ID());
			}
		}
		return configDB;
	}

	@Override
	public PartitionerConfig loadPartitionConfig(I_DLM_Partion_Config configDB)
	{

		final Builder configBuilder = PartitionerConfig.builder();

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final List<I_DLM_Partion_Config_Line> lines = queryBL.createQueryBuilder(I_DLM_Partion_Config_Line.class, new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_ThreadInherited))
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_DLM_Partion_Config_Line.COLUMN_DLM_Partion_Config_ID, configDB.getDLM_Partion_Config_ID())
				.create()
				.list();

		for (I_DLM_Partion_Config_Line line : lines)
		{
			final LineBuilder lineBuilder = configBuilder.newLine().setTableName(line.getDLM_Referencing_Table().getTableName());

			final List<I_DLM_Partion_Config_Reference> refs = queryBL.createQueryBuilder(I_DLM_Partion_Config_Reference.class, new PlainContextAware(Env.getCtx(), ITrx.TRXNAME_ThreadInherited))
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_DLM_Partion_Config_Reference.COLUMN_DLM_Partion_Config_Line_ID, line.getDLM_Partion_Config_Line_ID())
					.create()
					.list();

			for (I_DLM_Partion_Config_Reference ref : refs)
			{
				final RefBuilder refBuilder = lineBuilder.newRef()
						.setReferencedTableName(ref.getDLM_Referenced_Table().getTableName())
						.setReferencingColumnName(ref.getDLM_Referencing_Column().getColumnName());

				if (ref.getDLM_Referenced_Table_Partion_Config_Line_ID() > 0)
				{
					refBuilder.setReferencedConfigLine(
							ref.getDLM_Referenced_Table_Partion_Config_Line().getDLM_Referencing_Table().getTableName());
				}
				refBuilder.endRef();
			}
			lineBuilder.endLine();
		}

		return configBuilder.build();
	}

}
