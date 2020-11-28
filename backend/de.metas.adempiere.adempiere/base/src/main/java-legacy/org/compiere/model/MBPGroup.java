/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved. *
 * This program is free software; you can redistribute it and/or modify it *
 * under the terms version 2 of the GNU General Public License as published *
 * by the Free Software Foundation. This program is distributed in the hope *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. *
 * See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along *
 * with this program; if not, write to the Free Software Foundation, Inc., *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA. *
 * For the text or an alternative of this public license, you may reach us *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA *
 * or via info@compiere.org or http://www.compiere.org/license.html *
 *****************************************************************************/
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

@SuppressWarnings("serial")
@Deprecated
public class MBPGroup extends X_C_BP_Group
{
	public MBPGroup(Properties ctx, int C_BP_Group_ID, String trxName)
	{
		super(ctx, C_BP_Group_ID, trxName);
		if (C_BP_Group_ID == 0)
		{
			// setValue (null);
			// setName (null);
			setIsConfidentialInfo(false);	// N
			setIsDefault(false);
			setPriorityBase(PRIORITYBASE_Same);
		}
	}

	public MBPGroup(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean afterSave(boolean newRecord, boolean success)
	{
		if (newRecord && success)
			return insert_Accounting("C_BP_Group_Acct", "C_AcctSchema_Default", null);
		return success;
	}

	@Override
	protected boolean beforeDelete()
	{
		return delete_Accounting("C_BP_Group_Acct");
	}
}
