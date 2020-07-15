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

/** Generated Interface for CM_CStage
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_CM_CStage 
{

    /** TableName=CM_CStage */
    public static final String Table_Name = "CM_CStage";

    /** AD_Table_ID=866 */
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

    /** Column name CM_CStage_ID */
    public static final String COLUMNNAME_CM_CStage_ID = "CM_CStage_ID";

	/** Set Web Container Stage.
	  * Web Container Stage contains the staging content like images, text etc.
	  */
	public void setCM_CStage_ID (int CM_CStage_ID);

	/** Get Web Container Stage.
	  * Web Container Stage contains the staging content like images, text etc.
	  */
	public int getCM_CStage_ID();

    /** Column name CM_CStageLink_ID */
    public static final String COLUMNNAME_CM_CStageLink_ID = "CM_CStageLink_ID";

	/** Set Container Link.
	  * Stage Link to another Container in the Web Project
	  */
	public void setCM_CStageLink_ID (int CM_CStageLink_ID);

	/** Get Container Link.
	  * Stage Link to another Container in the Web Project
	  */
	public int getCM_CStageLink_ID();

	public I_CM_CStage getCM_CStageLink() throws RuntimeException;

    /** Column name CM_Template_ID */
    public static final String COLUMNNAME_CM_Template_ID = "CM_Template_ID";

	/** Set Template.
	  * Template defines how content is displayed
	  */
	public void setCM_Template_ID (int CM_Template_ID);

	/** Get Template.
	  * Template defines how content is displayed
	  */
	public int getCM_Template_ID();

	public I_CM_Template getCM_Template() throws RuntimeException;

    /** Column name CM_WebProject_ID */
    public static final String COLUMNNAME_CM_WebProject_ID = "CM_WebProject_ID";

	/** Set Web Project.
	  * A web project is the main data container for Containers, URLs, Ads, Media etc.
	  */
	public void setCM_WebProject_ID (int CM_WebProject_ID);

	/** Get Web Project.
	  * A web project is the main data container for Containers, URLs, Ads, Media etc.
	  */
	public int getCM_WebProject_ID();

	public I_CM_WebProject getCM_WebProject() throws RuntimeException;

    /** Column name ContainerLinkURL */
    public static final String COLUMNNAME_ContainerLinkURL = "ContainerLinkURL";

	/** Set External Link (URL).
	  * External Link (IRL) for the Container
	  */
	public void setContainerLinkURL (String ContainerLinkURL);

	/** Get External Link (URL).
	  * External Link (IRL) for the Container
	  */
	public String getContainerLinkURL();

    /** Column name ContainerType */
    public static final String COLUMNNAME_ContainerType = "ContainerType";

	/** Set Web Container Type.
	  * Web Container Type
	  */
	public void setContainerType (String ContainerType);

	/** Get Web Container Type.
	  * Web Container Type
	  */
	public String getContainerType();

    /** Column name ContainerXML */
    public static final String COLUMNNAME_ContainerXML = "ContainerXML";

	/** Set ContainerXML.
	  * Autogenerated Containerdefinition as XML Code
	  */
	public void setContainerXML (String ContainerXML);

	/** Get ContainerXML.
	  * Autogenerated Containerdefinition as XML Code
	  */
	public String getContainerXML();

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

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Comment/Help.
	  * Comment or Hint
	  */
	public void setHelp (String Help);

	/** Get Comment/Help.
	  * Comment or Hint
	  */
	public String getHelp();

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

    /** Column name IsIndexed */
    public static final String COLUMNNAME_IsIndexed = "IsIndexed";

	/** Set Indexed.
	  * Index the document for the internal search engine
	  */
	public void setIsIndexed (boolean IsIndexed);

	/** Get Indexed.
	  * Index the document for the internal search engine
	  */
	public boolean isIndexed();

    /** Column name IsModified */
    public static final String COLUMNNAME_IsModified = "IsModified";

	/** Set Modified.
	  * The record is modified
	  */
	public void setIsModified (boolean IsModified);

	/** Get Modified.
	  * The record is modified
	  */
	public boolean isModified();

    /** Column name IsSecure */
    public static final String COLUMNNAME_IsSecure = "IsSecure";

	/** Set Secure content.
	  * Defines whether content needs to get encrypted
	  */
	public void setIsSecure (boolean IsSecure);

	/** Get Secure content.
	  * Defines whether content needs to get encrypted
	  */
	public boolean isSecure();

    /** Column name IsSummary */
    public static final String COLUMNNAME_IsSummary = "IsSummary";

	/** Set Summary Level.
	  * This is a summary entity
	  */
	public void setIsSummary (boolean IsSummary);

	/** Get Summary Level.
	  * This is a summary entity
	  */
	public boolean isSummary();

    /** Column name IsValid */
    public static final String COLUMNNAME_IsValid = "IsValid";

	/** Set Valid.
	  * Element is valid
	  */
	public void setIsValid (boolean IsValid);

	/** Get Valid.
	  * Element is valid
	  */
	public boolean isValid();

    /** Column name Meta_Author */
    public static final String COLUMNNAME_Meta_Author = "Meta_Author";

	/** Set Meta Author.
	  * Author of the content
	  */
	public void setMeta_Author (String Meta_Author);

	/** Get Meta Author.
	  * Author of the content
	  */
	public String getMeta_Author();

    /** Column name Meta_Content */
    public static final String COLUMNNAME_Meta_Content = "Meta_Content";

	/** Set Meta Content Type.
	  * Defines the type of content i.e. "text/html;
 charset=UTF-8"
	  */
	public void setMeta_Content (String Meta_Content);

	/** Get Meta Content Type.
	  * Defines the type of content i.e. "text/html;
 charset=UTF-8"
	  */
	public String getMeta_Content();

    /** Column name Meta_Copyright */
    public static final String COLUMNNAME_Meta_Copyright = "Meta_Copyright";

	/** Set Meta Copyright.
	  * Contains Copyright information for the content
	  */
	public void setMeta_Copyright (String Meta_Copyright);

	/** Get Meta Copyright.
	  * Contains Copyright information for the content
	  */
	public String getMeta_Copyright();

    /** Column name Meta_Description */
    public static final String COLUMNNAME_Meta_Description = "Meta_Description";

	/** Set Meta Description.
	  * Meta info describing the contents of the page
	  */
	public void setMeta_Description (String Meta_Description);

	/** Get Meta Description.
	  * Meta info describing the contents of the page
	  */
	public String getMeta_Description();

    /** Column name Meta_Keywords */
    public static final String COLUMNNAME_Meta_Keywords = "Meta_Keywords";

	/** Set Meta Keywords.
	  * Contains the keywords for the content
	  */
	public void setMeta_Keywords (String Meta_Keywords);

	/** Get Meta Keywords.
	  * Contains the keywords for the content
	  */
	public String getMeta_Keywords();

    /** Column name Meta_Language */
    public static final String COLUMNNAME_Meta_Language = "Meta_Language";

	/** Set Meta Language.
	  * Language HTML Meta Tag
	  */
	public void setMeta_Language (String Meta_Language);

	/** Get Meta Language.
	  * Language HTML Meta Tag
	  */
	public String getMeta_Language();

    /** Column name Meta_Publisher */
    public static final String COLUMNNAME_Meta_Publisher = "Meta_Publisher";

	/** Set Meta Publisher.
	  * Meta Publisher defines the publisher of the content
	  */
	public void setMeta_Publisher (String Meta_Publisher);

	/** Get Meta Publisher.
	  * Meta Publisher defines the publisher of the content
	  */
	public String getMeta_Publisher();

    /** Column name Meta_RobotsTag */
    public static final String COLUMNNAME_Meta_RobotsTag = "Meta_RobotsTag";

	/** Set Meta RobotsTag.
	  * RobotsTag defines how search robots should handle this content
	  */
	public void setMeta_RobotsTag (String Meta_RobotsTag);

	/** Get Meta RobotsTag.
	  * RobotsTag defines how search robots should handle this content
	  */
	public String getMeta_RobotsTag();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name Notice */
    public static final String COLUMNNAME_Notice = "Notice";

	/** Set Notice.
	  * Contains last write notice
	  */
	public void setNotice (String Notice);

	/** Get Notice.
	  * Contains last write notice
	  */
	public String getNotice();

    /** Column name Priority */
    public static final String COLUMNNAME_Priority = "Priority";

	/** Set Priority.
	  * Indicates if this request is of a high, medium or low priority.
	  */
	public void setPriority (int Priority);

	/** Get Priority.
	  * Indicates if this request is of a high, medium or low priority.
	  */
	public int getPriority();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name RelativeURL */
    public static final String COLUMNNAME_RelativeURL = "RelativeURL";

	/** Set Relative URL.
	  * Contains the relative URL for the container
	  */
	public void setRelativeURL (String RelativeURL);

	/** Get Relative URL.
	  * Contains the relative URL for the container
	  */
	public String getRelativeURL();

    /** Column name StructureXML */
    public static final String COLUMNNAME_StructureXML = "StructureXML";

	/** Set StructureXML.
	  * Autogenerated Containerdefinition as XML Code
	  */
	public void setStructureXML (String StructureXML);

	/** Get StructureXML.
	  * Autogenerated Containerdefinition as XML Code
	  */
	public String getStructureXML();

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
