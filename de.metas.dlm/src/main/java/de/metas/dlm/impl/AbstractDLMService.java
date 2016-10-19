package de.metas.dlm.impl;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.util.DisplayType;

import de.metas.dlm.IDLMService;
import de.metas.dlm.model.IDLMAware;
import de.metas.dlm.model.I_AD_Table;

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

	@Override
	public void addTableToDLM(final I_AD_Table table)
	{
		executeDBFunction_add_table_to_dlm(table.getTableName());
		createOrUpdateDlmColumn(table, IDLMAware.COLUMNNAME_DLM_Level);
		createOrUpdateDlmColumn(table, IDLMAware.COLUMNNAME_DLM_Partition_ID);

		table.setIsDLM(true);
		InterfaceWrapperHelper.save(table);
	}

	private void createOrUpdateDlmColumn(final I_AD_Table table, final String columnName)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		// retrieve the column if it exists, even with IsActive='N'
		final IQueryBuilder<I_AD_Column> columnQueryBuilder = adTableDAO.retrieveColumnQueryBuilder(table.getTableName(), columnName);
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
		executeDBFunction_remove_table_from_dlm(table.getTableName());
		deactivateDlmColumn(table, IDLMAware.COLUMNNAME_DLM_Level);
		deactivateDlmColumn(table, IDLMAware.COLUMNNAME_DLM_Partition_ID);

		table.setIsDLM(false);
		InterfaceWrapperHelper.save(table);
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

	abstract void executeDBFunction_add_table_to_dlm(String tableName);

	abstract void executeDBFunction_remove_table_from_dlm(String tableName);

}
