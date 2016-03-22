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
package de.metas.banking.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_RecurrentPaymentHistory
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_RecurrentPaymentHistory extends org.compiere.model.PO implements I_C_RecurrentPaymentHistory, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2085439366L;

    /** Standard Constructor */
    public X_C_RecurrentPaymentHistory (Properties ctx, int C_RecurrentPaymentHistory_ID, String trxName)
    {
      super (ctx, C_RecurrentPaymentHistory_ID, trxName);
      /** if (C_RecurrentPaymentHistory_ID == 0)
        {
			setC_Invoice_ID (0);
			setC_RecurrentPaymentHistory_ID (0);
			setC_RecurrentPaymentLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_RecurrentPaymentHistory (Properties ctx, ResultSet rs, String trxName)
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
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    @Override
    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_C_RecurrentPaymentHistory[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_C_Invoice getC_Invoice() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class);
	}

	@Override
	public void setC_Invoice(org.compiere.model.I_C_Invoice C_Invoice)
	{
		set_ValueFromPO(COLUMNNAME_C_Invoice_ID, org.compiere.model.I_C_Invoice.class, C_Invoice);
	}

	/** Set Rechnung.
		@param C_Invoice_ID 
		Invoice Identifier
	  */
	@Override
	public void setC_Invoice_ID (int C_Invoice_ID)
	{
		if (C_Invoice_ID < 1) 
			set_Value (COLUMNNAME_C_Invoice_ID, null);
		else 
			set_Value (COLUMNNAME_C_Invoice_ID, Integer.valueOf(C_Invoice_ID));
	}

	/** Get Rechnung.
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

	/** Set Recurrent Payment History.
		@param C_RecurrentPaymentHistory_ID Recurrent Payment History	  */
	@Override
	public void setC_RecurrentPaymentHistory_ID (int C_RecurrentPaymentHistory_ID)
	{
		if (C_RecurrentPaymentHistory_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RecurrentPaymentHistory_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RecurrentPaymentHistory_ID, Integer.valueOf(C_RecurrentPaymentHistory_ID));
	}

	/** Get Recurrent Payment History.
		@return Recurrent Payment History	  */
	@Override
	public int getC_RecurrentPaymentHistory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RecurrentPaymentHistory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.banking.model.I_C_RecurrentPaymentLine getC_RecurrentPaymentLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_RecurrentPaymentLine_ID, de.metas.banking.model.I_C_RecurrentPaymentLine.class);
	}

	@Override
	public void setC_RecurrentPaymentLine(de.metas.banking.model.I_C_RecurrentPaymentLine C_RecurrentPaymentLine)
	{
		set_ValueFromPO(COLUMNNAME_C_RecurrentPaymentLine_ID, de.metas.banking.model.I_C_RecurrentPaymentLine.class, C_RecurrentPaymentLine);
	}

	/** Set Recurrent Payment Line.
		@param C_RecurrentPaymentLine_ID Recurrent Payment Line	  */
	@Override
	public void setC_RecurrentPaymentLine_ID (int C_RecurrentPaymentLine_ID)
	{
		if (C_RecurrentPaymentLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_RecurrentPaymentLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_RecurrentPaymentLine_ID, Integer.valueOf(C_RecurrentPaymentLine_ID));
	}

	/** Get Recurrent Payment Line.
		@return Recurrent Payment Line	  */
	@Override
	public int getC_RecurrentPaymentLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_RecurrentPaymentLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public org.compiere.util.KeyNamePair getKeyNamePair() 
    {
        return new org.compiere.util.KeyNamePair(get_ID(), String.valueOf(getC_RecurrentPaymentLine_ID()));
    }
}