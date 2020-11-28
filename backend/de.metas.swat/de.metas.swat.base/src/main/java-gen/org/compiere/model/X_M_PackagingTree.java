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

/** Generated Model for M_PackagingTree
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_M_PackagingTree extends PO implements I_M_PackagingTree, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130218L;

    /** Standard Constructor */
    public X_M_PackagingTree (Properties ctx, int M_PackagingTree_ID, String trxName)
    {
      super (ctx, M_PackagingTree_ID, trxName);
      /** if (M_PackagingTree_ID == 0)
        {
			setC_BPartner_ID (0);
			setM_PackagingTree_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_PackagingTree (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_M_PackagingTree[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	public org.compiere.model.I_C_Order getC_Order() throws RuntimeException
    {
		return (org.compiere.model.I_C_Order)MTable.get(getCtx(), org.compiere.model.I_C_Order.Table_Name)
			.getPO(getC_Order_ID(), get_TrxName());	}

	/** Set Auftrag.
		@param C_Order_ID 
		Order
	  */
	public void setC_Order_ID (int C_Order_ID)
	{
		if (C_Order_ID < 1) 
			set_Value (COLUMNNAME_C_Order_ID, null);
		else 
			set_Value (COLUMNNAME_C_Order_ID, Integer.valueOf(C_Order_ID));
	}

	/** Get Auftrag.
		@return Order
	  */
	public int getC_Order_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Order_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Packaging Tree.
		@param M_PackagingTree_ID Packaging Tree	  */
	public void setM_PackagingTree_ID (int M_PackagingTree_ID)
	{
		if (M_PackagingTree_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PackagingTree_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PackagingTree_ID, Integer.valueOf(M_PackagingTree_ID));
	}

	/** Get Packaging Tree.
		@return Packaging Tree	  */
	public int getM_PackagingTree_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PackagingTree_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_M_Warehouse getM_Warehouse_Dest() throws RuntimeException
    {
		return (org.compiere.model.I_M_Warehouse)MTable.get(getCtx(), org.compiere.model.I_M_Warehouse.Table_Name)
			.getPO(getM_Warehouse_Dest_ID(), get_TrxName());	}

	/** Set Ziel-Lager.
		@param M_Warehouse_Dest_ID Ziel-Lager	  */
	public void setM_Warehouse_Dest_ID (int M_Warehouse_Dest_ID)
	{
		if (M_Warehouse_Dest_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_Dest_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_Dest_ID, Integer.valueOf(M_Warehouse_Dest_ID));
	}

	/** Get Ziel-Lager.
		@return Ziel-Lager	  */
	public int getM_Warehouse_Dest_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Warehouse_Dest_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
	
	/**
	 * Set Verarbeitet.
	 * 
	 * @param Processed
	 *            Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 */
	public void setProcessed(boolean Processed)
	{
		set_Value(COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/**
	 * Get Verarbeitet.
	 * 
	 * @return Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 */
	public boolean isProcessed()
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
}
