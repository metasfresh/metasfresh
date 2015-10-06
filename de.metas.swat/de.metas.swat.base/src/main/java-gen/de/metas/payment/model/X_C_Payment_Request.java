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
package de.metas.payment.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for C_Payment_Request
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Payment_Request extends org.compiere.model.PO implements I_C_Payment_Request, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1048361384L;

    /** Standard Constructor */
    public X_C_Payment_Request (Properties ctx, int C_Payment_Request_ID, String trxName)
    {
      super (ctx, C_Payment_Request_ID, trxName);
      /** if (C_Payment_Request_ID == 0)
        {
			setAmount (Env.ZERO);
			setC_Payment_Request_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Payment_Request (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

    @Override
    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_C_Payment_Request[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Betrag.
		@param Amount 
		Betrag in einer definierten Währung
	  */
	@Override
	public void setAmount (java.math.BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Betrag.
		@return Betrag in einer definierten Währung
	  */
	@Override
	public java.math.BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class);
	}

	@Override
	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class, C_BP_BankAccount);
	}

	/** Set Bankverbindung.
		@param C_BP_BankAccount_ID 
		Bankverbindung des Geschäftspartners
	  */
	@Override
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
	}

	/** Get Bankverbindung.
		@return Bankverbindung des Geschäftspartners
	  */
	@Override
	public int getC_BP_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Zahlungsaufforderung.
		@param C_Payment_Request_ID Zahlungsaufforderung	  */
	@Override
	public void setC_Payment_Request_ID (int C_Payment_Request_ID)
	{
		if (C_Payment_Request_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Payment_Request_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Payment_Request_ID, Integer.valueOf(C_Payment_Request_ID));
	}

	/** Get Zahlungsaufforderung.
		@return Zahlungsaufforderung	  */
	@Override
	public int getC_Payment_Request_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_Request_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Referenz.
		@param Reference 
		Bezug für diesen Eintrag
	  */
	@Override
	public void setReference (java.lang.String Reference)
	{
		set_Value (COLUMNNAME_Reference, Reference);
	}

	/** Get Referenz.
		@return Bezug für diesen Eintrag
	  */
	@Override
	public java.lang.String getReference () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Reference);
	}
}