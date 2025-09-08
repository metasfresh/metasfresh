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

/** Generated Model for C_AcctSchema_GL
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_AcctSchema_GL extends org.compiere.model.PO implements I_C_AcctSchema_GL, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 792611298L;

    /** Standard Constructor */
    public X_C_AcctSchema_GL (Properties ctx, int C_AcctSchema_GL_ID, String trxName)
    {
      super (ctx, C_AcctSchema_GL_ID, trxName);
      /** if (C_AcctSchema_GL_ID == 0)
        {
			setC_AcctSchema_ID (0);
			setCommitmentOffset_Acct (0);
			setCommitmentOffsetSales_Acct (0);
			setIncomeSummary_Acct (0);
			setIntercompanyDueFrom_Acct (0);
			setIntercompanyDueTo_Acct (0);
			setPPVOffset_Acct (0);
			setRetainedEarning_Acct (0);
			setUseCurrencyBalancing (false);
			setUseSuspenseBalancing (false);
			setUseSuspenseError (false);
        } */
    }

    /** Load Constructor */
    public X_C_AcctSchema_GL (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_C_ValidCombination getCashRounding_A()
	{
		return get_ValueAsPO(COLUMNNAME_CashRounding_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCashRounding_A(final org.compiere.model.I_C_ValidCombination CashRounding_A)
	{
		set_ValueFromPO(COLUMNNAME_CashRounding_Acct, org.compiere.model.I_C_ValidCombination.class, CashRounding_A);
	}

	@Override
	public void setCashRounding_Acct (final int CashRounding_Acct)
	{
		set_Value (COLUMNNAME_CashRounding_Acct, CashRounding_Acct);
	}

	@Override
	public int getCashRounding_Acct()
	{
		return get_ValueAsInt(COLUMNNAME_CashRounding_Acct);
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCommitmentOffset_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_CommitmentOffset_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCommitmentOffset_A(org.compiere.model.I_C_ValidCombination CommitmentOffset_A)
	{
		set_ValueFromPO(COLUMNNAME_CommitmentOffset_Acct, org.compiere.model.I_C_ValidCombination.class, CommitmentOffset_A);
	}

	/** Set Commitment Offset.
		@param CommitmentOffset_Acct 
		Budgetary Commitment Offset Account
	  */
	@Override
	public void setCommitmentOffset_Acct (int CommitmentOffset_Acct)
	{
		set_Value (COLUMNNAME_CommitmentOffset_Acct, Integer.valueOf(CommitmentOffset_Acct));
	}

	/** Get Commitment Offset.
		@return Budgetary Commitment Offset Account
	  */
	@Override
	public int getCommitmentOffset_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CommitmentOffset_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCommitmentOffsetSales_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_CommitmentOffsetSales_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCommitmentOffsetSales_A(org.compiere.model.I_C_ValidCombination CommitmentOffsetSales_A)
	{
		set_ValueFromPO(COLUMNNAME_CommitmentOffsetSales_Acct, org.compiere.model.I_C_ValidCombination.class, CommitmentOffsetSales_A);
	}

	/** Set Commitment Offset Sales.
		@param CommitmentOffsetSales_Acct 
		Budgetary Commitment Offset Account for Sales
	  */
	@Override
	public void setCommitmentOffsetSales_Acct (int CommitmentOffsetSales_Acct)
	{
		set_Value (COLUMNNAME_CommitmentOffsetSales_Acct, Integer.valueOf(CommitmentOffsetSales_Acct));
	}

	/** Get Commitment Offset Sales.
		@return Budgetary Commitment Offset Account for Sales
	  */
	@Override
	public int getCommitmentOffsetSales_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CommitmentOffsetSales_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getCurrencyBalancing_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_CurrencyBalancing_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setCurrencyBalancing_A(org.compiere.model.I_C_ValidCombination CurrencyBalancing_A)
	{
		set_ValueFromPO(COLUMNNAME_CurrencyBalancing_Acct, org.compiere.model.I_C_ValidCombination.class, CurrencyBalancing_A);
	}

	/** Set Konto für Währungsdifferenzen.
		@param CurrencyBalancing_Acct 
		Konto, das verwendet wird, wenn eine Währung unausgeglichen ist
	  */
	@Override
	public void setCurrencyBalancing_Acct (int CurrencyBalancing_Acct)
	{
		set_Value (COLUMNNAME_CurrencyBalancing_Acct, Integer.valueOf(CurrencyBalancing_Acct));
	}

	/** Get Konto für Währungsdifferenzen.
		@return Konto, das verwendet wird, wenn eine Währung unausgeglichen ist
	  */
	@Override
	public int getCurrencyBalancing_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CurrencyBalancing_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getIncomeSummary_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_IncomeSummary_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setIncomeSummary_A(org.compiere.model.I_C_ValidCombination IncomeSummary_A)
	{
		set_ValueFromPO(COLUMNNAME_IncomeSummary_Acct, org.compiere.model.I_C_ValidCombination.class, IncomeSummary_A);
	}

	/** Set Gewinnvortrag vor Verwendung.
		@param IncomeSummary_Acct 
		Konto für Gewinnvortrag vor Verwendung
	  */
	@Override
	public void setIncomeSummary_Acct (int IncomeSummary_Acct)
	{
		set_Value (COLUMNNAME_IncomeSummary_Acct, Integer.valueOf(IncomeSummary_Acct));
	}

	/** Get Gewinnvortrag vor Verwendung.
		@return Konto für Gewinnvortrag vor Verwendung
	  */
	@Override
	public int getIncomeSummary_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_IncomeSummary_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getIntercompanyDueFrom_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_IntercompanyDueFrom_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setIntercompanyDueFrom_A(org.compiere.model.I_C_ValidCombination IntercompanyDueFrom_A)
	{
		set_ValueFromPO(COLUMNNAME_IntercompanyDueFrom_Acct, org.compiere.model.I_C_ValidCombination.class, IntercompanyDueFrom_A);
	}

	/** Set Forderungen geg. verbundenen Unternehmen.
		@param IntercompanyDueFrom_Acct 
		Konto für Forderungen geg. verbundenen Unternehmen
	  */
	@Override
	public void setIntercompanyDueFrom_Acct (int IntercompanyDueFrom_Acct)
	{
		set_Value (COLUMNNAME_IntercompanyDueFrom_Acct, Integer.valueOf(IntercompanyDueFrom_Acct));
	}

	/** Get Forderungen geg. verbundenen Unternehmen.
		@return Konto für Forderungen geg. verbundenen Unternehmen
	  */
	@Override
	public int getIntercompanyDueFrom_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_IntercompanyDueFrom_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getIntercompanyDueTo_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_IntercompanyDueTo_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setIntercompanyDueTo_A(org.compiere.model.I_C_ValidCombination IntercompanyDueTo_A)
	{
		set_ValueFromPO(COLUMNNAME_IntercompanyDueTo_Acct, org.compiere.model.I_C_ValidCombination.class, IntercompanyDueTo_A);
	}

	/** Set Verbindlichkeiten geg. verbundenen Unternehmen.
		@param IntercompanyDueTo_Acct 
		Konto für Verbindlichkeiten gegenüber verbundenen Unternehmen
	  */
	@Override
	public void setIntercompanyDueTo_Acct (int IntercompanyDueTo_Acct)
	{
		set_Value (COLUMNNAME_IntercompanyDueTo_Acct, Integer.valueOf(IntercompanyDueTo_Acct));
	}

	/** Get Verbindlichkeiten geg. verbundenen Unternehmen.
		@return Konto für Verbindlichkeiten gegenüber verbundenen Unternehmen
	  */
	@Override
	public int getIntercompanyDueTo_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_IntercompanyDueTo_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getPPVOffset_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_PPVOffset_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setPPVOffset_A(org.compiere.model.I_C_ValidCombination PPVOffset_A)
	{
		set_ValueFromPO(COLUMNNAME_PPVOffset_Acct, org.compiere.model.I_C_ValidCombination.class, PPVOffset_A);
	}

	/** Set PPV Offset.
		@param PPVOffset_Acct 
		Purchase Price Variance Offset Account
	  */
	@Override
	public void setPPVOffset_Acct (int PPVOffset_Acct)
	{
		set_Value (COLUMNNAME_PPVOffset_Acct, Integer.valueOf(PPVOffset_Acct));
	}

	/** Get PPV Offset.
		@return Purchase Price Variance Offset Account
	  */
	@Override
	public int getPPVOffset_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PPVOffset_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getRetainedEarning_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_RetainedEarning_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setRetainedEarning_A(org.compiere.model.I_C_ValidCombination RetainedEarning_A)
	{
		set_ValueFromPO(COLUMNNAME_RetainedEarning_Acct, org.compiere.model.I_C_ValidCombination.class, RetainedEarning_A);
	}

	/** Set Saldenvortrag Sachkonten.
		@param RetainedEarning_Acct Saldenvortrag Sachkonten	  */
	@Override
	public void setRetainedEarning_Acct (int RetainedEarning_Acct)
	{
		set_Value (COLUMNNAME_RetainedEarning_Acct, Integer.valueOf(RetainedEarning_Acct));
	}

	/** Get Saldenvortrag Sachkonten.
		@return Saldenvortrag Sachkonten	  */
	@Override
	public int getRetainedEarning_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RetainedEarning_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getSuspenseBalancing_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_SuspenseBalancing_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setSuspenseBalancing_A(org.compiere.model.I_C_ValidCombination SuspenseBalancing_A)
	{
		set_ValueFromPO(COLUMNNAME_SuspenseBalancing_Acct, org.compiere.model.I_C_ValidCombination.class, SuspenseBalancing_A);
	}

	/** Set CpD-Konto.
		@param SuspenseBalancing_Acct 
		CpD-Konto (Conto-pro-Diverse) für doppelte Buchführung bei manuellen Buchungen.
	  */
	@Override
	public void setSuspenseBalancing_Acct (int SuspenseBalancing_Acct)
	{
		set_Value (COLUMNNAME_SuspenseBalancing_Acct, Integer.valueOf(SuspenseBalancing_Acct));
	}

	/** Get CpD-Konto.
		@return CpD-Konto (Conto-pro-Diverse) für doppelte Buchführung bei manuellen Buchungen.
	  */
	@Override
	public int getSuspenseBalancing_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SuspenseBalancing_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_ValidCombination getSuspenseError_A() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_SuspenseError_Acct, org.compiere.model.I_C_ValidCombination.class);
	}

	@Override
	public void setSuspenseError_A(org.compiere.model.I_C_ValidCombination SuspenseError_A)
	{
		set_ValueFromPO(COLUMNNAME_SuspenseError_Acct, org.compiere.model.I_C_ValidCombination.class, SuspenseError_A);
	}

	/** Set CpD-Fehlerkonto.
		@param SuspenseError_Acct CpD-Fehlerkonto	  */
	@Override
	public void setSuspenseError_Acct (int SuspenseError_Acct)
	{
		set_Value (COLUMNNAME_SuspenseError_Acct, Integer.valueOf(SuspenseError_Acct));
	}

	/** Get CpD-Fehlerkonto.
		@return CpD-Fehlerkonto	  */
	@Override
	public int getSuspenseError_Acct () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SuspenseError_Acct);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Wähungsunterschiede verbuchen.
		@param UseCurrencyBalancing 
		Sollen Währungsdifferenzen verbucht werden?
	  */
	@Override
	public void setUseCurrencyBalancing (boolean UseCurrencyBalancing)
	{
		set_Value (COLUMNNAME_UseCurrencyBalancing, Boolean.valueOf(UseCurrencyBalancing));
	}

	/** Get Wähungsunterschiede verbuchen.
		@return Sollen Währungsdifferenzen verbucht werden?
	  */
	@Override
	public boolean isUseCurrencyBalancing () 
	{
		Object oo = get_Value(COLUMNNAME_UseCurrencyBalancing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set doppelte Buchführung.
		@param UseSuspenseBalancing 
		Verwendet doppelte Buchführung über Zwischenkonto bei manuellen Buchungen.
	  */
	@Override
	public void setUseSuspenseBalancing (boolean UseSuspenseBalancing)
	{
		set_Value (COLUMNNAME_UseSuspenseBalancing, Boolean.valueOf(UseSuspenseBalancing));
	}

	/** Get doppelte Buchführung.
		@return Verwendet doppelte Buchführung über Zwischenkonto bei manuellen Buchungen.
	  */
	@Override
	public boolean isUseSuspenseBalancing () 
	{
		Object oo = get_Value(COLUMNNAME_UseSuspenseBalancing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set CpD-Fehlerkonto verwenden.
		@param UseSuspenseError CpD-Fehlerkonto verwenden	  */
	@Override
	public void setUseSuspenseError (boolean UseSuspenseError)
	{
		set_Value (COLUMNNAME_UseSuspenseError, Boolean.valueOf(UseSuspenseError));
	}

	/** Get CpD-Fehlerkonto verwenden.
		@return CpD-Fehlerkonto verwenden	  */
	@Override
	public boolean isUseSuspenseError () 
	{
		Object oo = get_Value(COLUMNNAME_UseSuspenseError);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}