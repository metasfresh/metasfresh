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

/** Generated Model for C_DirectDebitLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.2a - $Id$ */
public class X_C_DirectDebitLine extends PO implements I_C_DirectDebitLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    /** Standard Constructor */
    public X_C_DirectDebitLine (Properties ctx, int C_DirectDebitLine_ID, String trxName)
    {
      super (ctx, C_DirectDebitLine_ID, trxName);
      /** if (C_DirectDebitLine_ID == 0)
        {
			setC_DirectDebit_ID (0);
			setC_DirectDebitLine_ID (0);
			setC_Invoice_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_DirectDebitLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    @Override
	protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    @Override
	protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    @Override
	public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_DirectDebitLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public I_C_DirectDebit getC_DirectDebit() throws Exception 
    {
		return get_ValueAsPO(COLUMNNAME_C_DirectDebit_ID, org.compiere.model.I_C_DirectDebit.class);
    }

	/** Set C_DirectDebit_ID.
		@param C_DirectDebit_ID C_DirectDebit_ID	  */
	@Override
	public void setC_DirectDebit_ID (int C_DirectDebit_ID)
	{
		if (C_DirectDebit_ID < 1)
			 throw new IllegalArgumentException ("C_DirectDebit_ID is mandatory.");
		set_Value (COLUMNNAME_C_DirectDebit_ID, Integer.valueOf(C_DirectDebit_ID));
	}

	/** Get C_DirectDebit_ID.
		@return C_DirectDebit_ID	  */
	@Override
	public int getC_DirectDebit_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DirectDebit_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set C_DirectDebitLine_ID.
		@param C_DirectDebitLine_ID C_DirectDebitLine_ID	  */
	@Override
	public void setC_DirectDebitLine_ID (int C_DirectDebitLine_ID)
	{
		if (C_DirectDebitLine_ID < 1)
			 throw new IllegalArgumentException ("C_DirectDebitLine_ID is mandatory.");
		set_ValueNoCheck (COLUMNNAME_C_DirectDebitLine_ID, Integer.valueOf(C_DirectDebitLine_ID));
	}

	/** Get C_DirectDebitLine_ID.
		@return C_DirectDebitLine_ID	  */
	@Override
	public int getC_DirectDebitLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DirectDebitLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public I_C_Invoice getC_Invoice() throws Exception 
    {
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
    }

	/** Set Invoice.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1)
			 throw new IllegalArgumentException ("C_Invoice_ID is mandatory.");
		set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Invoice.
		@return Invoice Identifier
	  */
	@Override
	public int getC_Invoice_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Invoice_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}