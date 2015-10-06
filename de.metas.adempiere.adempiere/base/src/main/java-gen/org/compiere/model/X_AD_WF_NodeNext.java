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

/** Generated Model for AD_WF_NodeNext
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_WF_NodeNext extends PO implements I_AD_WF_NodeNext, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_WF_NodeNext (Properties ctx, int AD_WF_NodeNext_ID, String trxName)
    {
      super (ctx, AD_WF_NodeNext_ID, trxName);
      /** if (AD_WF_NodeNext_ID == 0)
        {
			setAD_WF_Next_ID (0);
			setAD_WF_Node_ID (0);
			setAD_WF_NodeNext_ID (0);
			setEntityType (null);
// U
			setIsStdUserWorkflow (false);
			setSeqNo (0);
// 10
        } */
    }

    /** Load Constructor */
    public X_AD_WF_NodeNext (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_WF_NodeNext[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_WF_Node getAD_WF_Next() throws RuntimeException
    {
		return (I_AD_WF_Node)MTable.get(getCtx(), I_AD_WF_Node.Table_Name)
			.getPO(getAD_WF_Next_ID(), get_TrxName());	}

	/** Set Next Node.
		@param AD_WF_Next_ID 
		Next Node in workflow
	  */
	public void setAD_WF_Next_ID (int AD_WF_Next_ID)
	{
		if (AD_WF_Next_ID < 1) 
			set_Value (COLUMNNAME_AD_WF_Next_ID, null);
		else 
			set_Value (COLUMNNAME_AD_WF_Next_ID, Integer.valueOf(AD_WF_Next_ID));
	}

	/** Get Next Node.
		@return Next Node in workflow
	  */
	public int getAD_WF_Next_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_Next_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getAD_WF_Node_ID()));
    }

	/** Set Node Transition.
		@param AD_WF_NodeNext_ID 
		Workflow Node Transition
	  */
	public void setAD_WF_NodeNext_ID (int AD_WF_NodeNext_ID)
	{
		if (AD_WF_NodeNext_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_WF_NodeNext_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_WF_NodeNext_ID, Integer.valueOf(AD_WF_NodeNext_ID));
	}

	/** Get Node Transition.
		@return Workflow Node Transition
	  */
	public int getAD_WF_NodeNext_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_WF_NodeNext_ID);
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

	/** Set Std User Workflow.
		@param IsStdUserWorkflow 
		Standard Manual User Approval Workflow
	  */
	public void setIsStdUserWorkflow (boolean IsStdUserWorkflow)
	{
		set_Value (COLUMNNAME_IsStdUserWorkflow, Boolean.valueOf(IsStdUserWorkflow));
	}

	/** Get Std User Workflow.
		@return Standard Manual User Approval Workflow
	  */
	public boolean isStdUserWorkflow () 
	{
		Object oo = get_Value(COLUMNNAME_IsStdUserWorkflow);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Transition Code.
		@param TransitionCode 
		Code resulting in TRUE of FALSE
	  */
	public void setTransitionCode (String TransitionCode)
	{
		set_Value (COLUMNNAME_TransitionCode, TransitionCode);
	}

	/** Get Transition Code.
		@return Code resulting in TRUE of FALSE
	  */
	public String getTransitionCode () 
	{
		return (String)get_Value(COLUMNNAME_TransitionCode);
	}
}