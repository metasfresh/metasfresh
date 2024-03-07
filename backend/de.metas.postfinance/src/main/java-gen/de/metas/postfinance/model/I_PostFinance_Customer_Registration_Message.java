/*
 * #%L
 * de.metas.postfinance
 * %%
 * Copyright (C) 2024 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.postfinance.model;

import javax.annotation.Nullable;
import org.adempiere.model.ModelColumn;

/** Generated Interface for PostFinance_Customer_Registration_Message
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public interface I_PostFinance_Customer_Registration_Message 
{

	String Table_Name = "PostFinance_Customer_Registration_Message";

//	/** AD_Table_ID=542391 */
//	int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);


	/**
	 * Get Client.
	 * Client/Tenant for this installation.
	 *
	 * <br>Type: TableDir
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Client_ID();

	String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/**
	 * Set Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setAD_Org_ID (int AD_Org_ID);

	/**
	 * Get Organisation.
	 * Organisational entity within client
	 *
	 * <br>Type: Search
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getAD_Org_ID();

	String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/**
	 * Set Additional Data.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setAdditionalData (@Nullable java.lang.String AdditionalData);

	/**
	 * Get Additional Data.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getAdditionalData();

	ModelColumn<I_PostFinance_Customer_Registration_Message, Object> COLUMN_AdditionalData = new ModelColumn<>(I_PostFinance_Customer_Registration_Message.class, "AdditionalData", null);
	String COLUMNNAME_AdditionalData = "AdditionalData";

	/**
	 * Set Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setC_BPartner_ID (int C_BPartner_ID);

	/**
	 * Get Business Partner.
	 *
	 * <br>Type: Search
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	int getC_BPartner_ID();

	String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	/**
	 * Get Created.
	 * Date this record was created
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getCreated();

	ModelColumn<I_PostFinance_Customer_Registration_Message, Object> COLUMN_Created = new ModelColumn<>(I_PostFinance_Customer_Registration_Message.class, "Created", null);
	String COLUMNNAME_Created = "Created";

	/**
	 * Get Created By.
	 * User who created this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getCreatedBy();

	String COLUMNNAME_CreatedBy = "CreatedBy";

	/**
	 * Set Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setIsActive (boolean IsActive);

	/**
	 * Get Active.
	 * The record is active in the system
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isActive();

	ModelColumn<I_PostFinance_Customer_Registration_Message, Object> COLUMN_IsActive = new ModelColumn<>(I_PostFinance_Customer_Registration_Message.class, "IsActive", null);
	String COLUMNNAME_IsActive = "IsActive";

	/**
	 * Set PostFinance Customer Registration Message.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPostFinance_Customer_Registration_Message_ID (int PostFinance_Customer_Registration_Message_ID);

	/**
	 * Get PostFinance Customer Registration Message.
	 *
	 * <br>Type: ID
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getPostFinance_Customer_Registration_Message_ID();

	ModelColumn<I_PostFinance_Customer_Registration_Message, Object> COLUMN_PostFinance_Customer_Registration_Message_ID = new ModelColumn<>(I_PostFinance_Customer_Registration_Message.class, "PostFinance_Customer_Registration_Message_ID", null);
	String COLUMNNAME_PostFinance_Customer_Registration_Message_ID = "PostFinance_Customer_Registration_Message_ID";

	/**
	 * Set Customer eBill ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setPostFinance_Receiver_eBillId (java.lang.String PostFinance_Receiver_eBillId);

	/**
	 * Get Customer eBill ID.
	 *
	 * <br>Type: String
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getPostFinance_Receiver_eBillId();

	ModelColumn<I_PostFinance_Customer_Registration_Message, Object> COLUMN_PostFinance_Receiver_eBillId = new ModelColumn<>(I_PostFinance_Customer_Registration_Message.class, "PostFinance_Receiver_eBillId", null);
	String COLUMNNAME_PostFinance_Receiver_eBillId = "PostFinance_Receiver_eBillId";

	/**
	 * Set Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setProcessed (boolean Processed);

	/**
	 * Get Processed.
	 *
	 * <br>Type: YesNo
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	boolean isProcessed();

	ModelColumn<I_PostFinance_Customer_Registration_Message, Object> COLUMN_Processed = new ModelColumn<>(I_PostFinance_Customer_Registration_Message.class, "Processed", null);
	String COLUMNNAME_Processed = "Processed";

	/**
	 * Set QR Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	void setQRCode (@Nullable java.lang.String QRCode);

	/**
	 * Get QR Code.
	 *
	 * <br>Type: String
	 * <br>Mandatory: false
	 * <br>Virtual Column: false
	 */
	@Nullable java.lang.String getQRCode();

	ModelColumn<I_PostFinance_Customer_Registration_Message, Object> COLUMN_QRCode = new ModelColumn<>(I_PostFinance_Customer_Registration_Message.class, "QRCode", null);
	String COLUMNNAME_QRCode = "QRCode";

	/**
	 * Set Subscription Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	void setSubscriptionType (java.lang.String SubscriptionType);

	/**
	 * Get Subscription Type.
	 *
	 * <br>Type: List
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.lang.String getSubscriptionType();

	ModelColumn<I_PostFinance_Customer_Registration_Message, Object> COLUMN_SubscriptionType = new ModelColumn<>(I_PostFinance_Customer_Registration_Message.class, "SubscriptionType", null);
	String COLUMNNAME_SubscriptionType = "SubscriptionType";

	/**
	 * Get Updated.
	 * Date this record was updated
	 *
	 * <br>Type: DateTime
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	java.sql.Timestamp getUpdated();

	ModelColumn<I_PostFinance_Customer_Registration_Message, Object> COLUMN_Updated = new ModelColumn<>(I_PostFinance_Customer_Registration_Message.class, "Updated", null);
	String COLUMNNAME_Updated = "Updated";

	/**
	 * Get Updated By.
	 * User who updated this records
	 *
	 * <br>Type: Table
	 * <br>Mandatory: true
	 * <br>Virtual Column: false
	 */
	int getUpdatedBy();

	String COLUMNNAME_UpdatedBy = "UpdatedBy";
}
