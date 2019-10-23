package org.adempiere.ad.service.impl;

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

import org.adempiere.ad.service.IADInfoWindowBL;
import org.adempiere.ad.service.IADInfoWindowDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_InfoColumn;
import org.compiere.model.I_AD_InfoWindow;
import org.compiere.model.I_AD_InfoWindow_From;
import org.compiere.model.MTable;
import org.compiere.model.MTree;
import org.compiere.util.Env;

import de.metas.util.Check;
import de.metas.util.Services;

public class ADInfoWindowBL implements IADInfoWindowBL
{
	@Override
	public String getSqlFrom(final I_AD_InfoWindow infoWindow)
	{
		Check.assumeNotNull(infoWindow, "infoWindow not null");

		final StringBuilder sqlFrom = new StringBuilder();

		if (!Check.isEmpty(infoWindow.getFromClause(), true))
		{
			sqlFrom.append(infoWindow.getFromClause());
		}

		//
		// Add addtional SQL FROM clauses
		final List<I_AD_InfoWindow_From> fromDefs = Services.get(IADInfoWindowDAO.class).retrieveFroms(infoWindow);
		for (final I_AD_InfoWindow_From fromDef : fromDefs)
		{
			if (!fromDef.isActive())
			{
				continue;
			}
			if (Check.isEmpty(fromDef.getFromClause(), true))
			{
				continue;
			}

			if (sqlFrom.length() > 0)
			{
				sqlFrom.append("\n");
			}
			sqlFrom.append(fromDef.getFromClause());
		}

		return sqlFrom.toString();
	}

	@Override
	public String getTableName(final I_AD_InfoWindow infoWindow)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(infoWindow);
		return MTable.getTableName(ctx, infoWindow.getAD_Table_ID());
	}

	@Override
	public String getTreeColumnName(final I_AD_InfoWindow infoWindow)
	{
		final I_AD_InfoColumn treeColumn = Services.get(IADInfoWindowDAO.class).retrieveTreeInfoColumn(infoWindow);
		if (treeColumn == null)
		{
			return null;
		}

		final String treeColumnName = treeColumn.getSelectClause();
		return treeColumnName;
	}

	@Override
	// @Cached
	public int getAD_Tree_ID(final I_AD_InfoWindow infoWindow)
	{
		String keyName = getTreeColumnName(infoWindow);
		// the keyName is wrong typed in Mtree class
		// TODO: HARDCODED
		if ("M_Product_Category_ID".equals(keyName))
		{
			keyName = "M_ProductCategory_ID";
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(infoWindow);
		final int adTreeId = MTree.getDefaultAD_Tree_ID(Env.getAD_Client_ID(ctx), keyName);
		return adTreeId;
	}

	@Override
	public boolean isShowTotals(final I_AD_InfoWindow infoWindow)
	{
		return false;
	}

}
