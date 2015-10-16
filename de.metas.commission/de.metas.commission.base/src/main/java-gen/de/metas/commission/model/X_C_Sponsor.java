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
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for C_Sponsor
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_Sponsor extends PO implements I_C_Sponsor, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_Sponsor (Properties ctx, int C_Sponsor_ID, String trxName)
    {
      super (ctx, C_Sponsor_ID, trxName);
      /** if (C_Sponsor_ID == 0)
        {
			setC_AdvCommissionSalaryGroup_ID (0);
			setC_AdvComRank_System_ID (0);
			setC_Sponsor_ID (0);
			setIsManualRank (false);
// N
			setSponsorNo (null);
// 0
        } */
    }

    /** Load Constructor */
    public X_C_Sponsor (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_Sponsor[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public de.metas.commission.model.I_C_AdvCommissionSalaryGroup getC_AdvCommissionSalaryGroup() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionSalaryGroup)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionSalaryGroup.Table_Name)
			.getPO(getC_AdvCommissionSalaryGroup_ID(), get_TrxName());	}

	/** Set Vergütungsgruppe.
		@param C_AdvCommissionSalaryGroup_ID 
		US1026: Änderung Vergütungsplan (2011010610000028) R01A06
	  */
	public void setC_AdvCommissionSalaryGroup_ID (int C_AdvCommissionSalaryGroup_ID)
	{
		if (C_AdvCommissionSalaryGroup_ID < 1) 
			set_Value (COLUMNNAME_C_AdvCommissionSalaryGroup_ID, null);
		else 
			set_Value (COLUMNNAME_C_AdvCommissionSalaryGroup_ID, Integer.valueOf(C_AdvCommissionSalaryGroup_ID));
	}

	/** Get Vergütungsgruppe.
		@return US1026: Änderung Vergütungsplan (2011010610000028) R01A06
	  */
	public int getC_AdvCommissionSalaryGroup_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionSalaryGroup_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_AdvCommissionSalaryGroup getC_AdvComRank_System() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionSalaryGroup)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionSalaryGroup.Table_Name)
			.getPO(getC_AdvComRank_System_ID(), get_TrxName());	}

	/** Set Vergütungsgruppe (System).
		@param C_AdvComRank_System_ID 
		US1026: Änderung Vergütungsplan (2011010610000028) R01A06
	  */
	public void setC_AdvComRank_System_ID (int C_AdvComRank_System_ID)
	{
		if (C_AdvComRank_System_ID < 1) 
			set_Value (COLUMNNAME_C_AdvComRank_System_ID, null);
		else 
			set_Value (COLUMNNAME_C_AdvComRank_System_ID, Integer.valueOf(C_AdvComRank_System_ID));
	}

	/** Get Vergütungsgruppe (System).
		@return US1026: Änderung Vergütungsplan (2011010610000028) R01A06
	  */
	public int getC_AdvComRank_System_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComRank_System_ID);
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	/** Set C_Sponsor_Kunde_includedTab.
		@param C_Sponsor_Kunde_includedTab C_Sponsor_Kunde_includedTab	  */
	public void setC_Sponsor_Kunde_includedTab (String C_Sponsor_Kunde_includedTab)
	{
		set_Value (COLUMNNAME_C_Sponsor_Kunde_includedTab, C_Sponsor_Kunde_includedTab);
	}

	/** Get C_Sponsor_Kunde_includedTab.
		@return C_Sponsor_Kunde_includedTab	  */
	public String getC_Sponsor_Kunde_includedTab () 
	{
		return (String)get_Value(COLUMNNAME_C_Sponsor_Kunde_includedTab);
	}

	/** Set C_Sponsor_VP_includedTab.
		@param C_Sponsor_VP_includedTab C_Sponsor_VP_includedTab	  */
	public void setC_Sponsor_VP_includedTab (String C_Sponsor_VP_includedTab)
	{
		set_Value (COLUMNNAME_C_Sponsor_VP_includedTab, C_Sponsor_VP_includedTab);
	}

	/** Get C_Sponsor_VP_includedTab.
		@return C_Sponsor_VP_includedTab	  */
	public String getC_Sponsor_VP_includedTab () 
	{
		return (String)get_Value(COLUMNNAME_C_Sponsor_VP_includedTab);
	}

	/** Set Beschreibung.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
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

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		throw new IllegalArgumentException ("Name is virtual column");	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	/** Set Sponsornummer.
		@param SponsorNo Sponsornummer	  */
	public void setSponsorNo (String SponsorNo)
	{
		set_ValueNoCheck (COLUMNNAME_SponsorNo, SponsorNo);
	}

	/** Get Sponsornummer.
		@return Sponsornummer	  */
	public String getSponsorNo () 
	{
		return (String)get_Value(COLUMNNAME_SponsorNo);
	}

	/** Set Downline ENDKn.
		@param StatsDLCustomers Downline ENDKn	  */
	public void setStatsDLCustomers (int StatsDLCustomers)
	{
		set_Value (COLUMNNAME_StatsDLCustomers, Integer.valueOf(StatsDLCustomers));
	}

	/** Get Downline ENDKn.
		@return Downline ENDKn	  */
	public int getStatsDLCustomers () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_StatsDLCustomers);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Downline VPs.
		@param StatsDLSalesReps Downline VPs	  */
	public void setStatsDLSalesReps (int StatsDLSalesReps)
	{
		set_Value (COLUMNNAME_StatsDLSalesReps, Integer.valueOf(StatsDLSalesReps));
	}

	/** Get Downline VPs.
		@return Downline VPs	  */
	public int getStatsDLSalesReps () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_StatsDLSalesReps);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Statistik aktualisiert am.
		@param StatsLastUpdate Statistik aktualisiert am	  */
	public void setStatsLastUpdate (Timestamp StatsLastUpdate)
	{
		set_Value (COLUMNNAME_StatsLastUpdate, StatsLastUpdate);
	}

	/** Get Statistik aktualisiert am.
		@return Statistik aktualisiert am	  */
	public Timestamp getStatsLastUpdate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StatsLastUpdate);
	}
	
}