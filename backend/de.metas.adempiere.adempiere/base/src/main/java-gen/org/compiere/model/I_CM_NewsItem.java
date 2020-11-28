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

/** Generated Interface for CM_NewsItem
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_CM_NewsItem 
{

    /** TableName=CM_NewsItem */
    public static final String Table_Name = "CM_NewsItem";

    /** AD_Table_ID=871 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(6);

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

    /** Column name Author */
    public static final String COLUMNNAME_Author = "Author";

	/** Set Author.
	  * Author/Creator of the Entity
	  */
	public void setAuthor (String Author);

	/** Get Author.
	  * Author/Creator of the Entity
	  */
	public String getAuthor();

    /** Column name CM_NewsChannel_ID */
    public static final String COLUMNNAME_CM_NewsChannel_ID = "CM_NewsChannel_ID";

	/** Set News Channel.
	  * News channel for rss feed
	  */
	public void setCM_NewsChannel_ID (int CM_NewsChannel_ID);

	/** Get News Channel.
	  * News channel for rss feed
	  */
	public int getCM_NewsChannel_ID();

	public I_CM_NewsChannel getCM_NewsChannel() throws RuntimeException;

    /** Column name CM_NewsItem_ID */
    public static final String COLUMNNAME_CM_NewsItem_ID = "CM_NewsItem_ID";

	/** Set News Item / Article.
	  * News item or article defines base content
	  */
	public void setCM_NewsItem_ID (int CM_NewsItem_ID);

	/** Get News Item / Article.
	  * News item or article defines base content
	  */
	public int getCM_NewsItem_ID();

    /** Column name ContentHTML */
    public static final String COLUMNNAME_ContentHTML = "ContentHTML";

	/** Set Content HTML.
	  * Contains the content itself
	  */
	public void setContentHTML (String ContentHTML);

	/** Get Content HTML.
	  * Contains the content itself
	  */
	public String getContentHTML();

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

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

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

    /** Column name LinkURL */
    public static final String COLUMNNAME_LinkURL = "LinkURL";

	/** Set LinkURL.
	  * Contains URL to a target
	  */
	public void setLinkURL (String LinkURL);

	/** Get LinkURL.
	  * Contains URL to a target
	  */
	public String getLinkURL();

    /** Column name PubDate */
    public static final String COLUMNNAME_PubDate = "PubDate";

	/** Set Publication Date.
	  * Date on which this article will / should get published
	  */
	public void setPubDate (Timestamp PubDate);

	/** Get Publication Date.
	  * Date on which this article will / should get published
	  */
	public Timestamp getPubDate();

    /** Column name Title */
    public static final String COLUMNNAME_Title = "Title";

	/** Set Title.
	  * Name this entity is referred to as
	  */
	public void setTitle (String Title);

	/** Get Title.
	  * Name this entity is referred to as
	  */
	public String getTitle();

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
