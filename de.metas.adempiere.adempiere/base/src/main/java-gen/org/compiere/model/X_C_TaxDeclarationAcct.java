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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for C_TaxDeclarationAcct
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_TaxDeclarationAcct extends org.compiere.model.PO implements I_C_TaxDeclarationAcct, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1942430133L;

    /** Standard Constructor */
    public X_C_TaxDeclarationAcct (Properties ctx, int C_TaxDeclarationAcct_ID, String trxName)
    {
      super (ctx, C_TaxDeclarationAcct_ID, trxName);
      /** if (C_TaxDeclarationAcct_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setC_TaxDeclaration_ID (0);
			setC_TaxDeclarationAcct_ID (0);
			setFact_Acct_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_TaxDeclarationAcct (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_ElementValue getAccount() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Account_ID, org.compiere.model.I_C_ElementValue.class);
	}

	@Override
	public void setAccount(org.compiere.model.I_C_ElementValue Account)
	{
		set_ValueFromPO(COLUMNNAME_Account_ID, org.compiere.model.I_C_ElementValue.class, Account);
	}

	/** Set Konto.
		@param Account_ID 
		Account used
	  */
	@Override
	public void setAccount_ID (int Account_ID)
	{
		throw new IllegalArgumentException ("Account_ID is virtual column");	}

	/** Get Konto.
		@return Account used
	  */
	@Override
	public int getAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Account_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Haben.
		@param AmtAcctCr 
		Ausgewiesener Forderungsbetrag
	  */
	@Override
	public void setAmtAcctCr (java.math.BigDecimal AmtAcctCr)
	{
		throw new IllegalArgumentException ("AmtAcctCr is virtual column");	}

	/** Get Haben.
		@return Ausgewiesener Forderungsbetrag
	  */
	@Override
	public java.math.BigDecimal getAmtAcctCr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtAcctCr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Soll.
		@param AmtAcctDr 
		Ausgewiesener Verbindlichkeitsbetrag
	  */
	@Override
	public void setAmtAcctDr (java.math.BigDecimal AmtAcctDr)
	{
		throw new IllegalArgumentException ("AmtAcctDr is virtual column");	}

	/** Get Soll.
		@return Ausgewiesener Verbindlichkeitsbetrag
	  */
	@Override
	public java.math.BigDecimal getAmtAcctDr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtAcctDr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Ausgangsforderung.
		@param AmtSourceCr 
		Source Credit Amount
	  */
	@Override
	public void setAmtSourceCr (java.math.BigDecimal AmtSourceCr)
	{
		throw new IllegalArgumentException ("AmtSourceCr is virtual column");	}

	/** Get Ausgangsforderung.
		@return Source Credit Amount
	  */
	@Override
	public java.math.BigDecimal getAmtSourceCr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSourceCr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Ausgangsverbindlichkeit.
		@param AmtSourceDr 
		Source Debit Amount
	  */
	@Override
	public void setAmtSourceDr (java.math.BigDecimal AmtSourceDr)
	{
		throw new IllegalArgumentException ("AmtSourceDr is virtual column");	}

	/** Get Ausgangsverbindlichkeit.
		@return Source Debit Amount
	  */
	@Override
	public java.math.BigDecimal getAmtSourceDr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtSourceDr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_AcctSchema getC_AcctSchema() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class);
	}

	@Override
	public void setC_AcctSchema(org.compiere.model.I_C_AcctSchema C_AcctSchema)
	{
		set_ValueFromPO(COLUMNNAME_C_AcctSchema_ID, org.compiere.model.I_C_AcctSchema.class, C_AcctSchema);
	}

	/** Set Buchführungs-Schema.
		@param C_AcctSchema_ID 
		Rules for accounting
	  */
	@Override
	public void setC_AcctSchema_ID (int C_AcctSchema_ID)
	{
		if (C_AcctSchema_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AcctSchema_ID, Integer.valueOf(C_AcctSchema_ID));
	}

	/** Get Buchführungs-Schema.
		@return Rules for accounting
	  */
	@Override
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		throw new IllegalArgumentException ("C_BPartner_ID is virtual column");	}

	/** Get Geschäftspartner.
		@return Identifies a Business Partner
	  */
	@Override
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class);
	}

	@Override
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency)
	{
		set_ValueFromPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class, C_Currency);
	}

	/** Set Währung.
		@param C_Currency_ID 
		The Currency for this record
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		throw new IllegalArgumentException ("C_Currency_ID is virtual column");	}

	/** Get Währung.
		@return The Currency for this record
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_Tax getC_Tax() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Tax_ID, org.compiere.model.I_C_Tax.class);
	}

	@Override
	public void setC_Tax(org.compiere.model.I_C_Tax C_Tax)
	{
		set_ValueFromPO(COLUMNNAME_C_Tax_ID, org.compiere.model.I_C_Tax.class, C_Tax);
	}

	/** Set Steuer.
		@param C_Tax_ID 
		Tax identifier
	  */
	@Override
	public void setC_Tax_ID (int C_Tax_ID)
	{
		throw new IllegalArgumentException ("C_Tax_ID is virtual column");	}

	/** Get Steuer.
		@return Tax identifier
	  */
	@Override
	public int getC_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_TaxDeclaration getC_TaxDeclaration() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_TaxDeclaration_ID, org.compiere.model.I_C_TaxDeclaration.class);
	}

	@Override
	public void setC_TaxDeclaration(org.compiere.model.I_C_TaxDeclaration C_TaxDeclaration)
	{
		set_ValueFromPO(COLUMNNAME_C_TaxDeclaration_ID, org.compiere.model.I_C_TaxDeclaration.class, C_TaxDeclaration);
	}

	/** Set Steuererklärung.
		@param C_TaxDeclaration_ID 
		Define the declaration to the tax authorities
	  */
	@Override
	public void setC_TaxDeclaration_ID (int C_TaxDeclaration_ID)
	{
		if (C_TaxDeclaration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_TaxDeclaration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_TaxDeclaration_ID, Integer.valueOf(C_TaxDeclaration_ID));
	}

	/** Get Steuererklärung.
		@return Define the declaration to the tax authorities
	  */
	@Override
	public int getC_TaxDeclaration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxDeclaration_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Tax Declaration Accounting.
		@param C_TaxDeclarationAcct_ID 
		Tax Accounting Reconciliation 
	  */
	@Override
	public void setC_TaxDeclarationAcct_ID (int C_TaxDeclarationAcct_ID)
	{
		if (C_TaxDeclarationAcct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_TaxDeclarationAcct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_TaxDeclarationAcct_ID, Integer.valueOf(C_TaxDeclarationAcct_ID));
	}

	/** Get Tax Declaration Accounting.
		@return Tax Accounting Reconciliation 
	  */
	@Override
	public int getC_TaxDeclarationAcct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxDeclarationAcct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_TaxDeclarationLine getC_TaxDeclarationLine() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_TaxDeclarationLine_ID, org.compiere.model.I_C_TaxDeclarationLine.class);
	}

	@Override
	public void setC_TaxDeclarationLine(org.compiere.model.I_C_TaxDeclarationLine C_TaxDeclarationLine)
	{
		set_ValueFromPO(COLUMNNAME_C_TaxDeclarationLine_ID, org.compiere.model.I_C_TaxDeclarationLine.class, C_TaxDeclarationLine);
	}

	/** Set Tax Declaration Line.
		@param C_TaxDeclarationLine_ID 
		Tax Declaration Document Information
	  */
	@Override
	public void setC_TaxDeclarationLine_ID (int C_TaxDeclarationLine_ID)
	{
		if (C_TaxDeclarationLine_ID < 1) 
			set_Value (COLUMNNAME_C_TaxDeclarationLine_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxDeclarationLine_ID, Integer.valueOf(C_TaxDeclarationLine_ID));
	}

	/** Get Tax Declaration Line.
		@return Tax Declaration Document Information
	  */
	@Override
	public int getC_TaxDeclarationLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxDeclarationLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Buchungsdatum.
		@param DateAcct 
		Accounting Date
	  */
	@Override
	public void setDateAcct (java.sql.Timestamp DateAcct)
	{
		throw new IllegalArgumentException ("DateAcct is virtual column");	}

	/** Get Buchungsdatum.
		@return Accounting Date
	  */
	@Override
	public java.sql.Timestamp getDateAcct () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateAcct);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	@Override
	public org.compiere.model.I_Fact_Acct getFact_Acct() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Fact_Acct_ID, org.compiere.model.I_Fact_Acct.class);
	}

	@Override
	public void setFact_Acct(org.compiere.model.I_Fact_Acct Fact_Acct)
	{
		set_ValueFromPO(COLUMNNAME_Fact_Acct_ID, org.compiere.model.I_Fact_Acct.class, Fact_Acct);
	}

	/** Set Accounting Fact.
		@param Fact_Acct_ID Accounting Fact	  */
	@Override
	public void setFact_Acct_ID (int Fact_Acct_ID)
	{
		if (Fact_Acct_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Fact_Acct_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Fact_Acct_ID, Integer.valueOf(Fact_Acct_ID));
	}

	/** Get Accounting Fact.
		@return Accounting Fact	  */
	@Override
	public int getFact_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Fact_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Zeile Nr..
		@param Line 
		Unique line for this document
	  */
	@Override
	public void setLine (int Line)
	{
		set_Value (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Zeile Nr..
		@return Unique line for this document
	  */
	@Override
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}