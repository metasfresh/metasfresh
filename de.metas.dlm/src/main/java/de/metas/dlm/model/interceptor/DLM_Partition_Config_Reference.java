package de.metas.dlm.model.interceptor;

import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.service.ILookupDAO;
import org.adempiere.ad.service.ILookupDAO.ITableRefInfo;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.util.Services;
import org.compiere.util.DisplayType;

import de.metas.dlm.model.I_DLM_Partition_Config_Reference;

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
@Callout(I_DLM_Partition_Config_Reference.class)
public class DLM_Partition_Config_Reference
{
	static final DLM_Partition_Config_Reference INSTANCE = new DLM_Partition_Config_Reference();

	private DLM_Partition_Config_Reference()
	{
	}

	@CalloutMethod(columnNames = I_DLM_Partition_Config_Reference.COLUMNNAME_DLM_Referencing_Column_ID, skipIfCopying = true)
	public void updateTable(final I_DLM_Partition_Config_Reference ref)
	{
		if (ref.getDLM_Referencing_Column_ID() <= 0)
		{
			ref.setDLM_Referenced_Table_ID(0);
			return;
		}

		final int ad_Reference_ID = ref.getDLM_Referencing_Column().getAD_Reference_ID();
		if(!DisplayType.isLookup(ad_Reference_ID))
		{
			return;
		}

		final ILookupDAO lookupDAO = Services.get(ILookupDAO.class);
		final ITableRefInfo tableRefInfo;
		if (lookupDAO.isTableReference(ad_Reference_ID))
		{
			tableRefInfo = lookupDAO.retrieveTableRefInfo(ad_Reference_ID);
		}
		else
		{
			final String columnName = ref.getDLM_Referencing_Column().getColumnName();
			tableRefInfo = lookupDAO.retrieveTableDirectRefInfo(columnName);
		}

		if (tableRefInfo == null)
		{
			// what we do further up is not very sophisticated. e.g. for "CreatedBy", we currently don't find the table name.
			// therefore, don't set the table to null since we don't know that there is no table for the given column
			// ref.setDLM_Referenced_Table_ID(0);
			return;
		}

		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
		ref.setDLM_Referenced_Table_ID(adTableDAO.retrieveTableId(tableRefInfo.getTableName()));
	}
}
