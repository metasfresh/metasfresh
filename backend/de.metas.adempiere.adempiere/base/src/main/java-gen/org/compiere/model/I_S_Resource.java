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

/** Generated Interface for S_Resource
 *  @author Adempiere (generated)
 *  @version Release 3.5.4a
 */
public interface I_S_Resource
{

    /** TableName=S_Resource */
    String Table_Name = "S_Resource";

    /** AD_Table_ID=487 */
    int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	int getAD_Client_ID();

    /** Column name AD_Org_ID */
    String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	int getAD_Org_ID();

    /** Column name AD_User_ID */
    String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	int getAD_User_ID();

	I_AD_User getAD_User() throws RuntimeException;

    /** Column name ChargeableQty */
    String COLUMNNAME_ChargeableQty = "ChargeableQty";

	/** Set Chargeable Quantity	  */
	void setChargeableQty (BigDecimal ChargeableQty);

	/** Get Chargeable Quantity	  */
	BigDecimal getChargeableQty();

    /** Column name Created */
    String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	Timestamp getCreated();

    /** Column name CreatedBy */
    String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	int getCreatedBy();

    /** Column name DailyCapacity */
    String COLUMNNAME_DailyCapacity = "DailyCapacity";

	/** Set Daily Capacity	  */
	void setDailyCapacity (BigDecimal DailyCapacity);

	/** Get Daily Capacity	  */
	BigDecimal getDailyCapacity();

    /** Column name Description */
    String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	String getDescription();

    /** Column name IsActive */
    String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	boolean isActive();

    /** Column name IsAvailable */
    String COLUMNNAME_IsAvailable = "IsAvailable";

	/** Set Available.
	  * Resource is available
	  */
	void setIsAvailable (boolean IsAvailable);

	/** Get Available.
	  * Resource is available
	  */
	boolean isAvailable();

    /** Column name IsManufacturingResource */
    String COLUMNNAME_IsManufacturingResource = "IsManufacturingResource";

	/** Set Manufacturing Resource	  */
	void setIsManufacturingResource (boolean IsManufacturingResource);

	/** Get Manufacturing Resource	  */
	boolean isManufacturingResource();

    /** Column name ManufacturingResourceType */
    String COLUMNNAME_ManufacturingResourceType = "ManufacturingResourceType";

	/** Set Manufacturing Resource Type	  */
	void setManufacturingResourceType (String ManufacturingResourceType);

	/** Get Manufacturing Resource Type	  */
	String getManufacturingResourceType();

    /** Column name Name */
    String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	String getName();

    /** Column name PercentUtilization */
    String COLUMNNAME_PercentUtilization = "PercentUtilization";

	/** Set % Utilization	  */
	void setPercentUtilization (BigDecimal PercentUtilization);

	/** Get % Utilization	  */
	BigDecimal getPercentUtilization();

    /** Column name PlanningHorizon */
    String COLUMNNAME_PlanningHorizon = "PlanningHorizon";

	/** Set Planning Horizon.
	  * The planning horizon is the amount of time (Days) an organisation will look into the future when preparing a strategic plan.
	  */
	void setPlanningHorizon (int PlanningHorizon);

	/** Get Planning Horizon.
	  * The planning horizon is the amount of time (Days) an organisation will look into the future when preparing a strategic plan.
	  */
	int getPlanningHorizon();

    /** Column name QueuingTime */
    String COLUMNNAME_QueuingTime = "QueuingTime";

	/** Set Queuing Time	  */
	void setQueuingTime (BigDecimal QueuingTime);

	/** Get Queuing Time	  */
	BigDecimal getQueuingTime();

    /** Column name S_Resource_ID */
    String COLUMNNAME_S_Resource_ID = "S_Resource_ID";

	/** Set Resource.
	  * Resource
	  */
	void setS_Resource_ID (int S_Resource_ID);

	/** Get Resource.
	  * Resource
	  */
	int getS_Resource_ID();

    /** Column name S_ResourceType_ID */
    String COLUMNNAME_S_ResourceType_ID = "S_ResourceType_ID";

	/** Set Resource Type	  */
	void setS_ResourceType_ID (int S_ResourceType_ID);

	/** Get Resource Type	  */
	int getS_ResourceType_ID();

	I_S_ResourceType getS_ResourceType() throws RuntimeException;

    /** Column name Updated */
    String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	Timestamp getUpdated();

    /** Column name UpdatedBy */
    String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	int getUpdatedBy();

    /** Column name Value */
    String COLUMNNAME_Value = "Value";

	/** Set Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	void setValue (String Value);

	/** Get Search Key.
	  * Search key for the record in the format required - must be unique
	  */
	String getValue();

    /** Column name WaitingTime */
    String COLUMNNAME_WaitingTime = "WaitingTime";

	/** Set Waiting Time.
	  * Workflow Simulation Waiting time
	  */
	void setWaitingTime (BigDecimal WaitingTime);

	/** Get Waiting Time.
	  * Workflow Simulation Waiting time
	  */
	BigDecimal getWaitingTime();

	/**
	 * Set Exclude from MRP.
	 * Exclude from MRP calculation
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setMRP_Exclude (java.lang.String MRP_Exclude);

	/**
	 * Get Exclude from MRP.
	 * Exclude from MRP calculation
	 *
	 * <br>Type: List
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	java.lang.String getMRP_Exclude();

    /** Column definition for MRP_Exclude */
    org.adempiere.model.ModelColumn<I_S_Resource, Object> COLUMN_MRP_Exclude = new org.adempiere.model.ModelColumn<>(I_S_Resource.class, "MRP_Exclude", null);
    /** Column name MRP_Exclude */
    String COLUMNNAME_MRP_Exclude = "MRP_Exclude";
}
