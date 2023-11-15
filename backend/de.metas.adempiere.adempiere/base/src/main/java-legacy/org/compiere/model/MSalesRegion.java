/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

// TODO DELETE ME
@Deprecated
public class MSalesRegion extends X_C_SalesRegion
{
	/**
	 *
	 */
	private static final long serialVersionUID = 8582026748675153489L;

	@SuppressWarnings("unused")
	public MSalesRegion(Properties ctx, int C_SalesRegion_ID, String trxName)
	{
		super(ctx, C_SalesRegion_ID, trxName);
	}

	@SuppressWarnings("unused")
	public MSalesRegion(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	protected boolean beforeSave(boolean newRecord)
	{
		if (getAD_Org_ID() != 0)
			setAD_Org_ID(0);
		return true;
	}

	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (!success)
		{
			return false;
		}

		// if (newRecord)
		// 	insert_Tree(MTree_Base.TREETYPE_SalesRegion);

		//	Value/Name change
		if (!newRecord && (is_ValueChanged(COLUMNNAME_Value) || is_ValueChanged(COLUMNNAME_Name)))
		{
			MAccount.updateValueDescription(getCtx(), "C_SalesRegion_ID=" + getC_SalesRegion_ID(), get_TrxName());
		}

		return true;
	}

	// protected boolean afterDelete(boolean success)
	// {
	// 	if (success)
	// 		delete_Tree(MTree_Base.TREETYPE_SalesRegion);
	// 	return success;
	// }
}
