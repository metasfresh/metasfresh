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

/**
 * 	Recurring Run Model
 *
 *	@author Jorg Janke
 *	@version $Id: MRecurringRun.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MRecurringRun extends X_C_Recurring_Run
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5247737541955962981L;

	public MRecurringRun (Properties ctx, int C_Recurring_Run_ID, String trxName)
	{
		super (ctx, C_Recurring_Run_ID, trxName);
	}	//	MRecurringRun

	public MRecurringRun (Properties ctx, MRecurring recurring)
	{
		super(ctx, 0, recurring.get_TrxName());
		if (recurring != null)
		{
			setAD_Client_ID(recurring.getAD_Client_ID());
			setAD_Org_ID(recurring.getAD_Org_ID());
			setC_Recurring_ID (recurring.getC_Recurring_ID ());
			setDateDoc(recurring.getDateNextRun());
		}
	}	//	MRecurringRun

	public MRecurringRun (Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}	//	MRecurringRun

}	//	MRecurringRun
