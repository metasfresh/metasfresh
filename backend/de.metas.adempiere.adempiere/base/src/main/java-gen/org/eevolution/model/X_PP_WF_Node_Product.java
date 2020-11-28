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
package org.eevolution.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.model.*;
import org.compiere.util.Env;

/** Generated Model for PP_WF_Node_Product
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_PP_WF_Node_Product extends PO implements I_PP_WF_Node_Product, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_PP_WF_Node_Product (Properties ctx, int PP_WF_Node_Product_ID, String trxName)
    {
      super (ctx, PP_WF_Node_Product_ID, trxName);
      /** if (PP_WF_Node_Product_ID == 0)
        {
			setAD_WF_Node_ID (0);
			setEntityType (null);
// U
			setM_Product_ID (0);
			setPP_WF_Node_Product_ID (0);
        } */
    }

    /** Load Constructor */
    public X_PP_WF_Node_Product (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_PP_WF_Node_Product[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_WF_Node getAD_WF_Node() throws RuntimeException
    {
		return (I_AD_WF_Node)MTable.get(getCtx(), I_AD_WF_Node.Table_Name)
			.getPO(getAD_WF_Node_ID(), get_TrxName());	}

	/** Set Node.
		@param AD_WF_Node_ID 
		Workflow Node (activity), step or process
	  */
	public void setAD_WF_Node_ID (int AD_WF_Node_ID)
	{
		if (AD_WF_Node_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_Node_ID, Integer.valueOf(AD_WF_Node_ID));
	}

	/** Get Node.
		@return Workflow Node (activity), step or process
	  */
	public int getAD_WF_Node_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Node_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** ConfigurationLevel AD_Reference_ID=53222 */
	public static final int CONFIGURATIONLEVEL_AD_Reference_ID=53222;
	/** System = S */
	public static final String CONFIGURATIONLEVEL_System = "S";
	/** Client = C */
	public static final String CONFIGURATIONLEVEL_Client = "C";
	/** Organization = O */
	public static final String CONFIGURATIONLEVEL_Organization = "O";
	/** Set Configuration Level.
		@param ConfigurationLevel 
		Configuration Level for this parameter
	  */
	public void setConfigurationLevel (String ConfigurationLevel)
	{

		set_Value (COLUMNNAME_ConfigurationLevel, ConfigurationLevel);
	}

	/** Get Configuration Level.
		@return Configuration Level for this parameter
	  */
	public String getConfigurationLevel () 
	{
		return (String)get_Value(COLUMNNAME_ConfigurationLevel);
	}

	/** EntityType AD_Reference_ID=389 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entity Type.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	public void setEntityType (String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entity Type.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	public String getEntityType () 
	{
		return (String)get_Value(COLUMNNAME_EntityType);
	}

	/** Set Is Subcontracting.
		@param IsSubcontracting Is Subcontracting	  */
	public void setIsSubcontracting (boolean IsSubcontracting)
	{
		set_Value (COLUMNNAME_IsSubcontracting, Boolean.valueOf(IsSubcontracting));
	}

	/** Get Is Subcontracting.
		@return Is Subcontracting	  */
	public boolean isSubcontracting () 
	{
		Object oo = get_Value(COLUMNNAME_IsSubcontracting);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Workflow Node Product.
		@param PP_WF_Node_Product_ID Workflow Node Product	  */
	public void setPP_WF_Node_Product_ID (int PP_WF_Node_Product_ID)
	{
		if (PP_WF_Node_Product_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_PP_WF_Node_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_PP_WF_Node_Product_ID, Integer.valueOf(PP_WF_Node_Product_ID));
	}

	/** Get Workflow Node Product.
		@return Workflow Node Product	  */
	public int getPP_WF_Node_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_PP_WF_Node_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Quantity.
		@param Qty 
		Quantity
	  */
	public void setQty (BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	/** Get Quantity.
		@return Quantity
	  */
	public BigDecimal getQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Qty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}