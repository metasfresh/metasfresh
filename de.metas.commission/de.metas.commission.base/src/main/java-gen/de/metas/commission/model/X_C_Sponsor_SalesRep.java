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

/** Generated Model for C_Sponsor_SalesRep
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_Sponsor_SalesRep extends PO implements I_C_Sponsor_SalesRep, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_Sponsor_SalesRep (Properties ctx, int C_Sponsor_SalesRep_ID, String trxName)
    {
      super (ctx, C_Sponsor_SalesRep_ID, trxName);
      /** if (C_Sponsor_SalesRep_ID == 0)
        {
			setC_AdvComSystem_ID (0);
			setC_Sponsor_ID (0);
			setC_Sponsor_SalesRep_ID (0);
			setSponsorSalesRepType (null);
			setValidFrom (new Timestamp( System.currentTimeMillis() ));
			setValidTo (new Timestamp( System.currentTimeMillis() ));
        } */
    }

    /** Load Constructor */
    public X_C_Sponsor_SalesRep (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_C_Sponsor_SalesRep[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public de.metas.commission.model.I_C_AdvCommissionCondition getC_AdvCommissionCondition() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvCommissionCondition)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvCommissionCondition.Table_Name)
			.getPO(getC_AdvCommissionCondition_ID(), get_TrxName());	}

	/** Set Provisionsvertrag.
		@param C_AdvCommissionCondition_ID Provisionsvertrag	  */
	public void setC_AdvCommissionCondition_ID (int C_AdvCommissionCondition_ID)
	{
		if (C_AdvCommissionCondition_ID < 1) 
			set_Value (COLUMNNAME_C_AdvCommissionCondition_ID, null);
		else 
			set_Value (COLUMNNAME_C_AdvCommissionCondition_ID, Integer.valueOf(C_AdvCommissionCondition_ID));
	}

	/** Get Provisionsvertrag.
		@return Provisionsvertrag	  */
	public int getC_AdvCommissionCondition_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommissionCondition_ID);
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

	/** Set Gesch?ftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen Gesch�ftspartner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Gesch?ftspartner.
		@return Bezeichnet einen Gesch�ftspartner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
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

	public de.metas.commission.model.I_C_Sponsor getC_Sponsor_Parent() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_Sponsor)MTable.get(getCtx(), de.metas.commission.model.I_C_Sponsor.Table_Name)
			.getPO(getC_Sponsor_Parent_ID(), get_TrxName());	}

	/** Set Eltern-Sponsor.
		@param C_Sponsor_Parent_ID Eltern-Sponsor	  */
	public void setC_Sponsor_Parent_ID (int C_Sponsor_Parent_ID)
	{
		if (C_Sponsor_Parent_ID < 1) 
			set_Value (COLUMNNAME_C_Sponsor_Parent_ID, null);
		else 
			set_Value (COLUMNNAME_C_Sponsor_Parent_ID, Integer.valueOf(C_Sponsor_Parent_ID));
	}

	/** Get Eltern-Sponsor.
		@return Eltern-Sponsor	  */
	public int getC_Sponsor_Parent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Sponsor_Parent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sponsor-Vertriebspartner.
		@param C_Sponsor_SalesRep_ID Sponsor-Vertriebspartner	  */
	public void setC_Sponsor_SalesRep_ID (int C_Sponsor_SalesRep_ID)
	{
		if (C_Sponsor_SalesRep_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_SalesRep_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_SalesRep_ID, Integer.valueOf(C_Sponsor_SalesRep_ID));
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

	/** SponsorSalesRepType AD_Reference_ID=540284 */
	public static final int SPONSORSALESREPTYPE_AD_Reference_ID=540284;
	/** Hierarchie = H */
	public static final String SPONSORSALESREPTYPE_Hierarchie = "H";
	/** VP = S */
	public static final String SPONSORSALESREPTYPE_VP = "S";
	/** Set Art.
		@param SponsorSalesRepType Art	  */
	public void setSponsorSalesRepType (String SponsorSalesRepType)
	{

		set_Value (COLUMNNAME_SponsorSalesRepType, SponsorSalesRepType);
	}

	/** Get Art.
		@return Art	  */
	public String getSponsorSalesRepType () 
	{
		return (String)get_Value(COLUMNNAME_SponsorSalesRepType);
	}

	/** Set Gültig ab.
		@param ValidFrom 
		Gültig ab inklusiv (erster Tag)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Gültig ab.
		@return Gültig ab inklusiv (erster Tag)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Gültig bis.
		@param ValidTo 
		Gültig bis inklusiv (letzter Tag)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Gültig bis.
		@return Gültig bis inklusiv (letzter Tag)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
	}
}