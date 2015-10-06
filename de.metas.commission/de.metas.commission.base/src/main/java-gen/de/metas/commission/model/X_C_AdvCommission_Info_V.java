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

/** Generated Model for C_AdvCommission_Info_V
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_AdvCommission_Info_V extends PO implements I_C_AdvCommission_Info_V, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_AdvCommission_Info_V (Properties ctx, int C_AdvCommission_Info_V_ID, String trxName)
    {
      super (ctx, C_AdvCommission_Info_V_ID, trxName);
      /** if (C_AdvCommission_Info_V_ID == 0)
        {
			setC_AdvCommission_Info_V_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_AdvCommission_Info_V (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_AdvCommission_Info_V[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Provisions-Info.
		@param C_AdvCommission_Info_V_ID Provisions-Info	  */
	public void setC_AdvCommission_Info_V_ID (int C_AdvCommission_Info_V_ID)
	{
		if (C_AdvCommission_Info_V_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommission_Info_V_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvCommission_Info_V_ID, Integer.valueOf(C_AdvCommission_Info_V_ID));
	}

	/** Get Provisions-Info.
		@return Provisions-Info	  */
	public int getC_AdvCommission_Info_V_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvCommission_Info_V_ID);
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

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_ValueNoCheck (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
	}

	/** Set Ebene.
		@param Level Ebene	  */
	public void setLevel (int Level)
	{
		set_ValueNoCheck (COLUMNNAME_Level, Integer.valueOf(Level));
	}

	/** Get Ebene.
		@return Ebene	  */
	public int getLevel () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Level);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Punkte-Berechnet.
		@param Points_Calculated Punkte-Berechnet	  */
	public void setPoints_Calculated (BigDecimal Points_Calculated)
	{
		set_ValueNoCheck (COLUMNNAME_Points_Calculated, Points_Calculated);
	}

	/** Get Punkte-Berechnet.
		@return Punkte-Berechnet	  */
	public BigDecimal getPoints_Calculated () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Points_Calculated);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Punkte-Ausgezahlt.
		@param Points_Paid Punkte-Ausgezahlt	  */
	public void setPoints_Paid (BigDecimal Points_Paid)
	{
		set_ValueNoCheck (COLUMNNAME_Points_Paid, Points_Paid);
	}

	/** Get Punkte-Ausgezahlt.
		@return Punkte-Ausgezahlt	  */
	public BigDecimal getPoints_Paid () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Points_Paid);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Punkte-Prognostiziert.
		@param Points_Predicted Punkte-Prognostiziert	  */
	public void setPoints_Predicted (BigDecimal Points_Predicted)
	{
		set_ValueNoCheck (COLUMNNAME_Points_Predicted, Points_Predicted);
	}

	/** Get Punkte-Prognostiziert.
		@return Punkte-Prognostiziert	  */
	public BigDecimal getPoints_Predicted () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Points_Predicted);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_ValueNoCheck (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}
}