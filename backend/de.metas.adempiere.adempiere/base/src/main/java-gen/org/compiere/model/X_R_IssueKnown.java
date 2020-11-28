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

/** Generated Model for R_IssueKnown
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_R_IssueKnown extends PO implements I_R_IssueKnown, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_R_IssueKnown (Properties ctx, int R_IssueKnown_ID, String trxName)
    {
      super (ctx, R_IssueKnown_ID, trxName);
      /** if (R_IssueKnown_ID == 0)
        {
			setIssueSummary (null);
			setReleaseNo (null);
			setR_IssueKnown_ID (0);
        } */
    }

    /** Load Constructor */
    public X_R_IssueKnown (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_R_IssueKnown[")
        .append(get_ID()).append("]");
      return sb.toString();
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

	/** Set Issue Status.
		@param IssueStatus 
		Current Status of the Issue
	  */
	public void setIssueStatus (String IssueStatus)
	{
		set_Value (COLUMNNAME_IssueStatus, IssueStatus);
	}

	/** Get Issue Status.
		@return Current Status of the Issue
	  */
	public String getIssueStatus () 
	{
		return (String)get_Value(COLUMNNAME_IssueStatus);
	}

	/** Set Issue Summary.
		@param IssueSummary 
		Issue Summary
	  */
	public void setIssueSummary (String IssueSummary)
	{
		set_Value (COLUMNNAME_IssueSummary, IssueSummary);
	}

	/** Get Issue Summary.
		@return Issue Summary
	  */
	public String getIssueSummary () 
	{
		return (String)get_Value(COLUMNNAME_IssueSummary);
	}

	/** Set Line.
		@param LineNo 
		Line No
	  */
	public void setLineNo (int LineNo)
	{
		set_Value (COLUMNNAME_LineNo, Integer.valueOf(LineNo));
	}

	/** Get Line.
		@return Line No
	  */
	public int getLineNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LineNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Logger.
		@param LoggerName 
		Logger Name
	  */
	public void setLoggerName (String LoggerName)
	{
		set_Value (COLUMNNAME_LoggerName, LoggerName);
	}

	/** Get Logger.
		@return Logger Name
	  */
	public String getLoggerName () 
	{
		return (String)get_Value(COLUMNNAME_LoggerName);
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

	/** Set Release No.
		@param ReleaseNo 
		Internal Release Number
	  */
	public void setReleaseNo (String ReleaseNo)
	{
		set_Value (COLUMNNAME_ReleaseNo, ReleaseNo);
	}

	/** Get Release No.
		@return Internal Release Number
	  */
	public String getReleaseNo () 
	{
		return (String)get_Value(COLUMNNAME_ReleaseNo);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getReleaseNo());
    }

	/** Set Known Issue.
		@param R_IssueKnown_ID 
		Known Issue
	  */
	public void setR_IssueKnown_ID (int R_IssueKnown_ID)
	{
		if (R_IssueKnown_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_R_IssueKnown_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_R_IssueKnown_ID, Integer.valueOf(R_IssueKnown_ID));
	}

	/** Get Known Issue.
		@return Known Issue
	  */
	public int getR_IssueKnown_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_IssueKnown_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_R_IssueRecommendation getR_IssueRecommendation() throws RuntimeException
    {
		return (I_R_IssueRecommendation)MTable.get(getCtx(), I_R_IssueRecommendation.Table_Name)
			.getPO(getR_IssueRecommendation_ID(), get_TrxName());	}

	/** Set Issue Recommendation.
		@param R_IssueRecommendation_ID 
		Recommendations how to fix an Issue
	  */
	public void setR_IssueRecommendation_ID (int R_IssueRecommendation_ID)
	{
		if (R_IssueRecommendation_ID < 1) 
			set_Value (COLUMNNAME_R_IssueRecommendation_ID, null);
		else 
			set_Value (COLUMNNAME_R_IssueRecommendation_ID, Integer.valueOf(R_IssueRecommendation_ID));
	}

	/** Get Issue Recommendation.
		@return Recommendations how to fix an Issue
	  */
	public int getR_IssueRecommendation_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_IssueRecommendation_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_R_IssueStatus getR_IssueStatus() throws RuntimeException
    {
		return (I_R_IssueStatus)MTable.get(getCtx(), I_R_IssueStatus.Table_Name)
			.getPO(getR_IssueStatus_ID(), get_TrxName());	}

	/** Set Issue Status.
		@param R_IssueStatus_ID 
		Status of an Issue
	  */
	public void setR_IssueStatus_ID (int R_IssueStatus_ID)
	{
		if (R_IssueStatus_ID < 1) 
			set_Value (COLUMNNAME_R_IssueStatus_ID, null);
		else 
			set_Value (COLUMNNAME_R_IssueStatus_ID, Integer.valueOf(R_IssueStatus_ID));
	}

	/** Get Issue Status.
		@return Status of an Issue
	  */
	public int getR_IssueStatus_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_R_IssueStatus_ID);
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

	/** Set Source Class.
		@param SourceClassName 
		Source Class Name
	  */
	public void setSourceClassName (String SourceClassName)
	{
		set_Value (COLUMNNAME_SourceClassName, SourceClassName);
	}

	/** Get Source Class.
		@return Source Class Name
	  */
	public String getSourceClassName () 
	{
		return (String)get_Value(COLUMNNAME_SourceClassName);
	}

	/** Set Source Method.
		@param SourceMethodName 
		Source Method Name
	  */
	public void setSourceMethodName (String SourceMethodName)
	{
		set_Value (COLUMNNAME_SourceMethodName, SourceMethodName);
	}

	/** Get Source Method.
		@return Source Method Name
	  */
	public String getSourceMethodName () 
	{
		return (String)get_Value(COLUMNNAME_SourceMethodName);
	}
}