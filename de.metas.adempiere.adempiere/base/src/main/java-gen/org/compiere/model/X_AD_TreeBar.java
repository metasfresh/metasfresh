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

/** Generated Model for AD_TreeBar
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_TreeBar extends PO implements I_AD_TreeBar, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_TreeBar (Properties ctx, int AD_TreeBar_ID, String trxName)
    {
      super (ctx, AD_TreeBar_ID, trxName);
      /** if (AD_TreeBar_ID == 0)
        {
			setAD_Tree_ID (0);
			setAD_User_ID (0);
			setNode_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_TreeBar (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_AD_TreeBar[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Tree getAD_Tree() throws RuntimeException
    {
		return (I_AD_Tree)MTable.get(getCtx(), I_AD_Tree.Table_Name)
			.getPO(getAD_Tree_ID(), get_TrxName());	}

	/** Set Tree.
		@param AD_Tree_ID 
		Identifies a Tree
	  */
	public void setAD_Tree_ID (int AD_Tree_ID)
	{
		if (AD_Tree_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Tree_ID, Integer.valueOf(AD_Tree_ID));
	}

	/** Get Tree.
		@return Identifies a Tree
	  */
	public int getAD_Tree_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Tree_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_User getAD_User() throws RuntimeException
    {
		return (I_AD_User)MTable.get(getCtx(), I_AD_User.Table_Name)
			.getPO(getAD_User_ID(), get_TrxName());	}

	/** Set User/Contact.
		@param AD_User_ID 
		User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get User/Contact.
		@return User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Node_ID.
		@param Node_ID Node_ID	  */
	public void setNode_ID (int Node_ID)
	{
		if (Node_ID < 0) 
			set_Value (COLUMNNAME_Node_ID, null);
		else 
			set_Value (COLUMNNAME_Node_ID, Integer.valueOf(Node_ID));
	}

	/** Get Node_ID.
		@return Node_ID	  */
	public int getNode_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Node_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getNode_ID()));
    }
}