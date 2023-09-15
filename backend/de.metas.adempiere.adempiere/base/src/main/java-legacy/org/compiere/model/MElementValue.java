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

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.DB;

import java.io.Serial;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Natural Account
 *
 * @author Jorg Janke
 * @author Teo Sarca, SC ARHIPAC SERVICE SRL
 * BF [ 1883533 ] Change to summary - valid combination issue
 * BF [ 2320411 ] Translate "Already posted to" message
 * @version $Id: MElementValue.java,v 1.3 2006/07/30 00:58:37 jjanke Exp $
 */
public class MElementValue extends X_C_ElementValue
{
	/**
	 *
	 */
	@Serial private static final long serialVersionUID = 4765839867934329276L;

	public MElementValue(Properties ctx, int C_ElementValue_ID, String trxName)
	{
		super(ctx, C_ElementValue_ID, trxName);
		if (is_new())
		{
			//	setC_Element_ID (0);	//	Parent
			//	setName (null);
			//	setValue (null);
			setIsSummary(false);
			setAccountSign(ACCOUNTSIGN_Natural);
			setAccountType(ACCOUNTTYPE_Expense);
			setIsDocControlled(false);
			setIsForeignCurrency(false);
			setIsBankAccount(false);
			//
			setPostActual(true);
			setPostBudget(true);
			setPostEncumbrance(true);
			setPostStatistical(true);
		}
	}    //	MElementValue

	@SuppressWarnings("unused")
	public MElementValue(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}    //	MElementValue

	/**
	 * Is this a Balance Sheet Account
	 *
	 * @return boolean
	 */
	public boolean isBalanceSheet()
	{
		String accountType = getAccountType();
		return (ACCOUNTTYPE_Asset.equals(accountType)
				|| ACCOUNTTYPE_Liability.equals(accountType)
				|| ACCOUNTTYPE_OwnerSEquity.equals(accountType));
	}    //	isBalanceSheet

	/**
	 * Is this an Activa Account
	 *
	 * @return boolean
	 */
	public boolean isActiva()
	{
		return ACCOUNTTYPE_Asset.equals(getAccountType());
	}    //	isActive

	/**
	 * Is this a Passiva Account
	 *
	 * @return boolean
	 */
	public boolean isPassiva()
	{
		String accountType = getAccountType();
		return (ACCOUNTTYPE_Liability.equals(accountType)
				|| ACCOUNTTYPE_OwnerSEquity.equals(accountType));
	}    //	isPassiva

	@Override
	public String toString()
	{
		return getValue() + " - " + getName();
	}

	/**
	 * Extended String Representation
	 *
	 * @return info
	 */
	public String toStringX()
	{
		return "MElementValue[" + get_ID() + "," + getValue() + " - " + getName() + "]";
	}

	@Override
	protected boolean beforeSave(boolean newRecord)
	{
		//
		// Transform to summary level account
		if (!newRecord && isSummary() && is_ValueChanged(COLUMNNAME_IsSummary))
		{
			//
			// Check if we have accounting facts
			boolean match = new Query(getCtx(), I_Fact_Acct.Table_Name, I_Fact_Acct.COLUMNNAME_Account_ID + "=?", ITrx.TRXNAME_ThreadInherited)
					.setParameters(getC_ElementValue_ID())
					.anyMatch();
			if (match)
			{
				throw new AdempiereException("@AlreadyPostedTo@");
			}

			//
			// Check Valid Combinations - teo_sarca FR [ 1883533 ]
			String whereClause = MAccount.COLUMNNAME_Account_ID + "=?";
			POResultSet<MAccount> rs = new Query(getCtx(), MAccount.Table_Name, whereClause, ITrx.TRXNAME_ThreadInherited)
					.setParameters(getC_ElementValue_ID())
					.scroll();
			try
			{
				while (rs.hasNext())
				{
					rs.next().deleteEx(true);
				}
			}
			finally
			{
				DB.close(rs);
			}
		}
		return true;
	}    //	beforeSave
}    //	MElementValue
