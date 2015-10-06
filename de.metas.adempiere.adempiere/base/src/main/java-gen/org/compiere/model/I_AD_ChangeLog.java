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

/** Generated Interface for AD_ChangeLog
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_AD_ChangeLog 
{

    /** TableName=AD_ChangeLog */
    public static final String Table_Name = "AD_ChangeLog";

    /** AD_Table_ID=580 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(6);

    /** Load Meta Data */

    /** Column name AD_ChangeLog_ID */
    public static final String COLUMNNAME_AD_ChangeLog_ID = "AD_ChangeLog_ID";

	/** Set Change Log.
	  * Log of data changes
	  */
	public void setAD_ChangeLog_ID (int AD_ChangeLog_ID);

	/** Get Change Log.
	  * Log of data changes
	  */
	public int getAD_ChangeLog_ID();

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

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

	public I_AD_Column getAD_Column() throws RuntimeException;

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

    /** Column name AD_Session_ID */
    public static final String COLUMNNAME_AD_Session_ID = "AD_Session_ID";

	/** Set Session.
	  * User Session Online or Web
	  */
	public void setAD_Session_ID (int AD_Session_ID);

	/** Get Session.
	  * User Session Online or Web
	  */
	public int getAD_Session_ID();

	public I_AD_Session getAD_Session() throws RuntimeException;

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

	public I_AD_Table getAD_Table() throws RuntimeException;

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

    /** Column name EventChangeLog */
    public static final String COLUMNNAME_EventChangeLog = "EventChangeLog";

	/** Set Event Change Log.
	  * Type of Event in Change Log
	  */
	public void setEventChangeLog (String EventChangeLog);

	/** Get Event Change Log.
	  * Type of Event in Change Log
	  */
	public String getEventChangeLog();

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

    /** Column name IsCustomization */
    public static final String COLUMNNAME_IsCustomization = "IsCustomization";

	/** Set Customization.
	  * The change is a customization of the data dictionary and can be applied after Migration
	  */
	public void setIsCustomization (boolean IsCustomization);

	/** Get Customization.
	  * The change is a customization of the data dictionary and can be applied after Migration
	  */
	public boolean isCustomization();

    /** Column name NewValue */
    public static final String COLUMNNAME_NewValue = "NewValue";

	/** Set New Value.
	  * New field value
	  */
	public void setNewValue (String NewValue);

	/** Get New Value.
	  * New field value
	  */
	public String getNewValue();

    /** Column name OldValue */
    public static final String COLUMNNAME_OldValue = "OldValue";

	/** Set Old Value.
	  * The old file data
	  */
	public void setOldValue (String OldValue);

	/** Get Old Value.
	  * The old file data
	  */
	public String getOldValue();

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

    /** Column name Redo */
    public static final String COLUMNNAME_Redo = "Redo";

	/** Set Redo	  */
	public void setRedo (String Redo);

	/** Get Redo	  */
	public String getRedo();

    /** Column name TrxName */
    public static final String COLUMNNAME_TrxName = "TrxName";

	/** Set Transaction.
	  * Name of the transaction
	  */
	public void setTrxName (String TrxName);

	/** Get Transaction.
	  * Name of the transaction
	  */
	public String getTrxName();

    /** Column name Undo */
    public static final String COLUMNNAME_Undo = "Undo";

	/** Set Undo	  */
	public void setUndo (String Undo);

	/** Get Undo	  */
	public String getUndo();

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
