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
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;

/** Generated Model for C_AdvComFact_SalesRepFact_V
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_AdvComFact_SalesRepFact_V extends PO implements I_C_AdvComFact_SalesRepFact_V, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_AdvComFact_SalesRepFact_V (Properties ctx, int C_AdvComFact_SalesRepFact_V_ID, String trxName)
    {
      super (ctx, C_AdvComFact_SalesRepFact_V_ID, trxName);
      /** if (C_AdvComFact_SalesRepFact_V_ID == 0)
        {
			setAD_Table_ID (0);
			setC_AdvComFact_SalesRepFact_V_ID (0);
			setC_AdvCommissionInstance_ID (0);
			setC_AdvComSalesRepFact_ID (0);
			setCommissionPointsBase (Env.ZERO);
			setCommissionPointsSum (Env.ZERO);
			setDateDoc (new Timestamp( System.currentTimeMillis() ));
			setQty (Env.ZERO);
			setRecord_ID (0);
			setStatus (null);
        } */
    }

    /** Load Constructor */
    public X_C_AdvComFact_SalesRepFact_V (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_AdvComFact_SalesRepFact_V[")
        .append(get_ID()).append("]");
      return sb.toString();
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
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
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

	/** Set C_AdvComFact_SalesRepFact_V_ID.
		@param C_AdvComFact_SalesRepFact_V_ID C_AdvComFact_SalesRepFact_V_ID	  */
	public void setC_AdvComFact_SalesRepFact_V_ID (int C_AdvComFact_SalesRepFact_V_ID)
	{
		if (C_AdvComFact_SalesRepFact_V_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComFact_SalesRepFact_V_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComFact_SalesRepFact_V_ID, Integer.valueOf(C_AdvComFact_SalesRepFact_V_ID));
	}

	/** Get C_AdvComFact_SalesRepFact_V_ID.
		@return C_AdvComFact_SalesRepFact_V_ID	  */
	public int getC_AdvComFact_SalesRepFact_V_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComFact_SalesRepFact_V_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	public de.metas.commission.model.I_C_AdvComSalesRepFact getC_AdvComSalesRepFact() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvComSalesRepFact)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvComSalesRepFact.Table_Name)
			.getPO(getC_AdvComSalesRepFact_ID(), get_TrxName());	}

	/** Set Sponsor-Provisionsdatensatz.
		@param C_AdvComSalesRepFact_ID Sponsor-Provisionsdatensatz	  */
	public void setC_AdvComSalesRepFact_ID (int C_AdvComSalesRepFact_ID)
	{
		if (C_AdvComSalesRepFact_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComSalesRepFact_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComSalesRepFact_ID, Integer.valueOf(C_AdvComSalesRepFact_ID));
	}

	/** Get Sponsor-Provisionsdatensatz.
		@return Sponsor-Provisionsdatensatz	  */
	public int getC_AdvComSalesRepFact_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComSalesRepFact_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
		set_Value (COLUMNNAME_CommissionPointsSum, CommissionPointsSum);
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

	/** Set Belegdatum.
		@param DateDoc 
		Datum des Belegs
	  */
	public void setDateDoc (Timestamp DateDoc)
	{
		set_Value (COLUMNNAME_DateDoc, DateDoc);
	}

	/** Get Belegdatum.
		@return Datum des Belegs
	  */
	public Timestamp getDateDoc () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateDoc);
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

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
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

	/** Status AD_Reference_ID=540070 */
	public static final int STATUS_AD_Reference_ID=540070;
	/** prognostiziert = PR */
	public static final String STATUS_Prognostiziert = "PR";
	/** berechnet = CA */
	public static final String STATUS_Berechnet = "CA";
	/** auszuzahlen = PA */
	public static final String STATUS_Auszuzahlen = "PA";
	/** zu berechnen = CP */
	public static final String STATUS_ZuBerechnen = "CP";
	/** Set Status.
		@param Status 
		Status of the currently running check
	  */
	public void setStatus (String Status)
	{

		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status of the currently running check
	  */
	public String getStatus () 
	{
		return (String)get_Value(COLUMNNAME_Status);
	}
}