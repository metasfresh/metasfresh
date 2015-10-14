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
package de.metas.callcenter.model;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Properties;

import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_Persistent;
import org.compiere.model.I_R_Group;
import org.compiere.model.I_R_Status;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for RV_R_Group_Prospect
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_RV_R_Group_Prospect extends PO implements I_RV_R_Group_Prospect, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20091023L;

    /** Standard Constructor */
    public X_RV_R_Group_Prospect (Properties ctx, int RV_R_Group_Prospect_ID, String trxName)
    {
      super (ctx, RV_R_Group_Prospect_ID, trxName);
      /** if (RV_R_Group_Prospect_ID == 0)
        {
			setAD_User_ID (0);
// -1
			setC_BPartner_ID (0);
			setR_Group_ID (0);
        } */
    }

    /** Load Constructor */
    public X_RV_R_Group_Prospect (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_RV_R_Group_Prospect[")
        .append(get_ID()).append("]");
      return sb.toString();
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
			set_Value (COLUMNNAME_AD_User_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
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

	/** Set BP Search Key.
		@param BPValue 
		Business Partner Key Value
	  */
	public void setBPValue (String BPValue)
	{
		set_ValueNoCheck (COLUMNNAME_BPValue, BPValue);
	}

	/** Get BP Search Key.
		@return Business Partner Key Value
	  */
	public String getBPValue () 
	{
		return (String)get_Value(COLUMNNAME_BPValue);
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
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
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

	/** Set Comments.
		@param Comments 
		Comments or additional information
	  */
	public void setComments (String Comments)
	{
		set_ValueNoCheck (COLUMNNAME_Comments, Comments);
	}

	/** Get Comments.
		@return Comments or additional information
	  */
	public String getComments () 
	{
		return (String)get_Value(COLUMNNAME_Comments);
	}

	/** Set Date next action.
		@param DateNextAction 
		Date that this request should be acted on
	  */
	public void setDateNextAction (Timestamp DateNextAction)
	{
		set_ValueNoCheck (COLUMNNAME_DateNextAction, DateNextAction);
	}

	/** Get Date next action.
		@return Date that this request should be acted on
	  */
	public Timestamp getDateNextAction () 
	{
		return (Timestamp)get_Value(COLUMNNAME_DateNextAction);
	}

	/** Set Closed Status.
		@param IsClosed 
		The status is closed
	  */
	public void setIsClosed (String IsClosed)
	{
		set_Value (COLUMNNAME_IsClosed, IsClosed);
	}

	/** Get Closed Status.
		@return The status is closed
	  */
	public String getIsClosed () 
	{
		return (String)get_Value(COLUMNNAME_IsClosed);
	}

	/** Set Final Close.
		@param IsFinalClose 
		Entries with Final Close cannot be re-opened
	  */
	public void setIsFinalClose (String IsFinalClose)
	{
		set_Value (COLUMNNAME_IsFinalClose, IsFinalClose);
	}

	/** Get Final Close.
		@return Entries with Final Close cannot be re-opened
	  */
	public String getIsFinalClose () 
	{
		return (String)get_Value(COLUMNNAME_IsFinalClose);
	}

	/** Set Locked.
		@param Locked 
		Whether the terminal is locked
	  */
	public void setLocked (boolean Locked)
	{
		set_Value (COLUMNNAME_Locked, Boolean.valueOf(Locked));
	}

	/** Get Locked.
		@return Whether the terminal is locked
	  */
	public boolean isLocked () 
	{
		Object oo = get_Value(COLUMNNAME_Locked);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	public I_AD_User getLocke() throws RuntimeException
    {
		return (I_AD_User)MTable.get(getCtx(), I_AD_User.Table_Name)
			.getPO(getLockedBy(), get_TrxName());	}

	/** Set Locked By.
		@param LockedBy 
		User who locked this record
	  */
	public void setLockedBy (int LockedBy)
	{
		set_Value (COLUMNNAME_LockedBy, Integer.valueOf(LockedBy));
	}

	/** Get Locked By.
		@return User who locked this record
	  */
	public int getLockedBy () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LockedBy);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Locked Date.
		@param LockedDate 
		Date when this record was locked
	  */
	public void setLockedDate (Timestamp LockedDate)
	{
		set_Value (COLUMNNAME_LockedDate, LockedDate);
	}

	/** Get Locked Date.
		@return Date when this record was locked
	  */
	public Timestamp getLockedDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_LockedDate);
	}

	/** Set Phone.
		@param Phone 
		Identifies a telephone number
	  */
	public void setPhone (String Phone)
	{
		set_Value (COLUMNNAME_Phone, Phone);
	}

	/** Get Phone.
		@return Identifies a telephone number
	  */
	public String getPhone () 
	{
		return (String)get_Value(COLUMNNAME_Phone);
	}

	public I_R_Group getR_Group() throws RuntimeException
    {
		return (I_R_Group)MTable.get(getCtx(), I_R_Group.Table_Name)
			.getPO(getR_Group_ID(), get_TrxName());	}

	/** Set Group.
		@param R_Group_ID 
		Request Group
	  */
	public void setR_Group_ID (int R_Group_ID)
	{
		if (R_Group_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_R_Group_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_R_Group_ID, Integer.valueOf(R_Group_ID));
	}

	/** Get Group.
		@return Request Group
	  */
	public int getR_Group_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Group_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_R_Request getR_Request() throws RuntimeException
    {
		return (I_R_Request)MTable.get(getCtx(), I_R_Request.Table_Name)
			.getPO(getR_Request_ID(), get_TrxName());	}

	/** Set Request.
		@param R_Request_ID 
		Request from a Business Partner or Prospect
	  */
	public void setR_Request_ID (int R_Request_ID)
	{
		if (R_Request_ID < 1) 
			set_Value (COLUMNNAME_R_Request_ID, null);
		else 
			set_Value (COLUMNNAME_R_Request_ID, Integer.valueOf(R_Request_ID));
	}

	/** Get Request.
		@return Request from a Business Partner or Prospect
	  */
	public int getR_Request_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Request_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_R_Status getR_Status() throws RuntimeException
    {
		return (I_R_Status)MTable.get(getCtx(), I_R_Status.Table_Name)
			.getPO(getR_Status_ID(), get_TrxName());	}

	/** Set Status.
		@param R_Status_ID 
		Request Status
	  */
	public void setR_Status_ID (int R_Status_ID)
	{
		if (R_Status_ID < 1) 
			set_Value (COLUMNNAME_R_Status_ID, null);
		else 
			set_Value (COLUMNNAME_R_Status_ID, Integer.valueOf(R_Status_ID));
	}

	/** Get Status.
		@return Request Status
	  */
	public int getR_Status_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_Status_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Request Status.
		@param R_Status_Value Request Status	  */
	public void setR_Status_Value (String R_Status_Value)
	{
		set_Value (COLUMNNAME_R_Status_Value, R_Status_Value);
	}

	/** Get Request Status.
		@return Request Status	  */
	public String getR_Status_Value () 
	{
		return (String)get_Value(COLUMNNAME_R_Status_Value);
	}

	public I_AD_User getSalesRep() throws RuntimeException
    {
		return (I_AD_User)MTable.get(getCtx(), I_AD_User.Table_Name)
			.getPO(getSalesRep_ID(), get_TrxName());	}

	/** Set Sales Representative.
		@param SalesRep_ID 
		Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID)
	{
		if (SalesRep_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SalesRep_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/** Get Sales Representative.
		@return Sales Representative or Company Agent
	  */
	public int getSalesRep_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}
