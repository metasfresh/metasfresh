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

import org.adempiere.model.I_M_PackagingContainer;
import org.compiere.util.Env;

/** Generated Model for M_PackagingTreeItem
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_M_PackagingTreeItem extends PO implements I_M_PackagingTreeItem, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20130130L;

    /** Standard Constructor */
    public X_M_PackagingTreeItem (Properties ctx, int M_PackagingTreeItem_ID, String trxName)
    {
      super (ctx, M_PackagingTreeItem_ID, trxName);
      /** if (M_PackagingTreeItem_ID == 0)
        {
			setM_PackagingTree_ID (0);
			setM_PackagingTreeItem_ID (0);
			setWeight (Env.ZERO);
// 0
        } */
    }

    /** Load Constructor */
    public X_M_PackagingTreeItem (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    @Override
	protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    @Override
	protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    @Override
	public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_M_PackagingTreeItem[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set GroupID.
		@param GroupID GroupID	  */
	@Override
	public void setGroupID (int GroupID)
	{
		set_Value (COLUMNNAME_GroupID, Integer.valueOf(GroupID));
	}

	/** Get GroupID.
		@return GroupID	  */
	@Override
	public int getGroupID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_GroupID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public I_M_PackageTree getM_PackageTree() throws RuntimeException
    {
		return (I_M_PackageTree)MTable.get(getCtx(), I_M_PackageTree.Table_Name)
			.getPO(getM_PackageTree_ID(), get_TrxName());	}

	/** Set Virtual Package.
		@param M_PackageTree_ID Virtual Package	  */
	@Override
	public void setM_PackageTree_ID (int M_PackageTree_ID)
	{
		if (M_PackageTree_ID < 1) 
			set_Value (COLUMNNAME_M_PackageTree_ID, null);
		else 
			set_Value (COLUMNNAME_M_PackageTree_ID, Integer.valueOf(M_PackageTree_ID));
	}

	/** Get Virtual Package.
		@return Virtual Package	  */
	@Override
	public int getM_PackageTree_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PackageTree_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public I_M_PackagingContainer getM_PackagingContainer() throws RuntimeException
    {
		return (I_M_PackagingContainer)MTable.get(getCtx(), I_M_PackagingContainer.Table_Name)
			.getPO(getM_PackagingContainer_ID(), get_TrxName());	}

	/** Set Verpackung.
		@param M_PackagingContainer_ID Verpackung	  */
	@Override
	public void setM_PackagingContainer_ID (int M_PackagingContainer_ID)
	{
		if (M_PackagingContainer_ID < 1) 
			set_Value (COLUMNNAME_M_PackagingContainer_ID, null);
		else 
			set_Value (COLUMNNAME_M_PackagingContainer_ID, Integer.valueOf(M_PackagingContainer_ID));
	}

	/** Get Verpackung.
		@return Verpackung	  */
	@Override
	public int getM_PackagingContainer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PackagingContainer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public I_M_PackagingTree getM_PackagingTree() throws RuntimeException
    {
		return (I_M_PackagingTree)MTable.get(getCtx(), I_M_PackagingTree.Table_Name)
			.getPO(getM_PackagingTree_ID(), get_TrxName());	}

	/** Set Packaging Tree.
		@param M_PackagingTree_ID Packaging Tree	  */
	@Override
	public void setM_PackagingTree_ID (int M_PackagingTree_ID)
	{
		if (M_PackagingTree_ID < 1) 
			set_Value (COLUMNNAME_M_PackagingTree_ID, null);
		else 
			set_Value (COLUMNNAME_M_PackagingTree_ID, Integer.valueOf(M_PackagingTree_ID));
	}

	/** Get Packaging Tree.
		@return Packaging Tree	  */
	@Override
	public int getM_PackagingTree_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PackagingTree_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Packaging Tree Item.
		@param M_PackagingTreeItem_ID Packaging Tree Item	  */
	@Override
	public void setM_PackagingTreeItem_ID (int M_PackagingTreeItem_ID)
	{
		if (M_PackagingTreeItem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_PackagingTreeItem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_PackagingTreeItem_ID, Integer.valueOf(M_PackagingTreeItem_ID));
	}

	/** Get Packaging Tree Item.
		@return Packaging Tree Item	  */
	@Override
	public int getM_PackagingTreeItem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_PackagingTreeItem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
    {
		return (org.compiere.model.I_M_Product)MTable.get(getCtx(), org.compiere.model.I_M_Product.Table_Name)
			.getPO(getM_Product_ID(), get_TrxName());	}

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	@Override
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Menge.
		@param Qty 
		Menge
	  */
	@Override
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Menge.
		@return Menge
	  */
	@Override
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	@Override
	public I_M_PackagingTreeItem getRef_M_PackagingTreeItem() throws RuntimeException
    {
		return (I_M_PackagingTreeItem)MTable.get(getCtx(), I_M_PackagingTreeItem.Table_Name)
			.getPO(getRef_M_PackagingTreeItem_ID(), get_TrxName());	}

	/** Set Ref Packaging Tree Item.
		@param Ref_M_PackagingTreeItem_ID Ref Packaging Tree Item	  */
	@Override
	public void setRef_M_PackagingTreeItem_ID (int Ref_M_PackagingTreeItem_ID)
	{
		if (Ref_M_PackagingTreeItem_ID < 1) 
			set_Value (COLUMNNAME_Ref_M_PackagingTreeItem_ID, null);
		else 
			set_Value (COLUMNNAME_Ref_M_PackagingTreeItem_ID, Integer.valueOf(Ref_M_PackagingTreeItem_ID));
	}

	/** Get Ref Packaging Tree Item.
		@return Ref Packaging Tree Item	  */
	@Override
	public int getRef_M_PackagingTreeItem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ref_M_PackagingTreeItem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Status AD_Reference_ID=540165 */
	public static final int STATUS_AD_Reference_ID=540165;
	/** Ready = R */
	public static final String STATUS_Ready = "R";
	/** Packed = P */
	public static final String STATUS_Packed = "P";
	/** Partially Packed = PP */
	public static final String STATUS_PartiallyPacked = "PP";
	/** UnPacked = UP */
	public static final String STATUS_UnPacked = "UP";
	/** Open = O */
	public static final String STATUS_Open = "O";
	/** Set Status.
		@param Status Status	  */
	@Override
	public void setStatus (String Status)
	{

		set_Value (COLUMNNAME_Status, Status);
	}

	/** Get Status.
		@return Status	  */
	@Override
	public String getStatus () 
	{
		return (String)get_Value(COLUMNNAME_Status);
	}

	/** Type AD_Reference_ID=540166 */
	public static final int TYPE_AD_Reference_ID=540166;
	/** Box = B */
	public static final String TYPE_Box = "B";
	/** PackedItem = PI */
	public static final String TYPE_PackedItem = "PI";
	/** UnPackedItem = UI */
	public static final String TYPE_UnPackedItem = "UI";
	/** AvailableBox = AB */
	public static final String TYPE_AvailableBox = "AB";
	/** NonItem = NI */
	public static final String TYPE_NonItem = "NI";
	/** Set Art.
		@param Type Art	  */
	@Override
	public void setType (String Type)
	{

		set_Value (COLUMNNAME_Type, Type);
	}

	/** Get Art.
		@return Art	  */
	@Override
	public String getType () 
	{
		return (String)get_Value(COLUMNNAME_Type);
	}

	/** Set Gewicht.
		@param Weight 
		Gewicht eines Produktes
	  */
	@Override
	public void setWeight (BigDecimal Weight)
	{
		set_Value (COLUMNNAME_Weight, Weight);
	}

	/** Get Gewicht.
		@return Gewicht eines Produktes
	  */
	@Override
	public BigDecimal getWeight () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Weight);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}
