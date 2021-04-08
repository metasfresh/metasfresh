/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package org.adempiere.ad.table.api.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.adempiere.service.impl.TooltipType;
import de.metas.document.DocumentConstants;
import de.metas.i18n.ITranslatableString;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.column.AdColumnId;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.UpperCaseQueryFilterModifier;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.service.ISequenceDAO;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.ColumnSqlSourceDescriptor;
import org.adempiere.ad.table.api.ColumnSqlSourceDescriptor.FetchTargetRecordsMethod;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.window.api.IADWindowDAO;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_SQLColumn_SourceTableColumn;
import org.compiere.model.I_AD_Table;
import org.compiere.model.X_AD_SQLColumn_SourceTableColumn;
import org.compiere.util.DB;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.createOld;
import static org.adempiere.model.InterfaceWrapperHelper.getCtx;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class ADTableDAO implements IADTableDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private static final ImmutableSet<String> STANDARD_COLUMN_NAMES = ImmutableSet.of(
			"AD_Client_ID", "AD_Org_ID",
			"IsActive",
			"Created", "CreatedBy",
			"Updated", "UpdatedBy");

	@Override
	public I_AD_Column retrieveColumnById(@NonNull final AdColumnId columnId)
	{
		return load(columnId, I_AD_Column.class);
	}

	@Override
	public I_AD_Column retrieveColumn(@NonNull final AdTableId tableId, @NonNull final String columnName)
	{
		final I_AD_Column columnRecord = queryBL.createQueryBuilder(I_AD_Column.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Table_ID, tableId)
				.addEqualsFilter(I_AD_Column.COLUMNNAME_ColumnName, columnName)
				.create()
				.firstOnly(I_AD_Column.class);

		if (columnRecord == null)
		{
			if (Adempiere.isUnitTestMode())
			{
				final I_AD_Column newColumnRecord = newInstance(I_AD_Column.class);
				newColumnRecord.setAD_Table_ID(tableId.getRepoId());
				newColumnRecord.setColumnName(columnName);
				newColumnRecord.setName(columnName + " + on-the-fly created for unit-test");
				saveRecord(newColumnRecord);
				return newColumnRecord;
			}
			throw new AdempiereException("@NotFound@ @AD_Column_ID@ " + columnName + " (@AD_Table_ID@=" + tableId + ")");
		}
		return columnRecord;
	}

	@Override
	public AdColumnId retrieveColumnId(final AdTableId tableId, final String columnName)
	{
		final I_AD_Column column = retrieveColumn(tableId, columnName);

		return AdColumnId.ofRepoId(column.getAD_Column_ID());
	}

	@Override
	public I_AD_Column retrieveColumn(final String tableName, final String columnName)
	{
		final I_AD_Column column = retrieveColumnOrNull(tableName, columnName);
		if (column == null)
		{
			throw new AdempiereException("@NotFound@ @AD_Column_ID@ " + columnName + " (@AD_Table_ID@=" + tableName + ")");
		}
		return column;
	}

	@Override
	public I_AD_Column retrieveColumnOrNull(final String tableName, final String columnName)
	{
		final IQueryBuilder<I_AD_Column> queryBuilder = retrieveColumnQueryBuilder(tableName, columnName, ITrx.TRXNAME_ThreadInherited);
		return queryBuilder.create()
				.setOnlyActiveRecords(true)
				.firstOnly(I_AD_Column.class);
	}

	@Override
	public boolean hasColumnName(final String tableName, final String columnName)
	{
		final IQueryBuilder<I_AD_Column> queryBuilder = retrieveColumnQueryBuilder(tableName, columnName, ITrx.TRXNAME_None);
		return queryBuilder.create()
				.setOnlyActiveRecords(true)
				.anyMatch();
	}

	@Override
	public IQueryBuilder<I_AD_Column> retrieveColumnQueryBuilder(final String tableName,
			final String columnName,
			@Nullable final String trxName)
	{
		return queryBL.createQueryBuilder(I_AD_Column.class, Env.getCtx(), trxName)
				.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Table_ID, retrieveTableId(tableName))
				.addEqualsFilter(I_AD_Column.COLUMNNAME_ColumnName, columnName, UpperCaseQueryFilterModifier.instance);
	}

	@Override
	public String retrieveColumnName(final int adColumnId)
	{
		Check.assumeGreaterThanZero(adColumnId, "adColumnId");
		return DB.getSQLValueStringEx(ITrx.TRXNAME_None, "SELECT ColumnName FROM AD_Column WHERE AD_Column_ID=?", adColumnId);
	}

	@Override
	public String retrieveTableName(@NonNull final AdTableId adTableId)
	{
		return TableIdsCache.instance.getTableName(adTableId);
	}

	// IMPORTANT: make sure we are returning -1 in case tableName was not found (and NOT throw exception),
	// because there is business logic which depends on this
	@Override
	public int retrieveTableId(@Nullable final String tableName)
	{
		if (tableName == null || Check.isBlank(tableName))
		{
			return -1;
		}

		return TableIdsCache.instance.getTableId(tableName)
				.map(AdTableId::getRepoId)
				.orElse(-1);
	}

	@Override
	public AdTableId retrieveAdTableId(@Nullable final String tableName)
	{
		return AdTableId.ofRepoId(retrieveTableId(tableName));
	}

	@Override
	public boolean isExistingTable(final String tableName)
	{
		if (Check.isEmpty(tableName, true))
		{
			return false;
		}
		return retrieveTableId(tableName) > 0;
	}

	@Override
	public boolean isTableId(final String tableName, final int adTableId)
	{
		if (adTableId <= 0)
		{
			return false;
		}
		if (Check.isEmpty(tableName))
		{
			return false;
		}
		return adTableId == retrieveTableId(tableName);
	}

	@Override
	public List<I_AD_Table> retrieveAllTables(final Properties ctx, final String trxName)
	{
		return queryBL.createQueryBuilder(I_AD_Table.class, ctx, trxName)
				.orderBy(I_AD_Table.COLUMNNAME_TableName)
				.create()
				.list();
	}

	@Override
	public boolean isVirtualColumn(final I_AD_Column column)
	{
		final String s = column.getColumnSQL();
		return !Check.isEmpty(s, true);
	}

	@Override
	public Optional<AdWindowId> retrieveWindowId(final String tableName)
	{
		final I_AD_Table adTable = retrieveTable(tableName);
		if (adTable == null)
		{
			return Optional.empty();
		}

		return AdWindowId.optionalOfRepoId(adTable.getAD_Window_ID());
	}

	@Override
	public String retrieveWindowName(final Properties ctx, final String tableName)
	{
		final AdWindowId adWindowId = retrieveWindowId(tableName).orElse(null);
		if (adWindowId == null)
		{
			return "";
		}

		final IADWindowDAO adWindowDAO = Services.get(IADWindowDAO.class);
		final ITranslatableString windowName = adWindowDAO.retrieveWindowName(adWindowId);
		return windowName.translate(Env.getAD_Language(ctx));
	}

	@Override
	public void onTableNameRename(@NonNull final I_AD_Table table)
	{
		final I_AD_Table tableOld = createOld(table, I_AD_Table.class);
		final String tableNameOld = tableOld.getTableName();
		final String tableNameNew = table.getTableName();

		// Do nothing if the table name was not actually changed
		if (Objects.equals(tableNameOld, tableNameNew))
		{
			return;
		}

		final Properties ctx = getCtx(table);
		Services.get(ISequenceDAO.class).renameTableSequence(ctx, tableNameOld, tableNameNew);
	}

	@Override
	public I_AD_Element retrieveElement(final String columnName)
	{
		return queryBL.createQueryBuilderOutOfTrx(I_AD_Element.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Element.COLUMNNAME_ColumnName, columnName)
				.create()
				.firstOnly(I_AD_Element.class);
	}

	@Override
	public I_AD_Table retrieveTable(final String tableName)
	{
		final int adTableId = retrieveTableId(tableName);
		return loadOutOfTrx(adTableId, I_AD_Table.class); // load out of trx to benefit from caching
	}

	@Override
	public I_AD_Table retrieveTable(@NonNull final AdTableId tableId)
	{
		return loadOutOfTrx(tableId, I_AD_Table.class); // load out of trx to benefit from caching
	}

	@Override
	public I_AD_Table retrieveTableOrNull(@Nullable final String tableName)
	{
		final int adTableId = retrieveTableId(tableName);
		if (adTableId <= 0)
		{
			return null;
		}
		return loadOutOfTrx(adTableId, I_AD_Table.class); // load out of trx to benefit from caching
	}

	@Override
	public List<I_AD_Column> retrieveColumnsForTable(final I_AD_Table table)
	{
		return queryBL.createQueryBuilder(I_AD_Column.class, table)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Column.COLUMNNAME_AD_Table_ID, table.getAD_Table_ID())
				.orderBy(I_AD_Column.COLUMNNAME_AD_Column_ID)
				.create()
				.list();
	}

	@Override
	public I_AD_Table retrieveDocumentTableTemplate(final I_AD_Table targetTable)
	{
		return retrieveTable(DocumentConstants.AD_TABLE_Document_Template_TableName);
	}

	@Override
	public boolean isStandardColumn(final String columnName)
	{
		return STANDARD_COLUMN_NAMES.contains(columnName);
	}

	@Override
	public Set<String> getTableNamesWithRemoteCacheInvalidation()
	{
		final List<String> tableNames = queryBL.createQueryBuilder(I_AD_Table.Table_Name)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Table.COLUMNNAME_IsEnableRemoteCacheInvalidation, true)
				.orderBy(I_AD_Table.COLUMNNAME_TableName)
				.create()
				.listDistinct(I_AD_Table.COLUMNNAME_TableName, String.class);
		return ImmutableSet.copyOf(tableNames);
	}

	@Override
	public int getTypeaheadMinLength(@NonNull final String tableName)
	{
		final I_AD_Table table = retrieveTable(tableName);
		final int typeaheadMinLength = table.getACTriggerLength();
		return typeaheadMinLength > 0 ? typeaheadMinLength : 0;
	}

	@Override
	public List<I_AD_Table> retrieveAllImportTables()
	{
		return queryBL.createQueryBuilderOutOfTrx(I_AD_Table.class)
				.addOnlyActiveRecordsFilter()
				.addStringLikeFilter(I_AD_Table.COLUMNNAME_TableName, "I_%", /* ignore case */false)
				.orderBy(I_AD_Table.COLUMNNAME_TableName)
				.create()
				.stream()
				.filter(table -> table.getTableName().startsWith("I_")) // required because "I_%" could match "IMP_blabla" too
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<ColumnSqlSourceDescriptor> retrieveColumnSqlSourceDescriptors()
	{
		return queryBL.createQueryBuilderOutOfTrx(I_AD_SQLColumn_SourceTableColumn.class)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_SQLColumn_SourceTableColumn.COLUMNNAME_AD_Column_ID)
				.orderBy(I_AD_SQLColumn_SourceTableColumn.COLUMNNAME_AD_SQLColumn_SourceTableColumn_ID)
				.create()
				.stream()
				.map(this::toColumnSqlSourceDescriptor)
				.collect(ImmutableList.toImmutableList());
	}

	private ColumnSqlSourceDescriptor toColumnSqlSourceDescriptor(final I_AD_SQLColumn_SourceTableColumn record)
	{
		final String targetTableName = retrieveTableName(record.getAD_Table_ID());
		final String sourceTableName = retrieveTableName(record.getSource_Table_ID());
		final String fetchTargetRecordsMethod = record.getFetchTargetRecordsMethod();

		if (X_AD_SQLColumn_SourceTableColumn.FETCHTARGETRECORDSMETHOD_SQL.equals(fetchTargetRecordsMethod))
		{
			return ColumnSqlSourceDescriptor.builder()
					.targetTableName(targetTableName)
					.sourceTableName(sourceTableName)
					.fetchTargetRecordsMethod(FetchTargetRecordsMethod.SQL)
					.sqlToGetTargetRecordIdBySourceRecordId(record.getSQL_GetTargetRecordIdBySourceRecordId())
					.build();
		}
		else if (X_AD_SQLColumn_SourceTableColumn.FETCHTARGETRECORDSMETHOD_LinkColumn.equals(fetchTargetRecordsMethod))
		{
			return ColumnSqlSourceDescriptor.builder()
					.targetTableName(targetTableName)
					.sourceTableName(sourceTableName)
					.fetchTargetRecordsMethod(FetchTargetRecordsMethod.LINK_COLUMN)
					.linkColumnName(retrieveColumnName(record.getLink_Column_ID()))
					.build();
		}
		else
		{
			throw new AdempiereException("Unknown " + I_AD_SQLColumn_SourceTableColumn.COLUMNNAME_FetchTargetRecordsMethod + ": " + fetchTargetRecordsMethod);
		}
	}

	@Override
	public void validate(@NonNull final I_AD_SQLColumn_SourceTableColumn record)
	{
		toColumnSqlSourceDescriptor(record); // shall throw exception if not valid
	}

	@Override
	public @NonNull TooltipType getTooltipTypeByTableName(@NonNull final String tableName)
	{
		return TableIdsCache.instance.getTooltipType(tableName);
	}
}
