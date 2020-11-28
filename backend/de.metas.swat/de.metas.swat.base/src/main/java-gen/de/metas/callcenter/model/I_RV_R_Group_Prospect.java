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
package de.metas.callcenter.model;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.sql.Timestamp;

import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_R_Group;
import org.compiere.model.I_R_Status;
import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

/** Generated Interface for RV_R_Group_Prospect
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_RV_R_Group_Prospect 
{

    /** TableName=RV_R_Group_Prospect */
    public static final String Table_Name = "RV_R_Group_Prospect";

    /** AD_Table_ID=540017 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

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

    /** Column name AD_User_ID */
    public static final String COLUMNNAME_AD_User_ID = "AD_User_ID";

	/** Set User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public void setAD_User_ID (int AD_User_ID);

	/** Get User/Contact.
	  * User within the system - Internal or Business Partner Contact
	  */
	public int getAD_User_ID();

	public I_AD_User getAD_User() throws RuntimeException;

    /** Column name BPValue */
    public static final String COLUMNNAME_BPValue = "BPValue";

	/** Set BP Search Key.
	  * Business Partner Key Value
	  */
	public void setBPValue (String BPValue);

	/** Get BP Search Key.
	  * Business Partner Key Value
	  */
	public String getBPValue();

    /** Column name C_BPartner_ID */
    public static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/** Set Business Partner .
	  * Identifies a Business Partner
	  */
	public void setC_BPartner_ID (int C_BPartner_ID);

	/** Get Business Partner .
	  * Identifies a Business Partner
	  */
	public int getC_BPartner_ID();

	public I_C_BPartner getC_BPartner() throws RuntimeException;

    /** Column name Comments */
    public static final String COLUMNNAME_Comments = "Comments";

	/** Set Comments.
	  * Comments or additional information
	  */
	public void setComments (String Comments);

	/** Get Comments.
	  * Comments or additional information
	  */
	public String getComments();

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

    /** Column name DateNextAction */
    public static final String COLUMNNAME_DateNextAction = "DateNextAction";

	/** Set Date next action.
	  * Date that this request should be acted on
	  */
	public void setDateNextAction (Timestamp DateNextAction);

	/** Get Date next action.
	  * Date that this request should be acted on
	  */
	public Timestamp getDateNextAction();

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

    /** Column name IsClosed */
    public static final String COLUMNNAME_IsClosed = "IsClosed";

	/** Set Closed Status.
	  * The status is closed
	  */
	public void setIsClosed (String IsClosed);

	/** Get Closed Status.
	  * The status is closed
	  */
	public String getIsClosed();

    /** Column name IsFinalClose */
    public static final String COLUMNNAME_IsFinalClose = "IsFinalClose";

	/** Set Final Close.
	  * Entries with Final Close cannot be re-opened
	  */
	public void setIsFinalClose (String IsFinalClose);

	/** Get Final Close.
	  * Entries with Final Close cannot be re-opened
	  */
	public String getIsFinalClose();

    /** Column name Locked */
    public static final String COLUMNNAME_Locked = "Locked";

	/** Set Locked.
	  * Whether the terminal is locked
	  */
	public void setLocked (boolean Locked);

	/** Get Locked.
	  * Whether the terminal is locked
	  */
	public boolean isLocked();

    /** Column name LockedBy */
    public static final String COLUMNNAME_LockedBy = "LockedBy";

	/** Set Locked By.
	  * User who locked this record
	  */
	public void setLockedBy (int LockedBy);

	/** Get Locked By.
	  * User who locked this record
	  */
	public int getLockedBy();

	public I_AD_User getLocke() throws RuntimeException;

    /** Column name LockedDate */
    public static final String COLUMNNAME_LockedDate = "LockedDate";

	/** Set Locked Date.
	  * Date when this record was locked
	  */
	public void setLockedDate (Timestamp LockedDate);

	/** Get Locked Date.
	  * Date when this record was locked
	  */
	public Timestamp getLockedDate();

    /** Column name Phone */
    public static final String COLUMNNAME_Phone = "Phone";

	/** Set Phone.
	  * Identifies a telephone number
	  */
	public void setPhone (String Phone);

	/** Get Phone.
	  * Identifies a telephone number
	  */
	public String getPhone();

    /** Column name R_Group_ID */
    public static final String COLUMNNAME_R_Group_ID = "R_Group_ID";

	/** Set Group.
	  * Request Group
	  */
	public void setR_Group_ID (int R_Group_ID);

	/** Get Group.
	  * Request Group
	  */
	public int getR_Group_ID();

	public I_R_Group getR_Group() throws RuntimeException;

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

    /** Column name R_Status_ID */
    public static final String COLUMNNAME_R_Status_ID = "R_Status_ID";

	/** Set Status.
	  * Request Status
	  */
	public void setR_Status_ID (int R_Status_ID);

	/** Get Status.
	  * Request Status
	  */
	public int getR_Status_ID();

	public I_R_Status getR_Status() throws RuntimeException;

    /** Column name R_Status_Value */
    public static final String COLUMNNAME_R_Status_Value = "R_Status_Value";

	/** Set Request Status	  */
	public void setR_Status_Value (String R_Status_Value);

	/** Get Request Status	  */
	public String getR_Status_Value();

    /** Column name SalesRep_ID */
    public static final String COLUMNNAME_SalesRep_ID = "SalesRep_ID";

	/** Set Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public void setSalesRep_ID (int SalesRep_ID);

	/** Get Sales Representative.
	  * Sales Representative or Company Agent
	  */
	public int getSalesRep_ID();

	public I_AD_User getSalesRep() throws RuntimeException;

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
