package de.metas.dlm.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.table.process.AD_Table_CreatePK;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.service.IColumnBL;
import de.metas.adempiere.util.CacheCtx;
import de.metas.adempiere.util.CacheTrx;
import de.metas.dlm.IDLMService;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_AD_Table;
import de.metas.dlm.model.I_DLM_Partition;
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
	public void directUpdateDLMColumn(final IContextAware ctxAware,
			final int dlmPartitionId,
			final String columnName,
			final int targetValue)
	{

		// get all DLMed table names. they all might contain a record that belongs to the given partition
		final Stream<IQueryBuilder<IDLMAware>> queryBuilders = retrieveDLMTableNames(ctxAware, dlmPartitionId);

		queryBuilders.forEach(queryBuilder -> {

			final int updated = queryBuilder
					.addNotEqualsFilter(columnName, targetValue) // exclude records that already have the target value
					.create()
					.updateDirectly()
					.addSetColumnValue(columnName, targetValue)
					.execute();

			logger.debug("Table {}: updated {} record(s) to {}={} (but not yet committed!)", queryBuilder.getTableName(), updated, columnName, targetValue);
		});
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
}

