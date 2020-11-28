/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
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
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

/*
 * #%L
 * de.metas.banking.base
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

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_DirectDebit
 *  @author Adempiere (generated) 
 *  @version Release 3.5.2a - $Id$ */
public class X_C_DirectDebit extends PO implements I_C_DirectDebit, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    /** Standard Constructor */
    public X_C_DirectDebit (Properties ctx, int C_DirectDebit_ID, String trxName)
    {
      super (ctx, C_DirectDebit_ID, trxName);
      /** if (C_DirectDebit_ID == 0)
        {
			setC_DirectDebit_ID (0);
			setIsRemittance (false);
        } */
    }

    /** Load Constructor */
    public X_C_DirectDebit (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_DirectDebit[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Amount.
		@param Amount 
		Amount in a defined currency
	  */
	public void setAmount (String Amount)
	{
		throw new IllegalArgumentException ("Amount is virtual column");	}

	/** Get Amount.
		@return Amount in a defined currency
	  */
	public String getAmount () 
	{
		return (String)get_Value(COLUMNNAME_Amount);
	}

	/** Set Bank statement line.
		@param C_BankStatementLine_ID 
		Line on a statement from this Bank
	  */
	public void setC_BankStatementLine_ID (int C_BankStatementLine_ID)
	{
		if (C_BankStatementLine_ID < 1) 
			set_Value (COLUMNNAME_C_BankStatementLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_BankStatementLine_ID, Integer.valueOf(C_BankStatementLine_ID));
	}

	/** Get Bank statement line.
		@return Line on a statement from this Bank
	  */
	public int getC_BankStatementLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankStatementLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_DirectDebit_ID.
		@param C_DirectDebit_ID C_DirectDebit_ID	  */
	public void setC_DirectDebit_ID (int C_DirectDebit_ID)
	{
		if (C_DirectDebit_ID < 1)
			 throw new IllegalArgumentException ("C_DirectDebit_ID is mandatory.");
		set_ValueNoCheck (COLUMNNAME_C_DirectDebit_ID, Integer.valueOf(C_DirectDebit_ID));
	}

	/** Get C_DirectDebit_ID.
		@return C_DirectDebit_ID	  */
	public int getC_DirectDebit_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DirectDebit_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Dtafile.
		@param Dtafile 
		Copy of the *.dta stored as plain text
	  */
	public void setDtafile (String Dtafile)
	{
		set_Value (COLUMNNAME_Dtafile, Dtafile);
	}

	/** Get Dtafile.
		@return Copy of the *.dta stored as plain text
	  */
	public String getDtafile () 
	{
		return (String)get_Value(COLUMNNAME_Dtafile);
	}

	/** Set IsRemittance.
		@param IsRemittance IsRemittance	  */
	public void setIsRemittance (boolean IsRemittance)
	{
		set_Value (COLUMNNAME_IsRemittance, Boolean.valueOf(IsRemittance));
	}

	/** Get IsRemittance.
		@return IsRemittance	  */
	public boolean isRemittance () 
	{
		Object oo = get_Value(COLUMNNAME_IsRemittance);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}