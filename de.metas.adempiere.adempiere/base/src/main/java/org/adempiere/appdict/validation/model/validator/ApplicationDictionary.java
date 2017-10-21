/**
 * 
 */
package org.adempiere.appdict.validation.model.validator;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.List;
import java.util.Properties;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Field;
import org.compiere.model.I_AD_Ref_Table;
import org.compiere.model.I_AD_Tab;
import org.compiere.model.MClient;
import org.compiere.model.MColumn;
import org.compiere.model.MSequence;
import org.compiere.model.MTable;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * @author tsa
 */
public class ApplicationDictionary implements ModelValidator
{
	private final Logger log = LogManager.getLogger(getClass());

	private int m_AD_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public void initialize(ModelValidationEngine engine, MClient client)
	{
		if (client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
		}

		engine.addModelChange(I_AD_Tab.Table_Name, this);
		engine.addModelChange(I_AD_Ref_Table.Table_Name, this);
	}

	@Override
	public String login(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		//
		// Log Migration Scripts, if we log in with SysAdm role and the ID server is configured
		if (AD_Role_ID == IUserRolePermissions.SYSTEM_ROLE_ID && MSequence.isExternalIDSystemEnabled())
		{
			Ini.setProperty(Ini.P_LOGMIGRATIONSCRIPT, true);
		}
		else
		{
			Ini.setProperty(Ini.P_LOGMIGRATIONSCRIPT, false);
		}
		return null;
	}

	@Override
	public String modelChange(PO po, int type) throws Exception
	{
		if (I_AD_Tab.Table_Name.equals(po.get_TableName()) && type == TYPE_AFTER_CHANGE
				&& po.is_ValueChanged(I_AD_Tab.COLUMNNAME_AD_Table_ID))
		{
			I_AD_Tab tab = InterfaceWrapperHelper.create(po, I_AD_Tab.class);
			updateTabFieldColumns(po.getCtx(), tab, po.get_TrxName());
		}
		if (I_AD_Ref_Table.Table_Name.equals(po.get_TableName())
				&& (type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE))
		{
			I_AD_Ref_Table refTable = InterfaceWrapperHelper.create(po, I_AD_Ref_Table.class);
			validate(refTable);
		}
		//
		return null;
	}

	private void validate(I_AD_Ref_Table refTable)
	{
		String whereClause = refTable.getWhereClause();
		if (!Check.isEmpty(whereClause, true) && whereClause.indexOf("@") >= 0)
		{
			log.warn("Using context variables in table reference where clause is not adviced");
		}
	}

	private void updateTabFieldColumns(Properties ctx, I_AD_Tab tab, String trxName)
	{
		final String tableName = MTable.getTableName(ctx, tab.getAD_Table_ID());
		final List<I_AD_Field> fields = new Query(ctx, I_AD_Field.Table_Name, I_AD_Field.COLUMNNAME_AD_Tab_ID + "=?", trxName)
				.setParameters(tab.getAD_Tab_ID())
				.setOrderBy(I_AD_Field.COLUMNNAME_SeqNo)
				.list(I_AD_Field.class);

		for (I_AD_Field field : fields)
		{
			I_AD_Column columnOld = field.getAD_Column();
			int columnId = MColumn.getColumn_ID(tableName, columnOld.getColumnName());
			if (columnId > 0)
			{
				field.setAD_Column_ID(columnId);
				InterfaceWrapperHelper.save(field);
				log.info("Updated AD_Column_ID for field " + field.getName());
			}
			else
			{
				log.info("Deleting field " + field.getName());
				InterfaceWrapperHelper.getPO(field).deleteEx(true); // TODO: use POWrapper.delete
			}
		}
	}

	@Override
	public String docValidate(PO po, int timing)
	{
		return null;
	}
}
