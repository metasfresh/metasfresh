package org.adempiere.model.tree.spi.impl;

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


import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.tree.IADTreeAware;
import org.compiere.model.I_C_Element;
import org.compiere.model.I_C_ElementValue;
import org.compiere.model.MTree_Base;
import org.compiere.model.PO;
import org.compiere.model.Query;

public class MElementValueTreeSupport extends DefaultPOTreeSupport
{
	@Override
	public int getAD_Tree_ID(final PO po)
	{
		if (!InterfaceWrapperHelper.isInstanceOf(po, I_C_ElementValue.class))
		{
			return -1;
		}
		final I_C_ElementValue ev = InterfaceWrapperHelper.create(po, I_C_ElementValue.class);

		final IADTreeAware treeAware = InterfaceWrapperHelper.create(ev.getC_Element(), IADTreeAware.class);
		return treeAware.getAD_Tree_ID();
	}

	@Override
	public String getWhereClause(MTree_Base tree)
	{
		final String whereClause = I_C_Element.COLUMNNAME_AD_Tree_ID + "=?";
		final int C_Element_ID = new Query(tree.getCtx(), I_C_Element.Table_Name, whereClause, tree.get_TrxName())
				.setParameters(tree.getAD_Tree_ID())
				.firstIdOnly();
		if (C_Element_ID <= 0)
		{
			// the tree is not yet referenced from any C_Element record. maybe it was just created
			return "0=1";
		}
		
		final StringBuilder sql = new StringBuilder();
		sql.append(I_C_ElementValue.COLUMNNAME_C_Element_ID).append("=").append(C_Element_ID);
		return sql.toString();
	}
}
