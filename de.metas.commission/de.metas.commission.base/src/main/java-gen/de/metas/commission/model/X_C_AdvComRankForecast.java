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

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for C_AdvComRankForecast
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_AdvComRankForecast extends PO implements I_C_AdvComRankForecast, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_AdvComRankForecast (Properties ctx, int C_AdvComRankForecast_ID, String trxName)
    {
      super (ctx, C_AdvComRankForecast_ID, trxName);
      /** if (C_AdvComRankForecast_ID == 0)
        {
			setC_AdvCommissionSalaryGroup_ID (0);
			setC_AdvComRankForecast_ID (0);
			setC_AdvComSystem_ID (0);
			setC_Period_Since_ID (0);
			setC_Sponsor_ID (0);
			setIsManualRank (false);
// N
        } */
    }

    /** Load Constructor */
    public X_C_AdvComRankForecast (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 2 - Client 
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
      StringBuffer sb = new StringBuffer ("X_C_AdvComRankForecast[")
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
			set_Value (COLUMNNAME_C_AdvCommissionSalaryGroup_ID, null);
		else 
			set_Value (COLUMNNAME_C_AdvCommissionSalaryGroup_ID, Integer.valueOf(C_AdvCommissionSalaryGroup_ID));
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

	/** Set VG-Prognose.
		@param C_AdvComRankForecast_ID VG-Prognose	  */
	public void setC_AdvComRankForecast_ID (int C_AdvComRankForecast_ID)
	{
		if (C_AdvComRankForecast_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComRankForecast_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComRankForecast_ID, Integer.valueOf(C_AdvComRankForecast_ID));
	}

	/** Get VG-Prognose.
		@return VG-Prognose	  */
	public int getC_AdvComRankForecast_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComRankForecast_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_AdvCommissionSalaryGroup getC_AdvComRank_Next() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionSalaryGroup)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionSalaryGroup.Table_Name)
			.getPO(getC_AdvComRank_Next_ID(), get_TrxName());	}

	/** Set nächste VG.
		@param C_AdvComRank_Next_ID nächste VG	  */
	public void setC_AdvComRank_Next_ID (int C_AdvComRank_Next_ID)
	{
		if (C_AdvComRank_Next_ID < 1) 
			set_Value (COLUMNNAME_C_AdvComRank_Next_ID, null);
		else 
			set_Value (COLUMNNAME_C_AdvComRank_Next_ID, Integer.valueOf(C_AdvComRank_Next_ID));
	}

	/** Get nächste VG.
		@return nächste VG	  */
	public int getC_AdvComRank_Next_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComRank_Next_ID);
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
			set_Value (COLUMNNAME_C_AdvComSystem_ID, null);
		else 
			set_Value (COLUMNNAME_C_AdvComSystem_ID, Integer.valueOf(C_AdvComSystem_ID));
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

	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (org.compiere.model.I_C_BPartner)MTable.get(getCtx(), org.compiere.model.I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Geschäftspartner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Period getC_Period_Since() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_Since_ID(), get_TrxName());	}

	/** Set gültig seit einschl. Periode.
		@param C_Period_Since_ID gültig seit einschl. Periode	  */
	public void setC_Period_Since_ID (int C_Period_Since_ID)
	{
		if (C_Period_Since_ID < 1) 
			set_Value (COLUMNNAME_C_Period_Since_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_Since_ID, Integer.valueOf(C_Period_Since_ID));
	}

	/** Get gültig seit einschl. Periode.
		@return gültig seit einschl. Periode	  */
	public int getC_Period_Since_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_Since_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Period getC_Period_Until() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_Until_ID(), get_TrxName());	}

	/** Set gültig bis einschl. Periode.
		@param C_Period_Until_ID gültig bis einschl. Periode	  */
	public void setC_Period_Until_ID (int C_Period_Until_ID)
	{
		if (C_Period_Until_ID < 1) 
			set_Value (COLUMNNAME_C_Period_Until_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_Until_ID, Integer.valueOf(C_Period_Until_ID));
	}

	/** Get gültig bis einschl. Periode.
		@return gültig bis einschl. Periode	  */
	public int getC_Period_Until_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_Until_ID);
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

	/** Set fixierte VG.
		@param IsManualRank 
		US1026: Änderung Vergütungsplan (2011010610000028)  R01A06
	  */
	public void setIsManualRank (boolean IsManualRank)
	{
		set_Value (COLUMNNAME_IsManualRank, Boolean.valueOf(IsManualRank));
	}

	/** Get fixierte VG.
		@return US1026: Änderung Vergütungsplan (2011010610000028)  R01A06
	  */
	public boolean isManualRank () 
	{
		Object oo = get_Value(COLUMNNAME_IsManualRank);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}