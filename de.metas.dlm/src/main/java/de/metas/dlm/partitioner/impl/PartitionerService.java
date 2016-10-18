package de.metas.dlm.partitioner.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.service.IColumnBL;
import de.metas.dlm.Partition;
import de.metas.dlm.exception.DLMException;
import de.metas.dlm.migrator.IMigratorService;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_DLM_Partition;
import de.metas.dlm.partitioner.IPartitionerService;
import de.metas.dlm.partitioner.config.PartionConfigReference;
import de.metas.dlm.partitioner.config.PartitionerConfig;
import de.metas.dlm.partitioner.config.PartitionerConfigLine;
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
		// i.e. those DLM_PartionLine_Configs that are not referenced via DLM_PartionReference_Config.DLM_Referencing_PartionLine_Config_ID
		// i think we can also live with circles, i.e. if there is no "first", but for those "firsts" that there ware, we need to start with them
		final List<PartitionerConfigLine> lines = config.getLines(); // DLM_PartionLine_Config

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
			// if there is a DLMException, then depending on our config (LATER),
			// throw an exception (LATER),
			// skip the record (LATER)
			// or add another PartitionerConfigLine, get the additional line's records and retry.

			// when adding another PartitionerConfigLine but the table is not DLM'ed yet, then again depending on out config,
			// throw an exception or DLM it on the fly.

		}
		return partition;
	}

	@Override
	public void storePartition(final Partition partition)
	{
		final I_DLM_Partition partitionDB = InterfaceWrapperHelper.newInstance(I_DLM_Partition.class);
		// TODO: also let the new DLM_Partition record reference the config's DB records
		InterfaceWrapperHelper.save(partitionDB);

		// TODO: consider using directUpdate for better performance
		for (final IDLMAware dlmAware : partition.getRecords())
		{
			dlmAware.setDLM_Partition_ID(partitionDB.getDLM_Partition_ID());
			InterfaceWrapperHelper.save(dlmAware);
		}
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
	 * @param line
	 * @param record used to get and load further records which reference the given <code>record</code>.
	 *
	 * @param records the set of records we will eventually return. Needed to detect circles by checking if a record was already added. the given <code>record</code> is already included.
	 * @return
	 */
	private Set<IDLMAware> recurse(final PartitionerConfigLine line, final IDLMAware record, final Set<IDLMAware> records)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(record);
		final String trxName = InterfaceWrapperHelper.getTrxName(record);

		// for each ref,
		for (final PartionConfigReference ref : line.getReferences()) // DLM_PartionReference_Config
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

			// load the foreign record from the table DLM_PartionReference_Config.DLM_Referenced_Table_ID
			final IDLMAware foreignRecord = InterfaceWrapperHelper.create(ctx, ref.getReferencedTableName(), foreignKey, IDLMAware.class, trxName);
			logger.debug("Loaded referenced IDLMAware={} from table={} via {}.{}={}", foreignRecord, ref.getReferencedTableName(), line.getTableName(), ref.getReferencingColumnName(), foreignKey);

			final boolean recordWasNotYetAddedBefore = records.add(foreignRecord);

			// get the foreign record's PartitionerConfigLine (DLM_PartionReference_Config.DLM_Referencing_PartionLine_Config_ID)
			// note: it's OK to have a referencedConfigLine that has itself no references, but it's not OK to have no
			final PartitionerConfigLine referencedConfigLine = ref.getReferencedConfigLine();
			if (referencedConfigLine == null)
			{
				logger.debug("Referenced IDLMAware={} does not reference any further DLM records", foreignRecord);
				continue;
			}

			if (recordWasNotYetAddedBefore)
			{
				recurse(referencedConfigLine, foreignRecord, records);
			}
			else
			{
				logger.debug("IDLMAware={} was already added in a previous iteration. Returning", record); // avoid circles
			}
		}

		return records;
	}

}
