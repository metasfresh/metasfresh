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

/** Generated Interface for R_IssueKnown
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_R_IssueKnown 
{

    /** TableName=R_IssueKnown */
    public static final String Table_Name = "R_IssueKnown";

    /** AD_Table_ID=839 */
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

    /** Column name IssueStatus */
    public static final String COLUMNNAME_IssueStatus = "IssueStatus";

	/** Set Issue Status.
	  * Current Status of the Issue
	  */
	public void setIssueStatus (String IssueStatus);

	/** Get Issue Status.
	  * Current Status of the Issue
	  */
	public String getIssueStatus();

    /** Column name IssueSummary */
    public static final String COLUMNNAME_IssueSummary = "IssueSummary";

	/** Set Issue Summary.
	  * Issue Summary
	  */
	public void setIssueSummary (String IssueSummary);

	/** Get Issue Summary.
	  * Issue Summary
	  */
	public String getIssueSummary();

    /** Column name LineNo */
    public static final String COLUMNNAME_LineNo = "LineNo";

	/** Set Line.
	  * Line No
	  */
	public void setLineNo (int LineNo);

	/** Get Line.
	  * Line No
	  */
	public int getLineNo();

    /** Column name LoggerName */
    public static final String COLUMNNAME_LoggerName = "LoggerName";

	/** Set Logger.
	  * Logger Name
	  */
	public void setLoggerName (String LoggerName);

	/** Get Logger.
	  * Logger Name
	  */
	public String getLoggerName();

    /** Column name Processing */
    public static final String COLUMNNAME_Processing = "Processing";

	/** Set Process Now	  */
	public void setProcessing (boolean Processing);

	/** Get Process Now	  */
	public boolean isProcessing();

    /** Column name ReleaseNo */
    public static final String COLUMNNAME_ReleaseNo = "ReleaseNo";

	/** Set Release No.
	  * Internal Release Number
	  */
	public void setReleaseNo (String ReleaseNo);

	/** Get Release No.
	  * Internal Release Number
	  */
	public String getReleaseNo();

    /** Column name R_IssueKnown_ID */
    public static final String COLUMNNAME_R_IssueKnown_ID = "R_IssueKnown_ID";

	/** Set Known Issue.
	  * Known Issue
	  */
	public void setR_IssueKnown_ID (int R_IssueKnown_ID);

	/** Get Known Issue.
	  * Known Issue
	  */
	public int getR_IssueKnown_ID();

    /** Column name R_IssueRecommendation_ID */
    public static final String COLUMNNAME_R_IssueRecommendation_ID = "R_IssueRecommendation_ID";

	/** Set Issue Recommendation.
	  * Recommendations how to fix an Issue
	  */
	public void setR_IssueRecommendation_ID (int R_IssueRecommendation_ID);

	/** Get Issue Recommendation.
	  * Recommendations how to fix an Issue
	  */
	public int getR_IssueRecommendation_ID();

	public I_R_IssueRecommendation getR_IssueRecommendation() throws RuntimeException;

    /** Column name R_IssueStatus_ID */
    public static final String COLUMNNAME_R_IssueStatus_ID = "R_IssueStatus_ID";

	/** Set Issue Status.
	  * Status of an Issue
	  */
	public void setR_IssueStatus_ID (int R_IssueStatus_ID);

	/** Get Issue Status.
	  * Status of an Issue
	  */
	public int getR_IssueStatus_ID();

	public I_R_IssueStatus getR_IssueStatus() throws RuntimeException;

    /** Column name R_Request_ID */
    public static final String COLUMNNAME_R_Request_ID = "R_Request_ID";

	/** Set Request.
	  * Request from a Business Partner or Prospect
	  */
	public void setR_Request_ID (int R_Request_ID);

	/** Get Request.
	  * Request from a Business Partner or Prospect
	  */
	public int getR_Request_ID();

	public I_R_Request getR_Request() throws RuntimeException;

    /** Column name SourceClassName */
    public static final String COLUMNNAME_SourceClassName = "SourceClassName";

	/** Set Source Class.
	  * Source Class Name
	  */
	public void setSourceClassName (String SourceClassName);

	/** Get Source Class.
	  * Source Class Name
	  */
	public String getSourceClassName();

    /** Column name SourceMethodName */
    public static final String COLUMNNAME_SourceMethodName = "SourceMethodName";

	/** Set Source Method.
	  * Source Method Name
	  */
	public void setSourceMethodName (String SourceMethodName);

	/** Get Source Method.
	  * Source Method Name
	  */
	public String getSourceMethodName();

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
