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

/** Generated Interface for C_Subscription
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_Subscription 
{

    /** TableName=C_Subscription */
    public static final String Table_Name = "C_Subscription";

    /** AD_Table_ID=669 */
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

    /** Column name C_Subscription_ID */
    public static final String COLUMNNAME_C_Subscription_ID = "C_Subscription_ID";

	/** Set Subscription.
	  * Subscription of a Business Partner of a Product to renew
	  */
	public void setC_Subscription_ID (int C_Subscription_ID);

	/** Get Subscription.
	  * Subscription of a Business Partner of a Product to renew
	  */
	public int getC_Subscription_ID();

    /** Column name C_SubscriptionType_ID */
    public static final String COLUMNNAME_C_SubscriptionType_ID = "C_SubscriptionType_ID";

	/** Set Subscription Type.
	  * Type of subscription
	  */
	public void setC_SubscriptionType_ID (int C_SubscriptionType_ID);

	/** Get Subscription Type.
	  * Type of subscription
	  */
	public int getC_SubscriptionType_ID();

	public I_C_SubscriptionType getC_SubscriptionType() throws RuntimeException;

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

    /** Column name IsDue */
    public static final String COLUMNNAME_IsDue = "IsDue";

	/** Set Due.
	  * Subscription Renewal is Due
	  */
	public void setIsDue (boolean IsDue);

	/** Get Due.
	  * Subscription Renewal is Due
	  */
	public boolean isDue();

    /** Column name M_Product_ID */
    public static final String COLUMNNAME_M_Product_ID = "M_Product_ID";

	/** Set Product.
	  * Product, Service, Item
	  */
	public void setM_Product_ID (int M_Product_ID);

	/** Get Product.
	  * Product, Service, Item
	  */
	public int getM_Product_ID();

	public I_M_Product getM_Product() throws RuntimeException;

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

    /** Column name PaidUntilDate */
    public static final String COLUMNNAME_PaidUntilDate = "PaidUntilDate";

	/** Set Paid Until.
	  * Subscription is paid/valid until this date
	  */
	public void setPaidUntilDate (Timestamp PaidUntilDate);

	/** Get Paid Until.
	  * Subscription is paid/valid until this date
	  */
	public Timestamp getPaidUntilDate();

    /** Column name RenewalDate */
    public static final String COLUMNNAME_RenewalDate = "RenewalDate";

	/** Set Renewal Date	  */
	public void setRenewalDate (Timestamp RenewalDate);

	/** Get Renewal Date	  */
	public Timestamp getRenewalDate();

    /** Column name StartDate */
    public static final String COLUMNNAME_StartDate = "StartDate";

	/** Set Start Date.
	  * First effective day (inclusive)
	  */
	public void setStartDate (Timestamp StartDate);

	/** Get Start Date.
	  * First effective day (inclusive)
	  */
	public Timestamp getStartDate();

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
