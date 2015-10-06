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

/** Generated Model for CM_Ad
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_CM_Ad extends PO implements I_CM_Ad, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_CM_Ad (Properties ctx, int CM_Ad_ID, String trxName)
    {
      super (ctx, CM_Ad_ID, trxName);
      /** if (CM_Ad_ID == 0)
        {
			setActualClick (0);
			setActualImpression (0);
			setCM_Ad_Cat_ID (0);
			setCM_Ad_ID (0);
			setCM_Media_ID (0);
			setIsAdFlag (false);
			setIsLogged (false);
			setMaxClick (0);
			setMaxImpression (0);
			setName (null);
			setStartDate (new Timestamp( System.currentTimeMillis() ));
			setStartImpression (0);
			setTarget_Frame (null);
        } */
    }

    /** Load Constructor */
    public X_CM_Ad (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_CM_Ad[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Actual Click Count.
		@param ActualClick 
		How many clicks have been counted
	  */
	public void setActualClick (int ActualClick)
	{
		set_Value (COLUMNNAME_ActualClick, Integer.valueOf(ActualClick));
	}

	/** Get Actual Click Count.
		@return How many clicks have been counted
	  */
	public int getActualClick () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ActualClick);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Actual Impression Count.
		@param ActualImpression 
		How many impressions have been counted
	  */
	public void setActualImpression (int ActualImpression)
	{
		set_Value (COLUMNNAME_ActualImpression, Integer.valueOf(ActualImpression));
	}

	/** Get Actual Impression Count.
		@return How many impressions have been counted
	  */
	public int getActualImpression () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ActualImpression);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CM_Ad_Cat getCM_Ad_Cat() throws RuntimeException
    {
		return (I_CM_Ad_Cat)MTable.get(getCtx(), I_CM_Ad_Cat.Table_Name)
			.getPO(getCM_Ad_Cat_ID(), get_TrxName());	}

	/** Set Advertisement Category.
		@param CM_Ad_Cat_ID 
		Advertisement Category like Banner Homepage 
	  */
	public void setCM_Ad_Cat_ID (int CM_Ad_Cat_ID)
	{
		if (CM_Ad_Cat_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_Ad_Cat_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_Ad_Cat_ID, Integer.valueOf(CM_Ad_Cat_ID));
	}

	/** Get Advertisement Category.
		@return Advertisement Category like Banner Homepage 
	  */
	public int getCM_Ad_Cat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_Ad_Cat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Advertisement.
		@param CM_Ad_ID 
		An Advertisement is something like a banner
	  */
	public void setCM_Ad_ID (int CM_Ad_ID)
	{
		if (CM_Ad_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_CM_Ad_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_CM_Ad_ID, Integer.valueOf(CM_Ad_ID));
	}

	/** Get Advertisement.
		@return An Advertisement is something like a banner
	  */
	public int getCM_Ad_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_Ad_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_CM_Media getCM_Media() throws RuntimeException
    {
		return (I_CM_Media)MTable.get(getCtx(), I_CM_Media.Table_Name)
			.getPO(getCM_Media_ID(), get_TrxName());	}

	/** Set Media Item.
		@param CM_Media_ID 
		Contains media content like images, flash movies etc.
	  */
	public void setCM_Media_ID (int CM_Media_ID)
	{
		if (CM_Media_ID < 1) 
			set_Value (COLUMNNAME_CM_Media_ID, null);
		else 
			set_Value (COLUMNNAME_CM_Media_ID, Integer.valueOf(CM_Media_ID));
	}

	/** Get Media Item.
		@return Contains media content like images, flash movies etc.
	  */
	public int getCM_Media_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_CM_Media_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Content HTML.
		@param ContentHTML 
		Contains the content itself
	  */
	public void setContentHTML (String ContentHTML)
	{
		set_Value (COLUMNNAME_ContentHTML, ContentHTML);
	}

	/** Get Content HTML.
		@return Contains the content itself
	  */
	public String getContentHTML () 
	{
		return (String)get_Value(COLUMNNAME_ContentHTML);
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

	/** Set End Date.
		@param EndDate 
		Last effective date (inclusive)
	  */
	public void setEndDate (Timestamp EndDate)
	{
		set_Value (COLUMNNAME_EndDate, EndDate);
	}

	/** Get End Date.
		@return Last effective date (inclusive)
	  */
	public Timestamp getEndDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_EndDate);
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

	/** Set Special AD Flag.
		@param IsAdFlag 
		Do we need to specially mention this ad?
	  */
	public void setIsAdFlag (boolean IsAdFlag)
	{
		set_Value (COLUMNNAME_IsAdFlag, Boolean.valueOf(IsAdFlag));
	}

	/** Get Special AD Flag.
		@return Do we need to specially mention this ad?
	  */
	public boolean isAdFlag () 
	{
		Object oo = get_Value(COLUMNNAME_IsAdFlag);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Logging.
		@param IsLogged 
		Do we need to log the banner impressions and clicks? (needs much performance)
	  */
	public void setIsLogged (boolean IsLogged)
	{
		set_Value (COLUMNNAME_IsLogged, Boolean.valueOf(IsLogged));
	}

	/** Get Logging.
		@return Do we need to log the banner impressions and clicks? (needs much performance)
	  */
	public boolean isLogged () 
	{
		Object oo = get_Value(COLUMNNAME_IsLogged);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Max Click Count.
		@param MaxClick 
		Maximum Click Count until banner is deactivated
	  */
	public void setMaxClick (int MaxClick)
	{
		set_Value (COLUMNNAME_MaxClick, Integer.valueOf(MaxClick));
	}

	/** Get Max Click Count.
		@return Maximum Click Count until banner is deactivated
	  */
	public int getMaxClick () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxClick);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Max Impression Count.
		@param MaxImpression 
		Maximum Impression Count until banner is deactivated
	  */
	public void setMaxImpression (int MaxImpression)
	{
		set_Value (COLUMNNAME_MaxImpression, Integer.valueOf(MaxImpression));
	}

	/** Get Max Impression Count.
		@return Maximum Impression Count until banner is deactivated
	  */
	public int getMaxImpression () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxImpression);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Start Date.
		@param StartDate 
		First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate)
	{
		set_Value (COLUMNNAME_StartDate, StartDate);
	}

	/** Get Start Date.
		@return First effective day (inclusive)
	  */
	public Timestamp getStartDate () 
	{
		return (Timestamp)get_Value(COLUMNNAME_StartDate);
	}

	/** Set Start Count Impression.
		@param StartImpression 
		For rotation we need a start count
	  */
	public void setStartImpression (int StartImpression)
	{
		set_Value (COLUMNNAME_StartImpression, Integer.valueOf(StartImpression));
	}

	/** Get Start Count Impression.
		@return For rotation we need a start count
	  */
	public int getStartImpression () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_StartImpression);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Target Frame.
		@param Target_Frame 
		Which target should be used if user clicks?
	  */
	public void setTarget_Frame (String Target_Frame)
	{
		set_Value (COLUMNNAME_Target_Frame, Target_Frame);
	}

	/** Get Target Frame.
		@return Which target should be used if user clicks?
	  */
	public String getTarget_Frame () 
	{
		return (String)get_Value(COLUMNNAME_Target_Frame);
	}

	/** Set Target URL.
		@param TargetURL 
		URL for the Target
	  */
	public void setTargetURL (String TargetURL)
	{
		set_Value (COLUMNNAME_TargetURL, TargetURL);
	}

	/** Get Target URL.
		@return URL for the Target
	  */
	public String getTargetURL () 
	{
		return (String)get_Value(COLUMNNAME_TargetURL);
	}
}