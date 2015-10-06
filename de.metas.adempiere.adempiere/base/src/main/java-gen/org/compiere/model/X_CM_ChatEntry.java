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

/** Generated Model for CM_ChatEntry
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_CM_ChatEntry extends PO implements I_CM_ChatEntry, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_CM_ChatEntry (Properties ctx, int CM_ChatEntry_ID, String trxName)
    {
      super (ctx, CM_ChatEntry_ID, trxName);
      /** if (CM_ChatEntry_ID == 0)
        {
			setCharacterData (null);
			setChatEntryType (null);
// N
			setCM_ChatEntry_ID (0);
			setCM_Chat_ID (0);
			setConfidentialType (null);
        } */
    }

    /** Load Constructor */
    public X_CM_ChatEntry (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CM_ChatEntry[")
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

	/** Set Character Data.
		@param CharacterData 
		Long Character Field
	  */
	public void setCharacterData (String CharacterData)
	{
		set_ValueNoCheck (COLUMNNAME_CharacterData, CharacterData);
	}

	/** Get Character Data.
		@return Long Character Field
	  */
	public String getCharacterData () 
	{
		return (String)get_Value(COLUMNNAME_CharacterData);
	}

	/** ChatEntryType AD_Reference_ID=398 */
	public static final int CHATENTRYTYPE_AD_Reference_ID=398;
	/** Wiki = W */
	public static final String CHATENTRYTYPE_Wiki = "W";
	/** Note (flat) = N */
	public static final String CHATENTRYTYPE_NoteFlat = "N";
	/** Forum (threaded) = F */
	public static final String CHATENTRYTYPE_ForumThreaded = "F";
	/** Set Chat Entry Type.
		@param ChatEntryType 
		Type of Chat/Forum Entry
	  */
	public void setChatEntryType (String ChatEntryType)
	{

		set_Value (COLUMNNAME_ChatEntryType, ChatEntryType);
	}

	/** Get Chat Entry Type.
		@return Type of Chat/Forum Entry
	  */
	public String getChatEntryType () 
	{
		return (String)get_Value(COLUMNNAME_ChatEntryType);
	}

	public I_CM_ChatEntry getCM_ChatEntryGrandParent() throws RuntimeException
    {
		return (I_CM_ChatEntry)MTable.get(getCtx(), I_CM_ChatEntry.Table_Name)
			.getPO(getCM_ChatEntryGrandParent_ID(), get_TrxName());	}

	/** Set Chat Entry Grandparent.
		@param CM_ChatEntryGrandParent_ID 
		Link to Grand Parent (root level)
	  */
	public void setCM_ChatEntryGrandParent_ID (int CM_ChatEntryGrandParent_ID)
	{
		if (CM_ChatEntryGrandParent_ID < 1) 
			set_Value (COLUMNNAME_CM_ChatEntryGrandParent_ID, null);
		else 
			set_Value (COLUMNNAME_CM_ChatEntryGrandParent_ID, Integer.valueOf(CM_ChatEntryGrandParent_ID));
	}

	/** Get Chat Entry Grandparent.
		@return Link to Grand Parent (root level)
	  */
	public int getCM_ChatEntryGrandParent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_ChatEntryGrandParent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Chat Entry.
		@param CM_ChatEntry_ID 
		Individual Chat / Discussion Entry
	  */
	public void setCM_ChatEntry_ID (int CM_ChatEntry_ID)
	{
		if (CM_ChatEntry_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_ChatEntry_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_ChatEntry_ID, Integer.valueOf(CM_ChatEntry_ID));
	}

	/** Get Chat Entry.
		@return Individual Chat / Discussion Entry
	  */
	public int getCM_ChatEntry_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_ChatEntry_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), String.valueOf(getCM_ChatEntry_ID()));
    }

	public I_CM_ChatEntry getCM_ChatEntryParent() throws RuntimeException
    {
		return (I_CM_ChatEntry)MTable.get(getCtx(), I_CM_ChatEntry.Table_Name)
			.getPO(getCM_ChatEntryParent_ID(), get_TrxName());	}

	/** Set Chat Entry Parent.
		@param CM_ChatEntryParent_ID 
		Link to direct Parent
	  */
	public void setCM_ChatEntryParent_ID (int CM_ChatEntryParent_ID)
	{
		if (CM_ChatEntryParent_ID < 1) 
			set_Value (COLUMNNAME_CM_ChatEntryParent_ID, null);
		else 
			set_Value (COLUMNNAME_CM_ChatEntryParent_ID, Integer.valueOf(CM_ChatEntryParent_ID));
	}

	/** Get Chat Entry Parent.
		@return Link to direct Parent
	  */
	public int getCM_ChatEntryParent_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_ChatEntryParent_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CM_Chat getCM_Chat() throws RuntimeException
    {
		return (I_CM_Chat)MTable.get(getCtx(), I_CM_Chat.Table_Name)
			.getPO(getCM_Chat_ID(), get_TrxName());	}

	/** Set Chat.
		@param CM_Chat_ID 
		Chat or discussion thread
	  */
	public void setCM_Chat_ID (int CM_Chat_ID)
	{
		if (CM_Chat_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_Chat_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_Chat_ID, Integer.valueOf(CM_Chat_ID));
	}

	/** Get Chat.
		@return Chat or discussion thread
	  */
	public int getCM_Chat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_Chat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** ConfidentialType AD_Reference_ID=340 */
	public static final int CONFIDENTIALTYPE_AD_Reference_ID=340;
	/** Public Information = A */
	public static final String CONFIDENTIALTYPE_PublicInformation = "A";
	/** Partner Confidential = C */
	public static final String CONFIDENTIALTYPE_PartnerConfidential = "C";
	/** Internal = I */
	public static final String CONFIDENTIALTYPE_Internal = "I";
	/** Private Information = P */
	public static final String CONFIDENTIALTYPE_PrivateInformation = "P";
	/** Set Confidentiality.
		@param ConfidentialType 
		Type of Confidentiality
	  */
	public void setConfidentialType (String ConfidentialType)
	{

		set_Value (COLUMNNAME_ConfidentialType, ConfidentialType);
	}

	/** Get Confidentiality.
		@return Type of Confidentiality
	  */
	public String getConfidentialType () 
	{
		return (String)get_Value(COLUMNNAME_ConfidentialType);
	}

	/** ModeratorStatus AD_Reference_ID=396 */
	public static final int MODERATORSTATUS_AD_Reference_ID=396;
	/** Not Displayed = N */
	public static final String MODERATORSTATUS_NotDisplayed = "N";
	/** Published = P */
	public static final String MODERATORSTATUS_Published = "P";
	/** To be reviewed = R */
	public static final String MODERATORSTATUS_ToBeReviewed = "R";
	/** Suspicious = S */
	public static final String MODERATORSTATUS_Suspicious = "S";
	/** Set Moderation Status.
		@param ModeratorStatus 
		Status of Moderation
	  */
	public void setModeratorStatus (String ModeratorStatus)
	{

		set_Value (COLUMNNAME_ModeratorStatus, ModeratorStatus);
	}

	/** Get Moderation Status.
		@return Status of Moderation
	  */
	public String getModeratorStatus () 
	{
		return (String)get_Value(COLUMNNAME_ModeratorStatus);
	}

	/** Set Subject.
		@param Subject 
		Email Message Subject
	  */
	public void setSubject (String Subject)
	{
		set_Value (COLUMNNAME_Subject, Subject);
	}

	/** Get Subject.
		@return Email Message Subject
	  */
	public String getSubject () 
	{
		return (String)get_Value(COLUMNNAME_Subject);
	}
}