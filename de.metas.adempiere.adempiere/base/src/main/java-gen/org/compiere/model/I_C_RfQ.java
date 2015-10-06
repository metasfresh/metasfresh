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

/** Generated Interface for C_RfQ
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_RfQ 
{

    /** TableName=C_RfQ */
    public static final String Table_Name = "C_RfQ";

    /** AD_Table_ID=677 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 1 - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(1);

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

    /** Column name C_BPartner_Location_ID */
    public static final String COLUMNNAME_C_BPartner_Location_ID = "C_BPartner_Location_ID";

	/** Set Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID);

	/** Get Partner Location.
	  * Identifies the (ship to) address for this Business Partner
	  */
	public int getC_BPartner_Location_ID();

	public I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException;

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name CopyLines */
    public static final String COLUMNNAME_CopyLines = "CopyLines";

	/** Set Copy Lines	  */
	public void setCopyLines (String CopyLines);

	/** Get Copy Lines	  */
	public String getCopyLines();

    /** Column name C_Order_ID */
    public static final String COLUMNNAME_C_Order_ID = "C_Order_ID";

	/** Set Order.
	  * Order
	  */
	public void setC_Order_ID (int C_Order_ID);

	/** Get Order.
	  * Order
	  */
	public int getC_Order_ID();

	public I_C_Order getC_Order() throws RuntimeException;

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

    /** Column name CreatePO */
    public static final String COLUMNNAME_CreatePO = "CreatePO";

	/** Set Create PO.
	  * Create Purchase Order
	  */
	public void setCreatePO (String CreatePO);

	/** Get Create PO.
	  * Create Purchase Order
	  */
	public String getCreatePO();

    /** Column name CreateSO */
    public static final String COLUMNNAME_CreateSO = "CreateSO";

	/** Set Create SO	  */
	public void setCreateSO (String CreateSO);

	/** Get Create SO	  */
	public String getCreateSO();

    /** Column name C_RfQ_ID */
    public static final String COLUMNNAME_C_RfQ_ID = "C_RfQ_ID";

	/** Set RfQ.
	  * Request for Quotation
	  */
	public void setC_RfQ_ID (int C_RfQ_ID);

	/** Get RfQ.
	  * Request for Quotation
	  */
	public int getC_RfQ_ID();

    /** Column name C_RfQ_Topic_ID */
    public static final String COLUMNNAME_C_RfQ_Topic_ID = "C_RfQ_Topic_ID";

	/** Set RfQ Topic.
	  * Topic for Request for Quotations
	  */
	public void setC_RfQ_Topic_ID (int C_RfQ_Topic_ID);

	/** Get RfQ Topic.
	  * Topic for Request for Quotations
	  */
	public int getC_RfQ_Topic_ID();

	public I_C_RfQ_Topic getC_RfQ_Topic() throws RuntimeException;

    /** Column name DateResponse */
    public static final String COLUMNNAME_DateResponse = "DateResponse";

	/** Set Response Date.
	  * Date of the Response
	  */
	public void setDateResponse (Timestamp DateResponse);

	/** Get Response Date.
	  * Date of the Response
	  */
	public Timestamp getDateResponse();

    /** Column name DateWorkComplete */
    public static final String COLUMNNAME_DateWorkComplete = "DateWorkComplete";

	/** Set Work Complete.
	  * Date when work is (planned to be) complete
	  */
	public void setDateWorkComplete (Timestamp DateWorkComplete);

	/** Get Work Complete.
	  * Date when work is (planned to be) complete
	  */
	public Timestamp getDateWorkComplete();

    /** Column name DateWorkStart */
    public static final String COLUMNNAME_DateWorkStart = "DateWorkStart";

	/** Set Work Start.
	  * Date when work is (planned to be) started
	  */
	public void setDateWorkStart (Timestamp DateWorkStart);

	/** Get Work Start.
	  * Date when work is (planned to be) started
	  */
	public Timestamp getDateWorkStart();

    /** Column name DeliveryDays */
    public static final String COLUMNNAME_DeliveryDays = "DeliveryDays";

	/** Set Delivery Days.
	  * Number of Days (planned) until Delivery
	  */
	public void setDeliveryDays (int DeliveryDays);

	/** Get Delivery Days.
	  * Number of Days (planned) until Delivery
	  */
	public int getDeliveryDays();

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

    /** Column name DocumentNo */
    public static final String COLUMNNAME_DocumentNo = "DocumentNo";

	/** Set Document No.
	  * Document sequence number of the document
	  */
	public void setDocumentNo (String DocumentNo);

	/** Get Document No.
	  * Document sequence number of the document
	  */
	public String getDocumentNo();

    /** Column name Help */
    public static final String COLUMNNAME_Help = "Help";

	/** Set Comment/Help.
	  * Comment or Hint
	  */
	public void setHelp (String Help);

	/** Get Comment/Help.
	  * Comment or Hint
	  */
	public String getHelp();

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

    /** Column name IsInvitedVendorsOnly */
    public static final String COLUMNNAME_IsInvitedVendorsOnly = "IsInvitedVendorsOnly";

	/** Set Invited Vendors Only.
	  * Only invited vendors can respond to an RfQ
	  */
	public void setIsInvitedVendorsOnly (boolean IsInvitedVendorsOnly);

	/** Get Invited Vendors Only.
	  * Only invited vendors can respond to an RfQ
	  */
	public boolean isInvitedVendorsOnly();

    /** Column name IsQuoteAllQty */
    public static final String COLUMNNAME_IsQuoteAllQty = "IsQuoteAllQty";

	/** Set Quote All Quantities.
	  * Suppliers are requested to provide responses for all quantities
	  */
	public void setIsQuoteAllQty (boolean IsQuoteAllQty);

	/** Get Quote All Quantities.
	  * Suppliers are requested to provide responses for all quantities
	  */
	public boolean isQuoteAllQty();

    /** Column name IsQuoteTotalAmt */
    public static final String COLUMNNAME_IsQuoteTotalAmt = "IsQuoteTotalAmt";

	/** Set Quote Total Amt.
	  * The respnse can have just the total amount for the RfQ
	  */
	public void setIsQuoteTotalAmt (boolean IsQuoteTotalAmt);

	/** Get Quote Total Amt.
	  * The respnse can have just the total amount for the RfQ
	  */
	public boolean isQuoteTotalAmt();

    /** Column name IsRfQResponseAccepted */
    public static final String COLUMNNAME_IsRfQResponseAccepted = "IsRfQResponseAccepted";

	/** Set Responses Accepted.
	  * Are Resonses to the Request for Quotation accepted
	  */
	public void setIsRfQResponseAccepted (boolean IsRfQResponseAccepted);

	/** Get Responses Accepted.
	  * Are Resonses to the Request for Quotation accepted
	  */
	public boolean isRfQResponseAccepted();

    /** Column name IsSelfService */
    public static final String COLUMNNAME_IsSelfService = "IsSelfService";

	/** Set Self-Service.
	  * This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public void setIsSelfService (boolean IsSelfService);

	/** Get Self-Service.
	  * This is a Self-Service entry or this entry can be changed via Self-Service
	  */
	public boolean isSelfService();

    /** Column name Margin */
    public static final String COLUMNNAME_Margin = "Margin";

	/** Set Margin %.
	  * Margin for a product as a percentage
	  */
	public void setMargin (BigDecimal Margin);

	/** Get Margin %.
	  * Margin for a product as a percentage
	  */
	public BigDecimal getMargin();

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

    /** Column name PublishRfQ */
    public static final String COLUMNNAME_PublishRfQ = "PublishRfQ";

	/** Set Publish RfQ	  */
	public void setPublishRfQ (String PublishRfQ);

	/** Get Publish RfQ	  */
	public String getPublishRfQ();

    /** Column name QuoteType */
    public static final String COLUMNNAME_QuoteType = "QuoteType";

	/** Set RfQ Type.
	  * Request for Quotation Type
	  */
	public void setQuoteType (String QuoteType);

	/** Get RfQ Type.
	  * Request for Quotation Type
	  */
	public String getQuoteType();

    /** Column name RankRfQ */
    public static final String COLUMNNAME_RankRfQ = "RankRfQ";

	/** Set Rank RfQ	  */
	public void setRankRfQ (String RankRfQ);

	/** Get Rank RfQ	  */
	public String getRankRfQ();

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
