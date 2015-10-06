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
package de.metas.commission.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;

/** Generated Model for C_AdvCommissionPayrollLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_AdvCommissionPayrollLine extends PO implements I_C_AdvCommissionPayrollLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_AdvCommissionPayrollLine (Properties ctx, int C_AdvCommissionPayrollLine_ID, String trxName)
    {
      super (ctx, C_AdvCommissionPayrollLine_ID, trxName);
      /** if (C_AdvCommissionPayrollLine_ID == 0)
        {
			setC_AdvCommissionPayroll_ID (0);
			setC_AdvCommissionPayrollLine_ID (0);
			setC_AdvCommissionTerm_ID (0);
			setC_Currency_ID (0);
			setCommissionPoints (Env.ZERO);
			setC_TaxCategory_ID (0);
			setIsCommissionLock (false);
// N
			setProcessed (false);
// N
        } */
    }

    /** Load Constructor */
    public X_C_AdvCommissionPayrollLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
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
      StringBuffer sb = new StringBuffer ("X_C_AdvCommissionPayrollLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Multiplikator Betrag.
		@param AmtMultiplier 
		Betrags-Multiplikator für die Berechnung von Provisionen
	  */
	public void setAmtMultiplier (BigDecimal AmtMultiplier)
	{
		set_ValueNoCheck (COLUMNNAME_AmtMultiplier, AmtMultiplier);
	}

	/** Get Multiplikator Betrag.
		@return Betrags-Multiplikator für die Berechnung von Provisionen
	  */
	public BigDecimal getAmtMultiplier () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AmtMultiplier);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public de.metas.commission.model.I_C_AdvCommissionInstance getC_AdvCommissionInstance() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionInstance)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionInstance.Table_Name)
			.getPO(getC_AdvCommissionInstance_ID(), get_TrxName());	}

	/** Set Provisionsvorgang.
		@param C_AdvCommissionInstance_ID Provisionsvorgang	  */
	public void setC_AdvCommissionInstance_ID (int C_AdvCommissionInstance_ID)
	{
		if (C_AdvCommissionInstance_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionInstance_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionInstance_ID, Integer.valueOf(C_AdvCommissionInstance_ID));
	}

	/** Get Provisionsvorgang.
		@return Provisionsvorgang	  */
	public int getC_AdvCommissionInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_AdvCommissionPayroll getC_AdvCommissionPayroll() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionPayroll)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionPayroll.Table_Name)
			.getPO(getC_AdvCommissionPayroll_ID(), get_TrxName());	}

	/** Set Anlage zur Provisionsabrechnung.
		@param C_AdvCommissionPayroll_ID Anlage zur Provisionsabrechnung	  */
	public void setC_AdvCommissionPayroll_ID (int C_AdvCommissionPayroll_ID)
	{
		if (C_AdvCommissionPayroll_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionPayroll_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionPayroll_ID, Integer.valueOf(C_AdvCommissionPayroll_ID));
	}

	/** Get Anlage zur Provisionsabrechnung.
		@return Anlage zur Provisionsabrechnung	  */
	public int getC_AdvCommissionPayroll_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionPayroll_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Prov-Berechungsposition.
		@param C_AdvCommissionPayrollLine_ID Prov-Berechungsposition	  */
	public void setC_AdvCommissionPayrollLine_ID (int C_AdvCommissionPayrollLine_ID)
	{
		if (C_AdvCommissionPayrollLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionPayrollLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionPayrollLine_ID, Integer.valueOf(C_AdvCommissionPayrollLine_ID));
	}

	/** Get Prov-Berechungsposition.
		@return Prov-Berechungsposition	  */
	public int getC_AdvCommissionPayrollLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionPayrollLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_AdvCommissionTerm getC_AdvCommissionTerm() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionTerm)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionTerm.Table_Name)
			.getPO(getC_AdvCommissionTerm_ID(), get_TrxName());	}

	/** Set Provisionsart.
		@param C_AdvCommissionTerm_ID Provisionsart	  */
	public void setC_AdvCommissionTerm_ID (int C_AdvCommissionTerm_ID)
	{
		if (C_AdvCommissionTerm_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionTerm_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionTerm_ID, Integer.valueOf(C_AdvCommissionTerm_ID));
	}

	/** Get Provisionsart.
		@return Provisionsart	  */
	public int getC_AdvCommissionTerm_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionTerm_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
    {
		return (org.compiere.model.I_C_Currency)MTable.get(getCtx(), org.compiere.model.I_C_Currency.Table_Name)
			.getPO(getC_Currency_ID(), get_TrxName());	}

	/** Set Währung.
		@param C_Currency_ID 
		Die Wå©²ung fð² ¤iesen Eintrag
	  */
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return Die Wå©²ung fð² ¤iesen Eintrag
	  */
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Prov. %.
		@param Commission 
		Commission stated as a percentage
	  */
	public void setCommission (BigDecimal Commission)
	{
		set_ValueNoCheck (COLUMNNAME_Commission, Commission);
	}

	/** Get Prov. %.
		@return Commission stated as a percentage
	  */
	public BigDecimal getCommission () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Commission);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Provisions-Betrag.
		@param CommissionAmt 
		Provisions-Betrag
	  */
	public void setCommissionAmt (BigDecimal CommissionAmt)
	{
		set_ValueNoCheck (COLUMNNAME_CommissionAmt, CommissionAmt);
	}

	/** Get Provisions-Betrag.
		@return Provisions-Betrag
	  */
	public BigDecimal getCommissionAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CommissionAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Zeilennetto.
		@param CommissionAmtBase Zeilennetto	  */
	public void setCommissionAmtBase (BigDecimal CommissionAmtBase)
	{
		set_ValueNoCheck (COLUMNNAME_CommissionAmtBase, CommissionAmtBase);
	}

	/** Get Zeilennetto.
		@return Zeilennetto	  */
	public BigDecimal getCommissionAmtBase () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CommissionAmtBase);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Provisionspunkte.
		@param CommissionPoints Provisionspunkte	  */
	public void setCommissionPoints (BigDecimal CommissionPoints)
	{
		set_ValueNoCheck (COLUMNNAME_CommissionPoints, CommissionPoints);
	}

	/** Get Provisionspunkte.
		@return Provisionspunkte	  */
	public BigDecimal getCommissionPoints () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CommissionPoints);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Provisionspunkte pro Einh..
		@param CommissionPointsBase 
		Provisionspunkte für ein Stück
	  */
	public void setCommissionPointsBase (BigDecimal CommissionPointsBase)
	{
		set_Value (COLUMNNAME_CommissionPointsBase, CommissionPointsBase);
	}

	/** Get Provisionspunkte pro Einh..
		@return Provisionspunkte für ein Stück
	  */
	public BigDecimal getCommissionPointsBase () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CommissionPointsBase);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Provisionspunkte Summe.
		@param CommissionPointsSum Provisionspunkte Summe	  */
	public void setCommissionPointsSum (BigDecimal CommissionPointsSum)
	{
		set_ValueNoCheck (COLUMNNAME_CommissionPointsSum, CommissionPointsSum);
	}

	/** Get Provisionspunkte Summe.
		@return Provisionspunkte Summe	  */
	public BigDecimal getCommissionPointsSum () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CommissionPointsSum);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public org.compiere.model.I_C_TaxCategory getC_TaxCategory() throws RuntimeException
    {
		return (org.compiere.model.I_C_TaxCategory)MTable.get(getCtx(), org.compiere.model.I_C_TaxCategory.Table_Name)
			.getPO(getC_TaxCategory_ID(), get_TrxName());	}

	/** Set Steuerkategorie.
		@param C_TaxCategory_ID 
		Steuerkategorie
	  */
	public void setC_TaxCategory_ID (int C_TaxCategory_ID)
	{
		if (C_TaxCategory_ID < 1) 
			set_Value (COLUMNNAME_C_TaxCategory_ID, null);
		else 
			set_Value (COLUMNNAME_C_TaxCategory_ID, Integer.valueOf(C_TaxCategory_ID));
	}

	/** Get Steuerkategorie.
		@return Steuerkategorie
	  */
	public int getC_TaxCategory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_TaxCategory_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Provisionssperre.
		@param IsCommissionLock Provisionssperre	  */
	public void setIsCommissionLock (boolean IsCommissionLock)
	{
		set_ValueNoCheck (COLUMNNAME_IsCommissionLock, Boolean.valueOf(IsCommissionLock));
	}

	/** Get Provisionssperre.
		@return Provisionssperre	  */
	public boolean isCommissionLock () 
	{
		Object oo = get_Value(COLUMNNAME_IsCommissionLock);
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
		Einzelne Zeile in dem Dokument
	  */
	public void setLine (int Line)
	{
		set_ValueNoCheck (COLUMNNAME_Line, Integer.valueOf(Line));
	}

	/** Get Zeile Nr..
		@return Einzelne Zeile in dem Dokument
	  */
	public int getLine () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Line);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verarbeitet.
		@param Processed 
		The document has been processed
	  */
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return The document has been processed
	  */
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
		Menge
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Menge
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}