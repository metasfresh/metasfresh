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

/** Generated Model for C_Sponsor_CondLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_Sponsor_CondLine extends PO implements I_C_Sponsor_CondLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_Sponsor_CondLine (Properties ctx, int C_Sponsor_CondLine_ID, String trxName)
    {
      super (ctx, C_Sponsor_CondLine_ID, trxName);
      /** if (C_Sponsor_CondLine_ID == 0)
        {
			setC_AdvComSystem_ID (0);
			setC_Sponsor_Cond_ID (0);
			setC_Sponsor_CondLine_ID (0);
			setDateFrom (new Timestamp( System.currentTimeMillis() ));
			setDateTo (new Timestamp( System.currentTimeMillis() ));
			setSponsorSalesRepType (null);
        } */
    }

    /** Load Constructor */
    public X_C_Sponsor_CondLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_Sponsor_CondLine[")
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

	/** Set VergÃ¼tungsplan.
		@param C_AdvComSystem_ID VergÃ¼tungsplan	  */
	public void setC_AdvComSystem_ID (int C_AdvComSystem_ID)
	{
		if (C_AdvComSystem_ID < 1) 
			set_Value (COLUMNNAME_C_AdvComSystem_ID, null);
		else 
			set_Value (COLUMNNAME_C_AdvComSystem_ID, Integer.valueOf(C_AdvComSystem_ID));
	}

	/** Get VergÃ¼tungsplan.
		@return VergÃ¼tungsplan	  */
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

	/** Set GeschÃ¤ftspartner.
		@param C_BPartner_ID 
		Bezeichnet einen GeschÃ¤ftspartner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get GeschÃ¤ftspartner.
		@return Bezeichnet einen GeschÃ¤ftspartner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_Sponsor_Cond getC_Sponsor_Cond() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_Sponsor_Cond)MTable.get(getCtx(), de.metas.commission.model.I_C_Sponsor_Cond.Table_Name)
			.getPO(getC_Sponsor_Cond_ID(), get_TrxName());	}

	/** Set Sponsor Konditionen.
		@param C_Sponsor_Cond_ID Sponsor Konditionen	  */
	public void setC_Sponsor_Cond_ID (int C_Sponsor_Cond_ID)
	{
		if (C_Sponsor_Cond_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_Cond_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_Cond_ID, Integer.valueOf(C_Sponsor_Cond_ID));
	}

	/** Get Sponsor Konditionen.
		@return Sponsor Konditionen	  */
	public int getC_Sponsor_Cond_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Sponsor_Cond_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Konditionen-Zeile.
		@param C_Sponsor_CondLine_ID Konditionen-Zeile	  */
	public void setC_Sponsor_CondLine_ID (int C_Sponsor_CondLine_ID)
	{
		if (C_Sponsor_CondLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_CondLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_CondLine_ID, Integer.valueOf(C_Sponsor_CondLine_ID));
	}

	/** Get Konditionen-Zeile.
		@return Konditionen-Zeile	  */
	public int getC_Sponsor_CondLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Sponsor_CondLine_ID);
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

	/** Set Datum von.
		@param DateFrom 
		Startdatum eines Abschnittes
	  */
	public void setDateFrom (Timestamp DateFrom)
	{
		set_Value (COLUMNNAME_DateFrom, DateFrom);
	}

	/** Get Datum von.
		@return Startdatum eines Abschnittes
	  */
	public Timestamp getDateFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateFrom);
	}

	/** Set Datum bis.
		@param DateTo 
		Enddatum eines Abschnittes
	  */
	public void setDateTo (Timestamp DateTo)
	{
		set_Value (COLUMNNAME_DateTo, DateTo);
	}

	/** Get Datum bis.
		@return Enddatum eines Abschnittes
	  */
	public Timestamp getDateTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateTo);
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

		set_ValueNoCheck (COLUMNNAME_SponsorSalesRepType, SponsorSalesRepType);
	}

	/** Get Art.
		@return Art	  */
	public String getSponsorSalesRepType () 
	{
		return (String)get_Value(COLUMNNAME_SponsorSalesRepType);
	}
}