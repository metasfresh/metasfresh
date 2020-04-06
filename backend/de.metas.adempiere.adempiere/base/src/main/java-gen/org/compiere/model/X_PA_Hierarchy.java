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

/** Generated Model for PA_Hierarchy
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_PA_Hierarchy extends PO implements I_PA_Hierarchy, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_PA_Hierarchy (Properties ctx, int PA_Hierarchy_ID, String trxName)
    {
      super (ctx, PA_Hierarchy_ID, trxName);
      /** if (PA_Hierarchy_ID == 0)
        {
			setAD_Tree_Account_ID (0);
			setAD_Tree_Activity_ID (0);
			setAD_Tree_BPartner_ID (0);
			setAD_Tree_Campaign_ID (0);
			setAD_Tree_Org_ID (0);
			setAD_Tree_Product_ID (0);
			setAD_Tree_Project_ID (0);
			setAD_Tree_SalesRegion_ID (0);
			setName (null);
			setPA_Hierarchy_ID (0);
        } */
    }

    /** Load Constructor */
    public X_PA_Hierarchy (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_PA_Hierarchy[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Tree getAD_Tree_Account() throws RuntimeException
    {
		return (I_AD_Tree)MTable.get(getCtx(), I_AD_Tree.Table_Name)
			.getPO(getAD_Tree_Account_ID(), get_TrxName());	}

	/** Set Account Tree.
		@param AD_Tree_Account_ID 
		Tree for Natural Account Tree
	  */
	public void setAD_Tree_Account_ID (int AD_Tree_Account_ID)
	{
		if (AD_Tree_Account_ID < 1) 
			set_Value (COLUMNNAME_AD_Tree_Account_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Tree_Account_ID, Integer.valueOf(AD_Tree_Account_ID));
	}

	/** Get Account Tree.
		@return Tree for Natural Account Tree
	  */
	public int getAD_Tree_Account_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_Account_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Tree getAD_Tree_Activity() throws RuntimeException
    {
		return (I_AD_Tree)MTable.get(getCtx(), I_AD_Tree.Table_Name)
			.getPO(getAD_Tree_Activity_ID(), get_TrxName());	}

	/** Set Activity Tree.
		@param AD_Tree_Activity_ID 
		Tree to determine activity hierarchy
	  */
	public void setAD_Tree_Activity_ID (int AD_Tree_Activity_ID)
	{
		if (AD_Tree_Activity_ID < 1) 
			set_Value (COLUMNNAME_AD_Tree_Activity_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Tree_Activity_ID, Integer.valueOf(AD_Tree_Activity_ID));
	}

	/** Get Activity Tree.
		@return Tree to determine activity hierarchy
	  */
	public int getAD_Tree_Activity_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_Activity_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Tree getAD_Tree_BPartner() throws RuntimeException
    {
		return (I_AD_Tree)MTable.get(getCtx(), I_AD_Tree.Table_Name)
			.getPO(getAD_Tree_BPartner_ID(), get_TrxName());	}

	/** Set BPartner Tree.
		@param AD_Tree_BPartner_ID 
		Tree to determine business partner hierarchy
	  */
	public void setAD_Tree_BPartner_ID (int AD_Tree_BPartner_ID)
	{
		if (AD_Tree_BPartner_ID < 1) 
			set_Value (COLUMNNAME_AD_Tree_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Tree_BPartner_ID, Integer.valueOf(AD_Tree_BPartner_ID));
	}

	/** Get BPartner Tree.
		@return Tree to determine business partner hierarchy
	  */
	public int getAD_Tree_BPartner_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Tree getAD_Tree_Campaign() throws RuntimeException
    {
		return (I_AD_Tree)MTable.get(getCtx(), I_AD_Tree.Table_Name)
			.getPO(getAD_Tree_Campaign_ID(), get_TrxName());	}

	/** Set Campaign Tree.
		@param AD_Tree_Campaign_ID 
		Tree to determine marketing campaign hierarchy
	  */
	public void setAD_Tree_Campaign_ID (int AD_Tree_Campaign_ID)
	{
		if (AD_Tree_Campaign_ID < 1) 
			set_Value (COLUMNNAME_AD_Tree_Campaign_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Tree_Campaign_ID, Integer.valueOf(AD_Tree_Campaign_ID));
	}

	/** Get Campaign Tree.
		@return Tree to determine marketing campaign hierarchy
	  */
	public int getAD_Tree_Campaign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_Campaign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Tree getAD_Tree_Org() throws RuntimeException
    {
		return (I_AD_Tree)MTable.get(getCtx(), I_AD_Tree.Table_Name)
			.getPO(getAD_Tree_Org_ID(), get_TrxName());	}

	/** Set Organization Tree.
		@param AD_Tree_Org_ID 
		Tree to determine organizational hierarchy
	  */
	public void setAD_Tree_Org_ID (int AD_Tree_Org_ID)
	{
		if (AD_Tree_Org_ID < 1) 
			set_Value (COLUMNNAME_AD_Tree_Org_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Tree_Org_ID, Integer.valueOf(AD_Tree_Org_ID));
	}

	/** Get Organization Tree.
		@return Tree to determine organizational hierarchy
	  */
	public int getAD_Tree_Org_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_Org_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Tree getAD_Tree_Product() throws RuntimeException
    {
		return (I_AD_Tree)MTable.get(getCtx(), I_AD_Tree.Table_Name)
			.getPO(getAD_Tree_Product_ID(), get_TrxName());	}

	/** Set Product Tree.
		@param AD_Tree_Product_ID 
		Tree to determine product hierarchy
	  */
	public void setAD_Tree_Product_ID (int AD_Tree_Product_ID)
	{
		if (AD_Tree_Product_ID < 1) 
			set_Value (COLUMNNAME_AD_Tree_Product_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Tree_Product_ID, Integer.valueOf(AD_Tree_Product_ID));
	}

	/** Get Product Tree.
		@return Tree to determine product hierarchy
	  */
	public int getAD_Tree_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Tree getAD_Tree_Project() throws RuntimeException
    {
		return (I_AD_Tree)MTable.get(getCtx(), I_AD_Tree.Table_Name)
			.getPO(getAD_Tree_Project_ID(), get_TrxName());	}

	/** Set Project Tree.
		@param AD_Tree_Project_ID 
		Tree to determine project hierarchy
	  */
	public void setAD_Tree_Project_ID (int AD_Tree_Project_ID)
	{
		if (AD_Tree_Project_ID < 1) 
			set_Value (COLUMNNAME_AD_Tree_Project_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Tree_Project_ID, Integer.valueOf(AD_Tree_Project_ID));
	}

	/** Get Project Tree.
		@return Tree to determine project hierarchy
	  */
	public int getAD_Tree_Project_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_Project_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_Tree getAD_Tree_SalesRegion() throws RuntimeException
    {
		return (I_AD_Tree)MTable.get(getCtx(), I_AD_Tree.Table_Name)
			.getPO(getAD_Tree_SalesRegion_ID(), get_TrxName());	}

	/** Set Sales Region Tree.
		@param AD_Tree_SalesRegion_ID 
		Tree to determine sales regional hierarchy
	  */
	public void setAD_Tree_SalesRegion_ID (int AD_Tree_SalesRegion_ID)
	{
		if (AD_Tree_SalesRegion_ID < 1) 
			set_Value (COLUMNNAME_AD_Tree_SalesRegion_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Tree_SalesRegion_ID, Integer.valueOf(AD_Tree_SalesRegion_ID));
	}

	/** Get Sales Region Tree.
		@return Tree to determine sales regional hierarchy
	  */
	public int getAD_Tree_SalesRegion_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_SalesRegion_ID);
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

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Reporting Hierarchy.
		@param PA_Hierarchy_ID 
		Optional Reporting Hierarchy - If not selected the default hierarchy trees are used.
	  */
	public void setPA_Hierarchy_ID (int PA_Hierarchy_ID)
	{
		if (PA_Hierarchy_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PA_Hierarchy_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PA_Hierarchy_ID, Integer.valueOf(PA_Hierarchy_ID));
	}

	/** Get Reporting Hierarchy.
		@return Optional Reporting Hierarchy - If not selected the default hierarchy trees are used.
	  */
	public int getPA_Hierarchy_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_Hierarchy_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}