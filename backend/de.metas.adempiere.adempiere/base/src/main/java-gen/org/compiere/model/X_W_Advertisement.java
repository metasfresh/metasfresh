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
import java.sql.Timestamp;
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for W_Advertisement
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_W_Advertisement extends PO implements I_W_Advertisement, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_W_Advertisement (Properties ctx, int W_Advertisement_ID, String trxName)
    {
      super (ctx, W_Advertisement_ID, trxName);
      /** if (W_Advertisement_ID == 0)
        {
			setAD_User_ID (0);
// -1
			setC_BPartner_ID (0);
			setIsSelfService (true);
// Y
			setName (null);
			setPublishStatus (null);
// U
			setW_Advertisement_ID (0);
        } */
    }

    /** Load Constructor */
    public X_W_Advertisement (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_W_Advertisement[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Advertisement Text.
		@param AdText 
		Text of the Advertisement
	  */
	public void setAdText (String AdText)
	{
		set_Value (COLUMNNAME_AdText, AdText);
	}

	/** Get Advertisement Text.
		@return Text of the Advertisement
	  */
	public String getAdText () 
	{
		return (String)get_Value(COLUMNNAME_AdText);
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

	/** Set Image URL.
		@param ImageURL 
		URL of  image
	  */
	public void setImageURL (String ImageURL)
	{
		set_Value (COLUMNNAME_ImageURL, ImageURL);
	}

	/** Get Image URL.
		@return URL of  image
	  */
	public String getImageURL () 
	{
		return (String)get_Value(COLUMNNAME_ImageURL);
	}

	/** Set Self-Service.
		@param IsSelfService 
		This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public void setIsSelfService (boolean IsSelfService)
	{
		set_Value (COLUMNNAME_IsSelfService, Boolean.valueOf(IsSelfService));
	}

	/** Get Self-Service.
		@return This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public boolean isSelfService () 
	{
		Object oo = get_Value(COLUMNNAME_IsSelfService);
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

	/** PublishStatus AD_Reference_ID=310 */
	public static final int PUBLISHSTATUS_AD_Reference_ID=310;
	/** Released = R */
	public static final String PUBLISHSTATUS_Released = "R";
	/** Test = T */
	public static final String PUBLISHSTATUS_Test = "T";
	/** Under Revision = U */
	public static final String PUBLISHSTATUS_UnderRevision = "U";
	/** Void = V */
	public static final String PUBLISHSTATUS_Void = "V";
	/** Set Publication Status.
		@param PublishStatus 
		Status of Publication
	  */
	public void setPublishStatus (String PublishStatus)
	{

		set_Value (COLUMNNAME_PublishStatus, PublishStatus);
	}

	/** Get Publication Status.
		@return Status of Publication
	  */
	public String getPublishStatus () 
	{
		return (String)get_Value(COLUMNNAME_PublishStatus);
	}

	/** Set Valid from.
		@param ValidFrom 
		Valid from including this date (first day)
	  */
	public void setValidFrom (Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	/** Get Valid from.
		@return Valid from including this date (first day)
	  */
	public Timestamp getValidFrom () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/** Set Valid to.
		@param ValidTo 
		Valid to including this date (last day)
	  */
	public void setValidTo (Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	/** Get Valid to.
		@return Valid to including this date (last day)
	  */
	public Timestamp getValidTo () 
	{
		return (Timestamp)get_Value(COLUMNNAME_ValidTo);
	}

	/** Set Version.
		@param Version 
		Version of the table definition
	  */
	public void setVersion (int Version)
	{
		set_Value (COLUMNNAME_Version, Integer.valueOf(Version));
	}

	/** Get Version.
		@return Version of the table definition
	  */
	public int getVersion () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Version);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Advertisement.
		@param W_Advertisement_ID 
		Web Advertisement
	  */
	public void setW_Advertisement_ID (int W_Advertisement_ID)
	{
		if (W_Advertisement_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_W_Advertisement_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_W_Advertisement_ID, Integer.valueOf(W_Advertisement_ID));
	}

	/** Get Advertisement.
		@return Web Advertisement
	  */
	public int getW_Advertisement_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_Advertisement_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_W_ClickCount getW_ClickCount() throws RuntimeException
    {
		return (I_W_ClickCount)MTable.get(getCtx(), I_W_ClickCount.Table_Name)
			.getPO(getW_ClickCount_ID(), get_TrxName());	}

	/** Set Click Count.
		@param W_ClickCount_ID 
		Web Click Management
	  */
	public void setW_ClickCount_ID (int W_ClickCount_ID)
	{
		if (W_ClickCount_ID < 1) 
			set_Value (COLUMNNAME_W_ClickCount_ID, null);
		else 
			set_Value (COLUMNNAME_W_ClickCount_ID, Integer.valueOf(W_ClickCount_ID));
	}

	/** Get Click Count.
		@return Web Click Management
	  */
	public int getW_ClickCount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_ClickCount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_W_CounterCount getW_CounterCount() throws RuntimeException
    {
		return (I_W_CounterCount)MTable.get(getCtx(), I_W_CounterCount.Table_Name)
			.getPO(getW_CounterCount_ID(), get_TrxName());	}

	/** Set Counter Count.
		@param W_CounterCount_ID 
		Web Counter Count Management
	  */
	public void setW_CounterCount_ID (int W_CounterCount_ID)
	{
		if (W_CounterCount_ID < 1) 
			set_Value (COLUMNNAME_W_CounterCount_ID, null);
		else 
			set_Value (COLUMNNAME_W_CounterCount_ID, Integer.valueOf(W_CounterCount_ID));
	}

	/** Get Counter Count.
		@return Web Counter Count Management
	  */
	public int getW_CounterCount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_W_CounterCount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Web Parameter 1.
		@param WebParam1 
		Web Site Parameter 1 (default: header image)
	  */
	public void setWebParam1 (String WebParam1)
	{
		set_Value (COLUMNNAME_WebParam1, WebParam1);
	}

	/** Get Web Parameter 1.
		@return Web Site Parameter 1 (default: header image)
	  */
	public String getWebParam1 () 
	{
		return (String)get_Value(COLUMNNAME_WebParam1);
	}

	/** Set Web Parameter 2.
		@param WebParam2 
		Web Site Parameter 2 (default index page)
	  */
	public void setWebParam2 (String WebParam2)
	{
		set_Value (COLUMNNAME_WebParam2, WebParam2);
	}

	/** Get Web Parameter 2.
		@return Web Site Parameter 2 (default index page)
	  */
	public String getWebParam2 () 
	{
		return (String)get_Value(COLUMNNAME_WebParam2);
	}

	/** Set Web Parameter 3.
		@param WebParam3 
		Web Site Parameter 3 (default left - menu)
	  */
	public void setWebParam3 (String WebParam3)
	{
		set_Value (COLUMNNAME_WebParam3, WebParam3);
	}

	/** Get Web Parameter 3.
		@return Web Site Parameter 3 (default left - menu)
	  */
	public String getWebParam3 () 
	{
		return (String)get_Value(COLUMNNAME_WebParam3);
	}

	/** Set Web Parameter 4.
		@param WebParam4 
		Web Site Parameter 4 (default footer left)
	  */
	public void setWebParam4 (String WebParam4)
	{
		set_Value (COLUMNNAME_WebParam4, WebParam4);
	}

	/** Get Web Parameter 4.
		@return Web Site Parameter 4 (default footer left)
	  */
	public String getWebParam4 () 
	{
		return (String)get_Value(COLUMNNAME_WebParam4);
	}
}