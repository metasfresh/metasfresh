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
package de.metas.acct.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for Fact_Acct_EndingBalance
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_Fact_Acct_EndingBalance extends org.compiere.model.PO implements I_Fact_Acct_EndingBalance, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1932561643L;

    /** Standard Constructor */
    public X_Fact_Acct_EndingBalance (Properties ctx, int Fact_Acct_EndingBalance_ID, String trxName)
    {
      super (ctx, Fact_Acct_EndingBalance_ID, trxName);
      /** if (Fact_Acct_EndingBalance_ID == 0)
        {
			setAccount_ID (0);
			setAmtAcctCr_DTD (BigDecimal.ZERO);
			setAmtAcctDr_DTD (BigDecimal.ZERO);
			setC_AcctSchema_ID (0);
			setDateAcct (new Timestamp( System.currentTimeMillis() ));
			setFact_Acct_ID (0);
			setPostingType (null);
        } */
    }

    /** Load Constructor */
    public X_Fact_Acct_EndingBalance (Properties ctx, ResultSet rs, String trxName)
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
		Verwendetes Konto
	  */
	@Override
	public void setAccount_ID (int Account_ID)
	{
		if (Account_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Account_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Account_ID, Integer.valueOf(Account_ID));
	}

	/** Get Konto.
		@return Verwendetes Konto
	  */
	@Override
	public int getAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Account_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Haben (Day to Date).
		@param AmtAcctCr_DTD 
		Credit from day beginning to date
	  */
	@Override
	public void setAmtAcctCr_DTD (java.math.BigDecimal AmtAcctCr_DTD)
	{
		set_Value (COLUMNNAME_AmtAcctCr_DTD, AmtAcctCr_DTD);
	}

	/** Get Haben (Day to Date).
		@return Credit from day beginning to date
	  */
	@Override
	public java.math.BigDecimal getAmtAcctCr_DTD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtAcctCr_DTD);
		if (bd == null)
			 return BigDecimal.ZERO;
		return bd;
	}

	/** Set Soll (Day to Date).
		@param AmtAcctDr_DTD 
		Debit from day beginning to Date
	  */
	@Override
	public void setAmtAcctDr_DTD (java.math.BigDecimal AmtAcctDr_DTD)
	{
		set_Value (COLUMNNAME_AmtAcctDr_DTD, AmtAcctDr_DTD);
	}

	/** Get Soll (Day to Date).
		@return Debit from day beginning to Date
	  */
	@Override
	public java.math.BigDecimal getAmtAcctDr_DTD () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtAcctDr_DTD);
		if (bd == null)
			 return BigDecimal.ZERO;
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
		Stammdaten für Buchhaltung
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
		@return Stammdaten für Buchhaltung
	  */
	@Override
	public int getC_AcctSchema_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AcctSchema_ID);
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
		set_ValueNoCheck (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Buchungsdatum.
		@return Accounting Date
	  */
	@Override
	public java.sql.Timestamp getDateAcct () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateAcct);
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

	/** 
	 * PostingType AD_Reference_ID=125
	 * Reference name: _Posting Type
	 */
	public static final int POSTINGTYPE_AD_Reference_ID=125;
	/** Actual = A */
	public static final String POSTINGTYPE_Actual = "A";
	/** Budget = B */
	public static final String POSTINGTYPE_Budget = "B";
	/** Commitment = E */
	public static final String POSTINGTYPE_Commitment = "E";
	/** Statistical = S */
	public static final String POSTINGTYPE_Statistical = "S";
	/** Reservation = R */
	public static final String POSTINGTYPE_Reservation = "R";
	/** Set Buchungsart.
		@param PostingType 
		Die Art des gebuchten Betrages dieser Transaktion
	  */
	@Override
	public void setPostingType (java.lang.String PostingType)
	{

		set_ValueNoCheck (COLUMNNAME_PostingType, PostingType);
	}

	/** Get Buchungsart.
		@return Die Art des gebuchten Betrages dieser Transaktion
	  */
	@Override
	public java.lang.String getPostingType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PostingType);
	}
}