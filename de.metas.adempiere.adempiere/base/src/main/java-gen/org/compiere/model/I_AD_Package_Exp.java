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

/** Generated Interface for AD_Package_Exp
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_AD_Package_Exp 
{

    /** TableName=AD_Package_Exp */
    public static final String Table_Name = "AD_Package_Exp";

    /** AD_Table_ID=50005 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 4 - System 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(4);

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

    /** Column name AD_Package_Exp_ID */
    public static final String COLUMNNAME_AD_Package_Exp_ID = "AD_Package_Exp_ID";

	/** Set AD_Package_Exp_ID	  */
	public void setAD_Package_Exp_ID (int AD_Package_Exp_ID);

	/** Get AD_Package_Exp_ID	  */
	public int getAD_Package_Exp_ID();

    /** Column name AD_Package_Type */
    public static final String COLUMNNAME_AD_Package_Type = "AD_Package_Type";

	/** Set AD_Package_Type	  */
	public void setAD_Package_Type (String AD_Package_Type);

	/** Get AD_Package_Type	  */
	public String getAD_Package_Type();

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

    /** Column name EMail */
    public static final String COLUMNNAME_EMail = "EMail";

	/** Set EMail Address.
	  * Electronic Mail Address
	  */
	public void setEMail (String EMail);

	/** Get EMail Address.
	  * Electronic Mail Address
	  */
	public String getEMail();

    /** Column name File_Directory */
    public static final String COLUMNNAME_File_Directory = "File_Directory";

	/** Set File_Directory	  */
	public void setFile_Directory (String File_Directory);

	/** Get File_Directory	  */
	public String getFile_Directory();

    /** Column name Instructions */
    public static final String COLUMNNAME_Instructions = "Instructions";

	/** Set Instructions	  */
	public void setInstructions (String Instructions);

	/** Get Instructions	  */
	public String getInstructions();

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

    /** Column name PK_Version */
    public static final String COLUMNNAME_PK_Version = "PK_Version";

	/** Set Package Version	  */
	public void setPK_Version (String PK_Version);

	/** Get Package Version	  */
	public String getPK_Version();

    /** Column name Processed */
    public static final String COLUMNNAME_Processed = "Processed";

	/** Set Processed.
	  * The document has been processed
	  */
	public void setProcessed (boolean Processed);

	/** Get Processed.
	  * The document has been processed
	  */
	public boolean isProcessed();

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

    /** Column name UserName */
    public static final String COLUMNNAME_UserName = "UserName";

	/** Set Registered EMail.
	  * Email of the responsible for the System
	  */
	public void setUserName (String UserName);

	/** Get Registered EMail.
	  * Email of the responsible for the System
	  */
	public String getUserName();

    /** Column name Version */
    public static final String COLUMNNAME_Version = "Version";

	/** Set Version.
	  * Version of the table definition
	  */
	public void setVersion (String Version);

	/** Get Version.
	  * Version of the table definition
	  */
	public String getVersion();
}
