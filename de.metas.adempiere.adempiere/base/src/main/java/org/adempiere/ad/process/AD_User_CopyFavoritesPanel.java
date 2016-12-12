/**
 * 
 */
package org.adempiere.ad.process;

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

import org.adempiere.ad.dao.impl.TypedSqlQuery;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.compiere.model.I_AD_TreeBar;
import org.compiere.model.MUser;

import de.metas.adempiere.model.I_AD_User;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

/**
 * @author cg
 * 
 */
public class AD_User_CopyFavoritesPanel extends JavaProcess
{

	private int p_AD_User_ID = -1;
	public static final String PARAM_AD_User_ID = "AD_User_ID";

	@Override
	protected void prepare()
	{
		for (ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
			{
				continue;
			}
			else if (name.equals(PARAM_AD_User_ID))
			{
				p_AD_User_ID = para.getParameterAsInt();
			}
		}
	}

	@Override
	protected String doIt()
	{
		Check.assume(p_AD_User_ID > 0, "User should not be empty! ");
		
		final int targetUser_ID = getRecord_ID(); 
		
		Check.assume(targetUser_ID > 0, "There is no record selected! ");

		Check.assume(InterfaceWrapperHelper.create(MUser.get(getCtx(), targetUser_ID), I_AD_User.class).isSystemUser(), "Selected user is not system user! ");

		final String whereClause = I_AD_TreeBar.COLUMNNAME_AD_User_ID + " = ? ";

		final List<I_AD_TreeBar> treBars = new TypedSqlQuery<I_AD_TreeBar>(getCtx(), I_AD_TreeBar.class, whereClause, get_TrxName())
												.setOnlyActiveRecords(true)
												.setParameters(p_AD_User_ID)
												.list();
												
		int cnt = 0;

		for (final I_AD_TreeBar treeBar : treBars)
		{
			if (!existsAlready(targetUser_ID, treeBar.getNode_ID(), treeBar.getAD_Tree_ID()))
			{
				final I_AD_TreeBar tb = InterfaceWrapperHelper.create(getCtx(), I_AD_TreeBar.class, get_TrxName());
				tb.setAD_Org_ID(treeBar.getAD_Org_ID());
				tb.setAD_Tree_ID(treeBar.getAD_Tree_ID());
				tb.setNode_ID(treeBar.getNode_ID());
				tb.setAD_User_ID(targetUser_ID);
				InterfaceWrapperHelper.save(tb);
				cnt++;
			}
		}
		

		return "Count: " + cnt;
	}

	/**
	 * check if the TreeBar already exists
	 * 
	 * @param AD_User_ID
	 * @param Node_ID
	 * @param AD_Tree_ID
	 * @return
	 */
	private boolean existsAlready(final int AD_User_ID, final int Node_ID, final int AD_Tree_ID)
	{
		final String whereClause = I_AD_TreeBar.COLUMNNAME_AD_User_ID + " = ? AND " 
								 + I_AD_TreeBar.COLUMNNAME_Node_ID + " = ? AND " 
								 + I_AD_TreeBar.COLUMNNAME_AD_Tree_ID + " = ?";
		
		return new TypedSqlQuery<I_AD_TreeBar>(getCtx(), I_AD_TreeBar.class, whereClause, get_TrxName())
				.setOnlyActiveRecords(true)
				.setParameters(AD_User_ID, Node_ID, AD_Tree_ID)
				.match();
	}

}
