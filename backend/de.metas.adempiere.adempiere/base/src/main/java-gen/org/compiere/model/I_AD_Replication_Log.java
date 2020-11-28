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

/** Generated Interface for AD_Replication_Log
 *  @author Adempiere (generated) 
 *  @version Release 3.6.0LTS
 */
public interface I_AD_Replication_Log 
{

    /** TableName=AD_Replication_Log */
    public static final String Table_Name = "AD_Replication_Log";

    /** AD_Table_ID=604 */
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

    /** Column name AD_Replication_Log_ID */
    public static final String COLUMNNAME_AD_Replication_Log_ID = "AD_Replication_Log_ID";

	/** Set Replication Log.
	  * Data Replication Log Details
	  */
	public void setAD_Replication_Log_ID (int AD_Replication_Log_ID);

	/** Get Replication Log.
	  * Data Replication Log Details
	  */
	public int getAD_Replication_Log_ID();

    /** Column name AD_Replication_Run_ID */
    public static final String COLUMNNAME_AD_Replication_Run_ID = "AD_Replication_Run_ID";

	/** Set Replication Run.
	  * Data Replication Run
	  */
	public void setAD_Replication_Run_ID (int AD_Replication_Run_ID);

	/** Get Replication Run.
	  * Data Replication Run
	  */
	public int getAD_Replication_Run_ID();

	public I_AD_Replication_Run getAD_Replication_Run() throws RuntimeException;

    /** Column name AD_ReplicationTable_ID */
    public static final String COLUMNNAME_AD_ReplicationTable_ID = "AD_ReplicationTable_ID";

	/** Set Replication Table.
	  * Data Replication Strategy Table Info
	  */
	public void setAD_ReplicationTable_ID (int AD_ReplicationTable_ID);

	/** Get Replication Table.
	  * Data Replication Strategy Table Info
	  */
	public int getAD_ReplicationTable_ID();

	public I_AD_ReplicationTable getAD_ReplicationTable() throws RuntimeException;

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

    /** Column name IsReplicated */
    public static final String COLUMNNAME_IsReplicated = "IsReplicated";

	/** Set Replicated.
	  * The data is successfully replicated
	  */
	public void setIsReplicated (boolean IsReplicated);

	/** Get Replicated.
	  * The data is successfully replicated
	  */
	public boolean isReplicated();

    /** Column name P_Msg */
    public static final String COLUMNNAME_P_Msg = "P_Msg";

	/** Set Process Message	  */
	public void setP_Msg (String P_Msg);

	/** Get Process Message	  */
	public String getP_Msg();

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
