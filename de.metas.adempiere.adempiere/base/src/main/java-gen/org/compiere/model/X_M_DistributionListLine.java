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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

/** Generated Model for M_DistributionListLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_M_DistributionListLine extends PO implements I_M_DistributionListLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_M_DistributionListLine (Properties ctx, int M_DistributionListLine_ID, String trxName)
    {
      super (ctx, M_DistributionListLine_ID, trxName);
      /** if (M_DistributionListLine_ID == 0)
        {
			setC_BPartner_ID (0);
			setC_BPartner_Location_ID (0);
			setM_DistributionList_ID (0);
			setM_DistributionListLine_ID (0);
			setMinQty (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_M_DistributionListLine (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_DistributionListLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_BPartner getC_BPartner() throws RuntimeException
    {
		return (I_C_BPartner)MTable.get(getCtx(), I_C_BPartner.Table_Name)
			.getPO(getC_BPartner_ID(), get_TrxName());	}

	/** Set Business Partner .
		@param C_BPartner_ID 
		Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Business Partner .
		@return Identifies a Business Partner
	  */
	public int getC_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
    {
		return (I_C_BPartner_Location)MTable.get(getCtx(), I_C_BPartner_Location.Table_Name)
			.getPO(getC_BPartner_Location_ID(), get_TrxName());	}

	/** Set Partner Location.
		@param C_BPartner_Location_ID 
		Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Partner Location.
		@return Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	public I_M_DistributionList getM_DistributionList() throws RuntimeException
    {
		return (I_M_DistributionList)MTable.get(getCtx(), I_M_DistributionList.Table_Name)
			.getPO(getM_DistributionList_ID(), get_TrxName());	}

	/** Set Distribution List.
		@param M_DistributionList_ID 
		Distribution Lists allow to distribute products to a selected list of partners
	  */
	public void setM_DistributionList_ID (int M_DistributionList_ID)
	{
		if (M_DistributionList_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DistributionList_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DistributionList_ID, Integer.valueOf(M_DistributionList_ID));
	}

	/** Get Distribution List.
		@return Distribution Lists allow to distribute products to a selected list of partners
	  */
	public int getM_DistributionList_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DistributionList_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getM_DistributionList_ID()));
    }

	/** Set Distribution List Line.
		@param M_DistributionListLine_ID 
		Distribution List Line with Business Partner and Quantity/Percentage
	  */
	public void setM_DistributionListLine_ID (int M_DistributionListLine_ID)
	{
		if (M_DistributionListLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_DistributionListLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_DistributionListLine_ID, Integer.valueOf(M_DistributionListLine_ID));
	}

	/** Get Distribution List Line.
		@return Distribution List Line with Business Partner and Quantity/Percentage
	  */
	public int getM_DistributionListLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_DistributionListLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Minimum Quantity.
		@param MinQty 
		Minimum quantity for the business partner
	  */
	public void setMinQty (BigDecimal MinQty)
	{
		set_Value (COLUMNNAME_MinQty, MinQty);
	}

	/** Get Minimum Quantity.
		@return Minimum quantity for the business partner
	  */
	public BigDecimal getMinQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MinQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Ratio.
		@param Ratio 
		Relative Ratio for Distributions
	  */
	public void setRatio (BigDecimal Ratio)
	{
		set_Value (COLUMNNAME_Ratio, Ratio);
	}

	/** Get Ratio.
		@return Relative Ratio for Distributions
	  */
	public BigDecimal getRatio () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Ratio);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}