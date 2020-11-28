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
package org.adempiere.ad.migration.model;


/** Generated Interface for AD_MigrationStep
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_MigrationStep 
{

    /** TableName=AD_MigrationStep */
    public static final String Table_Name = "AD_MigrationStep";

    /** AD_Table_ID=53218 */
//    public static final int Table_ID = MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

    /** Column name Action */
    public static final String COLUMNNAME_Action = "Action";

	/** Set Action.
	  * Indicates the Action to be performed
	  */
	public void setAction (java.lang.String Action);

	/** Get Action.
	  * Indicates the Action to be performed
	  */
	public java.lang.String getAction();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column name AD_Migration_ID */
    public static final String COLUMNNAME_AD_Migration_ID = "AD_Migration_ID";

	/** Set Migration.
	  * Migration change management.
	  */
	public void setAD_Migration_ID (int AD_Migration_ID);

	/** Get Migration.
	  * Migration change management.
	  */
	public int getAD_Migration_ID();

	public org.adempiere.ad.migration.model.I_AD_Migration getAD_Migration() throws RuntimeException;

	public void setAD_Migration(org.adempiere.ad.migration.model.I_AD_Migration AD_Migration);

    /** Column name AD_MigrationStep_ID */
    public static final String COLUMNNAME_AD_MigrationStep_ID = "AD_MigrationStep_ID";

	/** Set Migration step.
	  * A single step in the migration process
	  */
	public void setAD_MigrationStep_ID (int AD_MigrationStep_ID);

	/** Get Migration step.
	  * A single step in the migration process
	  */
	public int getAD_MigrationStep_ID();

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

	public org.compiere.model.I_AD_Org getAD_Org() throws RuntimeException;

	public void setAD_Org(org.compiere.model.I_AD_Org AD_Org);

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set Table.
	  * Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID);

	/** Get Table.
	  * Database Table information
	  */
	public int getAD_Table_ID();

	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException;

	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table);

    /** Column name Apply */
    public static final String COLUMNNAME_Apply = "Apply";

	/** Set Apply.
	  * Apply migration
	  */
	public void setApply (java.lang.String Apply);

	/** Get Apply.
	  * Apply migration
	  */
	public java.lang.String getApply();

    /** Column name Comments */
    public static final String COLUMNNAME_Comments = "Comments";

	/** Set Comments.
	  * Comments or additional information
	  */
	public void setComments (java.lang.String Comments);

	/** Get Comments.
	  * Comments or additional information
	  */
	public java.lang.String getComments();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public java.sql.Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name DBType */
    public static final String COLUMNNAME_DBType = "DBType";

	/** Set DBType	  */
	public void setDBType (java.lang.String DBType);

	/** Get DBType	  */
	public java.lang.String getDBType();

    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/** Set Error Msg	  */
	public void setErrorMsg (java.lang.String ErrorMsg);

	/** Get Error Msg	  */
	public java.lang.String getErrorMsg();

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

    /** Column name Record_ID */
    public static final String COLUMNNAME_Record_ID = "Record_ID";

	/** Set Record ID.
	  * Direct internal record ID
	  */
	public void setRecord_ID (int Record_ID);

	/** Get Record ID.
	  * Direct internal record ID
	  */
	public int getRecord_ID();

    /** Column name RollbackStatement */
    public static final String COLUMNNAME_RollbackStatement = "RollbackStatement";

	/** Set Rollback Statement.
	  * SQL statement to rollback the current step.
	  */
	public void setRollbackStatement (java.lang.String RollbackStatement);

	/** Get Rollback Statement.
	  * SQL statement to rollback the current step.
	  */
	public java.lang.String getRollbackStatement();

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

    /** Column name SQLStatement */
    public static final String COLUMNNAME_SQLStatement = "SQLStatement";

	/** Set SQLStatement	  */
	public void setSQLStatement (java.lang.String SQLStatement);

	/** Get SQLStatement	  */
	public java.lang.String getSQLStatement();

    /** Column name StatusCode */
    public static final String COLUMNNAME_StatusCode = "StatusCode";

	/** Set Status Code	  */
	public void setStatusCode (java.lang.String StatusCode);

	/** Get Status Code	  */
	public java.lang.String getStatusCode();

    /** Column name StepType */
    public static final String COLUMNNAME_StepType = "StepType";

	/** Set Step type.
	  * Migration step type
	  */
	public void setStepType (java.lang.String StepType);

	/** Get Step type.
	  * Migration step type
	  */
	public java.lang.String getStepType();

    /** Column name TableName */
    public static final String COLUMNNAME_TableName = "TableName";

	/** Set Name der DB-Tabelle	  */
	public void setTableName (java.lang.String TableName);

	/** Get Name der DB-Tabelle	  */
	public java.lang.String getTableName();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public java.sql.Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
