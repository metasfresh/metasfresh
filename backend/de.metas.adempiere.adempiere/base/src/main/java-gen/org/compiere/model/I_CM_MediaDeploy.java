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

/** Generated Interface for CM_MediaDeploy
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_CM_MediaDeploy 
{

    /** TableName=CM_MediaDeploy */
    public static final String Table_Name = "CM_MediaDeploy";

    /** AD_Table_ID=892 */
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

    /** Column name CM_MediaDeploy_ID */
    public static final String COLUMNNAME_CM_MediaDeploy_ID = "CM_MediaDeploy_ID";

	/** Set Media Deploy.
	  * Media Deployment Log
	  */
	public void setCM_MediaDeploy_ID (int CM_MediaDeploy_ID);

	/** Get Media Deploy.
	  * Media Deployment Log
	  */
	public int getCM_MediaDeploy_ID();

    /** Column name CM_Media_ID */
    public static final String COLUMNNAME_CM_Media_ID = "CM_Media_ID";

	/** Set Media Item.
	  * Contains media content like images, flash movies etc.
	  */
	public void setCM_Media_ID (int CM_Media_ID);

	/** Get Media Item.
	  * Contains media content like images, flash movies etc.
	  */
	public int getCM_Media_ID();

	public I_CM_Media getCM_Media() throws RuntimeException;

    /** Column name CM_Media_Server_ID */
    public static final String COLUMNNAME_CM_Media_Server_ID = "CM_Media_Server_ID";

	/** Set Media Server.
	  * Media Server list to which content should get transfered
	  */
	public void setCM_Media_Server_ID (int CM_Media_Server_ID);

	/** Get Media Server.
	  * Media Server list to which content should get transfered
	  */
	public int getCM_Media_Server_ID();

	public I_CM_Media_Server getCM_Media_Server() throws RuntimeException;

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

    /** Column name IsDeployed */
    public static final String COLUMNNAME_IsDeployed = "IsDeployed";

	/** Set Deployed.
	  * Entity is deployed
	  */
	public void setIsDeployed (boolean IsDeployed);

	/** Get Deployed.
	  * Entity is deployed
	  */
	public boolean isDeployed();

    /** Column name LastSynchronized */
    public static final String COLUMNNAME_LastSynchronized = "LastSynchronized";

	/** Set Last Synchronized.
	  * Date when last synchronized
	  */
	public void setLastSynchronized (Timestamp LastSynchronized);

	/** Get Last Synchronized.
	  * Date when last synchronized
	  */
	public Timestamp getLastSynchronized();

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
