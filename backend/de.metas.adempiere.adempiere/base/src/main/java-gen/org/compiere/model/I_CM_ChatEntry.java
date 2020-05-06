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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for CM_ChatEntry
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_CM_ChatEntry 
{

    /** TableName=CM_ChatEntry */
    public static final String Table_Name = "CM_ChatEntry";

    /** AD_Table_ID=877 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public I_AD_User getAD_User() throws RuntimeException;

    /** Column name CharacterData */
    public static final String COLUMNNAME_CharacterData = "CharacterData";

	/** Set Character Data.
	  * Long Character Field
	  */
	public void setCharacterData (String CharacterData);

	/** Get Character Data.
	  * Long Character Field
	  */
	public String getCharacterData();

    /** Column name ChatEntryType */
    public static final String COLUMNNAME_ChatEntryType = "ChatEntryType";

	/** Set Chat Entry Type.
	  * Type of Chat/Forum Entry
	  */
	public void setChatEntryType (String ChatEntryType);

	/** Get Chat Entry Type.
	  * Type of Chat/Forum Entry
	  */
	public String getChatEntryType();

    /** Column name CM_ChatEntryGrandParent_ID */
    public static final String COLUMNNAME_CM_ChatEntryGrandParent_ID = "CM_ChatEntryGrandParent_ID";

	/** Set Chat Entry Grandparent.
	  * Link to Grand Parent (root level)
	  */
	public void setCM_ChatEntryGrandParent_ID (int CM_ChatEntryGrandParent_ID);

	/** Get Chat Entry Grandparent.
	  * Link to Grand Parent (root level)
	  */
	public int getCM_ChatEntryGrandParent_ID();

	public I_CM_ChatEntry getCM_ChatEntryGrandParent() throws RuntimeException;

    /** Column name CM_ChatEntry_ID */
    public static final String COLUMNNAME_CM_ChatEntry_ID = "CM_ChatEntry_ID";

	/** Set Chat Entry.
	  * Individual Chat / Discussion Entry
	  */
	public void setCM_ChatEntry_ID (int CM_ChatEntry_ID);

	/** Get Chat Entry.
	  * Individual Chat / Discussion Entry
	  */
	public int getCM_ChatEntry_ID();

    /** Column name CM_ChatEntryParent_ID */
    public static final String COLUMNNAME_CM_ChatEntryParent_ID = "CM_ChatEntryParent_ID";

	/** Set Chat Entry Parent.
	  * Link to direct Parent
	  */
	public void setCM_ChatEntryParent_ID (int CM_ChatEntryParent_ID);

	/** Get Chat Entry Parent.
	  * Link to direct Parent
	  */
	public int getCM_ChatEntryParent_ID();

	public I_CM_ChatEntry getCM_ChatEntryParent() throws RuntimeException;

    /** Column name CM_Chat_ID */
    public static final String COLUMNNAME_CM_Chat_ID = "CM_Chat_ID";

	/** Set Chat.
	  * Chat or discussion thread
	  */
	public void setCM_Chat_ID (int CM_Chat_ID);

	/** Get Chat.
	  * Chat or discussion thread
	  */
	public int getCM_Chat_ID();

	public I_CM_Chat getCM_Chat() throws RuntimeException;

    /** Column name ConfidentialType */
    public static final String COLUMNNAME_ConfidentialType = "ConfidentialType";

	/** Set Confidentiality.
	  * Type of Confidentiality
	  */
	public void setConfidentialType (String ConfidentialType);

	/** Get Confidentiality.
	  * Type of Confidentiality
	  */
	public String getConfidentialType();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name ModeratorStatus */
    public static final String COLUMNNAME_ModeratorStatus = "ModeratorStatus";

	/** Set Moderation Status.
	  * Status of Moderation
	  */
	public void setModeratorStatus (String ModeratorStatus);

	/** Get Moderation Status.
	  * Status of Moderation
	  */
	public String getModeratorStatus();

    /** Column name Subject */
    public static final String COLUMNNAME_Subject = "Subject";

	/** Set Subject.
	  * Email Message Subject
	  */
	public void setSubject (String Subject);

	/** Get Subject.
	  * Email Message Subject
	  */
	public String getSubject();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
