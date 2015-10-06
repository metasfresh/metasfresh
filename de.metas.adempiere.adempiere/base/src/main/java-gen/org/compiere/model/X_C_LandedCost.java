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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for C_LandedCost
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_LandedCost extends PO implements I_C_LandedCost, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_C_LandedCost (Properties ctx, int C_LandedCost_ID, String trxName)
    {
      super (ctx, C_LandedCost_ID, trxName);
      /** if (C_LandedCost_ID == 0)
        {
			setC_InvoiceLine_ID (0);
			setC_LandedCost_ID (0);
			setLandedCostDistribution (null);
// Q
			setM_CostElement_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_LandedCost (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_LandedCost[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_C_InvoiceLine getC_InvoiceLine() throws RuntimeException
    {
		return (I_C_InvoiceLine)MTable.get(getCtx(), I_C_InvoiceLine.Table_Name)
			.getPO(getC_InvoiceLine_ID(), get_TrxName());	}

	/** Set Invoice Line.
		@param C_InvoiceLine_ID 
		Invoice Detail Line
	  */
	public void setC_InvoiceLine_ID (int C_InvoiceLine_ID)
	{
		if (C_InvoiceLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_InvoiceLine_ID, Integer.valueOf(C_InvoiceLine_ID));
	}

	/** Get Invoice Line.
		@return Invoice Detail Line
	  */
	public int getC_InvoiceLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_InvoiceLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getC_InvoiceLine_ID()));
    }

	/** Set Landed Cost.
		@param C_LandedCost_ID 
		Landed cost to be allocated to material receipts
	  */
	public void setC_LandedCost_ID (int C_LandedCost_ID)
	{
		if (C_LandedCost_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_LandedCost_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_LandedCost_ID, Integer.valueOf(C_LandedCost_ID));
	}

	/** Get Landed Cost.
		@return Landed cost to be allocated to material receipts
	  */
	public int getC_LandedCost_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_LandedCost_ID);
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

	/** LandedCostDistribution AD_Reference_ID=339 */
	public static final int LANDEDCOSTDISTRIBUTION_AD_Reference_ID=339;
	/** Quantity = Q */
	public static final String LANDEDCOSTDISTRIBUTION_Quantity = "Q";
	/** Line = L */
	public static final String LANDEDCOSTDISTRIBUTION_Line = "L";
	/** Volume = V */
	public static final String LANDEDCOSTDISTRIBUTION_Volume = "V";
	/** Weight = W */
	public static final String LANDEDCOSTDISTRIBUTION_Weight = "W";
	/** Costs = C */
	public static final String LANDEDCOSTDISTRIBUTION_Costs = "C";
	/** Set Cost Distribution.
		@param LandedCostDistribution 
		Landed Cost Distribution
	  */
	public void setLandedCostDistribution (String LandedCostDistribution)
	{

		set_Value (COLUMNNAME_LandedCostDistribution, LandedCostDistribution);
	}

	/** Get Cost Distribution.
		@return Landed Cost Distribution
	  */
	public String getLandedCostDistribution () 
	{
		return (String)get_Value(COLUMNNAME_LandedCostDistribution);
	}

	public I_M_CostElement getM_CostElement() throws RuntimeException
    {
		return (I_M_CostElement)MTable.get(getCtx(), I_M_CostElement.Table_Name)
			.getPO(getM_CostElement_ID(), get_TrxName());	}

	/** Set Cost Element.
		@param M_CostElement_ID 
		Product Cost Element
	  */
	public void setM_CostElement_ID (int M_CostElement_ID)
	{
		if (M_CostElement_ID < 1) 
			set_Value (COLUMNNAME_M_CostElement_ID, null);
		else 
			set_Value (COLUMNNAME_M_CostElement_ID, Integer.valueOf(M_CostElement_ID));
	}

	/** Get Cost Element.
		@return Product Cost Element
	  */
	public int getM_CostElement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_CostElement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_InOut getM_InOut() throws RuntimeException
    {
		return (I_M_InOut)MTable.get(getCtx(), I_M_InOut.Table_Name)
			.getPO(getM_InOut_ID(), get_TrxName());	}

	/** Set Shipment/Receipt.
		@param M_InOut_ID 
		Material Shipment Document
	  */
	public void setM_InOut_ID (int M_InOut_ID)
	{
		if (M_InOut_ID < 1) 
			set_Value (COLUMNNAME_M_InOut_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOut_ID, Integer.valueOf(M_InOut_ID));
	}

	/** Get Shipment/Receipt.
		@return Material Shipment Document
	  */
	public int getM_InOut_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOut_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_InOutLine getM_InOutLine() throws RuntimeException
    {
		return (I_M_InOutLine)MTable.get(getCtx(), I_M_InOutLine.Table_Name)
			.getPO(getM_InOutLine_ID(), get_TrxName());	}

	/** Set Shipment/Receipt Line.
		@param M_InOutLine_ID 
		Line on Shipment or Receipt document
	  */
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_Value (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

	/** Get Shipment/Receipt Line.
		@return Line on Shipment or Receipt document
	  */
	public int getM_InOutLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_InOutLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_M_Product getM_Product() throws RuntimeException
    {
		return (I_M_Product)MTable.get(getCtx(), I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Product.
		@param M_Product_ID 
		Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Product.
		@return Product, Service, Item
	  */
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Process Now.
		@param Processing Process Now	  */
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	/** Get Process Now.
		@return Process Now	  */
	public boolean isProcessing () 
	{
		Object oo = get_Value(COLUMNNAME_Processing);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}
}