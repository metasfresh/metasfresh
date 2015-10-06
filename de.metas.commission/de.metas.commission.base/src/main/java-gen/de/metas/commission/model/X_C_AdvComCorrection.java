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

/** Generated Model for C_AdvComCorrection
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_AdvComCorrection extends PO implements I_C_AdvComCorrection, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_AdvComCorrection (Properties ctx, int C_AdvComCorrection_ID, String trxName)
    {
      super (ctx, C_AdvComCorrection_ID, trxName);
      /** if (C_AdvComCorrection_ID == 0)
        {
			setAD_Table_ID (0);
			setCommission (Env.ZERO);
			setIsTrigger (false);
// N
			setProcessed (false);
			setRecord_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_AdvComCorrection (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_AdvComCorrection[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Action AD_Reference_ID=540114 */
	public static final int ACTION_AD_Reference_ID=540114;
	/** Neue Vorgänge erzeugen = C */
	public static final String ACTION_NeueVorgaengeErzeugen = "C";
	/** Bestehende Vorgänge erweitern = A */
	public static final String ACTION_BestehendeVorgaengeErweitern = "A";
	/** Set Aktion.
		@param Action 
		Zeigt die durchzuführende Aktion an
	  */
	public void setAction (String Action)
	{

		set_Value (COLUMNNAME_Action, Action);
	}

	/** Get Aktion.
		@return Zeigt die durchzuführende Aktion an
	  */
	public String getAction () 
	{
		return (String)get_Value(COLUMNNAME_Action);
	}

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
    {
		return (org.compiere.model.I_AD_Table)MTable.get(getCtx(), org.compiere.model.I_AD_Table.Table_Name)
			.getPO(getAD_Table_ID(), get_TrxName());	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Provisions-Korrektursatz.
		@param C_AdvComCorrection_ID Provisions-Korrektursatz	  */
	public void setC_AdvComCorrection_ID (int C_AdvComCorrection_ID)
	{
		if (C_AdvComCorrection_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComCorrection_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComCorrection_ID, Integer.valueOf(C_AdvComCorrection_ID));
	}

	/** Get Provisions-Korrektursatz.
		@return Provisions-Korrektursatz	  */
	public int getC_AdvComCorrection_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComCorrection_ID);
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
		set_Value (COLUMNNAME_Commission, Commission);
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

	public de.metas.commission.model.I_C_Sponsor getC_Sponsor_Customer() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_Sponsor)MTable.get(getCtx(), de.metas.commission.model.I_C_Sponsor.Table_Name)
			.getPO(getC_Sponsor_Customer_ID(), get_TrxName());	}

	/** Set Sponsor-Kunde.
		@param C_Sponsor_Customer_ID Sponsor-Kunde	  */
	public void setC_Sponsor_Customer_ID (int C_Sponsor_Customer_ID)
	{
		if (C_Sponsor_Customer_ID < 1) 
			set_Value (COLUMNNAME_C_Sponsor_Customer_ID, null);
		else 
			set_Value (COLUMNNAME_C_Sponsor_Customer_ID, Integer.valueOf(C_Sponsor_Customer_ID));
	}

	/** Get Sponsor-Kunde.
		@return Sponsor-Kunde	  */
	public int getC_Sponsor_Customer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Sponsor_Customer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_Sponsor getC_Sponsor_SalesRep() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_Sponsor)MTable.get(getCtx(), de.metas.commission.model.I_C_Sponsor.Table_Name)
			.getPO(getC_Sponsor_SalesRep_ID(), get_TrxName());	}

	/** Set Sponsor-Vertriebspartner.
		@param C_Sponsor_SalesRep_ID Sponsor-Vertriebspartner	  */
	public void setC_Sponsor_SalesRep_ID (int C_Sponsor_SalesRep_ID)
	{
		if (C_Sponsor_SalesRep_ID < 1) 
			set_Value (COLUMNNAME_C_Sponsor_SalesRep_ID, null);
		else 
			set_Value (COLUMNNAME_C_Sponsor_SalesRep_ID, Integer.valueOf(C_Sponsor_SalesRep_ID));
	}

	/** Get Sponsor-Vertriebspartner.
		@return Sponsor-Vertriebspartner	  */
	public int getC_Sponsor_SalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Sponsor_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Auslöser.
		@param IsTrigger Auslöser	  */
	public void setIsTrigger (boolean IsTrigger)
	{
		set_Value (COLUMNNAME_IsTrigger, Boolean.valueOf(IsTrigger));
	}

	/** Get Auslöser.
		@return Auslöser	  */
	public boolean isTrigger () 
	{
		Object oo = get_Value(COLUMNNAME_IsTrigger);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Notiz.
		@param Note 
		Optional weitere Information für ein Dokument
	  */
	public void setNote (String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	/** Get Notiz.
		@return Optional weitere Information für ein Dokument
	  */
	public String getNote () 
	{
		return (String)get_Value(COLUMNNAME_Note);
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

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}