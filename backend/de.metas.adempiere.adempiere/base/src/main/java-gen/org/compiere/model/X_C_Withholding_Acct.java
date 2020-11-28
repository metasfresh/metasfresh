/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Withholding_Acct
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_Withholding_Acct extends PO implements I_C_Withholding_Acct, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_C_Withholding_Acct (Properties ctx, int C_Withholding_Acct_ID, String trxName)
    {
      super (ctx, C_Withholding_Acct_ID, trxName);
      /** if (C_Withholding_Acct_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setC_Withholding_ID (0);
			setWithholding_Acct (0);
        } */
    }

    /** Load Constructor */
    public X_C_Withholding_Acct (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_Withholding_Acct[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_AcctSchema getC_AcctSchema() throws RuntimeException
    {
		return (I_C_AcctSchema)MTable.get(getCtx(), I_C_AcctSchema.Table_Name)
			.getPO(getC_AcctSchema_ID(), get_TrxName());	}

	/** Set Accounting Schema.
		@param C_AcctSchema_ID 
		Rules for accounting
	  */
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Accounting Schema.
		@return Rules for accounting
	  */
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_Withholding getC_Withholding() throws RuntimeException
    {
		return (I_C_Withholding)MTable.get(getCtx(), I_C_Withholding.Table_Name)
			.getPO(getC_Withholding_ID(), get_TrxName());	}

	/** Set Withholding.
		@param C_Withholding_ID 
		Withholding type defined
	  */
	public void setC_Withholding_ID (int C_Withholding_ID)
	{
		if (C_Withholding_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Withholding_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Withholding_ID, Integer.valueOf(C_Withholding_ID));
	}

	/** Get Withholding.
		@return Withholding type defined
	  */
	public int getC_Withholding_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Withholding_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_ValidCombination getWithholding_A() throws RuntimeException
    {
		return (I_C_ValidCombination)MTable.get(getCtx(), I_C_ValidCombination.Table_Name)
			.getPO(getWithholding_Acct(), get_TrxName());	}

	/** Set Withholding.
		@param Withholding_Acct 
		Account for Withholdings
	  */
	public void setWithholding_Acct (int Withholding_Acct)
	{
		set_Value (COLUMNNAME_Withholding_Acct, Integer.valueOf(Withholding_Acct));
	}

	/** Get Withholding.
		@return Account for Withholdings
	  */
	public int getWithholding_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Withholding_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}