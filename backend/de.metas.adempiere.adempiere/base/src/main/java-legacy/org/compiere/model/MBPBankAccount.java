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
public class MBPBankAccount extends X_C_BP_BankAccount
{
	public MBPBankAccount(Properties ctx, int C_BP_BankAccount_ID, String trxName)
	{
		super(ctx, C_BP_BankAccount_ID, trxName);
		if (is_new())
		{
			// setC_BPartner_ID (0);
			setIsACH(false);
			setBPBankAcctUse(BPBANKACCTUSE_Both);
		}
	}

	public MBPBankAccount(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		// maintain routing on bank level
		if (isACH() && getC_Bank_ID() > 0)
		{
			setRoutingNo(null);
		}

		return true;
	}

	@Override
	public String toString()
	{
		return new StringBuilder("MBP_BankAccount[")
				.append(get_ID())
				.append(", Name=").append(getA_Name())
				.append("]")
				.toString();
	}
}
