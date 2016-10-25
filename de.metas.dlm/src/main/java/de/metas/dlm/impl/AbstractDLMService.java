package de.metas.dlm.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
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
import de.metas.dlm.Partition;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_AD_Table;
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
		final String trxName = InterfaceWrapperHelper.getTrxName(table);

		executeDBFunction_add_table_to_dlm(table.getTableName(), trxName); // make sure that the DB call and the changes to table take place in the same trx.

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
	public void directUpdateDLMColumn(final IContextAware ctxAware, final Partition partition, final String columnName, final int targetValue)
	{
		final Map<String, List<IDLMAware>> table2Record = partition
				.getRecords()
				.stream()
				.collect(Collectors.groupingBy(dlmAware -> InterfaceWrapperHelper.getModelTableName(dlmAware)));

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IColumnBL columnBL = Services.get(IColumnBL.class);

		for (final Entry<String, List<IDLMAware>> tableWithRecords : table2Record.entrySet())
		{
			final String tableName = tableWithRecords.getKey();
			final String keyColumn = columnBL.getSingleKeyColumn(tableName);

			final List<IDLMAware> records = tableWithRecords.getValue();
			final Integer[] recordIds = records
					.stream()
					.map(r -> InterfaceWrapperHelper.getId(r))
					.toArray(size -> new Integer[size]);

			final int updated = queryBL.createQueryBuilder(IDLMAware.class, tableName, ctxAware)
					.addInArrayFilter(keyColumn, recordIds)
					.create()
					.updateDirectly()
					.addSetColumnValue(columnName, targetValue)
					.execute();
			logger.debug("Table {}: updated {} record(s) to DLM_Level={} (but not yet committed!)", tableName, updated, targetValue);
		}
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

		final PlainContextAware ctxAware = new PlainContextAware(ctx, trxName);

		// get the list of all columns whose names that end with "Record_ID". They probably belong to a column-record table (but we will make sure).
		// we could have queried for columns ending with "Table_ID", but there might be more "*Table_ID" columns that don't have a "*Record_ID" column than the other way around.
		final List<I_AD_Column> recordIdColumns = queryBL.createQueryBuilder(I_AD_Column.class, ctxAware)
				.addOnlyActiveRecordsFilter()
				.addEndsWithQueryFilter(I_AD_Column.COLUMNNAME_ColumnName, "Record_ID")
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
							TableReferenceDescriptor.of(referencedTableName, table.getTableName(), recordIdColumn.getColumnName())));
		}

		return result;
	}

}
