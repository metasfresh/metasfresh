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

/** Generated Model for B_Offer
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_B_Offer extends PO implements I_B_Offer, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_B_Offer (Properties ctx, int B_Offer_ID, String trxName)
    {
      super (ctx, B_Offer_ID, trxName);
      /** if (B_Offer_ID == 0)
        {
			setAD_User_ID (0);
			setB_Offer_ID (0);
			setB_SellerFunds_ID (0);
			setB_Topic_ID (0);
			setIsWillingToCommit (false);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_B_Offer (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_B_Offer[")
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

	/** Set Offer.
		@param B_Offer_ID 
		Offer for a Topic
	  */
	public void setB_Offer_ID (int B_Offer_ID)
	{
		if (B_Offer_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_B_Offer_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_B_Offer_ID, Integer.valueOf(B_Offer_ID));
	}

	/** Get Offer.
		@return Offer for a Topic
	  */
	public int getB_Offer_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_Offer_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_B_SellerFunds getB_SellerFunds() throws RuntimeException
    {
		return (I_B_SellerFunds)MTable.get(getCtx(), I_B_SellerFunds.Table_Name)
			.getPO(getB_SellerFunds_ID(), get_TrxName());	}

	/** Set Seller Funds.
		@param B_SellerFunds_ID 
		Seller Funds from Offers on Topics
	  */
	public void setB_SellerFunds_ID (int B_SellerFunds_ID)
	{
		if (B_SellerFunds_ID < 1) 
			set_Value (COLUMNNAME_B_SellerFunds_ID, null);
		else 
			set_Value (COLUMNNAME_B_SellerFunds_ID, Integer.valueOf(B_SellerFunds_ID));
	}

	/** Get Seller Funds.
		@return Seller Funds from Offers on Topics
	  */
	public int getB_SellerFunds_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_SellerFunds_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_B_Topic getB_Topic() throws RuntimeException
    {
		return (I_B_Topic)MTable.get(getCtx(), I_B_Topic.Table_Name)
			.getPO(getB_Topic_ID(), get_TrxName());	}

	/** Set Topic.
		@param B_Topic_ID 
		Auction Topic
	  */
	public void setB_Topic_ID (int B_Topic_ID)
	{
		if (B_Topic_ID < 1) 
			set_Value (COLUMNNAME_B_Topic_ID, null);
		else 
			set_Value (COLUMNNAME_B_Topic_ID, Integer.valueOf(B_Topic_ID));
	}

	/** Get Topic.
		@return Auction Topic
	  */
	public int getB_Topic_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_B_Topic_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Willing to commit.
		@param IsWillingToCommit Willing to commit	  */
	public void setIsWillingToCommit (boolean IsWillingToCommit)
	{
		set_Value (COLUMNNAME_IsWillingToCommit, Boolean.valueOf(IsWillingToCommit));
	}

	/** Get Willing to commit.
		@return Willing to commit	  */
	public boolean isWillingToCommit () 
	{
		Object oo = get_Value(COLUMNNAME_IsWillingToCommit);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Private Note.
		@param PrivateNote 
		Private Note - not visible to the other parties
	  */
	public void setPrivateNote (String PrivateNote)
	{
		set_Value (COLUMNNAME_PrivateNote, PrivateNote);
	}

	/** Get Private Note.
		@return Private Note - not visible to the other parties
	  */
	public String getPrivateNote () 
	{
		return (String)get_Value(COLUMNNAME_PrivateNote);
	}

	/** Set Text Message.
		@param TextMsg 
		Text Message
	  */
	public void setTextMsg (String TextMsg)
	{
		set_Value (COLUMNNAME_TextMsg, TextMsg);
	}

	/** Get Text Message.
		@return Text Message
	  */
	public String getTextMsg () 
	{
		return (String)get_Value(COLUMNNAME_TextMsg);
	}
}