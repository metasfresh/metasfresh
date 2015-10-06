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


/** Generated Interface for AD_MigrationData
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public interface I_AD_MigrationData 
{

    /** TableName=AD_MigrationData */
    public static final String Table_Name = "AD_MigrationData";

    /** AD_Table_ID=53219 */
//    public static final int Table_ID = MTable.getTable_ID(Table_Name);

//    org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
//    java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(4);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

	public org.compiere.model.I_AD_Client getAD_Client() throws RuntimeException;

    /** Column name AD_Column_ID */
    public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/** Set Column.
	  * Column in the table
	  */
	public void setAD_Column_ID (int AD_Column_ID);

	/** Get Column.
	  * Column in the table
	  */
	public int getAD_Column_ID();

	public org.compiere.model.I_AD_Column getAD_Column() throws RuntimeException;

	public void setAD_Column(org.compiere.model.I_AD_Column AD_Column);

    /** Column name AD_MigrationData_ID */
    public static final String COLUMNNAME_AD_MigrationData_ID = "AD_MigrationData_ID";

	/** Set Migration data	  */
	public void setAD_MigrationData_ID (int AD_MigrationData_ID);

	/** Get Migration data	  */
	public int getAD_MigrationData_ID();

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

	public org.adempiere.ad.migration.model.I_AD_MigrationStep getAD_MigrationStep() throws RuntimeException;

	public void setAD_MigrationStep(org.adempiere.ad.migration.model.I_AD_MigrationStep AD_MigrationStep);

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

    /** Column name BackupValue */
    public static final String COLUMNNAME_BackupValue = "BackupValue";

	/** Set Backup Value.
	  * The value of the column prior to migration.
	  */
	public void setBackupValue (java.lang.String BackupValue);

	/** Get Backup Value.
	  * The value of the column prior to migration.
	  */
	public java.lang.String getBackupValue();

    /** Column name ColumnName */
    public static final String COLUMNNAME_ColumnName = "ColumnName";

	/** Set Spaltenname.
	  * Name der Spalte in der Datenbank
	  */
	public void setColumnName (java.lang.String ColumnName);

	/** Get Spaltenname.
	  * Name der Spalte in der Datenbank
	  */
	public java.lang.String getColumnName();

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

    /** Column name IsBackupNull */
    public static final String COLUMNNAME_IsBackupNull = "IsBackupNull";

	/** Set Backup value null.
	  * The backup value is null.
	  */
	public void setIsBackupNull (boolean IsBackupNull);

	/** Get Backup value null.
	  * The backup value is null.
	  */
	public boolean isBackupNull();

    /** Column name IsNewNull */
    public static final String COLUMNNAME_IsNewNull = "IsNewNull";

	/** Set New value null.
	  * The new value is null.
	  */
	public void setIsNewNull (boolean IsNewNull);

	/** Get New value null.
	  * The new value is null.
	  */
	public boolean isNewNull();

    /** Column name IsOldNull */
    public static final String COLUMNNAME_IsOldNull = "IsOldNull";

	/** Set Old value null.
	  * The old value was null.
	  */
	public void setIsOldNull (boolean IsOldNull);

	/** Get Old value null.
	  * The old value was null.
	  */
	public boolean isOldNull();

    /** Column name NewValue */
    public static final String COLUMNNAME_NewValue = "NewValue";

	/** Set New Value.
	  * New field value
	  */
	public void setNewValue (java.lang.String NewValue);

	/** Get New Value.
	  * New field value
	  */
	public java.lang.String getNewValue();

    /** Column name OldValue */
    public static final String COLUMNNAME_OldValue = "OldValue";

	/** Set Old Value.
	  * The old file data
	  */
	public void setOldValue (java.lang.String OldValue);

	/** Get Old Value.
	  * The old file data
	  */
	public java.lang.String getOldValue();

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
