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

/** Generated Model for C_AdvComSalesRepFact
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_AdvComSalesRepFact extends PO implements I_C_AdvComSalesRepFact, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120510L;

    /** Standard Constructor */
    public X_C_AdvComSalesRepFact (Properties ctx, int C_AdvComSalesRepFact_ID, String trxName)
    {
      super (ctx, C_AdvComSalesRepFact_ID, trxName);
      /** if (C_AdvComSalesRepFact_ID == 0)
        {
			setC_AdvComSalesRepFact_ID (0);
			setC_AdvComSystem_ID (0);
			setC_Period_ID (0);
			setC_Sponsor_ID (0);
// 1
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_AdvComSalesRepFact (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_AdvComSalesRepFact[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public de.metas.commission.model.I_C_AdvCommissionSalaryGroup getC_AdvCommissionSalaryGroup() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionSalaryGroup)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionSalaryGroup.Table_Name)
			.getPO(getC_AdvCommissionSalaryGroup_ID(), get_TrxName());	}

	/** Set Vergütungsgruppe.
		@param C_AdvCommissionSalaryGroup_ID Vergütungsgruppe	  */
	public void setC_AdvCommissionSalaryGroup_ID (int C_AdvCommissionSalaryGroup_ID)
	{
		if (C_AdvCommissionSalaryGroup_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionSalaryGroup_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommissionSalaryGroup_ID, Integer.valueOf(C_AdvCommissionSalaryGroup_ID));
	}

	/** Get Vergütungsgruppe.
		@return Vergütungsgruppe	  */
	public int getC_AdvCommissionSalaryGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionSalaryGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

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

	public de.metas.commission.model.I_C_AdvComSystem getC_AdvComSystem() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvComSystem)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvComSystem.Table_Name)
			.getPO(getC_AdvComSystem_ID(), get_TrxName());	}

	/** Set Vergütungsplan.
		@param C_AdvComSystem_ID Vergütungsplan	  */
	public void setC_AdvComSystem_ID (int C_AdvComSystem_ID)
	{
		if (C_AdvComSystem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComSystem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComSystem_ID, Integer.valueOf(C_AdvComSystem_ID));
	}

	/** Get Vergütungsplan.
		@return Vergütungsplan	  */
	public int getC_AdvComSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Periode.
		@param C_Period_ID 
		Periode des Kalenders
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Periode.
		@return Periode des Kalenders
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_Sponsor getC_Sponsor() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_Sponsor)MTable.get(getCtx(), de.metas.commission.model.I_C_Sponsor.Table_Name)
			.getPO(getC_Sponsor_ID(), get_TrxName());	}

	/** Set Sponsor.
		@param C_Sponsor_ID Sponsor	  */
	public void setC_Sponsor_ID (int C_Sponsor_ID)
	{
		if (C_Sponsor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_ID, Integer.valueOf(C_Sponsor_ID));
	}

	/** Get Sponsor.
		@return Sponsor	  */
	public int getC_Sponsor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Sponsor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Account Date.
		@param DateAcct 
		Accounting Date
	  */
	public void setDateAcct (Timestamp DateAcct)
	{
		set_Value (COLUMNNAME_DateAcct, DateAcct);
	}

	/** Get Account Date.
		@return Accounting Date
	  */
	public Timestamp getDateAcct () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateAcct);
	}

	/** Name AD_Reference_ID=540123 */
	public static final int NAME_AD_Reference_ID=540123;
	/** APV = APV */
	public static final String NAME_APV = "APV";
	/** 6EDL = ADV */
	public static final String NAME_6EDL = "ADV";
	/** VG-Änderung = SalaryGroupChange */
	public static final String NAME_VG_Aenderung = "SalaryGroupChange";
	/** Dyn. Kompression  = Compression */
	public static final String NAME_DynKompression = "Compression";
	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{

		set_ValueNoCheck (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Status AD_Reference_ID=540124 */
	public static final int STATUS_AD_Reference_ID=540124;
	/** Prognose = FORECAST */
	public static final String STATUS_Prognose = "FORECAST";
	/** Prov.-Relevant = ACTUAL */
	public static final String STATUS_Prov_Relevant = "ACTUAL";
	/** Manuell = MANUAL */
	public static final String STATUS_Manuell = "MANUAL";
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

	/** Set Zahlwert.
		@param ValueNumber 
		Numeric Value
	  */
	public void setValueNumber (BigDecimal ValueNumber)
	{
		set_ValueNoCheck (COLUMNNAME_ValueNumber, ValueNumber);
	}

	/** Get Zahlwert.
		@return Numeric Value
	  */
	public BigDecimal getValueNumber () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_ValueNumber);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Stringwert.
		@param ValueStr Stringwert	  */
	public void setValueStr (String ValueStr)
	{
		set_ValueNoCheck (COLUMNNAME_ValueStr, ValueStr);
	}

	/** Get Stringwert.
		@return Stringwert	  */
	public String getValueStr () 
	{
		return (String)get_Value(COLUMNNAME_ValueStr);
	}
}