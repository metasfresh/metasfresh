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

/** Generated Model for GL_JournalLine
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_GL_JournalLine extends org.compiere.model.PO implements I_GL_JournalLine, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 305106884L;

    /** Standard Constructor */
    public X_GL_JournalLine (Properties ctx, int GL_JournalLine_ID, String trxName)
    {
      super (ctx, GL_JournalLine_ID, trxName);
      /** if (GL_JournalLine_ID == 0)
        {
			setAmtAcctCr (Env.ZERO);
			setAmtAcctDr (Env.ZERO);
			setAmtSourceCr (Env.ZERO);
			setAmtSourceDr (Env.ZERO);
			setC_ConversionType_ID (0);
			setC_Currency_ID (0);
// @C_Currency_ID@
			setCR_AutoTaxAccount (false);
// N
			setCurrencyRate (Env.ZERO);
// @CurrencyRate@;1
			setDateAcct (new Timestamp( System.currentTimeMillis() ));
// @DateAcct@
			setDR_AutoTaxAccount (false);
// N
			setGL_Journal_ID (0);
			setGL_JournalLine_Group (0);
			setGL_JournalLine_ID (0);
			setIsAllowAccountCR (true);
// Y
			setIsAllowAccountDR (true);
// Y
			setIsGenerated (false);
			setIsSplitAcctTrx (false);
// N
			setLine (0);
// @SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM GL_JournalLine WHERE GL_Journal_ID=@GL_Journal_ID@
			setProcessed (false);
			setType (null);
// N
        } */
    }

    /** Load Constructor */
    public X_GL_JournalLine (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_A_Asset_Group getA_Asset_Group() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_A_Asset_Group_ID, org.compiere.model.I_A_Asset_Group.class);
	}

	@Override
	public void setA_Asset_Group(org.compiere.model.I_A_Asset_Group A_Asset_Group)
	{
		set_ValueFromPO(COLUMNNAME_A_Asset_Group_ID, org.compiere.model.I_A_Asset_Group.class, A_Asset_Group);
	}

	/** Set Asset-Gruppe.
		@param A_Asset_Group_ID 
		Group of Assets
	  */
	@Override
	public void setA_Asset_Group_ID (int A_Asset_Group_ID)
	{
		if (A_Asset_Group_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_Group_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_Group_ID, Integer.valueOf(A_Asset_Group_ID));
	}

	/** Get Asset-Gruppe.
		@return Group of Assets
	  */
	@Override
	public int getA_Asset_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_A_Asset getA_Asset() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_A_Asset_ID, org.compiere.model.I_A_Asset.class);
	}

	@Override
	public void setA_Asset(org.compiere.model.I_A_Asset A_Asset)
	{
		set_ValueFromPO(COLUMNNAME_A_Asset_ID, org.compiere.model.I_A_Asset.class, A_Asset);
	}

	/** Set Asset.
		@param A_Asset_ID 
		Asset used internally or by customers
	  */
	@Override
	public void setA_Asset_ID (int A_Asset_ID)
	{
		if (A_Asset_ID < 1) 
			set_Value (COLUMNNAME_A_Asset_ID, null);
		else 
			set_Value (COLUMNNAME_A_Asset_ID, Integer.valueOf(A_Asset_ID));
	}

	/** Get Asset.
		@return Asset used internally or by customers
	  */
	@Override
	public int getA_Asset_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_A_Asset_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getAccount_CR() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Account_CR_ID, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setAccount_CR(org.compiere.model.I_C_ValidCombination Account_CR)
	{
		set_ValueFromPO(COLUMNNAME_Account_CR_ID, org.compiere.model.I_C_ValidCombination.class, Account_CR);
	}

	/** Set Kombination-Haben.
		@param Account_CR_ID Kombination-Haben	  */
	@Override
	public void setAccount_CR_ID (int Account_CR_ID)
	{
		if (Account_CR_ID < 1) 
			set_Value (COLUMNNAME_Account_CR_ID, null);
		else 
			set_Value (COLUMNNAME_Account_CR_ID, Integer.valueOf(Account_CR_ID));
	}

	/** Get Kombination-Haben.
		@return Kombination-Haben	  */
	@Override
	public int getAccount_CR_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Account_CR_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getAccount_DR() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Account_DR_ID, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setAccount_DR(org.compiere.model.I_C_ValidCombination Account_DR)
	{
		set_ValueFromPO(COLUMNNAME_Account_DR_ID, org.compiere.model.I_C_ValidCombination.class, Account_DR);
	}

	/** Set Kombination-Soll.
		@param Account_DR_ID Kombination-Soll	  */
	@Override
	public void setAccount_DR_ID (int Account_DR_ID)
	{
		if (Account_DR_ID < 1) 
			set_Value (COLUMNNAME_Account_DR_ID, null);
		else 
			set_Value (COLUMNNAME_Account_DR_ID, Integer.valueOf(Account_DR_ID));
	}

	/** Get Kombination-Soll.
		@return Kombination-Soll	  */
	@Override
	public int getAccount_DR_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Account_DR_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Asset Related?.
		@param A_CreateAsset Asset Related?	  */
	@Override
	public void setA_CreateAsset (boolean A_CreateAsset)
	{
		set_Value (COLUMNNAME_A_CreateAsset, Boolean.valueOf(A_CreateAsset));
	}

	/** Get Asset Related?.
		@return Asset Related?	  */
	@Override
	public boolean isA_CreateAsset () 
	{
		Object oo = get_Value(COLUMNNAME_A_CreateAsset);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Haben.
		@param AmtAcctCr 
		Ausgewiesener Forderungsbetrag
	  */
	@Override
	public void setAmtAcctCr (java.math.BigDecimal AmtAcctCr)
	{
		set_ValueNoCheck (COLUMNNAME_AmtAcctCr, AmtAcctCr);
	}

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
		set_ValueNoCheck (COLUMNNAME_AmtAcctDr, AmtAcctDr);
	}

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

	/** Set Betrag Haben (Gruppe).
		@param AmtAcctGroupCr Betrag Haben (Gruppe)	  */
	@Override
	public void setAmtAcctGroupCr (java.math.BigDecimal AmtAcctGroupCr)
	{
		throw new IllegalArgumentException ("AmtAcctGroupCr is virtual column");	}

	/** Get Betrag Haben (Gruppe).
		@return Betrag Haben (Gruppe)	  */
	@Override
	public java.math.BigDecimal getAmtAcctGroupCr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtAcctGroupCr);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Betrag Soll (Gruppe).
		@param AmtAcctGroupDr Betrag Soll (Gruppe)	  */
	@Override
	public void setAmtAcctGroupDr (java.math.BigDecimal AmtAcctGroupDr)
	{
		throw new IllegalArgumentException ("AmtAcctGroupDr is virtual column");	}

	/** Get Betrag Soll (Gruppe).
		@return Betrag Soll (Gruppe)	  */
	@Override
	public java.math.BigDecimal getAmtAcctGroupDr () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtAcctGroupDr);
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
		set_Value (COLUMNNAME_AmtSourceCr, AmtSourceCr);
	}

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
		set_Value (COLUMNNAME_AmtSourceDr, AmtSourceDr);
	}

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

	/** Set A_Processed.
		@param A_Processed A_Processed	  */
	@Override
	public void setA_Processed (boolean A_Processed)
	{
		set_Value (COLUMNNAME_A_Processed, Boolean.valueOf(A_Processed));
	}

	/** Get A_Processed.
		@return A_Processed	  */
	@Override
	public boolean isA_Processed () 
	{
		Object oo = get_Value(COLUMNNAME_A_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_C_Activity getC_Activity() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Activity_ID, org.compiere.model.I_C_Activity.class);
	}

	@Override
	public void setC_Activity(org.compiere.model.I_C_Activity C_Activity)
	{
		set_ValueFromPO(COLUMNNAME_C_Activity_ID, org.compiere.model.I_C_Activity.class, C_Activity);
	}

	/** Set Kostenstelle.
		@param C_Activity_ID 
		Kostenstelle
	  */
	@Override
	public void setC_Activity_ID (int C_Activity_ID)
	{
		if (C_Activity_ID < 1) 
			set_Value (COLUMNNAME_C_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_C_Activity_ID, Integer.valueOf(C_Activity_ID));
	}

	/** Get Kostenstelle.
		@return Kostenstelle
	  */
	@Override
	public int getC_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ConversionType getC_ConversionType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_ConversionType_ID, org.compiere.model.I_C_ConversionType.class);
	}

	@Override
	public void setC_ConversionType(org.compiere.model.I_C_ConversionType C_ConversionType)
	{
		set_ValueFromPO(COLUMNNAME_C_ConversionType_ID, org.compiere.model.I_C_ConversionType.class, C_ConversionType);
	}

	/** Set Kursart.
		@param C_ConversionType_ID 
		Currency Conversion Rate Type
	  */
	@Override
	public void setC_ConversionType_ID (int C_ConversionType_ID)
	{
		if (C_ConversionType_ID < 1) 
			set_Value (COLUMNNAME_C_ConversionType_ID, null);
		else 
			set_Value (COLUMNNAME_C_ConversionType_ID, Integer.valueOf(C_ConversionType_ID));
	}

	/** Get Kursart.
		@return Currency Conversion Rate Type
	  */
	@Override
	public int getC_ConversionType_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_ConversionType_ID);
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
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

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

	/** Set Automatic tax account (Credit).
		@param CR_AutoTaxAccount 
		If an account is flagged as automatic tax account, the system will allow and help user to do manual tax bookings in GL journal
	  */
	@Override
	public void setCR_AutoTaxAccount (boolean CR_AutoTaxAccount)
	{
		set_Value (COLUMNNAME_CR_AutoTaxAccount, Boolean.valueOf(CR_AutoTaxAccount));
	}

	/** Get Automatic tax account (Credit).
		@return If an account is flagged as automatic tax account, the system will allow and help user to do manual tax bookings in GL journal
	  */
	@Override
	public boolean isCR_AutoTaxAccount () 
	{
		Object oo = get_Value(COLUMNNAME_CR_AutoTaxAccount);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCR_Tax_Acct() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_CR_Tax_Acct_ID, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCR_Tax_Acct(org.compiere.model.I_C_ValidCombination CR_Tax_Acct)
	{
		set_ValueFromPO(COLUMNNAME_CR_Tax_Acct_ID, org.compiere.model.I_C_ValidCombination.class, CR_Tax_Acct);
	}

	/** Set Tax account (credit).
		@param CR_Tax_Acct_ID Tax account (credit)	  */
	@Override
	public void setCR_Tax_Acct_ID (int CR_Tax_Acct_ID)
	{
		if (CR_Tax_Acct_ID < 1) 
			set_Value (COLUMNNAME_CR_Tax_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_CR_Tax_Acct_ID, Integer.valueOf(CR_Tax_Acct_ID));
	}

	/** Get Tax account (credit).
		@return Tax account (credit)	  */
	@Override
	public int getCR_Tax_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CR_Tax_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Steuerbetrag (Haben).
		@param CR_TaxAmt 
		Steuerbetrag für diesen Beleg
	  */
	@Override
	public void setCR_TaxAmt (java.math.BigDecimal CR_TaxAmt)
	{
		set_Value (COLUMNNAME_CR_TaxAmt, CR_TaxAmt);
	}

	/** Get Steuerbetrag (Haben).
		@return Steuerbetrag für diesen Beleg
	  */
	@Override
	public java.math.BigDecimal getCR_TaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CR_TaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Bezugswert (Haben).
		@param CR_TaxBaseAmt 
		Bezugswert für die Berechnung der Steuer
	  */
	@Override
	public void setCR_TaxBaseAmt (java.math.BigDecimal CR_TaxBaseAmt)
	{
		set_Value (COLUMNNAME_CR_TaxBaseAmt, CR_TaxBaseAmt);
	}

	/** Get Bezugswert (Haben).
		@return Bezugswert für die Berechnung der Steuer
	  */
	@Override
	public java.math.BigDecimal getCR_TaxBaseAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CR_TaxBaseAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_Tax getCR_Tax() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_CR_Tax_ID, org.compiere.model.I_C_Tax.class);
	}

	@Override
	public void setCR_Tax(org.compiere.model.I_C_Tax CR_Tax)
	{
		set_ValueFromPO(COLUMNNAME_CR_Tax_ID, org.compiere.model.I_C_Tax.class, CR_Tax);
	}

	/** Set Steuer (Haben).
		@param CR_Tax_ID 
		Steuerart
	  */
	@Override
	public void setCR_Tax_ID (int CR_Tax_ID)
	{
		if (CR_Tax_ID < 1) 
			set_Value (COLUMNNAME_CR_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_CR_Tax_ID, Integer.valueOf(CR_Tax_ID));
	}

	/** Get Steuer (Haben).
		@return Steuerart
	  */
	@Override
	public int getCR_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CR_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Summe (Haben).
		@param CR_TaxTotalAmt Summe (Haben)	  */
	@Override
	public void setCR_TaxTotalAmt (java.math.BigDecimal CR_TaxTotalAmt)
	{
		set_Value (COLUMNNAME_CR_TaxTotalAmt, CR_TaxTotalAmt);
	}

	/** Get Summe (Haben).
		@return Summe (Haben)	  */
	@Override
	public java.math.BigDecimal getCR_TaxTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CR_TaxTotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Unit of Measure
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Unit of Measure
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Wechselkurs.
		@param CurrencyRate 
		Currency Conversion Rate
	  */
	@Override
	public void setCurrencyRate (java.math.BigDecimal CurrencyRate)
	{
		set_ValueNoCheck (COLUMNNAME_CurrencyRate, CurrencyRate);
	}

	/** Get Wechselkurs.
		@return Currency Conversion Rate
	  */
	@Override
	public java.math.BigDecimal getCurrencyRate () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CurrencyRate);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Buchungsdatum.
		@param DateAcct 
		Accounting Date
	  */
	@Override
	public void setDateAcct (java.sql.Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

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

	/** Set Automatic tax account (Debit).
		@param DR_AutoTaxAccount 
		If an account is flagged as automatic tax account, the system will allow and help user to do manual tax bookings in GL journal
	  */
	@Override
	public void setDR_AutoTaxAccount (boolean DR_AutoTaxAccount)
	{
		set_Value (COLUMNNAME_DR_AutoTaxAccount, Boolean.valueOf(DR_AutoTaxAccount));
	}

	/** Get Automatic tax account (Debit).
		@return If an account is flagged as automatic tax account, the system will allow and help user to do manual tax bookings in GL journal
	  */
	@Override
	public boolean isDR_AutoTaxAccount () 
	{
		Object oo = get_Value(COLUMNNAME_DR_AutoTaxAccount);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getDR_Tax_Acct() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DR_Tax_Acct_ID, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setDR_Tax_Acct(org.compiere.model.I_C_ValidCombination DR_Tax_Acct)
	{
		set_ValueFromPO(COLUMNNAME_DR_Tax_Acct_ID, org.compiere.model.I_C_ValidCombination.class, DR_Tax_Acct);
	}

	/** Set Tax account (debit).
		@param DR_Tax_Acct_ID Tax account (debit)	  */
	@Override
	public void setDR_Tax_Acct_ID (int DR_Tax_Acct_ID)
	{
		if (DR_Tax_Acct_ID < 1) 
			set_Value (COLUMNNAME_DR_Tax_Acct_ID, null);
		else 
			set_Value (COLUMNNAME_DR_Tax_Acct_ID, Integer.valueOf(DR_Tax_Acct_ID));
	}

	/** Get Tax account (debit).
		@return Tax account (debit)	  */
	@Override
	public int getDR_Tax_Acct_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DR_Tax_Acct_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Steuerbetrag (Soll).
		@param DR_TaxAmt 
		Steuerbetrag für diesen Beleg
	  */
	@Override
	public void setDR_TaxAmt (java.math.BigDecimal DR_TaxAmt)
	{
		set_Value (COLUMNNAME_DR_TaxAmt, DR_TaxAmt);
	}

	/** Get Steuerbetrag (Soll).
		@return Steuerbetrag für diesen Beleg
	  */
	@Override
	public java.math.BigDecimal getDR_TaxAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DR_TaxAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Bezugswert (Soll).
		@param DR_TaxBaseAmt 
		Bezugswert für die Berechnung der Steuer
	  */
	@Override
	public void setDR_TaxBaseAmt (java.math.BigDecimal DR_TaxBaseAmt)
	{
		set_Value (COLUMNNAME_DR_TaxBaseAmt, DR_TaxBaseAmt);
	}

	/** Get Bezugswert (Soll).
		@return Bezugswert für die Berechnung der Steuer
	  */
	@Override
	public java.math.BigDecimal getDR_TaxBaseAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DR_TaxBaseAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_C_Tax getDR_Tax() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_DR_Tax_ID, org.compiere.model.I_C_Tax.class);
	}

	@Override
	public void setDR_Tax(org.compiere.model.I_C_Tax DR_Tax)
	{
		set_ValueFromPO(COLUMNNAME_DR_Tax_ID, org.compiere.model.I_C_Tax.class, DR_Tax);
	}

	/** Set Steuer (Soll).
		@param DR_Tax_ID 
		Steuerart
	  */
	@Override
	public void setDR_Tax_ID (int DR_Tax_ID)
	{
		if (DR_Tax_ID < 1) 
			set_Value (COLUMNNAME_DR_Tax_ID, null);
		else 
			set_Value (COLUMNNAME_DR_Tax_ID, Integer.valueOf(DR_Tax_ID));
	}

	/** Get Steuer (Soll).
		@return Steuerart
	  */
	@Override
	public int getDR_Tax_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_DR_Tax_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Summe (Soll).
		@param DR_TaxTotalAmt Summe (Soll)	  */
	@Override
	public void setDR_TaxTotalAmt (java.math.BigDecimal DR_TaxTotalAmt)
	{
		set_Value (COLUMNNAME_DR_TaxTotalAmt, DR_TaxTotalAmt);
	}

	/** Get Summe (Soll).
		@return Summe (Soll)	  */
	@Override
	public java.math.BigDecimal getDR_TaxTotalAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DR_TaxTotalAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_GL_Journal getGL_Journal() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_GL_Journal_ID, org.compiere.model.I_GL_Journal.class);
	}

	@Override
	public void setGL_Journal(org.compiere.model.I_GL_Journal GL_Journal)
	{
		set_ValueFromPO(COLUMNNAME_GL_Journal_ID, org.compiere.model.I_GL_Journal.class, GL_Journal);
	}

	/** Set Journal.
		@param GL_Journal_ID 
		General Ledger Journal
	  */
	@Override
	public void setGL_Journal_ID (int GL_Journal_ID)
	{
		if (GL_Journal_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GL_Journal_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GL_Journal_ID, Integer.valueOf(GL_Journal_ID));
	}

	/** Get Journal.
		@return General Ledger Journal
	  */
	@Override
	public int getGL_Journal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_Journal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Journal-Position (Gruppe).
		@param GL_JournalLine_Group Journal-Position (Gruppe)	  */
	@Override
	public void setGL_JournalLine_Group (int GL_JournalLine_Group)
	{
		set_Value (COLUMNNAME_GL_JournalLine_Group, Integer.valueOf(GL_JournalLine_Group));
	}

	/** Get Journal-Position (Gruppe).
		@return Journal-Position (Gruppe)	  */
	@Override
	public int getGL_JournalLine_Group () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_JournalLine_Group);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Journal-Position.
		@param GL_JournalLine_ID 
		General Ledger Journal Line
	  */
	@Override
	public void setGL_JournalLine_ID (int GL_JournalLine_ID)
	{
		if (GL_JournalLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_GL_JournalLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_GL_JournalLine_ID, Integer.valueOf(GL_JournalLine_ID));
	}

	/** Get Journal-Position.
		@return General Ledger Journal Line
	  */
	@Override
	public int getGL_JournalLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GL_JournalLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Haben erlaubt.
		@param IsAllowAccountCR Haben erlaubt	  */
	@Override
	public void setIsAllowAccountCR (boolean IsAllowAccountCR)
	{
		set_Value (COLUMNNAME_IsAllowAccountCR, Boolean.valueOf(IsAllowAccountCR));
	}

	/** Get Haben erlaubt.
		@return Haben erlaubt	  */
	@Override
	public boolean isAllowAccountCR () 
	{
		Object oo = get_Value(COLUMNNAME_IsAllowAccountCR);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Soll erlaubt.
		@param IsAllowAccountDR Soll erlaubt	  */
	@Override
	public void setIsAllowAccountDR (boolean IsAllowAccountDR)
	{
		set_Value (COLUMNNAME_IsAllowAccountDR, Boolean.valueOf(IsAllowAccountDR));
	}

	/** Get Soll erlaubt.
		@return Soll erlaubt	  */
	@Override
	public boolean isAllowAccountDR () 
	{
		Object oo = get_Value(COLUMNNAME_IsAllowAccountDR);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Generated.
		@param IsGenerated 
		This Line is generated
	  */
	@Override
	public void setIsGenerated (boolean IsGenerated)
	{
		set_ValueNoCheck (COLUMNNAME_IsGenerated, Boolean.valueOf(IsGenerated));
	}

	/** Get Generated.
		@return This Line is generated
	  */
	@Override
	public boolean isGenerated () 
	{
		Object oo = get_Value(COLUMNNAME_IsGenerated);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Splitbuchung.
		@param IsSplitAcctTrx Splitbuchung	  */
	@Override
	public void setIsSplitAcctTrx (boolean IsSplitAcctTrx)
	{
		set_Value (COLUMNNAME_IsSplitAcctTrx, Boolean.valueOf(IsSplitAcctTrx));
	}

	/** Get Splitbuchung.
		@return Splitbuchung	  */
	@Override
	public boolean isSplitAcctTrx () 
	{
		Object oo = get_Value(COLUMNNAME_IsSplitAcctTrx);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Menge.
		@param Qty 
		Quantity
	  */
	@Override
	public void setQty (java.math.BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Quantity
	  */
	@Override
	public java.math.BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** 
	 * Type AD_Reference_ID=540534
	 * Reference name: GL_JournalLine_Type
	 */
	public static final int TYPE_AD_Reference_ID=540534;
	/** Normal = N */
	public static final String TYPE_Normal = "N";
	/** Tax = T */
	public static final String TYPE_Tax = "T";
	/** Set Art.
		@param Type 
		Type of Validation (SQL, Java Script, Java Language)
	  */
	@Override
	public void setType (java.lang.String Type)
	{

		set_Value (COLUMNNAME_Type, Type);
	}

	/** Get Art.
		@return Type of Validation (SQL, Java Script, Java Language)
	  */
	@Override
	public java.lang.String getType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Type);
	}
}