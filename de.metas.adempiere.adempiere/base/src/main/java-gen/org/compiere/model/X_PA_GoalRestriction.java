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

/** Generated Model for PA_GoalRestriction
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_PA_GoalRestriction extends PO implements I_PA_GoalRestriction, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_PA_GoalRestriction (Properties ctx, int PA_GoalRestriction_ID, String trxName)
    {
      super (ctx, PA_GoalRestriction_ID, trxName);
      /** if (PA_GoalRestriction_ID == 0)
        {
			setGoalRestrictionType (null);
			setName (null);
			setPA_Goal_ID (0);
			setPA_GoalRestriction_ID (0);
        } */
    }

    /** Load Constructor */
    public X_PA_GoalRestriction (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 6 - System - Client 
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
      StringBuffer sb = new StringBuffer ("X_PA_GoalRestriction[")
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

	public I_C_BP_Group getC_BP_Group() throws RuntimeException
    {
		return (I_C_BP_Group)MTable.get(getCtx(), I_C_BP_Group.Table_Name)
			.getPO(getC_BP_Group_ID(), get_TrxName());	}

	/** Set Business Partner Group.
		@param C_BP_Group_ID 
		Business Partner Group
	  */
	public void setC_BP_Group_ID (int C_BP_Group_ID)
	{
		if (C_BP_Group_ID < 1) 
			set_Value (COLUMNNAME_C_BP_Group_ID, null);
		else 
			set_Value (COLUMNNAME_C_BP_Group_ID, Integer.valueOf(C_BP_Group_ID));
	}

	/** Get Business Partner Group.
		@return Business Partner Group
	  */
	public int getC_BP_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** GoalRestrictionType AD_Reference_ID=368 */
	public static final int GOALRESTRICTIONTYPE_AD_Reference_ID=368;
	/** Organization = O */
	public static final String GOALRESTRICTIONTYPE_Organization = "O";
	/** Business Partner = B */
	public static final String GOALRESTRICTIONTYPE_BusinessPartner = "B";
	/** Product = P */
	public static final String GOALRESTRICTIONTYPE_Product = "P";
	/** Bus.Partner Group = G */
	public static final String GOALRESTRICTIONTYPE_BusPartnerGroup = "G";
	/** Product Category = C */
	public static final String GOALRESTRICTIONTYPE_ProductCategory = "C";
	/** Set Restriction Type.
		@param GoalRestrictionType 
		Goal Restriction Type
	  */
	public void setGoalRestrictionType (String GoalRestrictionType)
	{

		set_Value (COLUMNNAME_GoalRestrictionType, GoalRestrictionType);
	}

	/** Get Restriction Type.
		@return Goal Restriction Type
	  */
	public String getGoalRestrictionType () 
	{
		return (String)get_Value(COLUMNNAME_GoalRestrictionType);
	}

	public I_M_Product_Category getM_Product_Category() throws RuntimeException
    {
		return (I_M_Product_Category)MTable.get(getCtx(), I_M_Product_Category.Table_Name)
			.getPO(getM_Product_Category_ID(), get_TrxName());	}

	/** Set Product Category.
		@param M_Product_Category_ID 
		Category of a Product
	  */
	public void setM_Product_Category_ID (int M_Product_Category_ID)
	{
		if (M_Product_Category_ID < 1) 
			set_Value (COLUMNNAME_M_Product_Category_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_Category_ID, Integer.valueOf(M_Product_Category_ID));
	}

	/** Get Product Category.
		@return Category of a Product
	  */
	public int getM_Product_Category_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_Category_ID);
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

	/** Set Organization.
		@param Org_ID 
		Organizational entity within client
	  */
	public void setOrg_ID (int Org_ID)
	{
		if (Org_ID < 1) 
			set_Value (COLUMNNAME_Org_ID, null);
		else 
			set_Value (COLUMNNAME_Org_ID, Integer.valueOf(Org_ID));
	}

	/** Get Organization.
		@return Organizational entity within client
	  */
	public int getOrg_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Org_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_PA_Goal getPA_Goal() throws RuntimeException
    {
		return (I_PA_Goal)MTable.get(getCtx(), I_PA_Goal.Table_Name)
			.getPO(getPA_Goal_ID(), get_TrxName());	}

	/** Set Goal.
		@param PA_Goal_ID 
		Performance Goal
	  */
	public void setPA_Goal_ID (int PA_Goal_ID)
	{
		if (PA_Goal_ID < 1) 
			set_Value (COLUMNNAME_PA_Goal_ID, null);
		else 
			set_Value (COLUMNNAME_PA_Goal_ID, Integer.valueOf(PA_Goal_ID));
	}

	/** Get Goal.
		@return Performance Goal
	  */
	public int getPA_Goal_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_Goal_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Goal Restriction.
		@param PA_GoalRestriction_ID 
		Performance Goal Restriction
	  */
	public void setPA_GoalRestriction_ID (int PA_GoalRestriction_ID)
	{
		if (PA_GoalRestriction_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PA_GoalRestriction_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PA_GoalRestriction_ID, Integer.valueOf(PA_GoalRestriction_ID));
	}

	/** Get Goal Restriction.
		@return Performance Goal Restriction
	  */
	public int getPA_GoalRestriction_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PA_GoalRestriction_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}