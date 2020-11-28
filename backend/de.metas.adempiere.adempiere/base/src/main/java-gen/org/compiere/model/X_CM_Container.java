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

/** Generated Model for CM_Container
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_CM_Container extends PO implements I_CM_Container, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_CM_Container (Properties ctx, int CM_Container_ID, String trxName)
    {
      super (ctx, CM_Container_ID, trxName);
      /** if (CM_Container_ID == 0)
        {
			setCM_Container_ID (0);
			setCM_Template_ID (0);
			setCM_WebProject_ID (0);
			setContainerType (null);
// D
			setIsIndexed (false);
			setIsSecure (false);
			setIsSummary (false);
			setMeta_Description (null);
			setMeta_Keywords (null);
			setName (null);
			setNotice (null);
			setPriority (0);
        } */
    }

    /** Load Constructor */
    public X_CM_Container (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CM_Container[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Web Container.
		@param CM_Container_ID 
		Web Container contains content like images, text etc.
	  */
	public void setCM_Container_ID (int CM_Container_ID)
	{
		if (CM_Container_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_Container_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_Container_ID, Integer.valueOf(CM_Container_ID));
	}

	/** Get Web Container.
		@return Web Container contains content like images, text etc.
	  */
	public int getCM_Container_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_Container_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CM_Container getCM_ContainerLink() throws RuntimeException
    {
		return (I_CM_Container)MTable.get(getCtx(), I_CM_Container.Table_Name)
			.getPO(getCM_ContainerLink_ID(), get_TrxName());	}

	/** Set Container Link.
		@param CM_ContainerLink_ID 
		Link to another Container in the Web Project
	  */
	public void setCM_ContainerLink_ID (int CM_ContainerLink_ID)
	{
		if (CM_ContainerLink_ID < 1) 
			set_Value (COLUMNNAME_CM_ContainerLink_ID, null);
		else 
			set_Value (COLUMNNAME_CM_ContainerLink_ID, Integer.valueOf(CM_ContainerLink_ID));
	}

	/** Get Container Link.
		@return Link to another Container in the Web Project
	  */
	public int getCM_ContainerLink_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_ContainerLink_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CM_Template getCM_Template() throws RuntimeException
    {
		return (I_CM_Template)MTable.get(getCtx(), I_CM_Template.Table_Name)
			.getPO(getCM_Template_ID(), get_TrxName());	}

	/** Set Template.
		@param CM_Template_ID 
		Template defines how content is displayed
	  */
	public void setCM_Template_ID (int CM_Template_ID)
	{
		if (CM_Template_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_Template_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_Template_ID, Integer.valueOf(CM_Template_ID));
	}

	/** Get Template.
		@return Template defines how content is displayed
	  */
	public int getCM_Template_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_Template_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CM_WebProject getCM_WebProject() throws RuntimeException
    {
		return (I_CM_WebProject)MTable.get(getCtx(), I_CM_WebProject.Table_Name)
			.getPO(getCM_WebProject_ID(), get_TrxName());	}

	/** Set Web Project.
		@param CM_WebProject_ID 
		A web project is the main data container for Containers, URLs, Ads, Media etc.
	  */
	public void setCM_WebProject_ID (int CM_WebProject_ID)
	{
		if (CM_WebProject_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_WebProject_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_WebProject_ID, Integer.valueOf(CM_WebProject_ID));
	}

	/** Get Web Project.
		@return A web project is the main data container for Containers, URLs, Ads, Media etc.
	  */
	public int getCM_WebProject_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_WebProject_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set External Link (URL).
		@param ContainerLinkURL 
		External Link (IRL) for the Container
	  */
	public void setContainerLinkURL (String ContainerLinkURL)
	{
		set_Value (COLUMNNAME_ContainerLinkURL, ContainerLinkURL);
	}

	/** Get External Link (URL).
		@return External Link (IRL) for the Container
	  */
	public String getContainerLinkURL () 
	{
		return (String)get_Value(COLUMNNAME_ContainerLinkURL);
	}

	/** ContainerType AD_Reference_ID=385 */
	public static final int CONTAINERTYPE_AD_Reference_ID=385;
	/** Document = D */
	public static final String CONTAINERTYPE_Document = "D";
	/** Internal Link = L */
	public static final String CONTAINERTYPE_InternalLink = "L";
	/** External URL = U */
	public static final String CONTAINERTYPE_ExternalURL = "U";
	/** Set Web Container Type.
		@param ContainerType 
		Web Container Type
	  */
	public void setContainerType (String ContainerType)
	{

		set_Value (COLUMNNAME_ContainerType, ContainerType);
	}

	/** Get Web Container Type.
		@return Web Container Type
	  */
	public String getContainerType () 
	{
		return (String)get_Value(COLUMNNAME_ContainerType);
	}

	/** Set ContainerXML.
		@param ContainerXML 
		Autogenerated Containerdefinition as XML Code
	  */
	public void setContainerXML (String ContainerXML)
	{
		set_ValueNoCheck (COLUMNNAME_ContainerXML, ContainerXML);
	}

	/** Get ContainerXML.
		@return Autogenerated Containerdefinition as XML Code
	  */
	public String getContainerXML () 
	{
		return (String)get_Value(COLUMNNAME_ContainerXML);
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

	/** Set Indexed.
		@param IsIndexed 
		Index the document for the internal search engine
	  */
	public void setIsIndexed (boolean IsIndexed)
	{
		set_Value (COLUMNNAME_IsIndexed, Boolean.valueOf(IsIndexed));
	}

	/** Get Indexed.
		@return Index the document for the internal search engine
	  */
	public boolean isIndexed () 
	{
		Object oo = get_Value(COLUMNNAME_IsIndexed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Secure content.
		@param IsSecure 
		Defines whether content needs to get encrypted
	  */
	public void setIsSecure (boolean IsSecure)
	{
		set_Value (COLUMNNAME_IsSecure, Boolean.valueOf(IsSecure));
	}

	/** Get Secure content.
		@return Defines whether content needs to get encrypted
	  */
	public boolean isSecure () 
	{
		Object oo = get_Value(COLUMNNAME_IsSecure);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Summary Level.
		@param IsSummary 
		This is a summary entity
	  */
	public void setIsSummary (boolean IsSummary)
	{
		set_ValueNoCheck (COLUMNNAME_IsSummary, Boolean.valueOf(IsSummary));
	}

	/** Get Summary Level.
		@return This is a summary entity
	  */
	public boolean isSummary () 
	{
		Object oo = get_Value(COLUMNNAME_IsSummary);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Valid.
		@param IsValid 
		Element is valid
	  */
	public void setIsValid (boolean IsValid)
	{
		set_Value (COLUMNNAME_IsValid, Boolean.valueOf(IsValid));
	}

	/** Get Valid.
		@return Element is valid
	  */
	public boolean isValid () 
	{
		Object oo = get_Value(COLUMNNAME_IsValid);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Meta Author.
		@param Meta_Author 
		Author of the content
	  */
	public void setMeta_Author (String Meta_Author)
	{
		set_Value (COLUMNNAME_Meta_Author, Meta_Author);
	}

	/** Get Meta Author.
		@return Author of the content
	  */
	public String getMeta_Author () 
	{
		return (String)get_Value(COLUMNNAME_Meta_Author);
	}

	/** Set Meta Content Type.
		@param Meta_Content 
		Defines the type of content i.e. "text/html; charset=UTF-8"
	  */
	public void setMeta_Content (String Meta_Content)
	{
		set_Value (COLUMNNAME_Meta_Content, Meta_Content);
	}

	/** Get Meta Content Type.
		@return Defines the type of content i.e. "text/html; charset=UTF-8"
	  */
	public String getMeta_Content () 
	{
		return (String)get_Value(COLUMNNAME_Meta_Content);
	}

	/** Set Meta Copyright.
		@param Meta_Copyright 
		Contains Copyright information for the content
	  */
	public void setMeta_Copyright (String Meta_Copyright)
	{
		set_Value (COLUMNNAME_Meta_Copyright, Meta_Copyright);
	}

	/** Get Meta Copyright.
		@return Contains Copyright information for the content
	  */
	public String getMeta_Copyright () 
	{
		return (String)get_Value(COLUMNNAME_Meta_Copyright);
	}

	/** Set Meta Description.
		@param Meta_Description 
		Meta info describing the contents of the page
	  */
	public void setMeta_Description (String Meta_Description)
	{
		set_Value (COLUMNNAME_Meta_Description, Meta_Description);
	}

	/** Get Meta Description.
		@return Meta info describing the contents of the page
	  */
	public String getMeta_Description () 
	{
		return (String)get_Value(COLUMNNAME_Meta_Description);
	}

	/** Set Meta Keywords.
		@param Meta_Keywords 
		Contains the keywords for the content
	  */
	public void setMeta_Keywords (String Meta_Keywords)
	{
		set_Value (COLUMNNAME_Meta_Keywords, Meta_Keywords);
	}

	/** Get Meta Keywords.
		@return Contains the keywords for the content
	  */
	public String getMeta_Keywords () 
	{
		return (String)get_Value(COLUMNNAME_Meta_Keywords);
	}

	/** Set Meta Language.
		@param Meta_Language 
		Language HTML Meta Tag
	  */
	public void setMeta_Language (String Meta_Language)
	{
		set_Value (COLUMNNAME_Meta_Language, Meta_Language);
	}

	/** Get Meta Language.
		@return Language HTML Meta Tag
	  */
	public String getMeta_Language () 
	{
		return (String)get_Value(COLUMNNAME_Meta_Language);
	}

	/** Set Meta Publisher.
		@param Meta_Publisher 
		Meta Publisher defines the publisher of the content
	  */
	public void setMeta_Publisher (String Meta_Publisher)
	{
		set_Value (COLUMNNAME_Meta_Publisher, Meta_Publisher);
	}

	/** Get Meta Publisher.
		@return Meta Publisher defines the publisher of the content
	  */
	public String getMeta_Publisher () 
	{
		return (String)get_Value(COLUMNNAME_Meta_Publisher);
	}

	/** Set Meta RobotsTag.
		@param Meta_RobotsTag 
		RobotsTag defines how search robots should handle this content
	  */
	public void setMeta_RobotsTag (String Meta_RobotsTag)
	{
		set_Value (COLUMNNAME_Meta_RobotsTag, Meta_RobotsTag);
	}

	/** Get Meta RobotsTag.
		@return RobotsTag defines how search robots should handle this content
	  */
	public String getMeta_RobotsTag () 
	{
		return (String)get_Value(COLUMNNAME_Meta_RobotsTag);
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

	/** Set Notice.
		@param Notice 
		Contains last write notice
	  */
	public void setNotice (String Notice)
	{
		set_Value (COLUMNNAME_Notice, Notice);
	}

	/** Get Notice.
		@return Contains last write notice
	  */
	public String getNotice () 
	{
		return (String)get_Value(COLUMNNAME_Notice);
	}

	/** Set Priority.
		@param Priority 
		Indicates if this request is of a high, medium or low priority.
	  */
	public void setPriority (int Priority)
	{
		set_Value (COLUMNNAME_Priority, Integer.valueOf(Priority));
	}

	/** Get Priority.
		@return Indicates if this request is of a high, medium or low priority.
	  */
	public int getPriority () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Priority);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Relative URL.
		@param RelativeURL 
		Contains the relative URL for the container
	  */
	public void setRelativeURL (String RelativeURL)
	{
		set_Value (COLUMNNAME_RelativeURL, RelativeURL);
	}

	/** Get Relative URL.
		@return Contains the relative URL for the container
	  */
	public String getRelativeURL () 
	{
		return (String)get_Value(COLUMNNAME_RelativeURL);
	}

	/** Set StructureXML.
		@param StructureXML 
		Autogenerated Containerdefinition as XML Code
	  */
	public void setStructureXML (String StructureXML)
	{
		set_Value (COLUMNNAME_StructureXML, StructureXML);
	}

	/** Get StructureXML.
		@return Autogenerated Containerdefinition as XML Code
	  */
	public String getStructureXML () 
	{
		return (String)get_Value(COLUMNNAME_StructureXML);
	}

	/** Set Title.
		@param Title 
		Name this entity is referred to as
	  */
	public void setTitle (String Title)
	{
		set_Value (COLUMNNAME_Title, Title);
	}

	/** Get Title.
		@return Name this entity is referred to as
	  */
	public String getTitle () 
	{
		return (String)get_Value(COLUMNNAME_Title);
	}
}